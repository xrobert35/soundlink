angular.module('soundlink').factory('timeoutHttpIntercept', function ($rootScope, $q) {
        return {
            'request': function (config) {
                config.timeout = 5000;
                return config;
            }
        };
    });