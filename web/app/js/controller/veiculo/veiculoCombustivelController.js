'use strict';

app.controller('VeiculoCombustivelController', ['$scope', '$filter', 'VeiculoCombustivelService', 'ModalService', 'DataTableService',
    function ($scope, $filter, VeiculoCombustivelService, ModalService, DataTableService) {

        var init = function () {
            $scope.veiculosCombustivel = [];
            $scope.veiculosCombustivelLista = [];
            $scope.dtOptions = DataTableService.default();
        };             

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
                return _.extend(obj, _.pick(combustivel.veiculo, 'placa'));
            })
        }

        $scope.visualizar = function(idVeiculo) {
            VeiculoCombustivelService.get(idVeiculo)
                .then(function(veiculo) {
                     modalVisualizar(veiculo).then(function(result) {
                         $scope.editar(result);
                     })          
                })
                .catch(function(e) {
                    modalMessage(e);
                });
        };

        $scope.salvar = function() {
            modalSalvar().then(function(result) {
                result = ajustarDados(result);
                VeiculoCombustivelService.save(result)
                    .then(function(data) {  
                        modalMessage("Abastecimento do Veículo Inserido " + getMsgToClient(data.veiculo) +  " com sucesso!");
                        todos();
                    })
                    .catch(function(e) {
                        modalMessage(e);
                    });
            });
        };

        $scope.editar = function(idVeiculo) {
            VeiculoCombustivelService.get(idVeiculo)
                .then(function(veiculo) {
                     modalSalvar(veiculo).then(function(result) {
                        result = ajustarDados(result);
                        VeiculoCombustivelService.update(idVeiculo, result)
                            .then(function (data) {  
                                modalMessage("Abastecimento do Veículo " + getMsgToClient(data.veiculo) + " Alterado com sucesso!");
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

        $scope.excluir = function(idVeiculo) {
            modalExcluir().then(function() {
                VeiculoCombustivelService.delete(idVeiculo)
                    .then(function(data) { 
                        modalMessage("Abastecimento do Veículo " + getMsgToClient(data.veiculo) + " Removido com sucesso!");
                        todos();                        
                    })
                    .catch(function(e) {
                        modalMessage(e);
                    });
            });
        }; 
        
        var ajustarDados = function(data) {            
            data.tipo = data.tipo.id; 
            data.veiculo = { 
                idVeiculo: data.veiculo.idVeiculo,
                modelo: data.veiculo.modelo,
                placa: data.veiculo.placa
            };
            return data;
        }
    
        var getMsgToClient = function(veiculo) {
            return veiculo.modelo + " (" + $filter('placa')(veiculo.placa) + ")";        
        }
        
        var modalMessage = function(message) {
            ModalService.modalMessage(message);
        };
        
        var modalVisualizar = function(veiculoCombustivel) {
            var modalInstance = ModalService.modalDefault('partials/veiculo/modalVisualizarVeiculoCombustivel.html', 'ModalVisualizarVeiculoCombustivelController', 'md',
                {
                    veiculoCombustivel: function() {
                        return veiculoCombustivel;
                    }
                });
            return modalInstance.result;
        };
        
        var modalSalvar = function(veiculoCombustivel) {
            var modalInstance = ModalService.modalDefault('partials/veiculo/modalEditarVeiculoCombustivel.html', 'ModalEditarVeiculoCombustivelController', 'lg',
                {
                    veiculoCombustivel: function() {
                        return veiculoCombustivel;
                    }
                });
            return modalInstance.result;
        };
        
        var modalExcluir = function() {
            var modalInstance = ModalService.modalExcluir('Excluir Veículo?', 'Deseja realmente excluir este abastecimento?');
            return modalInstance.result;
        };

        todos();
        init();

    }]);
