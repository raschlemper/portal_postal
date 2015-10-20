<%@page import="Emporium.Controle.ContrPreVenda"%>
<%@page import="Entidade.PreVenda"%>
<%@page import="java.math.BigDecimal"%>
<%@page import="Util.FormataString"%>
<%@ page import="java.sql.*, javax.swing.*, java.util.*, java.text.SimpleDateFormat, java.text.DecimalFormat, java.util.Date" %>
<%
    response.setContentType("application/xls");
    response.setHeader("Content-disposition", "attachment; filename=relatorio_etq_geradas.xls");
    response.setHeader("Cache-Control", "no-cache");


    int idCli = Integer.parseInt(request.getParameter("idCli"));
    String nomeBD = request.getParameter("nomeBD");
    String dataIni = request.getParameter("dataIni");
    String dataFim = request.getParameter("dataFim");

    int nivel = (Integer) session.getAttribute("nivelUsuarioEmp");
    int idUser = (Integer) session.getAttribute("idUsuarioEmp");
    ArrayList<PreVenda> lista = ContrPreVenda.consultaVendasReimpressao(idCli, 1, -1, nivel, idUser, true, dataIni, dataFim, nomeBD);

    if (lista.size() >= 1) {
%>
<table cellpadding="0" cellspacing="0" border="0">
    <thead>
        <tr>
            <th><h3>Nº DO OBJETO</h3></th>
            <th><h3>SERVIÇO</h3></th>
            <th><h3>DEPARTAMENTO</h3></th>
            <th><h3>DESTINATÁRIO</h3></th>
            <th><h3>EMPRESA</h3></th>
            <th><h3>CPF/CNPJ</h3></th>
            <th><h3>AOS CUIDADOS</h3></th>
            <th><h3>ENDEREÇO</h3></th>
            <th><h3>BAIRRO</h3></th>
            <th><h3>CIDADE / UF</h3></th>
            <th><h3>CEP</h3></th>
            <th><h3>N.F. / ID PEDIDO</h3></th>
            <th><h3>AR</h3></th>
            <th><h3>MP</h3></th>
            <th><h3>VD</h3></th>
            <th><h3>CONTEÚDO</h3></th>
            <th><h3>OBS</h3></th>
            <th><h3>DATA IMPRESSÃO</h3></th>
            <th><h3>E-MAIL</h3></th>
        </tr>
    </thead>
    <tbody>
        <%
            for (int i = 0; i < lista.size(); i++) {
                PreVenda des = lista.get(i);
                String numObj = des.getNumObjeto();
                if ("avista".equals(numObj)) {
                    numObj = "- - -";
                }
                String ar = "SIM";
                if (des.getAviso_recebimento() == 0) {
                    ar = "NÃO";
                }
                String vd = "0,00";
                if (des.getValor_declarado() > 0) {
                    vd = des.getValor_declarado()+"";
                }
                String mp = "SIM";
                if (des.getMao_propria() == 0) {
                    mp = "NÃO";
                }
        %>
        <tr>
            <td nowrap><%= numObj%></td>
            <td nowrap><%= des.getNomeServico()%></td>
            <td nowrap><%= des.getDepartamento() %></td>
            <td nowrap><%= des.getNomeDes()%></td>
            <td nowrap><%= des.getEmpresaDes() %></td>
            <td nowrap><%= des.getCpfDes() %></td>
            <td nowrap><%= des.getAos_cuidados() %></td>
            <td nowrap><%= des.getEnderecoDes() + ", " + des.getNumeroDes() + ", " + des.getComplementoDes() %></td>
            <td nowrap><%= des.getBairroDes() %></td>
            <td nowrap><%= des.getCidadeDes() + " / " + des.getUfDes()%></td>
            <td nowrap><%= des.getCepDes()%></td>
            <td nowrap><%= des.getNotaFiscal()%></td>
            <td nowrap><%= ar%></td>
            <td nowrap><%= mp%></td>
            <td nowrap><%= vd%></td>
            <td nowrap><%= des.getConteudo() %></td>
            <td nowrap><%= des.getObservacoes() %></td>
            <td nowrap><%= des.getDataImpressoFormatada() %></td>
            <td nowrap><%= des.getEmail_destinatario() %></td>
        </tr>
        <%}%>
    </tbody>
</table>
<%} else {%>
<div align='center' style='padding-top:25px;background:#fc8878;color:black;height:50px;width:100%;font-size:20px;font-weight:bold;'>Nenhum Objeto Encontrado!</div>
<%}%>