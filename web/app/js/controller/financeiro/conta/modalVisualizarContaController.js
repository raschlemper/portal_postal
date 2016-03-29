'use strict';

app.controller('ModalVisualizarContaController', ['$scope', '$modalInstance', 'conta', 
    function ($scope, $modalInstance, conta) {

        var init = function () { 
            $scope.conta = conta;
        };
        
        $scope.editar = function() {
            $modalInstance.close(conta.idConta);
        };
        
        $scope.cancel = function () {
            $modalInstance.dismiss('cancel');
        };

        init();

    }]);
