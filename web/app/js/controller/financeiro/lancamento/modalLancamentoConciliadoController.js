'use strict';

app.controller('ModalLancamentoConciliadoController', ['$scope', '$modalInstance', 'PlanoContaService', 'LancamentoService', 'DatePickerService', 'LISTAS',
    function ($scope, $modalInstance, PlanoContaService, LancamentoService, DatePickerService, LISTAS) {

        var init = function () {  
            $scope.datepickerCompetencia = angular.copy(DatePickerService.default); 
            $scope.datepickerLancamento = angular.copy(DatePickerService.default); 
            $scope.tipos = LISTAS.lancamento;
            $scope.lancamentoConciliado = {}; 
        };
        
        $scope.calcularSaldo = function(dataLancamento, saldoReconciliacao) {
            dataLancamento = moment(dataLancamento).format('YYYY-MM-DD HH:mm:ss');
            LancamentoService.getSaldoConciliado(dataLancamento)
                .then(function (data) {
                    if(!data.length) return;
                    $scope.saldo = calcularSaldo(data[0].valor, saldoReconciliacao);
                    if($scope.saldo.diferenca > 0) { $scope.lancamentoConciliado.tipo = $scope.tipos[0]; } 
                    else { $scope.lancamentoConciliado.tipo = $scope.tipos[1]; }
                    planoContas($scope.lancamentoConciliado.tipo);
                })
                .catch(function (e) {
                    console.log(e);
                });
        };
        
        var calcularSaldo = function(saldoAtual, saldoReconciliacao) {
            return {
                atual: saldoAtual,
                reconciliacao: saldoReconciliacao,
                diferenca: saldoReconciliacao - saldoAtual
            }
        }     
        
        var planoContas = function(tipo) {
            PlanoContaService.findContaResultadoByTipo(tipo.id)
                .then(function (data) {
                    $scope.planoContas = data;
                    $scope.lancamentoConciliado.planoConta = $scope.planoContas[0];
                })
                .catch(function (e) {
                    console.log(e);
                });
        };
        
        $scope.ok = function(form) {
            if (!validarForm(form)) return;
            $scope.lancamentoConciliado = ajustarDados($scope.saldo, $scope.lancamentoConciliado);
            $modalInstance.close($scope.lancamentoConciliado);            
        };
        
        var ajustarDados = function(saldo, lancamentoConciliado) {
            lancamentoConciliado.dataCompetencia = lancamentoConciliado.dataLancamento;
            lancamentoConciliado.dataEmissao = lancamentoConciliado.dataLancamento;
            if(saldo.diferenca < 0) { lancamentoConciliado.valor = saldo.diferenca * -1; }
            else { lancamentoConciliado.valor = saldo.diferenca; }
            return lancamentoConciliado;
        }
        
        $scope.cancel = function () {
            $modalInstance.dismiss('cancel');
        };

        var validarForm = function (form) {
            if (form.dataLancamento.$error.required) {
                alert('Preencha a data do lançamento reconciliado!');
                return false;
            }       
            if (form.dataLancamento.$modelValue && !moment(form.dataLancamento.$modelValue).isValid()) {
                alert('A data do lançamento reconciliado não é válida!');
                return false;
            }  
            if (form.historico.$error.required) {
                alert('Preencha o histórico do lançamento reconciliado!');
                return false;
            }
            return true;
        }     

        init();

    }]);
