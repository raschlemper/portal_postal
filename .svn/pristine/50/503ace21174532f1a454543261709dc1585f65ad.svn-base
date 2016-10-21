'use strict';

app.controller('ModalBancoEditarController', ['$scope', '$modalInstance', 'banco', 'BancoService', 'ModalService',
    function ($scope, $modalInstance, banco, BancoService, ModalService) {

        var init = function () {  
            $scope.maxValue = 999;
            $scope.banco = banco || {}; 
            getTitle();
        };
        
        var getTitle = function() {
            if(banco && banco.idBanco) { $scope.title = "Editar Banco"; }
            else { $scope.title = "Inserir Banco"; }
        }
        
        $scope.ok = function(form) {
            if (!validarForm(form)) return;
            BancoService.getByNumero($scope.banco.numero)
                .then(function(banco) {
                    if(!banco) { $modalInstance.close($scope.banco); }
                    else if(banco.idBanco == $scope.banco.idBanco) { $modalInstance.close($scope.banco); }
                    else { modalMessage("Este número de banco já está cadastrado"); } 
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
            if (form.numero.$error.required) {
                alert('Preencha o número do banco!');
                return false;
            }
            if (form.numero.$error.max) {
                alert('Preencha o número do banco com valores entre 1 e 999!');
                return false;
            }
            if (form.nome.$error.required) {
                alert('Preencha o nome do banco!');
                return false;
            }
            return true;
        }     

        init();

    }]);
