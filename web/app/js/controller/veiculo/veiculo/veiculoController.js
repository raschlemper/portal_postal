'use strict';

app.controller('VeiculoController', ['$scope', '$q', 'VeiculoService', 'ModalService', 'ValorService', 'VeiculoHandler',
    function ($scope, $q, VeiculoService, ModalService, ValorService, VeiculoHandler) {

        var init = function () {
            $scope.veiculos = [];
            $scope.veiculosLista = [];
            initTable();   
            todos();
        };                  

        // ***** TABLE ***** //
        
        var initTable = function() {            
            $scope.colunas = [
                {label: 'Marca / Modelo', column: 'marcaModelo'},    
                {label: 'Placa', column: 'placa', dataClass:'text-center text-nowrap', filter: {name:'placa', args: null}},
                {label: 'Combustível', column: 'combustivel'},             
                {label: 'Situação', column: 'situacao', dataClass:'text-center'},             
                {label: 'Cadastro', column: 'dataCadastro', dataClass:'text-center', filter: {name:'date', args: 'dd/MM/yyyy'}}
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
            VeiculoService.getAll()
                .then(function (data) {
                    $scope.veiculos = data;
                    $scope.veiculosLista = criarVeiculosLista($scope.veiculos);
                })
                .catch(function(e) {
                    modalMessage(e);
                });
        };
        
        var criarVeiculosLista = function(veiculos) {
            return _.map(veiculos, function(veiculo) {
                veiculo.marcaModelo = veiculo.marca + ' / ' + veiculo.modelo;
                veiculo.combustivel = veiculo.combustivel.descricao;
                veiculo.situacao = veiculo.situacao.descricao;
                return _.pick(veiculo, 'idVeiculo', 'marcaModelo', 'placa', 'combustivel', 'situacao', 'dataCadastro');
            })
        };
    
        var getMsgToClient = function(veiculo) {
            return ValorService.getValueMsgVeiculo(veiculo);              
        };

        // ***** VISUALIZAR ***** //
        
        $scope.visualizar = function(veiculo) {
            VeiculoService.get(veiculo.idVeiculo)
                .then(function(result) {
                    visualizar(result);
                })
                .catch(function(e) {
                    modalMessage(e);
                });
        };

        var visualizar = function(veiculo) {
            modalVisualizar(veiculo)
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

        var save = function(veiculo) {
            VeiculoService.save(veiculo)
                .then(function(data) {  
                    modalMessage("Veículo " + getMsgToClient(data) +  " Inserido com sucesso!");
                    todos();
                })
                .catch(function(e) {
                    modalMessage(e);
                });
        };

        // ***** EDITAR ***** //
        
        $scope.editar = function(veiculo) {
            VeiculoService.get(veiculo.idVeiculo)
                .then(function(result) {
                     editar(result);
                })
                .catch(function(e) {
                    modalMessage(e.error);
                });
        };

        var editar = function(veiculo) {
            modalSalvar(veiculo)
                .then(function(result) {
                    result = ajustarDados(result);
                    update(result);
                }); 
        };
        
        var update = function(veiculo) {
            VeiculoService.update(veiculo.idVeiculo, veiculo)
                .then(function (data) {  
                    modalMessage("Veículo " + getMsgToClient(data) + " Alterado com sucesso!");
                    todos();
                })
                .catch(function(e) {
                    modalMessage(e);
                });             
        };

        // ***** EXCLUIR ***** //

        $scope.excluir = function(veiculo) {
            $q.all([VeiculoService.getCombustivel(veiculo.idVeiculo),
                    VeiculoService.getManutencao(veiculo.idVeiculo),
                    VeiculoService.getMulta(veiculo.idVeiculo),
                    VeiculoService.getSeguro(veiculo.idVeiculo),
                    VeiculoService.getSinistro(veiculo.idVeiculo)])
                .then(function(values) {  
                    var podeExcluir = true;
                    angular.forEach(values, function(value, key) {
                        if(value.length > 0) podeExcluir = false;
                    });
                    return podeExcluir;
                }).then(function(podeExcluir) {
                    if(!podeExcluir) {
                        modalMessage("Este veículo não pode ser excluído!");
                        return;
                    }
                    excluir(veiculo);
                });
        }; 
        
        var excluir = function(veiculo) {
            modalExcluir()
                .then(function() {
                    remove(veiculo);
                });
        };
        
        var remove = function(veiculo) {
            VeiculoService.delete(veiculo.idVeiculo)
                .then(function(data) { 
                    modalMessage("Veículo " + getMsgToClient(data) + " Removido com sucesso!");
                    todos();                        
                })
                .catch(function(e) {
                    modalMessage(e);
                });
        };

        // ***** AJUSTAR ***** //        
        
        var ajustarDados = function(data) {       
            var veiculo = VeiculoHandler.handle(data); 
            return veiculo;
        };

        // ***** MODAL ***** //
        
        var modalMessage = function(message) {
            ModalService.modalMessage(message);
        };
        
        var modalVisualizar = function(veiculo) {
            var modalInstance = ModalService.modalDefault('partials/veiculo/veiculo/modalVisualizarVeiculo.html', 'ModalVisualizarVeiculoController', 'md',
                {
                    veiculo: function() {
                        return veiculo;
                    }
                });
            return modalInstance.result;
        };
        
        var modalSalvar = function(veiculo) {
            var modalInstance = ModalService.modalDefault('partials/veiculo/veiculo/modalEditarVeiculo.html', 'ModalEditarVeiculoController', 'lg',
                {
                    veiculo: function() {
                        return veiculo;
                    }
                });
            return modalInstance.result;
        };
        
        var modalExcluir = function() {
            var modalInstance = ModalService.modalExcluir('Excluir Veículo?', 'Deseja realmente excluir este veículo?');
            return modalInstance.result;
        };

        init();

    }]);
