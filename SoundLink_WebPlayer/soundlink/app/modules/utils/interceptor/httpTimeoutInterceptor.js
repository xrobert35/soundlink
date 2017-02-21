angular.module('soundlink').factory('timeoutHttpIntercept', function () {
        return {
            'request': function (config) {
                 config.timeout = -1;
                return config;
            }
        };
    });