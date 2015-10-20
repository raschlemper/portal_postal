<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.ArrayList"%>
<%@ page contentType="text/html" pageEncoding="ISO-8859-1"%>
<%
            /*AJAX*/
            request.setCharacterEncoding("ISO-8859-1");
            response.setCharacterEncoding("ISO-8859-1");
            response.setContentType("text/xml");
            response.setHeader("Cache-Control", "no-cache");
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            String resultado = "sessaoexpirada";

            String nomeBD = (String) session.getAttribute("empresa");
            if (nomeBD != null) {
                resultado = "";
                String idColeta = request.getParameter("idColeta");
                if (!idColeta.equals("")) {
                    int idColeta1 = Integer.parseInt(idColeta);
                    ArrayList<Entidade.LogColeta> lista = Controle.ContrLogColeta.consultaLogColetaById(idColeta1, nomeBD);
                    for(int i=0; i<lista.size(); i++) {
                        Entidade.LogColeta lc = lista.get(i);
                        String nomeUsuario = lc.getNomeUsuario();
                        String acao = lc.getAcao();
                        String dataHora = sdf.format(lc.getDataHora());

                        resultado += "<b>"+dataHora + " - " + nomeUsuario + ":</b> " + acao+"<br/>";
                    }
                }
                resultado += ";" + idColeta;

            }
            response.getWriter().write(resultado);
%>