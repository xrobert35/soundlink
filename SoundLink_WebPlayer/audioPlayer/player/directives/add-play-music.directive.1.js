'use strict';

angular.module("soundlink").directive('addPlayMusic', addPlayMusicDirective);

addPlayMusicDirective.$inject = ['audioPlayer'];

function addPlayMusicDirective(audioPlayer) {
  return {
    restrict: "EA",
    scope: {
      song: '=addPlayMusic'
    },
    link: function (scope, element, attrs) {
      element.bind('click', function (event) {
          audioPlayer.add(scope.song);
          audioPlayer.play(scope.song);
      });
    }
  };
}