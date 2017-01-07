'use strict';

angular.module("soundlink").config(albumConfig);

albumConfig.$inject = ['$stateProvider'];

function albumConfig($stateProvider) {

  initAlbums.$inject = ['soundlinkResource'];
  
  function initAlbums(soundlinkResource) {
    return soundlinkResource.getAlbums().then(function (albums) {
      return albums;
    });
  }

  $stateProvider.state('soundlink.albums', {
    url: 'albums',
    resolve: {
      albums: initAlbums
    },
    template: '<albums-page albums="$resolve.albums"></albums-page>'
  });

}