'use strict';

app.controller('DemonstrativoController', ['$scope', '$q', 'PlanoContaService', 'LancamentoService', 'SaldoService', 'GroupService', 'ModalService',
    function ($scope, $q, PlanoContaService, LancamentoService, SaldoService, GroupService,  ModalService) {
            
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
            $q.all([PlanoContaService.getStructure(), 
                    LancamentoService.getSaldo(moment("2016-01-01").format('YYYY-MM-DD'), moment("2016-12-31").format('YYYY-MM-DD'))])
               .then(function(values) {  
                    var estruturas = values[0];
                    var saldos = getSaldos(values[1]);
                    $scope.estruturasLista = PlanoContaService.estrutura(estruturas);
                    $scope.estruturasLista = PlanoContaService.flatten($scope.estruturasLista);
                    SaldoService.saldo($scope.estruturasLista, saldos);
                    $scope.totais = SaldoService.saldoTotalMes($scope.estruturasLista);
                    SaldoService.saldoGrupo($scope.estruturasLista);
                })
                .catch(function(e) {
                    modalMessage(e);
                });
        };
        
        var getSaldos = function(data) {
            var lancamentos = formatPlanoConta(data);
            return groupBy(lancamentos, ['planoConta','ano','mes']);
            
        }
        
        var formatPlanoConta = function(lancamentos) {
            return _.map(lancamentos, function(lancamento) {
                lancamento.mes = moment(lancamento.data).format('MM');
                lancamento.ano = moment(lancamento.data).format('YYYY');
                return _.pick(lancamento, ['planoConta', 'mes', 'ano', 'valor']);
            })
        };
        
        var groupBy = function(data, fields) {
            return GroupService.saldo(data, fields)
        }
        
        var modalMessage = function(message) {
            ModalService.modalMessage(message);
        };
        
        
        
        
        
        
        
        var saldosNovos = function() {
            LancamentoService.getSaldo(moment("2016-01-01").format('YYYY-MM-DD'), moment("2016-12-31").format('YYYY-MM-DD'))
               .then(function(data) {  
                   var lancamentos = formatPlanoContaByMes(data);
                   console.log(groupBy(lancamentos, ['planoConta','ano','mes']));
                })
                .catch(function(e) {
                    modalMessage(e);
                });
        };
        
        
        saldosNovos();
        init();

    }]);
