'use strict';

angular.module('soundlink').component('soundlinkMenu', {
    templateUrl: 'app/modules/soundlink_ihm/menu/menu.html',
    controller: menuController
});

menuController.$inject = ['eventManager', '$rootScope', '$state'];

function menuController(eventManager, $rootScope, $state) {
    var vm = this;

    vm.showMenu = false;

    vm.isCurrentUrl = function isCurrentUrl(url) {
        return $rootScope.currentStateUrl === url;
    };

    eventManager.subscribeToEvent("menuOpen", function (isOpen) {
        vm.showMenu = isOpen;
    });

    vm.goToIntegration = function () {
        $state.go("soundlink.integration");
    };

    vm.goToUserProfile = function () {
        $state.go("soundlink.userprofile");
    };
}