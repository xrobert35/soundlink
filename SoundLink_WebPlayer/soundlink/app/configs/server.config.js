'use strict';

angular.module("soundlink").constant('config', {
  serveurUrl: "/soundlink_server/",
  wsServeurUrl : "wss://"+ window.location.host + "/soundlink_server/ws/"
});