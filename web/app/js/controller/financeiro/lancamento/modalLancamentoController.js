'use strict';

app.controller('ModalLancamentoController', ['$scope', '$modalInstance', 'conta', 'lancamento', 'tipo', 'anexo', 
    function ($scope, $modalInstance, conta, lancamento, tipo, anexo) {

        var init = function () {  
            $scope.conta = conta;
            $scope.lancamento = lancamento || {};
            $scope.tipo = tipo;
            $scope.anexo = anexo; 
            initStep();
        };
                
        // ***** NAVEGAR ***** //  
                
        var initStep = function() {             
            $scope.goToEditar();
        };
        
        $scope.goToEditar = function() {
            $scope.stepFrom = angular.copy($scope.stepTo);
            $scope.stepTo = 'editar';             
        };
                
        // ***** CONTROLLER ***** //  
        
        $scope.close = function(lancamento) {
            $modalInstance.close(lancamento);
        };
        
        $scope.cancel = function () {
            $modalInstance.dismiss('cancel');
        };

        init();

    }]);
