'use strict';

angular.module('soundlink').service("audioPlayer", audioPlayer);

audioPlayer.$inject = ['audioStatus', '$q', '$cookies', 'tokenStorage', 'config', '$timeout', "$rootScope"];

function audioPlayer(audioStatus, $q, $cookies, tokenStorage, config, $timeout, $rootScope) {

  var audioPlayer = this;
  var player;

  audioPlayer.setVolume = function (volume) {
    audioStatus.setVolume(volume);
    if (player != null) {
      player.volume = volume;
    }
  };

  audioPlayer.setAudioPosition = function (positionInPercent) {
    var time = (audioStatus.getDuration() * positionInPercent) / 100;
    if (player != null) {
      player.seek(time);
    }
  };

  audioPlayer.add = function (song) {
    if (audioStatus.getPlaylist().indexOf(song) == -1) {
      audioStatus.getPlaylist().push(song);
    }
  };

  audioPlayer.play = function (song) {
    var currentSong = audioStatus.getCurrentSong();
    if (song != null) {
      if(currentSong != null && song.id == currentSong.id){
        player.play();
        audioPlayer.setAudioPosition(0);
      }else {
        if (player != null) {
          player.stop();
        }
        playSong(song);
      }
    } else if (player != null && currentSong != null) {
      player.play();
      audioStatus.setPlaying(true);
    } else if (audioStatus.getPlaylist().length > 0) {
      playSong(audioStatus.getPlaylist()[0]);
    }
  };

  function playSong(song) {
    $q.when(song).then(function () {
      audioStatus.setCurrentSong(song);
      audioStatus.setPlaying(true);
      audioStatus.setProgress(0);
    });
    // player = AV.Player.fromURL(song.url);
    $cookies.put('X-AUTH-TOKEN', tokenStorage.retrieve(), { path: '/soundlink_server', domain: config.wsDomain });
    player = AV.Player.fromWebSocket(config.wsServeurUrl + "ws/music", song.id);

    managePlayerEvent();
    player.play();
  }

  audioPlayer.pause = function () {
    if (player != null) {
      player.pause();
      audioStatus.setPlaying(false);
    }
  };

  audioPlayer.continue = function () {
    if (player != null) {
      player.play();
      audioStatus.setPlaying(true);
    }
  };

  audioPlayer.repeat = function () {
    audioStatus.setRepeating(!audioStatus.isRepeating());
  };

  audioPlayer.random = function () {

  };

  audioPlayer.previous = function () {
    var currentSong = audioStatus.getCurrentSong();
    if (currentSong != null) {
      var playlist = audioStatus.getPlaylist();
      var nextSongIndex = playlist.indexOf(currentSong) -1;
      if (nextSongIndex >= 0 ) {
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
      if (playlist.length > nextSongIndex) {
        audioPlayer.play(playlist[nextSongIndex]);
      }else if(audioStatus.isRepeating()){
        audioPlayer.play(playlist[0]);
      }
    }
  };

  function managePlayerEvent() {
    player.volume = audioStatus.getVolume();
    player.on('ready', function () {
      player.play();
    });

    player.on('end', function () {
      audioStatus.setProgress(100);
      $rootScope.$apply();
      audioPlayer.next();
    });
    player.on('error', function (err, code, toto) {
      if (err.message === 'Invalid sync code') {
        //probleme de desynchro à la fin de certain fichier flac
        player.stop();
        audioStatus.setProgress(100);
        $rootScope.$apply();
        audioPlayer.next();
      }
      console.log(err + ' ' + audioStatus.getCurrentSong().title);
    });
    player.on('progress', function (progress) {
      audioStatus.setProgress((progress / audioStatus.getDuration()) * 100);
      $rootScope.$apply();
    });
    player.on('duration', function (pDuration) {
      audioStatus.setDuration(pDuration);
      $rootScope.$apply();
    });
    player.on('buffer', function (percent) {
      audioStatus.setLoadingPercent(percent);
      $rootScope.$apply();
    });
  }
}