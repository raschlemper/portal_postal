'use strict';

app.controller('ModalEditarCartaoCreditoController', ['$scope', '$modalInstance', 'cartaoCredito', 'CartaoCreditoService', 'ModalService',
    function ($scope, $modalInstance, cartaoCredito, CartaoCreditoService, ModalService) {

        var init = function () {  
            $scope.maxValue = 31;
            $scope.cartaoCredito = {
                idCartaoCredito: (cartaoCredito && cartaoCredito.idCartaoCredito) || null,
                nome: (cartaoCredito && cartaoCredito.nome) || null,
                bandeira: (cartaoCredito && cartaoCredito.bandeira) || null,
                diaFechamento: (cartaoCredito && cartaoCredito.diaFechamento) || null,
                diaVencimento: (cartaoCredito && cartaoCredito.diaVencimento) || null,
                valorLimiteCredito: (cartaoCredito && cartaoCredito.valorLimiteCredito) || null
            }; 
            getTitle();
        };
        
        var getTitle = function() {
            if(cartaoCredito && cartaoCredito.idCartaoCredito) { $scope.title = "Editar Cartão de Crédito"; }
            else { $scope.title = "Inserir Novo Cartão de Crédito"; }
        }
        
        $scope.ok = function(form) {
            if (!validarForm(form)) return;
            $modalInstance.close($scope.cartaoCredito);
        };
        
        $scope.cancel = function () {
            $modalInstance.dismiss('cancel');
        };

        var validarForm = function (form) {
            if (form.nome.$error.required) {
                alert('Preencha o nome do cartão de crédito!');
                return false;
            }
            if (form.bandeira.$error.required) {
                alert('Preencha o nome da bandeira do cartão de crédito!');
                return false;
            }
            if (form.diaFechamento.$error.required) {
                alert('Preencha o dia de fechamento do cartão de crédito!');
                return false;
            }
            if (form.diaFechamento.$error.max) {
                alert('Preencha o dia de fechamento com valores entre 1 e 31!');
                return false;
            }
            if (form.diaVencimento.$error.required) {
                alert('Preencha o dia de vencimento do cartão de crédito!');
                return false;
            }
            if (form.diaVencimento.$error.max) {
                alert('Preencha o dia de vencimento com valores entre 1 e 31!');
                return false;
            }
            if (form.valorLimiteCredito.$error.required) {
                alert('Preencha o valor limite de crédito do cartão de crédito!');
                return false;
            }
            return true;
        }     

        init();

    }]);
