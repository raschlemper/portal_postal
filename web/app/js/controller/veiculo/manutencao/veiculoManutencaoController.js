'use strict';

app.controller('VeiculoManutencaoController', ['$scope', 'VeiculoManutencaoService', 'ValorService', 'ModalService', 'VeiculoHandler', 'VeiculoManutencaoHandler', 
    function ($scope, VeiculoManutencaoService, ValorService, ModalService, VeiculoHandler, VeiculoManutencaoHandler) {

        var init = function () {
            $scope.veiculosManutencao = [];
            $scope.veiculosManutencaoLista = [];
            initTable();   
            todos();
        };             

        // ***** TABLE ***** //
        
        var initTable = function() {            
            $scope.colunas = [
                {label: 'Placa', column: 'placa', dataClass:'text-center text-nowrap', filter: {name:'placa', args: null}},
                {label: 'Tipo', column: 'tipo'},             
                {label: 'Km', column: 'quilometragem', filter: {name:'number', args: null}},            
                {label: 'Valor', column: 'valor', dataClass:'text-right', filter: {name:'currency', args: ''}},  
                {label: 'Data Agendamento', column: 'dataAgendamento', dataClass:'text-center', filter: {name: 'date', args: 'dd/MM/yyyy'}},
                {label: 'Data Manutenção', column: 'dataManutencao', dataClass:'text-center', filter: {name: 'date', args: 'dd/MM/yyyy'}}
            ]            
            $scope.linha = {
                events: { 
                    edit: function(veiculoManutencao) {
                        $scope.editar(veiculoManutencao);
                    },
                    remove: function(veiculoManutencao) {
                        $scope.excluir(veiculoManutencao);
                    },
                    view: function(veiculoManutencao) {
                        $scope.visualizar(veiculoManutencao);
                    },
                }   
            }          
        };        

        // ***** CONTROLLER ***** //

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
                manutencao.tipo = manutencao.tipo.descrica;
                var obj = _.pick(manutencao, 'idVeiculoManutencao', 'tipo', 'quilometragem', 'valor', 'dataManutencao', 'dataAgendamento');
                return _.extend(obj, _.pick(manutencao.veiculo, 'marca', 'modelo', 'placa'));
            })
        };
    
        var getMsgToClient = function(veiculo) {
            return ValorService.getValueMsgVeiculo(veiculo);     
        };

        // ***** VISUALIZAR ***** //

        $scope.visualizar = function(manutencao) {
            VeiculoManutencaoService.get(manutencao.idVeiculoManutencao)
                .then(function(result) {
                    visualizar(result);
                })
                .catch(function(e) {
                    modalMessage(e);
                });
        };

        var visualizar = function(manutencao) {
            modalVisualizar(manutencao)
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

        var save = function(manutencao) {
            VeiculoManutencaoService.save(manutencao)
                .then(function(data) {  
                    modalMessage("Manutenção do Veículo " + getMsgToClient(data.veiculo) +  " Inserido com sucesso!");
                    todos();
                })
                .catch(function(e) {
                    modalMessage(e);
                });
        };

        // ***** EDITAR ***** //

        $scope.editar = function(manutencao) {
            VeiculoManutencaoService.get(manutencao.idVeiculoManutencao)
                .then(function(result) {
                     editar(result);
                })
                .catch(function(e) {
                    modalMessage(e.error);
                });
        };

        var editar = function(manutencao) {
            modalSalvar(manutencao)
                .then(function(result) {
                    result = ajustarDados(result);
                    update(result);
                }); 
        };
        
        var update = function(manutencao) {
            VeiculoManutencaoService.update(manutencao.idVeiculoManutencao, manutencao)
                .then(function (data) {  
                    modalMessage("Manutenção do Veículo " + getMsgToClient(data.veiculo) + " Alterado com sucesso!");
                    todos();
                })
                .catch(function(e) {
                    modalMessage(e);
                });
        };

        // ***** EXCLUIR ***** //

        $scope.excluir = function(manutencao) {
            excluir(manutencao);
        }; 
        
        var excluir = function(manutencao) {
            modalExcluir()
                .then(function() {
                    remove(manutencao);
                });
        };
        
        var remove = function(manutencao) {
            VeiculoManutencaoService.delete(manutencao.idVeiculoManutencao)
                .then(function(data) { 
                    modalMessage("Manutenção do Veículo " + getMsgToClient(data.veiculo) + " Removido com sucesso!");
                    todos();                        
                })
                .catch(function(e) {
                    modalMessage(e);
                });
        };

        // ***** AJUSTAR ***** //
        
        var ajustarDados = function(data) {       
            var manutencao = VeiculoManutencaoHandler.handle(data);
            manutencao.veiculo = VeiculoHandler.handle(data.veiculo);  
            return manutencao;
        };

        // ***** MODAL ***** //
        
        var modalMessage = function(message) {
            ModalService.modalMessage(message);
        };
        
        var modalVisualizar = function(veiculoManutencao) {
            var modalInstance = ModalService.modalDefault('partials/veiculo/manutencao/modalVisualizarVeiculoManutencao.html', 'ModalVisualizarVeiculoManutencaoController', 'md',
                {
                    veiculoManutencao: function() {
                        return veiculoManutencao;
                    }
                });
            return modalInstance.result;
        };
        
        var modalSalvar = function(veiculoManutencao) {
            var modalInstance = ModalService.modalDefault('partials/veiculo/manutencao/modalEditarVeiculoManutencao.html', 'ModalEditarVeiculoManutencaoController', 'lg',
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
        
        init();

    }]);
