<%@page import="Vendedor.Controle.contrVendedores"%>
<%@page import="Coleta.Controle.contrTipoColeta"%>
<%@page import="Coleta.Entidade.*"%>
<%@page import="Coleta.Controle.contrColetaFixa"%>
<%@page import="java.util.Map"%>
<%@page import="Entidade.Clientes"%>
<%@page import="Coleta.Entidade.Coletador"%>
<%@page import="Coleta.Controle.contrColetador"%>
<%@ page import = "java.util.ArrayList,java.sql.Timestamp, java.util.Date, java.text.SimpleDateFormat"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
    Usuario usrSessao = (Usuario) session.getAttribute("agf_usuario");
    if (usrSessao == null) {
        response.sendRedirect("../../index.jsp?msgLog=3");
    } else if (usrSessao.getListaAcessosPortalPostal().contains("408")) {
        response.sendRedirect("../../NewTemplate/Dashboard/index.jsp?msg=Usuario sem permissao!");
    } else {

        String nomeBD = (String) session.getAttribute("empresa");

        int idVendedor = Integer.parseInt(request.getParameter("idVendedor"));
        String nomeVendedor = contrVendedores.consultaNomeVendedoreById(idVendedor, nomeBD);
        ArrayList listaClientes = contrVendedores.consultaClientesVendedor(idVendedor, nomeBD);

        String jsonCliente = "";

%>
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
                                <h4 class="page-header"><b class="text-primary"><i class="fa fa-gears "></i> Cadastro</b> > <a href="vendedor_lista_b.jsp">Vendedores</a> > <small><i class="fa fa-user fa-fw"></i> <%= nomeVendedor%></small></h4>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-lg-12">
                                <form name="form1" action="../../ServInserirClientesVendedor" method="post">
                                    <ul class="list-unstyled">
                                        <li class="list-group-item list-group-heading">
                                            <label>Lista de Clientes de : <%= nomeVendedor%></label>
                                        </li>
                                        <li class="list-group-item">      
                                            <div class="row form-horizontal">
                                                <div class="col-lg-8">
                                                    <select name="idCliente" id="idCliente" onchange="pegaClienteJson(this.value);">
                                                        <option value="sel" >-- Selecione um Cliente --</option>
                                                        <%
                                                            ArrayList<Clientes> listaCliente = Controle.contrCliente.getNomeCodigoMetodo(nomeBD, false);
                                                            for (Clientes c : listaCliente) {
                                                                out.println("<option value='" + c.getCodigo() + "'>[" + c.getCodigo() + "] " + c.getNome().replace("'", "") + "</option>");
                                                                jsonCliente += ",{ \"id\":\"" + c.getCodigo()
                                                                        + "\" , \"nome\":\"" + c.getNome().replace("\"", "").replace(",", "").replace("'", "")
                                                                        + "\" , \"logradouro\":\"" + c.getEndereco().replace("\"", "").replace("'", "").replace("/'", "-")
                                                                        + "\" , \"numero\":\"" + c.getNumero()
                                                                        + "\" , \"complemento\":\"" + c.getComplemento().replace("\"", "").replace("'", "").replace("/'", "-")
                                                                        + "\" , \"bairro\":\"" + c.getBairro().replace("\"", "").replace("'", "")
                                                                        + "\" , \"cidade\":\"" + c.getCidade().replace("\"", "").replace("'", "")
                                                                        + "\" , \"uf\":\"" + c.getUf()
                                                                        + "\" , \"cep\":\"" + c.getCep()
                                                                        + "\" , \"telefone\":\"" + c.getTelefone().replace("\"", "").replace("'", "")
                                                                        + "\" , \"email\":\"" + c.getEmail().replace("\"", "") + "\" }";
                                                            }
                                                            jsonCliente = "{ \"clientes\" : [" + jsonCliente.substring(1) + "]}";

                                                        %>
                                                    </select>
                                                </div>
                                                <div class="col-lg-2">
                                                    <button type="button" disabled class="btn btn-sm btn-success hidden" id="add" onclick="addRow();"><i class="fa fa-plus fa-spc"></i> ADICIONAR NA LISTA</button>
                                                    <input type="hidden" id="nomeCliAux" name="nomeCliAux" />
                                                    <input type="hidden" id="idCliAux" name="idCliAux" />
                                                </div>
                                            </div>
                                        </li>
                                        <li class="list-group-item" style="display: none;" id="liDadosCli">
                                        </li>
                                        <li class="list-group-item no-padding">
                                            <table class="table table-striped table-bordered table-hover table-condensed" id="dataTables-example">
                                                <thead>
                                                    <tr>
                                                        <th>Cliente</th>
                                                        <th width="110">Comissão (%)</th>
                                                        <th class="no-sort" width="60">Remover</th>
                                                    </tr>
                                                </thead>
                                                <tbody id="table">
                                                    <%                                                        for (int i = 0; i < listaClientes.size(); i++) {
                                                            Clientes cf = (Clientes) listaClientes.get(i);
                                                            String nomeCliente = cf.getNome();
                                                            int idCliente = cf.getCodigo();
                                                            if (nomeCliente.equals("")) {
                                                                nomeCliente = "Cliente Cód. " + idCliente + " não encontrado!";
                                                            }
                                                            float percent = cf.getFat_mes();
                                                            /*Entidade.Clientes cli = Controle.contrCliente.consultaClienteById(idCliente, nomeBD);
                                                             String nomeCliente = cli.getNome();*/
                                                            String className = "odd";
                                                            if (i % 2 == 0) {
                                                                className = "even";
                                                            }
                                                    %>
                                                    <tr class="<%= className%>">
                                                        <td>
                                                            <%= nomeCliente%>
                                                            <input class="form-control" type="hidden" id="cliente<%=i%>" value="<%= idCliente%>" name="cliente<%=i%>" />
                                                        </td>

                                                        <td align="center"><input class="numeric form-control" type="text" id="percent<%=i%>" name="percent<%=i%>" value="<%= percent%>" size="5" maxlength="5" /></td>

                                                        <td align="center"><button type="button" class="btn btn-sm btn-danger" id="del" onclick="addDelList('<%= idCliente%>', this);"><i class="fa fa-trash fa-lg"></i></button></td>
                                                    </tr>
                                                    <%}%>
                                                </tbody>
                                            </table>

                                        </li>
                                        <li class="list-group-item list-group-heading">
                                            <input type="hidden" name="idVendedor" id="idColetador" value="<%= idVendedor%>" />
                                            <input type="hidden" name="contador" id="contador" value="<%= listaClientes.size()%>" />
                                            <input type="hidden" name="delList" id="delList" value="" />
                                            <input type="hidden" name="nomeBD" id="nomeBD" value="<%= nomeBD%>" />
                                            <button type="submit" id="save" class="btn btn-success" <%if (listaClientes.size() <= 0) {%> disabled <%}%> onclick="javascript:document.form1.target = '';
                                                    document.form1.action = '../../ServInserirClientesVendedor';
                                                    return validateRow();" ><i class="fa fa-lg fa-spc fa-save"></i> SALVAR LISTA</button>
                                        </li>
                                    </ul>
                                </form>

                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <script language="Javascript" type="text/javascript">
            function AllTables() {
                //StartDataTable('dataTables-example');
                //LoadSelect2Script(MakeSelectDataTable('dataTables-example'));
                fechaMsg();
            }
            function selectCliente() {
                $('#idCliente').select2();
            }
            $(document).ready(function () {
                $(".numeric").numeric({decimal: ".", negative: false, scale: 2});
                LoadSelect2Script(selectCliente);
                LoadDataTablesScripts(AllTables);

            });

            var jsonCli = '<%= jsonCliente%>';
            function pegaClienteJson(idSelecionado) {
                if (idSelecionado === 'sel') {
                    desabilitaBotao();
                    $('#liDadosCli').hide();
                } else {

                    jsonCli = jsonCli.replace(/\\n/g, "\\n")
                            .replace(/\\'/g, "\\'")
                            .replace(/\\"/g, '\\"')
                            .replace(/\\&/g, "\\&")
                            .replace(/\\r/g, "\\r")
                            .replace(/\\t/g, "\\t")
                            .replace(/\\b/g, "\\b")
                            .replace(/\\f/g, "\\f");
// remove non-printable and other non-valid JSON chars
                    jsonCli = jsonCli.replace(/[\u0000-\u0019]+/g, "");
                    console.log(jsonCli.toString());
                    var data = JSON.parse(jsonCli);
                    var cliArray = data.clientes;
                    for (var i = 0; i < cliArray.length; i++) {
                        if (cliArray[i].id === idSelecionado) {
                            document.getElementById('idCliAux').value = cliArray[i].id;
                            document.getElementById('nomeCliAux').value = cliArray[i].nome;
                            $('#liDadosCli').html("<b>" + cliArray[i].nome + "</b><br/>" +
                                    cliArray[i].logradouro + " " + cliArray[i].complemento + "<br/>" +
                                    cliArray[i].bairro + " - " + cliArray[i].cidade + " / " + cliArray[i].uf + " - CEP " + cliArray[i].cep + "<br/>" +
                                    "Fone: " + cliArray[i].telefone);
                            $('#liDadosCli').show();
                            habilitaBotao();
                        }
                    }
                }
            }

            function disableEnterKey(e) {
                var key;
                if (window.event)
                    key = window.event.keyCode;     //IE
                else
                    key = e.which;     //firefox
                if (key === 13)
                    return false;
                else
                    return true;
            }



            function habilitaBotao() {
                if (!($('#add').is(':visible'))) {
                    $('#add').toggleClass('hidden');
                    document.getElementById('add').disabled = false;
                }
            }

            function desabilitaBotao() {
                if ($('#add').is(':visible')) {
                    $('#add').toggleClass('hidden');
                    document.getElementById('add').disabled = true;
                }
            }

            function addRow() {

                desabilitaBotao();

                var linha = document.getElementById('table').insertRow(document.getElementById('table').rows.length);
                var cont = document.getElementById('contador').value;
                var newCont = parseInt(cont) + 1;

                var nomeCli = document.getElementById('nomeCliAux').value;
                var idCli = document.getElementById('idCliAux').value;

                // insere as linhas na tabela
                var coluna0 = linha.insertCell(0);
                coluna0.innerHTML = "<input type='hidden' id='cliente" + cont + "' name='cliente" + cont + "' value='" + idCli + "' /><b>" + nomeCli + "</b>";
                coluna0.align = "left";


                var coluna1 = linha.insertCell(1);
                coluna1.innerHTML = "<input class='numeric form-control' type='text' id='percent" + cont + "' name='percent" + cont + "' value='' size='2' maxlength='5' />";
                coluna1.align = "center";

                var coluna2 = linha.insertCell(2);
                coluna2.innerHTML = "<button class='btn btn-sm btn-danger' type='button' id='del' onclick='delRow(this);'><i class='fa fa-lg fa-trash'></i></button>";
                coluna2.align = "center"

                document.getElementById('contador').value = newCont;

                document.getElementById('idCliAux').value = 0;
                document.getElementById('nomeCliAux').value = "";

                $('#liDadosCli').html('');
                $('#liDadosCli').hide();
                $("#idCliente").select2("val", "sel");
                $('#save').prop("disabled", false);
                document.getElementById('idCliente').focus();

                $(".numeric").numeric({decimal: ".", negative: false, scale: 2});

            }

            function delRow(linha) {
                if (confirm('Tem certeza que deseja excluir?')) {
                    var tabela = document.getElementById('table');
                    linha = linha.parentNode.parentNode;
                    id = linha.rowIndex;
                    tabela.deleteRow(id - 1);
                } else {
                    return false;
                }
            }

            function validateRow() {
                var lastRow = document.getElementById('table').rows.length;
                if (lastRow > 0) {
                    for (var i = 0; i < lastRow; i++) {
                        var campoComissao = document.getElementById('percent' + i);
                        if (campoComissao === null) {
                            alert('Preencha todos os % de comissão!');
                            campoComissao.focus();
                            return false;
                        } else if (campoComissao.value.trim().equals('')) {
                            alert('Preencha todos os % de comissão!');
                            campoComissao.focus();
                            return false;
                        }
                    }
                } else {
                    return false
                }
                return true;
            }

            function addDelList(idColetaFixa, linha) {
                bootbox.confirm({
                    title: 'Excluir Cliente da Lista?',
                    message: 'Deseja realmente excluir este cliente da lista?',
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
                            var tabela = document.getElementById('table');
                            linha = linha.parentNode.parentNode;
                            id = linha.rowIndex;
                            tabela.deleteRow(id - 1);
                            var lista = document.getElementById('delList').value;
                            if (lista == '') {
                                document.getElementById('delList').value += idColetaFixa;
                            } else {
                                document.getElementById('delList').value += ';' + idColetaFixa;
                            }
                        }
                    }
                });
            }
        </script>
    </body>
</html>
<%}%>