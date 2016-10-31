app.directive('datepickerPopup', function() {
    
    return {
        restrict: 'EAC',
        require: 'ngModel',
        link: function(scope, element, attr, controller) {
            controller.$formatters.shift();
        }
    }
});