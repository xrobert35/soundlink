'use strict';

angular.module('soundlink').component('soundlinkMenu', {
    templateUrl: 'app/modules/soundlink_ihm/menu/menu.html',
    controller: menuController
});

menuController.$inject = ['eventManager', '$rootScope', '$state', 'socketService'];

function menuController(eventManager, $rootScope, $state, socketService) {
    var vm = this;

    vm.showMenu = true;

    eventManager.subscribeToEvent("menuOpen", function (isOpen) {
        vm.showMenu = isOpen;
    });
}