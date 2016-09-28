'use strict';

app.controller('ModalVisualizarVeiculoController', ['$scope', '$modalInstance', 'veiculo', 'FipeService', 'ListaService', 'LISTAS',
    function ($scope, $modalInstance, veiculo, FipeService, ListaService, LISTAS) {

        var init = function () { 
            $scope.veiculo = veiculo;
            pesquisarVeiculoFipe();
        };
        
        // ***** CONTROLLER ***** //

        var pesquisarVeiculoFipe = function () {
            FipeService.veiculo($scope.veiculo.tipo.id, $scope.veiculo.idMarca, $scope.veiculo.idModelo, $scope.veiculo.idVersao)
                .then(function (data) {
                    $scope.veiculoFipe = data;
                })
                .catch(function (e) {
                    console.log(e);
                });
        };
        
        $scope.editar = function() {
            $modalInstance.close(veiculo);
        };
        
        $scope.cancel = function () {
            $modalInstance.dismiss('cancel');
        };

        init();

    }]);
