'use strict';

app.filter('telefone', function() {
    return function(input) {
        if(!input) return input;
        if(input.length != 11) return input;
        if(input.substring(2, 3) == 0) {
            return '(' + input.substring(0, 2) + ') ' + input.substring(3, 7) + '-' + input.substring(7, input.length);
        }  
        return '(' + input.substring(0, 2) + ') ' + input.substring(2, 7) + '-' + input.substring(7, input.length);
    };
});