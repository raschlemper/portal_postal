'use strict';

app.filter('favorecido', function() {
    
    return function(input) {
        if(!input) return input;
        if(!input.tipo) return input;
        var result = '';
        if(input.tipo.codigo === 'colaborador') {
            result += '<i class="fa fa-spc fa-user"></i>'; 
        } else if(input.tipo.codigo === 'fornecedor') {
            result += '<i class="fa fa-spc fa-truck"></i>'; 
        } else if(input.tipo.codigo === 'cliente') {
            result += '<i class="fa fa-spc fa-bank"></i>'; 
        }    
        return '<span>' + input.nome + '</span>'; 
    };
});