angular.module("soundlink").factory('UrlLanguageStorage', ['$location', urlLanguageStore]);

function urlLanguageStore($location) {
    return {
        put: function (name, value) { },
        get: function (name) {
            return $location.search()['lang'];
        }
    };
}