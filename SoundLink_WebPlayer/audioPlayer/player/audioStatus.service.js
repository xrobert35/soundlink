'use strict';

angular.module('soundlink').factory("audioStatus", audioStatus);

audioStatus.$inject = [];

function audioStatus() {

  var progress = 0;
  var duration = 0;
  var volume = 0;
  var currentSong;
  var isPlaying = false;
  var loadingPercent = 0;
  var playlist = [];

  var contenu = {};

  contenu.isPlaying = function () {
    return isPlaying;
  };

  contenu.setPlaying = function (bPlaying) {
    isPlaying = bPlaying;
  };

  contenu.setVolume = function (pVolume){
    volume = pVolume;
  };

  contenu.getVolume = function (){
    return volume;
  };

  contenu.setDuration = function (pDuration) {
    duration = pDuration;
  };

  contenu.getDuration = function () {
    return duration;
  };

  contenu.setProgress = function (pProgress) {
    progress = pProgress;
  };

  contenu.getProgress = function () {
    return progress;
  };

  contenu.setCurrentSong = function (pCurrentSong) {
    currentSong = pCurrentSong;
  };

  contenu.getCurrentSong = function () {
    return currentSong;
  };

  contenu.setPlaylist = function (pPlaylist) {
    playlist = pPlaylist;
  };

  contenu.getPlaylist = function () {
    return playlist;
  };

  contenu.setLoadingPercent = function (percent) {
    loadingPercent = percent;
  };

  contenu.getLoadingPercent = function () {
    return loadingPercent;
  };

  return contenu;

}