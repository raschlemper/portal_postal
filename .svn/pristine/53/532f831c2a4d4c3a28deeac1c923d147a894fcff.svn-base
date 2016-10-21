'use strict';

app.controller('ModalVisualizarVeiculoCombustivelController', ['$scope', '$modalInstance', 'veiculoCombustivel', 
    function ($scope, $modalInstance, veiculoCombustivel) {

        var init = function () { 
            $scope.veiculoCombustivel = veiculoCombustivel;
        };
        
        $scope.editar = function() {
            $modalInstance.close(veiculoCombustivel.idVeiculoCombustivel);
        };
        
        $scope.cancel = function () {
            $modalInstance.dismiss('cancel');
        };

        init();

    }]);
