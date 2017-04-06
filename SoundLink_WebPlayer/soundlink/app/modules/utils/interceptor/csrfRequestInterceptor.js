angular.module("soundlink").factory('csrfRequestInterceptor', csrfRequestInterceptor);

csrfRequestInterceptor.$inject = ['$q', 'tokenStorage', '$location'];

function csrfRequestInterceptor($q, tokenStorage, $location) {
    return {
        request: function (config) {
            var authToken = tokenStorage.retrieve();
            if (authToken) {
                config.headers['X-AUTH-TOKEN'] = authToken;
            }
            return config;
        },
        responseError: function (error) {
            if (error.status === 401 || error.status === 403) {
                tokenStorage.clear();
                $location.path("/");

            }
            return $q.reject(error);
        }
    };
}