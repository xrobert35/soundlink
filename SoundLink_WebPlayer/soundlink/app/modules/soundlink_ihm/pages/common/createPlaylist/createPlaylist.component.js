'use strict';

angular.module('soundlink').component('createPlaylist', {
    templateUrl: 'app/modules/soundlink_ihm/pages/common/createPlaylist/createPlaylist.html',
    controller: createPlaylistController,
    bindings: {}
});

createPlaylistController.$inject = ['$mdDialog', 'fileUtils', 'playlistsResource', 'notificationManager'];

function createPlaylistController($mdDialog, fileUtils, playlistsResource, notificationManager) {
    var vm = this;

    vm.playlist = {};

    vm.clickUpload = function () {
        document.getElementById("upload-playlist-picture").click();
    };

    vm.uploadPicture = function (event) {
        var file = event.target.files[0];
        fileUtils.extractBase64FromFile(file).then(function (base64File) {
            vm.playlist.cover = base64File;
        });
    };

    vm.closeDialog = function () {
        $mdDialog.cancel();
    };

    vm.valideDialog = function () {
        $mdDialog.hide(vm.playlist);
    };
}