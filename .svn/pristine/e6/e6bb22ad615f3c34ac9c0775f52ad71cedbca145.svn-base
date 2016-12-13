

package br.com.portalpostal.dao;

import br.com.portalpostal.entity.Coleta;
import javax.persistence.EntityManager;


public class ColetaDao {

    private final EntityManager manager;
    
    public ColetaDao(EntityManager manager){
        this.manager  = manager;
    }

    public Coleta persist(ColetaParameters coletaParameters){
        Coleta coleta = criaColeta(coletaParameters);
        manager.getTransaction().begin();
        manager.persist(coleta);
        manager.getTransaction().commit();
        return coleta;
    }

    private Coleta criaColeta(ColetaParameters coletaParameters) {
        Coleta coleta = new Coleta();
        coleta.setIdCliente(coletaParameters.getIdCliente());
        coleta.setIdUsuario(coletaParameters.getIdUsuario());
        coleta.setIdColetador(coletaParameters.getIdColetador());
        coleta.setIdContato(coletaParameters.getIdContato());
        coleta.setIdTipo(coletaParameters.getIdTipo());
        coleta.setStatus(coletaParameters.getStatus());
        coleta.setDataHoraColeta(coletaParameters.getDataHoraColeta());
        coleta.setDataHoraSolicitacao(coletaParameters.getDataHoraAtual());
        coleta.setTipoSolicitacao(coletaParameters.getTipoSolicitacao());
        coleta.setObs(coletaParameters.getObservacao());
        coleta.setIdDepartamento(coletaParameters.getIdDepartamento());
        return coleta;
    }
}
