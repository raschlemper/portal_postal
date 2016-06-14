'use strict';

app.controller('ModalCartaoCreditoVisualizarController', 
    ['$scope', '$modalInstance', 'cartaoCredito', 'MESSAGES',
    function ($scope, $modalInstance, cartaoCredito, MESSAGES) {

        var init = function () { 
            $scope.cartaoCredito = cartaoCredito;
        };
                
        // ***** CONTROLLER ***** //   
        
        $scope.editar = function() {
            $modalInstance.close(cartaoCredito.idCartaoCredito);
        };
        
        $scope.cancel = function () {
            $modalInstance.dismiss('cancel');
        };

        init();

    }]);
