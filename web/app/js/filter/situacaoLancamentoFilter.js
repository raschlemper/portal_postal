'use strict';

app.filter('situacaoLancamento', function(LISTAS) {
    return function(input, events) {
        if(input && input === LISTAS.situacaoLancamento[2].descricao) { 
            return '<div ng-click="' + events + '" style="cursor: pointer;" title="Lançamento Compensado"><i class="fa fa-lg fa-copyright"></i></div>'; 
        }   
        return '<div ng-click="' + events + '" style="color:#CCC; cursor: pointer;" title="Lançamento Compensado"><i class="fa fa-lg fa-copyright"></i></div>';
    };
});