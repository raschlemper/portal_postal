
<%@page import="java.sql.Timestamp"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.GregorianCalendar"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%
    if (session.getAttribute("usuario") == null) {
        response.sendRedirect("../index.jsp?msgLog=3");
    } else {

        int idNivelDoUsuario = (Integer) session.getAttribute("nivel");
        if (idNivelDoUsuario > 2) {
            response.sendRedirect("../jsp/imp_movimento.jsp?msg=Acesso Negado!");
        }

        SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        int idUsuario = (Integer) session.getAttribute("idUsuario");
        String nomeBD = (String) session.getAttribute("empresa");
        int num = Controle.contrCliente.numeroClientes(nomeBD);

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
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
                                <h4 class="page-header"><b class="text-primary"><i class="fa fa-save"></i> Importações</b> > <small>Arquivo de Clientes</small></h4>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-12">   
                                <div class="alert alert-info no-margin">
                                    <a href="#" class="close" data-dismiss="alert">&times;</a>
                                    Você possui <b style="color:red;"><%= num%></b> Clientes.
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="well well-md">  
                                <form class="form-inline" name="form1" action="../../ServImportacao" method="post" enctype="multipart/form-data">
                                    <ul class="list-unstyled">
                                        <li class="list-group-item">
                                                <label>Escolha o arquivo para importar</label><br/>
                                                <span class="btn btn-default btn-file"><i class="fa fa-folder-open"></i> Selecionar arquivo (clientes.txt) <input type="file" name="arquivo" /></span>
                                                <input type="hidden" id="tipoForm" name="tipoForm" value="imagem" />
                                        </li>
                                        <li class="list-group-item">                                    
                                            <div class="alert alert-danger no-margin">
                                                * Após a exportação no SECT o arquivo geralmente é encontrado em "C:/clientes.txt".<br/>
                                                * Para alterar este caminho no SECT vá em "Arquivos > Exportação > Destino dos Arquivos".<br/>
                                                * O arquivo não pode ser maior que 2MB.
                                            </div>
                                        </li>
                                        <li class="list-group-item">
                                            <button type="button" class="btn btn-success" onclick="return validaForm();"><i class="fa fa-lg fa-spc fa-upload"></i> IMPORTAR CLIENTES</button>
                                        </li>
                                    </ul>   
                                </form>
                            </div>   
                        </div>                          

                        <!-- /.row -->
                        <div class="row">
                            <div class="col-lg-12">
                                <div class="panel panel-default">
                                    <div class="panel-heading" >Histórico das importações de clientes</div>
                                    <div class="panel-body">
                                        <div class="dataTable_wrapper no-padding">
                                            <table class="table table-striped table-bordered table-hover table-condensed" id="dataTables-importArq">
                                                <thead>
                                                    <tr>
                                                        <th>Usuário que Importou</th>
                                                        <th>Data da Importação</th>
                                                        <th>QTD. de Clientes Importados</th>
                                                        <th>QTD. de Clientes Excluidos</th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <%
                                                        ArrayList listaHistImport2 = Controle.contrCliente.consultaHistoricoImportCli(nomeBD);
                                                        for (int j = 0; j < listaHistImport2.size(); j++) {
                                                            Entidade.HistoricoImport hst = (Entidade.HistoricoImport) listaHistImport2.get(j);
                                                            String vDataImportacao = "";
                                                            Date dataImportacao = hst.getDataImportacao();
                                                            if (dataImportacao != null) {
                                                                vDataImportacao = sdf2.format(dataImportacao);
                                                            }
                                                            int qtdClientes = hst.getQtdCliente();
                                                            int qtdExcluido = hst.getQtdExcluido();
                                                            int idUsuarioRealizou = hst.getIdUsuario();
                                                            String nomeUsuario = Controle.contrUsuario.consultaNomeUsuarioById(idUsuarioRealizou, nomeBD);
                                                    %>
                                                    <tr>
                                                        <td><%= nomeUsuario%></td>
                                                        <td align="center"><%= vDataImportacao%></td>
                                                        <td align="right"><%= qtdClientes%></td>
                                                        <td align="right"><%= qtdExcluido%></td>
                                                    </tr>
                                                    <%}%>
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

            <script type="text/javascript">
                function validaForm() {
                    var form = document.form1;
                    if (form.arquivo.value === "") {
                        alert("Escolha o arquivo de clientes a ser importado!\nGeralmente encontrado em 'C:/clientes.txt'.");
                        return false;
                    } else {
                        var indexA = form.arquivo.value.lastIndexOf(".");
                        var indexB = form.arquivo.value.length;
                        var ext = form.arquivo.value.substring(indexA, indexB).toUpperCase();
                        if (ext !== ".TXT") {
                            alert("O arquivo a ser importado deve ser '.TXT' !");
                            return false;
                        }
                    }

                    form.submit();
                }


                function AllTables() {
                    StartDataTable('dataTables-importArq');
                    LoadSelect2Script(MakeSelectDataTable('dataTables-importArq'));
                    fechaMsg();
                }

                $(document).ready(function () {
                    LoadDataTablesScripts(AllTables);
                });
            </script>

    </body>
</html>
<%}%>
