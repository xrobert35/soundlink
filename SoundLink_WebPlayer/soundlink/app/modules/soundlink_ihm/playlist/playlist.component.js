'use strict';

angular.module('soundlink').component('soundlinkPlaylist', {
    templateUrl: 'app/modules/soundlink_ihm/playlist/playlist.html',
    controller: playlistController
});

playlistController.$inject = ['eventManager', 'audioPlayer'];

function playlistController(eventManager, audioPlayer) {
    var vm = this;

    vm.showPlaylist = false;
    vm.getPlaylist = audioPlayer.getPlaylist;

    eventManager.subscribeToEvent("playlistOpen", function () {
        vm.showPlaylist =  !vm.showPlaylist;
    });
}