'use strict';

angular.module('soundlink').directive('zestTableColumn', zestTableColumn);

function zestTableColumn() {

  zestTableColumnController.$inject = ['$scope'];

  return {
    restrict: 'E',
    scope: {
      name: '@',
      libelle: '@?',
      ztClass: '<?',
      ztFormat: '<?',
      ztMedia: '@?',
      ztNoSort: '@?'
    },
    require: '^zestTable',
    transclude: true,
    replace : true,
    controller: zestTableColumnController,
    controllerAs: 'zestTableColumn',
    template: '<div ng-transclude></div>',
    link: function ($scope, element, $attrs, $ctrl) {
      angular.extend($scope.zestTableColumn, {
        name: $scope.name,
        libelle: $scope.libelle,
        ztClass: $scope.ztClass,
        ztFormat: $scope.ztFormat,
        ztMedia: $scope.ztMedia,
        ztNoSort: $scope.ztNoSort,
        template: isBlank(element[0].innerHTML) ? null : element[0].innerHTML,
      });
      //Supression du template a ne pas mettre dans le DOM
      element[0].innerHTML = '';

      $ctrl.addColumn($scope.zestTableColumn);

      function isBlank(str) {
        return (!str || /^\s*$/.test(str));
      }
    },
  };

  function zestTableColumnController(scope) {
    var vm = this;

    //initialisation du zestTableColumn qui  sera rempli dans le link
    scope.zestTableColumn = {};

    vm.addAction = function addAction(action) {
      if(scope.zestTableColumn.actions == null){
        scope.zestTableColumn.actions = [];
      }
       scope.zestTableColumn.actions.push(action);
    };

  }
}