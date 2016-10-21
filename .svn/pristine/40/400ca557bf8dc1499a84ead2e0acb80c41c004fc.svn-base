'use strict';

app.controller('ModalLancamentoProgramadoController', ['$scope', '$modalInstance', 'conta', 'tipo', 'lancamentoProgramado', 'ContaService', 'PlanoContaService', 'CentroCustoService', 'LancamentoConciliadoService', 'TipoDocumentoService', 'TipoFormaPagamentoService', 'FrequenciaLancamentoService', 'ModalService', 'DatePickerService', 'ListaService', 'LISTAS', 'MESSAGES',
    function ($scope, $modalInstance, conta, tipo, lancamentoProgramado, ContaService, PlanoContaService, CentroCustoService, LancamentoConciliadoService, TipoDocumentoService, TipoFormaPagamentoService, FrequenciaLancamentoService, ModalService, DatePickerService, ListaService, LISTAS, MESSAGES) {

        var init = function () {  
            $scope.conta = conta;
            $scope.lancamentoProgramado = lancamentoProgramado || {};
            $scope.tipo = tipo;
            initStep();
        };
        
        // ***** NAVEGAR ***** //
        
        var initStep = function() {
            $scope.goToEditar(); 
        };
        
        $scope.goToEditar = function() {
            if($scope.lancamentoProgramado && $scope.lancamentoProgramado.rateios && $scope.lancamentoProgramado.rateios.length) {
                $scope.showRatear = true;
            } 
            if($scope.lancamentoProgramado && $scope.lancamentoProgramado.quantidadeParcela) {
                $scope.showParcelar = true;
            } 
            $scope.stepFrom = null;
            $scope.stepTo = 'editar';             
        };
        
        $scope.goToLancar = function() {
            if($scope.lancamentoProgramado && $scope.lancamentoProgramado.rateios && $scope.lancamentoProgramado.rateios.length) {
                $scope.showRatear = true;
            } 
            $scope.stepFrom = angular.copy($scope.stepTo);
            $scope.stepTo = 'lancar';             
        };
        
        $scope.voltar = function() {
            if($scope.stepFrom === 'lancar') {
                $scope.goToGerar();  
            } else {
                $scope.goToEditar();
            }
        }
        
        // ***** CONTROLLER ***** //
        
        $scope.close = function(lancamentoProgramado) {            
            $modalInstance.close(lancamentoProgramado);    
        }
        
        $scope.cancel = function () {
            $modalInstance.dismiss('cancel');
        };

        init();

    }]);
