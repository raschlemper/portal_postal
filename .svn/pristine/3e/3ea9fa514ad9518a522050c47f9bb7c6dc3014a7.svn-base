<%@page import="Entidade.Endereco"%>
<%@page import="Entidade.TelegramaPostal"%>
<%@page import="Emporium.Controle.ContrTelegramaPostal"%>
<%@page import="Controle.contrCliente"%>
<%@page import="Entidade.Contato"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@page import="java.util.ArrayList, java.util.Date, java.text.SimpleDateFormat, java.sql.*, java.util.Calendar, java.util.Locale" %>

<%
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    String nomeBD = (String) session.getAttribute("nomeBD");
    if (nomeBD == null) {
        response.sendRedirect("../../index.jsp?msg=Sua sessao expirou! Para voltar ao Portal faça seu login novamente!");
    } else {
        String numCliente = String.valueOf(session.getAttribute("idCliente"));
        int idCliente = Integer.parseInt(numCliente);
        String vDataAtual = sdf.format(new Date());
        if (request.getParameter("data") != null) {
            vDataAtual = request.getParameter("data");
        }
        String vData2 = sdf.format(new Date());
        if (request.getParameter("data2") != null) {
            vData2 = request.getParameter("data2");
        }

        String dataBD = Util.FormatarData.DateToBD(vDataAtual);
        String dataBD2 = Util.FormatarData.DateToBD(vData2);
%>

<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
        <title>Portal Postal | Solicitar Coleta</title>
        <meta name="keywords" content="" />
        <meta name="description" content="" />

        <link href="../../css/estilo.css" rel="stylesheet" type="text/css" />
        <script type="text/javascript" src="../js/ajax_portal.js"></script>
        <script type="text/javascript" src="../../javascript/mascara.js"></script>
        <script type="text/javascript" src="../../javascript/ajax.js"></script>
        <script type="text/javascript" src="../../javascript/jsContato.js"></script>

        <link type="text/css" href="../../javascript/jquery/css/jquery-ui-1.8.16.custom.css" rel="stylesheet" />
        <script type="text/javascript" src="http://code.jquery.com/jquery-1.10.2.js"></script>
        <script type="text/javascript" src="http://code.jquery.com/ui/1.11.4/jquery-ui.js"></script>
        <script type="text/javascript" src="../../javascript/jquery/js/jquery.ui.datepicker-pt-BR.js"></script>
        <link href="http://code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css" rel="stylesheet" />

        <!-- FUSION CHARTS -->
        <script type="text/javascript" src="../../javascript/plugins/HighCharts/js/highcharts.js"></script>
        <script type="text/javascript" src="../../javascript/plugins/HighCharts/js/themes/grid.js"></script>
        <!-- FIM FUSION CHARTS -->

        <!-- TableSorter -->
        <link rel="stylesheet" href="../../javascript/plugins/TableSorter/styleSorterV3.css" />
        <script type="text/javascript" src="../../javascript/plugins/TableSorter/scriptSorterV3.js"></script>
        <!-- TableSorter -->

        <link rel="stylesheet" href="../../javascript/plugins/dropdown/css/style.css" type="text/css" media="screen, projection"/>
        <!--[if lte IE 7]><link rel="stylesheet" type="text/css" href="../../javascript/plugins/dropdown/css/ie.css" media="screen" /><![endif]-->
        <script type="text/javascript" src="../../javascript/plugins/dropdown/js/jquery.dropdownPlain.js"></script>

        <script type="text/javascript" charset="utf-8">
            $(function() {
                $("#data").datepicker({
                    maxDate: '<%= vDataAtual%>',
                    showOn: "button",
                    buttonImage: "../../imagensNew/calendario.png",
                    buttonImageOnly: true,
                    showAnim: "slideDown"
                });
                $("#data2").datepicker({
                    maxDate: '<%= vDataAtual%>',
                    showOn: "button",
                    buttonImage: "../../imagensNew/calendario.png",
                    buttonImageOnly: true,
                    showAnim: "slideDown"
                });
            });
        </script>
    </head>
    <body>
        <div id="divInteracao" class="esconder"><input id="textointeracao" /></div>
        <div id="divProtecao" class="esconder"></div>

        <%@ include file="../../Includes/telaMsg.jsp" %>
        <%@ include file="../../Includes/menu_cliente.jsp" %>

        <div id="divPrincipal" align="center">
            <div id="container">
                <div id="conteudo">
                    <ul class="ul_tab" style="width: 1160px;">
                        <li>
                            <dl style='width:170px; border-left: 1px solid #CCC;' onclick="location.href = 'telegrama_postal.jsp'">
                                <dd><b class='serv'>ENVIAR TELEGRAMA</b></dd>
                            </dl>
                            <dl class="ativo" style='width:170px;'>
                                <dd><b class='serv'>PESQUISAR TELEGRAMAS</b></dd>
                            </dl>
                            <dl style="width: 750px; background: white;border-top: 1px solid white;border-right: 1px solid white; cursor: default;" ></dl>
                        </li>
                    </ul>


                    <form action="telegrama_pesquisa.jsp" method="post">
                        <ul class="ul_formulario">                            
                            <li class="titulo">
                                <dd><span>Pesquisar telegramas</span></dd>
                            </li>
                            <li>
                                <dd>
                                    <label>Periodo de Data:</label>
                                    de
                                    <input type="text" name="data" id="data" style="width:60px;" value="<%= vDataAtual%>" maxlength="10" onKeyPress="mascara(this, maskData)" />
                                    até
                                    <input type="text" name="data2" id="data2" style="width:60px;" value="<%= vData2%>" maxlength="10" onKeyPress="mascara(this, maskData)" />
                                </dd>
                                <dd style="width: 100%;">
                                    <div class="buttons">
                                        <input type="hidden" name="idCliente" value="<%= idCliente%>"/>
                                        <button type="submit" class="regular" ><img src="../../imagensNew/lupa.png" /> PESQUISAR TELEGRAMAS</button>
                                    </div>
                                </dd>
                            </li>
                        </ul>
                    </form>
                    <img width="100%" src="../../imagensNew/linha.jpg"/>

                    <div id="titulo1">Resultado da Pesquisa</div>
                    <table cellpadding="0" cellspacing="0" border="0" id="table2" class="tinytable">
                        <thead>
                            <tr>
                                <th style="width: 50px;" class="nosort"><h3>STATUS</h3></th>
                                <th style="width: 50px;" class="nosort"><h3>IMPRIMIR</h3></th>
                                <th><h3>N° DO TELEGRAMA</h3></th>
                                <th><h3>Departamento</h3></th>
                                <th><h3>Destinatário</h3></th>
                                <th><h3>Cidade / UF</h3></th>
                                <th><h3>Dt. Solicitação / Dt. Envio</h3></th>
                                <th><h3>Adicionais</h3></th>
                            </tr>
                        </thead>
                        <tbody>
                            <%
                                ArrayList<TelegramaPostal> lista = ContrTelegramaPostal.consultaByData(idCliente, dataBD, dataBD2, nomeBD);
                                for (TelegramaPostal t : lista) {
                                    Endereco ed = t.getEnderecoDes();
                                    String enviado = "";
                                    if (t.getStatus() == 1) {
                                        enviado = " - "+sdf.format(t.getDataHoraEnviado());
                                    }
                            %>
                            <tr>
                                <td align="center">     
                                    <%if (t.getStatus() == 1) {%>    
                                    
                                <%if(t.getValor()>0){%>
                                <img src="../../imagensNew/tick_circle.png" /><br/>R$<%= t.getValor()%>
                                <%}else{%>
                                    <img src="../../imagensNew/tick_circle.png" />
                                <%}%>
                                    <%} else {%>
                                    <form action="../../ServTelegramaExcluir" method="post" >                                                 
                                        <input type="hidden" name="id" value="<%= t.getId()%>" />
                                        <input type="hidden" name='nomeBD' value="<%= nomeBD%>" />
                                        <input type="image" src="../../imagensNew/cross-button.png" alt="Submit" />
                                    </form>
                                    <%}%>
                                </td>
                                <td align="center">
                                        <a href="../../Agencia/Telegrama/telegrama_impressao.jsp?id=<%= t.getId()%>" target="_blank" ><img src="../../imagensNew/printer.png" /></a>
                                </td>
                                <td><a style="font-weight: bold; color: blue;" href='http://websro.correios.com.br/sro_bin/txect01$.QueryList?P_LINGUA=001&P_TIPO=001&P_COD_UNI=<%= t.getSro()%>' target=_blank><%= t.getSro()%></a></td>
                                <td><b><%= t.getDepartamento() %></b></td>
                                <td><b><%= ed.getNome()%></b></td>
                                <td><%= ed.getCidade() + " / " + ed.getUf()%></td>
                                <td><%= sdf.format(t.getDataHoraAgendado()) + enviado %></td>
                                <td><%= t.getAdicionais()%></td>
                            </tr>
                            <%}%>
                        </tbody>
                    </table>
                    <script type="text/javascript">
                        var sorter2 = new TINY.table.sorter('sorter2', 'table2', {
                            headclass: 'head',
                            ascclass: 'asc',
                            descclass: 'desc',
                            evenclass: 'evenrow',
                            oddclass: 'oddrow',
                            evenselclass: 'evenselected',
                            oddselclass: 'oddselected',
                            paginate: false,
                            size: 10,
                            hoverid: 'selectedrowPointer',
                            sortcolumn: 2,
                            sortdir: -1,
                            init: true
                        });
                    </script>
                    <div style="width: 100%;clear: both;"></div>



                </div>
            </div>
        </div>
    </body>
</html>
<%}%>