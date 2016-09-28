'use strict';

app.controller('VeiculoSeguroController', ['$scope', '$filter', 'VeiculoSeguroService', 'ModalService', 'ValorService', 'VeiculoHandler', 'VeiculoSeguroHandler',
    function ($scope, $filter, VeiculoSeguroService, ModalService, ValorService, VeiculoHandler, VeiculoSeguroHandler) {

        var init = function () {
            $scope.veiculosSeguro = [];
            $scope.veiculosSeguroLista = [];
            initTable();   
            todos();
        };              

        // ***** TABLE ***** //
        
        var initTable = function() {            
            $scope.colunas = [
                {label: 'Placa', column: 'placa', dataClass:'text-center text-nowrap', filter: {name:'placa', args: null}},
                {label: 'Apólice', column: 'numeroApolice', filter: {name:'number', args: null}},             
                {label: 'Corretora', column: 'corretora'},            
                {label: 'Data Vigência', dataClass:'text-center', column: 'dataVigencia'}
            ]            
            $scope.linha = {
                events: { 
                    edit: function(veiculoSeguro) {
                        $scope.editar(veiculoSeguro);
                    },
                    remove: function(veiculoSeguro) {
                        $scope.excluir(veiculoSeguro);
                    },
                    view: function(veiculoSeguro) {
                        $scope.visualizar(veiculoSeguro);
                    },
                }   
            }          
        };        

        // ***** CONTROLLER ***** //         

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
                seguro.dataVigencia = $filter('date')(seguro.dataInicioVigencia, 'dd/MM/yyyy') + ' - ' + $filter('date')(seguro.dataFimVigencia, 'dd/MM/yyyy'); 
                var obj = _.pick(seguro, 'idVeiculoSeguro', 'numeroApolice', 'corretora', 'dataVigencia');
                return _.extend(obj, _.pick(seguro.veiculo, 'marca', 'modelo', 'placa'));
            })
        };
    
        var getMsgToClient = function(veiculo) {
            return ValorService.getValueMsgVeiculo(veiculo);      
        };

        // ***** VISUALIZAR ***** //
        
        $scope.visualizar = function(seguro) {
            VeiculoSeguroService.get(seguro.idVeiculoSeguro)
                .then(function(result) {
                    visualizar(result);
                })
                .catch(function(e) {
                    modalMessage(e);
                });
        };

        var visualizar = function(seguro) {
            modalVisualizar(seguro)
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

        var save = function(seguro) {
            VeiculoSeguroService.save(seguro)
                .then(function(data) {  
                    modalMessage("Seguro do Veículo " + getMsgToClient(data.veiculo) +  " Inserido com sucesso!");
                    todos();
                })
                .catch(function(e) {
                    modalMessage(e);
                });
        };

        // ***** EDITAR ***** //
        
        $scope.editar = function(seguro) {
            VeiculoSeguroService.get(seguro.idVeiculoSeguro)
                .then(function(result) {
                     editar(result);
                })
                .catch(function(e) {
                    modalMessage(e.error);
                });
        };

        var editar = function(seguro) {
            modalSalvar(seguro)
                .then(function(result) {
                    result = ajustarDados(result);
                    update(result);
                }); 
        };
        
        var update = function(seguro) {
            VeiculoSeguroService.update(seguro.idVeiculoSeguro, seguro)
                .then(function (data) {  
                    modalMessage("Seguro do Veículo " + getMsgToClient(data.veiculo) + " Alterado com sucesso!");
                    todos();
                })
                .catch(function(e) {
                    modalMessage(e);
                });             
        };

        // ***** EXCLUIR ***** //

        $scope.excluir = function(seguro) {
            excluir(seguro);
        }; 
        
        var excluir = function(seguro) {
            modalExcluir()
                .then(function() {
                    remove(seguro);
                });
        };
        
        var remove = function(seguro) {
            VeiculoSeguroService.delete(seguro.idVeiculoSeguro)
                .then(function(data) { 
                    modalMessage("Seguro do Veículo " + getMsgToClient(data.veiculo) + " Removido com sucesso!");
                    todos();                        
                })
                .catch(function(e) {
                    modalMessage(e);
                });
        };

        // ***** AJUSTAR ***** //
        
        var ajustarDados = function(data) {     
            var seguro = VeiculoSeguroHandler.handle(data);
            seguro.veiculo = VeiculoHandler.handle(data.veiculo);  
            return seguro;            
        };

        // ***** MODAL ***** //
        
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
        
        init();

    }]);
