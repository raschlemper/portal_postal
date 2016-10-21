
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
        if (idNivelDoUsuario > 2) {
            response.sendRedirect("../jsp/imp_movimento.jsp?msg=Acesso Negado!");
        }

        SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        int idUsuario = (Integer) session.getAttribute("idUsuario");
        String nomeBD = (String) session.getAttribute("empresa");
        int num = Controle.contrCliente.numeroClientes(nomeBD);

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        Date dataAtual = new Date();
        String vDataAtual = Util.SomaData.SomarDiasDatas(dataAtual, -1);
        String dataAnterior = Util.SomaData.SomarDiasDatas(dataAtual, -6);
        if (request.getParameter("dataFim") != null) {
            vDataAtual = request.getParameter("dataFim");
        }
        if (request.getParameter("dataIni") != null) {
            dataAnterior = request.getParameter("dataIni");
        }

        String vDataInicio = Util.FormatarData.DateToBD(dataAnterior);
        String vDataFinal = Util.FormatarData.DateToBD(vDataAtual);

%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />

        <script type="text/javascript" src="../../Cliente/js/ajax_portal.js"></script>
        <script type="text/javascript" src="../../javascript/ajax.js"></script>
        <script type="text/javascript" src="../../javascript/mascara.js"></script>


        <link type="text/css" href="../../javascript/jquery/css/jquery-ui-1.8.16.custom.css" rel="stylesheet" />
        <script type="text/javascript" src="../../javascript/jquery/js/jquery-1.6.2.min.js"></script>
        <script type="text/javascript" src="../../javascript/jquery/js/jquery-ui-1.8.16.custom.min.js"></script>
        <script type="text/javascript" src="../../javascript/jquery/js/jquery.ui.datepicker-pt-BR.js"></script>

        <!-- TableSorter -->
        <link rel="stylesheet" href="../../javascript/plugins/TableSorter/styleSorterV3.css" />
        <script type="text/javascript" src="../../javascript/plugins/TableSorter/scriptSorterV3.js"></script>
        <!-- TableSorter -->

        <link href="../../css/estilo.css" rel="stylesheet" type="text/css" />

        <link rel="stylesheet" href="../../javascript/plugins/dropdown/css/style.css" type="text/css" media="screen, projection"/>
        <!--[if lte IE 7]>
        <link rel="stylesheet" type="text/css" href="../../javascript/plugins/dropdown/css/ie.css" media="screen" />
        <![endif]-->
        <script type="text/javascript" language="javascript" src="../../javascript/plugins/dropdown/js/jquery.dropdownPlain.js"></script>

        <script type="text/javascript" charset="utf-8">
            function validaForm() {
                var form = document.form1;
                if (form.arquivo.value == "") {
                    alert("Escolha o arquivo de clientes a ser importado!\nGeralmente encontrado em 'C:/clientes.txt'.");
                    return false;
                } else {
                    var indexA = form.arquivo.value.lastIndexOf(".");
                    var indexB = form.arquivo.value.length;
                    var ext = form.arquivo.value.substring(indexA, indexB).toUpperCase();
                    if (ext != ".TXT") {
                        alert("O arquivo a ser importado deve ser '.TXT' !");
                        return false;
                    }
                }

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

            $(function () {
                $("#dataIni").datepicker({
                    maxDate: '<%= vDataAtual%>',
                    showOn: "button",
                    buttonImage: "../../imagensNew/calendario.png",
                    buttonImageOnly: true,
                    showAnim: "slideDown"
                });
                $("#dataFim").datepicker({
                    maxDate: '<%= vDataAtual%>',
                    showOn: "button",
                    buttonImage: "../../imagensNew/calendario.png",
                    buttonImageOnly: true,
                    showAnim: "slideDown"
                });
            });
        </script>
        <title>Portal Postal | Importação dos Clientes</title>
    </head>
    <body onload="alertaQtd();">
        <div id="divInteracao" class="esconder" align="center"><input id="textointeracao" /></div>
        <div id="divProtecao" class="esconder"></div>

        <%@ include file="../../Includes/menu_agencia.jsp" %>
        <%@ include file="../../Includes/telaMsg.jsp" %>

        <div id="divPrincipal" align="center">
            <div id="container">
                <div id="conteudo">


                    <div id="titulo1">Etiquetas Inutilizadas</div>

                    <form action="painel_etiquetas_inut.jsp" method="post">
                        <ul class="ul_formulario" >
                            <li class="titulo"><dd><span>SELECIONE O PERIODO</span></dd></li>
                            <li>
                                <dd>
                                    <label>Periodo de Data</label>
                                    <input type="text" style="width:60px;" name="dataIni" id="dataIni" value="<%= dataAnterior%>" maxlength="10" onkeypress="mascara(this, maskData);" />
                                    até
                                    <input type="text" style="width:60px;" name="dataFim" id="dataFim" value="<%=vDataAtual%>" maxlength="10" onkeypress="mascara(this, maskData);" />
                                </dd>
                            </li>
                            <li>
                                <dd>
                                    <div class="buttons">
                                        <button type="submit" class="regular"><img src="../../imagensNew/lupa.png"/> PESQUISAR</button>
                                    </div>
                                </dd>
                            </li>
                        </ul>
                    </form>
                    <div style="width: 100%; padding: 10px; background: white;">
                        <div id="titulo2">Lista de Etiquetas que estão INUTILIZADAS</div>
                        <table id="barraAtendimento" border="0">
                            <tr>
                                <td align="left" style="font-weight:bold;font-size:12px;">
                                    Pesquisa Rápida:
                                    <select style='min-width:150px;' id="columns3" onchange="sorter3.search('query3')"></select>
                                    <input type="text" id="query3" onkeyup="sorter3.search('query3')" placeholder="Digite aqui a sua pesquisa..." />
                                    <a style="text-decoration:none;font-weight:bold;font-size:11px;" href="javascript:document.getElementById('query3').value='';sorter3.reset()">RESTAURAR PADRÕES</a>
                                </td>
                                <td align="right">
                                    <div class="details" style="clear:both;">
                                        <div>Resultado <span id="startrecord3"></span>-<span id="endrecord3"></span> de <span id="totalrecords3"></span></div>
                                    </div>
                                </td>
                            </tr>
                        </table>
                        <table cellpadding="0" cellspacing="0" border="0" id="table3" class="tinytable">
                            <thead>
                                <tr>
                                    <%--<th class="nosort" width="40"><h3>Marcar</h3></th>--%>
                                    <th><h3>Nº do Objeto</h3></th>
                                    <th><h3>Serviço</h3></th>
                                    <th><h3>Cliente</h3></th>
                                    <th><h3>Destinatário</h3></th>
                                    <th><h3>CEP</h3></th>
                                    <th><h3>Gerada em</h3></th>
                                    <th><h3>Impressa em</h3></th>
                                    <th width="40"><h3>AR</h3></th>
                                    <th class="nosort" width="40"><h3>VER</h3></th>
                                </tr>
                            </thead>
                            <tbody>
                                <%
                                    ArrayList<PreVenda> lista2 = ContrPreVenda.consultaVendasNaoConsolidadas(nomeBD, vDataInicio, vDataFinal, 1);
                                    for (int i = 0; i < lista2.size(); i++) {
                                        PreVenda des = lista2.get(i);
                                        String numObj = des.getNumObjeto();
                                        if ("avista".equals(numObj)) {
                                            numObj = "- - -";
                                        }
                                        String ar = "SIM";
                                        if (des.getAviso_recebimento() == 0) {
                                            ar = "NÃO";
                                        }
                                        String dtVenda = " - - - ";
                                        if (des.getDataPreVenda() != null) {
                                            dtVenda = sdf2.format(des.getDataPreVenda());
                                        }
                                        String dtImpresso = " - - - ";
                                        if (des.getDataImpresso() != null) {
                                            dtImpresso = sdf2.format(des.getDataImpresso());
                                        }
                                        String nomeCli = contrCliente.consultaNomeById(des.getIdCliente(), nomeBD);
                                %>
                                <tr style="cursor:default;">
                                    <%--<td align="center"><input type="checkbox" name="ids" value="<%= des.getId()%>" /></td>--%>
                                    <td align="center">  
                                         <a href='#' onclick="pesqSro('<%= numObj %>');"><%= numObj%></a>                                     
                                    </td>
                                    <td><%= des.getNomeServico()%></td>
                                    <td><%= nomeCli%></td>
                                    <td><%= des.getNomeDes()%></td>
                                    <td><%= des.getCepDes()%></td>
                                    <td><%= dtVenda%></td>
                                    <td><%= dtImpresso%></td>
                                    <td><%= ar%></td>
                                    <td align="center"><a onclick="verVenda2(<%= des.getId()%>, <%= des.getIdCliente()%>);" style="cursor:pointer;" ><img src="../../imagensNew/lupa.png" /></a></td>
                                </tr>
                                <%}%>
                            </tbody>
                        </table>
                        <div id="tablefooter" align='center'>
                            <div align="left" style='float:left; width:20%;'>
                                <select onchange="sorter3.size(this.value)">
                                    <option value="5">5</option>
                                    <option value="10" selected="selected">10</option>
                                    <option value="20">20</option>
                                    <option value="50">50</option>
                                    <option value="100">100</option>
                                </select>
                                <span>Linhas por Página</span>
                            </div>
                            <div id="tablenav3" class="tablenav">
                                <div>
                                    <img src="../../javascript/plugins/TableSorter/images/left_end.png" width="20" height="20" alt="First Page" onclick="sorter3.move(-1, true)" />
                                    <img src="../../javascript/plugins/TableSorter/images/left.png" width="20" height="20" alt="First Page" onclick="sorter3.move(-1)" />
                                    <img src="../../javascript/plugins/TableSorter/images/right.png" width="20" height="20" alt="First Page" onclick="sorter3.move(1)" />
                                    <img src="../../javascript/plugins/TableSorter/images/right_end.png" width="20" height="20" alt="Last Page" onclick="sorter3.move(1, true)" />
                                    <select style="margin-left:5px;" id="pagedropdown3"></select>
                                    <a style="margin-left:10px;" href="javascript:sorter3.showall()">Ver Tudo</a>
                                </div>
                            </div>
                            <div id="tablelocation">
                                <div class="page">Página <span id="currentpage3"></span> de <span id="totalpages3"></span></div>
                            </div>
                        </div>
                        <form name="frmSRO" id="frmSRO" method="post" action="http://www2.correios.com.br/sistemas/rastreamento/Resultado.cfm" target="_blank">
                            <input type="hidden" name="objetos" id="objetos" value="" />
                        </form>
                        <script type="text/javascript">
                            var sorter3 = new TINY.table.sorter('sorter3', 'table3', {
                                headclass: 'head',
                                ascclass: 'asc',
                                descclass: 'desc',
                                evenclass: 'evenrow',
                                oddclass: 'oddrow',
                                evenselclass: 'evenselected',
                                oddselclass: 'oddselected',
                                paginate: true,
                                size: 10,
                                colddid: 'columns3',
                                currentid: 'currentpage3',
                                totalid: 'totalpages3',
                                startingrecid: 'startrecord3',
                                endingrecid: 'endrecord3',
                                totalrecid: 'totalrecords3',
                                hoverid: 'selectedrowPointer',
                                pageddid: 'pagedropdown3',
                                navid: 'tablenav3',
                                sortcolumn: 6,
                                sortdir: 1,
                                init: true
                            });
                            function pesqSro(param) {
                                $('#objetos').val(param);
                                $('#frmSRO').submit();
                            }
                        </script>
                    </div>

                </div>
            </div>
        </div>
    </body>
</html>
<%}%>