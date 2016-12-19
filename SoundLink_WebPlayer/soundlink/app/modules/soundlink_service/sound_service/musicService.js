angular.module("soundlink-data").service('musicService', soundService);

soundService.$inject = ['$http' , 'config'];

function soundService($http, config) {

    var controllerUrl = config.serveurUrl + '/soundlink/';

    this.getAlbums = function getAlbums() {
        return $http.get(controllerUrl + '/albums');
    };

    this.getMusicsFromAlbum = function getMusicsFromAlbum(albumId) {
        return $http.get(controllerUrl + '/albumMusics', { albumId : albumId})
    };
}