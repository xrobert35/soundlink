'use strict';

angular.module('soundlink').component('searchPage', {
    templateUrl: 'app/modules/soundlink_ihm/pages/search/search.html',
    controller: searchController,
    bindings: { artistes: '<' }
});

searchController.$inject = ['artistesResource', 'usersResource', 'notificationManager', '$location'];

function searchController(artistesResource, usersResource, notificationManager, $location) {
    var vm = this;

    vm.getPaginations = function () {
        return 'ABCDEFGHIJKLMNOPQRSTUVWXYZ'.split('');
    };

    vm.isActive = function (startChain) {
        return $location.hash() == startChain;
    };

    vm.getArtistesStartWith = function (startChain) {
        $location.hash(startChain);
        artistesResource.getArtistesStartWith(startChain).then(function (artistes) {
            vm.artistes = artistes;
        });
    };

    vm.search = function () {
        if (vm.searchValue != null && vm.searchValue.length > 0) {
            artistesResource.getArtistesStartWith(vm.searchValue).then(function (artistes) {
                vm.artistes = artistes;
            });
        }
    };

    vm.addRemoveArtisteToUser = function (artiste) {
        if (artiste.selected) {
            usersResource.removeFavoriteArtiste(artiste.id).then(function () {
                artiste.selected = false;
                notificationManager.showNotification("Artiste removed from favorite");
            });
        } else {
            usersResource.addFavoriteArtiste(artiste.id).then(function () {
                artiste.selected = true;
                notificationManager.showNotification("Artiste added to favorite");
            });
        }
    };

    vm.selectArtiste = function (artiste) {
        vm.selectedArtiste = artiste;
    };
}
