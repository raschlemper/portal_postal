app.directive('favorecido', function(FavorecidoService) {
    
    return {
        restrict: 'E',
        templateUrl: "js/directive/html/favorecido.html",
        scope: { favorecidoModel: '=', favorecidos: '=?' },
        link: function($scope, element, attr, controller) {  
            
            var init = function() {
                $scope.filter = { name: 'favorecido', args: null };
                $scope.events = { 'selectItem': selectFavorecido };
                $scope.favorecidos = $scope.favorecidos || [];
                favorecidos();
            }       
        
            var favorecidos = function() {
                FavorecidoService.getAll()
                    .then(function (data) {
                        angular.forEach(data, function(favorecido) {
                            favorecido.descricao = favorecido.nome;
                            $scope.favorecidos.push(favorecido);                            
                        });
                    })
                    .catch(function (e) {
                        console.log(e);
                    });
            };
        
            var selectFavorecido = function(favorecido) {
                $scope.favorecidoModel = favorecido;
            }
            
            init();
        }
    }
});