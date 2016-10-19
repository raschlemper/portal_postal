'use strict';

app.factory('FinanceiroValidation', function(ModalService, LISTAS, MESSAGES) {    
    
    var statusConta = LISTAS.statusConta;
        
    var contaEncerrada = function(conta) {
        if(conta.status.id === statusConta[1].id) {
            modalMessage(MESSAGES.conta.info.CONTA_ENCERRADO);
            return false;
        };
        return true;
    }
    
    var planoContaResultado = function(planoContas, planoContaSelected) {
        if(!planoContaSelected || !planoContaSelected.idPlanoConta) return true;
        if(planoContaSelected.ehGrupo) {
            modalMessage(MESSAGES.planoConta.info.NAO_PERMITE_GRUPO);
            return false;
        };
        var result = _.find(planoContas, function(planoConta) { 
            return planoConta.descricao == planoContaSelected.descricao; 
        });
        if(!result) {
            modalMessage(MESSAGES.planoConta.info.PLANO_CONTA_NAO_CADASTRADO);
            return false;
        };
        return true;
    };

    var centroCustoResultado = function(centroCustos, centroCustoSelected) {
        if(!centroCustoSelected || !centroCustoSelected.idCentroCusto) return true;
        if(centroCustoSelected.ehGrupo) {
            modalMessage(MESSAGES.centroCusto.info.NAO_PERMITE_GRUPO);
            return false;
        };
        var result = _.find(centroCustos, function(centroCusto) { 
            return centroCusto.descricao == centroCustoSelected.descricao; 
        });
        if(!result) {
            modalMessage(MESSAGES.centroCusto.info.CENTRO_CUSTO_NAO_CADASTRADO);
            return false;
        };
        return true;
    };

    var favorecidoResultado = function(favorecidos, favorecidoSelected) {
        if(!favorecidoSelected) return true;
        var result = _.find(favorecidos, function(favorecido) { 
            return favorecido.nome == favorecidoSelected; 
        });
        if(!result) {
            modalMessage(MESSAGES.lancamento.info.FAVORECIDO_NAO_CADASTRADO);
            return false;
        };
        return true;
    };

    var rateioSaldo = function(valor, rateios) {
        var saldo = saldoRateio(rateios)
        if(!valor || !saldo) return true;
        saldo = parseFloat(saldo.toFixed(2));
        valor = parseFloat(valor.toFixed(2));
        if(saldo !== valor) {
            alert(MESSAGES.lancamento.ratear.validacao.SALDO_INCORRETO);
            return false;                    
        }
        return true;
    };    

    var parcelaSaldo = function(valor, parcelas) {
        var saldo = saldoParcela(parcelas)
        if(!valor || !saldo) return true;
        saldo = parseFloat(saldo.toFixed(2));
        valor = parseFloat(valor.toFixed(2));
        if(saldo !== valor) {
            alert(MESSAGES.lancamento.parcelar.validacao.SALDO_INCORRETO);
            return false;                    
        }
        return true;
    }; 

    var saldoParcela = function(parcelas) {            
        var saldo = 0;
        _.map(parcelas, function(parcela) {
            saldo += parseFloat(parcela.valor.toFixed(2));
        });
        return saldo;
    };

    var saldoRateio = function(rateios) {            
        var saldo = 0;
        _.map(rateios, function(rateio) {
            saldo += parseFloat(rateio.valor.toFixed(2));
        });
        return saldo;
    };
        
    var modalMessage = function(message) {
        ModalService.modalMessage(message);
    };
        

    return {
        contaEncerrada: contaEncerrada,
        planoContaResultado: planoContaResultado,
        centroCustoResultado: centroCustoResultado,
        favorecidoResultado: favorecidoResultado,
        parcelaSaldo: parcelaSaldo,
        rateioSaldo: rateioSaldo
        
    }

});