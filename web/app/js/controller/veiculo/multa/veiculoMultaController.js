'use strict';

app.controller('VeiculoMultaController', ['$scope', '$filter', 'VeiculoMultaService', 'ModalService', 'DataTableService',
    function ($scope, $filter, VeiculoMultaService, ModalService, DataTableService) {

        var init = function () {
            $scope.veiculosMulta = [];
            $scope.veiculosMultaLista = [];
            $scope.dtOptions = DataTableService.default();
        };             

        var todos = function() {
            VeiculoMultaService.getAll()
                .then(function (data) {
                    $scope.veiculosMulta = data;
                    $scope.veiculosMultaLista = criarVeiculosMultaLista($scope.veiculosMulta);
                })
                .catch(function(e) {
                    modalMessage(e);
                });
        };
        
        var criarVeiculosMultaLista = function(manutencoes) {
            return _.map(manutencoes, function(multa) {
                var obj = _.pick(multa, 'idVeiculoMulta', 'numero', 'condutor', 'data', 'valor');
                return _.extend(obj, _.pick(multa.veiculo, 'marca', 'modelo', 'placa'));
            })
        }

        $scope.visualizar = function(idVeiculoMulta) {
            VeiculoMultaService.get(idVeiculoMulta)
                .then(function(veiculoMulta) {
                     modalVisualizar(veiculoMulta).then(function(result) {
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
                VeiculoMultaService.save(result)
                    .then(function(data) {  
                        modalMessage("Multa do Veículo " + getMsgToClient(data.veiculo) +  " Inserido com sucesso!");
                        todos();
                    })
                    .catch(function(e) {
                        modalMessage(e);
                    });
            });
        };

        $scope.editar = function(idVeiculoMulta) {
            VeiculoMultaService.get(idVeiculoMulta)
                .then(function(veiculoMulta) {
                     modalSalvar(veiculoMulta).then(function(result) {
                        result = ajustarDados(result);
                        VeiculoMultaService.update(idVeiculoMulta, result)
                            .then(function (data) {  
                                modalMessage("Multa do Veículo " + getMsgToClient(data.veiculo) + " Alterado com sucesso!");
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

        $scope.excluir = function(idVeiculoMulta) {
            modalExcluir().then(function() {
                VeiculoMultaService.delete(idVeiculoMulta)
                    .then(function(data) { 
                        modalMessage("Multa do Veículo " + getMsgToClient(data.veiculo) + " Removido com sucesso!");
                        todos();                        
                    })
                    .catch(function(e) {
                        modalMessage(e);
                    });
            });
        }; 
        
        var ajustarDados = function(data) {  
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
        
        var modalVisualizar = function(veiculoMulta) {
            var modalInstance = ModalService.modalDefault('partials/veiculo/multa/modalVisualizarVeiculoMulta.html', 'ModalVisualizarVeiculoMultaController', 'md',
                {
                    veiculoMulta: function() {
                        return veiculoMulta;
                    }
                });
            return modalInstance.result;
        };
        
        var modalSalvar = function(veiculoMulta) {
            var modalInstance = ModalService.modalDefault('partials/veiculo/multa/modalEditarVeiculoMulta.html', 'ModalEditarVeiculoMultaController', 'lg',
                {
                    veiculoMulta: function() {
                        return veiculoMulta;
                    }
                });
            return modalInstance.result;
        };
        
        var modalExcluir = function() {
            var modalInstance = ModalService.modalExcluir('Excluir Multa?', 'Deseja realmente excluir esta multa?');
            return modalInstance.result;
        };

        todos();
        init();

    }]);
