'use strict';

angular.module('soundlink').component('zestTableComponent', {
    templateUrl: 'app/modules/utils/directive/zest-table/zest-table-component/zest-table.component.html',
    controller: zestTableController,
    bindings: {
        name: '@?',
        ztClass: '<?',
        ztDataLoader: "<",
        ztColumns: "<"
    }
});

zestTableController.$inject = ['$scope', '$mdMedia'];

function zestTableController($scope, $mdMedia) {
    var vm = this;

    vm.data = {
        results: [],
        pageIndex: 1,
        totalPages: 1,
        totalElements: 0
    };

    $scope.$watch(function () { return $mdMedia('xs'); }, function (small) {
        vm.isSmall = small;
    });

    vm.sortedColumn = null;

    vm.rechercheDone = false;

    vm.getXLColumns = function () {
        var xlColumns = [];
        if (vm.isSmall) {
            angular.forEach(vm.ztColumns, function (column) {
                if (column.ztMedia == 'large') {
                    xlColumns.push(column);
                }
            });
        }
        return xlColumns;
    };

    vm.executeAction = function (action, row, column) {
        action.ztAction(row, column);
    };

    vm.getColumns = function () {
        if (vm.isSmall) {
            var columns = [];
            angular.forEach(vm.ztColumns, function (column) {
                if (column.ztMedia != 'large') {
                    columns.push(column);
                }
            });
            return columns;
        } else {
            return vm.ztColumns;
        }
    };

    vm.getPagesNumber = function () {
        return new Array(vm.data.totalPages);
    };

    vm.sortColumn = function (column) {
        if (!column.ztNoSort) {
            //Si il y avait déjà une colonne trie on enleve son tri
            if (vm.sortedColumn != null && vm.sortedColumn != column) {
                vm.sortedColumn.asc = null;
            }
            vm.sortedColumn = column;

            //On switch de tri de la column à trier
            //Si elle n'etait pas triée elle le devient si elle l'était elle change d'ordre
            vm.sortedColumn.asc = !column.asc;

            loadData();
        }
    };

    vm.getPageIndex = function () {
        return vm.data.pageIndex;
    };

    vm.getResults = function () {
        return vm.data.results;
    };

    vm.hasResult = function () {
        return vm.data.totalElements > 0;
    };

    vm.getTotalPages = function () {
        return vm.data.totalPages;
    };

    vm.getTotalElements = function () {
        return vm.data.totalElements;
    };

    vm.onFirstPage = function () {
        return vm.data.pageIndex === 1;
    };

    vm.onLastPage = function () {
        return vm.data.pageIndex === vm.data.totalPages;
    };

    vm.isCurrentPage = function (pageIndex) {
        return vm.data.pageIndex === pageIndex;
    };

    vm.previousPage = function () {
        if (vm.data.pageIndex > 1) {
            loadData();
        }
    };

    vm.nextPage = function () {
        if (vm.data.pageIndex < vm.data.totalPages) {
            loadData();
        }
    };

    vm.displayCell = function (row, column) {
        var template = column.template;
        var data;
        if (column.ztFormat) {
            data = column.ztFormat(row, column);
        } else {
            data = row[column.name];
            if (data instanceof Date) {
                data = row[column.name].getTime();
            }
        }

        if (template != null) {
            if (column.ztType == 'date') {
                return template.replace('{data}', data);
            } else {
                return template.replace('{data}', data);
            }
        } else {
            return data;
        }
    };

    function loadData(searchData) {
        if (vm.ztDataLoader != null) {
            return vm.ztDataLoader(getSearchFilter()).then(function (data) {
                vm.rechercheDone = true;
                angular.copy(data, vm.data);
            });
        }
    }

    function getSearchFilter() {
        var searchFilter = {
            pageIndex: vm.data.pageIndex
        };
        if (vm.sortedColumn != null) {
            searchFilter.tri = vm.sortedColumn.sortName != null ? vm.sortedColumn.sortName : vm.sortedColumn.name;
            searchFilter.asc = vm.sortedColumn.asc;
        }
        return searchFilter;
    }

    vm.$onInit = function () {
        $scope.$on("refresh:" + vm.name, function () {
            loadData();
        });
    };
}