'use strict';

app.filter('tipoLancamento', function($sce, LISTAS) {
    return function(input) {
        if(!input) return input;
        if(input === LISTAS.lancamento[0].descricao) { return $sce.trustAsHtml('<div>R</div>'); }
        if(input === LISTAS.lancamento[1].descricao) { return 'D'; }
    };
});