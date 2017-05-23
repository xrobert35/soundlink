'use strict';

//Route configuration
angular.module("soundlink").config(loginConfig);

loginConfig.$inject = ['$stateProvider'];

function loginConfig($stateProvider) {

    var viewFolder = "";

    $stateProvider.state('login', {
        url: '/login',
        template: '<login-page layout="row" flex></login-page>'
    });
}