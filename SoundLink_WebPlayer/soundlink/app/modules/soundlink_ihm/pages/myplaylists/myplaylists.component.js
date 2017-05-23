'use strict';

angular.module('soundlink').component('myplaylistsPage', {
    templateUrl: 'app/modules/soundlink_ihm/pages/myplaylists/myplaylists.html',
    controller: myplaylistsController,
    bindings: { playlists : '<' }
});

myplaylistsController.$inject = ['playlistsResource', 'notificationManager'];

function myplaylistsController(playlistsResource, notificationManager) {
    var vm = this;

    vm.selectPlaylist = function (playlist) {
        vm.selectedPlaylist = playlist;
    };

    vm.deletePlaylist = function (playlist) {
        playlistsResource.deletePlaylist(playlist.id).then(function(){
            notificationManager.showNotification("Playlist  " + playlist.name+ " deleted");
            vm.playlists.splice(vm.playlists.indexOf(playlist), 1);
        });
    };
}
