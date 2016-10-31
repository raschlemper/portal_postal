<%@page import="java.util.Arrays"%>
<%@page import="java.text.ParseException"%>
<%@page import="java.text.DateFormat"%>
<%@page import="java.util.Calendar"%>
<%@page import="Controle.ContrReclamacao"%>
<%@page import="java.util.Date"%>
<%@page import="java.math.BigDecimal"%>
<%@page import="Util.FormataString"%>
<%@page import="Entidade.Movimentacao"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    response.setContentType("text/xml");
    response.setHeader("Cache-Control", "no-cache");

    String nomeBD = (String) session.getAttribute("nomeBD");
    if (nomeBD != null) {
        int nivel = (Integer) session.getAttribute("nivelUsuarioEmp");
        int idCliente = Integer.parseInt(request.getParameter("idCliente"));
        String dataInicio = request.getParameter("dataIni");
        String dataFinal = request.getParameter("dataFim");
        String vDataInicio = Util.FormatarData.DateToBD(dataInicio);
        String vDataFinal = Util.FormatarData.DateToBD(dataFinal);
        String situacao = request.getParameter("situacao");
        String servico = request.getParameter("servico");
        String departamento = request.getParameter("departamento");
        String status_pi = request.getParameter("status_pi");
        
        String sql = "SELECT id, descServico, peso, quantidade, valorServico, dataPostagem, codStatus,"
                + " movimentacao.numObjeto, destinatario, cep, departamento, status, dataEntrega, notaFiscal, numVenda, numCaixa,"
                + " last_status_date, last_status_name, last_status_code, last_status_type, prazo_estimado, prazo_cumprido, idPre_venda,"
                + " pi_number, pi_date, pi_status_code, pi_status_name, pi_status_date, pi_motivo_code, pi_motivo_name"
                + " FROM movimentacao"
                + " LEFT JOIN movimentacao_tracking AS mt ON movimentacao.numObjeto = mt.numObjeto"
                + " WHERE codCliente = " + idCliente;
        if (dataInicio.length() == 10 && dataFinal.length() == 10) {
            sql += " AND (dataPostagem BETWEEN '" + vDataInicio + "' AND '" + vDataFinal + "')";
        }
        if (!situacao.equals("")) {
            sql += situacao; 
        }
        if (!status_pi.equals("")) {
            sql += " AND pi_status_code = " + status_pi; 
        }
        if (!servico.equals("0")) {
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
        }   
        
        ArrayList movimentacao = Controle.contrMovimentacao.getConsultaReclamacao(sql, nomeBD);

        if (movimentacao.size() >= 1) {

%>
<!--  <div style="padding:8px 5px; background: white;">

      <a href="#" onclick="document.formEXP.action = '../AjaxPages/xls_sintetico.jsp';
              document.formEXP.submit();"><img class="link_img" src="../../imagensNew/excel.png"> EXPORTAR .XLS</a>
      <b style="margin:0 12px 0 10px;">|</b>
      <a href="#" onclick="document.formEXP.action = '../AjaxPages/csv_sintetico.jsp';
              document.formEXP.submit();"><img class="link_img" src="../../imagensNew/csv.png"> EXPORTAR .CSV</a>

  </div> -->
<table id="barraAtendimento" border="0">
    <tbody><tr>
            <td align="left" style="font-weight:bold;font-size:12px;">
                Pesquisa Rápida:
                <select style="min-width:150px;" id="columns2" onchange="sorter2.search('query2')"><option value="-1">Todas as Colunas</option><option value="0"></option><option value="1">OBJETO</option><option value="2">SERVIÇO</option><option value="3">PESO</option><option value="4">QTD</option><option value="5">POSTAGEM</option><option value="6">VALOR</option><option value="7">DESTINATÁRIO</option><option value="8">CEP</option><option value="9">SITUAÇÃO</option><option value="10">NF</option><option value="11">DEPARTAMENTO</option><option value="12">PRAZO EST.</option><option value="13">PRAZO REAL</option></select>
                <input type="text" id="query2" onkeyup="sorter2.search('query2')" placeholder="Digite aqui a sua pesquisa...">
                <a style="text-decoration:none;font-weight:bold;font-size:11px;" href="javascript:document.getElementById('query2').value='';sorter2.reset()">RESTAURAR PADRÕES</a>
            </td>
            <td align="right">
                <div class="details" style="clear:both;">
                    <div>Resultado <span id="startrecord2">1</span>-<span id="endrecord2">20</span> de <span id="totalrecords2">44</span></div>
                </div>
            </td>
        </tr>
    </tbody>
</table>
<div style="max-width:100%;overflow:auto;">
    <table style="width: 1600px;" cellpadding="0" cellspacing="0" border="0" id="table2" class="tinytable">
        <thead>
            <tr>
                <th width="10" class="nosort"><h3></h3></th>
                <th width="50" ><h3>PROTOCOLO</h3></th>
                <th><h3>MOTIVO</h3></th>
                <th width="100"><h3>OBJETO</h3></th>
                <th><h3>SERVIÇO</h3></th>
                <th width="50"><h3>POSTAGEM</h3></th>
                <th width="50"><h3>PRAZO ESTIMADO</h3></th>
                <th width="50"><h3>PRAZO REAL</h3></th>
                <th width="80"><h3>VALOR</h3></th>
                <th width="80"><h3>VD</h3></th>
                <th><h3>DESTINATÁRIO</h3></th>
                <th width="80" ><h3>CEP</h3></th>
                <th><h3>SITUAÇÃO</h3></th>
                <th><h3>DEPARTAMENTO</h3></th>
            </tr>
        </thead>
        <tbody>
            <%                
                for (int i = 0; i < movimentacao.size(); i++) {
                    Movimentacao mov = (Movimentacao) movimentacao.get(i);

                    float valor = mov.getValorServico();
                    //vlrTotal = vlrTotal.add(new BigDecimal(valor));
                    String vValor = Util.FormatarDecimal.formatarFloat(valor);
                    float valord = mov.getValorDeclarado();
                    //vlrTotal = vlrTotal.add(new BigDecimal(valor));
                    String vVD = Util.FormatarDecimal.formatarFloat(valord);

                    Date data = mov.getDataPostagem();
                    String vData = sdf.format(data);

                    String pz_estimado = "---";
                    String pz_cumprido = "---";
                    String atrasado = "";
                    String motivo = "";
                    String imagem = "<img style='cursor:pointer;' class='link_img' src='../../imagensNew/megaphone_plus.png' onclick='chamaJanelaAbertura(\""+mov.getNumObjeto()+"\");'/>";
                    Integer[] codigoSinistro = new Integer[]{9, 28, 37, 43, 50, 51, 52};
                    if(Arrays.asList(codigoSinistro).contains(mov.getLast_status_code())){
                        atrasado = "color:orange;font-weight:bold;";
                        motivo = "Destinatário não recebeu a correspondência";
                        imagem = "<img class='link_img' src='../../imagensNew/megaphone_exclamation.png'/>";                        
                    }
                    
                    if (mov.getPrazo_estimado() != null) {
                        ContrReclamacao cr = new ContrReclamacao();
                        Calendar novadataPrevisaoEntrega = cr.recalculaDataEstimada(cr.dateToCalendar(mov.getDataPostagem()), cr.dateToCalendar(mov.getPrazo_estimado()), "");
                        if (novadataPrevisaoEntrega != null) {
                            mov.setPrazo_estimado(novadataPrevisaoEntrega.getTime());
                        }
                    }
                    if (mov.getPrazo_estimado() != null && mov.getPrazo_cumprido_date() != null) {
                        pz_estimado = sdf.format(mov.getPrazo_estimado());
                        pz_cumprido = sdf.format(mov.getPrazo_cumprido_date());
                        if (mov.getPrazo_estimado().before(mov.getPrazo_cumprido_date())) {
                            atrasado = "color:red;font-weight:bold;";
                            motivo = "Objeto entregue com atraso";
                            imagem = "<img class='link_img' src='../../imagensNew/megaphone.png'/>";
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
                            motivo = "Objeto entregue com atraso";
                            imagem = "<img class='link_img' src='../../imagensNew/megaphone.png'/>";
                        }
                    }
                    
                    if(mov.getPi_status_code() == 1){
                        imagem = "<img style='cursor:pointer;' class='link_img' src='../../imagensNew/megaphone_yellow.png' onclick='chamaJanelaFechamento(\""+mov.getNumObjeto()+"\");'/>";                    
                    }else if(mov.getPi_status_code() == 2){
                        imagem = "<img class='link_img' src='../../imagensNew/megaphone_red.png'/>";                            
                    }else if(mov.getPi_status_code() == 3){
                        imagem = "<img class='link_img' src='../../imagensNew/megaphone_green.png'/>";                            
                    }else if(mov.getPi_status_code() == -1){
                        imagem = "<img style='cursor:pointer;' class='link_img' src='../../imagensNew/megaphone_exclamation.png' onclick='chamaJanelaFechamento(\""+mov.getNumObjeto()+"\");'/>";                            
                    }
                    
            %>
            <tr align="center" style="font-size: 10px; <%= atrasado%>">
                <td><%= imagem %></td>
                <td>
                    <% if(mov.getPi_number() != null && !mov.getPi_number().equals("0")){ 
                        out.print(mov.getPi_number());
                     } %>
                </td>
                <td><%= motivo %></td>
                <td>
                    <form name="frm<%= mov.getNumObjeto()%>" id="frm<%= mov.getNumObjeto()%>" method="post" action="http://www2.correios.com.br/sistemas/rastreamento/Resultado.cfm" target="_blank">
                        <input type="hidden" name="objetos" id="objetos" value="<%= mov.getNumObjeto()%>">
                    </form>                    
                    <a href="#" onclick="document.getElementById('frm<%= mov.getNumObjeto()%>').submit();"><%= mov.getNumObjeto()%></a>
                </td>
                <td align="left" ><%= mov.getDescServico()%></td>
                <td><%= vData %></td>
                <td><%= pz_estimado %></td>
                <td><%= pz_cumprido %></td> 
                <td align="right">R$ <%= vValor %></td>
                <td align="right">R$ <%= vVD %></td>
                <td align="left"><%= mov.getDestinatario()%></td>
                <td><%= mov.getCep() %></td>
                <td align="left"><%= mov.getLast_status_name() %></td>
                <td align="left"><%= mov.getDepartamento() %></td>
            </tr>
            <%}%>
        </tbody>
        <%--<tfoot>
            <tr style="background: #f0f0f0; color:red; font-size: 12px;">
                <td colspan="14"></td>
            </tr>
        </tfoot>--%>
    </table>
</div>
<div id="tablefooter" align="center">
    <div align="left" style="float:left; width:20%;">
        <select onchange="sorter2.size(this.value)">
            <option value="5">5</option>
            <option value="10">10</option>
            <option value="20" selected="selected">20</option>
            <option value="50">50</option>
            <option value="100">100</option>
        </select>
        <span>Linhas por Página</span>
    </div>
    <div id="tablenav2" class="tablenav" style="display: block;">
        <div>
            <img src="../../javascript/plugins/TableSorter/images/left_end.png" width="20" height="20" alt="First Page" onclick="sorter2.move(-1, true)"/>
            <img src="../../javascript/plugins/TableSorter/images/left.png" width="20" height="20" alt="First Page" onclick="sorter2.move(-1)"/>
            <img src="../../javascript/plugins/TableSorter/images/right.png" width="20" height="20" alt="First Page" onclick="sorter2.move(1)"/>
            <img src="../../javascript/plugins/TableSorter/images/right_end.png" width="20" height="20" alt="Last Page" onclick="sorter2.move(1, true)"/>
            <select style="margin-left:5px;" id="pagedropdown2" onchange="sorter2.goto(this.value)"><option value="1">1</option><option value="2">2</option><option value="3">3</option></select>
            <a style="margin-left:10px;" href="javascript:sorter2.showall()">Ver Tudo</a>
        </div>
    </div>
    <div id="tablelocation">
        <div class="page">Página <span id="currentpage2">1</span> de <span id="totalpages2">3</span></div>
    </div>
</div>
<%} else {%>
<div align='center' style='padding-top:25px;background:#fc8878;color:black;height:50px;width:100%;font-size:20px;font-weight:bold;'>Nenhum Objeto Encontrado!</div>
<%}%>
<%} else {%>
sessaoexpirada
<%}%>