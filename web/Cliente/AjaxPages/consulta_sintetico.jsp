<%@page import="Controle.ContrReclamacao"%>
<%@page import="java.text.ParseException"%>
<%@page import="java.text.DateFormat"%>
<%@page import="Entidade.Movimentacao"%>
<%@page import="Controle.ContrClienteDeptos"%>
<%@page import="java.math.BigDecimal"%>
<%@page import="Util.FormataString"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.sql.*, javax.swing.*, java.util.*, java.text.SimpleDateFormat, java.text.DecimalFormat, java.util.Date" %>
<%
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    response.setContentType("text/xml");
    response.setHeader("Cache-Control", "no-cache");
    int qtdEnc = 0, qtdPos = 0, qtdEnt = 0, qtdDev = 0, qtdExt = 0, qtdTotal = 0;
    BigDecimal vlrTotal = new BigDecimal(0);

    String nomeBD = (String) session.getAttribute("nomeBD");
    if (nomeBD != null) {

        ArrayList<Integer> acessosUs = (ArrayList<Integer>) session.getAttribute("acessos");
        int nivel = (Integer) session.getAttribute("nivelUsuarioEmp");
        int idCliente = Integer.parseInt(request.getParameter("idCliente"));
        String dataInicio = request.getParameter("dataIni");
        String dataFinal = request.getParameter("dataFim");
        String vDataInicio = Util.FormatarData.DateToBD(dataInicio);
        String vDataFinal = Util.FormatarData.DateToBD(dataFinal);
        String situacao = request.getParameter("situacao");
        String servico = request.getParameter("servico");
        String departamento = request.getParameter("departamento");

        String objeto = request.getParameter("objeto");
        String nota = request.getParameter("notaFiscal");
        String dest = request.getParameter("destinatario");
        String cep = request.getParameter("cep");
        String ar = request.getParameter("ar");
        String vd = request.getParameter("vd");
        String uf = request.getParameter("uf");
        String late = request.getParameter("atrasado");

        String tpFat = request.getParameter("tipoFat");

        String sql = "SELECT id, descServico, peso, quantidade, valorServico, dataPostagem, codStatus,"
                + " movimentacao.numObjeto, destinatario, cep, departamento, status, dataEntrega, notaFiscal, numVenda, numCaixa,"
                + " last_status_date, last_status_name, last_status_code, last_status_type, prazo_estimado, prazo_cumprido, idPre_venda"
                + " FROM movimentacao"
                + " LEFT JOIN movimentacao_tracking AS mt ON movimentacao.numObjeto = mt.numObjeto"
                + " WHERE codCliente = " + idCliente;
        if (dataInicio.length() == 10 && dataFinal.length() == 10) {
            sql += " AND (dataPostagem BETWEEN '" + vDataInicio + "' AND '" + vDataFinal + "')";
        }
        if (!situacao.equals("")) {
            //if (!situacao.equals("0")) {
            //sql += " AND status LIKE '" + situacao + "'"; //TROCAR DEPOIS PARA COD_STATUS
            sql += situacao; //TROCAR DEPOIS PARA COD_STATUS
        }
        if (!servico.equals("0")) {
            //sql += " AND descServico LIKE '" + servico + "'"; //TROCAR DEPOIS PARA CODIGO ECT
            sql += FormataString.montaWhereServicos(servico);
        }
        if (!departamento.equals("0")) {
            String auxd[] = departamento.split(";");
            sql += " AND (movimentacao.idDepartamento = " + auxd[0] + " OR departamento = '" + auxd[1] + "')";
        } else if (nivel != 1) {
            ArrayList<Integer> dptosSessaoUsuario = (ArrayList<Integer>) session.getAttribute("departamentos");
            if (dptosSessaoUsuario != null && dptosSessaoUsuario.size() > 0) {
                String idsDepto = "";
                for (Integer idDep : dptosSessaoUsuario) {
                    idsDepto += idDep + ",";
                }
                if (!idsDepto.equals("")) {
                    idsDepto = idsDepto.substring(0, idsDepto.lastIndexOf(","));
                    sql += " AND movimentacao.idDepartamento IN (" + idsDepto + ") ";
                }
            }
            //sql += ContrClienteDeptos.consultaDeptosWherePesquisaMovimento(dptosSessaoUsuario, idCliente, nomeBD);
        }
        if (!objeto.equals("")) {
            sql += " AND movimentacao.numObjeto LIKE '%" + objeto + "%'";
        }
        if (!cep.equals("")) {
            //sql += " AND cep LIKE '%" + cep + "%'";
            sql += " AND (cep LIKE '%" + cep + "%' OR cep LIKE '%" + cep.replaceAll("-", "") + "%') ";
        }
        if (!nota.equals("")) {
            sql += " AND notaFiscal LIKE '%" + nota + "%'";
        }
        if (!dest.equals("")) {
            sql += " AND destinatario LIKE '%" + dest + "%'";
        }
        if (!uf.equals("")) {
            sql += " AND " + uf;
        }
        if (ar.equals("true")) {
            sql += " AND siglaServAdicionais LIKE '%AR%'";
        }
        if (vd.equals("true")) {
            sql += " AND siglaServAdicionais LIKE '%VD%'";
        }
        if (late.equals("true")) {
            sql += " AND prazo_cumprido > prazo_estimado ";
        }
        if (!tpFat.equals("")) {
            sql += tpFat;
        }
        sql += " ORDER BY dataPostagem DESC";

        ArrayList movimentacao = Controle.contrMovimentacao.getConsultaSintetica(sql, nomeBD);

        if (movimentacao.size() >= 1) {
%>
<%if (acessosUs.contains(8)) {%>
<div class="mostar" id="alertWrap">            
    <div class="warningBox">
        <span class="closebtn" onclick=" document.getElementById('alertWrap').className = 'esconder';">&times;</span> 
        <div id="alertMsg">
            Caso a data estimada de entrega <b>(PRAZO EST.)</b> caia em algum sábado, domingo ou feriado, deve-se considerar como prazo o proximo dia útil.  
        </div>
    </div>
</div>

<%}%>

<div style="padding:8px 5px; background: white;">
    <%--<a href="../AjaxPages/print_sintetico.jsp?sql=<%= sql%>"><img class="link_img" src="../../imagensNew/printer.png" /> IMPRIMIR</a>
    <b style="margin:0 12px 0 10px;">|</b>--%>
    <a href="#" onclick="document.formEXP.action = '../AjaxPages/xls_sintetico.jsp';
            document.formEXP.submit();"><img class="link_img" src="../../imagensNew/excel.png" /> EXPORTAR .XLS</a>
    <b style="margin:0 12px 0 10px;">|</b>
    <a href="#" onclick="document.formEXP.action = '../AjaxPages/csv_sintetico.jsp';
            document.formEXP.submit();"><img class="link_img" src="../../imagensNew/csv.png" /> EXPORTAR .CSV</a>
        <%--<b style="margin:0 12px 0 10px;">|</b>
            <a href="#" onclick="document.formEXP.action='../AjaxPages/csv_servico_por_depto.jsp'; document.formEXP.submit();"><img class="link_img" src="../../imagensNew/csv.png" /> EXPORTAR .CSV</a>
            <b style="margin:0 12px 0 10px;">|</b>
            <a href="../AjaxPages/pdf_sintetico.jsp?sql=<%= sql%>"><img class="link_img" src="../../imagensNew/pdf.png" /> EXPORTAR .PDF</a>--%>
    <form name="formEXP" method="post" action="#">
        <input type="hidden" name="sql" value="<%= sql%>" />
        <input type="hidden" name="nivel" value="<%= nivel%>" />
        <input type="hidden" name="nomeBD" value="<%= nomeBD%>" />
    </form>
</div>


<table id="barraAtendimento" border="0">
    <tr>
        <td align="left" style="font-weight:bold;font-size:12px;">
            Pesquisa Rápida:
            <select style='min-width:150px;' id="columns2" onchange="sorter2.search('query2')"></select>
            <input type="text" id="query2" onkeyup="sorter2.search('query2')" placeholder="Digite aqui a sua pesquisa..." />
            <a style="text-decoration:none;font-weight:bold;font-size:11px;" href="javascript:document.getElementById('query2').value='';sorter2.reset()">RESTAURAR PADRÕES</a>
        </td>
        <td align="right">
            <div class="details" style="clear:both;">
                <div>Resultado <span id="startrecord2"></span>-<span id="endrecord2"></span> de <span id="totalrecords2"></span></div>
            </div>
        </td>
    </tr>
</table>
<div style='max-width:100%;overflow:auto;'>
    <table style="width: 1500px;" cellpadding="0" cellspacing="0" border="0" id="table2" class="tinytable">
        <thead>
            <tr>
                <th width='10' class="nosort"><h3></h3></th>
        <th width='100'><h3>OBJETO</h3></th>
        <th><h3>SERVIÇO</h3></th>
        <th width='30'><h3>PESO</h3></th>
        <th width='30'><h3>QTD</h3></th>
        <th width='50'><h3>POSTAGEM</h3></th>
        <%if (acessosUs.contains(3)) {%>
        <th width='50'><h3>VALOR</h3></th>
        <%}%>
        <th><h3>DESTINATÁRIO</h3></th>
        <th width='80'><h3>CEP</h3></th>
        <th><h3>SITUAÇÃO</h3></th>
        <th width='60'><h3>NF</h3></th>
        <th width='100'><h3>DEPARTAMENTO</h3></th>
        <%if (acessosUs.contains(8)) {%>
        <th width='50'><h3>PRAZO EST.</h3></th>
        <th width='50'><h3>PRAZO REAL</h3></th>
        <%}%>
        </tr>
        </thead>
        <tbody>
            <%
                for (int i = 0; i < movimentacao.size(); i++) {
                    Movimentacao mov = (Movimentacao) movimentacao.get(i);
                    String servico2 = mov.getDescServico();
                    int peso = (int) mov.getPeso();
                    int qtd = (int) mov.getQuantidade();
                    qtdTotal += qtd;

                    float valor = mov.getValorServico();
                    vlrTotal = vlrTotal.add(new BigDecimal(valor));
                    String vValor = Util.FormatarDecimal.formatarFloat(valor);

                    Date data = mov.getDataPostagem();
                    String vData = sdf.format(data);

                    String notaFiscal = mov.getNotaFiscal();
                    String numeroRegistro = mov.getNumObjeto();
                    String destinatario = mov.getDestinatario();
                    String cepDestino = FormataString.formataCep(mov.getCep());
                    String departamento2 = mov.getDepartamento();

                    String pz_estimado = "---";
                    String pz_cumprido = "---";
                    String atrasado = "";
                    if (acessosUs.contains(8)) {
                        if (mov.getPrazo_estimado() != null) {
                            ContrReclamacao cr = new ContrReclamacao();
                            Calendar novadataPrevisaoEntrega = cr.recalculaDataEstimada(cr.dateToCalendar(mov.getDataPostagem()), cr.dateToCalendar(mov.getPrazo_estimado()), "");
                            if(novadataPrevisaoEntrega != null){
                                mov.setPrazo_estimado(novadataPrevisaoEntrega.getTime());                    
                            }
                        }
                        if (mov.getPrazo_estimado() != null && mov.getPrazo_cumprido_date() != null) {
                            pz_estimado = sdf.format(mov.getPrazo_estimado());
                            pz_cumprido = sdf.format(mov.getPrazo_cumprido_date());
                            if (mov.getPrazo_estimado().before(mov.getPrazo_cumprido_date())) {
                                atrasado = "color:red;font-weight:bold;";
                            }
                        } else if (mov.getPrazo_estimado() != null) {
                            pz_estimado = sdf.format(mov.getPrazo_estimado());
                            Date date = new Date();
                            try {
                                DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                                date = (java.util.Date) formatter.parse(formatter.format(date));
                            } catch (ParseException e) {
                                //System.out.println(e.getMessage());
                            }
                            if (mov.getPrazo_estimado().before(date)) {
                                atrasado = "color:red;font-weight:bold;";
                            }
                        }
                    }

                    String status = mov.getStatus();
                    String codStatus = "" + mov.getCodStatus();
                    String grupoStatus = Util.Situacao.consultaGrupoStatus(codStatus, status);
                    if (mov.getLast_status_date() != null) {
                        status = mov.getLast_status_name();
                        int codigoStatus = mov.getLast_status_code();
                        grupoStatus = Util.Situacao.consultaGrupoStatusNovo(codigoStatus, mov.getLast_status_type(), status);
                    }

                    String img_status = "";
                    if (grupoStatus.equals("POSTADO")) {
                        img_status = "../../imagensNew/mail.png";
                        qtdPos++;
                    } else if (grupoStatus.equals("ENTREGUE")) {
                        img_status = "../../imagensNew/mail_open.png";
                        qtdEnt++;
                    } else if (grupoStatus.equals("DEVOLVIDO")) {
                        img_status = "../../imagensNew/mail_back.png";
                        qtdDev++;
                    } else if (grupoStatus.equals("EXTRAVIADO")) {
                        img_status = "../../imagensNew/mail_alert.png";
                        qtdExt++;
                    } else {
                        img_status = "../../imagensNew/mail_send.png";
                        qtdEnc++;
                    }
            %>
            <tr align='center' style="font-size: 10px; <%= atrasado%> ">
                <td><img class="link_img" src="<%= img_status%>" /></td>
                <td>
                    <%--<form name="frm<%= numeroRegistro%>" id="frm<%= numeroRegistro%>" method="post" action="http://www2.correios.com.br/sistemas/rastreamento/multResultado.cfm" target="_blank">
                    <form name="frm<%= numeroRegistro%>" id="frm<%= numeroRegistro%>" method="post" action="http://www2.correios.com.br/sistemas/rastreamento/newprint.cfm" target="_blank">--%>
                    <form name="frm<%= numeroRegistro%>" id="frm<%= numeroRegistro%>" method="post" action="http://www2.correios.com.br/sistemas/rastreamento/Resultado.cfm" target="_blank">
                        <input type="hidden" name="objetos" id="objetos" value="<%= numeroRegistro%>" />
                    </form>                    
                    <a href='#' onclick="document.getElementById('frm<%= numeroRegistro%>').submit();"><%= numeroRegistro%></a>
                </td>
                <%--<a href='http://websro.correios.com.br/sro_bin/txect01$.QueryList?P_LINGUA=001&P_TIPO=001&P_COD_UNI=<%= numeroRegistro%>' target=_blank><%= numeroRegistro%></a></td>--%>
                <td align='left'><a href='visulizaTicket.jsp?idmov=<%=mov.getId()%>' target='_blank'><%= servico2%></a></td>
                <td align='right'><%= peso%>g</td>
                <td align='right'><%= qtd%></td>
                <td><%= vData%></td>
                <%if (acessosUs.contains(3)) {%>
                <td nowrap align='right'>R$ <%= vValor%></td>
                <% }%>
                <td align='left' style="font-size: 10px;"><a onclick="verVenda(<%= mov.getIdPre_venda()%>);" style="cursor:pointer;" ><%= destinatario%></a></td>
                <td><%= cepDestino%></td>
                <td><%= status%></td>
                <td><%= notaFiscal%></td>
                <td><%= departamento2%></td>
                <%if (acessosUs.contains(8)) {%>
                <td><%= pz_estimado%></td>
                <td><%= pz_cumprido%></td>
                <%}%>
            </tr>
            <%}%>
        </tbody>
        <tfoot>
            <tr style="background: #f0f0f0; color:red; font-size: 12px;">
                <td colspan="4"></td>
                <td nowrap="true" align="right"><%= qtdTotal%></td>
                <td></td>
                <%if (acessosUs.contains(3)) {%>
                <td nowrap="true" align='right'>R$ <%= Util.FormatarDecimal.formatarFloat(vlrTotal.floatValue())%></td>
                <%}%>
                <td colspan="7"></td>
            </tr>
        </tfoot>
    </table>
</div>
<div id="tablefooter" align='center'>
    <div align="left" style='float:left; width:20%;'>
        <select onchange="sorter2.size(this.value)">
            <option value="5">5</option>
            <option value="10">10</option>
            <option value="20" selected="selected">20</option>
            <option value="50">50</option>
            <option value="100">100</option>
        </select>
        <span>Linhas por Página</span>
    </div>
    <div id="tablenav2" class="tablenav">
        <div>
            <img src="../../javascript/plugins/TableSorter/images/left_end.png" width="20" height="20" alt="First Page" onclick="sorter2.move(-1, true)" />
            <img src="../../javascript/plugins/TableSorter/images/left.png" width="20" height="20" alt="First Page" onclick="sorter2.move(-1)" />
            <img src="../../javascript/plugins/TableSorter/images/right.png" width="20" height="20" alt="First Page" onclick="sorter2.move(1)" />
            <img src="../../javascript/plugins/TableSorter/images/right_end.png" width="20" height="20" alt="Last Page" onclick="sorter2.move(1, true)" />
            <select style="margin-left:5px;" id="pagedropdown2"></select>
            <a style="margin-left:10px;" href="javascript:sorter2.showall()">Ver Tudo</a>
        </div>
    </div>
    <div id="tablelocation">
        <div class="page">Página <span id="currentpage2"></span> de <span id="totalpages2"></span></div>
    </div>
</div>
<%} else {%>
<div align='center' style='padding-top:25px;background:#fc8878;color:black;height:50px;width:100%;font-size:20px;font-weight:bold;'>Nenhum Objeto Encontrado!</div>
<%}%>
<%} else {%>
sessaoexpirada
<%}%>
<input type="hidden" id="dadosGrafico" value="POSTADO,<%= qtdPos%>;ENTREGUE,<%= qtdEnt%>;EXTRAVIADO,<%= qtdExt%>;DEVOLVIDO,<%= qtdDev%>;ENCAMINHADO,<%= qtdEnc%>" />
