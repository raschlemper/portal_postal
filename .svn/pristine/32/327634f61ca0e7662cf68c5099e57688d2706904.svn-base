
<%@page import="Entidade.ClientesDeptos"%>
<%@page import="Controle.ContrClienteDeptos"%>
<%@ page import = "java.util.ArrayList,java.sql.Timestamp, java.util.Date, java.text.SimpleDateFormat"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%
    Usuario usrSessao = (Usuario) session.getAttribute("agf_usuario");
    if (usrSessao == null) {
        response.sendRedirect("../../index.jsp?msgLog=3");
    } else if (usrSessao.getListaAcessosPortalPostal().contains("405")) {
        response.sendRedirect("../../NewTemplate/Dashboard/index.jsp?msg=Usuario sem permissao!");
    } else {

        String nomeBD = (String) session.getAttribute("empresa");
        int idEmpresa = (Integer) session.getAttribute("idEmpresa");

        int idClienteInc = Integer.parseInt(request.getParameter("idCliente"));
        Entidade.Clientes cliInc = Controle.contrCliente.consultaClienteById(idClienteInc, nomeBD);
%>
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

                        <jsp:include page="cliente_menu_b.jsp" >
                            <jsp:param name="nomeBDTab" value="<%= nomeBD%>" />
                            <jsp:param name="activeTab" value="3" />
                            <jsp:param name="idClienteTab" value="<%= idClienteInc%>" />
                            <jsp:param name="temContratoTab" value="<%= cliInc.getTemContrato()%>" />
                            <jsp:param name="nomeClienteTab" value="<%= cliInc.getNomeFantasia()%>" />
                        </jsp:include>

                        <div class="row">
                            <div class="col-md-12">   
                                <form name="form1" action="../../ServCriarLoginPortal" method="post">
                                    <ul class="list-group">
                                        <li class="list-group-item list-group-heading">
                                            <label>Cadastrar Novo Usuário</label>
                                        </li>
                                        <li class="list-group-item">
                                            <div class="row form-horizontal">
                                                <div class="col-sm-6 col-md-3 col-lg-2">
                                                    <label class="small">Login</label>
                                                    <div class="input-group">
                                                        <span class="input-group-addon" ><i class="fa fa-user"></i></span>
                                                        <input class="form-control" type="text" autocomplete="off" placeholder="Login" name="login" onkeyup="ajaxConfereLogin(this.value, 'foo');" />
                                                    </div>   
                                                    <div id="foo"></div>
                                                </div>
                                                <div class="col-sm-6 col-md-3 col-lg-2">
                                                    <label class="small">Senha</label>                                            
                                                    <div class="input-group">
                                                        <span class="input-group-addon" ><i class="fa fa-lock"></i></span>
                                                        <input class="form-control" placeholder="Senha" type="password" name="senha" />
                                                    </div>
                                                </div>
                                                <div class="col-sm-6 col-md-3 col-lg-2">
                                                    <label class="small">Repita a Senha</label>                                                                                   
                                                    <div class="input-group">
                                                        <span class="input-group-addon" ><i class="fa fa-lock"></i></span>
                                                        <input class="form-control" placeholder="Repita a senha" type="password" name="senha2" />
                                                    </div>
                                                </div>
                                                <div class="col-sm-6 col-md-3 col-lg-2">
                                                    <label class="small">Nível</label>                                                   
                                                    <div class="input-group">
                                                        <span class="input-group-addon" ><i class="fa fa-signal"></i></span>
                                                        <select class="form-control" name="nivel" >
                                                            <option value="1">Administrador</option>
                                                            <option value="2">Gerencia</option>
                                                            <option value="3">Usuário</option>
                                                            <option value="99">Web Service</option>
                                                        </select>
                                                    </div>
                                                </div>
                                            </div>
                                        </li>
                                        <li class="list-group-item">
                                            <div class="row form-horizontal">
                                                <div class="col-sm-12 col-md-4 col-lg-4">
                                                    <label class="small">
                                                        PERMISSÕES:<br/>
                                                        <a style="color:blue;font-size: 9px;" onclick="selectAllCombo(document.getElementById('acessos'), true);">MARCAR TUDO</a><br/>
                                                        <a style="color:red;font-size: 9px;" onclick="selectAllCombo(document.getElementById('acessos'), false);">DESMARCAR TUDO</a>
                                                    </label>
                                                    <select class="form-control" name='acessos' id='acessos' multiple='true' onclick="controleCombobox0(this)" size=10 >
                                                        <option value="1" >PESQUISAS / RELATÓRIOS</option>
                                                        <option value="2" >CONTROLE DE A.R.</option>
                                                        <option value="3" >VISUALIZA VALORES / DESPESAS</option>
                                                        <option value="4" >SOLICITAÇÃO DE COLETA</option>
                                                        <option value="5" >GERAR ETIQUETAS</option>
                                                        <option value="6" >VER DADOS DA EMPRESA</option>
                                                        <option value="7" >CADASTRO DE DESTINATÁRIOS</option>
                                                        <option value="8" >VISUALIZAR PRAZOS ESTIMADOS</option>
                                                        <option value="9" >REIMPRIMIR ETIQUETAS ANTIGAS</option>
                                                        <option value="10" >INCLUIR PESOS NA DIGITAÇÃO PRE-POSTAGEM</option>
                                                    </select>
                                                    <script language="">
                                                        function selectAllCombo(combo, flag) {
                                                            for (i = 0; i < combo.length; i++) {
                                                                combo.options[i].selected = flag;
                                                            }
                                                        }
                                                        function controleCombobox0(combo) {
                                                            combo_aux0[combo.selectedIndex] = !combo_aux0[combo.selectedIndex];
                                                            for (i = 0; i < combo.length; i++) {
                                                                combo.options[i].selected = combo_aux0[i];
                                                            }
                                                        }
                                                        var combo_aux0 = new Array(document.getElementById("acessos").options.length);
                                                        for (i = 0; i < document.getElementById("acessos").options.length; i++) {
                                                            combo_aux0[i] = document.getElementById("acessos").options[i].selected;
                                                        }

                                                    </script>
                                                </div>
                                                <div class="col-sm-12 col-md-4 col-lg-4">
                                                    <label class="small">
                                                        DEPARTAMENTOS:<br/>
                                                        <a style="color:blue;font-size: 9px;" onclick="selectAllCombo(document.getElementById('departamentos'), true);">MARCAR TUDO</a><br/>
                                                        <a style="color:red;font-size: 9px;" onclick="selectAllCombo(document.getElementById('departamentos'), false);">DESMARCAR TUDO</a>
                                                    </label>
                                                    <select class="form-control" name='departamentos' id='departamentos' multiple='true' onclick="controleCombobox1(this)" size=10 >
                                                        <%
                                                            ArrayList<ClientesDeptos> listaDep = ContrClienteDeptos.consultaDeptos(idClienteInc, nomeBD);
                                                            for (int i = 0; i < listaDep.size(); i++) {
                                                                ClientesDeptos cd = listaDep.get(i);
                                                        %>
                                                        <option value="<%=cd.getIdDepartamento()%>"><%= cd.getNomeDepartamento()%></option>
                                                        <%}%>
                                                    </select>
                                                    <script language="">
                                                        function controleCombobox1(combo) {
                                                            combo_aux1[combo.selectedIndex] = !combo_aux1[combo.selectedIndex];
                                                            for (i = 0; i < combo.length; i++) {
                                                                combo.options[i].selected = combo_aux1[i];
                                                            }
                                                        }
                                                        var combo_aux1 = new Array(document.getElementById("departamentos").options.length);
                                                        for (i = 0; i < document.getElementById("departamentos").options.length; i++) {
                                                            combo_aux1[i] = document.getElementById("departamentos").options[i].selected;
                                                        }

                                                    </script>
                                                </div>
                                                <div class="col-sm-12 col-md-4 col-lg-4">
                                                    <label class="small">
                                                        SERVIÇOS:<br/>
                                                        <a style="color:blue;font-size: 9px;" onclick="selectAllCombo(document.getElementById('servicos'), true);">MARCAR TUDO</a><br/>
                                                        <a style="color:red;font-size: 9px;" onclick="selectAllCombo(document.getElementById('servicos'), false);">DESMARCAR TUDO</a>
                                                    </label>
                                                    <select class="form-control" name="servicos" id="servicos" multiple='true' onclick="controleCombobox2(this)" size="10" >
                                                        <option value="1" >PAC</option>
                                                        <option value="9" >PAC GRD. FORMATOS</option>
                                                        <option value="2" >SEDEX</option>
                                                        <option value="4" >SEDEX 10</option>
                                                        <option value="7" >SEDEX 12</option>
                                                        <option value="8" >SEDEX HOJE</option>
                                                        <option value="5" >E-SEDEX</option>
                                                        <option value="6" >CARTA REGISTRADA</option>
                                                        <option value="11" >IMPRESSO REGISTRADO</option>
                                                        <option value="3" >SEDEX A COBRAR</option>
                                                        <option value="10" >PAC A COBRAR</option>
                                                        <option value="12" >MDPB REGISTRADO</option>
                                                    </select>
                                                    <script language="">
                                                        function controleCombobox2(combo) {
                                                            combo_aux2[combo.selectedIndex] = !combo_aux2[combo.selectedIndex];
                                                            for (i = 0; i < combo.length; i++) {
                                                                combo.options[i].selected = combo_aux2[i];
                                                            }
                                                        }
                                                        var combo_aux2 = new Array(document.getElementById("servicos").options.length);
                                                        for (i = 0; i < document.getElementById("servicos").options.length; i++) {
                                                            combo_aux2[i] = document.getElementById("servicos").options[i].selected;
                                                        }

                                                    </script>
                                                </div>
                                            </div>
                                        </li>
                                        <li class="list-group-item">
                                            <input type="hidden" name="local" value="1" />
                                            <input type="hidden" name="idCliente" value="<%= idClienteInc%>" />
                                            <button type="button" class="btn btn-success" onclick="return preencherCampos();"><i class="fa fa-lg fa-spc fa-save"></i> SALVAR DADOS</button>                                        
                                        </li>
                                    </ul>
                                </form>

                                <div class="panel panel-default">
                                    <div class="panel-heading"><label>Lista de Todos os Usuários</label></div>
                                    <div class="panel-body">
                                        <div class="dataTable_wrapper no-padding">
                                            <table class="table table-striped table-bordered table-hover table-condensed" id="dataTables-example">
                                                <thead>
                                                    <tr>
                                                        <th>Login</th>
                                                        <th>Senha</th>
                                                        <th>Nivel</th>
                                                        <th class="no-sort" style="width: 60px;">Acessar</th>
                                                        <th class="no-sort" style="width: 60px;">Alterar</th>
                                                        <th class="no-sort" style="width: 60px;">Excluir</th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <%
                                                        ArrayList<Entidade.SenhaCliente> listaLogins = Controle.contrSenhaCliente.consultaTodasSenhaCliente(idClienteInc, nomeBD);
                                                        for (int i = 0; i < listaLogins.size(); i++) {
                                                            Entidade.SenhaCliente sc3 = listaLogins.get(i);
                                                            String loginSc = sc3.getLogin();
                                                            String senhaSc = sc3.getSenha();
                                                            int nivel = sc3.getNivel();
                                                            int id = sc3.getId();
                                                            String nomeNivel = Controle.contrNivel.consultaNomeByIdNivel(nivel, nomeBD);
                                                            if (nivel == 99) {
                                                                nomeNivel = "WEB SERVICE";
                                                            }
                                                            if (nivel == 100) {
                                                                nomeNivel = "OPERADOR MASTER";
                                                                senhaSc = "********";
                                                            }
                                                    %>
                                                    <tr>
                                                        <td><%= loginSc%></td>
                                                        <td><%= senhaSc%></td>
                                                        <td><%= nomeNivel%></td>                                
                                                        <td align="center">    
                                                            <form action="../../ServLoginEmporium" method="post">

                                                                <input type="hidden" name="agenciaHoito" id="inputCodigo" value="<%= idEmpresa%>" />
                                                                <input type="hidden" name="loginHoito" id="inputEmail" value="<%= loginSc%>" />
                                                                <input type="hidden" name="senhaHoito" id="inputPassword" value="<%= senhaSc%>" />                        
                                                                <%if (nivel < 99) {%>
                                                                <button type="submit" class="btn btn-primary" formtarget="_blank"><i class="fa fa-sign-in fa-lg"></i></button>
                                                                    <%} else {%>
                                                                <button type="submit" class="btn btn-primary disabled" formtarget="_blank"><i class="fa fa-sign-in fa-lg"></i></button>
                                                                    <%}%>
                                                            </form> 
                                                            <%--<a class="btn btn-sm btn-info" href="../../ServLoginCliente?agenciaHoito=<%= idEmpresa%>&loginHoito=<%= loginSc%>&senhaHoito=<%= senhaSc%>" target="_blank" style="cursor:pointer;" ><i class="fa fa-sign-in fa-lg"></i></a>--%>                              
                                                        </td>
                                                        <td align="center">
                                                            <%if (nivel < 99) {%>
                                                            <a class="btn btn-sm btn-warning" onclick="ajaxEditarUsuario(<%= id%>, 1);" ><i class="fa fa-pencil fa-lg"></i></a>
                                                                <%} else {%>
                                                            <a class="btn btn-sm btn-default disabled"><i class="fa fa-pencil fa-lg"></i></a>  
                                                                <%}%>
                                                        </td>
                                                        <td align="center">
                                                            <form action="../../ServExcluirLoginPortal" method="post" name="formDel">
                                                                <input type="hidden" name="local" value="1" />
                                                                <input type="hidden" name="idCliente" value="<%= idClienteInc%>" />
                                                                <input type="hidden" name="login" value="<%= loginSc%>" />
                                                                <input type="hidden" name="senha" value="<%= senhaSc%>" />
                                                                <%if (nivel < 99) {%>
                                                                <button type="button" class="btn btn-sm btn-danger" onClick="confirmExcluir(this);" ><i class="fa fa-trash fa-lg"></i></button>
                                                                    <%} else {%>
                                                                <button type="button" class="btn btn-sm btn-danger disabled" onClick="confirmExcluir(this);" ><i class="fa fa-trash fa-lg"></i></button>
                                                                    <%}%>
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
                        <div class="row spacer-xlg"></div>
                    </div>
                </div>
            </div>
        </div>
        <script type="text/javascript">
            function ajaxConfereLogin(login, divRetorno) {
                $.ajax({
                    method: "POST",
                    url: "ajax/confereLoginPortal.jsp",
                    data: {login: login},
                    dataType: 'html'
                }).done(function (data) {
                    document.getElementById(divRetorno).innerHTML = data;
                });
            }

            function confirmExcluir(button) {
                bootbox.confirm({
                    title: 'Excluir Usuário do Cliente?',
                    message: 'Deseja realmente excluir este usuário do cliente?',
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
                    callback: function (result) {
                        if (result) {
                            button.form.submit();
                        }
                    }
                });
            }

            function preencherCampos() {
                var form = document.form1;
                if (form.nivel.value === '') {
                    alert('Preencha o nivel do usuário!');
                    return false;
                }
                if (form.login.value === '') {
                    alert('Insira um login para o usuário!');
                    return false;
                }
                if (document.getElementById('foo').innerHTML.indexOf('w') === -1) {
                    alert('O login digitado é inválido ou já existente!\n Favor escolha outro!');
                    return false;
                }
                if (form.senha.value === '') {
                    alert('Insira uma senha para o usuário!');
                    return false;
                }
                if (form.senha.value !== form.senha2.value) {
                    alert('As senhas digitadas não conferem!');
                    return false;
                }
                waitMsg();
                form.submit();
            }

            function preencherCamposEdit() {
                var form = document.form5;
                if (form.nivel.value === '') {
                    alert('Preencha o nivel do usuário!');
                    return false;
                }
                if (form.login.value === '') {
                    alert('Insira um login para o usuário!');
                    return false;
                }
                if (form.login.value !== form.loginaux.value) {
                    if (document.getElementById('fooEditar').innerHTML.indexOf('w') === -1) {
                        alert('O login digitado é inválido ou já existente!\n\nFavor escolha outro!');
                        return false;
                    }
                }
                if (form.senha.value !== '') {
                    if (form.senha.value !== form.senha2.value) {
                        alert('As senhas digitadas não conferem!');
                        return false;
                    }
                }
                waitMsg();
                form.submit();
            }

            function AllTables() {
                StartDataTable('dataTables-example');
                LoadSelect2Script(MakeSelectDataTable('dataTables-example'));
            }

            $(document).ready(function () {
                LoadDataTablesScripts(AllTables);
            });

            function ajaxEditarUsuario(idUser, localPagina) {
                $.ajax({
                    method: "POST",
                    url: "ajax/cliente_usuarios_editar_dialog.jsp",
                    data: {id: idUser, local: localPagina},
                    dataType: 'html'
                }).done(function (data) {
                    editarUsuario(data);
                });
            }

            function editarUsuario(retorno) {
                bootbox.dialog({
                    title: "Editar Usuário",
                    message: retorno,
                    animate: true,
                    className: "modal-lgWidth",
                    onEscape: true,
                    buttons: {
                        "Cancelar": {
                            label: "<i class='fa fa-lg fa-spc fa-times'></i>CANCELAR",
                            className: "btn btn-default",
                            callback: function () {
                                bootbox.hideAll();
                            }
                        },
                        success: {
                            label: "<i class='fa fa-lg fa-spc fa-save'></i>SALVAR",
                            className: "btn btn-success",
                            callback: function () {
                                preencherCamposEdit();
                            }
                        }
                    }
                });
            }

        </script>
    </body>
</html>
<%}%>