'use strict';

app.controller('ModalVisualizarVeiculoMultaController', ['$scope', '$modalInstance', 'veiculoMulta', 
    function ($scope, $modalInstance, veiculoMulta) {

        var init = function () { 
            $scope.veiculoMulta = veiculoMulta;
        };
        
        $scope.editar = function() {
            $modalInstance.close(veiculoMulta.idVeiculoMulta);
        };
        
        $scope.cancel = function () {
            $modalInstance.dismiss('cancel');
        };

        init();

    }]);
