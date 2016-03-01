'use strict';

app.controller('ModalVisualizarVeiculoController', ['$scope', '$modalInstance', 'veiculo', 'FipeService', 'ListaService', 'LISTAS',
    function ($scope, $modalInstance, veiculo, FipeService, ListaService, LISTAS) {

        var init = function () { 
            $scope.veiculo = veiculo;
        };
        
        $scope.cancel = function () {
            $modalInstance.dismiss('cancel');
        };

        init();

    }]);
