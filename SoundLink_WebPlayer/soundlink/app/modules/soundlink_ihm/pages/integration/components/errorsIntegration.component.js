'use strict';

angular.module('soundlink').component('errorsIntegration', {
  templateUrl: 'app/modules/soundlink_ihm/pages/integration/components/errorsIntegration.html',
  controller: errorsIntegrationController,
    bindings : {
        errors : '<'
    }
});

errorsIntegrationController.$inject = [];

function errorsIntegrationController() {
  var vm = this;

 
}