<%@ page import = "java.util.ArrayList"%>
<%
      Usuario usrSessao = (Usuario) session.getAttribute("agf_usuario");
    if (usrSessao == null) {
        response.sendRedirect("../../index.jsp?msgLog=3");
    } else if (usrSessao.getListaAcessosPortalPostal().contains("204")) {
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
                                <h4 class="page-header"><b class="text-primary"><i class="fa fa-truck"></i> Coleta</b> > <small>Tipos de Coleta</small></h4>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-lg-12">
                                <form name="form1" action="../../ServInserirTipoColeta" method="post">
                                    <ul class="list-unstyled">
                                        <li class="list-group-item list-group-heading">
                                            <label>Inserir novo tipo de coleta</label>
                                        </li>
                                        <li class="list-group-item">
                                            <div class="row form-horizontal">
                                                <div class="col-xs-12 col-sm-6 col-md-4 col-lg-4">
                                                    <label class="small">Tipo da Coleta</label>
                                                    <div class="input-group">
                                                        <span class="input-group-addon" ><i class="fa fa-truck"></i></span>
                                                        <input type="text" name="tipo"  class="form-control" placeholder="Tipo de Coleta" />
                                                    </div>
                                                </div>
                                            </div>
                                        </li>
                                        <li class="list-group-item">
                                            <button type="button" class="btn btn-success" onclick="return preencherCampos();"><i class="fa fa-lg fa-spc fa-save"></i> INSERIR NOVO TIPO DE COLETA</button>
                                        </li>
                                    </ul>
                                </form>
                                <div class="panel panel-default">
                                    <div class="panel-heading" >Lista com todos os tipos de coleta</div>
                                    <div class="panel-body no-padding">
                                        <div class="dataTable_wrapper">
                                            <table class="table table-striped table-bordered table-hover table-condensed" id="dataTables-example">

                                                <thead>
                                                    <tr>
                                                        <th width="80">N�</th>
                                                        <th>Tipo da Coleta</th>
                                                        <th class="no-sort" width="100">Alterar</th>
                                                        <th class="no-sort" width="100">Excluir</th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <%
                                                        ArrayList listaTipo = Coleta.Controle.contrTipoColeta.consultaTodosTipoColeta(nomeBD);
                                                        for (int j = 0; j < listaTipo.size(); j++) {
                                                            Coleta.Entidade.TipoColeta tipoCol = (Coleta.Entidade.TipoColeta) listaTipo.get(j);
                                                            String tipo = tipoCol.getTipo();
                                                            int idTipo = tipoCol.getIdTipoColeta();
                                                    %>
                                                    <tr>
                                                        <td align="right"><%= idTipo%></td>
                                                        <td><%= tipo%></td>
                                                        <td align="center"><a class="btn btn-sm btn-warning" onclick="ajaxEditarTipoColeta(<%= idTipo%>);" ><i class="fa fa-lg fa-pencil"></i></a></td>
                                                        <td align="center">
                                                            <form action="../../ServExcluirTipoColeta" method="post" name="formDel">
                                                                <input type="hidden" name="idTipoColeta" value="<%= idTipo%>" />
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
                    </div>
                </div>
            </div>
        </div>
        <script type="text/javascript">
            function confirmExcluir(button) {                
                bootbox.confirm({
                    title: 'Excluir Tipo de Coleta?',
                    message: 'Deseja realmente excluir este tipo de coleta?',
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
        
            function preencherCampos() {
                var form = document.form1;
                if (form.tipo.value === '') {
                    alert('Preencha o Tipo da Coleta!');
                    return false;
                }
                form.submit();
            }
            function preencherCamposEdit() {
                var form = document.form5;
                if (form.tipo.value === '') {
                    alert('Preencha o Tipo da Coleta!');
                    return false;
                }
                form.submit();
            }
            
            function ajaxEditarTipoColeta(idTipoColeta) {
                $.ajax({
                    method: "POST",
                    url: "ajax/tipo_coleta_editar.jsp",
                    data: {idTipoColeta: idTipoColeta},
                    dataType: 'html'
                }).done(function(retorno) {
                    editarTipoColeta(retorno);
                });
            }
            
            function editarTipoColeta(retorno) {
                bootbox.dialog({
                    title: "Editar Tipo de Coleta",
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
                                preencherCamposEdit();
                            }
                        }
                    }
                });
                return false;
            }
            
            
            /************************************/
            
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