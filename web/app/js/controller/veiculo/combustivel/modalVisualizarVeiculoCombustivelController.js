'use strict';

app.controller('ModalVisualizarVeiculoCombustivelController', ['$scope', '$modalInstance', 'veiculoCombustivel', 
    function ($scope, $modalInstance, veiculoCombustivel) {

        var init = function () { 
            $scope.veiculoCombustivel = veiculoCombustivel;
        };
                
        // ***** CONTROLLER ***** // 
        
        $scope.editar = function() {
            $modalInstance.close(veiculoCombustivel);
        };
        
        $scope.cancel = function () {
            $modalInstance.dismiss('cancel');
        };

        init();

    }]);
