'use strict';

app.controller('VeiculoSeguroController', ['$scope', '$filter', 'VeiculoSeguroService', 'ModalService', 'DataTableService',
    function ($scope, $filter, VeiculoSeguroService, ModalService, DataTableService) {

        var init = function () {
            $scope.veiculosSeguro = [];
            $scope.veiculosSeguroLista = [];
            $scope.dtOptions = DataTableService.default();
        };             

        var todos = function() {
            VeiculoSeguroService.getAll()
                .then(function (data) {
                    $scope.veiculosSeguro = data;
                    $scope.veiculosSeguroLista = criarVeiculosSeguroLista($scope.veiculosSeguro);
                })
                .catch(function(e) {
                    modalMessage(e);
                });
        };
        
        var criarVeiculosSeguroLista = function(manutencoes) {
            return _.map(manutencoes, function(seguro) {
                var obj = _.pick(seguro, 'idVeiculoSeguro', 'numeroApolice', 'corretora', 'dataInicioVigencia', 'dataFimVigencia');
                return _.extend(obj, _.pick(seguro.veiculo, 'marca', 'modelo', 'placa'));
            })
        }

        $scope.visualizar = function(idVeiculoSeguro) {
            VeiculoSeguroService.get(idVeiculoSeguro)
                .then(function(veiculoSeguro) {
                     modalVisualizar(veiculoSeguro).then(function(result) {
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
                VeiculoSeguroService.save(result)
                    .then(function(data) {  
                        modalMessage("Seguro do Veículo " + getMsgToClient(data.veiculo) +  " Inserido com sucesso!");
                        todos();
                    })
                    .catch(function(e) {
                        modalMessage(e);
                    });
            });
        };

        $scope.editar = function(idVeiculoSeguro) {
            VeiculoSeguroService.get(idVeiculoSeguro)
                .then(function(veiculoSeguro) {
                     modalSalvar(veiculoSeguro).then(function(result) {
                        result = ajustarDados(result);
                        VeiculoSeguroService.update(idVeiculoSeguro, result)
                            .then(function (data) {  
                                modalMessage("Seguro do Veículo " + getMsgToClient(data.veiculo) + " Alterado com sucesso!");
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

        $scope.excluir = function(idVeiculoSeguro) {
            modalExcluir().then(function() {
                VeiculoSeguroService.delete(idVeiculoSeguro)
                    .then(function(data) { 
                        modalMessage("Seguro do Veículo " + getMsgToClient(data.veiculo) + " Removido com sucesso!");
                        todos();                        
                    })
                    .catch(function(e) {
                        modalMessage(e);
                    });
            });
        }; 
        
        var ajustarDados = function(data) {         
            data.indenizacao = data.indenizacao.id; 
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
        
        var modalVisualizar = function(veiculoSeguro) {
            var modalInstance = ModalService.modalDefault('partials/veiculo/seguro/modalVisualizarVeiculoSeguro.html', 'ModalVisualizarVeiculoSeguroController', 'md',
                {
                    veiculoSeguro: function() {
                        return veiculoSeguro;
                    }
                });
            return modalInstance.result;
        };
        
        var modalSalvar = function(veiculoSeguro) {
            var modalInstance = ModalService.modalDefault('partials/veiculo/seguro/modalEditarVeiculoSeguro.html', 'ModalEditarVeiculoSeguroController', 'lg',
                {
                    veiculoSeguro: function() {
                        return veiculoSeguro;
                    }
                });
            return modalInstance.result;
        };
        
        var modalExcluir = function() {
            var modalInstance = ModalService.modalExcluir('Excluir Seguro?', 'Deseja realmente excluir este seguro?');
            return modalInstance.result;
        };

        todos();
        init();

    }]);
