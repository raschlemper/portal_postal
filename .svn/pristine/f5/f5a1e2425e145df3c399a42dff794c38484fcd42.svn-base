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
        <table style="margin:0 auto;">
            <tr>
                <td style="padding-right: 25px;">
                    <img src="<%=captcha%>"/>
                </td>
                <td style="padding-left: 25px; border-left: 1px solid silver;" align="left">                    
                    <label style="font-size: 11px; font-weight: bold;">Digite o Captcha:</label><br/>
                    <input type="text" size="15" id="captcha"/>            
                    <br/><br/>
                    <label style="font-size: 11px; font-weight: bold;">Chave da Nota Fiscal:</label><br/>
                    <input type="text" size="50" id="keyNF" onkeypress="mascara(this, maskNumero);" onblur="mascara(this, maskNumero);" value=""/>
                    <br/><br/>
                    <label>&nbsp;</label>
                    <input type="checkbox" id="vdNF" value="1" /> Declarar Valor da Nota Fiscal.
                </td>
            </tr>
            <tr>
                <td></td>
                <td>
                    <div class="buttons">
                        <button type="button" class="regular" onclick="pesquisarNFE();"><img src="../../imagensNew/lupa.png"/> PESQUISAR</button>
                        <button type="button" class="negative" onclick="chamaDivProtecao();"><img src="../../imagensNew/cross_circle.png" /> CANCELAR</button>
                    </div>
                </td>
            </tr>
        </table>
    </form>  
</div>
<%}%>