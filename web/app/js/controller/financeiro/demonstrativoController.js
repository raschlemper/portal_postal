'use strict';

app.controller('DemonstrativoController', ['$scope', '$q', '$filter', 'PlanoContaService', 'LancamentoService', 'SaldoService', 'GroupService', 'ModalService', 'LISTAS',
    function ($scope, $q, $filter, PlanoContaService, LancamentoService, SaldoService, GroupService,  ModalService, LISTAS) {
            
        var init = function () {
            $scope.meses = LISTAS.meses;
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
            var dataInicio = moment(ano + "-01-01").format('YYYY-MM-DD');
            var dataFim = moment(ano + "-12-31").format('YYYY-MM-DD');
            $q.all([PlanoContaService.getStructure(), 
                    LancamentoService.getSaldoPlanoConta(dataInicio, dataFim)])
               .then(function(values) {  
                    var estruturas = values[0];
                    var saldos = getSaldos(values[1]);
                    PlanoContaService.estrutura(estruturas);
                    $scope.estruturasLista = PlanoContaService.flatten(estruturas);
                    SaldoService.saldoPlanoConta($scope.estruturasLista, saldos);
                    $scope.totais = SaldoService.saldoPlanoContaTotalMes($scope.estruturasLista);
                    SaldoService.saldoPlanoContaGrupo($scope.estruturasLista);
                })
                .catch(function(e) {
                    modalMessage(e);
                });
        };
        
        var getSaldos = function(data) {
            var saldos = formatPlanoConta(data);
            var saldos = $filter('orderBy')(saldos, ['idPlanoConta', 'ano','mes']);
            return GroupService.saldo(saldos, ['idPlanoConta','ano','mes']);
        }
        
        var formatPlanoConta = function(saldos) {
            return _.map(saldos, function(saldo) {
                saldo.idPlanoConta = saldo.id;
                saldo.mes = moment(saldo.data).format('MM');
                saldo.ano = moment(saldo.data).format('YYYY');
                return _.pick(saldo, ['idPlanoConta', 'mes', 'ano', 'valor']);
            });
            
        };
        
        var modalMessage = function(message) {
            ModalService.modalMessage(message);
        };
        
        init();

    }]);
