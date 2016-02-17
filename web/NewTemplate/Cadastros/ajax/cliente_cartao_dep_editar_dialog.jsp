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
        <div class="row form-horizontal">
            <div class="col-xs-12 col-sm-8 col-md-6 col-lg-6">
                <label class="small">Nome do Departamento: </label>
                <div class="input-group">
                    <span class="input-group-addon" ><i class="fa fa-sitemap"></i></span>
                    <input class="form-control" autocomplete="off" type='text' name='nome' placeholder="Nome do Depto." value='<%= depto%>' maxlength="40" />
                </div>
            </div>
        </div>
        <div class="row form-horizontal">
            <div class="col-xs-12 col-sm-6 col-md-4 col-lg-4">
                <label class="small">Cartão de Postagem: </label>
                <div class="input-group">
                    <span class="input-group-addon" ><i class="fa fa-asterisk"></i></span>
                    <input class="form-control" autocomplete="off" type='text' name='cartao' placeholder="Depto. sem cartão" value='<%= cartao%>' maxlength="10" />
                </div>
            </div>
        </div>
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