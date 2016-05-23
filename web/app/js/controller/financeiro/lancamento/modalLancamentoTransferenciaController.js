'use strict';

app.controller('ModalLancamentoTransferenciaController', ['$scope', '$modalInstance', 'ContaService', 'DatePickerService', 'LISTAS',
    function ($scope, $modalInstance, ContaService, DatePickerService, LISTAS) {

        var init = function () {  
            $scope.datepickerCompetencia = angular.copy(DatePickerService.default); 
            $scope.datepickerLancamento = angular.copy(DatePickerService.default); 
            $scope.situacoes = LISTAS.situacaoLancamento;
            $scope.lancamentoTransferencia = {};             
            getTitle();
            contas();
        };
        
        var getTitle = function() {
            $scope.title = "Inserir Novo Lançamento de Transferência";
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
        
        $scope.ok = function(form) {
            if (!validarForm(form)) return;
            $modalInstance.close($scope.lancamentoTransferencia);            
        };
        
        $scope.cancel = function () {
            $modalInstance.dismiss('cancel');
        };

        var validarForm = function (form) {
            if(form.contaOrigem.$modelValue === form.contaDestino.$modelValue) {
                alert('A conta de origem deve ser diferente da conta de destino!');
                return false;                
            }
            if (form.dataCompetencia.$error.required) {
                alert('Preencha a competência do lançamento!');
                return false;
            }        
            if (form.dataCompetencia.$modelValue && !moment(form.dataCompetencia.$modelValue).isValid()) {
                alert('A competência do lançamento não é válida!');
                return false;
            }    
            if (form.dataLancamento.$error.required) {
                alert('Preencha a data do lançamento!');
                return false;
            }       
            if (form.dataLancamento.$modelValue && !moment(form.dataLancamento.$modelValue).isValid()) {
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
