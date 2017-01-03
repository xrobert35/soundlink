'use strict';

angular.module('soundlink').component('albumDetail', {
  templateUrl: 'app/modules/soundlink_ihm/pages/albums/album/albumDetail.html',
  controller: albumDetailController,
  bindings: {
    musics: '<',
    album: '<'
  }
});

albumDetailController.$inject = ['socketService', 'soundlinkResource', 'eventManager', 'audioPlayer'];

function albumDetailController(socketService, soundlinkResource, eventManager, audioPlayer) {
  var vm = this;

  vm.play = function (song) {
    audioPlayer.playFromUrl(song);
  };
}
