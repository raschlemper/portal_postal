'use strict';

app.controller('ModalColaboradorEditarController', 
    ['$scope', '$modalInstance', 'colaborador', 'BancoService', 'CepService', 'ModalService', 'DatePickerService', 'LISTAS', 'MESSAGES',
    function ($scope, $modalInstance, colaborador, BancoService, CepService, ModalService, DatePickerService, LISTAS, MESSAGES) {

        var init = function () {  
            $scope.situacoes = LISTAS.statusColaborador;
            $scope.sexos = LISTAS.sexo;
            $scope.estadosCivil = LISTAS.estadoCivil;
            $scope.tipos = LISTAS.tipoConta;
            $scope.colaborador = colaborador || {};  
            $scope.colaborador.endereco = $scope.colaborador.endereco || {};
            $scope.colaborador.informacaoProfissional = $scope.colaborador.informacaoProfissional || {};
            $scope.colaborador.informacaoBancaria = $scope.colaborador.informacaoBancaria || {};
            $scope.colaborador.status = (colaborador && colaborador.status) || $scope.situacoes[0]; 
            $scope.colaborador.sexo = (colaborador && colaborador.sexo) || $scope.sexos[0];
            $scope.colaborador.estadoCivil = (colaborador && colaborador.estadoCivil) || $scope.estadosCivil[0];
            $scope.colaborador.informacaoBancaria.tipoConta = (colaborador && colaborador.informacaoBancaria.tipoConta) || $scope.tipos[0];
            getTitle();
            bancos();
        };

        // ***** CONTROLLER ***** //
        
        var getTitle = function() {
            if(colaborador && colaborador.idColaborador) { $scope.title = "Editar Colaborador"; }
            else { $scope.title = "Inserir Colaborador"; }
        };
        
        var bancos = function() {
            BancoService.getAll()
                .then(function (data) {
                    $scope.bancos = data;
                    $scope.colaborador.informacaoBancaria.banco = $scope.colaborador.informacaoBancaria.banco || $scope.bancos[0];
                })
                .catch(function (e) {
                    console.log(e);
                });
        };
        
        $scope.pesquisarCep = function(cep) {
            CepService.cep(cep)
                .then(function (data) {
                    $scope.colaborador.endereco.logradouro = data.logradouro;
                    $scope.colaborador.endereco.bairro = data.bairro;
                    $scope.colaborador.endereco.cidade = data.cidade;
                    $scope.colaborador.endereco.estado = data.estado;
                })
                .catch(function (e) {
                    $scope.colaborador.endereco.logradouro = '';
                    $scope.colaborador.endereco.bairro = '';
                    $scope.colaborador.endereco.cidade = '';
                    $scope.colaborador.endereco.estado = '';
                });
        };
        
        $scope.ok = function(form) {
            if (!validarForm(form)) return;
            $modalInstance.close($scope.colaborador);
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
                alert('Preencha o nome do colaborador!');
                return false;
            }
            return true;
        }     

        init();

    }]);
