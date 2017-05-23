'use strict';

angular.module('soundlink').component('integrationPage', {
    templateUrl: 'app/modules/soundlink_ihm/pages/integration/integration.html',
    controller: integrationController
});

integrationController.$inject = ['$state', 'soundlinkadminResource', 'integrationContenu', 'config', 'socketManager', 'Upload'];

function integrationController($state, soundlinkadminResource, integrationContenu, config, socketManager, Upload) {
    var vm = this;

    vm.isloading = false;
    vm.integrationdatas = null;
    vm.selectArtisteChange = selectArtisteChange;
    vm.selectAlbumChange = selectAlbumChange;
    vm.uploadFiles = uploadFiles;

    function selectArtisteChange(artiste) {
        angular.forEach(artiste.albums, function (album) {
            album.selected = artiste.selected;
        });
    }

    vm.getIntegrationProgress = function () {
        return vm.integrationProgress || 0;
    };

    vm.getUploadProgress = function () {
        return vm.uploadProgress || 0;
    };

    vm.getUploadMessage = function () {
        return vm.uploadMessage;
    };

    vm.getIntegrationMessage = function () {
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
                vm.uploadMessage = 'Success';
            }, function (resp) {
                vm.uploadSuccess = false;
                vm.uploadMessage = 'Error';
            }, function (evt) {
                vm.uploadProgress = parseInt(100.0 * evt.loaded / evt.total);
                vm.uploadMessage = vm.uploadProgress + '%';
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
        socketManager.openSocket('integration').then(function (dataStream) {
            vm.isloading = true;
            dataStream.onOpen(function () {
                vm.integrationRunning = true;
                vm.progress = 0;
                dataStream.send(JSON.stringify({ action: 'start' }));
            });
            dataStream.onClose(function () {
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
                if(data.end){
                    vm.integrationEnded = true;
                    vm.integrationRunning = false;
                }
            });
        });

    };

    vm.$onInit = function () {
    };
}