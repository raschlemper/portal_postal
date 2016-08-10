<%@page import="Controle.ContrServicoAbrangencia"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%
            response.setContentType("text/xml");
            response.setHeader("Cache-Control", "no-cache");
            String resultado = "sessaoexpirada";

            Integer[] CEPS_SUSPENSOS = {
                20271080, 20271090, 20271100, 20550140, 20271150, 
                /*20271110, 20271111, 20271130, 20271160, 20271260, 20550170, dia 12*/
                20756120, 20756121, 20770001,
                20770002, 20755330, 20756116, 20756115, 20760225, 20760226, 20770061,
                20770062, 20770010, 20770006, 20770060, 20755310, 20755320, 20756085,
                20755300, 20755290, 20755280, 20770100, 20770080, 20770070, 20770210,
                20770120, 20770090, 20755250, 22780160, 22783127, 22783119, 22783135,
                22783145, 22790714, 22775023, 22775024, 22775025, 22775026, 22775027, 
                22775028, 22775029, 22775030, 22775031, 22775032, 22775033, 22775034, 
                22775035, 22775036, 22775037, 22775038, 22775039, 22775040, 
                22775060, 22775120, 22775051,
                21615220, 21615310, 21615435, 21745590, 21745520, 21745420, 21750330,
                21750320, 21735035, 21620070, 21853000
            };

            String nomeBD = (String) session.getAttribute("nomeBD");
            if (nomeBD != null) {            
                int cep = Integer.parseInt(request.getParameter("cep").replace("-", "").replace(".", ""));
                String servico = request.getParameter("servico");        
                resultado = "aceita";  
                for (int cs : CEPS_SUSPENSOS) {
                    if (!servico.equals("CARTA") && cs == cep) {
                        resultado = "MSGRIO";
                    }
                }               
            }
            response.getWriter().write(resultado);
%>