/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Emporium.Controle;

import Controle.ContrErroLog;
import Entidade.DadosPLP;
import Entidade.Destinatario;
import Entidade.Endereco;
import Util.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Fernando
 */
public class ContrImpressaoPLP {
      
    public static void inserePLP(String sro, String plp, int status, String contrato, String cartaoPostagem, String codAdministrativo, int codECT, String nomeServico, int idCliente, int idDepto, int ar, int mp, float vd, String notaFiscal, Endereco endRem, Endereco endDest, String nomeBD){        
        Connection conn = Conexao.conectar(nomeBD);
        
        String sql = "INSERT INTO me_plp (sro, idPLP, status, contrato, cartaoPostagem, codAdministrativo, codECT, servico, idCliente, idDepartamento, dataHoraImportacao, ar, mp, vd, notaFiscal,"
                + " nomeDestinatario, cepDestinatario, enderecoDestinatario, numeroDestinatario, complementoDestinatario, bairroDestinatario, cidadeDestinatario, ufDestinatario," 
                + " nomeRemetente, cepRemetente, enderecoRemetente, numeroRemetente, complementoRemetente, bairroRemetente, cidadeRemetente, ufRemetente)"
                + " VALUES (?,?,?,?,?,?,?,?,?,?,NOW(),?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"
                + " ON DUPLICATE KEY UPDATE idPLP = VALUES(idPLP), status = 0";
        
        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            valores.setString(1, sro);
            valores.setString(2, plp);
            valores.setInt(3, status);
            valores.setString(4, contrato);
            valores.setString(5, cartaoPostagem);
            valores.setString(6, codAdministrativo);
            valores.setInt(7, codECT);
            valores.setString(8, nomeServico);
            valores.setInt(9, idCliente);
            valores.setInt(10, idDepto);
            valores.setInt(11, ar);
            valores.setInt(12, mp);
            valores.setFloat(13, vd);
            valores.setString(14, notaFiscal);
            valores.setString(15, endDest.getNome());
            valores.setString(16, endDest.getCep());
            valores.setString(17, endDest.getLogradouro());
            valores.setString(18, endDest.getNumero());
            valores.setString(19, endDest.getComplemento());
            valores.setString(20, endDest.getBairro());
            valores.setString(21, endDest.getCidade());
            valores.setString(22, endDest.getUf());
            valores.setString(23, endRem.getNome());
            valores.setString(24, endRem.getCep());
            valores.setString(25, endRem.getLogradouro());
            valores.setString(26, endRem.getNumero());
            valores.setString(27, endRem.getComplemento());
            valores.setString(28, endRem.getBairro());
            valores.setString(29, endRem.getCidade());
            valores.setString(30, endRem.getUf());
            valores.execute();
            valores.close();
        } catch (SQLException e) {
            System.out.println(sql);
            //JOptionPane.showMessageDialog(null, "Falha ao inserir - inserePLP no BD:\n- " + e);
        } finally {
            Conexao.desconectar(conn);
        }
    }
    
    public static ArrayList<DadosPLP> consultaNaoImpressos(int idCli, String nomeBD) {
        Connection conn = (Connection) Conexao.conectar(nomeBD);
        String sql = "SELECT * FROM me_plp WHERE idCliente = " + idCli + " AND status = 0 ORDER BY sro";
        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            ResultSet result = (ResultSet) valores.executeQuery();
            ArrayList<DadosPLP> lista = new ArrayList<DadosPLP>();
            while(result.next()) {
                lista.add(new DadosPLP(result));
            }
            valores.close();
            return lista;
        } catch (SQLException e) {
            ContrErroLog.inserir("PortalPostal - ContrImpressaoPLP", "SQLException", sql, e.toString());
            return null;
        } finally {
            Conexao.desconectar(conn);
        }
    }
    
    public static int setarImpresso(String nomeBD, String SROs) {
        Connection conn = Conexao.conectar(nomeBD);
        try {
            String sql = "UPDATE me_plp SET status = 1 WHERE sro IN (" + SROs + ");";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            int i = pstmt.executeUpdate();
            return i;
        } catch (SQLException e) {
            return -1;
        } finally {
            Conexao.desconectar(conn);
        }
    }
    
}
