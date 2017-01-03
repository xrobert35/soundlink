'use strict';

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