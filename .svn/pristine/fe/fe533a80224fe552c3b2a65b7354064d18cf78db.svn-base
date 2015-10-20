
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

        int idCliente = Integer.parseInt(request.getParameter("idCliente"));
        int idVenda = Integer.parseInt(request.getParameter("idVenda"));
        PreVenda pv = ContrPreVenda.consultaVendaById(idVenda, nomeBD);
        Clientes cli = contrCliente.consultaClienteById(idCliente, nomeBD);
        String dtVenda = " - - - ";
        if (pv.getDataPreVenda() != null) {
            dtVenda = sdf.format(pv.getDataPreVenda());
        }

        String nrObj = " - - - ";
        if (!pv.getNumObjeto().equals("avista")) {
            nrObj = pv.getNumObjeto();
        }
%>
<div>

    <ul class="list-unstyled">  

        <li class="list-group-item">
            <a class="list-group-item-heading">
                <h4>Pré-postaegm</h4>
            </a>       

            <dd>
                <label>Data da Pré-Postagem</label>
                <%= dtVenda%>
            </dd>


            <dd>
                <label>Nº do Objeto</label>
                <%= nrObj%>
            </dd>
            <dd>
                <label>Serviço</label>
                <%= pv.getNomeServico()%>
            </dd>

            <dd>
                <label>Serviços Adicionais</label>
                <b>VD:</b> R$ <%= pv.getValor_declarado()%>
                <b style='margin:0 20px 0 20px;'>|</b> <b>AR:</b> <% if (pv.getAviso_recebimento() == 1) { %> <img width="12" src="../../imagensNew/tick_circle.png" /> <%} else {%> <img width="12" src="../../imagensNew/cross_circle.png" /> <%}%>
                <b style='margin:0 20px 0 20px;'>|</b> <b>MP:</b> <% if (pv.getMao_propria() == 1) { %> <img width="12" src="../../imagensNew/tick_circle.png" /> <%} else {%> <img width="12" src="../../imagensNew/cross_circle.png" /> <%}%>
            </dd>
        </li>  

        <li class="list-group-item"> 
            <a class="list-group-item-heading">
                <h4>Remetente</h4>
            </a>     
            <label>Nome / Razão Social</label>
            <%= cli.getNome()%>
            </dd>
            <dd>
                <label>Departamento</label>
                <%= pv.getDepartamento()%>
            </dd>

            <dd>
                <label>Contrato</label>
                <%= cli.getNumContrato() + "/" + cli.getAnoContrato() + " - DR/" + cli.getUfContrato()%>
            </dd>
        </li>

        <li class="list-group-item"> 
            <a class="list-group-item-heading">
                <h4>Destinatário</h4>
            </a>
            <label>Nome / Razão Social</label>
            <%= pv.getNomeDes()%>
            </dd>
            <%if (pv.getAos_cuidados() != null && !"".equals(pv.getAos_cuidados())) {%>
            <dd style="margin-left: 50px;">
                <label>Aos Cuidados</label>
                <%= pv.getAos_cuidados()%>
            </dd>
            <%}%>
            <dd>
                <label>Empresa</label>
                <%= pv.getEmpresaDes()%>
            </dd>

            <dd>
                <label>Endereço</label>
                <%= pv.getEnderecoDes() + ", n " + pv.getNumeroDes() + " " + pv.getComplementoDes()%>
            </dd>


            <dd>
                <label>Bairro - Cidade / UF</label>
                <%= pv.getBairroDes() + " - " + pv.getCidadeDes() + " / " + pv.getUfDes()%>
            </dd>

            <dd>
                <label>CEP</label>
                <%= pv.getCepDes()%>
            </dd>
        </li>
    </ul>                                

</div>
<%}%>