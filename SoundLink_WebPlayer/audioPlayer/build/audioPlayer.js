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

angular.module("soundlink").directive('positionControle', nextMusicDirective);

nextMusicDirective.$inject = ['audioPlayer'];

function nextMusicDirective(audioPlayer) {
  return {
    restrict: "A",
    link: function (scope, element, attrs) {

      function getXOffset(event) {
        var x = 0, element = event.target;
        while (element && !isNaN(element.offsetLeft) && !isNaN(element.offsetTop)) {
          x += element.offsetLeft - element.scrollLeft;
          element = element.offsetParent;
        }
        return event.clientX - x;
      }

      element.bind('click', function (event) {
          var mouseX =  getXOffset(event);
          var clientWidth = event.target.clientWidth;
          var position = (mouseX / clientWidth) * 100;
          audioPlayer.setAudioPosition(position);
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
    restrict: "EA",
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

angular.module("soundlink").directive('addPlayMusic', addPlayMusicDirective);

addPlayMusicDirective.$inject = ['audioPlayer'];

function addPlayMusicDirective(audioPlayer) {
  return {
    restrict: "EA",
    scope: {
      song: '=addPlayMusic'
    },
    link: function (scope, element, attrs) {
      element.bind('click', function (event) {
          audioPlayer.add(scope.song);
          audioPlayer.play(scope.song);
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
    restrict: "EA",
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
  var playing = false;
  var repeating = false;

  var loadingPercent = 0;
  var playlist = [];

  var contenu = {};

  contenu.isPlaying = function () {
    return playing;
  };

  contenu.setPlaying = function (pPlaying) {
    playing = pPlaying;
  };

  contenu.isRepeating = function (){
    return repeating;
  };

  contenu.setRepeating = function (pRepeating){
    repeating = pRepeating;
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

angular.module('soundlink').service("audioPlayer", audioPlayer);

audioPlayer.$inject = ['audioStatus', '$q', '$cookies', 'tokenStorage', 'config', '$timeout', "$rootScope"];

function audioPlayer(audioStatus, $q, $cookies, tokenStorage, config, $timeout, $rootScope) {

  var audioPlayer = this;
  var player;

  audioPlayer.setVolume = function (volume) {
    audioStatus.setVolume(volume);
    if (player != null) {
      player.volume = volume;
    }
  };

  audioPlayer.setAudioPosition = function (positionInPercent) {
    var time = (audioStatus.getDuration() * positionInPercent) / 100;
    if (player != null) {
      player.seek(time);
    }
  };

  audioPlayer.add = function (song) {
    if (audioStatus.getPlaylist().indexOf(song) == -1) {
      audioStatus.getPlaylist().push(song);
    }
  };

  audioPlayer.play = function (song) {
    var currentSong = audioStatus.getCurrentSong();
    if (song != null) {
      if(currentSong != null && song.id == currentSong.id){
        player.play();
        audioPlayer.setAudioPosition(0);
      }else {
        if (player != null) {
          player.stop();
        }
        playSong(song);
      }
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
      audioStatus.setProgress(0);
    });
    // player = AV.Player.fromURL(song.url);
    $cookies.put('X-AUTH-TOKEN', tokenStorage.retrieve(), { path: '/soundlink_server', domain: config.wsDomain });
    player = AV.Player.fromWebSocket(config.wsServeurUrl + "ws/music", song.id);

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
    audioStatus.setRepeating(!audioStatus.isRepeating());
  };

  audioPlayer.random = function () {

  };

  audioPlayer.previous = function () {
    var currentSong = audioStatus.getCurrentSong();
    if (currentSong != null) {
      var playlist = audioStatus.getPlaylist();
      var nextSongIndex = playlist.indexOf(currentSong) -1;
      if (nextSongIndex >= 0 ) {
        var song = playlist[nextSongIndex];
        audioPlayer.play(song);
      }
    }
  };

  audioPlayer.next = function () {
    var currentSong = audioStatus.getCurrentSong();
    if (currentSong != null) {
      var playlist = audioStatus.getPlaylist();
      var nextSongIndex = playlist.indexOf(currentSong) + 1;
      if (playlist.length > nextSongIndex) {
        audioPlayer.play(playlist[nextSongIndex]);
      }else if(audioStatus.isRepeating()){
        audioPlayer.play(playlist[0]);
      }
    }
  };

  function managePlayerEvent() {
    player.volume = audioStatus.getVolume();
    player.on('ready', function () {
      player.play();
    });

    player.on('end', function () {
      audioStatus.setProgress(100);
      $rootScope.$apply();
      audioPlayer.next();
    });
    player.on('error', function (err, code, toto) {
      if (err.message === 'Invalid sync code') {
        //probleme de desynchro à la fin de certain fichier flac
        player.stop();
        audioStatus.setProgress(100);
        $rootScope.$apply();
        audioPlayer.next();
      }
      console.log(err + ' ' + audioStatus.getCurrentSong().title);
    });
    player.on('progress', function (progress) {
      audioStatus.setProgress((progress / audioStatus.getDuration()) * 100);
      $rootScope.$apply();
    });
    player.on('duration', function (pDuration) {
      audioStatus.setDuration(pDuration);
      $rootScope.$apply();
    });
    player.on('buffer', function (percent) {
      audioStatus.setLoadingPercent(percent);
      $rootScope.$apply();
    });
  }
}
}());


//# sourceMappingURL=audioPlayer.js.map