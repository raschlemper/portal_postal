'use strict';

app.factory('SaldoService', function($filter, LISTAS) {   
        
    // SALDOS /////
    
    var setSaldo = function(datas, saldos, id) {
        angular.forEach(datas, function(data) {
            data.saldos = {};
            getMesSaldo(data, saldos, id);
        });
    };
    
    var setSaldoByMes = function(datas, saldos, mes, id) {
        angular.forEach(datas, function(data) {
            data.saldos = {};
            data.saldos[mes.id] = 0;
            getSaldo(data, mes, saldos, id);
        });
    };

    var getMesSaldo = function(data, saldos, id) {
        angular.forEach(LISTAS.meses, function(mes) {
            data.saldos[mes.id] = 0;
            getSaldo(data, mes, saldos, id);
        });
    };

    var getSaldo = function(data, mes, saldos, id) {
        angular.forEach(saldos, function(saldo) {
            if(data[id] === saldo[id] && mes.id === saldo.mes - 1) {
                data.saldos[mes.id] = saldo.valor;
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
     
    var setSaldoPlanoContaTotalMes = function(estruturas) {
        var totais = {};
        angular.forEach(estruturas, function(estrutura) {
            getSaldoEstrutura(estrutura, totais);
        });
        return totais;
    };

    var getSaldoEstrutura = function(estrutura, totais) {
        angular.forEach(LISTAS.meses, function(mes) {               
            if(!totais[mes.id]) { totais[mes.id] = 0; }
            if(estrutura.tipo.codigo === 'receita') { totais[mes.id] += estrutura.saldos[mes.id]; }
            else if(estrutura.tipo.codigo === 'despesa') { totais[mes.id] -= estrutura.saldos[mes.id]; }
        });
    };  
    
    // SALDO PLANO DE CONTA /////
    
    var setSaldoPlanoConta = function(estruturas, saldos) {
        setSaldo(estruturas, saldos, 'idPlanoConta');
    }
    
    var setSaldoPlanoContaByMes = function(estruturas, saldos, mes) {
        return setSaldoByMes(estruturas, saldos, mes, 'idPlanoConta');
    }
    
    return {
        saldoPlanoConta: setSaldoPlanoConta,
        saldoPlanoContaByMes: setSaldoPlanoContaByMes,
        saldoPlanoContaGrupo: setSaldoPlanoContaGrupo,
        saldoPlanoContaTotalMes: setSaldoPlanoContaTotalMes
    }

});