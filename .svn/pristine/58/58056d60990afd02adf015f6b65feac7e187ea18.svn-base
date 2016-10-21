'use strict';

app.controller('ModalVisualizarVeiculoSinistroController', ['$scope', '$modalInstance', 'veiculoSinistro', 
    function ($scope, $modalInstance, veiculoSinistro) {

        var init = function () { 
            $scope.veiculoSinistro = veiculoSinistro;
        };
        
        $scope.editar = function() {
            $modalInstance.close(veiculoSinistro.idVeiculoSinistro);
        };
        
        $scope.cancel = function () {
            $modalInstance.dismiss('cancel');
        };

        init();

    }]);
