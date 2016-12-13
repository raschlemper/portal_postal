package br.com.portalpostal.entity;

import br.com.portalpostal.entity.Cliente;
import br.com.portalpostal.entity.ClienteDepartamentoPK;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-12-13T09:46:07")
@StaticMetamodel(ClienteDepartamento.class)
public class ClienteDepartamento_ { 

    public static volatile SingularAttribute<ClienteDepartamento, Integer> temEndereco;
    public static volatile SingularAttribute<ClienteDepartamento, Integer> ativo;
    public static volatile SingularAttribute<ClienteDepartamento, String> cidade;
    public static volatile SingularAttribute<ClienteDepartamento, String> nomeDepartamento;
    public static volatile SingularAttribute<ClienteDepartamento, String> numero;
    public static volatile SingularAttribute<ClienteDepartamento, String> bairro;
    public static volatile SingularAttribute<ClienteDepartamento, String> latitude;
    public static volatile SingularAttribute<ClienteDepartamento, String> nomeEndereco;
    public static volatile SingularAttribute<ClienteDepartamento, String> codReferencia;
    public static volatile SingularAttribute<ClienteDepartamento, String> cep;
    public static volatile SingularAttribute<ClienteDepartamento, String> uf;
    public static volatile SingularAttribute<ClienteDepartamento, Cliente> cliente;
    public static volatile SingularAttribute<ClienteDepartamento, String> complemento;
    public static volatile SingularAttribute<ClienteDepartamento, ClienteDepartamentoPK> clienteDepartamentoPK;
    public static volatile SingularAttribute<ClienteDepartamento, String> logradouro;
    public static volatile SingularAttribute<ClienteDepartamento, String> cartaoPostagem;
    public static volatile SingularAttribute<ClienteDepartamento, String> longitude;

}