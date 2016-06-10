'use strict';

app.factory('LancamentoTransferenciaHandler', function() {
    
    var handleList = function(lancamentoTransferenciaList) { 
        return _.map(lancamentoTransferenciaList, function(lancamentoTransferencia) {
            return handle(lancamentoTransferencia);
        });
    };
    
    var handle = function(lancamentoTransferencia) {  
        var lancamentoTransferenciaHandle = {};
        lancamentoTransferenciaHandle.idLancamentoTransferencia = getId(lancamentoTransferencia);            
        lancamentoTransferenciaHandle.lancamentoOrigem = getLancamentoOrigem(lancamentoTransferencia);  
        lancamentoTransferenciaHandle.lancamentoDestino = getLancamentoDestino(lancamentoTransferencia);  
        lancamentoTransferenciaHandle.numero = getNumero(lancamentoTransferencia); 
        lancamentoTransferenciaHandle.dataCompetencia = getDataCompetencia(lancamentoTransferencia);
        lancamentoTransferenciaHandle.dataEmissao = getDataEmissao(lancamentoTransferencia);
        lancamentoTransferenciaHandle.dataLancamento = getDataLancamento(lancamentoTransferencia);
        lancamentoTransferenciaHandle.valor = getValor(lancamentoTransferencia);
        lancamentoTransferenciaHandle.historico = getHistorico(lancamentoTransferencia);
        lancamentoTransferenciaHandle.observacao = getObservacao(lancamentoTransferencia);
        lancamentoTransferenciaHandle.usuario = getUsuario(lancamentoTransferencia);
        return lancamentoTransferenciaHandle;
    };
    
    var getId = function(lancamentoTransferencia) {
        return lancamentoTransferencia.idLancamentoTransferencia || null;
    };
    
    var getLancamentoOrigem = function(lancamentoTransferencia) {
        if(!lancamentoTransferencia.lancamentoOrigem) return lancamentoTransferencia.lancamentoOrigem;
        return { idLancamento: lancamentoTransferencia.lancamentoOrigem.idLancamento }; 
    };
    
    var getLancamentoDestino = function(lancamentoTransferencia) {
        if(!lancamentoTransferencia.lancamentoDestino) return lancamentoTransferencia.lancamentoDestino;
        return { idLancamento: lancamentoTransferencia.lancamentoDestino.idLancamento }; 
    };
    
    var getNumero = function(lancamentoTransferencia) {
        return lancamentoTransferencia.numero || null;
    };
    
    var getDataCompetencia = function(lancamentoTransferencia) {
        return lancamentoTransferencia.dataCompetencia || moment(); 
    };
    
    var getDataEmissao = function(lancamentoTransferencia) {
        return lancamentoTransferencia.dataEmissao || moment(); 
    };
    
    var getDataLancamento = function(lancamentoTransferencia) {
        return lancamentoTransferencia.dataLancamento || moment(); 
    };
    
    var getValor = function(lancamentoTransferencia) {
        return lancamentoTransferencia.valor || 0;
    };
    
    var getHistorico = function(lancamentoTransferencia) {
        return lancamentoTransferencia.historico || null;
    };
    
    var getObservacao = function(lancamentoTransferencia) {
        return lancamentoTransferencia.observacao || null;
    };
    
    var getUsuario = function(lancamentoTransferencia) {
        return lancamentoTransferencia.usuario || null;
    };

    return {
        handle: handle,
        handleList: handleList
    }

});