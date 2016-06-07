angular.module("soundlink-data").factory('csrfRequestInterceptor', ['$q', 'tokenStorage', csrfRequestInterceptor]);

function csrfRequestInterceptor($q, tokenStorage) {
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
            }
            return $q.reject(error);
        }
    }
}