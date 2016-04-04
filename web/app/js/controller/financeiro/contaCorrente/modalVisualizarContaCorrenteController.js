'use strict';

app.controller('ModalVisualizarContaCorrenteController', ['$scope', '$modalInstance', 'contaCorrente', 
    function ($scope, $modalInstance, contaCorrente) {

        var init = function () { 
            $scope.contaCorrente = criarContaCorrente(contaCorrente);
        };
        
        var criarContaCorrente = function(contaCorrente) {
            contaCorrente.banco = contaCorrente.banco.nome;
            contaCorrente.agencia = $filter('number')(contaCorrente.agencia) + '-' + contaCorrente.agenciaDv;
            contaCorrente.contaCorrente = $filter('number')(contaCorrente.contaCorrente) + '-' + contaCorrente.contaCorrenteDv;
        };
        
        $scope.editar = function() {
            $modalInstance.close(contaCorrente.idContaCorrente);
        };
        
        $scope.cancel = function () {
            $modalInstance.dismiss('cancel');
        };

        init();

    }]);
