'use strict';

angular.module("soundlink").directive('pauseMusic', pauseMusicDirective);

pauseMusicDirective.$inject = ['audioPlayer'];

function pauseMusicDirective(audioPlayer) {
    return {
        restrict: "A",
        link: function (scope, element, attrs) {
            element.bind('click', function (event) {
                audioPlayer.pause();
            });
        }
    };
}