/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controle;

import Entidade.Clientes;
import Entidade.HistoricoImport;
import Util.Conexao;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 *
 * @author Administrador
 */
public class contrCliente {

    private static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

    public static void criaClienteBalcao(String nomeBD) {
        Connection conn = Conexao.conectar(nomeBD);
        String sql = "INSERT INTO cliente (codigo, nome) VALUES(-99,'BALCÃO');";

        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            valores.executeUpdate();
            valores.close();
        } catch (SQLException e) {
            System.out.println(e);
            ContrErroLog.inserir("HOITO - criaClienteBalcao", "SQLException", sql, e.toString());
        } finally {
            Conexao.desconectar(conn);
        }
    }

    public static int inserirCliente(String nome, String nomeFantasia, String endereco, String numero, String complemento, String bairro, String cidade, String uf, String cep, String telefone, String email, String cnpj, double latitude, double longitude, int grupo_fat, String nomeBD) {
        Connection conn = Conexao.conectar(nomeBD);
        String sql = "INSERT INTO cliente (nome,endereco,telefone,bairro,cidade,uf,cep,email,cnpj,nomeFantasia,complemento,numero,latitude,longitude, idGrupoFaturamento) \n"
                + " VALUES ('" + nome + "','" + endereco + "','" + telefone + "','" + bairro + "','" + cidade + "','" + uf + "','" + cep + "','" + email + "','" + cnpj + "','" + nomeFantasia + "','" + complemento + "','" + numero + "','" + latitude + "','" + longitude + "', "+grupo_fat+");";
        try {
            PreparedStatement valores = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
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
            ContrErroLog.inserir("HOITO - criaClienteBalcao", "SQLException", sql, e.toString());
            return 0;
        } finally {
            Conexao.desconectar(conn);
        }
    }

    public static int alterarCliente(String nome, String nomeFantasia, String endereco, String numero, String complemento, String bairro, String cidade, String uf, String cep, String telefone, String email, String cnpj, double latitude, double longitude, int grupo_fat, int idCliente, String nomeBD) {
        Connection conn = Conexao.conectar(nomeBD);
        String sql = "UPDATE cliente SET nome='" + nome + "',endereco='" + endereco + "',telefone='" + telefone + "',bairro='" + bairro + "',cidade='" + cidade + "',"
                + "uf='" + uf + "',cep='" + cep + "',email='" + email + "',cnpj='" + cnpj + "',nomeFantasia='" + nomeFantasia + "',complemento='" + complemento + "',"
                + "numero='" + numero + "',latitude='" + latitude + "',longitude='" + longitude + "', idGrupoFaturamento="+grupo_fat+" WHERE codigo = " + idCliente+" ;";
        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            valores.executeUpdate();
            valores.close();
            return 1;
        } catch (SQLException e) {
            System.out.println(e);
            ContrErroLog.inserir("HOITO - criaClienteBalcao", "SQLException", sql, e.toString());
            return 0;
        } finally {
            Conexao.desconectar(conn);
        }
    }

    //Importa arquivos tipo .TXT separados com campos com tamanhos determinados
    public static void importarCli(ArrayList<String> listaIDS, ArrayList<String> listaValues, ArrayList<String> listaQuerysServicos, String sqlBase, String sqlDuplicate, String nomeBD, int idUsuario) throws SQLException {
        Connection conn = (Connection) Conexao.conectar(nomeBD);
        try {
            //DELETA OS CLIENTES QUE NAO ESTAVAM NO ARQUIVO
            String sql = "DELETE FROM cliente WHERE codigo NOT IN (";
            for (int i = 0; i < listaIDS.size(); i++) {
                if (i == 0) {
                    sql += "\"" + listaIDS.get(i) + "\"";
                } else {
                    sql += ", \"" + listaIDS.get(i) + "\"";
                }
            }
            sql += ")";
            PreparedStatement valores = conn.prepareStatement(sql);
            int qtdExcluida = valores.executeUpdate();
            valores.close();

            //IMPORTA OS OBJETOS DO ARQUIVO
            for (int i = 0; i < listaValues.size(); i++) {
                PreparedStatement valores1 = conn.prepareStatement(listaValues.get(i));
                valores1.executeUpdate();
                valores1.close();
            }

            //IMPORTA OS SERVICOS DO CONTRATO
            for (int i = 0; i < listaQuerysServicos.size(); i++) {
                PreparedStatement valores1 = conn.prepareStatement(listaQuerysServicos.get(i));
                valores1.executeUpdate();
                valores1.close();
            }

            //INSERE O LOG DA IMPORTACAO
            contrCliente.inserirHistoricoCli(nomeBD, idUsuario, qtdExcluida);
        } catch (SQLException e) {
            ContrErroLog.inserir("HOITO - contrCliente.importarCli", "SQLException", "", e.toString());
            throw e;
        } finally {
            Conexao.desconectar(conn);
        }
    }

    public static boolean inserirHistoricoCli(String nomeBD, int idUsuario, int qtdExcluida) {
        Connection conn = Conexao.conectar(nomeBD);
        String sql = "insert into log_importacao_cli (dataImportacao, qtdCliente, idUsuario, qtdExcluido) values(NOW(),?,?,?);";
        String sqlSelect = "SELECT COUNT(codigo) as qtdCliente from cliente;";
        try {
            PreparedStatement valoresQTD = conn.prepareStatement(sqlSelect);
            ResultSet resultSelect = (ResultSet) valoresQTD.executeQuery();
            int qtdCli = 0;
            if (resultSelect.next()) {
                qtdCli = resultSelect.getInt("qtdCliente");
            }
            valoresQTD.close();

            PreparedStatement valores = conn.prepareStatement(sql);
            valores.setInt(1, qtdCli);
            valores.setInt(2, idUsuario);
            valores.setInt(3, qtdExcluida);
            valores.executeUpdate();
            valores.close();
            return true;
        } catch (SQLException e) {
            System.out.println(e);
            ContrErroLog.inserir("HOITO - contrMovimentacao", "SQLException", sql, e.toString());
            return false;
        } finally {
            Conexao.desconectar(conn);
        }
    }

    public static ArrayList consultaHistoricoImportCli(String nomeBD) {
        Connection conn = (Connection) Conexao.conectar(nomeBD);
        String sql = "SELECT * FROM log_importacao_cli ORDER BY dataImportacao DESC LIMIT 50;";
        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            ResultSet result = (ResultSet) valores.executeQuery();
            ArrayList listaHist = new ArrayList();
            for (int i = 0; result.next(); i++) {
                Timestamp dataimportacao = result.getTimestamp("dataImportacao");
                int qtClientes = result.getInt("qtdCliente");
                int qtdExcluido = result.getInt("qtdExcluido");
                int idUsuario = result.getInt("idUsuario");
                HistoricoImport hst = new HistoricoImport(dataimportacao, qtClientes, idUsuario, qtdExcluido);
                listaHist.add(hst);
            }
            valores.close();
            return listaHist;
        } catch (SQLException e) {
            ContrErroLog.inserir("HOITO - contrMovimentacao", "SQLException", sql, e.toString());
            return null;
        } finally {
            Conexao.desconectar(conn);
        }
    }

    /**
     * ************************************************************************
     */
    /**
     * ************************************************************************
     */
    /**
     * ************************************************************************
     */
    public static boolean editar(int idCliente, String nome, int cep, String endereco, String numero, String complemento, String bairro, String cidade, String uf, String nomeBD) {
        Connection conn = Conexao.conectar(nomeBD);
        String sql = "UPDATE cliente SET nomeFantasia = ?, cep = ?, endereco = ?, numero = ?, complemento = ?, bairro = ?, cidade = ?, uf = ? WHERE codigo = ? ";
        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            valores.setString(1, nome);
            valores.setInt(2, cep);
            valores.setString(3, endereco);
            valores.setString(4, numero);
            valores.setString(5, complemento);
            valores.setString(6, bairro);
            valores.setString(7, cidade);
            valores.setString(8, uf);
            valores.setInt(9, idCliente);
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

    public static boolean editarNomeEtq(int idCliente, int nome_etq, String nomeBD) {
        Connection conn = Conexao.conectar(nomeBD);
        String sql = "UPDATE cliente SET nome_etq = ? WHERE codigo = ? ";
        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            valores.setInt(1, nome_etq);
            valores.setInt(2, idCliente);
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

    public static boolean alterarLogo(String url_logo, int idCliente, String nomeBD) {
        Connection conn = Conexao.conectar(nomeBD);
        String sql = "UPDATE cliente SET url_logo = ? WHERE codigo = ? ";
        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            valores.setString(1, url_logo);
            valores.setInt(2, idCliente);
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

    public static boolean alterarUsaEtiquetador(int usaEtiquetador, int idCliente, String nomeBD) {
        Connection conn = Conexao.conectar(nomeBD);
        String sql = "UPDATE cliente SET usaEtiquetador = ? WHERE codigo = ? ";
        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            valores.setInt(1, usaEtiquetador);
            valores.setInt(2, idCliente);
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

    public static boolean alterarLatitudeLongitude(double latitude, double longitude, int idCliente, String nomeBD) {
        Connection conn = Conexao.conectar(nomeBD);
        String sql = "UPDATE cliente SET latitude = ?, longitude = ? WHERE codigo = ? ";
        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            valores.setDouble(1, latitude);
            valores.setDouble(2, longitude);
            valores.setInt(3, idCliente);
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

    public static boolean alterarLoginPrecoPrazo(String login_correio, String senha_correio, int idCliente, String nomeBD) {
        Connection conn = Conexao.conectar(nomeBD);
        String sql = "UPDATE cliente SET login_correio = ?, senha_correio = ? WHERE codigo = ? ";
        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            valores.setString(1, login_correio);
            valores.setString(2, senha_correio);
            valores.setInt(3, idCliente);
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

    public static boolean alterarLoginReversa(String login_correio, String senha_correio, String cartao_correio, int idCliente, String nomeBD) {
        Connection conn = Conexao.conectar(nomeBD);
        String sql = "UPDATE cliente SET login_reversa = ?, senha_reversa = ?, cartao_reversa = ? WHERE codigo = ? ";
        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            valores.setString(1, login_correio);
            valores.setString(2, senha_correio);
            valores.setString(3, cartao_correio);
            valores.setInt(4, idCliente);
            valores.executeUpdate();
            valores.close();
            return true;
        } catch (SQLException e) {
            System.out.println(e);
            ContrErroLog.inserir("HOITO - contrContato", "SQLException", sql, e.toString());
            return false;
        } finally {
            Conexao.desconectar(conn);
        }
    }

    public static String consultaNomeById(int idCliente, String nomeBD) {
        Connection conn = (Connection) Conexao.conectar(nomeBD);
        String sql = "SELECT nome FROM cliente WHERE codigo = " + idCliente;
        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            ResultSet result = (ResultSet) valores.executeQuery();
            if (result.next()) {
                return result.getString("nome");
            } else {
                return "";
            }
        } catch (Exception e) {
            ContrErroLog.inserir("HOITO - contrCliente", "SQLException", sql, e.toString());
            return null;
        } finally {
            Conexao.desconectar(conn);
        }
    }

    public static String[] consultaLoginPrecosPrazosCorreios(int idCliente, String nomeBD) {
        Connection conn = (Connection) Conexao.conectar(nomeBD);
        String sql = "SELECT login_correio, senha_correio FROM cliente WHERE codigo = " + idCliente;
        try {
            String[] ret = new String[2];
            PreparedStatement valores = conn.prepareStatement(sql);
            ResultSet result = (ResultSet) valores.executeQuery();
            if (result.next()) {
                ret[0] = result.getString("login_correio");
                ret[1] = result.getString("senha_correio");
                return ret;
            } else {
                return null;
            }
        } catch (Exception e) {
            ContrErroLog.inserir("HOITO - contrCliente", "SQLException", sql, e.toString());
            return null;
        } finally {
            Conexao.desconectar(conn);
        }
    }

    public static int numeroClientes(String nomeBD) {
        Connection conn = (Connection) Conexao.conectar(nomeBD);
        String sql = "SELECT COUNT(*) AS contador FROM cliente";
        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            ResultSet result = (ResultSet) valores.executeQuery();
            if (result.next()) {
                return result.getInt("contador");
            } else {
                return 0;
            }
        } catch (Exception e) {
            ContrErroLog.inserir("HOITO - contrCliente", "SQLException", sql, e.toString());
            return 0;
        } finally {
            Conexao.desconectar(conn);
        }
    }

    /*public static ArrayList pesquisa(String codigo, String nome, String telefone, String cnpj, String bairro, String cidade, String cep, String nomeBD) {
        Connection conn = (Connection) Conexao.conectar(nomeBD);
        String sql = "SELECT * FROM cliente"
                + " WHERE codigo LIKE '%" + codigo + "%'"
                + " AND nome LIKE '%" + nome + "%'"
                + " AND telefone LIKE '%" + telefone + "%'"
                + " AND cnpj LIKE '%" + cnpj + "%'"
                + " AND bairro LIKE '%" + bairro + "%'"
                + " AND cidade LIKE '%" + cidade + "%'"
                + " AND cep LIKE '%" + cep + "%'"
                + " ORDER BY nome";
        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            ResultSet result = (ResultSet) valores.executeQuery();
            ArrayList listaClientes = new ArrayList();
            while (result.next()) {                
                Clientes cli = new Clientes(result);
                listaClientes.add(cli);
            }
            valores.close();
            return listaClientes;
        } catch (SQLException e) {
            ContrErroLog.inserir("HOITO - contrCliente", "SQLException", sql, e.toString());
            return null;
        } finally {
            Conexao.desconectar(conn);
        }
    }*/
    public static Clientes consultaClienteById(int idCliente, String nomeBD) {
        Connection conn = (Connection) Conexao.conectar(nomeBD);
        String sql = "SELECT * FROM cliente WHERE codigo = " + idCliente;
        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            ResultSet result = (ResultSet) valores.executeQuery();

            if (result.next()) {
                return new Clientes(result);
            } else {
                return null;
            }
        } catch (Exception e) {
            ContrErroLog.inserir("Portal Postal - contrCliente", "SQLException", sql, e.toString());
            return null;
        } finally {
            Conexao.desconectar(conn);
        }
    }

    public static Clientes consultaClienteBySRO(String sro, String nomeBD) {

        Connection conn = (Connection) Conexao.conectar(nomeBD);
        String sql = "SELECT cliente.* FROM movimentacao LEFT JOIN cliente ON codigo = codCliente"
                + " WHERE numObjeto = '" + sro + "' ;";
        System.out.println(sql);
        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            ResultSet result = (ResultSet) valores.executeQuery();

            if (result.next()) {

                String nome = result.getString("nome");
                int idCliente = result.getInt("codigo");

                return new Clientes(idCliente, nome);
            } else {
                return null;
            }
        } catch (Exception e) {
            ContrErroLog.inserir("Portal Postal - contrCliente", "SQLException", sql, e.toString());
            return null;
        } finally {
            Conexao.desconectar(conn);
        }
    }

    public static ArrayList<Clientes> getNomeCodigoMetodo(String nomeBD) {
        Connection conn = (Connection) Conexao.conectar(nomeBD);
        String sql = "SELECT * FROM cliente ORDER BY nome";
        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            ResultSet result = (ResultSet) valores.executeQuery();

            ArrayList<Clientes> listaNomes = new ArrayList<Clientes>();

            for (int i = 0; result.next(); i++) {
                listaNomes.add(new Clientes(result));
            }
            valores.close();

            return listaNomes;
        } catch (Exception e) {
            ContrErroLog.inserir("Portal Postal - contrCliente", "SQLException", sql, e.toString());
            return null;
        } finally {
            Conexao.desconectar(conn);
        }
    }

    public static ArrayList<Clientes> getClientesComErroAtualizacao(String nomeBD) {
        Connection conn = (Connection) Conexao.conectar(nomeBD);
        String sql = "SELECT * FROM cliente WHERE erro_atualizacao = 1 ORDER BY dataHoraAtualizacao DESC";
        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            ResultSet result = (ResultSet) valores.executeQuery();

            ArrayList<Clientes> listaNomes = new ArrayList<Clientes>();

            for (int i = 0; result.next(); i++) {
                listaNomes.add(new Clientes(result));
            }
            valores.close();

            return listaNomes;
        } catch (Exception e) {
            ContrErroLog.inserir("Portal Postal - contrCliente", "SQLException", sql, e.toString());
            return null;
        } finally {
            Conexao.desconectar(conn);
        }
    }

    public static ArrayList<Clientes> getClientesComContratoVencendo(int dias, String nomeBD) {
        Connection conn = (Connection) Conexao.conectar(nomeBD);
        String sql = "SELECT * FROM cliente WHERE temContrato = 1 AND DATEDIFF(dtVigenciaFimContrato, DATE(NOW())) < " + dias + " ORDER BY dataHoraAtualizacao DESC";
        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            ResultSet result = (ResultSet) valores.executeQuery();

            ArrayList<Clientes> listaNomes = new ArrayList<Clientes>();

            for (int i = 0; result.next(); i++) {
                listaNomes.add(new Clientes(result));
            }
            valores.close();

            return listaNomes;
        } catch (Exception e) {
            ContrErroLog.inserir("Portal Postal - contrCliente", "SQLException", sql, e.toString());
            return null;
        } finally {
            Conexao.desconectar(conn);
        }
    }

    public static ArrayList<Clientes> consultaClienteByRazaoSocial(String nome, String nomeBD) {
        Connection conn = (Connection) Conexao.conectar(nomeBD);
        String sql = "SELECT * FROM cliente WHERE locate('" + nome + "',nome) > 0 ORDER BY locate('" + nome + "',nome) limit 20";
        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            ResultSet result = (ResultSet) valores.executeQuery();

            ArrayList<Clientes> listaClientes = new ArrayList<Clientes>();

            while (result.next()) {
                Clientes cli = new Clientes(result);
                listaClientes.add(cli);
            }

            valores.close();

            return listaClientes;
        } catch (SQLException e) {
            ContrErroLog.inserir("Portal Postal - contrCliente", "SQLException", sql, e.toString());
            return null;
        } finally {
            Conexao.desconectar(conn);
        }
    }

    public static ArrayList<Integer> consultaClienteComContrato(String nomeBD) {
        Connection conn = (Connection) Conexao.conectar(nomeBD);
        String sql = "SELECT codigo FROM cliente WHERE temContrato = 1";
        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            ResultSet result = (ResultSet) valores.executeQuery();

            ArrayList<Integer> listaClientes = new ArrayList<Integer>();

            while (result.next()) {
                listaClientes.add(result.getInt("codigo"));
            }

            valores.close();

            return listaClientes;
        } catch (SQLException e) {
            System.out.println(e);
            ContrErroLog.inserir("Portal Postal - contrCliente", "SQLException", sql, e.toString());
            return null;
        } finally {
            Conexao.desconectar(conn);
        }
    }

    public static ArrayList<Clientes> consultaClienteUltColeta(String nomeBD, int limit) {
        Connection conn = (Connection) Conexao.conectar(nomeBD);
        String sql = "SELECT DISTINCT t.codigo, t.nome, c.dataHoraColeta"
                + " FROM cliente AS t"
                + " LEFT JOIN coleta AS c ON c.idCliente = t.codigo"
                + " GROUP BY t.codigo"
                + " ORDER BY c.dataHoraColeta DESC LIMIT " + limit + ";";
        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            ResultSet result = (ResultSet) valores.executeQuery();

            ArrayList<Clientes> lista = new ArrayList<Clientes>();

            for (int i = 0; result.next(); i++) {
                String nome = result.getString("t.nome").trim();
                int codigo = result.getInt("t.codigo");
                Timestamp data = result.getTimestamp("c.dataHoraColeta");
                String dataUlt = "Sem Coleta Registrada!";
                if (data != null) {
                    dataUlt = sdf.format(data);
                }

                lista.add(new Clientes(nome, dataUlt, codigo));
            }
            valores.close();

            return lista;
        } catch (Exception e) {
            ContrErroLog.inserir("HOITO - contrCliente", "SQLException", sql, e.toString());
            return null;
        } finally {
            Conexao.desconectar(conn);
        }
    }

    /**
     * *********************************************************
     */
    /**
     * ************ consultas do esqueceu senha ****************
     */
    /**
     * *********************************************************
     */
    public static String consultaEmailByCnpj(String cnpj, String nomeBD) {
        Connection conn = (Connection) Conexao.conectar(nomeBD);
        String sql = "SELECT email FROM cliente WHERE cnpj LIKE '" + cnpj + "'";
        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            ResultSet result = (ResultSet) valores.executeQuery();

            if (result.next()) {
                return result.getString("email");
            } else {
                return "";
            }
        } catch (Exception e) {
            ContrErroLog.inserir("HOITO - contrCliente", "SQLException", sql, e.toString());
            return null;
        } finally {
            Conexao.desconectar(conn);
        }
    }

    public static String consultaEmpresaByCnpj(String cnpj, String nomeBD) {
        Connection conn = (Connection) Conexao.conectar(nomeBD);
        String sql = "SELECT nomeFantasia FROM cliente WHERE cnpj LIKE '" + cnpj + "'";
        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            ResultSet result = (ResultSet) valores.executeQuery();

            if (result.next()) {
                return result.getString("nomeFantasia");
            } else {
                return "";
            }
        } catch (Exception e) {
            ContrErroLog.inserir("HOITO - contrCliente", "SQLException", sql, e.toString());
            return null;
        } finally {
            Conexao.desconectar(conn);
        }
    }

    public static int consultaIdClienteByCnpj(String cnpj, String nomeBD) {
        Connection conn = (Connection) Conexao.conectar(nomeBD);
        String sql = "SELECT codigo FROM cliente WHERE cnpj LIKE '" + cnpj + "';";
        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            ResultSet result = (ResultSet) valores.executeQuery();

            if (result.next()) {
                return result.getInt("codigo");
            } else {
                return 0;
            }
        } catch (Exception e) {
            ContrErroLog.inserir("HOITO - contrCliente", "SQLException", sql, e.toString());
            return 0;
        } finally {
            Conexao.desconectar(conn);
        }
    }
}
