angular.module("soundlink").config(function ($stateProvider, $urlRouterProvider) {

    var viewFolder = "app/modules/soundlink_ihm/pages";

    $stateProvider.state('soundlink.artistes', {
        url: 'artistes',
        controller: "artistesController",
        controllerAs: "artistesCtrl",
        templateUrl: viewFolder + '/artistes/artistes.html'
    });
});