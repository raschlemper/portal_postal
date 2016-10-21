'use strict';

app.filter('numeroCartaoCredito', function() {
    
    return function(input) {
        if(!input) return input;
        var numbers = input.split("");
        var numerocartaoFormat = "XXX.XXXX.XXXX." + input;
//        _.map(numbers, function(number, index) {
//            index += 1;
//            if(index > 12) { numerocartaoFormat += number; } 
//            else { numerocartaoFormat += "X"; }
//            if(index % 4 === 0 && index < 16) { numerocartaoFormat += "."; }
//        });
        return numerocartaoFormat;
    };
});