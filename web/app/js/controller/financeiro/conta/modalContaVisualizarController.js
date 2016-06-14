'use strict';

app.controller('ModalContaVisualizarController', ['$scope', '$modalInstance', 'conta', 
    function ($scope, $modalInstance, conta) {

        var init = function () { 
            $scope.conta = conta;
        };
                
        // ***** CONTROLLER ***** //  
        
        $scope.editar = function() {
            $modalInstance.close(conta.idConta);
        };
        
        $scope.cancel = function () {
            $modalInstance.dismiss('cancel');
        };

        init();

    }]);
