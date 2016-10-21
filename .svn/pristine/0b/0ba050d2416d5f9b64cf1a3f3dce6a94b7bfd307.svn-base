'use strict';

app.controller('ModalContaCorrenteVisualizarController', 
    ['$scope', '$modalInstance', '$filter', 'contaCorrente', 'MESSAGES',
    function ($scope, $modalInstance, $filter, contaCorrente, MESSAGES) {

        var init = function () { 
            $scope.contaCorrente = criarContaCorrente(contaCorrente);
        };
                
        // ***** CONTROLLER ***** //  
        
        var criarContaCorrente = function(contaCorrente) {
            contaCorrente.agencia = $filter('number')(contaCorrente.agencia) + '-' + contaCorrente.agenciaDv;
            contaCorrente.contaCorrente = $filter('number')(contaCorrente.contaCorrente) + '-' + contaCorrente.contaCorrenteDv;
            return contaCorrente
        };
        
        $scope.editar = function() {
            $modalInstance.close(contaCorrente);
        };
        
        $scope.cancel = function () {
            $modalInstance.dismiss('cancel');
        };

        init();

    }]);
