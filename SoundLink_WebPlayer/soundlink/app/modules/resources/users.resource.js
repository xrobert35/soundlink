'use strict';

angular.module("soundlink").service('usersResource', usersResource);

usersResource.$inject = ['$http', 'config'];

function usersResource($http, config) {

  var controllerUrl = config.serveurUrl + 'soundlink/users/';

  this.getUserInformation = function () {
    return $http.get(controllerUrl + 'userInformation').then(getData);
  };

  this.saveUserInformation = function (userInformation) {
    return $http.post(controllerUrl + 'saveUserInformation', userInformation).then(getData);
  };

  this.addFavoriteAlbum = function (albumId) {
    return $http.post(controllerUrl + 'addFavoriteAlbum', { param: { albumId: albumId } }).then(getData);
  };

  this.removeFavoriteAlbum = function (albumId) {
    return $http.post(controllerUrl + 'removeFavoriteAlbum', { param: { albumId: albumId } }).then(getData);
  };

  this.addFavoriteArtiste = function (artisteId) {
    return $http.post(controllerUrl + 'addFavoriteArtiste', null, { params: { artisteId: artisteId } }).then(getData);
  };

  this.removeFavoriteArtiste = function (artisteId) {
    return $http.post(controllerUrl + 'removeFavoriteArtiste', null, { params: { artisteId: artisteId } }).then(getData);
  };

  function getData(result) {
    return result.data;
  }
}
