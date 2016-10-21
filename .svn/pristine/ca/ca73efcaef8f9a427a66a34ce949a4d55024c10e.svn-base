'use strict';

app.controller('ModalVisualizarVeiculoManutencaoController', ['$scope', '$modalInstance', 'veiculoManutencao', 
    function ($scope, $modalInstance, veiculoManutencao) {

        var init = function () { 
            $scope.veiculoManutencao = veiculoManutencao;
        };
                
        // ***** CONTROLLER ***** // 
        
        $scope.editar = function() {
            $modalInstance.close(veiculoManutencao);
        };
        
        $scope.cancel = function () {
            $modalInstance.dismiss('cancel');
        };

        init();

    }]);
