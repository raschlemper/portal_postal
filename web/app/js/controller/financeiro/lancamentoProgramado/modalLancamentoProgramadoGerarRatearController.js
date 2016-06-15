'use strict';

app.controller('ModalLancamentoProgramadoGerarRatearController', ['$scope', 'ListaService', 'FinanceiroValidation', 'MESSAGES',
    function ($scope, ListaService, FinanceiroValidation, MESSAGES) {

        var init = function () {  
            $scope.lancamento = $scope.lancamento || $scope.getLancamento($scope.lancamentoProgramado, null, $scope.modelos[2]);
            getTitle();
            ratear($scope.lancamento);  
        };
                
        // ***** CONTROLLER ***** //  
        
        var getTitle = function() {
            $scope.titleGerarRatear =  $scope.title + " - " + MESSAGES.lancamento.ratear.title.INSERIR;
        };
                
        // ***** RATEAR ***** //  
        
        var ratear = function(lancamento) {  
            lancamento.rateios = lancamento.rateios || [];
            criarRateiosLista(lancamento.rateios);
            setRateioDefault(lancamento, null);            
        }
        
        $scope.cancelarRatear = function() {
            $scope.lancamento.rateios = [];
            $scope.voltar();
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

        $scope.lancarRatear = function (form, lancamentoProgramado, lancamento) {
            if(!validarRateio(lancamento)) { return false; }
            $scope.lancar(form, lancamentoProgramado, lancamento);
        }
                
        // ***** AJUSTAR ***** // 
                
        // ***** VALIDAR ***** //  
        
        var validarRateio = function(lancamento) {
            if (!lancamento.rateios || !lancamento.rateios.length) return true;
            var saldo = saldoRateio(lancamento);
            if(!FinanceiroValidation.rateioSaldo(lancamento, saldo)) return false;
            _.map(lancamento.rateios, function(rateio) {
                if($scope.validarPlanoConta(rateio.planoConta)) { return false; }
                if(!$scope.validarCentroCusto(rateio.centroCusto)) { return false; }
            });  
            return true;
        };
                
        // ***** MODAL ***** //  

        init();

    }]);
