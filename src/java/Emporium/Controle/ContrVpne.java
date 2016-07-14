/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Emporium.Controle;

import static Controle.ContrCep.pesquisaCep;
import static Controle.ContrClienteDeptos.consultaDeptoById;
import static Controle.contrMovimentacao.getConsultaBySRO;
import Entidade.ClientesDeptos;
import Entidade.Destinatario;
import Entidade.Endereco;
import Entidade.Movimentacao;
import Entidade.Vpne;
import Util.Conexao;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.fileupload.FileItem;

/**
 *
 * @author Fernando
 */
public class ContrVpne {

    public static String importaPedidoVpne(ArrayList<FileItem> listaFiles, int idCliente, int idDepartamento, String nomeBD) {

        String sql = "REPLACE INTO vpne (idCliente, idDepartamento,nomeDepartamento ,sro, descricao, valor, remetente, cnpj_remetente, rlogradouro, rnumero, rbairro, rcidade,"
                + " ruf, destinatario, cpf_cnpj_dest, dlogradouro, dnumero, dbairro, dcidade, dcep ,duf, data) VALUES ";
       
        try {
            
             ClientesDeptos dep = consultaDeptoById(idCliente, idDepartamento,  nomeBD);
            String nomeDepto = dep.getNomeDepartamento();

            for (FileItem item : listaFiles) {
                sql += lerVpne(item, idCliente, idDepartamento, nomeDepto, nomeBD);
            }

            sql = sql.substring(0, sql.lastIndexOf(","));
            System.out.println(sql);
            boolean flag = inserirVpne(sql, nomeBD);
            // boolean flag = true;
            if (flag) {
                return "Pedidos Importados Com Sucesso!";
            } else {
                return "Falha ao importar Pedidos!";
            }

        } catch (IOException e) {
            return "ERRO: Não foi possivel ler o arquivo:\nIoException " + e;
        } catch (Exception e) {
            return "ERRO: Falha na importacao dos pedidos:\nException: " + e;
        }

    }

    public static boolean inserirVpne(String sql, String nomeBD) {
        Connection conn = Conexao.conectar(nomeBD);
        try {
            //System.out.println("SQL PEDIDO:\n" + sql);
            PreparedStatement valores = conn.prepareStatement(sql);
            valores.executeUpdate();
            valores.close();
            return true;
        } catch (SQLException e) {
            Logger.getLogger(ContrPreVendaImporta.class.getName()).log(Level.WARNING, e.getMessage(), e);
            return false;
        } finally {
            Conexao.desconectar(conn);
        }
    }

    public static String lerVpne(FileItem item, int idCli, int idDepto, String nomeDepto, String nomeBD) throws UnsupportedEncodingException, IOException {
        DecimalFormat df = new DecimalFormat("0.00");
        
        String ret = "";

        String r_nome = "";
        String r_cpf_cnpj = "";
        String r_cep = "";
        String r_endereco = "";
        String r_numero = "";
        String r_bairro = "";
        String r_cidade = "";// se exiisteir o CEP no DNE
        String r_uf = "";

        String valor = "";

        String d_nome = "";
        String d_cpf_cnpj = "";
        String d_cep = "";
        String d_endereco = "";
        String d_numero = "";
        String d_bairro = "";
        
        
        String d_cidade = ""; // se existir na tabela movimentação
        String d_uf = "";

        String descricao = "";
        String sro = "";
        String data = "";
       
        

        BufferedReader le = new BufferedReader(new InputStreamReader(item.getInputStream(), "ISO-8859-1"));
        // LE UMA LINHA DO ARQUIVO PARA PULAR O CABEÇALHO   
        int lineNum = 1;
        while (le.ready()) {
            //LE UMA LINHA DO ARQUIVO 
            String aux = le.readLine();
            //ADICIONA CONTADOR DE LINHA

            if (lineNum == 1) {
                //remetente
                r_nome = aux.substring(38, 88).trim();
                r_cpf_cnpj = aux.substring(98, 112).trim();
                r_cep = aux.substring(112, 120).trim();
                r_endereco = aux.substring(130, 190).trim();
                r_numero = aux.substring(190, 198).trim();
                r_bairro = aux.substring(236, 276).trim();
                //String r_cidade; // se exiisteir o CEP no DNE
                // String r_uf; // se exiisteir o CEP no DNE
            }
            if (lineNum == 2) {
                //destinatario
                d_nome = aux.substring(26, 76).trim();
                d_cpf_cnpj = aux.substring(86, 100).trim();
                d_cep = aux.substring(100, 108).trim();
                d_endereco = aux.substring(118, 178).trim();
                d_numero = aux.substring(178, 224).trim();
                d_bairro = aux.substring(224, 264).trim();
                // String d_cidade;// se exiisteir o CEP no DNE
                // String d_uf;// se exiisteir o CEP no DNE

                sro = aux.substring(300, 313).trim();
                descricao = aux.substring(313, 363).trim();
                valor = aux.substring(292, 300).trim();
                data = aux.substring(363).trim();
            }
            lineNum++;

        }
        le.close();
        try {
            r_numero = Integer.parseInt(r_numero) + "";
            d_numero = Integer.parseInt(d_numero) + "";
            valor = df.format((double) Integer.parseInt(valor)/100) + "";
        } catch (Exception ex) {

        }
        data = data.substring(4, 8) + "-" + data.substring(2, 4) + "-" + data.substring(0, 2);
        
        System.out.println("foi 1");
        Movimentacao mv = getConsultaBySRO(sro, nomeBD);
         System.out.println("foi 2");
        if(mv != null){
             d_uf = mv.getId();// USEI O CAMPO dufF PARA COLOCAR O ID DA MOVIMENTACAO CASO EXISTA
        }
         System.out.println("foi 3");
        Endereco end_cep =  pesquisaCep(d_cep);
         System.out.println("foi 4");
        d_cidade = end_cep.getCidade() +" / "+end_cep.getUf();

        ret = "(" + idCli + "," + idDepto + ",'" + nomeDepto + "','" + sro + "','" + descricao + "','" + valor + "','" + r_nome + "','" + r_cpf_cnpj + "','" + r_endereco + "','" + r_numero + "','" + r_bairro + "','" + r_cidade + "','" + r_uf + "',"
                + "'" + d_nome + "','" + d_cpf_cnpj + "','" + d_endereco + "','" + d_numero + "','" + d_bairro + "','" + d_cidade + "','" + d_cep + "','" + d_uf +"','" + data + "'),";
        return ret;
    }

    public static ArrayList<Vpne> listaVpne(String where, String nomeBD, int idCli) {

        String sql = "SELECT * FROM vpne WHERE idCliente = "+ idCli+" ";
        sql = sql + where + " ;";
        Connection conn = Conexao.conectar(nomeBD);
        ArrayList<Vpne> listaVpne = new ArrayList<Vpne>();

        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            valores.executeQuery();
            ResultSet result = (ResultSet) valores.executeQuery();

            while (result.next()) {

                int idCliente = result.getInt("idCliente");
                int idDepartamento = result.getInt("idDepartamento");
                String nomeDepto = result.getString("nomeDepartamento");
                
                String sro = result.getString("sro");
                String descricao = result.getString("descricao");
                String valor = result.getString("valor");

                String remetente = result.getString("remetente");
                String cnpj_remetente = result.getString("cnpj_remetente");
                String rlogradouro = result.getString("rlogradouro");
                String rnumero = result.getString("rnumero");
                String rbairro = result.getString("rbairro");
                String rcidade = result.getString("rcidade");
                String ruf = result.getString("ruf");

                String destinatario = result.getString("destinatario");
                String cpf_cnpj_dest = result.getString("cpf_cnpj_dest");
                String dlogradouro = result.getString("dlogradouro");
                String dnumero = result.getString("dnumero");
                String dbairro = result.getString("dbairro");
                String dcidade = result.getString("dcidade");
                String dcep = result.getString("dcep");
                String duf = result.getString("duf");

                String data = result.getString("data");

                Destinatario remVpne = new Destinatario(remetente, cnpj_remetente, rlogradouro, rnumero, rbairro, rcidade, ruf);
                Destinatario destVpne = new Destinatario(destinatario, cpf_cnpj_dest, dlogradouro, dnumero, dbairro, dcidade,dcep ,duf);
                
                Vpne vp = new Vpne(sro, descricao, valor, idCliente, idDepartamento, nomeDepto ,data, remVpne, destVpne);
                listaVpne.add(vp);
            }
            valores.close();
            return listaVpne;
        } catch (SQLException e) {
            Logger.getLogger(ContrVpne.class.getName()).log(Level.WARNING, e.getMessage(), e);
            return listaVpne;
        } finally {
            Conexao.desconectar(conn);
        }
    }

}
