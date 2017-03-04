'use strict';

angular.module("soundlink").config(artistesConfig);

artistesConfig.$inject = ['$stateProvider'];

function artistesConfig($stateProvider, $urlRouterProvider) {

    initArtistes.$inject = ['soundlinkResource'];

    function initArtistes(soundlinkResource) {
        return soundlinkResource.getArtistes().then(function (artistes) {
            return artistes;
        });
    }

    $stateProvider.state('soundlink.artistes', {
        url: 'artistes',
        resolve: {
            albums: initArtistes
        },
        template: '<artistes-page artistes="$resolve.albums"></artistes-page>'
    });
}