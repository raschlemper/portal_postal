<%@page import="Entidade.Destinatario"%>
<%@page import="Controle.contrDestinatario"%>
<%@page import="java.math.BigDecimal"%>
<%@page import="Util.FormataString"%>
<%@ page import="java.sql.*, javax.swing.*, java.util.*, java.text.SimpleDateFormat, java.text.DecimalFormat, java.util.Date" %>
<%
    response.setContentType("application/xls");
    response.setHeader("Content-disposition", "attachment; filename=relatorio_destinatarios.xls");
    response.setHeader("Cache-Control", "no-cache");

    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    String nomeBD = request.getParameter("nomeBD");

    int idCli = Integer.parseInt(String.valueOf(session.getAttribute("idCliente")));

    ArrayList<Destinatario> lista = contrDestinatario.pesquisa(idCli, "", "", "", "", "", "", "", "", nomeBD, "");
    if (lista.size() > 0) {

%>
<table cellpadding="0" cellspacing="0" border="0">
    <thead>
        <tr>
            <th><h3>COD.</h3></th>
            <th><h3>NOME</h3></th>
            <th><h3>EMPRESA</h3></th>
            <th><h3>CPF/CNPJ</h3></th>
            <th><h3>Endereço</h3></th>
            <th><h3>NUMERO</h3></th>
            <th><h3>COMPLEMENTO</h3></th>
            <th><h3>BAIRRO</h3></th>
            <th><h3>CIDADE</h3></th>
            <th><h3>UF</h3></th>
            <th><h3>CEP</h3></th>
            <th><h3>EMAIL</h3></th>
            <th><h3>CELULAR</h3></th>
            <th><h3>TAGS</h3></th>
        </tr>
</thead>
<tbody>
    <%    
        for (int i = 0; i < lista.size(); i++) {
        Destinatario des = lista.get(i);
        String email ="";
        if(des.getEmail() != null){
            email = des.getEmail();
        }
        String cel ="";
        if(des.getCelular() != null){
            cel = des.getCelular();
        }
        String tag ="";
        if(des.getTags() != null){
            tag = des.getTags();
        }
        
    %>
    <tr style="cursor:default;">
        <td><%= des.getIdDestinatario()%></td>
        <td><%= des.getNome()%></td>
        <td><%= des.getEmpresa()%></td>
        <td><%= des.getCpf_cnpj()%></td>
        <td><%= des.getEndereco()%></td>
        <td><%= des.getNumero()%></td>
        <td><%= des.getComplemento() %></td>
        <td><%= des.getBairro()%></td>
        <td><%= des.getCidade()%></td>
        <td><%= des.getUf()%></td>
        <td><%= des.getCep()%></td>
        <td><%= email %></td>
        <td><%= cel%></td>
        <td><%= tag%></td>
    </tr>
    <%}%>

</tbody>
</table>
<%} else {%>
<div align='center' style='padding-top:25px;background:#fc8878;color:black;height:50px;width:100%;font-size:20px;font-weight:bold;'>Nenhum Objeto Encontrado!</div>
<%}%>