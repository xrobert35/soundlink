'use strict';

angular.module('soundlink').directive('zestTableAction', zestTableAction);

function zestTableAction() {
  return {
    restrict: 'E',
    scope: {
      ztAction : '<'
    },
    require: '^zestTableColumn',
    replace: true,
    transclude : true,
    template: '<div ng-transclude></div>',
    link: function ($scope, element, $attrs, $ctrl) {
      $scope.zestTableAction = {
        ztAction: $scope.ztAction,
        template: isBlank(element[0].innerHTML) ? null : element[0].innerHTML,
      };
      //Supression du template a ne pas mettre dans le DOM
      element[0].innerHTML = '';

      $ctrl.addAction($scope.zestTableAction);

      function isBlank(str) {
        return (!str || /^\s*$/.test(str));
      }
    }
  };
}