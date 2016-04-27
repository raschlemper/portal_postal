'use strict';

app.controller('ModalEditarLancamentoController', ['$scope', '$modalInstance', 'conta', 'lancamento', 'ContaService', 'PlanoContaService', 'DatePickerService', 'ListaService', 'LISTAS',
    function ($scope, $modalInstance, conta, lancamento, ContaService, PlanoContaService, DatePickerService, ListaService, LISTAS) {

        var init = function () {  
            $scope.datepickerCompetencia = angular.copy(DatePickerService.default); 
            $scope.datepickerLancamento = angular.copy(DatePickerService.default); 
            $scope.tipos = LISTAS.lancamento; 
            $scope.modelos = LISTAS.modeloLancamento;
            $scope.situacoes = LISTAS.situacaoLancamento;
            $scope.lancamento = lancamento || {};
            $scope.lancamento.tipo = (lancamento && lancamento.tipo) || $scope.tipos[0];
            $scope.lancamento.situacao = (lancamento && lancamento.situacao) || $scope.situacoes[0];
            $scope.lancamento.modelo = (lancamento && lancamento.modelo) || $scope.modelos[0];            
            getTitle();
            contas();
            $scope.changeTipo($scope.lancamento.tipo);
        };
        
        $scope.editConta = function() {
            return (lancamento && lancamento.idLancamento);
        }
        
        var getTitle = function() {
            if(lancamento && lancamento.idLancamento) { $scope.title = "Editar Lançamento"; }
            else { $scope.title = "Inserir Novo Lançamento"; }
        };
        
        var contas = function() {
            ContaService.getAll()
                .then(function (data) {
                    $scope.contas = data;
                    $scope.lancamento.conta = conta || $scope.lancamento.conta || $scope.contas[0];
                })
                .catch(function (e) {
                    console.log(e);
                });
        };
        
        $scope.changeTipo = function(tipo) {
            planoContas(tipo);
        };
        
        var planoContas = function(tipo) {
            PlanoContaService.findContaResultadoByTipo(tipo.id)
                .then(function (data) {
                    $scope.planoContas = data;
                    if($scope.lancamento.planoConta) {
                        $scope.lancamento.planoConta = ListaService.getPlanoContaValue($scope.planoContas, $scope.lancamento.planoConta.idPlanoConta) || data[0];
                    } else {
                         $scope.lancamento.planoConta  = data[0];
                    }
                })
                .catch(function (e) {
                    console.log(e);
                });
        };
        
        $scope.getTotal = function(lancamento) {
            var valor = lancamento.valor || 0;
            var valorJuros = lancamento.valorJuros || 0;
            var valorMulta = lancamento.valorMulta || 0;
            var valorDesconto = lancamento.valorDesconto || 0;
            return valor + valorJuros + valorMulta - valorDesconto;
        }
        
        $scope.ok = function(form) {
            if (!validarForm(form)) return;
            var lancamento = setData($scope.lancamento, $scope.data);
            $modalInstance.close(lancamento);            
        };
        
        $scope.cancel = function () {
            $modalInstance.dismiss('cancel');
        };
        
        var setData = function(lancamento, data) {
            lancamento.dataEmissao = moment();
            lancamento.dataVencimento = lancamento.dataLancamento;
            return lancamento;
        }

        var validarForm = function (form) {
            if (form.competencia.$error.required) {
                alert('Preencha a competência do lançamento!');
                return false;
            }       
            if (form.competencia.$modelValue && !moment(form.competencia.$modelValue).isValid()) {
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
