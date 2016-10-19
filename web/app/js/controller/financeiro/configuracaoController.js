'use strict';

app.controller('ConfiguracaoController', ['$scope', 'ConfiguracaoService', 'ModalService', 'LISTAS',
    function ($scope, ConfiguracaoService, ModalService, LISTAS) {
            
        var init = function () {            
            $scope.configuracao = {};
            $scope.configuracao.favorecido = false; 
            $scope.configuracao.historico = false; 
            $scope.configuracao.planoConta = false; 
            $scope.configuracao.centroCusto = false; 
            visualizar();
        };

        // ***** VISUALIZAR ***** //

        var visualizar = function() {
            ConfiguracaoService.get()
                .then(function(data) {
                    $scope.configuracao = data;
                })
                .catch(function(e) {
                    modalMessage(e);
                });
        };

        // ***** SALVAR ***** //

        $scope.salvar = function(form, configuracao) {
            if(!validarForm(form)) return;
            save(configuracao);
        };
        
        var save = function(configuracao) {
            ConfiguracaoService.save(configuracao)
                .then(function(data) { 
                    $scope.configuracao = data;      
                    modalMessage("Configuração Alterada com sucesso!");
                })
                .catch(function(e) {
                    modalMessage(e);
                });            
        };
        
        // ***** VALIDAR ***** //  

        var validarForm = function (form) {
            if (form.favorecido.$error.required) {
                alert('Selecione o favorecido!');
                return false;
            }
            if (form.historico.$error.required) {
                alert('Selecione o histórico!');
                return false;
            }
            if (form.planoConta.$error.required) {
                alert('Selecione o plano de contas!');
                return false;
            }
            if (form.centroCusto.$error.required) {
                alert('Selecione o centro de custos!');
                return false;
            }
            return true;
        };

        // ***** MODAL ***** //
        
        var modalMessage = function(message) {
            ModalService.modalMessage(message);
        };
        
        init();

    }]);
