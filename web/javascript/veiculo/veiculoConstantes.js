var VeiculoConstantes = function() {
    var app = {};
    
    app.tipo = [{'key': 'motos', 'value': 'Moto'},
                {'key': 'carros', 'value': 'Carro'},
<<<<<<< HEAD
                {'key': 'caminhoes', 'value': 'Caminh\u00E3o'}];
=======
                {'key': 'caminhoes', 'value': 'Caminhão'}];
>>>>>>> 175902c9d28a837edc35f1159eb3fc0bbaaa09ae

    app.combustivel = [{'key': 'gasolina', 'value': 'Gasolina'},  
                       {'key': 'etanol', 'value': 'Etanol'},
                       {'key': 'disel', 'value': 'Disel'},
<<<<<<< HEAD
                       {'key': 'gas', 'value': 'G\u00E1s Natural Veicular'},
                       {'key': 'flex', 'value': 'Gasolina/\u00C1lcool'}];
=======
                       {'key': 'gas', 'value': 'Gás Natural Veicular'},
                       {'key': 'flex', 'value': 'Gasolina/Álcool'}];
>>>>>>> 175902c9d28a837edc35f1159eb3fc0bbaaa09ae

    app.status = [{'key': 'novo', 'value': 'Novo'},  
                  {'key': 'seminovo', 'value': 'Seminovo'},  
                  {'key': 'usado', 'value': 'Usado'}];

    app.situacao = [{'key': 'ativo', 'value': 'Ativo'},  
                    {'key': 'inativo', 'value': 'Inativo'},  
<<<<<<< HEAD
                    {'key': 'manutencao', 'value': 'Manuten\u00E7\u00E3o'}];
    
    app.manutencao = [{'key': 'programada', 'value': 'Programada'},
                      {'key': 'rotina', 'value': 'Rotina'},
                      {'key': 'trocaoleo', 'value': 'Troca de \u00D3leo'}];
=======
                    {'key': 'manutencao', 'value': 'Manutenção'}];
    
    app.manutencao = [{'key': 'programada', 'value': 'Programada'},
                      {'key': 'rotina', 'value': 'Rotina'},
                      {'key': 'trocaoleo', 'value': 'Troca de Óleo'}];
>>>>>>> 175902c9d28a837edc35f1159eb3fc0bbaaa09ae
    
    app.seguro = [{'key': 'parcial', 'value': 'Parcial'},
                  {'key': 'integral', 'value': 'Integral'}];
    
<<<<<<< HEAD
    app.sinistro = [{'key': 'colisao', 'value': 'Colis\u00E3o'},
=======
    app.sinistro = [{'key': 'colisao', 'value': 'Colisão'},
>>>>>>> 175902c9d28a837edc35f1159eb3fc0bbaaa09ae
                    {'key': 'roubo', 'value': 'Roubo'},
                    {'key': 'furto', 'value': 'Furto'},
                    {'key': 'incendio', 'value': 'Inc\u00EAndio'},
                    {'key': 'enchentealagamento', 'value': 'Enchente Alagamento'}];
    
    app.responsavel = [{'key': 'motorista', 'value': 'Motorista'},
                       {'key': 'terceiros', 'value': 'Terceiros'}];
             
    app.getValue = function(list, key) {
        var value = _.find(list, function(item){ return item.key == key; });
        if(!value) { value = {'key': 'nenhum', 'value': ''} }
        return value;
    }
             
    return app;         
}();