'use strict';

app.controller('ModalLancamentoProgramadoEditarController', ['$scope', '$modalInstance', 'conta', 'tipo', 'lancamentoProgramado', 'ContaService', 'PlanoContaService', 'CentroCustoService', 'LancamentoConciliadoService', 'TipoDocumentoService', 'TipoFormaPagamentoService', 'FrequenciaLancamentoService', 'ModalService', 'DatePickerService', 'ListaService', 'LISTAS',
    function ($scope, $modalInstance, conta, tipo, lancamentoProgramado, ContaService, PlanoContaService, CentroCustoService, LancamentoConciliadoService, TipoDocumentoService, TipoFormaPagamentoService, FrequenciaLancamentoService, ModalService, DatePickerService, ListaService, LISTAS) {

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
            getTitle();
            contas();
            centroCustos();
            tipoDocumento();
            tipoFormaPagamento();
            planoContas($scope.lancamentoProgramado.tipo);
        };
        
        var initStep = function() {
            if(lancamentoProgramado && lancamentoProgramado.quantidadeParcela) {
                parcelarLancamento(lancamentoProgramado);
            } else {            
                goToEditar(); 
            }
        }
        
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
        
        var getTitle = function() {
            if(lancamentoProgramado && lancamentoProgramado.idLancamentoProgramado) { $scope.title = "Editar Lançamento Programado"; }
            else { $scope.title = "Inserir Lançamento Programado"; }
        };
        
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
                    $scope.planoContas = criarPlanoContasLista($scope.planoContas);
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
                    $scope.centroCustos = criarCentroCustosLista($scope.centroCustos);
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
        
        $scope.getTotal = function(lancamento) {
            if(!lancamento) return;
            lancamento.valor = lancamento.valor || 0;
            lancamento.valorJuros = lancamento.valorJuros || 0;
            lancamento.valorMulta = lancamento.valorMulta || 0;
            lancamento.valorDesconto = lancamento.valorDesconto || 0;
            return lancamento.valor + lancamento.valorJuros + lancamento.valorMulta - lancamento.valorDesconto;
        }
        
        $scope.ok = function(form) {
            if(!validaConta($scope.lancamentoProgramado.conta)) return;
            if (!validarForm(form)) return;
            delete $scope.lancamentoProgramado.parcelas;
            delete $scope.lancamentoProgramado.lancamentos;
            $scope.lancamentoProgramado.gerarLancamento = false;
            $modalInstance.close($scope.lancamentoProgramado);            
        };
        
        $scope.createParcelas = function(lancamentoProgramado) {       
            $scope.lancamentoProgramado.parcelas = [];
            var frequencia = lancamentoProgramado.frequencia;
            var dataCompetencia = lancamentoProgramado.dataCompetencia;
            var dataVencimento = lancamentoProgramado.dataVencimento;
            for(var i=0; i<lancamentoProgramado.quantidadeParcela; i++) {
                var numeroParcela = (i + 1);
                var lancamento = findParcelaBaixada(lancamentoProgramado.lancamentos, numeroParcela);
                var parcela = {
                    numero: lancamentoProgramado.numero,
                    numeroParcela: numeroParcela,
                    dataCompetencia: dataCompetencia,
                    dataVencimento: dataVencimento,
                    valor: lancamentoProgramado.valor / lancamentoProgramado.quantidadeParcela,
                    lancamento: lancamento
                }
                $scope.lancamentoProgramado.parcelas.push(parcela);
                dataCompetencia = FrequenciaLancamentoService.addData(frequencia, dataCompetencia);
                dataVencimento = FrequenciaLancamentoService.addData(frequencia, dataVencimento);
            }
        }
        
        var findParcelaBaixada = function(lancamentos, numeroParcela) {
            return _.find(lancamentos, function(lancamento) { 
                return lancamento.numeroParcela == numeroParcela; 
            });
        }
        
        $scope.lancarProgramado = function(form, lancamentoProgramado) {
            if(!validaConta(lancamentoProgramado.conta)) return;
            if (!validarForm(form)) return;         
            goToLancar();
            $scope.lancamento = getLancamento(lancamentoProgramado, null, $scope.modelos[2]);
        };
        
        $scope.lancarParcela = function(form, lancamentoProgramado, parcela) {
            if(!validaConta(lancamentoProgramado.conta)) return;
            if (!validarForm(form)) return;           
            goToLancar();
            $scope.lancamento = getLancamento(lancamentoProgramado, parcela, $scope.modelos[4]);
        };
        
        $scope.lancar = function(form, lancamentoProgramado, lancamento) {
            if(!validaConta(lancamentoProgramado.conta)) return;
            if (!validarForm(form)) return;          
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
            delete $scope.lancamentoProgramado.parcelas;
            lancamentoProgramado.gerarLancamento = true;
            lancamento = ajustarLancamento(lancamento);
            lancamentoProgramado.lancamentos = [];
            lancamentoProgramado.lancamentos.push(lancamento);
            encerrarLancamentoProgramado(lancamentoProgramado, lancamento);
            $modalInstance.close(lancamentoProgramado);             
        }
                
        $scope.parcelar = function(form, lancamentoProgramado) {
            if(!validaConta(lancamentoProgramado.conta)) return;
            if (!validarForm(form)) return;
            parcelarLancamento(lancamentoProgramado);
        };
        
        var parcelarLancamento = function(lancamentoProgramado) {            
            goToParcelar();
            $scope.createParcelas(lancamentoProgramado);
        }
        
        $scope.ratear = function() {
            goToRatear();
            $scope.lancamento.rateios = [];
            setRateioDefault();
        };
        
        $scope.cancelarRatear = function() {
            goToEditar();
            $scope.lancamento.rateios = null;
        };
        
        $scope.salvarRateio = function(rateio) {
            var saldo = saldoRateio();
            if(saldo + rateio.valor > $scope.lancamento.valor) {
                modalMessage("A soma dos valores de rateio é superior ao valor do lançamento!");
                return;
            }
            $scope.lancamento.rateios.push(rateio);
            setRateioDefault();
        }
        
        $scope.editarRateio = function(rateio, index) {
            $scope.rateio = rateio;
            $scope.removerRateio(index);
        }
        
        $scope.removerRateio = function(index) {     
            $scope.lancamento.rateios.splice(index, 1); 
            setRateioDefault();   
        }
        
        var setRateioDefault = function() {
            $scope.rateio = {};
            $scope.rateio.planoConta = $scope.planoContas[0];
            $scope.rateio.valor = $scope.lancamento.valor - saldoRateio();
        }
        
        var saldoRateio = function() {            
            var saldo = 0;
            _.map($scope.lancamento.rateios, function(rateio) {
                saldo += rateio.valor;
            });
            return saldo;
        };
                
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
        
        var validaConta = function(conta) {
            if(conta.status.id === $scope.statusConta[1].id) {
                modalMessage("Esta conta está encerrada!");
                return false;
            };
            return true;
        }
        
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
        }
        
        var encerrarLancamentoProgramado = function(lancamentoProgramado, lancamento) {
            if(lancamentoProgramado.frequencia.codigo === 'unico') { 
                lancamentoProgramado.situacao = $scope.situacoes[2];
            }
            if(lancamento.numeroParcela === lancamentoProgramado.quantidadeParcela) {
                lancamentoProgramado.situacao = $scope.situacoes[2];                
            }
            
        }
        
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
        
        $scope.cancel = function () {
            $modalInstance.dismiss('cancel');
        };
        
        var goToEditar = function() {
            $scope.stepFrom = angular.copy($scope.stepTo);
            $scope.stepTo = 'editar';             
        }
        
        var goToLancar = function() {
            $scope.stepFrom = angular.copy($scope.stepTo);
            $scope.stepTo = 'lancar';             
        }
        
        var goToParcelar = function() {
            $scope.stepFrom = angular.copy($scope.stepTo);
            $scope.stepTo = 'parcelar';             
        }
        
        var goToRatear = function() {
            $scope.stepFrom = angular.copy($scope.stepTo);
            $scope.stepTo = 'ratear';             
        }
        
        var modalConfirmarConciliado = function() {
            var modalInstance = ModalService.modalConfirmar('Alerta Lançamento', 'Você está inserindo um lançamento em um período reconciliado. <br/> Está inclusão irá impactar no lançamento de reconciliação! <br/> Deseja continuar?');
            return modalInstance.result;
        };
                
        var modalMessage = function(message) {
            ModalService.modalMessage(message);
        };
        
        var validarForm = function (form) {
            if (form.dataCompetencia.$error.required) {
                alert('Preencha a competência do lançamento programado!');
                return false;
            }       
            if (form.dataCompetencia.$modelValue && !moment(form.dataCompetencia.$modelValue).isValid()) {
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
