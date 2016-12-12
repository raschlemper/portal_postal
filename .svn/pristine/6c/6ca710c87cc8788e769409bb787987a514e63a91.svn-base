
package br.com.portalpostal.providers;

import br.com.portalpostal.componentes.ConexaoEntityManager;
import br.com.portalpostal.entity.ServicosEct;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

/**
 *
 * @author Viviane
 */
public class ProviderServicoEct extends Provider{

    EntityManager manager;
    public ProviderServicoEct(EntityManager manager){
        this.manager = manager;
    }
    
    public List<ServicosEct> findAllServicosAtivos(){
         EntityManager managerPostal = ConexaoEntityManager.getConnection(ConexaoEntityManager.DBPORTALPOSTAL);
        TypedQuery<ServicosEct> query = managerPostal.createNamedQuery("ServicosEct.findServicos", ServicosEct.class);
        return (List<ServicosEct>)getResultList(query);
    }


}
