<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page import = "java.util.ArrayList,java.sql.Timestamp, java.util.Date, java.text.SimpleDateFormat"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    /*
     * AJAX
     */
    response.setContentType("text/xml");
    response.setHeader("Cache-Control", "no-cache");
    String resultado = "sessaoexpirada";

    String nomeBD = (String) session.getAttribute("empresa");
    if (nomeBD != null) {

        int idCliente = Integer.parseInt(request.getParameter("idCliente"));
        int idDep = Integer.parseInt(request.getParameter("idDepartamento"));
        String cartao = request.getParameter("cartao");
%>

<div style="width: 100%; margin-top: 15px;">
    <div style="width: 95%; text-align: left;">
        <div style='float:right;'><a onclick='chamaDivProtecao();' href='#' class='botaoClose'>Fechar</a></div>
        <div id='titulo1'>Editar Cartão de Postagem</div>
    </div>
    <img style="margin-bottom: 25px;" width="100%" src="../../imagensNew/linha.jpg"/>
    <form name='form5' action='../../ServEditarCartaoDep' method='post'>
        <ul style="width: 95%; text-align: left;" class="ul_formulario">
            <li>
                <dd>
                    <label>Cartão de Postagem</label>
                    <input style='width:150px;' autocomplete="off" type='text' name='cartao' value='<%= cartao%>' onkeypress="mascara(this, maskNumero)" />
                </dd>
            </li>
            <li>
                <dd style='width: 100%; padding-top: 30px;color:red;'>*Obs.: Caso não tenha cartão de postagem deixe o campo em branco!</dd>
            </li>
        </ul>

        <div class="buttons">
            <input type='hidden' name='idCliente' value='<%= idCliente%>' />
            <input type='hidden' name='idDepartamento' value='<%= idDep%>' />

            <button type='submit' class='positive' ><img src="../../imagensNew/tick_circle.png" /> SALVAR DADOS</button>
            <button type="button" class="negative" onClick='chamaDivProtecao();'><img src="../../imagensNew/cross_circle.png" /> CANCELAR</button>
        </div>
    </form>
</div>
<%}%>