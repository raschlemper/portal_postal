
<%@page import="Coleta.Controle.contrColeta"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page import = "java.util.ArrayList,java.sql.Timestamp, java.util.Date, java.text.SimpleDateFormat"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    /*
     * AJAX
     */
    response.setContentType("text/xml");
    response.setHeader("Cache-Control", "no-cache");

    String nomeBD = (String) session.getAttribute("empresa");
    if (nomeBD != null) {
        String multi = request.getParameter("multi");       
%>
<div style="width: 100%; margin-top: 15px; margin-bottom: 100px;">
    <div style="width: 95%; text-align: left;">
        <div style='float:right;'><a onclick='chamaDivProtecao();' href='#' class='botaoClose'>Fechar</a></div>
        <div id='titulo1'>Pesquisar Destinatário</div>
    </div>
    <img width="100%" src="../../imagensNew/linha.jpg"/>

    <form name='formObs' action='../../ServAlterarObsColeta' method='post'>
        <ul style="width: 95%; text-align: left;" class="ul_formulario">
            <li>
                <dd>
                    <label>Código</label>
                    <input name="codigo_d" id="codigo_d" size="5" />
                </dd>
                <dd>
                    <label>Nome / Razão Social</label>
                    <input name="nome_d" id="nome_d" size="40" />
                </dd>
                <dd>
                    <label>Empresa</label>
                    <input name="empresa_d" id="empresa_d" size="25" />
                </dd>
                <dd>
                    <label>Endereço</label>
                    <input name="endereco_d" id="endereco_d" size="25" />
                </dd>
                <dd>
                    <label>CEP</label>
                    <input name="cep_d" id="cep_d" size="6" maxlength="9" onkeypress="mascara(this, maskCep)"/>
                </dd>
                <dd>
                    <div class="buttons" style="margin-top: -7px;">
                        <button type="button" class="regular" onclick="pesquisarDestinatario('<%=nomeBD%>', '<%=multi%>');"><img src="../../imagensNew/lupa.png"/> PESQUISAR</button>
                    </div>
                </dd>
            </li>
        </ul>
    </form>
                
    <img width="100%" src="../../imagensNew/linha.jpg"/>
    
    <div id="divLoad" class="esconder" style="width: 100%; text-align: center; font-size: 18px; font-weight: bold;"><p><br/><br/><img src="../../imagensNew/loader.gif" border="0" /><br/><br/>PESQUISANDO... AGUARDE!</p></div>
    <div id="resultadoPesq" style="width: 95%;"></div>                    

</div>
<%}%>