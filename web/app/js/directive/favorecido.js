app.directive('favorecido', function(FavorecidoService) {
    
    return {
        restrict: 'E',
        templateUrl: "js/directive/html/favorecido.html",
        scope: { favorecidoModel: '=' },
        link: function($scope, element, attr, controller) {  
            
            var init = function() {
                favorecidos();
            }       
        
            var favorecidos = function() {
                FavorecidoService.getAll()
                    .then(function (data) {
                        $scope.favorecidos = data;
                    })
                    .catch(function (e) {
                        console.log(e);
                    });
            };
        
            $scope.selectFavorecido = function(favorecido) {
                $scope.favorecidoModel = favorecido;
            }
            
            init();
        }
    }
});