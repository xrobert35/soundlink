angular.module("mod-login").provider('loginService', loginProvider);

function loginProvider() {

    this.successState = "default";

    this.$get = ['tokenStorage', '$http', '$resource', '$q',
        '$location', '$state', function (tokenStorage, $http, $resource, $q, $location, $state) {

            var providerArgs = this;

            var service = {
                login: login,
                logout: logout
            };

            var loginResources = $resource('/SoundLink_Server/security/login', {}, {
                post: { method: 'POST', cache: false }
            });

            var logoutResources = $resource('/SoundLink_Server/logout', {}, {
                post: { method: 'POST', headers: { 'Content-Type': 'application/x-www-form-urlencoded' }, cache: false }
            });

            return service;

            function login(username, password) {
                var deferred = $q.defer();
                var credentiels = { username: username, password: password };
                loginResources.post(credentiels, function (data, headers) {
                    tokenStorage.store(headers('X-AUTH-TOKEN'));
                    // console.log("redirect" + providerArgs.successState);
                    deferred.resolve("success");
                    $state.go(providerArgs.successState);
                }, function (data, status, headers, config) {
                    console.log("status " + status + " data " + data);
                    if (status === -1) {
                        deferred.reject("Communication problem : contact the administrator");
                    }else{
                        deferred.reject("Wrong login/password");
                    }
                });
                return deferred.promise;
            }

            function logout() {
                logoutResources.post('').$promise.then(function (response) {
                    console.log("logout success");

                    delete $cookies['JSESSIONID'];
                    console.info('The user has been logged out!');

                    $location.url('/login');

                }).catch(function (data, status, headers, config) {
                    console.log("logout error");
                });
            }
        }];
}
