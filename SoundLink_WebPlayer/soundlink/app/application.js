
//Module declaration
angular.module("soundlink", ['ngSanitize',
    'ui.router', 'ngAria', 'ngAnimate', 'ngMaterial', 'ngCookies',
    'pascalprecht.translate', 'ngResource', 'ngMessages', 'angularMoment', 'ngWebSocket', 'ngFileUpload', 'angular-bind-html-compile']);

angular.module("soundlink").constant('lodash', window._);

angular.module("soundlink").run(main);

main.$inject = ['$state', '$rootScope', 'lodash'];

function main($state, $rootScope, lodash) {

    var startResolve = false;
    var startError = false;
    var statePrevent;
    var statePreventParams;

    $rootScope.lodash = lodash;


    $rootScope.$on('$stateChangeStart', function (event, toState, toParams, fromState, fromParams) {
        if (toState.name != 'start' && !startResolve) {
            statePrevent = toState;
            statePreventParams = toParams;
            event.preventDefault();
        } else if (startError && toState.name != 'login') {
            event.preventDefault();
            $state.go("login");
        }
    });

    $rootScope.$on('$stateChangeSuccess', function (event, toState, toParams, fromState, fromParams) {
        if (toState.name == 'start') {
            startResolve = true;
            startError = false;
            if (statePrevent != null && statePrevent.name != "login") {
                $state.go(statePrevent, statePreventParams);
            } else {
                $state.go("soundlink.search");
            }
        } else if (toState.name == 'login') {
            startResolve = true;
            startError = false;
        }
    });

    $rootScope.$on('$stateChangeError', function (event, toState, toParams, fromState, fromParams, error) {
        if (toState.name == 'start') {
            startResolve = true;
            startError = true;
            event.preventDefault();
            $state.go("login");
        }
    });
    $state.go("start");
}