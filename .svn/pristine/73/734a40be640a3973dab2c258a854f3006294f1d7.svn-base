<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.sql.*, javax.swing.*, java.util.*" %>
<%
            /*AJAX*/
            response.setContentType("text/xml");
            response.setHeader("Cache-Control", "no-cache");
            String resultado = "sessaoexpirada";

            String nomeBD = (String) session.getAttribute("empresa");
            if (nomeBD != null) {

                resultado = ";;";
                if (request.getParameter("idContato") != null && !request.getParameter("idContato").equals("") && !request.getParameter("idContato").equals("0")) {
                int idContato = Integer.parseInt(request.getParameter("idContato"));
                    Entidade.Contato cont = Controle.contrContato.consultaContatoPorId(idContato, nomeBD);
                    if (cont != null) {
                        String email = cont.getEmail();
                        if(email == null || email.equals("")){
                            email = "- - -";
                        }
                        String fone = cont.getFoneramal();
                        if(fone == null || fone.equals("")){
                            fone = "- - -";
                        }
                        String setor = cont.getSetor();
                        if(setor == null || setor.equals("")){
                            setor = "- - -";
                        }

                        resultado = email + ";" + fone + ";" + setor;
                    }
                }

            }

            response.getWriter().write(resultado);
%>
