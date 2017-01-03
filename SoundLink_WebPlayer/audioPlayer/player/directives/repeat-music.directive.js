'use strict';

angular.module("soundlink").directive('repeatMusic', repeatMusicDirective);

repeatMusicDirective.$inject = ['audioPlayer'];

function repeatMusicDirective(audioPlayer) {
  return {
    restrict: "A",
    link: function (scope, element, attrs) {
      element.bind('click', function (event) {
        audioPlayer.repeat();
      });
    }
  };
}