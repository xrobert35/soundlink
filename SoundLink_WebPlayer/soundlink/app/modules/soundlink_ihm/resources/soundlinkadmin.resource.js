angular.module("soundlink").service('soundlinkadminResource', soundlinkadminResource);

soundlinkadminResource.$inject = ['$http', 'config'];

function soundlinkadminResource($http, config) {

  var resource = this;

  resource.loadMusics = function () {
    return $http.post(config.serveurUrl + 'admin/loadMusics');
  };
}
