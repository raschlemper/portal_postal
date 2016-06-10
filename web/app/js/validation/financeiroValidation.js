'use strict';

app.factory('FinanceiroValidation', function(LISTAS, MESSAGES) {    
    
    var statusConta = LISTAS.statusConta;
        
    var contaEncerrada = function(conta) {
        if(conta.status.id === statusConta[1].id) {
            modalMessage(MESSAGES.conta.info.CONTA_ENCERRADO);
            return false;
        };
        return true;
    }
    
    var planoContaResultado = function(planoConta) {
        if(!planoConta) return true;
        if(planoConta.ehGrupo) {
            modalMessage(MESSAGES.planoConta.info.NAO_PERMITE_GRUPO);
            return false;
        };
        return true;
    };

    var centroCustoResultado = function(centroCusto) {
        if(!centroCusto) return true;
        if(centroCusto.ehGrupo) {
            modalMessage(MESSAGES.centroCusto.info.NAO_PERMITE_GRUPO);
            return false;
        };
        return true;
    };

    var rateioSaldo = function(lancamento, saldo) {
        if(!lancamento || !lancamento.valor || !saldo) return true;
        if(saldo !== lancamento.valor) {
            alert(MESSAGES.lancamento.ratear.validacao.SALDO_INCORRETO);
            return false;                    
        }
        return true;
    };

    return {
        contaEncerrada: contaEncerrada,
        planoContaResultado: planoContaResultado,
        centroCustoResultado: centroCustoResultado,
        rateioSaldo: rateioSaldo
        
    }

});