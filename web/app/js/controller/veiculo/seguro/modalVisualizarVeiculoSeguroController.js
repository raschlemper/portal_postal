'use strict';

app.controller('ModalVisualizarVeiculoSeguroController', ['$scope', '$modalInstance', 'veiculoSeguro', 
    function ($scope, $modalInstance, veiculoSeguro) {

        var init = function () { 
            $scope.veiculoSeguro = veiculoSeguro;
        };
        
        $scope.editar = function() {
            $modalInstance.close(veiculoSeguro.idVeiculoSeguro);
        };
        
        $scope.cancel = function () {
            $modalInstance.dismiss('cancel');
        };

        init();

    }]);
