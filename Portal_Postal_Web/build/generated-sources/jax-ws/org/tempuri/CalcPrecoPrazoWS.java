
package org.tempuri;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.WebServiceFeature;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.6-1b01 
 * Generated source version: 2.1
 * 
 */
@WebServiceClient(name = "CalcPrecoPrazoWS", targetNamespace = "http://tempuri.org/", wsdlLocation = "http://ws.correios.com.br/calculador/CalcPrecoPrazo.asmx?WSDL")
public class CalcPrecoPrazoWS
    extends Service
{

    private final static URL CALCPRECOPRAZOWS_WSDL_LOCATION;
    private final static WebServiceException CALCPRECOPRAZOWS_EXCEPTION;
    private final static QName CALCPRECOPRAZOWS_QNAME = new QName("http://tempuri.org/", "CalcPrecoPrazoWS");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL("http://ws.correios.com.br/calculador/CalcPrecoPrazo.asmx?WSDL");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        CALCPRECOPRAZOWS_WSDL_LOCATION = url;
        CALCPRECOPRAZOWS_EXCEPTION = e;
    }

    public CalcPrecoPrazoWS() {
        super(__getWsdlLocation(), CALCPRECOPRAZOWS_QNAME);
    }

    public CalcPrecoPrazoWS(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    /**
     * 
     * @return
     *     returns CalcPrecoPrazoWSSoap
     */
    @WebEndpoint(name = "CalcPrecoPrazoWSSoap")
    public CalcPrecoPrazoWSSoap getCalcPrecoPrazoWSSoap() {
        return super.getPort(new QName("http://tempuri.org/", "CalcPrecoPrazoWSSoap"), CalcPrecoPrazoWSSoap.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns CalcPrecoPrazoWSSoap
     */
    @WebEndpoint(name = "CalcPrecoPrazoWSSoap")
    public CalcPrecoPrazoWSSoap getCalcPrecoPrazoWSSoap(WebServiceFeature... features) {
        return super.getPort(new QName("http://tempuri.org/", "CalcPrecoPrazoWSSoap"), CalcPrecoPrazoWSSoap.class, features);
    }

    /**
     * 
     * @return
     *     returns CalcPrecoPrazoWSSoap
     */
    @WebEndpoint(name = "CalcPrecoPrazoWSSoap12")
    public CalcPrecoPrazoWSSoap getCalcPrecoPrazoWSSoap12() {
        return super.getPort(new QName("http://tempuri.org/", "CalcPrecoPrazoWSSoap12"), CalcPrecoPrazoWSSoap.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns CalcPrecoPrazoWSSoap
     */
    @WebEndpoint(name = "CalcPrecoPrazoWSSoap12")
    public CalcPrecoPrazoWSSoap getCalcPrecoPrazoWSSoap12(WebServiceFeature... features) {
        return super.getPort(new QName("http://tempuri.org/", "CalcPrecoPrazoWSSoap12"), CalcPrecoPrazoWSSoap.class, features);
    }

    /**
     * 
     * @return
     *     returns CalcPrecoPrazoWSHttpGet
     */
    @WebEndpoint(name = "CalcPrecoPrazoWSHttpGet")
    public CalcPrecoPrazoWSHttpGet getCalcPrecoPrazoWSHttpGet() {
        return super.getPort(new QName("http://tempuri.org/", "CalcPrecoPrazoWSHttpGet"), CalcPrecoPrazoWSHttpGet.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns CalcPrecoPrazoWSHttpGet
     */
    @WebEndpoint(name = "CalcPrecoPrazoWSHttpGet")
    public CalcPrecoPrazoWSHttpGet getCalcPrecoPrazoWSHttpGet(WebServiceFeature... features) {
        return super.getPort(new QName("http://tempuri.org/", "CalcPrecoPrazoWSHttpGet"), CalcPrecoPrazoWSHttpGet.class, features);
    }

    /**
     * 
     * @return
     *     returns CalcPrecoPrazoWSHttpPost
     */
    @WebEndpoint(name = "CalcPrecoPrazoWSHttpPost")
    public CalcPrecoPrazoWSHttpPost getCalcPrecoPrazoWSHttpPost() {
        return super.getPort(new QName("http://tempuri.org/", "CalcPrecoPrazoWSHttpPost"), CalcPrecoPrazoWSHttpPost.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns CalcPrecoPrazoWSHttpPost
     */
    @WebEndpoint(name = "CalcPrecoPrazoWSHttpPost")
    public CalcPrecoPrazoWSHttpPost getCalcPrecoPrazoWSHttpPost(WebServiceFeature... features) {
        return super.getPort(new QName("http://tempuri.org/", "CalcPrecoPrazoWSHttpPost"), CalcPrecoPrazoWSHttpPost.class, features);
    }

    private static URL __getWsdlLocation() {
        if (CALCPRECOPRAZOWS_EXCEPTION!= null) {
            throw CALCPRECOPRAZOWS_EXCEPTION;
        }
        return CALCPRECOPRAZOWS_WSDL_LOCATION;
    }

}
