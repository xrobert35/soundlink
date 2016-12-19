angular.module("soundlink").config(albumConfig);

albumConfig.$inject = ['$stateProvider'];

function albumConfig($stateProvider) {

    var viewFolder = "app/modules/soundlink_ihm/pages";

    $stateProvider.state('soundlink.albums', {
        url: 'albums',
        controller: "albumsController",
        controllerAs: "vm",
        templateUrl: viewFolder + '/albums/albums.html'
    });
}