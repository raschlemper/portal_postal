'use strict';

app.filter('tipoLancamento', function(LISTAS) {
    
    var tipos = LISTAS.lancamento;
    var modelos = LISTAS.modeloLancamento;
    
    return function(input, item) {
        if(!input) return input;
        
        if(input.modelo.id === modelos[1].id || input.modelo.id === modelos[3].id) {
            return '<span title="Lançamento Transferência" class="col-transferencia"><i class="fa fa-exchange"></i></span>'; 
        }         
        if(input.id === tipos[0].id) { 
            return '<span title="Lançamento Receita"><i class="fa fa-lg fa-plus-circle"></i></span>'; 
        }
        if(input.id === tipos[1].id) { 
            return '<span title="Lançamento Despesa"><i class="fa fa-lg fa-minus-circle"></i></span>'; 
        }
    };
});