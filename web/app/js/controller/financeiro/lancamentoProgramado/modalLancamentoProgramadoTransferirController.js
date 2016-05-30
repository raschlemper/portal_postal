'use strict';

app.controller('ModalLancamentoProgramadoTransferirController', ['$scope', '$modalInstance', 'ContaService', 'DatePickerService', 'LISTAS',
    function ($scope, $modalInstance, ContaService, DatePickerService, LISTAS) {

        var init = function () {  
            $scope.datepicker = DatePickerService.default; 
            $scope.situacoes = LISTAS.situacaoLancamentoProgramado;
            $scope.lancamentoTransferencia = {};
            $scope.lancamentoTransferencia.situacao = $scope.situacoes[0];             
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
