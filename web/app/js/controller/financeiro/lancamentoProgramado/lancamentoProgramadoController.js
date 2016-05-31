'use strict';

app.controller('LancamentoProgramadoController', ['$scope', '$filter', '$state', 'LancamentoProgramadoService', 'ContaService', 'ModalService', 'DatePickerService', 'LISTAS', 'MESSAGES',
    function ($scope, $filter, $state, LancamentoProgramadoService, ContaService, ModalService, DatePickerService, LISTAS, MESSAGES) {

        var init = function () {
            $scope.lancamentoProgramados = [];
            $scope.lancamentoProgramadosLista = [];
            $scope.tipos = LISTAS.lancamento;
            $scope.modelos = LISTAS.modeloLancamento;
            $scope.datepickerDataInicio = angular.copy(DatePickerService.default);      
            $scope.datepickerDataFim = angular.copy(DatePickerService.default);    
            $scope.lancSearch = {};
            contas();
            todos();  
            initTable(); 
        };  
        
        var initTable = function() {            
            $scope.colunas = [              
                {label: '', column: 'tipo', headerClass: 'no-sort', dataClass:'text-center col-tipo', filter: {name: 'tipoLancamento', args: ''}},       
                {label: 'Conta', column: 'conta.nome', filter: {name: 'date', args: 'dd/MM/yyyy'}},                      
                {label: 'Vencimento', column: 'dataVencimento', filter: {name: 'date', args: 'dd/MM/yyyy'}},                
                {label: 'Número', column: 'numeroParcela'},               
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
                        $scope.editar($scope.conta, lancamentoProgramado.tipo, lancamentoProgramado.idLancamentoProgramado);
                    },
                    remove: function(lancamentoProgramado) {
                        $scope.excluir($scope.conta, lancamentoProgramado.idLancamentoProgramado);
                    },
                    view: function(lancamentoProgramado) {
                        $scope.visualizar($scope.conta, lancamentoProgramado.idLancamentoProgramado);
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
            var tipo = JSON.parse(search.tipo); 
            lista = _.filter(lista, function(item) {
                return item.tipo.id === tipo.id;
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
        
        var contas = function() {
            ContaService.getAll()
                .then(function (data) {
                    $scope.contas = data;
                    if($state.params.id) { $scope.editar($scope.conta, $state.params.id); }
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
                    modalMessage(e);
                });
        };
        
        var criarLancamentoProgramadosLista = function(lancamentosProgramados) {
            return _.map(lancamentosProgramados, function(lancamentoProgramado) {  
                
                lancamentoProgramado.tipo.modelo = $scope.modelos[2];
                
                var complementoDescricaoFrequencia = '';
                if(lancamentoProgramado.quantidadeParcela) { 
                    complementoDescricaoFrequencia = ' - ' + lancamentoProgramado.quantidadeParcela + 'x (' + $filter('currency')(lancamentoProgramado.valor, 'R$ ') + ')';
                    lancamentoProgramado.tipo.modelo = $scope.modelos[4];
                }
                
                lancamentoProgramado.situacao = lancamentoProgramado.situacao.descricao;
                lancamentoProgramado.frequencia = lancamentoProgramado.frequencia.descricao + complementoDescricaoFrequencia;
                lancamentoProgramado.numeroParcela = lancamentoProgramado.numero;
                lancamentoProgramado.tipo.modelo = $scope.modelos;
                
                return _.pick(lancamentoProgramado, 'idLancamentoProgramado', 'conta', 'tipo', 'dataVencimento', 'numeroParcela', 'favorecido', 'valor', 'situacao', 'frequencia');
            })
        };

        $scope.visualizar = function(conta, idLancamentoProgramado) {
            LancamentoProgramadoService.get(idLancamentoProgramado)
                .then(function(lancamentoProgramado) {
                     modalVisualizar(lancamentoProgramado).then(function(result) {
                        $scope.editar(conta, lancamentoProgramado.tipo, result);
                     })          
                })
                .catch(function(e) {
                    modalMessage(e);
                });
        };

        $scope.salvar = function(conta, tipo) {
            modalSalvar(conta, tipo, null).then(function(result) {
                var gerarLancamento = result.gerarLancamento;
                result = ajustarDados(result);
                if(gerarLancamento) { criarLancamento(conta, result); } 
                else { 
                    LancamentoProgramadoService.save(result)
                        .then(function(data) { 
                            modalMessage(MESSAGES.lancamento.programar.sucesso.INSERIDO_SUCESSO);
                            todos(conta);
                        })
                        .catch(function(e) {
                            modalMessage(e);
                        });
                }
            });
        };

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

        $scope.editar = function(conta, tipo, idLancamentoProgramado) {
            LancamentoProgramadoService.get(idLancamentoProgramado)
                .then(function(lancamentoProgramado) {
                     modalSalvar(conta, tipo, lancamentoProgramado).then(function(result) {
                        var gerarLancamento = result.gerarLancamento;
                        result = ajustarDados(result);
                        if(gerarLancamento) { criarLancamento(conta, result); } 
                        else { 
                            LancamentoProgramadoService.update(idLancamentoProgramado, result)
                                .then(function (data) {  
                                    modalMessage(MESSAGES.lancamento.programar.sucesso.ALTERADO_SUCESSO);
                                    todos(conta);
                                })
                                .catch(function(e) {
                                    modalMessage(e);
                                });
                        }
                    });
                })
                .catch(function(e) {
                    modalMessage(e.error);
                });           
        };
        
        $scope.excluir = function(conta, idLancamentoProgramado) {            
            LancamentoProgramadoService.getLancamento(idLancamentoProgramado)
                .then(function(data) {   
                    if(data.lancamentos.length) {
                        modalMessage(MESSAGES.lancamento.programar.info.LANCAMENTO_VINCULADOS);
                    } else {
                        excluir(conta, idLancamentoProgramado);
                    }
                });
        }
        
        var excluir = function(conta, idLancamentoProgramado) {
            modalExcluir().then(function() {
                LancamentoProgramadoService.delete(idLancamentoProgramado)
                    .then(function(data) { 
                        modalMessage(MESSAGES.lancamento.programar.sucesso.REMOVIDO_SUCESSO);
                        todos(conta);                        
                    })
                    .catch(function(e) {
                        modalMessage(e);
                    });
            });
        }; 
        
        var criarLancamento = function(conta, result) { 
            if(result.idLancamentoProgramado) {
                LancamentoProgramadoService.getByNumeroParcela(result.idLancamentoProgramado, result.numeroParcela)
                    .then(function(data) {  
                        if(data) {
                            modalMessage(MESSAGES.lancamento.programar.info.PARCELA_EXISTENTE);
                        } else {
                            create(conta, result);
                        }
                    })
                    .catch(function(e) {
                        modalMessage(e);
                    });
            } else {
                create(conta, result);
            }
        }
        
        var create = function(conta, result) { 
            LancamentoProgramadoService.create(result)
                .then(function(data) {  
                    modalMessage(MESSAGES.lancamento.sucesso.INSERIDO_SUCESSO);
                    todos(conta);                        
                })
                .catch(function(e) {
                    modalMessage(e);
                });
        }
        
        var ajustarDados = function(data) {       
            delete data.gerarLancamento;
            data.conta = { idConta: data.conta.idConta };       
            data.planoConta = { idPlanoConta: data.planoConta.idPlanoConta };    
            if(data.centroCusto) {
                data.centroCusto = { idCentroCusto: data.centroCusto.idCentroCusto }; 
            }
//            if(data.lancamentoParcelado) {
//                data.lancamentoParcelado = { idLancamentoParcelado: data.lancamentoParcelado.idLancamentoParcelado }; 
//            }
            data.documento = { idTipoDocumento: data.documento.idTipoDocumento }; 
            data.formaPagamento = { idTipoFormaPagamento: data.formaPagamento.idTipoFormaPagamento }; 
            data.tipo = data.tipo.id;
            data.dataEmissao = data.dataEmissao || moment();
            data.dataVencimento = data.dataVencimento || data.dataLancamento || moment();
            data.frequencia = data.frequencia.id;
            data.situacao = data.situacao.id;   
            return data;
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
            var modalInstance = ModalService.modalDefault('partials/financeiro/lancamentoProgramado/modalLancamentoProgramado.html', 'ModalLancamentoProgramadoEditarController', 'lg',
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
        
        var modalExcluir = function() {
            var modalInstance = ModalService.modalExcluir(MESSAGES.lancamento.programar.info.ALERT_EXCLUIR, MESSAGES.lancamento.programar.info.CONFIRMAR_EXCLUIR);
            return modalInstance.result;
        };
        
        init();

    }]);
