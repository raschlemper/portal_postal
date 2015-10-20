package Util;

/*
 * @Author Eduardo Hiroshi Campos Tamaki edhiroshi86@yahoo.com.br
 */
import Controle.ContrErroLog;
import Controle.contrCliente;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;

public class CepService {

    public static void main(String[] args) {
            importaCEP();
/*
        // Define o CEP
        String cep = JOptionPane.showInputDialog(null, "Informe o CEP");

        // String da url		
        String urlString = "http://www.buscarcep.com.br/index.php";

        // Parametros a serem enviados
        Properties parameters = new Properties();
        parameters.setProperty("cep", cep);
        parameters.setProperty("formato", "xml");

        // Iterador
        Iterator i = parameters.keySet().iterator();
        // Contador
        int counter = 0;

        // Enquanto ainda existir parametros
        while (i.hasNext()) {

            // Nome
            String name = (String) i.next();
            // Valor
            String value = parameters.getProperty(name);

            // Adiciona com um conector (? ou &)
            // Separa a url por ?, e as vari�veis com &
            urlString += (++counter == 1 ? "?" : "&") + name + "=" + value;

        }

        try {
            // Objeto URL
            URL url = new URL(urlString);

            // Objeto HttpURLConnection
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // M�todo
            connection.setRequestProperty("Request-Method", "GET");

            // V�ariavel do resultado
            connection.setDoInput(true);
            connection.setDoOutput(false);

            // Faz a conex�o
            connection.connect();

            // Abre a conex�o
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            // L� a conex�o
            StringBuffer newData = new StringBuffer();
            String s = "";
            while (null != ((s = br.readLine()))) {
                newData.append(s);
            }
            br.close();

            String comHtml = new String(newData);
            Pattern p = Pattern.compile("<.*?>");
            Matcher m = p.matcher(comHtml);
            String semHtml = m.replaceAll(" ");
            String endereco[] = semHtml.split("  ");
            try {
                System.out.println(endereco[1]);
            } catch (Exception e) {
            }
            try {
                System.out.println(endereco[2]);
            } catch (Exception e) {
            }
            try {
                System.out.println(endereco[3]);
            } catch (Exception e) {
            }
            try {
                System.out.println(endereco[4]);
            } catch (Exception e) {
            }
            try {
                System.out.println(endereco[5]);
            } catch (Exception e) {
            }
            try {
                System.out.println(endereco[6]);
                String end = "CEP: " + endereco[1] + "\nEstado: " + endereco[2] + "\nCidade: " + endereco[3] + "\nBairro: " + endereco[4] + "\nLogradouro: " + endereco[5] + " " + endereco[6];
                JOptionPane.showMessageDialog(null, end);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "CEP n�o consta no banco de dados");
            }


        } catch (Exception e) {
            e.printStackTrace();
        }*/

    }

    public static void importaCEP() {
        int linha = 0;
        ArrayList<String> listaQuerys = new ArrayList<String>();
        ArrayList<String> listaIDS = new ArrayList<String>();

        String sqlBase = "INSERT INTO logradouros (uf, cidade, bairro, logradouro, logradouro_abreviatura, cep) VALUES ";
        StringBuilder sqlValues = new StringBuilder();
        /*String sqlDuplicated = " ON DUPLICATE KEY UPDATE uf = VALUES(nome), endereco = VALUES(endereco)"
                + ", telefone = VALUES(telefone), bairro = VALUES(bairro), cidade = VALUES(cidade)"
                + ", uf = VALUES(uf), cep = VALUES(cep), email = VALUES(email), cnpj = VALUES(cnpj)"
                + ", nomeFantasia = VALUES(nomeFantasia), complemento = VALUES(complemento), codSTO = VALUES(codSTO);";*/


        try {
            BufferedReader le = new BufferedReader(new FileReader("C:/Documents and Settings/RICARDINHO/My Documents/ARQUIVOS SCC4/Desktop/CEP/TABELAS LOGRADOUROS/DNE_GU_TO_LOGRADOUROS.TXT"));
            while (le.ready()) {
                linha++;
                String buffer = le.readLine();
                if(linha == 1){
                    buffer = le.readLine();
                }
                
                if(!buffer.substring(0, 1).equals("#")){

                    if (linha % 500 == 0) {
                        String sqlQuery = sqlBase + sqlValues.substring(0, sqlValues.toString().lastIndexOf(","));// + sqlDuplicated;
                        listaQuerys.add(sqlQuery);
                        sqlValues = new StringBuilder();
                    }

                    StringBuilder strBuf = new StringBuilder();
                    strBuf.append(" (\"").append(toStr(buffer.substring(1, 3), "")).append("\", "); //UF
                    strBuf.append("\"").append(toStr(buffer.substring(17, 89), "")).append("\", "); //CIDADE
                    strBuf.append("\"").append(toStr(buffer.substring(102, 174), "")).append("\", "); //BAIRRO
                    strBuf.append("\"").append(toStr(buffer.substring(259, 285), "")).append(toStr(buffer.substring(285, 288), " ")).append(toStr(buffer.substring(288, 360), " ")).append(toStr(buffer.substring(374, 446), " ")).append("\", "); //LOGRADOURO
                    strBuf.append("\"").append(toStr(buffer.substring(446, 482), "")).append("\", "); //LOGRADOURO ABRV
                    strBuf.append("\"").append(toStr(buffer.substring(518, 526), "")).append("\"),\n"); //CEP

                    sqlValues.append(strBuf);
                    listaIDS.add(toStr(buffer.substring(0, 14), ""));
                
                }

            }
            le.close();

            if (!sqlValues.toString().equals("")) {
                String sqlQuery = sqlBase + sqlValues.substring(0, sqlValues.toString().lastIndexOf(","));// + sqlDuplicated;
                listaQuerys.add(sqlQuery);
            }

            Connection conn = (Connection) Conexao.conectarCep();
            try {
                //IMPORTA OS OBJETOS DO ARQUIVO
                for (int i = 0; i < listaQuerys.size(); i++) {
                    PreparedStatement valores1 = conn.prepareStatement(listaQuerys.get(i));
                    valores1.executeUpdate();
                    valores1.close();
                }
            } catch (SQLException e) {
                System.out.println("ERRO NO BD: "+e);
            } finally {
                Conexao.desconectar(conn);
            }

            System.out.println("Atualização de Clientes Realizada Com Sucesso! "+ (linha-1) +" cidades");

        } catch (IOException e) {
            System.out.println("Erro na linha <b style='color:red;'>" + linha + "</b> do arquivo!<br><br>Falha: Não foi possivel ler o arquivo!<br>Detalhes:" + e);
        } catch (Exception e) {
            System.out.println("Erro na linha <b style='color:red;'>" + linha + "</b> do arquivo!<br><br>Falha: Problema no tratamento do arquivo!<br>Detalhes: " + e);
        }
    }

    private static String toStr(String str, String append) {
        String ret = str.trim().replaceAll("\"", "");
        
        if(!ret.equals("")){
            ret = append + ret;
        }
        
        return ret;
    }
}
