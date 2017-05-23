'use strict';

angular.module("soundlink").config(userprofileConfig);

userprofileConfig.$inject = ['$stateProvider', '$urlRouterProvider'];

function userprofileConfig ($stateProvider, $urlRouterProvider) {
    $stateProvider.state('soundlink.userprofile', {
        url: 'userprofile',
        template : '<user-profile-page></user-profile-page>'
    });
}