'use strict';

app.controller('VeiculoManutencaoController', ['$scope', '$filter', 'VeiculoManutencaoService', 'ModalService', 'DataTableService',
    function ($scope, $filter, VeiculoManutencaoService, ModalService, DataTableService) {

        var init = function () {
            $scope.veiculosManutencao = [];
            $scope.veiculosManutencaoLista = [];
            $scope.dtOptions = DataTableService.default();
        };             

        var todos = function() {
            VeiculoManutencaoService.getAll()
                .then(function (data) {
                    $scope.veiculosManutencao = data;
                    $scope.veiculosManutencaoLista = criarVeiculosManutencaoLista($scope.veiculosManutencao);
                })
                .catch(function(e) {
                    modalMessage(e);
                });
        };
        
        var criarVeiculosManutencaoLista = function(manutencoes) {
            return _.map(manutencoes, function(manutencao) {
                var obj = _.pick(manutencao, 'idVeiculoManutencao', 'tipo', 'quilometragem', 'valor', 'dataManutencao', 'dataAgendamento');
                return _.extend(obj, _.pick(manutencao.veiculo, 'marca', 'modelo', 'placa'));
            })
        }

        $scope.visualizar = function(idVeiculoManutencao) {
            VeiculoManutencaoService.get(idVeiculoManutencao)
                .then(function(veiculoManutencao) {
                     modalVisualizar(veiculoManutencao).then(function(result) {
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
                VeiculoManutencaoService.save(result)
                    .then(function(data) {  
                        modalMessage("Manutenção do Veículo " + getMsgToClient(data.veiculo) +  " Inserido com sucesso!");
                        todos();
                    })
                    .catch(function(e) {
                        modalMessage(e);
                    });
            });
        };

        $scope.editar = function(idVeiculoManutencao) {
            VeiculoManutencaoService.get(idVeiculoManutencao)
                .then(function(veiculoManutencao) {
                     modalSalvar(veiculoManutencao).then(function(result) {
                        result = ajustarDados(result);
                        VeiculoManutencaoService.update(idVeiculoManutencao, result)
                            .then(function (data) {  
                                modalMessage("Manutenção do Veículo " + getMsgToClient(data.veiculo) + " Alterado com sucesso!");
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

        $scope.excluir = function(idVeiculoManutencao) {
            modalExcluir().then(function() {
                VeiculoManutencaoService.delete(idVeiculoManutencao)
                    .then(function(data) { 
                        modalMessage("Manutenção do Veículo " + getMsgToClient(data.veiculo) + " Removido com sucesso!");
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
        
        var modalVisualizar = function(veiculoManutencao) {
            var modalInstance = ModalService.modalDefault('partials/veiculo/modalVisualizarVeiculoManutencao.html', 'ModalVisualizarVeiculoManutencaoController', 'md',
                {
                    veiculoManutencao: function() {
                        return veiculoManutencao;
                    }
                });
            return modalInstance.result;
        };
        
        var modalSalvar = function(veiculoManutencao) {
            var modalInstance = ModalService.modalDefault('partials/veiculo/modalEditarVeiculoManutencao.html', 'ModalEditarVeiculoManutencaoController', 'lg',
                {
                    veiculoManutencao: function() {
                        return veiculoManutencao;
                    }
                });
            return modalInstance.result;
        };
        
        var modalExcluir = function() {
            var modalInstance = ModalService.modalExcluir('Excluir Manutenção?', 'Deseja realmente excluir esta manutencao?');
            return modalInstance.result;
        };

        todos();
        init();

    }]);
