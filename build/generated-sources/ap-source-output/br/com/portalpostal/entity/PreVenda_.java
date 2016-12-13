package br.com.portalpostal.entity;

import br.com.portalpostal.entity.Cliente;
import br.com.portalpostal.entity.PreVendaDestinatario;
import br.com.portalpostal.entity.PreVendaPK;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-12-13T09:46:07")
@StaticMetamodel(PreVenda.class)
public class PreVenda_ { 

    public static volatile SingularAttribute<PreVenda, String> siglaPais;
    public static volatile SingularAttribute<PreVenda, Date> dataVenda;
    public static volatile SingularAttribute<PreVenda, String> lote;
    public static volatile SingularAttribute<PreVenda, Integer> userImpressao;
    public static volatile SingularAttribute<PreVenda, String> destinoPostagem;
    public static volatile SingularAttribute<PreVenda, String> nomeVenda;
    public static volatile SingularAttribute<PreVenda, Float> valorDeclarado;
    public static volatile SingularAttribute<PreVenda, Short> postaRestante;
    public static volatile SingularAttribute<PreVenda, String> metodoInsercao;
    public static volatile SingularAttribute<PreVenda, Integer> largura;
    public static volatile SingularAttribute<PreVenda, Integer> peso;
    public static volatile SingularAttribute<PreVenda, String> contrato;
    public static volatile SingularAttribute<PreVenda, Date> dataPreVenda;
    public static volatile SingularAttribute<PreVenda, String> serieNotaFiscal;
    public static volatile SingularAttribute<PreVenda, Integer> idPlp;
    public static volatile SingularAttribute<PreVenda, PreVendaDestinatario> destinatario;
    public static volatile SingularAttribute<PreVenda, Integer> impressoAr;
    public static volatile SingularAttribute<PreVenda, Cliente> cliente;
    public static volatile SingularAttribute<PreVenda, Float> valorCobrar;
    public static volatile SingularAttribute<PreVenda, Integer> altura;
    public static volatile SingularAttribute<PreVenda, String> aosCuidados;
    public static volatile SingularAttribute<PreVenda, String> notaFiscal;
    public static volatile SingularAttribute<PreVenda, String> tipoEncomenda;
    public static volatile SingularAttribute<PreVenda, String> siglaAmarracao;
    public static volatile SingularAttribute<PreVenda, String> setor;
    public static volatile SingularAttribute<PreVenda, String> conteudo;
    public static volatile SingularAttribute<PreVenda, Integer> codECT;
    public static volatile SingularAttribute<PreVenda, String> arquivoAr;
    public static volatile SingularAttribute<PreVenda, String> emailDestinatario;
    public static volatile SingularAttribute<PreVenda, Integer> impresso;
    public static volatile SingularAttribute<PreVenda, Integer> maoPropria;
    public static volatile SingularAttribute<PreVenda, Boolean> isSync;
    public static volatile SingularAttribute<PreVenda, Integer> consolidado;
    public static volatile SingularAttribute<PreVenda, Integer> idDepartamento;
    public static volatile SingularAttribute<PreVenda, String> tipoEtiqueta;
    public static volatile SingularAttribute<PreVenda, PreVendaPK> preVendaPK;
    public static volatile SingularAttribute<PreVenda, Integer> idCliente;
    public static volatile SingularAttribute<PreVenda, Integer> userConsolidado;
    public static volatile SingularAttribute<PreVenda, Integer> comprimento;
    public static volatile SingularAttribute<PreVenda, Date> dataImpressao;
    public static volatile SingularAttribute<PreVenda, Date> dataConsolidado;
    public static volatile SingularAttribute<PreVenda, String> responsavel;
    public static volatile SingularAttribute<PreVenda, String> nomePreVenda;
    public static volatile SingularAttribute<PreVenda, Short> registroModico;
    public static volatile SingularAttribute<PreVenda, String> nomeConsolidado;
    public static volatile SingularAttribute<PreVenda, Integer> inutilizada;
    public static volatile SingularAttribute<PreVenda, Integer> userVenda;
    public static volatile SingularAttribute<PreVenda, String> chave;
    public static volatile SingularAttribute<PreVenda, String> nomeImpressao;
    public static volatile SingularAttribute<PreVenda, Integer> userPreVenda;
    public static volatile SingularAttribute<PreVenda, Integer> idOs;
    public static volatile SingularAttribute<PreVenda, Integer> avisoRecebimento;
    public static volatile SingularAttribute<PreVenda, Integer> registro;
    public static volatile SingularAttribute<PreVenda, String> observacoes;
    public static volatile SingularAttribute<PreVenda, String> nomeServico;
    public static volatile SingularAttribute<PreVenda, String> departamento;
    public static volatile SingularAttribute<PreVenda, Integer> idRemetente;
    public static volatile SingularAttribute<PreVenda, String> cartaoPostagem;
    public static volatile SingularAttribute<PreVenda, Integer> idDestinatario;

}