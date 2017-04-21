'use strict';

angular.module('soundlink').component('soundlinkMenu', {
    templateUrl: 'app/modules/soundlink_ihm/menu/menu.html',
    controller: menuController
});

menuController.$inject = ['eventManager', 'notificationManager'];

function menuController(eventManager, notificationManager) {
    var vm = this;

    vm.showMenu = true;

    eventManager.subscribeToEvent("menuOpen", function (isOpen) {
        vm.showMenu = isOpen;
    });

    vm.remoteControlChanged = function () {
        if (vm.remoteControl) {
            notificationManager.showNotification("Remote control activited ");
        } else {
            notificationManager.showNotification("Remote control desactivited ");
        }
    };
}