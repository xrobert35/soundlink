'use strict';

angular.module('soundlink').directive("showDetail", showDetail);

showDetail.$inject = ['$compile', '$anchorScroll', '$location'];

var currentTemplate;

function showDetail($compile, $anchorScroll, $location) {
  return {
    restrict: 'A',
    link: function (scope, element, attrs) {
      element.bind("click", function ($event) {
        removeDetailItem();

        var configInclude = "<album-detail album='$ctrl.selectedAlbum' id='detailItem"+scope.album.id+"'></album-detail>";
        currentTemplate = $compile(configInclude)(scope);

        var templateElement = angular.element(currentTemplate);

        currentTemplate.on("$destroy", function () {
          if (scope.$$childHead != null) {
            scope.$$childHead.$destroy();
          }
          scope.$$watchers = [];
        });

        var div = angular.element(element[0]);

        //We search for the div or parent div with the class show-detail-item
        while (div.parent().length > 0 && !div.hasClass("show-detail-item")) {
          div = div.parent();
        }

        var currentTop = div[0].getBoundingClientRect().top;

        var possibleEndElement = div;
        for (var childIndex = 0; childIndex < div.parent()[0].children.length; childIndex++) {
          if (div.parent()[0].children[childIndex].getBoundingClientRect().top > currentTop) {
            break;
          }
          possibleEndElement = div.parent()[0].children[childIndex];
        }

        insertAfter(templateElement[0], possibleEndElement);

        function insertAfter(newNode, referenceNode) {
          referenceNode.parentNode.insertBefore(newNode, referenceNode.nextSibling);
          $location.hash('detailItem' + scope.album.id);
        }
      });
    }
  };
}

angular.module('soundlink').directive("removeDetailItem", removeDetailItemDirective);

function removeDetailItemDirective() {
  return {
    restrict: 'A',
    link: function (scope, element, attrs) {
      element.bind("click", function ($event) {
        removeDetailItem();
      });
    }
  };
}

function removeDetailItem() {
  if (currentTemplate != null) {
    currentTemplate.remove();
  }
}
