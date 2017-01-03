'use strict';

angular.module("soundlink").config(artistesConfig);

artistesConfig.$inject = ['$stateProvider'];

function artistesConfig ($stateProvider, $urlRouterProvider) {
    $stateProvider.state('soundlink.artistes', {
        url: 'artistes',
        template : '<artistes-page></artistes-page>'    
    });
}