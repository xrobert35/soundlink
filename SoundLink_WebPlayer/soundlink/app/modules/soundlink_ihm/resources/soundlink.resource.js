'use strict';

angular.module("soundlink").service('soundlinkResource', soundlinkResource);

soundlinkResource.$inject = ['$http', 'config'];

function soundlinkResource($http, config) {

    var controllerUrl = config.serveurUrl + 'soundlink/';

    function getData(result) {
        return result.data;
    }

    this.getUserInformation = function getUserInformation() {
        return $http.get(controllerUrl + 'userInformation').then(getData);
    };

    this.saveUserInformation = function saveUserInformation(userInformation) {
        return $http.post(controllerUrl + 'saveUserInformation', userInformation).then(getData);
    };

    this.getAlbums = function getAlbums() {
        return $http.get(controllerUrl + 'albums').then(getData);
    };

    this.getAlbumsFromArtiste = function getAlbumsFromArtiste(artisteId) {
        return $http.get(controllerUrl + "albumsArtiste", { params: { 'artisteId': artisteId } }).then(getData);
    };

    this.getArtistes = function getArtistes() {
        return $http.get(controllerUrl + 'artistes').then(getData);
    };

    this.getArtistesStartWith = function getArtistesStartWith(startChain) {
        return $http.get(controllerUrl + 'artistesStartWith', { params: { 'startChain': startChain } }).then(getData);
    };

    this.getMusicsFromAlbum = function getMusicsFromAlbum(albumId) {
        return $http.get(controllerUrl + 'albumMusics', { params: { 'albumId': albumId } }).then(getData);
    };

    this.getPlaylistsByUser = function getPlaylistsByUser(userId) {
        return $http.get(controllerUrl + '/playlist/playlistsByUser', { params: { 'userId': userId } }).then(getData);
    };

    this.addMusicToPlaylist = function addMusicToPlaylist(playlistId, userId) {
        return $http.post(controllerUrl + '/playlist/addMusic', { playlistId: playlistId, userId: userId }).then(getData);
    };

    this.removeMusicFromPlaylist = function removeMusicFromPlaylist(playlistId, userId) {
        return $http.post(controllerUrl + '/playlist/removeMusic', { playlistId: playlistId, userId: userId }).then(getData);
    };

    this.createPlaylist = function removeMusicFromPlaylist(playlist) {
        return $http.post(controllerUrl + '/playlist/create', playlist).then(getData);
    };

}