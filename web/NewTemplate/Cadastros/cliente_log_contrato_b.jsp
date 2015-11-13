<%-- 
    Document   : cliente_log_contrato
    Created on : Apr 16, 2015, 10:14:31 AM
    Author     : Fernando
--%>
<%@page import="java.util.ArrayList"%>
<%@page import="Controle.ContrClienteContrato"%>
<%@page import="Controle.contrCliente"%>
<%@page import="Entidade.Clientes"%>
<%@page import="Entidade.LogAtualizacaoContratos"%>
<%@page import="Controle.ContrLogAtualizacaoContrato"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    if (session.getAttribute("usuario") == null) {
        response.sendRedirect("../../index.jsp?msgLog=3");
    } else {

        int idNivelDoUsuario = (Integer) session.getAttribute("nivel");
        String nomeBD = (String) session.getAttribute("empresa");
        int idEmpresa = (Integer) session.getAttribute("idEmpresa");
        empresas emp = (empresas) session.getAttribute("emp");
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy HH:mm");

        String msga = (String) session.getAttribute("msg2");
        session.setAttribute("msg2", null);
        LogAtualizacaoContratos log = ContrLogAtualizacaoContrato.consultaUltimoLog(nomeBD);
%>

<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <title>Portal Postal</title>
        <%@ include file="../includes/Css_js.jsp" %>
        <style>
            a.tip {
                border-bottom: 1px dashed;
                text-decoration: none
            }
            a.tip:hover {
                cursor: help;
                position: relative
            }
            a.tip span {
                display: none
            }
            a.tip:hover span {
                border: #c0c0c0 1px dotted;
                padding: 10px;
                display: block;
                z-index: 100;
                background: url(../images/status-info.png) #f0f0f0 no-repeat 100% 5%;
                left: 0px;
                margin: 10px;
                position: absolute;
                top: 15px;
                white-space: nowrap;
                text-decoration: none;
                font-weight:bold;
            }
        </style>
    </head>
    <body onload="fechaMsg();">   
        <script type="text/javascript">
            waitMsg();
        </script>
        <jsp:include page="../includes/navBarTop.jsp"></jsp:include>
            <div id="wrapper">
            <%@ include file="../includes/menu_agencia_bootstrap.jsp" %>

            <div id="page-content-wrapper">
                <div class="container-fluid">
                    <div id="page-wrapper">

                        <div class="row">
                            <div class="col-md-12">                       
                                <h4 class="page-header"><b class="text-primary"><i class="fa fa-gears"></i> Cadastros</b> > <small>Verificação de Contratos</small></h4>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-12">                                                                
                                <% if (log != null) {%>
                                <div class="alert alert-info">
                                    <i class="fa fa-info-circle fa-lg fa-spc"></i>&nbsp;&nbsp;&nbsp;<b>Última Verificação:</b> <%= sdf2.format(log.getDataHora())%>
                                </div>
                                <%}%>                                
                                <form action="../../ServAtualizaContrato" name="formAtualiza" method="post">
                                    <ul class="list-unstyled" >
                                        <li class="list-group-item list-group-heading">
                                            <label>VERIFICAR DADOS DOS CONTRATOS ECT</label>
                                        </li>
                                        <li class="list-group-item">
                                            <button type="button" onclick="verificaComErro();" class="btn btn-primary"><i class="fa fa-refresh fa-lg fa-spc"></i> VERIFICAR CONTRATOS ECT</button>
                                            <input type="hidden" name="comErro" id="comErro" value="0"/>                                                 
                                        </li>
                                    </ul>
                                </form>

                                <div class="panel panel-default">
                                    <div class="panel-heading">Log da Última Verificação dos Contratos ECT</div>
                                    <div class="panel-body">
                                        <div class="dataTable_wrapper no-padding">
                                            <table class="table table-striped table-bordered table-hover table-condensed" id="dataTables-example">
                                                <thead>
                                                    <tr>
                                                        <th width="70">Nº</th>
                                                        <th>Nome Fantasia</th>
                                                        <th width="270">Serviços ECT</th>
                                                        <th width="125">Vigência</th>
                                                        <th width="300">Msg. do SigepWEB</th>
                                                        <th class="no-sort" width="80">Config.</th>
                                                    </tr>
                                                </thead>
                                                <tbody>

                                                    <% if (log != null) {
                                                            String[] mgFalha = log.getMsgFalha().split(";");
                                                            String[] cf = log.getCodFalha().split(";");
                                                            for (int i = 0; i < mgFalha.length; i++) {
                                                                String falha = mgFalha[i];
                                                                String[] cFalha = cf[i].split("@");
                                                                if (cFalha != null && cf[i].contains("@")) {
                                                                    String nome = "";
                                                                    if (cFalha.length == 2) {
                                                                        nome = cFalha[1];
                                                                    }
                                                    %>   
                                                    <tr>
                                                        <td align="right"><%= cFalha[0]%></td>
                                                        <td><%= nome%></td>
                                                        <td>- - -</td>
                                                        <td>Falha no SigepWEB</td>
                                                        <td style="color:red;"><%= falha%></td>
                                                        <td align="center"><a class="btn btn-sm btn-warning" href="cliente_contrato_b.jsp?idCliente=<%= cFalha[0]%>" ><i class="fa fa-lg fa-pencil-square-o"></i></a></td>
                                                    </tr>
                                                    <%}
                                                            }%>
                                                    <%
                                                        String[] serv = log.getMsgSucesso().split(";");
                                                        String[] cod = log.getCodSucesso().split(";");
                                                        for (int i = 0; i < cod.length; i++) {
                                                            String[] c = cod[i].split("@");

                                                            String s = "";
                                                            String[] saux = serv[i].split("@");
                                                            ArrayList<Integer> listaServ = ContrClienteContrato.consultaContratoCliente(Integer.parseInt(c[0]), nomeBD);
                                                            for (String se : saux) {
                                                                if (se.contains("-")) {
                                                                    String[] aux = se.split(" - ");
                                                                    if (listaServ.contains(Integer.parseInt(aux[0]))) {
                                                                        s += "<span style='color:green;'>" + se + "</span><br/>";
                                                                    } else {
                                                                        s += "<a style='color:red;' class='tip'>" + se + " <span>Serviço não cadastrado no contrato do cliente do Portal Postal.</span></a><br/>";
                                                                    }
                                                                } else {
                                                                    se += se;
                                                                }
                                                            }

                                                            Clientes cli = contrCliente.consultaClienteById(Integer.parseInt(c[0]), nomeBD);
                                                            if(cli != null){

                                                            String cnpj = cli.getCnpj().trim();
                                                            String cnpjSara = c[2].trim();

                                                            String codAdm = cli.getCodAdministrativo().trim();
                                                            String codAdmSara = Integer.parseInt(c[3].trim()) + "";

                                                            String dtVg = sdf.format(cli.getDtVigenciaFimContrato());
                                                            String dataVg = c[5];
                                                            //String ano = c[6];
                                                            //String uf = c[7];
                                                            String msgm = "";
                                                            String cor = " style='color:green;'";
                                                            if (!dtVg.equals(dataVg)) {
                                                                msgm += "<br/>Data de Vigência cadastrada diferente do SARA!<br/>"
                                                                        + "<b>Data Vigência SARA: </b>" + dataVg
                                                                        + "<br/><b>Data Vigência Portal Postal: </b>" + dtVg;
                                                                cor = " style='color:red;'";
                                                            }
                                                            if (!codAdm.equals(codAdmSara)) {
                                                                msgm += "<br/>Cód. Adm. cadastrado diferente do SARA!<br/>"
                                                                        + "<b>Cód Adm. SARA: </b>" + codAdmSara
                                                                        + "<br/><b>Cód. Adm. Portal Postal: </b>" + codAdm;
                                                                cor = " style='color:red;'";
                                                            }
                                                            if (!cnpj.equals(cnpjSara)) {
                                                                msgm += "<br/>CNPJ cadastrado diferente do SARA!<br/>"
                                                                        + "<b>CNPJ SARA: </b>" + cnpjSara
                                                                        + "<br/><b>CNPJ Portal Postal: </b>" + cnpj;
                                                                cor = " style='color:red;'";
                                                            }
                                                            if (msgm.equals("")) {
                                                                msgm = "SigepWEB OK!";
                                                            }

                                                            String status = "Suspenso/Cancelado";
                                                            if (c[4].equals("1")) {
                                                                status = "Válido até " + dataVg;
                                                            }
                                                    %>
                                                    <tr>
                                                        <td align="right"><%= c[0]%></td>
                                                        <td><%= c[1]%></td>
                                                        <td><%= s%></td>
                                                        <td><%= status%></td>
                                                        <td <%=cor%>><%= msgm%></td>
                                                        <td align="center"><a href="cliente_contrato_b.jsp?idCliente=<%= c[0]%>" class="btn btn-sm btn-warning" ><i class="fa fa-lg fa-pencil-square-o"></i></a></td>
                                                    </tr>
                                                    <%}}}%>
                                                </tbody>
                                            </table>

                                        </div>
                                    </div>
                                </div>

                            </div>   
                        </div>   
                    </div>   
                </div>   
            </div>   
        </div>   
        <script>

            function AllTables() {
                StartDataTable('dataTables-example');
                LoadSelect2Script(MakeSelectDataTable('dataTables-example'));
                fechaMsg();
            }

            $(document).ready(function() {
                LoadDataTablesScripts(AllTables);
            });

            function verificaComErro() {
                bootbox.confirm({
                    title: 'Verificação de Contratos',
                    message: 'Este processo pode demorar alguns minutos.<br/><br/>Deseja verificar os contratos dos clientes?',
                    buttons: {
                        'cancel': {
                            label: '<i class="fa fa-lg fa-times fa-spc"></i> CANCELAR',
                            className: 'btn btn-default pull-left'
                        },
                        'confirm': {
                            label: '<i class="fa fa-lg fa-refresh fa-spc"></i> VERIFICAR CONTRATOS',
                            className: 'btn btn-primary pull-right'
                        }
                    },
                    callback: function(result) {
                        if (result) {
                            bootbox.hideAll();
                            waitMsg();
                            document.getElementById("comErro").value = 0;
                            document.formAtualiza.submit();
                        }
                    }
                });
            }
        </script>
    </body>
</html>
<%}%>
