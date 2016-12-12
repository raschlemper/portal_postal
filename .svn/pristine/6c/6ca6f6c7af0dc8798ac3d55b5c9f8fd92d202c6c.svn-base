
package br.com.portalpostal.providers;


import br.com.portalpostal.entity.Cliente;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

public class ProviderCliente extends Provider {

    private EntityManager manager;

    public ProviderCliente(EntityManager manager){
        this.manager=manager;
    }
    
    public Cliente findClienteById(Integer idCliente){
        TypedQuery<Cliente> query = manager.createNamedQuery("Cliente.findByCodigo",Cliente.class);
        query.setParameter("codigo", idCliente);
        return (Cliente)getSingleResult(query);
    }



}
