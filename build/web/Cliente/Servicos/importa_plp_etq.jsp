
<%@page import="Controle.ContrClienteEtiquetas"%>
<%@page import="Controle.ContrClienteContrato"%>
<%@page import="Controle.contrDestinatario"%>
<%@page import="Entidade.Destinatario"%>
<%@page import="Entidade.SenhaCliente"%>
<%@page import = "java.util.ArrayList,java.sql.Timestamp, java.util.Date, java.text.SimpleDateFormat"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%
    String nomeBD = (String) session.getAttribute("nomeBD");
    if (nomeBD == null) {
        response.sendRedirect("../../index.jsp?msg=Sua sessao expirou! Para voltar ao Portal faça seu login novamente!");
    } else {
        String formato = request.getParameter("formato");
        String aux[] = request.getParameterValues("ids");
        String ids = "";
        for (int i = 0; i < aux.length; i++) {
            ids += ",'" + aux[i] + "'";
        }
        ids = ids.substring(1);
%>
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />

        <script type="text/javascript" src="../../javascript/ajax.js"></script>
        <script type="text/javascript" src="../js/ajax_portal.js"></script>
        <script type="text/javascript" src="../../javascript/mascara.js"></script>

        <link href="../../css/estilo.css" rel="stylesheet" type="text/css" />
        <script type="text/javascript" src="../../javascript/jquery/js/jquery-1.6.2.min.js"></script>

        <!-- Menu -->
        <link rel="stylesheet" href="../../javascript/plugins/dropdown/css/style.css" type="text/css" media="screen, projection"/>
        <script type="text/javascript" language="javascript" src="../../javascript/plugins/dropdown/js/jquery.dropdownPlain.js"></script>
        <!--[if lte IE 7]>
        <link rel="stylesheet" type="text/css" href="../../javascript/plugins/dropdown/css/ie.css" media="screen" />
        <![endif]-->
        <!-- Menu -->

        <!-- TableSorter -->
        <link rel="stylesheet" href="../../javascript/plugins/TableSorter/styleSorterV3.css" />
        <script type="text/javascript" src="../../javascript/plugins/TableSorter/scriptSorterV3.js"></script>
        <!-- TableSorter -->
        <script>
            //initiates print once content has been loaded into iframe
            function callPrint(iframeId) {
                var PDF = document.getElementById(iframeId);
                PDF.focus();
                PDF.contentWindow.print();
            }
        </script>
        <title>Portal Postal | Impressão</title>
    </head>
    <body>
        <div id="divInteracao" class="esconder" style="top:10%; left:10%; right:10%; bottom:10%;" align="center"><input id="textointeracao" /></div>
        <div id="divProtecao" class="esconder"></div>
        <%@ include file="../../Includes/menu_cliente.jsp" %>
        <%@ include file="../../Includes/telaMsg.jsp" %>
        <div id="divPrincipal" align="center">
            <div id="container">
                <div id="conteudo">
                    <div id="titulo1">Imprimir Etiquetas de PLP</div>
                    <ul class="ul_dados">
                        <li>
                            <dd style="color: red;">
                                <b>ATENÇÃO !!!</b><br/><br/>
                                Estas etiquetas já são consideradas como impressas!<br/>
                            </dd>
                        </li>
                        <li>
                            <dd>
                                <div class="buttons">
                                    <button type="button" class="regular" onClick="callPrint('iEtiquetas');"><img src="../../imagensNew/printer.png" /> IMPRIMIR ETIQUETAS</button>
                                    <button type="button" class="regular" onClick="callPrint('iARs');"><img src="../../imagensNew/printer.png" /> IMPRIMIR ARs</button>
                                </div>
                            </dd>
                        </li>
                    </ul>
                    <img width="100%" src="../../imagensNew/linha.jpg"/>
                    <center>
                        <iframe id="iEtiquetas" src="<%=request.getContextPath()%>/ServEtiquetasPLP?formato=<%= formato%>&ids=<%= ids%>" style="overflow: auto;" frameborder="0" scrolling="no" width="46%" height="750"></iframe>
                        <iframe id="iARs" src="<%=request.getContextPath()%>/ServEtiquetasArPLP?formato=<%= formato%>&ids=<%= ids%>" style="overflow: auto;" frameborder="0" scrolling="no" width="46%" height="750"></iframe>
                    </center> 
                </div>
            </div>
        </div>
    </body>
</html>
<%}%>