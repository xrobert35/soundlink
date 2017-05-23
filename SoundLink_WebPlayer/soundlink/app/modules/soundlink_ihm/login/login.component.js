'use strict';


angular.module('soundlink').component('loginPage', {
    templateUrl: 'app/modules/soundlink_ihm/login/login.html',
    controller: loginController
});

loginController.$inject = ['$state','loginService'];

function loginController($state, loginService) {
    var vm = this;

    vm.login = function login() {
        vm.loginMessage = "Connecting...";
        loginService.login(vm.username, vm.password).then(function (message) {
            vm.loginMessage = "Connection success";
            $state.go('start');
        }).catch(function (data, status) {
            if (data.status === -1) {
                vm.loginMessage = 'Communication problem : contact the administrator';
            } else {
                vm.loginMessage = 'Wrong login/password';
            }
        });
    };
}
