'use strict';

angular.module("soundlink").service('soundlinkadminResource', soundlinkadminResource);

soundlinkadminResource.$inject = ['$http', 'config'];

function soundlinkadminResource($http, config) {

  var controllerUrl = config.serveurUrl + 'admin/';

  function getData(result) {
    return result.data;
  }

  this.loadMusics = function () {
    return $http.post(controllerUrl + 'loadMusics').then(getData);
  };
}
