
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
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss");

    String nomeBD = (String) session.getAttribute("empresa");
    if (nomeBD != null) {

        int idCliente = (Integer) session.getAttribute("idCliente");
        int idVenda = Integer.parseInt(request.getParameter("idVenda"));
        PreVenda pv = ContrPreVenda.consultaVendaById(idVenda, nomeBD);
        Clientes cli = contrCliente.consultaClienteById(idCliente, nomeBD);
        String dtVenda = sdf.format(pv.getDataPreVenda());
        
        String nrObj = " - - - ";
        if(!pv.getNumObjeto().equals("avista")){
            nrObj = pv.getNumObjeto();
        }
%>
<div style="width: 100%; margin: 15px 0 15px 0;">
    <div style="width: 95%; text-align: left;">
        <div style='float:right;'><a onclick='chamaDivProtecao();' href='#' class='botaoClose'>Fechar</a></div>
        <div id='titulo1'>Visualizar Pré-Postagem</div>
    </div>
    <img width="100%" src="../../imagensNew/linha.jpg"/>

    <ul style="width: 95%; text-align: left;" class="ul_dados">   
        <li><dd class="titulo">Dados da Venda</dd></li>        
        <li>
            <dd style="width: 200px;">
                <label>Data da Pré-Postagem</label>
                <%= dtVenda %>
            </dd>
        </li>
        <li>
            <dd style="width: 200px;">
                <label>Nº do Objeto</label>
                <%= nrObj %>
            </dd>
            <dd>
                <label>Serviço</label>
                <%= pv.getNomeServico()%>
            </dd>
        </li>
        <li>
            <dd>
                <label>Serviços Adicionais</label>
                <b>VD:</b> R$ <%= pv.getValor_declarado() %>
                <b style='margin:0 20px 0 20px;'>|</b> <b>AR:</b> <% if(pv.getAviso_recebimento() == 1){ %> <img width="12" src="../../imagensNew/tick_circle.png" /> <%}else{%> <img width="12" src="../../imagensNew/cross_circle.png" /> <%}%>
                <b style='margin:0 20px 0 20px;'>|</b> <b>MP:</b> <% if(pv.getMao_propria() == 1){ %> <img width="12" src="../../imagensNew/tick_circle.png" /> <%}else{%> <img width="12" src="../../imagensNew/cross_circle.png" /> <%}%>
            </dd>
        </li>        
        <li><dd class="titulo">Dados do Remetente</dd></li>
        <li>
            <dd>
                <label>Nome / Razão Social</label>
                <%= cli.getNome()%>
            </dd>
            <dd style="margin-left: 50px;">
                <label>Departamento</label>
                <%= pv.getDepartamento() %>
            </dd>
            <dd style="margin-left: 50px;">
                <label>Contrato</label>
                <%= cli.getNumContrato() + "/" + cli.getAnoContrato() + " - DR/" + cli.getUfContrato()%>
            </dd>
        </li>
        <li><dd class="titulo">Dados do Destinatário</dd></li>
        <li>
            <dd>
                <label>Nome / Razão Social</label>
                <%= pv.getNomeDes()%>
            </dd>
                <%if(pv.getAos_cuidados() != null && !"".equals(pv.getAos_cuidados())){%>
            <dd style="margin-left: 50px;">
                <label>Aos Cuidados</label>
                <%= pv.getAos_cuidados()%>
            </dd>
            <%}%>
            <dd style="margin-left: 50px;">
                <label>Empresa</label>
                <%= pv.getEmpresaDes()%>
            </dd>
        </li>
        <li>
            <dd>
                <label>Endereço</label>
                <%= pv.getEnderecoDes() + ", " + pv.getNumeroDes() + ", " + pv.getComplementoDes()%>
            </dd>
        </li>
        <li>
            <dd>
                <label>Bairro - Cidade / UF</label>
                <%= pv.getBairroDes() + " - " + pv.getCidadeDes() + " / " + pv.getUfDes()%>
            </dd>
        </li>
        <li>
            <dd>
                <label>CEP</label>
                <%= pv.getCepDes()%>
            </dd>
        </li>
    </ul>                                

</div>
<%}%>