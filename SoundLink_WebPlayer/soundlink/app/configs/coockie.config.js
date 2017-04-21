'use strict';

angular.module("soundlink").config(coockieConfig);

coockieConfig.$inject = ['$cookiesProvider'];

function coockieConfig($cookiesProvider) {
  $cookiesProvider.defaults.path = '/soundlink_server';
  $cookiesProvider.defaults.domaine = '/' + window.location.hostname;
}