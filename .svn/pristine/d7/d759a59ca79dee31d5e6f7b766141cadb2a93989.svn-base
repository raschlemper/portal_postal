
<%@page import="Controle.contrCliente"%>
<%@page import="Emporium.Controle.ContrPreVenda"%>
<%@page import="Entidade.PreVenda"%>
<%@page import="Entidade.ClienteLogEtiqueta"%>
<%@ page import = "java.text.DateFormat,java.util.ArrayList,java.sql.Timestamp,java.util.Calendar, java.util.GregorianCalendar, java.util.Date, java.text.SimpleDateFormat"%>

<%
    if (session.getAttribute("usuario") == null) {
        response.sendRedirect("../index.jsp?msgLog=3");
    } else {

        SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        int idUsuario = (Integer) session.getAttribute("idUsuario");
        String nomeBD = (String) session.getAttribute("empresa");

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

    <head>
        <title>Portal Postal</title>
        <%@ include file="../includes/Css_js.jsp" %>
    </head>

    <body onload="fechaMsg();">   
        <script>
            waitMsg();
        </script>
        <jsp:include page="../includes/navBarTop.jsp"></jsp:include>
            <div id="wrapper">
            <%@ include file="../includes/menu_agencia_bootstrap.jsp" %>

            <div id="page-content-wrapper">
                <div class="container-fluid">
                    <div id="page-wrapper">

                        <!-- Indica o Local Da Página -->
                        <div class="row">
                            <div class="col-md-12">                       
                                <h4 class="page-header"><b class="text-primary"><i class="fa fa-barcode"></i> Gerenciar Etiquetas</b> > <small>Etiquetas Restantes</small></h4>
                            </div>
                        </div>

                        <!-- Mostra Aviso na tela -->
                        <div class="col-lg-12" id="msg" style="display: block">
                            <div class="alert alert-danger">
                                <a href="#" class="close" data-dismiss="alert">&times;</a>
                                <strong>ATENÇÃO!</strong> Existem clientes com poucas etiquetas. Verifique na tabela abaixo.
                            </div>
                        </div>

                        <!-- /.row -->
                        <div class="row">
                            <div class="col-lg-12">
                                <div class="panel panel-default">

                                    <div class="panel-heading" >Lista de Etiquetas Restantes dos Clientes</div>


                                    <div class="panel-body">
                                        <div class="dataTable_wrapper no-padding">
                                            <table class="table table-bordered table-striped table-hover table-heading table-datatable" id="dataTables-example">
                                                <thead>
                                                    <tr>
                                                        <th>Etiquetas Restantes</th>
                                                        <th>Nome do Cliente</th>
                                                        <th>Nome do Serviço</th>
                                                        <th>Tempo de Existência</th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <%                                                
                                                        boolean flag = false;
                                                        boolean flag2 = false;
                                                        ArrayList<ClienteLogEtiqueta> lista = Controle.ContrClienteEtiquetas.consultaQtdEtiquetasRestantes(nomeBD);
                                                        for (int j = 0; j < lista.size(); j++) {
                                                            ClienteLogEtiqueta hst = lista.get(j);
                                                            String cor = "";
                                                            if (hst.getNomeUsuario() != null && !hst.getNomeUsuario().equals("null")) {
                                                                if (hst.getQtd() <= 200) {
                                                                    cor = "style='color:red; font-weight: bold;'";
                                                                    flag = true;
                                                                }

                                                                int dias = (int) Util.FormatarData.diferencaEmDias(hst.getDataHora(), new Date());
                                                                if (dias > 90) {
                                                                    flag2 = true;
                                                                }

                                                    %>
                                                    <tr <%= cor%> >
                                                        <td><%= hst.getQtd()%></td>
                                                        <td><a style="color: inherit;" href="../Cadastros/cliente_etiquetas_b.jsp?idCliente=<%= hst.getIdCliente()%>"><%= hst.getNomeUsuario()%></a></td>
                                                        <td><%= hst.getNomeServico()%></td>
                                                        <td><%= dias%> Dias</td>
                                                    </tr>
                                                    <%}
                                                }%>
                                                </tbody>
                                            </table>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <!-- /.row -->
                    </div>
                </div>
            </div>
        </div>
        <!-- /#page-wrapper -->

        <!-- /#wrapper -->

        <script>
            function AllTables() {
                StartDataTable('dataTables-example');
                LoadSelect2Script(MakeSelectDataTable('dataTables-example'));
            }
            $(document).ready(function () {
                LoadDataTablesScripts(AllTables);
            });

            function alertaQtd() {
            <%if (flag) {%>
                bootbox.dialog({
                    title: "<span style='color:red; font-weight: bold;'>ATENÇÃO</span>",
                    message: "Existem Clientes com menos de 200 etiquetas."
                });
                $('#msg').css("display", "block");
            <%}%>
            }
        </script>

    </body>

</html>
<%}%>
