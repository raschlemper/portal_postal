
<%@page import="Entidade.Contato"%>
<%
            /*AJAX*/
            response.setContentType("text/xml");
            response.setHeader("Cache-Control", "no-cache");
            String resultado = "sessaoexpirada";

            String nomeBD = (String) session.getAttribute("empresa");
            if (nomeBD != null) {
                int idCliente = Integer.parseInt(request.getParameter("idCliente").toString());
%>
<%@page import="java.util.ArrayList"%>
<select style="width:250px;" name="contato" id="contato" onChange="buscarDadosContato(<%= idCliente%>);">
    <option value="">-- Selecione --</option>
    <%
                    ArrayList<Contato> listaContatos = Controle.contrContato.consultaContatos(idCliente, nomeBD);
                    for (int j = 0; j < listaContatos.size(); j++) {
                        Contato con = listaContatos.get(j);
    %>
    <option value="<%= con.getIdContato() %>"><%= con.getContato() %></option>
    <%}%>
</select>
<%}%>