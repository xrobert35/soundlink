'use strict';

angular.module("soundlink").config(myalbumsConfig);

myalbumsConfig.$inject = ['$stateProvider'];

function myalbumsConfig($stateProvider) {

    initAlbums.$inject = ['albumsResource'];
    function initAlbums(albumsResource) {
         return albumsResource.getUserAlbums().then(function (albums) {
             return albums;
        });
    }

    $stateProvider.state('soundlink.myalbums', {
        url: 'my_albums',
        resolve: {
            albums: initAlbums
        },
        template: '<myalbums-page playlists="$resolve.albums" layout="column"></myalbums-page>'
    });
}