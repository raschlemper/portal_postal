'use strict';

app.controller('ModalCartaoCreditoEditarController', 
    ['$scope', '$modalInstance', 'cartaoCredito', 'ContaCorrenteService', 'ModalService', 'MESSAGES',
    function ($scope, $modalInstance, cartaoCredito, ContaCorrenteService, ModalService, MESSAGES) {

        var init = function () {  
            $scope.maxValue = 31;
            $scope.cartaoCredito = cartaoCredito || {}; 
            getTitle();
            contaCorrente();
        };

        // ***** CONTROLLER ***** //
        
        var getTitle = function() {
            if(cartaoCredito && cartaoCredito.idCartaoCredito) { $scope.title = "Editar Cartão de Crédito"; }
            else { $scope.title = "Inserir Cartão de Crédito"; }
        };
        
        var contaCorrente = function() {
            ContaCorrenteService.getAll()
                .then(function (data) {
                    $scope.contasCorrentes = data;
                    $scope.cartaoCredito.contaCorrente = $scope.cartaoCredito.contaCorrente;
                })
                .catch(function (e) {
                    console.log(e);
                });
        };
        
        $scope.openContaCorrente = function() {            
            modalSalvarContaCorrente().then(function(result) {
                ContaCorrenteService.save(result)
                    .then(function(data) {  
                        modalMessage("Conta Corrente " + data.nome +  " Inserida com sucesso!");
                        contaCorrente();
                    })
                    .catch(function(e) {
                        modalMessage(e);
                    });
            });
        };
        
        $scope.ok = function(form) {
            if (!validarForm(form)) return;
            $modalInstance.close($scope.cartaoCredito);
        };
        
        $scope.cancel = function () {
            $modalInstance.dismiss('cancel');
        };

        // ***** MODAL ***** //
        
        var modalSalvarContaCorrente = function(contaCorrente) {
            var modalInstance = ModalService.modalDefault('partials/financeiro/contaCorrente/ModalContaCorrenteEditar.html', 'ModalContaCorrenteEditarController', 'lg',
                {
                    contaCorrente: function() {
                        return contaCorrente;
                    }
                });
            return modalInstance.result;
        };
        
        var modalMessage = function(message) {
            ModalService.modalMessage(message);
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
        };  

        init();

    }]);
