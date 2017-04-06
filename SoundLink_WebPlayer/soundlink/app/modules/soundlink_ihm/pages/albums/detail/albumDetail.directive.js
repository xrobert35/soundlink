'use strict';

angular.module('soundlink').directive("showAlbumDetail", showAlbumDetail);

showAlbumDetail.$inject = ['$compile', '$timeout'];

var currentTemplate;
var currentIdItem;

function showAlbumDetail($compile, $timeout) {
  return {
    restrict: 'A',
    link: function (scope, element, attrs) {
      element.bind("click", function ($event) {

        if (currentTemplate != null) {
          currentTemplate.remove();
        }

        var idItem = 'detailItem' + scope.album.id;
        if (currentIdItem != null && currentIdItem == idItem) {
          currentIdItem = null;
          return;
        }

        currentIdItem = idItem;

        var configInclude = "<album-detail album='$ctrl.selectedAlbum' id='" + idItem + "'></album-detail>";
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
        while (div.parent().length > 0 && !div.hasClass("album-item")) {
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
          // if (referenceNode.nextSibling.localName != 'album-detail') {
          //   
          // }
          referenceNode.parentNode.insertBefore(newNode, referenceNode.nextSibling);
        }
      });
    }
  };
}
