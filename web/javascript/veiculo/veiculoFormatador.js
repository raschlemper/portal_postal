var VeiculoFormatador = function() {
    var app = {};
    
    app.toNumber = function(value) {
        return value.toString().replace(/(.)(?=(\d{3})+$)/g,'$1.');
    };
    
    app.toNumeric = function(value) {
        return value.toString().replace(/(.)(?=(\d{3})+(\d{2}))/g,'$1.');
    };
       
    app.toDate = function(data) {
        var pattern = /(\d{2})\/(\d{2})\/(\d{4})/;
        return new Date(data.replace(pattern,'$3-$2-$1'));
    }
    
    return app;
}();