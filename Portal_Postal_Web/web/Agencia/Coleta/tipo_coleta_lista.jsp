
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
%>
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />

        <script type="text/javascript" src="../../javascript/ajax.js"></script>

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

        <script type="text/javascript">
            function preencherCampos(){
                var form = document.form1;
                if(form.tipo.value == ''){
                    alert('Preencha o Tipo da Coleta!');
                    return false;
                }
                form.submit();
            }
            function preencherCamposEdit(){
                var form = document.form5;
                if(form.tipo.value == ''){
                    alert('Preencha o Tipo da Coleta!');
                    return false;
                }
                form.submit();
            }

            function chamaDivProtecao(){
                var classe = document.getElementById("divProtecao").className;
                if(classe == "esconder"){
                    document.getElementById("divProtecao").className = "mostrar";
                    document.getElementById("divInteracao").className = "mostrar";
                }else{
                    document.getElementById("divProtecao").className = "esconder";
                    document.getElementById("divInteracao").className = "esconder";
                }
            }

        </script>

        <title>Portal Postal | Tipos de Coleta</title>

    </head>
    <body>
        <div id="divInteracao" class="esconder" style="top:20%; left:25%; right:25%; bottom:20%;" align="center"><input id="textointeracao" /></div>
        <div id="divProtecao" class="esconder"></div>

        <%@ include file="../../Includes/menu_agencia.jsp" %>
        <%@ include file="../../Includes/telaMsg.jsp" %>

        <div id="divPrincipal" align="center">
            <div id="container">
                <div id="conteudo">


                    <div id="titulo1">Cadastro de Tipos de Coleta</div>
                    <form name="form1" action="../../ServInserirTipoColeta" method="post">
                        <ul class="ul_formulario">
                            <li class="titulo">
                                <dd><span>Inserir novo tipo de coleta</span></dd>
                            </li>
                            <li>
                                <dd>
                                    <label>Tipo da Coleta</label>
                                    <input style="width:200px;" name="tipo" type="text" />
                                </dd>
                            </li>
                            <li>
                                <dd>
                                    <div class="buttons">
                                        <button type="button" class="positive" onclick="return preencherCampos();"><img src="../../imagensNew/tick_circle.png" /> INSERIR NOVO TIPO DE COLETA</button>
                                    </div>
                                </dd>
                            </li>
                        </ul>
                    </form>
                    <img width="100%" src="../../imagensNew/linha.jpg"/>


                    <div id="titulo2">Lista de todos os tipos de coleta</div>
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
                                <th><h3>Tipo da Coleta</h3></th>
                                <th class="nosort" width="65"><h3>Alterar</h3></th>
                                <th class="nosort" width="65"><h3>Excluir</h3></th>
                            </tr>
                        </thead>
                        <tbody>
                            <%
                                            ArrayList listaTipo = Coleta.Controle.contrTipoColeta.consultaTodosTipoColeta(nomeBD);
                                            for (int j = 0; j < listaTipo.size(); j++) {
                                                Coleta.Entidade.TipoColeta tipoCol = (Coleta.Entidade.TipoColeta) listaTipo.get(j);
                                                String tipo = tipoCol.getTipo();
                                                int idTipo = tipoCol.getIdTipoColeta();
                            %>
                            <tr>
                                <td><%= idTipo%></td>
                                <td><%= tipo%></td>
                                <td align="center"><a onclick="verEditarTipoColeta(<%= idTipo%>);" ><img src="../../imagensNew/pencil.png" border="0" /></a></td>
                                <td align="center">
                                    <form action="../../ServExcluirTipoColeta" method="post" name="formDel">
                                        <input type="hidden" name="idTipoColeta" value="<%= idTipo%>" />
                                        <input type="image" src="../../imagensNew/cancel.png" border="0" onClick="javascript:if (confirm('Tem certeza que deseja excluir?')){return true;}else{return false;}" />
                                    </form>
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