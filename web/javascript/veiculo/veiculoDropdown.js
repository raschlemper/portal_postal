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
    
    var optionVeiculo = function(veiculo) {
        var option = document.createElement("option");
        option.text = veiculo.marca + ' / ' + veiculo.modelo + ' (' + veiculo.placa + ')';
        option.value = veiculo.id;
        return option;
    };
    
    app.marcasVeiculo = function(select, marcas) {
        removeOptions(select);
        _.map(marcas, function(marca) {
            var option = document.createElement("option");
            option.text = marca.name;
            option.value = JSON.stringify(marca);
            select.add(option);
        });
    };
    
    app.modelosVeiculo = function(select, modelos) {
        removeOptions(select);
        _.map(modelos, function(modelo) {
            var option = document.createElement("option");
            option.text = modelo.name;
            option.value = JSON.stringify(modelo);
            select.add(option);
        });
    };
    
    app.tiposVeiculo = function(select) {
        dropdownDefault(select, VeiculoConstantes.tipo);
    }; 
    
    app.tiposCombustivel = function(select) {
        dropdownDefault(select, VeiculoConstantes.combustivel);
    }; 
    
    app.tiposStatus = function(select) {
        dropdownDefault(select, VeiculoConstantes.status);
    };  
    
    app.tiposSituacao = function(select) {
        dropdownDefault(select, VeiculoConstantes.situacao);
    };  
    
    app.tiposManutencao = function(select) {
        dropdownDefault(select, VeiculoConstantes.manutencao);
    };  
    
    app.tiposSeguro = function(select) {
        dropdownDefault(select, VeiculoConstantes.seguro);
    };   
    
    app.tiposSinistro = function(select) {
        dropdownDefault(select, VeiculoConstantes.sinistro);
    }; 
    
    app.tiposResponsavel = function(select) {
        dropdownDefault(select, VeiculoConstantes.responsavel);
    }; 
    
    var dropdownDefault = function(select, values) {
        removeOptions(select);
        _.map(values, function(value) {
            var option = document.createElement("option");
            option.text = value.value;
            option.value = value.key;
            select.add(option);
        });        
    }
    
    var removeOptions = function(select) {
        while(select.options.length > 0) { select.remove(select.options.length - 1); }        
    };
    
    return app;
}();