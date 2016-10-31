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

    return {

        periodoOneYear: periodoOneYear

    }

});