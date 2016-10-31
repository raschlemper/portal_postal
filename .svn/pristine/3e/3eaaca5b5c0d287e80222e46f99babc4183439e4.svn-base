'use strict';

app.factory('DemonstrativoService', function() {
    
    var report = function(dataInicio, dataFim, periodos, estruturas) {
        var dados = [];
        _.map(estruturas, function(estrutura) {           
            _.map(periodos, function(periodo) {
                var report = {};
                report.dataInicio = moment(dataInicio);
                report.dataFim = moment(dataFim);
                report.descricao = estrutura.descricao; 
                report.periodo = moment(periodo.ano + '-' + (periodo.id + 1) + '-01');//.format('YYYY-MM-DD HH:mm:ss');
                report.saldo = estrutura.saldos[periodo.order];
                dados.push(report);
            });
        });
        return dados;
    };

    return {
        report: report
    }

});