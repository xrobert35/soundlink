'use strict';

angular.module("soundlink").directive('positionControle', nextMusicDirective);

nextMusicDirective.$inject = ['audioPlayer'];

function nextMusicDirective(audioPlayer) {
  return {
    restrict: "A",
    link: function (scope, element, attrs) {

      function getXOffset(event) {
        var x = 0, element = event.target;
        while (element && !isNaN(element.offsetLeft) && !isNaN(element.offsetTop)) {
          x += element.offsetLeft - element.scrollLeft;
          element = element.offsetParent;
        }
        return event.clientX - x;
      }

      element.bind('click', function (event) {
          var mouseX =  getXOffset(event);
          var clientWidth = event.target.clientWidth;
          var position = (mouseX / clientWidth) * 100;
          audioPlayer.setAudioPosition(position);
      });
    }
  };
}