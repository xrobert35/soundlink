'use strict';

angular.module("soundlink").config(iconConfig);

iconConfig.$inject = ['$mdIconProvider'];

function iconConfig($mdIconProvider) {
  $mdIconProvider.fontSet('md', 'material-icons');
}