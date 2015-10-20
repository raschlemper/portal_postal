
<%@ page import = "java.util.ArrayList,java.sql.Timestamp, java.util.Date, java.text.SimpleDateFormat"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%
            if (session.getAttribute("usuario") == null) {
                response.sendRedirect("../index.jsp?msgLog=3");
            } else {

                int idNivelDoUsuario = (Integer) session.getAttribute("nivel");
                if (idNivelDoUsuario == 3) {
                    response.sendRedirect("../Importacao/imp_movimento.jsp?msg=Acesso Negado!");
                }

                String nomeBD = (String) session.getAttribute("empresa");
                int idClienteInc = Integer.parseInt(request.getParameter("idCliente"));
                Entidade.Clientes cliInc = Controle.contrCliente.consultaClienteById(idClienteInc, nomeBD);
                String nomeFantasia = cliInc.getNomeFantasia();
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
            function preencherCampos(){
                var form = document.form1;
                if(form.contatoNome.value==''){
                    alert('Preencha o nome do contato!');
                    return false;
                }
                form.submit();
            }

            function preencherCamposEdit(){
                var form = document.form5;
                if(form.contatoNome.value==''){
                    alert('Preencha o nome do contato!');
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

        <title>Portal Postal | Usuários do Sistema</title>

    </head>
    <body>
        <div id="divInteracao" class="esconder" style="top:10%; left:20%; right:20%; bottom:10%;" align="center"><input id="textointeracao" /></div>
        <div id="divProtecao" class="esconder"></div>

        <%@ include file="../../Includes/menu_agencia.jsp" %>
        <%@ include file="../../Includes/telaMsg.jsp" %>

        <div id="divPrincipal" align="center">
            <div id="container">
                <div id="conteudo">

                    <div id="titulo1">Contatos do Cliente <span><%= nomeFantasia%></span></div>
                    
                    <jsp:include page="cliente_tab_menu.jsp" >
                        <jsp:param name="nomeBDTab" value="<%= nomeBD%>" />
                        <jsp:param name="activeTab" value="2" />
                        <jsp:param name="idClienteTab" value="<%= idClienteInc%>" />
                        <jsp:param name="temContratoTab" value="<%= cliInc.getTemContrato()%>" />
                    </jsp:include>

                    <form name="form1" action="../../ServInserirContato" method="post">
                        <ul class="ul_formulario">
                            <li class="titulo">
                                <dd><span>Cadastrar Novo Contato</span></dd>
                            </li>
                            <li>
                                <dd><label>Nome:</label><input type="text" name="contatoNome" id="contatoNome" /></dd>
                                <dd><label>Telefone:</label><input type="text" name="contatoFone" id="contatoFone" maxlength="14" onkeypress="mascara(this,maskTelefone)" /></dd>
                                <dd><label>Email:</label><input type="text" name="contatoEmail" id="contatoEmail" /></dd>
                                <dd><label>Setor:</label><input type="text" name="contatoSetor" id="contatoSetor" /></dd>
                            </li>
                            <li>
                                <dd>
                                    <div class="buttons">
                                        <input name="idCliente" value="<%= idClienteInc%>" type="hidden" />
                                        <button type="button" class="positive" onclick="return preencherCampos();"><img src="../../imagensNew/tick_circle.png" /> SALVAR DADOS</button>
                                    </div>
                                </dd>
                            </li>
                        </ul>
                    </form>
                    <img width="100%" src="../../imagensNew/linha.jpg"/>


                    <div id="titulo2">Lista de todos os contatos do cliente</div>
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
                                <th><h3>Nome</h3></th>
                                <th><h3>Email</h3></th>
                                <th><h3>Telefone</h3></th>
                                <th><h3>Setor</h3></th>
                                <th class="nosort" width="65"><h3>Alterar</h3></th>
                                <th class="nosort" width="65"><h3>Excluir</h3></th>
                            </tr>
                        </thead>
                        <tbody>
                            <%
                                            ArrayList contatos = Controle.contrContato.consultaContatos(idClienteInc, nomeBD);
                                            for (int i = 0; i < contatos.size(); i++) {
                                                Entidade.Contato cont = (Entidade.Contato) contatos.get(i);
                                                int idContato = cont.getIdContato();
                                                String contatoNome = cont.getContato();
                                                String contatoEmail = cont.getEmail();
                                                String contatoFone = cont.getFoneramal();
                                                String contatoSetor = cont.getSetor();
                            %>
                            <tr style="cursor:default;">
                                <td><%= contatoNome%></td>
                                <td><%= contatoEmail%></td>
                                <td><%= contatoFone%></td>
                                <td><%= contatoSetor%></td>
                                <td align="center"><img src="../../imagensNew/pencil.png" width="15" onClick="verEditarContato(<%= idContato%>)" style="cursor:pointer;" /></td>
                                <td align="center">
                                    <form action="../../ServExcluirContato" method="post" name="formDel">
                                        <input type="hidden" name="idCliente" value="<%= idClienteInc%>" />
                                        <input type="hidden" name="idContato" value="<%= idContato %>" />
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