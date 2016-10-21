'use strict';

app.controller('HelpController', ['$scope', '$modalInstance', 'error', 'success',
    function ($scope, $modalInstance, error, success) {

        var init = function () {
            $scope.error = error;
            $scope.success = success;
        };
        
        $scope.cancel = function () {
            $modalInstance.dismiss('cancel');
        };

        init();

    }]);
