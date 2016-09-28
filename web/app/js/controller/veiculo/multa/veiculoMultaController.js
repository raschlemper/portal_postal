'use strict';

app.controller('VeiculoMultaController', ['$scope', 'VeiculoMultaService', 'ModalService', 'ValorService', 'VeiculoHandler', 'VeiculoMultaHandler',
    function ($scope, VeiculoMultaService, ModalService, ValorService, VeiculoHandler, VeiculoMultaHandler) {

        var init = function () {
            $scope.veiculosMulta = [];
            $scope.veiculosMultaLista = [];
            initTable();   
            todos();
        };    

        // ***** TABLE ***** //
        
        var initTable = function() {            
            $scope.colunas = [
                {label: 'Placa', column: 'placa', dataClass:'text-center text-nowrap', filter: {name:'placa', args: null}},         
                {label: 'Número', column: 'numero', filter: {name:'number', args: null}},       
                {label: 'Condutor', column: 'condutor'},  
                {label: 'Data', column: 'data', dataClass:'text-center', filter: {name: 'date', args: 'dd/MM/yyyy'}},            
                {label: 'Valor', column: 'valor', dataClass:'text-right', filter: {name:'currency', args: ''}}
            ]            
            $scope.linha = {
                events: { 
                    edit: function(veiculoMulta) {
                        $scope.editar(veiculoMulta);
                    },
                    remove: function(veiculoMulta) {
                        $scope.excluir(veiculoMulta);
                    },
                    view: function(veiculoMulta) {
                        $scope.visualizar(veiculoMulta);
                    },
                }   
            }          
        };        

        // ***** CONTROLLER ***** //                    

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
        
        var criarVeiculosMultaLista = function(multas) {
            return _.map(multas, function(multa) {
                var obj = _.pick(multa, 'idVeiculoMulta', 'numero', 'condutor', 'data', 'valor');
                return _.extend(obj, _.pick(multa.veiculo, 'marca', 'modelo', 'placa'));
            })
        };
    
        var getMsgToClient = function(veiculo) {
            return ValorService.getValueMsgVeiculo(veiculo);      
        };

        // ***** VISUALIZAR ***** //
        
        $scope.visualizar = function(multa) {
            VeiculoMultaService.get(multa.idVeiculoMulta)
                .then(function(result) {
                    visualizar(result);
                })
                .catch(function(e) {
                    modalMessage(e);
                });
        };

        var visualizar = function(multa) {
            modalVisualizar(multa)
                .then(function(result) {
                    $scope.editar(result);
                });
        };

        // ***** SALVAR ***** //        

        $scope.salvar = function() {
            salvar();
        };

        var salvar = function() {
            modalSalvar()
                .then(function(result) {
                    result = ajustarDados(result);
                    save(result);
                });
        };

        var save = function(multa) {
            VeiculoMultaService.save(multa)
                .then(function(data) {  
                    modalMessage("Multa do Veículo " + getMsgToClient(data.veiculo) +  " Inserido com sucesso!");
                    todos();
                })
                .catch(function(e) {
                    modalMessage(e);
                });
        };

        // ***** EDITAR ***** //

        $scope.editar = function(multa) {
            VeiculoMultaService.get(multa.idVeiculoMulta)
                .then(function(result) {
                     editar(result);
                })
                .catch(function(e) {
                    modalMessage(e.error);
                });
        };

        var editar = function(multa) {
            modalSalvar(multa)
                .then(function(result) {
                    result = ajustarDados(result);
                    update(result);
                }); 
        };
        
        var update = function(multa) {
            VeiculoMultaService.update(multa.idVeiculoMulta, multa)
                .then(function (data) {  
                    modalMessage("Multa do Veículo " + getMsgToClient(data.veiculo) + " Alterado com sucesso!");
                    todos();
                })
                .catch(function(e) {
                    modalMessage(e);
                });             
        };

        // ***** EXCLUIR ***** //

        $scope.excluir = function(multa) {
            excluir(multa);
        }; 
        
        var excluir = function(multa) {
            modalExcluir()
                .then(function() {
                    remove(multa);
                });
        };
        
        var remove = function(multa) {
            VeiculoMultaService.delete(multa.idVeiculoMulta)
                .then(function(data) { 
                    modalMessage("Multa do Veículo " + getMsgToClient(data.veiculo) + " Removido com sucesso!");
                    todos();                        
                })
                .catch(function(e) {
                    modalMessage(e);
                });
        };

        // ***** AJUSTAR ***** //
                        
        var ajustarDados = function(data) {  
            var multa = VeiculoMultaHandler.handle(data);
            multa.veiculo = VeiculoHandler.handle(data.veiculo);  
            return multa;
        };

        // ***** MODAL ***** //
        
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
        
        init();

    }]);
