'use strict';

angular.module('soundlink').component('soundlinkHeader', {
    templateUrl: 'app/modules/soundlink_ihm/header/header.html',
    controller: headerController
});

headerController.$inject = ['$scope', 'loginService', 'eventManager', '$state', 'userStorage'];

function headerController($scope, loginService, eventManager, $state, userStorage) {
    var vm = this;

    vm.userInformation = userStorage.getUserInformation();

    $scope.$watch('menuOpen', function (isOpen) {
        eventManager.fireEvent("menuOpen", isOpen);
    });

    vm.logout = function () {
        loginService.logout().then(function () {
            $state.go('login');
        });
    };
}