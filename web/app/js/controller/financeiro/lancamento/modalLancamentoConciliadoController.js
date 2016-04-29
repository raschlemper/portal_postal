'use strict';

app.controller('ModalLancamentoConciliadoController', ['$scope', '$modalInstance', 'ContaService', 'PlanoContaService', 'DatePickerService', 'LISTAS',
    function ($scope, $modalInstance, ContaService, PlanoContaService, DatePickerService, LISTAS) {

        var init = function () {  
            $scope.datepickerCompetencia = angular.copy(DatePickerService.default); 
            $scope.datepickerLancamento = angular.copy(DatePickerService.default); 
            $scope.tipos = LISTAS.lancamento;
            $scope.situacoes = LISTAS.situacaoLancamento;
            $scope.lancamentoConciliado = {};             
            getTitle();
            contas();
            $scope.changeTipo($scope.lancamento.tipo);
        };
        
        var getTitle = function() {
            $scope.title = "Inserir Novo Lançamento de Conciliado";
        };
        
        var contas = function() {
            ContaService.getAll()
                .then(function (data) {
                    $scope.contas = data;
                    $scope.lancamentoConciliado.conta = $scope.contas[0];
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
        
        $scope.ok = function(form) {
            if (!validarForm(form)) return;
            $modalInstance.close($scope.lancamentoConciliado);            
        };
        
        $scope.cancel = function () {
            $modalInstance.dismiss('cancel');
        };

        var validarForm = function (form) {
            if (form.competencia.$error.required) {
                alert('Preencha a competência do lançamento conciliado!');
                return false;
            }        
            if (form.competencia.$modelValue && !moment(form.competencia.$modelValue).isValid()) {
                alert('A competência do lançamento conciliado não é válida!');
                return false;
            }    
            if (form.dataLancamento.$error.required) {
                alert('Preencha a data do lançamento conciliado!');
                return false;
            }       
            if (form.dataLancamento.$modelValue && !moment(form.dataLancamento.$modelValue).isValid()) {
                alert('A data do lançamento conciliado não é válida!');
                return false;
            }    
            if (form.valor.$error.required) {
                alert('Preencha o valor do lançamento conciliado!');
                return false;
            }
            if (form.historico.$error.required) {
                alert('Preencha o histórico do lançamento conciliado!');
                return false;
            }
            return true;
        }     

        init();

    }]);
