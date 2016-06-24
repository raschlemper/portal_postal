app.directive('appParcelar', function(FrequenciaLancamentoService) {
    
    return {
        restrict: 'E',
        templateUrl: 'js/directive/html/appParcelar.html',
        scope: {
            lancamentoParcelar: '=',
            lancar: '=?',
            form: '=',
            onlyView: '=',
            show: '='
        },
        link: function($scope, element, attr, controller, transclude) {            
                        
            var init = function (lancamentoParcelar) { 
                $scope.createParcelas(lancamentoParcelar);
            };

            // ***** CONTROLLER ***** //

            $scope.parcelar = function(lancamentoParcelar) {     
                $scope.show = !$scope.show;  
                init(lancamentoParcelar);           
            }

            $scope.cancelarParcelar = function(lancamentoParcelar) {
                $scope.show = !$scope.show;
                lancamentoParcelar.parcelas = null;
                lancamentoParcelar.quantidadeParcela = null;
            };

            $scope.createParcelas = function(lancamentoParcelar) {     
                lancamentoParcelar.parcelas = []; 
                var frequencia = lancamentoParcelar.frequencia;
                var dataCompetencia = lancamentoParcelar.dataCompetencia;
                var dataVencimento = lancamentoParcelar.dataVencimento;
                for(var i=0; i<lancamentoParcelar.quantidadeParcela; i++) {
                    var numeroParcela = (i + 1);
                    var lancamento = findParcelaBaixada(lancamentoParcelar.lancamentos, numeroParcela);
                    var parcela = getParcela(lancamentoParcelar, lancamento, numeroParcela, dataCompetencia, dataVencimento);
                    lancamentoParcelar.parcelas.push(parcela);
                    dataCompetencia = FrequenciaLancamentoService.addData(frequencia, dataCompetencia);
                    dataVencimento = FrequenciaLancamentoService.addData(frequencia, dataVencimento);
                }
            };

            var findParcelaBaixada = function(lancamentos, numeroParcela) {
                return _.find(lancamentos, function(lancamento) { 
                    return lancamento.numeroParcela === numeroParcela; 
                });
            };       
            
            $scope.$watchCollection("lancamentoParcelar.quantidadeParcela", function(newValue, oldValue) {
                if(!newValue) return;
                init($scope.lancamentoParcelar);
            });  
            
            $scope.$watchCollection("lancamentoParcelar.numero", function(newValue, oldValue) {
                if(newValue === oldValue) return;
                init($scope.lancamentoParcelar);
            });  
            
            $scope.$watchCollection("lancamentoParcelar.planoConta", function(newValue, oldValue) {
                if(newValue === oldValue) return;
                init($scope.lancamentoParcelar);
            });
            
            $scope.$watchCollection("lancamentoParcelar.centroCusto", function(newValue, oldValue) {
                if(newValue === oldValue) return;
                init($scope.lancamentoParcelar);
            });
            
            $scope.$watchCollection("lancamentoParcelar.vencimento", function(newValue, oldValue) {
                if(newValue === oldValue) return;
                init($scope.lancamentoParcelar);
            });
            
            $scope.$watchCollection("lancamentoParcelar.valor", function(newValue, oldValue) {
                if(newValue === oldValue) return;
                init($scope.lancamentoParcelar);
            });
            
            $scope.$watchCollection("lancamentoParcelar.rateios", function(newValue, oldValue) {
                if(newValue === oldValue) return;
                init($scope.lancamentoParcelar);
            });

            // ***** AJUSTAR ***** //

            var getParcela = function(lancamentoParcelar, lancamento, numeroParcela, dataCompetencia, dataVencimento) {
                var parcela = {
                    numero: lancamentoParcelar.numero,
                    numeroParcela: numeroParcela,
                    planoConta: lancamentoParcelar.planoConta,
                    centroCusto: lancamentoParcelar.centroCusto,
                    dataCompetencia: dataCompetencia,
                    dataVencimento: dataVencimento,
                    valor: lancamentoParcelar.valor / lancamentoParcelar.quantidadeParcela,
                    lancamento: lancamento
                };
                if(lancamentoParcelar.rateios && lancamentoParcelar.rateios.length) {
                    parcela.planoConta = { 'descricao': 'Diversos' }
                    parcela.centroCusto = { 'descricao': 'Diversos' }
                }
                return parcela;
            };
            
        }
    }
});