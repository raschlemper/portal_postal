<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page import = "java.util.ArrayList,java.sql.Timestamp, java.util.Date, java.text.SimpleDateFormat"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
            /*AJAX*/
            response.setContentType("text/xml");
            response.setHeader("Cache-Control", "no-cache");

            String nomeBD = (String) session.getAttribute("empresa");
            if (nomeBD != null) {

                int idColetador = Integer.parseInt(request.getParameter("idColetador"));
                Coleta.Entidade.Coletador col = Coleta.Controle.contrColetador.consultaColetadoresById(idColetador, nomeBD);
                String nome = col.getNome();
                String telefone = col.getTelefone();
                String login = col.getLogin();
                int rota = col.getRota();
%>

<div style="width: 100%; margin: 15px;">
    <div style="width: 95%; text-align: left;">
        <div style='float:right;'><a onclick='chamaDivProtecao();' href='#' class='botaoClose'>Fechar</a></div>
        <div id='titulo1'>Editar Coletador</div>
    </div>
    <img style="margin-bottom: 25px;" width="100%" src="../../imagensNew/linha.jpg"/>
    <form name='form5' action='../../ServEditarColetador' method='post'>
        <ul style="width: 95%; text-align: left;" class="ul_formulario">
            <li>
                <dd>
                    <label>Nome do Coletador: </label>
                    <input type='text' name='nome' value='<%= nome%>' />
                </dd>
                <dd>
                    <label>Telefone:</label>
                    <input type='text' name='telefone' maxlength='14' onKeyPress='mascara(this,maskTelefone)' value='<%= telefone%>' />
                </dd>
            </li>
            <li>
                <dd>
                    <label>Login</label>
                    <input type='text' autocomplete="off" name='login' value='<%= login%>' onkeyup='confereLoginColetadorEditar(this.value);' />
                    <input type='hidden' name='loginaux' value='<%= login%>' />
                    <div id='fooEditar'></div>
                </dd>
                <dd>
                    <label>Nova Senha:</label>
                    <input type='password' name='senha' />
                </dd>
                <dd>
                    <label>Repita a Senha:</label>
                    <input type='password' name='senha2' />
                </dd>
            </li>
            <li>
                <dd style='width: 100%; color:red; padding-top: 35px;'>*Obs.: Caso não queira mudar a senha deixe os campos da senha em branco!</dd>
            </li>
        </ul>

        <div class="buttons">
            <input type='hidden' name='rota' value='<%= rota%>' />
            <input type='hidden' name='idColetador' value='<%= idColetador%>' />
            <button type="button" class="positive" onclick='return preencherCamposEdit();'><img src="../../imagensNew/tick_circle.png" /> SALVAR DADOS</button>
            <button type="button" class="negative" onClick='chamaDivProtecao();'><img src="../../imagensNew/cross_circle.png" /> CANCELAR</button>
        </div>
    </form>


</div>

<%}%>