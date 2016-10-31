'use strict';

app.controller('VeiculoSinistroController', ['$scope', 'VeiculoSinistroService', 'ModalService', 'ValorService', 'VeiculoHandler', 'VeiculoSinistroHandler',
    function ($scope, VeiculoSinistroService, ModalService, ValorService, VeiculoHandler, VeiculoSinistroHandler) {

        var init = function () {
            $scope.veiculosSinistro = [];
            $scope.veiculosSinistroLista = [];
            initTable();   
            todos();
        };                  

        // ***** TABLE ***** //
        
        var initTable = function() {            
            $scope.colunas = [
                {label: 'Placa', column: 'placa', dataClass:'text-center text-nowrap', filter: {name:'placa', args: null}},
                {label: 'Número BO', column: 'boletimOcorrencia', filter: {name:'number', args: null}},    
                {label: 'Tipo', column: 'tipo'},             
                {label: 'Data', column: 'data', dataClass:'text-center', filter: {name:'date', args: 'dd/MM/yyyy'}},            
                {label: 'Responsável', column: 'responsavel'}
            ]            
            $scope.linha = {
                events: { 
                    edit: function(veiculoSinistro) {
                        $scope.editar(veiculoSinistro);
                    },
                    remove: function(veiculoSinistro) {
                        $scope.excluir(veiculoSinistro);
                    },
                    view: function(veiculoSinistro) {
                        $scope.visualizar(veiculoSinistro);
                    },
                }   
            }          
        };        

        // ***** CONTROLLER ***** //      

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
                sinistro.tipo = sinistro.tipo.descricao;
                sinistro.responsavel = sinistro.responsavel.descricao;
                var obj = _.pick(sinistro, 'idVeiculoSinistro', 'boletimOcorrencia', 'tipo', 'data', 'responsavel');
                return _.extend(obj, _.pick(sinistro.veiculo, 'marca', 'modelo', 'placa'));
            })
        };
    
        var getMsgToClient = function(veiculo) {
            return ValorService.getValueMsgVeiculo(veiculo);        
        };

        // ***** VISUALIZAR ***** //
        
        $scope.visualizar = function(sinistro) {
            VeiculoSinistroService.get(sinistro.idVeiculoSinistro)
                .then(function(result) {
                    visualizar(result);
                })
                .catch(function(e) {
                    modalMessage(e);
                });
        };

        var visualizar = function(sinistro) {
            modalVisualizar(sinistro)
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

        var save = function(sinistro) {
            VeiculoSinistroService.save(sinistro)
                .then(function(data) {  
                    modalMessage("Sinistro do Veículo " + getMsgToClient(data.veiculo) +  " Inserido com sucesso!");
                    todos();
                })
                .catch(function(e) {
                    modalMessage(e);
                });
        };

        // ***** EDITAR ***** //
        
        $scope.editar = function(sinistro) {
            VeiculoSinistroService.get(sinistro.idVeiculoSinistro)
                .then(function(result) {
                     editar(result);
                })
                .catch(function(e) {
                    modalMessage(e.error);
                });
        };

        var editar = function(sinistro) {
            modalSalvar(sinistro)
                .then(function(result) {
                    result = ajustarDados(result);
                    update(result);
                }); 
        };
        
        var update = function(sinistro) {
            VeiculoSinistroService.update(sinistro.idVeiculoSinistro, sinistro)
                .then(function (data) {  
                    modalMessage("Sinistro do Veículo " + getMsgToClient(data.veiculo) + " Alterado com sucesso!");
                    todos();
                })
                .catch(function(e) {
                    modalMessage(e);
                });             
        };

        // ***** EXCLUIR ***** //

        $scope.excluir = function(sinistro) {
            excluir(sinistro);
        }; 
        
        var excluir = function(sinistro) {
            modalExcluir()
                .then(function() {
                    remove(sinistro);
                });
        };
        
        var remove = function(sinistro) {
            VeiculoSinistroService.delete(sinistro.idVeiculoSinistro)
                .then(function(data) { 
                    modalMessage("Sinistro do Veículo " + getMsgToClient(data.veiculo) + " Removido com sucesso!");
                    todos();                        
                })
                .catch(function(e) {
                    modalMessage(e);
                });
        };

        // ***** AJUSTAR ***** //
        
        var ajustarDados = function(data) {      
            var sinistro = VeiculoSinistroHandler.handle(data);
            sinistro.veiculo = VeiculoHandler.handle(data.veiculo);  
            return sinistro;   
        };

        // ***** MODAL ***** //
        
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

        init();

    }]);
