<%@page import="Emporium.Controle.ContrPreVenda"%><%@page import="Entidade.PreVenda"%><%@page import="java.math.BigDecimal"%><%@page import="Util.FormataString"%><%@ page import="java.sql.*, javax.swing.*, java.util.*, java.text.SimpleDateFormat, java.text.DecimalFormat, java.util.Date" %><%
    response.setContentType("application/csv");
    response.setHeader("Content-disposition", "attachment; filename=relatorio_etq_geradas.csv");
    response.setHeader("Cache-Control", "no-cache");


    int idCli = Integer.parseInt(request.getParameter("idCli"));
    String nomeBD = request.getParameter("nomeBD");
    String dataIni = request.getParameter("dataIni");
    String dataFim = request.getParameter("dataFim");

    int nivel = (Integer) session.getAttribute("nivelUsuarioEmp");
    int idUser = (Integer) session.getAttribute("idUsuarioEmp");
    ArrayList<PreVenda> lista = ContrPreVenda.consultaVendasReimpressao(idCli, 1, -1, nivel, idUser, true, dataIni, dataFim, nomeBD);

    if (lista.size() >= 1) {
%>NUM. DO OBJETO;SERVIÇO;DEPARTAMENTO;DESTINATÁRIO;EMPRESA;CPF/CNPJ;AOS CUIDADOS;ENDEREÇO;BAIRRO;CIDADE;UF;CEP;NOTA FISCAL / ID PEDIDO;AR;MP;VD;CONTEÚDO;OBSERVAÇÕES;CHAVE;DATA IMPRESSÃO;E-MAIL
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
%><%= numObj%>;<%= des.getNomeServico()%>;<%= des.getDepartamento() %>;<%= des.getNomeDes()%>;<%= des.getEmpresaDes() %>;<%= des.getCpfDes() %>;<%= des.getAos_cuidados() %>;<%= des.getEnderecoDes() + ", " + des.getNumeroDes() + ", " + des.getComplementoDes() %>;<%= des.getBairroDes() %>;<%= des.getCidadeDes() + ";" + des.getUfDes()%>;<%= des.getCepDes()%>;<%= des.getNotaFiscal()%>;<%= ar%>;<%= mp%>;<%= vd%>;<%= des.getConteudo() %>;<%= des.getObservacoes() %>;<%= des.getNomeVenda() %>;<%= des.getDataImpressoFormatada() %>;<%= des.getEmail_destinatario() %>
<% }} else {%>
Nenhum Objeto Encontrado!
<%}%>