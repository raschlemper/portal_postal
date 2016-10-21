app.directive('appRatear', function(FinanceiroValidation, ListaService,  ModalService, MESSAGES) {
    
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
                $scope.lancamentoRatear.rateios.push(angular.copy(rateio));
                setRateioDefault(lancamentoRatear, null);
            };

            $scope.editarRateio = function(lancamentoRatear, rateio, index) {
                $scope.rateio = rateio;
                $scope.removerRateio(lancamentoRatear, rateio, index);
                setRateioDefault(lancamentoRatear, rateio);
            };

            $scope.removerRateio = function(lancamentoRatear, rateio, index) {     
                lancamentoRatear.rateios.splice(index, 1); 
                setRateioDefault(lancamentoRatear, null);
            };

            var setRateioDefault = function(lancamentoRatear, rateio) {
                $scope.rateio = {};
                $scope.rateio.planoConta = (rateio && rateio.planoConta) || lancamentoRatear.planoConta || null;
                $scope.rateio.centroCusto = (rateio && rateio.centroCusto) || lancamentoRatear.centroCusto || null;
                $scope.rateio.valor = lancamentoRatear.valor - saldoRateio(lancamentoRatear); //(lancamentoRatear.valor / getQuantidadeParcela(lancamentoRatear.quantidadeParcela)) - saldoRateio(lancamentoRatear);
            };

            var saldoRateio = function(lancamentoRatear) {            
                var saldo = 0;
                _.map(lancamentoRatear.rateios, function(rateio) {
                    saldo += toFixe(rateio.valor, 2);
                });
                return saldo;
            };

            var criarRateiosLista = function(lancamentoRatear) {
                var saldo = saldoRateio(lancamentoRatear);
                return _.map(lancamentoRatear.rateios, function(rateio) {                 
                    if(rateio.planoConta && rateio.planoConta.idPlanoConta) { 
                        rateio.planoConta = ListaService.getPlanoContaValue($scope.planoContas, rateio.planoConta.idPlanoConta); 
                    }                 
                    if(rateio.centroCusto && rateio.centroCusto.idCentroCusto) { 
                        rateio.centroCusto = ListaService.getCentroCustoValue($scope.centroCustos, rateio.centroCusto.idCentroCusto); 
                    } 
                    rateio.percentual = calculatePercentual(saldo, rateio);
                    rateio.valor = rateio.percentual * lancamentoRatear.valor;
//                    rateio.valorParcela = rateio.percentual * (lancamentoRatear.valor / getQuantidadeParcela(lancamentoRatear.quantidadeParcela));
                })
            };  
            
            var calculatePercentual = function(saldo, rateio) {
//                return rateio.valor / (lancamentoRatear.valor / getQuantidadeParcela(lancamentoRatear.quantidadeParcela));
                return rateio.valor / saldo;
            }; 
            
            var getQuantidadeParcela = function(quantidadeParcela) {
                if(quantidadeParcela) return quantidadeParcela;
                return 1;
            }
            
            var updateValueRateio = function(quantidade, valor, rateios) {
//                var valorParcelado = valor / quantidade;
                var valorParcelado = valor;
                var saldo = saldoRateio($scope.lancamentoRatear);
                return _.map(rateios, function(rateio) { 
                    rateio.percentual = calculatePercentual(saldo, rateio);
                    rateio.valor =  rateio.percentual * valorParcelado;
                });
            };     
        
            var toFixe = function(value, fixe) {
                return parseFloat(value.toFixed(fixe));
            }  
            
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

            // ***** VALIDAR ***** //  
            
            $scope.validarPlanoConta = function(planoConta) {
                return FinanceiroValidation.planoContaResultado($scope.planoContas, planoConta);
            };

            $scope.validarCentroCusto = function(centroCusto) {
                return FinanceiroValidation.centroCustoResultado($scope.centroCustos, centroCusto);
            };   
        
            // ***** MODAL ***** //

            var modalMessage = function(message) {
                ModalService.modalMessage(message);
            }; 
            
        }
    }
});