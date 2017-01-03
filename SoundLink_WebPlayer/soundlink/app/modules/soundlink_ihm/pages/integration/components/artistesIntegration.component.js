'use strict';

angular.module('soundlink').component('artistesIntegration', {
    templateUrl: 'app/modules/soundlink_ihm/pages/integration/components/artistesIntegration.html',
    controller: artistesIntegrationController
});

artistesIntegrationController.$inject = ['integrationContenu'];

function artistesIntegrationController(integrationContenu) {

    var vm = this;

    vm.artistes = integrationContenu.getIntegrationDatas().artistes;
}