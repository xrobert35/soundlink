'use strict';

angular.module("soundlink").config(integrationConfig);

integrationConfig.$inject = ['$stateProvider'];

function integrationConfig($stateProvider, $urlRouterProvider) {
    $stateProvider.state('soundlink.integration', {
        url: 'integration',
        template: '<integration-page></integration-page>'
    });

    //PAGES
    $stateProvider.state('soundlink.integration.artistes', {
        url: '/artistes',
        template: '<artistes-integration></artistes-integration>'
    });

    $stateProvider.state('soundlink.integration.errors', {
        url: '/errors',
        template: '<errors-integration></errors-integration>'
    });

}