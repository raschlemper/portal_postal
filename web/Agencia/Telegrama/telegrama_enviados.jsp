
<%@page import="Entidade.Endereco"%>
<%@page import="Entidade.TelegramaPostal"%>
<%@page import="Emporium.Controle.ContrTelegramaPostal"%>
<%@page import="Controle.contrCliente"%>
<%@page import="Entidade.Contato"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@page import="java.util.ArrayList, java.util.Date, java.text.SimpleDateFormat, java.sql.*, java.util.Calendar, java.util.Locale" %>

<%
    SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy HH:mm");
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    String nomeBD = (String) session.getAttribute("nomeBD");
    if (nomeBD == null) {
        response.sendRedirect("../../index.jsp?msg=Sua sessao expirou! Para voltar ao Portal faça seu login novamente!");
    } else {

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
            $(function () {
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
            function pesqSro(param) {
                $('#objetos').val(param);
                $('#frmSRO').submit();
            }
        </script>
    </head>
    <body>
        <div id="divInteracao" class="esconder"><input id="textointeracao" /></div>
        <div id="divProtecao" class="esconder"></div>

        <%@ include file="../../Includes/telaMsg.jsp" %>
        <%@ include file="../../Includes/menu_agencia.jsp" %>

        <form name="frmSRO" id="frmSRO" method="post" action="http://www2.correios.com.br/sistemas/rastreamento/Resultado.cfm" target="_blank">
            <input type="hidden" name="objetos" id="objetos" value="" />
        </form>

        <div id="divPrincipal" align="center">
            <div id="container">
                <div id="conteudo">
                    <ul class="ul_tab" style="width: 1160px;">
                        <li>
                            <dl style='width:170px; border-left: 1px solid #CCC;' onclick="location.href = 'telegrama_naoenviados.jsp'">
                                <dd><b class='serv'>TELEGRAMAS</b></dd>
                                <dd><p><b>NÃO ENVIADOS</b></p></dd>
                            </dl>
                            <dl class="ativo" style='width:170px;'>
                                <dd><b class='serv'>TELEGRAMAS</b></dd>
                                <dd><p><b>ENVIADOS</b></p></dd>
                            </dl>
                            <dl style="width: 750px; background: white;border-top: 1px solid white;border-right: 1px solid white; cursor: default;" ></dl>
                        </li>
                    </ul>
                    <div style="width: 100%;clear: both;"></div>
                    <form action="telegrama_enviados.jsp" method="post">
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
                                        <button type="submit" class="regular" ><img src="../../imagensNew/lupa.png" /> PESQUISAR TELEGRAMAS</button>
                                    </div>
                                </dd>
                            </li>
                        </ul>
                    </form>
                    <img width="100%" src="../../imagensNew/linha.jpg"/>

                    <div id="titulo1">Resultado da Pesquisa</div>
                    <%
                        ArrayList<TelegramaPostal> lista = ContrTelegramaPostal.consultaEnviados(dataBD, dataBD2, nomeBD);
                        for (TelegramaPostal t : lista) {
                            Endereco ed = t.getEnderecoDes();
                            Endereco er = t.getEnderecoRem();
                            String adicionais = "";
                            if (t.getAdicionais().contains("AR")) {
                                adicionais = "<br/>- AVISO DE ENTREGA";
                            }
                            if (t.getAdicionais().contains("CP")) {
                                adicionais += "<br/>- CÓPIA DE TELEGRAMA - VIA " + t.getEnvioCopia() + ": " + t.getEmailCopia();
                            }
                    %>
                    <table cellpadding="0" cellspacing="0" border="0" class="tinytable">
                        <tbody>     
                            <tr>
                                <td style="width: 50%;border-top: 1px solid lightsteelblue;">
                                    <b>Remetente:</b><br/>
                                    <%= er.getNome()%><br/>
                                    <%= t.getDepartamento()%><br/>
                                    <%= er.getLogradouro() + " " + er.getNumero()%><br/>
                                    <%= er.getComplemento()%><br/>
                                    <%= er.getBairro()%><br/>
                                    <%= er.getCidade() + "/" + er.getUf()%><br/>
                                    <%= er.getCep()%>
                                </td>
                                <td style="width: 50%;border-top: 1px solid lightsteelblue;">
                                    <b>Destinatario:</b><br/>
                                    <%= ed.getNome()%><br/>
                                    <%= ed.getLogradouro() + " " + ed.getNumero()%><br/>
                                    <%= ed.getComplemento()%><br/>
                                    <%= ed.getBairro()%><br/>
                                    <%= ed.getCidade() + "/" + ed.getUf()%><br/>
                                    <%= ed.getCep()%>
                                </td>
                            </tr>
                            <tr>
                                <td colspan="2">
                                    <b>Solicitado Em:</b> <%= sdf2.format(t.getDataHora())%><br/>
                                    <b>Agendado Para:</b> <%= sdf2.format(t.getDataHoraAgendado())%> por <%= t.getUserAgendado()%><br/>
                                    <b>Enviado Em:</b> <%= sdf2.format(t.getDataHoraEnviado())%> por <%= t.getUserEnviado()%><br/><br/>
                                    <b>N° do Telegrama:</b>                                    
                                    <a href='#' onclick="pesqSro('<%= t.getSro()%>');"><%= t.getSro()%></a> 
                                    <br/><br/>
                                    <b>Adicionais:</b> <%= adicionais%><br/><br/>
                                    <b>Mensagem:</b><br/>
                                    <%= t.getMensagem()%>                                    
                                    <div class="buttons"> 
                                        <button type="button" onclick="window.open('telegrama_impressao.jsp?id=<%= t.getId()%>', '_blank');" class="regular" ><img src="../../imagensNew/printer.png" /> IMPRIMIR TELEGRAMA</button>
                                    </div>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                    <br/><hr/><br/>
                    <%}%>
                    <div style="width: 100%;clear: both;"></div>



                </div>
            </div>
        </div>
    </body>
</html>
<%}%>