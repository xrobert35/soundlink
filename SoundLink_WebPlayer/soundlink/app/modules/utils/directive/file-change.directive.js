'use strict';

angular.module('soundlink').directive('fileChange', function() {
  return {
    restrict: 'A',
    link: function (scope, element, attrs) {
      var onChangeHandler = scope.$eval(attrs.fileChange);
      element.bind('change', onChangeHandler);
    }
  };
});