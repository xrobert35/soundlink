'use strict';

angular.module("soundlink").config(myartistesConfig);

myartistesConfig.$inject = ['$stateProvider'];

function myartistesConfig($stateProvider) {

    initArtistes.$inject = ['artistesResource'];
    function initArtistes(artistesResource, $location) {
        return artistesResource.getUserArtistes().then(function (artistes) {
            return artistes;
        });
    }

    $stateProvider.state('soundlink.myartistes', {
        url: 'my_artistes',
        resolve: {
            artistes: initArtistes
        },
        template: '<myartistes-page artistes="$resolve.artistes" layout="column"></myartistes-page>'
    });
}