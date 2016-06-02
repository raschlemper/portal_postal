'use strict';

app.controller('ModalLancamentoProgramadoParcelarController', ['$scope', 'FrequenciaLancamentoService', 'MESSAGES',
    function ($scope, FrequenciaLancamentoService, MESSAGES) {

        var init = function () {  
            getTitle();
            $scope.createParcelas($scope.lancamentoProgramado);
        };
        
        // ***** CONTROLLER ***** //
        
        var getTitle = function() {
            $scope.titleParcelar = $scope.title + " - " + MESSAGES.lancamento.title.PARCELAR; 
        };
        
        $scope.lancarParcela = function(form, lancamentoProgramado, parcela) {
            if(!$scope.validaConta(lancamentoProgramado.conta)) return;
            if(!$scope.validarForm(form, lancamentoProgramado)) return;     
            $scope.goToLancar();
            $scope.lancamento = $scope.getLancamento($scope.lancamentoProgramado, parcela, $scope.modelos[4]);
        };
        
        $scope.createParcelas = function(lancamentoProgramado) {       
            $scope.lancamentoProgramado.parcelas = [];
            var frequencia = lancamentoProgramado.frequencia;
            var dataCompetencia = lancamentoProgramado.dataCompetencia;
            var dataVencimento = lancamentoProgramado.dataVencimento;
            for(var i=0; i<lancamentoProgramado.quantidadeParcela; i++) {
                var numeroParcela = (i + 1);
                var lancamento = findParcelaBaixada(lancamentoProgramado.lancamentos, numeroParcela);
                var parcela = getParcela(lancamentoProgramado, lancamento, numeroParcela);
                $scope.lancamentoProgramado.parcelas.push(parcela);
                dataCompetencia = FrequenciaLancamentoService.addData(frequencia, dataCompetencia);
                dataVencimento = FrequenciaLancamentoService.addData(frequencia, dataVencimento);
            }
        };
        
        var findParcelaBaixada = function(lancamentos, numeroParcela) {
            return _.find(lancamentos, function(lancamento) { 
                return lancamento.numeroParcela == numeroParcela; 
            });
        };
        
        $scope.okParcelar = function(form, lancamentoProgramado) {
            $scope.ok(form, lancamentoProgramado);   
        };
        
        // ***** AJUSTAR ***** //
        
        var getParcela = function(lancamentoProgramado, lancamento, numeroParcela) {
            return {
                numero: lancamentoProgramado.numero,
                numeroParcela: numeroParcela,
                dataCompetencia: lancamentoProgramado.dataCompetencia,
                dataVencimento: lancamentoProgramado.dataVencimento,
                valor: lancamentoProgramado.valor / lancamentoProgramado.quantidadeParcela,
                lancamento: lancamento
            };
        };
        
        // ***** VALIDAR ***** //
        
        // ***** MODAL ***** //

        init();

    }]);
