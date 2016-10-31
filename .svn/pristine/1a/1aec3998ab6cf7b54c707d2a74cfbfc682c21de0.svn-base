<%@page import="Entidade.empresas"%>
<%@page import="Entidade.Usuario"%>
<%
    Usuario usrSessao = (Usuario) session.getAttribute("agf_usuario");
    if (usrSessao == null) {
        response.sendRedirect("../../index.jsp?msgLog=3");
    } else {
        empresas emp = (empresas) session.getAttribute("emp");
        int idVendedor = Integer.parseInt(request.getParameter("idVendedor"));
        Vendedor.Controle.contrVendedores.deletarListaCliente(idVendedor, emp.getCnpj());
    }
%>