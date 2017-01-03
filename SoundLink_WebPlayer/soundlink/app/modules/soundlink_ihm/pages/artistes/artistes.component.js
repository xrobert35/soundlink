'use strict';

angular.module('soundlink').component('artistesPage', {
    templateUrl: 'app/modules/soundlink_ihm/pages/artistes/artistes.html',
    controller: artistesController
});

artistesController.$inject = ['soundlinkResource'];

function artistesController(soundlinkResource) {
        var vm = this;

        vm.getArtistes = function(){

        };
}
