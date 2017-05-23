'use strict';

angular.module("soundlink").config(soundlinkConfig);

soundlinkConfig.$inject = ['$stateProvider', '$urlRouterProvider'];

function soundlinkConfig($stateProvider, $urlRouterProvider) {

    var viewFolder = "app/modules/soundlink_ihm";

    $stateProvider.state('soundlink', {
        url: '/soundlink/',
        controller: "soundlinkController",
        controllerAs: "soundlinkCtrl",
        templateUrl: viewFolder + '/soundlink.html',
        redirectTo: 'soundlink.albums'
    });
}