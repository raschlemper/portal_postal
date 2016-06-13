'use strict';

app.factory('LancamentoHandler', function(LISTAS) {
            
    var situacoes = LISTAS.situacaoLancamento;
    var modelos = LISTAS.modeloLancamento;
    
    var handleList = function(lancamentoList) { 
        return _.map(lancamentoList, function(lancamento) {
            return handle(lancamento);
        });
    };
    
    var handle = function(lancamento) {  
        var lancamentoHandle = {};
        lancamentoHandle.idLancamento = getId(lancamento);            
        lancamentoHandle.conta = getConta(lancamento);  
        lancamentoHandle.planoConta = getPlanoConta(lancamento);  
        lancamentoHandle.centroCusto = getCentroCusto(lancamento);
        lancamentoHandle.lancamentoProgramado = getLancamentoProgramado(lancamento);
        lancamentoHandle.lancamentoTransferencia = getLancamentoTransferencia(lancamento);
        lancamentoHandle.tipo = getTipo(lancamento); 
        lancamentoHandle.favorecido = getFavorecido(lancamento); 
        lancamentoHandle.numero = getNumero(lancamento); 
        lancamentoHandle.numeroParcela = getNumeroParcela(lancamento); 
        lancamentoHandle.dataCompetencia = getDataCompetencia(lancamento);
        lancamentoHandle.dataEmissao = getDataEmissao(lancamento);
        lancamentoHandle.dataVencimento = getDataVencimento(lancamento);
        lancamentoHandle.dataLancamento = getDataLancamento(lancamento);
        lancamentoHandle.dataCompensacao = getDataCompensacao(lancamento);
        lancamentoHandle.situacao = getSituacao(lancamento);
        lancamentoHandle.modelo = getModelo(lancamento); 
        lancamentoHandle.valor = getValor(lancamento);
        lancamentoHandle.valorDesconto = getValorDesconto(lancamento);
        lancamentoHandle.valorJuros = getValorJuros(lancamento);
        lancamentoHandle.valorMulta = getValorMulta(lancamento);
        lancamentoHandle.numeroLoteConciliado = getNumeroLoteConciliado(lancamento);
        lancamentoHandle.autenticacao = getAutenticacao(lancamento);
        lancamentoHandle.historico = getHistorico(lancamento);
        lancamentoHandle.observacao = getObservacao(lancamento);
        lancamentoHandle.usuario = getUsuario(lancamento);
        lancamentoHandle.anexos = getAnexos(lancamento);
        lancamentoHandle.rateios = null;
        return lancamentoHandle;
    };
    
    var getId = function(lancamento) {
        return lancamento.idLancamento || null;
    };
    
    var getConta = function(lancamento) {
        if(!lancamento.conta) return lancamento.conta;
        return { idConta: lancamento.conta.idConta };
    };
    
    var getPlanoConta = function(lancamento) {
        if(!lancamento.planoConta) return lancamento.planoConta;    
        return { idPlanoConta: lancamento.planoConta.idPlanoConta }; 
    };
    
    var getCentroCusto = function(lancamento) {
        if(!lancamento.centroCusto) return lancamento.centroCusto;    
        return { idCentroCusto: lancamento.centroCusto.idCentroCusto }; 
    };
    
    var getLancamentoProgramado = function(lancamento) {
        if(!lancamento.lancamentoProgramado) return lancamento.lancamentoProgramado;
        return { idLancamentoProgramado: lancamento.lancamentoProgramado.idLancamentoProgramado }; 
    };
    
    var getLancamentoTransferencia = function(lancamento) {
        if(!lancamento.lancamentoTransferencia) return lancamento.lancamentoTransferencia;
        return { idLancamentoTransferencia: lancamento.lancamentoTransferencia.idLancamentoTransferencia }; 
    };
    
    var getTipo = function(lancamento) {
        if(!lancamento.tipo) return lancamento.tipo.id;
        return lancamento.tipo.id; 
    };
    
    var getFavorecido = function(lancamento) {
        return lancamento.favorecido || null;
    };
    
    var getNumero = function(lancamento) {
        return lancamento.numero || null;
    };
    
    var getNumeroParcela = function(lancamento) {
        return lancamento.numeroParcela || null;
    };
    
    var getSituacao = function(lancamento) {
        if(!lancamento.situacao) return lancamento.situacao.id;
        return lancamento.situacao.id; 
    };
    
    var getModelo = function(lancamento) {
        if(!lancamento.modelo) return modelos[0].id;
        return lancamento.modelo.id; 
    };
    
    var getDataCompetencia = function(lancamento) {
        return lancamento.dataCompetencia || moment(); 
    };
    
    var getDataEmissao = function(lancamento) {
        return lancamento.dataEmissao || moment(); 
    };
    
    var getDataVencimento = function(lancamento) {
        return lancamento.dataVencimento || lancamento.dataLancamento || moment(); 
    };
    
    var getDataLancamento = function(lancamento) {
//        return lancamento.dataLancamento || moment(); 
        return lancamento.dataLancamento; 
    };
    
    var getDataCompensacao = function(lancamento) {
        if(lancamento.situacao !== situacoes[2].id) return lancamento.dataCompensacao;
        return lancamento.dataCompensacao = moment();
    };
    
    var getValor = function(lancamento) {
        return lancamento.valor || 0;
    };
    
    var getValorDesconto = function(lancamento) {
        return lancamento.valorDesconto || 0;
    };
    
    var getValorJuros = function(lancamento) {
        return lancamento.valorJuros || 0;
    };
    
    var getValorMulta = function(lancamento) {
        return lancamento.valorMulta || 0;
    };
    
    var getNumeroLoteConciliado = function(lancamento) {
        return lancamento.numeroLoteConciliado || null;
    };
    
    var getAutenticacao = function(lancamento) {
        return lancamento.autenticacao || null;
    };
    
    var getHistorico = function(lancamento) {
        return lancamento.historico || null;
    };
    
    var getObservacao = function(lancamento) {
        return lancamento.observacao || null;
    };
    
    var getUsuario = function(lancamento) {
        return lancamento.usuario || null;
    };
    
    var getAnexos = function(lancamento) {
        return lancamento.anexos || false;
    };
    
    var getDate = function() {
        return moment().format("DD/MM/YYYY")
    }

    return {
        handle: handle,
        handleList: handleList
    }

});