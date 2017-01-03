'use strict';

//Route configuration
angular.module("soundlink").config(loginConfig);

loginConfig.$inject = ['$stateProvider'];

function loginConfig($stateProvider) {

    var viewFolder = "";

    $stateProvider.state('login', {
        url: '/login',
        controller: "loginController",
        controllerAs: "vm",
        templateUrl: '/app/modules/soundlink_ihm/login/login.html'
    });
}