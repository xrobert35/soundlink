'use strict';

angular.module("soundlink").directive('addMusic', addMusicDirective);

addMusicDirective.$inject = ['audioPlayer', 'eventManager'];

function addMusicDirective(audioPlayer, eventManager) {
  return {
    restrict: "EA",
    scope: {
      song: '=addMusic'
    },
    link: function (scope, element, attrs) {
      element.bind('click', function (event) {
        audioPlayer.add(scope.song);
        eventManager.fireEvent("playlistOpen");
      });
    }
  };
}