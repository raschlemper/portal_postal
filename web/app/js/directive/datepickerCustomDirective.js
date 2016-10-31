app.directive('datepickerCustom', function() {
    
    return {
    restrict: 'A',
    require : 'ngModel',
    link : function (scope, element, attrs, ngModel) {
        
        $(function(){
            element.datepicker({
                dateFormat:'dd/mm/yy',
                showAnim: "slideDown"
            });            
        });
            
        scope.$watch(function () {
            return ngModel.$modelValue;
        }, function(newValue) {
            if(!newValue) return;
            if(angular.isDate(newValue)) {
                var date = moment(newValue);
                element.val(date.format('DD/MM/YYYY'));   
            } else if(moment.isMoment(newValue)) {
                element.val(newValue.format('DD/MM/YYYY'));               
            } else {
                element.val(newValue);   
            }
            
        });
    }
  };
  
});