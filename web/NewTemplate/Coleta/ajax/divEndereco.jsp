<%@page import="br.com.portalpostal.entity.ClienteDepartamento"%>
<%@page import="Coleta.Controle.contrColetaFixa"%>
<%@page import="Coleta.Entidade.Coleta"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.ArrayList"%>
<%@page import="br.com.portalpostal.entity.Cliente"%>
<%@page import="br.com.portalpostal.componentes.ConexaoEntityManager"%>
<%@page import="br.com.portalpostal.providers.ProviderCliente"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    String nomeBD = (String) session.getAttribute("empresa");
    Integer idCliente = Integer.valueOf(request.getParameter("idCliente"));
    Integer idDepartamento = Integer.valueOf(request.getParameter("idDepartamento"));
    
    ProviderCliente providerCliente = new ProviderCliente(ConexaoEntityManager.getConnection(nomeBD));
    
    Cliente cliente = providerCliente.findClienteById(idCliente);
    ClienteDepartamento clienteDepartamento = null;
    for(ClienteDepartamento departamento : cliente.getDepartamentos()){
        if(departamento.getAtivo()==1 && 
           departamento.getClienteDepartamentoPK().getIdDepartamento()==idDepartamento &&
           departamento.getTemEndereco()==1){
                clienteDepartamento = departamento;
            break;
        }
    }
    
    Map<Integer, ArrayList<Coleta>> lsC = contrColetaFixa.verificaExistenciaRotaParaCliente(nomeBD);
    
    if(clienteDepartamento!=null){
%>
<div class="row form-horizontal">
    <div class="col-xs-6">
        <div><strong><%= cliente.getNome()%></strong></div>
        <div><%=  clienteDepartamento.getLogradouro()%> - <%=  clienteDepartamento.getBairro()%></div>
        <div>Fone:<%=  cliente.getTelefone()%></div>
        <div><a href="mailto:#"><%= cliente.getEmail()%></a></div>
        <div><strong>Rota(s):</strong></div>
        <%
            if (lsC.containsKey(cliente.getCodigo())) {
                ArrayList<Coleta> colts = lsC.get(cliente.getCodigo());
                for (Coleta cl : colts) {
                    String fx = "eventual";
                    if (cl.getIdTipo() == 1) {
                        fx = "fixo";
                    }%>
        <div><%= cl.getNomeColetador()%> - <%= cl.getTipoColeta()%> - <%=fx%></div>
        <% }
            }%>

    </div>
</div>
<%}else{%>            
<div class="row form-horizontal">
    <div class="col-xs-6">
        <div><strong><%= cliente.getNome()%></strong></div>
        <div><%=  cliente.getEndereco()%> - <%=  cliente.getBairro()%></div>
        <div>Fone:<%=cliente.getTelefone()%></div>
        <div><a href="mailto:#"><%= cliente.getEmail()%></a></div>
        <div><strong>Rota(s):</strong></div>
        <%
            if (lsC.containsKey(cliente.getCodigo())) {
                ArrayList<Coleta> colts = lsC.get(cliente.getCodigo());
                for (Coleta cl : colts) {
                    String fx = "eventual";
                    if (cl.getIdTipo() == 1) {
                        fx = "fixo";
                    }%>
        <div><%= cl.getNomeColetador()%> - <%= cl.getTipoColeta()%> - <%=fx%></div>
        <% }
            }%>

    </div>
</div>
<%}%>
