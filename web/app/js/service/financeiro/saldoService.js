'use strict';

app.factory('SaldoService', function($filter) {   
        
    // SALDOS /////
    
    var setSaldo = function(datas, saldos, meses, id) {
        angular.forEach(datas, function(data) {
            data.saldos = {};
            getMesSaldo(data, saldos, meses, id);
        });
    };
    
    var setSaldoByMes = function(datas, saldos, mes, id) {
        angular.forEach(datas, function(data) {
            data.saldos = {};
            data.saldos[mes.order] = 0;
            getSaldo(data, mes, saldos, id);
        });
    };

    var getMesSaldo = function(data, saldos, meses,  id) {
        angular.forEach(meses, function(mes) {
            data.saldos[mes.order] = 0;
            getSaldo(data, mes, saldos, id);
        });
    };

    var getSaldo = function(data, mes, saldos, id) {
        angular.forEach(saldos, function(saldo) {
            if(data[id] === saldo[id] && mes.id === saldo.mes - 1) {
                data.saldos[mes.order] = saldo.valor;
            }
        });
    };    
    
    // SALDO GRUPO PLANO DE CONTA /////
        
    var setSaldoPlanoContaGrupo = function(estruturas) {
        estruturas = $filter('orderBy')(estruturas, 'nivel', true);
        angular.forEach(estruturas, function(estrutura) {
            getSaldoGrupo(estruturas, estrutura);
        });
    };
    
    // SALDO GRUPO CENTRO DE CUSTO /////
                
    var setSaldoCentroCustoGrupo = function(estruturas) {
        estruturas = $filter('orderBy')(estruturas, 'nivel', true);
        angular.forEach(estruturas, function(estrutura) {
            getSaldoGrupo(estruturas, estrutura);
        });
    };

    var getSaldoGrupo = function(estruturas, estruturaGrupo) {
        var estruturaSaldo = _.find(estruturas, function(estrutura) { 
            return estrutura.idPlanoConta === estruturaGrupo.idGrupo;
        });             
        if(estruturaSaldo) { sumSaldo(estruturaSaldo, estruturaGrupo.saldos); }
    }; 

    var sumSaldo = function(estrutura, saldos) {
        angular.forEach(saldos, function(saldo, index) {
            estrutura.saldos[index] += saldo;
        })
    };
    
    // SALDO TOTAL MES PLANO DE CONTA /////
     
    var setSaldoPlanoContaTotalMes = function(estruturas, meses) {
        var totais = {};
        angular.forEach(estruturas, function(estrutura) {
            getSaldoEstrutura(estrutura, totais, meses);
        });
        return totais;
    };
    
    // SALDO TOTAL MES CENTRO DE CUSTO /////
     
    var setSaldoCentroCustoTotalMes = function(estruturas, meses) {
        var totais = {};
        angular.forEach(estruturas, function(estrutura) {
            getSaldoEstrutura(estrutura, totais, meses);
        });
        return totais;
    };

    var getSaldoEstrutura = function(estrutura, totais, meses) {
        angular.forEach(meses, function(mes) {               
            if(!totais[mes.order]) { totais[mes.order] = 0; }
            if(estrutura.tipo) {
                if(estrutura.tipo.codigo === 'receita') { totais[mes.order] += estrutura.saldos[mes.order]; }
                else if(estrutura.tipo.codigo === 'despesa') { totais[mes.order] -= estrutura.saldos[mes.order]; }
            } else {
                totais[mes.order] += estrutura.saldos[mes.order];
            }    
        });
    };  
    
    // SALDO PLANO DE CONTA /////
    
    var setSaldoPlanoConta = function(estruturas, saldos, meses) {
        setSaldo(estruturas, saldos, meses, 'idPlanoConta');
    }
    
    var setSaldoPlanoContaByMes = function(estruturas, saldos, mes) {
        return setSaldoByMes(estruturas, saldos, mes, 'idPlanoConta');
    } 
    
    // SALDO CENTRO DE CUSTO /////
    
    var setSaldoCentroCusto = function(estruturas, saldos, meses) {
        setSaldo(estruturas, saldos, meses, 'idCentroCusto');
    }
    
    var setSaldoCentroCustoByMes = function(estruturas, saldos, mes) {
        return setSaldoByMes(estruturas, saldos, mes, 'idCentroCusto');
    } 
    
    // SALDO TIPO LANCAMENTO /////
    
    var setSaldoTipoLancamento = function(tipos, saldos, meses) {
        setSaldo(tipos, saldos, meses, 'id');
    }
    
    return {
        saldoPlanoConta: setSaldoPlanoConta,
        saldoCentroCusto: setSaldoCentroCusto,
        saldoPlanoContaByMes: setSaldoPlanoContaByMes,
        saldoCentroCustoByMes: setSaldoCentroCustoByMes,
        saldoTipoLancamento: setSaldoTipoLancamento,
        saldoPlanoContaGrupo: setSaldoPlanoContaGrupo,
        saldoCentroCustoGrupo: setSaldoCentroCustoGrupo,
        saldoPlanoContaTotalMes: setSaldoPlanoContaTotalMes,
        saldoCentroCustoTotalMes: setSaldoCentroCustoTotalMes
    }

});