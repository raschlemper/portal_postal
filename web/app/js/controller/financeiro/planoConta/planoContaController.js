'use strict';

app.controller('PlanoContaController', 
    ['$scope', '$q', 'PlanoContaService', 'ReportService', 'ModalService', 'PlanoContaHandler', 'StringService', 'LISTAS', 'MESSAGES',
    function ($scope, $q, PlanoContaService, ReportService, ModalService, PlanoContaHandler, StringService, LISTAS, MESSAGES) {

        var init = function () {
            $scope.planoContas = [];
            $scope.planoContasLista = [];
            $scope.tipos = LISTAS.planoConta;
            initTree();
            todos();
        };  

        // ***** TREE ***** //

        var initTree = function() {            
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
            };            
        };

        // ***** CONTROLLER ***** //

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

        // ***** SALVAR ***** //

        $scope.salvar = function(idPlanoConta) {            
            $q.all([PlanoContaService.getLancamento(idPlanoConta),
                    PlanoContaService.getLancamentoProgramado(idPlanoConta)])
                .then(function(values) {    
                    if(values[0].lancamentos.length || values[1].lancamentosProgramados.length) {
                        modalMessage(MESSAGES.planoConta.info.NAO_PERMITE_ADD_GRUPO);
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
                            save(planoConta, result);
                        });
                })
                .catch(function(e) {
                    modalMessage(e);
                });
        }
        
        var save = function(planoConta, result) {
            var grupo = planoConta || result.grupo;
            PlanoContaService.getByTipoGrupoCodigo(result.tipo.id, grupo.idPlanoConta, result.codigo)
                .then(function(data) {
                    if(data) { modalMessage(MESSAGES.planoConta.info.PLANO_CONTA_EXISTENTE); } 
                    else { return result; }
                }).then(function(result) {   
                    if(!result) return;
                    result = ajustarDados(result, result.grupo);
                    PlanoContaService.save(result)
                        .then(function(data) {  
                            //modalMessage(StringService.format(MESSAGES.planoConta.sucesso.INSERIDO_SUCESSO, data.nome));
                            todos();
                        })
                        .catch(function(e) {
                            modalMessage(e);
                        });
                });
        };

        // ***** EDITAR ***** //

        $scope.editar = function(idPlanoConta) {
            PlanoContaService.get(idPlanoConta)
                .then(function(planoConta) {
                    modalSalvar(planoConta, 'edit')
                        .then(function(result) {    
                            editar(planoConta, result);
                        });
                })
                .catch(function(e) {
                    modalMessage(e);
                });           
        };
        
        var editar = function(planoConta, result) {                           
            var grupo = result.grupo || planoConta;                                   
            PlanoContaService.getByTipoGrupoCodigo(result.tipo.id, grupo.idPlanoConta, result.codigo)
                .then(function(data) {
                    if(data && data.idPlanoConta !== result.idPlanoConta) { modalMessage("Este Plano de Conta já existe!"); } 
                    else { return result; }
                }).then(function(result) {    
                    if(!result) return;
                    result = ajustarDados(result, result.grupo);
                    PlanoContaService.update(planoConta.idPlanoConta, result)
                        .then(function (data) {  
                            modalMessage("Plano de Conta " + data.nome + " Alterado com sucesso!");
                            todos();
                        })
                        .catch(function(e) {
                            modalMessage(e);
                        });
                });
        };

        // ***** EXCLUIR ***** //

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
        };
        
        $scope.setDefault = function() {
            modalDefault().then(function() {
                PlanoContaService.setDefault()
                    .then(function(data) { 
                        modalMessage("Plano de Conta atualizado com sucesso!");
                        todos();                        
                    })
                    .catch(function(e) {
                        modalMessage(e);
                    });
            });
        };

        // ***** REPORT ***** //
        
        $scope.report = function(planoContas) {
            var params = PlanoContaService.report(planoContas);   
            ReportService.pdf('planoconta', params);
        };

        // ***** VALIDAR ***** //

        // ***** AJUSTAR ***** //
        
        var ajustarDados = function(data, grupo) { 
            data.grupo = grupo;
            return PlanoContaHandler.handle(data);
        }

        // ***** MODAL ***** //
        
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
            var modalInstance = ModalService.modalExcluir('Excluir Plano de Conta?', 'Deseja realmente excluir este plano de conta?');
            return modalInstance.result;
        };
        
        var modalDefault = function() {
            var modalInstance = ModalService.modalExcluir('Carregar Plano de Conta Padrão?', 
            'Deseja realmente carregar o plano de conta padrão?<br/>Todos os planos de contas dos lançamentos serão excluídos!');
            return modalInstance.result;
        };
        
        init();

    }]);
