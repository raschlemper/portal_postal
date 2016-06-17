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

    var saldoRateio = function(rateios) {            
        var saldo = 0;
        _.map(rateios, function(rateio) {
            saldo += rateio.valor;
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
        rateioSaldo: rateioSaldo
        
    }

});