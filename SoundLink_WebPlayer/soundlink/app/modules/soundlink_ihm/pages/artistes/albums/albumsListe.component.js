'use strict';

angular.module('soundlink').component('albumsListe', {
    templateUrl: 'app/modules/soundlink_ihm/pages/artistes/albums/albumsListe.html',
    controller: albumsListeController,
    bindings: { artiste: '<' }
});

albumsListeController.$inject = ['socketService', 'soundlinkResource'];

function albumsListeController(socketService, soundlinkResource) {
    var vm = this;
    vm.albumSongs = [];

    vm.selectAlbum = function (album) {
        vm.selectedAlbum = album;

        soundlinkResource.getMusicsFromAlbum(album.id).then(function (data) {
            vm.albumSongs = [];
            angular.forEach(data, function (music, value) {
                var song = {};
                angular.copy(music, song);
                song.url = "/soundlink_server/soundlink/music/" + music.id;
                song.album = album;
                vm.albumSongs.push(song);
            });
        });
    };

    vm.$onInit = function () {
        soundlinkResource.getAlbumsFromArtiste(vm.artiste.id).then(function (albums) {
            vm.albums = albums;
        });
    };
}