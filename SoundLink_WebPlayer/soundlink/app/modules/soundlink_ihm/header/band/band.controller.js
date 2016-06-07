angular.module('soundlink').controller('bandController', ['eventManager', '$translate', '$timeout', bandController]);

function bandController(eventManager, $translate, $timeout) {
    var controller = this;

    eventManager.subscribeToEvent("bandMessage", function onBandMessage(message) {
        $translate(message).then(function (welcome) {
            showMessage("MSG", welcome);
        });
    });

    eventManager.subscribeToEvent("bandError", function onBandMessage(message) {
        showMessage("ERROR", message);
    });

    controller.showMessage = false;
    var messageType = "";
    controller.message = "";

    function showMessage(type, message) {
        messageType = type;
        controller.message = message;
        controller.showMessage = true;
        $timeout(hideMessage, 3000);
    }

    function hideMessage() {
        controller.showMessage = false;
        controller.message = "";
        messageType = "";
    }

    controller.getMessageTypeColor = function getMessageTypeColor() {
        if (messageType == "ERROR") {
            return { 'background-color': 'firebrick' };
        } else if (messageType == "MSG") {
            return { 'background-color': 'blue' };
        }
    }

    controller.messageClass = function messageClass() {
        if (messageType == "ERROR") {
            return "show errorMessage";
        } else if (messageType == "MSG") {
            return "show simpleMessage";
        } else {
            return "";
        }
    }
}
