'use strict';

angular.module('soundlink').component('soundlinkAudioplayer', {
    templateUrl: 'app/modules/soundlink_ihm/audioPlayer/audioPlayer.html',
    controller: audioplayerController
});

audioplayerController.$inject = ['$scope', 'eventManager', 'audioPlayer', 'audioStatus'];

function audioplayerController($scope, eventManager, audioPlayer, audioStatus) {
    var vm = this;

    vm.volume = 50;

    vm.isPlaying = audioStatus.isPlaying;
    vm.isRepeating = audioStatus.isRepeating;
    vm.isRepeatingOne = audioStatus.isRepeatingOne;
    vm.isMuted = audioStatus.isMuted;
    vm.getProgress = audioStatus.getProgress;
    vm.getCurrentSong = audioStatus.getCurrentSong;
    vm.getLoadingPercent = audioStatus.getLoadingPercent;

    var audioVolumeWatcher = $scope.$watch(audioStatus.getVolume, function(volume){
        vm.volume = volume * 100;
    });

    vm.$onDestroy = function (){
        audioVolumeWatcher();
    };

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