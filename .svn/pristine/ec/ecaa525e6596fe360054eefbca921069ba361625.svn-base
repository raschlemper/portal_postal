app.directive('centroCusto', function(CentroCustoService, ListaService) {
    
    return {
        restrict: 'E',
        templateUrl: "js/directive/html/centroCusto.html",
        scope: { centroCustoModel: '=' },
        link: function($scope, element, attr, controller) {  
            
            var init = function() {
                centroCustos(); 
            }        
        
            var centroCustos = function() {
                CentroCustoService.getStructure()
                    .then(function (data) {
                        createList(data);
                    })
                    .catch(function (e) {
                        console.log(e);
                    });
            };   
            
            var createList = function(data) {
                $scope.centroCustos = data;
                CentroCustoService.estrutura($scope.centroCustos);
                $scope.centroCustos = CentroCustoService.flatten($scope.centroCustos);
                if($scope.centroCustoModel) {
                    $scope.centroCustoModel = ListaService.getCentroCustoValue(angular.copy($scope.centroCustos), $scope.centroCustoModel.idCentroCusto);
                }                 
            }
        
            $scope.selectCentroCusto = function(centroCusto) {
                $scope.centroCustoModel = centroCusto;
            }
            
            init();
        }
    }
});