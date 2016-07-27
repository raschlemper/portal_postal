'use strict';

app.controller('ModalLancamentoProgramadoTransferirController', 
    ['$scope', '$modalInstance', 'lancamentoTransferencia', 'ContaService', 'DatePickerService', 'LISTAS', 'MESSAGES',
    function ($scope, $modalInstance, lancamentoTransferencia, ContaService, DatePickerService, LISTAS, MESSAGES) {

        var init = function () { 
            $scope.datepickerCompetencia = angular.copy(DatePickerService.default); 
            $scope.datepickerLancamento = angular.copy(DatePickerService.default); 
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
                    $scope.lancamentoTransferencia.contaOrigem = $scope.contas[0];
                    $scope.lancamentoTransferencia.contaDestino = $scope.contas[0];
                })
                .catch(function (e) {
                    console.log(e);
                });
        };
        
        $scope.setDataCompetencia = function(lancamentoTransferencia) {
            if(lancamentoTransferencia.dataCompetencia) return;
            lancamentoTransferencia.dataCompetencia = lancamentoTransferencia.dataLancamento;
        };
        
        $scope.ok = function(form, lancamentoTransferencia) {
            if (!validarForm(form)) return;
            $modalInstance.close(lancamentoTransferencia);            
        };
        
        $scope.cancel = function () {
            $modalInstance.dismiss('cancel');
        };

        var validarForm = function (form) {
            if(form.contaOrigem.$modelValue === form.contaDestino.$modelValue) {
                alert(MESSAGES.lancamento.transferir.validacao.CONTA_DIFERENTE);
                return false;                
            }
            if (form.dataCompetencia.$error.required) {
                alert(MESSAGES.lancamento.transferir.validacao.DATA_COMPETENCIA_REQUERIDA);
                return false;
            }        
            if (form.dataCompetencia.$modelValue && !moment(form.dataCompetencia.$modelValue).isValid()) {
                alert(MESSAGES.lancamento.transferir.validacao.DATA_COMPETENCIA_VALIDA);
                return false;
            }    
            if (form.dataVencimento.$error.required) {
                alert(MESSAGES.lancamento.transferir.validacao.DATA_VENCIMENTO_REQUERIDA);
                return false;
            }       
            if (form.dataVencimento.$modelValue && !moment(form.dataVencimento.$modelValue).isValid()) {
                alert(MESSAGES.lancamento.transferir.validacao.DATA_VENCIMENTO_VALIDA);
                return false;
            }    
            if (form.valor.$error.required) {
                alert(MESSAGES.lancamento.transferir.validacao.VALOR_REQUERIDA);
                return false;
            }
            if (form.historico.$error.required) {
                alert(MESSAGES.lancamento.transferir.validacao.HISTORICO_REQUERIDA);
                return false;
            }
            return true;
        }      

        init();

    }]);
