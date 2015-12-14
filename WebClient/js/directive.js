var weChatDirectives = angular.module('weChatDirectives', []);

weChatDirectives.directive('contextMenu', ['$window', function($window) {

    return {
        restrict: 'A',
        link: function($scope, element, attrs) {
            var menuElement = angular.element(document.getElementById(attrs.target));
            function open(event, element) {
                menuElement.removeClass("ng-hide");
                menuElement.css('top', event.clientY + 'px');
                menuElement.css('left', event.clientX + 'px');
            };
            function close(element) {
                menuElement.addClass("ng-hide");
            };
            element.bind('contextmenu', function(event) {
                $scope.$apply(function() {
                    event.preventDefault();
                    open(event, menuElement);
                });
            });
            angular.element($window).bind('click', function(event) {
                if (menuElement.className!="ng-hide") {
                    $scope.$apply(function() {
                        event.preventDefault();
                        close(menuElement);
                    });
                }
            });
        }
    };
}]);
