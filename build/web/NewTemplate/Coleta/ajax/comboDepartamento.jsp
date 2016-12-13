<%@page import="br.com.portalpostal.entity.ClienteDepartamento"%>
<%@page import="br.com.portalpostal.entity.Cliente"%>
<%@page import="br.com.portalpostal.componentes.ConexaoEntityManager"%>
<%@page import="br.com.portalpostal.providers.ProviderCliente"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<option value="sel">-- Selecione um Departamento --</option>
<%
String nomeBD = (String) session.getAttribute("empresa");
Integer idCliente = Integer.valueOf(request.getParameter("idCliente"));
ProviderCliente providerCliente = new ProviderCliente(ConexaoEntityManager.getConnection(nomeBD));
Cliente cliente = providerCliente.findClienteById(idCliente);
for(ClienteDepartamento departamento : cliente.getDepartamentos()){
    if(departamento.getAtivo()==1){
        out.print("<option data='"+departamento.getTemEndereco()+"' value=\""+departamento.getClienteDepartamentoPK().getIdDepartamento()+"\">"+departamento.getNomeDepartamento()+"</option>");
    }
}

%>
