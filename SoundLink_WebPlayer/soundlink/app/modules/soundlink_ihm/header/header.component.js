'use strict';

angular.module('soundlink').component('soundlinkHeader', {
    templateUrl: 'app/modules/soundlink_ihm/header/header.html',
    controller: headerController
});

headerController.$inject = ['loginService', 'eventManager','$location'];

function headerController(loginService, eventManager, $location) {
    var vm = this;

    var originatorEv;

    vm.openMenu = function ($mdOpenMenu, ev) {
        originatorEv = ev;
        $mdOpenMenu(ev);
    };

    vm.showMenu = function(){
        eventManager.fireEvent("showMenu");
    };

    var menuItems = [{
        libelle : "Logout",
        action : logout
    }];

    vm.getMenuItems = function(){
        return menuItems;
    };

    function logout(){
        loginService.logout().then(function(){
            $location.path('/');
        });
    }
}