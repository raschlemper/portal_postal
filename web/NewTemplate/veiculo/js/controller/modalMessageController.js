'use strict';

veiculo.controller('ModalMessageController', ['$scope', '$modalInstance', 'message',
    function ($scope, $modalInstance, message) {

        var init = function () {
            $scope.message = message;
        };
        
        $scope.cancel = function () {
            $modalInstance.dismiss('cancel');
        };

        init();

    }]);
