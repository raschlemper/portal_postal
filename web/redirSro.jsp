<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">        
        <%
            String sro = request.getParameter("sro");
            
            %>
    </head>
    <body onload="document.frmSRO.submit();">
        <form name="frmSRO" id="frmSRO" method="post" action="http://www2.correios.com.br/sistemas/rastreamento/Resultado.cfm">
            <input type="hidden" name="objetos" id="objetos" value="<%=sro%>" />
        </form> 
        
    </body>
    
</html>
