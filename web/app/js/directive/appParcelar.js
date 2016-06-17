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
                        
            var init = function () {  
                $scope.parcelar($scope.lancamentoParcelar);
            };

            // ***** CONTROLLER ***** //

            $scope.parcelar = function(lancamentoParcelar) {       
                lancamentoParcelar.parcelas = [];
                $scope.createParcelas(lancamentoParcelar);
            }

            $scope.cancelarParcelar = function(lancamentoParcelar) {
                lancamentoParcelar.parcelas = null;
                lancamentoParcelar.quantidadeParcela = null;
            };

            $scope.createParcelas = function(lancamentoParcelar) {     
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
            
            $scope.$watchCollection("lancamentoParcelar.numero", function(newValue, oldValue) {
                if(newValue === oldValue) return;
                init();
            });  
            
            $scope.$watchCollection("lancamentoParcelar.planoConta", function(newValue, oldValue) {
                if(newValue === oldValue) return;
                init();
            });
            
            $scope.$watchCollection("lancamentoParcelar.centroCusto", function(newValue, oldValue) {
                if(newValue === oldValue) return;
                init();
            });
            
            $scope.$watchCollection("lancamentoParcelar.vencimento", function(newValue, oldValue) {
                if(newValue === oldValue) return;
                init();
            });
            
            $scope.$watchCollection("lancamentoParcelar.valor", function(newValue, oldValue) {
                if(newValue === oldValue) return;
                init();
            });
            
            $scope.$watchCollection("lancamentoParcelar.rateios", function(newValue, oldValue) {
                if(newValue === oldValue) return;
                init();
            });

//            $scope.okParcelar = function(form, lancamentoProgramado) {
//                $scope.ok(form, lancamentoProgramado);   
//            };

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

            init();
            
        }
    }
});