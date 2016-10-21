'use strict';

app.filter('favorecido', function() {
    
    return function(input) {
        if(!input) return input;
        if(!input.tipo) return input;
        var result = '';
        if(input.tipo.codigo === 'colaborador') {
            result += '<i class="fa fa-spc fa-user fa-fw"></i>'; 
        } else if(input.tipo.codigo === 'fornecedor') {
            result += '<i class="fa fa-spc fa-truck fa-fw"></i>'; 
        } else if(input.tipo.codigo === 'cliente') {
            result += '<i class="fa fa-spc fa-bank fa-fw"></i>'; 
        }    
        return '<span>' + result + input.nome + '</span>'; 
    };
});