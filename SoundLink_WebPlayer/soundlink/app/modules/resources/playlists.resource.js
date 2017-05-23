'use strict';

angular.module("soundlink").service('playlistsResource', playlistsResource);

playlistsResource.$inject = ['$http', 'config'];

function playlistsResource($http, config) {

    var controllerUrl = config.serveurUrl + 'soundlink/playlist/';

    function getData(result) {
        return result.data;
    }

    this.createPlaylist = function (playlist) {
        return $http.post(controllerUrl + 'create', playlist).then(getData);
    };

    this.deletePlaylist = function (playlistId){
        return $http.delete(controllerUrl + 'delete',  { params: { 'playlistId': playlistId }} ).then(getData);
    };

    this.getPlaylistsByUser = function (userId) {
        return $http.get(controllerUrl + 'playlistsByUser', { params: { 'userId': userId } }).then(getData);
    };

    this.getUserPlaylists = function () {
        return $http.get(controllerUrl + 'userPlaylists').then(getData);
    };

    this.addMusic = function (playlistId, musicId) {
        return $http.post(controllerUrl + 'addMusic', { playlistId: playlistId, musicId: musicId }).then(getData);
    };

    this.removeMusic = function (playlistId, musicId) {
        return $http.post(controllerUrl + 'removeMusic', { playlistId: playlistId, musicId: musicId }).then(getData);
    };
}