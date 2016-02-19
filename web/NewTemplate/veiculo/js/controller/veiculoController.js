'use strict';

veiculo.controller('VeiculoController', ['$scope', 'VeiculoService', 'LISTAS', 
    function ($scope, VeiculoService, LISTAS) {

  var init = function () {
      $scope.tipoSelected = null;
      $scope.marcaSelected = null;
      $scope.modeloSelected = null;
      $scope.combustivelSelected = null;
      $scope.statusSelected = null;
      $scope.situacaoSelected = null;
      
      $scope.tipos = LISTAS.tipo;
      $scope.tipoSelected = $scope.tipos[1];
      $scope.combustiveis = LISTAS.combustivel;
      $scope.combustivelSelected = $scope.combustiveis[0];
      $scope.status = LISTAS.status;
      $scope.statusSelected = $scope.status[0];
      $scope.situacoes = LISTAS.situacao;
      $scope.situacaoSelected = $scope.situacoes[0];
           
      $scope.minVal = 1970;
      $scope.maxVal = (new Date).getFullYear() + 1;     
      
      $scope.changeTipo($scope.tipoSelected);
  };
  
  $scope.changeTipo = function(tipo) {
    VeiculoService.marcaVeiculo(tipo.key)
        .then(function(data) {
            $scope.marcas = data;
            $scope.marcaSelected = data[0];
            $scope.changeMarca($scope.tipoSelected, $scope.marcaSelected);
        })
        .catch(function(e) {     
            console.log(e);
        });     
  };
  
  $scope.changeMarca = function(tipo, marca) {
    VeiculoService.modeloVeiculo(tipo.key, marca.id)
        .then(function(data) {
            $scope.modelos = data;
            $scope.modeloSelected = data[0];
        })
        .catch(function(e) {     
            console.log(e);
        });     
  };
  
  $scope.salvar = function(form) {
      if(validarForm(form)) {
        console.log(form.$valid);
        //action="${pageContext.request.contextPath}/veiculo?action=save" method="post"
     }
  }
  
  var validarForm = function(form) {
       if(form.placa.$error.required) { 
           alert('Preencha a placa do ve\u00EDculo!'); 
           return false; 
       }    
       if(form.anoFabricacao.$error.min || form.anoFabricacao.$error.max) { 
           alert('Preencha o ano de fabrica\u00E7\u00E3o do ve\u00EDculo com valores entre ' + $scope.minVal + ' e ' + $scope.maxVal + '!'); 
           return false; 
       }   
       if(form.anoModelo.$error.min || form.anoModelo.$error.max) { 
           alert('Preencha o ano do modelo do ve\u00EDculo com valores entre ' + $scope.minVal + ' e ' + $scope.maxVal + '!'); 
           return false; 
       }     
       if(form.renavam.$error.required) { 
           alert('Preencha o renavam do ve\u00EDculo!'); 
           return false; 
       }      
       if(form.quilometragem.$error.required) { 
           alert('Preencha a quilometragem do ve\u00EDculo!'); 
           return false; 
       } 
       return true;
  }

  init();

}]);
