'use strict';

angular.module('soundlink').component('soundlinkAudioplayer', {
    templateUrl: 'app/modules/soundlink_ihm/audioPlayer/audioPlayer.html',
    controller: audioplayerController
});

audioplayerController.$inject = ['eventManager', 'audioPlayer', 'audioStatus'];

function audioplayerController(eventManager, audioPlayer, audioStatus) {
    var vm = this;

    vm.volume = 50;

    vm.isPlaying = audioStatus.isPlaying;
    vm.getProgress = audioStatus.getProgress;
    vm.getCurrentSong = audioStatus.getCurrentSong;
    vm.getLoadingPercent = audioStatus.getLoadingPercent;

    vm.volumeChange = function () {
        audioPlayer.setVolume(vm.volume);
    };

    vm.openPlaylist = function () {
        eventManager.fireEvent("tooglePlaylistOpen");
    };

    vm.$onInit = function () {
        audioPlayer.setVolume(vm.volume);
    };
}