'use strict';

app.controller('PlanoContaController', ['$scope', 'PlanoContaService', 'ModalService',
    function ($scope, PlanoContaService, ModalService) {

        var init = function () {
            $scope.planoContas = [];
            $scope.planoContasLista = [];
            
            $scope.colunas = [
                {label: 'Número', column: 'numero'},
                {label: 'Nome', column: 'nome', class: 'col-md-4', filter: {name: 'uppercase', args: null}},                
                {label: 'Website', column: 'website', class: 'col-md-4'}
            ]
            
            $scope.events = { 
                edit: function(planoConta) {
                    $scope.editar(planoConta.idPlanoConta);
                },
                remove: function(planoConta) {
                    $scope.excluir(planoConta.idPlanoConta);
                }
            }         
        };  

        var todos = function() {
            PlanoContaService.getStructure()
                .success(function(data) {
                    $scope.planoContas = data;
                    $scope.planoContasLista = criarPlanoContasLista($scope.planoContas);
                })
                .error(function(e) {
                    modalMessage(e);
                });
        };
        
        var criarPlanoContasLista = function(planoContas) {
            return _.map(planoContas, function(planoConta) {
                return _.pick(planoConta, 'idPlanoConta', 'tipo', 'codigo', 'nome', 'grupo', 'contas');
            })
        }

        $scope.salvar = function() {
            modalSalvar().then(function(result) {
                PlanoContaService.save(result)
                    .then(function(data) {  
                        modalMessage("PlanoConta " + data.nome +  " Inserido com sucesso!");
                        todos();
                    })
                    .catch(function(e) {
                        modalMessage(e);
                    });
            });
        };

        $scope.editar = function(idPlanoConta) {
            PlanoContaService.get(idPlanoConta)
                .then(function(planoConta) {
                     modalSalvar(planoConta).then(function(result) {
                        PlanoContaService.update(idPlanoConta, result)
                            .then(function (data) {  
                                modalMessage("PlanoConta " + data.nome + " Alterado com sucesso!");
                                todos();
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

        $scope.excluir = function(idPlanoConta) {
            modalExcluir().then(function() {
                PlanoContaService.delete(idPlanoConta)
                    .then(function(data) { 
                        modalMessage("PlanoConta " + data.nome + " Removido com sucesso!");
                        todos();                        
                    })
                    .catch(function(e) {
                        modalMessage(e);
                    });
            });
        }; 
        
        var modalMessage = function(message) {
            ModalService.modalMessage(message);
        };
        
        var modalSalvar = function(planoConta) {
            var modalInstance = ModalService.modalDefault('partials/financeiro/modalEditarPlanoConta.html', 'ModalEditarPlanoContaController', 'lg',
                {
                    planoConta: function() {
                        return planoConta;
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
