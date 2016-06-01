'use strict';

app.controller('ModalLancamentoEditarController', ['$scope', '$modalInstance', 'conta', 'lancamento', 'tipo', 'anexo', 'ContaService', 'PlanoContaService', 'CentroCustoService', 'LancamentoConciliadoService', 'LancamentoAnexoService', 'ModalService', 'DatePickerService', 'ListaService', 'LISTAS', 'MESSAGES',
    function ($scope, $modalInstance, conta, lancamento, tipo, anexo, ContaService, PlanoContaService, CentroCustoService, LancamentoConciliadoService, LancamentoAnexoService, ModalService, DatePickerService, ListaService, LISTAS, MESSAGES) {

        var init = function () {  
            $scope.datepickerCompetencia = angular.copy(DatePickerService.default); 
            $scope.datepickerLancamento = angular.copy(DatePickerService.default); 
            $scope.modelos = LISTAS.modeloLancamento;
            $scope.situacoes = LISTAS.situacaoLancamento;
            $scope.statusConta = LISTAS.statusConta;
            $scope.lancamento = lancamento || {};
            $scope.lancamento.rateios = (lancamento && lancamento.rateios) || [];
            $scope.lancamento.tipo = (lancamento && lancamento.tipo) || tipo;
            $scope.lancamento.situacao = (lancamento && lancamento.situacao) || $scope.situacoes[0];
            $scope.lancamento.modelo = (lancamento && lancamento.modelo) || $scope.modelos[0];
            getTitle(tipo);
            contas();
            centroCustos();
            planoContas($scope.lancamento.tipo);  
        };
                
        // ***** NAVEGAR ***** //  
                
        var initStep = function(lancamento) {      
//            if(anexo){                
//                $scope.anexar(lancamento);
//            } else {
                if(existRateio(lancamento)) { ratear(lancamento); }
                else { goToEditar(); }
//            }
        };
        
        var existRateio = function(lancamento) {
            return lancamento && lancamento.rateios && lancamento.rateios.length;
        };
        
        var goToEditar = function() {
            $scope.stepFrom = angular.copy($scope.stepTo);
            $scope.stepTo = 'editar';             
        };
        
        var goToRatear = function() {
            $scope.stepFrom = angular.copy($scope.stepTo); 
            $scope.stepTo = 'ratear'; 
        };
        
        var goToAnexar = function() {
            $scope.stepFrom = angular.copy($scope.stepTo); 
            $scope.stepTo = 'anexar'; 
            //anexos(lancamento.idLancamento);
        };
                
        // ***** CONTROLLER ***** //  
        
        var getTitle = function(tipo) {
            if(lancamento && lancamento.idLancamento) { 
                $scope.title = MESSAGES.lancamento.title.EDITAR + " " + tipo.descricao; 
            } else { 
                $scope.title = MESSAGES.lancamento.title.INSERIR + " " + tipo.descricao; 
            }
            $scope.titleRatear = MESSAGES.lancamento.ratear.title.INSERIR;
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
                    initStep($scope.lancamento);
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
        
        $scope.ok = function(form, lancamento) {
            if(!validaConta(lancamento.conta)) return;
            if (!validarForm(form, lancamento)) return;
            var lancamento = ajustarDados(lancamento);
            var data = moment(lancamento.dataLancamento).format('YYYY-MM-DD HH:mm:ss');
            LancamentoConciliadoService.getByData(data)
                .then(function(data) {  
                    if(data.length) {
                        modalConfirmarConciliado().then(function() {
                            $modalInstance.close(lancamento);
                        });
                    } else { $modalInstance.close(lancamento); }
                })
                .catch(function(e) {
                    modalMessage(e);
                });             
        };
        
        $scope.cancel = function () {
            $modalInstance.dismiss('cancel');
        };
                
        // ***** RATEAR ***** //  
        
        $scope.ratear = function(form, lancamento) {
            if(!validaConta(lancamento.conta)) return;
            ratear(lancamento);
        };
        
        var ratear = function(lancamento) {       
            goToRatear();
            lancamento.rateios = lancamento.rateios || [];
            criarRateiosLista(lancamento.rateios);
            setRateioDefault(lancamento, null);            
        }
        
        $scope.cancelarRatear = function() {
            goToEditar();
            $scope.lancamento.rateios = [];
        };
        
        $scope.salvarRateio = function(lancamento, rateio) {
            if(!$scope.validarPlanoConta(rateio.planoConta)) return;
            if(!$scope.validarCentroCusto(rateio.centroCusto)) return;
            var saldo = saldoRateio(lancamento);
            if(saldo + rateio.valor > lancamento.valor) {
                modalMessage(MESSAGES.lancamento.ratear.validacao.SALDO_INCORRETO);
                return;
            }
            $scope.lancamento.rateios.push(rateio);
            setRateioDefault(lancamento, rateio);
        };
        
        $scope.editarRateio = function(lancamento, rateio, index) {
            $scope.rateio = rateio;
            $scope.removerRateio(index);
            setRateioDefault(lancamento, rateio);
        };
        
        $scope.removerRateio = function(lancamento, rateio, index) {     
            $scope.lancamento.rateios.splice(index, 1); 
            setRateioDefault(lancamento, rateio);
        };
        
        var setRateioDefault = function(lancamento, rateio) {
            $scope.rateio = {};
            $scope.rateio.planoConta = (rateio && rateio.planoConta) || $scope.planoContas[0];
            $scope.rateio.centroCusto = (rateio && rateio.centroCusto) || null;
            $scope.rateio.valor = lancamento.valor - saldoRateio(lancamento);
        };
        
        var saldoRateio = function(lancamento) {            
            var saldo = 0;
            _.map(lancamento.rateios, function(rateio) {
                saldo += rateio.valor;
            });
            return saldo;
        };
        
        var criarRateiosLista = function(rateios) {
            return _.map(rateios, function(rateio) {                 
                if(rateio.planoConta && rateio.planoConta.idPlanoConta) { 
                    rateio.planoConta = ListaService.getPlanoContaValue($scope.planoContas, rateio.planoConta.idPlanoConta); 
                }                 
                if(rateio.centroCusto && rateio.centroCusto.idCentroCusto) { 
                    rateio.centroCusto = ListaService.getCentroCustoValue($scope.centroCustos, rateio.centroCusto.idCentroCusto); 
                } 
            })
        };
                
        // ***** ANEXAR ***** //  

//        $scope.anexar = function(form, lancamento) {
//            if(!validaConta(lancamento.conta)) return;
//            if (!validarForm(form)) return;         
//            $scope.stepFrom = 'editar'; 
//            $scope.stepTo = 'anexar'; 
//            anexos(lancamento.idLancamento);
//        };
        
//        $scope.setAnexo = function(lancamento, anexo) {            
//            LancamentoAnexoService.upload(lancamento.idLancamento, anexo[0])
//                .done(function (data) {
//                    $scope.anexoFile = null;
//                    anexos(lancamento.idLancamento);
//                }).fail(function (e) {
//                    console.log(e);
//                });
//            anexos(lancamento.idLancamento);           
//        };
        
//        $scope.removeAnexo = function(anexo) {            
//            LancamentoAnexoService.delete(anexo.idLancamentoAnexo);      
//            anexos(lancamento.idLancamento);      
//        };
        
//        $scope.visualizarAnexo = function(anexo) {
//            $scope.contentFile = anexo.anexo;            
//        }
        
//        $scope.voltar = function() {
//            $scope.stepFrom = 'anexar'; 
//            $scope.stepTo = 'editar';             
//        }
        
//        var anexos = function(idLancamento) {
//            LancamentoAnexoService.getLancamento(idLancamento)
//                .then(function (data) {
//                    $scope.anexos = data;
//                })
//                .catch(function (e) {
//                    modalMessage(e);
//                });
//        }
                
        // ***** AJUSTAR ***** // 
        
        var ajustarDados = function(lancamento) {
            lancamento.dataEmissao = moment();
            lancamento.dataVencimento = lancamento.dataLancamento;
            return lancamento;
        };
                
        // ***** VALIDAR ***** //  
        
        var validaConta = function(conta) {
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
        
        var validarRateio = function(lancamento) {
            if (!lancamento.rateios || !lancamento.rateios.length) return true;
            var saldo = saldoRateio(lancamento);
            if(saldo !== lancamento.valor) {
                alert(MESSAGES.lancamento.ratear.validacao.SALDO_INCORRETO);
                return false;                    
            }
            _.map(lancamento.rateios, function(rateio) {
                if($scope.validarPlanoConta(rateio.planoConta)) {
                    return false;
                }
                if(!$scope.validarCentroCusto(rateio.centroCusto)) {
                    return false;
                }
            });  
            return true;
        };

        var validarForm = function (form, lancamento) {
            if(!validarRateio(lancamento)) {
                return false;
            }
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

        var validarFormAnexo = function (form) {
            if (form.file.$error.required) {
                alert(MESSAGES.lancamento.anexar.validacao.ARQUIVO_REQUERIDA);
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
