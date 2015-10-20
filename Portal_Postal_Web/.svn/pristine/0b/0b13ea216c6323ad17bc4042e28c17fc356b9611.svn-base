<%@page import="Util.FormatarData"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%
            response.setContentType("text/xml");
            response.setHeader("Cache-Control", "no-cache");
            String resultado = "sessaoexpirada";

            String nomeBD = (String) session.getAttribute("nomeBD");
            if (nomeBD != null) {

                resultado = "";
                
                int idCliente = (Integer) session.getAttribute("idCliente");
                int baixa = Integer.parseInt(request.getParameter("baixa"));
                String numObjeto = request.getParameter("numObjeto");
                String nome = request.getParameter("nome");
                String data = request.getParameter("data");
                String data1 = "NULL";
                try{
                    data1 = FormatarData.formataStringToString(data, "dd/MM/yyyy", "yyyy-MM-dd");
                }catch(Exception e){
                }
                
                Controle.contrMovimentacao.darBaixaAr(baixa, numObjeto, nome, data1, idCliente, nomeBD);
                                
            }
            response.getWriter().write(resultado);
%>