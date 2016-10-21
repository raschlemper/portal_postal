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
            $scope.title = MESSAGES.lancamento.programar.title.VISUALIZAR + ' ' + lancamentoProgramado.modelo.descricao;
        };
        
        var planoContas = function() {
            PlanoContaService.getStructure()
                .then(function (data) {
                    $scope.planoContas = data;
                    PlanoContaService.estrutura($scope.planoContas);
                    $scope.planoContas = PlanoContaService.flatten($scope.planoContas);   
                    var planoConta = ListaService.getPlanoContaValue($scope.planoContas, lancamentoProgramado.planoConta.idPlanoConta);                     
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
                    $scope.centroCustos = data;
                    CentroCustoService.estrutura($scope.centroCustos);
                    $scope.centroCustos = CentroCustoService.flatten($scope.centroCustos); 
                    var centroCusto = ListaService.getCentroCustoValue($scope.centroCustos, lancamentoProgramado.centroCusto.idCentroCusto); 
                    if(centroCusto) { lancamentoProgramado.centroCusto = centroCusto.descricao; }
                    else { lancamentoProgramado.centroCusto = null; }
                })
                .catch(function (e) {
                    console.log(e);
                });
        };
        
        $scope.getTipo = function(lancamentoProgramado) {
           if(lancamentoProgramado.modelo.id == 3) {
               return lancamentoProgramado.tipo.descricao + ' (Transferência)';
           } 
           return lancamentoProgramado.tipo.descricao;
        };
        
        $scope.editar = function() {
            $modalInstance.close(lancamentoProgramado);
        };
        
        $scope.cancel = function () {
            $modalInstance.dismiss('cancel');
        };

        init();

    }]);
