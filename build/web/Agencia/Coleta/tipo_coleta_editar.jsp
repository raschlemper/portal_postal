
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page import = "java.util.ArrayList,java.sql.Timestamp, java.util.Date, java.text.SimpleDateFormat"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
            /*AJAX*/
            response.setContentType("text/xml");
            response.setHeader("Cache-Control", "no-cache");

            String nomeBD = (String) session.getAttribute("empresa");
            if (nomeBD != null) {

                int idTipoColeta = Integer.parseInt(request.getParameter("idTipoColeta"));
                Coleta.Entidade.TipoColeta tipoColeta = Coleta.Controle.contrTipoColeta.consultaTipoColetaById(idTipoColeta, nomeBD);
                String tipo = tipoColeta.getTipo();
%>

<div style="width: 100%; margin: 15px;">
    <div style="width: 95%; text-align: left;">
        <div style='float:right;'><a onclick='chamaDivProtecao();' href='#' class='botaoClose'>Fechar</a></div>
        <div id='titulo1'>Editar Tipo de Coleta</div>
    </div>
    <img style="margin-bottom: 25px;" width="100%" src="../../imagensNew/linha.jpg"/>

    <form name='form5' action='../../ServEditarTipoColeta' method='post'>
        <ul style="width: 95%; text-align: left;" class="ul_formulario">
            <li>
                <dd>
                    <label>Nome do Tipo da Coleta: </label>
                    <input style='width:200px;' name='tipo' type='text' value='<%= tipo%>' />
                </dd>
            </li>
        </ul>

        <div class="buttons">
            <input type='hidden' name='idTipoColeta' value='<%= idTipoColeta%>' />
            <button type="button" class="positive" onclick="preencherCamposEdit();"><img src="../../imagensNew/tick_circle.png" /> SALVAR DADOS</button>
            <button type="button" class="negative" onClick='chamaDivProtecao();'><img src="../../imagensNew/cross_circle.png" /> CANCELAR</button>
        </div>
    </form>

</div>
<%}%>