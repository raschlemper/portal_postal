
<%@page import="Coleta.Controle.contrColeta"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page import = "java.util.ArrayList,java.sql.Timestamp, java.util.Date, java.text.SimpleDateFormat"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
            /*AJAX*/
            response.setContentType("text/xml");
            response.setHeader("Cache-Control", "no-cache");

            String nomeBD = (String) session.getAttribute("empresa");
            if (nomeBD != null) {

                int idColeta = Integer.parseInt(request.getParameter("idColeta"));
                int idColetador = Integer.parseInt(request.getParameter("idColetador"));
                String obs = contrColeta.consultaObsByIdColeta(idColeta, nomeBD);
%>
<div style="width: 100%; margin-top: 15px;">
    <div style="width: 95%; text-align: left;">
        <div style='float:right;'><a onclick='chamaDivProtecao();' href='#' class='botaoClose'>Fechar</a></div>
        <div id='titulo1'>Alterar Observações</div>
    </div>
    <img style="margin-bottom: 25px;" width="100%" src="../../imagensNew/linha.jpg"/>

    <form name='formObs' action='../../ServAlterarObsColeta' method='post'>
        <ul style="width: 95%; text-align: left;" class="ul_formulario">
            <li>
                <dd>
                    <label>Observações: </label>
                    <textarea style="width: 420px; height: 70px;" name="obs" maxlength="180" ><%= obs%></textarea>
                </dd>
            </li>
            <li>
                <dd>
                    <b style="color: red;">Atenção! a observação anterior será perdida!</b>
                </dd>
            </li>
        </ul>

        <div class="buttons">
            <input type="hidden" name="idColeta" value="<%= idColeta %>"/>
            <input type="hidden" name="idColetador" value="<%= idColetador %>"/>
            <button type="submit" class="positive"><img src="../../imagensNew/tick_circle.png" /> SALVAR DADOS</button>
            <button type="button" class="negative" onClick='chamaDivProtecao();'><img src="../../imagensNew/cross_circle.png" /> CANCELAR</button>
        </div>
    </form>

</div>
<%}%>