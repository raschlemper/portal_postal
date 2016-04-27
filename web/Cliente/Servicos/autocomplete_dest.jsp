<%@page import="Entidade.Clientes"%>
<%@page import="Entidade.ClientesUsuario"%>
<%@page import="Controle.contrDestinatario"%>
<%
    
    ClientesUsuario us = (ClientesUsuario) session.getAttribute("usuario_sessao_cliente");
    Clientes cli = (Clientes) session.getAttribute("cliente");
        
    String nomePesquisa = request.getParameter("nomePesquisa");
    String servico = request.getParameter("servico");
    String tipoDestino = request.getParameter("tipoDestino");
    
    String destino = "NAC";
    if(servico.equals("OUTROS") && tipoDestino.contains("INT")){
        destino = "INT";
    }
    
    String nomeBD = (String) session.getAttribute("nomeBD");
    
    int idCli = Integer.parseInt(String.valueOf(session.getAttribute("idCliente")));
    String autoCompleteArray = contrDestinatario.consultaDestinatarioAutoComplete(idCli, nomePesquisa, destino, cli.getSeparar_destinatarios(), us.getDepartamentos(), nomeBD);
    
    out.print(autoCompleteArray);
%>