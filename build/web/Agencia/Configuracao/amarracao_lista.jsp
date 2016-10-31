
<%@page import="Controle.ContrAmarracaoServico"%>
<%@ page import = "java.util.ArrayList,java.sql.Timestamp, java.util.Date, java.text.SimpleDateFormat"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%
    if (session.getAttribute("usuario") == null) {
        response.sendRedirect("../index.jsp?msgLog=3");
    } else {

        int idNivelDoUsuario = (Integer) session.getAttribute("nivel");
        if (idNivelDoUsuario != 1) {
            response.sendRedirect("../jsp/imp_movimento.jsp?msg=Acesso Negado!");
        }

        String nomeBD = (String) session.getAttribute("empresa");
        int idEmpresa = (Integer) session.getAttribute("idEmpresa");
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

        <link rel="stylesheet" href="../../javascript/plugins/dropdown/css/style.css" type="text/css" media="screen, projection"/>
        <!--[if lte IE 7]>
        <link rel="stylesheet" type="text/css" href="../../javascript/plugins/dropdown/css/ie.css" media="screen" />
        <![endif]-->
        <script type="text/javascript" language="javascript" src="../../javascript/plugins/dropdown/js/jquery.dropdownPlain.js"></script>

        <title>Portal Postal | Amarrações</title>

    </head>
    <body>
        <div id="divInteracao" class="esconder" style="top:10%; left:20%; right:20%; bottom:10%;" align="center"><input id="textointeracao" /></div>
        <div id="divInteracao3" class="esconder" style="top:10%; left:20%; right:20%; bottom:10%;" align="center"><input id="textointeracao" /></div>
        <div id="divProtecao" class="esconder"></div>

        <%@ include file="../../Includes/menu_agencia.jsp" %>
        <%@ include file="../../Includes/telaMsg.jsp" %>

        <div id="divPrincipal" align="center">
            <div id="container">
                <div id="conteudo">


                    <div id="titulo1">Cadastro de Amarrações</div>
                    <ul class="ul_formulario">
                        <li>
                            <dd>
                                <div class="buttons">
                                    <button onclick="javascript:window.location='amarracao_inserir.jsp';" name="save" id="sub" class="regular"><img src="../../imagensNew/plus_circle.png" alt=""/> Adicionar Nova Amarração</button>
                                </div>
                            </dd>
                        </li>
                    </ul>
                    <img width="100%" src="../../imagensNew/linha.jpg"/>

                    <div id="titulo2">Lista de Todas as Amarrações</div>
                    <table id="barraTableSorter" border="0">
                        <tr>
                            <td align="left" style="font-weight:bold;font-size:12px;">
                                Pesquisa Rápida:
                                <select style='min-width:150px;' id="columns1" onchange="sorter1.search('query1')"></select>
                                <input type="text" id="query1" onkeyup="sorter1.search('query1')" placeholder="Digite aqui a sua pesquisa..." />
                                <a style="text-decoration:none;font-weight:bold;font-size:11px;" href="javascript:document.getElementById('query1').value='';sorter1.reset()">RESTAURAR PADRÕES</a>
                            </td>
                            <td align="right">
                                <div class="details" style="clear:both;">
                                    <div>Resultado <span id="startrecord1"></span>-<span id="endrecord1"></span> de <span id="totalrecords1"></span></div>
                                </div>
                            </td>
                        </tr>
                    </table>                    
                    <table cellpadding="0" cellspacing="0" border="0" id="table1" class="tinytable">
                        <thead>
                            <tr>
                                <th width="40"><h3>Nº</h3></th>
                                <th width="40"><h3>Sigla</h3></th>
                                <th><h3>Descrição</h3></th>
                                <th><h3>Serviços</h3></th>
                                <th class="nosort" width="65"><h3>Alterar</h3></th>
                                <th class="nosort" width="65"><h3>Excluir</h3></th>
                            </tr>
                        </thead>
                        <tbody>
                            <%
                                ArrayList<Entidade.Amarracao> listaAm = Controle.ContrAmarracao.consultaTodosAmarracao(nomeBD);
                                for (int j = 0; j < listaAm.size(); j++) {
                                    Entidade.Amarracao am = listaAm.get(j);
                                    String nomeAm = am.getNomeAmarracao();
                                    String siglaAm = am.getSiglaAmarracao();
                                    int idAmarracao = am.getIdAmarracao();
                                    
                                    ArrayList<String> listaServ = ContrAmarracaoServico.consultaTodosServicosByIdAmarracao(idAmarracao, nomeBD);
                                    String serv = "";
                                    for(int i=0; i<listaServ.size();i++){
                                        if(i == 0){
                                            serv += listaServ.get(i);
                                        }else{
                                            serv += " <b>|</b> "+listaServ.get(i);
                                        }
                                    }
                                    
                            %>
                            <tr>
                                <td><%= idAmarracao%></td>
                                <td align="center"><b><%= siglaAm%></b></td>
                                <td><%= nomeAm%></td>
                                <td><%= serv %></td>
                                <td align="center"><a style="cursor:pointer;" href="amarracao_editar.jsp?idAmarracao=<%= idAmarracao%>" ><img src="../../imagensNew/pencil.png" border="0" /></a></td>
                                <td align="center">
                                    <form action="../../ServExcluirAmarracao" method="post" name="formDel">
                                        <input type="hidden" name="idAmarracao" value="<%= idAmarracao%>">
                                            <input type="image" src="../../imagensNew/cancel.png" border="0" onClick="javascript:if (confirm('Tem certeza que deseja excluir?')){return true;}else{return false;}" />
                                    </form>
                                </td>
                            </tr>
                            <%}%>
                        </tbody>
                    </table>
                    <div id="tablefooter" align='center'>
                        <div align="left" style='float:left; width:20%;'>
                            <select onchange="sorter1.size(this.value)">
                                <option value="5">5</option>
                                <option value="10">10</option>
                                <option value="20" selected="selected">20</option>
                                <option value="50">50</option>
                                <option value="100">100</option>
                            </select>
                            <span>Linhas por Página</span>
                        </div>
                        <div id="tablenav1" class="tablenav">
                            <div>
                                <img src="../../javascript/plugins/TableSorter/images/left_end.png" width="20" height="20" alt="First Page" onclick="sorter2.move(-1,true)" />
                                <img src="../../javascript/plugins/TableSorter/images/left.png" width="20" height="20" alt="First Page" onclick="sorter2.move(-1)" />
                                <img src="../../javascript/plugins/TableSorter/images/right.png" width="20" height="20" alt="First Page" onclick="sorter2.move(1)" />
                                <img src="../../javascript/plugins/TableSorter/images/right_end.png" width="20" height="20" alt="Last Page" onclick="sorter2.move(1,true)" />
                                <select style="margin-left:5px;" id="pagedropdown1"></select>
                                <a style="margin-left:10px;" href="javascript:sorter1.showall()">Ver Tudo</a>
                            </div>
                        </div>
                        <div id="tablelocation">
                            <div class="page">Página <span id="currentpage1"></span> de <span id="totalpages1"></span></div>
                        </div>
                    </div>
                    <script type="text/javascript">
                        var sorter1 = new TINY.table.sorter('sorter1','table1',{
                            headclass:'head',
                            ascclass:'asc',
                            descclass:'desc',
                            evenclass:'evenrow',
                            oddclass:'oddrow',
                            evenselclass:'evenselected',
                            oddselclass:'oddselected',
                            paginate:true,
                            size:20,
                            colddid:'columns1',
                            currentid:'currentpage1',
                            totalid:'totalpages1',
                            startingrecid:'startrecord1',
                            endingrecid:'endrecord1',
                            totalrecid:'totalrecords1',
                            hoverid:'selectedrowDefault',
                            pageddid:'pagedropdown1',
                            navid:'tablenav1',
                            sortcolumn:0,
                            sortdir:1,
                            init:true
                        });
                    </script>

                </div>
            </div>
        </div>
    </body>
</html>
<%}%>