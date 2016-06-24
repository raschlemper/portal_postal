<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.ArrayList"%>
<%
    int qtdNao = 0, qtdSim = 0;
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    String nomeBD = (String) session.getAttribute("nomeBD");
    if (nomeBD != null) {

        String numCliente = String.valueOf(session.getAttribute("idCliente"));
        int idCliente = Integer.parseInt(numCliente);
        int ar = Integer.parseInt(request.getParameter("ar"));
        String vDepartamento = request.getParameter("departamento");
        String dataInicio = Util.FormatarData.DateToBD(request.getParameter("dataIni"));
        String dataFinal = Util.FormatarData.DateToBD(request.getParameter("dataFim"));
        
        String sql = "";
        if (!vDepartamento.equals("0")) {
            sql += " AND m.departamento LIKE '" + vDepartamento + "'";
        }else{
            ArrayList<Integer> dptosSessaoUsuario = (ArrayList<Integer>) session.getAttribute("departamentos");
            if (dptosSessaoUsuario != null && dptosSessaoUsuario.size() > 0) {
                String idsDepto = "";
                for (Integer idDep : dptosSessaoUsuario) {
                    idsDepto += idDep + ",";
                }
                if (!idsDepto.equals("")) {
                    idsDepto = idsDepto.substring(0, idsDepto.lastIndexOf(","));
                    sql += " AND m.idDepartamento IN (" + idsDepto + ") ";
                }
            }
        }

        ArrayList movimentacao = Controle.contrMovimentacao.getMovimentacaoAR(dataInicio, dataFinal, idCliente, ar, sql, nomeBD);
        if (movimentacao.size() > 0) {
%>


<div style="padding:8px 5px; background: white;">
    <%--<a target="_blank" href="../AjaxPages/print_analitico.jsp?sql=<%= sql%>"><img class="link_img" src="../../imagensNew/printer.png" /> IMPRIMIR</a>
    <b style="margin:0 12px 0 10px;">|</b>--%>
    <a href="../AjaxPages/xls_ar.jsp?nomeBD=<%= nomeBD%>&ar=<%= ar%>&departamento=<%= vDepartamento%>&inicio=<%= dataInicio%>&final=<%= dataFinal%>&idCliente=<%= idCliente%>"><img class="link_img" src="../../imagensNew/excel.png" /> EXPORTAR .XLS</a>
    <b style="margin:0 12px 0 10px;">|</b>
    <a href="../AjaxPages/csv_ar.jsp?nomeBD=<%= nomeBD%>&ar=<%= ar%>&departamento=<%= vDepartamento%>&inicio=<%= dataInicio%>&final=<%= dataFinal%>&idCliente=<%= idCliente%>"><img class="link_img" src="../../imagensNew/csv.png" /> EXPORTAR .CSV</a>
        <%--<b style="margin:0 12px 0 10px;">|</b>
        <a target="_blank" href="../AjaxPages/pdf_analitico.jsp?sql=<%= sql%>"><img class="link_img" src="../../imagensNew/pdf.png" /> EXPORTAR .PDF</a>--%>
</div>
 <form name="frmSRO" id="frmSRO" method="post" action="http://www2.correios.com.br/sistemas/rastreamento/Resultado.cfm" target="_blank">
    <input type="hidden" name="objetos" id="objetos" value="" />
 </form> 

<table id="barraAtendimento" border="0">
    <tr>
        <td align="left" style="font-weight:bold;font-size:12px;">
            Pesquisa Rápida:
            <select style='min-width:150px;' id="columns2" onchange="sorter2.search('query2')"></select>
            <input type="text" id="query2" onkeyup="sorter2.search('query2')" placeholder="Digite aqui a sua pesquisa..." />
            <a style="text-decoration:none;font-weight:bold;font-size:11px;" href="javascript:document.getElementById('query2').value='';sorter2.reset()">RESTAURAR PADRÕES</a>
        </td>
        <td align="right">
            <div class="details" style="clear:both;">
                <div>Resultado <span id="startrecord2"></span>-<span id="endrecord2"></span> de <span id="totalrecords2"></span></div>
            </div>
        </td>
    </tr>
</table>
<table cellpadding="0" cellspacing="0" border="0" id="table2" class="tinytable">
    <thead>
        <tr>
            <th class='nosort'><h3>RETORNOU?</h3></th>
<th><h3>RECEBIDO POR</h3></th>
<th><h3>DATA RECEB.</h3></th>
<th><h3>DOCUMENTO</h3></th>
<th><h3>DATA POSTAGEM</h3></th>
<th><h3>DESTINATÁRIO</h3></th>
<th><h3>CEP</h3></th>
<th><h3>SITUAÇÃO</h3></th>
<th><h3>DEPARTAMENTO</h3></th>
</tr>
</thead>
<tbody>
    <%
        for (int i = 0; i < movimentacao.size(); i++) {
            Entidade.Movimentacao mov = (Entidade.Movimentacao) movimentacao.get(i);

            Date data = mov.getDataPostagem();
            String vData = sdf.format(data);
            String numeroRegistro = mov.getNumObjeto();
            String destinatario = mov.getDestinatario();
            String cepDestino = mov.getCep();
            String departamento = mov.getDepartamento();
            String status = mov.getStatus();
            String nomeRecebAr = mov.getNomeRecebAr();
            String dataRecebAr = mov.getDataBaixaAr();
            int baixaAr = mov.getBaixaAr();
            if (baixaAr == 1) {
                qtdSim++;
            } else {
                qtdNao++;
            }
    %>
    <tr align='center' style="font-size: 11px;">
        <%if (baixaAr == 0) {%>
        <td>
            <img id='img<%=numeroRegistro%>' style='cursor:pointer;' src='../../imagensNew/cross_circle.png' onclick="chamaJanelaBaixaAr('<%=numeroRegistro%>');" />
            <input type='hidden' name='<%=numeroRegistro%>' id='<%=numeroRegistro%>' value='1' />
        </td>
        <%} else {%>
        <td>
            <img id='img<%=numeroRegistro%>' style='cursor:pointer;' src='../../imagensNew/tick_circle.png' onclick="javascript:if (confirm('Você tem certeza que deseja marcar este objeto como não entregue?')) {
                        chamaJanelaBaixaAr('<%=numeroRegistro%>');
                    } else {
                        return false;
                    }" />
            <input type='hidden' name='<%=numeroRegistro%>' id='<%=numeroRegistro%>' value='0' />
        </td>
        <%}%>
        <td><div id='nome<%= numeroRegistro%>'><a href="../../ImagemAR?sro=<%=numeroRegistro%>" target="_blank"><%=nomeRecebAr%></a></div></td>
        <td><div id='data<%= numeroRegistro%>'><%=dataRecebAr%></div></td>
        <td>
         <a href='#' onclick="pesqSro('<%= numeroRegistro %>');"><%= numeroRegistro%></a> 
        </td>
        <td><%= vData%></td>
        <td><%= destinatario%></td>
        <td><%= cepDestino%></td>
        <td><%= status%></td>
        <td><%= departamento%></td>
    </tr>
    <%}%>
</tbody>
</table>
<div id="tablefooter" align='center'>
    <div align="left" style='float:left; width:20%;'>
        <select onchange="sorter2.size(this.value)">
            <option value="5">5</option>
            <option value="10">10</option>
            <option value="20" selected="selected">20</option>
            <option value="50">50</option>
            <option value="100">100</option>
        </select>
        <span>Linhas por Página</span>
    </div>
    <div id="tablenav2" class="tablenav">
        <div>
            <img src="../../javascript/plugins/TableSorter/images/left_end.png" width="20" height="20" alt="First Page" onclick="sorter2.move(-1, true)" />
            <img src="../../javascript/plugins/TableSorter/images/left.png" width="20" height="20" alt="First Page" onclick="sorter2.move(-1)" />
            <img src="../../javascript/plugins/TableSorter/images/right.png" width="20" height="20" alt="First Page" onclick="sorter2.move(1)" />
            <img src="../../javascript/plugins/TableSorter/images/right_end.png" width="20" height="20" alt="Last Page" onclick="sorter2.move(1, true)" />
            <select style="margin-left:5px;" id="pagedropdown2"></select>
            <a style="margin-left:10px;" href="javascript:sorter2.showall()">Ver Tudo</a>
        </div>
    </div>
    <div id="tablelocation">
        <div class="page">Página <span id="currentpage2"></span> de <span id="totalpages2"></span></div>
    </div>
</div>
<%} else {%>
<div align='center' style='padding-top:25px;background:#fc8878;color:black;height:50px;width:100%;font-size:20px;font-weight:bold;'>Nenhum Objeto Encontrado!</div>
<%}%>
<%} else {%>
sessaoexpirada
<%}%>
<input type="hidden" id="dadosGrafico" value="NÃO RETORNADO,<%= qtdNao%>;JÁ RETORNADO,<%= qtdSim%>" />
