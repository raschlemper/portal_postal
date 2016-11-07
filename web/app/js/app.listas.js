'use strict';

app.constant('LISTAS', {

    "combustivel": [{'id': 0, 'codigo': 'gasolina', 'descricao': 'Gasolina'},  
                    {'id': 1, 'codigo': 'etanol', 'descricao': 'Etanol/Álcool'},
                    {'id': 2, 'codigo': 'diesel', 'descricao': 'Diesel'},
                    {'id': 3, 'codigo': 'gas', 'descricao': 'Gás Natural Veicular'},
                    {'id': 4, 'codigo': 'flex', 'descricao': 'Gasolina/Álcool'}],
                
    "estadoCivil": [{'id': 0, 'codigo': 'solteiro', 'descricao': 'Solteiro'},
                    {'id': 1, 'codigo': 'casado', 'descricao': 'Casado'},
                    {'id': 2, 'codigo': 'separado', 'descricao': 'Separado'},
                    {'id': 3, 'codigo': 'divorciado', 'descricao': 'Divorciado'},
                    {'id': 4, 'codigo': 'viuvo', 'descricao': 'Viúvo'}],
    
    "frequencia": [{'id': 0, 'codigo': 'unico', 'descricao': 'Único'},  
                   {'id': 1, 'codigo': 'diario', 'descricao': 'Diário'},  
                   {'id': 2, 'codigo': 'semanal', 'descricao': 'Semanal'},  
                   {'id': 3, 'codigo': 'quinzenal', 'descricao': 'Quinzenal'},  
                   {'id': 4, 'codigo': 'mensal', 'descricao': 'Mensal'},  
                   {'id': 5, 'codigo': 'bimestral', 'descricao': 'Bimestral'},  
                   {'id': 6, 'codigo': 'trimestral', 'descricao': 'Trimestral'},  
                   {'id': 7, 'codigo': 'quadrimestral', 'descricao': 'Quadrimestral'},  
                   {'id': 8, 'codigo': 'semestral', 'descricao': 'Semestral'},  
                   {'id': 9, 'codigo': 'anual', 'descricao': 'Anual'}],
                
    "lancamento": [{'id': 0, 'codigo': 'receita', 'descricao': 'Receita'},
                   {'id': 1, 'codigo': 'despesa', 'descricao': 'Despesa'}],
     
    "manutencao": [{'id': 0, 'codigo': 'programada', 'descricao': 'Programada'},
                   {'id': 1, 'codigo': 'rotina', 'descricao': 'Rotina'},
                   {'id': 2, 'codigo': 'trocaoleo', 'descricao': 'Troca de Óleo'}],
    
    "meses": [{'id': 0, 'codigo': 'janeiro', 'descricao': 'Janeiro', abreviacao: 'Jan', order: 1},  
              {'id': 1, 'codigo': 'fevereiro', 'descricao': 'Fevereiro', abreviacao: 'Fev', order: 2},  
              {'id': 2, 'codigo': 'marco', 'descricao': 'Março', abreviacao: 'Mar', order: 3},  
              {'id': 3, 'codigo': 'abril', 'descricao': 'Abril', abreviacao: 'Abr', order: 4},  
              {'id': 4, 'codigo': 'maio', 'descricao': 'Maio', abreviacao: 'Mai', order: 5},  
              {'id': 5, 'codigo': 'junho', 'descricao': 'Junho', abreviacao: 'Jun', order: 6},  
              {'id': 6, 'codigo': 'julho', 'descricao': 'Julho', abreviacao: 'Jul', order: 7},  
              {'id': 7, 'codigo': 'agosto', 'descricao': 'Agosto', abreviacao: 'Ago', order: 8},  
              {'id': 8, 'codigo': 'setembro', 'descricao': 'Setembro', abreviacao: 'Set', order: 9},  
              {'id': 9, 'codigo': 'outubro', 'descricao': 'Outubro', abreviacao: 'Out', order: 10},  
              {'id': 10, 'codigo': 'novembro', 'descricao': 'Novembro', abreviacao: 'Nov', order: 11},  
              {'id': 11, 'codigo': 'dezembro', 'descricao': 'Dezembro', abreviacao: 'Dez', order: 12}],
                
    "modeloLancamento": [{'id': 0, 'codigo': 'normal', 'descricao': 'Normal'},
                         {'id': 1, 'codigo': 'transferencia', 'descricao': 'Transferência'},
                         {'id': 2, 'codigo': 'programado', 'descricao': 'Programado'},
                         {'id': 3, 'codigo': 'transferenciaprogramado', 'descricao': 'Transferência Programada'},
                         {'id': 4, 'codigo': 'parcelado', 'descricao': 'Parcelado'},
                         {'id': 5, 'codigo': 'reconciliado', 'descricao': 'Reconciliado'},
                         {'id': 6, 'codigo': 'automatico', 'descricao': 'Automático'}],
     
    "periodo": [{'id': 0, 'codigo': 'vencido', 'descricao': 'Vencido'},
                {'id': 1, 'codigo': 'mesatual', 'descricao': 'Mês Vigente'},
                {'id': 2, 'codigo': 'setedias', 'descricao': 'Últimos 7 Dias'},
                {'id': 3, 'codigo': 'quinzedias', 'descricao': 'Últimos 15 Dias'},
                {'id': 4, 'codigo': 'trintadias', 'descricao': 'Últimos 30 Dias'},
                {'id': 5, 'codigo': 'sessentadias', 'descricao': 'Últimos 60 Dias'},
                {'id': 6, 'codigo': 'noventadias', 'descricao': 'Últimos 90 dias'}],
     
    "planoConta": [{'id': 0, 'codigo': 'receita', 'descricao': 'Receita'},
                    {'id': 1, 'codigo': 'despesa', 'descricao': 'Despesa'}],     
     
    "responsavel": [{'id': 0, 'codigo': 'motorista', 'descricao': 'Motorista'},
                    {'id': 1, 'codigo': 'terceiros', 'descricao': 'Terceiros'}],
     
    "seguro": [{'id': 0, 'codigo': 'parcial', 'descricao': 'Parcial'},
               {'id': 1, 'codigo': 'integral', 'descricao': 'Integral'}],
     
    "sexo": [{'id': 0, 'codigo': 'masculino', 'descricao': 'Masculino'},
             {'id': 1, 'codigo': 'feminino', 'descricao': 'Feminino'}],
     
    "sinistro": [{'id': 0, 'codigo': 'colisao', 'descricao': 'Colisão'},
                 {'id': 1, 'codigo': 'roubo', 'descricao': 'Roubo'},
                 {'id': 2, 'codigo': 'furto', 'descricao': 'Furto'},
                 {'id': 3, 'codigo': 'incendio', 'descricao': 'Incêndio'},
                 {'id': 4, 'codigo': 'enchentealagamento', 'descricao': 'Enchente/Algamento'}],

    "situacaoLancamento": [{'id': 0, 'codigo': 'normal', 'descricao': 'Normal'},  
                           {'id': 1, 'codigo': 'naocompensado', 'descricao': 'Não Compensado'},  
                           {'id': 2, 'codigo': 'compensado', 'descricao': 'Compensado'}],

    "situacaoLancamentoProgramado": [{'id': 0, 'codigo': 'ativo', 'descricao': 'Ativo'},  
                                     {'id': 1, 'codigo': 'cancelado', 'descricao': 'Cancelado'},  
                                     {'id': 2, 'codigo': 'encerrado', 'descricao': 'Encerrado'}],

    "situacaoVeiculo": [{'id': 0, 'codigo': 'ativo', 'descricao': 'Ativo'},  
                        {'id': 1, 'codigo': 'inativo', 'descricao': 'Inativo'},  
                        {'id': 2, 'codigo': 'manutencao', 'descricao': 'Manutenção'}],

    "statusConta": [{'id': 0, 'codigo': 'aberto', 'descricao': 'Aberto'},  
                    {'id': 1, 'codigo': 'encerrado', 'descricao': 'Encerrado'}],

    "statusColaborador": [{'id': 0, 'codigo': 'ativo', 'descricao': 'Ativo'},  
                          {'id': 1, 'codigo': 'cancelado', 'descricao': 'Cancelado'}],

    "statusFornecedor": [{'id': 0, 'codigo': 'ativo', 'descricao': 'Ativo'},  
                         {'id': 1, 'codigo': 'cancelado', 'descricao': 'Cancelado'}],

    "statusVeiculo": [{'id': 0, 'codigo': 'novo', 'descricao': 'Novo'},  
                      {'id': 1, 'codigo': 'seminovo', 'descricao': 'Seminovo'},  
                      {'id': 2, 'codigo': 'usado', 'descricao': 'Usado'}],
     
    "tipoConta": [{'id': 0, 'codigo': 'dinheiro', 'descricao': 'Dinheiro'},
                  {'id': 1, 'codigo': 'contacorrente', 'descricao': 'Conta Corrente'},
                  {'id': 2, 'codigo': 'poupanca', 'descricao': 'Poupança'},
                  {'id': 3, 'codigo': 'cobranca', 'descricao': 'Cobrança'},
                  {'id': 4, 'codigo': 'cartaocredito', 'descricao': 'Cartão Crédito'}],

    "tipoPessoa": [{'id': 0, 'codigo': 'fisica', 'descricao': 'Física'},
                   {'id': 1, 'codigo': 'juridica', 'descricao': 'Jurídica'}],

    "tipoVeiculo": [{'id': 0, 'codigo': 'motos', 'descricao': 'Moto'},
                    {'id': 1, 'codigo': 'carros', 'descricao': 'Carro'},
                    {'id': 2, 'codigo': 'caminhoes', 'descricao': 'Caminhão'}]

});
