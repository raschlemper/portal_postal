<%@page import="Controle.contrCliente"%>
<%
    String nomeBD = request.getParameter("nomeBD");
    int idCliente = Integer.parseInt(request.getParameter("idCliente"));
    int usaEtiquetador = Integer.parseInt(request.getParameter("usaEtiquetador"));
    contrCliente.alterarUsaEtiquetador(usaEtiquetador, idCliente, nomeBD);
%>