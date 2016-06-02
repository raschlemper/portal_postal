'use strict';

app.controller('ModalLancamentoVisualizarController', ['$scope', '$modalInstance', '$state', 'lancamento', 'MESSAGES', 
    function ($scope, $modalInstance, $state, lancamento, MESSAGES) {

        var init = function () { 
            $scope.lancamento = lancamento;
            getTitle(lancamento);
        };  
                
        // ***** CONTROLLER ***** //    
        
        var getTitle = function(lancamento) {
            $scope.title = MESSAGES.lancamento.title.VISUALIZAR + ' ' + lancamento.modelo.descricao; 
        };
        
        $scope.editar = function() {
            $modalInstance.close(lancamento);
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
