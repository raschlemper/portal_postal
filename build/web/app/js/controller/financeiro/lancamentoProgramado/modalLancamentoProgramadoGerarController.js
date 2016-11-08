'use strict';

app.controller('ModalLancamentoProgramadoGerarController', 
    ['$scope', 'LancamentoConciliadoService', 'ConfiguracaoService', 'ListaService', 'ModalService', 'LancamentoHandler', 'LancamentoRateioHandler', 'LISTAS', 'MESSAGES',
    function ($scope, LancamentoConciliadoService, ConfiguracaoService, ListaService, ModalService, LancamentoHandler, LancamentoRateioHandler, LISTAS, MESSAGES) {

        var init = function () {  
            $scope.favorecidos = [];
            getTitle();
            initStep($scope.lancamento);
//            favorecidos();
        };
        
        // ***** NAVEGAR ***** //
        
        var initStep = function(lancamento) {
            if(lancamento && lancamento.rateios && lancamento.rateios.length) {
                $scope.showRatear = true;    
            } 
        };
        
        // ***** CONTROLLER ***** //
        
        var getTitle = function() {
            $scope.titleGerar = $scope.title + " - " + MESSAGES.lancamento.title.GERAR; 
        };
        
//        var favorecidos = function() {
//            FavorecidoService.getAll()
//                .then(function (data) {
//                    $scope.favorecidos = data;
//                })
//                .catch(function (e) {
//                    console.log(e);
//                });
//        };
        
//        $scope.selectFavorecido = function(favorecido) {
//            $scope.lancamento.favorecido = favorecido;
//        }
        
        $scope.lancar = function(form, lancamentoProgramado, lancamento) {
            return validarLancamentoProgramado(form, lancamentoProgramado, lancamento);
        };
        
        var lancar = function(lancamentoProgramado, lancamento) {            
            var data = moment(lancamento.dataLancamento).format('YYYY-MM-DD HH:mm:ss');  
            LancamentoConciliadoService.getByData(data)
                .then(function(data) {  
                    if(data.length) {
                        modalConfirmarConciliado().then(function() {
                            gerarLancar(lancamentoProgramado, lancamento);
                        });
                    } else { gerarLancar(lancamentoProgramado, lancamento); }
                })
                .catch(function(e) {
                    $scope.modalMessage(e);
                });    
        };
        
        var gerarLancar = function(lancamentoProgramado, lancamento) {  
            lancamentoProgramado.gerarLancamento = true;
            lancamentoProgramado.lancamentos = [];
            lancamentoProgramado.lancamentos.push(lancamento);
            $scope.close(lancamentoProgramado);           
        };
        
        // ***** AJUSTAR ***** //
        
        var ajustarLancamento = function(lancamento) { 
            lancamento.historico = '(' + lancamento.modelo.descricao + ') ' + lancamento.historico || "";
            var lancamentoHandle = LancamentoHandler.handle(lancamento);
            lancamentoHandle.rateios = LancamentoRateioHandler.handleList(lancamento.rateios);
            return lancamentoHandle;
        };
        
        // ***** VALIDAR ***** //
        
        var validarLancamentoProgramado = function(form, lancamentoProgramado, lancamento) {
            if(!$scope.validaConta(lancamentoProgramado.conta)) return;
            if(!$scope.validarRateio(lancamento.valor, lancamentoProgramado)) return;  
            if(!$scope.validarForm(form, lancamentoProgramado)) return;   
            if(!validarForm(form, lancamento)) return;     
            ConfiguracaoService.get()
                .then(function(data) {
                    if(!validarLancamentoProgramadoByConfiguracao(data, lancamento)) return;
                    lancamento = ajustarLancamento(lancamento);  
                    lancar(lancamentoProgramado, lancamento);
                })
                .catch(function(e) {
                    modalMessage(e);
                });
        }; 

        var validarLancamentoProgramadoByConfiguracao = function (configuracao, lancamento) {
            if (configuracao.favorecido && !lancamento.favorecido) {
                alert(MESSAGES.lancamento.programar.validacao.FAVORECIDO_REQUERIDA);
                return false;
            } 
            if (configuracao.historico && !lancamento.historico) {
                alert(MESSAGES.lancamento.programar.validacao.HISTORICO_REQUERIDA);
                return false;
            }
            if(!lancamento.rateios || !lancamento.rateios.length) {
                if (configuracao.planoConta && !lancamento.planoConta) {
                    alert(MESSAGES.lancamento.programar.validacao.PLANO_CONTA_REQUERIDA);
                    return false;
                }
                if (configuracao.centroCusto && !lancamento.centroCusto) {
                    alert(MESSAGES.lancamento.programar.validacao.CENTRO_CUSTO_REQUERIDA);
                    return false;
                }
            } else {
                var retorno = true;
                _.map(lancamento.rateios, function(rateio) { 
                    if (retorno && configuracao.planoConta && (!rateio.planoConta || !rateio.planoConta.idPlanoConta)) {
                        alert(MESSAGES.lancamento.programar.validacao.PLANO_CONTA_REQUERIDA);
                        retorno = false;
                    }
                    if (retorno && configuracao.centroCusto && (!rateio.centroCusto || !rateio.centroCusto.idCentroCusto)) {
                        alert(MESSAGES.lancamento.programar.validacao.CENTRO_CUSTO_REQUERIDA);
                        retorno = false;
                    }   
                });
                if(!retorno) return false;
            }
            return true;
        }; 

        var validarForm = function (form, lancamento) {
            if(!lancamento.rateios || !lancamento.rateios.length) {
                if(!$scope.validarPlanoConta(lancamento.planoConta)) {
                    return false;
                }
                if(!$scope.validarCentroCusto(lancamento.centroCusto)) {
                    return false;
                }
            } 
            if (!$scope.validarFavorecido(form.favorecido.$modelValue)) {
                return false;
            }  
            if (form.dataCompetencia.$error.required) {
                alert(MESSAGES.lancamento.validacao.DATA_COMPETENCIA_REQUERIDA);
                return false;
            }       
            if (form.dataCompetencia.$modelValue && !moment(form.dataCompetencia.$modelValue).isValid()) {
                alert(MESSAGES.lancamento.validacao.DATA_COMPETENCIA_VALIDA);
                return false;
            }    
            if (form.dataLancamento.$error.required) {
                alert(MESSAGES.lancamento.validacao.DATA_LANCAMENTO_REQUERIDA);
                return false;
            }       
            if (form.dataLancamento.$modelValue && !moment(form.dataLancamento.$modelValue).isValid()) {
                alert(MESSAGES.lancamento.validacao.DATA_LANCAMENTO_VALIDA);
                return false;
            }    
            if (form.valor.$error.required) {
                alert(MESSAGES.lancamento.validacao.VALOR_REQUERIDA);
                return false;
            }
//            if (form.historico.$error.required) {
//                alert(MESSAGES.lancamento.validacao.HISTORICO_REQUERIDA);
//                return false;
//            }
            return true;
        }; 
        
        // ***** MODAL ***** //
        
        var modalConfirmarConciliado = function() {
            var modalInstance = ModalService.modalConfirmar(MESSAGES.lancamento.info.ALERT, MESSAGES.lancamento.conciliar.info.CONFIRMAR_INCLUIR);
            return modalInstance.result;
        };

        init();

    }]);
