<%@page import="Entidade.Usuario"%>
<% 
     Usuario usrSessao = (Usuario) session.getAttribute("agf_usuario");
    if (usrSessao == null) {
        response.sendRedirect("../../index.jsp?msgLog=3");
    } else  {
    
    String type = request.getParameter("PrintType");
    int idEmpresa = (Integer) session.getAttribute("idEmpresa");
    Coleta.Controle.contrColetaFixa.setTipoEscolhaColetaDoCliente(idEmpresa, type);
    }
%>