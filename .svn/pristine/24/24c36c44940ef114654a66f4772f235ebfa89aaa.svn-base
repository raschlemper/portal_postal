
<%@page import="Controle.ContrClienteDeptos"%>
<%@page import="Entidade.ClientesDeptos"%>
<%@page import="Controle.ContrClienteEtiquetas"%>
<%@page import="Entidade.ClienteLogEtiqueta"%>
<%@page import="Entidade.ServicoECT"%>
<%@page import="Controle.ContrServicoECT"%>
<%@page import="Controle.ContrClienteContrato"%>
<%@page import = "java.util.ArrayList,java.sql.Timestamp, java.util.Date, java.text.SimpleDateFormat"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%
    if (session.getAttribute("emp") == null) {
        response.sendRedirect("../../index.jsp?msgLog=3");
    } else {

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        empresas emp = (empresas) session.getAttribute("emp");
        String nomeBD = (String) session.getAttribute("empresa");

        int idClienteInc = Integer.parseInt(request.getParameter("idCliente"));
        Entidade.Clientes cliInc = Controle.contrCliente.consultaClienteById(idClienteInc, nomeBD);
        String cnpjInc = cliInc.getCnpj();
        String cnpjNum = cnpjInc.replace(".", "").replaceAll("-", "").replaceAll("/", "");

        if (cliInc.getTemContrato() == 0) {
            response.sendRedirect("cliente_usuarios.jsp?idCliente=" + idClienteInc + "&msg=Este Cliente nao Possui Contrato ECT");
        } else {
%>
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />

        <script type="text/javascript" src="../../javascript/ajax.js"></script>
        <script type="text/javascript" src="../../javascript/mascara.js"></script>

        <link href="../../css/estilo.css" rel="stylesheet" type="text/css" />
        <script type="text/javascript" src="../../javascript/jquery/js/jquery-1.6.2.min.js"></script>

        <!-- Menu -->
        <link rel="stylesheet" href="../../javascript/plugins/dropdown/css/style.css" type="text/css" media="screen, projection"/>
        <script type="text/javascript" language="javascript" src="../../javascript/plugins/dropdown/js/jquery.dropdownPlain.js"></script>
        <!--[if lte IE 7]>
        <link rel="stylesheet" type="text/css" href="../../javascript/plugins/dropdown/css/ie.css" media="screen" />
        <![endif]-->
        <!-- Menu -->

        <!-- TableSorter -->
        <link rel="stylesheet" href="../../javascript/plugins/TableSorter/styleSorterV3.css" />
        <script type="text/javascript" src="../../javascript/plugins/TableSorter/scriptSorterV3.js"></script>
        <!-- TableSorter -->

        <script type="text/javascript">
            function preencherCampos() {
                var form = document.form1;
                var pi = form.prefixo_inicial.value.toString().length;
                var pf = form.prefixo_final.value.toString().length;
                var fi = form.faixa_inicial.value.toString().length;
                var ff = form.faixa_final.value.toString().length;

                //VERIFICA SE ESCOLHEU SERVIÇO
                if (form.servico.value == '0') {
                    alert('Escolha um serviço para a faixa de etiqueta!');
                    return false;
                }
                //VEROFOCA QTD CARACTERES DOS PREFIXOS
                if (pi != 2) {
                    alert('Preencha Corretamente o Prefixo Inicial');
                    return false;
                }
                if (pf != 2) {
                    alert('Preencha Corretamente o Prefixo Final');
                    return false;
                }
                //VERIFICA SE PREFIXOS ESTAO IGUAIS
                if (form.prefixo_inicial.value != form.prefixo_final.value) {
                    alert('Os Prefixos não estão iguais!\n\nConfira com atenção se estão corretamente preenchidos!');
                    return false;
                }
                //VERIFICA QTD DE CARACTERES
                if (fi != 9) {
                    alert('Preencha Completamente a Faixa Inicial!');
                    return false;
                }
                if (ff != 9) {
                    alert('Preencha Completamente a Faixa Final!');
                    return false;
                }
                //VERIFICA DIGITO VERIFICADOR
                var dig1 = valida_SRO(form.faixa_inicial.value)
                if (dig1 != 'ok') {
                    alert("Erro na Faixa de Etiqueta Inicial!\n\nConfira com atenção se está corretamente preenchida!\nVerifique se o Digito Verificador está correto!\nDigito calculado pelo sistema: " + dig1 + "");
                    return false;
                }
                var dig2 = valida_SRO(form.faixa_final.value);
                if (dig2 != 'ok') {
                    alert("Erro na Faixa de Etiqueta Final!\n\nConfira com atenção se está corretamente preenchida!\nVerifique se o Digito Verificador está correto!\nDigito calculado pelo sistema: " + dig2 + "");
                    return false;
                }
                //VERIFICA SE QTD DE ETIQUESTAS ESTA CORRETO COM A QTD DIGITADA
                var nIni = form.faixa_inicial.value.substring(0, 8);
                var nFim = form.faixa_final.value.substring(0, 8);
                var qtd = parseInt(nFim - nIni + 1);
                if (form.qtd.value != qtd) {
                    alert('Quantidade de Etiquetas não Confere!\n\nQuantidade Calculada pelo Sistema: ' + qtd);
                    return false;
                }

                abrirTelaEspera();
                form.submit();
            }

            function preencherCampos2() {
                var form = document.form2;

                if (form.servico.value == '0') {
                    alert('Escolha um Serviço para a Faixa de Etiqueta!');
                    return false;
                }
                if (form.cnpj.value == '' || form.cnpj.value.length != 14) {
                    alert('Preencha o CNPJ completo do seu Cliente!');
                    return false;
                }
                if (form.login.value == '') {
                    alert('Preencha o Login do Sigep WEB de sua Agência!');
                    return false;
                }
                if (form.senha.value == '') {
                    alert('Preencha a Senha do Sigep WEB de sua Agência!');
                    return false;
                }

                abrirTelaEspera();
                form.submit();
            }

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


            function objDepto(cartao, depto) {
                this.cartao = cartao;
                this.depto = depto;
            }
        </script>

        <title>Portal Postal | Sequências Lógicas</title>

    </head>
    <body>
        <div id="divInteracao" class="esconder" style="top:10%; left:20%; right:20%; bottom:10%;" align="center"><input id="textointeracao" /></div>
        <div id="divProtecao" class="esconder"></div>

        <%@ include file="../../Includes/menu_agencia.jsp" %>
        <%@ include file="../../Includes/telaMsg.jsp" %>

        <div id="divPrincipal" align="center">
            <div id="container">
                <div id="conteudo">

                    <div id="titulo1">Faixas de Etiquetas do Cliente <span><%= cliInc.getNomeFantasia()%></span></div>

                    <jsp:include page="cliente_tab_menu.jsp" >
                        <jsp:param name="nomeBDTab" value="<%= nomeBD%>" />
                        <jsp:param name="activeTab" value="5" />
                        <jsp:param name="idClienteTab" value="<%= idClienteInc%>" />
                        <jsp:param name="temContratoTab" value="<%= cliInc.getTemContrato()%>" />
                    </jsp:include>

                    <form name="form1" action="../../ServInserirFaixaEtiqueta" method="post">
                        <ul class="ul_formulario">
                            <li class="titulo">
                                <dd><span>Inserir Nova Faixa de Etiquetas Manualmente</span></dd>
                            </li>
                            <li>
                                <dd>
                                    <label>Serviço</label>
                                    <select name="servico">
                                        <option value="0">-- ESCOLHA UM SERVIÇO --</option>
                                        <%
                                            ArrayList<Integer> listaContrato = ContrClienteContrato.consultaContratoClienteGroupByServico(idClienteInc, nomeBD);
                                            ArrayList<ServicoECT> listaServ = ContrServicoECT.consultaServicosSigepWEB();
                                            for (int i = 0; i < listaServ.size(); i++) {
                                                ServicoECT sv = listaServ.get(i);
                                                if (listaContrato.contains(sv.getCodECT()) && !sv.getGrupoServico().equals("SIMPLES")) {
                                                    out.print("<option value='0;" + sv.getGrupoServico() + "'>" + sv.getNomeSimples() + "</option>");
                                                }
                                            }
                                        %>
                                    </select>
                                </dd>
                                <dd>
                                    <label>FAIXA INICIAL</label>
                                    <input style="width: 22px; text-transform: uppercase;" type="text" autocomplete="off" name="prefixo_inicial" maxlength="2" onkeypress="return maskLetras(event)" />
                                    <input style="width: 60px;" type="text" autocomplete="off" name="faixa_inicial" maxlength="9" onKeyPress="mascara(this, maskNumero)" />
                                    <input style="width: 22px;" value="BR" readonly="true" type="text" autocomplete="off" name="sufixo_inicial" maxlength="2" />
                                    <div id="foo"></div>
                                </dd>
                                <dd>
                                    <label>FAIXA FINAL</label>
                                    <input style="width: 22px; text-transform: uppercase;" type="text" autocomplete="off" name="prefixo_final" maxlength="2" onkeypress="return maskLetras(event)" />
                                    <input style="width: 60px;" type="text" autocomplete="off" name="faixa_final" maxlength="9" onKeyPress="mascara(this, maskNumero)" />
                                    <input style="width: 22px;" value="BR" readonly="true" type="text" autocomplete="off" name="sufixo_final" maxlength="2" />
                                    <div id="foo2"></div>
                                </dd>
                                <dd>
                                    <label>QUANTIDADE</label>
                                    <input style="width: 80px;" type="text" autocomplete="off" name="qtd" maxlength="5" onKeyPress="mascara(this, maskNumero)" />
                                </dd>
                            </li>
                            <li>
                                <dd>
                                    <div class="buttons">
                                        <input type="hidden" name="idCliente" value="<%= idClienteInc%>" />
                                        <button type="button" class="positive" onclick="return preencherCampos();"><img src="../../imagensNew/tick_circle.png" /> INSERIR ETIQUETAS MANUAL</button>
                                    </div>
                                </dd>
                            </li>
                        </ul>
                    </form>
                    <form name="form2" action="../../ServInserirFaixaEtiquetaWS" method="post">
                        <ul class="ul_formulario">
                            <li class="titulo">
                                <dd><span>Inserir Nova Faixa de Etiquetas via SigepWEB</span></dd>
                            </li>
                            <li>
                                <dd>
                                    <label>Destino da Etiqueta</label>
                                    <select name="uso">
                                        <option selected value="0">PARA PORTAL POSTAL</option>
                                        <option value="1">FORNECIDA PARA CLIENTE</option>
                                    </select>
                                </dd>
                                <dd>
                                    <label>Serviço</label>
                                    <select name="servico">
                                        <option value="0">-- ESCOLHA UM SERVIÇO --</option>
                                        <%
                                            for (int i = 0; i < listaServ.size(); i++) {
                                                ServicoECT sv = listaServ.get(i);
                                                if (listaContrato.contains(sv.getCodECT()) && sv.getIdServicoECT() > 0 && !sv.getGrupoServico().equals("SIMPLES")) {
                                                    out.print("<option value='" + sv.getCodECT() + ";" + sv.getGrupoServico() + ";" + sv.getIdServicoECT() + "'>" + sv.getNomeSimples() + "</option>");
                                                }
                                            }
                                        %>
                                    </select>
                                </dd>
                                <dd>
                                    <label>QUANTIDADE</label>
                                    <select name="qtd" style="width: 80px;">
                                        <option value="50">50</option>
                                        <option value="100">100</option>
                                        <option value="250">250</option>
                                        <option value="500">500</option>
                                        <option value="1000">1000</option>
                                        <option value="5000">5000</option>
                                    </select>
                                </dd>
                                <dd>
                                    <label>CNPJ DO CLIENTE</label>
                                    <input style="width: 120px;" type="text" autocomplete="off" name="cnpj" maxlength="14" value="<%= cnpjNum%>" onKeyPress="mascara(this, maskNumero)" />
                                    <input type="hidden" name="tipoDestinatario" value="C" />
                                    <input style="width: 120px;" type="hidden" autocomplete="off" name="login" readonly value="<%= emp.getLogin_ws_sigep()%>" />
                                    <input style="width: 120px;" type="hidden" autocomplete="off" name="senha" readonly value="<%= emp.getSenha_ws_sigep()%>" />
                                </dd>
                            </li>
                            <li>
                                <dd>
                                    <%if (cliInc.getLogin_sigep() != null && !cliInc.getLogin_sigep().equals("") && !cliInc.getLogin_sigep().toLowerCase().equals("null")) {%>
                                    <div class="buttons">
                                        <input type="hidden" name="idCliente" value="<%= idClienteInc%>" />
                                        <button type="button" class="positive" onclick="return preencherCampos2();"><img src="../../imagensNew/tick_circle.png" /> INSERIR ETIQUETAS SIGEP WEB</button>
                                    </div>
                                    <%} else {%>
                                    <b style="font-size: 14px; color: red;">
                                        Para habilitar esta funcionalidade, solicite o login do SIGEP WEB para o seu Cliente!<br/>
                                        Após recebida a senha, altere o cadastro do Contrato ECT do cliente no Portal Postal!
                                    </b>
                                    <%}%>
                                </dd>
                            </li>
                        </ul>
                    </form>
                    <img width="100%" src="../../imagensNew/linha.jpg"/>

                    <div id="titulo2">Lista de todas as etiquetas do cliente</div>
                    <div style="padding:8px 5px; background: white;">
                        <a href="../../ServRelatorioLogEtq?idCliente=<%= idClienteInc%>" target="_blank"><img class="link_img" src="../../imagensNew/pdf.png" /> RELATÓRIO DE ETIQUETAS CADASTRADAS</a>
                    </div>
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
                                <th><h3>Nº</h3></th>
                                <th><h3>Serviço</h3></th>
                                <th><h3>Faixa Inicial</h3></th>
                                <th><h3>Faixa Final</h3></th>
                                <th><h3>Total</h3></th>
                                <th><h3>Utilizadas</h3></th>
                                <th><h3>Restantes</h3></th>
                                <th><h3>Usuário</h3></th>
                                <th><h3>Data Inserção</h3></th>
                                <th class="nosort" width="65"><h3>Excluir</h3></th>
                            </tr>
                        </thead>
                        <tbody>
                            <%
                                ArrayList<ClienteLogEtiqueta> listaLog = Controle.ContrClienteEtiquetas.consultaLogFaixas(idClienteInc, 60, nomeBD);
                                for (int i = 0; i < listaLog.size(); i++) {
                                    ClienteLogEtiqueta log = listaLog.get(i);
                                    int qtdUt = ContrClienteEtiquetas.contaQtdUtilizadaPorIdLog(log.getIdLog(), 1, nomeBD);
                                    String nomeServ = log.getServico() + "";

                                    double dias = Util.FormatarData.diferencaEmDias(log.getDataHora(), new Date());
                            %>
                            <tr style="cursor:default;">
                                <td><%= log.getIdLog()%></td>
                                <td><%= nomeServ%></td>
                                <td><%= log.getFaixaIni()%></td>
                                <td><%= log.getFaixaFim()%></td>
                                <td><%= log.getQtd()%></td>
                                <td><%= qtdUt%></td>
                                <td><%= log.getQtd() - qtdUt%></td>
                                <td><%= log.getNomeUsuario()%></td>
                                <td <%if (dias > 90) {%>style="color: red;"<%}%>><%= sdf.format(log.getDataHora())%></td>
                                <td align="center">
                                    <%if (qtdUt == 0) {%>
                                    <form action="../../ServExcluirFaixaEtiqueta" method="post" name="formDel">
                                        <input type="hidden" name="idLog" value="<%= log.getIdLog()%>" />
                                        <input type="hidden" name="idCliente" value="<%= idClienteInc%>" />
                                        <input type="image" src="../../imagensNew/cancel.png" onClick="javascript:if (confirm('Tem certeza que deseja excluir a faixa?')) {
                                                    return true;
                                                } else {
                                                    return false;
                                                }" />
                                    </form>
                                    <%} else if ((log.getQtd() - qtdUt) > 0) {%>
                                    <form action="../../ServSuspenderFaixaEtiqueta" method="post" name="formDel">
                                        <input type="hidden" name="idLog" value="<%= log.getIdLog()%>" />
                                        <input type="hidden" name="idCliente" value="<%= idClienteInc%>" />
                                        <input type="image" src="../../imagensNew/pause.png" onClick="javascript:if (confirm('Tem certeza que deseja suspender a faixa')) {
                                                    return true;
                                                } else {
                                                    return false;
                                                }" />
                                    </form>
                                    <%}%>
                                </td>
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
                            sortdir: -1,
                            init: true
                        });
                    </script>


                </div>
            </div>
        </div>
    </body>
</html>
<%}
    }%>