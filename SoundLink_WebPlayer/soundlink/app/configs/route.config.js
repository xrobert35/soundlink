'use strict';

//Route configuration
angular.module("soundlink").config(routeConfig);

routeConfig.$inject = ['$stateProvider', '$urlRouterProvider'];

function routeConfig($stateProvider, $urlRouterProvider) {
  //Default page is login
  $urlRouterProvider.otherwise('soundlink/albums');
  $urlRouterProvider.when('/soundlink','/soundlink/albums');

initResolve.$inject = ['soundlinkResource', 'userStorage'];
  function initResolve(soundlinkResource, userStorage) {
    return soundlinkResource.getUserInformation().then(function (userInformation) {
      userStorage.saveUserInformation(userInformation);
    });
  }

  $stateProvider.state('start', {
    template: '<div></div>',
    resolve: {
      init: initResolve
    }
  });
}


