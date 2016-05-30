'use strict';

app.filter('saldoLancamento', function($filter) {
    return function(input) {
        var valor = $filter('currency')(input, '');
        if(input >= 0) { 
            return '<span class="text-primary"><strong>' + valor + '</strong></span>'; 
        } 
        return '<span class="text-danger"><strong>' + valor + '</strong></span>'; 
    };
});