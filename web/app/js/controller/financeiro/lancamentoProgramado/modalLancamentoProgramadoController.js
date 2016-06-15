'use strict';

app.controller('ModalLancamentoProgramadoController', ['$scope', '$modalInstance', 'conta', 'tipo', 'lancamentoProgramado', 'ContaService', 'PlanoContaService', 'CentroCustoService', 'LancamentoConciliadoService', 'TipoDocumentoService', 'TipoFormaPagamentoService', 'FrequenciaLancamentoService', 'ModalService', 'DatePickerService', 'ListaService', 'LISTAS', 'MESSAGES',
    function ($scope, $modalInstance, conta, tipo, lancamentoProgramado, ContaService, PlanoContaService, CentroCustoService, LancamentoConciliadoService, TipoDocumentoService, TipoFormaPagamentoService, FrequenciaLancamentoService, ModalService, DatePickerService, ListaService, LISTAS, MESSAGES) {

        var init = function () {  
            $scope.conta = conta;
            $scope.lancamentoProgramado = lancamentoProgramado || {};
            $scope.tipo = tipo;
            initStep($scope.lancamentoProgramado);
        };
        
        // ***** NAVEGAR ***** //
        
        var initStep = function(lancamentoProgramado) {
            $scope.goToEditar(); 
        };
        
        $scope.goToEditar = function() {
            $scope.stepFrom = null;
            $scope.stepTo = 'editar';             
        };
        
        $scope.goToLancar = function() {
            $scope.stepFrom = 'editar';
            $scope.stepTo = 'lancar';             
        };
        
        $scope.goToLancarParcelar = function() {
            $scope.stepFrom = 'parcelar';
            $scope.stepTo = 'lancar';             
        };
        
        $scope.goToLancarRatear = function() {
            $scope.stepFrom = angular.copy($scope.stepTo);
            $scope.stepTo = 'lancarRatear';             
        };
        
        $scope.goToParcelar = function() {
            $scope.stepFrom = 'editar';
            $scope.stepTo = 'parcelar';             
        };
        
        $scope.goToRatear = function() {
            $scope.stepFrom = angular.copy($scope.stepTo);
            $scope.stepTo = 'ratear';             
        };
        
        $scope.voltar = function() {
            if($scope.stepFrom === 'lancar') {
                $scope.goToGerar();  
            } else if($scope.stepFrom === 'lancarRatear') {
                $scope.goToLancarRatear();                                  
            } else if($scope.stepFrom === 'parcelar') {
                $scope.goToParcelar();                      
            } else if($scope.stepFrom === 'ratear') {
                $scope.goToRatear();            
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
