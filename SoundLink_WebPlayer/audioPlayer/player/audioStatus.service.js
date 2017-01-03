'use strict';

angular.module('soundlink').factory("audioStatus", audioStatus);

audioStatus.$inject = [];

function audioStatus() {

  var progress = 0;
  var duration = 0;

  var contenu = {};

  contenu.setProgress = function(pProgress){
    progress = pProgress;
  };

  contenu.getProgress = function(){
    return progress;
  };

  return contenu;

}