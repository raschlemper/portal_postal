app.directive('appDatepicker', function(DatePickerService) {
    
    return {
        restrict: 'E',
        templateUrl: "js/directive/html/appDatepicker.html",
        scope: { datepickerModel: '=', evtOnblur: evtOnblur },
        link: function($scope, element, attr, controller) {  
            
            var init = function() {
                $scope.datepicker = angular.copy(DatePickerService.default);      
                $scope.name = attr.name;     
                $scope.placeholder = attr.placeholder;
            }       
            
            init();
        }
    }
});