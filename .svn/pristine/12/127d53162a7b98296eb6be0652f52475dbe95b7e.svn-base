package Controle;

import Entidade.Movimentacao;
import Entidade.PreVenda;
import Util.Conexao;
import java.io.DataInputStream;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.ParseException;
//import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

public class contrSRO {

    // private static SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static String callSRO(String nroObjeto, String vCnpj) {
        try {
            String saida = "";
            URL url = new URL("http://websro.correios.com.br/sro_bin/sroii_xml.eventos");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");

            OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
            writer.write("usuario=ECT&senha=SRO&tipo=L&resultado=U&objetos=" + nroObjeto.toUpperCase());
            writer.close();

            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new Exception("callSRO - Correios - SRO - falha: " + connection.getResponseMessage());
            }
            DataInputStream inStream = new DataInputStream(connection.getInputStream());

            String inputLine;
            while ((inputLine = inStream.readLine()) != null) {
                saida += inputLine;
            }
            inStream.close();
            saida = saida.replaceAll("<destino>", "");
            saida = saida.replaceAll("</destino>", "");
            return saida;
        } catch (Exception ex) {
            System.out.println("callSRO = " + nroObjeto + " - " + ex.getMessage());
            return null;
        }
    }

    public static ArrayList<String> consultaObjetosSroNaoEntregues(String cnpj) {

        Connection conn = (Connection) Conexao.conectar(cnpj);
        String sql = "SELECT TRIM(numObjeto) AS numObj FROM movimentacao WHERE numObjeto <> '-' "
                + " AND (codStatus NOT IN (1, 5, 9, 12, 23, 26, 27, 28, 31, 42, 43, 44, 50, 51, 52, 69, 99)"
                + " OR (codStatus = 99 AND status <> 'Objeto entregue ao destinatário')"
                + " OR (codStatus = 9 AND status <> 'Extraviado')"
                + " OR (codStatus = 1 AND status <> 'Entregue'))"
                + " ORDER BY dataPostagem DESC;";
        //System.out.println(sql);
        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            ResultSet result = (ResultSet) valores.executeQuery();
            ArrayList<String> lista = new ArrayList<String>();
            String group = "";
            for (int i = 0; result.next(); i++) {
                String numRegistro = result.getString("numObj");
                group += numRegistro;
                if ((i + 1) % 5 == 0) {
                    lista.add(group);
                    group = "";
                }
            }
            if (!"".equals(group)) {
                lista.add(group);
            }
            valores.close();
            return lista;
        } catch (Exception e) {
            System.out.println("contrSRO - FALHA AO EXECUTAR SQL: " + e);
            return null;
        } finally {
            Conexao.desconectar(conn);
        }
    }

    public static int atualizaSRO(String vCnpj) {

        String numObjeto = "";
        try {

            ArrayList<String> listaObjetos = consultaObjetosSroNaoEntregues(vCnpj);

            if (listaObjetos != null && listaObjetos.size() > 0) {
                Connection conn = Conexao.conectar(vCnpj);
                for (int j = 0; j < listaObjetos.size(); j++) {
                    numObjeto = listaObjetos.get(j);
                    String retHTML = callSRO(numObjeto, vCnpj);
                    if (retHTML != null && !retHTML.isEmpty()) {
                        try {
                            SAXReader reader = new SAXReader();
                            StringReader sr = new StringReader(retHTML);
                            Document doc = reader.read(sr);
                            List<Node> eventos = (List<Node>) doc.selectNodes("//sroxml/objeto");
                            for (Node node : eventos) {
                                String numObj = node.valueOf("numero");
                                String dtHr = node.valueOf("evento/data") + " " + node.valueOf("evento/hora");
                                String descStatus = node.valueOf("evento/descricao");
                                String codStatus = node.valueOf("evento/status");
                                String tipoStatus = node.valueOf("evento/tipo");

                                int st = Integer.parseInt(codStatus);
                                if (st == 1 && !"Entregue".equals(descStatus.trim())) {
                                    codStatus = "99";
                                    if ("OEC".equals(tipoStatus)) {
                                        descStatus = "Encaminhado";
                                    }
                                }
                                if (st == 9 && !"Extraviado".equals(descStatus.trim())) {
                                    codStatus = "0";
                                }

                                contrRobozinho.alterarStatusSro(numObj, codStatus, descStatus, dtHr, vCnpj, conn);

                                if ((st == 1 && "Entregue".equals(descStatus.trim())) || st == 5 || st == 23 || st == 26 || st == 27 || st == 42 || (st == 9 && "Extraviado".equals(descStatus.trim())) || st == 12 || st == 28 || st == 31 || st == 43 || st == 44 || st == 50 || st == 51 || st == 52 || st == 69) {

                                }
                                System.out.println(vCnpj + " >>> " + numObj + " | " + codStatus + " - " + descStatus + " [" + j + " de " + listaObjetos.size() + "]");
                            }
                        } catch (DocumentException ex) {
                            System.out.println("Document Exception = " + numObjeto + " - " + ex.getMessage());
                            return 0;
                        } catch (NumberFormatException ex) {
                            System.out.println("NumberFormatException = " + numObjeto + " - " + ex.getMessage());
                            return 0;
                        } catch (ParseException ex) {
                            System.out.println("ParseException = " + numObjeto + " - " + ex.getMessage());
                            return 0;
                        }
                    }
                    
                }
                Conexao.desconectar(conn);
                return listaObjetos.size();
            }else{
                return 0;
            }

        } catch (Exception ex) {
            System.out.println("Exception = " + ex.getMessage());
            return 0;
        }

    }

    public static ArrayList<Movimentacao> consultaDetalhesObjetosSroNaoEntregues(String cnpj) {

        Connection conn = (Connection) Conexao.conectar(cnpj);
        String sql = "SELECT * FROM movimentacao WHERE numObjeto <> '-' "
                + " AND (codStatus NOT IN (1, 5, 9, 12, 23, 26, 27, 28, 31, 42, 43, 44, 50, 51, 52, 69, 99)"
                + " OR (codStatus = 99 AND status <> 'Objeto entregue ao destinatário')"
                + " OR (codStatus = 9 AND status <> 'Extraviado')"
                + " OR (codStatus = 1 AND status <> 'Entregue'))"
                + " ORDER BY dataPostagem DESC;";

        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            ResultSet result = (ResultSet) valores.executeQuery();
            ArrayList<Movimentacao> lista = new ArrayList<Movimentacao>();
            for (int i = 0; result.next(); i++) {

                Date dataPostagem = result.getDate("dataPostagem");
                String descServico = result.getString("descServico");
                String numObjeto = result.getString("numObjeto");
                String destinatario = result.getString("destinatario");
                String cep = result.getString("cep");
                float valorServico = result.getFloat("valorServico");
                float valorDeclarado = result.getFloat("valorDeclarado");
                String codCliente = result.getString("codCliente");
                // String cliente = result.getString("c.cliente));
                String contratoEct = result.getString("contratoEct");
                String codigoEct = result.getString("codigoEct");
                int codStatus = result.getInt("codStatus");
                String status = result.getString("status");

                Movimentacao mv = new Movimentacao(dataPostagem, descServico, numObjeto, destinatario, cep, valorServico, valorDeclarado, codCliente,"",contratoEct, codigoEct, codStatus, status);
                lista.add(mv);
            }

            valores.close();
            return lista;
        } catch (Exception e) {
            System.out.println("contrSRO - FALHA AO EXECUTAR SQL: " + e);
            return null;
        } finally {
            Conexao.desconectar(conn);
        }
    }
}
