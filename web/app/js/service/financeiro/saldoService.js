'use strict';

app.factory('SaldoService', function($filter, LISTAS) {   
        
    // CONTAS /////
    
    var setSaldo = function(estruturas, saldos) {
        angular.forEach(estruturas, function(estrutura) {
            estrutura.saldos = {};
            getMesSaldo(estrutura, saldos);
        });
    };
    
    var setSaldoByMes = function(estruturas, saldos, mes) {
        angular.forEach(estruturas, function(estrutura) {
            estrutura.saldos = {};
            estrutura.saldos[mes.id] = 0;
            getSaldo(estrutura, mes, saldos);
        });
    };

    var getMesSaldo = function(estrutura, saldos) {
        angular.forEach(LISTAS.meses, function(mes) {
            estrutura.saldos[mes.id] = 0;
            getSaldo(estrutura, mes, saldos);
        });
    };

    var getSaldo = function(estrutura, mes, saldos) {
        angular.forEach(saldos, function(saldo) {
            if(estrutura.idPlanoConta === saldo.planoConta && mes.id === saldo.mes - 1) {
                estrutura.saldos[mes.id] = saldo.valor;
            }
        });
    };
    
    // GRUPO /////
        
    var setSaldoGrupo = function(estruturas) {
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
    
    // SALDO TOTAL /////
     
    var setSaldoTotalMes = function(estruturas) {
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
    
    return {
        saldo: setSaldo,
        saldoByMes: setSaldoByMes,
        saldoGrupo: setSaldoGrupo,
        saldoTotalMes: setSaldoTotalMes
    }

});