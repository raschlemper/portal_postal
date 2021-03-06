/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Coleta.Controle;

import Coleta.Entidade.Coleta;
import Controle.ContrErroLog;
import Util.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author SCC4
 */
public class contrColeta {

    public static int inserir(int idCliente, int idUsuario, int idColetador, int idContato, int idTipo, int status, Timestamp dataHoraAtual, Timestamp dataHoraColeta, String obs, int tipoSolicitacao, String nomeBD) {
        Connection conn = Conexao.conectar(nomeBD);
        String sql = "insert into coleta (idCliente, idUsuario, idColetador, idContato, idTipo, status, dataHoraSolicitacao, dataHoraColeta, obs, tipoSolicitacao) values(?,?,?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement valores = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            valores.setInt(1, idCliente);
            valores.setInt(2, idUsuario);
            valores.setInt(3, idColetador);
            valores.setInt(4, idContato);
            valores.setInt(5, idTipo);
            valores.setInt(6, status);
            valores.setTimestamp(7, dataHoraAtual);
            valores.setTimestamp(8, dataHoraColeta);
            valores.setString(9, obs);
            valores.setInt(10, tipoSolicitacao);
            valores.executeUpdate();
            int autoIncrementKey = 0;
            ResultSet rs = valores.getGeneratedKeys();
            if (rs.next()) {
                autoIncrementKey = rs.getInt(1);
            }
            valores.close();
            return autoIncrementKey;
        } catch (SQLException e) {
            ContrErroLog.inserir("HOITO - contrColeta", "SQLException", sql, e.toString());
            return 0;
        } finally {
            Conexao.desconectar(conn);
        }
    }

    public static int alterarStatus(int idColeta, int status, String nomeBD) {
        Connection conn = Conexao.conectar(nomeBD);
        String sql = "update coleta set status=? where idColeta = ?";
        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            valores.setInt(1, status);
            valores.setInt(2, idColeta);
            int i = valores.executeUpdate();
            return i;
        } catch (SQLException e) {
            ContrErroLog.inserir("HOITO - contrColeta", "SQLException", sql, e.toString());
            return -1;
        } finally {
            Conexao.desconectar(conn);
        }
    }

    public static int alterarHoraAguardandoCliente(int idColeta, String nomeBD) {
        Connection conn = Conexao.conectar(nomeBD);
        String sql = "update coleta set dataHoraAguardando = NOW() where idColeta = ?";
        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            valores.setInt(1, idColeta);
            int i = valores.executeUpdate();
            return i;
        } catch (SQLException e) {
            ContrErroLog.inserir("HOITO - contrColeta", "SQLException", sql, e.toString());
            return -1;
        } finally {
            Conexao.desconectar(conn);
        }
    }
public static int editarAjax(String idColeta, String param, String value, String nomeBD) {
        Connection conn = Conexao.conectar(nomeBD); 
        value = value+":00";
        String sql = "UPDATE coleta SET "+param+" = CONCAT(DATE(dataHoraColeta),' "+value+"')  WHERE idColeta = " + idColeta+" ;";        
        System.out.println(sql);
        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            valores.executeUpdate();
            valores.close();
            int i = valores.executeUpdate();
            return i;
        } catch (SQLException e) {
            ContrErroLog.inserir("contrContato.editarAjax", "SQLException", sql, e.toString());
            return -1;
        } finally {
            Conexao.desconectar(conn);
        }
}
    public static int alterarObs(int idColeta, String obs, String nomeBD) {
        Connection conn = Conexao.conectar(nomeBD);
        String sql = "update coleta set obs=? where idColeta = ?";
        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            valores.setString(1, obs);
            valores.setInt(2, idColeta);
            int i = valores.executeUpdate();
            return i;
        } catch (SQLException e) {
            ContrErroLog.inserir("HOITO - contrColeta", "SQLException", sql, e.toString());
            return -1;
        } finally {
            Conexao.desconectar(conn);
        }
    }

    public static int alterarStatusAtrasadas(int status, String nomeBD) {
        Connection conn = Conexao.conectar(nomeBD);
        String sql = "update coleta set status=? where DATE(dataHoraColeta) < DATE(NOW()) ;";
        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            valores.setInt(1, status);
            int i = valores.executeUpdate();
            return i;
        } catch (SQLException e) {
            ContrErroLog.inserir("HOITO - contrColeta", "SQLException", sql, e.toString());
            return -1;
        } finally {
            Conexao.desconectar(conn);
        }
    }

    public static int darBaixaColetas(String idColetas, int status, int statusEntrega, Timestamp dataHoraAtual, String nomeBD) {
        Connection conn = Conexao.conectar(nomeBD);
        String sql = "UPDATE coleta SET status=?, statusEntrega=?, dataHoraBaixa=? WHERE idColeta IN(" + idColetas + ")";

        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            valores.setInt(1, status);
            valores.setInt(2, statusEntrega);
            valores.setTimestamp(3, dataHoraAtual);
            int i = valores.executeUpdate();
            return i;
        } catch (SQLException e) {
            ContrErroLog.inserir("HOITO - contrColeta", "SQLException", sql, e.toString());
            return -1;
        } finally {
            Conexao.desconectar(conn);
        }
    }
    public static int darBaixa(int idColeta, int status, int statusEntrega, String nomeBD) {
        Connection conn = Conexao.conectar(nomeBD);
        String sql = "update coleta set status=?, statusEntrega=?, dataHoraBaixa=NOW() where idColeta = ?";

        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            valores.setInt(1, status);
            valores.setInt(2, statusEntrega);
            valores.setInt(3, idColeta);
            int i = valores.executeUpdate();
            return i;
        } catch (SQLException e) {
            ContrErroLog.inserir("HOITO - contrColeta", "SQLException", sql, e.toString());
            return -1;
        } finally {
            Conexao.desconectar(conn);
        }
    }

    public static int alterarColetador(int idColeta, int idColetador, String nomeBD) {
        Connection conn = Conexao.conectar(nomeBD);
        String sql = "update coleta set idColetador=? where idColeta = ?";
        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            valores.setInt(1, idColetador);
            valores.setInt(2, idColeta);
            int i = valores.executeUpdate();
            return i;
        } catch (SQLException e) {
            ContrErroLog.inserir("HOITO - contrColeta", "SQLException", sql, e.toString());
            return -1;
        } finally {
            Conexao.desconectar(conn);
        }
    }
    public static int alterarColetadordasColetas(String idColetas, int idColetador, boolean isWeb ,String nomeBD) {
        Connection conn = Conexao.conectar(nomeBD);
        
        String sql = "UPDATE coleta SET idColetador=? WHERE idColeta IN("+idColetas+");";
        if(isWeb){
            
        sql = "UPDATE coleta SET idColetador = ?, status = 2 WHERE idColeta IN("+idColetas+");";
        }
        
        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            valores.setInt(1, idColetador);
            int i = valores.executeUpdate();
            return i;
        } catch (SQLException e) {
            ContrErroLog.inserir("HOITO - contrColeta", "SQLException", sql, e.toString());
            return -1;
        } finally {
            Conexao.desconectar(conn);
        }
    }

    public static int excluirAtrasadas(int status, String nomeBD) {
        Connection conn = Conexao.conectar(nomeBD);
        String sql = "delete from coleta where status = ? AND DATE(dataHoraColeta) < DATE(NOW()) ;";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, status);
            int i = pstmt.executeUpdate();
            return i;
        } catch (SQLException e) {
            ContrErroLog.inserir("HOITO - contrColeta", "SQLException", sql, e.toString());
            return -1;
        } finally {
            Conexao.desconectar(conn);
        }
    }

    public static ArrayList<Coleta> consultaUltimasColetasDoCliente(int idCliente, String nomeBD) {
        Connection conn = Conexao.conectar(nomeBD);
        String sql = "SELECT * FROM coleta WHERE idCliente=? ORDER BY idColeta DESC LIMIT 5;";
        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            valores.setInt(1, idCliente);
            ResultSet result = (ResultSet) valores.executeQuery();
            ArrayList<Coleta> listaStatus = new ArrayList();
            for (int i = 0; result.next(); i++) {
                int idColeta = result.getInt("idColeta");
                int idUsuario = result.getInt("idUsuario");
                int idColetador = result.getInt("idColetador");
                int idContato = result.getInt("idContato");
                int idTipo = result.getInt("idTipo");
                int status = result.getInt("status");
                Timestamp dataHoraSolicitacao = result.getTimestamp("dataHoraSolicitacao");
                Timestamp dataHoraColeta = result.getTimestamp("dataHoraColeta");
                Timestamp dataHoraBaixa = result.getTimestamp("dataHoraBaixa");
                Timestamp dataHoraAguardando = result.getTimestamp("dataHoraAguardando");
                String obs = result.getString("obs");
                int statusEntrega = result.getInt("statusEntrega");
                int tipoSolicitacao = result.getInt("tipoSolicitacao");
                Coleta coleta = new Coleta(idColeta, idCliente, idUsuario, idColetador, idContato, idTipo, status, dataHoraSolicitacao, dataHoraColeta, dataHoraBaixa, obs, statusEntrega, tipoSolicitacao, dataHoraAguardando);
                listaStatus.add(coleta);
            }
            valores.close();
            return listaStatus;
        } catch (SQLException e) {
            ContrErroLog.inserir("consultaUltimasColetasDoCliente", "SQLException", sql, e.toString());
            return null;
        } finally {
            Conexao.desconectar(conn);
        }
    }

    public static ArrayList<Coleta> consultaUltimasColetas(String nomeBD, String data) {
        Connection conn = Conexao.conectar(nomeBD);
       // String sql = "SELECT * FROM coleta WHERE DATE(dataHoraColeta)='" + data + "' and status > 2 ORDER BY dataHoraBaixa DESC LIMIT 10;";
        String sql = "SELECT * FROM coleta WHERE DATE(dataHoraColeta)='" + data + "' and status > 2 ORDER BY dataHoraBaixa DESC;";

        try {
            PreparedStatement valores = conn.prepareStatement(sql);

            ResultSet result = (ResultSet) valores.executeQuery();
            ArrayList<Coleta> listaStatus = new ArrayList();
            for (int i = 0; result.next(); i++) {
                int idCliente = result.getInt("idCliente");
                int idColeta = result.getInt("idColeta");
                int idUsuario = result.getInt("idUsuario");
                int idColetador = result.getInt("idColetador");
                int idContato = result.getInt("idContato");
                int idTipo = result.getInt("idTipo");
                int status = result.getInt("status");
                Timestamp dataHoraSolicitacao = result.getTimestamp("dataHoraSolicitacao");
                Timestamp dataHoraColeta = result.getTimestamp("dataHoraColeta");
                Timestamp dataHoraBaixa = result.getTimestamp("dataHoraBaixa");
                Timestamp dataHoraAguardando = result.getTimestamp("dataHoraAguardando");
                String obs = result.getString("obs");
                int statusEntrega = result.getInt("statusEntrega");
                int tipoSolicitacao = result.getInt("tipoSolicitacao");
                Coleta coleta = new Coleta(idColeta, idCliente, idUsuario, idColetador, idContato, idTipo, status, dataHoraSolicitacao, dataHoraColeta, dataHoraBaixa, obs, statusEntrega, tipoSolicitacao, dataHoraAguardando);
                listaStatus.add(coleta);
            }
            valores.close();
            return listaStatus;
        } catch (SQLException e) {
            ContrErroLog.inserir("consultaUltimasColetasDoCliente", "SQLException", sql, e.toString());
            return null;
        } finally {
            Conexao.desconectar(conn);
        }
    }

    public static int consultaIdTipoDaUltimaColetaDoCliente(int idCliente, String nomeBD) {
        Connection conn = Conexao.conectar(nomeBD);
        String sql = "SELECT idTipo FROM coleta WHERE idCliente=? ORDER BY idColeta DESC LIMIT 5;";
        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            valores.setInt(1, idCliente);
            ResultSet result = (ResultSet) valores.executeQuery();
            if (result.next()) {
                return result.getInt("idTipo");
            } else {
                return 0;
            }
        } catch (SQLException e) {
            ContrErroLog.inserir("consultaIdTipoDaUltimaColetaDoCliente", "SQLException", sql, e.toString());
            return 0;
        } finally {
            Conexao.desconectar(conn);
        }
    }

    public static String consultaObsByIdColeta(int idColeta, String nomeBD) {
        Connection conn = Conexao.conectar(nomeBD);
        String sql = "SELECT obs FROM coleta WHERE idColeta = " + idColeta;
        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            ResultSet result = (ResultSet) valores.executeQuery();
            if (result.next()) {
                return result.getString("obs");
            } else {
                return "";
            }
        } catch (SQLException e) {
            ContrErroLog.inserir("consultaObsByIdColeta", "SQLException", sql, e.toString());
            return "";
        } finally {
            Conexao.desconectar(conn);
        }
    }

    public static Map<Integer, Integer> pesquisaColetaSolicitada(String nomeBD, String data) {
        Map<Integer, Integer> mapA = new HashMap();
        Connection conn = Conexao.conectar(nomeBD);
        String sql = "SELECT HOUR(dataHoraColeta) as h, COUNT(*) as c FROM coleta WHERE DATE(dataHoraColeta)= '" + data + "' GROUP BY HOUR(dataHoraColeta);";

        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            ResultSet result = (ResultSet) valores.executeQuery();
            while (result.next()) {
                int hora = result.getInt("h");
                int quantidade = result.getInt("c");
                mapA.put(hora, quantidade);
            }
        } catch (SQLException e) {
            ContrErroLog.inserir("pesquisaColetaSolicitada", "SQLException", sql, e.toString());

        } finally {
            Conexao.desconectar(conn);
        }
        return mapA;
    }

    public static Map<Integer, Integer> pesquisaColetaEfetuada(String nomeBD, String data) {
        Map<Integer, Integer> mapB = new HashMap();

        Connection conn = Conexao.conectar(nomeBD);
        String sql = "SELECT HOUR(dataHoraBaixa) as h, COUNT(*) as c FROM coleta WHERE DATE(dataHoraBaixa)= '" + data + "' GROUP BY HOUR(dataHoraBaixa);";

        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            ResultSet result = (ResultSet) valores.executeQuery();
            while (result.next()) {
                int hora = result.getInt("h");
                int quantidade = result.getInt("c");
                mapB.put(hora, quantidade);
            }
        } catch (SQLException e) {
            ContrErroLog.inserir("pesquisaColetaEfetuada", "SQLException", sql, e.toString());

        } finally {
            Conexao.desconectar(conn);
        }
        return mapB;
    }

    public static int vMap(Map<Integer, Integer> mp, int key) {       

        if (mp.containsKey(key)) {
            return mp.get(key);
        }
        return 0;
    }

    public static String consultaQtdColetasSolicitadas(String nomeBD) {
        Connection conn = Conexao.conectar(nomeBD);
        String sql = "SELECT COUNT(idColeta) AS qtd FROM coleta WHERE status = 1";        
        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            ResultSet result = (ResultSet) valores.executeQuery();
            if (result.next()) {
                return result.getString("qtd");
            } else {
                return "0";
            }
        } catch (SQLException e) {
            ContrErroLog.inserir("HOITO - contrColeta", "SQLException", sql, e.toString());
            return "0";
        } finally {
            Conexao.desconectar(conn);
        }
    }

    public static boolean verificaSeJaHouveColeta(int idColetador, String data, String nomeBD){
        Connection conn = Conexao.conectar(nomeBD);
        String sql = "SELECT *"
                + " FROM coleta AS c"
                + " WHERE c.idColetador = ? AND DATE(dataHoraColeta) = ? " 
                + " AND status IN (4,5,6,7);";
        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            valores.setInt(1, idColetador);
            valores.setString(2, data);
            ResultSet result = (ResultSet) valores.executeQuery();
            boolean r = result.next();
            valores.close();
            return r;
        } catch (SQLException e) {
            ContrErroLog.inserir("HOITO - contrColeta", "SQLException", sql, e.toString());
            return false;
        } finally {
            Conexao.desconectar(conn);
        }
    }
    public static boolean verificaSeJaHouveRotaFixa(String data, String nomeBD){
        Connection conn = Conexao.conectar(nomeBD);
        String sql = "SELECT * FROM log_coleta_fixa WHERE DATE(dataHoraCarregada) = '"+data+"';";
        try {
            PreparedStatement valores = conn.prepareStatement(sql);           
            ResultSet result = (ResultSet) valores.executeQuery();
            boolean r = result.next();
            valores.close();
            return r;
        } catch (SQLException e) {
            ContrErroLog.inserir("HOITO - contrColeta", "SQLException", sql, e.toString());
            return false;
        } finally {
            Conexao.desconectar(conn);
        }
    }
    
    public static ArrayList<Coleta> consultaColetasPeloStatus(int status, int idColetador, String data, String ordem, String nomeBD) {
        Connection conn = Conexao.conectar(nomeBD);
        String sql = "SELECT idColeta, status, idCliente, idTipo, dataHoraColeta, dataHoraAguardando, dataHoraBaixa, dataHoraSolicitacao, c.obs,"
                + " cliente.nome, cliente.nomeFantasia, coleta_tipos.tipo, coleta_coletador.nome, statusEntrega, tipoSolicitacao, cs.nome"
                + " FROM coleta AS c"
                + " LEFT JOIN cliente ON cliente.codigo = idCliente"
                + " LEFT JOIN coleta_status AS cs ON c.status = cs.id "
                + " LEFT JOIN coleta_tipos ON coleta_tipos.idTipoColeta = idTipo"
                + " LEFT JOIN coleta_coletador ON coleta_coletador.idColetador = c.idColetador"
                + " WHERE c.idColetador = ? AND DATE(dataHoraColeta) = ? ";
        if (status == 3 || status == 4) {
           
            sql += " AND (status = 3 OR status = 4)";
        } else if (status == 2) {
           
            sql += " AND (status = 2 OR status = 6)";
        } else if (status == 5) {
           
            sql += " AND (status = 5 OR status = 7)";
        } else if (status > 0) {
           
            sql += " AND status = " + status;
        }
        sql += " ORDER BY " + ordem;
        
        
        ArrayList<Coleta> listaStatus = new ArrayList();
        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            valores.setInt(1, idColetador);
            valores.setString(2, data);
            ResultSet result = (ResultSet) valores.executeQuery();
            for (int i = 0; result.next(); i++) {
                int idColeta = result.getInt("idColeta");
                int idCliente = result.getInt("idCliente");
                Timestamp dataHoraSolicitacao = result.getTimestamp("dataHoraSolicitacao");
                Timestamp dataHoraColeta = result.getTimestamp("dataHoraColeta");
                Timestamp dataHoraBaixa = result.getTimestamp("dataHoraBaixa");
                Timestamp dataHoraAguardando = result.getTimestamp("dataHoraAguardando");
                String obs = result.getString("c.obs");
                String nomeCliente = result.getString("cliente.nome");
                String nomeFantasia = result.getString("cliente.nomeFantasia");
                String tipoColeta = result.getString("coleta_tipos.tipo");
                if (tipoColeta == null) {
                    tipoColeta = "Sol. WEB";
                }
                String nomeColetador = result.getString("coleta_coletador.nome");
                int statusEntrega = result.getInt("status");
                int tipoSolicitacao = result.getInt("tipoSolicitacao");
                String nomeStatus = result.getString("cs.nome");
                Coleta coleta = new Coleta(idColeta, idCliente, dataHoraColeta, dataHoraBaixa, obs, nomeCliente, nomeFantasia, tipoColeta, nomeColetador, dataHoraSolicitacao, statusEntrega, tipoSolicitacao, idColetador, dataHoraAguardando, nomeStatus);
                listaStatus.add(coleta);
            }
            valores.close();
            return listaStatus;
        } catch (SQLException e) {

            ContrErroLog.inserir("HOITO - contrColeta", "SQLException", sql, e.toString());
            return listaStatus;
        } finally {
            Conexao.desconectar(conn);
        }
    }

    public static String consultaStatusById(int status, String nomeBD) {

        String nomeStatus = "";
        Connection conn = Conexao.conectar(nomeBD);

        String sql = "SELECT nome FROM coleta_status WHERE id = " + status + ";";
        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            ResultSet result = (ResultSet) valores.executeQuery();

            while (result.next()) {
                nomeStatus = result.getString("nome");
            }
        } catch (SQLException e) {
            ContrErroLog.inserir("consultaStatusById", "SQLException", sql, e.toString());
            return null;
        } finally {
            Conexao.desconectar(conn);
        }

        return nomeStatus;
    }

    public static ArrayList<Coleta> consultaColetasPelaWeb(String ordem, String nomeBD) {
        Connection conn = Conexao.conectar(nomeBD);
        String sql = "SELECT idColeta, idCliente, idTipo, dataHoraColeta, dataHoraBaixa, dataHoraSolicitacao, dataHoraAguardando, c.obs, cs.nome,"
                + " cliente.nome, cliente.nomeFantasia, coleta_tipos.tipo, coleta_coletador.nome, statusEntrega, tipoSolicitacao, c.idColetador"
                + " FROM coleta AS c"
                + " LEFT JOIN cliente ON cliente.codigo = idCliente"
                + " LEFT JOIN coleta_status AS cs ON c.status = cs.id "
                + " LEFT JOIN coleta_tipos ON coleta_tipos.idTipoColeta = idTipo"
                + " LEFT JOIN coleta_coletador ON coleta_coletador.idColetador = c.idColetador"
                + " WHERE status = 1 ORDER BY " + ordem;

        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            ResultSet result = (ResultSet) valores.executeQuery();
            ArrayList<Coleta> listaStatus = new ArrayList();
            for (int i = 0; result.next(); i++) {
                int idColetador = result.getInt("idColetador");
                int idColeta = result.getInt("idColeta");
                int idCliente = result.getInt("idCliente");
                Timestamp dataHoraSolicitacao = result.getTimestamp("dataHoraSolicitacao");
                Timestamp dataHoraColeta = result.getTimestamp("dataHoraColeta");
                Timestamp dataHoraBaixa = result.getTimestamp("dataHoraBaixa");
                Timestamp dataHoraAguardando = result.getTimestamp("dataHoraAguardando");
                String obs = result.getString("c.obs");
                String nomeCliente = result.getString("cliente.nome");
                String nomeFantasia = result.getString("cliente.nomeFantasia");
                String tipoColeta = result.getString("coleta_tipos.tipo");
                String nomeColetador = result.getString("coleta_coletador.nome");
                int statusEntrega = result.getInt("statusEntrega");
                int tipoSolicitacao = result.getInt("tipoSolicitacao");
                String nomeStatus = result.getString("cs.nome");
                Coleta coleta = new Coleta(idColeta, idCliente, dataHoraColeta, dataHoraBaixa, obs, nomeCliente, nomeFantasia, tipoColeta, nomeColetador, dataHoraSolicitacao, statusEntrega, tipoSolicitacao, idColetador, dataHoraAguardando, nomeStatus);
                listaStatus.add(coleta);
            }
            valores.close();
            return listaStatus;
        } catch (SQLException e) {
            ContrErroLog.inserir("consultaColetasPelaWeb", "SQLException", sql, e.toString());
            return null;
        } finally {
            Conexao.desconectar(conn);
        }
    }

    public static ArrayList<String> consultaColetasAntigasNaoFinalizadas(int idColetador, String nomeBD) {
        Connection conn = Conexao.conectar(nomeBD);
        String sql = "SELECT DISTINCT DATE(dataHoraColeta) FROM coleta WHERE status < 3 AND idColetador = " + idColetador + " AND DATE(dataHoraColeta) < DATE(NOW()) ORDER BY dataHoraColeta ;";
        if (idColetador == 0) {
            sql = "SELECT DISTINCT DATE(dataHoraColeta) FROM coleta WHERE status < 3 AND DATE(dataHoraColeta) < DATE(NOW()) ORDER BY dataHoraColeta ;";
        }
        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            ResultSet result = (ResultSet) valores.executeQuery();
            ArrayList<String> listaStatus = new ArrayList<String>();
            for (int i = 0; result.next(); i++) {
                Date data = result.getDate("DATE(dataHoraColeta)");
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                listaStatus.add(sdf.format(data));
            }
            valores.close();
            return listaStatus;
        } catch (SQLException e) {
            ContrErroLog.inserir("HOITO - contrColeta", "SQLException", sql, e.toString());
            return null;
        } finally {
            Conexao.desconectar(conn);
        }
    }

    public static ArrayList<Coleta> consultaTodasColetasDoColetador(int idColetador, String data, String ordem, String nomeBD) {
        Connection conn = Conexao.conectar(nomeBD);
        String sql = "SELECT * FROM coleta WHERE idColetador=? and DATE(dataHoraColeta)=? and status IN (2,4,5,6,7) ORDER BY " + ordem;
        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            valores.setInt(1, idColetador);
            valores.setString(2, data);
            ResultSet result = (ResultSet) valores.executeQuery();
            ArrayList<Coleta> listaStatus = new ArrayList();
            for (int i = 0; result.next(); i++) {
                int idColeta = result.getInt("idColeta");
                int idUsuario = result.getInt("idUsuario");
                int idContato = result.getInt("idContato");
                int idTipo = result.getInt("idTipo");
                int idCliente = result.getInt("idCliente");
                int status = result.getInt("status");
                Timestamp dataHoraSolicitacao = result.getTimestamp("dataHoraSolicitacao");
                Timestamp dataHoraColeta = result.getTimestamp("dataHoraColeta");
                Timestamp dataHoraBaixa = result.getTimestamp("dataHoraBaixa");
                Timestamp dataHoraAguardando = result.getTimestamp("dataHoraAguardando");
                String obs = result.getString("obs");
                int statusEntrega = result.getInt("statusEntrega");
                int tipoSolicitacao = result.getInt("tipoSolicitacao");

                Coleta coleta = new Coleta(idColeta, idCliente, idUsuario, idColetador, idContato, idTipo, status, dataHoraSolicitacao, dataHoraColeta, dataHoraBaixa, obs, statusEntrega, tipoSolicitacao, dataHoraAguardando);
                listaStatus.add(coleta);
            }
            valores.close();
            return listaStatus;
        } catch (SQLException e) {
            ContrErroLog.inserir("consultaTodasColetasDoColetador", "SQLException", sql, e.toString());
            return null;
        } finally {
            Conexao.desconectar(conn);
        }
    }

    public static ArrayList<Coleta> consultaTodasFinalizadas(int idColetador, String data, String ordem, String nomeBD) {
        Connection conn = Conexao.conectar(nomeBD);
        String sql = "SELECT * FROM coleta WHERE idColetador=? and DATE(dataHoraColeta)=? and status IN (4,5,7) ORDER BY " + ordem;
        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            valores.setInt(1, idColetador);
            valores.setString(2, data);
            ResultSet result = (ResultSet) valores.executeQuery();
            ArrayList<Coleta> listaStatus = new ArrayList();
            for (int i = 0; result.next(); i++) {
                int idColeta = result.getInt("idColeta");
                int idUsuario = result.getInt("idUsuario");
                int idContato = result.getInt("idContato");
                int idTipo = result.getInt("idTipo");
                int idCliente = result.getInt("idCliente");
                int status = result.getInt("status");
                Timestamp dataHoraSolicitacao = result.getTimestamp("dataHoraSolicitacao");
                Timestamp dataHoraColeta = result.getTimestamp("dataHoraColeta");
                Timestamp dataHoraBaixa = result.getTimestamp("dataHoraBaixa");
                Timestamp dataHoraAguardando = result.getTimestamp("dataHoraAguardando");
                String obs = result.getString("obs");
                int statusEntrega = result.getInt("statusEntrega");
                int tipoSolicitacao = result.getInt("tipoSolicitacao");

                Coleta coleta = new Coleta(idColeta, idCliente, idUsuario, idColetador, idContato, idTipo, status, dataHoraSolicitacao, dataHoraColeta, dataHoraBaixa, obs, statusEntrega, tipoSolicitacao, dataHoraAguardando);
                listaStatus.add(coleta);
            }
            valores.close();
            return listaStatus;
        } catch (SQLException e) {
            ContrErroLog.inserir("consultaTodasFinalizadas", "SQLException", sql, e.toString());
            return null;
        } finally {
            Conexao.desconectar(conn);
        }
    }
}
