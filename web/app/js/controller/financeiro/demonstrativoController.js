'use strict';

app.controller('DemonstrativoController', ['$scope', '$q', 'PlanoContaService', 'LancamentoService', 'SaldoService', 'ModalService',
    function ($scope, $q, PlanoContaService, LancamentoService, SaldoService, ModalService) {
            
        var init = function () {
            $scope.estruturasLista = [];
            anos();
        }; 
        
        $scope.changeAno = function(ano) {
            estruturas(ano.codigo);
        }

        var anos = function() {
            LancamentoService.findYearFromLancamento()
                .then(function (data) {
                    $scope.anos = createAnosLista(data);
                    $scope.anoSelected = $scope.anos[$scope.anos.length-1];
                    if($scope.anoSelected) { estruturas($scope.anoSelected.codigo); }
                })
                .catch(function(e) {
                    modalMessage(e);
                });
        };   
        
        var createAnosLista = function(anos) {            
            return _.map(anos, function(ano, index) {
                return {'id': index, 'codigo': ano, 'descricao': ano}
            })
        }
        
        var estruturas = function(ano) {
            $q.all([PlanoContaService.getStructure(), LancamentoService.getPlanoContaSaldo(ano, null, null)])
               .then(function(values) {  
                    $scope.estruturas = values[0];
                    $scope.saldos = values[1];
                    $scope.estruturasLista = PlanoContaService.estrutura($scope.estruturas);
                    $scope.estruturasLista = PlanoContaService.flatten($scope.estruturasLista);
                    SaldoService.saldo($scope.estruturasLista, $scope.saldos);
                    $scope.totais = SaldoService.saldoTotalMes($scope.estruturasLista);
                    SaldoService.saldoGrupo($scope.estruturasLista);
                })
                .catch(function(e) {
                    modalMessage(e);
                });
        };
        
        var modalMessage = function(message) {
            ModalService.modalMessage(message);
        };
        
        init();

    }]);
