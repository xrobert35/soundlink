'use strict';

angular.module("soundlink").directive('addPlayMusics', addPlayMusicsDirective);

addPlayMusicsDirective.$inject = ['audioPlayer'];

function addPlayMusicsDirective(audioPlayer) {
  return {
    restrict: "EA",
    scope: {
      songs: '=addPlayMusics'
    },
    link: function (scope, element, attrs) {
      element.bind('click', function (event) {
        var firstSong = scope.songs[0];
        angular.forEach(scope.songs , function (song){
          audioPlayer.add(song);
        });
        audioPlayer.play(firstSong);
      });
    }
  };
}