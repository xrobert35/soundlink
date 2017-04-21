'use strict';

angular.module('soundlink').component('albumsListe', {
    templateUrl: 'app/modules/soundlink_ihm/pages/common/albums/albumsListe.html',
    controller: albumsListeController,
    bindings: { artiste: '<' }
});

albumsListeController.$inject = ['albumsResource', 'musicsResource'];

function albumsListeController(albumsResource, musicsResource) {
    var vm = this;
    vm.albumSongs = [];

    vm.selectAlbum = function (album) {
        vm.selectedAlbum = album;

        musicsResource.getMusicsFromAlbum(album.id).then(function (data) {
            vm.albumSongs = [];
            angular.forEach(data, function (music, value) {
                var song = {};
                angular.copy(music, song);
                song.album = album;
                vm.albumSongs.push(song);
            });
        });
    };

    vm.$onInit = function () {
        albumsResource.getAlbumsFromArtiste(vm.artiste.id).then(function (albums) {
            vm.albums = albums;
            //Select the first album
            if(albums != null && albums.length > 0){
                vm.selectAlbum(vm.albums[0]);
            }
        });

    };
}