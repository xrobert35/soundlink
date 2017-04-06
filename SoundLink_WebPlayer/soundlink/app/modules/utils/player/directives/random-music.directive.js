'use strict';

angular.module("soundlink").directive('randomMusic', randomMusicDirective);

randomMusicDirective.$inject = ['audioPlayer'];

function randomMusicDirective(audioPlayer) {
  return {
    restrict: "A",
    link: function (scope, element, attrs) {
      element.bind('click', function (event) {
        audioPlayer.random();
      });
    }
  };
}