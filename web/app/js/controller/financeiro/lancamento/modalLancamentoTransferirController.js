'use strict';

app.controller('ModalLancamentoTransferirController', ['$scope', '$modalInstance', 'lancamentoTransferencia', 'ContaService', 'DatePickerService', 'LISTAS', 'MESSAGES',
    function ($scope, $modalInstance, lancamentoTransferencia, ContaService, DatePickerService, LISTAS, MESSAGES) {

        var init = function () {  
            $scope.datepickerCompetencia = angular.copy(DatePickerService.default); 
            $scope.datepickerLancamento = angular.copy(DatePickerService.default); 
            $scope.situacoes = LISTAS.situacaoLancamento;
            $scope.lancamentoTransferencia = lancamentoTransferencia || {};             
            getTitle();
            contas();
        };
        
        var getTitle = function() {
            if(lancamentoTransferencia && lancamentoTransferencia.idLancamentoTransferencia) { $scope.title = "Editar Lançamento de Transferência" }
            else { $scope.title = "Inserir Lançamento de Transferência"; }
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
        
        $scope.ok = function(form) {
            if (!validarForm(form)) return;
            $modalInstance.close($scope.lancamentoTransferencia);            
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
            if (form.dataLancamento.$error.required) {
                alert(MESSAGES.lancamento.transferir.validacao.DATA_LANCAMENTO_REQUERIDA);
                return false;
            }       
            if (form.dataLancamento.$modelValue && !moment(form.dataLancamento.$modelValue).isValid()) {
                alert(MESSAGES.lancamento.transferir.validacao.DATA_LANCAMENTO_VALIDA);
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
