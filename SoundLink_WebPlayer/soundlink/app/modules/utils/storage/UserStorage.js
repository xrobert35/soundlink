angular.module("soundlink").factory('userStorage', function () {
    var userInformation;
    return {
        saveUserInformation : function (pUserInformation) {
            userInformation = pUserInformation;
        },
        getUserInformation: function () {
            return userInformation;
        },
        clear: function () {
          userInformation = null;
        }
    }
});