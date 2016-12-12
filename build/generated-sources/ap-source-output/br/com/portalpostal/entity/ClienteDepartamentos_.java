package br.com.portalpostal.entity;

import br.com.portalpostal.entity.Cliente;
import br.com.portalpostal.entity.ClienteDepartamentosPK;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-12-02T11:35:28")
@StaticMetamodel(ClienteDepartamentos.class)
public class ClienteDepartamentos_ { 

    public static volatile SingularAttribute<ClienteDepartamentos, String> complemento;
    public static volatile SingularAttribute<ClienteDepartamentos, ClienteDepartamentosPK> clienteDepartamentosPK;
    public static volatile SingularAttribute<ClienteDepartamentos, String> uf;
    public static volatile SingularAttribute<ClienteDepartamentos, String> codReferencia;
    public static volatile SingularAttribute<ClienteDepartamentos, Integer> temEndereco;
    public static volatile SingularAttribute<ClienteDepartamentos, String> numero;
    public static volatile SingularAttribute<ClienteDepartamentos, String> cidade;
    public static volatile SingularAttribute<ClienteDepartamentos, String> bairro;
    public static volatile SingularAttribute<ClienteDepartamentos, String> cep;
    public static volatile SingularAttribute<ClienteDepartamentos, Cliente> cliente;
    public static volatile SingularAttribute<ClienteDepartamentos, Integer> ativo;
    public static volatile SingularAttribute<ClienteDepartamentos, String> logradouro;
    public static volatile SingularAttribute<ClienteDepartamentos, String> nomeDepartamento;
    public static volatile SingularAttribute<ClienteDepartamentos, String> nomeEndereco;
    public static volatile SingularAttribute<ClienteDepartamentos, String> cartaoPostagem;

}