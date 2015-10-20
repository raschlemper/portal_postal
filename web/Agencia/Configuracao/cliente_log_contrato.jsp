<%-- 
    Document   : cliente_log_contrato
    Created on : Apr 16, 2015, 10:14:31 AM
    Author     : Fernando
--%>
<%@page import="Controle.ContrClienteContrato"%>
<%@page import="Controle.contrCliente"%>
<%@page import="Entidade.Clientes"%>
<%@page import="Entidade.LogAtualizacaoContratos"%>
<%@page import="Controle.ContrLogAtualizacaoContrato"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
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
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

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
        <script>
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
                        /*'ATUALIZAR SOMENTE COM ERROS': function() {
                         document.getElementById("comErro").value = 1;
                         $(this).dialog("close");
                         abrirTelaEspera();
                         document.formAtualiza.submit();
                         },*/
                        'VERIFICAR CONTRATOS': function() {
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
        <style>
            a.tip {
                border-bottom: 1px dashed;
                text-decoration: none
            }
            a.tip:hover {
                cursor: help;
                position: relative
            }
            a.tip span {
                display: none
            }
            a.tip:hover span {
                border: #c0c0c0 1px dotted;
                padding: 10px;
                display: block;
                z-index: 100;
                background: url(../images/status-info.png) #f0f0f0 no-repeat 100% 5%;
                left: 0px;
                margin: 10px;
                position: absolute;
                top: 15px;
                white-space: nowrap;
                text-decoration: none;
                font-weight:bold;
            }
        </style>
        <title>Portal Postal | Clientes</title>
    </head>
    <body onload="fecharTelaEspera();">
        <div id="divInteracao" class="esconder" style="top:10%; left:20%; right:20%; bottom:10%;" align="center"></div>
        <div id="divInteracao3" class="esconder" style="top:10%; left:20%; right:20%; bottom:10%;" align="center"></div>
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
                    <form action="../../ServAtualizaContrato" name="formAtualiza" method="post">
                        <ul class="ul_formulario" >
                            <li class="titulo"><dd><span>VERIFICAR DADOS DOS CONTRATOS</span></dd></li>
                            <li>
                            <dd>
                                <div class="buttons">
                                    <button type="button" onclick="verificaComErro();" class="regular"><img src="../../imagensNew/refresh.png"/> VERIFICAR CONTRATOS ECT</button>
                                    <input type="hidden" name="comErro" id="comErro" value="0"/> 
                                </div>
                            </dd>
                            <dd>
                                <br/><br/>
                                <% if (log != null) {%><a style="color: blue;font-weight: bold;cursor: pointer;" onclick="chamaDivProtecao3();">*Ultima Verificação: <%= sdf2.format(log.getDataHora())%></a><%}%>
                            </dd>
                            </li>
                        </ul>
                    </form>
                    <div id="titulo1">Log da Última Verificação dos Contratos ECT</div>  
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
                        <th><h3>Nome Fantasia</h3></th>
                        <th width="270"><h3>Serviços ECT</h3></th>
                        <th width="125"><h3>Vigência</h3></th>
                        <th width="300"><h3>Msg do SigepWEB</h3></th>
                        <th class="nosort" width="120"><h3>Configurações</h3></th>
                        </tr>
                        </thead>
                        <tbody>

                            <% if (log != null) {
                                    String[] mgFalha = log.getMsgFalha().split(";");
                                    String[] cf = log.getCodFalha().split(";");
                                    for (int i = 0; i < mgFalha.length; i++) {
                                        String falha = mgFalha[i];
                                        String[] cFalha = cf[i].split("@");
                                        if (cFalha != null && cf[i].contains("@")) {
                                            String nome = "";
                                            if (cFalha.length == 2) {
                                                nome = cFalha[1];
                                            }
                            %>   
                            <tr>
                                <td><%= cFalha[0]%></td>
                                <td><%= nome%></td>
                                <td>- - -</td>
                                <td>Falha no SigepWEB</td>
                                <td style="color:red;"><%= falha%></td>
                                <td align="center"><a href="cliente_contrato.jsp?idCliente=<%= cFalha[0]%>" style="cursor:pointer;" ><img class="link_img" src="../../imagensNew/clipboard.png" /> Configurações</a></td>
                            </tr>
                            <%}
                                }%>
                            <%
                                String[] serv = log.getMsgSucesso().split(";");
                                String[] cod = log.getCodSucesso().split(";");
                                for (int i = 0; i < cod.length; i++) {
                                    String[] c = cod[i].split("@");

                                    String s = "";
                                    String[] saux = serv[i].split("@");
                                    ArrayList<Integer> listaServ = ContrClienteContrato.consultaContratoCliente(Integer.parseInt(c[0]), nomeBD);
                                    for (String se : saux) {
                                        if(se.contains("-")){
                                            String[] aux = se.split(" - ");
                                            if (listaServ.contains(Integer.parseInt(aux[0]))) {
                                                s += "<span style='color:green;'>" + se + "</span><br/>";
                                            } else {
                                                s += "<a style='color:red;' class='tip'>" + se + " <span>Serviço não cadastrado no contrato do cliente do Portal Postal.</span></a><br/>";
                                            }
                                        }else{
                                            se += se;
                                        }
                                    }

                                    Clientes cli = contrCliente.consultaClienteById(Integer.parseInt(c[0]), nomeBD);

                                    String cnpj = cli.getCnpj().trim();
                                    String cnpjSara = c[2].trim();

                                    String codAdm = cli.getCodAdministrativo().trim();
                                    String codAdmSara = Integer.parseInt(c[3].trim()) + "";

                                    String dtVg = sdf.format(cli.getDtVigenciaFimContrato());
                                    String dataVg = c[5];
                                    //String ano = c[6];
                                    //String uf = c[7];
                                    String msgm = "";
                                    String cor = " style='color:green;'";
                                    if (!dtVg.equals(dataVg)) {
                                        msgm += "<br/>Data de Vigência cadastrada diferente do SARA!<br/>"
                                                + "<b>Data Vigência SARA: </b>" + dataVg
                                                + "<br/><b>Data Vigência Portal Postal: </b>" + dtVg;
                                        cor = " style='color:red;'";
                                    }
                                    if (!codAdm.equals(codAdmSara)) {
                                        msgm += "<br/>Cód. Adm. cadastrado diferente do SARA!<br/>"
                                                + "<b>Cód Adm. SARA: </b>" + codAdmSara
                                                + "<br/><b>Cód. Adm. Portal Postal: </b>" + codAdm;
                                        cor = " style='color:red;'";
                                    }
                                    if (!cnpj.equals(cnpjSara)) {
                                        msgm += "<br/>CNPJ cadastrado diferente do SARA!<br/>"
                                                + "<b>CNPJ SARA: </b>" + cnpjSara
                                                + "<br/><b>CNPJ Portal Postal: </b>" + cnpj;
                                        cor = " style='color:red;'";
                                    }
                                    if (msgm.equals("")) {
                                        msgm = "SigepWEB OK!";
                                    }

                                    String status = "Suspenso/Cancelado";
                                    if (c[4].equals("1")) {
                                        status = "Válido até " + dataVg;
                                    }
                            %>
                            <tr>
                                <td><%= c[0]%></td>
                                <td><%= c[1]%></td>
                                <td><%= s%></td>
                                <td><%= status%></td>
                                <td <%=cor%>><%= msgm%></td>
                                <td align="center"><a href="cliente_contrato.jsp?idCliente=<%= c[0]%>" style="cursor:pointer;" ><img class="link_img" src="../../imagensNew/clipboard.png" /> Configurações</a></td>
                            </tr>
                            <%}
                                }%>

                        </tbody>
                    </table>
                    <div id="tablefooter" align='center'>
                        <div align="left" style='float:left; width:20%;'>
                            <select onchange="sorter2.size(this.value)">
                                <option value="5">5</option>
                                <option value="10">10</option>
                                <option value="20">20</option>
                                <option value="50">50</option>
                                <option value="100">100</option>
                                <option value="500" selected="selected">500</option>
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
                            size: 500,
                            colddid: 'columns2',
                            currentid: 'currentpage2',
                            totalid: 'totalpages2',
                            startingrecid: 'startrecord2',
                            endingrecid: 'endrecord2',
                            totalrecid: 'totalrecords2',
                            hoverid: 'selectedrowDefault',
                            pageddid: 'pagedropdown2',
                            navid: 'tablenav2',
                            sortcolumn: 4,
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
