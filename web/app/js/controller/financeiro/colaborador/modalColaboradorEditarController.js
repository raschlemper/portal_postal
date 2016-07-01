'use strict';

app.controller('ModalColaboradorEditarController', 
    ['$scope', '$modalInstance', 'colaborador', 'ModalService', 'MESSAGES',
    function ($scope, $modalInstance, colaborador, ModalService, MESSAGES) {

        var init = function () {  
            $scope.colaborador = colaborador || {};
            getTitle();
        };

        // ***** CONTROLLER ***** //
        
        var getTitle = function() {
            if(colaborador && colaborador.idColaborador) { $scope.title = "Editar Colaborador"; }
            else { $scope.title = "Inserir Colaborador"; }
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
