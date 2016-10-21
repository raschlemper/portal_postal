'use strict';

app.controller('ModalContaCorrenteEditarController', 
    ['$scope', '$modalInstance', 'contaCorrente', 'ContaCorrenteService', 'BancoService', 'ModalService', 'MESSAGES',
    function ($scope, $modalInstance, contaCorrente, ContaCorrenteService, BancoService, ModalService, MESSAGES) {

        var init = function () {  
            $scope.maxValue = 31;
            $scope.contaCorrente = contaCorrente || {};
            $scope.contaCorrente.poupanca = (contaCorrente && contaCorrente.poupanca);
            if(!$scope.contaCorrente.poupanca) { $scope.contaCorrente.poupanca = false; }
            getTitle();
            bancos();
        };
                
        // ***** CONTROLLER ***** //  
        
        var getTitle = function() {
            if(contaCorrente && contaCorrente.idContaCorrente) { $scope.title = "Editar Conta Corrente"; }
            else { $scope.title = "Inserir Conta Corrente"; }
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
        
        $scope.openBanco = function() {
            modalSalvarBanco().then(function(result) {
                BancoService.save(result)
                    .then(function(data) {  
                        modalMessage("Banco " + data.nome +  " Inserido com sucesso!");
                        bancos();
                    })
                    .catch(function(e) {
                        modalMessage(e);
                    });
            });
        }
        
        var modalSalvarBanco = function(banco) {
            var modalInstance = ModalService.modalDefault('partials/financeiro/banco/modalBancoEditar.html', 'ModalBancoEditarController', 'lg',
                {
                    banco: function() {
                        return banco;
                    }
                });
            return modalInstance.result;
        };
        
        $scope.ok = function(form) {
            if (!validarForm(form)) return;
            ContaCorrenteService.getByContaCorrente($scope.contaCorrente.banco.idBanco, $scope.contaCorrente.agencia, 
                $scope.contaCorrente.agenciaDv || 0, $scope.contaCorrente.contaCorrente, $scope.contaCorrente.contaCorrenteDv || 0)
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
        
        // ***** VALIDAR ***** //  

        var validarForm = function (form) {
            if (form.nome.$error.required) {
                alert('Preencha o nome da conta corrente!');
                return false;
            }
            if (form.agencia.$error.required) {
                alert('Preencha a agência da conta corrente!');
                return false;
            }
//            if (form.agenciaDv.$error.required) {
//                alert('Preencha o dígito verificador (DV) da agência da conta corrente!');
//                return false;
//            }
//            if (form.agenciaDv.$error.min || form.agenciaDv.$error.max) {
//                alert('Preencha o dígito verificador (DV) da agência da conta corrente com no máximo 2 dígitos!');
//                return false;
//            }
            if (form.contaCorrente.$error.required) {
                alert('Preencha a conta corrente!');
                return false;
            }
//            if (form.contaCorrenteDv.$error.required) {
//                alert('Preencha o dígito verificador (DV) da conta corrente!');
//                return false;
//            }
//            if (form.contaCorrenteDv.$error.min || form.contaCorrenteDv.$error.max) {
//                alert('Preencha o dígito verificador (DV) da conta corrente com no máximo 2 dígitos!');
//                return false;
//            }
            return true;
        };
                
        // ***** MODAL ***** //  
        
        var modalMessage = function(message) {
            ModalService.modalMessage(message);
        };

        init();

    }]);
