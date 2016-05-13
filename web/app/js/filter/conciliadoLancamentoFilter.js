'use strict';

app.filter('conciliadoLancamento', function(LISTAS) {
    return function(input) {
        if(!input) return input;
        return '<span><i class="fa fa-lg fa-registered"></i></span>';       
    };
});