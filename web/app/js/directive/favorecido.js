app.directive('favorecido', function(FavorecidoService, ColaboradorService, FornecedorService, ColaboradorHandler, InformacaoProfissionalHandler, 
    InformacaoBancariaHandler, FornecedorHandler, ModalService) {
    
    return {
        restrict: 'E',
        templateUrl: "js/directive/html/favorecido.html",
        scope: { favorecidoModel: '=', favorecidos: '=?' },
        link: function($scope, element, attr, controller) {  
            
            var init = function() {
                $scope.filter = { name: 'favorecido', args: null };
                $scope.events = { 'selectItem': selectFavorecido };
                $scope.favorecidos = [];
                favorecidos();
            }       
        
            var favorecidos = function() {
                FavorecidoService.getAll()
                    .then(function (data) {
                        angular.forEach(data, function(favorecido) {
                            $scope.favorecidos.push(createItem(favorecido));                            
                        });
                        $scope.favorecidoModel = createItem($scope.favorecidoModel);
                    })
                    .catch(function (e) {
                        console.log(e);
                    });
            };
        
            var selectFavorecido = function(favorecido) {
                $scope.favorecidoModel = createItem(favorecido);
            };
            
            var createItem = function(favorecido) {
                if(!favorecido) return;
                favorecido.descricao = favorecido.nome;
                return favorecido;
            };

            // ***** COLABORADOR ***** //   
            
            $scope.salvarColaborador = function() {
                salvarColaborador();
            };

            var salvarColaborador = function() {
                modalSalvarColaborador()
                    .then(function(result) {
                        result = ajustarDadosColaborador(result);
                        saveColaborador(result);
                    });
            };

            var saveColaborador = function(colaborador) {
                ColaboradorService.save(colaborador)
                    .then(function(data) {  
                        $scope.favorecidos.splice(0, $scope.favorecidos.length);
                        favorecidos();
                    })
                    .catch(function(e) {
                        modalMessage(e);
                    });
            };

            // ***** FORNECEDOR ***** //   

            $scope.salvarFornecedor = function() {
                salvarFornecedor();
            };

            var salvarFornecedor = function() {
                modalSalvarFornecedor()
                    .then(function(result) {
                        result = ajustarDadosFornecedor(result);
                        saveFornecedor(result);
                    });
            };

            var saveFornecedor = function(fornecedor) {
                FornecedorService.save(fornecedor)
                    .then(function(data) {  
                        $scope.favorecidos.splice(0, $scope.favorecidos.length);
                        favorecidos();
                    })
                    .catch(function(e) {
                        modalMessage(e);
                    });
            };

            // ***** AJUSTAR ***** //

            var ajustarDadosColaborador = function(data) {  
                var colaborador = ColaboradorHandler.handle(data);            
                if(!_.isEmpty(data.endereco)) {
                    colaborador.endereco = data.endereco;
                }
                if(!_.isEmpty(data.informacaoProfissional)) {
                    colaborador.informacaoProfissional = InformacaoProfissionalHandler.handle(data.informacaoProfissional);
                }            
                if(!_.isEmpty(data.informacaoBancaria)) {
                    colaborador.informacaoBancaria = InformacaoBancariaHandler.handle(data.informacaoBancaria);
                }
                return colaborador;
            };
        
            var ajustarDadosFornecedor = function(data) {  
                var fornecedor = FornecedorHandler.handle(data);            
                if(!_.isEmpty(data.endereco)) {
                    fornecedor.endereco = data.endereco;
                }
                return fornecedor;
            };
                
            // ***** MODAL ***** //   

            var modalSalvarColaborador = function(colaborador) {
                var modalInstance = ModalService.modalDefault('partials/financeiro/colaborador/modalColaboradorEditar.html', 'ModalColaboradorEditarController', 'lg',
                    {
                        colaborador: function() {
                            return colaborador;
                        }
                    });
                return modalInstance.result;
            };
        
            var modalSalvarFornecedor = function(fornecedor) {
                var modalInstance = ModalService.modalDefault('partials/financeiro/fornecedor/modalFornecedorEditar.html', 'ModalFornecedorEditarController', 'lg',
                    {
                        fornecedor: function() {
                            return fornecedor;
                        }
                    });
                return modalInstance.result;
            };
            
            init();
        }
    }
});