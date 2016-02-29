'use strict';


app.controller('VeiculoController', ['$scope', 'VeiculoService', 'ModalService', 'DataTableService',  'LISTAS',
    function ($scope, VeiculoService, ModalService, DataTableService, LISTAS) {

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
                    modalMessage(e.error);
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
                        modalMessage(e.error);
                    });
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
                                modalMessage(e.error);
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
                        modalMessage(e.error);
                    });
            });
        }; 
    
        var getMsgToClient = function(veiculo) {
            return veiculo.modelo + " (" + veiculo.placa + ")";        
        }
        
        var modalMessage = function(message) {
            ModalService.modalMessage(message);
        };
        
        var modalSalvar = function(veiculo) {
            var modalInstance = ModalService.modalDefault('partials/veiculo/modalVeiculo.html', 'ModalVeiculoController', veiculo);
            return modalInstance.result;
        };
        
        var modalExcluir = function() {
            var modalInstance = ModalService.modalExcluir('Excluir Ve\u00EDculo?', 'Deseja realmente excluir este ve\u00EDculo?');
            return modalInstance.result;
        };

        todos();
        init();

    }]);
