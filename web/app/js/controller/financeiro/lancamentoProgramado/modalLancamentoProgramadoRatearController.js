'use strict';

app.controller('ModalLancamentoProgramadoRatearController', ['$scope', 'ListaService', 'FinanceiroValidation', 'MESSAGES',
    function ($scope, ListaService, FinanceiroValidation, MESSAGES) {

        var init = function () {  
//            $scope.lancamento = $scope.lancamento || $scope.getLancamento($scope.lancamentoProgramado, null, $scope.modelos[2]);
            getTitle();
            ratear($scope.lancamentoProgramado);  
        };
                
        // ***** CONTROLLER ***** //  
        
        var getTitle = function() {
            $scope.titleRatear = $scope.title + " - " + MESSAGES.lancamento.ratear.title.INSERIR; // Ajustear mensagem para programação
        };
                
        // ***** RATEAR ***** //  
        
        var ratear = function(lancamentoProgramado) {  
            lancamentoProgramado.rateios = lancamentoProgramado.rateios || [];
            criarRateiosLista(lancamentoProgramado.rateios);
            setRateioDefault(lancamentoProgramado, null);            
        }
        
        $scope.cancelarRatear = function() {
            $scope.lancamentoProgramado.rateios = [];
            $scope.voltar();
        };
        
        $scope.salvarRateio = function(lancamentoProgramado, rateio) {
            if(!$scope.validarPlanoConta(rateio.planoConta)) return;
            if(!$scope.validarCentroCusto(rateio.centroCusto)) return;
            var saldo = saldoRateio(lancamentoProgramado);
            if(saldo + rateio.valor > lancamentoProgramado.valor) {
                modalMessage(MESSAGES.lancamento.ratear.validacao.SALDO_INCORRETO);
                return;
            }
            $scope.lancamentoProgramado.rateios.push(rateio);
            setRateioDefault(lancamentoProgramado, rateio);
        };
        
        $scope.editarRateio = function(lancamentoProgramado, rateio, index) {
            $scope.rateio = rateio;
            $scope.removerRateio(index);
            setRateioDefault(lancamentoProgramado, rateio);
        };
        
        $scope.removerRateio = function(lancamentoProgramado, rateio, index) {     
            $scope.lancamentoProgramado.rateios.splice(index, 1); 
            setRateioDefault(lancamentoProgramado, rateio);
        };
        
        var setRateioDefault = function(lancamentoProgramado, rateio) {
            $scope.rateio = {};
            $scope.rateio.planoConta = (rateio && rateio.planoConta) || lancamentoProgramado.planoConta || null;
            $scope.rateio.centroCusto = (rateio && rateio.centroCusto) || lancamentoProgramado.centroCusto || null;
            $scope.rateio.valor = lancamentoProgramado.valor - saldoRateio(lancamentoProgramado);
        };
        
        var saldoRateio = function(lancamentoProgramado) {            
            var saldo = 0;
            _.map(lancamentoProgramado.rateios, function(rateio) {
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

        $scope.gerarRatear = function (form, lancamentoProgramado, lancamento) {
            if(!validarRateio(lancamentoProgramado)) { return false; }
            $scope.gerar(form, lancamentoProgramado, lancamento);
        };
        
        $scope.okRatear = function(form, lancamentoProgramado) {
            $scope.ok(form, lancamentoProgramado);   
        };
                
        // ***** VALIDAR ***** //  
        
        var validarRateio = function(lancamentoProgramado) {
            if (!lancamentoProgramado.rateios || !lancamentoProgramado.rateios.length) return true;
            var saldo = saldoRateio(lancamentoProgramado);
            if(!FinanceiroValidation.rateioSaldo(lancamentoProgramado, saldo)) return false;
            _.map(lancamentoProgramado.rateios, function(rateio) {
                if($scope.validarPlanoConta(rateio.planoConta)) { return false; }
                if(!$scope.validarCentroCusto(rateio.centroCusto)) { return false; }
            });  
            return true;
        };                

        init();

    }]);
