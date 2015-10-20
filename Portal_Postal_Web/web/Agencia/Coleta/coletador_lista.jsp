
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
        <script type="text/javascript" src="../../javascript/mascara.js" charset="utf-8"></script>

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
                if(form.nome.value==""){
                    alert('Preencha o Nome do coletador!');
                    return false;
                }
                if(form.telefone.value==""){
                    alert('Preencha o Telefone do coletador!');
                    return false;
                }
                if(form.login.value==""){
                    alert('Insira um Login para o coletador!');
                    return false;
                }
                if(document.getElementById('foo').innerHTML.indexOf('w') == -1){
                    alert('O Login digitado é inválido ou já existente!\n Favor escolha outro!');
                    return false;
                }
                if(form.senha.value=="" || form.senha2.value==""){
                    alert('Insira uma Senha para o coletador!');
                    return false;
                }
                if(form.senha.value!=form.senha2.value){
                    alert('As senhas digitadas não conferem!');
                    return false;
                }

                form.submit();
            }
            
            function preencherCamposEdit(){
                var form = document.form5;
                if(form.nome.value==''){
                    alert('Preencha o nome do coletador!');
                    return false;
                }
                if(form.telefone.value==''){
                    alert('Preencha o Telefone do coletador!');
                    return false;
                }
                if(form.login.value==''){
                    alert('Insira um login para o coletador!');
                    return false;
                }
                if(form.login.value != form.loginaux.value){
                    if(document.getElementById('fooEditar').innerHTML.indexOf('w') == -1){
                        alert('O login digitado é inválido ou já existente!\n Favor escolha outro!');
                        return false;
                    }
                }
                if(form.senha.value!=form.senha2.value){
                    alert('As senhas digitadas não conferem!');
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

        <title>Portal Postal | Coletadores</title>
    </head>
    <body>
        <div id="divInteracao" class="esconder" style="top:10%; left:20%; right:20%; bottom:10%;" align="center"><input id="textointeracao" /></div>
        <div id="divProtecao" class="esconder"></div>

        <%@ include file="../../Includes/menu_agencia.jsp" %>
        <%@ include file="../../Includes/telaMsg.jsp" %>

        <div id="divPrincipal" align="center">
            <div id="container">
                <div id="conteudo">


                    <div id="titulo1">Cadastro de Coletadores</div>                
                    <form name="form1" action="../../ServInserirColetador" method="post">
                        <ul class="ul_formulario">
                            <li>
                                <dd>
                                    <label>Nome do Coletador</label>
                                    <input type="text" name="nome" />
                                </dd>
                                <dd>
                                    <label>Telefone</label>
                                    <input type="text" name="telefone" maxlength="14" onKeyPress="mascara(this,maskTelefone)" />
                                </dd>
                            </li>
                            <li>
                                <dd>
                                    <label>Login Mobile</label>
                                    <input type="text" name="login" maxlength="30" autocomplete="off" onkeyup="confereLoginColetadorNovo(this.value);" />
                                    <div id="foo"></div>
                                </dd>
                                <dd>
                                    <label>Senha</label>
                                    <input type="password" name="senha" />
                                </dd>
                                <dd>
                                    <label>Repita a Senha</label>
                                    <input type="password" name="senha2" />
                                </dd>
                            </li>
                            <li>
                                <dd style="width: 100%;">
                                    <div class="buttons">
                                        <input type="hidden" name="rota" value="0" />
                                        <button type="button" class="positive" onclick="return preencherCampos();"><img src="../../imagensNew/tick_circle.png" /> INSERIR NOVO COLETADOR</button>
                                    </div>
                                </dd>
                            </li>
                        </ul>
                    </form>
                    <img width="100%" src="../../imagensNew/linha.jpg"/>

                    <div id="titulo2">Lista com todos os coletadores</div>
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
                                <th><h3>Nome</h3></th>
                                <th width="120"><h3>Telefone</h3></th>
                                <th class="nosort" width="80"><h3>Rota</h3></th>
                                <th class="nosort" width="65"><h3>Alterar</h3></th>
                                <th class="nosort" width="65"><h3>Excluir</h3></th>
                            </tr>
                        </thead>
                        <tbody>
                            <%
                                            ArrayList listaColetador = Coleta.Controle.contrColetador.consultaTodosColetadores(nomeBD);
                                            for (int j = 0; j < listaColetador.size(); j++) {
                                                Coleta.Entidade.Coletador col = (Coleta.Entidade.Coletador) listaColetador.get(j);
                                                String nomeColetador = col.getNome();
                                                String telefoneColetador = col.getTelefone();
                                                int rota = col.getRota();
                                                int idColetador = col.getIdColetador();
                            %>
                            <tr>
                                <td><%= idColetador%></td>
                                <td><%= nomeColetador%></td>
                                <td><%= telefoneColetador%></td>
                                <td align="center"><a href="coletador_rota.jsp?idColetador=<%= idColetador%>"><img class="link_img" src="../../imagensNew/map_pencil.png" border="0" /> Rota</a></td>
                                <td align="center"><a onclick="verEditarColetador(<%= idColetador%>);" ><img src="../../imagensNew/pencil.png" border="0" /></a></td>
                                <td align="center">
                                    <form action="../../ServExcluirColetador" method="post" name="formDel">
                                        <input type="hidden" name="idColetador" value="<%= idColetador%>" />
                                        <input type="image" src="../../imagensNew/cancel.png" width="15" border="0" onClick="javascript:if (confirm('Tem certeza que deseja excluir?')){return true;}else{return false;}" />
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