angular.module("mod-login", ['ngCookies', 'ngMaterial']);

//Route configuration
angular.module("mod-login").config(loginConfig);

loginConfig.$inject = ['$stateProvider'];

function loginConfig($stateProvider) {

    var viewFolder = "";

    $stateProvider.state('login', {
        url: '/login',
        controller: "loginController",
        controllerAs: "vm",
        templateUrl: '/app/modules/login/login.html'
    });
}