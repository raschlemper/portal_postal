'use strict';

app.controller('ModalLancamentoProgramadoEditarController', 
    ['$scope', '$filter', 'ContaService', 'PlanoContaService', 'CentroCustoService', 'TipoDocumentoService', 'TipoFormaPagamentoService', 'FavorecidoService', 
     'ModalService', 'DatePickerService', 'LancamentoHandler', 'ListaService', 'FinanceiroValidation', 'LISTAS', 'MESSAGES',
    function ($scope, $filter, ContaService, PlanoContaService, CentroCustoService, TipoDocumentoService, TipoFormaPagamentoService, FavorecidoService, 
        ModalService, DatePickerService, LancamentoHandler, ListaService, FinanceiroValidation, LISTAS, MESSAGES) {

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
            $scope.lancamentoProgramado.tipo = ($scope.lancamentoProgramado && $scope.lancamentoProgramado.tipo) || $scope.tipo;
            $scope.lancamentoProgramado.frequencia = ($scope.lancamentoProgramado && $scope.lancamentoProgramado.frequencia) || $scope.frequencias[0];
            $scope.lancamentoProgramado.situacao = ($scope.lancamentoProgramado && $scope.lancamentoProgramado.situacao) || $scope.situacoes[0];
            $scope.lancamentoProgramado.numeroParcela = getNumeroParcela($scope.lancamentoProgramado); 
//            $scope.lancamento = $scope.getLancamento($scope.lancamentoProgramado, null, $scope.modelos[2]);
            getTitle($scope.lancamentoProgramado);
            contas();
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
            if(lancamentoProgramado.parcelas && lancamentoProgramado.parcelas.length) return 0;
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
                    $scope.lancamentoProgramado.conta = $scope.lancamentoProgramado.conta || $scope.conta || $scope.contas[0];
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
                        $scope.lancamentoProgramado.planoConta = ListaService.getPlanoContaValue($scope.planoContas, $scope.lancamentoProgramado.planoConta.idPlanoConta);
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
                        $scope.lancamentoProgramado.centroCusto = ListaService.getCentroCustoValue($scope.centroCustos, $scope.lancamentoProgramado.centroCusto.idCentroCusto);
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
                    favorecidos();
                })
                .catch(function (e) {
                    console.log(e);
                });
        };
        
        var favorecidos = function() {
            FavorecidoService.getAll()
                .then(function (data) {
                    $scope.favorecidos = data;
                })
                .catch(function (e) {
                    console.log(e);
                });
        };
        
        $scope.selectFavorecido = function(favorecido) {
            $scope.lancamentoProgramado.favorecido = favorecido;
        }
        
        $scope.setDataCompetencia = function(lancamentoProgramado) {
            if(lancamentoProgramado.dataCompetencia) return;
            lancamentoProgramado.dataCompetencia = lancamentoProgramado.dataLancamento || lancamentoProgramado.dataVencimento;
        };
        
        $scope.gerar = function(form, lancamentoProgramado) {
            if(!$scope.validaConta(lancamentoProgramado.conta)) return;
            if(!$scope.validarRateio(lancamentoProgramado.valor, lancamentoProgramado)) return;  
            if(!$scope.validarForm(form, lancamentoProgramado)) return;   
            $scope.lancamento = $scope.getLancamento($scope.lancamentoProgramado, null, $scope.modelos[2]);  
            $scope.goToLancar();
        };
        
        $scope.gerarParcela = function(form, lancamentoProgramado, parcela) {
            if(!$scope.validaConta(lancamentoProgramado.conta)) return;
            if(!$scope.validarRateio(lancamentoProgramado.valor, lancamentoProgramado)) return;  
            if(!$scope.validarForm(form, lancamentoProgramado)) return;   
            $scope.lancamento = $scope.getLancamento($scope.lancamentoProgramado, parcela, $scope.modelos[4]);  
            $scope.goToLancar();
        };
        
        $scope.ok = function(form, lancamentoProgramado) {
            if(!$scope.validaConta(lancamentoProgramado.conta)) return;
            if(!$scope.validarRateio(lancamentoProgramado.valor, lancamentoProgramado)) return;  
            if(!$scope.validarForm(form, lancamentoProgramado)) return;
//            delete lancamentoProgramado.parcelas;
            delete lancamentoProgramado.lancamentos;
            lancamentoProgramado.gerarLancamento = false;     
            $scope.close(lancamentoProgramado);
        };
        
        // ***** AJUSTAR ***** //
                
        $scope.getLancamento = function(lancamentoProgramado, parcela, modelo) {
            var lancamento = LancamentoHandler.handle(lancamentoProgramado);
            lancamento.planoConta = lancamentoProgramado.planoConta;
            lancamento.centroCusto = lancamentoProgramado.centroCusto;
            lancamento.tipo = lancamentoProgramado.tipo;
            lancamento.numero = (parcela && parcela.numero) || lancamentoProgramado.numero;
            lancamento.numeroParcela = (parcela && parcela.numero) || lancamentoProgramado.numeroParcela;
            lancamento.dataCompetencia = (parcela && parcela.dataCompetencia) || lancamentoProgramado.dataCompetencia;
            lancamento.dataVencimento = (parcela && parcela.dataVencimento) || lancamentoProgramado.dataVencimento;
            lancamento.valor = (parcela && parcela.valor) || lancamentoProgramado.valor;
            lancamento.situacao = (lancamentoProgramado && lancamentoProgramado.situacao) || $scope.situacoes[0]; 
            lancamento.modelo = modelo;
//            lancamento.parcelas = lancamentoProgramado.parcelas;
            lancamento.rateios = lancamentoProgramado.rateios;
            return lancamento;
        };
        
        // ***** VALIDAR ***** //
        
        $scope.validaConta = function(conta) {
            return FinanceiroValidation.contaEncerrada(conta);
        };
        
        $scope.validarPlanoConta = function(planoConta) {
            return FinanceiroValidation.planoContaResultado(planoConta);
        };
        
        $scope.validarCentroCusto = function(centroCusto) {
            return FinanceiroValidation.centroCustoResultado(centroCusto);
        };  

        $scope.validarRateio = function(valor, lancamentoProgramado) {
            if (!lancamentoProgramado.rateios || !lancamentoProgramado.rateios.length) return true;
//            var valor = lancamentoProgramado.valor; 
//            if(lancamentoProgramado.quantidadeParcela) { 
//                valor = lancamentoProgramado.valor / lancamentoProgramado.quantidadeParcela; 
//                valor = parseFloat(valor.toFixed(2));
//            }
            if(!FinanceiroValidation.rateioSaldo(valor, lancamentoProgramado.rateios)) return false;
            _.map(lancamentoProgramado.rateios, function(rateio) {
                if(!$scope.validarPlanoConta(rateio.planoConta)) { return false; }
                if(!$scope.validarCentroCusto(rateio.centroCusto)) { return false; }
            });  
            return true;
        };   
        
        $scope.validarForm = function (form, lancamentoProgramado) {  
            if(!lancamentoProgramado.rateios || !lancamentoProgramado.rateios.length) {
                if(!$scope.validarPlanoConta(lancamentoProgramado.planoConta)) {
                    return false;
                }
                if(!$scope.validarCentroCusto(lancamentoProgramado.centroCusto)) {
                    return false;
                }
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
