'use strict';

angular.module('soundlink').component('myplaylistsPage', {
    templateUrl: 'app/modules/soundlink_ihm/pages/myplaylists/myplaylists.html',
    controller: myplaylistsController,
    bindings: { playlists : '<' }
});

myplaylistsController.$inject = ['playlistsResource'];

function myplaylistsController(playlistsResource) {
    var vm = this;

    vm.selectPlaylist = function (playlist) {
        vm.selectedPlaylist = playlist;
    };

    vm.deletePlaylist = function (playlist) {
        playlistsResource.deletePlaylist(playlist.id);
    };
}
