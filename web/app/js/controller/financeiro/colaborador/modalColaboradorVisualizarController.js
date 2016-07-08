'use strict';

app.controller('ModalColaboradorVisualizarController', 
    ['$scope', '$modalInstance', 'colaborador', 'MESSAGES', 
    function ($scope, $modalInstance, colaborador, MESSAGES) {

        var init = function () { 
            $scope.colaborador = colaborador;
        };     
                
        // ***** CONTROLLER ***** //  
        
        $scope.editar = function() {
            $modalInstance.close(colaborador);
        };
        
        $scope.cancel = function () {
            $modalInstance.dismiss('cancel');
        };

        init();

    }]);
