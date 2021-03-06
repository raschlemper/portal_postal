
package br.com.correios.bsb.sigep.master.bean.cliente;

import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.6-1b01 
 * Generated source version: 2.1
 * 
 */
@WebService(name = "AtendeCliente", targetNamespace = "http://cliente.bean.master.sigep.bsb.correios.com.br/")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface AtendeCliente {


    /**
     * 
     * @param usuario
     * @param xml
     * @param idPlpCliente
     * @param senha
     * @param faixaEtiquetas
     * @param cartaoPostagem
     * @return
     *     returns java.lang.Long
     * @throws SigepClienteException
     * @throws AutenticacaoException
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "fechaPlp", targetNamespace = "http://cliente.bean.master.sigep.bsb.correios.com.br/", className = "br.com.correios.bsb.sigep.master.bean.cliente.FechaPlp")
    @ResponseWrapper(localName = "fechaPlpResponse", targetNamespace = "http://cliente.bean.master.sigep.bsb.correios.com.br/", className = "br.com.correios.bsb.sigep.master.bean.cliente.FechaPlpResponse")
    public Long fechaPlp(
        @WebParam(name = "xml", targetNamespace = "")
        String xml,
        @WebParam(name = "idPlpCliente", targetNamespace = "")
        Long idPlpCliente,
        @WebParam(name = "cartaoPostagem", targetNamespace = "")
        String cartaoPostagem,
        @WebParam(name = "faixaEtiquetas", targetNamespace = "")
        String faixaEtiquetas,
        @WebParam(name = "usuario", targetNamespace = "")
        String usuario,
        @WebParam(name = "senha", targetNamespace = "")
        String senha)
        throws AutenticacaoException, SigepClienteException
    ;

    /**
     * 
     * @param usuario
     * @param pedidosInformacao
     * @param senha
     * @return
     *     returns java.util.List<br.com.correios.bsb.sigep.master.bean.cliente.Retorno>
     * @throws SigepClienteException
     * @throws AutenticacaoException
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "registrarPedidosInformacao", targetNamespace = "http://cliente.bean.master.sigep.bsb.correios.com.br/", className = "br.com.correios.bsb.sigep.master.bean.cliente.RegistrarPedidosInformacao")
    @ResponseWrapper(localName = "registrarPedidosInformacaoResponse", targetNamespace = "http://cliente.bean.master.sigep.bsb.correios.com.br/", className = "br.com.correios.bsb.sigep.master.bean.cliente.RegistrarPedidosInformacaoResponse")
    public List<Retorno> registrarPedidosInformacao(
        @WebParam(name = "pedidosInformacao", targetNamespace = "")
        List<PedidoInformacaoRegistro> pedidosInformacao,
        @WebParam(name = "usuario", targetNamespace = "")
        String usuario,
        @WebParam(name = "senha", targetNamespace = "")
        String senha)
        throws AutenticacaoException, SigepClienteException
    ;

    /**
     * 
     * @param usuario
     * @param idCartaoPostagem
     * @param idContrato
     * @param senha
     * @return
     *     returns br.com.correios.bsb.sigep.master.bean.cliente.ClienteERP
     * @throws SigepClienteException
     * @throws AutenticacaoException
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "buscaCliente", targetNamespace = "http://cliente.bean.master.sigep.bsb.correios.com.br/", className = "br.com.correios.bsb.sigep.master.bean.cliente.BuscaCliente")
    @ResponseWrapper(localName = "buscaClienteResponse", targetNamespace = "http://cliente.bean.master.sigep.bsb.correios.com.br/", className = "br.com.correios.bsb.sigep.master.bean.cliente.BuscaClienteResponse")
    public ClienteERP buscaCliente(
        @WebParam(name = "idContrato", targetNamespace = "")
        String idContrato,
        @WebParam(name = "idCartaoPostagem", targetNamespace = "")
        String idCartaoPostagem,
        @WebParam(name = "usuario", targetNamespace = "")
        String usuario,
        @WebParam(name = "senha", targetNamespace = "")
        String senha)
        throws AutenticacaoException, SigepClienteException
    ;

    /**
     * 
     * @param numeroEtiqueta
     * @param usuario
     * @param idPlp
     * @param senha
     * @return
     *     returns boolean
     * @throws SigepClienteException
     * @throws AutenticacaoException
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "validaEtiquetaPLP", targetNamespace = "http://cliente.bean.master.sigep.bsb.correios.com.br/", className = "br.com.correios.bsb.sigep.master.bean.cliente.ValidaEtiquetaPLP")
    @ResponseWrapper(localName = "validaEtiquetaPLPResponse", targetNamespace = "http://cliente.bean.master.sigep.bsb.correios.com.br/", className = "br.com.correios.bsb.sigep.master.bean.cliente.ValidaEtiquetaPLPResponse")
    public boolean validaEtiquetaPLP(
        @WebParam(name = "numeroEtiqueta", targetNamespace = "")
        String numeroEtiqueta,
        @WebParam(name = "idPlp", targetNamespace = "")
        Long idPlp,
        @WebParam(name = "usuario", targetNamespace = "")
        String usuario,
        @WebParam(name = "senha", targetNamespace = "")
        String senha)
        throws AutenticacaoException, SigepClienteException
    ;

    /**
     * 
     * @param codAdministrativo
     * @param usuario
     * @param cepOrigem
     * @param cepDestino
     * @param numeroServico
     * @param senha
     * @return
     *     returns boolean
     * @throws SigepClienteException
     * @throws AutenticacaoException
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "verificaDisponibilidadeServico", targetNamespace = "http://cliente.bean.master.sigep.bsb.correios.com.br/", className = "br.com.correios.bsb.sigep.master.bean.cliente.VerificaDisponibilidadeServico")
    @ResponseWrapper(localName = "verificaDisponibilidadeServicoResponse", targetNamespace = "http://cliente.bean.master.sigep.bsb.correios.com.br/", className = "br.com.correios.bsb.sigep.master.bean.cliente.VerificaDisponibilidadeServicoResponse")
    public boolean verificaDisponibilidadeServico(
        @WebParam(name = "codAdministrativo", targetNamespace = "")
        Integer codAdministrativo,
        @WebParam(name = "numeroServico", targetNamespace = "")
        String numeroServico,
        @WebParam(name = "cepOrigem", targetNamespace = "")
        String cepOrigem,
        @WebParam(name = "cepDestino", targetNamespace = "")
        String cepDestino,
        @WebParam(name = "usuario", targetNamespace = "")
        String usuario,
        @WebParam(name = "senha", targetNamespace = "")
        String senha)
        throws AutenticacaoException, SigepClienteException
    ;

    /**
     * 
     * @param arg1
     * @param arg0
     * @return
     *     returns br.com.correios.bsb.sigep.master.bean.cliente.StatusPlp
     * @throws SigepClienteException
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getStatusPLP", targetNamespace = "http://cliente.bean.master.sigep.bsb.correios.com.br/", className = "br.com.correios.bsb.sigep.master.bean.cliente.GetStatusPLP")
    @ResponseWrapper(localName = "getStatusPLPResponse", targetNamespace = "http://cliente.bean.master.sigep.bsb.correios.com.br/", className = "br.com.correios.bsb.sigep.master.bean.cliente.GetStatusPLPResponse")
    public StatusPlp getStatusPLP(
        @WebParam(name = "arg0", targetNamespace = "")
        List<ObjetoPostal> arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        String arg1)
        throws SigepClienteException
    ;

    /**
     * 
     * @param acao
     * @param numeroEtiqueta
     * @param tipoBloqueio
     * @param usuario
     * @param idPlp
     * @param senha
     * @return
     *     returns java.lang.String
     * @throws SigepClienteException
     * @throws AutenticacaoException
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "bloquearObjeto", targetNamespace = "http://cliente.bean.master.sigep.bsb.correios.com.br/", className = "br.com.correios.bsb.sigep.master.bean.cliente.BloquearObjeto")
    @ResponseWrapper(localName = "bloquearObjetoResponse", targetNamespace = "http://cliente.bean.master.sigep.bsb.correios.com.br/", className = "br.com.correios.bsb.sigep.master.bean.cliente.BloquearObjetoResponse")
    public String bloquearObjeto(
        @WebParam(name = "numeroEtiqueta", targetNamespace = "")
        String numeroEtiqueta,
        @WebParam(name = "idPlp", targetNamespace = "")
        Long idPlp,
        @WebParam(name = "tipoBloqueio", targetNamespace = "")
        TipoBloqueio tipoBloqueio,
        @WebParam(name = "acao", targetNamespace = "")
        Acao acao,
        @WebParam(name = "usuario", targetNamespace = "")
        String usuario,
        @WebParam(name = "senha", targetNamespace = "")
        String senha)
        throws AutenticacaoException, SigepClienteException
    ;

    /**
     * 
     * @param usuario
     * @param identificador
     * @param tipoDestinatario
     * @param qtdEtiquetas
     * @param idServico
     * @param senha
     * @return
     *     returns java.lang.String
     * @throws SigepClienteException
     * @throws AutenticacaoException
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "solicitaEtiquetas", targetNamespace = "http://cliente.bean.master.sigep.bsb.correios.com.br/", className = "br.com.correios.bsb.sigep.master.bean.cliente.SolicitaEtiquetas")
    @ResponseWrapper(localName = "solicitaEtiquetasResponse", targetNamespace = "http://cliente.bean.master.sigep.bsb.correios.com.br/", className = "br.com.correios.bsb.sigep.master.bean.cliente.SolicitaEtiquetasResponse")
    public String solicitaEtiquetas(
        @WebParam(name = "tipoDestinatario", targetNamespace = "")
        String tipoDestinatario,
        @WebParam(name = "identificador", targetNamespace = "")
        String identificador,
        @WebParam(name = "idServico", targetNamespace = "")
        Long idServico,
        @WebParam(name = "qtdEtiquetas", targetNamespace = "")
        Integer qtdEtiquetas,
        @WebParam(name = "usuario", targetNamespace = "")
        String usuario,
        @WebParam(name = "senha", targetNamespace = "")
        String senha)
        throws AutenticacaoException, SigepClienteException
    ;

    /**
     * 
     * @return
     *     returns java.util.List<br.com.correios.bsb.sigep.master.bean.cliente.MensagemRetornoPIMaster>
     * @throws SigepClienteException
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "obterMensagemRetornoPI", targetNamespace = "http://cliente.bean.master.sigep.bsb.correios.com.br/", className = "br.com.correios.bsb.sigep.master.bean.cliente.ObterMensagemRetornoPI")
    @ResponseWrapper(localName = "obterMensagemRetornoPIResponse", targetNamespace = "http://cliente.bean.master.sigep.bsb.correios.com.br/", className = "br.com.correios.bsb.sigep.master.bean.cliente.ObterMensagemRetornoPIResponse")
    public List<MensagemRetornoPIMaster> obterMensagemRetornoPI()
        throws SigepClienteException
    ;

    /**
     * 
     * @param usuario
     * @param pedidosInformacao
     * @param senha
     * @return
     *     returns java.util.List<br.com.correios.bsb.sigep.master.bean.cliente.Retorno>
     * @throws SigepClienteException
     * @throws AutenticacaoException
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "consultarPedidosInformacao", targetNamespace = "http://cliente.bean.master.sigep.bsb.correios.com.br/", className = "br.com.correios.bsb.sigep.master.bean.cliente.ConsultarPedidosInformacao")
    @ResponseWrapper(localName = "consultarPedidosInformacaoResponse", targetNamespace = "http://cliente.bean.master.sigep.bsb.correios.com.br/", className = "br.com.correios.bsb.sigep.master.bean.cliente.ConsultarPedidosInformacaoResponse")
    public List<Retorno> consultarPedidosInformacao(
        @WebParam(name = "pedidosInformacao", targetNamespace = "")
        List<PedidoInformacaoConsulta> pedidosInformacao,
        @WebParam(name = "usuario", targetNamespace = "")
        String usuario,
        @WebParam(name = "senha", targetNamespace = "")
        String senha)
        throws AutenticacaoException, SigepClienteException
    ;

    /**
     * 
     * @param usuario
     * @param senha
     * @return
     *     returns java.lang.String
     * @throws SigepClienteException
     * @throws AutenticacaoException
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "buscaPagamentoEntrega", targetNamespace = "http://cliente.bean.master.sigep.bsb.correios.com.br/", className = "br.com.correios.bsb.sigep.master.bean.cliente.BuscaPagamentoEntrega")
    @ResponseWrapper(localName = "buscaPagamentoEntregaResponse", targetNamespace = "http://cliente.bean.master.sigep.bsb.correios.com.br/", className = "br.com.correios.bsb.sigep.master.bean.cliente.BuscaPagamentoEntregaResponse")
    public String buscaPagamentoEntrega(
        @WebParam(name = "usuario", targetNamespace = "")
        String usuario,
        @WebParam(name = "senha", targetNamespace = "")
        String senha)
        throws AutenticacaoException, SigepClienteException
    ;

    /**
     * 
     * @param usuario
     * @param etiquetas
     * @param senha
     * @return
     *     returns java.util.List<java.lang.Integer>
     * @throws SigepClienteException
     * @throws AutenticacaoException
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "geraDigitoVerificadorEtiquetas", targetNamespace = "http://cliente.bean.master.sigep.bsb.correios.com.br/", className = "br.com.correios.bsb.sigep.master.bean.cliente.GeraDigitoVerificadorEtiquetas")
    @ResponseWrapper(localName = "geraDigitoVerificadorEtiquetasResponse", targetNamespace = "http://cliente.bean.master.sigep.bsb.correios.com.br/", className = "br.com.correios.bsb.sigep.master.bean.cliente.GeraDigitoVerificadorEtiquetasResponse")
    public List<Integer> geraDigitoVerificadorEtiquetas(
        @WebParam(name = "etiquetas", targetNamespace = "")
        List<String> etiquetas,
        @WebParam(name = "usuario", targetNamespace = "")
        String usuario,
        @WebParam(name = "senha", targetNamespace = "")
        String senha)
        throws AutenticacaoException, SigepClienteException
    ;

    /**
     * 
     * @param numeroEtiqueta
     * @param usuario
     * @param idPlp
     * @param senha
     * @return
     *     returns java.lang.Boolean
     * @throws SigepClienteException
     * @throws Exception_Exception
     * @throws AutenticacaoException
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "cancelarObjeto", targetNamespace = "http://cliente.bean.master.sigep.bsb.correios.com.br/", className = "br.com.correios.bsb.sigep.master.bean.cliente.CancelarObjeto")
    @ResponseWrapper(localName = "cancelarObjetoResponse", targetNamespace = "http://cliente.bean.master.sigep.bsb.correios.com.br/", className = "br.com.correios.bsb.sigep.master.bean.cliente.CancelarObjetoResponse")
    public Boolean cancelarObjeto(
        @WebParam(name = "idPlp", targetNamespace = "")
        Long idPlp,
        @WebParam(name = "numeroEtiqueta", targetNamespace = "")
        String numeroEtiqueta,
        @WebParam(name = "usuario", targetNamespace = "")
        String usuario,
        @WebParam(name = "senha", targetNamespace = "")
        String senha)
        throws AutenticacaoException, Exception_Exception, SigepClienteException
    ;

    /**
     * 
     * @param usuario
     * @param listaEtiquetas
     * @param xml
     * @param idPlpCliente
     * @param senha
     * @param cartaoPostagem
     * @return
     *     returns java.lang.Long
     * @throws SigepClienteException
     * @throws AutenticacaoException
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "fechaPlpVariosServicos", targetNamespace = "http://cliente.bean.master.sigep.bsb.correios.com.br/", className = "br.com.correios.bsb.sigep.master.bean.cliente.FechaPlpVariosServicos")
    @ResponseWrapper(localName = "fechaPlpVariosServicosResponse", targetNamespace = "http://cliente.bean.master.sigep.bsb.correios.com.br/", className = "br.com.correios.bsb.sigep.master.bean.cliente.FechaPlpVariosServicosResponse")
    public Long fechaPlpVariosServicos(
        @WebParam(name = "xml", targetNamespace = "")
        String xml,
        @WebParam(name = "idPlpCliente", targetNamespace = "")
        Long idPlpCliente,
        @WebParam(name = "cartaoPostagem", targetNamespace = "")
        String cartaoPostagem,
        @WebParam(name = "listaEtiquetas", targetNamespace = "")
        List<String> listaEtiquetas,
        @WebParam(name = "usuario", targetNamespace = "")
        String usuario,
        @WebParam(name = "senha", targetNamespace = "")
        String senha)
        throws AutenticacaoException, SigepClienteException
    ;

    /**
     * 
     * @param codigoServico
     * @param codAdministrativo
     * @param usuario
     * @param coleta
     * @param senha
     * @param cepDestinatario
     * @return
     *     returns java.lang.Boolean
     * @throws SigepClienteException
     * @throws AutenticacaoException
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "validarPostagemReversa", targetNamespace = "http://cliente.bean.master.sigep.bsb.correios.com.br/", className = "br.com.correios.bsb.sigep.master.bean.cliente.ValidarPostagemReversa")
    @ResponseWrapper(localName = "validarPostagemReversaResponse", targetNamespace = "http://cliente.bean.master.sigep.bsb.correios.com.br/", className = "br.com.correios.bsb.sigep.master.bean.cliente.ValidarPostagemReversaResponse")
    public Boolean validarPostagemReversa(
        @WebParam(name = "codAdministrativo", targetNamespace = "")
        Integer codAdministrativo,
        @WebParam(name = "codigoServico", targetNamespace = "")
        Integer codigoServico,
        @WebParam(name = "cepDestinatario", targetNamespace = "")
        String cepDestinatario,
        @WebParam(name = "coleta", targetNamespace = "")
        ColetaReversaTO coleta,
        @WebParam(name = "usuario", targetNamespace = "")
        String usuario,
        @WebParam(name = "senha", targetNamespace = "")
        String senha)
        throws AutenticacaoException, SigepClienteException
    ;

    /**
     * 
     * @param servicosAdicionais
     * @param servico
     * @param usuario
     * @param cliente
     * @param unidadePostagem
     * @param cartao
     * @param senha
     * @param diretoria
     * @param numero
     * @return
     *     returns boolean
     * @throws SigepClienteException
     * @throws AutenticacaoException
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "validaPlp", targetNamespace = "http://cliente.bean.master.sigep.bsb.correios.com.br/", className = "br.com.correios.bsb.sigep.master.bean.cliente.ValidaPlp")
    @ResponseWrapper(localName = "validaPlpResponse", targetNamespace = "http://cliente.bean.master.sigep.bsb.correios.com.br/", className = "br.com.correios.bsb.sigep.master.bean.cliente.ValidaPlpResponse")
    public boolean validaPlp(
        @WebParam(name = "cliente", targetNamespace = "")
        long cliente,
        @WebParam(name = "numero", targetNamespace = "")
        String numero,
        @WebParam(name = "diretoria", targetNamespace = "")
        long diretoria,
        @WebParam(name = "cartao", targetNamespace = "")
        String cartao,
        @WebParam(name = "unidadePostagem", targetNamespace = "")
        String unidadePostagem,
        @WebParam(name = "servico", targetNamespace = "")
        Long servico,
        @WebParam(name = "servicosAdicionais", targetNamespace = "")
        List<String> servicosAdicionais,
        @WebParam(name = "usuario", targetNamespace = "")
        String usuario,
        @WebParam(name = "senha", targetNamespace = "")
        String senha)
        throws AutenticacaoException, SigepClienteException
    ;

    /**
     * 
     * @param codAdministrativo
     * @param usuario
     * @param tipo
     * @param senha
     * @param idPostagem
     * @return
     *     returns br.com.correios.bsb.sigep.master.bean.cliente.RetornoCancelamentoTO
     * @throws SigepClienteException
     * @throws AutenticacaoException
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "cancelarPedidoScol", targetNamespace = "http://cliente.bean.master.sigep.bsb.correios.com.br/", className = "br.com.correios.bsb.sigep.master.bean.cliente.CancelarPedidoScol")
    @ResponseWrapper(localName = "cancelarPedidoScolResponse", targetNamespace = "http://cliente.bean.master.sigep.bsb.correios.com.br/", className = "br.com.correios.bsb.sigep.master.bean.cliente.CancelarPedidoScolResponse")
    public RetornoCancelamentoTO cancelarPedidoScol(
        @WebParam(name = "codAdministrativo", targetNamespace = "")
        Integer codAdministrativo,
        @WebParam(name = "idPostagem", targetNamespace = "")
        String idPostagem,
        @WebParam(name = "tipo", targetNamespace = "")
        String tipo,
        @WebParam(name = "usuario", targetNamespace = "")
        String usuario,
        @WebParam(name = "senha", targetNamespace = "")
        String senha)
        throws AutenticacaoException, SigepClienteException
    ;

    /**
     * 
     * @return
     *     returns java.util.List<br.com.correios.bsb.sigep.master.bean.cliente.EmbalagemLRSMaster>
     * @throws SigepClienteException
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "obterEmbalagemLRS", targetNamespace = "http://cliente.bean.master.sigep.bsb.correios.com.br/", className = "br.com.correios.bsb.sigep.master.bean.cliente.ObterEmbalagemLRS")
    @ResponseWrapper(localName = "obterEmbalagemLRSResponse", targetNamespace = "http://cliente.bean.master.sigep.bsb.correios.com.br/", className = "br.com.correios.bsb.sigep.master.bean.cliente.ObterEmbalagemLRSResponse")
    public List<EmbalagemLRSMaster> obterEmbalagemLRS()
        throws SigepClienteException
    ;

    /**
     * 
     * @param codigoServico
     * @param codAdministrativo
     * @param usuario
     * @param coleta
     * @param senha
     * @param cepDestinatario
     * @return
     *     returns java.lang.Boolean
     * @throws SigepClienteException
     * @throws AutenticacaoException
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "validarPostagemSimultanea", targetNamespace = "http://cliente.bean.master.sigep.bsb.correios.com.br/", className = "br.com.correios.bsb.sigep.master.bean.cliente.ValidarPostagemSimultanea")
    @ResponseWrapper(localName = "validarPostagemSimultaneaResponse", targetNamespace = "http://cliente.bean.master.sigep.bsb.correios.com.br/", className = "br.com.correios.bsb.sigep.master.bean.cliente.ValidarPostagemSimultaneaResponse")
    public Boolean validarPostagemSimultanea(
        @WebParam(name = "codAdministrativo", targetNamespace = "")
        Integer codAdministrativo,
        @WebParam(name = "codigoServico", targetNamespace = "")
        Integer codigoServico,
        @WebParam(name = "cepDestinatario", targetNamespace = "")
        String cepDestinatario,
        @WebParam(name = "coleta", targetNamespace = "")
        ColetaSimultaneaTO coleta,
        @WebParam(name = "usuario", targetNamespace = "")
        String usuario,
        @WebParam(name = "senha", targetNamespace = "")
        String senha)
        throws AutenticacaoException, SigepClienteException
    ;

    /**
     * 
     * @param usuario
     * @param idCartaoPostagem
     * @param idContrato
     * @param senha
     * @return
     *     returns java.util.List<br.com.correios.bsb.sigep.master.bean.cliente.ServicoERP>
     * @throws SigepClienteException
     * @throws AutenticacaoException
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "buscaServicos", targetNamespace = "http://cliente.bean.master.sigep.bsb.correios.com.br/", className = "br.com.correios.bsb.sigep.master.bean.cliente.BuscaServicos")
    @ResponseWrapper(localName = "buscaServicosResponse", targetNamespace = "http://cliente.bean.master.sigep.bsb.correios.com.br/", className = "br.com.correios.bsb.sigep.master.bean.cliente.BuscaServicosResponse")
    public List<ServicoERP> buscaServicos(
        @WebParam(name = "idContrato", targetNamespace = "")
        String idContrato,
        @WebParam(name = "idCartaoPostagem", targetNamespace = "")
        String idCartaoPostagem,
        @WebParam(name = "usuario", targetNamespace = "")
        String usuario,
        @WebParam(name = "senha", targetNamespace = "")
        String senha)
        throws AutenticacaoException, SigepClienteException
    ;

    /**
     * 
     * @param codAdministrativo
     * @param usuario
     * @param xml
     * @param senha
     * @return
     *     returns java.lang.String
     * @throws SigepClienteException
     * @throws AutenticacaoException
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "solicitarPostagemScol", targetNamespace = "http://cliente.bean.master.sigep.bsb.correios.com.br/", className = "br.com.correios.bsb.sigep.master.bean.cliente.SolicitarPostagemScol")
    @ResponseWrapper(localName = "solicitarPostagemScolResponse", targetNamespace = "http://cliente.bean.master.sigep.bsb.correios.com.br/", className = "br.com.correios.bsb.sigep.master.bean.cliente.SolicitarPostagemScolResponse")
    public String solicitarPostagemScol(
        @WebParam(name = "codAdministrativo", targetNamespace = "")
        Integer codAdministrativo,
        @WebParam(name = "xml", targetNamespace = "")
        String xml,
        @WebParam(name = "usuario", targetNamespace = "")
        String usuario,
        @WebParam(name = "senha", targetNamespace = "")
        String senha)
        throws AutenticacaoException, SigepClienteException
    ;

    /**
     * 
     * @param numeroCartaoPostagem
     * @param usuario
     * @param senha
     * @return
     *     returns br.com.correios.bsb.sigep.master.bean.cliente.StatusCartao
     * @throws SigepClienteException
     * @throws AutenticacaoException
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getStatusCartaoPostagem", targetNamespace = "http://cliente.bean.master.sigep.bsb.correios.com.br/", className = "br.com.correios.bsb.sigep.master.bean.cliente.GetStatusCartaoPostagem")
    @ResponseWrapper(localName = "getStatusCartaoPostagemResponse", targetNamespace = "http://cliente.bean.master.sigep.bsb.correios.com.br/", className = "br.com.correios.bsb.sigep.master.bean.cliente.GetStatusCartaoPostagemResponse")
    public StatusCartao getStatusCartaoPostagem(
        @WebParam(name = "numeroCartaoPostagem", targetNamespace = "")
        String numeroCartaoPostagem,
        @WebParam(name = "usuario", targetNamespace = "")
        String usuario,
        @WebParam(name = "senha", targetNamespace = "")
        String senha)
        throws AutenticacaoException, SigepClienteException
    ;

    /**
     * 
     * @param usuario
     * @param idPlpMaster
     * @param senha
     * @param numEtiqueta
     * @return
     *     returns java.lang.String
     * @throws SigepClienteException
     * @throws AutenticacaoException
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "solicitaPLP", targetNamespace = "http://cliente.bean.master.sigep.bsb.correios.com.br/", className = "br.com.correios.bsb.sigep.master.bean.cliente.SolicitaPLP")
    @ResponseWrapper(localName = "solicitaPLPResponse", targetNamespace = "http://cliente.bean.master.sigep.bsb.correios.com.br/", className = "br.com.correios.bsb.sigep.master.bean.cliente.SolicitaPLPResponse")
    public String solicitaPLP(
        @WebParam(name = "idPlpMaster", targetNamespace = "")
        Long idPlpMaster,
        @WebParam(name = "numEtiqueta", targetNamespace = "")
        String numEtiqueta,
        @WebParam(name = "usuario", targetNamespace = "")
        String usuario,
        @WebParam(name = "senha", targetNamespace = "")
        String senha)
        throws AutenticacaoException, SigepClienteException
    ;

    /**
     * 
     * @param usuario
     * @param idPlpMaster
     * @param senha
     * @return
     *     returns java.lang.String
     * @throws SigepClienteException
     * @throws AutenticacaoException
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "solicitaXmlPlp", targetNamespace = "http://cliente.bean.master.sigep.bsb.correios.com.br/", className = "br.com.correios.bsb.sigep.master.bean.cliente.SolicitaXmlPlp")
    @ResponseWrapper(localName = "solicitaXmlPlpResponse", targetNamespace = "http://cliente.bean.master.sigep.bsb.correios.com.br/", className = "br.com.correios.bsb.sigep.master.bean.cliente.SolicitaXmlPlpResponse")
    public String solicitaXmlPlp(
        @WebParam(name = "idPlpMaster", targetNamespace = "")
        Long idPlpMaster,
        @WebParam(name = "usuario", targetNamespace = "")
        String usuario,
        @WebParam(name = "senha", targetNamespace = "")
        String senha)
        throws AutenticacaoException, SigepClienteException
    ;

    /**
     * 
     * @return
     *     returns java.util.List<br.com.correios.bsb.sigep.master.bean.cliente.MotivoPIMaster>
     * @throws SigepClienteException
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "obterMotivosPI", targetNamespace = "http://cliente.bean.master.sigep.bsb.correios.com.br/", className = "br.com.correios.bsb.sigep.master.bean.cliente.ObterMotivosPI")
    @ResponseWrapper(localName = "obterMotivosPIResponse", targetNamespace = "http://cliente.bean.master.sigep.bsb.correios.com.br/", className = "br.com.correios.bsb.sigep.master.bean.cliente.ObterMotivosPIResponse")
    public List<MotivoPIMaster> obterMotivosPI()
        throws SigepClienteException
    ;

    /**
     * 
     * @param usuario
     * @param senha
     * @param diretoria
     * @param numero
     * @return
     *     returns br.com.correios.bsb.sigep.master.bean.cliente.ContratoERP
     * @throws SigepClienteException
     * @throws AutenticacaoException
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "buscaContrato", targetNamespace = "http://cliente.bean.master.sigep.bsb.correios.com.br/", className = "br.com.correios.bsb.sigep.master.bean.cliente.BuscaContrato")
    @ResponseWrapper(localName = "buscaContratoResponse", targetNamespace = "http://cliente.bean.master.sigep.bsb.correios.com.br/", className = "br.com.correios.bsb.sigep.master.bean.cliente.BuscaContratoResponse")
    public ContratoERP buscaContrato(
        @WebParam(name = "numero", targetNamespace = "")
        String numero,
        @WebParam(name = "diretoria", targetNamespace = "")
        long diretoria,
        @WebParam(name = "usuario", targetNamespace = "")
        String usuario,
        @WebParam(name = "senha", targetNamespace = "")
        String senha)
        throws AutenticacaoException, SigepClienteException
    ;

    /**
     * 
     * @param tipoResultado
     * @param listaObjetos
     * @param senhaSro
     * @param usuarioSro
     * @param tipoConsulta
     * @return
     *     returns java.lang.String
     * @throws SigepClienteException
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "consultaSRO", targetNamespace = "http://cliente.bean.master.sigep.bsb.correios.com.br/", className = "br.com.correios.bsb.sigep.master.bean.cliente.ConsultaSRO")
    @ResponseWrapper(localName = "consultaSROResponse", targetNamespace = "http://cliente.bean.master.sigep.bsb.correios.com.br/", className = "br.com.correios.bsb.sigep.master.bean.cliente.ConsultaSROResponse")
    public String consultaSRO(
        @WebParam(name = "listaObjetos", targetNamespace = "")
        List<String> listaObjetos,
        @WebParam(name = "tipoConsulta", targetNamespace = "")
        String tipoConsulta,
        @WebParam(name = "tipoResultado", targetNamespace = "")
        String tipoResultado,
        @WebParam(name = "usuarioSro", targetNamespace = "")
        String usuarioSro,
        @WebParam(name = "senhaSro", targetNamespace = "")
        String senhaSro)
        throws SigepClienteException
    ;

    /**
     * 
     * @param cnpjCliente
     * @param usuario
     * @param senha
     * @return
     *     returns javax.xml.datatype.XMLGregorianCalendar
     * @throws SigepClienteException
     * @throws AutenticacaoException
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "obterClienteAtualizacao", targetNamespace = "http://cliente.bean.master.sigep.bsb.correios.com.br/", className = "br.com.correios.bsb.sigep.master.bean.cliente.ObterClienteAtualizacao")
    @ResponseWrapper(localName = "obterClienteAtualizacaoResponse", targetNamespace = "http://cliente.bean.master.sigep.bsb.correios.com.br/", className = "br.com.correios.bsb.sigep.master.bean.cliente.ObterClienteAtualizacaoResponse")
    public XMLGregorianCalendar obterClienteAtualizacao(
        @WebParam(name = "cnpjCliente", targetNamespace = "")
        String cnpjCliente,
        @WebParam(name = "usuario", targetNamespace = "")
        String usuario,
        @WebParam(name = "senha", targetNamespace = "")
        String senha)
        throws AutenticacaoException, SigepClienteException
    ;

    /**
     * 
     * @param codAdministrativo
     * @param usuario
     * @param senha
     * @return
     *     returns java.lang.Boolean
     * @throws SigepClienteException
     * @throws AutenticacaoException
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "integrarUsuarioScol", targetNamespace = "http://cliente.bean.master.sigep.bsb.correios.com.br/", className = "br.com.correios.bsb.sigep.master.bean.cliente.IntegrarUsuarioScol")
    @ResponseWrapper(localName = "integrarUsuarioScolResponse", targetNamespace = "http://cliente.bean.master.sigep.bsb.correios.com.br/", className = "br.com.correios.bsb.sigep.master.bean.cliente.IntegrarUsuarioScolResponse")
    public Boolean integrarUsuarioScol(
        @WebParam(name = "codAdministrativo", targetNamespace = "")
        Integer codAdministrativo,
        @WebParam(name = "usuario", targetNamespace = "")
        String usuario,
        @WebParam(name = "senha", targetNamespace = "")
        String senha)
        throws AutenticacaoException, SigepClienteException
    ;

    /**
     * 
     * @param usuario
     * @param idPlpMaster
     * @param xml
     * @param senha
     * @param numEtiqueta
     * @return
     *     returns boolean
     * @throws SigepClienteException
     * @throws AutenticacaoException
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "atualizaPLP", targetNamespace = "http://cliente.bean.master.sigep.bsb.correios.com.br/", className = "br.com.correios.bsb.sigep.master.bean.cliente.AtualizaPLP")
    @ResponseWrapper(localName = "atualizaPLPResponse", targetNamespace = "http://cliente.bean.master.sigep.bsb.correios.com.br/", className = "br.com.correios.bsb.sigep.master.bean.cliente.AtualizaPLPResponse")
    public boolean atualizaPLP(
        @WebParam(name = "idPlpMaster", targetNamespace = "")
        Long idPlpMaster,
        @WebParam(name = "numEtiqueta", targetNamespace = "")
        String numEtiqueta,
        @WebParam(name = "usuario", targetNamespace = "")
        String usuario,
        @WebParam(name = "senha", targetNamespace = "")
        String senha,
        @WebParam(name = "xml", targetNamespace = "")
        String xml)
        throws AutenticacaoException, SigepClienteException
    ;

    /**
     * 
     * @return
     *     returns java.util.List<br.com.correios.bsb.sigep.master.bean.cliente.AssuntoPIMaster>
     * @throws SigepClienteException
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "obterAssuntosPI", targetNamespace = "http://cliente.bean.master.sigep.bsb.correios.com.br/", className = "br.com.correios.bsb.sigep.master.bean.cliente.ObterAssuntosPI")
    @ResponseWrapper(localName = "obterAssuntosPIResponse", targetNamespace = "http://cliente.bean.master.sigep.bsb.correios.com.br/", className = "br.com.correios.bsb.sigep.master.bean.cliente.ObterAssuntosPIResponse")
    public List<AssuntoPIMaster> obterAssuntosPI()
        throws SigepClienteException
    ;

    /**
     * 
     * @param cep
     * @return
     *     returns br.com.correios.bsb.sigep.master.bean.cliente.EnderecoERP
     * @throws SigepClienteException
     * @throws SQLException_Exception
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "consultaCEP", targetNamespace = "http://cliente.bean.master.sigep.bsb.correios.com.br/", className = "br.com.correios.bsb.sigep.master.bean.cliente.ConsultaCEP")
    @ResponseWrapper(localName = "consultaCEPResponse", targetNamespace = "http://cliente.bean.master.sigep.bsb.correios.com.br/", className = "br.com.correios.bsb.sigep.master.bean.cliente.ConsultaCEPResponse")
    public EnderecoERP consultaCEP(
        @WebParam(name = "cep", targetNamespace = "")
        String cep)
        throws SQLException_Exception, SigepClienteException
    ;

}
