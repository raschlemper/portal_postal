
<%@page import="Emporium.Controle.ContrLogisticaReversa"%>
<%@page import="Entidade.LogisticaReversa"%>
<%@page import="Controle.contrCliente"%>
<%@page import="Entidade.Clientes"%>
<%@page import="Controle.contrEmpresa"%>
<%@page import="Entidade.empresas"%>
<%@page import="Emporium.Controle.ContrPreVenda"%>
<%@page import="Entidade.PreVenda"%>
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

        int idRev = Integer.parseInt(request.getParameter("idRev"));
        LogisticaReversa pv = ContrLogisticaReversa.consultaReversaById(idRev, nomeBD);
        
        String nrObj = " - - - ";
        if(!pv.getNumObjeto().equals("")){
            nrObj = pv.getNumObjeto();
        }
%>
<div style="width: 100%; margin: 15px 0 15px 0;">
    <div style="width: 95%; text-align: left;">
        <div style='float:right;'><a onclick='chamaDivProtecao();' href='#' class='botaoClose'>Fechar</a></div>
        <div id='titulo1'>Visualizar Solicitação de Autorização de Postagem</div>
    </div>
    <img width="100%" src="../../imagensNew/linha.jpg"/>

    <ul style="width: 95%; text-align: left;" class="ul_dados">   
        <li><dd class="titulo">Dados da Solicitação da Autorização de Postagem</dd></li>        
        <li>
            <dd style="width: 200px;">
                <label>Data da Solicitação</label>
                <%= pv.getDataSolicitacao() %>
            </dd>
            <dd  style="width: 200px;">
                <label>Usuário que Solicitou</label>
                <%= pv.getUserSolicitacao() %>
            </dd>
            <dd>
                <label>Departamento</label>
                <%= pv.getNomeDepto()%>
            </dd>
        </li>
        <li>
            <dd style="width: 200px;">
                <label>Nº da Autorização</label>
                <%= pv.getCod_ap() %>
            </dd>
            <dd style="width: 200px;">
                <label>Nº do Objeto</label>
                <%= nrObj %>
            </dd>
            <dd>
                <label>Situação</label>
                <%= pv.getDescricaoStatus() %>
            </dd>
        </li>
        <li>
             <dd  style="width: 200px;">
                <label>Cartão Postagem</label>
                <%= pv.getCartao() %>
            </dd>
            <dd>
                <label>Serviços Adicionais</label>
                <b>VD:</b> R$ <%= pv.getVd() %>
                <b style='margin:0 20px 0 20px;'>|</b> <b>AR:</b> <% if(pv.getAr() == 1){ %> <img width="12" src="../../imagensNew/tick_circle.png" /> <%}else{%> <img width="12" src="../../imagensNew/cross_circle.png" /> <%}%>
            </dd>
        </li>        
        <li><dd class="titulo">Dados do Remetente</dd></li>
        <li>
            <dd style="width: 200px;">
                <label>Nome / Razão Social</label>
                <%= pv.getNome_rem() %>
            </dd>
            <dd>
                <label>E-mail</label>
                <%= pv.getEmail_rem() %>
            </dd>
        </li>
        <li>
            <dd>
                <label>Endereço</label>
                <%= pv.getEndereco_rem() + ", " + pv.getNumero_rem() + ", " + pv.getComplemento_rem() %>
            </dd>
        </li>
        <li>
            <dd>
                <label>Bairro - Cidade / UF</label>
                <%= pv.getBairro_rem() + " - " + pv.getCidade_rem() + " / " + pv.getUf_rem() %>
            </dd>
        </li>
        <li>
            <dd>
                <label>CEP</label>
                <%= pv.getCep_rem() %>
            </dd>
        </li>
        <li><dd class="titulo">Dados do Destinatário</dd></li>
        <li>
            <dd style="width: 200px;">
                <label>Nome / Razão Social</label>
                <%= pv.getNome_des() %>
            </dd>
            <dd>
                <label>E-mail</label>
                <%= pv.getEmail_des() %>
            </dd>
        </li>
        <li>
            <dd>
                <label>Endereço</label>
                <%= pv.getEndereco_des() + ", " + pv.getNumero_des() + ", " + pv.getComplemento_des() %>
            </dd>
        </li>
        <li>
            <dd>
                <label>Bairro - Cidade / UF</label>
                <%= pv.getBairro_des() + " - " + pv.getCidade_des() + " / " + pv.getUf_des() %>
            </dd>
        </li>
        <li>
            <dd>
                <label>CEP</label>
                <%= pv.getCep_des() %>
            </dd>
        </li>
    </ul>                                

</div>
<%}%>