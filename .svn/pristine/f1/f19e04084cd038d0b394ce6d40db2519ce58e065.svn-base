<%-- 
    Document   : relatorioColeta
    Created on : 30/09/2009, 12:42:40
    Author     : SCC4
--%>
<%@page import="Entidade.Clientes"%>
<%@page import="java.sql.Timestamp"%>
<%@ page import="java.text.SimpleDateFormat, java.util.Date, java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%
            String nomeBD = (String) session.getAttribute("empresa");
            if (nomeBD == null) {
                response.sendRedirect("../index.jsp?msgLog=3");
            } else {

                String vIdColetador = request.getParameter("idColetador");
                int idColetador = 0;
                if (vIdColetador != null) {
                    idColetador = Integer.parseInt(vIdColetador);
                }

                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

                Date dataAtual = new Date();

                String vDataAtual = sdf.format(dataAtual);
                String ontem = Util.SomaData.SomarDiasDatas(dataAtual, -1);
                String mes = Util.SomaData.SomarDiasDatas(dataAtual, -30);

                String data1 = request.getParameter("data1");
                if (data1 == null || data1.equals("")) {
                    data1 = mes;
                }
                String data2 = request.getParameter("data2");
                if (data2 == null || data2.equals("")) {
                    data2 = ontem;
                }

//cria array list de coletadores e de IDcoletadores
                ArrayList listaColetadores = Coleta.Controle.contrColetador.consultaTodosColetadores(nomeBD);

%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Portal Postal | Relatório de Coletas</title>

        <script type="text/javascript" src="../../javascript/mascara.js"></script>
        <SCRIPT LANGUAGE="Javascript" SRC="../../FusionCharts/FusionCharts.js"></SCRIPT>
        <link href="../../css/estilo.css" rel="stylesheet" type="text/css" />

        <!-- TableSorter -->
        <link rel="stylesheet" href="../../javascript/plugins/TableSorter/styleSorterV3.css" />
        <script type="text/javascript" src="../../javascript/plugins/TableSorter/scriptSorterV3.js"></script>
        <!-- TableSorter -->

        <link rel="stylesheet" href="../../javascript/plugins/dropdown/css/style.css" type="text/css" media="screen, projection"/>
        <!--[if lte IE 7]>
        <link rel="stylesheet" type="text/css" href="../../javascript/plugins/dropdown/css/ie.css" media="screen" />
        <![endif]-->
        <script type="text/javascript" language="javascript" src="../../javascript/plugins/dropdown/js/jquery.dropdownPlain.js"></script>
    </head>
    <body>

        <%@ include file="../../Includes/menu_agencia.jsp" %>
        <%@ include file="../../Includes/telaMsg.jsp" %>

        <div id="divPrincipal" align="center">
            <div id="container">
                <div id="conteudo">


                    <form name="form1" method="post" action="">
                        <center>
                            <div style="padding-bottom:15px;">
                                Escolha um Coletador:
                                <select name="idColetador" onchange="javascript:document.form1.submit();">
                                    <option value="0">Todos os Coletadores</option>
                                    <%
                                                    for (int i = 0; i < listaColetadores.size(); i++) {
                                                        Coleta.Entidade.Coletador coletador = (Coleta.Entidade.Coletador) listaColetadores.get(i);
                                                        String nomeColetador = coletador.getNome();
                                                        int idCol = coletador.getIdColetador();
                                    %>
                                    <option value="<%= idCol%>" <%if (idCol == idColetador) {%> selected <%}%> ><%= nomeColetador%></option>
                                    <%}%>
                                </select>
                            </div>
                            <div style="height:300px;width:800px;">
                                <div style="border:2px solid #bbbbff;float:left;">
                                    <div style="background:#bbbbff;color:white;width:100%;font-size:11px;font-weight:bold;">Comparação de Quantidade de Coletas por Dia da Semana</div>
                                    <% String url1 = "../../FusionCharts/graficosHoito/graficoQtdPorSemana.jsp?datas=" + idColetador;%>
                                    <jsp:include page="../../FusionCharts/Includes/FusionChartsRenderer.jsp" flush="true">
                                        <jsp:param name="chartSWF" value="../../FusionCharts/FCF_MSColumn3D.swf" />
                                        <jsp:param name="strURL" value="<%= url1%>" />
                                        <jsp:param name="strXML" value="" />
                                        <jsp:param name="chartId" value="3" />
                                        <jsp:param name="chartWidth" value="530" />
                                        <jsp:param name="chartHeight" value="250" />
                                        <jsp:param name="debugMode" value="false" />
                                        <jsp:param name="registerWithJS" value="false" />
                                    </jsp:include>
                                    <div style="height:30px;">
                                        <a href="relatorioColetaClientes.jsp">Visualizar relatorio de coletas de todos os clientes.</a>
                                    </div>
                                </div>
                                <div style="border:2px solid #bbbbff;float:right;">
                                    <div style="background:#bbbbff;color:white;width:100%;font-size:11px;font-weight:bold;">Porcentagem de Coletas por Serviços</div>
                                    <div style="height:30px;">
                                        De <input type="text" name="data1" value="<%= data1%>" style="width:70px;" maxlength="10" onKeyPress="mascara(this,maskData)">
                                        até <input type="text" name="data2" value="<%= data2%>" style="width:70px;" maxlength="10" onKeyPress="mascara(this,maskData)">
                                        <input type="submit" value="OK">
                                    </div>
                                    <% String url2 = "../../FusionCharts/graficosHoito/graficoColetadorPorServico.jsp?datas=" + data1 + ";" + data2 + ";" + idColetador;%>
                                    <jsp:include page="../../FusionCharts/Includes/FusionChartsRenderer.jsp" flush="true">
                                        <jsp:param name="chartSWF" value="../../FusionCharts/FCF_Pie2D.swf" />
                                        <jsp:param name="strURL" value="<%= url2%>" />
                                        <jsp:param name="strXML" value="" />
                                        <jsp:param name="chartId" value="4" />
                                        <jsp:param name="chartWidth" value="250" />
                                        <jsp:param name="chartHeight" value="250" />
                                        <jsp:param name="debugMode" value="false" />
                                        <jsp:param name="registerWithJS" value="false" />
                                    </jsp:include>
                                </div>
                            </div>
                            <%--<div style="border:2px solid #bbbbff;margin-top:10px;width:800px;">
                                <div style="background:#bbbbff;color:white;width:100%;font-size:11px;font-weight:bold;">Rentabilidade das Coletas por Mês</div>
                                <% String url3 = "../../FusionCharts/graficosHoito/graficoValorPorColetador.jsp?datas=" + idColetador;%>
                                <jsp:include page="../../FusionCharts/Includes/FusionChartsRenderer.jsp" flush="true">
                                    <jsp:param name="chartSWF" value="../../FusionCharts/FCF_MSColumn3D.swf" />
                                    <jsp:param name="strURL" value="<%= url3%>" />
                                    <jsp:param name="strXML" value="" />
                                    <jsp:param name="chartId" value="ss1" />
                                    <jsp:param name="chartWidth" value="800" />
                                    <jsp:param name="chartHeight" value="250" />
                                    <jsp:param name="debugMode" value="false" />
                                    <jsp:param name="registerWithJS" value="false" />
                                </jsp:include>
                            </div>
                            <div style="border:2px solid #bbbbff;margin-top:10px;width:800px;">
                                <div style="background:#bbbbff;color:white;width:100%;font-size:11px;font-weight:bold;">Quantidade de Objetos Coletados por Mês</div>
                                <% String url4 = "../../FusionCharts/graficosHoito/graficoQtdPorColetador.jsp?datas=" + idColetador;%>
                                <jsp:include page="../../FusionCharts/Includes/FusionChartsRenderer.jsp" flush="true">
                                    <jsp:param name="chartSWF" value="../../FusionCharts/FCF_MSColumn3D.swf" />
                                    <jsp:param name="strURL" value="<%= url4%>" />
                                    <jsp:param name="strXML" value="" />
                                    <jsp:param name="chartId" value="ss2" />
                                    <jsp:param name="chartWidth" value="800" />
                                    <jsp:param name="chartHeight" value="250" />
                                    <jsp:param name="debugMode" value="false" />
                                    <jsp:param name="registerWithJS" value="false" />
                                </jsp:include>
                            </div>--%>
                        </center>

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
                                        <th><h3>Nº</h3></th>
                                        <th><h3>Nome do Cliente</h3></th>
                                        <th><h3>Data da última coleta</h3></th>
                                        <th><h3>Qtd Coletado neste Periodo</h3></th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <%
                                            ArrayList<Clientes> listaClientes = Controle.contrCliente.consultaClienteUltColeta(nomeBD, 50);
                                            for (int j = 0; j < listaClientes.size(); j++) {
                                                Clientes cli = listaClientes.get(j);
                                                String nomeCli = cli.getNome();
                                                int idCliente = cli.getCodigo();
                                                String dataUlt = cli.getEmail();
                                    %>
                                    <tr>
                                        <td><%= idCliente%></td>
                                        <td><%= nomeCli%></td>
                                        <td><%= dataUlt%></td>
                                        <td></td>
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
                                        <img src="../../javascript/plugins/TableSorter/images/left_end.png" width="20" height="20" alt="First Page" onclick="sorter2.move(-1,true)" />
                                        <img src="../../javascript/plugins/TableSorter/images/left.png" width="20" height="20" alt="First Page" onclick="sorter2.move(-1)" />
                                        <img src="../../javascript/plugins/TableSorter/images/right.png" width="20" height="20" alt="First Page" onclick="sorter2.move(1)" />
                                        <img src="../../javascript/plugins/TableSorter/images/right_end.png" width="20" height="20" alt="Last Page" onclick="sorter2.move(1,true)" />
                                        <select style="margin-left:5px;" id="pagedropdown2"></select>
                                        <a style="margin-left:10px;" href="javascript:sorter2.showall()">Ver Tudo</a>
                                    </div>
                                </div>
                                <div id="tablelocation">
                                    <div class="page">Página <span id="currentpage2"></span> de <span id="totalpages2"></span></div>
                                </div>
                            </div>
                            <script type="text/javascript">
                                var sorter2 = new TINY.table.sorter('sorter2','table2',{
                                    headclass:'head',
                                    ascclass:'asc',
                                    descclass:'desc',
                                    evenclass:'evenrow',
                                    oddclass:'oddrow',
                                    evenselclass:'evenselected',
                                    oddselclass:'oddselected',
                                    paginate:true,
                                    size:20,
                                    colddid:'columns2',
                                    currentid:'currentpage2',
                                    totalid:'totalpages2',
                                    startingrecid:'startrecord2',
                                    endingrecid:'endrecord2',
                                    totalrecid:'totalrecords2',
                                    hoverid:'selectedrowPointer',
                                    pageddid:'pagedropdown2',
                                    navid:'tablenav2',
                                    sortcolumn:0,
                                    sortdir:1,
                                    sum:[3,4,5,6],
                                    columns:[{index:3, format:' Objetos', decimals:1},{index:4, format:' Objetos', decimals:1}],
                                    init:true
                                });
                            </script>
                    </form>

                </div>
            </div>
        </div>
    </body>
</html>
<%}%>