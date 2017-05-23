'use strict';

angular.module('soundlink').component('albumsListe', {
    templateUrl: 'app/modules/soundlink_ihm/pages/common/albumsListe/albumsListe.html',
    controller: albumsListeController,
    bindings: { artiste: '<' }
});

albumsListeController.$inject = ['albumsResource', 'musicsResource', 'notificationManager', 'usersResource', 'audioPlayer'];

function albumsListeController(albumsResource, musicsResource, notificationManager, usersResource, audioPlayer) {
    var vm = this;
    vm.albumSongs = [];

    vm.selectAlbum = function (album) {
        vm.selectedAlbum = album;

        musicsResource.getMusicsFromAlbum(album.id).then(function (musics) {
            vm.albumSongs = [];
            angular.forEach(musics, function (music) {
                music.album = album;
                vm.albumSongs.push(music);
            });
        });
    };

    vm.playAlbum = function (album) {
        musicsResource.getMusicsFromAlbum(album.id).then(function (musics) {
            var firstSong = musics[0];
            angular.forEach(musics, function (music) {
                music.album = album;
                audioPlayer.add(music);
            });
            audioPlayer.play(firstSong);
        });
    };

    vm.addRemoveAlbumToUser = function (album) {
        if (album.selected) {
            usersResource.removeFavoriteAlbum(album.id).then(function () {
                album.selected = false;
                notificationManager.showNotification("Album removed from favorite");
            });
        } else {
            usersResource.addFavoriteAlbum(album.id).then(function () {
                album.selected = true;
                notificationManager.showNotification("Album added to favorite");
            });
        }
    };

    vm.$onInit = function () {
        albumsResource.getAlbumsFromArtiste(vm.artiste.id).then(function (albums) {
            vm.albums = albums;
            //Select the first album
            if (albums != null && albums.length > 0) {
                vm.selectAlbum(vm.albums[0]);
            }
        });

    };
}