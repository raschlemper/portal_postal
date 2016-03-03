'use strict';

app.controller('ModalVisualizarVeiculoCombustivelController', ['$scope', '$modalInstance', 'veiculoCombustivel', 
    function ($scope, $modalInstance, veiculoCombustivel) {

        var init = function () { 
            $scope.veiculoCombustivel = veiculoCombustivel;
        };
        
        $scope.editar = function() {
            $modalInstance.close(veiculo.idVeiculo);
        };
        
        $scope.cancel = function () {
            $modalInstance.dismiss('cancel');
        };

        init();

    }]);
