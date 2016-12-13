'use strict';

app.factory('LancamentoProgramadoService', function($http, PromiseService, FrequenciaLancamentoService, LISTAS) {
    
    var situacoes = LISTAS.situacaoLancamentoProgramado;
    var periodos = angular.copy(LISTAS.periodo);
    
    var report = function(lancamentos) {
        var dados = [];
        _.map(lancamentos, function(lancamento) { 
            var report = {}; 
            report.tipo = lancamento.tipo.id;
            report.modelo = lancamento.tipo.modelo.id;
            report.data = moment(lancamento.dataVencimento);          
            report.conta = lancamento.conta.nome;
            report.numero = lancamento.numero;
            report.favorecido = lancamento.favorecido;
            report.planoConta = lancamento.planoConta.descricao;
            report.historico = lancamento.historico;
            report.frequencia = lancamento.frequencia.descricao;
            report.deposito = lancamento.deposito;
            report.pagamento = lancamento.pagamento;
            dados.push(report);
        });
        return dados;
    };
    
    var lancamentoProgramado = function(lista, lancamento, dataInicio, dataFim) {
        if(!isValid(lancamento)) return;
        var dataVencimento = moment(lancamento.dataVencimento);
        if((dataVencimento.isSame(dataInicio) || dataVencimento.isAfter(dataInicio)) && 
           (dataVencimento.isSame(dataFim) || dataVencimento.isBefore(dataFim)) && !lancamento.baixado) {
            lista.push(angular.copy(lancamento));  
        }
        if(dataVencimento.isAfter(dataFim)) return; 
        lancamentoProgramado(lista, getNextLancamento(lancamento), dataInicio, dataFim);        
    }
    
    var lancamentoProgramadoVencido = function(lista, lancamento, data) {
        if(!isValid(lancamento)) return;
        var dataVencimento = moment(lancamento.dataVencimento);
        if(dataVencimento.isBefore(data) && !lancamento.baixado) { 
            lista.push(angular.copy(lancamento)); 
        } 
        if(dataVencimento.isAfter(data)) return;
        lancamentoProgramadoVencido(lista, getNextLancamento(lancamento), data);        
    }
    
    var isValid = function(lancamento) {      
        if(!lancamento) return false;
        if(lancamento.situacao.id !== situacoes[0].id) return false;
        if(lancamento.quantidadeParcela && lancamento.numeroParcela > lancamento.quantidadeParcela) return false;
        return true;
    }
    
    var getFirstLancamento = function(lancamento) {
        if(!lancamento.parcelas || !lancamento.parcelas.length) { return lancamento; }
        return setLancamento(lancamento, 1);
    }
    
    var getNextLancamento = function(lancamento) {
        if(!lancamento.parcelas || !lancamento.parcelas.length) {
            return FrequenciaLancamentoService.execute(lancamento);
        }
        return setLancamento(lancamento, lancamento.numeroParcela + 1);
    }
    
    var setLancamento = function(lancamento, numeroParcela) {  
        var parcelaLancamento;
        lancamento.parcelas.map(function(parcela) {
            if(parcela.numero === numeroParcela) { parcelaLancamento = parcela; }
        });      
        if(!parcelaLancamento) return null;
        lancamento.numeroParcela = parcelaLancamento.numero;
        lancamento.dataVencimento = parcelaLancamento.dataVencimento;
        lancamento.baixado = false;
        if(parcelaLancamento && parcelaLancamento.lancamento) { lancamento.baixado = true; }
        return lancamento;
    };
        
    var getFirstParcelaAberta = function(lancamentoProgramado) {
        var numeroParcela = 0
        if(lancamentoProgramado.lancamentos && lancamentoProgramado.lancamentos.length){
            numeroParcela = lancamentoProgramado.lancamentos[0].numeroParcela;
        };
        var parcelas = lancamentoProgramado.parcelas || [];
        return _.find(parcelas, function(parcela) { 
            return !parcela.lancamento && numeroParcela !== parcela.numero; 
        });
    };
    
    var encerrarLancamentoProgramado = function(lancamentoProgramado, lancamento) {
        if(lancamentoProgramado.frequencia === 0) { // Único
            lancamentoProgramado.situacao = situacoes[2].id;
        }
        if(lancamento.numeroParcela === lancamentoProgramado.quantidadeParcela) {
            lancamentoProgramado.situacao = situacoes[2].id;                
        }            
    };
    
    var getPeriodos = function() {
        var periodosCustom = [];
        periodos.map(function(periodo) {
            if(periodo.id === 0)  { periodosCustom.push(periodo); }
            if(periodo.id === 1)  { periodosCustom.push(periodo); }
            if(periodo.id === 9)  { periodosCustom.push(periodo); }
            if(periodo.id === 10) { periodosCustom.push(periodo); }
            if(periodo.id === 11) { periodosCustom.push(periodo); }
            if(periodo.id === 12) { periodosCustom.push(periodo); }
            if(periodo.id === 13) { periodosCustom.push(periodo); }
            if(periodo.id === 14) { periodosCustom.push(periodo); }    
        });
        return periodosCustom;
    };
            
    var ajustarLancamentoProgramadoFrequencia = function(lancamentoProgramado) {             
        if((lancamentoProgramado.parcelas && lancamentoProgramado.parcelas.length)) {
            lancamentoProgramado.dataCompetencia = FrequenciaLancamentoService.addData(lancamentoProgramado.frequencia, lancamentoProgramado.dataCompetencia) || moment();
            lancamentoProgramado.dataVencimento = FrequenciaLancamentoService.addData(lancamentoProgramado.frequencia, lancamentoProgramado.dataVencimento) || moment();
            var parcela = getFirstParcelaAberta(lancamentoProgramado);
            if(parcela) { lancamentoProgramado.dataVencimento = parcela.dataVencimento || moment(); } 
        } else {
            lancamentoProgramado.dataCompetencia = FrequenciaLancamentoService.addData(lancamentoProgramado.frequencia, lancamentoProgramado.dataCompetencia) || moment();
            lancamentoProgramado.dataVencimento = FrequenciaLancamentoService.addData(lancamentoProgramado.frequencia, lancamentoProgramado.dataVencimento) || moment();
        }
        return lancamentoProgramado;
    };
    
//    var setNumeroParcelaLancamento = function(lancamento) {
//        if(lancamento.parcelas && lancamento.parcelas.length) {
//            lancamento.numeroParcela = 0;            
//        }        
//    }

    return {

        getAll: function(dataInicio, dataFim) {
            var url = _contextPath + "/api/financeiro/lancamento/programado/?";
            if(dataInicio) url += "dataInicio=" + dataInicio;
            if(dataInicio && dataFim) url += "&";
            if(dataFim) url += "dataFim=" + dataFim;
            return PromiseService.execute($http.get(url));
        },

        getAllById: function(ids) {
            return PromiseService.execute(
                    $http.post(_contextPath + "/api/financeiro/lancamento/programado/id", ids));
        },

        getAllAtivo: function(dataInicio, dataFim) {
            var url = _contextPath + "/api/financeiro/lancamento/programado/ativo?";
            if(dataInicio) url += "dataInicio=" + dataInicio;
            if(dataInicio && dataFim) url += "&";
            if(dataFim) url += "dataFim=" + dataFim;
            return PromiseService.execute($http.get(url));
        },

        get: function(idLancamentoProgramado) {
            return PromiseService.execute(
                    $http.get(_contextPath + "/api/financeiro/lancamento/programado/" + idLancamentoProgramado));
        },

        getLancamento: function(idLancamentoProgramado) {
            return PromiseService.execute(
                    $http.get(_contextPath + "/api/financeiro/lancamento/programado/" + idLancamentoProgramado + "/lancamento"));
        },

        getLast: function(idLancamentoProgramado) {
            return PromiseService.execute(
                    $http.get(_contextPath + "/api/financeiro/lancamento/programado/" + idLancamentoProgramado + '/last'));
        },

        getByNumeroParcela: function(idLancamentoProgramado, numeroParcela) {
            return PromiseService.execute(
                    $http.get(_contextPath + "/api/financeiro/lancamento/programado/" + idLancamentoProgramado + '/parcela/' + numeroParcela));
        },

        save: function(data) {
            return PromiseService.execute(
                    $http.post(_contextPath + "/api/financeiro/lancamento/programado/", data));
        },

        update: function(idLancamentoProgramado, data) {
            return PromiseService.execute(
                    $http.put(_contextPath + "/api/financeiro/lancamento/programado/" + idLancamentoProgramado, data));
        },

        updateConta: function(data) {
            return PromiseService.execute(
                    $http.put(_contextPath + "/api/financeiro/lancamento/programado/conta", data));
        },

        updateSituacao: function(data) {
            return PromiseService.execute(
                    $http.put(_contextPath + "/api/financeiro/lancamento/programado/situacao", data));
        },

        updateAll: function(data) {
            return PromiseService.execute(
                    $http.put(_contextPath + "/api/financeiro/lancamento/programado/", data));
        },

        create: function(data) {
            return PromiseService.execute(
                    $http.post(_contextPath + "/api/financeiro/lancamento/programado/create", data));
        },

        createAll: function(data) {
            return PromiseService.execute(
                    $http.post(_contextPath + "/api/financeiro/lancamento/programado/create/all", data));
        },

        delete: function(idLancamentoProgramado) {
            return PromiseService.execute(
                    $http.delete(_contextPath + "/api/financeiro/lancamento/programado/" + idLancamentoProgramado));
        },

        deleteAll: function(data) {
            return PromiseService.execute(
                    $http.post(_contextPath + "/api/financeiro/lancamento/programado/delete", data));
        },
        
        lancamentoProgramado: function(lancamento, dataInicio, dataFim) {
            var lista = [];
//            setNumeroParcelaLancamento(lancamento);
            lancamento = getFirstLancamento(lancamento);
            lancamentoProgramado(lista, lancamento, dataInicio, dataFim)
            return lista;
        },
        
        lancamentoProgramadoVencido: function(lancamento, data) {
            var lista = [];
//            setNumeroParcelaLancamento(lancamento);
            lancamento = getFirstLancamento(lancamento);
            lancamentoProgramadoVencido(lista, lancamento, data)
            return lista;
        },
        
        getFirstParcelaAberta: getFirstParcelaAberta,
        
        encerrarLancamentoProgramado: encerrarLancamentoProgramado,
        
        ajustarLancamentoProgramadoFrequencia: ajustarLancamentoProgramadoFrequencia,
        
        report: report,
        
        periodos: getPeriodos()

    }

});