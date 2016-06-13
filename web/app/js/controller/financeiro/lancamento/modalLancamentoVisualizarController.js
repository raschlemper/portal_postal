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
                    var planoContas = data;
                    PlanoContaService.estrutura(planoContas);
                    planoContas = PlanoContaService.flatten(planoContas);   
                    var planoConta = ListaService.getPlanoContaValue(planoContas, lancamento.planoConta.idPlanoConta);                                    
                    if(planoConta) { lancamento.planoConta = planoConta.descricao; }
                })
                .catch(function (e) {
                    console.log(e);
                });
        };
        
        var centroCustos = function() {
            CentroCustoService.getStructure()
                .then(function (data) {
                    var centroCustos = data;
                    CentroCustoService.estrutura(centroCustos);
                    centroCustos = CentroCustoService.flatten(centroCustos); 
                    var centroCusto = ListaService.getCentroCustoValue(centroCustos, lancamento.centroCusto.idCentroCusto);                     
                    if(centroCusto) { lancamento.centroCusto = centroCusto.descricao; }                  
                })
                .catch(function (e) {
                    console.log(e);
                });
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
