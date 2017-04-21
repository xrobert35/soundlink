'use strict';

angular.module("soundlink").directive('muteMusic', muteMusicDirective);

muteMusicDirective.$inject = ['audioPlayer'];

function muteMusicDirective(audioPlayer) {
  return {
    restrict: "A",
    link: function (scope, element, attrs) {
      element.bind('click', function (event) {
        audioPlayer.mute();
      });
    }
  };
}