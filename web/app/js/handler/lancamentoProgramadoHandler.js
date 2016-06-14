'use strict';

app.factory('LancamentoProgramadoHandler', function() {
    
    var handleList = function(lancamentoProgramadoList) { 
        return _.map(lancamentoProgramadoList, function(lancamentoProgramado) {
            return handle(lancamentoProgramado);
        });
    };
    
    var handle = function(lancamentoProgramado) {  
        var lancamentoProgramadoHandle = {};
        lancamentoProgramadoHandle.idLancamentoProgramado = getId(lancamentoProgramado);            
        lancamentoProgramadoHandle.conta = getConta(lancamentoProgramado);  
        lancamentoProgramadoHandle.planoConta = getPlanoConta(lancamentoProgramado);  
        lancamentoProgramadoHandle.centroCusto = getCentroCusto(lancamentoProgramado);
        lancamentoProgramadoHandle.tipo = getTipo(lancamentoProgramado); 
        lancamentoProgramadoHandle.favorecido = getFavorecido(lancamentoProgramado); 
        lancamentoProgramadoHandle.numero = getNumero(lancamentoProgramado); 
        lancamentoProgramadoHandle.documento = getDocumento(lancamentoProgramado); 
        lancamentoProgramadoHandle.formaPagamento = getFormaPagamento(lancamentoProgramado); 
        lancamentoProgramadoHandle.frequencia = getFrequencia(lancamentoProgramado);
        lancamentoProgramadoHandle.quantidadeParcela = getQuantidadeParcela(lancamentoProgramado);
        lancamentoProgramadoHandle.numeroParcela = getNumeroParcela(lancamentoProgramado);
        lancamentoProgramadoHandle.dataCompetencia = getDataCompetencia(lancamentoProgramado);
        lancamentoProgramadoHandle.dataEmissao = getDataEmissao(lancamentoProgramado);
        lancamentoProgramadoHandle.dataVencimento = getDataVencimento(lancamentoProgramado);
        lancamentoProgramadoHandle.valor = getValor(lancamentoProgramado);
        lancamentoProgramadoHandle.situacao = getSituacao(lancamentoProgramado);
        lancamentoProgramadoHandle.historico = getHistorico(lancamentoProgramado);
        lancamentoProgramadoHandle.usuario = getUsuario(lancamentoProgramado);
        return lancamentoProgramadoHandle;
    };
    
    var getId = function(lancamentoProgramado) {
        return lancamentoProgramado.idLancamentoProgramado || null;
    };
    
    var getConta = function(lancamentoProgramado) {
        if(!lancamentoProgramado.conta) return lancamentoProgramado.conta;
        return { idConta: lancamentoProgramado.conta.idConta };
    };
    
    var getPlanoConta = function(lancamentoProgramado) {
        if(!lancamentoProgramado.planoConta) return lancamentoProgramado.planoConta;    
        return { idPlanoConta: lancamentoProgramado.planoConta.idPlanoConta }; 
    };
    
    var getCentroCusto = function(lancamentoProgramado) {
        if(!lancamentoProgramado.centroCusto) return lancamentoProgramado.centroCusto;    
        return { idCentroCusto: lancamentoProgramado.centroCusto.idCentroCusto }; 
    };
    
    var getTipo = function(lancamentoProgramado) {
        if(!lancamentoProgramado.tipo) return lancamentoProgramado.tipo.id;
        return lancamentoProgramado.tipo.id; 
    };
    
    var getFavorecido = function(lancamentoProgramado) {
        return lancamentoProgramado.favorecido || null;
    };
    
    var getNumero = function(lancamentoProgramado) {
        return lancamentoProgramado.numero;
    };
    
    var getDocumento = function(lancamentoProgramado) {
        if(!lancamentoProgramado.documento) return lancamentoProgramado.documento.idTipoDocumento;
        return { idTipoDocumento: lancamentoProgramado.documento.idTipoDocumento };
    };
    
    var getFormaPagamento = function(lancamentoProgramado) {
        if(!lancamentoProgramado.formaPagamento) return lancamentoProgramado.formaPagamento.idTipoFormaPagamento;
        return { idTipoFormaPagamento: lancamentoProgramado.formaPagamento.idTipoFormaPagamento };
    };
    
    var getFrequencia = function(lancamentoProgramado) {
        if(!lancamentoProgramado.frequencia) return lancamentoProgramado.frequencia.id;
        return lancamentoProgramado.frequencia.id;
    };
    
    var getQuantidadeParcela = function(lancamentoProgramado) {
        return lancamentoProgramado.quantidadeParcela;
    };
    
    var getNumeroParcela = function(lancamentoProgramado) {
        return lancamentoProgramado.numeroParcela;
    };
    
    var getDataCompetencia = function(lancamentoProgramado) {
        return lancamentoProgramado.dataCompetencia || moment(); 
    };
    
    var getDataEmissao = function(lancamentoProgramado) {
        return lancamentoProgramado.dataEmissao || moment(); 
    };
    
    var getDataVencimento = function(lancamentoProgramado) {
        return lancamentoProgramado.dataVencimento || lancamentoProgramado.dataLancamento || moment(); 
    };
    
    var getValor = function(lancamentoProgramado) {
        return lancamentoProgramado.valor || 0;
    };
    
    var getSituacao = function(lancamentoProgramado) {
        if(!lancamentoProgramado.situacao) return lancamentoProgramado.situacao.id;
        return lancamentoProgramado.situacao.id; 
    };
    
    var getHistorico = function(lancamentoProgramado) {
        return lancamentoProgramado.historico || null;
    };
    
    var getUsuario = function(lancamentoProgramado) {
        return lancamentoProgramado.usuario || null;
    };

    return {
        handle: handle,
        handleList: handleList
    }

});