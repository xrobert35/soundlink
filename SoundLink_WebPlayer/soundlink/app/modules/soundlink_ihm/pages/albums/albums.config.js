angular.module("soundlink").config(function ($stateProvider, $urlRouterProvider) {

    var viewFolder = "app/modules/soundlink_ihm/pages";

    $stateProvider.state('soundlink.albums', {
        url: 'albums',
        controller: "albumsController",
        controllerAs: "albumsCtrl",
        templateUrl: viewFolder + '/albums/albums.html'
    });
});