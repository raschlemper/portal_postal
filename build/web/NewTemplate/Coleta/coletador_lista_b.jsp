
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
    } else if (usrSessao.getListaAcessosPortalPostal().contains("203")) {
        response.sendRedirect("../../NewTemplate/Dashboard/index.jsp?msg=Usuario sem permissao!");
    } else {

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
                                <h4 class="page-header"><b class="text-primary"><i class="fa fa-truck"></i> Coleta</b> > <small>Cadastro de Rotas</small></h4>
                            </div>
                        </div>
                        <div class="col-lg-12">
                            <div class="row">
                                <form name="form1" action="../../ServInserirColetador" method="post">

                                    <ul class="list-unstyled">
                                        <li class="list-group-item list-group-heading">
                                            <label>Cadastrar Rota</label>
                                        </li>
                                        <li class="list-group-item">
                                            <div class="row form-horizontal">
                                                <div class="col-xs-12 col-sm-6 col-md-4 col-lg-4">
                                                    <label class="small">Nome da Rota</label>
                                                    <div class="input-group">
                                                        <span class="input-group-addon" ><i class="fa fa-user"></i></span>
                                                        <input type="text" name="Nome"  class="form-control" placeholder="Nome" />
                                                    </div>
                                                </div>
                                                <div class="col-xs-6 col-sm-5 col-md-3 col-lg-3">
                                                    <label class="small">Telefone de Contato</label>
                                                    <div class="input-group">
                                                        <span class="input-group-addon" ><i class="fa fa-phone"></i></span>
                                                        <input type="text" name="telefone" onKeyPress="mascara(this, maskTelefone)"  class="form-control" placeholder="Telefone" />
                                                    </div>
                                                </div>
                                            </div>
                                        </li>

                                        <li class="list-group-item">
                                                <button type="button" class="btn btn-success" onclick="return preencherCampos();"><i class="fa fa-save fa-lg fa-spc"></i>&nbsp; INSERIR NOVA ROTA</button>
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
                                    <div class="panel-heading" >Lista com todas as rotas</div>
                                    <div class="panel-body no-padding">
                                        <div class="dataTable_wrapper">
                                            <table class="table table-striped table-bordered table-hover table-condensed" id="dataTables-example">
                                                <thead>
                                                    <tr>
                                                        <th style="width: 80px;">Nº</th>
                                                        <th>Nome</th>
                                                        <th>Celular</th>
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
                    title: "Editar Rota",
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
                    title: 'Excluir Rota?',
                    message: 'Deseja realmente excluir esta rota?',
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
