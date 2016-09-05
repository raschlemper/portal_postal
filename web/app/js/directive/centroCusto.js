app.directive('centroCusto', function(CentroCustoService, ListaService, FinanceiroValidation) {
    
    return {
        restrict: 'E',
        templateUrl: "js/directive/html/centroCusto.html",
        scope: { centroCustoModel: '=' },
        link: function($scope, element, attr, controller) {  
            
            var init = function() {
                $scope.filter = { name: 'centroCusto', args: null };
                $scope.events = { 'selectItem': selectCentroCusto };
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
        
            var selectCentroCusto = function(centroCusto) {
                if(!validarCentroCusto(centroCusto)) return;
                $scope.centroCustoModel = centroCusto;
            };
        
            var validarCentroCusto = function(centroCusto) {
                return FinanceiroValidation.centroCustoResultado($scope.centroCustos, centroCusto);
            };
            
            init();
        }
    }
});