'use strict';

angular.module("soundlink").service('albumsResource', albumsResource);

albumsResource.$inject = ['$http', 'config'];

function albumsResource($http, config) {

  var controllerUrl = config.serveurUrl + 'soundlink/albums/';

  function getData(result) {
    return result.data;
  }

  this.getAlbumsFromArtiste = function (artisteId) {
    return $http.get(controllerUrl + "fromArtiste", { params: { 'artisteId': artisteId } }).then(getData);
  };

  this.getUserAlbums = function(){
    return $http.get(controllerUrl + "userAlbums").then(getData);
  };
}