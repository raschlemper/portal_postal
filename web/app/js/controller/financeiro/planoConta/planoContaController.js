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
                        $scope.planoContasLista[key] = criarPlanoContasLista($scope.planoContas[key]);                        
                    });
                })
                .catch(function(e) {
                    modalMessage(e);
                });
        };
        
        var criarPlanoContasLista = function(planoContas) {
            return _.map(planoContas, function(planoConta) {
                return _.pick(planoConta, 'idPlanoConta', 'tipo', 'codigo', 'nome', 'grupo', 'contas');
            })
        }

        $scope.salvar = function(idPlanoConta) {
            PlanoContaService.get(idPlanoConta)
                .then(function(planoConta) {                  
                    modalSalvar(planoConta, 'save')
                    .then(function(result) {                                     
                        PlanoContaService.getByTipoGrupoCodigo(result.tipo.id, planoConta.idPlanoConta, result.codigo)
                        .then(function(data) {
                            if(data) { modalMessage("Este Plano de Conta já existe!"); } 
                            else { return result; }
                        }).then(function(result) {   
                            if(!result) return;
                            result = ajustarDados(result, planoConta);
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
        };

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
        }; 
        
        var ajustarDados = function(data, planoConta) {            
            data.tipo = data.tipo.id; 
            data.grupo = {
                idPlanoConta: planoConta.idPlanoConta
            }
            return data;
        }
        
        var modalMessage = function(message) {
            ModalService.modalMessage(message);
        };
        
        var modalSalvar = function(planoConta, action) {
            var modalInstance = ModalService.modalDefault('partials/financeiro/modalEditarPlanoConta.html', 'ModalEditarPlanoContaController', 'lg',
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
