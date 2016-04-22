'use strict';

app.controller('ModalVisualizarLancamentoController', ['$scope', '$modalInstance', 'lancamento', 
    function ($scope, $modalInstance, lancamento) {

        var init = function () { 
            $scope.lancamento = lancamento;
        };    
        
        $scope.editar = function() {
            $modalInstance.close(lancamento.idLancamento);
        };
        
        $scope.cancel = function () {
            $modalInstance.dismiss('cancel');
        };

        init();

    }]);
