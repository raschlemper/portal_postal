/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Emporium.Controle;

import Controle.ContrErroLog;
import Util.Conexao;
import Util.FormataString;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author RICARDINHO
 */
public class ContrPreVendaDest {
    
    
    public static int inserir(int idCliente, String nome, String cpf_cnpj, String empresa, String cep, String endereco, String numero, String complemento, String bairro, String cidade, String uf, String email, String celular, String pais, String nomeBD) {
        Connection conn = Conexao.conectar(nomeBD);
        String sql = "INSERT INTO pre_venda_destinatario (idCliente, nome, nome_sa, cpf_cnpj, empresa, cep, endereco, numero, complemento, bairro, cidade, uf, email, celular, pais) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement valores = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            valores.setInt(1, idCliente);
            valores.setString(2, FormataString.removeSpecialChars(nome));
            valores.setString(3, FormataString.removeAccentsToUpper(nome));
            valores.setString(4, cpf_cnpj);
            valores.setString(5, empresa);
            valores.setString(6, cep);
            valores.setString(7, FormataString.removeSpecialChars(endereco));
            valores.setString(8, numero);
            valores.setString(9, complemento);
            valores.setString(10, bairro);
            valores.setString(11, cidade);
            valores.setString(12, uf);
            valores.setString(13, email);
            valores.setString(14, celular);
            valores.setString(15, pais);
            valores.executeUpdate();
            int autoIncrementKey = 0;
            ResultSet rs = valores.getGeneratedKeys();
            if (rs.next()) {
                autoIncrementKey = rs.getInt(1);
            }
            valores.close();
            return autoIncrementKey;
        } catch (SQLException e) {
            System.out.println(e);
            ContrErroLog.inserir("HOITO - contrContato", "SQLException", sql, e.toString());
            return 0;
        } finally {
            Conexao.desconectar(conn);
        }
    }
    
    public static boolean editar(int idDestinatario, int idCliente, String nome, String cpf_cnpj, String empresa, String cep, String endereco, String numero, String complemento, String bairro, String cidade, String uf, String email, String celular, String nomeBD) {
        Connection conn = Conexao.conectar(nomeBD);
        String sql = "UPDATE pre_venda_destinatario SET nome = ?, nome_sa = ?, cpf_cnpj = ?, empresa = ?, cep = ?, endereco = ?, numero = ?, complemento = ?, bairro = ?, cidade = ?, uf = ?, email = ?, celular = ? WHERE idDestinatario = ? AND idCliente = ? ";
        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            valores.setString(1, FormataString.removeSpecialChars(nome));
            valores.setString(2, FormataString.removeSpecialChars(nome));
            valores.setString(3, cpf_cnpj);
            valores.setString(4, empresa);
            valores.setString(5, cep);
            valores.setString(6, endereco);
            valores.setString(7, numero);
            valores.setString(8, complemento);
            valores.setString(9, bairro);
            valores.setString(10, cidade);
            valores.setString(11, uf);
            valores.setString(12, email);
            valores.setString(13, celular);
            valores.setInt(14, idDestinatario);
            valores.setInt(15, idCliente);
            valores.executeUpdate();
            valores.close();
            return true;
        } catch (SQLException e) {
            ContrErroLog.inserir("HOITO - contrContato", "SQLException", sql, e.toString());
            return false;
        } finally {
            Conexao.desconectar(conn);
        }
    }
    
}
