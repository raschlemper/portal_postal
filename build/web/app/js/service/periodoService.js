'use strict';

app.factory('PeriodoService', function(LISTAS) {  
    
    var meses = LISTAS.meses;
        
    var periodoOneYear = function(mesStart, anoStart) { 
        var periodo = [];
        var index = 1;
        // Ano corrente
        _.map(meses, function(mes) {
            if(mes.id < mesStart.id) return;
            mes.order = index;
            mes.ano = anoStart;
            periodo.push(mes);
            index++;
        }); 
        // Próximo ano
        var nextAno = anoStart + 1;
        _.map(meses, function(mes) {
            if(mes.id >= mesStart.id) return;
            mes.order = index;
            mes.ano = nextAno;
            periodo.push(mes);
            index++;
        });
        return periodo;
    };

    var dateByPeriodo = function(periodo) {
        if(!periodo) return;
        var dataInicio;
        var dataFim;
        switch(periodo.id) {
            case 0:
                dataInicio = null;
                dataFim = moment().subtract(1, 'days').endOf('day');
                break; 
            case 1:
                dataInicio = moment().startOf('month');
                dataFim = moment().endOf('month');
                break; 
            case 2:
                dataInicio = moment().subtract(7, 'days');
                dataFim = moment();
                break; 
            case 3:
                dataInicio = moment().subtract(15, 'days');
                dataFim = moment();
                break; 
            case 4:
                dataInicio = moment().subtract(30, 'days');
                dataFim = moment();
                break; 
            case 5:
                dataInicio = moment().subtract(60, 'days');
                dataFim = moment();
                break; 
            case 6:
                dataInicio = moment().subtract(90, 'days');
                dataFim = moment();
                break; 
            case 7:
                dataInicio = moment();
                dataFim = moment();
                break; 
            case 8:
                dataInicio = moment().businessAdd(-1);
                dataFim = moment();
                break; 
            case 9:
                dataInicio = null;
                dataFim = moment().businessAdd(1);
                break; 
            case 10:
                dataInicio = null;
                dataFim = moment().add(7, 'days');
                break; 
            case 11:
                dataInicio = null;
                dataFim = moment().add(15, 'days');
                break; 
            case 12:
                dataInicio = null;
                dataFim = moment().add(30, 'days');
                break;
            case 13:
                dataInicio = null;
                dataFim = moment().add(60, 'days');
                break;  
            case 14:
                dataInicio = null;
                dataFim = moment().add(90, 'days');
                break; 
        }; 
        
        return { dataInicio: dataInicio, dataFim: dataFim };
    };

    return {

        periodoOneYear: periodoOneYear,
        dateByPeriodo: dateByPeriodo

    }

});