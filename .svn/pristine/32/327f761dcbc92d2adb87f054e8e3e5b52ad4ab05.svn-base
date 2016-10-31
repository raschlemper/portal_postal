'use strict';

app.filter('anexo', function(LISTAS) {
    
    var modelos = LISTAS.modeloLancamento;
    
    return function(input, events) {
        if(!input) return input;
        if(input.modelo.id === modelos[1].id || input.modelo.id === modelos[3].id) return "";
        if(input.exists) { 
            return '<div ng-click="' + events + '" style="cursor: pointer;" title="Anexos do Lançamento"><i class="fa fa-lg fa-paperclip"></i></div>'; 
        }   
        return '<div ng-click="' + events + '" style="color:#CCC; cursor: pointer;" title="Anexos do Lançamento"><i class="fa fa-lg fa-paperclip"></i></div>';
    };
});