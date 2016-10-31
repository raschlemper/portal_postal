app.directive('typeaheadEmpty', function ($timeout) {
    return {
        require: 'ngModel',
        link: function (scope, element, attr, ctrl) {
            element.bind('click', function () {
                ctrl.$setViewValue(' ');
            });
            element.bind('blur', function () {
                ctrl.$setViewValue('');
            });

//            var triggerFunc = function(evt) {
//                var ctrl = angular.copy(element.controller('ngModel'));
//                var prev = angular.copy(ctrl.$modelValue) || '';
//                if (prev) {
//                    ctrl.$setViewValue('');
//                    $timeout(function() {
//                        ctrl.$setViewValue(prev);
//                    });
//                } else {
//                    ctrl.$setViewValue(' ');
//                }
//            }
//            element.bind('click', triggerFunc);
        }
    };
});