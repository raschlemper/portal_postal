'use strict';

app.filter('renavam', function($filter) {
    return function(input) {
        if(!input) return input;
        input = input.replace('-', '');
        if(input.length !== 11) {
            var zeros = 11 - input.length;
            for(var i=0; i<zeros; i++) { input =+ '0' + input; }            
        }
        return $filter('number')(input.substring(0, 10)) + '-' + input.substring(10, input.length);;
    };
});