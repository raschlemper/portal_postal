
<%@page import="Controle.contrSRO"%>
<%@page import="Entidade.Movimentacao"%>
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


%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />

        <script type="text/javascript" src="../Cliente/js/ajax_portal.js"></script>
        <script type="text/javascript" src="../javascript/ajax.js"></script>
        <script type="text/javascript" src="../javascript/mascara.js"></script>        

        <link type="text/css" href="../javascript/jquery/css/jquery-ui-1.8.16.custom.css" rel="stylesheet" />
        <script type="text/javascript" src="../javascript/jquery/js/jquery-1.6.2.min.js"></script>
        <script type="text/javascript" src="../javascript/jquery/js/jquery-ui-1.8.16.custom.min.js"></script>
        <script type="text/javascript" src="../javascript/jquery/js/jquery.ui.datepicker-pt-BR.js"></script>

        <!-- TableSorter -->
        <link rel="stylesheet" href="../javascript/plugins/TableSorter/styleSorterV3.css" />
        <script type="text/javascript" src="../javascript/plugins/TableSorter/scriptSorterV3.js"></script>
        <!-- TableSorter -->

        <link href="../css/estilo.css" rel="stylesheet" type="text/css" />

        <link rel="stylesheet" href="../javascript/plugins/dropdown/css/style.css" type="text/css" media="screen, projection"/>
        <!--[if lte IE 7]>
        <link rel="stylesheet" type="text/css" href="../../javascript/plugins/dropdown/css/ie.css" media="screen" />
        <![endif]-->
        <script type="text/javascript" language="javascript" src="../javascript/plugins/dropdown/js/jquery.dropdownPlain.js"></script>

        <script type="text/javascript" charset="utf-8">


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



        </script>
        <title>Portal Postal | Objetos não Entregues</title>
    </head>
    <body onload="alertaQtd();">
        <div id="divInteracao" class="esconder" align="center"><input id="textointeracao" /></div>
        <div id="divProtecao" class="esconder"></div>

        <%@ include file="../Includes/telaMsg_pi.jsp" %>
        <%@ include file="../Includes/menu_agencia_pi.jsp" %>

        <div id="divPrincipal" align="center">
            <div id="container">
                <div id="conteudo">


                    <div id="titulo1">Objetos não Entregues</div>

                    <div style="width: 100%; padding: 10px; background: white;">


                        <ul style="width: 95%; text-align: left;" class="ul_formulario">
                            <li>
                                <dd style="width: 500px;" >
                                    <div class="buttons">
                                        <form name='formObs' action='../ServAtualizaSRO' method='post'>
                                            <input type="hidden" name="nomeBD" value="<%= nomeBD%>" />
                                            <input type="hidden" name="SROpi/paginaRedirect" value="painel_nao_entregues" />
                                            <button type="submit" onclick="abrirTelaEspera();" class="regular"><img src="../imagensNew/refresh.png"/> ATUALIZAR SRO</button>
                                        </form>  
                                        <br/>
                                        <b>* Antes de atualizar o SRO, verifique se o Web Service da ECT está online.</b>

                                    </div>
                                </dd>
                            </li>
                        </ul>

                        <div id="titulo2" style="margin-top: 50px;">Lista de Objetos NÃO ENTREGUES</div>
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
                                    <th><h3>Cliente</h3></th>
                                    <th><h3>Nº do Objeto</h3></th>
                                    <th><h3>Serviço</h3></th>  
                                    <th><h3>Cod ECT</h3></th>
                                    <th><h3>Destinatário</h3></th>
                                    <th><h3>CEP</h3></th> 
                                    <th><h3>Postada em</h3></th>
                                    <th width="120"><h3>Ultimo status</h3></th>
                                    <th width="40"><h3>Tempo</h3></th>
                                    <th width="40"><h3>PI</h3></th>
                                        <%--<th class="nosort" width="40"><h3>DEL</h3></th>--%>
                                </tr>
                            </thead>
                            <tbody>
                                <%
                                    ArrayList<Movimentacao> lista2 = Controle.contrSRO.consultaDetalhesObjetosSroNaoEntregues(nomeBD);
                                    for (int i = 0; i < lista2.size(); i++) {
                                        Movimentacao mov = lista2.get(i);
                                        String nomeCli = contrCliente.consultaNomeById(Integer.parseInt(mov.getCodCliente()), nomeBD);
                                        String numObj = mov.getNumObjeto();
                                        String servico = mov.getDescServico();
                                        String codigoEct = mov.getCodigoEct();
                                        String detinatario = mov.getDestinatario();
                                        String cep = mov.getCep();
                                        //String AR = "";
                                        //String VD =  ""+mov.getValorDeclarado();
                                        String dtVenda = sdf.format(mov.getDataPostagem());
                                        String ultimoStatus = mov.getStatus();
                                        int dif = ((int) Util.SomaData.diferencaEmDias(mov.getDataPostagem(), dataAtual)) - 1;
                                        String pi = "";

                                %>
                                <tr style="cursor:default;">
                                    <td><%= nomeCli%></td>
                                    <td align="center"><a href='http://websro.correios.com.br/sro_bin/txect01$.QueryList?P_LINGUA=001&P_TIPO=001&P_COD_UNI=<%= numObj%>' target=_blank><%= numObj%></a></td>
                                    <td><%= servico%></td>
                                    <td><%= codigoEct%></td>
                                    <td><%= detinatario%></td>
                                    <td><%= cep%></td>
                                    <td><%= dtVenda%></td>
                                    <td><%= ultimoStatus%></td>
                                    <td><%= dif%> dias</td>
                                    <td align="center"><a onclick="" style="cursor:pointer;" >pi</a></td>

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
                                    <img src="../javascript/plugins/TableSorter/images/left_end.png" width="20" height="20" alt="First Page" onclick="sorter3.move(-1, true)" />
                                    <img src="../javascript/plugins/TableSorter/images/left.png" width="20" height="20" alt="First Page" onclick="sorter3.move(-1)" />
                                    <img src="../javascript/plugins/TableSorter/images/right.png" width="20" height="20" alt="First Page" onclick="sorter3.move(1)" />
                                    <img src="../javascript/plugins/TableSorter/images/right_end.png" width="20" height="20" alt="Last Page" onclick="sorter3.move(1, true)" />
                                    <select style="margin-left:5px;" id="pagedropdown3"></select>
                                    <a style="margin-left:10px;" href="javascript:sorter3.showall()">Ver Tudo</a>
                                </div>
                            </div>
                            <div id="tablelocation">
                                <div class="page">Página <span id="currentpage3"></span> de <span id="totalpages3"></span></div>
                            </div>
                        </div>
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
                        </script>
                        <input type="hidden" name="nomeBD" value="<%= nomeBD%>" />
                        <input type="hidden" name="idVenda" id="idVendaDel" value="" />
                        <input type="hidden" name="numObjeto" id="numObjetoDel" value="" />


                    </div>

                </div>
            </div>
        </div>
    </body>
</html>
<%}%>