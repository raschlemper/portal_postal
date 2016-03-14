<%@page import="Entidade.Clientes"%>
<%
    if (session.getAttribute("usuario") == null) {
        response.sendRedirect("../index.jsp?msgLog=3");
    } else {

        String nomeBD = (String) session.getAttribute("empresa");
        String sro = request.getParameter("sro");

        //PESQUISA NO BANCO
        Clientes cli = Controle.contrCliente.consultaClienteBySRO(sro, nomeBD);

        if (cli == null) {
            out.println("ERRO: SRO não encontrato no movimento!");
        } else if (!cli.getEmail().contains("AR")) {
            out.println("ERRO: Postagem sem AR!");
        } else {
            out.println(cli.getNome());
        }

    }
%>