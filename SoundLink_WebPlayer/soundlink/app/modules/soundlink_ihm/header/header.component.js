'use strict';

angular.module('soundlink').component('soundlinkHeader', {
    templateUrl: 'app/modules/soundlink_ihm/header/header.html',
    controller: headerController
});

headerController.$inject = ['$scope', 'loginService', 'eventManager', '$state', 'userStorage'];

function headerController($scope, loginService, eventManager, $state, userStorage) {
    var vm = this;

    vm.getUserInformation = userStorage.getUserInformation;

    vm.menuOpen = true;
    vm.toggleMenu = function () {
        vm.menuOpen = !vm.menuOpen;
        eventManager.fireEvent("menuOpen", vm.menuOpen);
    };

    vm.logout = function () {
        loginService.logout().then(function () {
            $state.go('login');
        });
    };
}