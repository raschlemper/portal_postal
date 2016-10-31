/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controle;

import Entidade.Clientes;
import Entidade.HistoricoImport;
import Util.Conexao;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
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

    public static void destivaCliente(String nomeBD, String idCliente) {
        Connection conn = Conexao.conectar(nomeBD);
        String sql = "UPDATE cliente SET ativo = 0 WHERE codigo = " + idCliente + " ;";
        
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

    public static void ativaCliente(String nomeBD, String idCliente) {
        Connection conn = Conexao.conectar(nomeBD);
        String sql = "UPDATE cliente SET ativo = 1 WHERE codigo = " + idCliente + " ;";
        
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
    
    public static void separarDestinatarios(String nomeBD, String idCliente, int separar) {
        Connection conn = Conexao.conectar(nomeBD);
        String sql = "UPDATE cliente SET separar_destinatarios = " + separar + " WHERE codigo = " + idCliente + " ;";
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
    
    public static int inserirCliente(String nome, String nomeFantasia, String endereco, String numero, String complemento, String bairro, String cidade, String uf, String cep, String telefone, String email, String cnpj, double latitude, double longitude, int grupo_fat, String obs, String nomeBD) {
        Connection conn = Conexao.conectar(nomeBD);
        String sql = "INSERT INTO cliente (nome,endereco,telefone,bairro,cidade,uf,cep,email,cnpj,nomeFantasia,complemento,numero,latitude,longitude,idGrupoFaturamento,obscli) \n"
                + " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
        try {
            PreparedStatement valores = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            valores.setString(1, nome);
            valores.setString(2, endereco);
            valores.setString(3, telefone);
            valores.setString(4, bairro);
            valores.setString(5, cidade);
            valores.setString(6, uf);
            valores.setString(7, cep);
            valores.setString(8, email);
            valores.setString(9, cnpj);
            valores.setString(10, nomeFantasia);
            valores.setString(11, complemento);
            valores.setString(12, numero);
            valores.setString(13, latitude + "");
            valores.setString(14, longitude + "");
            valores.setInt(15, grupo_fat);
            valores.setString(16, obs);
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
    
    public static int alterarCliente(String nome, String nomeFantasia, String endereco, String numero, String complemento, String bairro, String cidade, String uf, String cep, String telefone, String email, String cnpj, double latitude, double longitude, int grupo_fat, int idCliente, String obs, String nomeBD) {
        Connection conn = Conexao.conectar(nomeBD);
        String sql = "UPDATE cliente SET nome=?,endereco=?,telefone=?,bairro=?,cidade=?,"
                + "uf=?,cep=?,email=?,cnpj=?,nomeFantasia=?,complemento=?,"
                + "numero=?,latitude=?,longitude=?,idGrupoFaturamento=?,obscli=? WHERE codigo = ? ;";
        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            valores.setString(1, nome);
            valores.setString(2, endereco);
            valores.setString(3, telefone);
            valores.setString(4, bairro);
            valores.setString(5, cidade);
            valores.setString(6, uf);
            valores.setString(7, cep);
            valores.setString(8, email);
            valores.setString(9, cnpj);
            valores.setString(10, nomeFantasia);
            valores.setString(11, complemento);
            valores.setString(12, numero);
            valores.setString(13, latitude + "");
            valores.setString(14, longitude + "");
            valores.setInt(15, grupo_fat);
            valores.setString(16, obs);
            valores.setInt(17, idCliente);
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

    public static Clientes consultaClienteFaturamentoById(int idCliente, String nomeBD) {
        Connection conn = (Connection) Conexao.conectar(nomeBD);
        String sql = "SELECT *, codCliente, SUM(valorServico)/2 AS faturamento FROM cliente"
                    + " LEFT JOIN movimentacao ON codigo = codCliente "
                    + " WHERE MONTH(dataPostagem) IN(MONTH(CURRENT_DATE - INTERVAL 1 MONTH),MONTH(CURRENT_DATE - INTERVAL 2 MONTH)) "
                    + " AND codigo = " + idCliente;
        
        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            ResultSet result = (ResultSet) valores.executeQuery();
            
            if (result.next()) {
                int codigo = result.getInt("codigo");
                String nome = result.getString("nome");
                String endereco = result.getString("endereco");
                String telefone = result.getString("telefone");
                String bairro = result.getString("bairro");
                String cidade = result.getString("cidade");
                String uf = result.getString("uf");
                int cep = result.getInt("cep");
                String email = result.getString("email");
                String cnpj = result.getString("cnpj");
                String nomeFantasia = result.getString("nomeFantasia");
                String complemento = result.getString("complemento");
                String numero = result.getString("numero");
                String url_logo = result.getString("url_logo");
                int temContrato = result.getInt("temContrato");
                String numContrato = result.getString("numContrato");
                int anoContrato = result.getInt("anoContrato");
                String ufContrato = result.getString("ufContrato");
                String nomeContrato = result.getString("nomeContrato");
                String cartaoPostagem = result.getString("cartaoPostagem");
                int usaEtiquetador = result.getInt("usaEtiquetador");
                Double latitude = result.getDouble("latitude");
                Double longitude = result.getDouble("longitude");
                int envio_email = result.getInt("envio_email");
                String login_correio = result.getString("login_correio");
                String senha_correio = result.getString("senha_correio");
                String codAdministrativo = result.getString("codAdministrativo");
                Date dtVigenciaFimContrato = result.getDate("dtVigenciaFimContrato");
                int codDiretoria = result.getInt("codDiretoria");
                int statusCartaoPostagem = result.getInt("statusCartaoPostagem");
                String nomeClienteSara = result.getString("nomeClienteSara");
                int nome_etq = result.getInt("nome_etq");
                int erro_atualizacao = result.getInt("erro_atualizacao");
                Timestamp dataHoraAtualizacao = result.getTimestamp("dataHoraAtualizacao");
                String login_reversa = result.getString("login_reversa");
                String senha_reversa = result.getString("senha_reversa");
                String cartao_reversa = result.getString("cartao_reversa");
                String login_sigep = result.getString("login_sigep");
                String senha_sigep = result.getString("senha_sigep");
                int idGrupoFaturamento = result.getInt("idGrupoFaturamento");
                int ativo = result.getInt("ativo");
                int separar_destinatarios = result.getInt("separar_destinatarios");  
                Float fat_mes = result.getFloat("faturamento");
                
                return new Clientes(codigo, nome, endereco, telefone, bairro, cidade, uf, cep, email, cnpj, nomeFantasia, complemento, cnpj, numero, url_logo, temContrato, numContrato, anoContrato, ufContrato, nomeContrato, usaEtiquetador, latitude, longitude, cartaoPostagem, envio_email, login_correio, senha_correio, codAdministrativo, dtVigenciaFimContrato, codDiretoria, statusCartaoPostagem, nomeClienteSara, nome_etq, erro_atualizacao, dataHoraAtualizacao, login_reversa, senha_reversa, cartao_reversa, login_sigep, senha_sigep, idGrupoFaturamento, ativo, separar_destinatarios, fat_mes);
                
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
        String sql = "SELECT cliente.nome, cliente.codigo, m.siglaServAdicionais FROM movimentacao AS m"
                + " LEFT JOIN cliente ON codigo = codCliente"
                + " WHERE numObjeto = '" + sro + "' ;";
        //System.out.println(sql);
        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            ResultSet result = (ResultSet) valores.executeQuery();
            
            if (result.next()) {
                
                String nome = result.getString("cliente.nome");
                int idCliente = result.getInt("cliente.codigo");
                String adicionais = result.getString("m.siglaServAdicionais");
                
                return new Clientes(nome, adicionais, idCliente);
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
    
    public static ArrayList<Clientes> consultaClientesSemLoginPortalPostal(String nomeBD) {
        
        Connection conn = (Connection) Conexao.conectar(nomeBD);
        String sql = "SELECT * "
                + " FROM cliente "
                + " WHERE nome <> '' "
                + " AND codigo <> -99 "
                + " AND ativo = 1 "
                + " AND codigo NOT IN (SELECT codigo FROM cliente_usuarios WHERE nivel < 99)";
        String sql2 = "SELECT login FROM cliente_usuarios";
        //System.out.println(sql);
        try {
            PreparedStatement valores1 = conn.prepareStatement(sql2);
            ResultSet r = (ResultSet) valores1.executeQuery();
            ArrayList<String> logins = new ArrayList<String>();
            while (r.next()) {                
                logins.add(r.getString("login"));
            }
            
            PreparedStatement valores = conn.prepareStatement(sql);
            ResultSet result = (ResultSet) valores.executeQuery();
            ArrayList<Clientes> lista = new ArrayList<Clientes>();
            while (result.next()) {
                
                int idCliente = result.getInt("codigo");
                String nome = result.getString("nome");
                nome = Util.FormataString.removeAccentsToUpper(nome).replace(".", "").replace(" / ", " ").replace("/", " ").replace(" - ", " ").replace("-", " ");
                String aux[] = nome.split(" ");
                //System.out.println("> "+nome);
                String login = "";
                for (int i = 0; i < aux.length; i++) {                    
                    login += aux[i].trim();
                    if (login.length() >= 4 && !logins.contains(login)) {
                        break;
                    } else if ((i + 1) == aux.length) {
                        login = login + idCliente;
                    }
                }
                Clientes cli = new Clientes(result);
                cli.setLogin_correio(login);
                //System.out.println(">>> "+login+"\n");

                lista.add(cli);
            }
            return lista;
        } catch (Exception e) {
            ContrErroLog.inserir("Portal Postal - contrCliente", "SQLException", sql, e.toString());
            return null;
        } finally {
            Conexao.desconectar(conn);
        }
    }
    public static ArrayList<Clientes> consultaDestinatarioLR(int idCliente, String nomeBD) {
        
        Connection conn = (Connection) Conexao.conectar(nomeBD);
        String sql = "SELECT * "
                + " FROM cliente_remetente "
                + "WHERE idCliente = "+idCliente+" ;";
        //System.out.println(sql);
        try {
          
            PreparedStatement valores = conn.prepareStatement(sql);
            ResultSet result = (ResultSet) valores.executeQuery();
            ArrayList<Clientes> lista = new ArrayList<Clientes>();
            while (result.next()) {
                
                String nome = result.getString("nome");
                nome = Util.FormataString.removeAccentsToUpper(nome).replace(".", "").replace(" / ", " ").replace("/", " ").replace(" - ", " ").replace("-", " ");
                String cep = result.getString("cep").trim();
                if(cep.contains("-")){
                cep=cep.replace("-","");
                }
                int c = Integer.parseInt(cep);
                String endereco = result.getString("endereco");
                String numero = result.getString("numero");
                String bairro = result.getString("bairro");
                String cidade = result.getString("cidade");
                String uf = result.getString("uf");
                String complemento = result.getString("complemento");
                Clientes cli = new Clientes(nome, endereco, bairro, cidade, uf, c, numero, complemento);
                lista.add(cli);
            }
            return lista;
        } catch (Exception e) {
            ContrErroLog.inserir("Portal Postal - contrCliente", "SQLException", sql, e.toString());
            return null;
        } finally {
            Conexao.desconectar(conn);
        }
    }
    
    public static ArrayList<Clientes> getNomeCodigoMetodo(String nomeBD, boolean showInativo) {
        Connection conn = (Connection) Conexao.conectar(nomeBD);
        String all = " WHERE ativo = 1 ";
        if (showInativo) {
            all = "";
        }
        String sql = "SELECT * FROM cliente " + all + " ;";
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
