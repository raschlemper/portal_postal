<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="Entidade.empresas"%>
<%@page import="Coleta.Relatorio.RelatorioDeColeta"%>
<%@page import="Coleta.View.TotalClientesPorColetador"%>

<%
String nomeDB = (String) session.getAttribute("empresa");
String idColetador = request.getParameter("idColetador");
String nomeColetador = request.getParameter("nomeColetador");
String dataInicial= request.getParameter("dataInicial");
String dataFinal =  request.getParameter("dataFinal");
RelatorioDeColeta relatorio = new RelatorioDeColeta(nomeDB);

List<TotalClientesPorColetador> resultReport =  relatorio.findClientesPorColetador(idColetador, dataInicial, dataFinal);

%>
<h4 class="modal-title">Coletador: <%=nomeColetador%> período <%=relatorio.dateFomat(dataInicial,"yyyy-MM-dd","dd/MM/yyyy")%> de <%=relatorio.dateFomat(dataFinal,"yyyy-MM-dd","dd/MM/yyyy")%> </h4>
<% if(resultReport.size()>0) {%>
<table class="table table-striped table-bordered table-hover table-condensed">
    <tr>
        <th>Código</th>
        <th>Cliente</th>
        <th>Total</th>
    </tr>
    <%
        double totalGeral = 0;
        for(TotalClientesPorColetador cliente : resultReport ){
            totalGeral = totalGeral + cliente.getTotal();
    %>
    <tr>
        <td class="text-right" width="8%"><%=cliente.getIdCliente()%></td>
        <td><%=cliente.getNomeCliente()%></td>
        <td class="text-right" width="15%"><%=relatorio.formataValorMonetario(cliente.getTotal())%></td>
    </tr>
    <%  } %>
    <tr>
        <td colspan="2" class="text-right"><b>Total:</b> </td>
        <td class="text-right"><b><%=relatorio.formataValorMonetario(totalGeral)%></b></td>
    </tr>
</table>
<%}else{%>
<div class="alert alert-danger" align="center">  <strong>OPS!</strong> NÃO HOUVERAM RESULTADOS PARA A PESQUISA</div>
<%}%>
