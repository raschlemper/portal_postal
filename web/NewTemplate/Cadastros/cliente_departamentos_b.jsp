
<%@page import="java.util.ArrayList"%>
<%@page import="Entidade.ClientesDeptos"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%
    if (session.getAttribute("usuario") == null) {
        response.sendRedirect("../index.jsp?msgLog=3");
    } else {

        int idNivelDoUsuario = (Integer) session.getAttribute("nivel");
        if (idNivelDoUsuario == 3) {
            response.sendRedirect("../Importacao/imp_movimento.jsp?msg=Acesso Negado!");
        }

        String nomeBD = (String) session.getAttribute("empresa");
        int idClienteInc = Integer.parseInt(request.getParameter("idCliente"));
        Entidade.Clientes cliInc = Controle.contrCliente.consultaClienteById(idClienteInc, nomeBD);
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

                        <jsp:include page="cliente_menu_b.jsp" >
                            <jsp:param name="nomeBDTab" value="<%= nomeBD%>" />
                            <jsp:param name="activeTab" value="1" />
                            <jsp:param name="idClienteTab" value="<%= idClienteInc%>" />
                            <jsp:param name="temContratoTab" value="<%= cliInc.getTemContrato()%>" />
                            <jsp:param name="nomeClienteTab" value="<%= cliInc.getNomeFantasia()%>" />
                        </jsp:include>    

                        <div class="row">
                            <div class="col-xs-12">

                                <form name="form1" action="../../ServInserirDepto" method="post">
                                    <ul class="list-unstyled">
                                        <li class="list-group-item list-group-heading">
                                            <label>Inserir Novo Departamento</label>
                                        </li>
                                        <li class="list-group-item">
                                            <div class="row form-horizontal">
                                                <div class="col-xs-12 col-sm-8 col-md-6 col-lg-4">
                                                    <label class="small">Nome do Departamento</label>
                                                    <div class="input-group">
                                                        <span class="input-group-addon" ><i class="fa fa-sitemap"></i></span>
                                                        <input type="text" name="nome" maxlength="40" class="form-control" placeholder="Nome do Departamento" />
                                                    </div>
                                                </div>
                                                <div class="col-xs-12 col-sm-8 col-md-6 col-lg-4">
                                                    <label class="small">Cartão de Postagem <span style="color:red;font-size: 10px;">*Se não possuir deixe em branco</span></label>
                                                    <div class="input-group">
                                                        <span class="input-group-addon" ><i class="fa fa-asterisk"></i></span>
                                                        <input type="text" name="cartao" maxlength="10" class="form-control" onkeypress="mascara(this, maskNumero)" placeholder="Cartão de Postagem" />
                                                    </div>
                                                </div>
                                            </div>
                                        </li>
                                        <li class="list-group-item">
                                            <input type="hidden" name="idCliente" value="<%= cliInc.getCodigo()%>" />
                                            <button type="button" class="btn btn-success" onclick="return preencherCampos();"><i class="fa fa-lg fa-spc fa-save"></i> INSERIR NOVO DEPARTAMENTO</button>
                                        </li>
                                    </ul>
                                </form>

                                <div class="panel panel-default">
                                    <div class="panel-heading"><label>Lista de Todos os Departamentos</label></div>
                                    <div class="panel-body">
                                        <div class="dataTable_wrapper no-padding">
                                            <table class="table table-striped table-bordered table-hover table-condensed" id="dataTables-example">
                                                <thead>
                                                    <tr>
                                                        <th>Código</th>
                                                        <th>Departamento</th>
                                                        <th>Cartão de Postagem</th>
                                                        <th class="no-sort" width="150">Alterar</th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <%
                                                        ArrayList<ClientesDeptos> listaLogins = Controle.ContrClienteDeptos.consultaDeptos(idClienteInc, nomeBD);
                                                        for (int i = 0; i < listaLogins.size(); i++) {
                                                            ClientesDeptos sc3 = listaLogins.get(i);
                                                            String cartao = sc3.getCartaoPostagem();
                                                            String cart = cartao;
                                                            if (cartao == null) {
                                                                cartao = "";
                                                                cart = " - - - ";
                                                            }
                                                    %>
                                                    <tr>
                                                        <td><%= sc3.getIdDepartamento()%></td>
                                                        <td><%= sc3.getNomeDepartamento()%></td>
                                                        <td><%= cart%></td>
                                                        <td align="center"><button type="button" class="btn btn-sm btn-warning" onclick="ajaxCartaoPostagem(<%= idClienteInc%>, <%= sc3.getIdDepartamento()%>, '<%= cartao%>', '<%= sc3.getNomeDepartamento()%>');" ><i class="fa fa-lg fa-pencil"></i></button></td>
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

            function preencherCampos() {
                var form = document.form1;
                if (form.nome.value === '') {
                    alert('Preencha o nome do grupo!');
                    return false;
                }
                form.submit();
            }

            function preencherCamposEdit() {
                var form = document.form5;
                if (form.nome.value === '') {
                    alert('Preencha o nome do grupo!');
                    return false;
                }
                form.submit();
            }

            function ajaxCartaoPostagem(idCliente, idDepartamento, cartao, depto) {
                $.ajax({
                    method: "POST",
                    url: "ajax/cliente_cartao_dep_editar_dialog.jsp",
                    data: {
                        idCliente: idCliente, 
                        idDepartamento: idDepartamento, 
                        cartao: cartao, 
                        depto: depto
                    },
                    dataType: 'html'
                }).done(function (retorno) {
                    editarCartaoPostagemDialog(retorno);
                });
            }
                        
            function editarCartaoPostagemDialog(retorno) {
                bootbox.dialog({
                    title: "Editar Cartão de Postagem do Departamento",
                    message: retorno,
                    animate: true,
                    onEscape: true,
                    buttons: {
                        "Cancelar": {
                            label: "<i class='fa fa-lg fa-times fa-spc'></i>CANCELAR",
                            className: "btn btn-default",
                            callback: function () {
                                bootbox.hideAll();
                            }
                        },
                        success: {
                            label: "<i class='fa fa-lg fa-save fa-spc'></i>SALVAR",
                            className: "btn btn-success",
                            callback: function () {
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

            $(document).ready(function () {
                LoadDataTablesScripts(AllTables);
            });

        </script>
    </body>
</html>
<%}%>