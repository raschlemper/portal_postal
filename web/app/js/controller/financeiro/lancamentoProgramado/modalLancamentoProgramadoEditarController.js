'use strict';

app.controller('ModalLancamentoProgramadoEditarController', ['$scope', 'ContaService', 'PlanoContaService', 'CentroCustoService', 'TipoDocumentoService', 'TipoFormaPagamentoService', 'ModalService', 'DatePickerService', 'ListaService', 'LISTAS', 'MESSAGES',
    function ($scope, ContaService, PlanoContaService, CentroCustoService, TipoDocumentoService, TipoFormaPagamentoService, ModalService, DatePickerService, ListaService, LISTAS, MESSAGES) {

        var init = function () {  
            $scope.datepickerCompetencia = angular.copy(DatePickerService.default); 
            $scope.datepickerVencimento = angular.copy(DatePickerService.default); 
            $scope.datepickerLancamento = angular.copy(DatePickerService.default); 
            $scope.tipos = LISTAS.lancamento; 
            $scope.frequencias = LISTAS.frequencia;
            $scope.situacoes = LISTAS.situacaoLancamentoProgramado;
            $scope.situacoesLancamento = LISTAS.situacaoLancamento;
            $scope.modelos = LISTAS.modeloLancamento;
            $scope.statusConta = LISTAS.statusConta;
            $scope.lancamentoProgramado = $scope.lancamentoProgramado || {}; 
            $scope.lancamentoProgramado.tipo = ($scope.lancamentoProgramado && $scope.lancamentoProgramado.tipo) || tipo;
            $scope.lancamentoProgramado.frequencia = ($scope.lancamentoProgramado && $scope.lancamentoProgramado.frequencia) || $scope.frequencias[0];
            $scope.lancamentoProgramado.situacao = ($scope.lancamentoProgramado && $scope.lancamentoProgramado.situacao) || $scope.situacoes[0];
            $scope.lancamentoProgramado.numeroParcela = getNumeroParcela($scope.lancamentoProgramado); 
            $scope.lancamento = $scope.getLancamento($scope.lancamentoProgramado, null, $scope.modelos[2]);
            getTitle($scope.lancamentoProgramado);
            contas();
        };
        
        // ***** NAVEGAR ***** //
        
        var initStep = function(lancamentoProgramado) {
            if(lancamentoProgramado && lancamentoProgramado.quantidadeParcela) {
                $scope.goToParcelar();
            } else {            
                $scope.goToEditar(); 
            }
        };
        
        // ***** CONTROLLER ***** //
        
        var getTitle = function(lancamentoProgramado) {
            if(lancamentoProgramado && lancamentoProgramado.idLancamentoProgramado) { 
                $scope.title = MESSAGES.lancamento.programar.title.EDITAR + " " + lancamentoProgramado.tipo.descricao; 
            } else { 
                $scope.title = MESSAGES.lancamento.programar.title.INSERIR + " " + lancamentoProgramado.tipo.descricao;
            } 
        };
        
        var getNumeroParcela = function(lancamentoProgramado) {
            if(!lancamentoProgramado) return 1;
            var lancamento = _.max(lancamentoProgramado.lancamentos, function(lancamento){ 
                return lancamento.numeroParcela; 
            });
            if(lancamento.numeroParcela) return lancamento.numeroParcela + 1;
            return 1;
        }
        
        var contas = function() {
            ContaService.getAll()
                .then(function (data) {
                    $scope.contas = data;
                    $scope.lancamentoProgramado.conta = $scope.lancamentoProgramado.conta || conta || $scope.contas[0];
                    planoContas($scope.lancamentoProgramado.tipo);
                })
                .catch(function (e) {
                    console.log(e);
                });
        }; 
        
        var planoContas = function(tipo) {
            PlanoContaService.getStructureByTipo(tipo.id)
                .then(function (data) {
                    $scope.planoContas = data;
                    PlanoContaService.estrutura($scope.planoContas);
                    $scope.planoContas = PlanoContaService.flatten($scope.planoContas);
//                    $scope.planoContas = criarPlanoContasLista($scope.planoContas);
                    if($scope.lancamentoProgramado.planoConta) {
                        $scope.lancamentoProgramado.planoConta = ListaService.getPlanoContaValue($scope.planoContas, $scope.lancamentoProgramado.planoConta.idPlanoConta) || $scope.planoContas[0];
                    } else {
                         $scope.lancamentoProgramado.planoConta  = $scope.planoContas[0];
                    }
                    centroCustos();
                })
                .catch(function (e) {
                    console.log(e);
                });
        };
        
        var centroCustos = function() {
            CentroCustoService.getStructure()
                .then(function (data) {
                    $scope.centroCustos = data;
                    CentroCustoService.estrutura($scope.centroCustos);
                    $scope.centroCustos = CentroCustoService.flatten($scope.centroCustos);
//                    $scope.centroCustos = criarCentroCustosLista($scope.centroCustos);
                    if($scope.lancamentoProgramado.centroCusto && $scope.lancamentoProgramado.centroCusto.idCentroCusto) {
                        $scope.lancamentoProgramado.centroCusto = ListaService.getCentroCustoValue($scope.centroCustos, $scope.lancamentoProgramado.centroCusto.idCentroCusto) || $scope.centroCustos[0];
                    } 
                    tipoDocumento();
                })
                .catch(function (e) {
                    console.log(e);
                });
        };
        
        var criarPlanoContasLista = function(data) {
            return _.filter(data, function(planoConta) { 
                return !planoConta.ehGrupo;
            });            
        }
        
        var criarCentroCustosLista = function(data) {
            return _.filter(data, function(centroCusto) { 
                return !centroCusto.ehGrupo;
            });            
        }
        
        var tipoDocumento = function() {
            TipoDocumentoService.getAll()
                .then(function (data) {
                    $scope.documentos = data;
                    $scope.lancamentoProgramado.documento = $scope.lancamentoProgramado.documento || $scope.documentos[1];                    
                    tipoFormaPagamento();
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
                    initStep($scope.lancamentoProgramado);  
                })
                .catch(function (e) {
                    console.log(e);
                });
        };
        
        $scope.ok = function(form, lancamentoProgramado) {
            if(!$scope.validaConta(lancamentoProgramado.conta)) return;
            if(!$scope.validarForm(form, lancamentoProgramado)) return;
            delete lancamentoProgramado.parcelas;
            delete lancamentoProgramado.lancamentos;
            lancamentoProgramado.gerarLancamento = false;     
            $scope.close(lancamentoProgramado);
        };
        
        $scope.cancel = function () {
            $scope.cancel();
        };
        
        // ***** AJUSTAR ***** //
                
        $scope.getLancamento = function(lancamentoProgramado, parcela, modelo) {
            var lancamento = {
                conta: lancamentoProgramado.conta,
                planoConta: lancamentoProgramado.planoConta,
                centroCusto: lancamentoProgramado.centroCusto,
                tipo: lancamentoProgramado.tipo,
                favorecido: lancamentoProgramado.favorecido,
                numero: (parcela && parcela.numero) || lancamentoProgramado.numero,
                numeroParcela: (parcela && parcela.numeroParcela) || lancamentoProgramado.numeroParcela,
                dataCompetencia: (parcela && parcela.dataCompetencia) || lancamentoProgramado.dataCompetencia,
                dataEmissao: lancamentoProgramado.dataEmissao || moment(),
                dataVencimento: (parcela && parcela.dataVencimento) || lancamentoProgramado.dataVencimento,
                dataLancamento: null,
                dataCompensacao: null,
                valor: (parcela && parcela.valor) || lancamentoProgramado.valor,
                valorDesconto: 0,
                valorJuros: 0,
                valorMulta: 0,
                situacao: $scope.situacoesLancamento[0],
                modelo: modelo,
                historico: '(' + modelo.descricao + ') ' + lancamentoProgramado.historico,
                observacao: null,
                rateios: []
            }  
            return lancamento;
        };
        
        // ***** VALIDAR ***** //
        
        $scope.validaConta = function(conta) {
            if(conta.status.id === $scope.statusConta[1].id) {
                modalMessage(MESSAGES.conta.info.CONTA_ENCERRADO);
                return false;
            };
            return true;
        };
        
        $scope.validarPlanoConta = function(planoConta) {
            if(!planoConta) return true;
            if(planoConta.ehGrupo) {
                alert(MESSAGES.planoConta.info.NAO_PERMITE_GRUPO);
                return false;
            }
            return true;
        };
        
        $scope.validarCentroCusto = function(centroCusto) {
            if(!centroCusto) return true;
            if(centroCusto.ehGrupo) {
                alert(MESSAGES.centroCusto.info.NAO_PERMITE_GRUPO);
                return false;
            }
            return true;
        };
        
        $scope.validarForm = function (form, lancamentoProgramado) {  
            if(!$scope.validarPlanoConta(lancamentoProgramado.planoConta)) {
                return false;
            }
            if(!$scope.validarCentroCusto(lancamentoProgramado.centroCusto)) {
                return false;
            }
            if (form.dataCompetencia.$error.required) {
                alert(MESSAGES.lancamento.programar.validacao.DATA_COMPETENCIA_REQUERIDA);
                return false;
            }       
            if (form.dataCompetencia.$modelValue && !moment(form.dataCompetencia.$modelValue).isValid()) {
                alert(MESSAGES.lancamento.programar.validacao.DATA_COMPETENCIA_VALIDA);
                return false;
            } 
            if (form.dataVencimento.$error.required) {
                alert(MESSAGES.lancamento.programar.validacao.DATA_LANCAMENTO_REQUERIDA);
                return false;
            }       
            if (form.dataVencimento.$modelValue && !moment(form.dataVencimento.$modelValue).isValid()) {
                alert(MESSAGES.lancamento.programar.validacao.DATA_LANCAMENTO_VALIDA);
                return false;
            } 
            if (form.valor.$error.required) {
                alert(MESSAGES.lancamento.programar.validacao.VALOR_REQUERIDA);
                return false;
            }
            if (form.historico.$error.required) {
                alert(MESSAGES.lancamento.programar.validacao.HISTORICO_REQUERIDA);
                return false;
            }
            return true;
        };
        
        // ***** MODAL ***** //
                
        var modalMessage = function(message) {
            ModalService.modalMessage(message);
        }; 

        init();

    }]);
