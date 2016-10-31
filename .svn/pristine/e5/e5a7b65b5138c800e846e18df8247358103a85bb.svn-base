/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Emporium.Controle;

import Controle.ContrErroLog;
import Entidade.PreVenda;
import Util.Conexao;
import Util.FormataString;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

/**
 *
 * @author RICARDINHO
 */
public class ContrPreVenda {

    public static int inserir(int idCliente, String numObjeto, int idDestinatario, int idRemetente, int codECT, String contrato, String departamento, String aosCuidados, String obs, String conteudo, int peso, int altura, int largura, int comprimento, float vd, int ar, int mp, String siglaAmarracao, String nomeServico, String notaFiscal, float valor_cobrar, String tipo, int idDepartamento, String cartaoPostagem, int idUser, int registro, String nomePreVenda, String email_destinatario, String tipo_etiqueta, String siglaPais, String destino_postagem, String nomeBD, int posta_restante, int registro_modico, String setor) {
        Connection conn = Conexao.conectar(nomeBD);
        String sql = "INSERT INTO pre_venda (id, numObjeto, idCliente, idDestinatario, idRemetente, codECT, contrato, departamento, aos_cuidados, observacoes, conteudo, peso, altura, largura, comprimento, valor_declarado, aviso_recebimento, mao_propria, siglaAmarracao, nomeServico, notaFiscal, valor_cobrar, tipoEncomenda, idDepartamento, dataPreVenda, cartaoPostagem, userPreVenda, registro, nomePreVenda, email_destinatario, tipo_etiqueta, sigla_pais, destino_postagem, posta_restante, registro_modico, setor) values(0,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,NOW(),?,?,?,?,?,?,?,?,?,?,?)";
        if (numObjeto.equals("avista")) {
            sql = "INSERT INTO pre_venda (numObjeto, idCliente, idDestinatario, idRemetente, codECT, contrato, departamento, aos_cuidados, observacoes, conteudo, peso, altura, largura, comprimento, valor_declarado, aviso_recebimento, mao_propria, siglaAmarracao, nomeServico, notaFiscal, valor_cobrar, tipoEncomenda, idDepartamento, dataPreVenda, cartaoPostagem, userPreVenda, registro, nomePreVenda, email_destinatario, tipo_etiqueta, sigla_pais, destino_postagem, posta_restante, registro_modico, setor) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,NOW(),?,?,?,?,?,?,?,?,?,?,?)";
        }
        Integer idPreVenda = null;
        try {
            PreparedStatement valores = conn.prepareStatement(sql,PreparedStatement.RETURN_GENERATED_KEYS);
            valores.setString(1, numObjeto);
            valores.setInt(2, idCliente);
            valores.setInt(3, idDestinatario);
            valores.setInt(4, idRemetente);
            valores.setInt(5, codECT);
            valores.setString(6, contrato);
            valores.setString(7, departamento);
            valores.setString(8, FormataString.removeSpecialChars(aosCuidados));
            valores.setString(9, FormataString.removeSpecialChars(obs));
            valores.setString(10, FormataString.removeSpecialChars(conteudo));
            valores.setInt(11, peso);
            valores.setInt(12, altura);
            valores.setInt(13, largura);
            valores.setInt(14, comprimento);
            valores.setFloat(15, vd);
            valores.setInt(16, ar);
            valores.setInt(17, mp);
            valores.setString(18, siglaAmarracao);
            valores.setString(19, nomeServico);
            valores.setString(20, notaFiscal);
            valores.setFloat(21, valor_cobrar);
            valores.setString(22, tipo);
            valores.setInt(23, idDepartamento);
            valores.setString(24, cartaoPostagem);
            valores.setInt(25, idUser);
            valores.setInt(26, registro);
            valores.setString(27, nomePreVenda);
            valores.setString(28, email_destinatario);
            valores.setString(29, tipo_etiqueta);
            valores.setString(30, siglaPais);
            valores.setString(31, destino_postagem);
            valores.setInt(32, posta_restante);
            valores.setInt(33, registro_modico);
            valores.setString(34, setor);
            valores.executeUpdate();
            ResultSet result = valores.getGeneratedKeys();
            if(result.next()){
                idPreVenda = result.getInt(1);
            }
            valores.close();
        } catch (SQLException e) {
            System.out.println("ERRO " + e);
            ContrErroLog.inserir("HOITO - contrContato", "SQLException", sql, e.toString());

        } finally {
            Conexao.desconectar(conn);
            return idPreVenda;
        }
    }
    public static int inserir_marcar_impresso(int idCliente, String numObjeto, int idDestinatario, int idRemetente, int codECT, String contrato, String departamento, String aosCuidados, String obs, String conteudo, int peso, int altura, int largura, int comprimento, float vd, int ar, int mp, String siglaAmarracao, String nomeServico, String notaFiscal, float valor_cobrar, String tipo, int idDepartamento, String cartaoPostagem, int idUser, int registro, String nomePreVenda, String email_destinatario, String tipo_etiqueta, String siglaPais, String destino_postagem, String nomeBD, int posta_restante, int registro_modico, String setor) {
        Connection conn = Conexao.conectar(nomeBD);
        String sql = "INSERT INTO pre_venda (id, numObjeto, idCliente, idDestinatario, idRemetente, codECT, contrato, departamento, aos_cuidados, observacoes, conteudo, peso, altura, largura, comprimento, valor_declarado, aviso_recebimento, mao_propria, siglaAmarracao, nomeServico, notaFiscal, valor_cobrar, tipoEncomenda, idDepartamento, dataPreVenda, cartaoPostagem, userPreVenda, registro, nomePreVenda, email_destinatario, tipo_etiqueta, sigla_pais, destino_postagem, posta_restante, registro_modico, setor, impresso, userImpressao, dataImpressao) values(0,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,NOW(),?,?,?,?,?,?,?,?,?,?,?,1,0,NOW())";
        if (numObjeto.equals("avista")) {
            sql = "INSERT INTO pre_venda (numObjeto, idCliente, idDestinatario, idRemetente, codECT, contrato, departamento, aos_cuidados, observacoes, conteudo, peso, altura, largura, comprimento, valor_declarado, aviso_recebimento, mao_propria, siglaAmarracao, nomeServico, notaFiscal, valor_cobrar, tipoEncomenda, idDepartamento, dataPreVenda, cartaoPostagem, userPreVenda, registro, nomePreVenda, email_destinatario, tipo_etiqueta, sigla_pais, destino_postagem, posta_restante, registro_modico, setor, impresso, userImpressao, dataImpressao) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,NOW(),?,?,?,?,?,?,?,?,?,?,?,1,0,NOW())";
        }
        Integer idPreVenda = null;
        try {
            PreparedStatement valores = conn.prepareStatement(sql,PreparedStatement.RETURN_GENERATED_KEYS);
            valores.setString(1, numObjeto);
            valores.setInt(2, idCliente);
            valores.setInt(3, idDestinatario);
            valores.setInt(4, idRemetente);
            valores.setInt(5, codECT);
            valores.setString(6, contrato);
            valores.setString(7, departamento);
            valores.setString(8, FormataString.removeSpecialChars(aosCuidados));
            valores.setString(9, FormataString.removeSpecialChars(obs));
            valores.setString(10, FormataString.removeSpecialChars(conteudo));
            valores.setInt(11, peso);
            valores.setInt(12, altura);
            valores.setInt(13, largura);
            valores.setInt(14, comprimento);
            valores.setFloat(15, vd);
            valores.setInt(16, ar);
            valores.setInt(17, mp);
            valores.setString(18, siglaAmarracao);
            valores.setString(19, nomeServico);
            valores.setString(20, notaFiscal);
            valores.setFloat(21, valor_cobrar);
            valores.setString(22, tipo);
            valores.setInt(23, idDepartamento);
            valores.setString(24, cartaoPostagem);
            valores.setInt(25, idUser);
            valores.setInt(26, registro);
            valores.setString(27, nomePreVenda);
            valores.setString(28, email_destinatario);
            valores.setString(29, tipo_etiqueta);
            valores.setString(30, siglaPais);
            valores.setString(31, destino_postagem);
            valores.setInt(32, posta_restante);
            valores.setInt(33, registro_modico);
            valores.setString(34, setor);
            valores.executeUpdate();
            ResultSet result = valores.getGeneratedKeys();
            if(result.next()){
                idPreVenda = result.getInt(1);
            }
            valores.close();
        } catch (SQLException e) {
            e.printStackTrace();
            ContrErroLog.inserir("HOITO - contrContato", "SQLException", sql, e.toString());

        } finally {
            Conexao.desconectar(conn);
            return idPreVenda;
        }
    }

    public static boolean alterar(int idCliente, String numObjeto, int idDestinatario, int idRemetente, int codECT, String contrato, String departamento, String aosCuidados, String obs, String conteudo, int peso, int altura, int largura, int comprimento, float vd, int ar, int mp, String siglaAmarracao, String nomeServico, String notaFiscal, float valor_cobrar, String tipo, int idDepartamento, String cartaoPostagem, int idUser, int id, int registro, String nomePreVenda, String tipo_etiqueta, String email, int rm, String nomeBD) {
        Connection conn = Conexao.conectar(nomeBD);
        String sql = "UPDATE pre_venda SET numObjeto=?, idCliente=?, idDestinatario=?, idRemetente=?, codECT=?, contrato=?, departamento=?, aos_cuidados=?, observacoes=?, conteudo=?, peso=?, altura=?, largura=?, comprimento=?, valor_declarado=?, aviso_recebimento=?, mao_propria=?, siglaAmarracao=?, nomeServico=?, notaFiscal=?, valor_cobrar=?, tipoEncomenda=?, idDepartamento=?, dataPreVenda=NOW(), cartaoPostagem=?, userPreVenda=?, registro=?, nomePreVenda=?, tipo_etiqueta=?, email_destinatario=?, registro_modico = ? WHERE id = ?;";

        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            valores.setString(1, numObjeto);
            valores.setInt(2, idCliente);
            valores.setInt(3, idDestinatario);
            valores.setInt(4, idRemetente);
            valores.setInt(5, codECT);
            valores.setString(6, contrato);
            valores.setString(7, departamento);
            valores.setString(8, aosCuidados);
            valores.setString(9, obs);
            valores.setString(10, conteudo);
            valores.setInt(11, peso);
            valores.setInt(12, altura);
            valores.setInt(13, largura);
            valores.setInt(14, comprimento);
            valores.setFloat(15, vd);
            valores.setInt(16, ar);
            valores.setInt(17, mp);
            valores.setString(18, siglaAmarracao);
            valores.setString(19, nomeServico);
            valores.setString(20, notaFiscal);
            valores.setFloat(21, valor_cobrar);
            valores.setString(22, tipo);
            valores.setInt(23, idDepartamento);
            valores.setString(24, cartaoPostagem);
            valores.setInt(25, idUser);
            valores.setInt(26, registro);
            valores.setString(27, nomePreVenda);
            valores.setString(28, tipo_etiqueta);
            valores.setString(29, email);
            valores.setInt(30, rm);
            valores.setInt(31, id);
            valores.executeUpdate();
            valores.close();
            return true;
        } catch (SQLException e) {
            System.out.println("ERRO " + e);
            ContrErroLog.inserir("HOITO - contrContato", "SQLException", sql, e.toString());
            return false;
        } finally {
            Conexao.desconectar(conn);
        }
    }

    public static int excluir(int idVenda, String nomeBD) {
        Connection conn = Conexao.conectar(nomeBD);
        try {
            String sql = "DELETE FROM pre_venda WHERE id = ?;";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, idVenda);
            int i = pstmt.executeUpdate();
            return i;
        } catch (SQLException e) {
            return -1;
        } finally {
            Conexao.desconectar(conn);
        }
    }

    public static int inutilizar(int idVenda, String nomeBD) {
        Connection conn = Conexao.conectar(nomeBD);
        try {
            String sql = "UPDATE pre_venda SET inutilizada = 1 WHERE id = ? ;";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, idVenda);
            int i = pstmt.executeUpdate();
            return i;
        } catch (SQLException e) {
            return -1;
        } finally {
            Conexao.desconectar(conn);
        }
    }

    public static int setarImpresso(String nomeBD, String param, int idUser, String nomeUser) {
        Connection conn = Conexao.conectar(nomeBD);
        try {
            String sql = "UPDATE pre_venda SET impresso = 1, isSync = 0, dataImpressao = NOW(), userImpressao = " + idUser + ", nomeImpressao = '" + nomeUser + "' WHERE impresso = 0 AND id IN (" + param + ");";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            int i = pstmt.executeUpdate();
            return i;
        } catch (SQLException e) {
            return -1;
        } finally {
            Conexao.desconectar(conn);
        }
    }

    public static int setarImpressoAr(String nomeBD, String param) {
        Connection conn = Conexao.conectar(nomeBD);
        try {
            String sql = "UPDATE pre_venda SET impresso_ar = 1 WHERE impresso_ar = 0 AND id IN (" + param + ");";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            int i = pstmt.executeUpdate();
            return i;
        } catch (SQLException e) {
            return -1;
        } finally {
            Conexao.desconectar(conn);
        }
    }

    public static boolean verificaImpresso(int id, String nomeBD) {
        Connection conn = Conexao.conectar(nomeBD);
        try {
            String sql = "SELECT id FROM pre_venda WHERE impresso = 1 AND id = " + id + ";";
            PreparedStatement valores = conn.prepareStatement(sql);
            ResultSet result = (ResultSet) valores.executeQuery();
            if (result.next()) {
                return true;
            }
            return false;
        } catch (SQLException e) {
            return false;
        } finally {
            Conexao.desconectar(conn);
        }
    }

    public static ArrayList<PreVenda> consultaVendas(int idCliente, int impresso, int consolidado, int nivel, int idUser, String nomeBD) {
        Connection conn = (Connection) Conexao.conectar(nomeBD);
        String nv = "";
        if (nivel == 3) {
            nv = " AND userPreVenda = " + idUser;
        }
        String sql = "SELECT * FROM pre_venda AS p"
                + " LEFT JOIN pre_venda_destinatario AS d ON p.idDestinatario = d.idDestinatario"
                + " WHERE p.idRemetente = 0 AND p.idCliente = " + idCliente + " AND impresso = " + impresso + " AND consolidado = " + consolidado + " " + nv;
        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            ResultSet result = (ResultSet) valores.executeQuery();
            ArrayList<PreVenda> lista = new ArrayList<PreVenda>();
            for (int i = 0; result.next(); i++) {
                int id = result.getInt("p.id");
                String numObjeto = result.getString("p.numObjeto");
                int idRemetente = result.getInt("p.idRemetente");
                int idDestinatario = result.getInt("p.idDestinatario");
                int codECT = result.getInt("p.codECT");
                String nomeServico = result.getString("p.nomeServico");
                String contrato = result.getString("p.contrato");
                String departamento = result.getString("p.departamento");
                String aos_cuidados = result.getString("p.aos_cuidados");
                String observacoes = result.getString("p.observacoes");
                String conteudo = result.getString("p.conteudo");
                String notaFiscal = result.getString("p.notaFiscal");
                String siglaAmarracao = result.getString("p.siglaAmarracao");
                int peso = result.getInt("p.peso");
                int altura = result.getInt("p.altura");
                int largura = result.getInt("p.largura");
                int comprimento = result.getInt("p.comprimento");
                float valor_declarado = result.getFloat("p.valor_declarado");
                float valor_cobrar = result.getFloat("p.valor_cobrar");
                int mao_propria = result.getInt("p.mao_propria");
                int aviso_recebimento = result.getInt("p.aviso_recebimento");

                int posta_restante = result.getInt("p.posta_restante");
                int registro_modico = result.getInt("p.registro_modico");

                String nomeDes = result.getString("d.nome");
                String empresaDes = result.getString("d.empresa");
                String cepDes = result.getString("d.cep");
                String enderecoDes = result.getString("d.endereco");
                String bairroDes = result.getString("d.bairro");
                String cidadeDes = result.getString("d.cidade");
                String ufDes = result.getString("d.uf");
                String numeroDes = result.getString("d.numero");
                String cpfDes = result.getString("d.cpf_cnpj");
                String complementoDes = result.getString("d.complemento");
                String celularDes = result.getString("d.celular");

                int idOs = result.getInt("p.idOs");
                String responsavel = result.getString("p.responsavel");
                String emailDes = result.getString("d.email");

                int userPreVenda = result.getInt("p.userPreVenda");
                int userImpresso = result.getInt("p.userImpressao");
                int userConsolidado = result.getInt("p.userConsolidado");
                int userVenda = result.getInt("p.userVenda");
                String nomePreVenda = result.getString("p.nomePreVenda");
                String nomeImpresso = result.getString("p.nomeImpressao");
                String nomeConsolidado = result.getString("p.nomeConsolidado");
                String nomeVenda = result.getString("p.nomeVenda");
                Timestamp dataPreVenda = result.getTimestamp("p.dataPreVenda");
                Timestamp dataImpresso = result.getTimestamp("p.dataImpressao");
                Timestamp dataConsolidado = result.getTimestamp("p.dataConsolidado");
                Timestamp dataVenda = result.getTimestamp("p.dataVenda");
                String cartaoPostagem = result.getString("p.cartaoPostagem");
                String metodo_insercao = result.getString("p.metodo_insercao");
                int idDepartamento = result.getInt("p.idDepartamento");
                String arquivo_ar = result.getString("p.arquivo_ar");

                PreVenda pv = new PreVenda(id, numObjeto, idCliente, idRemetente, idDestinatario, codECT, nomeServico, contrato, departamento, aos_cuidados, observacoes, conteudo, siglaAmarracao, peso, altura, largura, comprimento, valor_declarado, mao_propria, aviso_recebimento, nomeDes, empresaDes, cpfDes, enderecoDes, numeroDes, complementoDes, cidadeDes, ufDes, cepDes, bairroDes, notaFiscal, valor_cobrar, userPreVenda, dataPreVenda, dataImpresso, dataConsolidado, dataVenda, userImpresso, userConsolidado, userVenda, nomePreVenda, nomeImpresso, nomeConsolidado, nomeVenda, responsavel, emailDes, idOs, celularDes, cartaoPostagem, metodo_insercao, posta_restante, registro_modico, idDepartamento, arquivo_ar);
                lista.add(pv);
            }
            valores.close();
            return lista;
        } catch (SQLException e) {
            System.out.println(e);
            //ContrErroLog.inserir("HOITO - contrMovimentacao", "SQLException", sql, e.toString());
            return null;
        } finally {
            Conexao.desconectar(conn);
        }
    }

    public static ArrayList<PreVenda> consultaVendasReimpressao(int idCliente, int impresso, int consolidado, int nivel, int idUser, boolean hoje, String dataIni, String dataFim, String nomeBD) {
        Connection conn = (Connection) Conexao.conectar(nomeBD);

        String nv = "";
        if (nivel == 3) {
            nv = " AND userPreVenda = " + idUser;
        }

        String hj = " AND DATE(dataImpressao) < DATE(NOW()) ";
        if (dataIni != null && dataFim != null && !dataIni.equals("NULL") && !dataFim.equals("NULL")) {
            hj = " AND DATE(dataImpressao) BETWEEN '" + dataIni + "' AND '" + dataFim + "'";
        } else if (hoje) {
            hj = " AND DATE(dataImpressao) = DATE(NOW()) ";
        }

        String cons = " AND consolidado = " + consolidado;
        if (consolidado == -1) {
            cons = "";
        }

        String sql = "SELECT * FROM pre_venda AS p"
                + " LEFT JOIN pre_venda_destinatario AS d ON p.idDestinatario = d.idDestinatario"
                + " WHERE p.idCliente = " + idCliente + " AND inutilizada = 0 AND impresso = " + impresso + " " + cons + " " + hj + " " + nv;

        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            ResultSet result = (ResultSet) valores.executeQuery();
            ArrayList<PreVenda> lista = new ArrayList<PreVenda>();
            for (int i = 0; result.next(); i++) {
                int id = result.getInt("p.id");
                String numObjeto = result.getString("p.numObjeto");
                int idRemetente = result.getInt("p.idRemetente");
                int idDestinatario = result.getInt("p.idDestinatario");
                int codECT = result.getInt("p.codECT");
                String nomeServico = result.getString("p.nomeServico");
                String contrato = result.getString("p.contrato");
                String departamento = result.getString("p.departamento");
                String aos_cuidados = result.getString("p.aos_cuidados");
                String observacoes = result.getString("p.observacoes");
                String conteudo = result.getString("p.conteudo");
                String notaFiscal = result.getString("p.notaFiscal");
                String siglaAmarracao = result.getString("p.siglaAmarracao");
                int peso = result.getInt("p.peso");
                int altura = result.getInt("p.altura");
                int largura = result.getInt("p.largura");
                int comprimento = result.getInt("p.comprimento");
                float valor_declarado = result.getFloat("p.valor_declarado");
                float valor_cobrar = result.getFloat("p.valor_cobrar");
                int mao_propria = result.getInt("p.mao_propria");
                int aviso_recebimento = result.getInt("p.aviso_recebimento");

                int posta_restante = result.getInt("p.posta_restante");
                int registro_modico = result.getInt("p.registro_modico");

                String nomeDes = result.getString("d.nome");
                String empresaDes = result.getString("d.empresa");
                String cepDes = result.getString("d.cep");
                String enderecoDes = result.getString("d.endereco");
                String bairroDes = result.getString("d.bairro");
                String cidadeDes = result.getString("d.cidade");
                String ufDes = result.getString("d.uf");
                String numeroDes = result.getString("d.numero");
                String cpfDes = result.getString("d.cpf_cnpj");
                String complementoDes = result.getString("d.complemento");
                String celularDes = result.getString("d.celular");

                int idOs = result.getInt("p.idOs");
                String responsavel = result.getString("p.responsavel");
                String emailDes = result.getString("d.email");

                int userPreVenda = result.getInt("p.userPreVenda");
                int userImpresso = result.getInt("p.userImpressao");
                int userConsolidado = result.getInt("p.userConsolidado");
                int userVenda = result.getInt("p.userVenda");
                String nomePreVenda = result.getString("p.nomePreVenda");
                String nomeImpresso = result.getString("p.nomeImpressao");
                String nomeConsolidado = result.getString("p.nomeConsolidado");
                String nomeVenda = result.getString("p.chave");
                Timestamp dataPreVenda = result.getTimestamp("p.dataPreVenda");
                Timestamp dataImpresso = result.getTimestamp("p.dataImpressao");
                Timestamp dataConsolidado = result.getTimestamp("p.dataConsolidado");
                Timestamp dataVenda = result.getTimestamp("p.dataVenda");
                String cartaoPostagem = result.getString("p.cartaoPostagem");
                String metodo_insercao = result.getString("p.metodo_insercao");
                int idDepartamento = result.getInt("p.idDepartamento");
                String arquivo_ar = result.getString("p.arquivo_ar");

                PreVenda pv = new PreVenda(id, numObjeto, idCliente, idRemetente, idDestinatario, codECT, nomeServico, contrato, departamento, aos_cuidados, observacoes, conteudo, siglaAmarracao, peso, altura, largura, comprimento, valor_declarado, mao_propria, aviso_recebimento, nomeDes, empresaDes, cpfDes, enderecoDes, numeroDes, complementoDes, cidadeDes, ufDes, cepDes, bairroDes, notaFiscal, valor_cobrar, userPreVenda, dataPreVenda, dataImpresso, dataConsolidado, dataVenda, userImpresso, userConsolidado, userVenda, nomePreVenda, nomeImpresso, nomeConsolidado, nomeVenda, responsavel, emailDes, idOs, celularDes, cartaoPostagem, metodo_insercao, posta_restante, registro_modico, idDepartamento, arquivo_ar);
                lista.add(pv);
            }
            valores.close();
            return lista;
        } catch (SQLException e) {
            System.out.println(e);
            //ContrErroLog.inserir("HOITO - contrMovimentacao", "SQLException", sql, e.toString());
            return null;
        } finally {
            Conexao.desconectar(conn);
        }
    }

    public static ArrayList<PreVenda> consultaPreVendasByClientePeriodo(int idCliente, String dataIni, String dataFim, String nomeBD) {
        Connection conn = (Connection) Conexao.conectar(nomeBD);

        String periodo = " AND DATE(dataImpressao) > DATE(NOW()) ";
        if (dataIni != null && dataFim != null && !dataIni.equals("NULL") && !dataFim.equals("NULL")) {
            periodo = " AND DATE(dataImpressao) BETWEEN '" + dataIni + "' AND '" + dataFim + "' ";
        }
        String cliente = "p.idCliente <> 0";
        if (idCliente != 0) {
            cliente = "p.idCliente = " + idCliente;
        }

        String sql = "SELECT * FROM pre_venda AS p"
                + " LEFT JOIN pre_venda_destinatario AS d ON p.idDestinatario = d.idDestinatario"
                + " WHERE " + cliente + " " + periodo;

        System.out.println(sql);
        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            ResultSet result = (ResultSet) valores.executeQuery();
            ArrayList<PreVenda> lista = new ArrayList<PreVenda>();
            for (int i = 0; result.next(); i++) {
                int id = result.getInt("p.id");
                String numObjeto = result.getString("p.numObjeto");
                int idRemetente = result.getInt("p.idRemetente");
                int idDestinatario = result.getInt("p.idDestinatario");
                int codECT = result.getInt("p.codECT");
                String nomeServico = result.getString("p.nomeServico");
                String contrato = result.getString("p.contrato");
                String departamento = result.getString("p.departamento");
                String aos_cuidados = result.getString("p.aos_cuidados");
                String observacoes = result.getString("p.observacoes");
                String conteudo = result.getString("p.conteudo");
                String notaFiscal = result.getString("p.notaFiscal");
                String siglaAmarracao = result.getString("p.siglaAmarracao");
                int peso = result.getInt("p.peso");
                int altura = result.getInt("p.altura");
                int largura = result.getInt("p.largura");
                int comprimento = result.getInt("p.comprimento");
                float valor_declarado = result.getFloat("p.valor_declarado");
                float valor_cobrar = result.getFloat("p.valor_cobrar");
                int mao_propria = result.getInt("p.mao_propria");
                int aviso_recebimento = result.getInt("p.aviso_recebimento");

                int posta_restante = result.getInt("p.posta_restante");
                int registro_modico = result.getInt("p.registro_modico");

                String nomeDes = result.getString("d.nome");
                String empresaDes = result.getString("d.empresa");
                String cepDes = result.getString("d.cep");
                String enderecoDes = result.getString("d.endereco");
                String bairroDes = result.getString("d.bairro");
                String cidadeDes = result.getString("d.cidade");
                String ufDes = result.getString("d.uf");
                String numeroDes = result.getString("d.numero");
                String cpfDes = result.getString("d.cpf_cnpj");
                String complementoDes = result.getString("d.complemento");
                String celularDes = result.getString("d.celular");

                int idOs = result.getInt("p.idOs");
                String responsavel = result.getString("p.responsavel");
                String emailDes = result.getString("d.email");

                int userPreVenda = result.getInt("p.userPreVenda");
                int userImpresso = result.getInt("p.userImpressao");
                int userConsolidado = result.getInt("p.userConsolidado");
                int userVenda = result.getInt("p.userVenda");
                String nomePreVenda = result.getString("p.nomePreVenda");
                String nomeImpresso = result.getString("p.nomeImpressao");
                String nomeConsolidado = result.getString("p.nomeConsolidado");
                String nomeVenda = result.getString("p.chave");
                Timestamp dataPreVenda = result.getTimestamp("p.dataPreVenda");
                Timestamp dataImpresso = result.getTimestamp("p.dataImpressao");
                Timestamp dataConsolidado = result.getTimestamp("p.dataConsolidado");
                Timestamp dataVenda = result.getTimestamp("p.dataVenda");
                String cartaoPostagem = result.getString("p.cartaoPostagem");
                String metodo_insercao = result.getString("p.metodo_insercao");
                int idDepartamento = result.getInt("p.idDepartamento");
                String arquivo_ar = result.getString("p.arquivo_ar");

                PreVenda pv = new PreVenda(id, numObjeto, idCliente, idRemetente, idDestinatario, codECT, nomeServico, contrato, departamento, aos_cuidados, observacoes, conteudo, siglaAmarracao, peso, altura, largura, comprimento, valor_declarado, mao_propria, aviso_recebimento, nomeDes, empresaDes, cpfDes, enderecoDes, numeroDes, complementoDes, cidadeDes, ufDes, cepDes, bairroDes, notaFiscal, valor_cobrar, userPreVenda, dataPreVenda, dataImpresso, dataConsolidado, dataVenda, userImpresso, userConsolidado, userVenda, nomePreVenda, nomeImpresso, nomeConsolidado, nomeVenda, responsavel, emailDes, idOs, celularDes, cartaoPostagem, metodo_insercao, posta_restante, registro_modico, idDepartamento, arquivo_ar);
                lista.add(pv);
            }
            valores.close();
            return lista;
        } catch (SQLException e) {
            System.out.println(e);
            //ContrErroLog.inserir("HOITO - contrMovimentacao", "SQLException", sql, e.toString());
            return null;
        } finally {
            Conexao.desconectar(conn);
        }
    }

    public static ArrayList<PreVenda> consultaVendasRelatorio(int idCliente, int impresso, int consolidado, String data, String nomeBD) {
        Connection conn = (Connection) Conexao.conectar(nomeBD);
        String sql = "SELECT * FROM pre_venda AS p"
                + " LEFT JOIN pre_venda_destinatario AS d ON p.idDestinatario = d.idDestinatario"
                + " WHERE p.idCliente = " + idCliente;
        if (data != null && !data.equals("")) {
            sql += " AND DATE(dataPreVenda) = '" + data + "'";
        } else {
            sql += " AND DATE(dataPreVenda) = DATE(NOW())";
        }
        if (consolidado >= 0) {
            sql += " AND consolidado = " + consolidado;
        }
        if (impresso >= 0) {
            sql += " AND impresso = " + impresso;
        }

        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            ResultSet result = (ResultSet) valores.executeQuery();
            ArrayList<PreVenda> lista = new ArrayList<PreVenda>();
            for (int i = 0; result.next(); i++) {
                int id = result.getInt("p.id");
                String numObjeto = result.getString("p.numObjeto");
                int idRemetente = result.getInt("p.idRemetente");
                int idDestinatario = result.getInt("p.idDestinatario");
                int codECT = result.getInt("p.codECT");
                String nomeServico = result.getString("p.nomeServico");
                String contrato = result.getString("p.contrato");
                String departamento = result.getString("p.departamento");
                String aos_cuidados = result.getString("p.aos_cuidados");
                String observacoes = result.getString("p.observacoes");
                String conteudo = result.getString("p.conteudo");
                String notaFiscal = result.getString("p.notaFiscal");
                String siglaAmarracao = result.getString("p.siglaAmarracao");
                int peso = result.getInt("p.peso");
                int altura = result.getInt("p.altura");
                int largura = result.getInt("p.largura");
                int comprimento = result.getInt("p.comprimento");
                float valor_declarado = result.getFloat("p.valor_declarado");
                float valor_cobrar = result.getFloat("p.valor_cobrar");
                int mao_propria = result.getInt("p.mao_propria");
                int aviso_recebimento = result.getInt("p.aviso_recebimento");

                int posta_restante = result.getInt("p.posta_restante");
                int registro_modico = result.getInt("p.registro_modico");

                String nomeDes = result.getString("d.nome");
                String empresaDes = result.getString("d.empresa");
                String cepDes = result.getString("d.cep");
                String enderecoDes = result.getString("d.endereco");
                String bairroDes = result.getString("d.bairro");
                String cidadeDes = result.getString("d.cidade");
                String ufDes = result.getString("d.uf");
                String numeroDes = result.getString("d.numero");
                String cpfDes = result.getString("d.cpf_cnpj");
                String complementoDes = result.getString("d.complemento");
                String celularDes = result.getString("d.celular");

                int idOs = result.getInt("p.idOs");
                String responsavel = result.getString("p.responsavel");
                String emailDes = result.getString("d.email");

                int userPreVenda = result.getInt("p.userPreVenda");
                int userImpresso = result.getInt("p.userImpressao");
                int userConsolidado = result.getInt("p.userConsolidado");
                int userVenda = result.getInt("p.userVenda");
                String nomePreVenda = result.getString("p.nomePreVenda");
                String nomeImpresso = result.getString("p.nomeImpressao");
                String nomeConsolidado = result.getString("p.nomeConsolidado");
                String nomeVenda = result.getString("p.nomeVenda");
                Timestamp dataPreVenda = result.getTimestamp("p.dataPreVenda");
                Timestamp dataImpresso = result.getTimestamp("p.dataImpressao");
                Timestamp dataConsolidado = result.getTimestamp("p.dataConsolidado");
                Timestamp dataVenda = result.getTimestamp("p.dataVenda");
                String cartaoPostagem = result.getString("p.cartaoPostagem");
                String metodo_insercao = result.getString("p.metodo_insercao");
                int idDepartamento = result.getInt("p.idDepartamento");
                String arquivo_ar = result.getString("p.arquivo_ar");

                PreVenda pv = new PreVenda(id, numObjeto, idCliente, idRemetente, idDestinatario, codECT, nomeServico, contrato, departamento, aos_cuidados, observacoes, conteudo, siglaAmarracao, peso, altura, largura, comprimento, valor_declarado, mao_propria, aviso_recebimento, nomeDes, empresaDes, cpfDes, enderecoDes, numeroDes, complementoDes, cidadeDes, ufDes, cepDes, bairroDes, notaFiscal, valor_cobrar, userPreVenda, dataPreVenda, dataImpresso, dataConsolidado, dataVenda, userImpresso, userConsolidado, userVenda, nomePreVenda, nomeImpresso, nomeConsolidado, nomeVenda, responsavel, emailDes, idOs, celularDes, cartaoPostagem, metodo_insercao, posta_restante, registro_modico, idDepartamento, arquivo_ar);
                lista.add(pv);
            }
            valores.close();
            return lista;
        } catch (SQLException e) {
            System.out.println(e);
            //ContrErroLog.inserir("HOITO - contrMovimentacao", "SQLException", sql, e.toString());
            return null;
        } finally {
            Conexao.desconectar(conn);
        }
    }

    public static PreVenda consultaVendaById(int idVenda, String nomeBD) {
        Connection conn = (Connection) Conexao.conectar(nomeBD);
        String sql = "SELECT * FROM pre_venda AS p"
                + " LEFT JOIN pre_venda_destinatario AS d ON p.idDestinatario = d.idDestinatario"
                + " WHERE p.id = " + idVenda;
        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            ResultSet result = (ResultSet) valores.executeQuery();
            PreVenda lista = null;
            for (int i = 0; result.next(); i++) {
                int id = result.getInt("p.id");
                int idCliente = result.getInt("p.idCliente");
                String numObjeto = result.getString("p.numObjeto");
                int idRemetente = result.getInt("p.idRemetente");
                int idDestinatario = result.getInt("p.idDestinatario");
                int codECT = result.getInt("p.codECT");
                String nomeServico = result.getString("p.nomeServico");
                String contrato = result.getString("p.contrato");
                String departamento = result.getString("p.departamento");
                String aos_cuidados = result.getString("p.aos_cuidados");
                String observacoes = result.getString("p.observacoes");
                String conteudo = result.getString("p.conteudo");
                String notaFiscal = result.getString("p.notaFiscal");
                String siglaAmarracao = result.getString("p.siglaAmarracao");
                int peso = result.getInt("p.peso");
                int altura = result.getInt("p.altura");
                int largura = result.getInt("p.largura");
                int comprimento = result.getInt("p.comprimento");
                float valor_declarado = result.getFloat("p.valor_declarado");
                float valor_cobrar = result.getFloat("p.valor_cobrar");
                int mao_propria = result.getInt("p.mao_propria");
                int aviso_recebimento = result.getInt("p.aviso_recebimento");

                int posta_restante = result.getInt("p.posta_restante");
                int registro_modico = result.getInt("p.registro_modico");

                String nomeDes = result.getString("d.nome");
                String empresaDes = result.getString("d.empresa");
                String cepDes = result.getString("d.cep");
                String enderecoDes = result.getString("d.endereco");
                String bairroDes = result.getString("d.bairro");
                String cidadeDes = result.getString("d.cidade");
                String ufDes = result.getString("d.uf");
                String numeroDes = result.getString("d.numero");
                String cpfDes = result.getString("d.cpf_cnpj");
                String complementoDes = result.getString("d.complemento");
                String celularDes = result.getString("d.celular");

                int idOs = result.getInt("p.idOs");
                String responsavel = result.getString("p.responsavel");
                String emailDes = result.getString("d.email");

                int userPreVenda = result.getInt("p.userPreVenda");
                int userImpresso = result.getInt("p.userImpressao");
                int userConsolidado = result.getInt("p.userConsolidado");
                int userVenda = result.getInt("p.userVenda");
                String nomePreVenda = result.getString("p.nomePreVenda");
                String nomeImpresso = result.getString("p.nomeImpressao");
                String nomeConsolidado = result.getString("p.nomeConsolidado");
                String nomeVenda = result.getString("p.nomeVenda");
                Timestamp dataPreVenda = result.getTimestamp("p.dataPreVenda");
                Timestamp dataImpresso = result.getTimestamp("p.dataImpressao");
                Timestamp dataConsolidado = result.getTimestamp("p.dataConsolidado");
                Timestamp dataVenda = result.getTimestamp("p.dataVenda");
                String cartaoPostagem = result.getString("p.cartaoPostagem");
                String metodo_insercao = result.getString("p.metodo_insercao");
                int idDepartamento = result.getInt("p.idDepartamento");
                String arquivo_ar = result.getString("p.arquivo_ar");

                lista = new PreVenda(id, numObjeto, idCliente, idRemetente, idDestinatario, codECT, nomeServico, contrato, departamento, aos_cuidados, observacoes, conteudo, siglaAmarracao, peso, altura, largura, comprimento, valor_declarado, mao_propria, aviso_recebimento, nomeDes, empresaDes, cpfDes, enderecoDes, numeroDes, complementoDes, cidadeDes, ufDes, cepDes, bairroDes, notaFiscal, valor_cobrar, userPreVenda, dataPreVenda, dataImpresso, dataConsolidado, dataVenda, userImpresso, userConsolidado, userVenda, nomePreVenda, nomeImpresso, nomeConsolidado, nomeVenda, responsavel, emailDes, idOs, celularDes, cartaoPostagem, metodo_insercao, posta_restante, registro_modico, idDepartamento, arquivo_ar);
            }
            valores.close();
            return lista;
        } catch (SQLException e) {
            System.out.println(e);
            //ContrErroLog.inserir("HOITO - contrMovimentacao", "SQLException", sql, e.toString());
            return null;
        } finally {
            Conexao.desconectar(conn);
        }
    }

    public static ArrayList<PreVenda> consultaVendaByIds(String ids, String nomeBD) {
        Connection conn = (Connection) Conexao.conectar(nomeBD);
        String sql = "SELECT * FROM pre_venda AS p"
                + " LEFT JOIN pre_venda_destinatario AS d ON p.idDestinatario = d.idDestinatario"
                + " WHERE p.id IN (" + ids + ")";
        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            ResultSet result = (ResultSet) valores.executeQuery();
            ArrayList<PreVenda> lista = new ArrayList<PreVenda>();
            for (int i = 0; result.next(); i++) {
                int id = result.getInt("p.id");
                int idCliente = result.getInt("p.idCliente");
                String numObjeto = result.getString("p.numObjeto");
                int idRemetente = result.getInt("p.idRemetente");
                int idDestinatario = result.getInt("p.idDestinatario");
                int codECT = result.getInt("p.codECT");
                String nomeServico = result.getString("p.nomeServico");
                String contrato = result.getString("p.contrato");
                String departamento = result.getString("p.departamento");
                String aos_cuidados = result.getString("p.aos_cuidados");
                String observacoes = result.getString("p.observacoes");
                String conteudo = result.getString("p.conteudo");
                String notaFiscal = result.getString("p.notaFiscal");
                String siglaAmarracao = result.getString("p.siglaAmarracao");
                int peso = result.getInt("p.peso");
                int altura = result.getInt("p.altura");
                int largura = result.getInt("p.largura");
                int comprimento = result.getInt("p.comprimento");
                float valor_declarado = result.getFloat("p.valor_declarado");
                float valor_cobrar = result.getFloat("p.valor_cobrar");
                int mao_propria = result.getInt("p.mao_propria");
                int aviso_recebimento = result.getInt("p.aviso_recebimento");

                int posta_restante = result.getInt("p.posta_restante");
                int registro_modico = result.getInt("p.registro_modico");

                String nomeDes = result.getString("d.nome");
                String empresaDes = result.getString("d.empresa");
                String cepDes = result.getString("d.cep");
                String enderecoDes = result.getString("d.endereco");
                String bairroDes = result.getString("d.bairro");
                String cidadeDes = result.getString("d.cidade");
                String ufDes = result.getString("d.uf");
                String numeroDes = result.getString("d.numero");
                String cpfDes = result.getString("d.cpf_cnpj");
                String complementoDes = result.getString("d.complemento");
                String celularDes = result.getString("d.celular");

                int idOs = result.getInt("p.idOs");
                String responsavel = result.getString("p.responsavel");
                String emailDes = result.getString("d.email");

                int userPreVenda = result.getInt("p.userPreVenda");
                int userImpresso = result.getInt("p.userImpressao");
                int userConsolidado = result.getInt("p.userConsolidado");
                int userVenda = result.getInt("p.userVenda");
                String nomePreVenda = result.getString("p.nomePreVenda");
                String nomeImpresso = result.getString("p.nomeImpressao");
                String nomeConsolidado = result.getString("p.nomeConsolidado");
                String nomeVenda = result.getString("p.nomeVenda");
                Timestamp dataPreVenda = result.getTimestamp("p.dataPreVenda");
                Timestamp dataImpresso = result.getTimestamp("p.dataImpressao");
                Timestamp dataConsolidado = result.getTimestamp("p.dataConsolidado");
                Timestamp dataVenda = result.getTimestamp("p.dataVenda");
                String cartaoPostagem = result.getString("p.cartaoPostagem");
                String metodo_insercao = result.getString("p.metodo_insercao");
                int idDepartamento = result.getInt("p.idDepartamento");
                String arquivo_ar = result.getString("p.arquivo_ar");

                lista.add(new PreVenda(id, numObjeto, idCliente, idRemetente, idDestinatario, codECT, nomeServico, contrato, departamento, aos_cuidados, observacoes, conteudo, siglaAmarracao, peso, altura, largura, comprimento, valor_declarado, mao_propria, aviso_recebimento, nomeDes, empresaDes, cpfDes, enderecoDes, numeroDes, complementoDes, cidadeDes, ufDes, cepDes, bairroDes, notaFiscal, valor_cobrar, userPreVenda, dataPreVenda, dataImpresso, dataConsolidado, dataVenda, userImpresso, userConsolidado, userVenda, nomePreVenda, nomeImpresso, nomeConsolidado, nomeVenda, responsavel, emailDes, idOs, celularDes, cartaoPostagem, metodo_insercao, posta_restante, registro_modico, idDepartamento, arquivo_ar));
            }
            valores.close();
            return lista;
        } catch (SQLException e) {
            System.out.println(e);
            //ContrErroLog.inserir("HOITO - contrMovimentacao", "SQLException", sql, e.toString());
            return null;
        } finally {
            Conexao.desconectar(conn);
        }
    }

    public static int consultaIdBySRO(String sro, String nomeBD) {
        Connection conn = (Connection) Conexao.conectar(nomeBD);
        String sql = "SELECT id FROM pre_venda "
                + " WHERE numObjeto = '" + sro + "' ;";
        int re = 0;
        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            ResultSet result = (ResultSet) valores.executeQuery();
           
            if (result.next()) {
                re = result.getInt("p.id");
            }
        } catch (Exception ex) {

        }
        return re;
    }

    public static PreVenda consultaVendaBySRO(String sro, String nomeBD) {
        Connection conn = (Connection) Conexao.conectar(nomeBD);
        String sql = "SELECT * FROM pre_venda AS p"
                + " LEFT JOIN pre_venda_destinatario AS d ON p.idDestinatario = d.idDestinatario"
                + " WHERE p.numObjeto = '" + sro + "' ;";
        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            ResultSet result = (ResultSet) valores.executeQuery();
            PreVenda lista = null;
            for (int i = 0; result.next(); i++) {
                int id = result.getInt("p.id");
                int idCliente = result.getInt("p.idCliente");
                String numObjeto = result.getString("p.numObjeto");
                int idRemetente = result.getInt("p.idRemetente");
                int idDestinatario = result.getInt("p.idDestinatario");
                int codECT = result.getInt("p.codECT");
                String nomeServico = result.getString("p.nomeServico");
                String contrato = result.getString("p.contrato");
                String departamento = result.getString("p.departamento");
                String aos_cuidados = result.getString("p.aos_cuidados");
                String observacoes = result.getString("p.observacoes");
                String conteudo = result.getString("p.conteudo");
                String notaFiscal = result.getString("p.notaFiscal");
                String siglaAmarracao = result.getString("p.siglaAmarracao");
                int peso = result.getInt("p.peso");
                int altura = result.getInt("p.altura");
                int largura = result.getInt("p.largura");
                int comprimento = result.getInt("p.comprimento");
                float valor_declarado = result.getFloat("p.valor_declarado");
                float valor_cobrar = result.getFloat("p.valor_cobrar");
                int mao_propria = result.getInt("p.mao_propria");
                int aviso_recebimento = result.getInt("p.aviso_recebimento");
                String cartaoPostagem = result.getString("p.cartaoPostagem");

                int posta_restante = result.getInt("p.posta_restante");
                int registro_modico = result.getInt("p.registro_modico");

                String nomeDes = result.getString("d.nome");
                String empresaDes = result.getString("d.empresa");
                String cepDes = result.getString("d.cep");
                String enderecoDes = result.getString("d.endereco");
                String bairroDes = result.getString("d.bairro");
                String cidadeDes = result.getString("d.cidade");
                String ufDes = result.getString("d.uf");
                String numeroDes = result.getString("d.numero");
                String cpfDes = result.getString("d.cpf_cnpj");
                String complementoDes = result.getString("d.complemento");
                String celularDes = result.getString("d.celular");

                int idOs = result.getInt("p.idOs");
                String responsavel = result.getString("p.responsavel");
                String emailDes = result.getString("d.email");

                int userPreVenda = result.getInt("p.userPreVenda");
                int userImpresso = result.getInt("p.userImpressao");
                int userConsolidado = result.getInt("p.userConsolidado");
                int userVenda = result.getInt("p.userVenda");
                String nomePreVenda = result.getString("p.nomePreVenda");
                String nomeImpresso = result.getString("p.nomeImpressao");
                String nomeConsolidado = result.getString("p.nomeConsolidado");
                String nomeVenda = result.getString("p.nomeVenda");
                Timestamp dataPreVenda = result.getTimestamp("p.dataPreVenda");
                Timestamp dataImpresso = result.getTimestamp("p.dataImpressao");
                Timestamp dataConsolidado = result.getTimestamp("p.dataConsolidado");
                Timestamp dataVenda = result.getTimestamp("p.dataVenda");
                String metodo_insercao = result.getString("p.metodo_insercao");
                int idDepartamento = result.getInt("p.idDepartamento");
                String arquivo_ar = result.getString("p.arquivo_ar");

                lista = new PreVenda(id, numObjeto, idCliente, idRemetente, idDestinatario, codECT, nomeServico, contrato, departamento, aos_cuidados, observacoes, conteudo, siglaAmarracao, peso, altura, largura, comprimento, valor_declarado, mao_propria, aviso_recebimento, nomeDes, empresaDes, cpfDes, enderecoDes, numeroDes, complementoDes, cidadeDes, ufDes, cepDes, bairroDes, notaFiscal, valor_cobrar, userPreVenda, dataPreVenda, dataImpresso, dataConsolidado, dataVenda, userImpresso, userConsolidado, userVenda, nomePreVenda, nomeImpresso, nomeConsolidado, nomeVenda, responsavel, emailDes, idOs, celularDes, cartaoPostagem, metodo_insercao, posta_restante, registro_modico, idDepartamento, arquivo_ar);
            }
            valores.close();
            return lista;
        } catch (SQLException e) {
            System.out.println(e);
            //ContrErroLog.inserir("HOITO - contrMovimentacao", "SQLException", sql, e.toString());
            return null;
        } finally {
            Conexao.desconectar(conn);
        }
    }

    public static ArrayList<PreVenda> consultaVendasNaoConsolidadas(String nomeBD, String dataIni, String dataFim, int idCli, int inut) {
        Connection conn = (Connection) Conexao.conectar(nomeBD);
        String cliente = "";
        if (idCli > 0) {
            cliente = " AND p.idCliente = " + idCli;
        }
        String sql = "SELECT * FROM pre_venda AS p"
                + " LEFT JOIN pre_venda_destinatario AS d ON p.idDestinatario = d.idDestinatario"
                + " WHERE consolidado = 0 AND impresso = 1 AND inutilizada = " + inut + " AND nomeServico <> 'SIMPLES' AND numObjeto <> 'avista' AND DATE(dataImpressao) BETWEEN '" + dataIni + "' AND '" + dataFim + "' " + cliente + ";";
        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            ResultSet result = (ResultSet) valores.executeQuery();
            ArrayList<PreVenda> lista = new ArrayList<PreVenda>();
            for (int i = 0; result.next(); i++) {
                int id = result.getInt("p.id");
                int idCliente = result.getInt("p.idCliente");
                String numObjeto = result.getString("p.numObjeto");
                int idRemetente = result.getInt("p.idRemetente");
                int idDestinatario = result.getInt("p.idDestinatario");
                int codECT = result.getInt("p.codECT");
                String nomeServico = result.getString("p.nomeServico");
                String contrato = result.getString("p.contrato");
                String departamento = result.getString("p.departamento");
                String aos_cuidados = result.getString("p.aos_cuidados");
                String observacoes = result.getString("p.observacoes");
                String conteudo = result.getString("p.conteudo");
                String notaFiscal = result.getString("p.notaFiscal");
                String siglaAmarracao = result.getString("p.siglaAmarracao");
                int peso = result.getInt("p.peso");
                int altura = result.getInt("p.altura");
                int largura = result.getInt("p.largura");
                int comprimento = result.getInt("p.comprimento");
                float valor_declarado = result.getFloat("p.valor_declarado");
                float valor_cobrar = result.getFloat("p.valor_cobrar");
                int mao_propria = result.getInt("p.mao_propria");
                int aviso_recebimento = result.getInt("p.aviso_recebimento");

                int posta_restante = result.getInt("p.posta_restante");
                int registro_modico = result.getInt("p.registro_modico");

                String nomeDes = result.getString("d.nome");
                String empresaDes = result.getString("d.empresa");
                String cepDes = result.getString("d.cep");
                String enderecoDes = result.getString("d.endereco");
                String bairroDes = result.getString("d.bairro");
                String cidadeDes = result.getString("d.cidade");
                String ufDes = result.getString("d.uf");
                String numeroDes = result.getString("d.numero");
                String cpfDes = result.getString("d.cpf_cnpj");
                String complementoDes = result.getString("d.complemento");
                String celularDes = result.getString("d.celular");

                int idOs = result.getInt("p.idOs");
                String responsavel = result.getString("p.responsavel");
                String emailDes = result.getString("d.email");

                int userPreVenda = result.getInt("p.userPreVenda");
                int userImpresso = result.getInt("p.userImpressao");
                int userConsolidado = result.getInt("p.userConsolidado");
                int userVenda = result.getInt("p.userVenda");
                String nomePreVenda = result.getString("p.nomePreVenda");
                String nomeImpresso = result.getString("p.nomeImpressao");
                String nomeConsolidado = result.getString("p.nomeConsolidado");
                String nomeVenda = result.getString("p.nomeVenda");
                Timestamp dataPreVenda = result.getTimestamp("p.dataPreVenda");
                Timestamp dataImpresso = result.getTimestamp("p.dataImpressao");
                Timestamp dataConsolidado = result.getTimestamp("p.dataConsolidado");
                Timestamp dataVenda = result.getTimestamp("p.dataVenda");
                String cartaoPostagem = result.getString("p.cartaoPostagem");
                String metodo_insercao = result.getString("p.metodo_insercao");
                int idDepartamento = result.getInt("p.idDepartamento");
                String arquivo_ar = result.getString("p.arquivo_ar");

                PreVenda pv = new PreVenda(id, numObjeto, idCliente, idRemetente, idDestinatario, codECT, nomeServico, contrato, departamento, aos_cuidados, observacoes, conteudo, siglaAmarracao, peso, altura, largura, comprimento, valor_declarado, mao_propria, aviso_recebimento, nomeDes, empresaDes, cpfDes, enderecoDes, numeroDes, complementoDes, cidadeDes, ufDes, cepDes, bairroDes, notaFiscal, valor_cobrar, userPreVenda, dataPreVenda, dataImpresso, dataConsolidado, dataVenda, userImpresso, userConsolidado, userVenda, nomePreVenda, nomeImpresso, nomeConsolidado, nomeVenda, responsavel, emailDes, idOs, celularDes, cartaoPostagem, metodo_insercao, posta_restante, registro_modico, idDepartamento, arquivo_ar);
                lista.add(pv);
            }
            valores.close();
            return lista;
        } catch (SQLException e) {
            System.out.println(e);
            //ContrErroLog.inserir("HOITO - contrMovimentacao", "SQLException", sql, e.toString());
            return null;
        } finally {
            Conexao.desconectar(conn);
        }
    }

    public static ArrayList<PreVenda> consultaVendasImportadas(String nomeBD, int idCli) {
        Connection conn = (Connection) Conexao.conectar(nomeBD);
        String sql = "SELECT * FROM pre_venda AS p"
                + " LEFT JOIN pre_venda_destinatario AS d ON p.idDestinatario = d.idDestinatario"
                + " WHERE idRemetente = 1 AND userPreVenda = 0 AND p.idCliente = " + idCli + ";";
        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            ResultSet result = (ResultSet) valores.executeQuery();
            ArrayList<PreVenda> lista = new ArrayList<PreVenda>();
            for (int i = 0; result.next(); i++) {
                int id = result.getInt("p.id");
                int idCliente = result.getInt("p.idCliente");
                String numObjeto = result.getString("p.numObjeto");
                int idRemetente = result.getInt("p.idRemetente");
                int idDestinatario = result.getInt("p.idDestinatario");
                int codECT = result.getInt("p.codECT");
                String nomeServico = result.getString("p.nomeServico");
                String contrato = result.getString("p.contrato");
                String departamento = result.getString("p.departamento");
                String aos_cuidados = result.getString("p.aos_cuidados");
                String observacoes = result.getString("p.observacoes");
                String conteudo = result.getString("p.conteudo");
                String notaFiscal = result.getString("p.notaFiscal");
                String siglaAmarracao = result.getString("p.siglaAmarracao");
                int peso = result.getInt("p.peso");
                int altura = result.getInt("p.altura");
                int largura = result.getInt("p.largura");
                int comprimento = result.getInt("p.comprimento");
                float valor_declarado = result.getFloat("p.valor_declarado");
                float valor_cobrar = result.getFloat("p.valor_cobrar");
                int mao_propria = result.getInt("p.mao_propria");
                int aviso_recebimento = result.getInt("p.aviso_recebimento");

                int posta_restante = result.getInt("p.posta_restante");
                int registro_modico = result.getInt("p.registro_modico");

                String nomeDes = result.getString("d.nome");
                String empresaDes = result.getString("d.empresa");
                String cepDes = result.getString("d.cep");
                String enderecoDes = result.getString("d.endereco");
                String bairroDes = result.getString("d.bairro");
                String cidadeDes = result.getString("d.cidade");
                String ufDes = result.getString("d.uf");
                String numeroDes = result.getString("d.numero");
                String cpfDes = result.getString("d.cpf_cnpj");
                String complementoDes = result.getString("d.complemento");
                String celularDes = result.getString("d.celular");
                String emailDes = result.getString("d.email");

                int idOs = result.getInt("p.idOs");
                String responsavel = result.getString("p.responsavel");

                int userPreVenda = result.getInt("p.userPreVenda");
                int userImpresso = result.getInt("p.userImpressao");
                int userConsolidado = result.getInt("p.userConsolidado");
                int userVenda = result.getInt("p.userVenda");
                String nomePreVenda = result.getString("p.nomePreVenda");
                String nomeImpresso = result.getString("p.nomeImpressao");
                String nomeConsolidado = result.getString("p.nomeConsolidado");
                String nomeVenda = result.getString("p.nomeVenda");
                Timestamp dataPreVenda = result.getTimestamp("p.dataPreVenda");
                Timestamp dataImpresso = result.getTimestamp("p.dataImpressao");
                Timestamp dataConsolidado = result.getTimestamp("p.dataConsolidado");
                Timestamp dataVenda = result.getTimestamp("p.dataVenda");
                String cartaoPostagem = result.getString("p.cartaoPostagem");
                String metodo_insercao = result.getString("p.metodo_insercao");
                int idDepartamento = result.getInt("p.idDepartamento");
                String arquivo_ar = result.getString("p.arquivo_ar");

                PreVenda pv = new PreVenda(id, numObjeto, idCliente, idRemetente, idDestinatario, codECT, nomeServico, contrato, departamento, aos_cuidados, observacoes, conteudo, siglaAmarracao, peso, altura, largura, comprimento, valor_declarado, mao_propria, aviso_recebimento, nomeDes, empresaDes, cpfDes, enderecoDes, numeroDes, complementoDes, cidadeDes, ufDes, cepDes, bairroDes, notaFiscal, valor_cobrar, userPreVenda, dataPreVenda, dataImpresso, dataConsolidado, dataVenda, userImpresso, userConsolidado, userVenda, nomePreVenda, nomeImpresso, nomeConsolidado, nomeVenda, responsavel, emailDes, idOs, celularDes, cartaoPostagem, metodo_insercao, posta_restante, registro_modico, idDepartamento, arquivo_ar);
                lista.add(pv);
            }
            valores.close();
            return lista;
        } catch (SQLException e) {
            System.out.println(e);
            //ContrErroLog.inserir("HOITO - contrMovimentacao", "SQLException", sql, e.toString());
            return null;
        } finally {
            Conexao.desconectar(conn);
        }
    }

    public static ArrayList<PreVenda> consultaVendasArDigital(String nomeBD, int idCli, String data1, String data2, String arquivoFTP) {
        Connection conn = (Connection) Conexao.conectar(nomeBD);

        String whererArq = " AND DATE(dataImpressao) BETWEEN '" + data1 + "' AND '" + data2 + "' ";
        if (arquivoFTP != null && arquivoFTP.equals("1")) {
            //somente com arquivos gerados
            whererArq += " AND p.arquivo_ar <> '' ";
        } else if (arquivoFTP != null && arquivoFTP.equals("2")) {
            //somente sem arquivos gerados
            whererArq += " AND p.arquivo_ar = '' ";
        } else if (arquivoFTP != null && !arquivoFTP.equals("") && !arquivoFTP.equals("0")) {
            whererArq = " AND p.arquivo_ar = '" + arquivoFTP + "' ";
        }

        String sql = "SELECT * FROM pre_venda AS p"
                + " LEFT JOIN pre_venda_destinatario AS d ON p.idDestinatario = d.idDestinatario"
                + " WHERE aviso_recebimento = 2 AND p.idCliente = " + idCli + " "
                + whererArq;
        
        System.out.println(sql);
        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            ResultSet result = (ResultSet) valores.executeQuery();
            ArrayList<PreVenda> lista = new ArrayList<PreVenda>();
            for (int i = 0; result.next(); i++) {
                int id = result.getInt("p.id");
                int idCliente = result.getInt("p.idCliente");
                String numObjeto = result.getString("p.numObjeto");
                int idRemetente = result.getInt("p.idRemetente");
                int idDestinatario = result.getInt("p.idDestinatario");
                int codECT = result.getInt("p.codECT");
                String nomeServico = result.getString("p.nomeServico");
                String contrato = result.getString("p.contrato");
                String departamento = result.getString("p.departamento");
                String aos_cuidados = result.getString("p.aos_cuidados");
                String observacoes = result.getString("p.observacoes");
                String conteudo = result.getString("p.conteudo");
                String notaFiscal = result.getString("p.notaFiscal");
                String siglaAmarracao = result.getString("p.siglaAmarracao");
                int peso = result.getInt("p.peso");
                int altura = result.getInt("p.altura");
                int largura = result.getInt("p.largura");
                int comprimento = result.getInt("p.comprimento");
                float valor_declarado = result.getFloat("p.valor_declarado");
                float valor_cobrar = result.getFloat("p.valor_cobrar");
                int mao_propria = result.getInt("p.mao_propria");
                int aviso_recebimento = result.getInt("p.aviso_recebimento");

                int posta_restante = result.getInt("p.posta_restante");
                int registro_modico = result.getInt("p.registro_modico");

                String nomeDes = result.getString("d.nome");
                String empresaDes = result.getString("d.empresa");
                String cepDes = result.getString("d.cep");
                String enderecoDes = result.getString("d.endereco");
                String bairroDes = result.getString("d.bairro");
                String cidadeDes = result.getString("d.cidade");
                String ufDes = result.getString("d.uf");
                String numeroDes = result.getString("d.numero");
                String cpfDes = result.getString("d.cpf_cnpj");
                String complementoDes = result.getString("d.complemento");
                String celularDes = result.getString("d.celular");
                String emailDes = result.getString("d.email");

                int idOs = result.getInt("p.idOs");
                String responsavel = result.getString("p.responsavel");

                int userPreVenda = result.getInt("p.userPreVenda");
                int userImpresso = result.getInt("p.userImpressao");
                int userConsolidado = result.getInt("p.userConsolidado");
                int userVenda = result.getInt("p.userVenda");
                String nomePreVenda = result.getString("p.nomePreVenda");
                String nomeImpresso = result.getString("p.nomeImpressao");
                String nomeConsolidado = result.getString("p.nomeConsolidado");
                String nomeVenda = result.getString("p.nomeVenda");
                Timestamp dataPreVenda = result.getTimestamp("p.dataPreVenda");
                Timestamp dataImpresso = result.getTimestamp("p.dataImpressao");
                Timestamp dataConsolidado = result.getTimestamp("p.dataConsolidado");
                Timestamp dataVenda = result.getTimestamp("p.dataVenda");
                String cartaoPostagem = result.getString("p.cartaoPostagem");
                String metodo_insercao = result.getString("p.metodo_insercao");
                int idDepartamento = result.getInt("p.idDepartamento");
                String arquivo_ar = result.getString("p.arquivo_ar");

                PreVenda pv = new PreVenda(id, numObjeto, idCliente, idRemetente, idDestinatario, codECT, nomeServico, contrato, departamento, aos_cuidados, observacoes, conteudo, siglaAmarracao, peso, altura, largura, comprimento, valor_declarado, mao_propria, aviso_recebimento, nomeDes, empresaDes, cpfDes, enderecoDes, numeroDes, complementoDes, cidadeDes, ufDes, cepDes, bairroDes, notaFiscal, valor_cobrar, userPreVenda, dataPreVenda, dataImpresso, dataConsolidado, dataVenda, userImpresso, userConsolidado, userVenda, nomePreVenda, nomeImpresso, nomeConsolidado, nomeVenda, responsavel, emailDes, idOs, celularDes, cartaoPostagem, metodo_insercao, posta_restante, registro_modico, idDepartamento, arquivo_ar);
                lista.add(pv);
            }
            valores.close();
            return lista;
        } catch (SQLException e) {
            System.out.println(e);
            //ContrErroLog.inserir("HOITO - contrMovimentacao", "SQLException", sql, e.toString());
            return null;
        } finally {
            Conexao.desconectar(conn);
        }
    }

}
