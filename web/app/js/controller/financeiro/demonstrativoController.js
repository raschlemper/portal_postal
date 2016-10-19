'use strict';

app.controller('DemonstrativoController', ['$scope', '$q', '$filter', 'PlanoContaService', 'CentroCustoService', 'LancamentoService', 'SaldoService', 'PeriodoService', 'DemonstrativoService', 'ReportService', 'GroupService', 'ModalService', 'LISTAS',
    function ($scope, $q, $filter, PlanoContaService, CentroCustoService, LancamentoService, SaldoService, PeriodoService, DemonstrativoService, ReportService, GroupService, ModalService, LISTAS) {
            
        var init = function () {
            $scope.meses = LISTAS.meses;
            $scope.mesSelected = $scope.meses[0];
            $scope.tipos = LISTAS.lancamento;
            $scope.estruturasLista = [];
            $scope.withValues = true;
            $scope.byCompetencia = false;  
            $scope.tipos = LISTAS.lancamento;
            anos();
        }; 
        
        $scope.setData = function(mesSelected, anoSelected) {     
            if(!mesSelected || !anoSelected) return;       
            $scope.dataInicio = getDataInicio(mesSelected.id + 1, anoSelected.codigo);
            $scope.dataFim = getDataFim(mesSelected.id + 1, anoSelected.codigo);
            $scope.periodoSelected = PeriodoService.periodoOneYear(mesSelected, anoSelected.codigo);
        }
        
        $scope.pesquisar = function(byCompetencia, byCentroCusto, dataInicio, dataFim) {  
            if(byCompetencia) { 
                if(byCentroCusto) { estruturasCentroCustoCompetencia(dataInicio, dataFim); }
                else { estruturasPlanoContaCompetencia(dataInicio, dataFim); }
            } else { 
                if(byCentroCusto) { estruturasCentroCusto(dataInicio, dataFim); }
                else { estruturasPlanoConta(dataInicio, dataFim); }
            }
        }

        var anos = function() {
            LancamentoService.findYearFromLancamento()
                .then(function (data) {
                    $scope.anos = createAnosLista(data);
                    $scope.anoSelected = $scope.anos[$scope.anos.length-1];
                    $scope.setData($scope.mesSelected, $scope.anoSelected);
                    $scope.pesquisar($scope.byCompetencia, $scope.byCentroCusto, $scope.dataInicio, $scope.dataFim);
                })
                .catch(function(e) {
                    modalMessage(e);
                });
        }; 
        
        var createAnosLista = function(anos) {    
            if(!anos) { anos = []; }
            if(!anos.length) { anos.push(moment().year()); }
            return _.map(anos, function(ano, index) {
                return {'id': index, 'codigo': ano, 'descricao': ano}
            })
        };  
        
        var estruturasPlanoConta = function(dataInicio, dataFim) {
            $q.all([PlanoContaService.getStructureByTipo($scope.tipos[0].id), 
                    PlanoContaService.getStructureByTipo($scope.tipos[1].id),
                    LancamentoService.getSaldoTipoPlanoConta(dataInicio, dataFim, $scope.tipos[0].id),
                    LancamentoService.getSaldoTipoPlanoConta(dataInicio, dataFim, $scope.tipos[1].id)])
               .then(function(values) {  
                    var estruturasReceita = setEstruturaPlanoContaDefault(values[0], $scope.tipos[0]);
                    var estruturasDespesa = setEstruturaPlanoContaDefault(values[1], $scope.tipos[1]);
                    var estruturasListaReceita = montaListaSaldosEstruturaPlanoConta(estruturasReceita, getSaldosPlanoConta(values[2]));
                    var estruturasListaDespesa = montaListaSaldosEstruturaPlanoConta(estruturasDespesa, getSaldosPlanoConta(values[3]));
                    montaListaEstrutura(estruturasListaReceita, estruturasListaDespesa);
                })
                .catch(function(e) {
                    modalMessage(e);
                });
        };
        
        var estruturasCentroCusto = function(dataInicio, dataFim) {
            $q.all([CentroCustoService.getStructure(), 
                    LancamentoService.getSaldoCentroCusto(dataInicio, dataFim)])
               .then(function(values) {  
                    var estruturas = setEstruturaCentroCustoDefault(values[0]);
                    montaListaSaldosEstruturaCentroCusto(estruturas, getSaldosCentroCusto(values[1]));
                })
                .catch(function(e) {
                    modalMessage(e);
                });
        };
        
        var estruturasPlanoContaCompetencia = function(dataInicio, dataFim) {
            $q.all([PlanoContaService.getStructureByTipo($scope.tipos[0].id), 
                    PlanoContaService.getStructureByTipo($scope.tipos[1].id),
                    LancamentoService.getSaldoTipoPlanoContaCompetencia(dataInicio, dataFim, $scope.tipos[0].id),
                    LancamentoService.getSaldoTipoPlanoContaCompetencia(dataInicio, dataFim, $scope.tipos[1].id)])
               .then(function(values) {  
                    var estruturasReceita = setEstruturaPlanoContaDefault(values[0], $scope.tipos[0]);
                    var estruturasDespesa = setEstruturaPlanoContaDefault(values[1], $scope.tipos[1]);
                    var estruturasListaReceita = montaListaSaldosEstruturaPlanoConta(estruturasReceita, getSaldosPlanoConta(values[2]));
                    var estruturasListaDespesa = montaListaSaldosEstruturaPlanoConta(estruturasDespesa, getSaldosPlanoConta(values[3]));
                    montaListaEstrutura(estruturasListaReceita, estruturasListaDespesa);
                })
                .catch(function(e) {
                    modalMessage(e);
                });            
        };
        
        var estruturasCentroCustoCompetencia = function(dataInicio, dataFim) {
            $q.all([CentroCustoService.getStructure(), 
                    LancamentoService.getSaldoCentroCustoCompetencia(dataInicio, dataFim)])
               .then(function(values) {  
                    var estruturas = setEstruturaCentroCustoDefault(values[0]);
                    montaListaSaldosEstruturaCentroCusto(estruturas, getSaldosCentroCusto(values[1]));
                })
                .catch(function(e) {
                    modalMessage(e);
                });            
        };
        
        var montaListaEstrutura = function(estruturasReceita, estruturasDespesa) {  
            $scope.estruturasLista = [];
            estruturasReceita.map(function(receita) {
                $scope.estruturasLista.push(receita);
            });
            estruturasDespesa.map(function(despesa) {
                $scope.estruturasLista.push(despesa);
            });
            $scope.estruturasLista;
        }
        
        var setEstruturaPlanoContaDefault = function(estruturas, tipo) {
            var estrutura = { "idPlanoConta": null };
            estruturas.push(getEstruturaDefault(estrutura, tipo));
            return estruturas;
        }
        
        var setEstruturaCentroCustoDefault = function(estruturas) {
            var estrutura = { "idCentroCusto": null };
            estruturas.push(getEstruturaDefault(estrutura, null));
            return estruturas;
        }
        
        var getEstruturaDefault = function(estrutura, tipo) {
            estrutura.idPlanoConta = 0;
            estrutura.codigo = 'X';
            estrutura.nome = "Não Identificado";
            estrutura.descricao = null;
            estrutura.nivel = 1;
            estrutura.estrutura = {"1":'X'};
            estrutura.grupo = null;
            estrutura.tipo = tipo || null;
            estrutura.centros = null;
            estrutura.lancamentos = null;
            estrutura.lancamentosProgramados = null;
            return estrutura;
        }
        
        var montaListaSaldosEstruturaPlanoConta = function(estruturas, saldos) {
            PlanoContaService.estrutura(estruturas);
            var estruturasLista = PlanoContaService.flatten(estruturas);
            SaldoService.saldoPlanoConta(estruturasLista, saldos, $scope.periodoSelected);
            $scope.totais = SaldoService.saldoPlanoContaTotalMes(estruturasLista, $scope.periodoSelected);
            SaldoService.saldoPlanoContaGrupo(estruturasLista);
            return estruturasLista;
        }
        
        var montaListaSaldosEstruturaCentroCusto = function(estruturas, saldos) {
            CentroCustoService.estrutura(estruturas);
            $scope.estruturasLista = CentroCustoService.flatten(estruturas);
            SaldoService.saldoCentroCusto($scope.estruturasLista, saldos, $scope.periodoSelected);
            $scope.totais = SaldoService.saldoCentroCustoTotalMes($scope.estruturasLista, $scope.periodoSelected);
            SaldoService.saldoCentroCustoGrupo($scope.estruturasLista);
        }
        
        var getDataInicio = function(mes, ano) {
            return moment(ano + "-" + mes + "-01").format('YYYY-MM-DD HH:mm:ss');            
        }
        
        var getDataFim = function(mes, ano) {
            var dataInicio = getDataInicio(mes, ano)
            return moment(dataInicio).add(12, 'M').subtract(1, 'seconds').format('YYYY-MM-DD HH:mm:ss');
        }
        
        var getSaldosPlanoConta = function(data) {
            var saldos = formatPlanoConta(data);
            var saldos = $filter('orderBy')(saldos, ['idPlanoConta', 'ano','mes']);
            return GroupService.saldo(saldos, ['idPlanoConta','ano','mes']);
        }
        
        var getSaldosCentroCusto = function(data) {
            var saldos = formatCentroCusto(data);
            var saldos = $filter('orderBy')(saldos, ['idCentroCusto', 'ano','mes']);
            return GroupService.saldo(saldos, ['idCentroCusto','ano','mes']);
        }
        
        var formatPlanoConta = function(saldos) {
            return _.map(saldos, function(saldo) {
                saldo.idPlanoConta = saldo.id;
                saldo.mes = moment(saldo.data).format('MM');
                saldo.ano = moment(saldo.data).format('YYYY');
                return _.pick(saldo, ['idPlanoConta', 'mes', 'ano', 'valor']);
            });            
        };
        
        var formatCentroCusto = function(saldos) {
            return _.map(saldos, function(saldo) {
                saldo.idCentroCusto = saldo.id;
                saldo.mes = moment(saldo.data).format('MM');
                saldo.ano = moment(saldo.data).format('YYYY');
                return _.pick(saldo, ['idCentroCusto', 'mes', 'ano', 'valor']);
            });            
        };
        
        $scope.showRow = function(withValues, estrutura) {
            if(!withValues) return true;
            var saldos = _.filter(estrutura.saldos, function(saldo){ return saldo > 0; });
            if(!saldos.length) return false;
            return true;
        }
        
        var modalMessage = function(message) {
            ModalService.modalMessage(message);
        };
        
        $scope.report = function(dataInicio, dataFim, periodos, estruturas) {
            var params = DemonstrativoService.report(dataInicio, dataFim, periodos, estruturas);   
            ReportService.pdf('demonstrativo', params);
        }
        
        init();

    }]);
