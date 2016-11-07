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
    String idServicos = request.getParameter("idServicos");
    AcompanhamentoDeVendas vendas = new AcompanhamentoDeVendas(nomeDB);

    List<VendasPorCliente> resultVendas = vendas.findVendasPorClienteFilterServicos(Integer.valueOf(idCliente),idServicos);
    
    Map<Integer, String> ultimosMeses = vendas.getMonthName(resultVendas);

    StringBuilder dataMonth = new StringBuilder();
    for(Entry entry : ultimosMeses.entrySet() ){
        Double[] totais =vendas.somaTotaisDoMes((Integer)entry.getKey(),resultVendas);
           dataMonth.append("['"+entry.getValue()+"',{v:"+totais[0]+",f:'"+totais[0]+"'},{v:"+totais[1]+",f:'"+FormatarDecimal.formataValorMonetario(totais[1])+"'}],");
    }
    
    if(dataMonth.length()>1){
        dataMonth.deleteCharAt(dataMonth.length()-1);
    }
    
    String dataChart = "[['Mes','Quantidade','Valor'],"+dataMonth.toString()+"]";
    out.print("%js showCharts("+dataChart+"); js%");
    
    String dataChart3 = vendas.getDataChartsValorPorServico(resultVendas,ultimosMeses);
    out.print("%js showCharts3("+dataChart3+"); js%");
    
    String dataChart2 = vendas.getDataChartsQuantidadePorServico(resultVendas,ultimosMeses);
    out.print("%js showCharts2("+dataChart2+"); js%");
    
   
    
%>
