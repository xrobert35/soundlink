'use strict';

angular.module('soundlink').component('artistesIntegration', {
    templateUrl: 'app/modules/soundlink_ihm/pages/integration/components/artistesIntegration.html',
    controller: artistesIntegrationController,
    bindings : { artistes : '<' }
});

artistesIntegrationController.$inject = ['$scope'];

function artistesIntegrationController($scope) {
    var vm = this;

     var artiste = vm.artistes;
}