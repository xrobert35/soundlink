'use strict';

// translate provider configuration
angular.module("soundlink").config(translateConfig);

translateConfig.$inject = ['$translateProvider'];

function translateConfig ($translateProvider) {
    $translateProvider.useUrlLoader("/soundlink_server/message/default");
    $translateProvider.useStorage('UrlLanguageStorage');
    $translateProvider.preferredLanguage('fr');
}