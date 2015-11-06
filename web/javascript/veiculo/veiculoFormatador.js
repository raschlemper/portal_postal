var VeiculoFormatador = function() {
    var app = {};
    
    app.toNumberUs = function(value) {
        value = numeral().unformat(value);
        return parseInt(value);
    };
    
    app.toNumberBr = function(value) {
        numeral.language("pt-br");
        //value = numeral().unformat(value);
        return numeral(value).format('0,0');
    };
    
    app.toNumericUs = function(value) {
        value = numeral(value).format('0,0.00');
        return Number(value);
    };
    
    app.toNumericBr = function(value) {
        numeral.language("pt-br");
        return numeral(value).format('0,0.00');
    };
       
    app.toDate = function(data) {
        var pattern = /(\d{2})\/(\d{2})\/(\d{4})/;
        return new Date(data.replace(pattern,'$3-$2-$1'));
    }
    
    return app;
}();