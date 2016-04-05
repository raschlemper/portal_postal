
<%@page import="Entidade.ClientesDeptos"%>
<%@page import="Controle.ContrClienteDeptos"%>
<%@ page import = "java.util.ArrayList,java.sql.Timestamp, java.util.Date, java.text.SimpleDateFormat"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%
    String nomeBD = (String) session.getAttribute("nomeBD");
    if (nomeBD == null) {
        response.sendRedirect("../../index.jsp?msg=Sua sessao expirou! Para voltar ao Portal faça seu login novamente!");
    } else {

        int nivelUsuarioEmp2 = (Integer) session.getAttribute("nivelUsuarioEmp");
        int idEmpresa = Integer.parseInt(String.valueOf(session.getAttribute("idCliente")));
        int idCli = Integer.parseInt(String.valueOf(session.getAttribute("idCliente")));
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
                if(form.nivel.value==""){
                    alert('Preencha o nivel do usuário!');
                    return false;
                }
                if(form.login.value==""){
                    alert('Insira um login para o usuário!');
                    return false;
                }
                if(document.getElementById('foo').innerHTML.indexOf('w') == -1){
                    alert('O login digitado é inválido ou já existente!\n Favor escolha outro!');
                    return false;
                }
                if(form.senha.value==""){
                    alert('Insira uma senha para o usuário!');
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
                if(form.nivel.value==''){
                    alert('Preencha o nivel do usuário!');
                    return false;
                }
                if(form.login.value==''){
                    alert('Insira um login para o usuário!');
                    return false;
                }
                if(form.login.value != form.loginaux.value){
                    if(document.getElementById('fooEditar').innerHTML.indexOf('w') == -1){
                        alert('O login digitado é inválido ou já existente!\n\nFavor escolha outro!');
                        return false;
                    }
                }
                if(form.senha.value!=''){
                    if(form.senha.value!=form.senha2.value){
                        alert('As senhas digitadas não conferem!');
                        return false;
                    }
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


            function controleSelecao() {
                if (document.frm.acc[0].checked) {
                    document.getElementById('persons').disabled = true;
                    document.getElementById('usuario').disabled = true;
                }
                else if (document.frm.acc[1].checked) {
                    document.getElementById('persons').disabled = false;
                    document.getElementById('usuario').disabled = true;
                }
                else if (document.frm.acc[2].checked) {
                    document.getElementById('persons').disabled = true;
                    document.getElementById('usuario').disabled = false;
                }
            }
        </script>

        <title>Portal Postal | Usuários do Sistema</title>

    </head>
    <body>
        <div id="divInteracao" class="esconder" style="top:10%; left:20%; right:20%; bottom:10%;" align="center"><input id="textointeracao" /></div>
        <div id="divProtecao" class="esconder"></div>

        <%@ include file="../../Includes/menu_cliente.jsp" %>
        <%@ include file="../../Includes/telaMsg.jsp" %>

        <div id="divPrincipal" align="center">
            <div id="container">
                <div id="conteudo">

                    <div id="titulo1">Cadastro de Usuários</div>

                    <form name="form1" action="../../ServCriarLoginPortal" method="post">
                        <ul class="ul_formulario">
                            <li class="titulo">
                                <dd><span>Cadastrar Novo Usuário</span></dd>
                            </li>
                            <li>
                                <dd>
                                    <label>Login</label>
                                    <input style='width:200px;' type="text" autocomplete="off" name="login" onchange="confereLoginPortal(this.value);" />
                                    <div id="foo"></div>
                                </dd>
                                <dd>
                                    <label>Senha</label>
                                    <input style='width:200px;' type="password" name="senha" />
                                </dd>
                                <dd>
                                    <label>Repita a Senha</label>
                                    <input style='width:200px;' type="password" name="senha2" />
                                </dd>
                                <dd>
                                    <label>Nível</label>
                                    <select name="nivel" style="width: 200px;">
                                        <option value="1">Administrador</option>
                                        <option value="2">Gerencia</option>
                                        <option value="3">Usuário</option>
                                    </select>
                                </dd>
                            </li>
                            <li>
                                <dd>
                                    <label>ACESSOS:</label>
                                    <select style="width: 206px;" name=acessos id=acessos multiple onclick="controleCombobox0(this)" size=10 >
                                        <option value="1" >PESQUISAS / RELATÓRIOS</option>
                                        <option value="2" >CONTROLE DE A.R.</option>
                                        <option value="3" >VISUALIZA VALORES / DESPESAS</option>
                                        <option value="4" >SOLICITAÇÃO DE COLETA</option>
                                        <option value="5" >GERAR ETQIUETAS</option>
                                        <option value="6" >VER DADOS DA EMPRESA</option>
                                        <option value="7" >CADASTRO DE DESTINATÁRIOS</option>
                                    </select>
                                    <script language="">
                                        function controleCombobox0(combo) {
                                            combo_aux0[combo.selectedIndex] = !combo_aux0[combo.selectedIndex];
                                            for (i=0; i < combo.length; i++ ){
                                                combo.options[i].selected = combo_aux0[i];
                                            }
                                        }
                                        var combo_aux0 = new Array(document.getElementById("acessos").options.length);
                                        for (i=0; i < document.getElementById("acessos").options.length; i++ ) {
                                            combo_aux0[i] = document.getElementById("acessos").options[i].selected;
                                        }

                                    </script>
                                </dd>
                                <dd>
                                    <label>DEPARTAMENTOS:</label>
                                    <select style="width: 206px;" name=departamentos id=departamentos multiple onclick="controleCombobox1(this)" size=10 >
                                        <%
                                            ArrayList<ClientesDeptos> listaDep = ContrClienteDeptos.consultaDeptos(idCli, nomeBD);
                                            for (int i = 0; i < listaDep.size(); i++) {
                                                ClientesDeptos cd = listaDep.get(i);
                                        %>
                                        <option value="<%=cd.getIdDepartamento()%>"><%= cd.getNomeDepartamento()%></option>
                                        <%}%>
                                    </select>
                                    <script language="">
                                        function controleCombobox1(combo) {
                                            combo_aux1[combo.selectedIndex] = !combo_aux1[combo.selectedIndex];
                                            for (i=0; i < combo.length; i++ ){
                                                combo.options[i].selected = combo_aux1[i];
                                            }
                                        }
                                        var combo_aux1 = new Array(document.getElementById("departamentos").options.length);
                                        for (i=0; i < document.getElementById("departamentos").options.length; i++ ) {
                                            combo_aux1[i] = document.getElementById("departamentos").options[i].selected;
                                        }

                                    </script>
                                </dd>
                                <dd>
                                    <label>SERVIÇOS:</label>
                                    <select style="width: 206px;" name=servicos id=servicos multiple onclick="controleCombobox2(this)" size=10 >
                                        <option value="1" >PAC</option>
                                        <option value="2" >SEDEX</option>
                                        <option value="3" >SEDEX A COBRAR</option>
                                        <option value="4" >SEDEX 10</option>
                                        <option value="5" >E-SEDEX</option>
                                        <option value="6" >CARTA REGISTRADA</option>
                                        <option value="7" >SEDEX 12</option>
                                    </select>
                                    <script language="">
                                        function controleCombobox2(combo) {
                                            combo_aux2[combo.selectedIndex] = !combo_aux2[combo.selectedIndex];
                                            for (i=0; i < combo.length; i++ ){
                                                combo.options[i].selected = combo_aux2[i];
                                            }
                                        }
                                        var combo_aux2 = new Array(document.getElementById("servicos").options.length);
                                        for (i=0; i < document.getElementById("servicos").options.length; i++ ) {
                                            combo_aux2[i] = document.getElementById("servicos").options[i].selected;
                                        }

                                    </script>
                                </dd>
                            </li>
                            <li>
                                <dd style="width: 100%">
                                    <div class="buttons">
                                        <input type="hidden" name="idCliente" value="<%= idCli%>" />
                                        <button type="button" class="positive" onclick="return preencherCampos();"><img src="../../imagensNew/tick_circle.png" /> SALVAR DADOS</button>
                                    </div>
                                </dd>
                            </li>
                        </ul>
                    </form>
                    <img width="100%" src="../../imagensNew/linha.jpg"/>


                    <div id="titulo2">Lista de todos os usuários</div>
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
                                <th><h3>Login</h3></th>
                               <!-- <th><h3>Senha</h3></th> -->
                                <th><h3>Nivel</h3></th>
                                <th class="nosort" width="60"><h3>Alterar</h3></th>
                                <th class="nosort" width="60"><h3>Excluir</h3></th>
                            </tr>
                        </thead>
                        <tbody>
                            <%
                                ArrayList<Entidade.SenhaCliente> listaLogins = Controle.contrSenhaCliente.consultaTodasSenhaCliente(idCli, nomeBD);
                                for (int i = 0; i < listaLogins.size(); i++) {
                                    Entidade.SenhaCliente sc3 = listaLogins.get(i);
                                    String loginSc = sc3.getLogin();
                                    String senhaSc = sc3.getSenha();
                                    int nivel = sc3.getNivel();
                                    int id = sc3.getId();
                                    String nomeNivel = Controle.contrNivel.consultaNomeByIdNivel(nivel, nomeBD);
                                                if(nivel == 99 ){
                                                    nomeNivel = "WEB SERVICE";
                                                }
                            %>
                            <tr style="cursor:default;">
                                <td><%= loginSc%></td>
                              <%--   <td><%= senhaSc%></td> --%>
                                <td><%= nomeNivel%></td>
                                <td align="center">
                                    <%if(nivel < 99){%>
                                    <a onclick="verEditarLoginPortal(<%= id%>, 2);" style="cursor:pointer;" ><img src="../../imagensNew/pencil.png" /></a>
                                    <%}%>
                                </td>                                    
                                <td align="center">
                                    <%if(nivel < 99){%>
                                    <form action="../../ServExcluirLoginPortal" method="post" name="formDel">
                                        <input type="hidden" name="idCliente" value="<%= idCli%>" />
                                        <input type="hidden" name="login" value="<%= loginSc%>" />
                                        <input type="hidden" name="senha" value="<%= senhaSc%>" />
                                        <input type="image" src="../../imagensNew/cancel.png" border="0" onClick="javascript:if (confirm('Tem certeza que deseja excluir?')){return true;}else{return false;}" />
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