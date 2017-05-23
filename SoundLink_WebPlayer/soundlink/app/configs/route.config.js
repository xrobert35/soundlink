'use strict';

//Route configuration
angular.module("soundlink").config(routeConfig);

routeConfig.$inject = ['$stateProvider', '$urlRouterProvider'];

function routeConfig($stateProvider, $urlRouterProvider) {
  //Default page is login
  $urlRouterProvider.otherwise('soundlink/search');
  $urlRouterProvider.when('/soundlink','/soundlink/search');

initResolve.$inject = ['usersResource', 'userStorage'];
  function initResolve(usersResource, userStorage) {
    return usersResource.getUserInformation().then(function (userInformation) {
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


