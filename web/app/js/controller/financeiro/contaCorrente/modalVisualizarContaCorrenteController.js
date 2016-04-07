'use strict';

app.controller('ModalVisualizarContaCorrenteController', ['$scope', '$modalInstance', '$filter', 'contaCorrente', 
    function ($scope, $modalInstance, $filter, contaCorrente) {

        var init = function () { 
            $scope.contaCorrente = criarContaCorrente(contaCorrente);
        };
        
        var criarContaCorrente = function(contaCorrente) {
            contaCorrente.agencia = $filter('number')(contaCorrente.agencia) + '-' + contaCorrente.agenciaDv;
            contaCorrente.contaCorrente = $filter('number')(contaCorrente.contaCorrente) + '-' + contaCorrente.contaCorrenteDv;
            return contaCorrente
        };
        
        $scope.editar = function() {
            $modalInstance.close(contaCorrente.idContaCorrente);
        };
        
        $scope.cancel = function () {
            $modalInstance.dismiss('cancel');
        };

        init();

    }]);
