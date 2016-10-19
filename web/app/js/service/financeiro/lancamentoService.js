'use strict';

app.factory('LancamentoService', function($http, PromiseService) {
    
    var report = function(lancamentos) {
        var dados = [];
        _.map(lancamentos, function(lancamento) { 
            var report = {}; 
            if(lancamento.numeroLoteConciliado) { report.reconciliado = true; }
            else { report.reconciliado = false; }
            report.tipo = lancamento.tipo.id;
            report.data = moment(lancamento.dataLancamento);
            report.numero = lancamento.numero;
            report.favorecido = lancamento.favorecido;
            report.historico = lancamento.historico;
            report.deposito = lancamento.deposito;
            report.pagamento = lancamento.pagamento;
            report.saldo = lancamento.saldo;
            dados.push(report);
        });
        return dados;
    };
    
    var lancamentoFromRateio = function(lancamento) {
        var rateios = [];
        _.map(lancamento.rateios, function(rateio) {
            var lancamentoRateio = angular.copy(lancamento);
            lancamentoRateio.planoConta = rateio.planoConta;
            lancamentoRateio.centroCusto = rateio.centroCusto;
            lancamentoRateio.valor = rateio.valor;
            lancamentoRateio.observacao = rateio.observacao;
            rateios.push(lancamentoRateio);
        });
        return rateios;
    }

    return {

        getAll: function(dataInicio, dataFim) {
            var url = _contextPath + "/api/financeiro/lancamento/";
            if(dataInicio && dataFim) { url += "?dataInicio=" + dataInicio + "&dataFim=" + dataFim; }
            return PromiseService.execute($http.get(url));
        },

        getSaldo: function(dataInicio, dataFim) {
            return PromiseService.execute(
                    $http.get(_contextPath + "/api/financeiro/lancamento/saldo?dataInicio=" + dataInicio + "&dataFim=" + dataFim));
        },

        getSaldoByConta: function(idConta, dataInicio, dataFim) {
            var url = _contextPath + "/api/financeiro/lancamento/conta/" + idConta + "/saldo?";
            if(dataInicio) url += "dataInicio=" + dataInicio;
            if(dataInicio && dataFim) url += "&";
            if(dataFim) url += "dataFim=" + dataFim;
            return PromiseService.execute($http.get(url));
        },

        getSaldoPlanoConta: function(dataInicio, dataFim) {
            return PromiseService.execute(
                    $http.get(_contextPath + "/api/financeiro/lancamento/planoconta/saldo?dataInicio=" + dataInicio + "&dataFim=" + dataFim));
        },

        getSaldoTipoPlanoConta: function(dataInicio, dataFim, tipo) {
            return PromiseService.execute(
                    $http.get(_contextPath + "/api/financeiro/lancamento/planoconta/tipo/" + tipo + "/saldo?dataInicio=" + dataInicio + "&dataFim=" + dataFim));
        },

        getSaldoCentroCusto: function(dataInicio, dataFim) {
            return PromiseService.execute(
                    $http.get(_contextPath + "/api/financeiro/lancamento/centrocusto/saldo?dataInicio=" + dataInicio + "&dataFim=" + dataFim));
        },

        getSaldoPlanoContaCompetencia: function(dataInicio, dataFim) {
            return PromiseService.execute(
                    $http.get(_contextPath + "/api/financeiro/lancamento/planoconta/saldo/competencia?dataInicio=" + dataInicio + "&dataFim=" + dataFim));
        },

        getSaldoTipoPlanoContaCompetencia: function(dataInicio, dataFim, tipo) {
            return PromiseService.execute(
                    $http.get(_contextPath + "/api/financeiro/lancamento/planoconta/tipo/" + tipo + "/saldo/competencia?dataInicio=" + dataInicio + "&dataFim=" + dataFim));
        },

        getSaldoCentroCustoCompetencia: function(dataInicio, dataFim) {
            return PromiseService.execute(
                    $http.get(_contextPath + "/api/financeiro/lancamento/centrocusto/saldo/competencia?dataInicio=" + dataInicio + "&dataFim=" + dataFim));
        },

        getSaldoTipo: function(dataInicio, dataFim) {
            return PromiseService.execute(
                    $http.get(_contextPath + "/api/financeiro/lancamento/tipo/saldo?dataInicio=" + dataInicio + "&dataFim=" + dataFim));
        },

        findYearFromLancamento: function() {
            return PromiseService.execute(
                    $http.get(_contextPath + "/api/financeiro/lancamento/anos"));
        },

        get: function(idLancamento) {
            return PromiseService.execute(
                    $http.get(_contextPath + "/api/financeiro/lancamento/" + idLancamento));
        },

        save: function(data) {
            return PromiseService.execute(
                    $http.post(_contextPath + "/api/financeiro/lancamento/", data));
        },

        update: function(idLancamento, data) {
            return PromiseService.execute(
                    $http.put(_contextPath + "/api/financeiro/lancamento/" + idLancamento, data));
        },

        updateSituacao: function(data) {
            return PromiseService.execute(
                    $http.put(_contextPath + "/api/financeiro/lancamento/situacao", data));
        },

        updateAll: function(data) {
            return PromiseService.execute(
                    $http.put(_contextPath + "/api/financeiro/lancamento/", data));
        },

        delete: function(idLancamento) {
            return PromiseService.execute(
                    $http.delete(_contextPath + "/api/financeiro/lancamento/" + idLancamento));
        },

        deleteAll: function(data) {
            return PromiseService.execute(
                    $http.post(_contextPath + "/api/financeiro/lancamento/delete", data));
        },
        
        lancamentoFromRateio: function(lancamento) {
            return lancamentoFromRateio(lancamento);
        },
        
        lancamentosFromRateio: function(lancamentos) {
            var rateios = [];
            _.union(rateios, _.map(lancamentos, function(lancamento) {
                return lancamentoFromRateio(lancamento);
            }));
            return rateios;
        },
        
        report: report

    }

});