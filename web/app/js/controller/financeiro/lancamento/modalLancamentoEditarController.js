'use strict';

app.controller('ModalLancamentoEditarController', 
    ['$scope', 'ContaService', 'PlanoContaService', 'CentroCustoService', 'LancamentoConciliadoService', 'LancamentoAnexoService', 'ModalService', 
     'DatePickerService', 'ListaService', 'FinanceiroValidation', 'LISTAS', 'MESSAGES',
    function ($scope, ContaService, PlanoContaService, CentroCustoService, LancamentoConciliadoService, LancamentoAnexoService, ModalService, 
        DatePickerService, ListaService, FinanceiroValidation, LISTAS, MESSAGES) {

        var init = function () {  
            $scope.datepickerCompetencia = angular.copy(DatePickerService.default); 
            $scope.datepickerLancamento = angular.copy(DatePickerService.default); 
            $scope.modelos = LISTAS.modeloLancamento;
            $scope.situacoes = LISTAS.situacaoLancamento;
            $scope.statusConta = LISTAS.statusConta;
//            $scope.lancamento = lancamento || {};
            $scope.lancamento.rateios = ($scope.lancamento && $scope.lancamento.rateios) || [];
            $scope.lancamento.tipo = ($scope.lancamento && $scope.lancamento.tipo) || $scope.tipo;
            $scope.lancamento.situacao = ($scope.lancamento && $scope.lancamento.situacao) || $scope.situacoes[0];
            $scope.lancamento.modelo = ($scope.lancamento && $scope.lancamento.modelo) || $scope.modelos[0];
            getTitle($scope.lancamento, $scope.lancamento.tipo);
            contas();
        };
                
        // ***** NAVEGAR ***** //  
                
        var initStep = function(lancamento) {      
//            if($scope.anexo){                
//                $scope.anexar(lancamento);
//            } else {
                if(existRateio(lancamento)) { $scope.goToRatear(); }
                else { $scope.goToEditar(); }
//            }
        };
                
        var existRateio = function(lancamento) {
            return lancamento && lancamento.rateios && lancamento.rateios.length;
        };
                
        // ***** CONTROLLER ***** //  
        
        var getTitle = function(lancamento, tipo) {
            if(lancamento && lancamento.idLancamento) { 
                $scope.title = MESSAGES.lancamento.title.EDITAR + " " + tipo.descricao; 
            } else { 
                $scope.title = MESSAGES.lancamento.title.INSERIR + " " + tipo.descricao; 
            }
        };
        
        var contas = function() {
            ContaService.getAll()
                .then(function (data) {
                    $scope.contas = data;
                    $scope.lancamento.conta = $scope.conta || $scope.lancamento.conta || $scope.contas[0];                    
                    planoContas($scope.lancamento.tipo);
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
                    if($scope.lancamento.planoConta) {
                        $scope.lancamento.planoConta = ListaService.getPlanoContaValue($scope.planoContas, $scope.lancamento.planoConta.idPlanoConta) || $scope.planoContas[0];
                    } else {
                         $scope.lancamento.planoConta  = $scope.planoContas[0];
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
                    if($scope.lancamento.centroCusto && $scope.lancamento.centroCusto.idCentroCusto) {
                        $scope.lancamento.centroCusto = ListaService.getCentroCustoValue($scope.centroCustos, $scope.lancamento.centroCusto.idCentroCusto) || $scope.centroCustos[0];
                    } 
                    initStep($scope.lancamento);  
                })
                .catch(function (e) {
                    console.log(e);
                });
        };
        
        var criarPlanoContasLista = function(data) {
            return _.filter(data, function(planoConta) { 
                return !planoConta.ehGrupo;
            });            
        };
        
        var criarCentroCustosLista = function(data) {
            return _.filter(data, function(centroCusto) { 
                return !centroCusto.ehGrupo;
            });            
        };
        
        $scope.setDataCompetencia = function(lancamento) {
            if(lancamento.dataCompetencia) return;
            lancamento.dataCompetencia = lancamento.dataLancamento;
        };
        
        $scope.ok = function(form, lancamento) {
            if(!validaConta(lancamento.conta)) return;
            if(!validarRateio(lancamento)) return;  
            if (!validarForm(form, lancamento)) return;
            var lancamento = ajustarDados(lancamento);
            var data = moment(lancamento.dataLancamento).format('YYYY-MM-DD HH:mm:ss');
            LancamentoConciliadoService.getByData(data)
                .then(function(data) {  
                    if(data.length) {
                        modalConfirmarConciliado().then(function() {
                            $scope.close(lancamento);
                        });
                    } else { $scope.close(lancamento); }
                })
                .catch(function(e) {
                    modalMessage(e);
                });             
        };
                
        // ***** AJUSTAR ***** // 
        
        var ajustarDados = function(lancamento) {
            lancamento.dataEmissao = moment();
            lancamento.dataVencimento = lancamento.dataLancamento;
            return lancamento;
        };
                
        // ***** VALIDAR ***** //  
        
        var validaConta = function(conta) {
            return FinanceiroValidation.contaEncerrada(conta);
        };
        
        $scope.validarPlanoConta = function(planoConta) {
            return FinanceiroValidation.planoContaResultado(planoConta);
        };
        
        $scope.validarCentroCusto = function(centroCusto) {
            return FinanceiroValidation.centroCustoResultado(centroCusto);
        }; 

        $scope.validarRateio = function(lancamento) {
            if (!lancamento.rateios || !lancamento.rateios.length) return true;
            if(!FinanceiroValidation.rateioSaldo(lancamento.valor, lancamento.rateios)) return false;
            _.map(lancamento.rateios, function(rateio) {
                if(!$scope.validarPlanoConta(rateio.planoConta)) { return false; }
                if(!$scope.validarCentroCusto(rateio.centroCusto)) { return false; }
            });  
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
            if (form.historico.$error.required) {
                alert(MESSAGES.lancamento.validacao.HISTORICO_REQUERIDA);
                return false;
            }
            return true;
        }; 
                
        // ***** MODAL ***** //  
        
        var modalConfirmarConciliado = function() {
            var modalInstance = ModalService.modalConfirmar(MESSAGES.lancamento.info.ALERT, MESSAGES.lancamento.conciliar.info.CONFIRMAR_INCLUIR);
            return modalInstance.result;
        };
        
        var modalMessage = function(message) {
            ModalService.modalMessage(message);
        };

        init();

    }]);
