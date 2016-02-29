'use strict';

app.constant('LISTAS', {
    
    "size": [{'key': 10, 'value': '10'},
             {'key': 20, 'value': '20'},
             {'key': 50, 'value': '50'},
             {'key': 100, 'value': '100'},
             {'key': -1, 'value': 'Todos'}],

    "tipo": [{'key': 'motos', 'value': 'Moto'},
             {'key': 'carros', 'value': 'Carro'},
             {'key': 'caminhoes', 'value': 'Caminh\u00E3o'}],

    "combustivel": [{'key': 'gasolina', 'value': 'Gasolina'},  
                    {'key': 'etanol', 'value': 'Etanol'},
                    {'key': 'disel', 'value': 'Disel'},
                    {'key': 'gas', 'value': 'G\u00E1s Natural Veicular'},
                    {'key': 'flex', 'value': 'Gasolina/\u00C1lcool'}],

    "status": [{'key': 'novo', 'value': 'Novo'},  
               {'key': 'seminovo', 'value': 'Seminovo'},  
               {'key': 'usado', 'value': 'Usado'}],

    "situacao": [{'key': '0', 'value': 'Ativo'},  
                 {'key': '1', 'value': 'Inativo'},  
                 {'key': '2', 'value': 'Manuten\u00E7\u00E3o'}]

});