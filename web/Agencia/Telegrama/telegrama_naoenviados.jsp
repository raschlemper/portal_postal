
<%@page import="Entidade.Endereco"%>
<%@page import="Entidade.TelegramaPostal"%>
<%@page import="Emporium.Controle.ContrTelegramaPostal"%>
<%@page import="Controle.contrCliente"%>
<%@page import="Entidade.Contato"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@page import="java.util.ArrayList, java.util.Date, java.text.SimpleDateFormat, java.sql.*, java.util.Calendar, java.util.Locale" %>

<%
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
    String nomeBD = (String) session.getAttribute("nomeBD");
    if (nomeBD == null) {
        response.sendRedirect("../../index.jsp?msg=Sua sessao expirou! Para voltar ao Portal faça seu login novamente!");
    } else {
        String nomeUser = (String) session.getAttribute("usuario");
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

    </head>
    <body>
        <div id="divInteracao" class="esconder"><input id="textointeracao" /></div>
        <div id="divProtecao" class="esconder"></div>

        <%@ include file="../../Includes/telaMsg.jsp" %>
        <%@ include file="../../Includes/menu_agencia.jsp" %>

        <div id="divPrincipal" align="center">
            <div id="container">
                <div id="conteudo">
                    <ul class="ul_tab" style="width: 1160px;">
                        <li>
                            <dl class="ativo" style='width:170px; border-left: 1px solid #CCC;'>
                                <dd><b class='serv'>TELEGRAMAS</b></dd>
                                <dd><p><b>NÃO ENVIADOS</b></p></dd>
                            </dl>
                            <dl style='width:170px;' onclick="location.href = 'telegrama_enviados.jsp'">
                                <dd><b class='serv'>TELEGRAMAS</b></dd>
                                <dd><p><b>ENVIADOS</b></p></dd>
                            </dl>
                            <dl style="width: 750px; background: white;border-top: 1px solid white;border-right: 1px solid white; cursor: default;" ></dl>
                        </li>
                    </ul>
                    <div style="width: 100%;clear: both;"></div>
                    <br/><br/>
                    <%                                
                        ArrayList<TelegramaPostal> lista = ContrTelegramaPostal.consultaNaoEnviados(nomeBD);
                        for (TelegramaPostal t : lista) {
                            Endereco ed = t.getEnderecoDes();
                            Endereco er = t.getEnderecoRem();
                            String adicionais = "";
                            if (t.getAdicionais().contains("AR")) {
                                adicionais = "<br/>- PEDIDO DE CONFIRMAÇÃO";
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
                                    <b>Solicitado Em:</b> <%= sdf.format(t.getDataHora())%><br/>
                                    <b>Agendado Para:</b> <%= sdf.format(t.getDataHoraAgendado())%> por <%= t.getUserAgendado() %><br/><br/>
                                    <b>Adicionais:</b> <%= adicionais%><br/><br/>
                                    <b>Mensagem:</b><br/>
                                    <textarea style="width: 100%; height: 150px;" cols="10" rows="350" onclick="this.focus();this.select();"><%= t.getMensagem()%></textarea><br/>
                                    <form action="../../ServTelegramaEnvia">
                                        <div class="buttons"> 
                                            <b>N° do Telegrama:</b>
                                            <input type="text" name="sro" value="" />                                                       
                                            <input type="hidden" name="id" value="<%= t.getId()%>"/>
                                            <input type="hidden" name='nomeUser' value="<%= nomeUser%>"/>
                                            <input type="hidden" name='nomeBD' value="<%= nomeBD%>"/>
                                            <button type="submit" class="positive" ><img src="../../imagensNew/tick_circle.png" /> MARCAR COMO ENVIADO</button>
                                            <button type="button" onclick="window.open('telegrama_impressao.jsp?id=<%= t.getId()%>','_blank');" class="regular" ><img src="../../imagensNew/printer.png" /> IMPRIMIR TELEGRAMA</button>
                                        </div>
                                    </form>
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