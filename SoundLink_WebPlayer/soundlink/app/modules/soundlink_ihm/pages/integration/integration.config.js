'use strict';

angular.module("soundlink").config(integrationConfig);

integrationConfig.$inject = ['$stateProvider', '$urlRouterProvider',];

function integrationConfig($stateProvider, $urlRouterProvider, integrationContenu) {
    $stateProvider.state('soundlink.integration', {
        url: 'integration',
        template: '<integration-page></integration-page>'
    });

    initArtiste.$inject = ['integrationContenu'];
    function initArtiste(integrationContenu) {
        return integrationContenu.getIntegrationDatas().artistes;
    }

    initErrors.$inject = ['integrationContenu'];
    function initErrors(integrationContenu) {
        return integrationContenu.getIntegrationDatas().errors;
    }

    //PAGES
    $stateProvider.state('soundlink.integration.artistes', {
        url: '/artistes',
        resolve: {
            artistes: initArtiste
        },
        template: '<artistes-integration artistes="$resolve.artistes"></artistes-integration>'
    });

    $stateProvider.state('soundlink.integration.errors', {
        url: '/errors',
        resolve: {
            errors: initErrors
        },
        template: '<errors-integration errors="$resolve.errors"></errors-integration>'
    });

}