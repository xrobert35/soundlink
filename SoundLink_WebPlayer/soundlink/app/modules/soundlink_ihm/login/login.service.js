'use strict';

angular.module("soundlink").service('loginService', loginService);

loginService.$inject = ['$state', 'loginResource', '$cookies', 'tokenStorage', 'audioPlayer'];

function loginService($state, loginResource, $cookies, tokenStorage, audioPlayer) {

    var service = this;

    var JSESSIONID = 'JSESSIONID';
    var XAUTHTOKEN = 'X-AUTH-TOKEN';

    service.login = function (username, password) {
        var credentiels = { username: username, password: password };
        return loginResource.login(credentiels).then(function (data) {
            tokenStorage.store(data.headers(XAUTHTOKEN));
        });
    };

    service.logout = function logout() {
        return loginResource.logout().then(function (response) {
            delete $cookies[JSESSIONID];
            delete $cookies[XAUTHTOKEN];
            tokenStorage.clear();
            audioPlayer.stop();
        }).catch(function () {
            console.log("logout error");
            delete $cookies[JSESSIONID];
            delete $cookies[XAUTHTOKEN];
            tokenStorage.clear();
            audioPlayer.stop();
        });
    };
}
