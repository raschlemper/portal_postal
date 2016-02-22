'use strict';

veiculo.controller('ModalExcluirController', ['$scope', '$uibModalInstance', 'title', 'message',
    function ($scope, $uibModalInstance, title, message) {

        var init = function () {
            $scope.title = title;
            $scope.message = message;
        };
        
        $scope.ok = function() {
            $uibModalInstance.close();
        };
        
        $scope.cancel = function () {
            $uibModalInstance.dismiss('cancel');
        };

        init();

    }]);
