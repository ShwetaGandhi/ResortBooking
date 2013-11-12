//var app = angular.module('hotelData', ['mydirectives']);
app.controller('aboutusController', function($scope) {
  
});

// add a namespace for custom directives
angular.module('mydirectives', []);

angular.module('mydirectives').directive('youtube', function() {
  return {
    restrict: 'EA',
    scope: { code:'@code' },
    template: '<div style="height:400px;"><iframe style="overflow:hidden;height:100%;width:50%" width="50%" height="100%" src="http://www.youtube.com/embed/{{code}}" frameborder="0" allowfullscreen></iframe></div>'
  };
});
