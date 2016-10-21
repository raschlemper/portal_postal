app.directive('appParcelar', function(DatePickerService, FrequenciaLancamentoService) {
    
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
                $scope.datepickerVencimento = angular.copy(DatePickerService.default); 
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
                if(lancamentoParcelar.quantidadeParcela && lancamentoParcelar.parcelas && lancamentoParcelar.parcelas.length) { createParcelasFromLista(lancamentoParcelar); }
                else { createNovasParcelas(lancamentoParcelar); }
            };

            var createParcelasFromLista = function(lancamentoParcelar) {  
                var parcelas = lancamentoParcelar.parcelas; 
                lancamentoParcelar.parcelas = []; 
                var frequencia = lancamentoParcelar.frequencia;
                var dataVencimento = lancamentoParcelar.dataVencimento;
                parcelas.map(function(parcela) {
                    if(!parcela.idLancamentoProgramadoParcela) { parcela.dataVencimento = dataVencimento; }
//                    var lancamento = findParcelaBaixada(lancamentoParcelar.lancamentos, parcela.numero);
//                    if(parcela.lancamento) { parcela.lancamento = LancamentoHandler.handle(parcela.lancamento); }
                    parcela = getParcela(parcela.idLancamentoProgramadoParcela, lancamentoParcelar, parcela.lancamento, parcela.numero, 
                                            parcela.dataVencimento, parcela.dataVencimento, parcela.valor);
                    lancamentoParcelar.parcelas.push(parcela);
                    dataVencimento = FrequenciaLancamentoService.addData(frequencia.id, dataVencimento);
                });
            };

            var createNovasParcelas = function(lancamentoParcelar) {   
                lancamentoParcelar.parcelas = []; 
                var frequencia = lancamentoParcelar.frequencia;
//                var dataCompetencia = lancamentoParcelar.dataCompetencia;
                var dataVencimento = lancamentoParcelar.dataVencimento;
                for(var i=0; i<lancamentoParcelar.quantidadeParcela; i++) {
                    var numeroLancamento = (i + 1);
//                    var lancamento = findParcelaBaixada(lancamentoParcelar.lancamentos, numeroLancamento);
                    var parcela = getParcela(null, lancamentoParcelar, null, numeroLancamento, dataVencimento, dataVencimento, null);
                    lancamentoParcelar.parcelas.push(parcela);
//                    dataCompetencia = FrequenciaLancamentoService.addData(frequencia, dataCompetencia);
                    dataVencimento = FrequenciaLancamentoService.addData(frequencia.id, dataVencimento);
                }
            };
            
            $scope.disabledNumeroParcela = function(parcelas) {
                if(!parcelas) return false;
                var disabled = false;
                parcelas.map(function(parcela) {
                    if(parcela.lancamento) { disabled = true; }
                });
                return disabled;
            }

            var findParcelaBaixada = function(lancamentos, numeroParcela) {
                return _.find(lancamentos, function(lancamento) { 
                    return lancamento.numeroParcela === numeroParcela; 
                });
            };       
            
            $scope.$watch("lancamentoParcelar.quantidadeParcela", function(newValue, oldValue) {
                if(!newValue) return;
                if(newValue === oldValue) return;
                createNovasParcelas($scope.lancamentoParcelar);
            });  
            
            $scope.$watch("lancamentoParcelar.numero", function(newValue, oldValue) {
                if(newValue === oldValue) return;
                init($scope.lancamentoParcelar);
            });  
            
            $scope.$watch("lancamentoParcelar.planoConta", function(newValue, oldValue) {
                if(newValue === oldValue) return;
                init($scope.lancamentoParcelar);
            });
            
            $scope.$watch("lancamentoParcelar.centroCusto", function(newValue, oldValue) {
                if(newValue === oldValue) return;
                init($scope.lancamentoParcelar);
            });
            
            $scope.$watch("lancamentoParcelar.frequencia", function(newValue, oldValue) {
                if(newValue === oldValue) return;
                init($scope.lancamentoParcelar);
            });
                        
            $scope.$watch("lancamentoParcelar.dataVencimento", function(newValue, oldValue) {
                if(newValue === oldValue) return;
                init($scope.lancamentoParcelar);
            });
            
            $scope.$watch("lancamentoParcelar.valor", function(newValue, oldValue) {
                if(newValue === oldValue) return;
                init($scope.lancamentoParcelar);
            });
            
            $scope.$watchCollection("lancamentoParcelar.rateios", function(newValue, oldValue) {
                if(newValue === oldValue) return;
                init($scope.lancamentoParcelar);
            });

            // ***** AJUSTAR ***** //

            var getParcela = function(idLancamentoProgramadoParcela, lancamentoParcelar, lancamento, numeroParcela, dataCompetencia, dataVencimento, valorParcela) {
                var parcela = {
                    idLancamentoProgramadoParcela: idLancamentoProgramadoParcela,
                    numero: numeroParcela,
                    numeroLancamento: lancamentoParcelar.numero,
                    planoConta: lancamentoParcelar.planoConta,
                    centroCusto: lancamentoParcelar.centroCusto,
                    dataCompetencia: dataCompetencia,
                    dataVencimento: dataVencimento,
                    valor: valorParcela || (lancamentoParcelar.valor / lancamentoParcelar.quantidadeParcela),
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