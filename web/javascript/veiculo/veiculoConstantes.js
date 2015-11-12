var VeiculoConstantes = function() {
    var app = {};
    
    app.tipo = [{'key': 'motos', 'value': 'Moto'},
                {'key': 'carros', 'value': 'Carro'},
                {'key': 'caminhoes', 'value': 'Caminh\u00E3o'}];

    app.combustivel = [{'key': 'gasolina', 'value': 'Gasolina'},  
                       {'key': 'etanol', 'value': 'Etanol'},
                       {'key': 'disel', 'value': 'Disel'},
                       {'key': 'gas', 'value': 'G\u00E1s Natural Veicular'},
                       {'key': 'flex', 'value': 'Gasolina/\u00C1lcool'}];

    app.status = [{'key': 'novo', 'value': 'Novo'},  
                  {'key': 'seminovo', 'value': 'Seminovo'},  
                  {'key': 'usado', 'value': 'Usado'}];

    app.situacao = [{'key': 'ativo', 'value': 'Ativo'},  
                    {'key': 'inativo', 'value': 'Inativo'},  
                    {'key': 'manutencao', 'value': 'Manuten\u00E7\u00E3o'}];
    
    app.manutencao = [{'key': 'programada', 'value': 'Programada'},
                      {'key': 'rotina', 'value': 'Rotina'},
                      {'key': 'trocaoleo', 'value': 'Troca de \u00D3leo'}];
    
    app.seguro = [{'key': 'parcial', 'value': 'Parcial'},
                  {'key': 'integral', 'value': 'Integral'}];
    
    app.sinistro = [{'key': 'colisao', 'value': 'Colis\u00E3o'},
                    {'key': 'roubo', 'value': 'Roubo'},
                    {'key': 'furto', 'value': 'Furto'},
                    {'key': 'incendio', 'value': 'Inc\u00EAndio'},
                    {'key': 'enchentealagamento', 'value': 'Enchente Alagamento'}];
    
    app.responsavel = [{'key': 'motorista', 'value': 'Motorista'},
                       {'key': 'terceiros', 'value': 'Terceiros'}];
             
             
             
    return app;         
}();