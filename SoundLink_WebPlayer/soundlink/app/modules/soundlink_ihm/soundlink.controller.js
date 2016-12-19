'use strict';

angular.module('soundlink').controller('soundlinkController', ['socketService', 'musicService', 'eventManager', soundLinkController]);

function soundLinkController(socketService, musicService, eventManager) {
    var controller = this;

    var json = " ";
    var connected = false;
    var pair = true;

    controller.albums = null;
    controller.selectedAlbum = null;
    controller.albumMusics = null;
    controller.albumSongs = null;

    controller.getAlbums = function getAlbums() {
        musicService.getAlbums().then(function (data) {
            controller.albums = data;
        }).catch(function (value) {
            eventManager.fireEvent("bandError", "Une erreur est survenu lors de la recuperation des albums");
        });
    }

    controller.showAlbumMusics = function showAlbumMusics(album) {
        pair = true;
        controller.selectedAlbum = album;
        musicService.getMusicsFromAlbum(album.artisteName, album.albumName).then(function (data) {
            controller.albumMusics = data;
            controller.albumSongs = [];
            angular.forEach(data, function (key, value) {
                var song = new Song();
                song.setId(key.title + key.trackNumber);
                song.setTitle(key.title);
                song.setArtist(key.artist);
                song.setDuration(key.duration)
                song.setTrackNumber(key.trackNumber);
                song.setUrl("/SoundLink_Server/soundlink/music/" + key.artist + "/" + key.album + "/" + key.title);
                controller.albumSongs.push(song);
            });
        }).catch(function (value) {
            eventManager.fireEvent("bandError", "Une erreur est survenu lors de la recupation des musics pour l'album" + album.albumName);
        });
    }

    controller.event = function event() {
        eventManager.fireEvent("bandMessage", "band.message.welcome");
    }

    controller.event();
    controller.getAlbums();

    controller.send = function () {
        socketService.sendMessage("/app/jack", this.json);
    }

    controller.connect = function () {
        socketService.connect().then(function (value) {
            console.log("Connected To " + value)
            controller.connected = true;
        }).catch(function (value) {
            console.log("errorr " + value);
        });
    }

    controller.subscribe = function () {
        socketService.subscribe("/player", function (msg) {
            console.log(msg)
        });
    }

    controller.unsubscribe = function () {
        socketService.unsubscribe("/player");
    }

    controller.disconnect = function () {
        this.connected = false;
        socketService.disconnect();
    }


    socketService.onConnected(function (info) {
        console.log("Connection to : " + info);
    });

    socketService.onDisconnected(function (info) {
        console.log("Disconnected from : " + info);
    });

    controller.isPair = function isPair() {
        console.log("pair");
        return 'pair';
    }
}
