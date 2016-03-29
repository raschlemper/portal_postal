'use strict';

app.controller('ModalVisualizarContaCorrenteController', ['$scope', '$modalInstance', 'contaCorrente', 
    function ($scope, $modalInstance, contaCorrente) {

        var init = function () { 
            $scope.contaCorrente = contaCorrente;
        };
        
        $scope.editar = function() {
            $modalInstance.close(contaCorrente.idContaCorrente);
        };
        
        $scope.cancel = function () {
            $modalInstance.dismiss('cancel');
        };

        init();

    }]);
