'use strict';

angular.module("soundlink").config(artistesConfig);

artistesConfig.$inject = ['$stateProvider'];

function artistesConfig($stateProvider) {

    initArtistes.$inject = ['artistesResource', '$location'];
    function initArtistes(artistesResource, $location) {
        var startChain = $location.hash();
        if (startChain == null || startChain == "") {
            startChain = 'A';
        }
        return artistesResource.getArtistesStartWith(startChain).then(function (artistes) {
            return artistes;
        });
    }

    $stateProvider.state('soundlink.search', {
        url: 'search',
        resolve: {
            artistes: initArtistes
        },
        template: '<search-page artistes="$resolve.artistes" layout="column"></search-page>'
    });
}