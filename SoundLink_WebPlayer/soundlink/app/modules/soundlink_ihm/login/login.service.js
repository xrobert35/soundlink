'use strict';

angular.module("soundlink").service('loginService', loginService);

loginService.$inject = ['$state', 'loginResource', '$cookies', 'tokenStorage'];

function loginService($state, loginResource, $cookies, tokenStorage) {

    var service = this;
    
    var JSESSIONID = 'JSESSIONID';

    service.login = function (username, password) {
        var credentiels = { username: username, password: password };
        return loginResource.login(credentiels).then(function (data) {
            tokenStorage.store(data.headers('X-AUTH-TOKEN'));
        });
    };

    service.logout = function logout() {
        //TODO STOPER LA LECTURE
        return loginResource.logout().then(function (response) {
            console.debug("logout success");
            delete $cookies[JSESSIONID];
            tokenStorage.clear();
            console.debug('The user has been logged out!');
        }).catch(function () {
            console.log("logout error");
        });
    };
}
