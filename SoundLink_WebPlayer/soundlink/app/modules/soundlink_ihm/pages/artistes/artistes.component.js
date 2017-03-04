'use strict';

angular.module('soundlink').component('artistesPage', {
    templateUrl: 'app/modules/soundlink_ihm/pages/artistes/artistes.html',
    controller: artistesController
});

artistesController.$inject = ['soundlinkResource', '$q', '$scope'];

function artistesController(soundlinkResource, $q, $scope) {
    var vm = this;

    vm.getArtistes = function () {
        $scope.$broadcast("refresh:table");
    };

    vm.action = function (row, column){
        console.log("action");
    };

    vm.getDatas = function (tri, callback) {
        return $q.when({
            pageIndex: 1,
            totalPages: 3,
            totalElements: 3,
            results: [{
                column1: "Toto1",
                column2: "Tata1",
                column3: new Date(),
                column4: "SUPER TESTTTTTTTTTTTTTTTTT 1 new Date()"
            },
            {
                column1: "Toto2",
                column2: "Tata2",
                column3: null,
                column4: "SUPER TESTTTTTTTTTTTTTTTTT 2 new Date()"
            },
            {
                column1: "Toto3",
                column2: "Tata3",
                column3: new Date(),
                column4: "SUPER TESTTTTTTTTTTTTTTTTT 3 new Date()"
            },
            ]
        });
    };

    vm.format = function (row) {
        return row.column1 + ' - ' + row.column2;
    };
}
