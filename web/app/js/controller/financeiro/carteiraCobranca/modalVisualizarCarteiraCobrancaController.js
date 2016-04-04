'use strict';

app.controller('ModalVisualizarCarteiraCobrancaController', ['$scope', '$modalInstance', 'carteiraCobranca', 
    function ($scope, $modalInstance, carteiraCobranca) {

        var init = function () { 
            $scope.carteiraCobranca = criarCarteiraCobranca(carteiraCobranca);
        };       
        
        var criarCarteiraCobranca = function(carteiraCobranca) {
            carteiraCobranca.codigoBeneficiario = $filter('number')(carteiraCobranca.codigoBeneficiario) + '-' + carteiraCobranca.codigoBeneficiarioDv;            
        };
        
        $scope.editar = function() {
            $modalInstance.close(carteiraCobranca.idCarteiraCobranca);
        };
        
        $scope.cancel = function () {
            $modalInstance.dismiss('cancel');
        };

        init();

    }]);
