'use strict';

app.controller('ModalColaboradorVisualizarController', 
    ['$scope', '$modalInstance', '$filter', 'colaborador', 'MESSAGES', 
    function ($scope, $modalInstance, $filter, colaborador, MESSAGES) {

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
