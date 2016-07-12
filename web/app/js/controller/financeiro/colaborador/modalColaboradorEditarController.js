'use strict';

app.controller('ModalFornecedorEditarController', 
    ['$scope', '$modalInstance', 'fornecedor', 'CategoriaService', 'CepService', 'ModalService', 'LISTAS', 'MESSAGES',
    function ($scope, $modalInstance, fornecedor, CategoriaService, CepService, ModalService, LISTAS, MESSAGES) {

        var init = function () {  
            $scope.situacoes = LISTAS.statusFornecedor;
            $scope.tipoPessoas = LISTAS.tipoPessoa;
            $scope.sexos = LISTAS.sexo;
            $scope.fornecedor = fornecedor || {};  
            $scope.fornecedor.endereco = $scope.fornecedor.endereco || {};
            $scope.fornecedor.status = (fornecedor && fornecedor.status) || $scope.situacoes[0]; 
            $scope.fornecedor.tipoPessoa = (fornecedor && fornecedor.tipoPessoa) || $scope.tipoPessoas[0]; 
            $scope.fornecedor.sexo = (fornecedor && fornecedor.sexo) || $scope.sexos[0];
            getTitle();
            categorias();
        };

        // ***** CONTROLLER ***** //
        
        var getTitle = function() {
            if(fornecedor && fornecedor.idFornecedor) { $scope.title = "Editar Fornecedor"; }
            else { $scope.title = "Inserir Fornecedor"; }
        };
        
        var categorias = function() {
            CategoriaService.getAll()
                .then(function (data) {
                    $scope.categorias = data;
                    $scope.fornecedor.categoria = $scope.fornecedor.categoria || $scope.categorias[0];
                })
                .catch(function (e) {
                    console.log(e);
                });
        };
        
        $scope.pesquisarCep = function(cep) {
            CepService.cep(cep)
                .then(function (data) {
                    $scope.fornecedor.endereco.logradouro = data.logradouro;
                    $scope.fornecedor.endereco.bairro = data.bairro;
                    $scope.fornecedor.endereco.cidade = data.cidade;
                    $scope.fornecedor.endereco.estado = data.estado;
                })
                .catch(function (e) {
                    $scope.fornecedor.endereco.logradouro = '';
                    $scope.fornecedor.endereco.bairro = '';
                    $scope.fornecedor.endereco.cidade = '';
                    $scope.fornecedor.endereco.estado = '';
                });
        };
        
        $scope.ok = function(form) {
            if (!validarForm(form)) return;
            $modalInstance.close($scope.fornecedor);
        };
        
        $scope.cancel = function () {
            $modalInstance.dismiss('cancel');
        };

        // ***** MODAL ***** //
        
        var modalMessage = function(message) {
            ModalService.modalMessage(message);
        };

        var validarForm = function (form) {
            if (form.nome.$error.required) {
                alert('Preencha o nome do fornecedor!');
                return false;
            }
            return true;
        }     

        init();

    }]);
