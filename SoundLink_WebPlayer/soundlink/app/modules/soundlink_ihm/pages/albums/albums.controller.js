angular.module('soundlink').controller('albumsController', ['socketService', 'musicService', 'eventManager', albumsController]);

function albumsController(socketService, musicService, eventManager) {
    var controller = this;

    controller.albums = null;
    controller.selectedAlbum = null;
    controller.albumMusics = null;
    controller.albumSongs = null;

    controller.getAlbums = function getAlbums() {
        console.log("changement des albums");
        musicService.getAlbums().then(function (data) {
            controller.albums = data;
        }).catch(function (value) {
            eventManager.fireEvent("bandError", "Une erreur est survenu lors de la recuperation des albums");
        });
    }

    controller.showAlbumMusics = function showAlbumMusics(album) {
        controller.selectedAlbum = album;
        musicService.getMusicsFromAlbum(album.artisteName, album.albumName).then(function (data) {
            controller.albumMusics = data;
            controller.albumSongs = new Array();
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
    controller.getAlbums();
}



angular.module('soundlink').directive("showConfig", function ($compile) {
    return function (scope, element, attrs) {
        var configInclude = "<div><ng-include src=\"'modules/serveurs/applicationConfig.html'\"></div>";
        console.log(configInclude);
        var template = $compile(configInclude)(scope);

        element.bind("click", function ($event) {
            var div = angular.element(element[0]);

            console.log(" current taarget " + div[0].getBoundingClientRect().top);
            var currentTop = div[0].getBoundingClientRect().top;

            var possibleEndElement = div;
            for (var childIndex = 0; childIndex < div.parent()[0].children.length; childIndex++) {
                console.log(" current top  " + div.parent()[0].children[childIndex].getBoundingClientRect().top);
                if (div.parent()[0].children[childIndex].getBoundingClientRect().top > currentTop) {
                    break;
                }
                possibleEndElement = div.parent()[0].children[childIndex];
            }

            console.log(" right end  " + possibleEndElement.getBoundingClientRect().right);
            var templateElement = angular.element(template);
            insertAfter(templateElement[0], possibleEndElement);

            function insertAfter(newNode, referenceNode) {
                referenceNode.parentNode.insertBefore(newNode, referenceNode.nextSibling);
            }
        });
    };
});