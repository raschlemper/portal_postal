/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controle;

import Util.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author Fernando
 */
public class ContrBoxCubo {

    public static String inserirLog(String mac, String datErro, String erro, String ip) {
      /*  Connection conn = Conexao.conectarBoxCubo();
        String sql = "INSERT INTO log_erro (mac, dataErro, erro, dataInsercao, ip) VALUES('" + mac + "','" + datErro + "','" + erro + "',NOW(),'" + ip + "');";

        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            valores.executeUpdate();
            valores.close();*/
            return "0";/*
        } catch (SQLException e) {
            System.out.println(e);
            ContrErroLog.inserir("HOITO - ContrBoxCubo.inserirLog", "SQLException", sql, e.toString());
            return "1";
        } finally {
            Conexao.desconectar(conn);
        }*/
    }

}
