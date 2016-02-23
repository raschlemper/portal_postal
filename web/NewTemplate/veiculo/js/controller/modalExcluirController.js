'use strict';

veiculo.controller('ModalExcluirController', ['$scope', '$modalInstance', 'title', 'message',
    function ($scope, $modalInstance, title, message) {

        var init = function () {
            $scope.title = title;
            $scope.message = message;
        };
        
        $scope.ok = function() {
            $modalInstance.close();
        };
        
        $scope.cancel = function () {
            $modalInstance.dismiss('cancel');
        };

        init();

    }]);
