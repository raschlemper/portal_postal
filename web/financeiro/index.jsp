<%@ page import = "java.util.ArrayList,java.sql.Timestamp, java.util.Date, java.text.SimpleDateFormat"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
    if (session.getAttribute("usuario") == null) {
        response.sendRedirect( request.getContextPath() + "/index.jsp?msgLog=3");
    }
%>
<html lang="en" ng-app="Financeiro">
    <head>
        <title> Portal Postal</title>
        <%@ include file="../NewTemplate/includes/Css_js.jsp" %>
    </head> 
    <body>       
        <%@ include file="../NewTemplate/includes/navBarTop.jsp" %>
        <div id="wrapper">
            <%@ include file="../NewTemplate/includes/menu_agencia_bootstrap.jsp" %>
            <div ui-view></div>
        </div>    

        <script src="${pageContext.request.contextPath}/plugins/angularjs/angular.min.js"></script>
        <script src="${pageContext.request.contextPath}/plugins/angularjs/angular-cookies/angular-cookies.min.js"></script>
        <script src="${pageContext.request.contextPath}/plugins/angularjs/angular-resource/angular-resource.min.js"></script>
        <script src="${pageContext.request.contextPath}/plugins/angularjs/angular-sanitize/angular-sanitize.min.js"></script>
        <script src="${pageContext.request.contextPath}/plugins/angularjs/angular-ui-router/angular-ui-router.min.js"></script>
       
        <script type="text/javascript" charset="UTF-8" src="js/app.js"></script>
        <script type="text/javascript" charset="UTF-8" src="js/controller/financeiroController.js"></script>
    </body>
</html>
