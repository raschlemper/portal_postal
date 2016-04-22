'use strict';

app.controller('ModalEditarLancamentoProgramadoController', ['$scope', '$modalInstance', 'conta', 'lancamentoProgramado', 'ContaService', 'PlanoContaService', 'TipoDocumentoService', 'TipoFormaPagamentoService', 'DatePickerService', 'ListaService', 'LISTAS',
    function ($scope, $modalInstance, conta, lancamentoProgramado, ContaService, PlanoContaService, TipoDocumentoService, TipoFormaPagamentoService, DatePickerService, ListaService, LISTAS) {

        var init = function () {  
            $scope.datepickerCompetencia = angular.copy(DatePickerService.default); 
            $scope.datepickerVencimento = angular.copy(DatePickerService.default); 
            $scope.datepickerLancamento = angular.copy(DatePickerService.default); 
            $scope.tipos = LISTAS.lancamento; 
            $scope.frequencias = LISTAS.frequencia;
            $scope.situacoes = LISTAS.situacaoLancamentoProgramado;
            $scope.lancamentoProgramado = {
                idLancamentoProgramado: (lancamentoProgramado && lancamentoProgramado.idLancamentoProgramado) || null,
                conta: conta || (lancamentoProgramado && lancamentoProgramado.conta) || null,
                planoConta: (lancamentoProgramado && lancamentoProgramado.planoConta) || null,  
                tipo: (lancamentoProgramado && lancamentoProgramado.tipo) || $scope.tipos[0],
                favorecido: (lancamentoProgramado && lancamentoProgramado.favorecido) || null,
                numero: (lancamentoProgramado && lancamentoProgramado.numero) || null,     
                documento: (lancamentoProgramado && lancamentoProgramado.documento) || null,  
                formaPagamento: (lancamentoProgramado && lancamentoProgramado.formaPagamento) || null, 
                frequencia: (lancamentoProgramado && lancamentoProgramado.frequencia) || $scope.frequencias[0],
                numeroParcela: (lancamentoProgramado && lancamentoProgramado.numeroParcela) || null,  
                competencia: (lancamentoProgramado && lancamentoProgramado.competencia) || null,  
                dataEmissao: (lancamentoProgramado && lancamentoProgramado.dataEmissao) || null,
                dataVencimento: (lancamentoProgramado && lancamentoProgramado.dataVencimento) || null,
                dataLancamento: (lancamentoProgramado && lancamentoProgramado.dataLancamento) || null,
                valor: (lancamentoProgramado && lancamentoProgramado.valor) || null,    
                situacao: (lancamentoProgramado && lancamentoProgramado.situacao) || $scope.situacoes[0],
                historico: (lancamentoProgramado && lancamentoProgramado.historico) || null,
                gerarLancamento: false
            };            
            $scope.lancamentoProgramado.numero = $scope.lancamentoProgramado.numero + '-' + $scope.lancamentoProgramado.numeroParcela;
            getTitle();
            contas();
            tipoDocumento();
            tipoFormaPagamento();
            $scope.changeTipo($scope.lancamentoProgramado.tipo);
        };
        
        $scope.editConta = function() {
            return (lancamentoProgramado && lancamentoProgramado.idLancamentoProgramado);
        }
        
        var getTitle = function() {
            if(lancamentoProgramado && lancamentoProgramado.idLancamentoProgramado) { $scope.title = "Editar Lançamento Programado"; }
            else { $scope.title = "Inserir Novo Lançamento Programado"; }
        };
        
        var contas = function() {
            ContaService.getAll()
                .then(function (data) {
                    $scope.contas = data;
                    $scope.lancamentoProgramado.conta = conta || $scope.lancamentoProgramado.conta || $scope.contas[0];
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
                    if($scope.lancamentoProgramado.planoConta) {
                        $scope.lancamentoProgramado.planoConta = ListaService.getPlanoContaValue($scope.planoContas, $scope.lancamentoProgramado.planoConta.idPlanoConta) || data[0];
                    } else {
                         $scope.lancamentoProgramado.planoConta  = data[0];
                    }
                })
                .catch(function (e) {
                    console.log(e);
                });
        };
        
        var tipoDocumento = function() {
            TipoDocumentoService.getAll()
                .then(function (data) {
                    $scope.documentos = data;
                    $scope.lancamentoProgramado.documento = $scope.lancamentoProgramado.documento || $scope.documentos[1];
                })
                .catch(function (e) {
                    console.log(e);
                });
        };
        
        var tipoFormaPagamento = function() {
            TipoFormaPagamentoService.getAll()
                .then(function (data) {
                    $scope.formaPagamentos = data;
                    $scope.lancamentoProgramado.formaPagamento = $scope.lancamentoProgramado.formaPagamento || $scope.formaPagamentos[1];
                })
                .catch(function (e) {
                    console.log(e);
                });
        };
        
        $scope.ok = function(form) {
            if (!validarForm(form)) return;
            $scope.lancamentoProgramado.gerarLancamento = false;
            $modalInstance.close($scope.lancamentoProgramado);            
        };
        
        $scope.gerarLancamento = function(form) {
            if (!validarForm(form)) return;
            $scope.lancamentoProgramado.gerarLancamento = true;
            $modalInstance.close($scope.lancamentoProgramado);            
        };
        
        $scope.cancel = function () {
            $modalInstance.dismiss('cancel');
        };

        var validarForm = function (form) {
            if (form.competencia.$error.required) {
                alert('Preencha a competência do lançamento programado!');
                return false;
            }       
            if (form.competencia.$modelValue && !moment(form.competencia.$modelValue).isValid()) {
                alert('A competência do lançamento programado não é válida!');
                return false;
            } 
            if (form.dataVencimento.$error.required) {
                alert('Preencha a data de vencimento do lançamento programado!');
                return false;
            }       
            if (form.dataVencimento.$modelValue && !moment(form.dataVencimento.$modelValue).isValid()) {
                alert('A data de vencimento do lançamento programado não é válida!');
                return false;
            } 
            if(form.idLancamentoProgramado.$modelValue) {
                if (form.dataLancamento.$error.required) {
                    alert('Preencha a data do lançamento programado!');
                    return false;
                }       
                if (form.dataLancamento.$modelValue && !moment(form.dataLancamento.$modelValue).isValid()) {
                    alert('A data do lançamento programado não é válida!');
                    return false;
                }  
            }
            if (form.valor.$error.required) {
                alert('Preencha o valor do lançamento programado!');
                return false;
            }
            if (form.historico.$error.required) {
                alert('Preencha o histórico do lançamento programado!');
                return false;
            }
            return true;
        }     

        init();

    }]);
