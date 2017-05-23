'use strict';

angular.module("soundlink").service('loginResource', loginResource);

loginResource.$inject = ['$http', 'config'];

function loginResource($http, config) {

  this.login = function (credential) {
    return $http.post(config.serveurUrl + 'security/login', credential);
  };

  this.logout = function (){
    return $http.post(config.serveurUrl + 'security/logout');
  };
}
