'use strict';

app.controller('CarteiraCobrancaController', 
    ['$scope', '$filter', 'CarteiraCobrancaService', 'ModalService', 'MESSAGES',
    function ($scope, $filter, CarteiraCobrancaService, ModalService, MESSAGES) {

        var init = function () {
            $scope.carteiraCobrancas = [];
            $scope.carteiraCobrancasLista = [];
            initTable();      
            todos();
        };  

        // ***** TABLE ***** //
        
        var initTable = function() {            
            $scope.colunas = [
                {label: 'Nome', column: 'nome'},
                {label: 'Beneficiário', column: 'beneficiarioNome'},                         
                {label: 'Código', column: 'codigoBeneficiario'},                         
                {label: 'Convênio', column: 'codigoConvenio'},                         
                {label: 'Carteira', column: 'codigoCarteira'}
            ]            
            $scope.linha = {
                events: { 
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
        };

        // ***** CONTROLLER ***** //

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

        // ***** VISUALIZAR ***** //

        $scope.visualizar = function(idCarteiraCobranca) {
            CarteiraCobrancaService.get(idCarteiraCobranca)
                .then(function(carteiraCobranca) {
                     visualizar(carteiraCobranca);   
                })
                .catch(function(e) {
                    modalMessage(e);
                });
        };

        var visualizar = function(carteiraCobranca) {
            modalVisualizar(carteiraCobranca).then(function(result) {
                $scope.editar(result);
            })          
        };

        // ***** SALVAR ***** //

        $scope.salvar = function() {
            salvar();
        };

        var salvar = function() {
            modalSalvar()
                .then(function(result) {
                    save(result);
                });
        };

        var save = function(carteiraCobranca) {
            CarteiraCobrancaService.save(carteiraCobranca)
                .then(function(data) {  
                    modalMessage("Carteira de Cobrança " + data.nome +  " Inserida com sucesso!");
                    todos();
                })
                .catch(function(e) {
                    modalMessage(e);
                });
        };

        // ***** EDITAR ***** //

        $scope.editar = function(idCarteiraCobranca) {
            CarteiraCobrancaService.get(idCarteiraCobranca)
                .then(function(carteiraCobranca) {
                     editar(carteiraCobranca);
                })
                .catch(function(e) {
                    modalMessage(e.error);
                });           
        };

        var editar = function(carteiraCobranca) {
            modalSalvar(carteiraCobranca)
                .then(function(result) {
                    update(result);
                });
        };

        var update = function(carteiraCobranca) {
            CarteiraCobrancaService.update(carteiraCobranca.idCarteiraCobranca, carteiraCobranca)
                .then(function (data) {  
                    modalMessage("Carteira de Cobrança " + data.nome + " Alterada com sucesso!");
                    todos();
                })
                .catch(function(e) {
                    modalMessage(e);
                });
        };

        // ***** EXCLUIR ***** //

        $scope.excluir = function(idCarteiraCobranca) {
            CarteiraCobrancaService.getConta(idCarteiraCobranca)
                .then(function(carteiraCobranca) {   
                    if(carteiraCobranca.contas.length) {
                        modalMessage("Esta carteira de cobrança não pode ser excluída! <br/> Existem Contas vinculadas a esta carteira de cobrança.");
                    } else {
                        excluir(idCarteiraCobranca);
                    }
                })
                .catch(function(e) {
                    modalMessage(e);
                });
        }; 
        
        var excluir = function(idCarteiraCobranca) {
            modalExcluir()
                .then(function() {
                    remove(idCarteiraCobranca);
                });
        };
        
        var remove = function(idCarteiraCobranca) {
            CarteiraCobrancaService.delete(idCarteiraCobranca)
                .then(function(data) { 
                    modalMessage("Carteira de Cobrança " + data.nome + " Removida com sucesso!");
                    todos();                        
                })
                .catch(function(e) {
                    modalMessage(e);
                });
        };

        // ***** MODAL ***** //
        
        var modalMessage = function(message) {
            ModalService.modalMessage(message);
        };
        
        var modalVisualizar = function(carteiraCobranca) {
            var modalInstance = ModalService.modalDefault('partials/financeiro/carteiraCobranca/modalCarteiraCobrancaVisualizar.html', 'ModalCarteiraCobrancaVisualizarController', 'md',
                {
                    carteiraCobranca: function() {
                        return carteiraCobranca;
                    }
                });
            return modalInstance.result;
        };
        
        var modalSalvar = function(carteiraCobranca) {
            var modalInstance = ModalService.modalDefault('partials/financeiro/carteiraCobranca/modalCarteiraCobrancaEditar.html', 'ModalCarteiraCobrancaEditarController', 'lg',
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
        
        init();

    }]);
