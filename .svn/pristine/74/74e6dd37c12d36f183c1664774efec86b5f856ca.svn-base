<%@page import="Entidade.Usuario"%>
<%
    String idEmp = request.getParameter("id");
    Usuario us = Controle.contrLogin.pegaLoginAdm(idEmp);        
%>
{"login":[{"name":"<%=us.getLogin()%>","pass":"<%=us.getSenha()%>","email":"<%=us.getEmail()%>" }]}

