'use strict';

angular.module('soundlink').service('integrationContenu', integrationContenu);

function integrationContenu() {

  var contenu;

  this.getIntegrationDatas = function () {
    return contenu;
  };

  this.setIntegrationDatas = function (pDatas) {
    contenu = pDatas;
  };
}