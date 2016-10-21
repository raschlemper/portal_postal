
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
                if (form.nome.value == "") {
                    alert('Preencha o nome do usuário!');
                    return false;
                }
                if (form.nivel.value == "") {
                    alert('Preencha o nivel do usuário!');
                    return false;
                }
                if (form.login.value == "") {
                    alert('Insira um login para o usuário!');
                    return false;
                }
                if (document.getElementById('foo').innerHTML.indexOf('w') == -1) {
                    alert('O login digitado é inválido ou já existente!\n Favor escolha outro!');
                    return false;
                }
                if (form.senha.value == "") {
                    alert('Insira uma senha para o usuário!');
                    return false;
                }
                if (form.senha.value != form.senha2.value) {
                    alert('As senhas digitadas não conferem!');
                    return false;
                }

                form.submit();
            }

            function preencherCamposEdit() {
                var form = document.form5;
                if (form.nome.value == '') {
                    alert('Preencha o nome do usuário!');
                    return false;
                }
                if (form.nivel.value == '') {
                    alert('Preencha o nivel do usuário!');
                    return false;
                }
                if (form.login.value == '') {
                    alert('Insira um login para o usuário!');
                    return false;
                }
                if (form.login.value != form.loginaux.value) {
                    if (document.getElementById('fooEditar').innerHTML.indexOf('w') == -1) {
                        alert('O login digitado é inválido ou já existente!\n\nFavor escolha outro!');
                        return false;
                    }
                }
                if (form.senha.value != '') {
                    if (form.senha.value != form.senha2.value) {
                        alert('As senhas digitadas não conferem!');
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

        </script>

        <title>Portal Postal | Usuários do Sistema</title>

    </head>
    <body>
        <div id="divInteracao" class="esconder" style="top:8%; left:5%; right:5%; bottom:8%;" align="center"></div>
        <div id="divProtecao" class="esconder"></div>

        <%@ include file="../../Includes/menu_agencia.jsp" %>
        <%@ include file="../../Includes/telaMsg.jsp" %>

        <div id="divPrincipal" align="center">
            <div id="container">
                <div id="conteudo">

                    <div id="titulo1">Cadastro de Usuários</div>
                    <form name="form1" action="../../ServInserirUsuario" method="post">
                        <ul class="ul_formulario">
                            <li class="titulo">
                                <dd><span>Cadastrar Novo Usuário</span></dd>
                            </li>
                            <li>
                                <dd>
                                    <label>Nome do Usuário</label>
                                    <input type="text" autocomplete="off" name="nome" />
                                </dd>
                                <dd>
                                    <label>Nível</label>
                                    <select name="nivel">
                                        <%
                                            ArrayList listaNiveis = Controle.contrNivel.consultaTodosNiveis(nomeBD);
                                            for (int i = 0; i < listaNiveis.size(); i++) {
                                                Entidade.Nivel nv = (Entidade.Nivel) listaNiveis.get(i);
                                                String nomeNivel = nv.getNivel();
                                                int idNivel = nv.getIdNivel();
                                        %>
                                        <option value="<%= idNivel%>"><%= nomeNivel%></option>
                                        <%}%>
                                    </select>
                                </dd>
                                <dd>
                                    <label>E-mail</label>
                                    <input type="text" autocomplete="off" name="email" />
                                </dd>
                                <dd>
                                    <label>Login</label>
                                    <input type="text" autocomplete="off" name="login" onkeyup="confereLoginUsuarioNovo(this.value);" />
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
                            <li class="titulo" style="margin-top: 20px;">
                                <dd><span><input type="checkbox" name="usaPortalPostal" onclick="if(this.checked){document.getElementById('liPortalPostal').className='mostrar'}else{document.getElementById('liPortalPostal').className='esconder'}" value="1" /> - Usa Portal Postal?</span></dd>
                            </li>
                            <li id="liPortalPostal" class="esconder">
                                <dd>
                                    <label>
                                        ABA GERENCIAR ETIQUETAS:<br/>
                                        <a style="color:blue;font-size: 8px;" onclick="selectAllCombo(document.getElementById('geretq'), true);">MARCAR TUDO</a><br/>
                                        <a style="color:red;font-size: 8px;" onclick="selectAllCombo(document.getElementById('geretq'), false);">DESMARCAR TUDO</a><br/>
                                    </label>
                                    <select style="width: 275px;" name=geretq id=geretq multiple onclick="controleCombobox0(this)" size=10 >
                                        <option value="101" >ETIQUETAS RESTANTES</option>
                                        <option value="102" >SEQUÊNCIAS GERADAS</option>
                                        <option value="103" >PESQUISAR ETIQUETA</option>
                                        <option value="104" >ETIQUETAS PENDENTES</option>
                                        <option value="105" >ETIQUETAS INUTILIZADAS</option>
                                    </select>
                                    <script language="">
                                        function selectAllCombo(combo, flag) {
                                            for (i = 0; i < combo.length; i++) {
                                                combo.options[i].selected = flag;
                                            }
                                        }
                                        function controleCombobox0(combo) {
                                            combo_aux0[combo.selectedIndex] = !combo_aux0[combo.selectedIndex];
                                            for (i = 0; i < combo.length; i++) {
                                                combo.options[i].selected = combo_aux0[i];
                                            }
                                        }
                                        var combo_aux0 = new Array(document.getElementById("geretq").options.length);
                                        for (i = 0; i < document.getElementById("geretq").options.length; i++) {
                                            combo_aux0[i] = document.getElementById("geretq").options[i].selected;
                                        }

                                    </script>
                                </dd>
                                <dd>
                                    <label>
                                        ABA COLETA:<br/>
                                        <a style="color:blue;font-size: 8px;" onclick="selectAllCombo(document.getElementById('coleta'), true);">MARCAR TUDO</a><br/>
                                        <a style="color:red;font-size: 8px;" onclick="selectAllCombo(document.getElementById('coleta'), false);">DESMARCAR TUDO</a><br/>
                                    </label>
                                    <select style="width: 275px;" name=coleta id=coleta multiple onclick="controleCombobox1(this)" size=10 >
                                        <option value="201" >ACOMPANHAMENTO</option>
                                        <option value="202" >NOVA COLETA</option>
                                        <option value="203" >COLETADORES</option>
                                        <option value="204" >TIPO DE COLETA</option>
                                        <option value="205" >HORÁRIOS DA COLETA</option>
                                    </select>
                                    <script language="">
                                        function controleCombobox1(combo) {
                                            combo_aux1[combo.selectedIndex] = !combo_aux1[combo.selectedIndex];
                                            for (i = 0; i < combo.length; i++) {
                                                combo.options[i].selected = combo_aux1[i];
                                            }
                                        }
                                        var combo_aux1 = new Array(document.getElementById("coleta").options.length);
                                        for (i = 0; i < document.getElementById("coleta").options.length; i++) {
                                            combo_aux1[i] = document.getElementById("coleta").options[i].selected;
                                        }

                                    </script>
                                </dd>
                                <dd>
                                    <label>
                                        ABA IMPORTAÇÕES:<br/>
                                        <a style="color:blue;font-size: 8px;" onclick="selectAllCombo(document.getElementById('importacao'), true);">MARCAR TUDO</a><br/>
                                        <a style="color:red;font-size: 8px;" onclick="selectAllCombo(document.getElementById('importacao'), false);">DESMARCAR TUDO</a><br/></label>
                                    <select style="width: 275px;" name="importacao" id="importacao" multiple onclick="controleCombobox2(this)" size="10" >
                                        <option value="301" >ARQUIVO DE MOVIMENTO</option>
                                        <option value="302" >ARQUIVO DE AR's</option>
                                        <option value="303" >ARQUIVO DE CLIENTES</option>
                                        <option value="304" >ARQUIVO DE DEPARTAMENTOS</option>
                                    </select>
                                    <script language="">
                                        function controleCombobox2(combo) {
                                            combo_aux2[combo.selectedIndex] = !combo_aux2[combo.selectedIndex];
                                            for (i = 0; i < combo.length; i++) {
                                                combo.options[i].selected = combo_aux2[i];
                                            }
                                        }
                                        var combo_aux2 = new Array(document.getElementById("importacao").options.length);
                                        for (i = 0; i < document.getElementById("importacao").options.length; i++) {
                                            combo_aux2[i] = document.getElementById("importacao").options[i].selected;
                                        }

                                    </script>
                                </dd>
                                <dd>
                                    <label>
                                        ABA CADASTROS:<br/>
                                        <a style="color:blue;font-size: 8px;" onclick="selectAllCombo(document.getElementById('cadastro'), true);">MARCAR TUDO</a><br/>
                                        <a style="color:red;font-size: 8px;" onclick="selectAllCombo(document.getElementById('cadastro'), false);">DESMARCAR TUDO</a><br/></label>
                                    <select style="width: 275px;" name="cadastro" id="cadastro" multiple onclick="controleCombobox3(this)" size="10" >
                                        <option value="401" >PREFIXO DE ETIQUETAS</option>
                                        <option value="402" >ABRANGÊNCIA DE SERVIÇOS</option>
                                        <option value="403" >AMARRAÇÕES</option>
                                        <option value="404" >USUÁRIOS</option>
                                        <option value="405" >CLIENTES</option>
                                        <option value="406" >VERIFICAÇÃO DE CONTRATOS</option>
                                    </select>
                                    <script language="">
                                        function controleCombobox3(combo) {
                                            combo_aux3[combo.selectedIndex] = !combo_aux3[combo.selectedIndex];
                                            for (i = 0; i < combo.length; i++) {
                                                combo.options[i].selected = combo_aux3[i];
                                            }
                                        }
                                        var combo_aux3 = new Array(document.getElementById("cadastro").options.length);
                                        for (i = 0; i < document.getElementById("cadastro").options.length; i++) {
                                            combo_aux3[i] = document.getElementById("cadastro").options[i].selected;
                                        }

                                    </script>
                                </dd>
                            </li>

                            <li class="titulo" style="padding-top: 30px;">
                                <dd><span><input type="checkbox" name="usaConsolidador" onclick="if(this.checked){document.getElementById('liConsolidador').className='mostrar'}else{document.getElementById('liConsolidador').className='esconder'}" value="1" /> - Usa Consolidador?</span></dd>
                            </li>
                            <li id="liConsolidador" class="esconder">
                                <dd>
                                    <label>
                                        ABA CONFIGURAÇÕES:<br/>
                                        <a style="color:blue;font-size: 8px;" onclick="selectAllCombo(document.getElementById('con_conf'), true);">MARCAR TUDO</a><br/>
                                        <a style="color:red;font-size: 8px;" onclick="selectAllCombo(document.getElementById('con_conf'), false);">DESMARCAR TUDO</a><br/>
                                    </label>
                                    <select style="width: 275px;" name=con_conf id=con_conf multiple onclick="controleCombobox4(this)" size=10 >
                                        <option value="101" >CONFIGURAÇÕES GERAIS</option>
                                        <option value="102" >CONFIGURAR E-MAIL</option>
                                        <option value="103" >CONFIGURAR BALANÇA</option>
                                        <option value="104" >CONFIGURAR IMPRESSORA</option>
                                    </select>
                                    <script language="">
                                        function controleCombobox4(combo) {
                                            combo_aux4[combo.selectedIndex] = !combo_aux4[combo.selectedIndex];
                                            for (i = 0; i < combo.length; i++) {
                                                combo.options[i].selected = combo_aux4[i];
                                            }
                                        }
                                        var combo_aux4 = new Array(document.getElementById("con_conf").options.length);
                                        for (i = 0; i < document.getElementById("con_conf").options.length; i++) {
                                            combo_aux4[i] = document.getElementById("con_conf").options[i].selected;
                                        }

                                    </script>
                                </dd>
                                <dd>
                                    <label>
                                        ABA FERRAMENTAS:<br/>
                                        <a style="color:blue;font-size: 8px;" onclick="selectAllCombo(document.getElementById('con_ferramenta'), true);">MARCAR TUDO</a><br/>
                                        <a style="color:red;font-size: 8px;" onclick="selectAllCombo(document.getElementById('con_ferramenta'), false);">DESMARCAR TUDO</a><br/>
                                    </label>
                                    <select style="width: 275px;" name=con_ferramenta id=con_ferramenta multiple onclick="controleCombobox5(this)" size=10 >
                                        <option value="201" >PROCESSAR OBJETOS COM A BOXCUBO</option>
                                        <option value="202" >ALTERAÇÃO DE ETIQUETAS</option>
                                        <option value="203" >REIMPRESSÃO DE ETIQUETAS GERADAS</option>
                                        <option value="204" >ENVIAR E-MAIL DE OBJETOS CONSOLIDADOS</option>
                                        <option value="205" >VENDA LOCAL - SINTÉTICA</option>
                                        <option value="206" >ESTORNAR/ALTERAR VENDAS LOCAIS</option>
                                        <option value="207" >BIPAR OBJETOS DE PLPs DE TERCEIROS</option>
                                        <option value="208" >CONFERIR E ENVIAR PLPs DE TERCEIROS</option>
                                        <option value="209" >COMPARAR VENDAS A FATURAR SECT x SARA</option>
                                        <option value="210" >ENTREGA INTERNA DE ETIQUETAS</option>
                                    </select>
                                    <script language="">
                                        function controleCombobox5(combo) {
                                            combo_aux5[combo.selectedIndex] = !combo_aux5[combo.selectedIndex];
                                            for (i = 0; i < combo.length; i++) {
                                                combo.options[i].selected = combo_aux5[i];
                                            }
                                        }
                                        var combo_aux5 = new Array(document.getElementById("con_ferramenta").options.length);
                                        for (i = 0; i < document.getElementById("con_ferramenta").options.length; i++) {
                                            combo_aux5[i] = document.getElementById("con_ferramenta").options[i].selected;
                                        }

                                    </script>
                                </dd>
                                <dd>
                                    <label>
                                        ABA EXPORTAÇÕES:<br/>
                                            <a style="color:blue;font-size: 8px;" onclick="selectAllCombo(document.getElementById('con_exportacao'), true);">MARCAR TUDO</a><br/>
                                        <a style="color:red;font-size: 8px;" onclick="selectAllCombo(document.getElementById('con_exportacao'), false);">DESMARCAR TUDO</a><br/></label>
                                    <select style="width: 275px;" name="con_exportacao" id="con_exportacao" multiple onclick="controleCombobox6(this)" size="10" >
                                        <option value="301" >EXPORTAR DADOS PARA O SECT</option>
                                        <option value="302" >EXPORTAR DADOS PARA O SARA</option>
                                    </select>
                                    <script language="">
                                        function controleCombobox6(combo) {
                                            combo_aux6[combo.selectedIndex] = !combo_aux6[combo.selectedIndex];
                                            for (i = 0; i < combo.length; i++) {
                                                combo.options[i].selected = combo_aux6[i];
                                            }
                                        }
                                        var combo_aux6 = new Array(document.getElementById("con_exportacao").options.length);
                                        for (i = 0; i < document.getElementById("con_exportacao").options.length; i++) {
                                            combo_aux6[i] = document.getElementById("con_exportacao").options[i].selected;
                                        }

                                    </script>
                                </dd>
                                <dd>
                                    <label>
                                        ABA EXPEDIÇÃO:<br/>
                                        <a style="color:blue;font-size: 8px;" onclick="selectAllCombo(document.getElementById('con_exped'), true);">MARCAR TUDO</a><br/>
                                        <a style="color:red;font-size: 8px;" onclick="selectAllCombo(document.getElementById('con_exped'), false);">DESMARCAR TUDO</a><br/></label>
                                    <select style="width: 275px;" name="con_exped" id="con_exped" multiple onclick="controleCombobox7(this)" size="10" >
                                        <option value="401" >FAZER EXPEDIÇÃO</option>
                                        <option value="402" >RELATÓRIO DE OBJETOS NÃO EXPEDIDOS</option>
                                        <option value="403" >IMPRIMIR/EXPORTAR MALAS</option>
                                        <option value="404" >RELATÓRIO DE OBJETOS PROCESSADOS</option>
                                    </select>
                                    <script language="">
                                        function controleCombobox7(combo) {
                                            combo_aux7[combo.selectedIndex] = !combo_aux7[combo.selectedIndex];
                                            for (i = 0; i < combo.length; i++) {
                                                combo.options[i].selected = combo_aux7[i];
                                            }
                                        }
                                        var combo_aux7 = new Array(document.getElementById("con_exped").options.length);
                                        for (i = 0; i < document.getElementById("con_exped").options.length; i++) {
                                            combo_aux7[i] = document.getElementById("con_exped").options[i].selected;
                                        }

                                    </script>
                                </dd>
                                <dd>
                                    <label>
                                        ABA PESQUISAS:<br/>
                                        <a style="color:blue;font-size: 8px;" onclick="selectAllCombo(document.getElementById('con_pesq'), true);">MARCAR TUDO</a><br/>
                                        <a style="color:red;font-size: 8px;" onclick="selectAllCombo(document.getElementById('con_pesq'), false);">DESMARCAR TUDO</a><br/></label>
                                    <select style="width: 275px;" name="con_pesq" id="con_pesq" multiple onclick="controleCombobox8(this)" size="10" >
                                        <option value="501" >OBJETO POSTAL</option>
                                        <option value="502" >ORDENS DE SERVIÇO</option>
                                        <option value="503" >EXPORTAÇÕES SECT REALIZADAS</option>
                                        <option value="504" >EXPORTAÇÕES SARA REALIZADAS</option>
                                    </select>
                                    <script language="">
                                        function controleCombobox8(combo) {
                                            combo_aux8[combo.selectedIndex] = !combo_aux8[combo.selectedIndex];
                                            for (i = 0; i < combo.length; i++) {
                                                combo.options[i].selected = combo_aux8[i];
                                            }
                                        }
                                        var combo_aux8 = new Array(document.getElementById("con_pesq").options.length);
                                        for (i = 0; i < document.getElementById("con_pesq").options.length; i++) {
                                            combo_aux8[i] = document.getElementById("con_pesq").options[i].selected;
                                        }

                                    </script>
                                </dd>
                                <dd>
                                    <label>
                                        ABA RELATÓRIOS:<br/>
                                        <a style="color:blue;font-size: 8px;" onclick="selectAllCombo(document.getElementById('con_relat'), true);">MARCAR TUDO</a><br/>
                                        <a style="color:red;font-size: 8px;" onclick="selectAllCombo(document.getElementById('con_relat'), false);">DESMARCAR TUDO</a><br/></label>
                                    <select style="width: 275px;" name="con_relat" id="con_relat" multiple onclick="controleCombobox9(this)" size="10" >
                                        <option value="601" >ATENDIMENTOS DO SARA</option>
                                        <option value="602" >COMPARAÇÕES SECT x SARA REALIZADAS</option>
                                        <option value="603" >ETIQUETAS ALTERADAS</option>
                                    </select>
                                    <script language="">
                                        function controleCombobox9(combo) {
                                            combo_aux9[combo.selectedIndex] = !combo_aux9[combo.selectedIndex];
                                            for (i = 0; i < combo.length; i++) {
                                                combo.options[i].selected = combo_aux9[i];
                                            }
                                        }
                                        var combo_aux9 = new Array(document.getElementById("con_relat").options.length);
                                        for (i = 0; i < document.getElementById("con_relat").options.length; i++) {
                                            combo_aux9[i] = document.getElementById("con_relat").options[i].selected;
                                        }

                                    </script>
                                </dd>
                                <dd>
                                    <label>
                                        ABA UTILITÁRIOS:<br/>
                                        <a style="color:blue;font-size: 8px;" onclick="selectAllCombo(document.getElementById('con_util'), true);">MARCAR TUDO</a><br/>
                                        <a style="color:red;font-size: 8px;" onclick="selectAllCombo(document.getElementById('con_util'), false);">DESMARCAR TUDO</a><br/></label>
                                    <select style="width: 275px;" name="con_util" id="con_util" multiple onclick="controleCombobox10(this)" size="10" >
                                        <option value="701" >CALCULAR DIGITO VERIFICADOR</option>
                                        <option value="702" >DOWNLOAD DO ROBÔ DO SARA</option>
                                        <option value="703" >REORGANIZAR BANCO DE DADOS</option>
                                        <option value="704" >MARCAR TODOS OBJETOS COMO EXPEDIDO</option>
                                    </select>
                                    <script language="">
                                        function controleCombobox10(combo) {
                                            combo_aux10[combo.selectedIndex] = !combo_aux10[combo.selectedIndex];
                                            for (i = 0; i < combo.length; i++) {
                                                combo.options[i].selected = combo_aux10[i];
                                            }
                                        }
                                        var combo_aux10 = new Array(document.getElementById("con_util").options.length);
                                        for (i = 0; i < document.getElementById("con_util").options.length; i++) {
                                            combo_aux10[i] = document.getElementById("con_util").options[i].selected;
                                        }

                                    </script>
                                </dd>
                            </li>
                            <li>
                                <dd style="width: 100%;">
                                    <div class="buttons">
                                        <button type="button" class="positive" onclick="return preencherCampos();"><img src="../../imagensNew/tick_circle.png" /> SALVAR DADOS</button>
                                    </div>
                                </dd>
                            </li>
                        </ul>
                    </form>
                    <img width="100%" src="../../imagensNew/linha.jpg"/>


                    <div id="titulo2">Lista de todos os usuários do sistema</div>
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
                                <th><h3>E-mail</h3></th>
                                <th><h3>Nivel</h3></th>
                                <th class="nosort" width="65"><h3>Alterar</h3></th>
                                <th class="nosort" width="65"><h3>Excluir</h3></th>
                            </tr>
                        </thead>
                        <tbody>
                            <%
                                ArrayList listaUsuario = Controle.contrUsuario.consultaTodosUsuariosByIdEmpresa(nomeBD, idEmpresa);
                                for (int j = 0; j < listaUsuario.size(); j++) {
                                    Entidade.Usuario col = (Entidade.Usuario) listaUsuario.get(j);
                                    String nomeUs = col.getNome();
                                    String email = col.getEmail();
                                    int nivelUs = col.getIdNivel();
                                    String nomeNivel = Controle.contrNivel.consultaNomeByIdNivel(nivelUs, nomeBD);
                                    int idUsuario = col.getIdUsuario();
                            %>
                            <tr>
                                <td><%= idUsuario%></td>
                                <td><%= nomeUs%></td>
                                <td><%= email%></td>
                                <td><%= nomeNivel + " - " + nivelUs%></td>
                                <td align="center"><img style="cursor: pointer;" onclick="verEditarUsuario(<%= idUsuario%>);" src="../../imagensNew/pencil.png" border="0" /></td>
                                <td align="center">
                                    <form action="../../ServExcluirUsuario" method="post" name="formDel">
                                        <input type="hidden" name="idUsuario" value="<%= idUsuario%>" />
                                        <input type="image" src="../../imagensNew/cancel.png" border="0" onClick="javascript:if (confirm('Tem certeza que deseja excluir?')) {
                                                    return true;
                                                } else {
                                                    return false;
                                                }" />
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
                            sortdir: 1,
                            init: true
                        });
                        
                 
                        function populateVar(){                    
                            combo_aux0 = new Array(document.getElementById("geretq_e").options.length);
                            for (i = 0; i < document.getElementById("geretq_e").options.length; i++) {
                                combo_aux0[i] = document.getElementById("geretq_e").options[i].selected;
                            }
                            combo_aux1 = new Array(document.getElementById("coleta_e").options.length);
                            for (i = 0; i < document.getElementById("coleta_e").options.length; i++) {
                                combo_aux1[i] = document.getElementById("coleta_e").options[i].selected;
                            }
                            combo_aux2 = new Array(document.getElementById("importacao_e").options.length);
                            for (i = 0; i < document.getElementById("importacao_e").options.length; i++) {
                                combo_aux2[i] = document.getElementById("importacao_e").options[i].selected;
                            }
                            combo_aux3 = new Array(document.getElementById("cadastro_e").options.length);
                            for (i = 0; i < document.getElementById("cadastro_e").options.length; i++) {
                                combo_aux3[i] = document.getElementById("cadastro_e").options[i].selected;
                            }
                            combo_aux4 = new Array(document.getElementById("con_conf_e").options.length);
                            for (i = 0; i < document.getElementById("con_conf_e").options.length; i++) {
                                combo_aux4[i] = document.getElementById("con_conf_e").options[i].selected;
                            }
                            combo_aux5 = new Array(document.getElementById("con_ferramenta_e").options.length);
                            for (i = 0; i < document.getElementById("con_ferramenta_e").options.length; i++) {
                                combo_aux5[i] = document.getElementById("con_ferramenta_e").options[i].selected;
                            }
                            combo_aux6 = new Array(document.getElementById("con_exportacao_e").options.length);
                            for (i = 0; i < document.getElementById("con_exportacao_e").options.length; i++) {
                                combo_aux6[i] = document.getElementById("con_exportacao_e").options[i].selected;
                            }
                            combo_aux7 = new Array(document.getElementById("con_exped_e").options.length);
                            for (i = 0; i < document.getElementById("con_exped_e").options.length; i++) {
                                combo_aux7[i] = document.getElementById("con_exped_e").options[i].selected;
                            }
                            combo_aux8 = new Array(document.getElementById("con_pesq_e").options.length);
                            for (i = 0; i < document.getElementById("con_pesq_e").options.length; i++) {
                                combo_aux8[i] = document.getElementById("con_pesq_e").options[i].selected;
                            }
                            combo_aux9 = new Array(document.getElementById("con_relat_e").options.length);
                            for (i = 0; i < document.getElementById("con_relat_e").options.length; i++) {
                                combo_aux9[i] = document.getElementById("con_relat_e").options[i].selected;
                            }
                            combo_aux10 = new Array(document.getElementById("con_util_e").options.length);
                            for (i = 0; i < document.getElementById("con_util_e").options.length; i++) {
                                combo_aux10[i] = document.getElementById("con_util_e").options[i].selected;
                            }
                        }
                    </script>

                </div>
            </div>
        </div>
    </body>
</html>
<%}%>