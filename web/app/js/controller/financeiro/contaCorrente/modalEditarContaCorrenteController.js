'use strict';

app.controller('ModalEditarContaCorrenteController', ['$scope', '$modalInstance', 'contaCorrente', 'BancoService',
    function ($scope, $modalInstance, contaCorrente, BancoService) {

        var init = function () {  
            $scope.maxValue = 31;
            $scope.contaCorrente = {
                idContaCorrente: (contaCorrente && contaCorrente.idContaCorrente) || null,
                nome: (contaCorrente && contaCorrente.nome) || null,
                banco: (contaCorrente && contaCorrente.banco) || null,
                agencia: (contaCorrente && contaCorrente.agencia) || null,
                contaCorrente: (contaCorrente && contaCorrente.contaCorrente) || null,
                carteira: (contaCorrente && contaCorrente.carteira) || null,
                poupanca: (contaCorrente && contaCorrente.poupanca) || false
            }; 
            $scope.possuiCarteira = (contaCorrente && contaCorrente.carteira ? true : false);
            getTitle();
            bancos();
        };
        
        var getTitle = function() {
            if(contaCorrente && contaCorrente.idContaCorrente) { $scope.title = "Editar Conta Corrente"; }
            else { $scope.title = "Inserir Nova Conta Corrente"; }
        }
        
        var bancos = function() {
            BancoService.getAll()
                .then(function (data) {
                    $scope.bancos = data;
                    $scope.contaCorrente.banco = $scope.contaCorrente.banco || $scope.bancos[0];
                })
                .catch(function (e) {
                    console.log(e);
                });
        }
        
        $scope.ok = function(form) {
            if (!validarForm(form)) return;
            $modalInstance.close($scope.contaCorrente);
        };
        
        $scope.cancel = function () {
            $modalInstance.dismiss('cancel');
        };

        var validarForm = function (form) {
            if (form.nome.$error.required) {
                alert('Preencha o nome da conta corrente!');
                return false;
            }
            if (form.agencia.$error.required) {
                alert('Preencha a agência da conta corrente!');
                return false;
            }
            if (form.contaCorrente.$error.required) {
                alert('Preencha a conta corrente!');
                return false;
            }
            return true;
        }     

        init();

    }]);
