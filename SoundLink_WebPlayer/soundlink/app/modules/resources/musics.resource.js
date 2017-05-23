'use strict';

angular.module("soundlink").service('musicsResource', musicsResource);

musicsResource.$inject = ['$http', 'config'];

function musicsResource($http, config) {

  var controllerUrl = config.serveurUrl + 'soundlink/musics/';

  function getData(result) {
    return result.data;
  }

  this.getMusicsFromAlbum = function (albumId) {
    return $http.get(controllerUrl + 'fromAlbum', { params: { 'albumId': albumId } }).then(getData);
  };

  this.getMusicsFromPlaylist = function (playlistId) {
    return $http.get(controllerUrl + 'fromPlaylist', { params: { 'playlistId': playlistId } }).then(getData);
  };
}


