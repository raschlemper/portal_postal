'use strict';

app.factory('LancamentoProgramadoService', function($http, PromiseService, FrequenciaLancamentoService, LISTAS) {
    
    var situacoes = LISTAS.situacaoLancamentoProgramado;
    
    var lancamentoProgramado = function(lista, lancamento, dataInicio, dataFim) {
        if(lancamento.situacao.id !== situacoes[0].id) return;
        if(lancamento.quantidadeParcela && lancamento.numeroParcela > lancamento.quantidadeParcela) return;
        var dataVencimento = moment(lancamento.dataVencimento);
        if((dataVencimento.isSame(dataInicio) || dataVencimento.isAfter(dataInicio)) && 
           (dataVencimento.isSame(dataFim) || dataVencimento.isBefore(dataFim))) {
            lista.push(lancamento);  
        }
        if(dataVencimento.isAfter(dataFim)) return; 
        getSaldo(lista, FrequenciaLancamentoService.execute(lancamento), dataInicio, dataFim);        
    }

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
        
        lancamentoProgramado: function(lancamento, dataInicio, dataFim) {
            var lista = [];
            lancamentoProgramado(lista, lancamento, dataInicio, dataFim)
            return lista;
        }

    }

});