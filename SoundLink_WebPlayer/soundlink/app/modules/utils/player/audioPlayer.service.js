'use strict';

angular.module('soundlink').service("audioPlayer", audioPlayer);

audioPlayer.$inject = ['audioStatus', '$q', '$cookies', 'tokenStorage', 'config', '$timeout', "$rootScope", 'eventManager', 'lodash'];

function audioPlayer(audioStatus, $q, $cookies, tokenStorage, config, $timeout, $rootScope, eventManager, lodash) {

  var audioPlayer = this;

  var audio;

  audioPlayer.setVolume = function (volume) {
    var volumePerOne = volume / 100;
    audioStatus.setVolume(volumePerOne);
    if (audio != null) {
      audio.volume = volumePerOne;
    }
  };

  audioPlayer.setAudioPosition = function (positionInPercent) {
    var time = (audioStatus.getDuration() * positionInPercent) / 100;
    if (audio != null) {
      audio.currentTime = time;
    }
  };

  audioPlayer.add = function (song) {
    var songFind = lodash.find(audioStatus.getPlaylist(), function (music) {
      return song.id === music.id;
    });
    if (songFind == null) {
      $timeout(function () {
        audioStatus.getPlaylist().push(song);
        eventManager.fireEvent("musicAdded", song);
      });
    } else {
      $timeout(function () {
        lodash.remove(audioStatus.getPlaylist(), function (music) {
          return song.id === music.id;
        });
        audioStatus.getPlaylist().push(song);
      });
    }
  };

  audioPlayer.togglePlayPause = function () {
    if (audioStatus.isPlaying()) {
      audioPlayer.pause();
    } else {
      audioPlayer.play();
    }
  };

  audioPlayer.play = function (song) {
    var currentSong = audioStatus.getCurrentSong();
    if (song != null) {
      if (currentSong != null && song.id == currentSong.id) {
        audio.play();
        audioPlayer.setAudioPosition(0);
      } else {
        if (audio != null) {
          audioPlayer.stop();
        }
        playSong(song);
      }
    } else if (audio != null && currentSong != null) {
      audio.play();
      audioStatus.setPlaying(true);
    } else if (audioStatus.getPlaylist().length > 0) {
      playSong(audioStatus.getPlaylist()[0]);
    }
  };

  audioPlayer.stop = function () {
    if (audio != null) {
      audio.pause();
      audio.src = "";
    }
    audioStatus.progress = 0;
    audioStatus.duration = 0;
    audioStatus.currentSong = null;
    audioStatus.playing = false;
    audioStatus.loadingPercent = 0;
  };

  function playSong(song) {
    $q.when(song).then(function () {
      audioStatus.setCurrentSong(song);
      audioStatus.setPlaying(true);
      audioStatus.setProgress(0);
    });
    $cookies.put('X-AUTH-TOKEN', tokenStorage.retrieve());
    audio = new Audio('/soundlink_server/soundlink/musics/music/' + song.id);

    managePlayerEvent();
    audio.play();
  }

  audioPlayer.pause = function () {
    if (audio != null) {
      audio.pause();
      audioStatus.setPlaying(false);
    }
  };

  audioPlayer.repeat = function () {
    if (audioStatus.isRepeating()) {
      audioStatus.setRepeating(false);
      audioStatus.setRepeatingOne(true);
    } else if (audioStatus.isRepeatingOne()) {
      audioStatus.setRepeatingOne(false);
    } else {
      audioStatus.setRepeating(true);
    }
  };

  audioPlayer.random = function () {
    audioStatus.setRandom(!audioStatus.getRandom());
  };

  audioPlayer.mute = function () {
    audioStatus.setMuted(!audioStatus.isMuted());
    if (audio != null) {
      audio.muted = audioStatus.isMuted();
    }
  };

  audioPlayer.previous = function () {
    var currentSong = audioStatus.getCurrentSong();
    if (currentSong != null) {
      var playlist = audioStatus.getPlaylist();
      var nextSongIndex = playlist.indexOf(currentSong) - 1;
      if (nextSongIndex >= 0) {
        var song = playlist[nextSongIndex];
        audioPlayer.play(song);
      }
    }
  };

  audioPlayer.next = function () {
    var currentSong = audioStatus.getCurrentSong();
    if (currentSong != null) {
      var playlist = audioStatus.getPlaylist();
      var nextSongIndex = playlist.indexOf(currentSong) + 1;
      if (audioStatus.isRepeatingOne()) {
        audioPlayer.setAudioPosition(0);
        audio.play();
      } else if (playlist.length > nextSongIndex) {
        audioPlayer.play(playlist[nextSongIndex]);
      } else if (audioStatus.isRepeating()) {
        audioPlayer.play(playlist[0]);
      }
    }
  };

  function managePlayerEvent() {
    audio.volume = audioStatus.getVolume();
    audio.muted = audioStatus.isMuted();
    audio.oncanplay = function () {
      console.log("Ready to play ! ");
    };
    audio.onended = function () {
      audioStatus.setProgress(100);
      $rootScope.$apply();
      audioPlayer.next();
    };
    audio.onerror = function (err, code, toto) {
      console.log(err + ' ' + audioStatus.getCurrentSong().title);
    };
    audio.ontimeupdate = function () {
      audioStatus.setProgress((audio.currentTime / audioStatus.getDuration()) * 100);
      $rootScope.$apply();
    };
    audio.ondurationchange = function () {
      console.log("Duration change " + audio.duration);
      audioStatus.setDuration(audio.duration);
      $rootScope.$apply();
    };
    audio.onloadstart = function () {
      console.log("Load start !");
    };
    audio.onseeked = function () {
      console.log("Seeked finish");
    };
    audio.onprogress = function () {
      for (var bufferIndex = 0; bufferIndex < audio.buffered.length; bufferIndex++) {
        // console.log("###############################");
        // console.log("Buffer " + bufferIndex);
        // console.log("Start : " + audio.buffered.start(bufferIndex) + " end " + audio.buffered.end(bufferIndex));
        // console.log("###############################");
      }
      if (audio.buffered.length > 1) {
        // audioStatus.setLoadingPercent((audio.buffered.end(audio.buffered.length - 2) / audioStatus.getDuration()) * 100);
      }
      $rootScope.$apply();
    };
  }
}