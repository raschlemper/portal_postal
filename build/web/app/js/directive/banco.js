app.directive('banco', function(BancoService, ColaboradorService, FornecedorService, ColaboradorHandler, InformacaoProfissionalHandler, 
    InformacaoBancariaHandler, FornecedorHandler, ModalService) {
    
    return {
        restrict: 'E',
        templateUrl: "js/directive/html/banco.html",
        scope: { bancoModel: '=', bancos: '=?' },
        link: function($scope, element, attr, controller) {  
            
            var init = function() {
                $scope.events = { 'selectItem': selectBanco };
                $scope.bancos = [];
                bancos();
            }       
        
            var bancos = function() {
                BancoService.getAll()
                    .then(function (data) {
                        angular.forEach(data, function(banco) {
                            $scope.bancos.push(createItem(banco));                            
                        });
                        $scope.bancoModel = createItem($scope.bancoModel);
                    })
                    .catch(function (e) {
                        console.log(e);
                    });
            };
        
            var selectBanco = function(banco) {
                $scope.bancoModel = createItem(banco);
            };
            
            var createItem = function(banco) {
                if(!banco) return;
                banco.descricao = banco.numero + ' - ' + banco.nome;
                return banco;
            };
            
            init();
        }
    }
});