'use strict';

angular.module('soundlink').component('soundlinkPlaylist', {
    templateUrl: 'app/modules/soundlink_ihm/playlist/playlist.html',
    controller: playlistController
});

playlistController.$inject = ['eventManager', 'audioStatus'];

function playlistController(eventManager, audioStatus) {
    var vm = this;

    vm.showPlaylist = false;
    vm.getPlaylist = audioStatus.getPlaylist;

    eventManager.subscribeToEvent("playlistOpen", function () {
        vm.showPlaylist = true;
    });

    eventManager.subscribeToEvent("tooglePlaylistOpen", function () {
        vm.showPlaylist = !vm.showPlaylist;
    });


}