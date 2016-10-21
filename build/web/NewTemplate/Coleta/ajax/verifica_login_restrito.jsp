<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    /*AJAX*/
    response.setContentType("text/xml");
    response.setHeader("Cache-Control", "no-cache");

    String nomeBD = (String) session.getAttribute("empresa");
    if (nomeBD != null) {
        String servlet = request.getParameter("servlet");
        String mensagem = request.getParameter("mensagem");
        String idUsuario = request.getParameter("idUsuario");
%>
<form name='formLoginRestrito' id='formLoginRestrito' action='<%= servlet%>' method='post'>
    <ul class="list-unstyled">
        <li class="list-group-item list-group-heading">
            <b>Login de Administrador</b>                
        </li>
        <li class="list-group-item">
            <div class="row">
                <div class="col-xs-12 col-sm-6 col-md-4 col-lg-4">
                    <label class="small">Login: </label>
                    <input class="form-control" name='senha' id="login" type='text' />
                </div>
            </div>            
        </li>
        <li class="list-group-item">
            <div class="row">
                <div class="col-xs-12 col-sm-6 col-md-4 col-lg-4">
                    <label class="small">Senha: </label>
                    <input class="form-control" name='senha' id="senha" type='password' />
                </div>
            </div>
        </li>
    </ul>
    <div class="alert alert-danger">
        <b><%=mensagem%></b>
    </div>
    <input type="hidden" name="idUsuario" value="<%= idUsuario%>"/>
</form>
<%}%>