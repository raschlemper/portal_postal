'use strict';

app.controller('ModalEditarLancamentoController', ['$scope', '$modalInstance', 'conta', 'lancamento', 'ContaService', 'PlanoContaService', 'DatePickerService', 'ListaService', 'LISTAS',
    function ($scope, $modalInstance, conta, lancamento, ContaService, PlanoContaService, DatePickerService, ListaService, LISTAS) {

        var init = function () {  
            $scope.datepicker = DatePickerService.default; 
            $scope.tipos = LISTAS.lancamento; 
            $scope.situacoes = LISTAS.situacao;
            $scope.lancamento = {
                idLancamento: (lancamento && lancamento.idLancamento) || null,
                conta: conta || (lancamento && lancamento.conta) || null,
                planoConta: (lancamento && lancamento.planoConta) || null,  
                tipo: (lancamento && lancamento.tipo) || $scope.tipos[0],
                favorecido: (lancamento && lancamento.favorecido) || null,
                numero: (lancamento && lancamento.numero) || null,
                data: (lancamento && lancamento.data) || null,
                valor: (lancamento && lancamento.valor) || null,       
                situacao: (lancamento && lancamento.situacao) || $scope.situacoes[0],
                historico: (lancamento && lancamento.historico) || null
            }; 
            if($scope.lancamento.tipo == null) { $scope.tipo = $scope.tipos[0]; }
            else { $scope.tipo = $scope.lancamento.tipo; }
            
            getTitle();
            contas();
            $scope.changeTipo($scope.tipo);
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
        
        $scope.ok = function(form) {
            if (!validarForm(form)) return;
            $modalInstance.close($scope.lancamento);            
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
