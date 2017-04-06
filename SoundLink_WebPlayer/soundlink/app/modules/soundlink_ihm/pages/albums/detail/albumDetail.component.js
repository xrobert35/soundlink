'use strict';

angular.module('soundlink').component('albumDetail', {
  templateUrl: 'app/modules/soundlink_ihm/pages/albums/detail/albumDetail.html',
  controller: albumDetailController,
  bindings: {
    album : '<'
  }
});

albumDetailController.$inject = ['socketService', 'soundlinkResource', 'audioPlayer'];

function albumDetailController(socketService, soundlinkResource, audioPlayer) {
  var vm = this;

  vm.$onInit = function () {
    soundlinkResource.getMusicsFromAlbum(vm.album.id).then(function (data) {
      vm.albumSongs = [];
      angular.forEach(data, function (music, value) {
        var song = {};
        angular.copy(music, song);
        song.url = "/soundlink_server/soundlink/music/" + music.id;
        song.album = vm.album;
        vm.albumSongs.push(song);
      });
    });
  };
}
