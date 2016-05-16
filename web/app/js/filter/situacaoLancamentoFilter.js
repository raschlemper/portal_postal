'use strict';

app.filter('situacaoLancamento', function(LISTAS) {
    return function(input) {
        if(!input) return input;
        if(input === LISTAS.situacaoLancamento[2].descricao) { 
            return '<span title="Lançamento Compensado"><i class="fa fa-lg fa-copyright"></i></span>'; 
        }        
    };
});