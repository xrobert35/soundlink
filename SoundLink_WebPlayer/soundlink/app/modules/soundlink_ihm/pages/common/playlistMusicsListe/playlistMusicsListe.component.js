'use strict';

angular.module('soundlink').component('playlistMusicsListe', {
    templateUrl: 'app/modules/soundlink_ihm/pages/common/playlistMusicsListe/playlistMusicsListe.html',
    controller: playlistMusicsListeController,
    bindings: { playlist: '<' }
});

playlistMusicsListeController.$inject = ['musicsResource'];

function playlistMusicsListeController(musicsResource) {
    var vm = this;

    vm.musics = [];

    vm.$onInit = function () {
        musicsResource.getMusicsFromPlaylist(vm.playlist.id).then(function (musics) {
            vm.musics = musics;
        });
    };
}