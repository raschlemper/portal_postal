'use strict';

app.factory('FrequenciaLancamentoService', function(LISTAS) {
    
    var frequencias = LISTAS.frequencia;
    var situacoes = LISTAS.situacaoLancamentoProgramado;
    var execute = function (lancamentoProgramado) {
        if(!lancamentoProgramado) return lancamentoProgramado;
        var numeroParcela = lancamentoProgramado.numeroParcela + 1;
        switch (lancamentoProgramado.frequencia.id) {
            case frequencias[0].id: 
                lancamentoProgramado.situacao = situacoes[2];
                break;
            case frequencias[1].id: 
                lancamentoProgramado.numeroParcela = numeroParcela;
                lancamentoProgramado.competencia = addDaily(lancamentoProgramado.competencia);
                lancamentoProgramado.dataVencimento = addDaily(lancamentoProgramado.dataVencimento);
                break;
            case frequencias[2].id: 
                lancamentoProgramado.numeroParcela = numeroParcela;
                lancamentoProgramado.competencia = addWeekly(lancamentoProgramado.competencia);
                lancamentoProgramado.dataVencimento = addWeekly(lancamentoProgramado.dataVencimento);
                break;
            case frequencias[3].id: 
                lancamentoProgramado.numeroParcela = numeroParcela;
                lancamentoProgramado.competencia = addBiweekly(lancamentoProgramado.competencia);
                lancamentoProgramado.dataVencimento = addBiweekly(lancamentoProgramado.dataVencimento);
                break;
            case frequencias[4].id: 
                lancamentoProgramado.numeroParcela = numeroParcela;
                lancamentoProgramado.competencia = addMonthly(lancamentoProgramado.competencia);
                lancamentoProgramado.dataVencimento = addMonthly(lancamentoProgramado.dataVencimento);
                break;
            case frequencias[5].id: 
                lancamentoProgramado.numeroParcela = numeroParcela;
                lancamentoProgramado.competencia = addBimonthly(lancamentoProgramado.competencia);
                lancamentoProgramado.dataVencimento = addBimonthly(lancamentoProgramado.dataVencimento);
                break;
            case frequencias[6].id: 
                lancamentoProgramado.numeroParcela = numeroParcela;
                lancamentoProgramado.competencia = addQuarterly(lancamentoProgramado.competencia);
                lancamentoProgramado.dataVencimento = addQuarterly(lancamentoProgramado.dataVencimento);
                break;
            case frequencias[7].id: 
                lancamentoProgramado.numeroParcela = numeroParcela;
                lancamentoProgramado.competencia = addFourMonths(lancamentoProgramado.competencia);
                lancamentoProgramado.dataVencimento = addFourMonths(lancamentoProgramado.dataVencimento);
                break;
            case frequencias[8].id: 
                lancamentoProgramado.numeroParcela = numeroParcela;
                lancamentoProgramado.competencia = addSemiannual(lancamentoProgramado.competencia);
                lancamentoProgramado.dataVencimento = addSemiannual(lancamentoProgramado.dataVencimento);
                break;
            case frequencias[9].id: 
                lancamentoProgramado.numeroParcela = numeroParcela;
                lancamentoProgramado.competencia = addYearly(lancamentoProgramado.competencia);
                lancamentoProgramado.dataVencimento = addYearly(lancamentoProgramado.dataVencimento);
                break;
            default:
                lancamentoProgramado.situacao = situacoes[2];
                break;
        }
        return lancamentoProgramado;
    }
    
    var addData = function (frequencia, data) {
        switch (frequencia.id) {
            case frequencias[0].id: 
                return data;
            case frequencias[1].id: 
                return addDaily(data);
            case frequencias[2].id: 
                return addWeekly(data);
            case frequencias[3].id: 
                return addBiweekly(data);
            case frequencias[4].id: 
                return addMonthly(data);
            case frequencias[5].id: 
                return addBimonthly(data);
            case frequencias[6].id: 
                return addQuarterly(data);
            case frequencias[7].id: 
                return addFourMonths(data);
            case frequencias[8].id: 
                return addSemiannual(data);
            case frequencias[9].id: 
                return addYearly(data);
            default:
                return data;
        }
    }
    
    var addDaily = function(data) {
        return moment(data).add(1, 'd').toDate();
    }
    
    var addWeekly = function(data) {
        return moment(data).add(7, 'd').toDate();
    }
    
    var addBiweekly = function(data) {
        var days = 15;
        var lastDay = moment(angular.copy(data)).endOf('month').date();
        data = moment(data);        
        if(data.date() + days > lastDay) {
            if(lastDay > 30) {
                days += lastDay - 30;
            } else if(lastDay < 30) {
                days -= 30 - lastDay;      
            }
        }
        return data.add(days, 'd').toDate();

//        return moment(data).add(1, 'Q').toDate();
    }
    
    var addMonthly = function(data) {
        return moment(data).add(1, 'M').toDate();
    }
    
    var addBimonthly = function(data) {
        return moment(data).add(2, 'M').toDate();
    }
    
    var addQuarterly = function(data) {
        return moment(data).add(3, 'M').toDate();
    }
    
    var addFourMonths = function(data) {
        return moment(data).add(4, 'M').toDate();
    }
    
    var addSemiannual = function(data) {
        return moment(data).add(6, 'M').toDate();
    }
    
    var addYearly = function(data) {
        return moment(data).add(1, 'y').toDate();
    }

    return {
        execute: execute,
        addData: addData
    }

});