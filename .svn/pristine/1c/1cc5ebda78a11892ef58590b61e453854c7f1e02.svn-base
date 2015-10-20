<%@page import="Entidade.LogAtualizacaoContratos"%>
<%@page import="Controle.ContrLogAtualizacaoContrato"%>
<%@page import="Entidade.Clientes"%>
<%@ page import = "java.util.ArrayList,java.sql.Timestamp, java.util.Date, java.text.SimpleDateFormat"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
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
    </head>
    <body>   
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
                                <h4 class="page-header"><b class="text-primary"><i class="fa fa-gears"></i> Cadastros</b> > <small>Clientes</small></h4>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-12">   
                                <div class="panel panel-default">
                                    <div class="panel-heading">Lista de Todos os Clientes</div>
                                    <div class="panel-body">
                                        <div class="dataTable_wrapper no-padding">
                                            <table class="table table-striped table-bordered table-hover table-condensed" id="dataTables-example">
                                                <thead>
                                                    <tr>
                                                        <th width="70">Nº</th>
                                                        <th>Razão Social</th>
                                                        <th>Nome Fantasia</th>
                                                        <th width="170">Status Contrato</th>
                                                        <th width="120">SigepWEB</th>
                                                        <th width="120">Vigência</th>
                                                        <th class="no-sort text-center" width="50"><span class="fa fa-pencil-square-o"></span></th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <%
                                                        ArrayList<Clientes> listaCliente = Controle.contrCliente.getNomeCodigoMetodo(nomeBD);
                                                        for (int j = 0; j < listaCliente.size(); j++) {
                                                            Clientes col = listaCliente.get(j);
                                                            String nomeCliente = col.getNome();
                                                            int idCliente = col.getCodigo();
                                                            String nomeFantasia = col.getNomeFantasia();
                                                            Date dt = col.getDtVigenciaFimContrato();
                                                            String dtvg = "- - -";
                                                            if (dt != null) {
                                                                dtvg = sdf.format(dt);
                                                            }
                                                            String dtAt = "- - -";
                                                            if (col.getDataHoraAtualizacao() != null) {
                                                                dtAt = sdf2.format(col.getDataHoraAtualizacao());
                                                            }
                                                            if (col.getErro_atualizacao() == 1) {
                                                                dtAt = "<b style='color:red;font-weight:bold;'>FALHA AO ATUALIZAR</b>";
                                                            }

                                                            String statusCp = "<a href='cliente_contrato_b.jsp?idCliente=" + idCliente + "' style='color:silver;font-weight:bold;'>SEM CONTRATO ECT</a>";
                                                            if (col.getTemContrato() == 1) {
                                                                if (col.getStatusCartaoPostagem() == 1) {
                                                                    statusCp = "<a href='cliente_contrato_b.jsp?idCliente=" + idCliente + "' style='color:green;font-weight:bold;'>VÁLIDO</a>";
                                                                } else {
                                                                    statusCp = "<a href='cliente_contrato_b.jsp?idCliente=" + idCliente + "' style='color:red;font-weight:bold;'>INVÁLIDO/SUSPENSO</a>";
                                                                }
                                                            }
                                                            String sigep = "<b>POSSUI</b>";
                                                            if (col.getTemContrato() == 0) {
                                                                sigep = "";
                                                            } else if (col.getLogin_sigep() == null || col.getLogin_sigep().equals("null") || col.getLogin_sigep().equals("")) {
                                                                sigep = "<b style='color:red;'>NÃO POSSUI</b>";
                                                            }
                                                    %>
                                                    <tr>
                                                        <td align="right"><%= idCliente%></td>
                                                        <td style="font-size: 11px;"><%= nomeCliente%></td>
                                                        <td style="font-size: 11px;"><%= nomeFantasia%></td>
                                                        <%--<td align="center"><%= dtAt%></td>--%>
                                                        <td><%= statusCp%></td>
                                                        <td><%= sigep%></td>
                                                        <td align="center"><%= dtvg%></td>
                                                        <td align="center">
                                                            <a href="cliente_usuarios_b.jsp?idCliente=<%= idCliente%>" class="btn btn-sm btn-warning" ><i class="fa fa-lg fa-pencil-square-o"></i></a>
                                                        </td>
                                                    </tr>
                                                    <%}%>
                                                </tbody>
                                            </table>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row spacer-xlg"></div>
                    </div>
                </div>
            </div>
        </div>
        <script type="text/javascript">
            function AllTables() {
                StartDataTable('dataTables-example');
                LoadSelect2Script(MakeSelectDataTable('dataTables-example'));
                fechaMsg();
            }

            $(document).ready(function() {
                LoadDataTablesScripts(AllTables);
            });
        </script>
    </body>
</html>
<%}%>