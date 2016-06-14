'use strict';

app.controller('ModalLancamentoProgramadoVisualizarController', 
    ['$scope', '$modalInstance', 'lancamentoProgramado', 'PlanoContaService', 'CentroCustoService', 'ListaService', 'MESSAGES', 
    function ($scope, $modalInstance, lancamentoProgramado, PlanoContaService, CentroCustoService, ListaService, MESSAGES) {

        var init = function () { 
            $scope.lancamentoProgramado = lancamentoProgramado;
            getTitle();
            planoContas();              
            centroCustos();
        };    
                
        // ***** CONTROLLER ***** //    
        
        var getTitle = function() {
            $scope.title = MESSAGES.lancamento.programar.title.VISUALIZAR; 
        };
        
        var planoContas = function() {
            PlanoContaService.getStructure()
                .then(function (data) {
                    var planoContas = data;
                    PlanoContaService.estrutura(planoContas);
                    planoContas = PlanoContaService.flatten(planoContas);   
                    var planoConta = ListaService.getPlanoContaValue(planoContas, lancamentoProgramado.planoConta.idPlanoConta);                     
                    if(planoConta) { lancamentoProgramado.planoConta = planoConta.descricao; }
                    else { lancamentoProgramado.planoConta = null; }
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
                    var centroCusto = ListaService.getCentroCustoValue(centroCustos, lancamentoProgramado.centroCusto.idCentroCusto); 
                    if(centroCusto) { lancamentoProgramado.centroCusto = centroCusto.descricao; }
                    else { lancamentoProgramado.centroCusto = null; }
                })
                .catch(function (e) {
                    console.log(e);
                });
        };
        
        $scope.editar = function() {
            $modalInstance.close(lancamentoProgramado);
        };
        
        $scope.cancel = function () {
            $modalInstance.dismiss('cancel');
        };

        init();

    }]);
