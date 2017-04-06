'use strict';

angular.module("soundlink").directive('playMusic', playMusicDirective);

playMusicDirective.$inject = ['audioPlayer'];

function playMusicDirective(audioPlayer) {
  return {
    restrict: "EA",
    scope: {
      song: '=playMusic'
    },
    link: function (scope, element, attrs) {
      element.bind('click', function (event) {
        if (scope.song !== null) {
          audioPlayer.play(scope.song);
        }else{
          audioPlayer.play();
        }
      });
    }
  };
}