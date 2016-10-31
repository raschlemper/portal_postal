/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Controle;

import Util.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author scc4
 */
public class ContrServicoCombo {    
    
    public static int consultaCodCombo(int codECT, int ar, int mp, float vd) {        
        Connection conn = Conexao.conectarGeral();        
        String sql = "SELECT * FROM servicos_combos WHERE codigo_ECT_servico = "+codECT+" AND ar = "+ar+" AND mp = "+mp+" AND vd <= "+vd+" ;";
        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            ResultSet result = (ResultSet) valores.executeQuery();
            int sv = codECT;
            if (result.next()) {
                sv = result.getInt("codigo_ECT_combo");
            }
            valores.close();
            return sv;
        } catch (SQLException e) {
            ContrErroLog.inserir("HOITO - contrNivel", "SQLException", sql, e.toString());
            System.out.println(e);
            return codECT;
        } finally {
            Conexao.desconectar(conn);
        }
    }
    
}
