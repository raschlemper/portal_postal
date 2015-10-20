<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="Coleta.Controle.contrColeta"%>
<%
            /*AJAX*/
            response.setContentType("text/xml");
            response.setHeader("Cache-Control", "no-cache");

            String nomeBD = (String) session.getAttribute("empresa");
            if (nomeBD != null) {

               
                String obs = request.getParameter("obs");
                int idColeta = Integer.parseInt(request.getParameter("idColeta"));
                int idColetador = Integer.parseInt(request.getParameter("idColetador"));
               int i = Coleta.Controle.contrColeta.alterarObs(idColeta, obs, nomeBD);
               if(i != -1){
                   %>c<%
               }else {
                   %>error<% 
               }
}%>