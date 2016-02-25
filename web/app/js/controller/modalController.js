'use strict';

app.controller('ModalController', ['$scope', '$uibModalInstance', 'error', 'success',
    function ($scope, $uibModalInstance, error, success) {

        var init = function () {
            $scope.error = error;
            $scope.success = success;
        };
        
        $scope.cancel = function () {
            $uibModalInstance.dismiss('cancel');
        };

        init();

    }]);
