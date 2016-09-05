'use strict';

app.controller('ModalLancamentoTransferirController', 
    ['$scope', '$modalInstance', 'lancamentoTransferencia', 'ContaService', 'DatePickerService', 'LISTAS', 'MESSAGES',
    function ($scope, $modalInstance, lancamentoTransferencia, ContaService, DatePickerService, LISTAS, MESSAGES) {

        var init = function () {  
//            $scope.datepickerCompetencia = angular.copy(DatePickerService.default); 
//            $scope.datepickerLancamento = angular.copy(DatePickerService.default); 
            $scope.situacoes = LISTAS.situacaoLancamento;
            $scope.lancamentoTransferencia = lancamentoTransferencia || {};             
            getTitle(lancamentoTransferencia);
            contas();
        };
                
        // ***** CONTROLLER ***** //   
        
        var getTitle = function(lancamentoTransferencia) {
            if(lancamentoTransferencia && lancamentoTransferencia.idLancamentoTransferencia) { 
                $scope.title = MESSAGES.lancamento.transferir.title.EDITAR; 
            } else { 
                $scope.title = MESSAGES.lancamento.transferir.title.INSERIR; 
            }
        };
        
        var contas = function() {
            ContaService.getAll()
                .then(function (data) {
                    $scope.contas = data;
                    $scope.lancamentoTransferencia.contaOrigem = ($scope.lancamentoTransferencia.lancamentoOrigem && $scope.lancamentoTransferencia.lancamentoOrigem.conta) || $scope.contas[0];
                    $scope.lancamentoTransferencia.contaDestino = ($scope.lancamentoTransferencia.lancamentoDestino && $scope.lancamentoTransferencia.lancamentoDestino.conta) || $scope.contas[0];
                })
                .catch(function (e) {
                    console.log(e);
                });
        };
        
//        $scope.setDataCompetencia = function(lancamentoTransferencia) {
//            if(lancamentoTransferencia.dataCompetencia) return;
//            lancamentoTransferencia.dataCompetencia = lancamentoTransferencia.dataLancamento;
//        };
        
        $scope.eventsOrigem = {
            onblur : function() {
                setDataCompetenciaOrigem($scope.lancamentoTransferencia);
                //setDataVencimentoDestino($scope.lancamentoTransferencia);
                //setDataCompetenciaDestino($scope.lancamentoTransferencia);
            }
        };
        
        $scope.eventsDestino = {
            onblur : function() {
                setDataCompetenciaDestino($scope.lancamentoTransferencia);
            }
        };
        
        var setDataCompetenciaOrigem = function(lancamentoTransferencia){
            if(lancamentoTransferencia.dataCompetenciaOrigem) return;
            lancamentoTransferencia.dataCompetenciaOrigem = lancamentoTransferencia.dataLancamentoOrigem;            
        };
        
        var setDataVencimentoDestino = function(lancamentoTransferencia){
            if(lancamentoTransferencia.dataLancamentoDestino) return;
            lancamentoTransferencia.dataLancamentoDestino = lancamentoTransferencia.dataLancamentoOrigem;            
        };
        
        var setDataCompetenciaDestino = function(lancamentoTransferencia){
            if(lancamentoTransferencia.dataCompetenciaDestino) return;
            lancamentoTransferencia.dataCompetenciaDestino = lancamentoTransferencia.dataLancamentoDestino;            
        };
        
        $scope.ok = function(form, lancamentoTransferencia) {
            if (!validarForm(form)) return;
            $modalInstance.close(lancamentoTransferencia);            
        };
        
        $scope.cancel = function () {
            $modalInstance.dismiss('cancel');
        };
                
        // ***** VALIDAR ***** //  

        var validarForm = function (form) {
            if(form.contaOrigem.$modelValue === form.contaDestino.$modelValue) {
                alert(MESSAGES.lancamento.transferir.validacao.CONTA_DIFERENTE);
                return false;                
            }
            if (form.dataCompetenciaOrigem.$error.required) {
                alert(MESSAGES.lancamento.transferir.validacao.DATA_COMPETENCIA_ORIGEM_REQUERIDA);
                return false;
            }        
            if (form.dataCompetenciaOrigem.$modelValue && !moment(form.dataCompetenciaOrigem.$modelValue).isValid()) {
                alert(MESSAGES.lancamento.transferir.validacao.DATA_COMPETENCIA_ORIGEM_VALIDA);
                return false;
            }   
            if (form.dataCompetenciaDestino.$error.required) {
                alert(MESSAGES.lancamento.transferir.validacao.DATA_COMPETENCIA_DESTINO_REQUERIDA);
                return false;
            }        
            if (form.dataCompetenciaDestino.$modelValue && !moment(form.dataCompetenciaDestino.$modelValue).isValid()) {
                alert(MESSAGES.lancamento.transferir.validacao.DATA_COMPETENCIA_DESTINO_VALIDA);
                return false;
            }    
            if (form.dataLancamentoOrigem.$error.required) {
                alert(MESSAGES.lancamento.transferir.validacao.DATA_LANCAMENTO_ORIGEM_REQUERIDA);
                return false;
            }       
            if (form.dataLancamentoOrigem.$modelValue && !moment(form.dataLancamentoOrigem.$modelValue).isValid()) {
                alert(MESSAGES.lancamento.transferir.validacao.DATA_LANCAMENTO_ORIGEM_VALIDA);
                return false;
            }       
            if (form.dataLancamentoDestino.$error.required) {
                alert(MESSAGES.lancamento.transferir.validacao.DATA_LANCAMENTO_DESTINO_REQUERIDA);
                return false;
            }       
            if (form.dataLancamentoDestino.$modelValue && !moment(form.dataLancamentoDestino.$modelValue).isValid()) {
                alert(MESSAGES.lancamento.transferir.validacao.DATA_LANCAMENTO_DESTINO_VALIDA);
                return false;
            }   
            if (form.valor.$error.required) {
                alert(MESSAGES.lancamento.transferir.validacao.VALOR_REQUERIDA);
                return false;
            }
//            if (form.historico.$error.required) {
//                alert(MESSAGES.lancamento.transferir.validacao.HISTORICO_REQUERIDA);
//                return false;
//            }
            return true;
        }     

        init();

    }]);
