<%@page import="Entidade.Clientes"%>

<%
      
    if (session.getAttribute("usuario") == null) {
        response.sendRedirect("../index.jsp?msgLog=3");
    } else {

        String nomeBD = (String) session.getAttribute("empresa");
      
        String sro = request.getParameter("sro");
        
        //PESQUISA NO BANCO
        if (request.getParameter("sro") != null) {
            sro = request.getParameter("sro");
        }
        
        Clientes cli = Controle.contrCliente.consultaClienteBySRO(sro, nomeBD);
        //Clientes cli = Controle.contrCliente.consultaClienteBySRO(sro, nomeBD);
        
        if(cli == null){
            out.println("SRO inválido!. Favor digitar um SRO dos Correios Válido!");
            } else {
             out.println(cli.getNome());
            }
            
        }
%>
