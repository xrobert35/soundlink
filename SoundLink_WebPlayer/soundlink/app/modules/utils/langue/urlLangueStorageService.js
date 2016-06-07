angular.module("soundlink-data").factory('UrlLanguageStorage', ['$location', urlLanguageStore]);

function urlLanguageStore($location) {
    return {
        put: function (name, value) { },
        get: function (name) {
            return $location.search()['lang']
        }
    };
}