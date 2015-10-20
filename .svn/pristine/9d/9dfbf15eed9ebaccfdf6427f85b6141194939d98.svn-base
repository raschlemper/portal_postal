<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList"%>
<%@page import="Entidade.Endereco"%>
<%@page import="Controle.ContrCep"%>
<select name="pais" id="pais">
    <option value="">-- Selecione um País --</option>
<%
    String codECT = request.getParameter("codECT");
    ArrayList<Endereco> listaPaises = ContrCep.pesquisaPaises(codECT);
    for(Endereco e : listaPaises){
        out.println("<option value='"+e.getSiglaPais()+";"+e.getPais()+"'>"+e.getPais()+"</option>");
    }
%>
</select>