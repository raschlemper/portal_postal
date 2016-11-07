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

List<TotalClientesPorColetador> resultReport =  relatorio.findClientesPorColetadorDuplicado(idColetador, dataInicial, dataFinal);

%>

<% if(resultReport.size()>0) {%>
<div class="row" >
    <div class="form-inline col-md-8" style="margin-bottom: 15px;" >
        <button type="button" class="btn btn-sm btn-default form-control" onclick="doit('coletaDuplicada.xlsx', 'coletaPcolDuall');">
            <i class="fa fa-lg fa-spc fa-file-excel-o"></i>GERAR EXCELL
        </button>
    </div>
</div>
<table class="table table-striped table-bordered table-hover table-condensed" id="coletaPcolDuall">
    <tr> <td colspan="4"><b>Coletador: <%=nomeColetador%> período <%=relatorio.dateFomat(dataInicial,"yyyy-MM-dd","dd/MM/yyyy")%> de <%=relatorio.dateFomat(dataFinal,"yyyy-MM-dd","dd/MM/yyyy")%></b> </td></tr>
    <tr>
        <td><b>Código</b></td>
        <td><b>Cliente</b></td>
        <td><b>Qtd Coletas</b></td>
        <td><b>Total</b></td>
    </tr>
    <%
        double totalGeral = 0;
        for(TotalClientesPorColetador cliente : resultReport ){
            totalGeral = totalGeral + cliente.getTotal();
    %>
    <tr>
        <td class="text-right" width="8%"><%=cliente.getIdCliente()%></td>
        <td><a href="#" onclick="showClienteMovimento('<%=idColetador%>','<%=cliente.getIdCliente()%>','<%=nomeColetador%>','<%=dataInicial%>','<%=dataFinal%>')"><%=cliente.getNomeCliente()%></a></td>
        <td class="text-right" width="13%"><%=cliente.getQuantidade()%></td>
        <td class="text-right" width="15%"><%=relatorio.formataValorMonetario(cliente.getTotal())%></td>
    </tr>
    <%  } %>
    <tr>
        <td colspan="3" class="text-right"><b>Total:</b> </td>
        <td class="text-right"><b><%=relatorio.formataValorMonetario(totalGeral)%></b></td>
    </tr>
</table>
<%}else{%>
<div class="alert alert-danger" align="center">  <strong>OPS!</strong> NÃO HOUVERAM RESULTADOS PARA A PESQUISA</div>
<%}%>
