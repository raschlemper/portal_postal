'use strict';

app.controller('ModalVisualizarCarteiraCobrancaController', ['$scope', '$modalInstance', '$filter', 'carteiraCobranca', 
    function ($scope, $modalInstance, $filter, carteiraCobranca) {

        var init = function () { 
            $scope.carteiraCobranca = criarCarteiraCobranca(carteiraCobranca);
        };       
        
        var criarCarteiraCobranca = function(carteiraCobranca) {
            carteiraCobranca.codigoBeneficiario = $filter('number')(carteiraCobranca.codigoBeneficiario) + '-' + carteiraCobranca.codigoBeneficiarioDv;            
            return carteiraCobranca;
        };
        
        $scope.editar = function() {
            $modalInstance.close(carteiraCobranca.idCarteiraCobranca);
        };
        
        $scope.cancel = function () {
            $modalInstance.dismiss('cancel');
        };

        init();

    }]);
