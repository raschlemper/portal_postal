'use strict';

app.constant('LISTAS', {

    "tipo": [{'id': 0, 'codigo': 'motos', 'descricao': 'Moto'},
             {'id': 1, 'codigo': 'carros', 'descricao': 'Carro'},
             {'id': 2, 'codigo': 'caminhoes', 'descricao': 'Caminh\u00E3o'}],

    "combustivel": [{'id': 0, 'codigo': 'gasolina', 'descricao': 'Gasolina'},  
                    {'id': 1, 'codigo': 'etanol', 'descricao': 'Etanol'},
                    {'id': 2, 'codigo': 'diesel', 'descricao': 'Diesel'},
                    {'id': 3, 'codigo': 'gas', 'descricao': 'G\u00E1s Natural Veicular'},
                    {'id': 4, 'codigo': 'flex', 'descricao': 'Gasolina/\u00C1lcool'}],

    "status": [{'id': 0, 'codigo': 'novo', 'descricao': 'Novo'},  
               {'id': 1, 'codigo': 'seminovo', 'descricao': 'Seminovo'},  
               {'id': 2, 'codigo': 'usado', 'descricao': 'Usado'}],

    "situacao": [{'id': 0, 'codigo': 'ativo', 'descricao': 'Ativo'},  
                 {'id': 1, 'codigo': 'inativo', 'descricao': 'Inativo'},  
                 {'id': 2, 'codigo': 'manutencao', 'descricao': 'Manuten\u00E7\u00E3o'}],
     
    "manutencao": [{'id': 0, 'codigo': 'programada', 'value': 'Programada'},
                   {'id': 1, 'codigo': 'rotina', 'value': 'Rotina'},
                   {'id': 2, 'codigo': 'trocaoleo', 'value': 'Troca de \u00D3leo'}]

});