'use strict';

app.factory('LancamentoProgramadoTransferenciaHandler', function() {
    
    var handleList = function(lancamentoProgramadoTransferenciaList) { 
        return _.map(lancamentoProgramadoTransferenciaList, function(lancamentoProgramadoTransferencia) {
            return handle(lancamentoProgramadoTransferencia);
        });
    };
    
    var handle = function(lancamentoProgramadoTransferencia) {  
        var lancamentoProgramadoTransferenciaHandle = {};
        lancamentoProgramadoTransferenciaHandle.idLancamentoProgramadoTransferencia = getId(lancamentoProgramadoTransferencia);            
        lancamentoProgramadoTransferenciaHandle.lancamentoProgramadoOrigem = getLancamentoProgramadoOrigem(lancamentoProgramadoTransferencia);  
        lancamentoProgramadoTransferenciaHandle.lancamentoProgramadoDestino = getLancamentoProgramadoDestino(lancamentoProgramadoTransferencia);  
        lancamentoProgramadoTransferenciaHandle.numero = getNumero(lancamentoProgramadoTransferencia); 
        lancamentoProgramadoTransferenciaHandle.documento = getDocumento(lancamentoProgramadoTransferencia); 
        lancamentoProgramadoTransferenciaHandle.formaPagamento = getFormaPagamento(lancamentoProgramadoTransferencia); 
        lancamentoProgramadoTransferenciaHandle.frequencia = getFrequencia(lancamentoProgramadoTransferenciaHandle);
        lancamentoProgramadoTransferenciaHandle.dataCompetencia = getDataCompetencia(lancamentoProgramadoTransferencia);
        lancamentoProgramadoTransferenciaHandle.dataEmissao = getDataEmissao(lancamentoProgramadoTransferencia);
        lancamentoProgramadoTransferenciaHandle.dataVencimento = getDataVencimento(lancamentoProgramadoTransferencia);
        lancamentoProgramadoTransferenciaHandle.valor = getValor(lancamentoProgramadoTransferencia);
        lancamentoProgramadoTransferenciaHandle.historico = getHistorico(lancamentoProgramadoTransferencia);
        lancamentoProgramadoTransferenciaHandle.observacao = getObservacao(lancamentoProgramadoTransferencia);
        lancamentoProgramadoTransferenciaHandle.usuario = getUsuario(lancamentoProgramadoTransferencia);
        return lancamentoProgramadoTransferenciaHandle;
    };
    
    var getId = function(lancamentoProgramadoTransferencia) {
        return lancamentoProgramadoTransferencia.idLancamentoProgramadoTransferencia || null;
    };
    
    var getLancamentoProgramadoOrigem = function(lancamentoProgramadoTransferencia) {
        if(!lancamentoProgramadoTransferencia.lancamentoProgramadoOrigem) return lancamentoProgramadoTransferencia.lancamentoProgramadoOrigem;
        return { idLancamentoProgramado: lancamentoProgramadoTransferencia.lancamentoProgramadoOrigem.idLancamentoProgramado }; 
    };
    
    var getLancamentoProgramadoDestino = function(lancamentoProgramadoTransferencia) {
        if(!lancamentoProgramadoTransferencia.lancamentoProgramadoDestino) return lancamentoProgramadoTransferencia.lancamentoProgramadoDestino;
        return { idLancamentoProgramado: lancamentoProgramadoTransferencia.lancamentoProgramadoDestino.idLancamentoProgramado }; 
    };
    
    var getNumero = function(lancamentoProgramadoTransferencia) {
        return lancamentoProgramadoTransferencia.numero || null;
    };
    
    var getDocumento = function(lancamentoProgramadoTransferencia) {
        if(!lancamentoProgramadoTransferencia.documento) return lancamentoProgramadoTransferencia.documento;
        return { idTipoDocumento: lancamentoProgramadoTransferencia.documento.idTipoDocumento };
    };
    
    var getFormaPagamento = function(lancamentoProgramadoTransferencia) {
        if(!lancamentoProgramadoTransferencia.formaPagamento) return lancamentoProgramadoTransferencia.formaPagamento;
        return { idTipoFormaPagamento: lancamentoProgramadoTransferencia.formaPagamento.idTipoFormaPagamento };
    };
    
    var getFrequencia = function(lancamentoProgramadoTransferencia) {
        if(!lancamentoProgramadoTransferencia.frequencia) return lancamentoProgramadoTransferencia.frequencia;
        return lancamentoProgramadoTransferencia.frequencia.id;
    };
    
    var getDataCompetencia = function(lancamentoProgramadoTransferencia) {
        return lancamentoProgramadoTransferencia.dataCompetencia || moment(); 
    };
    
    var getDataEmissao = function(lancamentoProgramadoTransferencia) {
        return lancamentoProgramadoTransferencia.dataEmissao || moment(); 
    };
    
    var getDataVencimento = function(lancamentoProgramadoTransferencia) {
        return lancamentoProgramadoTransferencia.dataVencimento || moment(); 
    };
    
    var getValor = function(lancamentoProgramadoTransferencia) {
        return lancamentoProgramadoTransferencia.valor || 0;
    };
    
    var getHistorico = function(lancamentoProgramadoTransferencia) {
        return lancamentoProgramadoTransferencia.historico || null;
    };
    
    var getObservacao = function(lancamentoProgramadoTransferencia) {
        return lancamentoProgramadoTransferencia.observacao || null;
    };
    
    var getUsuario = function(lancamentoProgramadoTransferencia) {
        return lancamentoProgramadoTransferencia.usuario || null;
    };

    return {
        handle: handle,
        handleList: handleList
    }

});