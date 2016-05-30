'use strict';

app.controller('PlanoContaController', ['$scope', '$q', 'PlanoContaService', 'ModalService', 'LISTAS',
    function ($scope, $q, PlanoContaService, ModalService, LISTAS) {

        var init = function () {
            $scope.planoContas = [];
            $scope.planoContasLista = [];
            $scope.tipos = LISTAS.planoConta;
            
            $scope.events = { 
                add: function(idPlanoConta) {
                    $scope.salvar(idPlanoConta);
                },
                edit: function(idPlanoConta) {
                    $scope.editar(idPlanoConta);
                },
                remove: function(idPlanoConta) {
                    $scope.excluir(idPlanoConta);
                }
            }         
        };  

        var todos = function() {
            var planos = [];
            angular.forEach(LISTAS.planoConta, function(plano) {
                planos.push(PlanoContaService.getStructureByTipo(plano.id));
            })
            $q.all(planos)
                .then(function(values) {  
                    angular.forEach(values, function(value, key) {
                        $scope.planoContas[key] = value;
                        $scope.planoContasLista[key] = $scope.planoContas[key];
                        PlanoContaService.estrutura($scope.planoContasLista[key]);
                    });
                })
                .catch(function(e) {
                    modalMessage(e);
                });
        };

        $scope.salvar = function(idPlanoConta) {            
            $q.all([PlanoContaService.getLancamento(idPlanoConta),
                    PlanoContaService.getLancamentoProgramado(idPlanoConta)])
                .then(function(values) {    
                    if(values[0].lancamentos.length || values[1].lancamentosProgramados.length) {
                        modalMessage("Não é permitido adicionar contas a um plano de conta que possua lançamentos vinculados.");
                    } else {
                        salvar(idPlanoConta);
                    }
                });
        };
        
        var salvar = function(idPlanoConta) {                   
            PlanoContaService.get(idPlanoConta)
                .then(function(planoConta) {                  
                    modalSalvar(planoConta, 'save')
                    .then(function(result) {                                     
                        PlanoContaService.getByTipoGrupoCodigo(result.tipo.id, planoConta.grupo.idPlanoConta, result.codigo)
                        .then(function(data) {
                            if(data) { modalMessage("Este Plano de Conta já existe!"); } 
                            else { return result; }
                        }).then(function(result) {   
                            if(!result) return;
                            result = ajustarDados(result, result.grupo);
                            PlanoContaService.save(result)
                            .then(function(data) {  
                                modalMessage("Plano de Conta " + data.nome +  " Inserido com sucesso!");
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

        $scope.editar = function(idPlanoConta) {
            PlanoContaService.get(idPlanoConta)
                .then(function(planoConta) {
                     modalSalvar(planoConta, 'edit')
                     .then(function(result) {                                   
                        PlanoContaService.getByTipoGrupoCodigo(result.tipo.id, result.grupo.idPlanoConta, result.codigo)
                        .then(function(data) {
                            if(data && data.idPlanoConta != result.idPlanoConta) { modalMessage("Este Plano de Conta já existe!"); } 
                            else { return result; }
                        }).then(function(result) {    
                            if(!result) return;
                            result = ajustarDados(result, result.grupo);
                            PlanoContaService.update(idPlanoConta, result)
                            .then(function (data) {  
                                modalMessage("Plano de Conta " + data.nome + " Alterado com sucesso!");
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
        };

        $scope.excluir = function(idPlanoConta) {
            $q.all([PlanoContaService.getLancamento(idPlanoConta),
                    PlanoContaService.getLancamentoProgramado(idPlanoConta)])
                .then(function(values) {   
                    if(values[0].lancamentos.length || values[1].lancamentosProgramados.length) {
                        modalMessage("Este plano de conta não pode ser excluído! <br/> Existem Lançamentos vinculados a este plano de conta.");
                    } else {
                        excluir(idPlanoConta);
                    }
                });
        }; 
        
        var excluir = function(idPlanoConta) {
            modalExcluir().then(function() {
                PlanoContaService.delete(idPlanoConta)
                    .then(function(data) { 
                        modalMessage("Plano de Conta " + data.nome + " Removido com sucesso!");
                        todos();                        
                    })
                    .catch(function(e) {
                        modalMessage(e);
                    });
            });
        }
        
        var ajustarDados = function(data, grupo) { 
            delete data.contas;
            data.tipo = data.tipo.id; 
            data.grupo = {
                idPlanoConta: grupo.idPlanoConta
            }
            return data;
        }
        
        var modalMessage = function(message) {
            ModalService.modalMessage(message);
        };
        
        var modalSalvar = function(planoConta, action) {
            var modalInstance = ModalService.modalDefault('partials/financeiro/planoConta/modalPlanoContaEditar.html', 'ModalPlanoContaEditarController', 'lg',
                {
                    planoConta: function() {
                        return planoConta;
                    },
                    action: function() {
                        return action;
                    }
                });
            return modalInstance.result;
        };
        
        var modalExcluir = function() {
            var modalInstance = ModalService.modalExcluir('Excluir PlanoConta?', 'Deseja realmente excluir este planoConta?');
            return modalInstance.result;
        };

        todos();
        init();

    }]);
