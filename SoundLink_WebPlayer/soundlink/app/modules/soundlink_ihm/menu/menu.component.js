'use strict'; 

angular.module('soundlink').component('soundlinkMenu', {
    templateUrl: 'app/modules/soundlink_ihm/menu/menu.html',
    controller: menuController
});

menuController.$inject = ['eventManager', '$rootScope', 'soundlinkadminResource'];

function menuController(eventManager, $rootScope, soundlinkadminResource) {
    var vm = this;

    vm.showMenu = true;

    vm.isCurrentUrl = function isCurrentUrl(url) {
        return $rootScope.currentStateUrl === url;
    };

    eventManager.subscribeToEvent("showMenu", function () {
        vm.showMenu = !vm.showMenu;
    });

    vm.loadMusics = soundlinkadminResource.loadMusics;
}