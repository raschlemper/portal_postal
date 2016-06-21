package com.portalpostal.dao.handler;

import com.portalpostal.model.Colaborador;
import com.portalpostal.model.InformacaoProfissional;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.sql2o.ResultSetHandler;

public class InformacaoProfissionalHandler extends GenericHandler implements ResultSetHandler<InformacaoProfissional> {
        
    public InformacaoProfissionalHandler() {
        super("informacao_profissional");
    }
    
    public InformacaoProfissionalHandler(String table) {
        super(table);
    }

    public InformacaoProfissional handle(ResultSet result) throws SQLException {
        InformacaoProfissional informacaoProfissional = new InformacaoProfissional();
        informacaoProfissional.setIdInformacaoProfissional(getInt(result, "idInformacaoProfissional"));
        informacaoProfissional.setColaborador(getColaborador(result));
        informacaoProfissional.setCargaFuncao(getString(result, "cargaFuncao"));
        informacaoProfissional.setSalario(getDouble(result, "salario"));    
        informacaoProfissional.setDataAdmissao(getDate(result, "dataAdmissao"));   
        informacaoProfissional.setDataDemissao(getDate(result, "dataDemissao"));  
        informacaoProfissional.setPisPasep(getString(result, "pisPasep"));     
        informacaoProfissional.setTituloEleitoral(getString(result, "tituloEleitoral"));   
        informacaoProfissional.setCertificadoReservista(getString(result, "certificadoReservista"));   
        informacaoProfissional.setCtps(getString(result, "ctps"));   
        informacaoProfissional.setHorarioEntrada(getDate(result, "horarioEntrada"));   
        informacaoProfissional.setHorarioSaida(getDate(result, "horarioSaida"));   
        informacaoProfissional.setIntervaloDe(getDate(result, "intervaloDe"));   
        informacaoProfissional.setIntervaloAte(getDate(result, "intervaloAte"));   
        informacaoProfissional.setObservacao(getString(result, "observacao"));  
        return informacaoProfissional;
    }
    
    private Colaborador getColaborador(ResultSet result) throws SQLException {
        if(!existColumn(result, "colaborador.idColaborador")) return null;
        return new ColaboradorHandler().handle(result); 
    }
    
}
