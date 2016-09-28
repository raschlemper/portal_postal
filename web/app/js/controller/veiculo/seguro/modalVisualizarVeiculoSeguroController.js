'use strict';

app.controller('ModalVisualizarVeiculoSeguroController', ['$scope', '$modalInstance', 'veiculoSeguro', 
    function ($scope, $modalInstance, veiculoSeguro) {

        var init = function () { 
            $scope.veiculoSeguro = veiculoSeguro;
        };
        
        // ***** CONTROLLER ***** //
        
        $scope.editar = function() {
            $modalInstance.close(veiculoSeguro);
        };
        
        $scope.cancel = function () {
            $modalInstance.dismiss('cancel');
        };

        init();

    }]);
