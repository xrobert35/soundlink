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
        console.log("changement des albums");
        soundlinkResource.getAlbums().then(function (albums) {
            vm.albums = albums;
        });
    };

    vm.showAlbumMusics = function showAlbumMusics(album) {
        vm.selectedAlbum = album;
        soundlinkResource.getMusicsFromAlbum(album.id).then(function (data) {
            vm.albumMusics = data;
            vm.albumSongs = [];
            angular.forEach(data, function (music, value) {
                var song = {};
                song.id = music.title + music.trackNumber;
                song.title = music.title;
                song.artist = music.artist;
                song.durationInSeconde = music.durationInSeconde;
                song.trackNumber = music.trackNumber;
                song.url = "/soundlink_server/soundlink/music/" + music.id;
                vm.albumSongs.push(song);
            });
        });
    };
}