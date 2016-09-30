<%@page import="Controle.ContrClienteContrato"%>
<%@page import="Controle.ContrServicoECT"%>
<%@page import="Entidade.ServicoECT"%>
<%@ page import = "java.util.ArrayList,java.sql.Timestamp, java.util.Date, java.text.SimpleDateFormat"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%
    
    Usuario usrSessao = (Usuario) session.getAttribute("agf_usuario");
    if (usrSessao == null) {
        response.sendRedirect("../../index.jsp?msgLog=3");
    } else if (usrSessao.getListaAcessosPortalPostal().contains("405")) {
        response.sendRedirect("../../NewTemplate/Dashboard/index.jsp?msg=Usuario sem permissao!");
    } else {

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
        int statusCartao = cliInc.getStatusCartaoPostagem();
        if (cliInc.getDtVigenciaFimContrato() != null) {
            dtVig = sdf.format(cliInc.getDtVigenciaFimContrato());
            SimpleDateFormat sdfa = new SimpleDateFormat("yyyy-MM-dd");       
            Date dateWithoutTime = sdfa.parse(sdfa.format(new Date()));
            if(cliInc.getDtVigenciaFimContrato().before(dateWithoutTime)){
                statusCartao = 0; 
            }
        }
        String ufContrato = cliInc.getUfContrato();

        int codDir = cliInc.getCodDiretoria();
        String nomeSara = cliInc.getNome();
        String cnpjSenha = cliInc.getCnpj().replaceAll("[./-]", "").substring(0, 8);
%>
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <title>Portal Postal</title>
        <%@ include file="../includes/Css_js.jsp" %>
    </head>        
    <body>   
        <script type="text/javascript">
            waitMsg();
        </script> 
        <jsp:include page="../includes/navBarTop.jsp"></jsp:include>
            <div id="wrapper">
            <%@ include file="../includes/menu_agencia_bootstrap.jsp" %>

            <div id="page-content-wrapper">
                <div class="container-fluid">
                    <div id="page-wrapper">

                        <jsp:include page="cliente_menu_b.jsp" >
                            <jsp:param name="nomeBDTab" value="<%= nomeBD%>" />
                            <jsp:param name="activeTab" value="4" />
                            <jsp:param name="idClienteTab" value="<%= idClienteInc%>" />
                            <jsp:param name="temContratoTab" value="<%= cliInc.getTemContrato()%>" />
                            <jsp:param name="nomeClienteTab" value="<%= cliInc.getNomeFantasia()%>" />
                        </jsp:include>    

                        <div class="row">
                            <div class="col-xs-12">

                                <form name="form1" action="../../ServClienteContrato" method="post">
                                    <ul class="list-unstyled">
                                        <li class="list-group-item list-group-heading">
                                            <b>ALTERAR DADOS DO CONTRATO</b>
                                        </li>
                                        <li class="list-group-item">
                                            <div class="row form-horizontal">
                                                <div class="col-sm-12 col-md-12 col-lg-12">
                                                    <label class="small">Este Cliente possui contrato com a ECT?</label>
                                                    <div class="input-group">
                                                        <input type="checkbox" name="toggleBtn" id="radioContrato" value="1" <%if (cliInc.getTemContrato() == 1) {%> checked="true" <%}%> data-toggle="toggle" data-on="<i class='fa fa-check-circle fa-spc fa-lg'></i>SIM" data-off="<i class='fa fa-times-circle fa-spc fa-lg'></i>NÃO" data-onstyle="success" data-offstyle="danger" />
                                                        <input type="hidden" name="contrato" id="radio_1" value="<%=cliInc.getTemContrato()%>" />
                                                    </div>
                                                </div>
                                            </div>
                                        </li>
                                        <li id="divNum" <%if (cliInc.getTemContrato() == 1) {%> class="list-group-item" <%} else {%> class="esconder" <%}%>>
                                            <div class="row form-horizontal">
                                                <div class="col-sm-6 col-md-4 col-lg-3">
                                                    <label class="small">Número do Contrato</label>
                                                    <div class="input-group">                                                        
                                                        <span class="input-group-addon" ><i class="fa fa-asterisk fa-fw"></i></span>                                                     
                                                        <input class="form-control" type="text" name="numContrato" id="numContrato" value="<%= numContrato%>" maxlength="10" onkeypress="mascara(this, maskNumero)" />
                                                    </div>
                                                </div>
                                                <div class="col-sm-6 col-md-3 col-lg-2">
                                                    <label class="small">Cartão de Postagem</label>
                                                    <div class="input-group">                                                        
                                                        <span class="input-group-addon" ><i class="fa fa-asterisk fa-fw"></i></span>                                                     
                                                        <input class="form-control" type="text" name="cartaoPostagem" id="cartaoPostagem" value="<%= cartaoPostagem%>" maxlength="20" onkeypress="mascara(this, maskNumero)" />
                                                    </div>
                                                </div>
                                                <div class="col-sm-6 col-md-3 col-lg-2">
                                                    <label class="small">Cód. Administrativo</label>
                                                    <div class="input-group">                                                        
                                                        <span class="input-group-addon" ><i class="fa fa-asterisk fa-fw"></i></span>                                                     
                                                        <input class="form-control" type="text" name="codAdm" id="codAdm" value="<%= codAdm%>" maxlength="20" onkeypress="mascara(this, maskNumero)" />
                                                    </div>
                                                </div>
                                            </div>   
                                            <%--</li>
                                            <li id="divNum3" <%if (cliInc.getTemContrato() == 1) {%> class="list-group-item" <%} else {%> class="esconder" <%}%>>--%>
                                            <div class="row form-horizontal">

                                                <div class="col-sm-6 col-md-4 col-lg-3">
                                                    <label class="small">Status do Cartão de Postagem?</label>
                                                    <div class="input-group">                                                        
                                                        <span class="input-group-addon" ><i class="fa fa-info fa-fw"></i></span>                                                                                                             
                                                        <select class="form-control" name="statusCartao" id="statusCartao">
                                                            <option <%if (statusCartao == 1) {%> selected <%}%> value="1">ATIVO</option>
                                                            <option <%if (statusCartao == 0) {%> selected <%}%> value="0">INATIVO/SUSPENSO</option>
                                                        </select>
                                                    </div>
                                                </div>
                                                <div class="col-sm-6 col-md-3 col-lg-2">
                                                    <label class="small">Validade do Contraro</label>
                                                    <div class="input-group">                                                        
                                                        <input class="form-control" type="text" name="vigenciaFim" id="vigenciaFim" value="<%= dtVig%>" maxlength="10" size="10" onkeypress="mascara(this, maskData)" />
                                                        <span class="input-group-addon" ><i class="fa fa-calendar fa-fw"></i></span>  
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="row form-horizontal">
                                                <div class="col-sm-6 col-md-4 col-lg-3">
                                                    <label class="small">Nome para Chancela <span style="color:red;">(18 Carác.)</span></label>
                                                    <div class="input-group">   
                                                        <span class="input-group-addon" ><i class="fa fa-asterisk fa-fw"></i></span>                                                     
                                                        <input class="form-control" type="text" name="nomeContrato" id="nomeContrato" value="<%= nomeContrato%>" maxlength="18" />
                                                    </div>
                                                </div>
                                                <div class="col-sm-6 col-md-3 col-lg-2">
                                                    <label class="small">Ano do Contrato</label>
                                                    <div class="input-group">   
                                                        <span class="input-group-addon" ><i class="fa fa-asterisk fa-fw"></i></span>                                                     
                                                        <input class="form-control" type="text" name="anoContrato" id="anoContrato" value="<%= anoContrato%>" maxlength="4" onkeypress="mascara(this, maskNumero)" />
                                                    </div>
                                                </div>
                                                <div class="col-sm-6 col-md-3 col-lg-2">
                                                    <label class="small">UF do Contrato</label>
                                                    <div class="input-group">   
                                                        <span class="input-group-addon" ><i class="fa fa-asterisk fa-fw"></i></span>                                                     
                                                        <select class="form-control" name="ufContrato" id="ufContrato">
                                                            <option value="" selected>SELECIONE</option>
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
                                                    </div>
                                                </div>
                                            </div>
                                        </li>
                                        <li id="divNum2" <%if (cliInc.getTemContrato() == 1) {%> class="list-group-item list-group-heading" <%} else {%> class="esconder" <%}%>>
                                            <b>SELECIONE OS SERVIÇOS QUE FAZEM PARTE DO CONTRATO DO CLIENTE</b>
                                        </li>
                                        <li id="divServ" style="clear: both;" <%if (cliInc.getTemContrato() == 1) {%> class="list-group-item" <%} else {%> class="esconder" <%}%> >
                                            <ul class="list-inline">
                                                <li>
                                                    <ul class="list-unstyled">
                                                        <li class="list-group-item list-group-item-danger">
                                                            <b>SERVIÇOS ECT FORA DO CONTRATO</b>
                                                        </li>
                                                        <li class="list-group-item list-group-item-danger">
                                                            <select class="form-control" style="width: 300px;" name="servico_1" id="servico_1" size="10">
                                                                <%
                                                                    ArrayList<Integer> listaContrato = ContrClienteContrato.consultaContratoCliente(idClienteInc, nomeBD);
                                                                    ArrayList<ServicoECT> listaServ = ContrServicoECT.consultaServicos(0, 1, "CTR");
                                                                    if (listaServ != null) {
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
                                                        </li>
                                                    </ul>
                                                </li>
                                                <li>  
                                                    <div style="margin: 15px 10px;">
                                                        <button onclick="trocaServ('servico_1', 'servico_2');" type="button" class="btn btn-success" style="width: 135px;" ><i class="fa fa-lg fa-spc fa-plus-circle" ></i> ADICIONAR</button>
                                                        <br/><br/>
                                                        <button onclick="trocaServ('servico_2', 'servico_1');" type="button" class="btn btn-danger" style="width: 135px;" ><i class="fa fa-lg fa-spc fa-minus-circle" ></i> REMOVER</button>
                                                    </div>                                                  
                                                </li>
                                                <li>  
                                                    <ul class="list-unstyled">
                                                        <li class="list-group-item list-group-item-success">
                                                            <label>SERVIÇOS ECT QUE ESTÃO NO CONTRATO</label>
                                                        </li>
                                                        <li class="list-group-item list-group-item-success">                                                            
                                                            <select class="form-control" style="width: 300px;" name="servico_2" id="servico_2" size="10"> 
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
                                                        </li>
                                                    </ul>
                                                </li>
                                            </ul>
                                        </li>
                                        <li id="divNum4" <%if (cliInc.getTemContrato() == 1) {%> class="list-group-item list-group-heading" <%} else {%> class="esconder" <%}%>>
                                            <b>LOGIN E SENHA DO SIGEPWEB DO CLIENTE</b>
                                        </li>
                                        <li id="divNum5" <%if (cliInc.getTemContrato() == 1) {%> class="list-group-item" <%} else {%> class="esconder" <%}%>>
                                            <div class="row form-horizontal">
                                                <div class="col-sm-6 col-md-3 col-lg-3">
                                                    <label class="small">Login do SigepWeb</label>
                                                    <div class="input-group">                                                        
                                                        <span class="input-group-addon" ><i class="fa fa-user fa-fw"></i></span>                                                     
                                                        <input class="form-control" type="text" name="login_sigep" id="login_sigep" value="<%= cliInc.getLogin_sigep()%>" />
                                                    </div>
                                                </div>
                                                <div class="col-sm-6 col-md-3 col-lg-3">
                                                    <label class="small">Senha do SigepWeb</label>
                                                    <div class="input-group">                                                        
                                                        <span class="input-group-addon" ><i class="fa fa-lock fa-fw"></i></span>                                                     
                                                        <input class="form-control" type="text" name="senha_sigep" id="senha_sigep" value="<%= cliInc.getSenha_sigep()%>" />
                                                    </div>
                                                </div>
                                                <div class="col-sm-6 col-md-6 col-lg-6">        
                                                    <%if (cliInc.getLogin_sigep() != null && !cliInc.getLogin_sigep().equals("") && !cliInc.getLogin_sigep().toLowerCase().equals("null")) {%>                                                      
                                                        <label class="small">Clique para testar a senha</label>                                                    
                                                        <div>   
                                                            <button style="width: 300px;" onclick="ajaxTesteSigepWEB();" type="button" class="btn btn-sm btn-warning form-control" ><i class="fa fa-lg fa-spc fa-check-circle"></i> TESTAR FUNCIONAMENTO DO SIGEPWEB</button>                                                        
                                                        </div>
                                                    <%}%>
                                                </div>
                                            </div>   
                                        </li>

                                        <li id="divNum6" <%if (cliInc.getTemContrato() == 1) {%> class="list-group-item list-group-heading" <%} else {%> class="esconder" <%}%>>
                                            <b>LOGIN E SENHA LOGÍSTICA REVERSA DO CLIENTE</b>
                                        </li>
                                        <li id="divNum7" <%if (cliInc.getTemContrato() == 1) {%> class="list-group-item" <%} else {%> class="esconder" <%}%>>
                                            <div class="row form-horizontal">
                                                <div class="col-sm-6 col-md-4 col-lg-3">
                                                    <label class="small">Login da Log. Reversa</label>
                                                    <div class="input-group">                                                        
                                                        <span class="input-group-addon" ><i class="fa fa-user fa-fw"></i></span>                                                     
                                                        <input class="form-control" type="text" name="login_reversa" id="login_reversa" value="<%= cliInc.getLogin_reversa()%>" />
                                                    </div>
                                                </div>
                                                <div class="col-sm-6 col-md-4 col-lg-3">
                                                    <label class="small">Senha da Log. Reversa</label>
                                                    <div class="input-group">                                                        
                                                        <span class="input-group-addon" ><i class="fa fa-lock fa-fw"></i></span>                                                     
                                                        <input class="form-control" type="text" name="senha_reversa" id="senha_reversa" value="<%= cliInc.getSenha_reversa()%>" />
                                                    </div>
                                                </div>
                                                <div class="col-sm-6 col-md-5 col-lg-3">        
                                                        <label class="small">Cartão de Postagem Log. Reversa</label>                                                    
                                                        <div class="input-group">                                                        
                                                        <span class="input-group-addon" ><i class="fa fa-credit-card fa-fw"></i></span>                                                     
                                                        <input class="form-control" type="text" name="cartao_reversa" id="cartao_reversa" value="<%= cliInc.getCartao_reversa()%>" />
                                                    </div>
                                                </div>
                                            </div>   
                                        </li>
                                        <li class="list-group-item">
                                            <input type="hidden" name="codDir" id="codDir" value="<%= codDir%>" value="0" size="10" />
                                            <input type="hidden" name="nomeSara" id="nomeSara" value="<%= nomeSara%>" size="10" />
                                            <input type="hidden" name="cnpjSara" id="cnpjSara" value="<%= cnpjSenha%>" size="10" />       
                                            <input type="hidden" name="idCliente" id="idCliente" value="<%= idClienteInc%>" />
                                            <input type="hidden" name="cnpj" value="<%= cliInc.getCnpj()%>" />
                                            <input type="hidden" name="servicos" id="servicos" value="" />
                                            <button type="button" class="btn btn-success" onclick="return preencherCampos();"><i class="fa fa-lg fa-save fa-spc"></i> SALVAR DADOS</button>
                                        </li>
                                    </ul>
                                </form>

                            </div>
                        </div>
                        <div class="row spacer-xlg"></div>
                    </div>
                </div>
            </div>
        </div>

        <link href="https://gitcdn.github.io/bootstrap-toggle/2.2.0/css/bootstrap-toggle.min.css" rel="stylesheet" />
        <script src="https://gitcdn.github.io/bootstrap-toggle/2.2.0/js/bootstrap-toggle.min.js"></script>
        <script type="text/javascript">
            function ajaxTesteSigepWEB() {
                waitMsg();
                var contrato = document.getElementById('numContrato').value;
                var cartao = document.getElementById('cartaoPostagem').value;
                var loginSigep = document.getElementById('login_sigep').value;
                var senhaSigep = document.getElementById('senha_sigep').value;
                //var idCli = document.getElementById('idCliente').value;
                $.ajax({
                    method: "POST",
                    url: "ajax/pesquisaDadosCliSigep.jsp",
                    data: {numContrato: contrato, cartaoPostagem: cartao, loginSigep: loginSigep, senhaSigep: senhaSigep},
                    dataType: 'html'
                }).done(function(retorno) {
                    var aux = retorno.split(';');
                    if (aux[0] !== 'erro') {
                        if (aux[0] === '1') {
                            document.getElementById('statusCartao').value = '1';
                        } else {
                            document.getElementById('statusCartao').value = '2';
                        }
                        document.getElementById('codDir').value = aux[1];
                        document.getElementById('anoContrato').value = aux[2];
                        document.getElementById('ufContrato').value = aux[3];
                        document.getElementById('nomeSara').value = aux[4];
                        document.getElementById('cnpjSara').value = aux[5];
                        document.getElementById('codAdm').value = aux[6];
                        document.getElementById('vigenciaFim').value = aux[7];
                        fechaMsg();
                        
                        var msg_dialog = "<label>Verificação concluida com sucesso!</label><br/>"
                                +"Cód. Administrativo: " + aux[6]+"<br/>"
                                +"CNPJ do Contrato: " + aux[8]+"<br/>"
                                +"Vigente até: " + aux[7]+"<br/><br/>"
                                + aux[9].split('@').join('<br/>') ;
                        
                        
                        bootbox.dialog({                        
                        title: "<b class='text-success'>SUCESSO!</b>",
                        message: msg_dialog,
                        onEscape: true
                        });
                    } else {
                        fechaMsg();
                        bootbox.dialog({                        
                        title: "<b class='text-danger'>ATENÇÃO!</b>",
                        message: "<label>"+aux[1]+"</label>",
                        onEscape: true
                        });
                    }
                });
            }
            
            function trocaServ(listRemove, listAdiciona) {
                var selIndex = document.getElementById(listRemove).selectedIndex;
                var idServ = document.getElementById(listRemove).options[selIndex].value;
                var nomeServ = document.getElementById(listRemove).options[selIndex].text;

                if (listAdiciona === 'servico_2') {
                    var aux = idServ.split(";");
                    if (verificarGrupoServ(listAdiciona, aux[1])) {
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
                if (document.getElementById(listRemove).length === selIndex) {
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
                    if (aux[1] === grupo) {
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
                if (form.contrato.value === '1') {                    

                    if (document.getElementById('numContrato').value === '' || document.getElementById('numContrato').value.length !== 10) {
                        alert('Digite um Número de Contrato Válido para o Cliente!');
                        return false;
                    }

                    if (document.getElementById('cartaoPostagem').value === '') {
                        alert('Preencha o Cartão de Postagem do Contrato!');
                        return false;
                    }
                    
                    if (document.getElementById('codAdm').value === '') {
                        alert('Preencha o Código Administrativo do Contrtato!');
                        return false;
                    }
                    
                    if (document.getElementById('vigenciaFim').value === '') {
                        alert('Preencha a Validade do Contrtato!');
                        return false;
                    }

                    if (document.getElementById('nomeContrato').value === '') {
                        alert('Digite um Nome do Cliente para a Chancela do Contrato!');
                        return false;
                    }

                    if (document.getElementById('anoContrato').value === '') {
                        alert('Escolha o Ano do Contrato!');
                        return false;
                    }

                    if (document.getElementById('ufContrato').value === '') {
                        alert('Escolha a Unidade Federativa do Contrato!');
                        return false;
                    }

                    var lb = document.getElementById('servico_2');
                    var servicos = '';
                    for (i = 0; i < lb.length; i++) {
                        if (i === 0) {
                            servicos += lb.options[i].value;
                        } else {
                            servicos += "@" + lb.options[i].value;
                        }
                    }
                    document.getElementById('servicos').value = servicos;

                    if (servicos === '') {
                        alert('Selecione algum serviço para o Contrato do Cliente!');
                        return false;
                    }

                }
                waitMsg();
                form.submit();
            }

            function mostraEsconde(){
                $('#divServ').slideToggle();
                $('#divNum').slideToggle();
                $('#divNum2').slideToggle();
                $('#divNum3').slideToggle();
                $('#divNum4').slideToggle();
                $('#divNum5').slideToggle();
                $('#divNum6').slideToggle();
                $('#divNum7').slideToggle();
            }
            
            /************************************/

            $(document).ready(function() {
                $("#vigenciaFim").datepicker({
                    showAnim: 'slideDown',
                    minDate: new Date(),
                    numberOfMonths: 1
                });
                if($('#radioContrato').prop('checked')) {
                    $('#radio_1').val('1');
                } else {
                    mostraEsconde();
                    $('#radio_1').val('0');
                }
                fechaMsg();
            });
            
            $(function() {
                $('#radioContrato').change(function() {
                    if ($(this).prop('checked')) {
                        mostraEsconde();
                        $('#radio_1').val('1');
                    } else {
                        mostraEsconde();
                        $('#radio_1').val('0');
                    }
                });
            });
        </script>
    </body>
</html>
<%}%>