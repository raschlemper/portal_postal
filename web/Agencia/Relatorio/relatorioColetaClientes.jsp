<%--
    Document   : relatorioColeta
    Created on : 30/09/2009, 12:42:40
    Author     : SCC4
--%>
<%@page import="java.sql.Connection"%>
<%@page import="Util.Conexao"%>
<%@page import="Entidade.Clientes"%>
<%@ page import="java.text.SimpleDateFormat, java.util.Date, java.sql.Timestamp, java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%
            String nomeBD = (String) session.getAttribute("empresa");
            if (nomeBD == null) {
                response.sendRedirect("../index.jsp?msgLog=3");
            } else {

                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Portal Postal | Relatórios</title>

        <script type="text/javascript" src="../../javascript/mascara.js"></script>

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
    </head>
    <body>
        <div id="divInteracao" class="esconder" align="center"><input id="textointeracao"></div>
        <div id="divProtecao" class="esconder"></div>

        <%@ include file="../../Includes/menu_agencia.jsp" %>
        <%@ include file="../../Includes/telaMsg.jsp" %>

        <div id="divPrincipal" align="center">
            <div id="container">
                <div id="conteudo">

                    <div id="titulo1" style="margin-top:15px;">Relatório de Frequencia de Coleta de Todos os Clientes</div>
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
                                <th><h3>Nome do Cliente</h3></th>
                                <th><h3>Data da última coleta</h3></th>
                                <th><h3>Qtd mês atual</h3></th>
                                <th><h3>Qtd mês anterior</h3></th>
                                <th><h3>Valor mês atual(R$)</h3></th>
                                <th><h3>Valor mês anterior(R$)</h3></th>
                            </tr>
                        </thead>
                        <tbody>
                            <%
                                            //Connection conn = (Connection) Conexao.conectar(nomeBD);
                                            ArrayList<Clientes> listaClientes = Controle.contrCliente.consultaClienteUltColeta(nomeBD, 50);
                                            for (int j = 0; j < listaClientes.size(); j++) {
                                                Clientes cli = listaClientes.get(j);
                                                String nomeCli = cli.getNome();
                                                int idCliente = cli.getCodigo();
                                                String dataUlt = cli.getEmail();

                                               // ArrayList listaQtdValor = Controle.contrRelatoriosHoito.pesquisaQtdValorPorIdCliente(nomeBD, idCliente, conn);
                                                String qtdAtual = "0 Objetos", valorAtual = "0.00", qtdPassado = "0 Objetos", valorPassado = "0.00";
                                               /* for (int i = 0; i < listaQtdValor.size(); i++) {
                                                    String[] obj = (String[]) listaQtdValor.get(i);
                                                    if (i == 0) {
                                                        qtdPassado = obj[2] + " Objetos";
                                                        qtdPassado = qtdPassado.replace(".00", "");
                                                        valorPassado = obj[1];
                                                    } else {
                                                        qtdAtual = obj[2] + " Objetos";
                                                        qtdAtual = qtdAtual.replace(".00", "");
                                                        valorAtual = obj[1];
                                                    }
                                                }*/
                            %>
                            <tr>
                                <td><%= idCliente%></td>
                                <td><%= nomeCli%></td>
                                <td><%= dataUlt%></td>
                                <td><%= qtdAtual%></td>
                                <td><%= qtdPassado%></td>
                                <td><%= valorAtual%></td>
                                <td><%= valorPassado%></td>
                            </tr>
                            <%
                                            }
                                           // Conexao.desconectar(conn);
                            %>
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
                            hoverid:'selectedrowPointer',
                            pageddid:'pagedropdown2',
                            navid:'tablenav2',
                            sortcolumn:0,
                            sortdir:1,
                            sum:[3,4,5,6],
                            columns:[{index:3, format:' Objetos', decimals:1},{index:4, format:' Objetos', decimals:1}],
                            init:true
                        });
                    </script>


                </div>
            </div>
        </div>
    </body>
</html>
<%}%>