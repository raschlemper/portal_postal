'use strict';

app.controller('LancamentoProgramadoController', ['$scope', '$filter', 'LancamentoProgramadoService', 'LancamentoService', 'ContaService', 'ModalService', 'DatePickerService', 'LISTAS',
    function ($scope, $filter, LancamentoProgramadoService, LancamentoService, ContaService, ModalService, DatePickerService, LISTAS) {

        var init = function () {
            $scope.lancamentoProgramados = [];
            $scope.lancamentoProgramadosLista = [];
            $scope.tipos = LISTAS.lancamentoProgramado;
            $scope.datepickerDataInicio = angular.copy(DatePickerService.default);      
            $scope.datepickerDataFim = angular.copy(DatePickerService.default);    
            $scope.lancSearch = {};
            contas();
            initTable();      
        };  
        
        var initTable = function() {            
            $scope.colunas = [              
                {label: 'Tipo', column: 'tipo.descricao'},         
                {label: 'Vencimento', column: 'data', filter: {name: 'date', args: 'dd/MM/yyyy'}},                
                {label: 'Número/Parcela', column: 'numeroParcela'},               
                {label: 'Favorecido', column: 'favorecido'},  
                {label: 'Valor', column: 'valor', class: 'no-sort', filter: {name: 'currency', args: ''}},              
                {label: 'Situação', column: 'situacao'},  
                {label: 'Frequência', column: 'frequencia'}
            ]            
            $scope.events = { 
                edit: function(lancamentoProgramado) {
                    $scope.editar($scope.conta, lancamentoProgramado.idLancamentoProgramado);
                },
                remove: function(lancamentoProgramado) {
                    $scope.excluir($scope.conta, lancamentoProgramado.idLancamentoProgramado);
                },
                view: function(lancamentoProgramado) {
                    $scope.visualizar($scope.conta, lancamentoProgramado.idLancamentoProgramado);
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
            var data = moment(item.data);
            return (data.isBefore(search.dataFim) && data.isAfter(search.dataInicio) 
                    || (data.isSame(search.dataInicio) || data.isSame(search.dataFim)));
        };  
        
        var contas = function() {
            ContaService.getAll()
                .then(function (data) {
                    $scope.contas = data;
                    $scope.conta = $scope.contas[0];
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
            return _.map(data.lancamentoProgramados, function(lancamentoProgramado) {  
                lancamentoProgramado.numeroParcela = lancamentoProgramado.numero + '-' + lancamentoProgramado.numeroParcela;
                return _.pick(lancamentoProgramado, 'idLancamentoProgramado', 'tipo', 'data', 'numeroParcela', 'favorecido', 'valor', 'situacao', 'frequencia');
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
            modalSalvar(conta).then(function(result, gerarLancamento) {
                result = ajustarDados(result);
                LancamentoProgramadoService.save(result)
                    .then(function(data) {  
                        if(gerarLancamento) { gerarLancamento(data); } 
                        else { modalMessage("Lançamento Programado Inserido com sucesso!"); }
                        todos(conta);
                    })
                    .catch(function(e) {
                        modalMessage(e);
                    });
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
                     modalSalvar(conta, lancamentoProgramado).then(function(result, gerarLancamento) {
                        result = ajustarDados(result);
                        LancamentoProgramadoService.update(idLancamentoProgramado, result)
                            .then(function (data) {                                
                                if(gerarLancamento) { gerarLancamento(data); } 
                                else { modalMessage("Lançamento Programado Alterado com sucesso!"); }
                                todos(conta);
                            })
                            .catch(function(e) {
                                modalMessage(e);
                            });
                    });
                })
                .catch(function(e) {
                    modalMessage(e.error);
                });
           
        };

        // Permitir excluir caso não exista lançamento gerado, caso contrário, somente será permitido alterar a situação
        $scope.excluir = function(conta, idLancamentoProgramado) {
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
        
        var gerarLancamento = function(result) {  
            var lancamento = ajustarDadosLancamento(result)
            LancamentoService.save(lancamento)
                .then(function(data) {  
                    modalMessage("Lançamento Inserido com sucesso!");
                })
                .catch(function(e) {
                    modalMessage(e);
                });
        }
        
        var ajustarDados = function(data) {                 
            data.conta = { idConta: data.conta.idConta };       
            data.planoConta = { idPlanoConta: data.planoConta.idPlanoConta }; 
            data.documento = { idTipoDocumento: data.documento.idTipoDocumento }; 
            data.formaPagamento = { idTipoFormaPagamento: data.documento.idTipoFormaPagamento }; 
            data.tipo = data.tipo.id;
            data.frequencia = data.frequencia.id;
            data.situacao = data.situacao.id;
            if(!data.idLancamentoProgramado) { 
                data.quantidadeParcela = null;
                data.numeroParcela = 1; 
            }
            return data;
        } 
        
        var ajustarDadosLancamento = function(data) {    
            return {
                conta: data.conta = { idConta: data.conta.idConta },
                planoConta: data.planoConta = { idPlanoConta: data.planoConta.idPlanoConta }, 
                tipo: data.tipo.id,
                favorecido: data.favorecido,
                numero: data.numero + '-' + data.numeroParcela,
                data: data.data,
                valor: data.valor,
                situacao: data.situacao.id,
                historico: data.historico
            }
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
            var modalInstance = ModalService.modalDefault('partials/financeiro/lancamentoProgramado/modalEditarLancamentoProgramado.html', 'ModalEditarLancamentoProgramadoController', 'lg',
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
