'use strict';

angular.module('soundlink').component('soundlinkAudioplayer', {
    templateUrl: 'app/modules/soundlink_ihm/audioPlayer/audioPlayer.html',
    controller: audioplayerController
});

audioplayerController.$inject = ['eventManager', 'audioPlayer', 'audioStatus'];

function audioplayerController(eventManager, audioPlayer, audioStatus) {
    var vm = this;

    vm.isPlaying = audioPlayer.isPlaying;
    vm.getProgress = audioStatus.getProgress;

    vm.openPlaylist = function (){
        eventManager.fireEvent("playlistOpen");
    };
}