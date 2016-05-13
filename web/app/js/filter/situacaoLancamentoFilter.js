'use strict';

app.filter('situacaoLancamento', function(LISTAS) {
    return function(input) {
        if(!input) return input;
        if(input.id === LISTAS.situacaoLancamento[2].id) { return '<span><i class="fa fa-lg fa-copyright"></i></span>'; }        
    };
});