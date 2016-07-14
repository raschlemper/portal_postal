'use strict';

app.controller('ModalLancamentoProgramadoTransferirController', ['$scope', '$modalInstance', 'lancamentoTransferencia', 'ContaService', 'DatePickerService', 'LISTAS', 'MESSAGES',
    function ($scope, $modalInstance, lancamentoTransferencia, ContaService, DatePickerService, LISTAS, MESSAGES) {

        var init = function () { 
            $scope.datepickerCompetencia = angular.copy(DatePickerService.default); 
            $scope.datepickerLancamento = angular.copy(DatePickerService.default); 
            $scope.situacoes = LISTAS.situacaoLancamento;
            $scope.lancamentoTransferencia = lancamentoTransferencia || {};              
            getTitle(lancamentoTransferencia);
            contas();
        };
        
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
        
        $scope.ok = function(form, lancamentoTransferencia) {
            if (!validarForm(form)) return;
            $modalInstance.close(lancamentoTransferencia);            
        };
        
        $scope.cancel = function () {
            $modalInstance.dismiss('cancel');
        };

        var validarForm = function (form) {
            if (form.data.$error.required) {
                alert('Preencha a data do lançamento!');
                return false;
            }       
            if (form.data.$modelValue && !moment(form.data.$modelValue).isValid()) {
                alert('A data do lançamento não é válida!');
                return false;
            }    
            if (form.valor.$error.required) {
                alert('Preencha o valor do lançamento!');
                return false;
            }
            if (form.historico.$error.required) {
                alert('Preencha o histórico do lançamento!');
                return false;
            }
            return true;
        }     

        init();

    }]);
