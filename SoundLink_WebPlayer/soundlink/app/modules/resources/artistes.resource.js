'use strict';

angular.module("soundlink").service('artistesResource', artistesResource);

artistesResource.$inject = ['$http', 'config'];

function artistesResource($http, config) {

  var controllerUrl = config.serveurUrl + 'soundlink/artistes/';

  function getData(result) {
    return result.data;
  }

  this.getArtistesStartWith = function getArtistesStartWith(startChain) {
    return $http.get(controllerUrl + 'artistesStartWith', { params: { 'startChain': startChain } }).then(getData);
  };

  this.getUserArtistes = function () {
    return $http.get(controllerUrl + "userArtistes").then(getData);
  };
}