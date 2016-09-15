'use strict';

app.controller('VeiculoSinistroController', ['$scope', '$filter', 'VeiculoSinistroService', 'ModalService', 'ValorService', 'DataTableService',
    function ($scope, $filter, VeiculoSinistroService, ModalService, ValorService, DataTableService) {

        var init = function () {
            $scope.veiculosSinistro = [];
            $scope.veiculosSinistroLista = [];
            $scope.dtOptions = DataTableService.default();
        };             

        var todos = function() {
            VeiculoSinistroService.getAll()
                .then(function (data) {
                    $scope.veiculosSinistro = data;
                    $scope.veiculosSinistroLista = criarVeiculosSinistroLista($scope.veiculosSinistro);
                })
                .catch(function(e) {
                    modalMessage(e);
                });
        };
        
        var criarVeiculosSinistroLista = function(manutencoes) {
            return _.map(manutencoes, function(sinistro) {
                var obj = _.pick(sinistro, 'idVeiculoSinistro', 'boletimOcorrencia', 'tipo', 'data', 'responsavel');
                return _.extend(obj, _.pick(sinistro.veiculo, 'marca', 'modelo', 'placa'));
            })
        }

        $scope.visualizar = function(idVeiculoSinistro) {
            VeiculoSinistroService.get(idVeiculoSinistro)
                .then(function(veiculoSinistro) {
                     modalVisualizar(veiculoSinistro).then(function(result) {
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
                VeiculoSinistroService.save(result)
                    .then(function(data) {  
                        modalMessage("Sinistro do Veículo " + getMsgToClient(data.veiculo) +  " Inserido com sucesso!");
                        todos();
                    })
                    .catch(function(e) {
                        modalMessage(e);
                    });
            });
        };

        $scope.editar = function(idVeiculoSinistro) {
            VeiculoSinistroService.get(idVeiculoSinistro)
                .then(function(veiculoSinistro) {
                     modalSalvar(veiculoSinistro).then(function(result) {
                        result = ajustarDados(result);
                        VeiculoSinistroService.update(idVeiculoSinistro, result)
                            .then(function (data) {  
                                modalMessage("Sinistro do Veículo " + getMsgToClient(data.veiculo) + " Alterado com sucesso!");
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

        $scope.excluir = function(idVeiculoSinistro) {
            modalExcluir().then(function() {
                VeiculoSinistroService.delete(idVeiculoSinistro)
                    .then(function(data) { 
                        modalMessage("Sinistro do Veículo " + getMsgToClient(data.veiculo) + " Removido com sucesso!");
                        todos();                        
                    })
                    .catch(function(e) {
                        modalMessage(e);
                    });
            });
        }; 
        
        var ajustarDados = function(data) {  
            data.tipo = data.tipo.id; 
            data.responsavel = data.responsavel.id; 
            data.veiculo = { 
                idVeiculo: data.veiculo.idVeiculo,
                modelo: data.veiculo.modelo,
                placa: data.veiculo.placa
            };
            return data;
        }
    
        var getMsgToClient = function(veiculo) {
            return ValorService.getValueMsgVeiculo(veiculo);        
        }
        
        var modalMessage = function(message) {
            ModalService.modalMessage(message);
        };
        
        var modalVisualizar = function(veiculoSinistro) {
            var modalInstance = ModalService.modalDefault('partials/veiculo/sinistro/modalVisualizarVeiculoSinistro.html', 'ModalVisualizarVeiculoSinistroController', 'md',
                {
                    veiculoSinistro: function() {
                        return veiculoSinistro;
                    }
                });
            return modalInstance.result;
        };
        
        var modalSalvar = function(veiculoSinistro) {
            var modalInstance = ModalService.modalDefault('partials/veiculo/sinistro/modalEditarVeiculoSinistro.html', 'ModalEditarVeiculoSinistroController', 'lg',
                {
                    veiculoSinistro: function() {
                        return veiculoSinistro;
                    }
                });
            return modalInstance.result;
        };
        
        var modalExcluir = function() {
            var modalInstance = ModalService.modalExcluir('Excluir Sinistro?', 'Deseja realmente excluir esta sinistro?');
            return modalInstance.result;
        };

        todos();
        init();

    }]);
