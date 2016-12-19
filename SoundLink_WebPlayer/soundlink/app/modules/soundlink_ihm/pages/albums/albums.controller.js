angular.module('soundlink').controller('albumsController',  albumsController);

albumsController.$inject = ['socketService', 'musicService', 'eventManager'];

function albumsController(socketService, musicService, eventManager) {
    var vm = this;

    vm.albums = [];

    vm.getAlbums = function getAlbums() {
        console.log("changement des albums");
        musicService.getAlbums().then(function (albums) {
            vm.albums = albums;
        });
    };

    vm.showAlbumMusics = function showAlbumMusics(album) {
        vm.selectedAlbum = album;
        musicService.getMusicsFromAlbum(album.artisteName, album.albumName).then(function (data) {
            vm.albumMusics = data;
            vm.albumSongs = [];
            angular.forEach(data, function (music, value) {
                var song = new Song();
                song.setId(music.title + key.trackNumber);
                song.setTitle(music.title);
                song.setArtist(music.artist);
                song.setDuration(music.duration)
                song.setTrackNumber(music.trackNumber);
                song.setUrl("/soundlink_server/soundlink/music/" + music.id);
                vm.albumSongs.push(song);
            });
        });
    };


    vm.getAlbums();
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