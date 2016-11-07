<%@page import="Util.FormatarDecimal"%>
<%@page import="java.util.Map.Entry"%>
<%@page import="java.util.Map"%>
<%@page import="Util.FormatarData"%>
<%@page import="java.util.Locale"%>
<%@page import="java.util.Calendar"%>
<%@page import="dashboard.view.VendasPorCliente"%>
<%@page import="java.util.List"%>
<%@page import="dashboard.report.AcompanhamentoDeVendas"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    String nomeDB = (String) session.getAttribute("empresa");
    String idCliente = request.getParameter("idCliente");
    AcompanhamentoDeVendas vendas = new AcompanhamentoDeVendas(nomeDB);

    List<VendasPorCliente> resultVendas = vendas.findVendasPorCliente(Integer.valueOf(idCliente));
    
    Map<Integer, String> ultimosMeses = vendas.getMonthName(resultVendas);
    out.print("<table class=\"table table-striped table-bordered table-hover table-condensed\" ><tr><th></th><th></th>");
    for (String monthName : ultimosMeses.values()) {
        out.print("<th class=\"text-center\" colspan=\"2\">" + monthName + "</th>");
    }
    Map<Integer, List<VendasPorCliente>> gridVenda = vendas.montaGridVendas(resultVendas, ultimosMeses);
    out.print("</tr><tr><th></th><th>Serviço(s)</th><td>Qtd</td><td>Valor(R$)</td><td>Qtd</td><td>Valor(R$)</td><td>Qtd</td><td>Valor(R$)</td><td>Qtd</td><td>Valor(R$)</td><td>Qtd</td><td>Valor(R$)</td><td>Qtd</td><td>Valor(R$)</td></tr>");
    for (Entry<Integer, List<VendasPorCliente>> entry : gridVenda.entrySet()) {
        String servicoNome = entry.getKey()+" - "+ vendas.getNomeServico(entry.getKey(),resultVendas);
        out.print("<tr>");
        out.print("<td><input onclick=\"filtroServicos()\" type=\"checkBox\" value=\""+entry.getKey()+"\" checked/></td>");
        out.print("<td>" + servicoNome + "</td>");
        for (VendasPorCliente servico : entry.getValue()) {
            out.print("<td class=\"text-right\">" + servico.getQuantidade() + "</td><td class=\"text-right\">" + FormatarDecimal.formataValorMonetario(servico.getTotal()) + "</td>");
        }
        out.print("</tr>");
    }
    
    out.print("<tr><td></td><td><b>Total</b></td> ");
    StringBuilder dataMonth = new StringBuilder();
    for(Entry entry : ultimosMeses.entrySet() ){
        Double[] totais =vendas.somaTotaisDoMes((Integer)entry.getKey(),resultVendas);
        out.print("<td class=\"text-right\"><b>"+ new Double(totais[0]).intValue()+"</b></td><td class=\"text-right\"><b>"+ FormatarDecimal.formataValorMonetario(totais[1]) +"</b></td>");
        dataMonth.append("['"+entry.getValue()+"',{v:"+totais[0]+",f:'"+totais[0]+"'},{v:"+totais[1]+",f:'"+FormatarDecimal.formataValorMonetario(totais[1])+"'}],");
    }
    
    if(dataMonth.length()>1){
        dataMonth.deleteCharAt(dataMonth.length()-1);
    }
    
    out.print("</tr>");
    out.print("</table>");
    out.print("</tr></table>");
    String dataChart = "[['Mes','Quantidade','Valor'],"+dataMonth.toString()+"]";
    out.print("</br><div id=\"columnchart\" style=\"height: 400px;\"></div>%js  showCharts("+dataChart+"); js%");
    
    String dataChart3 = vendas.getDataChartsValorPorServico(resultVendas,ultimosMeses);
    out.print("</br><div id=\"columnchart3\" style=\"height: 400px;\" ></div>%js showCharts3("+dataChart3+"); js%");
    
    String dataChart2 = vendas.getDataChartsQuantidadePorServico(resultVendas,ultimosMeses);
    out.print("</br><div id=\"columnchart2\" style=\"height: 400px;\" ></div>%js showCharts2("+dataChart2+"); js%");
    
    
    

%>
