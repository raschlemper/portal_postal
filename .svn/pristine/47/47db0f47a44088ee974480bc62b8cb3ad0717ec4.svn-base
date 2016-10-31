/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Emporium.Controle;

import Controle.ContrErroLog;
import Util.Conexao;
import Util.FormataString;
import Util.FormatarData;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author SCC4
 */
public class ContrRelatorios {

    public static float pesquisaPrazoEntregaPorServico(String nomeBD, String idCliente, String servico, String data1, String data2, String departamento) throws ParseException {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date vdata1 = (java.util.Date) formatter.parse(data1);
        Date vdata2 = (java.util.Date) formatter.parse(data2);
        data1 = sdf.format(vdata1);
        data2 = sdf.format(vdata2);

        String strQuery = "SELECT DATEDIFF(t.last_status_date, dataPostagem) AS conta"
                + " FROM movimentacao"
                + " LEFT JOIN movimentacao_tracking AS t ON movimentacao.numObjeto = t.numObjeto"
                + " WHERE (dataPostagem BETWEEN '" + data1 + "' AND '" + data2 + "')"
                + " AND codCliente = " + idCliente + ""
                + " AND t.last_status_name LIKE '%entregue%' ";

        if (!servico.equals("0")) {
            //strQuery += " AND descServico LIKE '%" + servico + "%' ";
            strQuery += FormataString.montaWhereServicos(servico);
        }
        
        strQuery += departamento;
        

        Connection conn = (Connection) Conexao.conectar(nomeBD);
        try {
            PreparedStatement valores = conn.prepareStatement(strQuery);
            ResultSet result = (ResultSet) valores.executeQuery();
            ArrayList<Integer> lista = new ArrayList<Integer>();
            float total = 0;
            for (int i = 0; result.next(); i++) {
                int conta = result.getInt("conta");
                total += conta;
                lista.add(conta);
            }
            if (lista.size() > 0) {
                total = total / lista.size();
            }

            valores.close();
            return total;

        } catch (SQLException ex) {
            System.out.println(ex);
            return 0;
        } finally {
            Conexao.desconectar(conn);
        }
    }

    public static ArrayList pesquisaPrazoEntregaPorEstado(String nomeBD, String idCliente, String servico, String data1, String data2, String departamento) throws ParseException {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date vdata1 = (java.util.Date) formatter.parse(data1);
        Date vdata2 = (java.util.Date) formatter.parse(data2);
        data1 = sdf.format(vdata1);
        data2 = sdf.format(vdata2);

        String clausulaWhere = " WHERE (dataPostagem BETWEEN '" + data1 + "' AND '" + data2 + "')"
                + " AND codCliente = " + idCliente + ""
                + " AND t.last_status_name LIKE '%entregue%'"
                + " AND movimentacao.numObjeto <> '-' ";

        if (!servico.equals("0")) {
            //clausulaWhere += " AND descServico LIKE '%" + servico + "%'";
            clausulaWhere += FormataString.montaWhereServicos(servico);
        }
        
        clausulaWhere += departamento;

        String strQuery = "SELECT COUNT(cep) AS qtdObjetos, SUM(DATEDIFF(DATE(t.last_status_date), dataPostagem))/COUNT(*) AS prazoMedioDeEntrega, "
                + " CASE "
                + " WHEN (cep=0) THEN 'INT' "
                + " WHEN (cep>0 AND cep<=19999999) THEN 'SP' "
                + " WHEN (cep>=20000000 AND cep<=28999999) THEN 'RJ' "
                + " WHEN (cep>=29000000 AND cep<=29999999) THEN 'ES' "
                + " WHEN (cep>=30000000 AND cep<=39999999) THEN 'MG' "
                + " WHEN (cep>=40000000 AND cep<=48999999) THEN 'BA' "
                + " WHEN (cep>=49000000 AND cep<=49999999) THEN 'SE' "
                + " WHEN (cep>=50000000 AND cep<=56999999) THEN 'PE' "
                + " WHEN (cep>=57000000 AND cep<=57999999) THEN 'AL' "
                + " WHEN (cep>=58000000 AND cep<=58999999) THEN 'PB' "
                + " WHEN (cep>=59000000 AND cep<=59999999) THEN 'RN' "
                + " WHEN (cep>=60000000 AND cep<=63999999) THEN 'CE' "
                + " WHEN (cep>=64000000 AND cep<=64999999) THEN 'PI' "
                + " WHEN (cep>=65000000 AND cep<=65999999) THEN 'MA' "
                + " WHEN (cep>=66000000 AND cep<=68899999) THEN 'PA' "
                + " WHEN (cep>=68900000 AND cep<=68999999) THEN 'AP' "
                + " WHEN (cep>=69000000 AND cep<=69299999) THEN 'AM' "
                + " WHEN (cep>=69300000 AND cep<=69389999) THEN 'RR' "
                + " WHEN (cep>=69400000 AND cep<=69899999) THEN 'AM' "
                + " WHEN (cep>=69900000 AND cep<=69999999) THEN 'AC' "
                + " WHEN (cep>=70000000 AND cep<=73699999) THEN 'DF' "
                + " WHEN (cep>=72800000 AND cep<=76799999) THEN 'GO' "
                + " WHEN (cep>=76800000 AND cep<=76999999) THEN 'RO' "
                + " WHEN (cep>=77000000 AND cep<=77995999) THEN 'TO' "
                + " WHEN (cep>=78000000 AND cep<=78899999) THEN 'MT' "
                + " WHEN (cep>=78900000 AND cep<=78999999) THEN 'RO' "
                + " WHEN (cep>=79000000 AND cep<=79999999) THEN 'MS' "
                + " WHEN (cep>=80000000 AND cep<=87999999) THEN 'PR' "
                + " WHEN (cep>=88000000 AND cep<=89999999) THEN 'SC' "
                + " WHEN (cep>=90000000 AND cep<=99999999) THEN 'RS' "
                + " ELSE '--' "
                + " END AS estado "
                + " FROM movimentacao "
                + " LEFT JOIN movimentacao_tracking AS t ON movimentacao.numObjeto = t.numObjeto"
                + clausulaWhere
                + " GROUP BY estado;";
        
        Connection conn = (Connection) Conexao.conectar(nomeBD);
        try {
            PreparedStatement valores = conn.prepareStatement(strQuery);
            ResultSet result = (ResultSet) valores.executeQuery();

            ArrayList lista = new ArrayList();

            for (int i = 0; result.next(); i++) {
                String conta = result.getString("qtdObjetos").trim();
                String prazo = result.getString("prazoMedioDeEntrega").trim();
                String estado = result.getString("estado");
                String[] obj = {conta, prazo, estado};
                lista.add(obj);
            }

            valores.close();
            return lista;

        } catch (SQLException e) {
            System.out.println(e);
            ContrErroLog.inserir("HOITO - contrRelatorios", "SQLException", strQuery, e.toString());
            return null;
        } finally {
            Conexao.desconectar(conn);
        }
    }

    public static ArrayList pesquisaEntregaPorPeriodo(String nomeBD, String idCliente, String servico, String data1, String data2, String departamento) throws ParseException {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date vdata1 = (java.util.Date) formatter.parse(data1);
        Date vdata2 = (java.util.Date) formatter.parse(data2);
        data1 = sdf.format(vdata1);
        data2 = sdf.format(vdata2);

        double dias = FormatarData.diferencaEmDias(vdata1, vdata2);
        String group = "DATE";
        if (dias > 40) {
            group = "MONTH";
        } else if (dias > 380) {
            group = "YEAR";
        }

        String clausulaWhere = "WHERE (dataPostagem BETWEEN '" + data1 + "' AND '" + data2 + "')"
                + " AND codCliente = " + idCliente
                + " AND movimentacao.numObjeto <> '-' ";
        if (!servico.equals("0")) {
            //clausulaWhere += " AND descServico LIKE '%" + servico + "%'";
            clausulaWhere += FormataString.montaWhereServicos(servico);
        }
        clausulaWhere += departamento;


        String strQuery = "SELECT COUNT(*) AS qtdObjetos, COUNT(IF(t.last_status_name LIKE '%entregue%',1,NULL)) AS QtdEntregues, " + group + "(dataPostagem) AS agrupamento"
                + " FROM movimentacao "
                + " LEFT JOIN movimentacao_tracking AS t ON movimentacao.numObjeto = t.numObjeto "
                + clausulaWhere                
                + " GROUP BY agrupamento"
                + " ORDER BY dataPostagem;";

        Connection conn = (Connection) Conexao.conectar(nomeBD);
        try {
            PreparedStatement valores = conn.prepareStatement(strQuery);
            ResultSet result = (ResultSet) valores.executeQuery();

            ArrayList lista = new ArrayList();

            for (int i = 0; result.next(); i++) {
                String datas = result.getString("agrupamento");
                if (dias > 40) {
                    datas = FormatarData.getNomeMes(result.getString("agrupamento"));
                } else {
                    try {
                        datas = FormatarData.formataStringToString(result.getString("agrupamento"), "yyyy-MM-dd", "dd/MM/yyyy");
                    } catch (Exception ex) {}
                }
                String conta = result.getInt("qtdObjetos")+"";
                String conta2 = result.getInt("qtdEntregues")+"";

                String[] obj = {conta, conta2, datas};
                lista.add(obj);
            }

            valores.close();
            return lista;

        } catch (SQLException e) {
            System.out.println(e);
            ContrErroLog.inserir("HOITO - contrRelatorios", "SQLException", strQuery, e.toString());
            return null;
        } finally {
            Conexao.desconectar(conn);
        }
    }

    public static ArrayList pesquisaDespesasPorPeriodo(String nomeBD, String idCliente, String servico, String departamento) throws ParseException {

        String clausulaWhere = "";
        if (!servico.equals("0")) {
            //clausulaWhere += " AND descServico LIKE '%" + servico + "%'";
            clausulaWhere += FormataString.montaWhereServicos(servico);
        }
        if (!departamento.equals("0")) {
            clausulaWhere += " AND departamento LIKE '%" + departamento + "%'";
        }

        String strQuery = "SELECT SUM(quantidade) AS qtd, SUM(valorServico) AS vlr, MONTH(dataPostagem) AS agrupamento"
                        + " FROM movimentacao "
                        + " WHERE ((MONTH(dataPostagem) = MONTH(NOW()) AND YEAR(dataPostagem) = YEAR(NOW()))"
                        + " OR (MONTH(dataPostagem) = MONTH(DATE_ADD(NOW(), INTERVAL -1 MONTH)) AND YEAR(dataPostagem) = YEAR(DATE_ADD(NOW(), INTERVAL -1 MONTH)))"
                        + " OR (MONTH(dataPostagem) = MONTH(DATE_ADD(NOW(), INTERVAL -2 MONTH)) AND YEAR(dataPostagem) = YEAR(DATE_ADD(NOW(), INTERVAL -2 MONTH)))"
                        + " OR (MONTH(dataPostagem) = MONTH(DATE_ADD(NOW(), INTERVAL -3 MONTH)) AND YEAR(dataPostagem) = YEAR(DATE_ADD(NOW(), INTERVAL -3 MONTH))))"
                        + " AND codCliente = " + idCliente
                        + clausulaWhere
                        + " GROUP BY agrupamento"
                        + " ORDER BY dataPostagem";

        Connection conn = (Connection) Conexao.conectar(nomeBD);
        try {
            PreparedStatement valores = conn.prepareStatement(strQuery);
            ResultSet result = (ResultSet) valores.executeQuery();

            ArrayList lista = new ArrayList();

            for (int i = 0; result.next(); i++) {
                String datas = FormatarData.getNomeMes(result.getString("agrupamento"));
                String conta = result.getInt("qtd")+"";
                String conta2 = result.getString("vlr").trim();

                String[] obj = {conta, conta2, datas};
                lista.add(obj);
            }

            valores.close();
            return lista;

        } catch (SQLException e) {
            ContrErroLog.inserir("HOITO - contrRelatorios", "SQLException", strQuery, e.toString());
            return null;
        } finally {
            Conexao.desconectar(conn);
        }
    }

    public static String pesquisaSituacaoDoClientePorServico(String nomeBD, String idCliente, String servico, String data1, String data2, String departamento) throws ParseException {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date vdata1 = (java.util.Date) formatter.parse(data1);
        Date vdata2 = (java.util.Date) formatter.parse(data2);
        data1 = sdf.format(vdata1);
        data2 = sdf.format(vdata2);

        String strQuery = "SELECT t.last_status_name, t.last_status_code, t.last_status_type"
                + " FROM movimentacao"
                + " LEFT JOIN movimentacao_tracking AS t ON movimentacao.numObjeto = t.numObjeto"
                + " WHERE (dataPostagem BETWEEN '" + data1 + "' AND '" + data2 + "')"
                + " AND codCliente = " + idCliente + ""
                + " AND movimentacao.numObjeto <> '-'";

        if (!servico.equals("0")) {
            //strQuery += " AND descServico LIKE '%" + servico + "%'";
            strQuery += FormataString.montaWhereServicos(servico);
        }
        
        strQuery += departamento;

        //System.out.println(strQuery);
        
        Connection conn = (Connection) Conexao.conectar(nomeBD);
        try {
            PreparedStatement valores = conn.prepareStatement(strQuery);
            ResultSet result = (ResultSet) valores.executeQuery();

            int qtdEnc = 0, qtdPos = 0, qtdEnt = 0, qtdDev = 0, qtdExt = 0;
            for (int i = 0; result.next(); i++) {
                int codStatus = result.getInt("t.last_status_code");
                String codStatusType = result.getString("t.last_status_type");
                String status = result.getString("t.last_status_name");
                String grupoStatus = Util.Situacao.consultaGrupoStatusNovo(codStatus, codStatusType, status);
                if (grupoStatus.equals("POSTADO")) {
                    qtdPos++;
                } else if (grupoStatus.equals("ENTREGUE")) {
                    qtdEnt++;
                } else if (grupoStatus.equals("DEVOLVIDO")) {
                    qtdDev++;
                } else if (grupoStatus.equals("EXTRAVIADO")) {
                    qtdExt++;
                } else {
                    qtdEnc++;
                }

            }

            valores.close();
            String resultado = "['POSTADO'," + qtdPos + "],['ENTREGUE'," + qtdEnt + "],['EXTRAVIADO'," + qtdExt + "],['DEVOLVIDO'," + qtdDev + "],['ENCAMINHADO'," + qtdEnc + "]";
            return resultado;

        } catch (SQLException ex) {
            ContrErroLog.inserir("HOITO - contrRelatorios", "SQLException", strQuery, ex.toString());
            return null;
        } finally {
            Conexao.desconectar(conn);
        }
    }

    public static String pesquisaDespesasDoClientePorServico(String nomeBD, String idCliente, String departamento) throws ParseException {

        String strQuery = "SELECT descServico, SUM(quantidade) AS qtd, SUM(valorServico) AS vlr"
                + " FROM movimentacao"
                + " WHERE MONTH(dataPostagem) = MONTH(NOW())"
                + " AND codCliente = " + idCliente + "";

        if (!departamento.equals("0")) {
            strQuery += " AND departamento LIKE '%" + departamento + "%'";
        }
        strQuery += " GROUP BY descServico";

        Connection conn = (Connection) Conexao.conectar(nomeBD);
        try {
            PreparedStatement valores = conn.prepareStatement(strQuery);
            ResultSet result = (ResultSet) valores.executeQuery();

            String resultado = "";
            for (int i = 0; result.next(); i++) {
                String descServ = result.getString("descServico");
                int qtd = result.getInt("qtd");
                String vlr = result.getString("vlr");

                resultado += "['" + descServ + ": " + qtd + " Obj.'," + vlr + "],";
            }
            if(resultado.lastIndexOf(",") > 0){
                resultado = resultado.substring(0, resultado.lastIndexOf(","));
            }
            if(resultado.equals("")){
                resultado = "['Nenhuma Postagem Encontrada',0]";
            }

            valores.close();
            return resultado;
        } catch (SQLException ex) {
            ContrErroLog.inserir("HOITO - contrRelatorios", "SQLException", strQuery, ex.toString());
            return "";
        } finally {
            Conexao.desconectar(conn);
        }
    }

    /**
     * **************************************************************************
     */
    /**
     * **************************************************************************
     */
    /**
     * **************************************************************************
     */
    /**
     * **************************************************************************
     */


    public static ArrayList pesquisaSituacoesDoCliente(String nomeBD, String idCliente) {

        Connection conn = (Connection) Conexao.conectar(nomeBD);
        String strQuery = "select distinct status from movimentacao WHERE STATUS <> ' ' AND codCliente = " + idCliente + " ORDER BY status ;";

        try {
            PreparedStatement valores = conn.prepareStatement(strQuery);
            ResultSet result = (ResultSet) valores.executeQuery();

            ArrayList<String> lista = new ArrayList<String>();

            for (int i = 0; result.next(); i++) {
                String tipo = result.getString("status").trim();
                lista.add(tipo);
            }

            valores.close();
            return lista;

        } catch (SQLException ex) {
            ContrErroLog.inserir("HOITO - contrRelatorios", "SQLException", strQuery, ex.toString());
            return null;
        } finally {
            Conexao.desconectar(conn);
        }
    }

    public static ArrayList pesquisaTodosTipoPostagemDoCliente(String nomeBD, String idCliente) {

        Connection conn = (Connection) Conexao.conectar(nomeBD);
        String strQuery = "select distinct descServico from movimentacao WHERE codCliente = " + idCliente + " ORDER BY descServico ;";

        try {
            PreparedStatement valores = conn.prepareStatement(strQuery);
            ResultSet result = (ResultSet) valores.executeQuery();

            ArrayList<String> lista = new ArrayList<String>();

            for (int i = 0; result.next(); i++) {
                String tipo = result.getString("descServico").trim();
                lista.add(tipo);
            }

            valores.close();
            return lista;

        } catch (SQLException ex) {
            ContrErroLog.inserir("HOITO - contrRelatorios", "SQLException", strQuery, ex.toString());
            return null;
        } finally {
            Conexao.desconectar(conn);
        }
    }

    public static ArrayList pesquisaTodosTipoPostagemDoClienteComRastreio(String nomeBD, String idCliente) {

        Connection conn = (Connection) Conexao.conectar(nomeBD);
        String strQuery = "select distinct descServico from movimentacao WHERE numObjeto <> '-' AND codCliente = " + idCliente + " ORDER BY descServico ;";

        try {
            PreparedStatement valores = conn.prepareStatement(strQuery);
            ResultSet result = (ResultSet) valores.executeQuery();

            ArrayList<String> lista = new ArrayList<String>();

            for (int i = 0; result.next(); i++) {
                String tipo = result.getString("descServico").trim();
                lista.add(tipo);
            }

            valores.close();
            return lista;

        } catch (SQLException ex) {
            ContrErroLog.inserir("HOITO - contrRelatorios", "SQLException", strQuery, ex.toString());
            return null;
        } finally {
            Conexao.desconectar(conn);
        }
    }

    public static ArrayList pesquisaTodosDepartamentosDoCliente(String nomeBD, String idCliente) {

        Connection conn = (Connection) Conexao.conectar(nomeBD);
        String strQuery = "SELECT DISTINCT departamento FROM movimentacao WHERE departamento <> '' AND codCliente = " + idCliente + ";";

        try {
            PreparedStatement valores = conn.prepareStatement(strQuery);
            ResultSet result = (ResultSet) valores.executeQuery();

            ArrayList<String> lista = new ArrayList<String>();

            for (int i = 0; result.next(); i++) {
                String departamento = result.getString("departamento").trim();
                lista.add(departamento);
            }

            valores.close();
            return lista;

        } catch (SQLException ex) {
            System.out.println(ex);
            ContrErroLog.inserir("HOITO - contrRelatorios", "SQLException", strQuery, ex.toString());
            return null;
        } finally {
            Conexao.desconectar(conn);
        }
    }


    /**
     * ***************************** PESQUISAS DO EXTRATO
     * ******************************
     */
    public static ArrayList getMovimentacaoByIdEDatas(String dataInicio, String dataFinal, int idCliente, String nomeBD, int tipoPesq) {
        Connection conn = (Connection) Conexao.conectar(nomeBD);

        String sql = "SELECT descServico AS tipo, SUM(quantidade) AS qtd, SUM(valorServico) AS valor"
                + " FROM movimentacao"
                + " WHERE codCliente = ? AND (dataPostagem BETWEEN ? AND ?)"
                + " GROUP BY descServico;";
        if (tipoPesq == 2) {
            sql = "SELECT departamento AS tipo, SUM(quantidade) AS qtd, SUM(valorServico) AS valor"
                    + " FROM movimentacao"
                    + " WHERE codCliente = ? AND (dataPostagem BETWEEN ? AND ?)"
                    + " GROUP BY departamento;";
        }

        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            valores.setInt(1, idCliente);
            valores.setString(2, dataInicio);
            valores.setString(3, dataFinal);
            ResultSet result = (ResultSet) valores.executeQuery();

            ArrayList movimentacao = new ArrayList();

            for (int i = 0; result.next(); i++) {
                String valor = result.getString("valor");
                String qtd = result.getString("qtd");
                String tipo = result.getString("tipo");

                String[] obj = {tipo, valor, qtd};

                movimentacao.add(obj);
            }

            valores.close();
            return movimentacao;

        } catch (SQLException e) {
            ContrErroLog.inserir("HOITO - contrRelatorios", "SQLException", sql, e.toString());
            return null;
        } finally {
            Conexao.desconectar(conn);
        }
    }

    public static ArrayList getMovimentacaoByIdEDestinatario(int idCliente, String destinatario2, String nomeBD, int tipoPesq) {
        Connection conn = (Connection) Conexao.conectar(nomeBD);

        String sql = "SELECT descServico AS tipo, SUM(quantidade) AS qtd, SUM(valorServico) AS valor"
                + " FROM movimentacao"
                + " WHERE codCliente = " + idCliente + ""
                + " AND destinatario LIKE '%" + destinatario2 + "%'"
                + " GROUP BY descServico;";
        if (tipoPesq == 2) {
            sql = "SELECT departamento AS tipo, SUM(quantidade) AS qtd, SUM(valorServico) AS valor"
                    + " FROM movimentacao"
                    + " WHERE codCliente = " + idCliente + ""
                    + " AND destinatario LIKE '%" + destinatario2 + "%'"
                    + " GROUP BY departamento;";
        }

        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            ResultSet result = (ResultSet) valores.executeQuery();

            ArrayList movimentacao = new ArrayList();

            for (int i = 0; result.next(); i++) {
                String valor = result.getString("valor");
                String qtd = result.getString("qtd");
                String tipo = result.getString("tipo");

                String[] obj = {tipo, valor, qtd};

                movimentacao.add(obj);
            }

            valores.close();
            return movimentacao;

        } catch (SQLException e) {
            ContrErroLog.inserir("HOITO - contrRelatorios", "SQLException", sql, e.toString());
            return null;
        } finally {
            Conexao.desconectar(conn);
        }
    }

    public static ArrayList getMovimentacaoByIdECEP(int idCliente, String cep2, String nomeBD, int tipoPesq) {
        Connection conn = (Connection) Conexao.conectar(nomeBD);

        String sql = "SELECT descServico AS tipo, SUM(quantidade) AS qtd, SUM(valorServico) AS valor"
                + " FROM movimentacao "
                + " WHERE codCliente = " + idCliente + ""
                + " AND cep LIKE '%" + cep2 + "%'"
                + " GROUP BY descServico;";
        if (tipoPesq == 2) {
            sql = "SELECT departamento AS tipo, SUM(quantidade) AS qtd, SUM(valorServico) AS valor"
                    + " FROM movimentacao "
                    + " WHERE codCliente = " + idCliente + ""
                    + " AND cep LIKE '%" + cep2 + "%'"
                    + " GROUP BY departamento;";
        }

        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            ResultSet result = (ResultSet) valores.executeQuery();

            ArrayList movimentacao = new ArrayList();

            for (int i = 0; result.next(); i++) {
                String valor = result.getString("valor");
                String qtd = result.getString("qtd");
                String tipo = result.getString("tipo");

                String[] obj = {tipo, valor, qtd};

                movimentacao.add(obj);
            }

            valores.close();
            return movimentacao;

        } catch (SQLException e) {
            ContrErroLog.inserir("HOITO - contrRelatorios", "SQLException", sql, e.toString());
            return null;
        } finally {
            Conexao.desconectar(conn);
        }
    }

    public static ArrayList getMovimentacaoByNotaFiscal(int idCliente, String notaFiscal2, String nomeBD, int tipoPesq) {
        Connection conn = (Connection) Conexao.conectar(nomeBD);

        String sql = "SELECT descServico AS tipo, SUM(quantidade) AS qtd, SUM(valorServico) AS valor"
                + " FROM movimentacao "
                + " WHERE codCliente = " + idCliente + ""
                + " AND cep LIKE '%" + notaFiscal2 + "%'"
                + " GROUP BY descServico;";
        if (tipoPesq == 2) {
            sql = "SELECT departamento AS tipo, SUM(quantidade) AS qtd, SUM(valorServico) AS valor"
                    + " FROM movimentacao "
                    + " WHERE codCliente = " + idCliente + ""
                    + " AND cep LIKE '%" + notaFiscal2 + "%'"
                    + " GROUP BY departamento;";
        }

        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            ResultSet result = (ResultSet) valores.executeQuery();

            ArrayList movimentacao = new ArrayList();

            for (int i = 0; result.next(); i++) {
                String valor = result.getString("valor");
                String qtd = result.getString("qtd");
                String tipo = result.getString("tipo");

                String[] obj = {tipo, valor, qtd};

                movimentacao.add(obj);
            }

            valores.close();
            return movimentacao;

        } catch (SQLException e) {
            ContrErroLog.inserir("HOITO - contrRelatorios", "SQLException", sql, e.toString());
            return null;
        } finally {
            Conexao.desconectar(conn);
        }
    }

    public static ArrayList getMovimentacaoByObjeto(int idCliente, String objeto, String nomeBD, int tipoPesq) {
        Connection conn = (Connection) Conexao.conectar(nomeBD);

        String sql = "SELECT descServico AS tipo, SUM(quantidade) AS qtd, SUM(valorServico) AS valor"
                + " FROM movimentacao"
                + " WHERE codCliente = " + idCliente + " AND"
                + " numObjeto LIKE '%" + objeto + "%'"
                + " GROUP BY descServico;";
        if (tipoPesq == 2) {
            sql = "SELECT departamento AS tipo, SUM(quantidade) AS qtd, SUM(valorServico) AS valor"
                    + " FROM movimentacao"
                    + " WHERE codCliente = " + idCliente + " AND"
                    + " numObjeto LIKE '%" + objeto + "%'"
                    + " GROUP BY departamento;";
        }

        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            ResultSet result = (ResultSet) valores.executeQuery();

            ArrayList movimentacao = new ArrayList();

            for (int i = 0; result.next(); i++) {
                String valor = result.getString("valor");
                String qtd = result.getString("qtd");
                String tipo = result.getString("tipo");

                String[] obj = {tipo, valor, qtd};

                movimentacao.add(obj);
            }

            valores.close();
            return movimentacao;

        } catch (SQLException e) {
            ContrErroLog.inserir("HOITO - contrRelatorios", "SQLException", sql, e.toString());
            return null;
        } finally {
            Conexao.desconectar(conn);
        }
    }

    public static ArrayList getMovimentacaoByConteudo(int idCliente, String conteudo, String nomeBD, int tipoPesq) {
        Connection conn = (Connection) Conexao.conectar(nomeBD);

        String sql = "SELECT descServico AS tipo, SUM(quantidade) AS qtd, SUM(valorServico) AS valor"
                + " FROM movimentacao"
                + " WHERE codCliente = " + idCliente + " AND"
                + " conteudoObjeto LIKE '%" + conteudo + "%'"
                + " GROUP BY descServico;";
        if (tipoPesq == 2) {
            sql = "SELECT departamento AS tipo, SUM(quantidade) AS qtd, SUM(valorServico) AS valor"
                    + " FROM movimentacao"
                    + " WHERE codCliente = " + idCliente + " AND"
                    + " conteudoObjeto LIKE '%" + conteudo + "%'"
                    + " GROUP BY departamento;";
        }

        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            ResultSet result = (ResultSet) valores.executeQuery();

            ArrayList movimentacao = new ArrayList();

            for (int i = 0; result.next(); i++) {
                String valor = result.getString("valor");
                String qtd = result.getString("qtd");
                String tipo = result.getString("tipo");

                String[] obj = {tipo, valor, qtd};

                movimentacao.add(obj);
            }

            valores.close();
            return movimentacao;

        } catch (SQLException e) {
            ContrErroLog.inserir("HOITO - contrRelatorios", "SQLException", sql, e.toString());
            return null;
        } finally {
            Conexao.desconectar(conn);
        }
    }

    public static ArrayList getMovimentacaoByDepartamento(int idCliente, String departamento2, String nomeBD, int tipoPesq) {
        Connection conn = (Connection) Conexao.conectar(nomeBD);

        String sql = "SELECT descServico AS tipo, SUM(quantidade) AS qtd, SUM(valorServico) AS valor"
                + " FROM movimentacao"
                + " WHERE codCliente = " + idCliente + ""
                + " AND departamento LIKE '%" + departamento2 + "%'"
                + " GROUP BY descServico;";
        if (tipoPesq == 2) {
            sql = "SELECT departamento AS tipo, SUM(quantidade) AS qtd, SUM(valorServico) AS valor"
                    + " FROM movimentacao"
                    + " WHERE codCliente = " + idCliente + ""
                    + " AND departamento LIKE '%" + departamento2 + "%'"
                    + " GROUP BY departamento;";
        }

        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            ResultSet result = (ResultSet) valores.executeQuery();

            ArrayList movimentacao = new ArrayList();

            for (int i = 0; result.next(); i++) {
                String valor = result.getString("valor");
                String qtd = result.getString("qtd");
                String tipo = result.getString("tipo");

                String[] obj = {tipo, valor, qtd};

                movimentacao.add(obj);
            }

            valores.close();
            return movimentacao;

        } catch (SQLException e) {
            ContrErroLog.inserir("HOITO - contrRelatorios", "SQLException", sql, e.toString());
            return null;
        } finally {
            Conexao.desconectar(conn);
        }
    }

    public static ArrayList getMovimentacaoByServico(int idCliente, String servico, String nomeBD, int tipoPesq) {
        Connection conn = (Connection) Conexao.conectar(nomeBD);

        String sql = "SELECT descServico AS tipo, SUM(quantidade) AS qtd, SUM(valorServico) AS valor"
                + " FROM movimentacao"
                + " WHERE codCliente = " + idCliente + ""
                + " AND descServico LIKE '" + servico + "'"
                + " GROUP BY descServico;";
        if (tipoPesq == 2) {
            sql = "SELECT departamento AS tipo, SUM(quantidade) AS qtd, SUM(valorServico) AS valor"
                    + " FROM movimentacao"
                    + " WHERE codCliente = " + idCliente + ""
                    + " AND descServico LIKE '" + servico + "'"
                    + " GROUP BY departamento;";
        }

        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            ResultSet result = (ResultSet) valores.executeQuery();

            ArrayList movimentacao = new ArrayList();

            for (int i = 0; result.next(); i++) {
                String valor = result.getString("valor");
                String qtd = result.getString("qtd");
                String tipo = result.getString("tipo");

                String[] obj = {tipo, valor, qtd};

                movimentacao.add(obj);
            }

            valores.close();
            return movimentacao;

        } catch (SQLException e) {
            ContrErroLog.inserir("HOITO - contrRelatorios", "SQLException", sql, e.toString());
            return null;
        } finally {
            Conexao.desconectar(conn);
        }
    }

    public static ArrayList getMovimentacaoBySituacao(int idCliente, String situacao, String nomeBD, int tipoPesq) {
        Connection conn = (Connection) Conexao.conectar(nomeBD);

        String sql = "SELECT descServico AS tipo, SUM(quantidade) AS qtd, SUM(valorServico) AS valor"
                + " FROM movimentacao"
                + " WHERE codCliente = " + idCliente + ""
                + " AND status LIKE '" + situacao + "'"
                + " GROUP BY descServico;";
        if (tipoPesq == 2) {
            sql = "SELECT departamento AS tipo, SUM(quantidade) AS qtd, SUM(valorServico) AS valor"
                    + " FROM movimentacao"
                    + " WHERE codCliente = " + idCliente + ""
                    + " AND status LIKE '" + situacao + "'"
                    + " GROUP BY departamento;";
        }

        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            ResultSet result = (ResultSet) valores.executeQuery();

            ArrayList movimentacao = new ArrayList();

            for (int i = 0; result.next(); i++) {
                String valor = result.getString("valor");
                String qtd = result.getString("qtd");
                String tipo = result.getString("tipo");

                String[] obj = {tipo, valor, qtd};

                movimentacao.add(obj);
            }

            valores.close();
            return movimentacao;

        } catch (SQLException e) {
            ContrErroLog.inserir("HOITO - contrRelatorios", "SQLException", sql, e.toString());
            return null;
        } finally {
            Conexao.desconectar(conn);
        }
    }

    public static ArrayList getMovimentacaoPesquisaCustomizada(String dataInicio, String dataFinal, String situacao, String servico, String departamento, int idCliente, String nomeBD, int tipoPesq) {
        Connection conn = (Connection) Conexao.conectar(nomeBD);

        String sql = "SELECT descServico AS tipo, SUM(quantidade) AS qtd, SUM(valorServico) AS valor"
                + " FROM movimentacao"
                + " WHERE codCliente = ?"
                + " AND (dataPostagem BETWEEN ? AND ?)";
        if (!situacao.equals("0")) {
            sql += " AND status LIKE '" + situacao + "'";
        }
        if (!servico.equals("0")) {
            sql += " AND descServico LIKE '" + servico + "'";
        }
        if (!departamento.equals("0")) {
            sql += " AND departamento LIKE '%" + departamento + "%'";
        }
        sql += " GROUP BY descServico;";

        if (tipoPesq == 2) {
            sql = "SELECT departamento AS tipo, SUM(quantidade) AS qtd, SUM(valorServico) AS valor"
                    + " FROM movimentacao"
                    + " WHERE codCliente = ?"
                    + " AND (dataPostagem BETWEEN ? AND ?)";
            if (!situacao.equals("0")) {
                sql += " AND status LIKE '" + situacao + "'";
            }
            if (!servico.equals("0")) {
                sql += " AND descServico LIKE '" + servico + "'";
            }
            if (!departamento.equals("0")) {
                sql += " AND departamento LIKE '%" + departamento + "%'";
            }
            sql += " GROUP BY departamento;";
        }

        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            valores.setInt(1, idCliente);
            valores.setString(2, dataInicio);
            valores.setString(3, dataFinal);
            ResultSet result = (ResultSet) valores.executeQuery();

            ArrayList movimentacao = new ArrayList();

            for (int i = 0; result.next(); i++) {
                String valor = result.getString("valor");
                String qtd = result.getString("qtd");
                String tipo = result.getString("tipo");

                String[] obj = {tipo, valor, qtd};

                movimentacao.add(obj);
            }

            valores.close();
            return movimentacao;

        } catch (SQLException e) {
            ContrErroLog.inserir("HOITO - contrRelatorios", "SQLException", sql, e.toString());
            return null;
        } finally {
            Conexao.desconectar(conn);
        }
    }

    public static ArrayList getMovimentacaoPesquisaCustomizadaCompl(String dataInicio, String dataFinal, String situacao, String servico, String departamento, int idCliente, int valorCobrar, int valorDeclarado2, int ar, String nomeBD, int tipoPesq) {
        Connection conn = (Connection) Conexao.conectar(nomeBD);

        String sql = "SELECT descServico AS tipo, SUM(quantidade) AS qtd, SUM(valorServico) AS valor"
                + " FROM movimentacao"
                + " WHERE codCliente = ?"
                + " AND (dataPostagem BETWEEN ? AND ?)";
        if (!situacao.equals("0")) {
            sql += " AND status LIKE '" + situacao + "'";
        }
        if (!servico.equals("0")) {
            sql += " AND descServico LIKE '" + servico + "'";
        }
        if (!departamento.equals("0")) {
            sql += " AND departamento LIKE '%" + departamento + "%'";
        }
        if (valorCobrar == 1) {
            sql += " AND valorDestino > 0";
        }
        if (valorDeclarado2 == 1) {
            sql += " AND valorDeclarado > 0";
        }
        if (ar == 1) {
            sql += " AND servicoAdicional LIKE '%A. R.%'";
        }
        sql += " GROUP BY descServico;";

        if (tipoPesq == 2) {
            sql = "SELECT departamento AS tipo, SUM(quantidade) AS qtd, SUM(valorServico) AS valor"
                    + " FROM movimentacao"
                    + " WHERE codCliente = ?"
                    + " AND (dataPostagem BETWEEN ? AND ?)";
            if (!situacao.equals("0")) {
                sql += " AND status LIKE '" + situacao + "'";
            }
            if (!servico.equals("0")) {
                sql += " AND descServico LIKE '" + servico + "'";
            }
            if (!departamento.equals("0")) {
                sql += " AND departamento LIKE '%" + departamento + "%'";
            }
            if (valorCobrar == 1) {
                sql += " AND valorDestino > 0";
            }
            if (valorDeclarado2 == 1) {
                sql += " AND valorDeclarado > 0";
            }
            if (ar == 1) {
                sql += " AND servicoAdicional LIKE '%A. R.%'";
            }
            sql += " GROUP BY departamento;";
        }

        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            valores.setInt(1, idCliente);
            valores.setString(2, dataInicio);
            valores.setString(3, dataFinal);
            ResultSet result = (ResultSet) valores.executeQuery();

            ArrayList movimentacao = new ArrayList();

            for (int i = 0; result.next(); i++) {
                String valor = result.getString("valor");
                String qtd = result.getString("qtd");
                String tipo = result.getString("tipo");

                String[] obj = {tipo, valor, qtd};

                movimentacao.add(obj);
            }

            valores.close();
            return movimentacao;

        } catch (SQLException e) {
            ContrErroLog.inserir("HOITO - contrRelatorios", "SQLException", sql, e.toString());
            return null;
        } finally {
            Conexao.desconectar(conn);
        }
    }

    /**
     * ***************************** PESQUISAS DO EXTRATO
     * ******************************
     */
    public static ArrayList pesquisaRelatorio(String dataInicio, String dataFinal, String ordenacao, String agrupamento, int idCliente, String whereDeptos, String nomeBD) {
        Connection conn = (Connection) Conexao.conectar(nomeBD);

        String sql = "", select = "", agrup = "", ordem = "";
        if (agrupamento.equals("deptoServ")) {
            select = "CONCAT(departamento, ' - ', descServico)";
            agrup = "idDepartamento, codigoEct";
            ordem = "departamento";
        } else if (agrupamento.equals("0")) {
            if (ordenacao.equals("0")) {
                select = "dataPostagem";
                agrup = "dataPostagem";
                ordem = "dataPostagem";
            } else {
                select = "dataPostagem";
                agrup = "dataPostagem";
                ordem = ordenacao;
            }
        } else {
            if (ordenacao.equals("0")) {
                select = agrupamento;
                agrup = agrupamento;
                ordem = agrupamento;
            } else {
                select = agrupamento;
                agrup = agrupamento;
                ordem = ordenacao;
            }
        }
        if (agrupamento.equals("tipo")) {
            select = "CASE "
                    + "WHEN (cep=0) THEN 'INT' "
                    + "WHEN (cep>0 AND cep<=19999999) THEN 'SP' "
                    + "WHEN (cep>=20000000 AND cep<=28999999) THEN 'RJ' "
                    + "WHEN (cep>=29000000 AND cep<=29999999) THEN 'ES' "
                    + "WHEN (cep>=30000000 AND cep<=39999999) THEN 'MG' "
                    + "WHEN (cep>=40000000 AND cep<=48999999) THEN 'BA' "
                    + "WHEN (cep>=49000000 AND cep<=49999999) THEN 'SE' "
                    + "WHEN (cep>=50000000 AND cep<=56999999) THEN 'PE' "
                    + "WHEN (cep>=57000000 AND cep<=57999999) THEN 'AL' "
                    + "WHEN (cep>=58000000 AND cep<=58999999) THEN 'PB' "
                    + "WHEN (cep>=59000000 AND cep<=59999999) THEN 'RN' "
                    + "WHEN (cep>=60000000 AND cep<=63999999) THEN 'CE' "
                    + "WHEN (cep>=64000000 AND cep<=64999999) THEN 'PI' "
                    + "WHEN (cep>=65000000 AND cep<=65999999) THEN 'MA' "
                    + "WHEN (cep>=66000000 AND cep<=68899999) THEN 'PA' "
                    + "WHEN (cep>=68900000 AND cep<=68999999) THEN 'AP' "
                    + "WHEN (cep>=69000000 AND cep<=69299999) THEN 'AM' "
                    + "WHEN (cep>=69300000 AND cep<=69389999) THEN 'RR' "
                    + "WHEN (cep>=69400000 AND cep<=69899999) THEN 'AM' "
                    + "WHEN (cep>=69900000 AND cep<=69999999) THEN 'AC' "
                    + "WHEN (cep>=70000000 AND cep<=73699999) THEN 'DF' "
                    + "WHEN (cep>=72800000 AND cep<=76799999) THEN 'GO' "
                    + "WHEN (cep>=76800000 AND cep<=76999999) THEN 'RO' "
                    + "WHEN (cep>=77000000 AND cep<=77995999) THEN 'TO' "
                    + "WHEN (cep>=78000000 AND cep<=78899999) THEN 'MT' "
                    + "WHEN (cep>=78900000 AND cep<=78999999) THEN 'RO' "
                    + "WHEN (cep>=79000000 AND cep<=79999999) THEN 'MS' "
                    + "WHEN (cep>=80000000 AND cep<=87999999) THEN 'PR' "
                    + "WHEN (cep>=88000000 AND cep<=89999999) THEN 'SC' "
                    + "WHEN (cep>=90000000 AND cep<=99999999) THEN 'RS' "
                    + "ELSE '--' "
                    + "END";
        }
        sql = "SELECT " + select + " AS tipo, SUM(quantidade) AS qtd, SUM(valorServico) AS valor"
                + " FROM movimentacao"
                + " WHERE codCliente = ? AND (dataPostagem BETWEEN ? AND ?) "
                + whereDeptos
                + " GROUP BY " + agrup
                + " ORDER BY " + ordem + " ;";
        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            valores.setInt(1, idCliente);
            valores.setString(2, dataInicio);
            valores.setString(3, dataFinal);
            ResultSet result = (ResultSet) valores.executeQuery();

            ArrayList movimentacao = new ArrayList();

            for (int i = 0; result.next(); i++) {
                String valor = result.getString("valor");
                String qtd = result.getString("qtd");
                String tipo = result.getString("tipo");

                String[] obj = {tipo, valor, qtd};

                movimentacao.add(obj);
            }

            valores.close();
            return movimentacao;

        } catch (SQLException e) {
            ContrErroLog.inserir("HOITO - contrRelatorios", "SQLException", sql, e.toString());
            return null;
        } finally {
            Conexao.desconectar(conn);
        }
    }

    public static ArrayList getMovimentacaoByAR(String dataInicio, String dataFinal, int idCliente, String nomeBD) {
        Connection conn = (Connection) Conexao.conectar(nomeBD);

        String sql = "SELECT count(baixaAr) AS qtd, baixaAr"
                + " FROM movimentacao"
                + " WHERE codCliente = ?"
                + " AND servicoAdicional LIKE '%A. R.%'"
                + " AND (dataPostagem BETWEEN ? AND ?)"
                + " GROUP BY baixaAr";

        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            valores.setInt(1, idCliente);
            valores.setString(2, dataInicio);
            valores.setString(3, dataFinal);
            ResultSet result = (ResultSet) valores.executeQuery();

            ArrayList movimentacao = new ArrayList();

            for (int i = 0; result.next(); i++) {
                String qtd = result.getString("qtd");
                String baixaAr = result.getString("baixaAr");
                movimentacao.add(qtd + ";" + baixaAr);
            }

            valores.close();
            return movimentacao;

        } catch (SQLException e) {
            ContrErroLog.inserir("HOITO - contrRelatorios", "SQLException", sql, e.toString());
            return null;
        } finally {
            Conexao.desconectar(conn);
        }
    }
}
