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
            
            String idVendedor = request.getParameter("idVendedor");

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
                    sql = "SELECT codCliente AS COD, nome AS NOME_CLIENTE, "
                            + " CONCAT('R$ ', REPLACE(REPLACE(REPLACE(FORMAT(SUM(valorServico), 2),'.',';'),',','.'),';',',')) AS $TOTAL, "
                            + " CONCAT(percentual, ' %') AS PERCENTUAL, "
                            + " CONCAT('R$ ', REPLACE(REPLACE(REPLACE(FORMAT((SUM(valorServico) * (percentual/100)), 2),'.',';'),',','.'),';',',')) AS $COMISSAO "
                            + " FROM movimentacao"
                            + " LEFT JOIN cliente ON codCliente = codigo JOIN vendedor_cliente ON codCliente = idCliente "
                            + " WHERE dataPostagem >= '" + dataIni + "' "
                            + " AND dataPostagem <= '" + dataFim + "' "
                            + " AND idVendedor = " + idVendedor + " "
                            + " GROUP BY codCliente "
                            + " UNION"
                            + " SELECT ' ', 'TOTAL GERAL', "
                            + " CONCAT('R$ ', REPLACE(REPLACE(REPLACE(FORMAT(SUM(valorServico), 2),'.',';'),',','.'),';',',')), "
                            + " ' ', "
                            + " CONCAT('R$ ', REPLACE(REPLACE(REPLACE(FORMAT((SUM(valorServico) * (percentual/100)), 2),'.',';'),',','.'),';',',')) "
                            + " FROM movimentacao "
                            + " JOIN vendedor_cliente ON codCliente = idCliente"
                            + " WHERE dataPostagem >= '" + dataIni + "' "
                            + " AND dataPostagem <= '" + dataFim + "' "
                            + " AND idVendedor = " + idVendedor + " "                            
                            + " ;";
                    break;
                case "2":
                    sql = "SELECT codCliente AS COD, nome AS NOME_CLIENTE,"
                            + " descServico AS SERVICO, "
                            + " CONCAT('R$ ', REPLACE(REPLACE(REPLACE(FORMAT(SUM(valorServico), 2),'.',';'),',','.'),';',',')) AS $TOTAL, "
                            + " CONCAT(percentual, ' %') AS PERCENTUAL, "
                            + " CONCAT('R$ ', REPLACE(REPLACE(REPLACE(FORMAT((SUM(valorServico) * (percentual/100)), 2),'.',';'),',','.'),';',',')) AS $COMISSAO  "
                            + " FROM movimentacao"
                            + " LEFT JOIN cliente ON codCliente = codigo  JOIN vendedor_cliente ON codCliente = idCliente "
                            + " WHERE dataPostagem >= '" + dataIni + "' "
                            + " AND dataPostagem <= '" + dataFim + "' "
                            + " AND idVendedor = " + idVendedor + " "
                            + " GROUP BY codCliente, descServico  "
                            + " UNION"
                            + " SELECT '' , 'TOTAL',"
                            + " 'SERVICO',"
                            + " CONCAT('R$ ', REPLACE(REPLACE(REPLACE(FORMAT(SUM(valorServico), 2),'.',';'),',','.'),';',',')) AS $TOTAL,"
                            + " '', "
                            + " CONCAT('R$ ', REPLACE(REPLACE(REPLACE(FORMAT((SUM(valorServico) * (percentual/100)), 2),'.',';'),',','.'),';',',')) AS $COMISSAO"
                            + " FROM movimentacao"
                            + " LEFT JOIN cliente ON codCliente = codigo  JOIN vendedor_cliente ON codCliente = idCliente"
                            + " WHERE dataPostagem >= '" + dataIni + "' "
                            + " AND dataPostagem <= '" + dataFim + "'  "
                            + " AND idVendedor = " + idVendedor + " ;";
                    break;


                default:
                    sql = "";

            }
           System.out.println(sql);
            String jsn = Controle.contrRelatorios.montaJson(sql, sdf, nomeBd);


%><%=jsn%>
<%} catch (Exception ex) {
           // System.out.println(ex);

        }
    }%>