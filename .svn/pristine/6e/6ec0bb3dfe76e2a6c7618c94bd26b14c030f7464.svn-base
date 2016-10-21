
package Controle;

import Util.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author RICARDINHO
 */
public class contrRobozinho {

    public static ArrayList<String> consultaNomesBD() {
        Connection conn = (Connection) Conexao.conectarGeral();
        String sql = "SELECT cnpj FROM empresas WHERE crm = 1 AND status = 1 ";
        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            ResultSet result = (ResultSet) valores.executeQuery();
            ArrayList<String> lista = new ArrayList<String>();
            for (int i = 0; result.next(); i++) {
                String cnpj = result.getString("cnpj");
                lista.add(cnpj);
            }
            valores.close();
            return lista;
        } catch (Exception e) {
            System.out.println("ROBOZINHO = FALHA AO EXECUTAR SQL: " + e);
            return null;
        } finally {
            Conexao.desconectar(conn);
        }
    }
    
    public static ArrayList<String> consultaSro(String cnpj) {
        Connection conn = (Connection) Conexao.conectar(cnpj);
        String sql = "SELECT TRIM(numObjeto) AS numObj FROM movimentacao WHERE numObjeto <> '-' "
                + " AND codStatus NOT IN (1, 5, 26, 27, 42, 9, 12, 28, 31, 43, 44, 50, 51, 52, 69) ORDER BY dataPostagem;";
        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            ResultSet result = (ResultSet) valores.executeQuery();
            ArrayList<String> lista = new ArrayList<String>();
            String group = "";
            for (int i = 0; result.next(); i++) {
                String numRegistro = result.getString("numObj");
                group += numRegistro;
                if((i+1)%5 == 0){
                    lista.add(group);
                    group = "";
                }
            }
            if(!"".equals(group)){
                lista.add(group);
            }
            valores.close();
            return lista;
        } catch (Exception e) {
            System.out.println("ROBOZINHO = FALHA AO EXECUTAR SQL: " + e);
            return null;
        } finally {
            Conexao.desconectar(conn);
        }
    }

    public static int alterarStatusSro(String codSro, String codStatus, String nomeStatus, String dataStatus, String nomeBD, Connection conn) throws ParseException {
        //Connection conn = Conexao.conectar(nomeBD);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Date data1 = (java.util.Date) df.parse(dataStatus);
        dataStatus = sdf.format(data1);

        String sql = "UPDATE movimentacao SET codStatus = " + codStatus + " , status='" + nomeStatus + "' , dataEntrega='" + dataStatus + "' WHERE numObjeto = '" + codSro + "'";

        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            int i = valores.executeUpdate();
            return i;
        } catch (SQLException e) {
            System.out.println("ROBOZINHO = FALHA AO EXECUTAR SQL: " + e);
            return -1;
        }/* finally {
            Conexao.desconectar(conn);
        }*/
    }

    public static void excluirDadosAntigos(String nomeBD) {
        Connection conn = Conexao.conectar(nomeBD);
        int meses = 4; //TEMPO EM MESES
        if (nomeBD.equals("80037815000184")) {
            meses = 6;
        }
                
        String sqlMov = "DELETE FROM movimentacao WHERE dataPostagem < DATE_ADD(NOW(), INTERVAL -"+meses+" MONTH);";
        String sqlAr = "DELETE FROM movimentacao_ar WHERE dataBaixaAr < DATE_ADD(NOW(), INTERVAL -"+meses+" MONTH);";
        
        String sqlCol = "DELETE FROM coleta WHERE dataHoraColeta < DATE_ADD(NOW(), INTERVAL -"+meses+" MONTH);";
        String sqlColLog = "DELETE FROM log_coleta WHERE dataHora < DATE_ADD(NOW(), INTERVAL -"+meses+" MONTH);";
        String sqlColFixaLog = "DELETE FROM log_coleta_fixa WHERE dataHoraCarregada < DATE_ADD(NOW(), INTERVAL -"+meses+" MONTH);";
        
        String sqlImpAr = "DELETE FROM log_importacao_ar WHERE dataFim < DATE_ADD(NOW(), INTERVAL -"+meses+" MONTH);";
        String sqlImpMov = "DELETE FROM log_importacao_mov WHERE dataFim < DATE_ADD(NOW(), INTERVAL -"+meses+" MONTH);";
        String sqlImpCli = "DELETE FROM log_importacao_cli WHERE dataimportacao < DATE_ADD(NOW(), INTERVAL -"+meses+" MONTH);";
        String sqlImpDep = "DELETE FROM log_importacao_deptos WHERE dataimportacao < DATE_ADD(NOW(), INTERVAL -"+meses+" MONTH);";
        
        try {
            PreparedStatement valores = conn.prepareStatement(sqlMov);
            valores.executeUpdate();
            valores = conn.prepareStatement(sqlCol);
            valores.executeUpdate();
        } catch (SQLException e) {
            System.out.println("ROBOZINHO = FALHA AO EXECUTAR SQL: " + e);
        } finally {
            Conexao.desconectar(conn);
        }
    }
}
