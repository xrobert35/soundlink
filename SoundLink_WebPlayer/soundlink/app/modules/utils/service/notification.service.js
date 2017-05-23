angular.module('soundlink').service("notificationManager", notificationManager);

notificationManager.$inject = ['$mdToast', '$filter'];

function notificationManager($mdToast, $filter) {

  this.showNotification = function (text) {
    $mdToast.show($mdToast.simple().textContent($filter('translate')(text)).position('top right').hideDelay(3000));
  };

}
