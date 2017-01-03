angular.module('soundlink').factory('timeoutHttpIntercept', function () {
        return {
            'request': function (config) {
                // config.timeout = 5000;
                return config;
            }
        };
    });