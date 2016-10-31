<%@page import="Entidade.Endereco"%>
<%
    String cep = request.getParameter("cep");
    Endereco end = Util.PesquisarCep.pesquisaCEP(cep);
%>
<%= end.getCep()+";"+end.getUf()+";"+end.getCidade()+";"+end.getBairro()+";"+end.getLogradouro() %>