'use strict';

angular.module("soundlink").directive('previousMusic', previousMusicDirective);

previousMusicDirective.$inject = ['audioPlayer'];

function previousMusicDirective(audioPlayer) {
    return {
        restrict: "A",
        link: function (scope, element, attrs) {
            element.bind('click', function (event) {
                audioPlayer.previous();
            });
        }
    };
}
