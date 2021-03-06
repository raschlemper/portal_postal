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
    } else if (usrSessao.getListaAcessosPortalPostal().contains("203")) {
        response.sendRedirect("../../NewTemplate/Dashboard/index.jsp?msg=Usuario sem permissao!");
    } else {

        String nomeBD = (String) session.getAttribute("empresa");

        int idColetador = Integer.parseInt(request.getParameter("idColetador"));
        String nomeColetador = contrColetador.consultaNomeColetadoresById(idColetador, nomeBD);
        ArrayList listaColetaFixa = contrColetaFixa.consultaColetasFixasPeloColetador(idColetador, nomeBD);
        int numColetaFixa = listaColetaFixa.size();

        Map<Integer, ArrayList<Coleta>> lsC = contrColetaFixa.verificaExistenciaRotaParaCliente(nomeBD);

        ArrayList listaTipo = contrTipoColeta.consultaTodosTipoColeta(nomeBD);
        String optTipo = "";
        for (int i = 0; i < listaTipo.size(); i++) {
            TipoColeta tip = (TipoColeta) listaTipo.get(i);
            String nomeTipo = tip.getTipo();
            int idTipo = tip.getIdTipoColeta();
            optTipo += "<option value='" + idTipo + "'>" + nomeTipo + "</option>";
        }

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
                                <h4 class="page-header"><b class="text-primary"><i class="fa fa-truck"></i> Coleta</b> > <a href="coletador_lista_b.jsp">Cadastro de Rotas</a> > <small><i class="fa fa-road fa-fw"></i> Rota <i class="fa fa-long-arrow-right fa-fw"></i> <%= nomeColetador%></small></h4>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-lg-12">

                                <%if (listaTipo.size() <= 0) {%>
                                <div class="alert alert-danger">
                                    <i class="fa fa-warning fa-5x pull-left"></i>
                                    <b style="font-size: 18px;">ATEN��O !!!</b><br/>
                                    <b style="font-size: 14px;">Para inserir uma rota para este coletador, cadastre primeiramente os tipos de coleta! </b><br/>
                                    <a href="tipo_coleta_lista_b.jsp" >&raquo; Clique aqui para cadastrar um novo tipo de coleta.</a>
                                </div>
                                <%} else {%>


                                <form name="form1" action="../../ServInserirColetaFixa" method="post">
                                    <ul class="list-unstyled">
                                        <li class="list-group-item list-group-heading">
                                            <label>Montar Rota Fixa de <%= nomeColetador%></label>
                                        </li>
                                        <li class="list-group-item">      
                                            <div class="row form-horizontal">
                                                <div class="col-lg-8">
                                                    <select name="idCliente" id="idCliente" onchange="pegaClienteJson(this.value);">
                                                        <option value="sel" >-- Selecione um Cliente --</option>
                                                        <%
                                                            ArrayList<Clientes> listaCliente = Controle.contrCliente.getNomeCodigoMetodo(nomeBD, false);
                                                            for (Clientes c : listaCliente) {
                                                                out.println("<option value='" + c.getCodigo() + "'>[" + c.getCodigo() + "] " + c.getNome() + "</option>");
                                                                jsonCliente += ",{ \"id\":\"" + c.getCodigo()
                                                                        + "\" , \"nome\":\"" + c.getNome().replace("\"", "").replace("'", "")
                                                                        + "\" , \"logradouro\":\"" + c.getEndereco().replace("\"", "").replace("'", "")
                                                                        + "\" , \"numero\":\"" + c.getNumero()
                                                                        + "\" , \"complemento\":\"" + c.getComplemento().replace("\"", "").replace("'", "")
                                                                        + "\" , \"bairro\":\"" + c.getBairro().replace("\"", "").replace("'", "")
                                                                        + "\" , \"cidade\":\"" + c.getCidade().replace("\"", "").replace("'", "")
                                                                        + "\" , \"uf\":\"" + c.getUf()
                                                                        + "\" , \"cep\":\"" + c.getCep()
                                                                        + "\" , \"telefone\":\"" + c.getTelefone().replace("\"", "")
                                                                        + "\" , \"email\":\"" + c.getEmail().replace("\"", "") + "\" }";
                                                            }
                                                            jsonCliente = "{ \"clientes\" : [" + jsonCliente.substring(1) + "]}";
                                                        %>
                                                    </select>
                                                </div>
                                                <div class="col-lg-2">
                                                    <button type="button" disabled class="btn btn-sm btn-success hidden" id="add" onclick="addRow();"><i class="fa fa-plus fa-spc"></i> ADICIONAR NA ROTA</button>
                                                    <%--<input style="width:450px;" type="text" id="nomeCliente" name="nomeCliente" onclick="javascript:this.value = '';" />--%>
                                                    <input type="hidden" id="nomeCliAux" name="nomeCliAux" />
                                                    <input type="hidden" id="idCliAux" name="idCliAux" />
                                                </div>
                                                <div class="col-lg-2"><button type="button" disabled class="btn btn-sm btn-info hidden" id="verMapa" onclick="abrirPopMapa();" ><i class="fa fa-map-marker fa-spc"></i> VER NO MAPA</button></div>
                                            </div>
                                        </li>
                                        <li class="list-group-item" style="display: none;" id="liDadosCli">
                                            <%--<label>Endere�o:</label><div style="font-size: 11px;" id="cli_endereco"><b style="color:red;">Escolha um Cliente!</b></div>
                                            <label>Bairro:</label><div style="font-size: 11px;" id="cli_bairro"></div>
                                            <label>Cidade/UF:</label><div style="font-size: 11px;" id="cli_cidade"></div>
                                            <label>CEP:</label><div style="font-size: 11px;" id="cli_cep"></div>--%>
                                        </li>
                                        <li class="list-group-item no-padding">
                                            <table class="table table-striped table-bordered table-hover table-condensed" id="dataTables-example">
                                                <thead>
                                                    <tr>
                                                        <th class="no-sort">CK</th>
                                                        <th>Cliente</th>
                                                        <th>Outra Rota</th>
                                                        <th class="no-sort" width="150">Tipo da Coleta</th>
                                                        <th width="90">Horario</th>
                                                        <th class="no-sort" width="130">Coleta Fixa?</th>
                                                        <th class="no-sort" width="6" style="padding: 2px; text-align: center; vertical-align: middle;">S</th>
                                                        <th class="no-sort" width="6" style="padding: 2px; text-align: center; vertical-align: middle;">T</th>
                                                        <th class="no-sort" width="6" style="padding: 2px; text-align: center; vertical-align: middle;">Q</th>
                                                        <th class="no-sort" width="6" style="padding: 2px; text-align: center; vertical-align: middle;">Q</th>
                                                        <th class="no-sort" width="6" style="padding: 2px; text-align: center; vertical-align: middle;">S</th>
                                                        <th class="no-sort" width="6" style="padding: 2px; text-align: center; vertical-align: middle;">S</th>
                                                        <th class="no-sort" width="6">Remover</th>
                                                    </tr>
                                                </thead>
                                                <tbody id="table">
                                                    <%
                                                        for (int i = 0; i < numColetaFixa; i++) {
                                                            ColetaFixa cf = (ColetaFixa) listaColetaFixa.get(i);
                                                            int idColetaFixa = cf.getIdColetaFixa();
                                                            int idCliente = cf.getIdCliente();
                                                            int idTipoColeta = cf.getIdTipo();
                                                            int fixo = cf.getFixo();
                                                            String horaColeta = cf.getHora();
                                                            String dias = cf.getDias();
                                                            String nomeCliente = Controle.contrCliente.consultaNomeById(idCliente, nomeBD);
                                                            if (nomeCliente.equals("")) {
                                                                nomeCliente = "Cliente C�d. " + idCliente + " n�o encontrado!";
                                                            }
                                                            /*Entidade.Clientes cli = Controle.contrCliente.consultaClienteById(idCliente, nomeBD);
                                                             String nomeCliente = cli.getNome();*/
                                                            String className = "odd";
                                                            if (i % 2 == 0) {
                                                                className = "even";
                                                            }
                                                    %>
                                                    <tr class="<%= className%>">
                                                        <td align="center"><input type="checkbox" name="ids" value="<%=idColetaFixa%>" /></td>
                                                        <td>
                                                            <%= nomeCliente%>
                                                            <input class="form-control" type="hidden" id="cliente<%=i%>" value="<%= idCliente%>" name="cliente<%=i%>" />
                                                            <input class="form-control" type="hidden" id="idRota<%=i%>" value="<%= idColetaFixa%>" name="idRota<%=i%>" />
                                                        </td>
                                                        <td align="center">
                                                            <%
                                                                if (lsC.containsKey(idCliente)) {
                                                                    String rt = "";
                                                                    ArrayList<Coleta> colts = lsC.get(idCliente);
                                                                    for (Coleta cl : colts) {
                                                                        if (!cl.getNomeColetador().trim().equals(nomeColetador.trim())) {
                                                                            String fx = "eventual";
                                                                            if (cl.getIdTipo() == 1) {
                                                                                fx = "fixo";
                                                                            }
                                                                            rt += "| " + cl.getNomeColetador() + " - " + fx + " ";

                                                                        }
                                                                    }
                                                                    if (rt.contains("|")) {
                                                                        rt = rt.substring(1);
                                                                    }
                                                            %>
                                                            <%=rt%>
                                                            <%}%>
                                                        </td>
                                                        <td align="center">
                                                            <select class="form-control" id="select<%=i%>" name="select<%=i%>">
                                                                <%
                                                                    for (int j = 0; j < listaTipo.size(); j++) {
                                                                        TipoColeta tip = (TipoColeta) listaTipo.get(j);
                                                                        String nomeTipo = tip.getTipo();
                                                                        int idTipo = tip.getIdTipoColeta();
                                                                %>
                                                                <option value="<%= idTipo%>" <%if (idTipo == idTipoColeta) {%> selected <%}%> ><%= nomeTipo%></option>
                                                                <%}%>
                                                            </select>
                                                        </td>
                                                        <td align="center"><input class="form-control" type="text" id="hora<%=i%>" name="hora<%=i%>" value="<%= horaColeta%>" size="5" maxlength="5" onkeypress="mascara(this, maskHora)" onblur="valida_hora(this);" /></td>
                                                        <td align="center">
                                                            <select class="form-control" id="fixo<%=i%>" name="fixo<%=i%>" onchange="ajustaCk(<%=i%>, this.value)">
                                                                <option value="0" <%if (fixo == 0) {%> selected <%}%> >Eventual</option>
                                                                <option value="1" <%if (fixo == 1) {%> selected <%}%> >Fixa</option>
                                                            </select>
                                                        </td>
                                                                <td style="padding: 2px;"><input type="checkbox" name="seg<%=i%>" id="seg<%=i%>" value="2" onclick='handleClick(<%=idColetaFixa%>, <%=i%>);' <%if (fixo == 0) {%> checked disabled <%} else if(dias.contains("2")){%> checked <%}%>/></td>
                                                                <td style="padding: 2px;"><input type="checkbox" name="ter<%=i%>" id="ter<%=i%>" value="3" onclick='handleClick(<%=idColetaFixa%>, <%=i%>);' <%if (fixo == 0) {%>checked disabled <%} else if(dias.contains("3")){%> checked <%}%>/></td>
                                                                <td style="padding: 2px;"><input type="checkbox" name="qua<%=i%>" id="qua<%=i%>" value="4" onclick='handleClick(<%=idColetaFixa%>, <%=i%>);' <%if (fixo == 0) {%>checked disabled <%} else if(dias.contains("4")){%> checked <%}%>/></td>
                                                                <td style="padding: 2px;"><input type="checkbox" name="qui<%=i%>" id="qui<%=i%>" value="5" onclick='handleClick(<%=idColetaFixa%>, <%=i%>);' <%if (fixo == 0) {%>checked disabled <%} else if(dias.contains("5")){%> checked <%}%>/></td>
                                                                <td style="padding: 2px;"><input type="checkbox" name="sex<%=i%>" id="sex<%=i%>" value="6" onclick='handleClick(<%=idColetaFixa%>, <%=i%>);' <%if (fixo == 0) {%>checked disabled <%} else if(dias.contains("6")){%> checked <%}%>/></td>
                                                                <td style="padding: 2px;"><input type="checkbox" name="sab<%=i%>" id="sab<%=i%>" value="7" onclick='handleClick(<%=idColetaFixa%>, <%=i%>);' <%if (fixo == 0) {%>checked disabled <%} else if(dias.contains("7")){%> checked <%}%>/></td>
                                                        <td align="center"><button type="button" class="btn btn-sm btn-danger" id="del" onclick="addDelList('<%= idColetaFixa%>', this);"><i class="fa fa-trash fa-lg"></i></button></td>
                                                    </tr>
                                                    <%}%>
                                                </tbody>
                                            </table>

                                        </li>
                                        <li class="list-group-item list-group-heading">
                                            <input type="hidden" name="idColetador" id="idColetador" value="<%= idColetador%>" />
                                            <input type="hidden" name="contador" id="contador" value="<%= numColetaFixa%>" />
                                            <input type="hidden" name="delList" id="delList" value="" />
                                            <input type="hidden" name="nomeBD" id="nomeBD" value="<%= nomeBD%>" />
                                            <button type="submit" class="btn btn-success" <%if (listaTipo.size() <= 0) {%> disabled <%}%> onclick="javascript:document.form1.target = '';
                                                    document.form1.action = '../../ServInserirColetaFixa';
                                                    return validateRow();" ><i class="fa fa-lg fa-spc fa-save"></i> SALVAR ROTA</button>
                                            <button type="submit" class="btn btn-info" <%if (listaTipo.size() <= 0) {%> disabled <%}%> onclick="javascript:document.form1.target = '_blank';
                                                    document.form1.action = 'coletador_rota_mapa.jsp';" ><i class="fa fa-lg fa-spc fa-map-marker"></i> GERAR MAPA DA ROTA</button>
                                        </li>
                                    </ul>
                                    <ul class="list-unstyled">
                                        <li class="list-group-item list-group-heading">
                                            <label>ESCOLHA UMA NOVA ROTA PARA TRANSFERIR O CLIENTE SELECIONADO</label>
                                            <select class="form-control" name="idColetador2" style="width:300px;">
                                                <option value="0">-- SELECIONE UMA ROTA --</option>
                                                <%
                                                    ArrayList listaColetadores = contrColetador.consultaTodosColetadores(nomeBD);
                                                    for (int i = 0; i < listaColetadores.size(); i++) {
                                                        Coletador coletador = (Coletador) listaColetadores.get(i);
                                                        String nomeCol = coletador.getNome();
                                                        int idCol = coletador.getIdColetador();
                                                %>
                                                <option value="<%= idCol%>"><%= nomeCol%></option>
                                                <%}%>
                                            </select><br/>
                                            <button type="submit" class="btn btn-warning" <%if (listaTipo.size() <= 0) {%> disabled <%}%> onclick="javascript:document.form1.target = '';
                                                    document.form1.action = '../../ServAlterarBoyColetaFixa';
                                                    return validateRow();" ><i class="fa fa-lg fa-spc fa-exchange"></i> TRANSFERIR</button>
                                        </li>
                                    </ul>
                                </form>
                                <%}%>

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
                LoadSelect2Script(selectCliente);
                LoadDataTablesScripts(AllTables);
            });

            var jsonCli = '<%= jsonCliente%>';
            function pegaClienteJson(idSelecionado) {
                if (idSelecionado === 'sel') {
                    desabilitaBotao();
                    $('#liDadosCli').hide();
                } else {
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
                if (key == 13)
                    return false;
                else
                    return true;
            }

            function abrirPopMapa() {
                var idCliente = document.getElementById('idCliente').value;
                bootbox.dialog({
                    title: "Mapa do Cliente",
                    message: '<iframe style="width:100%; height:520px;border:0px;" src="ajax/coletador_rota_mapa_cliente.jsp?idCliente=' + idCliente + '"></iframe>',
                    animate: true,
                    className: "modal-lgWidth",
                    onEscape: true,
                    buttons: {
                        success: {
                            label: "<i class='fa fa-lg fa-times fa-spc'></i>&nbsp;&nbsp;FECHAR",
                            className: "btn btn-danger",
                            callback: function () {
                                bootbox.hideAll();
                            }
                        }
                    }
                });
            }

            function habilitaBotao() {

                $('#add').toggleClass('hidden');
                $('#verMapa').toggleClass('hidden');
                document.getElementById('add').disabled = false;
                document.getElementById('verMapa').disabled = false;
            }

            function desabilitaBotao() {
                $('#add').toggleClass('hidden');
                $('#verMapa').toggleClass('hidden');
                document.getElementById('add').disabled = true;
                document.getElementById('verMapa').disabled = true;
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
                coluna0.innerHTML = "---";
                coluna0.align = "center";

                linha.insertCell(1).innerHTML = "<input type='hidden' id='cliente" + cont + "' name='cliente" + cont + "' value='" + idCli + "' /><b>" + nomeCli + "</b>";

                var coluna2 = linha.insertCell(2);
                coluna2.innerHTML = "";
                coluna2.align = "center";

                var coluna3 = linha.insertCell(3);
                coluna3.innerHTML = "<select class='form-control' id='select" + cont + "' name='select" + cont + "'> <%= optTipo%> </select>";
                coluna3.align = "center";
                var coluna4 = linha.insertCell(4);
                coluna4.innerHTML = "<input class='form-control' type='text' id='hora" + cont + "' name='hora" + cont + "' value='' size='2' maxlength='5' onKeyPress='mascara(this,maskHora)' onblur='valida_hora(this);' />";
                coluna4.align = "center";
                var coluna5 = linha.insertCell(5);
                coluna5.innerHTML = "<select class='form-control' id='fixo" + cont + "' name='fixo" + cont + "' onchange='ajustaCk("+cont+", this.value)'>"
                        + "<option value='1' selected >Fixa</option>"
                        + "<option value='0'  >Eventual</option>"
                        + "</select>";
                coluna5.align = "center"

                var coluna6 = linha.insertCell(6);
                coluna6.innerHTML = "<td><input type='checkbox' value='2' name='seg"+cont+"' id='seg"+cont+"' checked/></td>";
                coluna6.setAttribute('style','padding : 2px;');
                var coluna7 = linha.insertCell(7);
                coluna7.innerHTML = "<td><input type='checkbox' value='3' name='ter"+cont+"' id='ter"+cont+"' checked/></td>";
                coluna7.setAttribute('style','padding : 2px;');
                var coluna8 = linha.insertCell(8);
                coluna8.innerHTML = "<td><input type='checkbox' value='4' name='qua"+cont+"' id='qua"+cont+"' checked/></td>";
                coluna8.setAttribute('style','padding : 2px;');
                var coluna9 = linha.insertCell(9);
                coluna9.innerHTML = "<td><input type='checkbox' value='5' name='qui"+cont+"' id='qui"+cont+"' checked/></td>";
                coluna9.setAttribute('style','padding : 2px;');
                var coluna10 = linha.insertCell(10);
                coluna10.innerHTML = "<td><input type='checkbox' value='6' name='sex"+cont+"' id='sex"+cont+"' checked/></td>";
                coluna10.setAttribute('style','padding : 2px;');
                var coluna11 = linha.insertCell(11);
                coluna11.innerHTML = "<td><input type='checkbox' value='7' name='sab"+cont+"' id='sab"+cont+"' checked/></td>";
                coluna11.setAttribute('style','padding : 2px;');
                var coluna12 = linha.insertCell(12);
                coluna12.innerHTML = "<button class='btn btn-sm btn-danger' type='button' id='del' onclick='delRow(this);'><i class='fa fa-lg fa-trash'></i></button>";
                coluna12.align = "center"

                document.getElementById('contador').value = newCont;
                document.getElementById('idCliAux').value = 0;
                document.getElementById('nomeCliAux').value = "";

                $('#liDadosCli').html('');
                $('#liDadosCli').hide();
                $("#idCliente").select2("val", "sel");
                document.getElementById('idCliente').focus();

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
                        var campoHora = document.getElementById('hora' + i);
                        if (campoHora != null && campoHora.value.length < 5) {
                            alert('Preencha todos os Horarios das Coletas!');
                            campoHora.focus();
                            return false;
                        }
                    }
                } else {
                    return false
                }
                return true;
            }

            function ajustaCk(ck, fx) {
                console.log(ck + ' -- ' + fx);
                if (fx === '1') {
                    console.log('hablitar');
                    $('#seg' + ck).removeAttr("disabled");
                    $('#ter' + ck).removeAttr("disabled");
                    $('#qua' + ck).removeAttr("disabled");
                    $('#qui' + ck).removeAttr("disabled");
                    $('#sex' + ck).removeAttr("disabled");
                    $('#sab' + ck).removeAttr("disabled");
                } else {
                    console.log('desablitar');
                    $('#seg' + ck).prop('checked', true);
                    $('#ter' + ck).prop('checked', true);
                    $('#qua' + ck).prop('checked', true);
                    $('#qui' + ck).prop('checked', true);
                    $('#sex' + ck).prop('checked', true);
                    $('#sab' + ck).prop('checked', true);
                    $('#seg' + ck).attr("disabled", true);
                    $('#ter' + ck).attr("disabled", true);
                    $('#qua' + ck).attr("disabled", true);
                    $('#qui' + ck).attr("disabled", true);
                    $('#sex' + ck).attr("disabled", true);
                    $('#sab' + ck).attr("disabled", true);
                }
            }
            function handleClick(id, ck) {
                var tx ='';
                    if($('#seg' + ck).prop('checked')){
                        tx += ';2';
                    };
                    if($('#ter' + ck).prop('checked')){
                        tx += ';3';
                    };
                    if($('#qua' + ck).prop('checked')){
                        tx += ';4';
                    };
                    if($('#qui' + ck).prop('checked')){
                        tx += ';5';
                    };
                    if($('#sex' + ck).prop('checked')){
                        tx += ';6';
                    };
                    if($('#sab' + ck).prop('checked')){
                        tx += ';7';
                    };
                console.log('id > '+id+' -- '+tx.substr(1));
                //fazer ajax
            }

            function addDelList(idColetaFixa, linha) {
                bootbox.confirm({
                    title: 'Excluir Cliente da Rota?',
                    message: 'Deseja realmente excluir este cliente da rota?',
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