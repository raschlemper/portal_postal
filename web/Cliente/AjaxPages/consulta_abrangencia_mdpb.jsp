<%@page import="Controle.ContrServicoAbrangencia"%>
<%
         
            String resultado ="erro";
            String nomeBD = (String) session.getAttribute("nomeBD");
            if (nomeBD != null) {            
                int cep = Integer.parseInt(request.getParameter("cep").replace("-", ""));
                   
                resultado = ContrServicoAbrangencia.verificaMDPBxCep(cep, nomeBD);
               
            }
%><%=resultado%>