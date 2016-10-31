/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Util;

import java.io.DataInputStream;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import org.dom4j.Document;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

/**
 *
 * @author RICADINHO
 */
public class RastreamentoSRO {

    @SuppressWarnings("unchecked")
    public void buscaSRO(String nroObjeto) {
        try {
            String retHTML = callSRO(nroObjeto);
            System.out.println(retHTML);
            if (retHTML != null && !retHTML.isEmpty()) {
                
                SAXReader reader = new SAXReader();
                StringReader sr = new StringReader(retHTML);
                Document doc = reader.read(sr);
                List<Node> eventos = (List<Node>) doc.selectNodes("//sroxml/objeto");

                // processa eventos
                for (Node node : eventos) {
                    String numObj = node.valueOf("numero");
                    String dtHr = node.valueOf("evento/data") + " " + node.valueOf("evento/hora");
                    String descStatus = node.valueOf("evento/descricao");
                    String codStatus = node.valueOf("evento/status");
                    String tip = node.valueOf("evento/tipo");
                    String cod = node.valueOf("evento/codigo");
                    System.out.println(numObj +" - "+dtHr+" = "+descStatus+" / "+codStatus+" --> " + tip +" - "+cod);
                    /*
                     * node.valueOf("evento/local");
                     * node.valueOf("evento/cidade");
                     * node.valueOf("evento/uf");
                     * node.valueOf("evento/tipo");
                     * node.valueOf("evento/codigo");
                     */
                }                
            }
        } catch (Exception ex) {
            System.err.println("Erro durante a consulta no SRO: " + ex.getMessage());
        }
        //return saida;
    }

    private String callSRO(String nroObjeto) {
        String saida = "";

        try {
            URL url = new URL("http://websro.correios.com.br/sro_bin/sroii_xml.eventos");

            /*// Configura��o Proxy Inicio
            Authenticator.setDefault(new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication("804058", "angel16".toCharArray());
            }
            });
            System.setProperty("http.proxyHost", "proxy01.prodesp.sp.gov.br");
            System.setProperty("http.proxyPort", "80");
            // Configura��o Proxy Fim
             */
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");

            OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
            writer.write("usuario=ECT&senha=SRO&tipo=L&resultado=U&objetos=" + nroObjeto.toUpperCase());
            // Usuario e senha disponibilizado pelos correios
            // resultado U ultimo resultado ou T todos os eventos
            // objetos, lista para consulta
            writer.close();

            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new Exception("Correios - SRO - falha: " + connection.getResponseMessage());
            }
            DataInputStream inStream = new DataInputStream(connection.getInputStream());

            String inputLine;
            while ((inputLine = inStream.readLine()) != null) {
                saida += inputLine;
            }
            inStream.close();

        } catch (Exception e) {
            System.err.println("Erro durante a consulta no SRO: " + e.getMessage());
        }
        return saida;
    }

    public static void main(String[] args) throws Exception {
        new RastreamentoSRO().buscaSRO("RQ267149052BR");
    }

}
