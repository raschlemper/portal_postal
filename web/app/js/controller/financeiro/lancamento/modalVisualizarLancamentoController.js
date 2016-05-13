'use strict';

app.controller('ModalVisualizarLancamentoController', ['$scope', '$modalInstance', '$state', 'lancamento', 
    function ($scope, $modalInstance, $state, lancamento) {

        var init = function () { 
            $scope.lancamento = lancamento;
        };    
        
        $scope.editar = function() {
            $modalInstance.close(lancamento.idLancamento);
        };
        
        $scope.cancel = function () {
            $modalInstance.dismiss('cancel');
        };
        
        $scope.programacao = function () {
            $modalInstance.dismiss('cancel');
            $state.go('app.financeiro.lancamentoprogramado.edit', 
                {"id": lancamento.lancamentoProgramado.idLancamentoProgramado});
        };

        init();

    }]);
