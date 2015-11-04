var VeiculoConstantes = function() {
    var app = {};
    
    app.tipos = [{'key': 'motos', 'value': 'Moto'},
                 {'key': 'carros', 'value': 'Carro'},
                 {'key': 'caminhoes', 'value': 'Caminhão'}];

    app.combustiveis = [{'key': 'gasolina', 'value': 'Gasolina'},  
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
    
    app.tiposManutencao = [{'key': 'programada', 'value': 'Programada'},
                           {'key': 'rotina', 'value': 'Rotina'},
                           {'key': 'trocaoleo', 'value': 'Troca de Óleo'}];
    
    app.tiposCombustivel = [{'key': 'alcool', 'value': 'Álcool'},
                            {'key': 'gasolina', 'value': 'Gasolina'},
                            {'key': 'flex', 'value': 'Flex'}];
             
             
             
    return app;         
}();