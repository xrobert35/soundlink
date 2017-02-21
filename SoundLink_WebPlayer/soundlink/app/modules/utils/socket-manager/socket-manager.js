angular.module('soundlink').service("socketService", socketService);

socketService.$inject = ['SOCKET_EVENTS', 'config', '$q', '$cookies', 'tokenStorage'];

function socketService(SOCKET_EVENTS, config, $q, $cookies, tokenStorage) {

    var serverUrl = config.serveurUrl;
    var socketService = this;
    //Global service var
    var stompClient = null;

    var onConnectCallBakcs = [];
    var onDisconnectCallBacks = [];

    var subscribedServices = {};

    var isConnected = false;

    /**
     * Connection function
     */
    socketService.connect = function connect() {
        var deferred = $q.defer();
        if (serverUrl === null) {
            deferred.reject("Server url is undefined, please configure serverUrl thank to the provider");
        } else {
            var socket = new SockJS(serverUrl + 'hello');
            $cookies.put('X-AUTH-TOKEN-COOK', tokenStorage.retrieve());
            stompClient = Stomp.over(socket);
            stompClient.connect({}, function (frame) {
                deferred.resolve(frame);
                isConnected = true;
                notifyCallBacks(SOCKET_EVENTS.CONNECTED, serverUrl);
            }, function (message) {
                //If we have been connected one it mean "disconnection" else it's just a fail
                if (!isConnected) {
                    deferred.reject("Impossible to connect to : " + serverUrl);
                } else {
                    console.log("Deconnected ! " + message);
                    notifyCallBacks(SOCKET_EVENTS.DISCONNECTED, serverUrl);
                }
            });

            stompClient.onclose = reconnect;
            // delete $cookies['X-AUTH-TOKEN'];
        }
        return deferred.promise;
    };

    var reconnect = function() {
        console.log("RECONNECT");
        service.connected = false;
        $timeout(function() {
            connect();
        }, 15000);
    };

    /**
     * Disconnection function
     */
    socketService.disconnect = function disconnect() {
        var deferred = $q.defer();
        if (stompClient !== null) {
            stompClient.disconnect(function () {
                deferred.resolve(serverUrl);
                subscribedServices = {};
                notifyCallBacks(SOCKET_EVENTS.DISCONNECTED, serverUrl);
            });
        } else {
            deferred.reject(serverUrl);
        }
        return deferred.promise;
    };

    /**
     * Subscribing function
     */
    socketService.subscribe = function subscribe(service, listener) {
        if (stompClient !== null) {
            if (!subscribedServices[service]) {
                var subscribeInfo = stompClient.subscribe(service, function (info) {
                    listener.call(info);
                });
                subscribedServices[service] = subscribeInfo;
            }
        }
    };

    /**
     * Unsubscribing function
     */
    socketService.unsubscribe = function unsubscribe(service) {
        if (subscribedServices[service]) {
            subscribedServices[service].unsubscribe();
            delete subscribedServices[service];
        }
    };

    /**
     * Send message function
     */
    socketService.sendMessage = function (service, jsonstr) {
        stompClient.send(service, {}, jsonstr);
    };

    /**
     * Allow you to had a connection listener
     */
    socketService.onConnected = function (callback) {
        onConnectCallBakcs.push(callback);
    };

    /**
     * Allow you to had a disconnection listener
     */
    socketService.onDisconnected = function (callback) {
        onDisconnectCallBacks.push(callback);
    };

    function notifyCallBacks(event, info) {
        var callbacksToNotify = null;
        switch (event) {
            case SOCKET_EVENTS.CONNECTED:
                callbacksToNotify = onConnectCallBakcs;
                break;
            case SOCKET_EVENTS.DISCONNECTED:
                callbacksToNotify = onDisconnectCallBacks;
                break;
        }
        angular.forEach(callbacksToNotify, function (value, key) {
            value(info);
        });
    }

    return socketService;
}


var socketsEvents = {
    "CONNECTED": "connected",
    "DISCONNECTED": "disconnected",
    "SUBSCRIBED": "subcribe",
    "UNSUBSCRIBED": "unsubcribed"
};

angular.module('soundlink').constant("SOCKET_EVENTS", socketsEvents);

