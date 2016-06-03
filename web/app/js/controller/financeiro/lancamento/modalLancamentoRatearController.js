'use strict';

app.controller('ModalLancamentoRatearController', ['$scope', 'ListaService', 'MESSAGES',
    function ($scope, ListaService, MESSAGES) {

        var init = function () {  
            $scope.lancamento.rateios = ($scope.lancamento && $scope.lancamento.rateios) || [];
            getTitle();
            ratear($scope.lancamento);  
        };
                
        // ***** CONTROLLER ***** //  
        
        var getTitle = function() {
            $scope.titleRatear = MESSAGES.lancamento.ratear.title.INSERIR;
        };
                
        // ***** RATEAR ***** //  
        
        var ratear = function(lancamento) {  
            lancamento.rateios = lancamento.rateios || [];
            criarRateiosLista(lancamento.rateios);
            setRateioDefault(lancamento, null);            
        }
        
        $scope.cancelarRatear = function() {
            $scope.lancamento.rateios = [];
            $scope.goToEditar();
        };
        
        $scope.salvarRateio = function(lancamento, rateio) {
            if(!$scope.validarPlanoConta(rateio.planoConta)) return;
            if(!$scope.validarCentroCusto(rateio.centroCusto)) return;
            var saldo = saldoRateio(lancamento);
            if(saldo + rateio.valor > lancamento.valor) {
                modalMessage(MESSAGES.lancamento.ratear.validacao.SALDO_INCORRETO);
                return;
            }
            $scope.lancamento.rateios.push(rateio);
            setRateioDefault(lancamento, rateio);
        };
        
        $scope.editarRateio = function(lancamento, rateio, index) {
            $scope.rateio = rateio;
            $scope.removerRateio(index);
            setRateioDefault(lancamento, rateio);
        };
        
        $scope.removerRateio = function(lancamento, rateio, index) {     
            $scope.lancamento.rateios.splice(index, 1); 
            setRateioDefault(lancamento, rateio);
        };
        
        var setRateioDefault = function(lancamento, rateio) {
            $scope.rateio = {};
            $scope.rateio.planoConta = (rateio && rateio.planoConta) || $scope.planoContas[0];
            $scope.rateio.centroCusto = (rateio && rateio.centroCusto) || null;
            $scope.rateio.valor = lancamento.valor - saldoRateio(lancamento);
        };
        
        var saldoRateio = function(lancamento) {            
            var saldo = 0;
            _.map(lancamento.rateios, function(rateio) {
                saldo += rateio.valor;
            });
            return saldo;
        };
        
        var criarRateiosLista = function(rateios) {
            return _.map(rateios, function(rateio) {                 
                if(rateio.planoConta && rateio.planoConta.idPlanoConta) { 
                    rateio.planoConta = ListaService.getPlanoContaValue($scope.planoContas, rateio.planoConta.idPlanoConta); 
                }                 
                if(rateio.centroCusto && rateio.centroCusto.idCentroCusto) { 
                    rateio.centroCusto = ListaService.getCentroCustoValue($scope.centroCustos, rateio.centroCusto.idCentroCusto); 
                } 
            })
        };        

        $scope.okRatear = function (form, lancamento) {
            if(!validarRateio(lancamento)) { return false; }
            $scope.ok(form, lancamento);
        }
                
        // ***** AJUSTAR ***** // 
                
        // ***** VALIDAR ***** //  
        
        var validarRateio = function(lancamento) {
            if (!lancamento.rateios || !lancamento.rateios.length) return true;
            var saldo = saldoRateio(lancamento);
            if(saldo !== lancamento.valor) {
                alert(MESSAGES.lancamento.ratear.validacao.SALDO_INCORRETO);
                return false;                    
            }
            _.map(lancamento.rateios, function(rateio) {
                if($scope.validarPlanoConta(rateio.planoConta)) { return false; }
                if(!$scope.validarCentroCusto(rateio.centroCusto)) { return false; }
            });  
            return true;
        };
                
        // ***** MODAL ***** //  

        init();

    }]);
