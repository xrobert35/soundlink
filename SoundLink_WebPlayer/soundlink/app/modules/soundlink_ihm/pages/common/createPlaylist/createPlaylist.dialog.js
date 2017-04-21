angular.module('soundlink').service("createPlaylistDialog", createPlaylistDialog);

createPlaylistDialog.$inject= ['$mdDialog'];

function createPlaylistDialog($mdDialog){
    this.open = function(){
        return $mdDialog.show({
            template : '<md-dialog><create-playlist></create-playlist></md-dialog>'
        });
    };
}