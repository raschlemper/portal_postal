'use strict';

app.factory('LancamentoProgramadoService', function($http, PromiseService, FrequenciaLancamentoService, LISTAS) {
    
    var situacoes = LISTAS.situacaoLancamentoProgramado;
    
    var lancamentoProgramado = function(lista, lancamento, dataInicio, dataFim) {
        if(!isValid(lancamento)) return;
        var dataVencimento = moment(lancamento.dataVencimento);
        if((dataVencimento.isSame(dataInicio) || dataVencimento.isAfter(dataInicio)) && 
           (dataVencimento.isSame(dataFim) || dataVencimento.isBefore(dataFim))) {
            lista.push(angular.copy(lancamento));  
        }
        if(dataVencimento.isAfter(dataFim)) return; 
        lancamentoProgramado(lista, getNextLancamento(lancamento), dataInicio, dataFim);        
    }
    
    var lancamentoProgramadoVencido = function(lista, lancamento, data) {
        if(!isValid(lancamento)) return;
        var dataVencimento = moment(lancamento.dataVencimento);
        if(dataVencimento.isBefore(data)) { 
            lista.push(lancamento); 
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
        return lancamento;
    }
    
//    var setNumeroParcelaLancamento = function(lancamento) {
//        if(lancamento.parcelas && lancamento.parcelas.length) {
//            lancamento.numeroParcela = 0;            
//        }        
//    }

    return {

        getAll: function() {
            return PromiseService.execute(
                    $http.get(_contextPath + "/api/financeiro/lancamento/programado/"));
        },

        getAllAtivo: function(dataInicio, dataFim) {
            return PromiseService.execute(
                    $http.get(_contextPath + "/api/financeiro/lancamento/programado/ativo"));
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

        create: function(data) {
            return PromiseService.execute(
                    $http.post(_contextPath + "/api/financeiro/lancamento/programado/create", data));
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
        }

    }

});