angular.module("mod-login", ['ngCookies']);

//Route configuration
angular.module("mod-login").config(function ($stateProvider, $urlRouterProvider) {
    
    var viewFolder = "app/modules";
    
    $stateProvider.state('login', {
        url: '/login',
        controller: "loginController",
        controllerAs: "loginCtrl",
        templateUrl: viewFolder + '/login/login.html'
    });
});