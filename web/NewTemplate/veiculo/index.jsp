<%@ page import = "java.util.ArrayList,java.sql.Timestamp, java.util.Date, java.text.SimpleDateFormat"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
    if (session.getAttribute("usuario") == null) {
        response.sendRedirect( request.getContextPath() + "/index.jsp?msgLog=3");
    }
%>
<html lang="en" ng-app="Veiculo">
    <head>
        <title> Portal Postal</title>
        <base href="${pageContext.request.contextPath}/NewTemplate/veiculo/"/>
        <%@ include file="../includes/Css_js.jsp" %>
    </head> 
    <body>       
        <%@ include file="../includes/navBarTop.jsp" %>
        <div id="wrapper">
            <%@ include file="../includes/menu_agencia_bootstrap.jsp" %>
            <div ui-view></div>
        </div> 
            
        <script type="text/javascript">
            var _contextPath = "${pageContext.request.contextPath}";
        </script>

        <script src="${pageContext.request.contextPath}/plugins/angular/angular.min.js"></script>
        <script src="${pageContext.request.contextPath}/plugins/angular/angular-cookies/angular-cookies.min.js"></script>
        <script src="${pageContext.request.contextPath}/plugins/angular/angular-resource/angular-resource.min.js"></script>
        <script src="${pageContext.request.contextPath}/plugins/angular/angular-sanitize/angular-sanitize.min.js"></script>
        <script src="${pageContext.request.contextPath}/plugins/angular/angular-animate/angular-animate.min.js"></script>
        <script src="${pageContext.request.contextPath}/plugins/angular/angular-locale-pt-br/angular-locale_pt-br.js"></script>
        <script src="${pageContext.request.contextPath}/plugins/angular/angular-ui-router/angular-ui-router.min.js"></script>
        <script src="${pageContext.request.contextPath}/plugins/angular/angular-bootstrap/ui-bootstrap.min.js"></script>
        <script src="${pageContext.request.contextPath}/plugins/angular/angular-bootstrap/ui-bootstrap-tpls.min.js"></script>
        <script src="${pageContext.request.contextPath}/plugins/angular/angular-ui-mask/mask.min.js"></script>
        <script src="${pageContext.request.contextPath}/plugins/angular/angular-ui-input-mask/angular-input-masks-standalone.min.js"></script>
        <script src="${pageContext.request.contextPath}/plugins/angular/angular-veasy-table/dist/js/veasy-table.min.js"></script>
        <script src="${pageContext.request.contextPath}/plugins/angular/angular-veasy-table/dist/js/veasy-table-tpls.min.js"></script>
       
        <script type="text/javascript" charset="UTF-8" src="js/app.js"></script>        
        <script type="text/javascript" charset="UTF-8" src="js/app.constants.js"></script>
        <script type="text/javascript" charset="UTF-8" src="js/interceptor/loadingInterceptor.js"></script>
        <script type="text/javascript" charset="UTF-8" src="js/controller/modalMessageController.js"></script>
        <script type="text/javascript" charset="UTF-8" src="js/controller/modalExcluirController.js"></script>
        <script type="text/javascript" charset="UTF-8" src="js/controller/veiculoController.js"></script>
        <script type="text/javascript" charset="UTF-8" src="js/controller/modalVeiculoController.js"></script>
        <script type="text/javascript" charset="UTF-8" src="js/service/fipeService.js"></script>
        <script type="text/javascript" charset="UTF-8" src="js/service/modalService.js"></script>
        <script type="text/javascript" charset="UTF-8" src="js/service/listaService.js"></script>
        <script type="text/javascript" charset="UTF-8" src="js/service/veiculoService.js"></script>
        <script type="text/javascript" charset="UTF-8" src="js/filter/placaFilter.js"></script>
        <script type="text/javascript" charset="UTF-8" src="js/filter/renavamFilter.js"></script>

    </body>
</html>
