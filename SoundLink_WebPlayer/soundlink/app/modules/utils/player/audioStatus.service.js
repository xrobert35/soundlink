'use strict';

angular.module('soundlink').factory("audioStatus", audioStatus);

audioStatus.$inject = [];

function audioStatus() {

  var status = {
    progress: 0,
    duration: 0,
    volume: 0,
    currentSong: null,
    playing: false,
    muted: false,
    random: false,
    repeating: false,
    repeatingOne: false,
    loadingPercent: 0,
    playlist: [],
    randomPlaylist: []
  };

  var contenu = {};

  contenu.isPlaying = function () {
    return status.playing;
  };

  contenu.setPlaying = function (pPlaying) {
    status.playing = pPlaying;
  };

  contenu.isRepeating = function () {
    return status.repeating;
  };

  contenu.setRepeating = function (pRepeating) {
    status.repeating = pRepeating;
  };

  contenu.isRepeatingOne = function () {
    return status.repeatingOne;
  };

  contenu.setRepeatingOne = function (pRepeatingOne) {
    status.repeatingOne = pRepeatingOne;
  };

  contenu.setVolume = function (pVolume) {
    status.volume = pVolume;
  };

  contenu.getVolume = function () {
    return status.volume;
  };

  contenu.setMuted = function (pMuted) {
    status.muted = pMuted;
  };

  contenu.isMuted = function () {
    return status.muted;
  };

  contenu.setRandom = function (pRandom) {
    status.random = pRandom;
  };

  contenu.getRandom = function () {
    return status.random;
  };

  contenu.setDuration = function (pDuration) {
    status.duration = pDuration;
  };

  contenu.getDuration = function () {
    return status.duration;
  };

  contenu.setProgress = function (pProgress) {
    status.progress = pProgress;
  };

  contenu.getProgress = function () {
    return status.progress;
  };

  contenu.setCurrentSong = function (pCurrentSong) {
    status.currentSong = pCurrentSong;
  };

  contenu.getCurrentSong = function () {
    return status.currentSong;
  };

  contenu.setPlaylist = function (pPlaylist) {
    status.playlist = pPlaylist;
  };

  contenu.getPlaylist = function () {
    return status.playlist;
  };

  contenu.setRandomPlaylist = function (pRandomPlaylist) {
    status.randomPlaylis = pRandomPlaylist;
  };

  contenu.getRandomPlaylist = function () {
    return status.randomPlaylis;
  };

  contenu.setLoadingPercent = function (percent) {
    status.loadingPercent = percent;
  };

  contenu.getLoadingPercent = function () {
    return status.loadingPercent;
  };

  contenu.getStatus = function () {
    return status;
  };

  contenu.setStatus = function (pStatus) {
    status = pStatus;
  };

  return contenu;

}