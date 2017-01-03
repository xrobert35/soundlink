'use strict';

angular.module("soundlink").directive('addMusic', addMusicDirective);

addMusicDirective.$inject = ['audioPlayer'];

function addMusicDirective(audioPlayer) {
  return {
    restrict: "A",
    scope: {
      song: '=addMusic'
    },
    link: function (scope, element, attrs) {
      element.bind('click', function (event) {
        audioPlayer.add(scope.song);
      });
    }
  };
}