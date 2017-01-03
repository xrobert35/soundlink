
//Module declaration
angular.module("soundlink", ['ngSanitize',
    'ui.router', 'ngAria', 'ngAnimate', 'ngMaterial', 'md.data.table', 'ngCookies',
    'pascalprecht.translate', 'ngResource', 'angularMoment']);


// angular.module("soundlink").run(function ($rootScope) {
//     $rootScope.$on('$stateChangeSuccess', function (event, toState, toParams, fromState, fromParams, options) {
//         $rootScope.currentStateUrl = toState.name;
//     });
// });


angular.module("soundlink").run(main);

main.$inject = ['$state', '$rootScope'];

function main($state, $rootScope) {

    // $state.preventDefault();
    var startResolve = false;
    var statePrevent;
    var statePreventParams;

    $rootScope.$on('$stateChangeStart', function (event, toState, toParams, fromState, fromParams) {
        if (toState.name != 'start' && !startResolve) {
            statePrevent = toState;
            statePreventParams = toParams;
            event.preventDefault();
        }
    });

    $rootScope.$on('$stateChangeSuccess', function (event, toState, toParams, fromState, fromParams) {
        if (toState.name == 'start') {
            startResolve = true;
            $state.go(statePrevent,statePreventParams);
        }
    });

    $rootScope.$on('$stateChangeError', function (event, toState, toParams, fromState, fromParams, error) {
        if (toState.name == 'start') {
            startResolve = true;
            $state.go("login");
        }
    });

    $state.go("start");
}

(function () {
        var root = angular.element(document.getElementsByTagName('html'));

        var watchers = [];

        var f = function (element) {
            angular.forEach(['$scope', '$isolateScope'], function (scopeProperty) {
                if (element.data() && element.data().hasOwnProperty(scopeProperty)) {
                    angular.forEach(element.data()[scopeProperty].$$watchers, function (watcher) {
                        watchers.push(watcher);
                    });
                }
            });

            angular.forEach(element.children(), function (childElement) {
                f(angular.element(childElement));
            });
        };

        f(root);

        // Remove duplicate watchers
        var watchersWithoutDuplicates = [];
        angular.forEach(watchers, function (item) {
            if (watchersWithoutDuplicates.indexOf(item) < 0) {
                watchersWithoutDuplicates.push(item);
            }
        });

        console.log(watchersWithoutDuplicates.length);
    })();