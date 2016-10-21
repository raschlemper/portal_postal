app.directive('appTypeahead', function() {
    
    return {
        restrict: 'E',
        templateUrl: "js/directive/html/appTypeahead.html",
        scope: { mdl: "=ngModel", options: "=",  config: "=?", events: "=", required: "=?ngRequired", disabled: "=?ngDisabled" },
        require: "ngModel",
        replace: true,
        link: function($scope, $element, $attrs) {
            $scope.externalEvents = {
                onItemSelect: angular.noop
            };
            angular.extend($scope.externalEvents, $scope.events || []);
        },

        controller: ["$scope", function (a) {
                a.onSelect = function (i) {
                    a.mdl = i;
                    if (a.events !== undefined) {
                        a.events.onItemSelect(i);
                    }
                }
            }
        ]
    }
});