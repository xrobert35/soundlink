'use strict';

// translate provider configuration
angular.module("soundlink").config(translateConfig);

translateConfig.$inject = ['$translateProvider', '$translatePartialLoaderProvider'];

function translateConfig($translateProvider, $translatePartialLoaderProvider) {
    $translateProvider.useStorage('UrlLanguageStorage');
    $translateProvider.preferredLanguage('fr');
    $translateProvider.useLoaderCache('$templateCache');
    $translateProvider.useLoader('$translatePartialLoader', {
        urlTemplate: 'app/modules/utils/translate/{lang}.json'
    });
    $translatePartialLoaderProvider.addPart("all");
}