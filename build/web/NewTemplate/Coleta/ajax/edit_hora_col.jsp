
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="Coleta.Controle.contrColeta"%>
<%

    response.setContentType("text/xml");
    response.setHeader("Cache-Control", "no-cache");

    String nomeBD = (String) session.getAttribute("empresa");
    if (nomeBD != null) {
        String id = request.getParameter("pk");
        String nome = request.getParameter("name");
        String val = request.getParameter("value");
        int i = contrColeta.editarAjax(id, nome, val, nomeBD);
        
         if(i != -1){
                   %>c<%
               }else {
                   %>error<% 
               }
    }
    

%>