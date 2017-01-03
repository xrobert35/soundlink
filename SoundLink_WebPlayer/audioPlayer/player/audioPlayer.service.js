'use strict';

var AV = require('av');
require('flac.js');

angular.module('soundlink').service("audioPlayer", audioPlayer);

audioPlayer.$inject = ['audioStatus', '$q'];

function audioPlayer(audioStatus, $q) {

  var audioPlayer = this;

  var player;
  var playlist = [];
  var currentSong;
  var duration;
  
  audioPlayer.getCurrentSong = function () {
    return currentSong;
  };

  audioPlayer.isPlaying = function () {
    return player != null && player.playing;
  };

  audioPlayer.getPlaylist = function () {
    return playlist;
  };

  audioPlayer.add = function (song) {
    playlist.push(song);
  };

  audioPlayer.play = function (song) {
    if (song != null && (currentSong == null || song.id != currentSong.id)) {
      if (player != null) {
        player.stop();
      }
      player = AV.Player.fromURL(song.url);
      currentSong = song;
      managePlayerEvent();
      player.play();
    } else if (player != null && currentSong != null) {
      player.play();
    } else if (playlist.length > 0) {
      currentSong = playlist[0];
      player = AV.Player.fromURL(currentSong.url);
      managePlayerEvent();
      player.play();
    }
  };

  audioPlayer.pause = function () {
    if (player != null) {
      player.pause();
    }
  };

  audioPlayer.continue = function () {
    if (player != null) {
      player.play();
    }
  };

  audioPlayer.repeat = function () {

  };

  audioPlayer.random = function () {

  };

  audioPlayer.previous = function () {

  };

  audioPlayer.next = function () {
    if (currentSong != null) {
      var nextSongIndex = playlist.indexOf(currentSong) + 1;
      if (playlist.length > nextSongIndex) {
        var song = playlist[playlist.indexOf(currentSong) + 1];
        audioPlayer.play(song);
      }
    }
  };

  function managePlayerEvent() {
    player.on('ready', function () {
      // fireEvent('ready');
    });
    player.on('end', function () {
      // fireEvent('end');
      audioPlayer.next();
    });
    player.on('progress', function (progress) {
      $q.when(progress).then(function(){
        audioStatus.setProgress((progress/duration) * 100);
      });
    });
    player.on('duration', function (pDuration) {
      duration = pDuration;
    });
    player.on('buffer', function (percent) {
      // fireEvent('buffer', percent);
    });
  }
}