angular.module('soundlink-data', ['ngCookies', 'ngResource']);

angular.module("soundlink-data").config(['$httpProvider', function ($httpProvider) {

	$httpProvider.defaults.xsrfCookieName = 'X-AUTH-TOKEN'; // The name of the cookie sent by the server
	$httpProvider.defaults.xsrfHeaderName = 'X-AUTH-TOKEN'; // The default header name picked up by Spring Security

    $httpProvider.interceptors.push('csrfRequestInterceptor');
	$httpProvider.interceptors.push('timeoutHttpIntercept');
}]);