
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page import = "java.util.ArrayList,java.sql.Timestamp, java.util.Date, java.text.SimpleDateFormat"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
            /*AJAX*/
            response.setContentType("text/xml");
            response.setHeader("Cache-Control", "no-cache");

            String nomeBD = (String) session.getAttribute("empresa");
            if (nomeBD != null) {

                String servlet = request.getParameter("servlet");
                String mensagem= request.getParameter("mensagem");
                String idUsuario = request.getParameter("idUsuario");
%>
<div style="width: 100%; margin-top: 15px;">
    <div style="width: 95%; text-align: left;">
        <div style='float:right;'><a onclick='chamaDivProtecao();' href='#' class='botaoClose'>Fechar</a></div>
        <div id='titulo1'>Acesso Restrito</div>
    </div>
    <img style="margin-bottom: 25px;" width="100%" src="../../imagensNew/linha.jpg"/>

    <form name='formLoginRestrito' action='<%= servlet %>' method='post'>
        <ul style="width: 95%; text-align: left;" class="ul_formulario">
            <li>
                <dd>
                    <label>Login: </label>
                    <input style='width:100px;' name='senha' id="login" type='text' />
                </dd>
            </li>
            <li>
                <dd>
                    <label>Senha: </label>
                    <input style='width:100px;' name='senha' id="senha" type='password' />
                </dd>
            </li>
            <li>
                <dd>
                    <b style="color: red;"><%=mensagem%></b>
                </dd>
            </li>
        </ul>

        <div class="buttons">
            <input type="hidden" name="idUsuario" value="<%= idUsuario %>"/>
            <button type="button" class="positive" onclick="loginRestrito();"><img src="../../imagensNew/tick_circle.png" /> CONTINUAR</button>
            <button type="button" class="negative" onClick='chamaDivProtecao();'><img src="../../imagensNew/cross_circle.png" /> CANCELAR</button>
        </div>
    </form>

</div>
<%}%>