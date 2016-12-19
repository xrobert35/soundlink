angular.module("soundlink").config(function ($stateProvider, $urlRouterProvider) {

    var viewFolder = "app/modules/soundlink_ihm";

    $urlRouterProvider.when('/soundlink','soundlink/albums');

    $stateProvider.state('soundlink', {
        url: '/soundlink/',
        controller: "soundlinkController",
        controllerAs: "soundlinkCtrl",
        templateUrl: viewFolder + '/soundlink.html',
        redirectTo : 'soundlink.albums'
    });
});