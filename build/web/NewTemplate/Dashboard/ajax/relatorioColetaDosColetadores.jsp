<%@page import="Coleta.View.TotalMovimentoPorColetador"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="Entidade.empresas"%>
<%@page import="Coleta.Relatorio.RelatorioDeColeta"%>
<%@page import="Coleta.View.TotalClientesPorColetador"%>

<%

String nomeDB = (String) session.getAttribute("empresa");

RelatorioDeColeta relatorio = new RelatorioDeColeta(nomeDB);
String dataInicial=  relatorio.dateFomat(request.getParameter("dataInicial"),"dd/MM/yyyy","yyyy-MM-dd");
String dataFinal =  relatorio.dateFomat(request.getParameter("dataFinal"),"dd/MM/yyyy","yyyy-MM-dd");
List<TotalMovimentoPorColetador> resultReport =  relatorio.findTotalMovimentoPorColetador(dataInicial, dataFinal);
List<TotalMovimentoPorColetador> resultDuplicados = relatorio.findTotalMovimentoPorColetadorDuplicados(dataInicial, dataFinal);
%>
<br>
 <% if(resultReport.size()>0) {%>
<table class="table table-striped table-bordered table-hover table-condensed">
    <tr>
        <th width="8%">Código</th>
        <th>Coletador</th>
        <th width="15%">Total</th>
    </tr>
    <%
        double totalGeral = 0;
        for(TotalMovimentoPorColetador coletador : resultReport ){
            totalGeral = totalGeral + coletador.getTotal();
    %>
    
    <tr> 
        <td class="text-right"><%=coletador.getIdColetador()%></td>
        <td>
            <a href="#" onclick="showClientePorPeriodoDetalhado('<%=coletador.getIdColetador()%>','<%=coletador.getNomeColetador()%>','<%=dataInicial%>','<%=dataFinal%>')">
                <%=coletador.getNomeColetador()%>
            </a>
        </td>
        <td class="text-right"><%=relatorio.formataValorMonetario(coletador.getTotal())%></td>
    
    </tr>

    <%  } %>
   
    <tr>
        <td colspan="2" class="text-right"><b>Total:</b> </td>
        <td class="text-right"><b><%=relatorio.formataValorMonetario(totalGeral)%></b></td>
    
    </tr>
</table>
   <%}%>

<% if(resultDuplicados.size()>0) {%>
<div class="alert alert-warning" align="left"><strong>OPS! </strong>Você possui mais de um coletador para o mesmo cliente.</div>      
<table class="table table-striped table-bordered table-hover table-condensed">
    <tr>
        <th width="8%">Código</th>
        <th >Coletador</th>
        <th width="15%">Total</th>
    </tr>
    <%
        double totalGeral = 0;
        for(TotalMovimentoPorColetador coletador : resultDuplicados ){
            totalGeral = totalGeral + coletador.getTotal();
    %>
    
    <tr> 
        <td class="text-right"><%=coletador.getIdColetador()%></td>
        <td>
            <a href="#" onclick="showClientePorPeriodoDetalhadoDuplicado('<%=coletador.getIdColetador()%>','<%=coletador.getNomeColetador()%>','<%=dataInicial%>','<%=dataFinal%>')">
                <%=coletador.getNomeColetador()%>
            </a>
        </td>
        <td class="text-right"><%=relatorio.formataValorMonetario(coletador.getTotal())%></td>
    
    </tr>

    <%  } %>
   
    <tr>
        <td colspan="2" class="text-right"><b>Total:</b> </td>
        <td class="text-right"><b><%=relatorio.formataValorMonetario(totalGeral)%></b></td>
    
    </tr>
</table>
   <%}%>
    
    <% if(resultDuplicados.size() == 0 && resultReport.size()==0)   {%>
    <div class="alert alert-danger" align="center">  <strong>OPS!</strong> NÃO HOUVERAM RESULTADOS PARA A PESQUISA</div>
   <%}%>
