'use strict';

app.controller('DemonstrativoController', ['$scope', '$q', '$filter', 'PlanoContaService', 'LancamentoService', 'SaldoService', 'PeriodoService', 'DemonstrativoService', 'ReportService', 'GroupService', 'ModalService', 'LISTAS',
    function ($scope, $q, $filter, PlanoContaService, LancamentoService, SaldoService, PeriodoService, DemonstrativoService, ReportService, GroupService, ModalService, LISTAS) {
            
        var init = function () {
            $scope.meses = LISTAS.meses;
            $scope.mesSelected = $scope.meses[0];
            $scope.estruturasLista = [];
            anos();
        }; 
        
        $scope.setData = function(mesSelected, anoSelected) {     
            if(!mesSelected || !anoSelected) return;       
            $scope.dataInicio = getDataInicio(mesSelected.id + 1, anoSelected.codigo);
            $scope.dataFim = getDataFim(mesSelected.id + 1, anoSelected.codigo);
            $scope.periodoSelected = PeriodoService.periodoOneYear(mesSelected, anoSelected.codigo);
        }
        
        $scope.pesquisar = function(dataInicio, dataFim) {            
            estruturas(dataInicio, dataFim);
        }

        var anos = function() {
            LancamentoService.findYearFromLancamento()
                .then(function (data) {
                    $scope.anos = createAnosLista(data);
                    $scope.anoSelected = $scope.anos[$scope.anos.length-1];
                    $scope.setData($scope.mesSelected, $scope.anoSelected);
                    $scope.pesquisar($scope.dataInicio, $scope.dataFim);
                })
                .catch(function(e) {
                    modalMessage(e);
                });
        }; 
        
        var createAnosLista = function(anos) {            
            return _.map(anos, function(ano, index) {
                return {'id': index, 'codigo': ano, 'descricao': ano}
            })
        };  
        
        var estruturas = function(dataInicio, dataFim) {
            $q.all([PlanoContaService.getStructure(), 
                    LancamentoService.getSaldoPlanoConta(dataInicio, dataFim)])
               .then(function(values) {  
                    var estruturas = values[0];
                    var saldos = getSaldos(values[1]);
                    PlanoContaService.estrutura(estruturas);
                    $scope.estruturasLista = PlanoContaService.flatten(estruturas);
                    SaldoService.saldoPlanoConta($scope.estruturasLista, saldos, $scope.periodoSelected);
                    $scope.totais = SaldoService.saldoPlanoContaTotalMes($scope.estruturasLista, $scope.periodoSelected);
                    SaldoService.saldoPlanoContaGrupo($scope.estruturasLista);
                })
                .catch(function(e) {
                    modalMessage(e);
                });
        };
        
        var getDataInicio = function(mes, ano) {
            return moment(ano + "-" + mes + "-01").format('YYYY-MM-DD HH:mm:ss');            
        }
        
        var getDataFim = function(mes, ano) {
            var dataInicio = getDataInicio(mes, ano)
            return moment(dataInicio).add(12, 'M').subtract(1, 'seconds').format('YYYY-MM-DD HH:mm:ss');
        }
        
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
        
        $scope.report = function(dataInicio, dataFim, periodos, estruturas) {
            var params = DemonstrativoService.report(dataInicio, dataFim, periodos, estruturas);   
            ReportService.pdf('demonstrativo', params);
        }
        
        init();

    }]);
