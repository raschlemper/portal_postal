
<%@page import="Entidade.LogAtualizacaoContratos"%>
<%@page import="Controle.ContrLogAtualizacaoContrato"%>
<%@page import="Entidade.Clientes"%>
<%@ page import = "java.util.ArrayList,java.sql.Timestamp, java.util.Date, java.text.SimpleDateFormat"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%
    if (session.getAttribute("usuario") == null) {
        response.sendRedirect("../../index.jsp?msgLog=3");
    } else {

        int idNivelDoUsuario = (Integer) session.getAttribute("nivel");
        String nomeBD = (String) session.getAttribute("empresa");
        int idEmpresa = (Integer) session.getAttribute("idEmpresa");
        empresas emp = (empresas) session.getAttribute("emp");
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy HH:mm");

        String msga = (String) session.getAttribute("msg2");
        session.setAttribute("msg2", null);
        LogAtualizacaoContratos log = ContrLogAtualizacaoContrato.consultaUltimoLog(nomeBD);
%>
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />

        <script type="text/javascript" src="../../javascript/ajax.js"></script>
        <script type="text/javascript" src="../../javascript/mascara.js"></script>

        <!-- TableSorter -->
        <link rel="stylesheet" href="../../javascript/plugins/TableSorter/styleSorterV3.css" />
        <script type="text/javascript" src="../../javascript/plugins/TableSorter/scriptSorterV3.js"></script>
        <!-- TableSorter -->

        <link href="../../css/estilo.css" rel="stylesheet" type="text/css" />
        <link href="../../javascript/jquery/css/jquery-ui-1.8.16.custom.css" rel="stylesheet" type="text/css" />

        <link rel="stylesheet" href="../../javascript/plugins/dropdown/css/style.css" type="text/css" media="screen, projection"/>
        <!--[if lte IE 7]>
        <link rel="stylesheet" type="text/css" href="../../javascript/plugins/dropdown/css/ie.css" media="screen" />
        <![endif]-->
        <script type="text/javascript" language="javascript" src="../../javascript/plugins/dropdown/js/jquery.dropdownPlain.js"></script>
        <script type="text/javascript" language="javascript" src="../../javascript/jquery/js/jquery-1.6.2.min.js"></script>
        <script type="text/javascript" language="javascript" src="../../javascript/jquery/js/jquery-ui-1.8.16.custom.min.js"></script>

        <script type="text/javascript">

            function chamaDivProtecao() {
                var classe = document.getElementById("divProtecao").className;
                if (classe == "esconder") {
                    document.getElementById("divProtecao").className = "mostrar";
                    document.getElementById("divInteracao").className = "mostrar";
                } else {
                    document.getElementById("divProtecao").className = "esconder";
                    document.getElementById("divInteracao").className = "esconder";
                }
            }
            function chamaDivProtecao3() {
                var classe = document.getElementById("divProtecao").className;
                if (classe == "esconder") {
                    document.getElementById("divProtecao").className = "mostrar";
                    document.getElementById("divInteracao3").className = "mostrar";
                } else {
                    document.getElementById("divProtecao").className = "esconder";
                    document.getElementById("divInteracao3").className = "esconder";
                }
            }

            function preencherCampos() {
                var form = document.form1;
                if (form.login.value == "") {
                    alert('Insira um login para o usuário!');
                    return false;
                }
                if (form.senha.value == "") {
                    alert('Insira uma senha para o usuário!');
                    return false;
                }
                if (document.getElementById('foo').innerHTML.indexOf('w') == -1) {
                    alert('O login digitado é inválido ou já existente!\n Favor escolha outro!');
                    return false;
                }
                return true;
            }

            function preencherCamposEdit() {
                var form = document.form1;
                if (form.login.value == '') {
                    alert('Insira um login para o usuário!');
                    return false;
                }
                if (form.senha.value != '') {
                    if (form.senha.value != form.senha2.value) {
                        alert('As senhas digitadas não conferem!');
                        return false;
                    }
                }
                if (form.login.value != form.loginaux.value) {
                    if (document.getElementById('fooEditar').innerHTML.indexOf('w') == -1) {
                        alert('O login digitado é inválido ou já existente!\n Favor escolha outro!');
                        return false;
                    }
                }
                return true;
            }

            function verificaComErro() {
                $("#dialog-message").dialog({
                    modal: true,
                    draggable: false,
                    resizable: false,
                    position: ['center', 'center'],
                    show: 'blind',
                    hide: 'blind',
                    width: 540,
                    dialogClass: 'ui-dialog-osx',
                    buttons: {
                        'ATUALIZAR SOMENTE COM ERROS': function() {
                            document.getElementById("comErro").value = 1;
                            $(this).dialog("close");
                            abrirTelaEspera();
                            document.formAtualiza.submit();
                        },
                        'ATUALIZAR TUDO': function() {
                            document.getElementById("comErro").value = 0;
                            $(this).dialog("close");
                            abrirTelaEspera();
                            document.formAtualiza.submit();
                        },                        
                        'X CANCELAR': function() {
                            $(this).dialog("close");
                        }

                    }
                });
            }
        </script>

        <title>Portal Postal | Clientes</title>

    </head>
    <body onload="fecharTelaEspera();<%if (msga != null) {%>chamaDivProtecao();<%}%>">
        <div id="divInteracao" class="esconder" style="top:10%; left:20%; right:20%; bottom:10%;" align="center">
            <div style="width: 80%; margin: 15px; text-align: left;">
                <div style="width: 95%; text-align: left;">
                    <div style='float:right;'><a onclick='chamaDivProtecao();' href='#' class='botaoClose'>Fechar</a></div>
                    <div id='titulo1'>Log Atualização de Contratos ECT</div>
                </div>
                <img style="margin-bottom: 25px;" width="100%" src="../../imagensNew/linha.jpg"/>
                <%= msga%>
            </div>
        </div>
        <div id="divInteracao3" class="esconder" style="top:10%; left:20%; right:20%; bottom:10%;" align="center">
            <div style="width: 80%; margin: 15px; text-align: left;">
                <div style="width: 95%; text-align: left;">
                    <div style='float:right;'><a onclick='chamaDivProtecao3();' href='#' class='botaoClose'>Fechar</a></div>
                    <div id='titulo1'>Log Atualização de Contratos ECT</div>
                </div>
                <img style="margin-bottom: 25px;" width="100%" src="../../imagensNew/linha.jpg"/>
                <% if (log != null) {%><%=log.getMsgFalha()%><%}%>
            </div>
        </div>
        <div id="divProtecao" class="esconder"></div>
        <div id="dialog-message" class="esconder" title="ATENÇÃO !">
            <div style="float: left;" ><img src="../../imagensNew/big_alert.png" /></div>
            <div style="margin-left: 50px;">
                Ocorreram erros inesperados de comunicação com o SIGEP WEB durante a última atualização!
                <br /><br />
                <b>PARA ATUALIZAR SELECIONE A OPÇÃO ABAIXO:</b>
            </div>
        </div>

        <div class="mostrar" id="protecaoTelaEspera">
            <div id="telaEspera">Por Favor, Aguarde...<br/><br/><img src="../../imagensNew/loader.gif" /></div>
        </div>
        <%@ include file="../../Includes/menu_agencia.jsp" %>
        <%@ include file="../../Includes/telaMsg.jsp" %>

        <div id="divPrincipal" align="center">
            <div id="container">
                <div id="conteudo">
                    <div id="titulo1">Lista de Todos os Clientes</div>
                    <table id="barraAtendimento" border="0">
                        <tr>
                            <td align="left" style="font-weight:bold;font-size:12px;">
                                Pesquisa Rápida:
                                <select style='min-width:150px;' id="columns2" onchange="sorter2.search('query2')"></select>
                                <input type="text" id="query2" onkeyup="sorter2.search('query2')" placeholder="Digite aqui a sua pesquisa..." />
                                <a style="text-decoration:none;font-weight:bold;font-size:11px;" href="javascript:document.getElementById('query2').value='';sorter2.reset()">RESTAURAR PADRÕES</a>
                            </td>
                            <td align="right">
                                <div class="details" style="clear:both;">
                                    <div>Resultado <span id="startrecord2"></span>-<span id="endrecord2"></span> de <span id="totalrecords2"></span></div>
                                </div>
                            </td>
                        </tr>
                    </table>
                    <table cellpadding="0" cellspacing="0" border="0" id="table2" class="tinytable">
                        <thead>
                            <tr>
                                <th width="50"><h3>Nº</h3></th>
                                <th><h3>Razão Social</h3></th>
                                <th><h3>Nome Fantasia</h3></th>
                                <th width="160"><h3>Atualizado em</h3></th>
                                <th width="150"><h3>Status Contrato</h3></th>
                                <th width="100"><h3>SigepWEB</h3></th>
                                <th width="125"><h3>Data Vigência</h3></th>
                                <th class="nosort" width="120"><h3>Configurações</h3></th>
                            </tr>
                        </thead>
                        <tbody>
                            <%
                                ArrayList<Clientes> listaCliente = Controle.contrCliente.getNomeCodigoMetodo(nomeBD, false);
                                for (int j = 0; j < listaCliente.size(); j++) {
                                    Clientes col = listaCliente.get(j);
                                    String nomeCliente = col.getNome();
                                    int idCliente = col.getCodigo();
                                    String nomeFantasia = col.getNomeFantasia();
                                    Date dt = col.getDtVigenciaFimContrato();
                                    String dtvg = "- - -";
                                    if (dt != null) {
                                        dtvg = sdf.format(dt);
                                    }
                                    String dtAt = "- - -";
                                    if (col.getDataHoraAtualizacao() != null) {
                                        dtAt = sdf2.format(col.getDataHoraAtualizacao());
                                    }
                                    if (col.getErro_atualizacao() == 1) {
                                        dtAt = "<b style='color:red;font-weight:bold;'>FALHA AO ATUALIZAR</b>";
                                    }

                                    String statusCp = "<a href='cliente_contrato.jsp?idCliente=" + idCliente + "' style='color:silver;font-weight:bold;'>SEM CONTRATO ECT</a>";
                                    if (col.getTemContrato() == 1) {
                                        if (col.getStatusCartaoPostagem() == 1) {
                                            statusCp = "<a href='cliente_contrato.jsp?idCliente=" + idCliente + "' style='color:green;font-weight:bold;'>VÁLIDO</a>";
                                        } else {
                                            statusCp = "<a href='cliente_contrato.jsp?idCliente=" + idCliente + "' style='color:red;font-weight:bold;'>INVÁLIDO/SUSPENSO</a>";
                                        }
                                    }
                                    String sigep = "<b>POSSUI</b>";
                                    if(col.getTemContrato() == 0){
                                        sigep = "";
                                    }else if(col.getLogin_sigep() == null || col.getLogin_sigep().equals("null") || col.getLogin_sigep().equals("")){
                                        sigep = "<b style='color:red;'>NÃO POSSUI</b>";
                                    }
                            %>
                            <tr>
                                <td align="right" style="font-size: 10px;"><%= idCliente%></td>
                                <td style="font-size: 10px;"><%= nomeCliente%></td>
                                <td style="font-size: 10px;"><%= nomeFantasia%></td>
                                <td align="center"><%= dtAt%></td>
                                <td><%= statusCp%></td>
                                <td><%= sigep %></td>
                                <td align="center"><%= dtvg%></td>
                                <td align="center"><a href="cliente_usuarios.jsp?idCliente=<%= idCliente%>" style="cursor:pointer;" ><img class="link_img" src="../../imagensNew/clipboard.png" /> Configurações</a></td>
                            </tr>
                            <%}%>
                        </tbody>
                    </table>
                    <div id="tablefooter" align='center'>
                        <div align="left" style='float:left; width:20%;'>
                            <select onchange="sorter2.size(this.value)">
                                <option value="5">5</option>
                                <option value="10">10</option>
                                <option value="20" selected="selected">20</option>
                                <option value="50">50</option>
                                <option value="100">100</option>
                            </select>
                            <span>Linhas por Página</span>
                        </div>
                        <div id="tablenav2" class="tablenav">
                            <div>
                                <img src="../../javascript/plugins/TableSorter/images/left_end.png" width="20" height="20" alt="First Page" onclick="sorter2.move(-1, true)" />
                                <img src="../../javascript/plugins/TableSorter/images/left.png" width="20" height="20" alt="First Page" onclick="sorter2.move(-1)" />
                                <img src="../../javascript/plugins/TableSorter/images/right.png" width="20" height="20" alt="First Page" onclick="sorter2.move(1)" />
                                <img src="../../javascript/plugins/TableSorter/images/right_end.png" width="20" height="20" alt="Last Page" onclick="sorter2.move(1, true)" />
                                <select style="margin-left:5px;" id="pagedropdown2"></select>
                                <a style="margin-left:10px;" href="javascript:sorter2.showall()">Ver Tudo</a>
                            </div>
                        </div>
                        <div id="tablelocation">
                            <div class="page">Página <span id="currentpage2"></span> de <span id="totalpages2"></span></div>
                        </div>
                    </div>
                    <script type="text/javascript">
                        var sorter2 = new TINY.table.sorter('sorter2', 'table2', {
                            headclass: 'head',
                            ascclass: 'asc',
                            descclass: 'desc',
                            evenclass: 'evenrow',
                            oddclass: 'oddrow',
                            evenselclass: 'evenselected',
                            oddselclass: 'oddselected',
                            paginate: true,
                            size: 20,
                            colddid: 'columns2',
                            currentid: 'currentpage2',
                            totalid: 'totalpages2',
                            startingrecid: 'startrecord2',
                            endingrecid: 'endrecord2',
                            totalrecid: 'totalrecords2',
                            hoverid: 'selectedrowDefault',
                            pageddid: 'pagedropdown2',
                            navid: 'tablenav2',
                            sortcolumn: 0,
                            sortdir: 1,
                            init: true
                        });
                    </script>


                </div>
            </div>
        </div>
    </body>
</html>
<%}%>