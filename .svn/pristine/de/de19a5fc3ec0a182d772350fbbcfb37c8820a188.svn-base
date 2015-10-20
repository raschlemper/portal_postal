/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Controle;

import Entidade.Amarracao;
import Util.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author SCC4
 */
public class ContrAmarracao {

    public static int inserir(String nome, String sigla, String cepCT, String nomeCT, String nomeBD) {
        Connection conn = Conexao.conectar(nomeBD);
        String sql = "insert into amarracao (nomeAmarracao, siglaAmarracao, ativo, cepCT, nomeCT) values(?,?,1,?,?)";
        try {
            PreparedStatement valores = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            valores.setString(1, nome);
            valores.setString(2, sigla);
            valores.setString(3, cepCT);
            valores.setString(4, nomeCT);
            valores.executeUpdate();
            
            int autoIncrementKey = 0;
            ResultSet rs = valores.getGeneratedKeys();
            if (rs.next()) {
                autoIncrementKey = rs.getInt(1);
            }
            valores.close();
            return autoIncrementKey;
        } catch (SQLException e) {
            Logger.getLogger(ContrAmarracao.class.getName()).log(Level.WARNING, e.getMessage(), e);
            return 0;
        } finally {
            Conexao.desconectar(conn);
        }
    }

    public static int alterar(String nome, String sigla, int idAmarracao, String cepCT, String nomeCT, String nomeBD) {
        Connection conn = Conexao.conectar(nomeBD);
        String sql = "UPDATE amarracao SET nomeAmarracao=? , siglaAmarracao=?, cepCT=?, nomeCT=? WHERE idAmarracao = ?";
        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            valores.setString(1, nome);
            valores.setString(2, sigla);
            valores.setString(3, cepCT);
            valores.setString(4, nomeCT);
            valores.setInt(5, idAmarracao);
            int i = valores.executeUpdate();
            return i;
        } catch (SQLException e) {
            Logger.getLogger(ContrAmarracao.class.getName()).log(Level.WARNING, e.getMessage(), e);
            return -1;
        } finally {
            Conexao.desconectar(conn);
        }
    }

    public static int excluir(int idAmarracao, String nomeBD) {
        Connection conn = Conexao.conectar(nomeBD);
        try {
            String sql = "update amarracao set ativo = 0 where idAmarracao = ?;";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, idAmarracao);
            int i = pstmt.executeUpdate();
            return i;
        } catch (SQLException e) {
            Logger.getLogger(ContrAmarracao.class.getName()).log(Level.WARNING, e.getMessage(), e);
            return -1;
        } finally {
            Conexao.desconectar(conn);
        }
    }

    public static ArrayList<Amarracao> consultaTodosAmarracao(String nomeBD) {
        Connection con = Conexao.conectar(nomeBD);
        String sql = "SELECT * FROM amarracao WHERE ativo = 1 ORDER BY nomeAmarracao";
        try {
            PreparedStatement valores = con.prepareStatement(sql);
            ResultSet result = (ResultSet) valores.executeQuery();
            ArrayList<Amarracao> listaAmarracao = new ArrayList<Amarracao>();
            for (int i = 0; result.next(); i++) {
                int idAmarracao = result.getInt("idAmarracao");
                int ativo = result.getInt("ativo");
                String nome = result.getString("nomeAmarracao");
                String sigla = result.getString("siglaAmarracao");
                String cepCT = result.getString("cepCT");
                String nomeCT = result.getString("nomeCT");
                Amarracao am = new Amarracao(idAmarracao, nome, sigla, ativo, cepCT, nomeCT);
                listaAmarracao.add(am);
            }
            return listaAmarracao;
        } catch (SQLException e) {
            Logger.getLogger(ContrAmarracao.class.getName()).log(Level.WARNING, e.getMessage(), e);
            return null;
        } finally {
            Conexao.desconectar(con);
        }
    }

    public static Amarracao consultaAmarracaoById(int idAmarracao, String nomeBD) {
        Connection con = Conexao.conectar(nomeBD);
        String sql = "SELECT * FROM amarracao WHERE idAmarracao = " + idAmarracao + " ORDER BY nomeAmarracao";
        try {
            PreparedStatement valores = con.prepareStatement(sql);
            ResultSet result = (ResultSet) valores.executeQuery();
            if (result.next()) {
                int ativo = result.getInt("ativo");
                String nome = result.getString("nomeAmarracao");
                String sigla = result.getString("siglaAmarracao");
                String cepCT = result.getString("cepCT");
                String nomeCT = result.getString("nomeCT");
                Amarracao am = new Amarracao(idAmarracao, nome, sigla, ativo, cepCT, nomeCT);
                return am;
            } else {
                return null;
            }
        } catch (SQLException e) {
            Logger.getLogger(ContrAmarracao.class.getName()).log(Level.WARNING, e.getMessage(), e);
            return null;
        } finally {
            Conexao.desconectar(con);
        }
    }
    
    public static Amarracao consultaAmarracaoByCep(String cep, String servico, String nomeBD){
        cep = cep.replace("-", "").replace(".", "");
        Connection con = Conexao.conectar(nomeBD);
        String sql = "SELECT a.* FROM amarracao_cep AS c"
                + " LEFT JOIN amarracao AS a ON c.idAmarracao = a.idAmarracao"
                + " WHERE cepInicial <= ? AND cepFinal >= ?"
                + " AND c.idAmarracao IN (SELECT idAmarracao FROM amarracao_servico WHERE servico = '"+servico+"');";

        try {
            PreparedStatement valores = con.prepareStatement(sql);
            valores.setString(1, cep);
            valores.setString(2, cep);
            ResultSet result = (ResultSet) valores.executeQuery();
            if (result.next()) {
                int ativo = result.getInt("a.ativo");
                int idAmarracao = result.getInt("a.idAmarracao");
                String nome = result.getString("a.nomeAmarracao");
                String sigla = result.getString("a.siglaAmarracao");
                String cepCT = result.getString("a.cepCT");
                String nomeCT = result.getString("a.nomeCT");
                Amarracao am = new Amarracao(idAmarracao, nome, sigla, ativo, cepCT, nomeCT);
                return am;
            } else {
                return null;
            }
        } catch (SQLException e) {
            Logger.getLogger(ContrAmarracaoCep.class.getName()).log(Level.WARNING, e.getMessage(), e);
            return null;
        } finally {
            Conexao.desconectar(con);
        }
    }

}
