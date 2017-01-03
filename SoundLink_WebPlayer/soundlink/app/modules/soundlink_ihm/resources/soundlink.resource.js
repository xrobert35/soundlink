'use strict';

angular.module("soundlink").service('soundlinkResource', soundlinkResource);

soundlinkResource.$inject = ['$http' , 'config'];

function soundlinkResource($http, config) {

    var controllerUrl = config.serveurUrl + 'soundlink/';

    function getData(result){
        return result.data;
    }

    this.getUserInformation = function getUserInformation(){
      return $http.get(controllerUrl + 'userInformation').then(getData);
    };

    this.saveUserInformation = function saveUserInformation(userInformation){
        return $http.post(controllerUrl + 'saveUserInformation', userInformation).then(getData);
    };

    this.getAlbums = function getAlbums() {
        return $http.get(controllerUrl + 'albums').then(getData);
    };

    this.getMusicsFromAlbum = function getMusicsFromAlbum(albumId) {
        return $http.get(controllerUrl + 'albumMusics', { params :{ 'albumId' : albumId}}).then(getData);
    };
}