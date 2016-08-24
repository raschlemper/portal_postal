<%@page import="Controle.ContrServicoAbrangencia"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%
            response.setContentType("text/xml");
            response.setHeader("Cache-Control", "no-cache");
            String resultado = "sessaoexpirada";

            Integer[] CEPS_SUSPENSOS = {-1};

            String nomeBD = (String) session.getAttribute("nomeBD");
            if (nomeBD != null) {            
                int cep = Integer.parseInt(request.getParameter("cep").replace("-", "").replace(".", ""));
                String servico = request.getParameter("servico");        
                resultado = servico;         
                if(ContrServicoAbrangencia.verificaByCepServico(cep, servico, nomeBD)){
                    resultado = "aceita";
                }     
                for (int cs : CEPS_SUSPENSOS) {
                    if (!servico.equals("CARTA") && cs == cep) {
                        resultado = "MSGRIO";
                    }
                }               
            }
            response.getWriter().write(resultado);
%>