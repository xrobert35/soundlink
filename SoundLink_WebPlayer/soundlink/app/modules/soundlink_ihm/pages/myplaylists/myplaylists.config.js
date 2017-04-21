'use strict';

angular.module("soundlink").config(myplaylistsConfig);

myplaylistsConfig.$inject = ['$stateProvider'];

function myplaylistsConfig($stateProvider) {

    initPlaylists.$inject = ['playlistsResource'];
    function initPlaylists(playlistsResource, $location) {
        return playlistsResource.getUserPlaylists().then(function (playlists) {
            return playlists;
        });
    }

    $stateProvider.state('soundlink.myplaylists', {
        url: 'my_playlists',
        resolve: {
            playlists: initPlaylists
        },
        template: '<myplaylists-page playlists="$resolve.playlists" layout="column"></myplaylists-page>'
    });
}