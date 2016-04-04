'use strict';

app.controller('ModalEditarContaCorrenteController', ['$scope', '$modalInstance', 'contaCorrente', 'ContaCorrenteService', 'BancoService', 'ModalService',
    function ($scope, $modalInstance, contaCorrente, ContaCorrenteService, BancoService, ModalService) {

        var init = function () {  
            $scope.maxValue = 31;
            $scope.contaCorrente = {
                idContaCorrente: (contaCorrente && contaCorrente.idContaCorrente) || null,
                nome: (contaCorrente && contaCorrente.nome) || null,
                banco: (contaCorrente && contaCorrente.banco) || null,
                agencia: (contaCorrente && contaCorrente.agencia) || null,
                agenciaDv: (contaCorrente && contaCorrente.agenciaDv) || null,
                contaCorrente: (contaCorrente && contaCorrente.contaCorrente) || null,
                contaCorrenteDv: (contaCorrente && contaCorrente.contaCorrenteDv) || null,
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
            ContaCorrenteService.getByContaCorrente($scope.contaCorrente.banco.idBanco, $scope.contaCorrente.agencia, 
                $scope.contaCorrente.agenciaDv, $scope.contaCorrente.contaCorrente, $scope.contaCorrente.contaCorrenteDv)
                .then(function(contaCorrente) {
                    if(!contaCorrente) { $modalInstance.close($scope.contaCorrente); }
                    else if(contaCorrente.idContaCorrente == $scope.contaCorrente.idContaCorrente) { $modalInstance.close($scope.contaCorrente); }
                    else { modalMessage("Esta conta corrente já está cadastrada"); } 
                })
                .catch(function(e) {
                    modalMessage(e);
                });
        };
        
        $scope.cancel = function () {
            $modalInstance.dismiss('cancel');
        };
        
        var modalMessage = function(message) {
            ModalService.modalMessage(message);
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
            if (form.agenciaDv.$error.required) {
                alert('Preencha o dígito verificador (DV) da agência da conta corrente!');
                return false;
            }
            if (form.agenciaDv.$error.maxlength) {
                alert('Preencha o dígito verificador (DV) da agência da conta corrente com no máximo 2 dígitos!');
                return false;
            }
            if (form.contaCorrente.$error.required) {
                alert('Preencha a conta corrente!');
                return false;
            }
            if (form.contaCorrenteDv.$error.required) {
                alert('Preencha o dígito verificador (DV) da conta corrente!');
                return false;
            }
            if (form.contaCorrenteDv.$error.maxlength) {
                alert('Preencha o dígito verificador (DV) da conta corrente com no máximo 2 dígitos!');
                return false;
            }
            return true;
        }     

        init();

    }]);
