
<%@page import="Controle.ContrClienteEtiquetas"%>
<%@page import="Controle.contrCliente"%>
<%@page import="Emporium.Controle.ContrPreVenda"%>
<%@page import="Entidade.PreVenda"%>
<%@page import="Entidade.ClienteLogEtiqueta"%>
<%@ page import = "java.text.DateFormat,java.util.ArrayList,java.sql.Timestamp,java.util.Calendar, java.util.GregorianCalendar, java.util.Date, java.text.SimpleDateFormat"%>

<%
    if (session.getAttribute("usuario") == null) {
        response.sendRedirect("../index.jsp?msgLog=3");
    } else {

        int idNivelDoUsuario = (Integer) session.getAttribute("nivel");
        if (idNivelDoUsuario != 1) {
            response.sendRedirect("../jsp/imp_movimento.jsp?msg=Acesso Negado!");
        }

        String nomeBD = (String) session.getAttribute("empresa");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

    <head>
        <title> Portal Postal</title>
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
                                <h4 class="page-header"><b class="text-primary"><i class="fa fa-truck"></i> Coleta</b> > <small>Coletadores</small></h4>
                            </div>
                        </div>
                        <div class="col-lg-12">
                            <div class="row">
                                <form name="form1" action="../../ServInserirColetador" method="post">

                                    <ul class="list-unstyled">
                                        <li class="list-group-item list-group-heading">
                                            <label>Cadastrar novo coletador</label>
                                        </li>
                                        <li class="list-group-item">
                                            <div class="row form-horizontal">
                                                <div class="col-xs-12 col-sm-6 col-md-4 col-lg-4">
                                                    <label class="small">Nome do Coletador</label>
                                                    <div class="input-group">
                                                        <span class="input-group-addon" ><i class="fa fa-user"></i></span>
                                                        <input type="text" name="nome"  class="form-control" placeholder="Nome do Coletador" />
                                                    </div>
                                                </div>
                                                <div class="col-xs-6 col-sm-5 col-md-3 col-lg-3">
                                                    <label class="small">Celular do Coletador</label>
                                                    <div class="input-group">
                                                        <span class="input-group-addon" ><i class="fa fa-phone"></i></span>
                                                        <input type="text" name="telefone" maxlength="14" onKeyPress="mascara(this, maskTelefone)"  class="form-control" placeholder="Telefone" />
                                                    </div>
                                                </div>
                                            </div>
                                        </li>

                                        <li class="list-group-item">
                                                <button type="button" class="btn btn-success" onclick="return preencherCampos();"><i class="fa fa-save fa-lg fa-spc"></i>&nbsp; INSERIR NOVO COLETADOR</button>
                                        </li>
                                    </ul>
                                    <input type="hidden" type="password" name="senha"/>
                                    <input type="hidden" type="password" name="senha2" />
                                    <input type="hidden" name="login" value="login" />
                                    <input type="hidden" name="rota" value= "0" />
                                </form>
                            </div>
                        </div>
                        <!-- /.row -->
                        <div class="row">
                            <div class="col-lg-12">
                                <div class="panel panel-default">
                                    <div class="panel-heading" >Lista com todos os coletadores</div>
                                    <div class="panel-body no-padding">
                                        <div class="dataTable_wrapper">
                                            <table class="table table-striped table-bordered table-hover table-condensed" id="dataTables-example">
                                                <thead>
                                                    <tr>
                                                        <th style="width: 80px;">Nº</th>
                                                        <th>Nome</th>
                                                        <th>Telefone</th>
                                                        <th style="width: 100px;" class="no-sort">Rota</th>
                                                        <th style="width: 100px;" class="no-sort">Editar</th>
                                                        <th style="width: 100px;" class="no-sort">Excluir</th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <%
                                                        ArrayList listaColetador = Coleta.Controle.contrColetador.consultaTodosColetadores(nomeBD);
                                                        for (int j = 0; j < listaColetador.size(); j++) {
                                                            Coleta.Entidade.Coletador col = (Coleta.Entidade.Coletador) listaColetador.get(j);
                                                            String nomeColetador = col.getNome();
                                                            String telefoneColetador = col.getTelefone();
                                                            int rota = col.getRota();
                                                            int idColetador = col.getIdColetador();
                                                    %>
                                                    <tr>
                                                        <td align="right"><%= idColetador%></td>
                                                        <td><%= nomeColetador%></td>
                                                        <td><%= telefoneColetador%></td>
                                                        <td align="center"><a href="coletador_rota_b.jsp?idColetador=<%= idColetador%>" class="btn btn-sm btn-info"><span class="fa fa-road fa-lg"></span></a></td>
                                                        <td align="center"><a onclick="ajaxEditarColetador(<%= idColetador%>);" class="btn btn-sm btn-warning"><span class="fa fa-pencil fa-lg"></span></a></td>
                                                        <td align="center">
                                                            <form action="../../ServExcluirColetador" method="post" name="formDel">
                                                                <input type="hidden" name="idColetador" value="<%= idColetador%>" />
                                                                <button type="button" class="btn btn-sm btn-danger" onClick="confirmExcluir(this);"><span class="fa fa-trash fa-lg"></span></button>
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
                </div>
            </div></div>
        <!-- /#page-wrapper -->

        <!-- /#wrapper -->

        <script type="text/javascript">
            function AllTables() {
                StartDataTable('dataTables-example');
                LoadSelect2Script(MakeSelectDataTable('dataTables-example'));
                fechaMsg();
            }
            $(document).ready(function() {
                LoadDataTablesScripts(AllTables);
            });


            function preencherCampos() {
                var form = document.form1;
                if (form.nome.value == "") {
                    alert('Preencha o Nome do Coletador!');
                    return false;
                }
                if (form.telefone.value == "") {
                    alert('Preencha o Celular do Coletador!');
                    return false;
                }
                form.submit();
            }

            function ajaxEditarColetador(idColetador) {
                $.ajax({
                    method: "POST",
                    url: "coletador_editar_b.jsp",
                    data: {idColetador: idColetador},
                    dataType: 'html'
                }).done(function(retorno) {
                    editarColetador(retorno);
                });
            }            
            
            function editarColetador(retorno) {
                bootbox.dialog({
                    title: "Editar Coletador",
                    message: retorno,
                    animate: true,
                    onEscape: true,
                    buttons: {
                        "Cancelar": {
                            label:"<i class='fa fa-lg fa-times fa-spc'></i>CANCELAR",
                            className: "btn btn-default",
                            callback: function() {
                                bootbox.hideAll();
                            }
                        },
                        success: {
                            label: "<i class='fa fa-lg fa-save fa-spc'></i>SALVAR",
                            className: "btn btn-success",
                            callback: function() {
                                var form = document.form5;
                                if (form.nome.value == '') {
                                    alert('Preencha o Nome do Coletador!');
                                    return false;
                                }
                                if (form.telefone.value == '') {
                                    alert('Preencha o Celular do Coletador!');
                                    return false;
                                }
                                waitMsg();
                                form.submit();
                            }
                        }
                    }
                }
                );
            }
            
            function confirmExcluir(button) {                
                bootbox.confirm({
                    title: 'Excluir Coletador?',
                    message: 'Ao excluir o coletador a rota será perdida!<br/><br/>Deseja realmente excluir este coletador?',
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

        </script>
    </body>
</html>
<%}%>
