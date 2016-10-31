package com.portal.componentes.nfe;

import java.io.IOException;
import org.jsoup.Connection;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class CarregaDadosNFE {

    private final ParametrosFormularioNFE parametros;

    public CarregaDadosNFE(ParametrosFormularioNFE parametros) {
        this.parametros = parametros;
    }

    public String getCaptcha() throws IOException {
        Connection connection = Jsoup.connect(parametros.getURL());
        Response response = connection.execute();
        Document document = response.parse();
        capturaValoreDoFormulario(response, document);
        return document.getElementById("ctl00_ContentPlaceHolder1_imgCaptcha").attributes().get("src");
    }

    public Document enviar(String capcha, String chaveNFE) throws IOException {
        Connection connection = Jsoup.connect(parametros.getURL());
        configuraCabecalho(connection);
        connection.cookies(parametros.getCookies());
        configuraVariaveis(connection, capcha, chaveNFE);
        connection.userAgent(parametros.getAGENT());
        return  connection.post();
    }

    private void capturaValoreDoFormulario(Response response, Document document) {
        parametros.setCookies(response.cookies());
        parametros.setViewStateGenerate(getValue(document, "__VIEWSTATEGENERATOR"));
        parametros.setEventValidation(getValue(document, "__EVENTVALIDATION"));
        parametros.setViewState(getValue(document, "__VIEWSTATE"));
        parametros.setToken(getValue(document, "ctl00_ContentPlaceHolder1_token"));
        parametros.setCaptchaSom(getValue(document, "ctl00_ContentPlaceHolder1_captchaSom"));
    }

    private void configuraVariaveis(Connection connection, String capcha, String chaveNFE) {
        connection.data("__EVENTTARGET", "");
        connection.data("__EVENTARGUMENT", "");
        connection.data("__VIEWSTATE", parametros.getViewState());
        connection.data("__VIEWSTATEGENERATOR", parametros.getViewStateGenerate());
        connection.data("__EVENTVALIDATION", parametros.getEventValidation());
        connection.data("ctl00$txtPalavraChave", "");
        connection.data("ctl00$ContentPlaceHolder1$txtChaveAcessoCompleta", chaveNFE);
        connection.data("ctl00$ContentPlaceHolder1$txtCaptcha", capcha.trim());
        connection.data("ctl00$ContentPlaceHolder1$btnConsultar", "Continuar");
        connection.data("ctl00$ContentPlaceHolder1$token", parametros.getToken());
        connection.data("ctl00$ContentPlaceHolder1$captchaSom", parametros.getCaptchaSom());
        connection.data("hiddenInputToUpdateATBuffer_CommonToolkitScripts", "1");
    }

    private void configuraCabecalho(Connection connection) {
        connection.header("Upgrade-Insecure-Requests", "1");
        connection.header("Content-Type", "application/x-www-form-urlencoded");
        connection.header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
        connection.header("Accept-Encoding", "gzip, deflate");
        connection.header("Accept-Language", "pt-BR,pt;q=0.8,en-US;q=0.6,en;q=0.4");
        connection.header("Referer", parametros.getURL());
        connection.header("Cache-Control", "max-age=0");
    }

    private String getValue(Document document, String atributo) {
        return document.getElementById(atributo).attr("value");
    }

}
