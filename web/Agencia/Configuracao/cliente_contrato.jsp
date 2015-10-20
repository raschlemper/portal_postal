<%@page import="Controle.ContrClienteContrato"%>
<%@page import="Controle.ContrServicoECT"%>
<%@page import="Entidade.ServicoECT"%>
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

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        empresas emp = (empresas) session.getAttribute("emp");
        String nomeBD = (String) session.getAttribute("empresa");
        int idClienteInc = Integer.parseInt(request.getParameter("idCliente"));
        
        Entidade.Clientes cliInc = Controle.contrCliente.consultaClienteById(idClienteInc, nomeBD);

        String nomeContrato = cliInc.getNomeContrato();
        if (nomeContrato == null) {
            nomeContrato = "";
        }
        int anoContrato = cliInc.getAnoContrato();
        if (anoContrato == 0) {
            anoContrato = 2012;
        }
        String numContrato = cliInc.getNumContrato();
        if (numContrato == null) {
            numContrato = "";
        }
        String cartaoPostagem = cliInc.getCartaoPostagem();
        if (cartaoPostagem == null) {
            cartaoPostagem = "";
        }
        String codAdm = cliInc.getCodAdministrativo();
        if (codAdm == null) {
            codAdm = "";
        }
        String dtVig = "---";
        if (cliInc.getDtVigenciaFimContrato() != null) {
            dtVig = sdf.format(cliInc.getDtVigenciaFimContrato());
        }
        String ufContrato = cliInc.getUfContrato();
        int statusCartao = cliInc.getStatusCartaoPostagem();
        
        int codDir = cliInc.getCodDiretoria();
        String nomeSara = cliInc.getNome();
        String cnpjSenha = cliInc.getCnpj().replaceAll("[./-]", "").substring(0, 8);
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
            function trocaServ(listRemove, listAdiciona) {
                var selIndex = document.getElementById(listRemove).selectedIndex;
                var idServ = document.getElementById(listRemove).options[selIndex].value;
                var nomeServ = document.getElementById(listRemove).options[selIndex].text;

                if(listAdiciona == 'servico_2'){
                    var aux = idServ.split(";");
                    if(verificarGrupoServ(listAdiciona, aux[1])){
                        alert('Este serviço já está na lista do contrato!\n\nPara adicionar, remova o serviço mesmo do contrato!');
                        return false;
                    }
                }

                document.getElementById(listRemove).remove(selIndex);

                var novaOpcao = new Option(nomeServ, idServ);
                document.getElementById(listAdiciona).options[document.getElementById(listAdiciona).length] = novaOpcao;

                OrdenarLista(listRemove);
                OrdenarLista(listAdiciona);

                document.getElementById(listRemove).focus();
                if (document.getElementById(listRemove).length == selIndex) {
                    document.getElementById(listRemove).selectedIndex = selIndex - 1;
                } else {
                    document.getElementById(listRemove).selectedIndex = selIndex;
                }
            }

            function verificarGrupoServ(combo, grupo) {
                var lb = document.getElementById(combo);
                var flag = false;
                for (i = 0; i < lb.length; i++) {
                    var aux = lb.options[i].value.split(";");
                    if (aux[1] == grupo) {
                        flag = true;
                    }
                }
                return flag;
            }

            function OrdenarLista(combo) {
                var lb = document.getElementById(combo);
                arrTexts = new Array();

                for (i = 0; i < lb.length; i++) {
                    arrTexts[i] = lb.options[i].text + "@" + lb.options[i].value;
                }

                arrTexts.sort();

                for (i = 0; i < lb.length; i++) {
                    var aux = arrTexts[i].split("@");
                    lb.options[i].text = aux[0];
                    lb.options[i].value = aux[1];
                }
            }

            function preencherCampos() {
                var form = document.form1;

                if (document.getElementById('radio_1').checked) {

                    if (document.getElementById('codAdm').value == '') {
                        alert('Preencha o Código Administrativo do Contrtato!');
                        return false;
                    }

                    if (document.getElementById('cartaoPostagem').value == '') {
                        alert('Preencha o Cartão de Postagem do Contrato!');
                        return false;
                    }
                    
                    if (document.getElementById('numContrato').value == '' || document.getElementById('numContrato').value.length != 10) {
                        alert('Digite um Número de Contrato Válido para o Cliente!');
                        return false;
                    }

                    if (document.getElementById('nomeContrato').value == '') {
                        alert('Digite um Nome do Cliente para a Chancela do Contrato!');
                        return false;
                    }

                    if (document.getElementById('ufContrato').value == '') {
                        alert('Escolha a Unidade Federativa do Contrato!');
                        return false;
                    }

                    var lb = document.getElementById('servico_2');
                    var servicos = "";
                    for (i = 0; i < lb.length; i++) {
                        if (i == 0) {
                            servicos += lb.options[i].value;
                        } else {
                            servicos += "@" + lb.options[i].value;
                        }
                    }
                    document.getElementById('servicos').value = servicos;

                    if (servicos == "") {
                        alert('Selecione algum serviço para o Contrato do Cliente!');
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

            function mostra() {
                document.getElementById('divServ').className = 'mostrar';
                document.getElementById('divNum').className = 'mostrar';
                document.getElementById('divNum2').className = 'mostrar';
                document.getElementById('divNum3').className = 'mostrar';
                document.getElementById('divNum4').className = 'titulo';
                document.getElementById('divNum5').className = 'mostrar';
                document.getElementById('divNum6').className = 'titulo';
                document.getElementById('divNum7').className = 'mostrar';
            }
            function esconde() {
                document.getElementById('divServ').className = 'esconder';
                document.getElementById('divNum').className = 'esconder';
                document.getElementById('divNum2').className = 'esconder';
                document.getElementById('divNum3').className = 'esconder';
                document.getElementById('divNum4').className = 'esconder';
                document.getElementById('divNum5').className = 'esconder';
                document.getElementById('divNum6').className = 'esconder';
                document.getElementById('divNum7').className = 'esconder';
            }

        </script>

        <title>Portal Postal | Sequências Lógicas</title>

    </head>
    <body>
        <div id="divInteracao" class="esconder" style="top:10%; left:20%; right:20%; bottom:10%;" align="center"><input id="textointeracao" /></div>
        <div id="divProtecao" class="esconder"></div>

        <%@ include file="../../Includes/menu_agencia.jsp" %>
        <%@ include file="../../Includes/telaMsg.jsp" %>

        <div id="divPrincipal" align="center">
            <div id="container">
                <div id="conteudo">

                    <div id="titulo1">Contrato do Cliente <span><%= cliInc.getNomeFantasia()%></span></div>

                    <jsp:include page="cliente_tab_menu.jsp" >
                        <jsp:param name="nomeBDTab" value="<%= nomeBD %>" />
                        <jsp:param name="activeTab" value="4" />
                        <jsp:param name="idClienteTab" value="<%= idClienteInc %>" />
                        <jsp:param name="temContratoTab" value="<%= cliInc.getTemContrato() %>" />
                    </jsp:include>

                    <form name="form1" action="../../ServClienteContrato" method="post">
                        <ul class="ul_formulario">
                            <li class="titulo">
                                <dd><span>ALTERAR DADOS DO CONTRATO</span></dd>
                            </li>
                            <li>
                                <dd>
                                    <label style="margin-bottom: 8px;">Este Cliente possui contrato com a ECT?</label>
                                    <label style="clear: none;display: inline;" for="radio_1"><input type="radio" name="contrato" id="radio_1" value="1" <%if (cliInc.getTemContrato() == 1) {%> checked="true" <%}%> onclick="mostra();" /> Sim</label> &nbsp;&nbsp;&nbsp;&nbsp;
                                    <label style="clear: none;display: inline;" for="radio_2"><input type="radio" name="contrato" id="radio_2" value="0" <%if (cliInc.getTemContrato() == 0) {%> checked="true" <%}%> onclick="esconde();" /> Não</label>
                                </dd>
                            </li>
                            <br/>
                            <li id="divNum" style="clear: both;" <%if (cliInc.getTemContrato() == 1) {%> class="mostrar" <%} else {%> class="esconder" <%}%>>
                                <dd>
                                    <label>Nome para Chancela<span style="color:red;">(Máx. 18 carac.)</span></label>
                                    <input type="text" name="nomeContrato" id="nomeContrato" value="<%= nomeContrato%>" size="40" maxlength="18" />
                                </dd>
                                <dd>
                                    <label>Nº do Contrato</label>
                                    <input type="text" name="numContrato" id="numContrato" value="<%= numContrato%>" maxlength="10" onkeypress="mascara(this, maskNumero)" />
                                </dd>
                                <dd>
                                    <label>Cartão de Postagem</label>
                                    <input type="text" name="cartaoPostagem" id="cartaoPostagem" value="<%= cartaoPostagem%>" size="25" maxlength="20" onkeypress="mascara(this, maskNumero)" />
                                </dd>                                
                                <dd>
                                    <%if (cliInc.getLogin_sigep() != null && !cliInc.getLogin_sigep().equals("") && !cliInc.getLogin_sigep().toLowerCase().equals("null")) {%>
                                    <div class="buttons2" style="margin: 8px 10px;">
                                        <button onclick="pesquisaDadosCliSigep();" type="button" class="regular" ><img class="link_img" src="../../imagensNew/lupa.png" /> PREENCHER CAMPOS COM DADOS DO SIGEPWEB</button>
                                    </div>
                                    <%} else {%>
                                    <b style="font-size: 14px; color: red;">
                                        Para habilitar esta funcionalidade, solicite o login do SIGEP WEB para o seu Cliente!<br/>
                                        Após recebida a senha, altere o cadastro do Contrato ECT do cliente no Portal Postal!
                                    </b>
                                    <%}%>
                                </dd>
                            </li>
                            <li id="divNum2" <%if (cliInc.getTemContrato() == 1) {%> class="mostrar" <%} else {%> class="esconder" <%}%>>
                                <dd style="clear:both;width: 100%;margin-top: 10px;border-bottom: 1px solid silver;">
                                    <b style="color: red;">
                                        *Os serviços devem ser preenchidos manualmente, pois o SigepWEB não informa todos os serviços que fazem parte do contrato!
                                        <%--*Os serviços PAC, SEDEX, SEDEX 10, SEDEX 12, E-SEDEX e PAC GRANDES FORMATOS serão preenchidos automaticamente através do Web Service do SigepWEB!<br/>
                                        *Exceto os serviços CARTA REG., CARTA SIMPLES e SEDEX A COBRAR devem ser preenchidos manualmente, pois o SigepWEB não informa se eles fazem parte do contrato!--%>
                                    </b>
                                </dd>
                            </li>
                            <li id="divNum3" <%if (cliInc.getTemContrato() == 1) {%> class="mostrar" <%} else {%> class="esconder" <%}%>>
                                <dd>
                                    <label>Status do Cartão de Postagem?</label>
                                    <select name="statusCartao" id="statusCartao">
                                        <option <%if(statusCartao==1){%> selected <%}%> value="1">ATIVO</option>
                                        <option <%if(statusCartao==0){%> selected <%}%> value="0">INATIVO/SUSPENSO</option>
                                    </select>
                                </dd>
                                <dd>
                                    <label>Cód. Administrativo</label>
                                    <input type="text" name="codAdm" id="codAdm" value="<%= codAdm %>" size="25" maxlength="20" onkeypress="mascara(this, maskNumero)" />
                                </dd>
                                <dd>
                                    <label>Válido Até</label>
                                    <input type="text" name="vigenciaFim" id="vigenciaFim" value="<%= dtVig%>" maxlength="10" size="10" onkeypress="mascara(this, maskData)" />
                                    <input type="hidden" name="codDir" id="codDir" value="<%= codDir %>" value="0" size="10" />
                                    <input type="hidden" name="nomeSara" id="nomeSara" value="<%= nomeSara %>" size="10" />
                                    <input type="hidden" name="cnpjSara" id="cnpjSara" value="<%= cnpjSenha %>" size="10" />
                                </dd>
                                <dd>
                                    <label>Ano</label>
                                    <input type="text" name="anoContrato" id="anoContrato" value="<%= anoContrato%>" maxlength="4" size="5" onkeypress="mascara(this, maskNumero)" />
                                </dd>
                                <dd>
                                    <label>UF</label> 
                                    <select style="width: 50px;" name="ufContrato" id="ufContrato">
                                        <option value="" selected>- - -</option>
                                        <option value="AC" <%if ("AC".equals(ufContrato)) {%> selected <%}%>>AC</option>
                                        <option value="AL" <%if ("AL".equals(ufContrato)) {%> selected <%}%>>AL</option>
                                        <option value="AP" <%if ("AP".equals(ufContrato)) {%> selected <%}%>>AP</option>
                                        <option value="AM" <%if ("AM".equals(ufContrato)) {%> selected <%}%>>AM</option>
                                        <option value="BA" <%if ("BA".equals(ufContrato)) {%> selected <%}%>>BA</option>
                                        <option value="CE" <%if ("CE".equals(ufContrato)) {%> selected <%}%>>CE</option>
                                        <option value="DF" <%if ("DF".equals(ufContrato)) {%> selected <%}%>>DF</option>
                                        <option value="ES" <%if ("ES".equals(ufContrato)) {%> selected <%}%>>ES</option>
                                        <option value="GO" <%if ("GO".equals(ufContrato)) {%> selected <%}%>>GO</option>
                                        <option value="MA" <%if ("MA".equals(ufContrato)) {%> selected <%}%>>MA</option>
                                        <option value="MT" <%if ("MT".equals(ufContrato)) {%> selected <%}%>>MT</option>
                                        <option value="MS" <%if ("MS".equals(ufContrato)) {%> selected <%}%>>MS</option>
                                        <option value="MG" <%if ("MG".equals(ufContrato)) {%> selected <%}%>>MG</option>
                                        <option value="PA" <%if ("PA".equals(ufContrato)) {%> selected <%}%>>PA</option>
                                        <option value="PB" <%if ("PB".equals(ufContrato)) {%> selected <%}%>>PB</option>
                                        <option value="PR" <%if ("PR".equals(ufContrato)) {%> selected <%}%>>PR</option>
                                        <option value="PE" <%if ("PE".equals(ufContrato)) {%> selected <%}%>>PE</option>
                                        <option value="PI" <%if ("PI".equals(ufContrato)) {%> selected <%}%>>PI</option>
                                        <option value="RJ" <%if ("RJ".equals(ufContrato)) {%> selected <%}%>>RJ</option>
                                        <option value="RN" <%if ("RN".equals(ufContrato)) {%> selected <%}%>>RN</option>
                                        <option value="RS" <%if ("RS".equals(ufContrato)) {%> selected <%}%>>RS</option>
                                        <option value="RO" <%if ("RO".equals(ufContrato)) {%> selected <%}%>>RO</option>
                                        <option value="RR" <%if ("RR".equals(ufContrato)) {%> selected <%}%>>RR</option>
                                        <option value="SC" <%if ("SC".equals(ufContrato)) {%> selected <%}%>>SC</option>
                                        <option value="SP" <%if ("SP".equals(ufContrato)) {%> selected <%}%>>SP</option>
                                        <option value="SE" <%if ("SE".equals(ufContrato)) {%> selected <%}%>>SE</option>
                                        <option value="TO" <%if ("TO".equals(ufContrato)) {%> selected <%}%>>TO</option>
                                    </select>
                                </dd>
                            </li>
                            <li id="divServ" style="clear: both;" <%if (cliInc.getTemContrato() == 1) {%> class="mostrar" <%} else {%> class="esconder" <%}%> >
                                <dd>
                                    <label>Serviços da ECT</label>
                                    <select style="width: 220px;" name="servico_1" id="servico_1" size="10">
                                        <%
                                            ArrayList<Integer> listaContrato = ContrClienteContrato.consultaContratoCliente(idClienteInc, nomeBD);
                                            ArrayList<ServicoECT> listaServ = ContrServicoECT.consultaServicos(0, 1, emp.getTipo_agencia());
                                            if(listaServ!=null){
                                            for (int i = 0; i < listaServ.size(); i++) {
                                                ServicoECT sv = listaServ.get(i);
                                                if (!listaContrato.contains(sv.getCodECT())) {
                                                    out.println("<option value='" + sv.getCodECT() + ";" + sv.getGrupoServico() + "'>" + sv.getNomeServico() + "</option>");
                                                }
                                            }
                                            }
                                        %>
                                    </select>
                                    <div id="foo"></div>
                                </dd>
                                <dd>
                                    <div class="buttons2" style="margin: 15px 10px;">
                                        <button onclick="trocaServ('servico_1', 'servico_2');" type="button" class="positive" style="width: 130px;" ><img class="link_img" src="../../imagensNew/plus_circle.png" /> ADICIONAR</button>
                                        <br/><br/>
                                        <button onclick="trocaServ('servico_2', 'servico_1');" type="button" class="negative" style="width: 130px;" ><img class="link_img" src="../../imagensNew/minus_circle.png" /> REMOVER</button>
                                    </div>
                                </dd>
                                <dd>
                                    <label>SERVIÇOS QUE ESTÃO NO CONTRATO</label>
                                    <select style="width: 300px;font-size: 12px;" name="servico_2" id="servico_2" size="10"> 
                                        <%
                                            if (listaServ != null) {
                                                for (int i = 0; i < listaServ.size(); i++) {
                                                    ServicoECT sv = listaServ.get(i);
                                                    if (listaContrato.contains(sv.getCodECT())) {
                                                        out.print("<option value='" + sv.getCodECT() + ";" + sv.getGrupoServico() + "'>" + sv.getNomeServico() + "</option>");
                                                    }
                                                }
                                            }
                                        %>                                
                                    </select>
                                </dd>
                                <dd style="clear:both;margin-top: 15px;"><b style="color: red;"></b></dd>
                            </li>
                              
                            <li id="divNum4" <%if (cliInc.getTemContrato() == 1) {%> class="titulo" <%} else {%> class="esconder" <%}%>>
                                <dd><span>LOGIN E SENHA DO SIGEPWEB DO CLIENTE</span></dd>
                            </li>
                            <li id="divNum5" <%if (cliInc.getTemContrato() == 1) {%> class="mostrar" <%} else {%> class="esconder" <%}%>>
                                <dd>
                                    <label>Login SigepWeb</label>
                                    <input type="text" name="login_sigep" id="login_sigep" value="<%= cliInc.getLogin_sigep() %>" size="25" />
                                </dd>
                                <dd>
                                    <label>Senha SigepWeb</label>
                                    <input type="text" name="senha_sigep" id="senha_sigep" value="<%= cliInc.getSenha_sigep() %>" size="25" />
                                </dd>
                            </li>
                              
                            <li  id="divNum6" <%if (cliInc.getTemContrato() == 1) {%> class="titulo" <%} else {%> class="esconder" <%}%>>
                                <dd><span>LOGIN E SENHA LOGÍSTICA REVERSA DO CLIENTE</span></dd>
                            </li>
                            <li id="divNum7" <%if (cliInc.getTemContrato() == 1) {%> class="mostrar" <%} else {%> class="esconder" <%}%>>
                                <dd>
                                    <label>Login Log. Reversa</label>
                                    <input type="text" name="login_reversa" id="login_reversa" value="<%= cliInc.getLogin_reversa() %>" size="25" />
                                </dd>
                                <dd>
                                    <label>Senha Log. Reversa</label>
                                    <input type="text" name="senha_reversa" id="senha_reversa" value="<%= cliInc.getSenha_reversa() %>" size="25" />
                                </dd>
                                <dd>
                                    <label>Cartão de Postagem  Log. Reversa</label>
                                    <input type="text" name="cartao_reversa" id="cartao_reversa" value="<%= cliInc.getCartao_reversa() %>" size="25" />
                                </dd>
                            </li>
                            <li>
                                <dd style="width: 100%;">
                                    <div class="buttons">
                                        <input type="hidden" name="idCliente" id="idCliente" value="<%= idClienteInc%>" />
                                        <input type="hidden" name="cnpj" value="<%= cliInc.getCnpj() %>" />
                                        <input type="hidden" name="servicos" id="servicos" value="" />
                                        <button type="button" class="positive" onclick="return preencherCampos();"><img src="../../imagensNew/tick_circle.png" /> ATUALIZAR E SALVAR DADOS</button>
                                    </div>
                                </dd>
                            </li>
                        </ul>
                    </form>
                    <img width="100%" src="../../imagensNew/linha.jpg"/>

                </div>
            </div>
        </div>
    </body>
</html>
<%}%>