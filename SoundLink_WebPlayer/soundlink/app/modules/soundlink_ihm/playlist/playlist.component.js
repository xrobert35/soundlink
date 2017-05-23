'use strict';

angular.module('soundlink').component('soundlinkPlaylist', {
    templateUrl: 'app/modules/soundlink_ihm/playlist/playlist.html',
    controller: playlistController
});

playlistController.$inject = ['eventManager', 'lodash', 'notificationManager', 'audioStatus', 'musicsResource', 'playlistsResource', 'createPlaylistDialog'];

function playlistController(eventManager, lodash, notificationManager, audioStatus, musicsResource, playlistsResource, createPlaylistDialog) {
    var vm = this;

    vm.showPlaylist = false;
    vm.getCurrentSong = audioStatus.getCurrentSong;
    vm.getPlaylist = audioStatus.getPlaylist;

    vm.playlists = [];

    vm.getPlaylists = function () {
        return vm.playlists;
    };

    vm.createPlaylist = function () {
        createPlaylistDialog.open().then(function (playlist) {
            playlistsResource.createPlaylist(playlist).then(function () {
                notificationManager.showNotification("Playlist " + playlist.name + " created");
                refreshPlaylists();
            });
        });
    };

    vm.extractPlaylist = function () {
        createPlaylistDialog.open().then(function (playlist) {
            var musicsId = lodash.flatMap(vm.getPlaylist(), function (playlist) {
                return playlist.id;
            });
            playlist.musicsId = musicsId;
            playlistsResource.createPlaylist(playlist).then(function () {
                notificationManager.showNotification("Playlist " + playlist.name + " created");
                refreshPlaylists();
            });
        });
    };

    vm.removeFromPlaylist = function (music) {
        if (!vm.selectedPlaylist.isQueue) {
            playlistsResource.removeMusic(vm.selectedPlaylist.id, music.id).then(function () {
                notificationManager.showNotification("Music removed from " + vm.selectedPlaylist.name);
                audioStatus.getPlaylist().splice(audioStatus.getPlaylist().indexOf(music), 1);
            });
        } else {
            audioStatus.getPlaylist().splice(audioStatus.getPlaylist().indexOf(music), 1);
        }
    };

    vm.onPlaylistSelected = function () {
        if (!vm.selectedPlaylist.isQueue) {
            musicsResource.getMusicsFromPlaylist(vm.selectedPlaylist.id).then(function (musics) {
                vm.selectedPlaylist.musics = musics;
                audioStatus.setPlaylist(musics);
            });
        } else {
            audioStatus.setPlaylist(vm.selectedPlaylist.musics);
        }
    };

    vm.clearPlaylist = function () {
        vm.getPlaylist().splice(0, vm.getPlaylist().length);
    };

    vm.$onInit = function () {
        refreshPlaylists();
    };

    function refreshPlaylists() {
        playlistsResource.getUserPlaylists().then(function (playlists) {
            vm.playlists = [];
            vm.playlists.push({
                name: "Current Queue",
                isQueue: true,
                musics: []
            });
            vm.playlists = vm.playlists.concat(playlists);
            vm.selectedPlaylist = vm.playlists[0];
            vm.onPlaylistSelected();
        });
    }

    eventManager.subscribeToEvent("playlistOpen", function () {
        vm.showPlaylist = true;
    });

    eventManager.subscribeToEvent("tooglePlaylistOpen", function () {
        vm.showPlaylist = !vm.showPlaylist;
    });

    eventManager.subscribeToEvent("musicAdded", function (music) {
        if (!vm.selectedPlaylist.isQueue) {
            playlistsResource.addMusic(vm.selectedPlaylist.id, music.id).then(function () {
                notificationManager.showNotification("Music added to " + vm.selectedPlaylist.name);
            });
        }
    });
}