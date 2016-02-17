<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.sql.*, javax.swing.*, java.util.*, java.text.*, java.util.Date" %>
<%
            response.setContentType("text/xml");
            response.setHeader("Cache-Control", "no-cache");
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            DecimalFormat df = new DecimalFormat("###,###");

            String nomeBD = (String) session.getAttribute("nomeBD");
            if (nomeBD != null) {

                int idCliente = Integer.parseInt(request.getParameter("idCliente"));
                String dataInicio = request.getParameter("inicio");
                String dataFinal = request.getParameter("final");
                String vDataInicio = Util.FormatarData.DateToBD(dataInicio);
                String vDataFinal = Util.FormatarData.DateToBD(dataFinal);
                String agrupamento = request.getParameter("agrupamento");
                String ordenacao = request.getParameter("ordenacao");
                
                int nivelUser = (Integer) session.getAttribute("nivelUsuarioEmp");
                String deptosWhere = "";
                if(nivelUser > 1){
                    ArrayList<Integer> deptosUser = (ArrayList<Integer>) session.getAttribute("departamentos");
                    for(int i=0; i<deptosUser.size(); i++){
                        deptosWhere += ", " + deptosUser.get(i);
                    }
                    deptosWhere = " AND idDepartamento IN ("+ deptosWhere.substring(1)+") ";
                }

                ArrayList movimentacao = Emporium.Controle.ContrRelatorios.pesquisaRelatorio(vDataInicio, vDataFinal, ordenacao, agrupamento, idCliente, deptosWhere, nomeBD);
                float total = 0, totalQtd = 0;
                String sql = "";
                if (movimentacao.size() >= 1) {
%>

<%--
<div style="padding:8px 5px; background: white;">
    <a target="_blank" href="../AjaxPages/print_sintetico.jsp?sql=<%= sql%>"><img class="link_img" src="../../imagensNew/printer.png" /> IMPRIMIR</a>
    <b style="margin:0 12px 0 10px;">|</b>
    <a target="_blank" href="../AjaxPages/xls_sintetico.jsp?sql=<%= sql%>"><img class="link_img" src="../../imagensNew/excel.png" /> EXPORTAR .XLS</a>
    <b style="margin:0 12px 0 10px;">|</b>
    <a target="_blank" href="../AjaxPages/csv_sintetico.jsp?sql=<%= sql%>"><img class="link_img" src="../../imagensNew/csv.png" /> EXPORTAR .CSV</a>
    <b style="margin:0 12px 0 10px;">|</b>
    <a target="_blank" href="../AjaxPages/pdf_sintetico.jsp?sql=<%= sql%>"><img class="link_img" src="../../imagensNew/pdf.png" /> EXPORTAR .PDF</a>
</div>
--%>

<div style="text-align: center; font-size: 18px; background: #f0f0f0; width: 100%; color: #b02c2c; padding:5px; margin-top: 15px;">
    <b>Relatório de <%= dataInicio %> até <%= dataFinal %></b>
</div>
<table width='100%' align='center' cellspacing='0' class="tb1">
    <thead>
        <tr>
            <th align ='left'>&nbsp;AGRUPAMENTO</th>
            <th align ='right'>QUANTIDADE</th>
            <th align ='right'>VALOR</th>
        </tr>
    </thead>
    <tbody>
        <%
                            for (int i = 0; i < movimentacao.size(); i++) {
                                String[] mov = (String[]) movimentacao.get(i);

                                String servicoTabela = mov[0];
                                if (agrupamento.equals("0")) {
                                    DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                                    Date vdata1 = (java.util.Date) formatter.parse(servicoTabela);
                                    servicoTabela = sdf.format(vdata1);
                                }
                                if (agrupamento.equals("MONTH(dataPostagem)")) {
                                    servicoTabela = Util.FormatarData.getNomeMes(servicoTabela);
                                }

                                float qtd = Float.parseFloat(mov[2]);
                                totalQtd += qtd;

                                float valor = Float.parseFloat(mov[1]);
                                total += valor;
        %>
        <tr>
            <td align='left'><%= servicoTabela%></td>
            <td align ='right'><%= df.format(qtd)%> Objetos</td>
            <td align ='right'>R$ <%= Util.FormatarDecimal.formatarFloat(valor)%></td>
        </tr>
        <%}%>
    </tbody>
    <tfoot style="background: #f0f0f0; color:red; font-size: 12px;">
        <tr>
            <td></td>
            <td align ='right'><b>TOTAL = <%= df.format(totalQtd)%> Objetos</b></td>
            <td align ='right'><b>TOTAL = R$ <%= Util.FormatarDecimal.formatarFloat(total)%></b></td>
        </tr>
    </tfoot>
</table>
<%} else {%>
<div align='center' style='padding-top:25px;background:#fc8878;color:black;height:50px;width:100%;font-size:20px;font-weight:bold;'>Nenhum Dado Encontrado!</div>
<%}%>
<%}else{%>
sessaoexpirada
<%}%>