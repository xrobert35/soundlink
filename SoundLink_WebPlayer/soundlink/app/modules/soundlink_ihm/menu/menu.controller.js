angular.module('soundlink').controller('leftbarController', ['eventManager', '$rootScope', leftbarController]);

function leftbarController(eventManager, $rootScope) {
    var controller = this;

    controller.isCurrentUrl = function isCurrentUrl(url) {
        console.log("is current " +url + " " + $rootScope.currentStateUrl);
        return $rootScope.currentStateUrl === url;
    }
}