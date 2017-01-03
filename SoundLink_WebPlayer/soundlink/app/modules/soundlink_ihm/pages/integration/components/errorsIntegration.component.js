'use strict';

angular.module('soundlink').component('errorsIntegration', {
  templateUrl: 'app/modules/soundlink_ihm/pages/integration/components/errorsIntegration.html',
  controller: errorsIntegrationController
});

errorsIntegrationController.$inject = ['integrationContenu'];

function errorsIntegrationController(integrationContenu) {

  var vm = this;

  vm.errors = integrationContenu.getIntegrationDatas().errors;

}