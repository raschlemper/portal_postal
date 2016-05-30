'use strict';

app.controller('ModalLancamentoProgramadoVisualizarController', ['$scope', '$modalInstance', 'lancamentoProgramado', 
    function ($scope, $modalInstance, lancamentoProgramado) {

        var init = function () { 
            $scope.lancamentoProgramado = lancamentoProgramado;
        };
        
        $scope.editar = function() {
            $modalInstance.close(lancamentoProgramado.idLancamentoProgramado);
        };
        
        $scope.cancel = function () {
            $modalInstance.dismiss('cancel');
        };

        init();

    }]);
