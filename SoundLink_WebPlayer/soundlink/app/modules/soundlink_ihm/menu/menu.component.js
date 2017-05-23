'use strict';

angular.module('soundlink').component('soundlinkMenu', {
    templateUrl: 'app/modules/soundlink_ihm/menu/menu.html',
    controller: menuController
});

menuController.$inject = ['eventManager', 'notificationManager', 'audioRemoteControl', 'loginService', '$state', 'userStorage'];

function menuController(eventManager, notificationManager, audioRemoteControl, loginService, $state, userStorage) {
    var vm = this;

    var menuExpanded = false;

    vm.getUserInformation = userStorage.getUserInformation;

    vm.remoteControlChanged = function () {
        if (vm.remoteControl) {
            audioRemoteControl.activeRemoteControl().then(function () {
                notificationManager.showNotification("Remote control activited ");
            });
        } else {
            audioRemoteControl.desactiveRemoteControl().then(function () {
                notificationManager.showNotification("Remote control desactivited ");
            });
        }
    };

    vm.isMenuExpanded = function (){
        return menuExpanded;
    };

    vm.toggleMenu = function (){
        menuExpanded = !menuExpanded;
    };

    vm.logout = function () {
        loginService.logout().then(function () {
            $state.go('login');
        });
    };
}