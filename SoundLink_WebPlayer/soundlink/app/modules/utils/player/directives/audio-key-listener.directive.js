'use strict';

angular.module("soundlink").directive('audioKeyListener', audioKeyListenerDirective);

audioKeyListenerDirective.$inject = ['audioPlayer', '$document'];

function audioKeyListenerDirective(audioPlayer, $document) {
  return {
    restrict: "A",
    link: function (scope, element, attrs) {
      element.bind('keypress', function (event) {
        if (event.keyCode == 32) {
          console.log("Key press" + event);
          event.preventDefault();
          audioPlayer.togglePlayPause();
        }
      });
    }
  };
}