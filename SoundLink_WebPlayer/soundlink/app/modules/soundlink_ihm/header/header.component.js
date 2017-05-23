'use strict';

angular.module('soundlink').component('soundlinkHeader', {
    templateUrl: 'app/modules/soundlink_ihm/header/header.html',
    controller: headerController
});

headerController.$inject = ['$scope', 'loginService', 'eventManager', '$state', 'userStorage'];

function headerController($scope, loginService, eventManager, $state, userStorage) {
    var vm = this;
}