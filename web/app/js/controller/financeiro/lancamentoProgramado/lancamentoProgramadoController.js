'use strict';

app.controller('LancamentoProgramadoController', 
    ['$scope', '$filter', '$state', 'LancamentoProgramadoService', 'ContaService', 'PlanoContaService', 'CentroCustoService', 'ModalService', 
     'DatePickerService', 'LancamentoProgramadoHandler', 'LancamentoHandler', 'ListaService', 'LISTAS', 'MESSAGES',
    function ($scope, $filter, $state, LancamentoProgramadoService, ContaService, PlanoContaService, CentroCustoService, ModalService, 
        DatePickerService, LancamentoProgramadoHandler, LancamentoHandler, ListaService, LISTAS, MESSAGES) {

        var init = function () {
            $scope.lancamentoProgramados = [];
            $scope.lancamentoProgramadosLista = [];
            $scope.tipos = LISTAS.lancamento;
            $scope.modelos = LISTAS.modeloLancamento;
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
                {label: 'Conta', column: 'conta.nome', filter: {name: 'date', args: 'dd/MM/yyyy'}},                      
                {label: 'Vencimento', column: 'dataVencimento', filter: {name: 'date', args: 'dd/MM/yyyy'}},                
                {label: 'Número', column: 'numeroParcela'},         
                {label: 'Plano Conta', column: 'planoConta'},   
                {label: 'Centro Custo', column: 'centroCusto', showColumn: true, selected: false},             
                {label: 'Favorecido', column: 'favorecido'},  
                {label: 'Valor', column: 'valor', headerClass: 'no-sort', filter: {name: 'currency', args: ''}},              
                {label: 'Situação', column: 'situacao'},  
                {label: 'Frequência', column: 'frequencia'}
            ]            
            $scope.linha = {
                conditionalClass: function(item) {
                    if(item.tipo.id === $scope.tipos[0].id) return 'text-primary';
                    else if(item.tipo.id === $scope.tipos[1].id) return 'text-danger';
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
                
                lancamentoProgramado.tipo.modelo = $scope.modelos[2];
                
                var complementoDescricaoFrequencia = '';
                if(lancamentoProgramado.quantidadeParcela) { 
                    complementoDescricaoFrequencia = ' - ' + lancamentoProgramado.quantidadeParcela + 'x (' + $filter('currency')(lancamentoProgramado.valor, 'R$ ') + ')';
                    lancamentoProgramado.tipo.modelo = $scope.modelos[4];
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
                
                lancamentoProgramado.situacao = lancamentoProgramado.situacao.descricao;
                lancamentoProgramado.frequencia = lancamentoProgramado.frequencia.descricao + complementoDescricaoFrequencia;
                lancamentoProgramado.numeroParcela = lancamentoProgramado.numero;
                lancamentoProgramado.tipo.modelo = $scope.modelos;
                if(lancamentoProgramado.lancamentos && lancamentoProgramado.lancamentos.length) {
                    lancamentoProgramado.lancamentos = lancamentoProgramado.lancamentos.length;
                }
                
                return _.pick(lancamentoProgramado, 'idLancamentoProgramado', 'lancamentos', 'conta', 'tipo', 'tipoLancamento', 'dataVencimento', 'numeroParcela', 'planoConta', 'centroCusto', 'favorecido', 'valor', 'situacao', 'frequencia');
            })
        };
        
        var getLancamentoProgramadosSelecionados = function(lancamentosProgramados) {            
            var lancamentosProgramadosSelecionados = [];
            _.map(lancamentosProgramados, function(lancamentoProgramado) {
                if(!lancamentoProgramado.selected) return;
                lancamentosProgramadosSelecionados.push(angular.copy(lancamentoProgramado));
            });
            return lancamentosProgramadosSelecionados;
        }
        
        // ***** VISUALIZAR ***** //

        $scope.visualizar = function(conta, lancamentoProgramado) {
            LancamentoProgramadoService.get(lancamentoProgramado.idLancamentoProgramado)
                .then(function(result) {
                     modalVisualizar(result)
                        .then(function(lancamentoProgramadoEditar) {
                            $scope.editar(conta, lancamentoProgramadoEditar, result);
                        })          
                })
                .catch(function(e) {
                    modalMessage(e);
                });
        };
        
        // ***** SALVAR ***** //

        $scope.salvar = function(conta, tipo) {
            modalSalvar(conta, tipo, null).then(function(result) {
                var gerarLancamento = result.gerarLancamento;
                result = ajustarDados(result);
                salvar(conta, result, gerarLancamento);
            });
        };
        
        var salvar = function(conta, lancamentoProgramado, gerarLancamento) {
            if(gerarLancamento) { criarLancamento(conta, lancamentoProgramado); } 
            else { 
                LancamentoProgramadoService.save(lancamentoProgramado)
                    .then(function(data) { 
                        modalMessage(MESSAGES.lancamento.programar.sucesso.INSERIDO_SUCESSO);
                        todos(conta);
                    })
                    .catch(function(e) {
                        modalMessage(e);
                    });
            }            
        }
        
        // ***** EDITAR ***** //

        $scope.editar = function(conta, lancamentoProgramado) {
            LancamentoProgramadoService.get(lancamentoProgramado.idLancamentoProgramado)
                .then(function(result) {
                     editarLancamento(conta, result);
                })
                .catch(function(e) {
                    modalMessage(e.error);
                });           
        };
        
        var editarLancamento = function(conta, lancamentoProgramado) {                   
            modalSalvar(conta, lancamentoProgramado.tipo, lancamentoProgramado)
                .then(function(result) {
                    var gerarLancamento = result.gerarLancamento;
                    result = ajustarDados(result);
                    if(gerarLancamento) { criarLancamento(conta, result); } 
                    else { editar(conta, result); }
                });            
        };
        
        var editar = function(conta, lancamentoProgramado) {
            LancamentoProgramadoService.update(lancamentoProgramado.idLancamentoProgramado, lancamentoProgramado)
                .then(function (data) {  
                    modalMessage(MESSAGES.lancamento.programar.sucesso.ALTERADO_SUCESSO);
                    todos(conta);
                })
                .catch(function(e) {
                    modalMessage(e);
                });
        };
        
        var criarLancamento = function(conta, lancamentoProgramado) { 
            if(lancamentoProgramado.idLancamentoProgramado) {
                LancamentoProgramadoService.getByNumeroParcela(lancamentoProgramado.idLancamentoProgramado, lancamentoProgramado.numeroParcela)
                    .then(function(data) {  
                        if(data) {
                            modalMessage(MESSAGES.lancamento.programar.info.PARCELA_EXISTENTE);
                        } else {
                            create(conta, lancamentoProgramado);
                        }
                    })
                    .catch(function(e) {
                        modalMessage(e);
                    });
            } else {
                create(conta, lancamentoProgramado);
            }
        };
        
        var create = function(conta, lancamentoProgramado) { 
            LancamentoProgramadoService.create(lancamentoProgramado)
                .then(function(data) {  
                    modalMessage(MESSAGES.lancamento.sucesso.INSERIDO_SUCESSO);
                    todos(conta);                        
                })
                .catch(function(e) {
                    modalMessage(e);
                });
        };
        
        // ***** EXCLUIR ***** //
        
        var existeLancamentoVinculado = function(lancamentoProgramado) {   
            if(lancamentoProgramado.lancamentos && lancamentoProgramado.lancamentos > 0) return true; 
            return false;
        }
        
        $scope.excluir = function(conta, lancamentoProgramado) {            
            LancamentoProgramadoService.getLancamento(lancamentoProgramado.idLancamentoProgramado)
                .then(function(data) {   
                    if(data.lancamentos) {
                        excluirLancamentos(conta, data);                        
                    } else {
                        excluirLancamentoProgramado(conta, data);
                    }
                });
        };
        
        var excluirLancamentos = function(conta, lancamentoProgramado) {    
            if(existeLancamentoVinculado(lancamentoProgramado)) {                
                modalMessage(MESSAGES.lancamento.programar.info.LANCAMENTO_VINCULADOS);
            } else {
                excluirLancamentoProgramado(conta, lancamentoProgramado);
            } 
        };
        
        var excluirLancamentoProgramado = function(conta, lancamentoProgramado) {
            modalExcluir(MESSAGES.lancamento.programar.info.CONFIRMAR_EXCLUIR)
                .then(function() {
                    excluir(conta, lancamentoProgramado);
                });
        };
        
        var excluir = function(conta, lancamentoProgramado) {
            LancamentoProgramadoService.delete(lancamentoProgramado.idLancamentoProgramado)
                .then(function(data) { 
                    modalMessage(MESSAGES.lancamento.programar.sucesso.REMOVIDO_SUCESSO);
                    todos(conta);                        
                })
                .catch(function(e) {
                    modalMessage(e);
                });
        }; 
        
        // ***** TODOS ***** //

        $scope.excluirTodos = function(conta, lancamentos) {
            var lancamentoSelecionados = getLancamentoProgramadosSelecionados(lancamentos);  
            var lancamentosValidos = getLancamentos(lancamentoSelecionados);
            var msg = MESSAGES.lancamento.programar.info.CONFIRMAR_EXCLUIR_TODOS;
            if(lancamentosValidos.length !== lancamentoSelecionados.length) { 
                msg = MESSAGES.lancamento.programar.info.CONFIRMAR_EXCLUIR_LANCAMENTOS_TODOS;
            }
            excluirLancamentosProgramados(conta, lancamentosValidos, msg);
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
        
        var excluirLancamentosProgramados = function(conta, lancamentos, message) {
            modalExcluir(message)
                .then(function() {
                    excluirAll(conta, lancamentos);
                });
        };
        
        var excluirAll = function(conta, lancamentosProgramados) {
            _.map(lancamentosProgramados, function(lancamentoProgramado) {
                lancamentoProgramado = ajustarDados(lancamentoProgramado);
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

//        $scope.transferir = function(conta) {
//            modalTransferir().then(function(result) {
//                result = ajustarDadosTransferencia(result);
//                LancamentoProgramadoTransferenciaService.save(result)
//                    .then(function(data) {  
//                        modalMessage("Lançamento Programado Transferido com sucesso!");
//                        todos(conta);
//                    })
//                    .catch(function(e) {
//                        modalMessage(e);
//                    });
//            });
//        };
                
        // ***** VALIDAR ***** //
        
        // ***** AJUSTAR ***** //
        
        var ajustarDados = function(data) {   
            var lancamentos = ajustarDadosLancamentos(data.lancamentos);
            var lancamentoProgramado = LancamentoProgramadoHandler.handle(data);
            lancamentoProgramado.lancamentos = lancamentos;
            return lancamentoProgramado;
        } 
        
        var ajustarDadosLancamentos = function(lancamentos) {
            var lancamentoList = [];
            _.map(lancamentos, function(lancamento) {
                lancamentoList.push(LancamentoHandler.handle(lancamento));
            });
            return lancamentoList;
        }
        
//        var ajustarDadosTransferencia = function(data) {                 
//            var lancamentoProgramadoTransferencia = { 
//                idLancamentoProgramadoTransferencia: null,
//                lancamentoProgramadoOrigem: getLancamentoProgramado(data.contaOrigem, data),
//                lancamentoProgramadoDestino: getLancamentoProgramado(data.contaDestino, data)
//            }; 
//            lancamentoProgramadoTransferencia.lancamentoProgramadoOrigem.tipo = $scope.tipos[1].id;
//            lancamentoProgramadoTransferencia.lancamentoProgramadoDestino.tipo = $scope.tipos[0].id;    
//            return lancamentoProgramadoTransferencia;
//        }
        
//        var getLancamentoProgramado = function(conta, data) {
//            return {
//                idLancamentoProgramado: null,
//                planoConta: null,
//                centroCusto: null,
//                favorecido: null,
//                numero: data.numero,
//                data: data.data,
//                valor: data.valor,       
//                situacao: data.situacao.id,
//                historico: data.historico,
//                conta: { idConta: conta.idConta }
//            }
//        }
        
        // ***** MODAL ***** //
        
        var modalMessage = function(message) {
            ModalService.modalMessage(message);
        };
        
        var modalVisualizar = function(lancamentoProgramado) {
            var modalInstance = ModalService.modalDefault('partials/financeiro/lancamentoProgramado/modalLancamentoProgramadoVisualizar.html', 'ModalLancamentoProgramadoVisualizarController', 'md',
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
        
//        var modalTransferir = function() {
//            var modalInstance = ModalService.modalDefault('partials/financeiro/lancamentoProgramado/modalLancamentoProgramadoTransferir.html', 'ModalLancamentoProgramadoTransferirController', 'lg');
//            return modalInstance.result;
//        };
        
        var modalExcluir = function(message) {
            var modalInstance = ModalService.modalExcluir(MESSAGES.lancamento.programar.info.ALERT_EXCLUIR, message);
            return modalInstance.result;
        };
        
        init();

    }]);
