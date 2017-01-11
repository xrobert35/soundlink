'use strict';

var AV = require('av');

angular.module('soundlink').service("audioPlayer", audioPlayer);

audioPlayer.$inject = ['audioStatus', '$q'];

function audioPlayer(audioStatus, $q) {

  var audioPlayer = this;
  var player;

  audioPlayer.setVolume = function (volume) {
    audioStatus.setVolume(volume);
    if (player != null) {
      player.volume = volume;
    }
  };

  audioPlayer.add = function (song) {
    if (audioStatus.getPlaylist().indexOf(song) == -1) {
      audioStatus.getPlaylist().push(song);
    }
  };

  audioPlayer.play = function (song) {
    var currentSong = audioStatus.getCurrentSong();
    if (song != null && (currentSong == null || song.id != currentSong.id)) {
      if (player != null) {
        player.stop();
      }
      playSong(song);
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
    });
    player = AV.Player.fromURL(song.url);
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

  };

  audioPlayer.random = function () {

  };

  audioPlayer.previous = function () {

  };

  audioPlayer.next = function () {
    var currentSong = audioStatus.getCurrentSong();
    var playlist = audioStatus.getPlaylist();
    if (currentSong != null) {
      var nextSongIndex = playlist.indexOf(currentSong) + 1;
      if (playlist.length > nextSongIndex) {
        var song = playlist[nextSongIndex];
        audioPlayer.play(song);
      }
    }
  };

  function managePlayerEvent() {
    player.volume = audioStatus.getVolume();
    player.on('ready', function () {

    });

    player.on('end', function () {
      audioPlayer.next();
    });

    player.on('error', function (err) {
      console.log(err);
    });

    player.on('progress', function (progress) {
      $q.when(progress).then(function () {
        audioStatus.setProgress((progress / audioStatus.getDuration()) * 100);
      });
    });
    player.on('duration', function (pDuration) {
      $q.when(pDuration).then(function () {
        audioStatus.setDuration(pDuration);
      });
    });

    player.on('buffer', function (percent) {
      $q.when(percent).then(function () {
        audioStatus.setLoadingPercent(percent);
      });
    });
  }
}