
<%@page import="Controle.ContrClienteEtiquetas"%>
<%@page import="Controle.contrCliente"%>
<%@page import="Emporium.Controle.ContrPreVenda"%>
<%@page import="Entidade.PreVenda"%>
<%@page import="Entidade.ClienteLogEtiqueta"%>
<%@ page import = "java.text.DateFormat,java.util.ArrayList,java.sql.Timestamp,java.util.Calendar, java.util.GregorianCalendar, java.util.Date, java.text.SimpleDateFormat"%>

<%
    if (session.getAttribute("usuario") == null) {
        response.sendRedirect("../index.jsp?msgLog=3");
    } else {

        String nomeBD = (String) session.getAttribute("empresa");        
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        Date dataAtual = new Date();
        String vDataAtual = sdf.format(dataAtual);
        String dataAnterior = Util.SomaData.SomarDiasDatas(dataAtual, -30);
        if(request.getParameter("dataFim")!=null){            
            vDataAtual = request.getParameter("dataFim");
        }
        if(request.getParameter("dataIni")!=null){         
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

        
        <script>
            
            $(function() {
                $("#dataIni").datepicker({
                    maxDate:'<%= vDataAtual%>',
                    showOn: "button",
                    buttonImage: "../../imagensNew/calendario.png",
                    buttonImageOnly: true,
                    showAnim: "slideDown"
                });
                $("#dataFim").datepicker({
                    maxDate:'<%= vDataAtual%>',
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
            <div id="container"><!--style="border:1px solid #008ED6;border-top: none;box-sizing:border-box;-moz-box-sizing:border-box;"-->
                <div id="conteudo">


                    <div id="titulo1">Sequências Lógicas Geradas no Portal Postal</div>
                    <form action="painel_etiquetas_geradas.jsp" method="post">
                        <ul class="ul_formulario" >
                            <li class="titulo"><dd><span>SELECIONE O PERIODO</span></dd></li>
                            <li>
                                <dd>
                                    <label>Periodo de Data</label>
                                    <input type="text" style="width:60px;" name="dataIni" id="dataIni" value="<%= dataAnterior %>" maxlength="10" onkeypress="mascara(this,maskData);" />
                                    até
                                    <input type="text" style="width:60px;" name="dataFim" id="dataFim" value="<%=vDataAtual %>" maxlength="10" onkeypress="mascara(this,maskData);" />
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

                    <div style="width: 100%;">
                        <div id="titulo2">Resultado da Pesquisa</div>
                        
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
                                <th><h3>Cliente</h3></th>
                                <th><h3>Serviço</h3></th>
                                <th><h3>Faixa Inicial</h3></th>
                                <th><h3>Faixa Final</h3></th>
                                <th><h3>Total</h3></th>
                                <th><h3>Utilizadas</h3></th>
                                <th><h3>Restantes</h3></th>
                                <th><h3>Usuário</h3></th>
                                <th><h3>Data Inserção</h3></th>
                            </tr>
                        </thead>
                        <tbody>
                            <%
                                SimpleDateFormat sdf3 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                                ArrayList<ClienteLogEtiqueta> listaLog = Controle.ContrClienteEtiquetas.consultaLogFaixas(vDataInicio, vDataFinal, nomeBD);
                                for (int i = 0; i < listaLog.size(); i++) {
                                    ClienteLogEtiqueta log = listaLog.get(i);
                                    int qtdUt = ContrClienteEtiquetas.contaQtdUtilizadaPorIdLog(log.getIdLog(), 1, nomeBD);
                                    String nomeServ = log.getServico() + "";
                                    String cli = log.getNomeServico();
                            %>
                            <tr style="cursor:default;">
                                <td><%= log.getIdLog()%></td>
                                <td><%= cli%></td>
                                <td><%= nomeServ%></td>
                                <td><%= log.getFaixaIni()%></td>
                                <td><%= log.getFaixaFim()%></td>
                                <td><%= log.getQtd()%></td>
                                <td><%= qtdUt%></td>
                                <td><%= log.getQtd() - qtdUt%></td>
                                <td><%= log.getNomeUsuario()%></td>
                                <td><%= sdf3.format(log.getDataHora())%></td>
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
                                <img src="../../javascript/plugins/TableSorter/images/left_end.png" width="20" height="20" alt="First Page" onclick="sorter2.move(-1,true)" />
                                <img src="../../javascript/plugins/TableSorter/images/left.png" width="20" height="20" alt="First Page" onclick="sorter2.move(-1)" />
                                <img src="../../javascript/plugins/TableSorter/images/right.png" width="20" height="20" alt="First Page" onclick="sorter2.move(1)" />
                                <img src="../../javascript/plugins/TableSorter/images/right_end.png" width="20" height="20" alt="Last Page" onclick="sorter2.move(1,true)" />
                                <select style="margin-left:5px;" id="pagedropdown2"></select>
                                <a style="margin-left:10px;" href="javascript:sorter2.showall()">Ver Tudo</a>
                            </div>
                        </div>
                        <div id="tablelocation">
                            <div class="page">Página <span id="currentpage2"></span> de <span id="totalpages2"></span></div>
                        </div>
                    </div>
                    <script type="text/javascript">
                        var sorter2 = new TINY.table.sorter('sorter2','table2',{
                            headclass:'head',
                            ascclass:'asc',
                            descclass:'desc',
                            evenclass:'evenrow',
                            oddclass:'oddrow',
                            evenselclass:'evenselected',
                            oddselclass:'oddselected',
                            paginate:true,
                            size:20,
                            colddid:'columns2',
                            currentid:'currentpage2',
                            totalid:'totalpages2',
                            startingrecid:'startrecord2',
                            endingrecid:'endrecord2',
                            totalrecid:'totalrecords2',
                            hoverid:'selectedrowDefault',
                            pageddid:'pagedropdown2',
                            navid:'tablenav2',
                            sortcolumn:0,
                            sortdir:-1,
                            init:true
                        });
                    </script>

                        <form action="../../ServPreVendaInutilizar" method="post" name="formDel">
                            <input type="hidden" name="nomeBD" value="<%= nomeBD%>" />
                            <input type="hidden" name="idVenda" id="idVendaDel" value="" />
                            <input type="hidden" name="numObjeto" id="numObjetoDel" value="" />
                        </form>
                    </div>

                </div>
            </div>
        </div>
    </body>
</html>
<%}%>