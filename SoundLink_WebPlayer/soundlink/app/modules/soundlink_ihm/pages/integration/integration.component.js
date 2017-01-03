'use strict';

angular.module('soundlink').component('integrationPage', {
    templateUrl: 'app/modules/soundlink_ihm/pages/integration/integration.html',
    controller: integrationController
});

integrationController.$inject = ['$state', 'soundlinkadminResource', 'integrationContenu'];

function integrationController($state, soundlinkadminResource, integrationContenu) {
    var vm = this;

    vm.isloading = false;
    vm.integrationdatas = null;
    vm.selectArtisteChange = selectArtisteChange;
    vm.selectAlbumChange = selectAlbumChange;

    function selectArtisteChange(artiste) {
        angular.forEach(artiste.albums, function (album) {
            album.selected = artiste.selected;
        });
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
        vm.isloading = true;
        soundlinkadminResource.loadMusics().then(function (pItegrationdatas) {
            vm.integrationdatas = pItegrationdatas;
            integrationContenu.setIntegrationDatas(pItegrationdatas);
            $state.go('soundlink.integration.artistes');
        }).finally(function(){
            vm.isloading = false;
        });
    };

    vm.$onInit = function () {
    };
}