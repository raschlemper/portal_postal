var VeiculoDropdown = function() {
    var app = {};
    
    app.veiculos = function(select, veiculos) {
        removeOptions(select);
        _.map(veiculos, function(veiculo) {
            select.add(optionVeiculo(veiculo));
        });
    };

    app.veiculo = function(select, veiculo) {
        removeOptions(select);
        select.add(optionVeiculo(veiculo));
        
    };
    
    app.tiposCombustivel = function(select, tipos) {
        removeOptions(select);
        _.map(tipos, function(tipo) {
            var option = document.createElement("option");
            option.text = tipo.value;
            option.value = tipo.key;
            select.add(option);
        });
    }; 
    
    var optionVeiculo = function(veiculo) {
        var option = document.createElement("option");
        option.text = veiculo.marca + ' / ' + veiculo.modelo + ' (' + veiculo.placa + ')';
        option.value = veiculo.id;
        return option;
    }
    
    var removeOptions = function(select) {
        while(select.options.length > 0) { select.remove(select.options.length - 1); }        
    };
    
    return app;
}();