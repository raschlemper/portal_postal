package br.com.portalpostal.entity;

import br.com.portalpostal.entity.ClienteContrato;
import br.com.portalpostal.entity.ClienteDepartamentos;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-12-02T11:35:28")
@StaticMetamodel(Cliente.class)
public class Cliente_ { 

    public static volatile SingularAttribute<Cliente, Integer> erroAtualizacao;
    public static volatile SingularAttribute<Cliente, String> nomeClienteSara;
    public static volatile SingularAttribute<Cliente, String> nomeFantasia;
    public static volatile SingularAttribute<Cliente, Integer> temContrato;
    public static volatile SingularAttribute<Cliente, String> loginSigep;
    public static volatile SingularAttribute<Cliente, String> cnpj;
    public static volatile SingularAttribute<Cliente, Date> dataHoraAtualizacao;
    public static volatile SingularAttribute<Cliente, String> obscli;
    public static volatile SingularAttribute<Cliente, Integer> statusCartaoPostagem;
    public static volatile SingularAttribute<Cliente, Integer> codigo;
    public static volatile SingularAttribute<Cliente, String> ufContrato;
    public static volatile SingularAttribute<Cliente, Integer> cep;
    public static volatile SingularAttribute<Cliente, String> codAdministrativo;
    public static volatile SingularAttribute<Cliente, String> cartaoReversa;
    public static volatile SingularAttribute<Cliente, Double> longitude;
    public static volatile SingularAttribute<Cliente, String> numContrato;
    public static volatile SingularAttribute<Cliente, Integer> nomeEtq;
    public static volatile SingularAttribute<Cliente, String> complemento;
    public static volatile SingularAttribute<Cliente, String> senhaCorreio;
    public static volatile SingularAttribute<Cliente, Integer> anoContrato;
    public static volatile SingularAttribute<Cliente, String> uf;
    public static volatile SingularAttribute<Cliente, String> nomeContrato;
    public static volatile SingularAttribute<Cliente, String> codSTO;
    public static volatile SingularAttribute<Cliente, Integer> arDigital;
    public static volatile SingularAttribute<Cliente, Integer> envioEmail;
    public static volatile SingularAttribute<Cliente, Integer> metodoInsercao;
    public static volatile SingularAttribute<Cliente, Integer> separarDestinatarios;
    public static volatile SingularAttribute<Cliente, Integer> codDiretoria;
    public static volatile SingularAttribute<Cliente, String> loginReversa;
    public static volatile SingularAttribute<Cliente, Integer> usaEtiquetador;
    public static volatile SingularAttribute<Cliente, String> numero;
    public static volatile SingularAttribute<Cliente, String> senhaSigep;
    public static volatile SingularAttribute<Cliente, String> senhaReversa;
    public static volatile SingularAttribute<Cliente, String> cidade;
    public static volatile SingularAttribute<Cliente, String> bairro;
    public static volatile SingularAttribute<Cliente, Date> dtVigenciaFimContrato;
    public static volatile SingularAttribute<Cliente, String> loginCorreio;
    public static volatile SingularAttribute<Cliente, Integer> idGrupoFaturamento;
    public static volatile SingularAttribute<Cliente, String> email;
    public static volatile SingularAttribute<Cliente, Short> ativo;
    public static volatile SingularAttribute<Cliente, String> telefone;
    public static volatile ListAttribute<Cliente, ClienteDepartamentos> departamentos;
    public static volatile SingularAttribute<Cliente, String> nome;
    public static volatile SingularAttribute<Cliente, Double> latitude;
    public static volatile SingularAttribute<Cliente, String> endereco;
    public static volatile ListAttribute<Cliente, ClienteContrato> contratos;
    public static volatile SingularAttribute<Cliente, String> cartaoPostagem;
    public static volatile SingularAttribute<Cliente, String> urlLogo;

}