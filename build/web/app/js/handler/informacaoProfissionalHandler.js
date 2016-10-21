'use strict';

app.factory('InformacaoProfissionalHandler', function() {
    
    var handleList = function(informacaoProfissionalList) { 
        return _.map(informacaoProfissionalList, function(informacaoProfissional) {
            return handle(informacaoProfissional);
        });
    };
    
    var handle = function(informacaoProfissional) {  
        var informacaoProfissionalHandle = {};
        informacaoProfissionalHandle.idInformacaoProfissional = getId(informacaoProfissional); 
        informacaoProfissionalHandle.colaborador = getColaborador(informacaoProfissional);
        informacaoProfissionalHandle.cargoFuncao = getCargoFuncao(informacaoProfissional);
        informacaoProfissionalHandle.salario = getSalario(informacaoProfissional);
        informacaoProfissionalHandle.dataAdmissao = getDataAdmissao(informacaoProfissional);
        informacaoProfissionalHandle.dataDemissao = getDataDemissao(informacaoProfissional);
        informacaoProfissionalHandle.pisPasep = getPisPasep(informacaoProfissional);
        informacaoProfissionalHandle.tituloEleitoral = getTituloEleitoral(informacaoProfissional);
        informacaoProfissionalHandle.certificadoReservista = getCertificadoReservista(informacaoProfissional);
        informacaoProfissionalHandle.ctps = getCtps(informacaoProfissional);
        informacaoProfissionalHandle.horarioEntrada = getHorarioEntrada(informacaoProfissional);
        informacaoProfissionalHandle.horarioSaida = getHorarioSaida(informacaoProfissional);
        informacaoProfissionalHandle.intervaloDe = getIntervaloDe(informacaoProfissional);
        informacaoProfissionalHandle.intervaloAte = getIntervaloAte(informacaoProfissional);
        informacaoProfissionalHandle.observacao = getObservacao(informacaoProfissional);
        return informacaoProfissionalHandle;
    };
    
    var getId = function(informacaoProfissional) {
        return informacaoProfissional.idInformacaoProfissional || null;
    };
    
    var getColaborador = function(informacaoProfissional) {
        if(!informacaoProfissional.colaborador) return informacaoProfissional.colaborador;
        return { idColaborador: informacaoProfissional.colaborador.idColaborador };        
    };
    
    var getCargoFuncao = function(informacaoProfissional) {
        return informacaoProfissional.cargoFuncao || null; 
    };
    
    var getSalario = function(informacaoProfissional) {
        return informacaoProfissional.salario || null;     
    };
    
    var getDataAdmissao = function(informacaoProfissional) {
        return informacaoProfissional.dataAdmissao || null; 
    };
    
    var getDataDemissao = function(informacaoProfissional) {
        return informacaoProfissional.dataDemissao || null; 
    };
    
    var getPisPasep = function(informacaoProfissional) {
        return informacaoProfissional.pisPasep || null; 
    };
    
    var getTituloEleitoral = function(informacaoProfissional) {
        return informacaoProfissional.tituloEleitoral || null; 
    };
    
    var getCertificadoReservista = function(informacaoProfissional) {
        return informacaoProfissional.certificadoReservista || null; 
    };
    
    var getCtps = function(informacaoProfissional) {
        return informacaoProfissional.ctps || null; 
    };
    
    var getHorarioEntrada = function(informacaoProfissional) {
        return getDateFormat(informacaoProfissional.horarioEntrada); 
    };
    
    var getHorarioSaida = function(informacaoProfissional) {
        return getDateFormat(informacaoProfissional.horarioSaida); 
    };
    
    var getIntervaloDe = function(informacaoProfissional) {
        return getDateFormat(informacaoProfissional.intervaloDe); 
    };
    
    var getIntervaloAte = function(informacaoProfissional) {
        return getDateFormat(informacaoProfissional.intervaloAte); 
    };
    
    var getObservacao = function(informacaoProfissional) {
        return informacaoProfissional.observacao || null; 
    };
    
    var getDateFormat = function(time) {
        if(!time) return time;
        return moment("1970-01-01 " + time + ":00");     
    }

    return {
        handle: handle,
        handleList: handleList
    }

});