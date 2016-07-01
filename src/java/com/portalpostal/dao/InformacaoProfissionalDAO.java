package com.portalpostal.dao;

import com.portalpostal.dao.handler.InformacaoProfissionalHandler;
import com.portalpostal.model.InformacaoProfissional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InformacaoProfissionalDAO extends GenericDAO { 
    
    private final InformacaoProfissionalHandler informacaoProfissionalHandler;

    public InformacaoProfissionalDAO(String nameDB) { 
        super(nameDB, InformacaoProfissionalDAO.class);
        informacaoProfissionalHandler = new InformacaoProfissionalHandler();
    } 

    public List<InformacaoProfissional> findAll() throws Exception {
        String sql = "SELECT * FROM informacao_profissional, colaborador "
                   + "WHERE informacao_profissional.idColaborador = colaborador.idColaborador "
                   + "ORDER BY idInformacaoProfissional";        
        return findAll(sql, null, informacaoProfissionalHandler);
    }

    public InformacaoProfissional find(Integer idInformacaoProfissional) throws Exception {
        String sql = "SELECT * FROM informacao_profissional, colaborador "
                   + "WHERE informacao_profissional.idColaborador = colaborador.idColaborador "
                   + "AND informacao_profissional.idInformacaoProfissional = :idInformacaoProfissional";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("idInformacaoProfissional", idInformacaoProfissional);
        return (InformacaoProfissional) find(sql, params, informacaoProfissionalHandler);
    }

    public InformacaoProfissional save(InformacaoProfissional informacaoProfissional) throws Exception {  
        String sql = "INSERT INTO informacao_profissional (idColaborador, cargoFuncao, salario, dataAdmissao, dataDemissao, "
                   + "pisPasep, tituloEleitoral, certificadoReservista, ctps, horarioEntrada, horarioSaida, intervaloDe, intervaloAte, "
                   + "observacao) "
                   + "VALUES(:idColaborador, :cargoFuncao, :salario, :dataAdmissao, :dataDemissao, :pisPasep, :tituloEleitoral, "
                   + ":certificadoReservista, :ctps, :horarioEntrada, :horarioSaida, :intervaloDe, :intervaloAte, :observacao)";        
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("idColaborador", informacaoProfissional.getColaborador().getIdColaborador());
        params.put("cargoFuncao", informacaoProfissional.getCargoFuncao());
        params.put("salario", informacaoProfissional.getSalario());      
        params.put("dataAdmissao", informacaoProfissional.getDataAdmissao());      
        params.put("dataDemissao", informacaoProfissional.getDataDemissao());      
        params.put("pisPasep", informacaoProfissional.getPisPasep());      
        params.put("tituloEleitoral", informacaoProfissional.getTituloEleitoral());      
        params.put("certificadoReservista", informacaoProfissional.getCertificadoReservista());    
        params.put("ctps", informacaoProfissional.getCtps());    
        params.put("horarioEntrada", informacaoProfissional.getHorarioEntrada());    
        params.put("horarioSaida", informacaoProfissional.getHorarioSaida());    
        params.put("intervaloDe", informacaoProfissional.getIntervaloDe());      
        params.put("intervaloAte", informacaoProfissional.getIntervaloAte());      
        params.put("observacao", informacaoProfissional.getObservacao());    
        Integer idInformacaoProfissional = save(sql, params, informacaoProfissionalHandler);
        return find(idInformacaoProfissional);
    }

    public InformacaoProfissional update(InformacaoProfissional informacaoProfissional) throws Exception {
        String sql = "UPDATE informacao_profissional "
                   + "SET idColaborador = :idColaborador, cargoFuncao = :cargoFuncao, salario = :salario, dataAdmissao = :dataAdmissao, "
                   + "dataDemissao = :dataDemissao, pisPasep = :pisPasep, tituloEleitoral = :tituloEleitoral, certificadoReservista = :certificadoReservista, "
                   + "ctps = :ctps, horarioEntrada = :horarioEntrada, horarioSaida = :horarioSaida, intervaloDe = :intervaloDe, intervaloAte = :intervaloAte, "
                   + "observacao = :observacao "
                   + "WHERE idInformacaoProfissional = :idInformacaoProfissional ";        
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("idInformacaoProfissional", informacaoProfissional.getIdInformacaoProfissional());
        params.put("idColaborador", informacaoProfissional.getColaborador().getIdColaborador());
        params.put("cargoFuncao", informacaoProfissional.getCargoFuncao());
        params.put("salario", informacaoProfissional.getSalario());      
        params.put("dataAdmissao", informacaoProfissional.getDataAdmissao());      
        params.put("dataDemissao", informacaoProfissional.getDataDemissao());      
        params.put("pisPasep", informacaoProfissional.getPisPasep());      
        params.put("tituloEleitoral", informacaoProfissional.getTituloEleitoral());      
        params.put("certificadoReservista", informacaoProfissional.getCertificadoReservista());    
        params.put("ctps", informacaoProfissional.getCtps());    
        params.put("horarioEntrada", informacaoProfissional.getHorarioEntrada());    
        params.put("horarioSaida", informacaoProfissional.getHorarioSaida());    
        params.put("intervaloDe", informacaoProfissional.getIntervaloDe());      
        params.put("intervaloAte", informacaoProfissional.getIntervaloAte());      
        params.put("observacao", informacaoProfissional.getObservacao());    
        update(sql, params, informacaoProfissionalHandler);
        return informacaoProfissional;  
    }

    public InformacaoProfissional remove(Integer idInformacaoProfissional) throws Exception { 
        String sql = "DELETE FROM informacao_profissional WHERE idInformacaoProfissional = :idInformacaoProfissional ";
        InformacaoProfissional informacaoProfissional = find(idInformacaoProfissional);
        Map<String, Object> params = new HashMap<String, Object>();        
        params.put("idInformacaoProfissional", idInformacaoProfissional);
        remove(sql, params, informacaoProfissionalHandler);
        return informacaoProfissional;
    }
    
}
