'use strict';

veiculo.controller('ModalMessageController', ['$scope', '$uibModalInstance', 'message',
    function ($scope, $uibModalInstance, message) {

        var init = function () {
            $scope.message = message;
        };
        
        $scope.cancel = function () {
            $uibModalInstance.dismiss('cancel');
        };

        init();

    }]);
