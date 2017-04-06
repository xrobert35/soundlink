'use strict';

angular.module('soundlink').service("audioPlayer", audioPlayer);

audioPlayer.$inject = ['audioStatus', '$q', '$cookies', 'tokenStorage', 'config', '$timeout', "$rootScope"];

function audioPlayer(audioStatus, $q, $cookies, tokenStorage, config, $timeout, $rootScope) {

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
    if (audioStatus.getPlaylist().indexOf(song) == -1) {
      audioStatus.getPlaylist().push(song);
      $rootScope.$apply();
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
          stopPlaying();
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

  function stopPlaying(){
    audio.pause();
    audio.src = "";
  }

  function playSong(song) {
    $q.when(song).then(function () {
      audioStatus.setCurrentSong(song);
      audioStatus.setPlaying(true);
      audioStatus.setProgress(0);
    });
    // player = AV.Player.fromURL(song.url);
    $cookies.put('X-AUTH-TOKEN', tokenStorage.retrieve(), { path: '/soundlink_server', domain: config.wsDomain });
    audio = new Audio('/soundlink_server/soundlink/music/' + song.id);

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
    audioStatus.setRepeating(!audioStatus.isRepeating());
  };

  audioPlayer.random = function () {

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
      if (playlist.length > nextSongIndex) {
        audioPlayer.play(playlist[nextSongIndex]);
      } else if (audioStatus.isRepeating()) {
        audioPlayer.play(playlist[0]);
      }
    }
  };

  function managePlayerEvent() {
    audio.volume = audioStatus.getVolume();
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
    audio.ondurationchange  = function () {
      console.log("Duration change " + audio.duration);
      audioStatus.setDuration(audio.duration);
      $rootScope.$apply();
    };
    audio.onloadstart = function (){
      console.log("Load start !");
    };
    audio.onseeked  = function (){
      console.log("Seeked finish");
    };
    audio.onprogress  = function (){
      for(var bufferIndex = 0; bufferIndex < audio.buffered.length; bufferIndex++){
        console.log("###############################");
        console.log("Buffer " + bufferIndex);
        console.log("Start : " + audio.buffered.start(bufferIndex) + " end " + audio.buffered.end(bufferIndex));
        console.log("###############################");
      }
      if(audio.buffered.length > 1 ){
        audioStatus.setLoadingPercent((audio.buffered.end(audio.buffered.length-2) / audioStatus.getDuration()) * 100);
      }
      $rootScope.$apply();
    };
  }
}