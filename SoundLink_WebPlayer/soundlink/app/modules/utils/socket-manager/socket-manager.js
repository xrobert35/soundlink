angular.module('soundlink').service("socketManager", socketManager);

socketManager.$inject = ['$q', '$websocket', '$cookies', 'tokenStorage', 'config'];

function socketManager($q, $websocket, $cookies, tokenStorage, config) {

  this.openSocket = function (path) {
    $cookies.put('X-AUTH-TOKEN', tokenStorage.retrieve());
    var socket = $websocket(config.wsServeurUrl + path);

    socket.onOpen(function () {
      $cookies.remove("X-AUTH-TOKEN");
    });

    return socket;
  };
}
