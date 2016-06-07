angular.module("soundlink-data").service('musicService', soundService);

function soundService($http, $q) {

    var serverName = "/SoundLink_Server/soundlink/";

    this.getAlbums = function getAlbums() {
        var deferred = $q.defer();

        var requete = {
            url: serverName + "albums",
            method: 'GET'
        }
        $http(requete).success(function (data, status, headers, config) {
            deferred.resolve(data);
        }).error(function () {
            deferred.reject("Error");
        });

        return deferred.promise;
    }

    this.getMusicsFromAlbum = function getMusicsFromAlbum(artisteName, albumName) {
        var deferred = $q.defer();

        var requete = {
            url: serverName + "albumMusics",
            method: 'GET',
            params: { "artisteName": artisteName, "albumName": albumName }
        }
        $http(requete).success(function (data, status, headers, config) {
            deferred.resolve(data);
        }).error(function () {
            deferred.reject("Error");
        });

        return deferred.promise;
    }


}