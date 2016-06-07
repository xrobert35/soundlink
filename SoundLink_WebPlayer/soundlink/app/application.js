
//Module declaration
angular.module("soundlink", ['ngSanitize', 'ui.router', 'ngCookies', 'socketmanager', 'soundlink-data', 'pascalprecht.translate', 'angularSoundManager', 'ngResource', 'mod-login']);

//Route configuration
angular.module("soundlink").config(function ($stateProvider, $urlRouterProvider) {
    //La page par defaut est la page /login
    $urlRouterProvider.otherwise('/login');
});

// Web socket with stomp configuration
angular.module("soundlink").config(function (socketServiceProvider) {
    var serverUrl = "/SoundLink_Server";
    socketServiceProvider.serverUrl = serverUrl;
});

// translate provider configuration
angular.module("soundlink").config(function ($translateProvider) {
    $translateProvider.useUrlLoader("/SoundLink_Server/soundlinkMessage/default");
    $translateProvider.useStorage('UrlLanguageStorage');
    $translateProvider.preferredLanguage('fr');

});

// http configuration
angular.module('soundlink').config(function($httpProvider) {
    $httpProvider.defaults.timeout = 500;
});

// translate provider configuration
angular.module("soundlink").config(function (loginServiceProvider) {
    loginServiceProvider.successState = 'soundlink.albums';
});

angular.module("soundlink").run(function ($rootScope) {
    $rootScope.$on('$stateChangeSuccess', function (event, toState, toParams, fromState, fromParams, options) {
        $rootScope.currentStateUrl = toState.name;
    });
});

angular.module("soundlink").directive('includeReplace', function () {
    return {
        require: 'ngInclude',
        restrict: 'A', /* optional */
        link: function (scope, el, attrs) {
            el.replaceWith(el.children());
        }
    };
});

angular.module('soundlink').directive('screenHeight', function ($window) {
    return function (scope, element) {
        var w = angular.element($window);
        scope.getWindowDimensions = function () {
            return { 'h': $window.innerHeight, 'w': $window.innerWidth };
        };
        scope.$watch(scope.getWindowDimensions, function (newValue, oldValue) {
            scope.windowHeight = newValue.h;
            scope.windowWidth = newValue.w;

            scope.style = function () {
                return {
                    'height': (newValue.h - 10) + 'px',
                    'max-height': (newValue.h - 20) + 'px',
                };
            };
        }, true);

        w.bind('resize', function () {
            scope.$apply();
        });
    };
});