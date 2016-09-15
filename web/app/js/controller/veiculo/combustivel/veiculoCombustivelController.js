'use strict';

app.controller('VeiculoCombustivelController', ['$scope', '$filter', 'VeiculoCombustivelService', 'ModalService', 'VeiculoHandler', 'VeiculoCombustivelHandler',
    function ($scope, $filter, VeiculoCombustivelService, ModalService, VeiculoHandler, VeiculoCombustivelHandler) {

        var init = function () {
            $scope.veiculosCombustivel = [];
            $scope.veiculosCombustivelLista = [];
            initTable();   
            todos();
        };     

        // ***** TABLE ***** //
        
        var initTable = function() {            
            $scope.colunas = [
                {label: 'Placa', column: 'placa', filter: {name:'placa', args: null}},
                {label: 'Data', column: 'data', filter: {name: 'date', args: 'dd/MM/yyyy'}},                
                {label: 'Km', column: 'quilometragem', filter: {name:'number', args: null}},             
                {label: 'Quantidade', column: 'quantidade', filter: {name:'number', args: null}},            
                {label: 'Valor Unitário', column: 'valorUnitario', filter: {name:'currency', args: null}},            
                {label: 'Valor Total', column: 'valorTotal', filter: {name:'currency', args: null}}
            ]            
            $scope.linha = {
                events: { 
                    edit: function(banco) {
                        $scope.editar(banco);
                    },
                    remove: function(banco) {
                        $scope.excluir(banco);
                    }
                }   
            }          
        };        

        // ***** CONTROLLER ***** //

        var todos = function() {
            VeiculoCombustivelService.getAll()
                .then(function (data) {
                    $scope.veiculosCombustivel = data;
                    $scope.veiculosCombustivelLista = criarVeiculosCombustivelLista($scope.veiculosCombustivel);
                })
                .catch(function(e) {
                    modalMessage(e);
                });
        };
        
        var criarVeiculosCombustivelLista = function(combustiveis) {
            return _.map(combustiveis, function(combustivel) {
                var obj = _.pick(combustivel, 'idVeiculoCombustivel', 'data', 'quilometragem', 'quantidade', 'valorUnitario', 'valorTotal');
                return _.extend(obj, _.pick(combustivel.veiculo, 'marca', 'modelo', 'placa'));
            })
        };
    
        var getMsgToClient = function(veiculo) {
            return veiculo.modelo + " (" + $filter('placa')(veiculo.placa) + ")";        
        };

        // ***** VISUALIZAR ***** //

        $scope.visualizar = function(combustivel) {
            VeiculoCombustivelService.get(combustivel.idVeiculoCombustivel)
                .then(function(result) {
                    visualizar(result);
                })
                .catch(function(e) {
                    modalMessage(e);
                });
        };

        var visualizar = function(combustivel) {
            modalVisualizar(combustivel)
                .then(function(result) {
                    $scope.editar(result);
                });
        };

        // ***** SALVAR ***** //

        $scope.salvar = function() {
            salvar();
        };

        var salvar = function() {
            modalSalvar().then(function(result) {
                result = ajustarDados(result);
                save(result);
            });
        };

        var save = function(combustivel) {
            VeiculoCombustivelService.save(combustivel)
                .then(function(data) {  
                    modalMessage("Abastecimento do Veículo " + getMsgToClient(data.veiculo) +  " Inserido com sucesso!");
                    todos();
                })
                .catch(function(e) {
                    modalMessage(e);
                });
        };

        // ***** EDITAR ***** //

        $scope.editar = function(combustivel) {
            VeiculoCombustivelService.get(combustivel.idVeiculoCombustivel)
                .then(function(data) {
                     editar(combustivel);
                })
                .catch(function(e) {
                    modalMessage(e.error);
                });
        };

        var editar = function(combustivel) {
            modalSalvar(combustivel)
                .then(function(result) {
                    result = ajustarDados(result);
                    update(combustivel);
                }); 
        };
        
        var update = function(combustivel) {
            VeiculoCombustivelService.update(combustivel.idVeiculoCombustivel, combustivel)
                .then(function (data) {  
                    modalMessage("Abastecimento do Veículo " + getMsgToClient(data.veiculo) + " Alterado com sucesso!");
                    todos();
                })
                .catch(function(e) {
                    modalMessage(e);
                });             
        };

        // ***** EXCLUIR ***** //

        $scope.excluir = function(combustivel) {
            excluir(combustivel);
        }; 
        
        var excluir = function(combustivel) {
            modalExcluir()
                .then(function() {
                    remove(combustivel);
                });
        };
        
        var remove = function(combustivel) {
            VeiculoCombustivelService.delete(combustivel.idVeiculoCombustivel)
                .then(function(data) { 
                    modalMessage("Abastecimento do Veículo " + getMsgToClient(data.veiculo) + " Removido com sucesso!");
                    todos();                        
                })
                .catch(function(e) {
                    modalMessage(e);
                });
        };

        // ***** AJUSTAR ***** //
                
        var ajustarDados = function(data) {  
            var combustivel = VeiculoCombustivelHandler.handle(data);
            combustivel.veiculo = VeiculoHandler.handleList(data.veiculo);  
            return combustivel;
        }

        // ***** MODAL ***** //
        
        var modalMessage = function(message) {
            ModalService.modalMessage(message);
        };
        
        var modalVisualizar = function(veiculoCombustivel) {
            var modalInstance = ModalService.modalDefault('partials/veiculo/combustivel/modalVisualizarVeiculoCombustivel.html', 'ModalVisualizarVeiculoCombustivelController', 'md',
                {
                    veiculoCombustivel: function() {
                        return veiculoCombustivel;
                    }
                });
            return modalInstance.result;
        };
        
        var modalSalvar = function(veiculoCombustivel) {
            var modalInstance = ModalService.modalDefault('partials/veiculo/combustivel/modalEditarVeiculoCombustivel.html', 'ModalEditarVeiculoCombustivelController', 'lg',
                {
                    veiculoCombustivel: function() {
                        return veiculoCombustivel;
                    }
                });
            return modalInstance.result;
        };
        
        var modalExcluir = function() {
            var modalInstance = ModalService.modalExcluir('Excluir Abastecimento?', 'Deseja realmente excluir este abastecimento?');
            return modalInstance.result;
        };
        
        init();

    }]);
