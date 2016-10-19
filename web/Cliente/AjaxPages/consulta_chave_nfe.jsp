<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="com.portal.componentes.nfe.ParametrosFormularioNFE"%>
<%@page import="com.portal.componentes.nfe.CarregaDadosNFE"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    response.setHeader("Cache-Control", "no-cache");
    String nomeBD = (String) session.getAttribute("empresa");
    if (nomeBD != null) {
        
        ParametrosFormularioNFE parametros = new ParametrosFormularioNFE();
        CarregaDadosNFE dadosNFE = new CarregaDadosNFE(parametros);
        String captcha = dadosNFE.getCaptcha();
        request.getSession().setAttribute("PARAMETRONFE",parametros);
%>
<div style="width: 100%; margin-top: 15px; margin-bottom: 100px;">
    <div style="width: 95%; text-align: left;">
        <div style='float:right;'><a onclick='chamaDivProtecao();' href='#' class='botaoClose'>Fechar</a></div>
        <div id='titulo1'>Pesquisar Destinatário por Chave de NF-e</div>
    </div>
    <img width="100%" src="../../imagensNew/linha.jpg"/>
    <form name='' action='' method='post'>
        <ul style="width: 95%; text-align: left;" class="ul_formulario">
            <li>
                <dd>
                    <label>Captcha:</label>
                    <img src="<%=captcha%>"/>
                </dd>
            </li>
            <li>                    
                <dd>
                    <label>Digite o Captcha:</label>
                    <input size="15" id="captcha"/>
                </dd>
            </li>
            <li>
                <dd>
                    <label>Chave da Nota Fiscal:</label>
                    <input size="50" id="keyNF" value=""/>
                </dd>
            </li>
            <li>
                <dd>
                    <div class="buttons" style="margin-top: -7px;">
                        <button type="button" class="regular" onclick="pesquisarNFE();"><img src="../../imagensNew/lupa.png"/> PESQUISAR</button>
                        <button type="button" class="negative" onclick="chamaDivProtecao();"><img src="../../imagensNew/cross_circle.png" /> CANCELAR</button>
                    </div>
                </dd>
            </li>
        </ul>
    </form>  
</div>
<%}%>