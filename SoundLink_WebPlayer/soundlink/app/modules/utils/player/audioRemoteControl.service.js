
'use strict';

angular.module('soundlink').service("audioRemoteControl", audioRemoteControlService);

audioRemoteControlService.$inject = ['socketManager', '$q', 'audioPlayer'];

function audioRemoteControlService(socketManager, $q, audioPlayer) {

  var remoteControlStream;

  var actionFunction = {
    PLAY: audioPlayer.play,
    PAUSE: audioPlayer.pause,
    NEXT_SONG: audioPlayer.next,
    PREVIOUS_SONG: audioPlayer.previous,
    SET_VOLUME: audioPlayer.setVolume
  };

  this.activeRemoteControl = function () {
    var deferred = $q.defer();
    if (remoteControlStream == null) {
      socketManager.openSocket('remotecontrol').then(function (controlStream) {
        remoteControlStream = controlStream;
        remoteControlStream.onOpen(function () {
          remoteControlStream.send(JSON.stringify({ action: 'REGISTER_LISTENER' }));
          deferred.resolve();
        });
        remoteControlStream.onClose(function () {
          remoteControlStream = null;
          console.log("Remote websocket close");
        });
        remoteControlStream.onMessage(function (wsMessage) {
          var message = JSON.parse(wsMessage.data);
          if (message.type == 'INFO') {
            var actionAndParam = message.code.split(":");
            var act = actionFunction[actionAndParam[0]];
            if (act != null) {
              if (actionAndParam.length > 1) {
                act(actionAndParam[1]);
              } else {
                act();
              }
            }
          }
        });
      });
    } else {
      remoteControlStream.send(JSON.stringify({ action: 'REGISTER_LISTENER' }));
      deferred.resolve();
    }
    return deferred.promise;
  };

  this.desactiveRemoteControl = function () {
    if (remoteControlStream != null) {
      remoteControlStream.send(JSON.stringify({ action: 'DEREGISTER_LISTENER' }));
    }
    return $q.when(true);
  };

  this.remote = function (action) {
    var deferred = $q.defer();
    if (remoteControlStream == null) {
      remoteControlStream = socketManager.openSocket('remotecontrol');
      remoteControlStream.onOpen(function () {
        remoteControlStream.send(JSON.stringify({ action: action }));
        deferred.resolve();
      });
    } else {
      remoteControlStream.send(JSON.stringify({ action: action }));
      deferred.resolve();
    }
    return deferred.promise;
  };

}