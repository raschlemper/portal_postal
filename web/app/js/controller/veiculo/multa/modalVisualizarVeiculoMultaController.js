'use strict';

app.controller('ModalVisualizarVeiculoMultaController', ['$scope', '$modalInstance', 'veiculoMulta', 
    function ($scope, $modalInstance, veiculoMulta) {

        var init = function () { 
            $scope.veiculoMulta = veiculoMulta;
        };
        
        // ***** CONTROLLER ***** //
        
        $scope.editar = function() {
            $modalInstance.close(veiculoMulta);
        };
        
        $scope.cancel = function () {
            $modalInstance.dismiss('cancel');
        };

        init();

    }]);
