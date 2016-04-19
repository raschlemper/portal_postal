'use strict';

app.controller('ModalVisualizarLancamentoProgramadoController', ['$scope', '$modalInstance', 'lancamentoProgramado', 
    function ($scope, $modalInstance, lancamentoProgramado) {

        var init = function () { 
            $scope.lancamentoProgramado = lancamentoProgramado;
        };
        
        $scope.editar = function() {
            lancamentoProgramado.gerarLancamento = false;
            $modalInstance.close(lancamentoProgramado.idLancamentoProgramado);
        };
        
        $scope.gerarLancamento = function() {
            lancamentoProgramado.gerarLancamento = true;
            $modalInstance.close(lancamentoProgramado);
        };
        
        $scope.cancel = function () {
            $modalInstance.dismiss('cancel');
        };

        init();

    }]);
