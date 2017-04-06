'use strict';

angular.module('soundlink').component('artistesPage', {
    templateUrl: 'app/modules/soundlink_ihm/pages/artistes/artistes.html',
    controller: artistesController,
    bindings: { artistes: '<' }
});

artistesController.$inject = ['soundlinkResource', '$location'];

function artistesController(soundlinkResource, $location) {
    var vm = this;

    vm.getPaginations = function () {
        return 'ABCDEFGHIJKLMNOPQRSTUVWXYZ'.split('');
    };

    vm.isActive = function(startChain){
        return $location.hash() == startChain;
    };

    vm.getArtistesStartWith = function (startChain) {
        $location.hash(startChain);
        soundlinkResource.getArtistesStartWith(startChain).then(function (artistes) {
            vm.artistes = artistes;
        });
    };

    vm.selectArtiste = function (artiste) {
        vm.selectedArtiste = artiste;
    };
}
