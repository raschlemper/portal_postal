'use strict';

app.controller('VeiculoCombustivelController', ['$scope', '$filter', 'VeiculoCombustivelService', 'ModalService', 'DataTableService',  'LISTAS',
    function ($scope, $filter, VeiculoCombustivelService, ModalService, DataTableService, LISTAS) {

        var init = function () {
            $scope.veiculosCombustivel = [];
            $scope.veiculosCombustivelLista = [];
            $scope.situacoes = LISTAS.situacao;
            $scope.dtOptions = DataTableService.default();
        };             

        var todos = function() {
            VeiculoService.getAll()
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
                return _.pick(combustivel, 'idVeiculoCombustivel', 'marca', 'modelo', 'placa', 'combustivel', 'situacao', 'dataCadastro');
            })
        }

        $scope.salvar = function() {
            modalSalvar().then(function(result) {
                result = ajustarDados(result);
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
                     modalVisualizar(veiculo).then(function(result) {
                         $scope.editar(result);
                     })          
                })
                .catch(function(e) {
                    modalMessage(e);
                });
        };

        $scope.editar = function(idVeiculo) {
            VeiculoService.get(idVeiculo)
                .then(function(veiculo) {
                     modalSalvar(veiculo).then(function(result) {
                        result = ajustarDados(result);
                        VeiculoService.update(idVeiculo, result)
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
        
        var ajustarDados = function(data) {            
            data.tipo = data.tipo.id;
            data.idMarca = data.marca.id;
            data.marca = data.marca.name;
            data.idModelo = data.modelo.id;
            data.modelo = data.modelo.name;
            data.idVersao = data.versao.id;
            data.versao = data.versao.name;
            data.combustivel = data.combustivel.id;
            data.status = data.status.id;
            data.situacao = data.situacao.id;
            return data;
        }
    
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
