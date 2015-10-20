/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controle;

import Util.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author SCC4
 */
public class contrRelatoriosHoito {

    /******************************** CONSULTAS DE RELATORIOS DE COLETA *******************************************/
    public static String pesquisaQtdValorPorMes(String nomeBD, int idCol, int mes, int campo) throws ParseException {

        Connection conn = (Connection) Conexao.conectar(nomeBD);
        String strQuery =
                "SELECT SUM(m.quantidade) AS qtd, SUM(m.valorServico) AS valor "
                + "FROM movimentacao AS m "
                + "JOIN coleta AS c "
                + "ON DATE(c.dataHoraColeta) = m.dataPostagem AND c.idCliente = m.codCliente "
                + "WHERE c.status=3 AND MONTH(c.dataHoraColeta) = '" + mes + "' AND MONTH(m.dataPostagem) = '" + mes + "' AND  c.idColetador = " + idCol;
        try {
            PreparedStatement valores = conn.prepareStatement(strQuery);
            ResultSet result = (ResultSet) valores.executeQuery();

            if (result.next()) {
                if (campo == 1) {
                    return result.getString("qtd");
                } else {
                    return result.getString("valor");
                }
            } else {
                return "0";
            }

        } catch (SQLException ex) {
            ContrErroLog.inserir("HOITO - contrRelatoriosHoito", "SQLException", strQuery, ex.toString());
            return "0";
        } finally {
            Conexao.desconectar(conn);
        }
    }

    public static String pesquisaQtdPorDia(String nomeBD, Date data, int idColetador) throws ParseException {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String data1 = sdf.format(data);

        Connection conn = (Connection) Conexao.conectar(nomeBD);

        String strQuery = "";
        if (idColetador == 0) {
            strQuery = " SELECT COUNT(*) AS qtd"
                    + " FROM coleta "
                    + " WHERE status = 3 AND DATE(dataHoraColeta) = '" + data1 + "' ";
        } else {
            strQuery = " SELECT COUNT(*) AS qtd"
                    + " FROM coleta "
                    + " WHERE status = 3 AND DATE(dataHoraColeta) = '" + data1 + "' AND idColetador = " + idColetador + " ";
        }

        try {
            PreparedStatement valores = conn.prepareStatement(strQuery);
            ResultSet result = (ResultSet) valores.executeQuery();

            if (result.next()) {
                return result.getString("qtd");
            } else {
                return "0";
            }

        } catch (SQLException ex) {
            ContrErroLog.inserir("HOITO - contrRelatoriosHoito", "SQLException", strQuery, ex.toString());
            return "0";
        } finally {
            Conexao.desconectar(conn);
        }
    }

    public static ArrayList pesquisaColetadorPorServico(String nomeBD, int idColetador, String data1, String data2) throws ParseException {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date vdata1 = (java.util.Date) formatter.parse(data1);
        Date vdata2 = (java.util.Date) formatter.parse(data2);
        data1 = sdf.format(vdata1);
        data2 = sdf.format(vdata2);

        Connection conn = (Connection) Conexao.conectar(nomeBD);
        String strQuery = "SELECT COUNT(c.idColeta) AS qtd, m.descServico AS servico "
                + " FROM movimentacao AS m "
                + " JOIN coleta AS c "
                + " ON DATE(c.dataHoraColeta) = m.dataPostagem AND c.idCliente = m.codCliente "
                + " WHERE c.status=3 AND m.dataPostagem BETWEEN '" + data1 + "' AND '" + data2 + "' "
                + " AND DATE(c.dataHoraColeta) BETWEEN '" + data1 + "' AND '" + data2 + "' "
                + " AND c.idColetador = " + idColetador + " "
                + " GROUP BY m.descServico"
                + " ORDER BY qtd DESC";
        if (idColetador == 0) {
            strQuery = "SELECT COUNT(c.idColeta) AS qtd, m.descServico AS servico "
                    + " FROM movimentacao AS m "
                    + " JOIN coleta AS c "
                    + " ON DATE(c.dataHoraColeta) = m.dataPostagem AND c.idCliente = m.codCliente "
                    + " WHERE c.status=3 AND m.dataPostagem BETWEEN '" + data1 + "' AND '" + data2 + "' "
                    + " AND DATE(c.dataHoraColeta) BETWEEN '" + data1 + "' AND '" + data2 + "' "
                    + " GROUP BY m.descServico"
                    + " ORDER BY qtd DESC";
        }
        try {
            PreparedStatement valores = conn.prepareStatement(strQuery);
            ResultSet result = (ResultSet) valores.executeQuery();

            ArrayList lista = new ArrayList();

            for (int i = 0; result.next(); i++) {
                String qtd = result.getString("qtd").trim();
                String servico = result.getString("servico").trim();
                String[] obj = {qtd, servico};
                lista.add(obj);
            }

            valores.close();
            return lista;

        } catch (SQLException ex) {
            ContrErroLog.inserir("HOITO - contrRelatoriosHoito", "SQLException", strQuery, ex.toString());
            return null;
        } finally {
            Conexao.desconectar(conn);
        }
    }

    public static ArrayList pesquisaQtdValorPorColetador(String nomeBD, String data1, String data2, int idColetador) throws ParseException {

        Connection conn = (Connection) Conexao.conectar(nomeBD);
        String strQuery = "";
        if (idColetador == 0) {
            strQuery = "SELECT MONTH(c.dataHoraColeta) AS mes, c.idColetador AS idCol, SUM(m.quantidade) AS qtd, SUM(m.valorServico) AS valor "
                    + " FROM movimentacao AS m "
                    + " JOIN coleta AS c "
                    + " ON DATE(c.dataHoraColeta) = m.dataPostagem AND c.idCliente = m.codCliente "
                    + " WHERE c.status=3 AND MONTH(c.dataHoraColeta) BETWEEN '" + data1 + "' AND '" + data2 + "' "
                    + " GROUP BY MONTH(c.dataHoraColeta), c.idColetador";
        } else {
            strQuery = "SELECT MONTH(c.dataHoraColeta) AS mes, c.idColetador AS idCol, SUM(m.quantidade) AS qtd, SUM(m.valorServico) AS valor "
                    + " FROM movimentacao AS m "
                    + " JOIN coleta AS c "
                    + " ON DATE(c.dataHoraColeta) = m.dataPostagem AND c.idCliente = m.codCliente "
                    + " WHERE c.status=3 AND MONTH(c.dataHoraColeta) BETWEEN '" + data1 + "' AND '" + data2 + "' AND c.idColetador = " + idColetador + " "
                    + " GROUP BY MONTH(c.dataHoraColeta), c.idColetador";
        }
        try {
            PreparedStatement valores = conn.prepareStatement(strQuery);
            ResultSet result = (ResultSet) valores.executeQuery();

            ArrayList lista = new ArrayList();

            for (int i = 0; result.next(); i++) {
                String mes = result.getString("mes").trim();
                String idCol = result.getString("idCol").trim();
                String qtd = result.getString("qtd").trim();
                String valor = result.getString("valor").trim();
                String[] obj = {mes, idCol, valor, qtd};
                lista.add(obj);
            }

            valores.close();
            return lista;

        } catch (SQLException ex) {
            ContrErroLog.inserir("HOITO - contrRelatoriosHoito", "SQLException", strQuery, ex.toString());
            return null;
        } finally {
            Conexao.desconectar(conn);
        }
    }

    public static Timestamp consultaUltimaColetaDoCliente(int idCliente, String nomeBD) {

        Connection conn = Conexao.conectar(nomeBD);
        String sql = "SELECT dataHoraColeta FROM coleta WHERE status = 3 AND idCliente=? ORDER BY dataHoraColeta DESC LIMIT 1;";

        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            valores.setInt(1, idCliente);
            ResultSet result = (ResultSet) valores.executeQuery();
            if (result.next()) {
                return result.getTimestamp("dataHoraColeta");
            } else {
                return null;
            }
        } catch (SQLException e) {
            ContrErroLog.inserir("HOITO - contrRelatoriosHoito", "SQLException", sql, e.toString());
            return null;
        } finally {
            Conexao.desconectar(conn);
        }
    }

    public static ArrayList pesquisaQtdValorPorIdCliente(String nomeBD, int idCliente, Connection conn) throws ParseException {

        String strQuery = "SELECT MONTH(dataPostagem) AS mes, SUM(quantidade) AS qtd, SUM(valorServico) AS valor"
                + " FROM movimentacao"
                + " WHERE codCliente = " + idCliente + ""
                + " AND MONTH(dataPostagem) BETWEEN MONTH(DATE_ADD(NOW(),INTERVAL -1 MONTH)) AND MONTH(NOW())"
                + " GROUP BY MONTH(dataPostagem)"
                + " ORDER BY mes ASC";

        try {
            PreparedStatement valores = conn.prepareStatement(strQuery);
            ResultSet result = (ResultSet) valores.executeQuery();

            ArrayList lista = new ArrayList();

            for (int i = 0; result.next(); i++) {
                String mes = result.getString("mes").trim();
                String qtd = result.getString("qtd").trim();
                String valor = result.getString("valor").trim();
                String[] obj = {mes, valor, qtd};
                lista.add(obj);
            }

            valores.close();
            return lista;

        } catch (SQLException ex) {
            ContrErroLog.inserir("HOITO - contrRelatoriosHoito", "SQLException", strQuery, ex.toString());
            return null;
        }
    }
    
}
