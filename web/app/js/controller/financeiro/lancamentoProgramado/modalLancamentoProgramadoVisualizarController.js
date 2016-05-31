'use strict';

app.controller('ModalLancamentoProgramadoVisualizarController', ['$scope', '$modalInstance', 'lancamentoProgramado', 'MESSAGES', 
    function ($scope, $modalInstance, lancamentoProgramado, MESSAGES) {

        var init = function () { 
            $scope.lancamentoProgramado = lancamentoProgramado;
            getTitle();
        };    
                
        // ***** CONTROLLER ***** //    
        
        var getTitle = function() {
            $scope.title = MESSAGES.lancamento.programar.title.VISUALIZAR; 
        };
        
        $scope.editar = function() {
            $modalInstance.close(lancamentoProgramado.idLancamentoProgramado);
        };
        
        $scope.cancel = function () {
            $modalInstance.dismiss('cancel');
        };

        init();

    }]);
