'use strict';

veiculo.filter('Placa', function() {
    return function(input) {
        if(!input) return input;
        input = input.replace('-', '');
        if(input.length != 7) return input;
        return input.substring(0, 3).toUpperCase() + '-' + input.substring(3, input.length);;
    };
});