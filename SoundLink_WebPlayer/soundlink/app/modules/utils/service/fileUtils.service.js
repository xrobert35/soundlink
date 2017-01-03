angular.module('soundlink').service("fileUtils", fileUtils);

fileUtils.$inject = ['$q'];

function fileUtils($q) {

  this.extractBase64FromFile = function (file) {
    var defered = $q.defer();

    var reader = new FileReader();
    reader.onload = function () {
      var binary = '';
      var arrayBuffer = this.result;
      var bytes = new Uint8Array(arrayBuffer);
      var len = bytes.byteLength;
      for (var i = 0; i < len; i++) {
        binary += String.fromCharCode(bytes[i]);
      }
      defered.resolve(window.btoa(binary));
    };

    reader.readAsArrayBuffer(file);

    return defered.promise;
  };
}
