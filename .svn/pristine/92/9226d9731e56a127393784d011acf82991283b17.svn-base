'use strict';

app.filter('tipoLancamento', function( LISTAS) {
    return function(input, item) {
        if(!input) return input;
        if(input === LISTAS.lancamento[0].descricao) { 
            return '<span title="Lançamento Receita"><i class="fa fa-lg fa-plus-circle"></i></span>'; 
        }
        if(input === LISTAS.lancamento[1].descricao) { 
            return '<span title="Lançamento Despesa"><i class="fa fa-lg fa-minus-circle"></i></span>'; 
        }
        if(input === LISTAS.modeloLancamento[1].descricao) { 
            return '<span title="Lançamento Transferência"><i class="fa fa-exchange"></i></span>'; 
        }
    };
});