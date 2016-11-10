<%@page import="Util.FormatarData"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%
            response.setContentType("text/xml");
            response.setHeader("Cache-Control", "no-cache");
           
            String nomeBD = (String) session.getAttribute("nomeBD");
            if (nomeBD != null) {              
                               
                String atributo = request.getParameter("atributo");
                String valor = request.getParameter("valor");
                String idPv = request.getParameter("idpv");  
                Emporium.Controle.ContrPreVenda.alterarCampo(atributo, valor, idPv, nomeBD);
                                
            }
           
%>