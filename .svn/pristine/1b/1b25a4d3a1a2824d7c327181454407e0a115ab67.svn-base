'use strict';

app.filter('planoConta', function() {
    
    return function(input) {
        if(!input) return input;
        
        if(input.ehGrupo) {
            return '<span class="bold">' + input.descricao + '</span>'; 
        }   
        
        return '<span>' + input.descricao + '</span>'; 
    };
});