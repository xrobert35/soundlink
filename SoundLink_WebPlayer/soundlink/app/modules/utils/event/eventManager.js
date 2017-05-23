'use strict';

angular.module("soundlink").service('eventManager', eventManager);

eventManager.$inject = ['$q'];

function eventManager($q) {

    var subscribers = {};

    this.subscribeToEvent = function subscribeToEvent(event, listener) {
        if (!subscribers[event]) {
            subscribers[event] = [];
        }
        subscribers[event].push(listener);
    };

    this.fireEvent = function (event, info) {
        if (subscribers[event]) {
            angular.forEach(subscribers[event], function (listener, index) {
                listener(info);
            });
        }
    };
}
