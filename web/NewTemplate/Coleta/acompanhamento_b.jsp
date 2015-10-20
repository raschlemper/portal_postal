<%@page import="Coleta.Entidade.Coletador"%>
<%@page import="Coleta.Entidade.Coleta"%>
<%@page import="Entidade.Clientes"%>
<%@page import="Coleta.Controle.contrColeta"%>
<%@page import="Coleta.Controle.contrColetador"%>
<%@page import="Controle.contrCliente"%>
<%@ page import = "java.text.DateFormat,java.util.ArrayList,java.sql.Timestamp,java.util.Calendar, java.util.GregorianCalendar, java.util.Date, java.text.SimpleDateFormat"%>
<% response.setHeader("Cache-Control", "no-cache"); //HTTP 1.1
    response.setHeader("Pragma", "no-cache"); //HTTP 1.0
    response.setDateHeader("Expires", 0); //prevent caching at the proxy server
    String nomeBD = (String) session.getAttribute("empresa");
    if (nomeBD == null) {
        response.sendRedirect("../index.jsp?msgLog=3");
    } else {
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <title>Portal Postal</title>
        <%@ include file="../includes/Css_js.jsp" %>
        <script src="http://www.amcharts.com/lib/3/amcharts.js"></script>
        <script src="http://www.amcharts.com/lib/3/serial.js"></script>
        <script src="http://www.amcharts.com/lib/3/themes/light.js"></script>
    </head>
    <body>
        <script type="text/javascript" charset="UTF-8">
            var auto_refresh = setInterval(function() {
                $('#reload').fadeOut(1000).load('ajax/reload.jsp').fadeIn(2000);
            }, 100000); // refresh every 10000 milliseconds
        </script>
        <jsp:include page="../includes/navBarTop.jsp"></jsp:include>
            <div id="wrapper">
            <%@ include file="../includes/menu_agencia_bootstrap.jsp" %>
            <div id="page-content-wrapper">
                <div class="container-fluid">
                    <div id="page-wrapper">
                        <div class="row" >
                            <div class="col-md-12">
                                <h4 class="page-header"><b class="text-primary"><i class="fa fa-truck"></i> Coleta</b> > <small>Acompanhamento Coleta</small></h4>
                            </div>
                        </div>
                        <div id="reload"></div>
                    </div>
                </div>
                <%}%>                    
            </div>
        </div>
        <script type="text/javascript" charset="UTF-8">
            jQuery(document).ready(function() {
                $('#reload').load('ajax/reload.jsp').fadeIn('slow');
                fechaMsg();
            });
        </script>
    </body>
</html>

