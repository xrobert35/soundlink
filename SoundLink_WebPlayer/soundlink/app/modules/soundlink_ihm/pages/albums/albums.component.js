'use strict';

angular.module('soundlink').component('albumsPage', {
    templateUrl: 'app/modules/soundlink_ihm/pages/albums/albums.html',
    controller: albumsController,
    bindings: { albums: '<' }
});

albumsController.$inject = ['socketService', 'soundlinkResource'];

function albumsController(socketService, soundlinkResource) {
    var vm = this;

    vm.showAlbumMusics = function showAlbumMusics(album) {
        vm.selectedAlbum = album;
    };
}