package br.com.portalpostal.providers;

import br.com.portalpostal.entity.LayoutImportacao;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

public class ProviderLayoutImportacao extends Provider {

    private final EntityManager manager;

//    public static void main(String args[]){
//        EntityManager manager = ConexaoEntityManager.getConnection("06895434000183");
//        ProviderLayoutImportacao provider = new ProviderLayoutImportacao(manager);
//        provider.findAllDistinctName();
//
//    }

    public ProviderLayoutImportacao(EntityManager manager) {
        this.manager = manager;
    }

    public List<LayoutImportacao> findAll() {
        TypedQuery<LayoutImportacao> query = manager.createNamedQuery("LayoutImportacao.findAll", LayoutImportacao.class);
        return (List<LayoutImportacao>) getResultList(query);
    }

    public List<LayoutImportacao> findByName(String name) {
        TypedQuery<LayoutImportacao> query = manager.createNamedQuery("LayoutImportacao.findByName", LayoutImportacao.class);
        query.setParameter("nome", name);
        return (List<LayoutImportacao>) getResultList(query);
    }

     public List<String> findAllDistinctName() {
        TypedQuery<LayoutImportacao> query = manager.createNamedQuery("LayoutImportacao.findDistinctName", LayoutImportacao.class);
        return (List<String>) getResultList(query);
    }

}
