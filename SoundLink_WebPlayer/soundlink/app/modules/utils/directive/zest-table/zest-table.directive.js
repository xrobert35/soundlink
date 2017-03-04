'use strict'

angular.module('soundlink').directive('zestTable', zestTable);

function zestTable() {

  zestTableController.$inject = ['$scope'];

  return {
    restrict: 'E',
    scope: {
      name: '@',
      ztClass: '<?',
      ztDataLoader: '<'
    },
    transclude: true,
    controller: zestTableController,
    controllerAs: 'zestTable',
    template: '<div ng-transclude></div><zest-table-component name="{{::zestTable.name}}" zt-columns="::zestTable.columns" zt-class="::zestTable.ztClass" zt-data-loader="::zestTable.ztDataLoader"></zest-table-component>'
  };


  function zestTableController(scope) {
    var vm = this;

    vm.columns = [];
    vm.ztClass = scope.ztClass;
    vm.ztDataLoader = scope.ztDataLoader;
    vm.name = scope.name;

    vm.addColumn = function addColumn(column) {
      vm.columns.push(column);
    };
  }
}
