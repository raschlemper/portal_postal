'use strict';

app.controller('ModalEditarLancamentoController', ['$scope', '$modalInstance', 'conta', 'lancamento', 'ContaService', 'PlanoContaService', 'DatePickerService', 'ListaService', 'LISTAS',
    function ($scope, $modalInstance, conta, lancamento, ContaService, PlanoContaService, DatePickerService, ListaService, LISTAS) {

        var init = function () {  
            $scope.datepickerCompetencia = angular.copy(DatePickerService.default); 
            $scope.datepickerLancamento = angular.copy(DatePickerService.default); 
            $scope.tipos = LISTAS.lancamento; 
            $scope.modelos = LISTAS.modeloLancamento;
            $scope.situacoes = LISTAS.situacaoLancamento;
            $scope.lancamento = {
                idLancamento: (lancamento && lancamento.idLancamento) || null,
                conta: conta || (lancamento && lancamento.conta) || null,
                planoConta: (lancamento && lancamento.planoConta) || null,  
                lancamentoProgramado: (lancamento && lancamento.lancamentoProgramado) || null,  
                tipo: (lancamento && lancamento.tipo) || $scope.tipos[0],
                favorecido: (lancamento && lancamento.favorecido) || null,
                numero: (lancamento && lancamento.numero) || null,
                competencia: (lancamento && lancamento.competencia) || null,
                dataEmissao: (lancamento && lancamento.dataEmissao) || null,
                dataVencimento: (lancamento && lancamento.dataVencimento) || null,
                dataLancamento: (lancamento && lancamento.dataLancamento) || null,
                dataCompensacao: (lancamento && lancamento.dataCompensacao) || null,
                valor: (lancamento && lancamento.valor) || null,       
                situacao: (lancamento && lancamento.situacao) || $scope.situacoes[0],    
                modelo: (lancamento && lancamento.modelo) || $scope.modelos[0],
                historico: (lancamento && lancamento.historico) || null,
                observacao: (lancamento && lancamento.observacao) || null
            }; 
            
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
