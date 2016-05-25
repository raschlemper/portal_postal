'use strict';

app.controller('LancamentoProgramadoController', ['$scope', '$filter', '$state', 'LancamentoProgramadoService', 'ContaService', 'ModalService', 'DatePickerService', 'LISTAS',
    function ($scope, $filter, $state, LancamentoProgramadoService, ContaService, ModalService, DatePickerService, LISTAS) {

        var init = function () {
            $scope.lancamentoProgramados = [];
            $scope.lancamentoProgramadosLista = [];
            $scope.tipos = LISTAS.lancamento;
            $scope.datepickerDataInicio = angular.copy(DatePickerService.default);      
            $scope.datepickerDataFim = angular.copy(DatePickerService.default);    
            $scope.lancSearch = {};
            contas();
            initTable(); 
        };  
        
        var initTable = function() {            
            $scope.colunas = [              
                {label: '', column: 'tipo.descricao', headerClass: 'no-sort', dataClass:'text-center col-tipo', filter: {name: 'tipoLancamento', args: ''}},         
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
                        $scope.editar($scope.conta, lancamentoProgramado.idLancamentoProgramado);
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
            return $filter('filter')(lista, search.tipo);
        };
        
        var filterByData = function(item, search) {
            if(!search.dataInicio || !search.dataFim) return true;
            var data = moment(item.dataVencimento);
            return (data.isBefore(search.dataFim) && data.isAfter(search.dataInicio) 
                    || (data.isSame(search.dataInicio) || data.isSame(search.dataFim)));
        };  
        
        var contas = function() {
            ContaService.getAll()
                .then(function (data) {
                    $scope.contas = data;
                    $scope.conta = $scope.contas[0];
                    if($state.params.id) { $scope.editar($scope.conta, $state.params.id); }
                    todos($scope.conta);  
                })
                .catch(function (e) {
                    console.log(e);
                });
        };
        
        $scope.changeConta = function(conta) {
            $scope.conta = conta;
            todos(conta);
        }

        var todos = function(conta) {
            ContaService.getLancamentoProgramado(conta.idConta)
                .then(function (data) {
                    $scope.lancamentoProgramados = data.lancamentoProgramados;
                    $scope.lancamentoProgramadosLista = criarLancamentoProgramadosLista(data);
                })
                .catch(function(e) {
                    modalMessage(e);
                });
        };
        
        var criarLancamentoProgramadosLista = function(data) {
            return _.map(data.lancamentosProgramados, function(lancamentoProgramado) {  
                
                var complementoDescricaoFrequencia = '';
                if(lancamentoProgramado.quantidadeParcela) { 
                    complementoDescricaoFrequencia = ' - Parcelado (' + lancamentoProgramado.quantidadeParcela + 'x)';
                }
                
                lancamentoProgramado.situacao = lancamentoProgramado.situacao.descricao;
                lancamentoProgramado.frequencia = lancamentoProgramado.frequencia.descricao + complementoDescricaoFrequencia;
                lancamentoProgramado.numeroParcela = lancamentoProgramado.numero;
                
                return _.pick(lancamentoProgramado, 'idLancamentoProgramado', 'tipo', 'dataVencimento', 'numeroParcela', 'favorecido', 'valor', 'situacao', 'frequencia');
            })
        };

        $scope.visualizar = function(conta, idLancamentoProgramado) {
            LancamentoProgramadoService.get(idLancamentoProgramado)
                .then(function(lancamentoProgramado) {
                     modalVisualizar(lancamentoProgramado).then(function(result) {
                        $scope.editar(conta, result);
                     })          
                })
                .catch(function(e) {
                    modalMessage(e);
                });
        };

        $scope.salvar = function(conta) {
            modalSalvar(conta).then(function(result) {
                var gerarLancamento = result.gerarLancamento;
                result = ajustarDados(result);
                if(gerarLancamento) { criarLancamento(conta, result); } 
                else { 
                    LancamentoProgramadoService.save(result)
                        .then(function(data) { 
                            modalMessage("Lançamento Programado Inserido com sucesso!");
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

        $scope.editar = function(conta, idLancamentoProgramado) {
            LancamentoProgramadoService.get(idLancamentoProgramado)
                .then(function(lancamentoProgramado) {
                     modalSalvar(conta, lancamentoProgramado).then(function(result) {
                        var gerarLancamento = result.gerarLancamento;
                        result = ajustarDados(result);
                        if(gerarLancamento) { criarLancamento(conta, result); } 
                        else { 
                            LancamentoProgramadoService.update(idLancamentoProgramado, result)
                                .then(function (data) {  
                                    modalMessage("Lançamento Programado Alterado com sucesso!");
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
                        modalMessage("Este lançamento programado não pode ser excluído! <br/> Existem Lançamentos vinculados a este lançamento programado.");
                    } else {
                        excluir(conta, idLancamentoProgramado);
                    }
                });
        }
        
        var excluir = function(conta, idLancamentoProgramado) {
            modalExcluir().then(function() {
                LancamentoProgramadoService.delete(idLancamentoProgramado)
                    .then(function(data) { 
                        modalMessage("Lançamento Programado Removido com sucesso!");
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
                            modalMessage("Este lançamento não pode ser inserido, pois esta parcela já existe!");
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
                    modalMessage("Lançamento Inserido com sucesso!");
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
            var modalInstance = ModalService.modalDefault('partials/financeiro/lancamentoProgramado/modalVisualizarLancamentoProgramado.html', 'ModalVisualizarLancamentoProgramadoController', 'md',
                {
                    lancamentoProgramado: function() {
                        return lancamentoProgramado;
                    }
                });
            return modalInstance.result;
        };
        
        var modalSalvar = function(conta, lancamentoProgramado) {
            var modalInstance = ModalService.modalDefault('partials/financeiro/lancamentoProgramado/modalLancamentoProgramado.html', 'ModalEditarLancamentoProgramadoController', 'lg',
                {
                    lancamentoProgramado: function() {
                        return lancamentoProgramado;
                    },
                    conta: function() {
                        return conta;
                    }
                });
            return modalInstance.result;
        };
        
//        var modalTransferir = function() {
//            var modalInstance = ModalService.modalDefault('partials/financeiro/lancamentoProgramado/modalLancamentoProgramadoTransferencia.html', 'ModalLancamentoProgramadoTransferenciaController', 'lg');
//            return modalInstance.result;
//        };
        
        var modalExcluir = function() {
            var modalInstance = ModalService.modalExcluir('Excluir Lançamento Programado?', 'Deseja realmente excluir este lançamento programado?');
            return modalInstance.result;
        };
        
        init();

    }]);
