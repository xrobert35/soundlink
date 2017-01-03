'use strict';

angular.module('soundlink').component('userProfilePage', {
    templateUrl: 'app/modules/soundlink_ihm/pages/userprofile/userprofile.html',
    controller: userprofileController
});

userprofileController.$inject = ['soundlinkResource', 'userStorage', 'fileUtils'];

function userprofileController(soundlinkResource, userStorage, fileUtils) {
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
        soundlinkResource.saveUserInformation(vm.userInformation).then(function (userInformation){
            userStorage.saveUserInformation(userInformation);
        });
    };

    vm.$onInit = function () {
        console.log("tooo");
    };
}