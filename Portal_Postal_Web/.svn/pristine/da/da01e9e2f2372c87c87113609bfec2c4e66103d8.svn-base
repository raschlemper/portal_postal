<%@page import="Controle.contrDestinatario"%>
<%
    String nomePesquisa = request.getParameter("nomePesquisa");
    String servico = request.getParameter("servico");
    String tipoDestino = request.getParameter("tipoDestino");
    
    String destino = "NAC";
    if(servico.equals("OUTROS") && tipoDestino.contains("INT")){
        destino = "INT";
    }
    
    System.out.println(tipoDestino+" - "+servico);
    String nomeBD = (String) session.getAttribute("nomeBD");
    
    int idCli = Integer.parseInt(String.valueOf(session.getAttribute("idCliente")));
    String autoCompleteArray = contrDestinatario.consultaDestinatarioAutoComplete(idCli, nomePesquisa, destino, nomeBD);
    
    out.print(autoCompleteArray);
%>