<%@page import="Entidade.Endereco"%>
<%
    String cep = request.getParameter("cep");
    if(cep == null || cep.trim().equals("")){
        cep = "0";
    }
    Endereco end = Util.PesquisarCep.pesquisaCEP(cep);
%>
{"cep":"<%=end.getCep()%>", "logradouro":"<%=end.getLogradouro()%>", "bairro":"<%= end.getBairro() %>", "cidade":"<%= end.getCidade() %>", "uf":"<%=end.getUf()%>"}