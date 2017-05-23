'use strict';

angular.module('soundlink').component('userProfilePage', {
    templateUrl: 'app/modules/soundlink_ihm/pages/userprofile/userprofile.html',
    controller: userprofileController
});

userprofileController.$inject = ['usersResource', 'userStorage', 'fileUtils', 'audioRemoteControl', 'notificationManager'];

function userprofileController(usersResource, userStorage, fileUtils, audioRemoteControl, notificationManager) {
    var vm = this;

    vm.userInformation = angular.copy(userStorage.getUserInformation());

    vm.clickUpload = function () {
        document.getElementById("upload-picture").click();
    };

    vm.uploadPicture = function (event) {
        var file = event.target.files[0];
        fileUtils.extractBase64FromFile(file).then(function (base64File) {
            vm.userInformation.picture = base64File;
        });
    };

    vm.saveUserInformation = function () {
        usersResource.saveUserInformation(vm.userInformation).then(function (userInformation) {
            userStorage.saveUserInformation(userInformation);
            notificationManager.showNotification("NOTIFICATION.profile-updated");
        });
    };

    vm.checkPassword = function () {
        if (vm.userInformation.currentPassword != null && vm.userInformation.currentPassword != "") {
            usersResource.checkPassword(vm.userInformation.currentPassword).then(function (passwordOk) {
                vm.userProfilForm.currentPassword.$setValidity('wrong', passwordOk);
            });
        } else {
            vm.userProfilForm.currentPassword.$setValidity('wrong', true);
        }
    };

    vm.isSamePassword = function () {
        if (vm.userInformation.confirmPassword != null && vm.userInformation.confirmPassword != "") {
            vm.userProfilForm.confirmPassword.$setValidity('different', vm.userInformation.newPassword == vm.userInformation.confirmPassword);
            return;
        }
        vm.userProfilForm.confirmPassword.$setValidity('different', true);
    };
}