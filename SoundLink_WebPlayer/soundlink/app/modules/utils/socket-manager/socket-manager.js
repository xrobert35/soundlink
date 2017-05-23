angular.module('soundlink').service("socketManager", socketManager);

socketManager.$inject = ['$q', '$websocket', '$cookies', 'tokenStorage', 'config', 'techResource'];

function socketManager($q, $websocket, $cookies, tokenStorage, config, techResource) {

  this.openSocket = function (path) {
    return techResource.getWsToken().then(function (token) {
      var httpParam = getHttpParam({'X-AUTH-TOKEN' : token});
      return  $websocket(config.wsServeurUrl + path + httpParam);
    });
  };

  function getHttpParam(param) {
    var httpParam = '?';
    var isFirst = true;
    for (var key in param) {
      if (param.hasOwnProperty(key)) {
        if (!isFirst) {
          httpParam += '&';
        } else {
          isFirst = false;
        }
        httpParam += key + '=' + param[key];
      }
    }
    return httpParam;
  }
}
