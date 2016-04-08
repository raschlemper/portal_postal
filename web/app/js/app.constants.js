'use strict';

app.constant('LISTAS', {

    "tipoVeiculo": [{'id': 0, 'codigo': 'motos', 'descricao': 'Moto'},
                    {'id': 1, 'codigo': 'carros', 'descricao': 'Carro'},
                    {'id': 2, 'codigo': 'caminhoes', 'descricao': 'Caminhão'}],

    "combustivel": [{'id': 0, 'codigo': 'gasolina', 'descricao': 'Gasolina'},  
                    {'id': 1, 'codigo': 'etanol', 'descricao': 'Etanol/Álcool'},
                    {'id': 2, 'codigo': 'diesel', 'descricao': 'Diesel'},
                    {'id': 3, 'codigo': 'gas', 'descricao': 'Gás Natural Veicular'},
                    {'id': 4, 'codigo': 'flex', 'descricao': 'Gasolina/Álcool'}],

    "statusVeiculo": [{'id': 0, 'codigo': 'novo', 'descricao': 'Novo'},  
                      {'id': 1, 'codigo': 'seminovo', 'descricao': 'Seminovo'},  
                      {'id': 2, 'codigo': 'usado', 'descricao': 'Usado'}],

    "situacaoVeiculo": [{'id': 0, 'codigo': 'ativo', 'descricao': 'Ativo'},  
                        {'id': 1, 'codigo': 'inativo', 'descricao': 'Inativo'},  
                        {'id': 2, 'codigo': 'manutencao', 'descricao': 'Manutenção'}],
     
    "manutencao": [{'id': 0, 'codigo': 'programada', 'descricao': 'Programada'},
                   {'id': 1, 'codigo': 'rotina', 'descricao': 'Rotina'},
                   {'id': 2, 'codigo': 'trocaoleo', 'descricao': 'Troca de Óleo'}],
     
    "seguro": [{'id': 0, 'codigo': 'parcial', 'descricao': 'Parcial'},
               {'id': 1, 'codigo': 'integral', 'descricao': 'Integral'}],
     
    "sinistro": [{'id': 0, 'codigo': 'colisao', 'descricao': 'Colisão'},
                 {'id': 1, 'codigo': 'roubo', 'descricao': 'Roubo'},
                 {'id': 2, 'codigo': 'furto', 'descricao': 'Furto'},
                 {'id': 3, 'codigo': 'incendio', 'descricao': 'Incêndio'},
                 {'id': 4, 'codigo': 'enchentealagamento', 'descricao': 'Enchente/Algamento'}],
     
    "responsavel": [{'id': 0, 'codigo': 'motorista', 'descricao': 'Motorista'},
                    {'id': 1, 'codigo': 'terceiros', 'descricao': 'Terceiros'}],
     
    "planoConta": [{'id': 0, 'codigo': 'receita', 'descricao': 'Receita'},
                    {'id': 1, 'codigo': 'despesa', 'descricao': 'Despesa'}],
     
    "tipoConta": [{'id': 0, 'codigo': 'dinheiro', 'descricao': 'Dinheiro'},
                  {'id': 1, 'codigo': 'contacorrente', 'descricao': 'Conta Corrente'},
                  {'id': 2, 'codigo': 'poupanca', 'descricao': 'Poupança'},
                  {'id': 3, 'codigo': 'cobranca', 'descricao': 'Cobrança'},
                  {'id': 4, 'codigo': 'cartaocredito', 'descricao': 'Cartão Crédito'}],

    "statusConta": [{'id': 0, 'codigo': 'aberto', 'descricao': 'Aberto'},  
                    {'id': 1, 'codigo': 'encerrado', 'descricao': 'Encerrado'}],

    "situacao": [{'id': 0, 'codigo': 'ativo', 'descricao': 'Ativo'},  
                 {'id': 1, 'codigo': 'cancelado', 'descricao': 'Cancelado'}],

});