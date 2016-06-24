
<%@page import="Controle.ContrAmarracaoServico"%>
<%@page import="Controle.ContrClienteEtiquetas"%>
<%@page import="Controle.contrCliente"%>
<%@page import="Emporium.Controle.ContrPreVenda"%>
<%@page import="Entidade.PreVenda"%>
<%@page import="Entidade.ClienteLogEtiqueta"%>
<%@ page import = "java.text.DateFormat,java.util.ArrayList,java.sql.Timestamp,java.util.Calendar, java.util.GregorianCalendar, java.util.Date, java.text.SimpleDateFormat"%>

<%
    Usuario usrSessao = (Usuario) session.getAttribute("agf_usuario");
    if (usrSessao == null) {
        response.sendRedirect("../../index.jsp?msgLog=3");
    } else if (usrSessao.getListaAcessosPortalPostal().contains("403")) {
        response.sendRedirect("../../NewTemplate/Dashboard/index.jsp?msg=Usuario sem permissao!");
    } else {

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

                        <div class="row">
                            <div class="col-md-12">                       
                                <h4 class="page-header"><b class="text-primary"><i class="fa fa-gears"></i> Cadastros</b> > <small>Amarrações</small></h4>
                            </div>
                        </div>
                        <div class="row">
                            <div class="well well-md"> 
                                <button class="btn btn-success" type="submit" onclick="javascript:window.location = 'amarracao_inserir_b.jsp';" name="save" id="sub"> <i class="fa fa-lg fa-spc fa-plus"></i> ADCIONAR NOVA AMARRAÇÃO</button>
                            </div>
                        </div>
                        <!-- /.row -->
                        <div class="row">
                            <div class="col-lg-12">
                                <div class="panel panel-default">
                                    <div class="panel-heading" >Lista das Amarrações</div>
                                    <div class="panel-body">
                                        <div class="dataTable_wrapper no-padding">
                                            <table class="table table-striped table-bordered table-hover table-condensed" id="dataTables-example">
                                                <thead>
                                                    <tr>
                                                        <th>Nº</th>
                                                        <th>Sigla</th>
                                                        <th>Descrição</th>
                                                        <th>Serviços</th>
                                                        <th class="no-sort" style="width: 100px;">Alterar</th>
                                                        <th class="no-sort" style="width: 100px;">Excluir</th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <%                                                    
                                                    ArrayList<Entidade.Amarracao> listaAm = Controle.ContrAmarracao.consultaTodosAmarracao(nomeBD);
                                                        for (int j = 0; j < listaAm.size(); j++) {
                                                            Entidade.Amarracao am = listaAm.get(j);
                                                            String nomeAm = am.getNomeAmarracao();
                                                            String siglaAm = am.getSiglaAmarracao();
                                                            int idAmarracao = am.getIdAmarracao();

                                                            ArrayList<String> listaServ = ContrAmarracaoServico.consultaTodosServicosByIdAmarracao(idAmarracao, nomeBD);
                                                            String serv = "";
                                                            for (int i = 0; i < listaServ.size(); i++) {
                                                                if (i == 0) {
                                                                    serv += listaServ.get(i);
                                                                } else {
                                                                    serv += " <b>|</b> " + listaServ.get(i);
                                                                }
                                                            }

                                                    %>
                                                    <tr>
                                                        <td align="right"><%= idAmarracao%></td>
                                                        <td align="center"><b><%= siglaAm%></b></td>
                                                        <td><%= nomeAm%></td>
                                                        <td><%= serv%></td>
                                                        <td align="center">
                                                            <a href="amarracao_editar_b.jsp?idAmarracao=<%= idAmarracao%>" class="btn btn-sm btn-warning" ><i class="fa fa-lg fa-pencil-square-o"></i></a>
                                                        </td>
                                                        <td align="center">
                                                            <form action="../../ServExcluirAmarracao" method="post" name="formDel">
                                                                <input type="hidden" name="idAmarracao" value="<%= idAmarracao%>" />
                                                                <button type="button" class="btn btn-sm btn-danger" onclick="confirmExcluir(this);" ><i class="fa fa-lg fa-trash"></i></button>
                                                            </form>
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

                        <!-- /.row -->
                    </div>
                    <!-- /#page-wrapper -->

                </div>
            </div>
        </div>
        <!-- /#wrapper -->

        <script type="text/javascript">
        function confirmExcluir(button) {                
                bootbox.confirm({
                    title: 'Excluir Amarração?',
                    message: 'Deseja realmente excluir esta amarração?',
                    buttons: {
                        'cancel': {
                            label: '<i class="fa fa-lg fa-times fa-spc"></i> CANCELAR',
                            className: 'btn btn-default pull-left'
                        },
                        'confirm': {
                            label: '<i class="fa fa-lg fa-trash fa-spc"></i> EXCLUIR',
                            className: 'btn btn-danger pull-right'
                        }
                    },
                    callback: function(result) {
                        if(result){
                            button.form.submit();
                        }
                    }
                });
            }

            function AllTables() {
                StartDataTable('dataTables-example');
                LoadSelect2Script(MakeSelectDataTable('dataTables-example'));
            }

            $(document).ready(function() {
                LoadDataTablesScripts(AllTables);
            });
        </script>
    </body>
</html>
<%}%>
