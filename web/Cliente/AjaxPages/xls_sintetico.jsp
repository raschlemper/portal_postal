<%@page import="Entidade.empresas"%>
<%@page import="Entidade.ClientesUsuario"%>
<%@page import="java.math.BigDecimal"%>
<%@page import="Util.FormataString"%>
<%@ page import="java.sql.*, javax.swing.*, java.util.*, java.text.SimpleDateFormat, java.text.DecimalFormat, java.util.Date" %>
<%
    response.setContentType("application/xls");
    response.setHeader("Content-disposition", "attachment; filename=relatorio_sintetico.xls");
    response.setHeader("Cache-Control", "no-cache");

    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    int qtdEnc = 0, qtdPos = 0, qtdEnt = 0, qtdDev = 0, qtdExt = 0, qtdTotal = 0;
    BigDecimal vlrTotal = new BigDecimal(0);

    if (session.getAttribute("usuario_sessao_cliente") != null) {

        ClientesUsuario us = (ClientesUsuario) session.getAttribute("usuario_sessao_cliente");
        empresas emp = (empresas) session.getAttribute("agencia");
    String sql = request.getParameter("sql");
    ArrayList movimentacao = Controle.contrMovimentacao.getConsultaSintetica(sql, emp.getCnpj());

    if (movimentacao.size() >= 1) {
%>
    <table cellpadding="0" cellspacing="0" border="0">
        <thead>
            <tr>
                <th><h5>OBJETO</h5></th>
                <th><h5>SERVIÇO</h5></th>
                <th><h5>PESO</h5></th>
                <th><h5>QTD</h5></th>
                <th><h5>POSTAGEM</h5></th>
            <%if (us.getAcessos().contains(3)) {%>
                <th><h5>VALOR</h5></th>
                <%}%>
                <th><h5>DESTINATÁRIO</h5></th>
                <th><h5>CEP</h5></th>
                <th><h5>SITUAÇÃO</h5></th>
                <th><h5>NF</h5></th>
                <th><h5>DEPARTAMENTO</h5></th>
            </tr>
        </thead>
        <tbody>
            <%
                for (int i = 0; i < movimentacao.size(); i++) {
                    Entidade.Movimentacao mov = (Entidade.Movimentacao) movimentacao.get(i);
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
                    String status = mov.getStatus();

                    String numVenda = mov.getNumVenda();
                    String numCaixa = mov.getNumCaixa();

                    String codStatus = "" + mov.getCodStatus();
                    String img_status = "";
                    String grupoStatus = Util.Situacao.consultaGrupoStatus(codStatus, status);
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
            <tr align='center' style="font-size: 10px;">
                <td><a href='http://websro.correios.com.br/sro_bin/txect01$.inexistente?p_itemcode=&amp;p_lingua=001&amp;p_teste=&amp;p_tipo=003&amp;z_action=&amp;p_cod_lis=<%= numeroRegistro%>' target=_blank><%= numeroRegistro%></a></td>
                <td align='left'><%= servico2%></td>
                <td><%= peso%>g</td>
                <td><%= qtd%></td>
                <td><%= vData%></td>
            <%if (us.getAcessos().contains(3)) {%>
                <td nowrap align='left'>R$ <%= vValor%></td>
                <% }%>
                <td style="font-size: 10px;"><%= destinatario%></td>
                <td><%= cepDestino%></td>
                <td><%= status%></td>
                <td><%= notaFiscal%></td>
                <td><%= departamento2%></td>
            </tr>
            <%}%>
        </tbody>
        <tfoot>
            <tr style="background: #f0f0f0; color:red; font-size: 12px;">
                <td colspan="3"></td>
                <td nowrap="true" align="center"><%= qtdTotal%></td>
                <td></td>
            <%if (us.getAcessos().contains(3)) {%>
                <td nowrap="true">R$ <%= Util.FormatarDecimal.formatarFloat(vlrTotal.floatValue())%></td>
                <%}%>
                <td colspan="5"></td>
            </tr>
        </tfoot>
    </table>
<%} else {%>
<div align='center' style='padding-top:25px;background:#fc8878;color:black;height:50px;width:100%;font-size:20px;font-weight:bold;'>Nenhum Objeto Encontrado!</div>
<%}%>
<%} else {%>
<div align='center' style='padding-top:25px;background:#fc8878;color:black;height:50px;width:100%;font-size:20px;font-weight:bold;'>Sessão expirada! Faça o login novamente!</div>
<%}%>