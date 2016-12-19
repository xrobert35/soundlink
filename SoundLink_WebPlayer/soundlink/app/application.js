
//Module declaration
angular.module("soundlink", ['ngSanitize', 
'ui.router', 'ngAria', 'ngAnimate', 'ngMaterial', 'ngCookies', 
'socketmanager', 'soundlink-data', 'pascalprecht.translate', 
'angularSoundManager', 'ngResource', 'mod-login']);

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
    $translateProvider.useUrlLoader("/soundlink_server/message/default");
    $translateProvider.useStorage('UrlLanguageStorage');
    $translateProvider.preferredLanguage('fr');
});

// http configuration
angular.module('soundlink').config(function($httpProvider) {
    $httpProvider.defaults.timeout = 60000;
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

angular.module("soundlink").constant("config", {
    serveurUrl : "/soundlink_server/"
});