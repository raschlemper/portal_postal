<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList"%>
<%@page import="Entidade.Contato"%>
<%
            /*AJAX*/
            response.setContentType("text/xml");
            response.setHeader("Cache-Control", "no-cache");
           

            String nomeBD = (String) session.getAttribute("empresa");
            if (nomeBD != null) {
                int idCliente = Integer.parseInt(request.getParameter("id").toString());
%>

<select name="contato" id="contato" onChange="ajaxDadosDoContato(<%= idCliente%>);">
    <option value="-1">-- Selecione um contato --</option>
    <%
                    ArrayList<Contato> listaContatos = Controle.contrContato.consultaContatos(idCliente, nomeBD);
                    for (int j = 0; j < listaContatos.size(); j++) {
                        Contato con = listaContatos.get(j);
    %>
    <option value="<%= con.getIdContato() %>"><%= con.getContato() %></option>
    <%}%>
</select>
<%}%>