'use strict';

angular.module('soundlink').component('myartistesPage', {
    templateUrl: 'app/modules/soundlink_ihm/pages/myartistes/myartistes.html',
    controller: myartistesController,
    bindings: { artistes: '<' }
});

myartistesController.$inject = ['artistesResource', 'usersResource', 'notificationManager'];

function myartistesController(artistesResource, usersResource, notificationManager) {

    var vm = this;

    vm.selectArtiste = function (artiste) {
        vm.selectedArtiste = artiste;
    };

    vm.removeArtisteFromUser = function (artiste, $event) {
        $event.stopPropagation();
        usersResource.removeFavoriteArtiste(artiste.id).then(function () {
            notificationManager.showNotification("Artiste removed from favorite");
            vm.artistes.splice(vm.artistes.indexOf(artiste), 1);
        });
    };
}
