'use strict';

app.controller('ModalColaboradorVisualizarController', 
    ['$scope', '$modalInstance', '$filter', 'colaborador', 'MESSAGES', 
    function ($scope, $modalInstance, $filter, colaborador, MESSAGES) {

        var init = function () { 
            $scope.colaborador = criarColaborador(colaborador);
        };     
                
        // ***** CONTROLLER ***** //  
        
        var criarColaborador = function(contaCorrente) {
            contaCorrente.agencia = $filter('number')(contaCorrente.agencia) + '-' + contaCorrente.agenciaDv;
            contaCorrente.contaCorrente = $filter('number')(contaCorrente.contaCorrente) + '-' + contaCorrente.contaCorrenteDv;
            return contaCorrente
        };
        
        $scope.editar = function() {
            $modalInstance.close(colaborador);
        };
        
        $scope.cancel = function () {
            $modalInstance.dismiss('cancel');
        };

        init();

    }]);
