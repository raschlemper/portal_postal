<%@page import="Entidade.empresas"%>
<%@page import="Entidade.Usuario"%>
<%
    Usuario usrSessao = (Usuario) session.getAttribute("agf_usuario");
    if (usrSessao == null) {
        response.sendRedirect("../../index.jsp?msgLog=3");
    } else if (usrSessao.getListaAcessosPortalPostal().contains("405")) {
        response.sendRedirect("../../NewTemplate/Dashboard/index.jsp?msg=Usuario sem permissao!");
    } else {

        empresas emp = (empresas) session.getAttribute("emp");

        String id = request.getParameter("pk");
        String nome = request.getParameter("name");
        String val = request.getParameter("value");
        
        Controle.contrContato.editarAjax(id, nome, val, emp.getCnpj());

    }

%>

