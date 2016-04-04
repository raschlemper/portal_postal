'use strict';

app.controller('CarteiraCobrancaController', ['$scope', '$filter', 'CarteiraCobrancaService', 'ModalService',
    function ($scope, $filter, CarteiraCobrancaService, ModalService) {

        var init = function () {
            $scope.carteiraCobrancas = [];
            $scope.carteiraCobrancasLista = [];
            initTable();      
        };  
        
        var initTable = function() {            
            $scope.colunas = [
                {label: 'Nome', column: 'nome'},
                {label: 'Beneficiário', column: 'beneficiarioNome'},                         
                {label: 'Código', column: 'codigoBeneficiario'},                         
                {label: 'Convênio', column: 'codigoConvenio'},                         
                {label: 'Carteira', column: 'codigoCarteira'}
            ]            
            $scope.events = { 
                edit: function(carteiraCobranca) {
                    $scope.editar(carteiraCobranca.idCarteiraCobranca);
                },
                remove: function(carteiraCobranca) {
                    $scope.excluir(carteiraCobranca.idCarteiraCobranca);
                },
                view: function(carteiraCobranca) {
                    $scope.visualizar(carteiraCobranca.idCarteiraCobranca);
                }
            }             
        }

        var todos = function() {
            CarteiraCobrancaService.getAll()
                .then(function (data) {
                    $scope.carteiraCobrancas = data;
                    $scope.carteiraCobrancasLista = criarCarteiraCobrancasLista($scope.carteiraCobrancas);
                })
                .catch(function(e) {
                    modalMessage(e);
                });
        };
        
        var criarCarteiraCobrancasLista = function(carteiraCobrancas) {
            return _.map(carteiraCobrancas, function(carteiraCobranca) {
                carteiraCobranca.codigoBeneficiario = $filter('number')(carteiraCobranca.codigoBeneficiario) + '-' + carteiraCobranca.codigoBeneficiarioDv;
                return _.pick(carteiraCobranca, 'idCarteiraCobranca', 'nome', 'beneficiarioNome', 'codigoBeneficiario', 'codigoConvenio', 'codigoCarteira');
            })
        };

        $scope.visualizar = function(idCarteiraCobranca) {
            CarteiraCobrancaService.get(idCarteiraCobranca)
                .then(function(carteiraCobranca) {
                     modalVisualizar(carteiraCobranca).then(function(result) {
                         $scope.editar(result);
                     })          
                })
                .catch(function(e) {
                    modalMessage(e);
                });
        };

        $scope.salvar = function() {
            modalSalvar().then(function(result) {
                CarteiraCobrancaService.save(result)
                    .then(function(data) {  
                        modalMessage("Carteira de Cobrança " + data.nome +  " Inserida com sucesso!");
                        todos();
                    })
                    .catch(function(e) {
                        modalMessage(e);
                    });
            });
        };

        $scope.editar = function(idCarteiraCobranca) {
            CarteiraCobrancaService.get(idCarteiraCobranca)
                .then(function(carteiraCobranca) {
                     modalSalvar(carteiraCobranca).then(function(result) {
                        CarteiraCobrancaService.update(idCarteiraCobranca, result)
                            .then(function (data) {  
                                modalMessage("Carteira de Cobrança " + data.nome + " Alterada com sucesso!");
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

        $scope.excluir = function(idCarteiraCobranca) {
            modalExcluir().then(function() {
                CarteiraCobrancaService.delete(idCarteiraCobranca)
                    .then(function(data) { 
                        modalMessage("Carteira de Cobrança " + data.nome + " Removida com sucesso!");
                        todos();                        
                    })
                    .catch(function(e) {
                        modalMessage(e);
                    });
            });
        }; 
        
        var modalMessage = function(message) {
            ModalService.modalMessage(message);
        };
        
        var modalVisualizar = function(carteiraCobranca) {
            var modalInstance = ModalService.modalDefault('partials/financeiro/carteiraCobranca/modalVisualizarCarteiraCobranca.html', 'ModalVisualizarCarteiraCobrancaController', 'md',
                {
                    carteiraCobranca: function() {
                        return carteiraCobranca;
                    }
                });
            return modalInstance.result;
        };
        
        var modalSalvar = function(carteiraCobranca) {
            var modalInstance = ModalService.modalDefault('partials/financeiro/carteiraCobranca/modalEditarCarteiraCobranca.html', 'ModalEditarCarteiraCobrancaController', 'lg',
                {
                    carteiraCobranca: function() {
                        return carteiraCobranca;
                    }
                });
            return modalInstance.result;
        };
        
        var modalExcluir = function() {
            var modalInstance = ModalService.modalExcluir('Excluir Carteira de Cobrança?', 'Deseja realmente excluir esta Carteira de Cobrança?');
            return modalInstance.result;
        };

        todos();
        init();

    }]);
