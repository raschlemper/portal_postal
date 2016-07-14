'use strict';

app.controller('ModalFornecedorVisualizarController', 
    ['$scope', '$modalInstance', 'fornecedor', 'MESSAGES', 
    function ($scope, $modalInstance, fornecedor, MESSAGES) {

        var init = function () { 
            $scope.fornecedor = fornecedor;
        };     
                
        // ***** CONTROLLER ***** //  
        
        $scope.editar = function() {
            $modalInstance.close(fornecedor);
        };
        
        $scope.cancel = function () {
            $modalInstance.dismiss('cancel');
        };

        init();

    }]);
