app.directive('appRatear', function(FinanceiroValidation, ListaService, MESSAGES) {
    
    return {
        restrict: 'E',
        templateUrl: 'js/directive/html/appRatear.html',
        scope: {
            planoContas: '=',
            centroCustos: '=',
            lancamentoRatear: '=',
            onlyView: '=',
            show: '='
        },
        link: function($scope, element, attr, controller, transclude) {            
                        
            var init = function (lancamentoRatear) {  
                lancamentoRatear.rateios = lancamentoRatear.rateios || [];
                criarRateiosLista(lancamentoRatear);
                setRateioDefault(lancamentoRatear, null);
            };

            // ***** CONTROLLER ***** //  

            $scope.ratear = function(lancamentoRatear) {  
                $scope.show = !$scope.show;
                init(lancamentoRatear);                              
            }

            $scope.cancelarRatear = function(lancamentoRatear) {
                $scope.show = !$scope.show;
                lancamentoRatear.rateios = null;
            };

            $scope.salvarRateio = function(lancamentoRatear, rateio) {
                if(!$scope.validarPlanoConta(rateio.planoConta)) return;
                if(!$scope.validarCentroCusto(rateio.centroCusto)) return;
                var saldo = saldoRateio(lancamentoRatear);
                if(saldo + rateio.valor > lancamentoRatear.valor) {
                    modalMessage(MESSAGES.lancamento.ratear.validacao.SALDO_INCORRETO);
                    return;
                }
                rateio.percentual = calculatePercentual(lancamentoRatear, rateio);
                $scope.lancamentoRatear.rateios.push(rateio);
                setRateioDefault(lancamentoRatear, rateio);
            };

            $scope.editarRateio = function(lancamentoRatear, rateio, index) {
                $scope.rateio = rateio;
                $scope.removerRateio(lancamentoRatear, rateio, index);
                setRateioDefault(lancamentoRatear, rateio);
            };

            $scope.removerRateio = function(lancamentoRatear, rateio, index) {     
                lancamentoRatear.rateios.splice(index, 1); 
                setRateioDefault(lancamentoRatear, rateio);
            };

            var setRateioDefault = function(lancamentoRatear, rateio) {
                $scope.rateio = {};
                $scope.rateio.planoConta = (rateio && rateio.planoConta) || lancamentoRatear.planoConta || null;
                $scope.rateio.centroCusto = (rateio && rateio.centroCusto) || lancamentoRatear.centroCusto || null;
                $scope.rateio.valor = (lancamentoRatear.valor / getQuantidadeParcela(lancamentoRatear.quantidadeParcela)) - saldoRateio(lancamentoRatear);
            };

            var saldoRateio = function(lancamentoRatear) {            
                var saldo = 0;
                _.map(lancamentoRatear.rateios, function(rateio) {
                    saldo += rateio.valor;
                });
                return saldo;
            };

            var criarRateiosLista = function(lancamentoRatear) {
                return _.map(lancamentoRatear.rateios, function(rateio) {                 
                    if(rateio.planoConta && rateio.planoConta.idPlanoConta) { 
                        rateio.planoConta = ListaService.getPlanoContaValue($scope.planoContas, rateio.planoConta.idPlanoConta); 
                    }                 
                    if(rateio.centroCusto && rateio.centroCusto.idCentroCusto) { 
                        rateio.centroCusto = ListaService.getCentroCustoValue($scope.centroCustos, rateio.centroCusto.idCentroCusto); 
                    } 
                    rateio.percentual = calculatePercentual(lancamentoRatear, rateio);
                })
            };  
            
            var calculatePercentual = function(lancamentoRatear, rateio) {
                return rateio.valor / (lancamentoRatear.valor / getQuantidadeParcela(lancamentoRatear.quantidadeParcela));
            }; 
            
            var getQuantidadeParcela = function(quantidadeParcela) {
                if(quantidadeParcela) return quantidadeParcela;
                return 1;
            }
            
            var updateValueRateio = function(quantidade, valor, rateios) {
                var valorParcelado = valor / quantidade;
                return _.map(rateios, function(rateio) { 
                    rateio.valor =  rateio.percentual * valorParcelado;
                });
            };      
            
            $scope.$watchCollection("lancamentoRatear.quantidadeParcela", function(newValue, oldValue) {
                if(!newValue) return;
                if(newValue === oldValue) return;
                updateValueRateio(newValue, $scope.lancamentoRatear.valor, $scope.lancamentoRatear.rateios);
            });        
            
            $scope.$watchCollection("lancamentoRatear.valor", function(newValue, oldValue) {
                if(newValue === oldValue) return;
                updateValueRateio($scope.lancamentoRatear.quantidadeParcela, newValue, $scope.lancamentoRatear.rateios);
            });  
            
            $scope.$watchCollection("planoContas", function(newValue, oldValue) {
                if(!newValue) return;
                if(!$scope.planoContas || !$scope.centroCustos) return;
                init($scope.lancamentoRatear);
            });   
            
            $scope.$watchCollection("centroCustos", function(newValue, oldValue) {
                if(!newValue) return;
                if(!$scope.planoContas || !$scope.centroCustos) return;
                init($scope.lancamentoRatear);
            }); 

//            $scope.gerarRatear = function (form, lancamentoRatear, lancamento) {
//                if(!validarRateio(lancamentoRatear)) { return false; }
//                $scope.gerar(form, lancamentoRatear, lancamento);
//            };

//            $scope.okRatear = function(form, lancamentoRatear) {
//                $scope.ok(form, lancamentoRatear);   
//            };

            // ***** VALIDAR ***** //  

//            var validarRateio = function(lancamentoRatear) {
//                if (!lancamentoRatear.rateios || !lancamentoRatear.rateios.length) return true;
//                var saldo = saldoRateio(lancamentoRatear);
//                if(!FinanceiroValidation.rateioSaldo(lancamentoRatear, saldo)) return false;
//                _.map(lancamentoRatear.rateios, function(rateio) {
//                    if($scope.validarPlanoConta(rateio.planoConta)) { return false; }
//                    if(!$scope.validarCentroCusto(rateio.centroCusto)) { return false; }
//                });  
//                return true;
//            };   
//        
            $scope.validarPlanoConta = function(planoConta) {
                return FinanceiroValidation.planoContaResultado(planoConta);
            };

            $scope.validarCentroCusto = function(centroCusto) {
                return FinanceiroValidation.centroCustoResultado(centroCusto);
            };   
            
        }
    }
});