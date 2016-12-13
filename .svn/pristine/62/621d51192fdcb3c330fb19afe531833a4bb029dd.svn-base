package br.com.portalpostal.providers;

import br.com.portalpostal.entity.ClienteDepartamento;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

/**
 *
 * @author Viviane
 */
public class ProviderClienteDepartamento extends Provider{

    private final EntityManager manager;

    public ProviderClienteDepartamento(EntityManager manager){
        this.manager = manager;
    }

    public List<ClienteDepartamento> findByIdCliente(Integer idCliente){
        TypedQuery<ClienteDepartamento> query = manager.createNamedQuery("ClienteDepartamentos.findByIdCliente", ClienteDepartamento.class);
        query.setParameter("idCliente", idCliente);
        return (List<ClienteDepartamento>) getResultList(query);
    }

    public ClienteDepartamento findByPK(Integer idCliente,Integer idDepartamento){
        TypedQuery<ClienteDepartamento> query = manager.createNamedQuery("ClienteDepartamentos.findByPK", ClienteDepartamento.class);
        query.setParameter("idCliente", idCliente);
        query.setParameter("idDepartamento", idDepartamento);
        return (ClienteDepartamento) getSingleResult(query);
    }

    public ClienteDepartamento findByName(Integer idCliente,String nomeDepartamento){
        TypedQuery<ClienteDepartamento> query = manager.createNamedQuery("ClienteDepartamentos.findByNomeDepartamento", ClienteDepartamento.class);
        query.setParameter("idCliente", idCliente);
        query.setParameter("nomeDepartamento", nomeDepartamento);
        return (ClienteDepartamento) getSingleResult(query);
    }

     public ClienteDepartamento findByPkAtivos(Integer idCliente,Integer idDepartamento){
        TypedQuery<ClienteDepartamento> query = manager.createNamedQuery("ClienteDepartamentos.findByPKAtivos", ClienteDepartamento.class);
        query.setParameter("idCliente", idCliente);
        query.setParameter("idDepartamento", idDepartamento);
        return (ClienteDepartamento) getSingleResult(query);
    }

}
