
package br.com.correios.logisticareversa.service;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the br.com.correios.logisticareversa.service package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _AcompanharPedidoResponse_QNAME = new QName("http://service.logisticareversa.correios.com.br/", "acompanharPedidoResponse");
    private final static QName _SobreWebServiceResponse_QNAME = new QName("http://service.logisticareversa.correios.com.br/", "sobreWebServiceResponse");
    private final static QName _SolicitarPostagemReversa_QNAME = new QName("http://service.logisticareversa.correios.com.br/", "solicitarPostagemReversa");
    private final static QName _SolicitarPostagemSimultanea_QNAME = new QName("http://service.logisticareversa.correios.com.br/", "solicitarPostagemSimultanea");
    private final static QName _CalcularDigitoVerificador_QNAME = new QName("http://service.logisticareversa.correios.com.br/", "calcularDigitoVerificador");
    private final static QName _SolicitarRange_QNAME = new QName("http://service.logisticareversa.correios.com.br/", "solicitarRange");
    private final static QName _RevalidarPrazoAutorizacaoPostagem_QNAME = new QName("http://service.logisticareversa.correios.com.br/", "revalidarPrazoAutorizacaoPostagem");
    private final static QName _AcompanharPedidoPorData_QNAME = new QName("http://service.logisticareversa.correios.com.br/", "acompanharPedidoPorData");
    private final static QName _SolicitarRangeResponse_QNAME = new QName("http://service.logisticareversa.correios.com.br/", "solicitarRangeResponse");
    private final static QName _WebServiceFaultInfo_QNAME = new QName("http://service.logisticareversa.correios.com.br/", "WebServiceFaultInfo");
    private final static QName _RevalidarPrazoAutorizacaoPostagemResponse_QNAME = new QName("http://service.logisticareversa.correios.com.br/", "revalidarPrazoAutorizacaoPostagemResponse");
    private final static QName _ValidarPostagemSimultanea_QNAME = new QName("http://service.logisticareversa.correios.com.br/", "validarPostagemSimultanea");
    private final static QName _ValidarPostagemReversa_QNAME = new QName("http://service.logisticareversa.correios.com.br/", "validarPostagemReversa");
    private final static QName _CalcularDigitoVerificadorResponse_QNAME = new QName("http://service.logisticareversa.correios.com.br/", "calcularDigitoVerificadorResponse");
    private final static QName _AcompanharPedidoPorDataResponse_QNAME = new QName("http://service.logisticareversa.correios.com.br/", "acompanharPedidoPorDataResponse");
    private final static QName _SolicitarPostagemReversaResponse_QNAME = new QName("http://service.logisticareversa.correios.com.br/", "solicitarPostagemReversaResponse");
    private final static QName _CancelarPedidoResponse_QNAME = new QName("http://service.logisticareversa.correios.com.br/", "cancelarPedidoResponse");
    private final static QName _ValidarPostagemReversaResponse_QNAME = new QName("http://service.logisticareversa.correios.com.br/", "validarPostagemReversaResponse");
    private final static QName _SolicitarPostagemSimultaneaResponse_QNAME = new QName("http://service.logisticareversa.correios.com.br/", "solicitarPostagemSimultaneaResponse");
    private final static QName _CancelarPedido_QNAME = new QName("http://service.logisticareversa.correios.com.br/", "cancelarPedido");
    private final static QName _AcompanharPedido_QNAME = new QName("http://service.logisticareversa.correios.com.br/", "acompanharPedido");
    private final static QName _SobreWebService_QNAME = new QName("http://service.logisticareversa.correios.com.br/", "sobreWebService");
    private final static QName _ValidarPostagemSimultaneaResponse_QNAME = new QName("http://service.logisticareversa.correios.com.br/", "validarPostagemSimultaneaResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: br.com.correios.logisticareversa.service
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ValidarPostagemReversaResponse }
     * 
     */
    public ValidarPostagemReversaResponse createValidarPostagemReversaResponse() {
        return new ValidarPostagemReversaResponse();
    }

    /**
     * Create an instance of {@link AcompanharPedido }
     * 
     */
    public AcompanharPedido createAcompanharPedido() {
        return new AcompanharPedido();
    }

    /**
     * Create an instance of {@link SobreWebService }
     * 
     */
    public SobreWebService createSobreWebService() {
        return new SobreWebService();
    }

    /**
     * Create an instance of {@link SolicitarPostagemSimultaneaResponse }
     * 
     */
    public SolicitarPostagemSimultaneaResponse createSolicitarPostagemSimultaneaResponse() {
        return new SolicitarPostagemSimultaneaResponse();
    }

    /**
     * Create an instance of {@link CancelarPedido }
     * 
     */
    public CancelarPedido createCancelarPedido() {
        return new CancelarPedido();
    }

    /**
     * Create an instance of {@link ValidarPostagemSimultaneaResponse }
     * 
     */
    public ValidarPostagemSimultaneaResponse createValidarPostagemSimultaneaResponse() {
        return new ValidarPostagemSimultaneaResponse();
    }

    /**
     * Create an instance of {@link ValidarPostagemSimultanea }
     * 
     */
    public ValidarPostagemSimultanea createValidarPostagemSimultanea() {
        return new ValidarPostagemSimultanea();
    }

    /**
     * Create an instance of {@link AcompanharPedidoPorDataResponse }
     * 
     */
    public AcompanharPedidoPorDataResponse createAcompanharPedidoPorDataResponse() {
        return new AcompanharPedidoPorDataResponse();
    }

    /**
     * Create an instance of {@link ValidarPostagemReversa }
     * 
     */
    public ValidarPostagemReversa createValidarPostagemReversa() {
        return new ValidarPostagemReversa();
    }

    /**
     * Create an instance of {@link CalcularDigitoVerificadorResponse }
     * 
     */
    public CalcularDigitoVerificadorResponse createCalcularDigitoVerificadorResponse() {
        return new CalcularDigitoVerificadorResponse();
    }

    /**
     * Create an instance of {@link SolicitarPostagemReversaResponse }
     * 
     */
    public SolicitarPostagemReversaResponse createSolicitarPostagemReversaResponse() {
        return new SolicitarPostagemReversaResponse();
    }

    /**
     * Create an instance of {@link CancelarPedidoResponse }
     * 
     */
    public CancelarPedidoResponse createCancelarPedidoResponse() {
        return new CancelarPedidoResponse();
    }

    /**
     * Create an instance of {@link RevalidarPrazoAutorizacaoPostagem }
     * 
     */
    public RevalidarPrazoAutorizacaoPostagem createRevalidarPrazoAutorizacaoPostagem() {
        return new RevalidarPrazoAutorizacaoPostagem();
    }

    /**
     * Create an instance of {@link SolicitarRangeResponse }
     * 
     */
    public SolicitarRangeResponse createSolicitarRangeResponse() {
        return new SolicitarRangeResponse();
    }

    /**
     * Create an instance of {@link AcompanharPedidoPorData }
     * 
     */
    public AcompanharPedidoPorData createAcompanharPedidoPorData() {
        return new AcompanharPedidoPorData();
    }

    /**
     * Create an instance of {@link WebServiceFaultInfo }
     * 
     */
    public WebServiceFaultInfo createWebServiceFaultInfo() {
        return new WebServiceFaultInfo();
    }

    /**
     * Create an instance of {@link RevalidarPrazoAutorizacaoPostagemResponse }
     * 
     */
    public RevalidarPrazoAutorizacaoPostagemResponse createRevalidarPrazoAutorizacaoPostagemResponse() {
        return new RevalidarPrazoAutorizacaoPostagemResponse();
    }

    /**
     * Create an instance of {@link SolicitarPostagemSimultanea }
     * 
     */
    public SolicitarPostagemSimultanea createSolicitarPostagemSimultanea() {
        return new SolicitarPostagemSimultanea();
    }

    /**
     * Create an instance of {@link SolicitarPostagemReversa }
     * 
     */
    public SolicitarPostagemReversa createSolicitarPostagemReversa() {
        return new SolicitarPostagemReversa();
    }

    /**
     * Create an instance of {@link SobreWebServiceResponse }
     * 
     */
    public SobreWebServiceResponse createSobreWebServiceResponse() {
        return new SobreWebServiceResponse();
    }

    /**
     * Create an instance of {@link AcompanharPedidoResponse }
     * 
     */
    public AcompanharPedidoResponse createAcompanharPedidoResponse() {
        return new AcompanharPedidoResponse();
    }

    /**
     * Create an instance of {@link SolicitarRange }
     * 
     */
    public SolicitarRange createSolicitarRange() {
        return new SolicitarRange();
    }

    /**
     * Create an instance of {@link CalcularDigitoVerificador }
     * 
     */
    public CalcularDigitoVerificador createCalcularDigitoVerificador() {
        return new CalcularDigitoVerificador();
    }

    /**
     * Create an instance of {@link RetornoRevalidarPrazo }
     * 
     */
    public RetornoRevalidarPrazo createRetornoRevalidarPrazo() {
        return new RetornoRevalidarPrazo();
    }

    /**
     * Create an instance of {@link Produto }
     * 
     */
    public Produto createProduto() {
        return new Produto();
    }

    /**
     * Create an instance of {@link ColetaSimultanea }
     * 
     */
    public ColetaSimultanea createColetaSimultanea() {
        return new ColetaSimultanea();
    }

    /**
     * Create an instance of {@link ObjetoPostal }
     * 
     */
    public ObjetoPostal createObjetoPostal() {
        return new ObjetoPostal();
    }

    /**
     * Create an instance of {@link RetornoSobreWebService }
     * 
     */
    public RetornoSobreWebService createRetornoSobreWebService() {
        return new RetornoSobreWebService();
    }

    /**
     * Create an instance of {@link Objeto }
     * 
     */
    public Objeto createObjeto() {
        return new Objeto();
    }

    /**
     * Create an instance of {@link ColetasSolicitadas }
     * 
     */
    public ColetasSolicitadas createColetasSolicitadas() {
        return new ColetasSolicitadas();
    }

    /**
     * Create an instance of {@link HistoricoColeta }
     * 
     */
    public HistoricoColeta createHistoricoColeta() {
        return new HistoricoColeta();
    }

    /**
     * Create an instance of {@link Coleta }
     * 
     */
    public Coleta createColeta() {
        return new Coleta();
    }

    /**
     * Create an instance of {@link RetornoAcompanhamento }
     * 
     */
    public RetornoAcompanhamento createRetornoAcompanhamento() {
        return new RetornoAcompanhamento();
    }

    /**
     * Create an instance of {@link Remetente }
     * 
     */
    public Remetente createRemetente() {
        return new Remetente();
    }

    /**
     * Create an instance of {@link RetornoDigitoVerificador }
     * 
     */
    public RetornoDigitoVerificador createRetornoDigitoVerificador() {
        return new RetornoDigitoVerificador();
    }

    /**
     * Create an instance of {@link ObjetoSimplificado }
     * 
     */
    public ObjetoSimplificado createObjetoSimplificado() {
        return new ObjetoSimplificado();
    }

    /**
     * Create an instance of {@link Pessoa }
     * 
     */
    public Pessoa createPessoa() {
        return new Pessoa();
    }

    /**
     * Create an instance of {@link RetornoCancelamento }
     * 
     */
    public RetornoCancelamento createRetornoCancelamento() {
        return new RetornoCancelamento();
    }

    /**
     * Create an instance of {@link ResultadoSolicitacao }
     * 
     */
    public ResultadoSolicitacao createResultadoSolicitacao() {
        return new ResultadoSolicitacao();
    }

    /**
     * Create an instance of {@link RetornoValidacao }
     * 
     */
    public RetornoValidacao createRetornoValidacao() {
        return new RetornoValidacao();
    }

    /**
     * Create an instance of {@link RetornoFaixaNumerica }
     * 
     */
    public RetornoFaixaNumerica createRetornoFaixaNumerica() {
        return new RetornoFaixaNumerica();
    }

    /**
     * Create an instance of {@link ColetaReversa }
     * 
     */
    public ColetaReversa createColetaReversa() {
        return new ColetaReversa();
    }

    /**
     * Create an instance of {@link RetornoPostagem }
     * 
     */
    public RetornoPostagem createRetornoPostagem() {
        return new RetornoPostagem();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AcompanharPedidoResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.logisticareversa.correios.com.br/", name = "acompanharPedidoResponse")
    public JAXBElement<AcompanharPedidoResponse> createAcompanharPedidoResponse(AcompanharPedidoResponse value) {
        return new JAXBElement<AcompanharPedidoResponse>(_AcompanharPedidoResponse_QNAME, AcompanharPedidoResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SobreWebServiceResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.logisticareversa.correios.com.br/", name = "sobreWebServiceResponse")
    public JAXBElement<SobreWebServiceResponse> createSobreWebServiceResponse(SobreWebServiceResponse value) {
        return new JAXBElement<SobreWebServiceResponse>(_SobreWebServiceResponse_QNAME, SobreWebServiceResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SolicitarPostagemReversa }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.logisticareversa.correios.com.br/", name = "solicitarPostagemReversa")
    public JAXBElement<SolicitarPostagemReversa> createSolicitarPostagemReversa(SolicitarPostagemReversa value) {
        return new JAXBElement<SolicitarPostagemReversa>(_SolicitarPostagemReversa_QNAME, SolicitarPostagemReversa.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SolicitarPostagemSimultanea }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.logisticareversa.correios.com.br/", name = "solicitarPostagemSimultanea")
    public JAXBElement<SolicitarPostagemSimultanea> createSolicitarPostagemSimultanea(SolicitarPostagemSimultanea value) {
        return new JAXBElement<SolicitarPostagemSimultanea>(_SolicitarPostagemSimultanea_QNAME, SolicitarPostagemSimultanea.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CalcularDigitoVerificador }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.logisticareversa.correios.com.br/", name = "calcularDigitoVerificador")
    public JAXBElement<CalcularDigitoVerificador> createCalcularDigitoVerificador(CalcularDigitoVerificador value) {
        return new JAXBElement<CalcularDigitoVerificador>(_CalcularDigitoVerificador_QNAME, CalcularDigitoVerificador.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SolicitarRange }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.logisticareversa.correios.com.br/", name = "solicitarRange")
    public JAXBElement<SolicitarRange> createSolicitarRange(SolicitarRange value) {
        return new JAXBElement<SolicitarRange>(_SolicitarRange_QNAME, SolicitarRange.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RevalidarPrazoAutorizacaoPostagem }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.logisticareversa.correios.com.br/", name = "revalidarPrazoAutorizacaoPostagem")
    public JAXBElement<RevalidarPrazoAutorizacaoPostagem> createRevalidarPrazoAutorizacaoPostagem(RevalidarPrazoAutorizacaoPostagem value) {
        return new JAXBElement<RevalidarPrazoAutorizacaoPostagem>(_RevalidarPrazoAutorizacaoPostagem_QNAME, RevalidarPrazoAutorizacaoPostagem.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AcompanharPedidoPorData }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.logisticareversa.correios.com.br/", name = "acompanharPedidoPorData")
    public JAXBElement<AcompanharPedidoPorData> createAcompanharPedidoPorData(AcompanharPedidoPorData value) {
        return new JAXBElement<AcompanharPedidoPorData>(_AcompanharPedidoPorData_QNAME, AcompanharPedidoPorData.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SolicitarRangeResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.logisticareversa.correios.com.br/", name = "solicitarRangeResponse")
    public JAXBElement<SolicitarRangeResponse> createSolicitarRangeResponse(SolicitarRangeResponse value) {
        return new JAXBElement<SolicitarRangeResponse>(_SolicitarRangeResponse_QNAME, SolicitarRangeResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link WebServiceFaultInfo }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.logisticareversa.correios.com.br/", name = "WebServiceFaultInfo")
    public JAXBElement<WebServiceFaultInfo> createWebServiceFaultInfo(WebServiceFaultInfo value) {
        return new JAXBElement<WebServiceFaultInfo>(_WebServiceFaultInfo_QNAME, WebServiceFaultInfo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RevalidarPrazoAutorizacaoPostagemResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.logisticareversa.correios.com.br/", name = "revalidarPrazoAutorizacaoPostagemResponse")
    public JAXBElement<RevalidarPrazoAutorizacaoPostagemResponse> createRevalidarPrazoAutorizacaoPostagemResponse(RevalidarPrazoAutorizacaoPostagemResponse value) {
        return new JAXBElement<RevalidarPrazoAutorizacaoPostagemResponse>(_RevalidarPrazoAutorizacaoPostagemResponse_QNAME, RevalidarPrazoAutorizacaoPostagemResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ValidarPostagemSimultanea }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.logisticareversa.correios.com.br/", name = "validarPostagemSimultanea")
    public JAXBElement<ValidarPostagemSimultanea> createValidarPostagemSimultanea(ValidarPostagemSimultanea value) {
        return new JAXBElement<ValidarPostagemSimultanea>(_ValidarPostagemSimultanea_QNAME, ValidarPostagemSimultanea.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ValidarPostagemReversa }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.logisticareversa.correios.com.br/", name = "validarPostagemReversa")
    public JAXBElement<ValidarPostagemReversa> createValidarPostagemReversa(ValidarPostagemReversa value) {
        return new JAXBElement<ValidarPostagemReversa>(_ValidarPostagemReversa_QNAME, ValidarPostagemReversa.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CalcularDigitoVerificadorResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.logisticareversa.correios.com.br/", name = "calcularDigitoVerificadorResponse")
    public JAXBElement<CalcularDigitoVerificadorResponse> createCalcularDigitoVerificadorResponse(CalcularDigitoVerificadorResponse value) {
        return new JAXBElement<CalcularDigitoVerificadorResponse>(_CalcularDigitoVerificadorResponse_QNAME, CalcularDigitoVerificadorResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AcompanharPedidoPorDataResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.logisticareversa.correios.com.br/", name = "acompanharPedidoPorDataResponse")
    public JAXBElement<AcompanharPedidoPorDataResponse> createAcompanharPedidoPorDataResponse(AcompanharPedidoPorDataResponse value) {
        return new JAXBElement<AcompanharPedidoPorDataResponse>(_AcompanharPedidoPorDataResponse_QNAME, AcompanharPedidoPorDataResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SolicitarPostagemReversaResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.logisticareversa.correios.com.br/", name = "solicitarPostagemReversaResponse")
    public JAXBElement<SolicitarPostagemReversaResponse> createSolicitarPostagemReversaResponse(SolicitarPostagemReversaResponse value) {
        return new JAXBElement<SolicitarPostagemReversaResponse>(_SolicitarPostagemReversaResponse_QNAME, SolicitarPostagemReversaResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CancelarPedidoResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.logisticareversa.correios.com.br/", name = "cancelarPedidoResponse")
    public JAXBElement<CancelarPedidoResponse> createCancelarPedidoResponse(CancelarPedidoResponse value) {
        return new JAXBElement<CancelarPedidoResponse>(_CancelarPedidoResponse_QNAME, CancelarPedidoResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ValidarPostagemReversaResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.logisticareversa.correios.com.br/", name = "validarPostagemReversaResponse")
    public JAXBElement<ValidarPostagemReversaResponse> createValidarPostagemReversaResponse(ValidarPostagemReversaResponse value) {
        return new JAXBElement<ValidarPostagemReversaResponse>(_ValidarPostagemReversaResponse_QNAME, ValidarPostagemReversaResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SolicitarPostagemSimultaneaResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.logisticareversa.correios.com.br/", name = "solicitarPostagemSimultaneaResponse")
    public JAXBElement<SolicitarPostagemSimultaneaResponse> createSolicitarPostagemSimultaneaResponse(SolicitarPostagemSimultaneaResponse value) {
        return new JAXBElement<SolicitarPostagemSimultaneaResponse>(_SolicitarPostagemSimultaneaResponse_QNAME, SolicitarPostagemSimultaneaResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CancelarPedido }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.logisticareversa.correios.com.br/", name = "cancelarPedido")
    public JAXBElement<CancelarPedido> createCancelarPedido(CancelarPedido value) {
        return new JAXBElement<CancelarPedido>(_CancelarPedido_QNAME, CancelarPedido.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AcompanharPedido }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.logisticareversa.correios.com.br/", name = "acompanharPedido")
    public JAXBElement<AcompanharPedido> createAcompanharPedido(AcompanharPedido value) {
        return new JAXBElement<AcompanharPedido>(_AcompanharPedido_QNAME, AcompanharPedido.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SobreWebService }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.logisticareversa.correios.com.br/", name = "sobreWebService")
    public JAXBElement<SobreWebService> createSobreWebService(SobreWebService value) {
        return new JAXBElement<SobreWebService>(_SobreWebService_QNAME, SobreWebService.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ValidarPostagemSimultaneaResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.logisticareversa.correios.com.br/", name = "validarPostagemSimultaneaResponse")
    public JAXBElement<ValidarPostagemSimultaneaResponse> createValidarPostagemSimultaneaResponse(ValidarPostagemSimultaneaResponse value) {
        return new JAXBElement<ValidarPostagemSimultaneaResponse>(_ValidarPostagemSimultaneaResponse_QNAME, ValidarPostagemSimultaneaResponse.class, null, value);
    }

}
