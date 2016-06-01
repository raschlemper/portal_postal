'use strict';

app.controller('ModalLancamentoProgramadoEditarController', ['$scope', '$modalInstance', 'conta', 'tipo', 'lancamentoProgramado', 'ContaService', 'PlanoContaService', 'CentroCustoService', 'LancamentoConciliadoService', 'TipoDocumentoService', 'TipoFormaPagamentoService', 'FrequenciaLancamentoService', 'ModalService', 'DatePickerService', 'ListaService', 'LISTAS', 'MESSAGES',
    function ($scope, $modalInstance, conta, tipo, lancamentoProgramado, ContaService, PlanoContaService, CentroCustoService, LancamentoConciliadoService, TipoDocumentoService, TipoFormaPagamentoService, FrequenciaLancamentoService, ModalService, DatePickerService, ListaService, LISTAS, MESSAGES) {

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
            $scope.lancamentoProgramado = lancamentoProgramado || {}; 
            $scope.lancamentoProgramado.tipo = (lancamentoProgramado && lancamentoProgramado.tipo) || tipo;
            $scope.lancamentoProgramado.frequencia = (lancamentoProgramado && lancamentoProgramado.frequencia) || $scope.frequencias[0];
            $scope.lancamentoProgramado.situacao = (lancamentoProgramado && lancamentoProgramado.situacao) || $scope.situacoes[0];
            $scope.lancamentoProgramado.numeroParcela = getNumeroParcela(lancamentoProgramado); 
            getTitle(tipo);
            contas();
            centroCustos();
            tipoDocumento();
            tipoFormaPagamento();
            planoContas($scope.lancamentoProgramado.tipo);
        };
        
        // ***** NAVEGAR ***** //
        
        var initStep = function() {
            if(lancamentoProgramado && lancamentoProgramado.quantidadeParcela) {
                parcelarLancamento(lancamentoProgramado);
            } else {            
                goToEditar(); 
            }
        };
        
        var goToEditar = function() {
            $scope.stepFrom = angular.copy($scope.stepTo);
            $scope.stepTo = 'editar';             
        };
        
        var goToLancar = function() {
            $scope.stepFrom = angular.copy($scope.stepTo);
            $scope.stepTo = 'lancar';             
        };
        
        var goToParcelar = function() {
            $scope.stepFrom = angular.copy($scope.stepTo);
            $scope.stepTo = 'parcelar';             
        };
        
        var goToRatear = function() {
            $scope.stepFrom = angular.copy($scope.stepTo);
            $scope.stepTo = 'ratear';             
        };
        
        $scope.voltar = function() {
            if($scope.stepFrom === 'lancar') {
                goToLancar();                   
            } else if($scope.stepFrom === 'parcelar') {
                goToParcelar();                      
            } else if($scope.stepFrom === 'ratear') {
                goToRatear();            
            } else {
                goToEditar();
            }
        }
        
        // ***** CONTROLLER ***** //
        
        var getTitle = function(tipo) {
            if(lancamentoProgramado && lancamentoProgramado.idLancamentoProgramado) { 
                $scope.title = MESSAGES.lancamento.programar.title.EDITAR + " " + tipo.descricao; 
            } else { 
                $scope.title = MESSAGES.lancamento.programar.title.INSERIR + " " + tipo.descricao;
            } 
            $scope.titleGerar = $scope.title + " - " + MESSAGES.lancamento.title.GERAR; 
            $scope.titleParcelar = $scope.title + " - " + MESSAGES.lancamento.title.PARCELAR; 
            $scope.titleRatear = MESSAGES.lancamento.ratear.title.INSERIR;
        };
        
        var getNumeroParcela = function(lancamentoProgramado) {
            if(!lancamentoProgramado) return 1;
            var lancamento = _.max(lancamentoProgramado.lancamentos, function(lancamento){ 
                return lancamento.numeroParcela; 
            });
            if(lancamento.numeroParcela) return lancamento.numeroParcela + 1;
            return 1;
        }
        
        $scope.editConta = function() {
            return (lancamentoProgramado && lancamentoProgramado.idLancamentoProgramado);
        }
        
        var contas = function() {
            ContaService.getAll()
                .then(function (data) {
                    $scope.contas = data;
                    $scope.lancamentoProgramado.conta = $scope.lancamentoProgramado.conta || conta || $scope.contas[0];
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
                    initStep(); 
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
        
        $scope.ok = function(form, lancamentoProgramado) {
            if(!validaConta(lancamentoProgramado.conta)) return;
            if (!validarForm(form, lancamentoProgramado)) return;
            delete lancamentoProgramado.parcelas;
            delete lancamentoProgramado.lancamentos;
            lancamentoProgramado.gerarLancamento = false;
            $modalInstance.close(lancamentoProgramado);            
        };
        
        $scope.cancel = function () {
            $modalInstance.dismiss('cancel');
        };
        
        // ***** LANÇAR ***** //
        
        $scope.lancar = function(form, lancamentoProgramado, lancamento) {
            if(!validaConta(lancamentoProgramado.conta)) return;
            if (!validarLancamentoForm(form, lancamento)) return;          
            var data = moment(lancamento.dataLancamento).format('YYYY-MM-DD HH:mm:ss');  
            LancamentoConciliadoService.getByData(data)
                .then(function(data) {  
                    if(data.length) {
                        modalConfirmarConciliado().then(function() {
                            lancar(lancamentoProgramado, lancamento);
                        });
                    } else { lancar(lancamentoProgramado, lancamento); }
                })
                .catch(function(e) {
                    modalMessage(e);
                });    
        };
        
        var lancar = function(lancamentoProgramado, lancamento) {  
            if(!lancamento.rateios) {
                if(!$scope.validarValor(lancamento.valor)) return;
                if(!$scope.validarPlanoConta(lancamento.planoConta)) return;
                if(!$scope.validarCentroCusto(lancamento.centroCusto)) return;      
            }
            delete $scope.lancamentoProgramado.parcelas;
            lancamentoProgramado.gerarLancamento = true;
            lancamento = ajustarLancamento(lancamento);
            lancamentoProgramado.lancamentos = [];
            lancamentoProgramado.lancamentos.push(lancamento);
            encerrarLancamentoProgramado(lancamentoProgramado, lancamento);
            $modalInstance.close(lancamentoProgramado);             
        };
        
        var encerrarLancamentoProgramado = function(lancamentoProgramado, lancamento) {
            if(lancamentoProgramado.frequencia.codigo === 'unico') { 
                lancamentoProgramado.situacao = $scope.situacoes[2];
            }
            if(lancamento.numeroParcela === lancamentoProgramado.quantidadeParcela) {
                lancamentoProgramado.situacao = $scope.situacoes[2];                
            }
            
        };
        
        // ***** PROGRAMAR ***** //
        
        $scope.lancarProgramado = function(form, lancamentoProgramado) {
            if(!validaConta(lancamentoProgramado.conta)) return;
            if (!validarForm(form)) return;         
            goToLancar();
            $scope.lancamento = getLancamento(lancamentoProgramado, null, $scope.modelos[2]);
        };
        
        // ***** PARCELAR ***** //
        
        $scope.lancarParcela = function(form, lancamentoProgramado, parcela) {
            if(!validaConta(lancamentoProgramado.conta)) return;
            if (!validarForm(form)) return;           
            goToLancar();
            $scope.lancamento = getLancamento(lancamentoProgramado, parcela, $scope.modelos[4]);
        };
                
        $scope.parcelar = function(form, lancamentoProgramado) {
            if(!validaConta(lancamentoProgramado.conta)) return;
            if (!validarForm(form)) return;
            parcelarLancamento(lancamentoProgramado);
        };
        
        var parcelarLancamento = function(lancamentoProgramado) {            
            goToParcelar();
            $scope.createParcelas(lancamentoProgramado);
        };
        
        $scope.createParcelas = function(lancamentoProgramado) {       
            $scope.lancamentoProgramado.parcelas = [];
            var frequencia = lancamentoProgramado.frequencia;
            var dataCompetencia = lancamentoProgramado.dataCompetencia;
            var dataVencimento = lancamentoProgramado.dataVencimento;
            for(var i=0; i<lancamentoProgramado.quantidadeParcela; i++) {
                var numeroParcela = (i + 1);
                var lancamento = findParcelaBaixada(lancamentoProgramado.lancamentos, numeroParcela);
                var parcela = getParcela(lancamentoProgramado, lancamento, numeroParcela);
                $scope.lancamentoProgramado.parcelas.push(parcela);
                dataCompetencia = FrequenciaLancamentoService.addData(frequencia, dataCompetencia);
                dataVencimento = FrequenciaLancamentoService.addData(frequencia, dataVencimento);
            }
        };
        
        var findParcelaBaixada = function(lancamentos, numeroParcela) {
            return _.find(lancamentos, function(lancamento) { 
                return lancamento.numeroParcela == numeroParcela; 
            });
        };
        
        var getParcela = function(lancamentoProgramado, lancamento, numeroParcela) {
            return {
                numero: lancamentoProgramado.numero,
                numeroParcela: numeroParcela,
                dataCompetencia: lancamentoProgramado.dataCompetencia,
                dataVencimento: lancamentoProgramado.dataVencimento,
                valor: lancamentoProgramado.valor / lancamentoProgramado.quantidadeParcela,
                lancamento: lancamento
            };
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
            setRateioDefault(lancamento);            
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
            setRateioDefault(lancamento);
        };
        
        $scope.editarRateio = function(rateio, index) {
            $scope.rateio = rateio;
            $scope.removerRateio(index);
        };
        
        $scope.removerRateio = function(index) {     
            $scope.lancamento.rateios.splice(index, 1);
        };
        
        var setRateioDefault = function(lancamento) {
            $scope.rateio = {};
            $scope.rateio.planoConta = $scope.planoContas[0];
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
        
        // ***** AJUSTAR ***** //
                
        var getLancamento = function(lancamentoProgramado, parcela, modelo) {
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
                observacao: null
            }  
            return lancamento;
        };
        
        var ajustarLancamento = function(lancamento) { 
            lancamento.conta = { idConta: lancamento.conta.idConta };
            lancamento.planoConta = { idPlanoConta: lancamento.planoConta.idPlanoConta };
            if(lancamento.centroCusto) {
                lancamento.centroCusto = { idCentroCusto: lancamento.centroCusto.idCentroCusto };
            }
            lancamento.tipo = lancamento.tipo.id;
            lancamento.dataCompetencia = lancamento.dataCompetencia || moment();
            lancamento.dataEmissao = lancamento.dataEmissao || moment();
            lancamento.dataVencimento = lancamento.dataVencimento || moment();
            lancamento.dataLancamento = lancamento.dataLancamento || moment();
            lancamento.situacao = lancamento.situacao.id;
            lancamento.modelo = lancamento.modelo.id;
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
        }
        
        $scope.validarCentroCusto = function(centroCusto) {
            if(!centroCusto) return true;
            if(centroCusto.ehGrupo) {
                alert(MESSAGES.centroCusto.info.NAO_PERMITE_GRUPO);
                return false;
            }
            return true;
        }
        
        var validarRateio = function(lancamento) {
            if (!lancamento.rateios || !lancamento.rateios.length) return true;
            var saldo = saldoRateio();
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
        
        var validarForm = function (form, lancamentoProgramado) {  
            if($scope.validarPlanoConta(lancamentoProgramado.planoConta)) {
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

        var validarLancamentoForm = function (form, lancamento) {
            if(!validarRateio(lancamento)) {
                return false;
            }
            if(!$scope.lancamento.rateios || !lancamento.rateios.length) {
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
