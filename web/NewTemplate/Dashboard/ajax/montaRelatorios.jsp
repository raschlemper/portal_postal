<%@page import="java.text.DateFormat"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.util.Date"%>
<%@page import="Entidade.empresas"%>
<%@page import="java.text.SimpleDateFormat"%>
<%

    if (session.getAttribute("agf_usuario") == null) {
        response.sendRedirect("../../index.jsp?msgLog=3");
    } else {
        try {

            empresas agf_empresa = (empresas) session.getAttribute("agf_empresa");

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat sdfBd = new SimpleDateFormat("yyyy-MM-dd");
            DateFormat df = sdf;
            String dtI = request.getParameter("dataI");
            String dtF = request.getParameter("dataF");

            String nomeBd = agf_empresa.getCnpj();
            // String nomeBd = "06895434000183";
            String sql = request.getParameter("sql");
            String dataIni = sdfBd.format(df.parse(dtI));
            String dataFim = sdfBd.format(df.parse(dtF));

            Calendar cal = Calendar.getInstance();
            int esteAno = cal.get(Calendar.YEAR);
            int esteMes = cal.get(Calendar.MONTH) + 1;
            int esteDia = cal.get(Calendar.DAY_OF_MONTH);
            cal.add(Calendar.MONTH, -1);
            int termMes = cal.get(Calendar.MONTH) + 1;
            int terAno = cal.get(Calendar.YEAR);
            cal.add(Calendar.MONTH, -1);
            int segMes = cal.get(Calendar.MONTH) + 1;
            int segAno = cal.get(Calendar.YEAR);
            cal.add(Calendar.MONTH, -1);
            int primMes = cal.get(Calendar.MONTH) + 1;
            int priAno = cal.get(Calendar.YEAR);

            switch (sql) {
                case "1":
                    sql = "SELECT codCliente AS COD, nome AS NOME_CLIENTE,"
                            + " CONCAT('R$ ', REPLACE(REPLACE(REPLACE(FORMAT(SUM(valorServico), 2),'.',';'),',','.'),';',',')) AS $TOTAL "
                            + " FROM movimentacao"
                            + " LEFT JOIN cliente ON codCliente = codigo"
                            + " WHERE dataPostagem >= '" + dataIni + "' "
                            + " AND dataPostagem <= '" + dataFim + "' "
                            + " GROUP BY codCliente "
                            + " ORDER BY $TOTAL; ";
                    break;
                case "2":
                    sql = "SELECT m.codCliente AS COD, nome AS NOME_CLIENTE, "
                            + " REPLACE(REPLACE(REPLACE(FORMAT(SUM(CASE WHEN MONTH(dataPostagem) = " + primMes + " THEN valorServico ELSE 0 END), 2),'.',';'),',','.'),';',',') AS " + "$" + Util.FormatarData.nomeMes(primMes) + ","
                            + " REPLACE(REPLACE(REPLACE(FORMAT(SUM(CASE WHEN MONTH(dataPostagem) = " + segMes + " THEN valorServico ELSE 0 END), 2),'.',';'),',','.'),';',',') AS " + "$" + Util.FormatarData.nomeMes(segMes) + ","
                            + " REPLACE(REPLACE(REPLACE(FORMAT(SUM(CASE WHEN MONTH(dataPostagem) = " + termMes + " THEN valorServico ELSE 0 END), 2),'.',';'),',','.'),';',',') AS " + "$" + Util.FormatarData.nomeMes(termMes) + ","
                            + " REPLACE(REPLACE(REPLACE(FORMAT(SUM(CASE WHEN MONTH(dataPostagem) = " + esteMes + " THEN valorServico ELSE 0 END), 2),'.',';'),',','.'),';',',') AS " + "$" + Util.FormatarData.nomeMes(esteMes) + ","
                            + " REPLACE(REPLACE(REPLACE(FORMAT(SUM(COALESCE(valorServico,0)), 2),'.',';'),',','.'),';',',') AS $TOTAL"
                            + " FROM movimentacao AS m"
                            + " LEFT JOIN cliente ON codigo = codCliente"
                            + " WHERE dataPostagem >= '" + priAno + "-" + primMes + "-01' AND  dataPostagem <= '" + esteAno + "-" + esteMes + "-31' "
                            + " AND nome IS NOT NULL"
                            + " GROUP BY codCliente"
                            + " ORDER BY nome; ";
                    break;

                case "3":
                    sql = "SELECT codigoECT AS COD_ECT, descServico AS SERVICO, SUM(quantidade) AS QTD , "
                            + " REPLACE(REPLACE(REPLACE(FORMAT(SUM(valorServico), 2),'.',';'),',','.'),';',',') AS $TOTAL  "
                            + " FROM movimentacao"
                            + " WHERE dataPostagem >= '" + dataIni + "' "
                            + " AND dataPostagem <= '" + dataFim + "' "
                            + " GROUP BY codigoECT"
                            + " ORDER BY $TOTAL; ";
                    break;

                case "4":
                    sql = "SELECT codigoECT AS COD_ECT, descServico AS SERVICO, "
                            + " REPLACE(REPLACE(REPLACE(FORMAT(SUM(CASE WHEN MONTH(dataPostagem) = " + primMes + " THEN valorServico ELSE 0 END), 2),'.',';'),',','.'),';',',') AS " + "$" + Util.FormatarData.nomeMes(primMes) + ","
                            + " REPLACE(REPLACE(REPLACE(FORMAT(SUM(CASE WHEN MONTH(dataPostagem) = " + segMes + " THEN valorServico ELSE 0 END), 2),'.',';'),',','.'),';',',') AS " + "$" + Util.FormatarData.nomeMes(segMes) + ","
                            + " REPLACE(REPLACE(REPLACE(FORMAT(SUM(CASE WHEN MONTH(dataPostagem) = " + termMes + " THEN valorServico ELSE 0 END), 2),'.',';'),',','.'),';',',') AS " + "$" + Util.FormatarData.nomeMes(termMes) + ","
                            + " REPLACE(REPLACE(REPLACE(FORMAT(SUM(CASE WHEN MONTH(dataPostagem) = " + esteMes + " THEN valorServico ELSE 0 END), 2),'.',';'),',','.'),';',',') AS " + "$" + Util.FormatarData.nomeMes(esteMes) + ","
                            + " REPLACE(REPLACE(REPLACE(FORMAT(SUM(COALESCE(valorServico,0)), 2),'.',';'),',','.'),';',',') AS $TOTAL"
                            + " FROM movimentacao"
                            + " WHERE dataPostagem >= '" + priAno + "-" + primMes + "-01' AND  dataPostagem <= '" + esteAno + "-" + esteMes + "-31' "
                            + " GROUP BY codigoECT; ";
                    break;

                case "5":
                    sql = "SELECT codCliente AS COD, nome AS NOME_CLIENTE, "
                            + " REPLACE(REPLACE(REPLACE(FORMAT(SUM(valorServico), 2),'.',';'),',','.'),';',',') AS $TOTAL "
                            + " FROM movimentacao"
                            + " LEFT JOIN cliente ON codCliente = codigo"
                            + " WHERE dataPostagem >= '" + dataIni + "' "
                            + " AND dataPostagem <= '" + dataFim + "' "
                            + " AND (contratoEct = '' OR contratoEct = 0) "
                            + " GROUP BY codCliente "
                            + " UNION"
                            + " SELECT ' ', 'TOTAL GERAL', "
                            + " REPLACE(REPLACE(REPLACE(FORMAT(SUM(valorServico), 2),'.',';'),',','.'),';',',')"
                            + " FROM movimentacao"
                            + " WHERE dataPostagem >= '" + dataIni + "' "
                            + " AND dataPostagem <= '" + dataFim + "' "
                            + " AND (contratoEct = '' OR contratoEct = 0); ";
                    break;

                case "6":
                    sql = "SELECT codCliente AS COD, nome AS NOME_CLIENTE,"
                            + " REPLACE(REPLACE(REPLACE(FORMAT(SUM(valorServico), 2),'.',';'),',','.'),';',',') AS $TOTAL "
                            + " FROM movimentacao"
                            + " LEFT JOIN cliente ON codCliente = codigo"
                            + " WHERE dataPostagem >= '" + dataIni + "' "
                            + " AND dataPostagem <= '" + dataFim + "' "
                            + " AND contratoEct <> '' AND contratoEct <> 0 "
                            + " GROUP BY codCliente"
                            + " UNION"
                            + " SELECT ' ', 'TOTAL GERAL', "
                            + " REPLACE(REPLACE(REPLACE(FORMAT(SUM(valorServico), 2),'.',';'),',','.'),';',',')"
                            + " FROM movimentacao"
                            + " WHERE dataPostagem >= '" + dataIni + "' "
                            + " AND dataPostagem <= '" + dataFim + "' "
                            + " AND contratoEct <> '' AND contratoEct <> 0; ";
                    break;

                case "7":
                    sql = "SELECT CONCAT('<a href=\"#\" onclick= pesqSro(\"',numObjeto,'\");>', numObjeto, '</a>') "
                            + " AS SRO, "
                            + " nome AS CLIENTE, "
                            + " cep_destino AS CEP, "
                            + " data_postagem AS DATA_POSTAGEM, "
                            + " prazo_estimado AS PRAZO_ECT,"
                            + " last_status_date AS ULTIMO_EVENTO "
                            + " FROM movimentacao_tracking "
                            + " LEFT JOIN cliente "
                            + " ON idCliente = codigo "
                            + " WHERE prazo_cumprido > prazo_estimado; ";
                    break;

                default:
                    sql = "";

            }
           //System.out.println(sql);
            String jsn = Controle.contrRelatorios.montaJson(sql, sdf, nomeBd);


%><%=jsn%>
<%} catch (Exception ex) {
           // System.out.println(ex);

        }
    }%>