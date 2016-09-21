app.directive('banco', function(BancoService, ColaboradorService, FornecedorService, ColaboradorHandler, InformacaoProfissionalHandler, 
    InformacaoBancariaHandler, FornecedorHandler, ModalService) {
    
    return {
        restrict: 'E',
        templateUrl: "js/directive/html/banco.html",
        scope: { bancoModel: '=', bancos: '=?' },
        link: function($scope, element, attr, controller) {  
            
            var init = function() {
                $scope.events = { 'selectItem': selectBanco };
                $scope.bancos = $scope.bancos || [];
                bancos();
            }       
        
            var bancos = function() {
                BancoService.getAll()
                    .then(function (data) {
                        angular.forEach(data, function(banco) {
                            banco.descricao = banco.nome;
                            $scope.bancos.push(banco);                            
                        });
                    })
                    .catch(function (e) {
                        console.log(e);
                    });
            };
        
            var selectBanco = function(banco) {
                $scope.bancoModel = banco;
            };
            
            init();
        }
    }
});