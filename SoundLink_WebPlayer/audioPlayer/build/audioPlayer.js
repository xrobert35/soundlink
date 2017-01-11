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

addMusicDirective.$inject = ['audioPlayer', 'eventManager'];

function addMusicDirective(audioPlayer, eventManager) {
  return {
    restrict: "A",
    scope: {
      song: '=addMusic'
    },
    link: function (scope, element, attrs) {
      element.bind('click', function (event) {
        audioPlayer.add(scope.song);
        eventManager.fireEvent("playlistOpen");
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
  var volume = 0;
  var currentSong;
  var isPlaying = false;
  var loadingPercent = 0;
  var playlist = [];

  var contenu = {};

  contenu.isPlaying = function () {
    return isPlaying;
  };

  contenu.setPlaying = function (bPlaying) {
    isPlaying = bPlaying;
  };

  contenu.setVolume = function (pVolume){
    volume = pVolume;
  };

  contenu.getVolume = function (){
    return volume;
  };

  contenu.setDuration = function (pDuration) {
    duration = pDuration;
  };

  contenu.getDuration = function () {
    return duration;
  };

  contenu.setProgress = function (pProgress) {
    progress = pProgress;
  };

  contenu.getProgress = function () {
    return progress;
  };

  contenu.setCurrentSong = function (pCurrentSong) {
    currentSong = pCurrentSong;
  };

  contenu.getCurrentSong = function () {
    return currentSong;
  };

  contenu.setPlaylist = function (pPlaylist) {
    playlist = pPlaylist;
  };

  contenu.getPlaylist = function () {
    return playlist;
  };

  contenu.setLoadingPercent = function (percent) {
    loadingPercent = percent;
  };

  contenu.getLoadingPercent = function () {
    return loadingPercent;
  };

  return contenu;

}
}());


(function() {
'use strict';

var AV = require('av');

angular.module('soundlink').service("audioPlayer", audioPlayer);

audioPlayer.$inject = ['audioStatus', '$q'];

function audioPlayer(audioStatus, $q) {

  var audioPlayer = this;
  var player;

  audioPlayer.setVolume = function (volume) {
    audioStatus.setVolume(volume);
    if (player != null) {
      player.volume = volume;
    }
  };

  audioPlayer.add = function (song) {
    if (audioStatus.getPlaylist().indexOf(song) == -1) {
      audioStatus.getPlaylist().push(song);
    }
  };

  audioPlayer.play = function (song) {
    var currentSong = audioStatus.getCurrentSong();
    if (song != null && (currentSong == null || song.id != currentSong.id)) {
      if (player != null) {
        player.stop();
      }
      playSong(song);
    } else if (player != null && currentSong != null) {
      player.play();
      audioStatus.setPlaying(true);
    } else if (audioStatus.getPlaylist().length > 0) {
      playSong(audioStatus.getPlaylist()[0]);
    }
  };

  function playSong(song) {
    $q.when(song).then(function () {
      audioStatus.setCurrentSong(song);
      audioStatus.setPlaying(true);
    });
    player = AV.Player.fromURL(song.url);
    managePlayerEvent();
    player.play();
  }

  audioPlayer.pause = function () {
    if (player != null) {
      player.pause();
      audioStatus.setPlaying(false);
    }
  };

  audioPlayer.continue = function () {
    if (player != null) {
      player.play();
      audioStatus.setPlaying(true);
    }
  };

  audioPlayer.repeat = function () {

  };

  audioPlayer.random = function () {

  };

  audioPlayer.previous = function () {

  };

  audioPlayer.next = function () {
    var currentSong = audioStatus.getCurrentSong();
    var playlist = audioStatus.getPlaylist();
    if (currentSong != null) {
      var nextSongIndex = playlist.indexOf(currentSong) + 1;
      if (playlist.length > nextSongIndex) {
        var song = playlist[nextSongIndex];
        audioPlayer.play(song);
      }
    }
  };

  function managePlayerEvent() {
    player.volume = audioStatus.getVolume();
    player.on('ready', function () {

    });

    player.on('end', function () {
      audioPlayer.next();
    });

    player.on('error', function (err) {
      console.log(err);
    });

    player.on('progress', function (progress) {
      $q.when(progress).then(function () {
        audioStatus.setProgress((progress / audioStatus.getDuration()) * 100);
      });
    });
    player.on('duration', function (pDuration) {
      $q.when(pDuration).then(function () {
        audioStatus.setDuration(pDuration);
      });
    });

    player.on('buffer', function (percent) {
      $q.when(percent).then(function () {
        audioStatus.setLoadingPercent(percent);
      });
    });
  }
}
}());


//# sourceMappingURL=audioPlayer.js.map