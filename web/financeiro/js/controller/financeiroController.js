'use strict';

app.controller('FinanceiroController', ['$scope',function ($scope) {

  var init = function () {
      $scope.paramSession = {
          usuario: sessionStorage.usuario          
      };
  };

  init();

}]);
