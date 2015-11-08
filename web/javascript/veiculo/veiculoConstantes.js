var VeiculoConstantes = function() {
    var app = {};
    
    app.tipo = [{'key': 'motos', 'value': 'Moto'},
                {'key': 'carros', 'value': 'Carro'},
                {'key': 'caminhoes', 'value': 'Caminhão'}];

    app.combustivel = [{'key': 'gasolina', 'value': 'Gasolina'},  
                       {'key': 'etanol', 'value': 'Etanol'},
                       {'key': 'disel', 'value': 'Disel'},
                       {'key': 'gas', 'value': 'Gás Natural Veicular'},
                       {'key': 'flex', 'value': 'Gasolina/Álcool'}];

    app.status = [{'key': 'novo', 'value': 'Novo'},  
                  {'key': 'seminovo', 'value': 'Seminovo'},  
                  {'key': 'usado', 'value': 'Usado'}];

    app.situacao = [{'key': 'ativo', 'value': 'Ativo'},  
                    {'key': 'inativo', 'value': 'Inativo'},  
                    {'key': 'manutencao', 'value': 'Manutenção'}];
    
    app.manutencao = [{'key': 'programada', 'value': 'Programada'},
                      {'key': 'rotina', 'value': 'Rotina'},
                      {'key': 'trocaoleo', 'value': 'Troca de Óleo'}];
    
    app.seguro = [{'key': 'parcial', 'value': 'Parcial'},
                  {'key': 'integral', 'value': 'Integral'}];
    
    app.sinistro = [{'key': 'colisao', 'value': 'Colisão'},
                    {'key': 'roubo', 'value': 'Roubo'},
                    {'key': 'furto', 'value': 'Furto'},
                    {'key': 'incendio', 'value': 'Incêndio'},
                    {'key': 'enchentealagamento', 'value': 'Enchente Alagamento'}];
    
    app.responsavel = [{'key': 'motorista', 'value': 'Motorista'},
                       {'key': 'terceiros', 'value': 'Terceiros'}];
             
             
             
    return app;         
}();