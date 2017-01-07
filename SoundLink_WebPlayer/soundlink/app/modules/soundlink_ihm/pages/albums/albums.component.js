'use strict';

angular.module('soundlink').component('albumsPage', {
    templateUrl: 'app/modules/soundlink_ihm/pages/albums/albums.html',
    controller: albumsController,
    bindings: { albums: '<' }
});

albumsController.$inject = ['socketService', 'soundlinkResource', 'eventManager'];

function albumsController(socketService, soundlinkResource, eventManager) {
    var vm = this;

    vm.albumSongs = [];

    vm.getAlbums = function getAlbums() {
        soundlinkResource.getAlbums().then(function (albums) {
            vm.albums = albums;
        });
    };

    vm.showAlbumMusics = function showAlbumMusics(album) {
        vm.selectedAlbum = album;
    };
}