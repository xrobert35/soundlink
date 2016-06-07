angular.module('mod-login').controller('loginController', ['loginService', 'eventManager', loginController]);

function loginController(loginService, eventManager) {
    var controller = this;

    controller.username = "";
    controller.password = "";
    controller.loginMessage = "";

    controller.login = function login() {
        controller.loginMessage = "Connecting...";
        loginService.login(controller.username, controller.password).then(function (message) {
            controller.loginMessage = "Connection success"
        }).catch(function (message) {
            console.log(message);
            controller.loginMessage = message;
        });
    };
}
