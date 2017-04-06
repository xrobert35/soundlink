'use strict';

angular.module("soundlink").directive('nextMusic', nextMusicDirective);

nextMusicDirective.$inject = ['audioPlayer'];

function nextMusicDirective(audioPlayer) {
  return {
    restrict: "A",
    link: function (scope, element, attrs) {
      element.bind('click', function (event) {
        audioPlayer.next(scope.song);
      });
    }
  };
}