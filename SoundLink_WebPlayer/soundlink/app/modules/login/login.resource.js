angular.module("mod-login").service('loginResource', loginResource);

loginResource.$inject = ['$http', 'config'];

function loginResource($http, config) {

  var service = this;

  service.login = function (credential) {
    return $http.post(config.serveurUrl + 'security/login', credential);
  };

  service.logout = function (){
    return $http.post(config.serveurUrl + 'security/logout');
  };
}
