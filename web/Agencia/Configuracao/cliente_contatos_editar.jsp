<%@page import="Entidade.Contato"%>
<%@page import="Controle.contrContato"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page import = "java.util.ArrayList,java.sql.Timestamp, java.util.Date, java.text.SimpleDateFormat"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
            /*AJAX*/
            response.setContentType("text/xml");
            response.setHeader("Cache-Control", "no-cache");

            String nomeBD = (String) session.getAttribute("empresa");
            if (nomeBD != null) {

                int idContato = Integer.parseInt(request.getParameter("idContato"));
                Contato ct = contrContato.consultaContatoPorId(idContato, nomeBD);
%>

<div style="width: 100%; margin: 15px;">
    <div style="width: 95%; text-align: left;">
        <div style='float:right;'><a onclick='chamaDivProtecao();' href='#' class='botaoClose'>Fechar</a></div>
        <div id='titulo1'>Editar Contato do Cliente</div>
    </div>
    <img style="margin-bottom: 25px;" width="100%" src="../../imagensNew/linha.jpg"/>
    <form name='form5' action='../../ServEditarContato' method='post'>
        <ul style="width: 95%; text-align: left;" class="ul_formulario">
            <li>
                <dd><label>Nome:</label><input type="text" name="contatoNome" id="contatoNome" value="<%= ct.getContato()%>" /></dd>
                <dd><label>Telefone:</label><input type="text" name="contatoFone" id="contatoFone" value="<%= ct.getFoneramal()%>" maxlength="14" onkeypress="mascara(this,maskTelefone)" /></dd>
                <dd><label>Email:</label><input type="text" name="contatoEmail" id="contatoEmail" value="<%= ct.getEmail()%>" /></dd>
                <dd><label>Setor:</label><input type="text" name="contatoSetor" id="contatoSetor" value="<%= ct.getSetor()%>" /></dd>
            </li>
        </ul>
        <div class="buttons">
            <input type='hidden' name='idCliente' value='<%= ct.getIdEmpresa()%>' />
            <input type='hidden' name='idContato' value='<%= ct.getIdContato()%>' />

            <button type='button' class='positive' onclick='return preencherCamposEdit();'><img src="../../imagensNew/tick_circle.png" /> SALVAR DADOS</button>
            <button type="button" class="negative" onClick='chamaDivProtecao();'><img src="../../imagensNew/cross_circle.png" /> CANCELAR</button>
        </div>
    </form>
</div>
<%}%>