app.directive('planoConta', function(PlanoContaService, ListaService, FinanceiroValidation) {
    
    return {
        restrict: 'E',
        templateUrl: "js/directive/html/planoConta.html",
        scope: { planoContaModel: '=', tipo: '=' },
        link: function($scope, element, attr, controller) {  
            
            var init = function() {
                $scope.showTypeFilter = false;  
                $scope.validarSelected = true;  
                $scope.filter = { name: 'planoConta', args: null };
                $scope.events = { 'selectItem': selectPlanoConta };
                setTypeFilter();
                setValidarSelected();
                if($scope.tipo) {
                    planoContasByTipo($scope.tipo);
                } else {
                    planoContas();                    
                }
            }; 
                        
            var setTypeFilter = function() {
                if(attr.showTypeFilter) { $scope.showTypeFilter = (attr.showTypeFilter === "true"); }
            };       
                        
            var setValidarSelected = function() {
                if(attr.validarSelected) { $scope.validarSelected = (attr.validarSelected === "true"); }
            }; 
        
            var planoContas = function() {
                PlanoContaService.getStructure()
                    .then(function (data) {
                        createList(data);
                    })
                    .catch(function (e) {
                        console.log(e);
                    });
            };     
        
            var planoContasByTipo = function(tipo) {
                PlanoContaService.getStructureByTipo(tipo.id)
                    .then(function (data) {
                        createList(data);
                    })
                    .catch(function (e) {
                        console.log(e);
                    });
            };
            
            var createList = function(data) {
                $scope.planoContas = data;
                PlanoContaService.estrutura($scope.planoContas);
                $scope.planoContas = PlanoContaService.flatten($scope.planoContas);
                if($scope.planoContaModel) {
                    $scope.planoContaModel = ListaService.getPlanoContaValue(angular.copy($scope.planoContas), $scope.planoContaModel.idPlanoConta);
                }                 
            };
        
            var selectPlanoConta = function(planoConta) {
                if(!validarPlanoConta(planoConta)) return;
                $scope.planoContaModel = planoConta;
            };
        
            var validarPlanoConta = function(planoConta) {
                if(!$scope.validarSelected) return true;
                return FinanceiroValidation.planoContaResultado($scope.planoContas, planoConta);
            };
            
            init();
        }
    }
});