'use strict';

angular.module("soundlink").directive('removeMusic', removeMusicDirective);

removeMusicDirective.$inject = ['audioPlayer'];

function removeMusicDirective(audioPlayer) {
  return {
    restrict: "A",
    scope: {
      song: '=removeMusic'
    },
    link: function (scope, element, attrs) {
      element.bind('click', function (event) {
        audioPlayer.remove(scope.song);
      });
    }
  };
}