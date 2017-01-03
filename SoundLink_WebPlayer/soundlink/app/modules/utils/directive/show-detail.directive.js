'use strict';

angular.module('soundlink').directive("showDetail", showDetail);

showDetail.$inject = ['$compile'];

function showDetail($compile) {
  return {
    restrict: 'A',
    link: function (scope, element, attrs) {

      element.bind("click", function ($event) {
        var configInclude = "<album-detail musics='$ctrl.albumSongs' album='$ctrl.selectedAlbum' id='detailItem'></album-detail>";
        var template = $compile(configInclude)(scope);
        
        removeDetailItem();

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

        var templateElement = angular.element(template);
        insertAfter(templateElement[0], possibleEndElement);

        function insertAfter(newNode, referenceNode) {
          referenceNode.parentNode.insertBefore(newNode, referenceNode.nextSibling);
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
  var detailItem = document.getElementById("detailItem");
  if (detailItem != null) {
    detailItem.remove();
  }
}
