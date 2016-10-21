'use strict';

app.controller('ModalMoverContaController', 
    ['$scope', '$modalInstance', 'conta', 'ContaService', 'ModalService', 'MESSAGES',
    function ($scope, $modalInstance, conta, ContaService, ModalService, MESSAGES) {

        var init = function () {  
            $scope.conta = {}; 
            todos();
        };
                
        // ***** CONTROLLER ***** //
        
        var todos = function() {
            ContaService.getAll()
                .then(function (data) {
                    $scope.contas = data;
                    $scope.conta = $scope.contas[0];
                })
                .catch(function(e) {
                    modalMessage(e);
                });
        };
        
        $scope.ok = function(form) {
            if (!validarForm(form)) return;
            $modalInstance.close($scope.conta);
        };
        
        $scope.cancel = function () {
            $modalInstance.dismiss('cancel');
        };

        // ***** MODAL ***** //
        
        var modalMessage = function(message) {
            ModalService.modalMessage(message);
        };
                
        // ***** VALIDAR ***** //  

        var validarForm = function (form) {
            if (form.conta.$error.required) {
                alert('Preencha a conta!');
                return false;
            }
            return true;
        }     

        init();

    }]);
