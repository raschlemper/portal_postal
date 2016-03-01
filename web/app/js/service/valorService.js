'use strict';

app.factory('ValorService', function() {

    return {
        
        getValueAnoFipe: function(value) {
            if(!_.isNumber(value)) { value = parseInt(value); }
            if(value === 32000) { value = (new Date).getFullYear(); }
            return value;
        }

    }

});