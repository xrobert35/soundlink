'use strict';

angular.module("soundlink").directive('addMusics', addMusicsDirective);

addMusicsDirective.$inject = ['audioPlayer'];

function addMusicsDirective(audioPlayer) {
  return {
    restrict: "EA",
    scope: {
      songs: '=addMusics'
    },
    link: function (scope, element, attrs) {
      element.bind('click', function (event) {
        angular.forEach(scope.songs , function (song){
          audioPlayer.add(song);
        });
      });
    }
  };
}