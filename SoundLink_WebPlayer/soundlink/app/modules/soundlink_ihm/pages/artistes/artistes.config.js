'use strict';

angular.module("soundlink").config(artistesConfig);

artistesConfig.$inject = ['$stateProvider'];

function artistesConfig($stateProvider) {

    initArtistes.$inject = ['soundlinkResource', '$location'];
    function initArtistes(soundlinkResource, $location) {
        var startChain = $location.hash();
        if (startChain == null || startChain == "") {
            startChain = 'A';
        }
        return soundlinkResource.getArtistesStartWith(startChain).then(function (artistes) {
            return artistes;
        });
    }

    $stateProvider.state('soundlink.artistes', {
        url: 'artistes',
        resolve: {
            artistes: initArtistes
        },
        template: '<artistes-page artistes="$resolve.artistes"></artistes-page>'
    });
}