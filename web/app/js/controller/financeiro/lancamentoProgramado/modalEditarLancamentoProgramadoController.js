'use strict';

app.controller('ModalEditarLancamentoProgramadoController', ['$scope', '$modalInstance', 'conta', 'lancamentoProgramado', 'ContaService', 'PlanoContaService', 'TipoDocumentoService', 'TipoFormaPagamentoService', 'DatePickerService', 'ListaService', 'LISTAS',
    function ($scope, $modalInstance, conta, lancamentoProgramado, ContaService, PlanoContaService, TipoDocumentoService, TipoFormaPagamentoService, DatePickerService, ListaService, LISTAS) {

        var init = function () {  
            $scope.datepicker = DatePickerService.default; 
            $scope.tipos = LISTAS.lancamentoProgramado; 
            $scope.frequencias = LISTAS.frequencia;
            $scope.situacoes = LISTAS.situacao;
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
                quantidadeParcela: (lancamentoProgramado && lancamentoProgramado.quantidadeParcela) || null,
                numeroParcela: (lancamentoProgramado && lancamentoProgramado.numeroParcela) || null,  
                data: (lancamentoProgramado && lancamentoProgramado.data) || null,
                valor: (lancamentoProgramado && lancamentoProgramado.valor) || null,    
                situacao: (lancamentoProgramado && lancamentoProgramado.situacao) || $scope.situacoes[0],
                historico: (lancamentoProgramado && lancamentoProgramado.historico) || null
            };             
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
        
        var tipoDocumento = function(tipo) {
            TipoDocumentoService.findAll(tipo.id)
                .then(function (data) {
                    $scope.documentos = data;
                    if($scope.lancamentoProgramado.documento) {
                        $scope.lancamentoProgramado.documento = ListaService.getPlanoContaValue($scope.documentos, $scope.lancamentoProgramado.documento.idTipoDocumento) || data[0];
                    } else {
                         $scope.lancamentoProgramado.documento  = data[0];
                    }
                })
                .catch(function (e) {
                    console.log(e);
                });
        };
        
        var tipoFormaPagamento = function(tipo) {
            TipoFormaPagamentoService.findAll(tipo.id)
                .then(function (data) {
                    $scope.formaPagamentos = data;
                    if($scope.lancamentoProgramado.formaPagamento) {
                        $scope.lancamentoProgramado.formaPagamento = ListaService.getPlanoContaValue($scope.formaPagamentos, $scope.lancamentoProgramado.formaPagamento.idFormaPagamento) || data[0];
                    } else {
                         $scope.lancamentoProgramado.formaPagamento  = data[0];
                    }
                })
                .catch(function (e) {
                    console.log(e);
                });
        };
        
        $scope.ok = function(form) {
            if (!validarForm(form)) return;
            $modalInstance.close($scope.lancamentoProgramado);            
        };
        
        $scope.gerarLancamento = function(form) {
            if (!validarForm(form)) return;
            $modalInstance.close($scope.lancamentoProgramado, true);            
        };
        
        $scope.cancel = function () {
            $modalInstance.dismiss('cancel');
        };

        var validarForm = function (form) {
            if (form.data.$error.required) {
                alert('Preencha a data do lançamento programado!');
                return false;
            }       
            if (form.data.$modelValue && !moment(form.data.$modelValue).isValid()) {
                alert('A data do lançamento programado não é válida!');
                return false;
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
