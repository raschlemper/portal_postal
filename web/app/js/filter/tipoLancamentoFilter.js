'use strict';

app.filter('tipoLancamento', function($sce, LISTAS) {
    return function(input) {
        if(!input) return input;
        if(input.id === LISTAS.lancamento[0].id) { return '<span><i class="fa fa-lg fa-plus-circle"></i></span>'; }
        if(input.id === LISTAS.lancamento[1].id) { return '<span><i class="fa fa-lg fa-minus-circle"></i></span>'; }
    };
});