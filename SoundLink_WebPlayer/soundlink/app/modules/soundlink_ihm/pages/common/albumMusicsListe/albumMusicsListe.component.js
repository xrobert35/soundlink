'use strict';

angular.module('soundlink').component('albumMusicsListe', {
    templateUrl: 'app/modules/soundlink_ihm/pages/common/albumMusicsListe/albumMusicsListe.html',
    controller: albumMusicsListeController,
    bindings: { album: '<' }
});

albumMusicsListeController.$inject = ['musicsResource'];

function albumMusicsListeController(musicsResource) {
    var vm = this;

    vm.musics = [];

    vm.$onInit = function () {
        musicsResource.getMusicsFromAlbum(vm.album.id).then(function (musics) {
            vm.musics = musics;
        });
    };
}