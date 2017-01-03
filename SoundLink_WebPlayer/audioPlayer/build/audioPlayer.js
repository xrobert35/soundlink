(function() {
'use strict';

angular.module("soundlink").directive('repeatMusic', repeatMusicDirective);

repeatMusicDirective.$inject = ['audioPlayer'];

function repeatMusicDirective(audioPlayer) {
  return {
    restrict: "A",
    link: function (scope, element, attrs) {
      element.bind('click', function (event) {
        audioPlayer.repeat();
      });
    }
  };
}
}());


(function() {
'use strict';

angular.module("soundlink").directive('removeMusic', removeMusicDirective);

removeMusicDirective.$inject = ['audioPlayer'];

function removeMusicDirective(audioPlayer) {
  return {
    restrict: "A",
    scope: {
      song: '=removeMusic'
    },
    link: function (scope, element, attrs) {
      element.bind('click', function (event) {
        audioPlayer.remove(scope.song);
      });
    }
  };
}
}());


(function() {
'use strict';

angular.module("soundlink").directive('randomMusic', randomMusicDirective);

randomMusicDirective.$inject = ['audioPlayer'];

function randomMusicDirective(audioPlayer) {
  return {
    restrict: "A",
    link: function (scope, element, attrs) {
      element.bind('click', function (event) {
        audioPlayer.random();
      });
    }
  };
}
}());


(function() {
'use strict';

angular.module("soundlink").directive('previousMusic', previousMusicDirective);

previousMusicDirective.$inject = ['audioPlayer'];

function previousMusicDirective(audioPlayer) {
    return {
        restrict: "A",
        link: function (scope, element, attrs) {
            element.bind('click', function (event) {
                audioPlayer.previous();
            });
        }
    };
}
}());


(function() {
'use strict';

angular.module("soundlink").directive('playMusic', playMusicDirective);

playMusicDirective.$inject = ['audioPlayer'];

function playMusicDirective(audioPlayer) {
  return {
    restrict: "A",
    scope: {
      song: '=playMusic'
    },
    link: function (scope, element, attrs) {
      element.bind('click', function (event) {
        if (scope.song !== null) {
          audioPlayer.play(scope.song);
        }else{
          audioPlayer.play();
        }
      });
    }
  };
}
}());


(function() {
'use strict';

angular.module("soundlink").directive('pauseMusic', pauseMusicDirective);

pauseMusicDirective.$inject = ['audioPlayer'];

function pauseMusicDirective(audioPlayer) {
    return {
        restrict: "A",
        link: function (scope, element, attrs) {
            element.bind('click', function (event) {
                audioPlayer.pause();
            });
        }
    };
}
}());


(function() {
'use strict';

angular.module("soundlink").directive('nextMusic', nextMusicDirective);

nextMusicDirective.$inject = ['audioPlayer'];

function nextMusicDirective(audioPlayer) {
  return {
    restrict: "A",
    link: function (scope, element, attrs) {
      element.bind('click', function (event) {
        audioPlayer.next(scope.song);
      });
    }
  };
}
}());


(function() {
'use strict';

angular.module("soundlink").directive('addMusic', addMusicDirective);

addMusicDirective.$inject = ['audioPlayer'];

function addMusicDirective(audioPlayer) {
  return {
    restrict: "A",
    scope: {
      song: '=addMusic'
    },
    link: function (scope, element, attrs) {
      element.bind('click', function (event) {
        audioPlayer.add(scope.song);
      });
    }
  };
}
}());


(function() {
'use strict';

angular.module('soundlink').factory("audioStatus", audioStatus);

audioStatus.$inject = [];

function audioStatus() {

  var progress = 0;
  var duration = 0;

  var contenu = {};

  contenu.setProgress = function(pProgress){
    progress = pProgress;
  };

  contenu.getProgress = function(){
    return progress;
  };

  return contenu;

}
}());


(function() {
'use strict';

var AV = require('av');
require('flac.js');

angular.module('soundlink').service("audioPlayer", audioPlayer);

audioPlayer.$inject = ['audioStatus', '$q'];

function audioPlayer(audioStatus, $q) {

  var audioPlayer = this;

  var player;
  var playlist = [];
  var currentSong;
  var duration;
  
  audioPlayer.getCurrentSong = function () {
    return currentSong;
  };

  audioPlayer.isPlaying = function () {
    return player != null && player.playing;
  };

  audioPlayer.getPlaylist = function () {
    return playlist;
  };

  audioPlayer.add = function (song) {
    playlist.push(song);
  };

  audioPlayer.play = function (song) {
    if (song != null && (currentSong == null || song.id != currentSong.id)) {
      if (player != null) {
        player.stop();
      }
      player = AV.Player.fromURL(song.url);
      currentSong = song;
      managePlayerEvent();
      player.play();
    } else if (player != null && currentSong != null) {
      player.play();
    } else if (playlist.length > 0) {
      currentSong = playlist[0];
      player = AV.Player.fromURL(currentSong.url);
      managePlayerEvent();
      player.play();
    }
  };

  audioPlayer.pause = function () {
    if (player != null) {
      player.pause();
    }
  };

  audioPlayer.continue = function () {
    if (player != null) {
      player.play();
    }
  };

  audioPlayer.repeat = function () {

  };

  audioPlayer.random = function () {

  };

  audioPlayer.previous = function () {

  };

  audioPlayer.next = function () {
    if (currentSong != null) {
      var nextSongIndex = playlist.indexOf(currentSong) + 1;
      if (playlist.length > nextSongIndex) {
        var song = playlist[playlist.indexOf(currentSong) + 1];
        audioPlayer.play(song);
      }
    }
  };

  function managePlayerEvent() {
    player.on('ready', function () {
      // fireEvent('ready');
    });
    player.on('end', function () {
      // fireEvent('end');
      audioPlayer.next();
    });
    player.on('progress', function (progress) {
      $q.when(progress).then(function(){
        audioStatus.setProgress((progress/duration) * 100);
      });
    });
    player.on('duration', function (pDuration) {
      duration = pDuration;
    });
    player.on('buffer', function (percent) {
      // fireEvent('buffer', percent);
    });
  }
}
}());


//# sourceMappingURL=audioPlayer.js.map