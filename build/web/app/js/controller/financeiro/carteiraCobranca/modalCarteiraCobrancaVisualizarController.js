'use strict';

app.controller('ModalCarteiraCobrancaVisualizarController', 
    ['$scope', '$modalInstance', '$filter', 'carteiraCobranca', 'MESSAGES', 
    function ($scope, $modalInstance, $filter, carteiraCobranca, MESSAGES) {

        var init = function () { 
            $scope.carteiraCobranca = criarCarteiraCobranca(carteiraCobranca);
        };     
                
        // ***** CONTROLLER ***** //   
        
        var criarCarteiraCobranca = function(carteiraCobranca) {
            carteiraCobranca.codigoBeneficiario = $filter('number')(carteiraCobranca.codigoBeneficiario) + '-' + carteiraCobranca.codigoBeneficiarioDv;            
            return carteiraCobranca;
        };
        
        $scope.editar = function() {
            $modalInstance.close(carteiraCobranca);
        };
        
        $scope.cancel = function () {
            $modalInstance.dismiss('cancel');
        };

        init();

    }]);
