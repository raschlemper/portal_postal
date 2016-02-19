'use strict';

veiculo.controller('VeiculoController', ['$scope', 'VeiculoService', 'LISTAS', 
    function ($scope, VeiculoService, LISTAS) {

  var init = function () {
      $scope.tipos = LISTAS.tipo;
      //teste
  };
  
  $scope.changeTipo = function() {
    VeiculoService.marcaVeiculo('carros')
        .then(function(data) {
            $scope.marcas = data;
        })
        .catch(function(e) {     
            console.log(e);
        });     
  }

  init();

}]);
