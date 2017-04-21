angular.module('soundlink').service("notificationManager", notificationManager);

notificationManager.$inject = ['$mdToast'];

function notificationManager($mdToast) {

  this.showNotification = function (text) {
    $mdToast.show($mdToast.simple().textContent(text).position('top right').hideDelay(3000));
  };

}
