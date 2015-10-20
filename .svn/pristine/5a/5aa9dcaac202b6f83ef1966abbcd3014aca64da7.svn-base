/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Util;

import Controle.contrRobozinho;
import java.io.DataInputStream;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.util.*;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.dom4j.Document;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

/**
 * Web application lifecycle listener.
 *
 * @author RICADINHO
 */
public class AtualizadorSRO implements ServletContextListener {

    Timer timer = null;

    public void contextInitialized(ServletContextEvent sce) {
        timer = new Timer();
        long delay = 10000;
        long period = 21600000; //6hrs

        timer.scheduleAtFixedRate(new TimerTask() {

            public void run() {
                //Funcao(); // método a ser executado
            }
        }, delay, period);
    }

    public void contextDestroyed(ServletContextEvent sce) {
        if (timer != null) {
            timer.cancel();
        }
        System.out.println(" -- TIMER FINALIZADO --");
    }

    private void Funcao() {
        try {
            System.out.println("INICIO EXEC - " + new Date());
            
            ArrayList<String> listaCnpj = contrRobozinho.consultaNomesBD();
            for (int i = 0; i < listaCnpj.size(); i++) {
                String nomeBD = listaCnpj.get(i);
                ArrayList<String> listaNumObj = contrRobozinho.consultaSro(nomeBD);
                Connection conn = Conexao.conectar(nomeBD);
                System.out.println(nomeBD + " - QTD OBJETOS APROX = " + listaNumObj.size());
                for (int j = 0; j < listaNumObj.size(); j++) {
                    String nrObjeto = listaNumObj.get(j);
                    String retHTML = callSRO(nrObjeto);
                    if (retHTML != null && !retHTML.isEmpty()) {
                        SAXReader reader = new SAXReader();
                        StringReader sr = new StringReader(retHTML);
                        Document doc = reader.read(sr);
                        List<Node> eventos = (List<Node>) doc.selectNodes("//sroxml/objeto");
                        for (Node node : eventos) {
                            String numObj = node.valueOf("numero");
                            String dtHr = node.valueOf("evento/data") + " " + node.valueOf("evento/hora");
                            String descStatus = node.valueOf("evento/descricao");
                            String codStatus = node.valueOf("evento/status");
                            //System.out.println(numObj +" - "+dtHr+" = "+descStatus+" / "+codStatus);

                            contrRobozinho.alterarStatusSro(numObj, codStatus, descStatus, dtHr, nomeBD, conn);
                        }
                    }
                }
                Conexao.desconectar(conn);
            }
            
            System.out.println("FINAL EXEC - " + new Date());

        } catch (Exception ex) {
            System.err.println("Erro durante a consulta no SRO: " + ex.getMessage());
        }



    }

    private String callSRO(String nroObjeto) {
        String saida = "";
        try {

            URL url = new URL("http://websro.correios.com.br/sro_bin/sroii_xml.eventos");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");

            OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
            writer.write("usuario=ECT&senha=SRO&tipo=L&resultado=U&objetos=" + nroObjeto.toUpperCase());
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
}
