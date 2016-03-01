'use strict';

app.controller('VeiculoController', ['$scope', '$filter', 'VeiculoService', 'ModalService', 'DataTableService',  'LISTAS',
    function ($scope, $filter, VeiculoService, ModalService, DataTableService, LISTAS) {

        var init = function () {
            $scope.veiculos = [];
            $scope.situacoes = LISTAS.situacao;
            $scope.dtOptions = DataTableService.default();
        };             

        var todos = function() {
            VeiculoService.getAll()
                .then(function (data) {
                    $scope.veiculos = data;
                })
                .catch(function(e) {
                    modalMessage(e);
                });
        };

        $scope.salvar = function() {
            modalSalvar().then(function(result) {
                result.tipo = result.tipo.key;
                result.marca = result.marca.id;
                result.modelo = result.modelo.id;
                result.combustivel = result.combustivel.key;
                result.status = result.status.key;
                result.situacao = result.situacao.key;
                result.versao = result.versao.id;
                VeiculoService.save(result)
                    .then(function(data) {  
                        modalMessage("Veículo Inserido " + getMsgToClient(data) +  " com sucesso!");
                        todos();
                    })
                    .catch(function(e) {
                        modalMessage(e);
                    });
            });
        };

        $scope.visualizar = function(idVeiculo) {
            VeiculoService.get(idVeiculo)
                .then(function(veiculo) {
                     modalVisualizar(veiculo);                
                })
                .catch(function(e) {
                    modalMessage(e);
                });
        };

        $scope.editar = function(idVeiculo) {
            VeiculoService.get(idVeiculo)
                .then(function(veiculo) {
                     modalSalvar(veiculo).then(function(result) {
                        VeiculoService.save(result)
                            .then(function (data) {  
                                modalMessage("Veículo " + getMsgToClient(data) + " Alterado com sucesso!");
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
                VeiculoService.delete(idVeiculo)
                    .then(function(data) { 
                        modalMessage("Veículo " + getMsgToClient(data) + " Removido com sucesso!");
                        todos();                        
                    })
                    .catch(function(e) {
                        modalMessage(e);
                    });
            });
        }; 
    
        var getMsgToClient = function(veiculo) {
            return veiculo.modelo + " (" + $filter('Placa')(veiculo.placa) + ")";        
        }
        
        var modalMessage = function(message) {
            ModalService.modalMessage(message);
        };
        
        var modalSalvar = function(veiculo) {
            var modalInstance = ModalService.modalDefault('partials/veiculo/modalEditarVeiculo.html', 'ModalEditarVeiculoController', 'lg',
                {
                    veiculo: function() {
                        return veiculo;
                    }
                });
            return modalInstance.result;
        };
        
        var modalVisualizar = function(veiculo) {
            var modalInstance = ModalService.modalDefault('partials/veiculo/modalVisualizarVeiculo.html', 'ModalVisualizarVeiculoController', 'md',
                {
                    veiculo: function() {
                        return veiculo;
                    }
                });
            return modalInstance.result;
        };
        
        var modalExcluir = function() {
            var modalInstance = ModalService.modalExcluir('Excluir Ve\u00EDculo?', 'Deseja realmente excluir este ve\u00EDculo?');
            return modalInstance.result;
        };

        todos();
        init();

    }]);
