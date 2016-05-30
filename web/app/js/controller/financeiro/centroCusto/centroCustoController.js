'use strict';

app.controller('CentroCustoController', ['$scope', '$q', 'CentroCustoService', 'ModalService',
    function ($scope, $q, CentroCustoService, ModalService) {

        var init = function () {
            $scope.centroCustos = [];
            $scope.centroCustosLista = [];
            
            $scope.events = { 
                add: function(idCentroCusto) {
                    $scope.salvar(idCentroCusto);
                },
                edit: function(idCentroCusto) {
                    $scope.editar(idCentroCusto);
                },
                remove: function(idCentroCusto) {
                    $scope.excluir(idCentroCusto);
                }
            }         
        };  

        var todos = function() {
            CentroCustoService.getStructure()
                .then(function(data) {  
                    $scope.centroCustos = data;
                    $scope.centroCustosLista = $scope.centroCustos;
                    CentroCustoService.estrutura($scope.centroCustosLista);
                })
                .catch(function(e) {
                    modalMessage(e);
                });
        };
        
        $scope.grupo = function() {
            modalSalvar(null, 'grupo')
            .then(function(result) {  
                if(!result) return;
                result = ajustarDados(result, result.grupo);
                CentroCustoService.save(result)
                .then(function(data) {  
                    modalMessage("Centro de Custo " + data.nome +  " Inserido com sucesso!");
                    todos();
                })
                .catch(function(e) {
                    modalMessage(e);
                });
            });
        }

        $scope.salvar = function(idCentroCusto) {            
            $q.all([CentroCustoService.getLancamento(idCentroCusto),
                    CentroCustoService.getLancamentoProgramado(idCentroCusto)])
                .then(function(values) {    
                    if(values[0].lancamentos.length || values[1].lancamentosProgramados.length) {
                        modalMessage("Não é permitido adicionar contas a um centro de custo que possua lançamentos vinculados.");
                    } else {
                        salvar(idCentroCusto);
                    }
                });
        };
        
        var salvar = function(idCentroCusto) {                   
            CentroCustoService.get(idCentroCusto)
                .then(function(centroCusto) {                  
                    modalSalvar(centroCusto, 'save')
                    .then(function(result) {     
                        var grupo = centroCusto.grupo || result.grupo;
                        CentroCustoService.getByGrupoCodigo(grupo.idCentroCusto, result.codigo)
                        .then(function(data) {
                            if(data) { modalMessage("Este Centro de Custo já existe!"); } 
                            else { return result; }
                        }).then(function(result) {   
                            if(!result) return;
                            result = ajustarDados(result, result.grupo);
                            CentroCustoService.save(result)
                            .then(function(data) {  
                                modalMessage("Centro de Custo " + data.nome +  " Inserido com sucesso!");
                                todos();
                            })
                            .catch(function(e) {
                                modalMessage(e);
                            });
                        });
                     });
                })
                .catch(function(e) {
                    modalMessage(e);
                });
        }

        $scope.editar = function(idCentroCusto) {
            CentroCustoService.get(idCentroCusto)
                .then(function(centroCusto) {
                     modalSalvar(centroCusto, 'edit')
                     .then(function(result) {                    
                        var grupo = result.grupo || centroCusto.grupo;      
                        if(grupo) {
                            CentroCustoService.getByGrupoCodigo(grupo.idCentroCusto, result.codigo)
                            .then(function(data) {
                                if(data && data.idCentroCusto != result.idCentroCusto) { modalMessage("Este Centro de Custo já existe!"); } 
                                else { return result; }
                            }).then(function(result) {    
                                update(idCentroCusto, result);
                            });
                        } else {
                            update(idCentroCusto, result);
                        }
                    });
                })
                .catch(function(e) {
                    modalMessage(e);
                });           
        };
        
        var update = function(idCentroCusto, result) {
            if(!result) return;
            result = ajustarDados(result, result.grupo);
            CentroCustoService.update(idCentroCusto, result)
            .then(function (data) {  
                modalMessage("Centro de Custo " + data.nome + " Alterado com sucesso!");
                todos();
            })
            .catch(function(e) {
                modalMessage(e);
            });
        }

        $scope.excluir = function(idCentroCusto) {
            $q.all([CentroCustoService.getLancamento(idCentroCusto),
                    CentroCustoService.getLancamentoProgramado(idCentroCusto)])
                .then(function(values) {   
                    if(values[0].lancamentos.length || values[1].lancamentosProgramados.length) {
                        modalMessage("Este centro de custo não pode ser excluído! <br/> Existem Lançamentos vinculados a este centro de custo.");
                    } else {
                        excluir(idCentroCusto);
                    }
                });
        }; 
        
        var excluir = function(idCentroCusto) {
            modalExcluir().then(function() {
                CentroCustoService.delete(idCentroCusto)
                    .then(function(data) { 
                        modalMessage("Centro de Custo " + data.nome + " Removido com sucesso!");
                        todos();                        
                    })
                    .catch(function(e) {
                        modalMessage(e);
                    });
            });
        }
        
        var ajustarDados = function(data, grupo) { 
            delete data.contas;
            data.grupo = null;
            if(grupo) {
                data.grupo = {
                    idCentroCusto: grupo.idCentroCusto
                }
            }
            return data;
        }
        
        var modalMessage = function(message) {
            ModalService.modalMessage(message);
        };
        
        var modalSalvar = function(centroCusto, action) {
            var modalInstance = ModalService.modalDefault('partials/financeiro/centroCusto/modalCentroCustoEditar.html', 'ModalCentroCustoEditarController', 'lg',
                {
                    centroCusto: function() {
                        return centroCusto;
                    },
                    action: function() {
                        return action;
                    }
                });
            return modalInstance.result;
        };
        
        var modalExcluir = function() {
            var modalInstance = ModalService.modalExcluir('Excluir CentroCusto?', 'Deseja realmente excluir este centroCusto?');
            return modalInstance.result;
        };

        todos();
        init();

    }]);
