
package br.com.portalpostal.dao;

import br.com.portalpostal.entity.ClienteDepartamento;
import br.com.portalpostal.providers.ProviderClienteDepartamento;
import javax.persistence.EntityManager;


public class ClienteDepartamentoDao {
    private final EntityManager manager;

    public ClienteDepartamentoDao(EntityManager manager){
        this.manager = manager;
    }

    public ClienteDepartamento persist(ClienteDepartamentoParameter parameter){
        ClienteDepartamento clienteDepartamento = new ClienteDepartamento();
        return clienteDepartamento;
    }

    public ClienteDepartamento update(ClienteDepartamentoParameter parameter){
        ProviderClienteDepartamento provider = new ProviderClienteDepartamento(manager);
        ClienteDepartamento clienteDepartamento = provider.findByPK(parameter.getIdCliente(), parameter.getIdDepartamento());
        alterarDadosClienteDepartamento(clienteDepartamento,parameter);
        manager.getTransaction().begin();
        manager.persist(clienteDepartamento);
        manager.getTransaction().commit();
        return clienteDepartamento;
    }

    private void alterarDadosClienteDepartamento(ClienteDepartamento clienteDepartamento, ClienteDepartamentoParameter parameter) {
        clienteDepartamento.setCartaoPostagem(parameter.getCartao());
        clienteDepartamento.setNomeDepartamento(parameter.getNome());
        clienteDepartamento.setTemEndereco(parameter.getTemEndereco());
        clienteDepartamento.setNomeEndereco(parameter.getNomeEndereco());
        clienteDepartamento.setLogradouro(parameter.getLogradouro());
        clienteDepartamento.setNumero(parameter.getNumero());
        clienteDepartamento.setComplemento(parameter.getComplemento());
        clienteDepartamento.setBairro(parameter.getBairro());
        clienteDepartamento.setCidade(parameter.getCidade());
        clienteDepartamento.setUf(parameter.getUf());
        clienteDepartamento.setCep(parameter.getCep());
        clienteDepartamento.setCodReferencia(parameter.getCodigoReferencia());
        clienteDepartamento.setLatitude(parameter.getLatitude());
        clienteDepartamento.setLongitude(parameter.getLongitude());

    }

}
