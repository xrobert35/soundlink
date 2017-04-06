'use strict';

angular.module('soundlink').controller('loginController', loginController);

loginController.$inject = ['$state','loginService'];

function loginController($state, loginService) {
    var vm = this;

    vm.login = function login() {
        vm.loginMessage = "Connecting...";
        loginService.login(vm.username, vm.password).then(function (message) {
            vm.loginMessage = "Connection success";
            $state.go('soundlink.artistes');
        }).catch(function (data, status) {
            if (status === -1) {
                vm.loginMessage = 'Communication problem : contact the administrator';
            } else {
                vm.loginMessage = 'Wrong login/password';
            }
        });
    };
}
