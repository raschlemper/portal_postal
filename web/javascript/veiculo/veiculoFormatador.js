var VeiculoFormatador = function() {
    var app = {};
    
    app.toNumber = function(value) {
        value.toString().replace(/(.)(?=(\d{3})+$)/g,'$1.');
    };
    
    app.toNumeric = function(value) {
        value.toString().replace(/(.)(?=(\d{3})+$)/g,'$1.');
    };
    
    return app;
}();