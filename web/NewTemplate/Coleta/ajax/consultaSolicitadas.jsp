<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="Entidade.Clientes"%>
<%@page import="Coleta.Controle.contrColeta"%>
<%@page import="Entidade.StatusEntrega"%>
<%@page import="Coleta.Controle.contrColetador"%>
<%@ page import = "java.text.DateFormat,java.util.ArrayList,java.sql.Timestamp, java.util.Date, java.text.SimpleDateFormat"%>
<%
    response.setHeader("Cache-Control", "no-cache"); //HTTP 1.1
    response.setHeader("Pragma", "no-cache"); //HTTP 1.0
    response.setDateHeader("Expires", 0); //prevent caching at the proxy server

    DateFormat df1 = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat sdf3 = new SimpleDateFormat("dd/MM/yyyy HH:mm");

    String nomeBD = (String) session.getAttribute("empresa");
    if (nomeBD == null) {
        response.sendRedirect("../index.jsp?msgLog=3");
    } else {

        Integer idUsuario = (Integer) session.getAttribute("idUsuario");

        int situacao = 2;
        int idCol = 0;

        String dataAtual = sdf1.format(new Date());
        String dataPesquisaBD = sdf2.format(new Date());
        String dataPesquisa = dataAtual;
        if (request.getParameter("dataPesquisa") != null && !request.getParameter("dataPesquisa").equals("")) {
            Date dataAux = df1.parse(request.getParameter("dataPesquisa"));
            dataPesquisa = sdf1.format(dataAux);
            dataPesquisaBD = sdf2.format(dataAux);
        }

        ArrayList listaColetadores = contrColetador.consultaTodosColetadoresColeta(dataPesquisaBD, 2, nomeBD);
        ArrayList<String> listaColetasAntigasNaoFinalizadas = contrColeta.consultaColetasAntigasNaoFinalizadas(idCol, nomeBD);
        ArrayList listaColetas = contrColeta.consultaColetasPelaWeb("dataHoraColeta, c.idColetador", nomeBD);
        String qtdSolicitadas = contrColeta.consultaQtdColetasSolicitadas(nomeBD);
        ArrayList<StatusEntrega> listaStatus = Controle.ContrStatusEntrega.consultaTodosStatus(nomeBD);
%>
<form name="formColetas" action="#" method="post">
    <% //For carregando as coletas solicitadas que não tem coletador designado
        int cont = 0;
        if (listaColetas.size() != 0) {
    %>
    <div class="col-md-12">

        <div class="panel panel-default">
            <div class="panel-heading" > 
                Lista das Coletas Solicitadas pela WEB

            </div>
            <div class="panel-body">
                <div class="dataTable_wrapper no-padding">
                    <table class="table table-striped table-bordered table-hover table-condensed">
                        <thead>                   
                            <tr>
                                <th><b>Nº</b></th>
                                <th>TS</th>
                                <th><b>Hora Coleta</b></th>
                                <th><b>Coletador</b></th>
                                <th><b>Nome Fantasia</b></th>
                                <th><b>Observações</b></th>
                                <th  class="text-center"><a onclick="marcarTudo();" style="cursor:pointer;color:blue;">SEL</a></th>
                            </tr>
                        </thead>
                        <tbody>
                        <%
                            for (int s = 0; s < listaColetas.size(); s++) {
                                Coleta.Entidade.Coleta col = (Coleta.Entidade.Coleta) listaColetas.get(s);
                                int idColetador = col.getIdColetador();
                                int idColeta = col.getIdColeta();
                                int idCli = col.getIdCliente();
                                String nomeFantasia = col.getNomeFantasia();
                                String tipo = col.getTipoColeta();
                                String obs = col.getObs();
                                String dataColeta = sdf3.format(col.getDataHoraColeta());
                                String coletador = col.getNomeColetador();
                                if (coletador == null) {
                                    coletador = "<b>Sem Coletador!</b>";
                                }
                                cont++;

                                int ts = col.getTipoSolicitacao();
                                String img = "../../imagensNew/download.png";
                                if (ts == 1) {
                                    img = "../../imagensNew/phone_2.png";
                                } else if (ts == 2) {
                                    img = "../../imagensNew/download.png";
                                } else if (ts == 3) {
                                    img = "../../imagensNew/globe_3.png";
                                }
                        %>
                            <tr>
                                <td><b><%= cont%></b></td>
                                <td align="center">
                                    <img style="cursor: pointer;" onclick="buscaLogColeta(<%= idColeta%>);" onmouseout="javascript:document.getElementById('tooltip<%= idColeta%>').className = 'esconder';" src="<%= img%>" alt="Tipo da Solicitação" />

                                </td>
                                <td><%= dataColeta%></td>
                                <td><%= coletador%></td>
                                <td>
                                    <%
                                        Clientes cli = Controle.contrCliente.consultaClienteById(idCli, nomeBD);
                                        String rua = cli.getEndereco();
                                        String compl = cli.getComplemento();
                                        String bairro = cli.getBairro();
                                        String cid = cli.getCidade();
                                        String tel = cli.getTelefone();

                                        String resultado = rua + "<br/>" + compl + "<br/>" + bairro + "<br/>" + cid;
                                        resultado = resultado.toUpperCase();

                                    %>
                                    <span class="popover-options">
                                        <a title="TELEFONE: <h4><%=tel%></h4>"  data-container="body" data-toggle="popover" data-content="<%= resultado%>" style="cursor: pointer;">
                                            <%= nomeFantasia%>
                                        </a>
                                    </span>
                                    <td>
                                        <span class= "xedit" id="<%= idColeta%>;<%= idCol%>">
                                            <i class="fa fa-pencil fa-spc"></i> <%= obs%>
                                        </span>
                                    </td>
                                    <td align="center">
                                        <input type="checkbox" name="idColeta<%= cont%>" id="check<%= idCol%>" value="<%= idColeta%>" />
                                        <input type="hidden" name="idColetador<%= cont%>" value="<%= idColetador%>" />
                                    </td>
                            </tr>
                            <%}%>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>

                        
        <div class="col-xs-12 col-sm-12 col-md-5 col-lg-4 form-group pull-left">
            <div class="well no-margin">
                <%if (situacao <= 2) {%>
                <label>Transferir coleta(s) selecionada(s) para: </label>
                <input type="hidden" name="idColetador1" value="<%= idCol%>"/>
                <input type="hidden" name="contador" value="<%= cont%>"/>
                <input type="hidden" name="pagina" value="acompanhamento.jsp"/>
                <select class="form-control" name="idColetador">
                    <option value="0">-- SELECIONE UM COLETADOR --</option>
                    <%
                        for (int i = 0; i < listaColetadores.size(); i++) {
                            Coleta.Entidade.Coletador coletador = (Coleta.Entidade.Coletador) listaColetadores.get(i);
                            String nomeColetador = coletador.getNome();
                            int idColetador = coletador.getIdColetador();
                    %>
                    <option value="<%= idColetador%>"><%= nomeColetador%></option>
                    <%}%>
                </select>
                    <button class="btn btn-info" type="submit" onclick="submitForm('../../ServAlterarColetadorColeta');">
                        <i class="fa fa-lg fa-spc fa-exchange"></i> 
                        ALTERAR COLETADOR
                    </button>                                        
                <%}%>
            </div>
        </div>
            
        <div class="col-xs-12 col-sm-12 col-md-5 col-lg-4 form-group pull-right">
            <div class="well no-margin">
                <button class="btn btn-success" type="button" onClick="submitForm2('../../ServRepassarColeta');"><i class="fa fa-check-circle fa-lg fa-spc"></i> CONFIRMAR COLETAS SOLICITADAS</button>
            </div>
        </div>
</form>

<%}%>

<%}%>