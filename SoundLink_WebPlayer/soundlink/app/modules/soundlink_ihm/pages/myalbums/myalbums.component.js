'use strict';

angular.module('soundlink').component('myalbumsPage', {
    templateUrl: 'app/modules/soundlink_ihm/pages/myalbums/myalbums.html',
    controller: myalbumsController,
    bindings: { albums: '<' }
});

myalbumsController.$inject = ['albumsResource', 'usersResource', 'notificationManager'];

function myalbumsController(albumsResource, usersResource, notificationManager) {

    var vm = this;

    vm.selectAlbum = function (album) {
        vm.selectedAlbum = album;
    };

    vm.removeAlbumFromUser = function (album) {
        usersResource.removeFavoriteAlbum(album.id).then(function () {
            notificationManager.showNotification("Album removed from favorite");
            vm.artistes.splice(vm.artistes.indexOf(album), 1);
        });
    };
}
