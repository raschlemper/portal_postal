
<%@page import="Entidade.ServicoPrefixo"%>
<%@page import="Controle.ContrServicoPrefixo"%>
<%@page import="Entidade.ServicoECT"%>
<%@page import="Controle.ContrServicoECT"%>
<%@page import="Controle.ContrClienteContrato"%>
<%@ page import = "java.util.ArrayList,java.sql.Timestamp, java.util.Date, java.text.SimpleDateFormat"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%
    if (session.getAttribute("usuario") == null) {
        response.sendRedirect("../index.jsp?msgLog=3");
    } else {

        int idNivelDoUsuario = (Integer) session.getAttribute("nivel");
        if (idNivelDoUsuario != 1) {
            response.sendRedirect("../jsp/imp_movimento.jsp?msg=Acesso Negado!");
        }

        String nomeBD = (String) session.getAttribute("empresa");
%>
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />

        <script type="text/javascript" src="../../javascript/ajax.js"></script>
        <script type="text/javascript" src="../../javascript/mascara.js"></script>
        <!-- TableSorter -->
        <link rel="stylesheet" href="../../javascript/plugins/TableSorter/styleSorterV3.css" />
        <script type="text/javascript" src="../../javascript/plugins/TableSorter/scriptSorterV3.js"></script>
        <!-- TableSorter -->
        <link href="../../css/estilo.css" rel="stylesheet" type="text/css" />
        <link rel="stylesheet" href="../../javascript/plugins/dropdown/css/style.css" type="text/css" media="screen, projection"/>
        <!--[if lte IE 7]>
        <link rel="stylesheet" type="text/css" href="../../javascript/plugins/dropdown/css/ie.css" media="screen" />
        <![endif]-->
        <script type="text/javascript" language="javascript" src="../../javascript/plugins/dropdown/js/jquery.dropdownPlain.js"></script>
        <script type="text/javascript">

        </script>

        <title>Portal Postal | Atualizar SRO</title>

    </head>
    <body onload="alertaQtd();">
        <div id="divInteracao" class="esconder" align="center"><input id="textointeracao" /></div>
        <div id="divProtecao" class="esconder"></div>

        <%@ include file="../../Includes/menu_agencia.jsp" %>
        <%@ include file="../../Includes/telaMsg.jsp" %>

        <div id="divPrincipal" align="center">
            <div id="container">
                <div id="conteudo">
                    <div id="titulo1">Atualizar Status do SRO</div> 
                    <form name='formObs' action='../../ServAtualizaSRO' method='post'>
                        <ul class="ul_formulario">     
                            <li>
                                <dd style="width: 100%; text-align: center;">
                                <div class="buttons">
                                    <input type="hidden" name="nomeBD" value="<%= nomeBD%>" />
                                    <input type="hidden" name="paginaRedirect" value="Agencia/Configuracao/atualizar_SRO" />
                                    <button type="submit" name="save" id="sub" class="positive" onclick="abrirTelaEspera();"><img src="../../imagensNew/refresh.png" alt=""/> ATUALIZAR AGORA</button>
                                </div>
                                </dd>
                            </li>
                        </ul>
                    </form>
                    <div style="width: 100%;clear: both;"></div>                
                </div>
            </div>
        </div>
    </body>
</html>
<%}%>