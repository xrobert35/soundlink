angular.module("soundlink").filter("minute", minuteFilter);

function minuteFilter() {
  return function (input) {
    if (input != null) {
      if (input > 3600) {
        return moment().startOf('day').seconds(input).format('HH:mm:ss');
      } else {
        return moment().startOf('day').seconds(input).format('mm:ss');
      }
    } else {
      return "";
    }
  };
}