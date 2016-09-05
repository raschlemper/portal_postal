'use strict';

app.controller('ModalLancamentoVisualizarController', 
    ['$scope', '$modalInstance', '$state', 'lancamento', 'PlanoContaService', 'CentroCustoService', 'ListaService', 'MESSAGES', 
    function ($scope, $modalInstance, $state, lancamento, PlanoContaService, CentroCustoService, ListaService, MESSAGES) {

        var init = function () { 
            $scope.lancamento = lancamento;
            getTitle(lancamento);
            planoContas();
            centroCustos();
        };  
                
        // ***** CONTROLLER ***** //    
        
        var getTitle = function(lancamento) {
            $scope.title = MESSAGES.lancamento.title.VISUALIZAR + ' ' + lancamento.modelo.descricao; 
        };
        
        var planoContas = function() {
            PlanoContaService.getStructure()
                .then(function (data) {
                    $scope.planoContas = data;
                    PlanoContaService.estrutura($scope.planoContas);
                    $scope.planoContas = PlanoContaService.flatten($scope.planoContas);   
                    var planoConta = ListaService.getPlanoContaValue($scope.planoContas, lancamento.planoConta.idPlanoConta);                                    
                    if(planoConta) { lancamento.planoConta = planoConta.descricao; }
                    else { lancamento.planoConta = null; }
                })
                .catch(function (e) {
                    console.log(e);
                });
        };
        
        var centroCustos = function() {
            CentroCustoService.getStructure()
                .then(function (data) {
                    $scope.centroCustos = data;
                    CentroCustoService.estrutura($scope.centroCustos);
                    $scope.centroCustos = CentroCustoService.flatten($scope.centroCustos); 
                    var centroCusto = ListaService.getCentroCustoValue($scope.centroCustos, lancamento.centroCusto.idCentroCusto);                     
                    if(centroCusto) { lancamento.centroCusto = centroCusto.descricao; } 
                    else { lancamento.centroCusto = null; }                 
                })
                .catch(function (e) {
                    console.log(e);
                });
        };
        
        $scope.getTipo = function(lancamento) {
           if(lancamento.modelo.id == 1) {
               var lancamentoTransferencia = getLancamentoTransferencia(lancamento);
               return lancamento.tipo.descricao + ' (Transferência - ' + 
                       lancamentoTransferencia.conta.nome + ')';
           } 
           return lancamento.tipo.descricao;
        };
        
        var getLancamentoTransferencia = function(lancamento) {
            if(lancamento.idLancamento === 
                    lancamento.lancamentoTransferencia.lancamentoOrigem.idLancamento) {
                return lancamento.lancamentoTransferencia.lancamentoDestino;
            } else {
                return lancamento.lancamentoTransferencia.lancamentoOrigem;
            }
        };
        
        $scope.editar = function() {
            $modalInstance.close(lancamento);
        };
        
        $scope.cancel = function () {
            $modalInstance.dismiss('cancel');
        };
        
        $scope.programacao = function () {
            $modalInstance.dismiss('cancel');
            $state.go('app.financeiro.lancamentoprogramado.edit', 
                {"id": lancamento.lancamentoProgramado.idLancamentoProgramado});
        };

        init();

    }]);
