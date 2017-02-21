'use strict';

angular.module('soundlink').component('integrationPage', {
    templateUrl: 'app/modules/soundlink_ihm/pages/integration/integration.html',
    controller: integrationController
});

integrationController.$inject = ['$state', 'soundlinkadminResource', 'integrationContenu', '$websocket', 'config', '$cookies', 'tokenStorage', 'Upload'];

function integrationController($state, soundlinkadminResource, integrationContenu, $websocket, config, $cookies, tokenStorage, Upload) {
    var vm = this;

    vm.isloading = false;
    vm.integrationdatas = null;
    vm.selectArtisteChange = selectArtisteChange;
    vm.selectAlbumChange = selectAlbumChange;
    vm.uploadFiles = uploadFiles;

    vm.getIntegrationProgress = getIntegrationProgress;
    vm.getUploadProgress = getUploadProgress;
    vm.getIntegrationMessage = getIntegrationMessage;

    function selectArtisteChange(artiste) {
        angular.forEach(artiste.albums, function (album) {
            album.selected = artiste.selected;
        });
    }

    function getIntegrationProgress() {
        return vm.integrationProgress || 0;
    }

    function getUploadProgress() {
        return vm.uploadProgress || 0;
    }

    function getIntegrationMessage() {
        if (vm.integrationMessage != null) {
            return vm.integrationMessage;
        }
    }

    function uploadFiles($files) {
        if ($files[0].type == 'application/x-zip-compressed') {
            vm.fileToUpload = $files[0];

            Upload.upload({
                url: config.serveurUrl + 'admin/integrateFromZipFile',
                data: { file: vm.fileToUpload }
            }).then(function (resp) {
                vm.uploadSuccess = true;
                console.log('Success ' + resp.config.data.file.name + 'uploaded. Response: ' + resp.data);
            }, function (resp) {
                vm.uploadSuccess = false;
                console.log('Error status: ' + resp.status);
            }, function (evt) {
                var progressPercentage = parseInt(100.0 * evt.loaded / evt.total);
                vm.uploadProgress = progressPercentage;
            });
        }
    }


    function selectAlbumChange(artiste) {
        artiste.selected = isAllAlbumSelected(artiste.albums);
    }

    function isAllAlbumSelected(albums) {
        var allSelected = true;
        angular.forEach(albums, function (album) {
            if (!album.selected) {
                allSelected = false;
            }
        });
        return allSelected;
    }

    vm.loadMusics = function loadMusics() {
        // $cookies.put('X-AUTH-TOKEN', tokenStorage.retrieve());
        var dataStream = $websocket(config.wsServeurUrl + "ws/integration");
        dataStream.send(JSON.stringify({ action: 'start' }));

        dataStream.onOpen(function () {
            // $cookies.remove("X-AUTH-TOKEN");
            vm.isloading = true;
            vm.progress = 0;
        });

        dataStream.onClose(function () {
            console.log('close');
            vm.isloading = false;
        });

        dataStream.onMessage(function (message) {
            var data = JSON.parse(message.data);
            if (data.progress) {
                vm.integrationProgress = data.progress;
            }
            if (data.message) {
                vm.integrationMessage = data.message;
            }
        });
    };

    vm.$onInit = function () {
    };
}