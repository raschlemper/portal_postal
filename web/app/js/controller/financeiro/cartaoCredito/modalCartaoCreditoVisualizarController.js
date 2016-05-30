'use strict';

app.controller('ModalCartaoCreditoVisualizarController', ['$scope', '$modalInstance', 'cartaoCredito', 
    function ($scope, $modalInstance, cartaoCredito) {

        var init = function () { 
            $scope.cartaoCredito = cartaoCredito;
        };
        
        $scope.editar = function() {
            $modalInstance.close(cartaoCredito.idCartaoCredito);
        };
        
        $scope.cancel = function () {
            $modalInstance.dismiss('cancel');
        };

        init();

    }]);
