'use strict';

app.controller('LancamentoProgramadoController', 
    ['$scope', '$filter', '$state', 'LancamentoProgramadoService', 'ContaService', 'PlanoContaService', 'CentroCustoService', 'LancamentoProgramadoTransferenciaService',
     'FrequenciaLancamentoService', 'ReportService', 'ModalService', 'DatePickerService', 'LancamentoProgramadoHandler', 'LancamentoProgramadoParcelaHandler', 
     'LancamentoProgramadoRateioHandler', 'LancamentoProgramadoTransferenciaHandler', 'LancamentoHandler', 'FinanceiroValidation', 'ListaService', 'LISTAS', 'MESSAGES',
    function ($scope, $filter, $state, LancamentoProgramadoService, ContaService, PlanoContaService, CentroCustoService, LancamentoProgramadoTransferenciaService,
        FrequenciaLancamentoService, ReportService, ModalService, DatePickerService, LancamentoProgramadoHandler, LancamentoProgramadoParcelaHandler, 
        LancamentoProgramadoRateioHandler, LancamentoProgramadoTransferenciaHandler, LancamentoHandler, FinanceiroValidation, ListaService, LISTAS, MESSAGES) {

        var init = function () {
            $scope.lancamentoProgramados = [];
            $scope.lancamentoProgramadosLista = [];
            $scope.tipos = LISTAS.lancamento;
            $scope.modelos = LISTAS.modeloLancamento;
            $scope.situacoes = LISTAS.situacaoLancamentoProgramado;
            $scope.tiposLancamento = [];
            $scope.datepickerDataInicio = angular.copy(DatePickerService.default);      
            $scope.datepickerDataFim = angular.copy(DatePickerService.default);    
            $scope.lancSearch = {};
            getTitle();
            createListTipoLancamento();
            contas();
            initTable(); 
        };  
        
        // ***** TABLE ***** //
        
        var initTable = function() {            
            $scope.colunas = [              
                {label: '', column: 'tipo', headerClass: 'no-sort', dataClass:'text-center col-tipo', filter: {name: 'tipoLancamento', args: ''}},       
                {label: 'Conta', column: 'conta.nome'},                      
                {label: 'Vencimento', column: 'dataVencimento', filter: {name: 'date', args: 'dd/MM/yyyy'}},                
                {label: 'Número', column: 'numero'},         
                {label: 'Plano Conta', column: 'planoConta'},   
                {label: 'Centro Custo', column: 'centroCusto', showColumn: true, selected: false},  
                {label: 'Usuário', column: 'usuario', showColumn: true, selected: false},           
                {label: 'Favorecido', column: 'favorecido'},  
                {label: 'Valor', column: 'valor', headerClass: 'no-sort', filter: {name: 'currency', args: ''}},              
                {label: 'Situação', column: 'situacao'},  
                {label: 'Frequência', column: 'frequencia'}
            ]            
            $scope.linha = {
                conditionalClass: function(item) {
                    if(item.tipo.id === $scope.tipos[0].id) return 'text-primary';
                    else if(item.tipo.id === $scope.tipos[1].id) return 'text-danger';
                    else if(item.tipo.id === $scope.tipos[$scope.tipos.length - 1].id) return 'text-warning';
                },
                events: { 
                    edit: function(lancamentoProgramado) {
                        $scope.editar($scope.conta, lancamentoProgramado);
                    },
                    remove: function(lancamentoProgramado) {
                        $scope.excluir($scope.conta, lancamentoProgramado);
                    },
                    view: function(lancamentoProgramado) {
                        $scope.visualizar($scope.conta, lancamentoProgramado);
                    }
                }
            };
        };
        
        $scope.filter = function(lista, search) {
            lista = _.filter(lista, function(item) {
                return filterByData(item, search);
            });  
            lista = filterTipo(lista, search);
            lista = filterConta(lista, search);
            return lista;
        }
        
        var filterByData = function(item, search) {
            if(!search.dataInicio || !search.dataFim) return true;
            var data = moment(item.dataVencimento);
            return (data.isBefore(search.dataFim) && data.isAfter(search.dataInicio) 
                    || (data.isSame(search.dataInicio) || data.isSame(search.dataFim)));
        }; 
        
        var filterTipo = function(lista, search) {          
            if(!search.tipo) return lista;
            lista = _.filter(lista, function(item) {
                return item.tipoLancamento === search.tipo;
            });
            return lista;
        }
        
        var filterConta = function(lista, search) {          
            if(!search.conta) return lista;
            var conta = JSON.parse(search.conta); 
            lista = _.filter(lista, function(item) {
                return item.conta.idConta === conta.idConta;
            });
            return lista;
        }
        
        // ***** CONTROLLER ***** //
        
        var getTitle = function() {
            $scope.title = MESSAGES.lancamento.programar.title.LISTA; 
        };
        
        var createListTipoLancamento = function() {
            $scope.tiposLancamento.push( { codigo: $scope.tipos[0].codigo, descricao: 'Contas a Receber' });
            $scope.tiposLancamento.push( { codigo: $scope.tipos[1].codigo, descricao: 'Contas a Pagar' });
            $scope.tiposLancamento.push( { codigo: $scope.modelos[3].codigo, descricao: 'Transf. Programada' });
        }
        
        var contas = function() {
            ContaService.getAll()
                .then(function (data) {
                    $scope.contas = data;
                    if($state.params.id) { $scope.editar($scope.conta, $state.params.id); }
                    planoContas();
                })
                .catch(function (e) {
                    console.log(e);
                });
        };
        
        var planoContas = function() {
            PlanoContaService.getStructure()
                .then(function (data) {
                    $scope.planoContas = data;
                    PlanoContaService.estrutura($scope.planoContas);
                    $scope.planoContas = PlanoContaService.flatten($scope.planoContas);                 
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
                    todos();
                })
                .catch(function (e) {
                    console.log(e);
                });
        };

        var todos = function() {
            LancamentoProgramadoService.getAll()
                .then(function (data) {
                    $scope.lancamentoProgramados = data;
                    $scope.lancamentoProgramadosLista = criarLancamentoProgramadosLista($scope.lancamentoProgramados);
                })
                .catch(function(e) {
                    console.log(e);
                });
        };
        
        var criarLancamentoProgramadosLista = function(lancamentosProgramados) {
            return _.map(lancamentosProgramados, function(lancamentoProgramado) {  
                
                lancamentoProgramado.tipo.modelo = lancamentoProgramado.modelo;
                
                var complementoDescricaoFrequencia = '';
                if(lancamentoProgramado.quantidadeParcela) { 
                    var saldo = getSaldoLancamentoProgramadoParcelado(lancamentoProgramado);
                    complementoDescricaoFrequencia = ' - ' + lancamentoProgramado.quantidadeParcelaAbertas + 'x (' + $filter('currency')(saldo, 'R$ ') + ')';
//                    lancamentoProgramado.tipo.modelo = $scope.modelos[4];
                    lancamentoProgramado.valor = lancamentoProgramado.valor / lancamentoProgramado.quantidadeParcela;
                }                
                
                if(lancamentoProgramado.tipo.modelo.id === $scope.modelos[3].id) { lancamentoProgramado.tipoLancamento = $scope.modelos[3].codigo; }
                else if(lancamentoProgramado.tipo.id === $scope.tipos[0].id) { lancamentoProgramado.tipoLancamento = lancamentoProgramado.tipo.codigo; }
                else if(lancamentoProgramado.tipo.id === $scope.tipos[1].id) { lancamentoProgramado.tipoLancamento = lancamentoProgramado.tipo.codigo; }
                
                if(lancamentoProgramado.planoConta && lancamentoProgramado.planoConta.idPlanoConta) { 
                    var planoConta = ListaService.getPlanoContaValue($scope.planoContas, lancamentoProgramado.planoConta.idPlanoConta); 
                    lancamentoProgramado.planoConta = planoConta.descricao;                    
                } else {
                    lancamentoProgramado.planoConta = null;
                }
                
                if(lancamentoProgramado.centroCusto && lancamentoProgramado.centroCusto.idCentroCusto) { 
                    var centroCusto = ListaService.getCentroCustoValue($scope.centroCustos, lancamentoProgramado.centroCusto.idCentroCusto); 
                    lancamentoProgramado.centroCusto = centroCusto.descricao;                    
                } else {
                    lancamentoProgramado.centroCusto = null;
                }
                
                if(lancamentoProgramado.lancamentos && lancamentoProgramado.lancamentos.length) {
                    lancamentoProgramado.lancamentos = lancamentoProgramado.lancamentos.length;
                }

                if(lancamentoProgramado.rateios && lancamentoProgramado.rateios.length) {
                    lancamentoProgramado.planoConta = "Diversos";
                    lancamentoProgramado.centroCusto = "Diversos";
                }
                
                if(lancamentoProgramado.favorecido) { lancamentoProgramado.favorecido = lancamentoProgramado.favorecido.nome; }

                if(lancamentoProgramado.modelo.id === $scope.modelos[3].id) { 
                    lancamentoProgramado.planoConta = $scope.modelos[3].descricao;
                }
                                
                lancamentoProgramado.situacao = lancamentoProgramado.situacao.descricao;
                lancamentoProgramado.frequencia = lancamentoProgramado.frequencia.descricao + complementoDescricaoFrequencia;
//                lancamentoProgramado.numeroParcela = lancamentoProgramado.numero;
//                lancamentoProgramado.tipo.modelo = $scope.modelos;
                
                return _.pick(lancamentoProgramado, 'idLancamentoProgramado', 'conta', 'tipo', 'tipoLancamento', 'dataVencimento', 'numero', 
                'planoConta', 'centroCusto', 'usuario', 'favorecido', 'valor', 'situacao', 'frequencia', 'existeLancamento');
            })
        };
        
        var getSaldoLancamentoProgramadoParcelado = function(lancamentoProgramado) {
            var saldo = 0;
            _.map(lancamentoProgramado.parcelas, function(parcela) {  
                if(!parcela.lancamento) { saldo += parcela.valor; }
            });   
            return saldo;
        }
        
        var getLancamentoProgramadosSelecionados = function(lancamentosProgramados) {            
            var lancamentosProgramadosSelecionados = [];
            _.map(lancamentosProgramados, function(lancamentoProgramado) {
                if(!lancamentoProgramado.selected) return;
                lancamentosProgramadosSelecionados.push(angular.copy(lancamentoProgramado));
            });
            return lancamentosProgramadosSelecionados;
        };
        
        var getLancamentos = function(lancamentosProgramados) {               
            var lancamentosProgramadosSelecionados = [];
            _.map(lancamentosProgramados, function(lancamentoProgramado) {        
                if(!existeLancamentoVinculado(lancamentoProgramado)) {                
                    lancamentosProgramadosSelecionados.push(lancamentoProgramado);
                }                 
            });
            return lancamentosProgramadosSelecionados;
        };
        
        // ***** VISUALIZAR ***** //

        $scope.visualizar = function(conta, lancamentoProgramado) {
            LancamentoProgramadoService.get(lancamentoProgramado.idLancamentoProgramado)
                .then(function(result) {
                     visualizar(conta, result);         
                })
                .catch(function(e) {
                    modalMessage(e);
                });
        };

        var visualizar = function(conta, lancamentoProgramado) {
            modalVisualizar(lancamentoProgramado)
               .then(function(lancamentoProgramadoEditar) {
                   $scope.editar(conta, lancamentoProgramadoEditar, lancamentoProgramado);
               });
        };
        
        // ***** SALVAR ***** //

        $scope.salvar = function(conta, tipo) {
            salvar(conta, tipo);
        };
        
        var salvar = function(conta, tipo) {
            modalSalvar(conta, tipo, null).then(function(result) {
                var gerarLancamento = result.gerarLancamento;
                result = ajustarDados(result);
                save(conta, result, gerarLancamento);
            });
        };
        
        var save = function(conta, lancamentoProgramado, gerarLancamento) {
            if(gerarLancamento) { criarLancamento(conta, lancamentoProgramado, true); } 
            else { 
                LancamentoProgramadoService.save(lancamentoProgramado)
                    .then(function(data) { 
                        modalMessage(MESSAGES.lancamento.programar.sucesso.INSERIDO_SUCESSO);
                        todos(conta);
                    })
                    .catch(function(e) {
                        modalMessage(e);
                    });
            };            
        };
        
        // ***** EDITAR ***** //

        $scope.editar = function(conta, lancamentoProgramado) {
            LancamentoProgramadoService.get(lancamentoProgramado.idLancamentoProgramado)
                .then(function(result) {
                    if(result.lancamentoProgramadoTransferencia) {
                        $scope.transferir(conta, result.lancamentoProgramadoTransferencia);
                    } else {   
                        editar(conta, result);
                    }
                })
                .catch(function(e) {
                    modalMessage(e.error);
                });           
        };
        
        var editar = function(conta, lancamentoProgramado) {                   
            modalSalvar(conta, lancamentoProgramado.tipo, lancamentoProgramado)
                .then(function(result) {
                    var gerarLancamento = result.gerarLancamento;
                    result = ajustarDados(result);
                    if(gerarLancamento) { criarLancamento(conta, result, true); } 
                    else { update(conta, result); }
                });            
        };
        
        var update = function(conta, lancamentoProgramado) {
            LancamentoProgramadoService.update(lancamentoProgramado.idLancamentoProgramado, lancamentoProgramado)
                .then(function (data) {  
                    modalMessage(MESSAGES.lancamento.programar.sucesso.ALTERADO_SUCESSO);
                    todos(conta);
                })
                .catch(function(e) {
                    modalMessage(e);
                });
        };
        
        // ***** CREATE ***** //
        
        var criarLancamento = function(conta, lancamentoProgramado, showMsg) { 
            if(lancamentoProgramado.idLancamentoProgramado) {
                LancamentoProgramadoService.getByNumeroParcela(lancamentoProgramado.idLancamentoProgramado, lancamentoProgramado.numeroParcela)
                    .then(function(data) {  
                        if(data) {
                            modalMessage(MESSAGES.lancamento.programar.info.PARCELA_EXISTENTE);
                        } else {
                            create(conta, lancamentoProgramado, showMsg);
                        }
                    })
                    .catch(function(e) {
                        modalMessage(e);
                    });
            } else {
                create(conta, lancamentoProgramado, showMsg);
            }
        };
        
        var criarLancamentoTransferencia = function(conta, lancamentoProgramadoTransferencia) { 
            criarLancamento(conta, lancamentoProgramadoTransferencia.lancamentoProgramadoOrigem, false);
            criarLancamento(conta, lancamentoProgramadoTransferencia.lancamentoProgramadoDestino, true);
        };
        
        var create = function(conta, lancamentoProgramado, showMsg) { 
            var isParcela = (lancamentoProgramado.parcelas && lancamentoProgramado.parcelas.length);
            lancamentoProgramado = ajustarLancamentoProgramadoFrequencia(lancamentoProgramado, isParcela);
            LancamentoProgramadoService.create(lancamentoProgramado)
                .then(function(data) {  
                    encerrarLancamentoProgramado(lancamentoProgramado, lancamentoProgramado.lancamentos[0]); 
                    if(showMsg) {
                        modalMessage(MESSAGES.lancamento.sucesso.INSERIDO_SUCESSO);
                        todos(conta);                        
                    }
                })
                .catch(function(e) {
                    modalMessage(e);
                });
        };
        
        // ***** EXCLUIR ***** //
        
        $scope.excluir = function(conta, lancamentoProgramado) {            
            LancamentoProgramadoService.getLancamento(lancamentoProgramado.idLancamentoProgramado)
                .then(function(data) {   
                    if(data.lancamentos) {
                        excluirLancamentos(conta, data);                        
                    } else {
                        excluir(conta, data);
                    }
                });
        };
        
        var excluirLancamentos = function(conta, lancamentoProgramado) {    
            if(existeLancamentoVinculado(lancamentoProgramado)) {                
                modalMessage(MESSAGES.lancamento.programar.info.LANCAMENTO_VINCULADOS);
            } else {
                excluir(conta, lancamentoProgramado);
            } 
        };
        
        var excluir = function(conta, lancamentoProgramado) {
            modalExcluir(MESSAGES.lancamento.programar.info.CONFIRMAR_EXCLUIR)
                .then(function() {
                    remover(conta, lancamentoProgramado);
                });
        };
        
        var remover = function(conta, lancamentoProgramado) {
            LancamentoProgramadoService.delete(lancamentoProgramado.idLancamentoProgramado)
                .then(function(data) { 
                    modalMessage(MESSAGES.lancamento.programar.sucesso.REMOVIDO_SUCESSO);
                    todos(conta);                        
                })
                .catch(function(e) {
                    modalMessage(e);
                });
        }; 
        
        // ***** EXCLUIR TODOS ***** //

        $scope.excluirTodos = function(conta, lancamentos) {
            var lancamentoSelecionados = getLancamentoProgramadosSelecionados(lancamentos);   
            if(!existeLancamentoSelecionado(lancamentoSelecionados)) return;
            var lancamentosValidos = getLancamentos(lancamentoSelecionados);
            var msg = MESSAGES.lancamento.programar.info.CONFIRMAR_EXCLUIR_TODOS;
            if(lancamentosValidos.length !== lancamentoSelecionados.length) { 
                msg = MESSAGES.lancamento.programar.info.CONFIRMAR_EXCLUIR_LANCAMENTOS_TODOS;
            }
            excluirTodos(conta, lancamentosValidos, msg);
        }; 
        
        var excluirTodos = function(conta, lancamentos, message) {
            modalExcluir(message)
                .then(function() {
                    removeAll(conta, lancamentos);
                });
        };
        
        var removeAll = function(conta, lancamentosProgramados) {
            lancamentosProgramados = _.map(lancamentosProgramados, function(lancamentoProgramado) {
                return ajustarDados(lancamentoProgramado);
            });
            LancamentoProgramadoService.deleteAll(lancamentosProgramados)
                .then(function(data) { 
                    modalMessage(MESSAGES.lancamento.programar.sucesso.REMOVIDO_SUCESSO_TODOS);
                    todos(conta);                        
                })
                .catch(function(e) {
                    modalMessage(e);
                });            
        }
        
        // ***** TRANSFERIR ***** //

        $scope.transferir = function(conta, lancamentoProgramadoTransferencia) {
            if(!lancamentoProgramadoTransferencia) {
                transferir(conta, lancamentoProgramadoTransferencia);
            } else {
                LancamentoProgramadoTransferenciaService.get(lancamentoProgramadoTransferencia.idLancamentoProgramadoTransferencia)
                    .then(function(lancamentoProgramadoTransferencia) {
                        transferir(conta, lancamentoProgramadoTransferencia);
                    })
                    .catch(function(e) {
                        modalMessage(e.error);
                    });
            }            
        };

        var transferir = function(conta, lancamentoProgramadoTransferencia) {
            modalTransferir(lancamentoProgramadoTransferencia).then(function(result) {
                var gerarLancamento = result.gerarLancamento;
                result = ajustarDadosTransferencia(result);
                if(lancamentoProgramadoTransferencia) {  
                    updateTransferencia(conta, result, gerarLancamento);
                } else {
                    saveTransferencia(conta, result, gerarLancamento);
                }
            });
        }

        var saveTransferencia = function(conta, lancamentoProgramadoTransferencia, gerarLancamento) {
            if(gerarLancamento) { criarLancamentoTransferencia(conta, lancamentoProgramadoTransferencia); } 
            else { 
                LancamentoProgramadoTransferenciaService.save(lancamentoProgramadoTransferencia)
                    .then(function(data) {  
                        modalMessage(MESSAGES.lancamento.transferir.sucesso.INSERIDO_SUCESSO);
                        todos();
                    })
                    .catch(function(e) {
                        modalMessage(e);
                    });  
            }
        }

        var updateTransferencia = function(conta, lancamentoProgramadoTransferencia, gerarLancamento) {
            if(gerarLancamento) { criarLancamentoTransferencia(conta, lancamentoProgramadoTransferencia); } 
            else { 
                LancamentoProgramadoTransferenciaService
                        .update(lancamentoProgramadoTransferencia.idLancamentoProgramadoTransferencia, lancamentoProgramadoTransferencia)
                    .then(function(data) {  
                        modalMessage(MESSAGES.lancamento.transferir.sucesso.ALTERADO_SUCESSO);
                        todos();
                    })
                    .catch(function(e) {
                        modalMessage(e);
                    });        
            }
        }
        
        var encerrarLancamentoProgramado = function(lancamentoProgramado, lancamento) {
            if(lancamentoProgramado.frequencia.codigo === 'unico') { 
                lancamentoProgramado.situacao = $scope.situacoes[2];
            }
            if(lancamento.numeroParcela === lancamentoProgramado.quantidadeParcela) {
                lancamentoProgramado.situacao = $scope.situacoes[2];                
            }            
        };

        // ***** REPORT ***** //
        
        $scope.report = function(lancamentos) {
            var params = LancamentoProgramadoService.report(lancamentos);   
            ReportService.pdf('lancamento/programado', params);
        };
                
        // ***** VALIDAR ***** //
        
        var existeLancamentoSelecionado = function(lancamentosSelecionados) {
            if(lancamentosSelecionados && lancamentosSelecionados.length) return true;
            modalMessage(MESSAGES.lancamento.programar.info.SEM_LANCAMENTO_SELECIONADO);
            return false;
        };
        
        var existeLancamentoVinculado = function(lancamentoProgramado) {   
            return lancamentoProgramado.existeLancamento;
        };
        
        // ***** AJUSTAR ***** //
        
        var ajustarDados = function(data) {   
            var lancamentos = ajustarDadosLancamentos(data.lancamentos);
            var lancamentoProgramado = LancamentoProgramadoHandler.handle(data);
            lancamentoProgramado.lancamentos = lancamentos;
            lancamentoProgramado.parcelas = LancamentoProgramadoParcelaHandler.handleList(data.parcelas);
            lancamentoProgramado.rateios = LancamentoProgramadoRateioHandler.handleList(data.rateios);
            return lancamentoProgramado;
        } 
        
        var ajustarDadosLancamentos = function(lancamentos) {
            var lancamentoList = [];
            _.map(lancamentos, function(lancamento) {
                lancamentoList.push(lancamento);
            });
            return lancamentoList;
        }
        
        var ajustarDadosTransferencia = function(data) {  
            var modelo = $scope.modelos[3];
            var lancamentoProgramadoTransferencia = LancamentoProgramadoTransferenciaHandler.handle(data); 
            lancamentoProgramadoTransferencia.lancamentoProgramadoOrigem = getLancamentoProgramado(data.contaOrigem, $scope.tipos[1], modelo, data, data.lancamentoProgramadoOrigem);
            lancamentoProgramadoTransferencia.lancamentoProgramadoDestino = getLancamentoProgramado(data.contaDestino, $scope.tipos[0], modelo, data, data.lancamentoProgramadoDestino);
            return lancamentoProgramadoTransferencia;
        }

        var getLancamentoProgramado = function(conta, tipo, modelo, data, lancamentoProgramadoOriginal) {
            data.idLancamentoProgramado = (lancamentoProgramadoOriginal && lancamentoProgramadoOriginal.idLancamentoProgramado) || null;
            data.conta = conta;
            data.tipo = tipo;
            data.modelo = modelo;
            data.situacao = (data && data.situacao) || $scope.situacoes[0];
            data.numeroParcela = data.numeroParcela || 0;
            data.dataLancamento = data.dataVencimento;
            setLancamentoTransferencia(data);
            var lancamentoProgramadoHandle = LancamentoProgramadoHandler.handle(data);
            lancamentoProgramadoHandle.lancamentos = data.lancamentos;
            return lancamentoProgramadoHandle;
        };
        
        var setLancamentoTransferencia = function(lancamentoProgramado) { 
//            lancamentoProgramado.historico = '(' + lancamentoProgramado.modelo.descricao + ') ' + lancamentoProgramado.historico || "";
            var lancamentoHandle = LancamentoHandler.handle(lancamentoProgramado);
            lancamentoProgramado.lancamentos = [];
            lancamentoProgramado.lancamentos.push(lancamentoHandle);
        };
        
        var ajustarLancamentoProgramadoFrequencia = function(lancamentoProgramado, isParcelas) {             
            if(!isParcelas) {
                lancamentoProgramado.dataCompetencia = FrequenciaLancamentoService.addData(lancamentoProgramado.frequencia, lancamentoProgramado.dataCompetencia) || moment();
                lancamentoProgramado.dataVencimento = FrequenciaLancamentoService.addData(lancamentoProgramado.frequencia, lancamentoProgramado.dataVencimento) || moment();
            }
            return lancamentoProgramado;
        };
        
        // ***** MODAL ***** //
        
        var modalMessage = function(message) {
            ModalService.modalMessage(message);
        };
        
        var modalVisualizar = function(lancamentoProgramado) {
            var modalInstance = ModalService.modalDefault('partials/financeiro/lancamentoProgramado/modalLancamentoProgramadoVisualizar.html', 'ModalLancamentoProgramadoVisualizarController', 'lg',
                {
                    lancamentoProgramado: function() {
                        return lancamentoProgramado;
                    }
                });
            return modalInstance.result;
        };
        
        var modalSalvar = function(conta, tipo, lancamentoProgramado) {
            var modalInstance = ModalService.modalDefault('partials/financeiro/lancamentoProgramado/modalLancamentoProgramado.html', 'ModalLancamentoProgramadoController', 'lg',
                {
                    lancamentoProgramado: function() {
                        return lancamentoProgramado;
                    },
                    tipo: function() {
                        return tipo;
                    },
                    conta: function() {
                        return conta;
                    }
                });
            return modalInstance.result;
        };
        
        var modalTransferir = function(lancamentoProgramadoTransferencia) {
            var modalInstance = ModalService.modalDefault('partials/financeiro/lancamentoProgramado/modalLancamentoProgramadoTransferir.html', 'ModalLancamentoProgramadoTransferirController', 'lg', 
                {
                    lancamentoProgramadoTransferencia: function() {
                        return lancamentoProgramadoTransferencia;
                    }
                });
            return modalInstance.result;
        };
        
        var modalExcluir = function(message) {
            var modalInstance = ModalService.modalExcluir(MESSAGES.lancamento.programar.info.ALERT_EXCLUIR, message);
            return modalInstance.result;
        };
        
        init();

    }]);
