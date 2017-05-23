'use strict';

angular.module("soundlink").service('techResource', techResource);

techResource.$inject = ['$http', 'config'];

function techResource($http, config) {

  var controllerUrl = config.serveurUrl + 'soundlink/tech/';

  this.getWsToken = function () {
    return $http.get(controllerUrl + 'wsToken').then(getData);
  };

  function getData(result) {
    return result.data;
  }
}
