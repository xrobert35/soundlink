angular.module('socketmanager', []);

angular.module('socketmanager').provider("socketService", SocketProvider);

function SocketProvider(SOCKET_EVENTS) {

    this.serverUrl = null;

    this.$get = function ($q) {
        // Var to make clear that we can have provider args here
        var providerArgs = this;

        //Our service
        var socketService = {};

        //Global service var
        var stompClient = null;

        var onConnectCallBakcs = new Array();
        var onDisconnectCallBacks = new Array();

        var subscribedServices = {};

        var isConnected = false;

        /**
         * Connection function
         */
        socketService.connect = function connect() {


            var deferred = $q.defer();
            if (providerArgs.serverUrl == null) {
                deferred.reject("Server url is undefined, please configure serverUrl thank to the provider");
            } else {
                var socket = new SockJS(providerArgs.serverUrl);
                stompClient = Stomp.over(socket);
                stompClient.connect({}, function (frame) {
                    deferred.resolve(frame);
                    isConnected = true;
                    notifyCallBacks(SOCKET_EVENTS.CONNECTED, providerArgs.serverUrl);
                }, function (message) {
                    //If we have been connected one it mean "disconnection" else it's just a fail
                    if (!isConnected) {
                        deferred.reject("Impossible to connect to : " + providerArgs.serverUrl)
                    } else {
                        console.log("Deconnected ! " + message);
                        notifyCallBacks(SOCKET_EVENTS.DISCONNECTED, providerArgs.serverUrl);

                    }

                });
            }
            return deferred.promise;
        }

        /**
         * Disconnection function
         */
        socketService.disconnect = function disconnect() {
            var deferred = $q.defer();
            if (stompClient != null) {
                stompClient.disconnect(function () {
                    deferred.resolve(providerArgs.serverUrl);
                    subscribedServices = {};
                    notifyCallBacks(SOCKET_EVENTS.DISCONNECTED, providerArgs.serverUrl);
                });
            } else {
                deferred.reject(providerArgs.serverUrl);
            }
            return deferred.promise;
        }

        /**
         * Subscribing function
         */
        socketService.subscribe = function subscribe(service, listener) {
            if (stompClient != null) {
                if (!subscribedServices[service]) {
                    var subscribeInfo = stompClient.subscribe(service, function (info) {
                        listener.call(info);
                    });
                    subscribedServices[service] = subscribeInfo;
                }
            }
        }

        /**
         * Unsubscribing function
         */
        socketService.unsubscribe = function unsubscribe(service) {
            if (subscribedServices[service]) {
                subscribedServices[service].unsubscribe();
                delete subscribedServices[service];
            }
        }

        /**
         * Send message function
         */
        socketService.sendMessage = function (service, jsonstr) {
            stompClient.send(service, {}, jsonstr);
        }

        /**
         * Allow you to had a connection listener
         */
        socketService.onConnected = function (callback) {
            onConnectCallBakcs.push(callback);
        }

        /**
         * Allow you to had a disconnection listener
         */
        socketService.onDisconnected = function (callback) {
            onDisconnectCallBacks.push(callback);
        }

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
}

angular.module('socketmanager').constant("SOCKET_EVENTS", socketsEvents);

var socketsEvents = {
    "CONNECTED": "connected",
    "DISCONNECTED": "disconnected",
    "SUBSCRIBED": "subcribe",
    "UNSUBSCRIBED": "unsubcribed"
}