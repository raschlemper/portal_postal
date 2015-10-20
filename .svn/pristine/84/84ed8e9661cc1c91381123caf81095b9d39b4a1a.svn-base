<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    response.setContentType("text/html");
    response.setHeader("Cache-Control", "no-cache");
    String nomeBD = (String) session.getAttribute("empresa");
    if (nomeBD != null) {
        int idCliente = Integer.parseInt(request.getParameter("idCliente"));
        int idDep = Integer.parseInt(request.getParameter("idDepartamento"));
        String cartao = request.getParameter("cartao");
        String depto = request.getParameter("depto");
%>
<form name='form5' action='../../ServEditarCartaoDep' method='post'>
    <ul class="list-unstyled">
        <li class="list-group-item list-group-heading">
            <b><%=depto%></b>
        </li>
        <li class="list-group-item">
            <label>Cartão de Postagem: </label>
            <input autocomplete="off" type='text' name='cartao' placeholder="Depto. sem cartão" value='<%= cartao%>' onkeypress="mascara(this, maskNumero)" />
        </li>
    </ul>
    <div class="alert alert-danger no-margin">
        <b>ATENÇÃO!</b> Caso não tenha cartão de postagem deixe o campo em branco!
    </div>
    <input type='hidden' name='idCliente' value='<%= idCliente%>' />
    <input type='hidden' name='idDepartamento' value='<%= idDep%>' />
</form>
<%}else{%>
sessaoexpirada
<%}%>