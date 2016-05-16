'use strict';

app.filter('conciliadoLancamento', function() {
    return function(input) {
        if(!input) return input;
        return '<span title="Lançamento Reconciliado"><i class="fa fa-lg fa-registered"></i></span>';       
    };
});