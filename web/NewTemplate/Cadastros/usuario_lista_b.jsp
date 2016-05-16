
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
        <title> Portal Postal</title>
        <%@ include file="../includes/Css_js.jsp" %>
        <link rel="stylesheet" href="../../plugins/multiselect/css/bootstrap-select.min.css" />
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
                        <div class="row">
                            <div class="col-md-12">
                                <h4 class="page-header"><b class="text-primary"><i class="fa fa-gears"></i> Cadastro</b> > <small>Usuários da Agência</small></h4>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-lg-12">
                                <form name="form1" action="../../ServInserirUsuario" method="post">
                                    <ul class="list-unstyled">
                                        <li class="list-group-item list-group-heading">
                                            <label>Inserir novo usuário da agência</label>
                                        </li>
                                        <li class="list-group-item">
                                            <div class="row form-horizontal">
                                                <div class="col-xs-12 col-sm-6 col-md-4 col-lg-4">
                                                    <label class="small">Nome do Usuário</label>
                                                    <div class="input-group">
                                                        <span class="input-group-addon" ><i class="fa fa-user"></i></span>
                                                        <input type="text" autocomplete="off" name="nome" class="form-control" placeholder="Nome do Usuário" />
                                                    </div>
                                                </div>
                                                <div class="col-xs-12 col-sm-6 col-md-4 col-lg-4">
                                                    <label class="small">E-mail do Usuário</label>
                                                    <div class="input-group">
                                                        <span class="input-group-addon" ><i class="fa fa-at"></i></span>
                                                        <input type="text" autocomplete="off" name="email" class="form-control" placeholder="E-mail do Usuário" />
                                                    </div>
                                                </div>
                                                <div class="col-xs-12 col-sm-6 col-md-4 col-lg-4">
                                                    <label class="small">Nível do Usuário</label>
                                                    <div class="input-group">
                                                        <span class="input-group-addon" ><i class="fa fa-signal"></i></span>
                                                        <select class="form-control" name="nivel">
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
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="row form-horizontal">
                                                <div class="col-xs-12 col-sm-6 col-md-4 col-lg-4">
                                                    <label class="small">Login do Usuário</label>
                                                    <div class="input-group">
                                                        <span class="input-group-addon" ><i class="fa fa-user"></i></span>
                                                        <input type="text" autocomplete="off" name="login" class="form-control" placeholder="Login do Usuário" onkeyup="ajaxConfereLogin(this.value, 'foo');" />                                    
                                                    </div>
                                                    <div id="foo"></div>
                                                </div>
                                                <div class="col-xs-12 col-sm-6 col-md-4 col-lg-4">
                                                    <label class="small">Senha do Usuário</label>
                                                    <div class="input-group">
                                                        <span class="input-group-addon" ><i class="fa fa-lock"></i></span>
                                                        <input type="password" autocomplete="off" name="senha" class="form-control" placeholder="Senha do Usuário" />
                                                    </div>
                                                </div>
                                                <div class="col-xs-12 col-sm-6 col-md-4 col-lg-4">
                                                    <label class="small">Repita a Senha</label>
                                                    <div class="input-group">
                                                        <span class="input-group-addon" ><i class="fa fa-lock"></i></span>
                                                        <input type="password" autocomplete="off" name="senha2" class="form-control" placeholder="Repita a Senha" />
                                                    </div>
                                                </div>
                                            </div>
                                        </li>
                                        <li class="list-group-item list-group-heading">
                                            <b>Usa Portal Postal? </b> 
                                            <input type="checkbox" name="usaPortalPostal" id="usaPortalPostal" value="1" data-size="mini" data-toggle="toggle" data-on="SIM" data-off="NÃO" data-onstyle="success" data-offstyle="danger" />
                                        </li>
                                        <li id="liPortalPostal" class="list-group-item" style="display: none;">
                                            <div class="row form-horizontal">
                                                <%--
                                                <div class="col-xs-12 col-sm-6 col-md-4 col-lg-3">
                                                    <label>
                                                        ABA GERENCIAR ETIQUETAS:<br/>
                                                        <a style="color:blue;font-size: 8px;" onclick="selectAllCombo(document.getElementById('geretq'), true);">MARCAR TUDO</a><br/>
                                                        <a style="color:red;font-size: 8px;" onclick="selectAllCombo(document.getElementById('geretq'), false);">DESMARCAR TUDO</a><br/>
                                                    </label>
                                                    <select class="form-control text-10px no-padding" name=geretq id=geretq multiple onclick="controleCombobox0(this)" size=10 >
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
                                                </div>
                                                <div class="col-xs-12 col-sm-6 col-md-4 col-lg-3">
                                                    <label>
                                                        ABA COLETA:<br/>
                                                        <a style="color:blue;font-size: 8px;" onclick="selectAllCombo(document.getElementById('coleta'), true);">MARCAR TUDO</a><br/>
                                                        <a style="color:red;font-size: 8px;" onclick="selectAllCombo(document.getElementById('coleta'), false);">DESMARCAR TUDO</a><br/>
                                                    </label>
                                                    <select class="form-control text-10px no-padding" name=coleta id=coleta multiple onclick="controleCombobox1(this)" size=10 >
                                                        <option value="201" >ACOMPANHAMENTO</option>
                                                        <option value="206" >GERENCIAR ROTAS</option>
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
                                                </div>
                                                <div class="col-xs-12 col-sm-6 col-md-4 col-lg-3">
                                                    <label>
                                                        ABA IMPORTAÇÕES:<br/>
                                                        <a style="color:blue;font-size: 8px;" onclick="selectAllCombo(document.getElementById('importacao'), true);">MARCAR TUDO</a><br/>
                                                        <a style="color:red;font-size: 8px;" onclick="selectAllCombo(document.getElementById('importacao'), false);">DESMARCAR TUDO</a><br/></label>
                                                    <select class="form-control text-10px no-padding" name="importacao" id="importacao" multiple onclick="controleCombobox2(this)" size="10" >
                                                        <option value="302" >ARQUIVO DE RETORNO DE AR</option>
                                                        <option value="301" >ARQUIVO DE MOVIMENTAÇAO</option>
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
                                                </div>
                                                <div class="col-xs-12 col-sm-6 col-md-4 col-lg-3">
                                                    <label>
                                                        ABA CADASTROS:<br/>
                                                        <a style="color:blue;font-size: 8px;" onclick="selectAllCombo(document.getElementById('cadastro'), true);">MARCAR TUDO</a><br/>
                                                        <a style="color:red;font-size: 8px;" onclick="selectAllCombo(document.getElementById('cadastro'), false);">DESMARCAR TUDO</a><br/></label>
                                                    <select class="form-control text-10px no-padding" name="cadastro" id="cadastro" multiple onclick="controleCombobox3(this)" size="10" >
                                                        <option value="401" >PREFIXO DE ETIQUETAS</option>
                                                        <option value="402" >ABRANGÊNCIA DE SERVIÇOS</option>
                                                        <option value="403" >AMARRAÇÕES</option>
                                                        <option value="404" >USUÁRIOS DA AGÊNCIA</option>
                                                        <option value="405" >CLIENTES</option>
                                                        <option value="407" >GRUPO DE FATURAMENTO</option>
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
                                                </div>
                                                <div class="col-xs-12 col-sm-6 col-md-4 col-lg-3">
                                                    <label>
                                                        ABA VEICULOS:<br/>
                                                        <a style="color:blue;font-size: 8px;" onclick="selectAllCombo(document.getElementById('veiculo'), true);">MARCAR TUDO</a><br/>
                                                        <a style="color:red;font-size: 8px;" onclick="selectAllCombo(document.getElementById('veiculo'), false);">DESMARCAR TUDO</a><br/></label>
                                                    <select class="form-control text-10px no-padding" name="veiculo" id="veiculo" multiple onclick="controleComboboxPP4(this)" size="10" >
                                                        <option value="501" >CADASTRO DE VEICULO</option>
                                                        <option value="502" >MANUTENÇAO</option>
                                                        <option value="503" >COMBUSTIVEL</option>
                                                        <option value="504" >MULTA</option>
                                                        <option value="505" >SEGURO</option>
                                                        <option value="506" >SINISTRO</option>
                                                    </select>
                                                    <script language="">
                                                        function controleComboboxPP4(combo) {
                                                            combo_aux_pp4[combo.selectedIndex] = !combo_aux_pp4[combo.selectedIndex];
                                                            for (i = 0; i < combo.length; i++) {
                                                                combo.options[i].selected = combo_aux_pp4[i];
                                                            }
                                                        }
                                                        var combo_aux_pp4 = new Array(document.getElementById("veiculo").options.length);
                                                        for (i = 0; i < document.getElementById("veiculo").options.length; i++) {
                                                            combo_aux_pp4[i] = document.getElementById("veiculo").options[i].selected;
                                                        }

                                                    </script>
                                                </div>
                                                --%>
                                                <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6">
                                                    <select style="margin-left: 15px;" name="acessos_pp"  id="acessos_pp" data-width="auto" class="selectpicker show-tick" multiple title="Selecione os acessos permitidos..." multiple data-actions-box="true" data-selected-text-format="static" ><%--onclick="controleComboboxPP5(this)"--%>
                                                        <optgroup label="TELEGRAMAS">
                                                            <option value="106" >TELEGRAMAS</option>                                                            
                                                        </optgroup>
                                                        <optgroup label="GERENCIAR ETIQUETAS">
                                                            <option value="101" >ETIQUETAS RESTANTES</option>
                                                            <option value="102" >SEQUÊNCIAS GERADAS</option>
                                                            <option value="103" >PESQUISAR ETIQUETA</option>
                                                            <option value="104" >ETIQUETAS PENDENTES</option>
                                                            <option value="105" >ETIQUETAS INUTILIZADAS</option>
                                                        </optgroup>
                                                        <optgroup label="COLETA">
                                                            <option value="201" >ACOMPANHAMENTO</option>
                                                            <option value="206" >GERENCIAR ROTAS</option>
                                                            <option value="202" >NOVA COLETA</option>
                                                            <option value="203" >COLETADORES</option>
                                                            <option value="204" >TIPO DE COLETA</option>
                                                            <option value="205" >HORÁRIOS DA COLETA</option>
                                                        </optgroup>
                                                        <optgroup label="IMPORTAÇOES">
                                                            <option value="302" >ARQUIVO DE RETORNO DE AR</option>
                                                            <option value="301" >ARQUIVO DE MOVIMENTAÇAO</option>
                                                            <option value="303" >ARQUIVO DE CLIENTES</option>
                                                            <option value="304" >ARQUIVO DE DEPARTAMENTOS</option>
                                                        </optgroup>
                                                        <optgroup label="CADASTROS">
                                                            <option value="401" >PREFIXO DE ETIQUETAS</option>
                                                            <option value="402" >ABRANGÊNCIA DE SERVIÇOS</option>
                                                            <option value="403" >AMARRAÇÕES</option>
                                                            <option value="404" >USUÁRIOS DA AGÊNCIA</option>
                                                            <option value="405" >CLIENTES</option>
                                                            <option value="407" >GRUPO DE FATURAMENTO</option>
                                                            <option value="406" >VERIFICAÇÃO DE CONTRATOS</option>
                                                        </optgroup>
                                                        <optgroup label="VEICULOS">
                                                            <option value="501" >CADASTRO DE VEICULO</option>
                                                            <option value="502" >MANUTENÇAO</option>
                                                            <option value="503" >COMBUSTIVEL</option>
                                                            <option value="504" >MULTA</option>
                                                            <option value="505" >SEGURO</option>
                                                            <option value="506" >SINISTRO</option>
                                                        </optgroup>
                                                        <optgroup label="FINANCEIRO">
                                                            <option value="601" >INICIO</option>
                                                            <option value="602" >LANÇAMENTOS</option>
                                                            <option value="603" >PROGRAMAÇAO</option>
                                                            <option value="604" >DEMONSTRATIVO</option>
                                                            <option value="605" >CADASTROS</option>
                                                        </optgroup>
                                                    </select>
                                                </div>
                                            </div>
                                        </li>
                                        <li class="list-group-item list-group-heading">
                                            <b>Usa Consolidador? </b>
                                            <input type="checkbox" name="usaConsolidador" id="usaConsolidador" value="1" data-size="mini" data-toggle="toggle" data-on="SIM" data-off="NÃO" data-onstyle="success" data-offstyle="danger" />
                                        </li>
                                        <li id="liConsolidador" class="list-group-item" style="display: none;">
                                            <div class="row form-horizontal">
                                                <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6">
                                                    <select style="margin-left: 15px;" name="acessos_cons"  id="acessos_cons" data-width="auto" class="selectpicker show-tick" multiple title="Selecione os acessos permitidos..." multiple data-actions-box="true" data-selected-text-format="static" ><%--onclick="controleComboboxPP5(this)"--%>
                                                        <optgroup label="CONFIGURAÇOES">
                                                            <option value="101" >CADASTRO DE SEQUENCIAS LOGICAS</option>
                                                            <option value="102" >CADASTRO DE SUB-CAIXAS DO SARA</option>
                                                            <option value="103" >CONFIGURAÇÕES GERAIS</option>
                                                            <option value="104" >CONFIGURAR IMPRESSORA</option>
                                                            <option value="105" >CONFIGURAR BALANÇA</option>
                                                            <option value="106" >CONFIGURAR E-MAIL</option>
                                                        </optgroup>
                                                        <optgroup label="IMPORTAÇOES">
                                                            <option value="1001" >IMPORTAR ARQUIVO DE POSTAGEM</option>
                                                            <option value="1002" >CADASTROS DE LAYOUT DE IMPORTAÇAO</option>
                                                        </optgroup>
                                                        <optgroup label="FERRAMENTAS">
                                                            <option value="201" >PROCESSAR OBJETOS COM A BOXCUBO</option>
                                                            <option value="202" >BIPAR OBJETO DE PRE-POSTAGEM</option>
                                                            <option value="203" >VENDA LOCAL - SINTETICA</option>
                                                            <option value="204" >VENDA LOCAL - COMPLETA</option>
                                                            <option value="205" >VENDA LOCAL - POR CHAVE DE CLIENTE</option>
                                                            <option value="206" >VENDA LOCAL - OBJETOS SEM REGISTRO</option>
                                                            <option value="207" >IMPORTAÇAO DE TICKET DO SARA</option>
                                                            <option value="208" >BIPAR OBJETOS DE PLP DE TERCEIRO</option>
                                                            <option value="209" >CONFERIR PLP DE TERCEIRO</option>
                                                            <option value="210" >ENTREGA INTERNA DE OBJETOS</option>
                                                            <option value="211" >ATUALIZAR SISTEMA MANUALMENTE</option>
                                                        </optgroup>
                                                        <optgroup label="EXPORTAÇOES">
                                                            <option value="301" >EXPORTAR VENDAS PARA O SARA</option>
                                                            <option value="302" >EXPORTAR LOTES PARA VENDA EM M.F.</option>
                                                            <option value="303" >EXPORTAR MOVIMENTO PARA PORTAL POSTAL WEB</option>
                                                            <option value="304" >EXPORTAÇOES DE M.F. REALIZADAS</option>
                                                            <option value="305" >EXPORTAÇOES SARA REALIZADAS</option>
                                                        </optgroup>
                                                        <optgroup label="EXPEDIÇAO">
                                                            <option value="401" >FAZER EXPEDIÇAO</option>
                                                            <option value="402" >OBJETOS NAO EXPEDIDOS</option>
                                                            <option value="403" >IMPRIMIR/EXPORTAR MALAS</option>
                                                            <option value="404" >SEPARAÇAO AUTOMATICA DE MALAS</option>
                                                        </optgroup>
                                                        <optgroup label="PESQUISAS">
                                                            <option value="501" >PESQUISAR OBJETO POR SRO</option>
                                                            <option value="502" >PESQUISAR OBJETOS COMPLETA</option>
                                                            <option value="503" >PESQUISAR O.S./LISTA DE POSTAGEM</option>
                                                            <option value="504" >PESQUISAR VENDAS LOCAIS</option>
                                                            <option value="505" >PESQUISAR CEP</option>
                                                            <option value="506" >PESQUISAR CLIENTES</option>
                                                        </optgroup>
                                                        <optgroup label="RELATORIOS">
                                                            <option value="601" >ATENDIMENTOS DO SARA</option>
                                                            <option value="602" >ETIQUETAS ALTERADAS</option>
                                                            <option value="603" >OBJETOS SEM PRE-POSTAGEM</option>
                                                            <option value="604" >CURVA ABC (RANKING)</option>
                                                            <option value="605" >RELATORIO DE OBJETOS PROCESSADOS</option>
                                                            <option value="606" >RELATORIO DE MOVIMENTO</option>
                                                            <option value="607" >AUDITORIA DE CAIXAS</option>
                                                        </optgroup>
                                                        <optgroup label="FATURAS">
                                                            <option value="701" >GERAR FATURAS</option>
                                                            <option value="702" >CONSULTAR FATURAS</option>
                                                            <option value="703" >RECEBER PAGAMENTO DE FATURA</option>
                                                            <option value="704" >OUTROS LANÇAMENTOS</option>
                                                            <option value="705" >POSIÇAO CONSOLIDADA DAS FATURAS</option>
                                                        </optgroup>
                                                        <optgroup label="BDF">
                                                            <option value="801" >IMPORTAR DADOS DO BDF</option>
                                                            <option value="802" >PRE-ALERTA BDF</option>
                                                            <option value="803" >DEFINIR CLIENTE PARA ATENDIMENTO SARA</option>
                                                        </optgroup>
                                                        <optgroup label="UTILITARIOS">
                                                            <option value="901" >CALCULAR DIGITO VERIFICADOR</option>
                                                            <option value="902" >MARCAR TODOS OBJETOS COMO EXPEDIDOS</option>
                                                            <option value="903" >IMPRIMIR CHANCELAS</option>
                                                            <option value="904" >IMPRIMIR SEQUÊNCIAS LOGICAS DE CLIENTE</option>
                                                            <option value="905" >ATUALIZAR IP DA BOXCUBO</option>
                                                            <option value="906" >ATUALIZAR BANCO DE CEPS E TARIFAS</option>
                                                            <option value="907" >FAZER BACKUP E ORGANIZAR BANCO DE DADOS</option>
                                                        </optgroup>
                                                    </select>
                                                </div>
                                                
                                                
                                                <%--
                                            <div class="col-xs-12 col-sm-6 col-md-4 col-lg-3">
                                                <label>
                                                    ABA CONFIGURAÇÕES:<br/>
                                                    <a style="color:blue;font-size: 8px;" onclick="selectAllCombo(document.getElementById('con_conf'), true);">MARCAR TUDO</a><br/>
                                                    <a style="color:red;font-size: 8px;" onclick="selectAllCombo(document.getElementById('con_conf'), false);">DESMARCAR TUDO</a><br/>
                                                </label>
                                                <select class="form-control text-10px no-padding" name=con_conf id=con_conf multiple onclick="controleCombobox4(this)" size=10 >
                                                    <option value="101" >CADASTRO DE SEQUENCIAS LOGICAS</option>
                                                    <option value="102" >CADASTRO DE SUB-CAIXAS DO SARA</option>
                                                    <option value="103" >CONFIGURAÇÕES GERAIS</option>
                                                    <option value="104" >CONFIGURAR IMPRESSORA</option>
                                                    <option value="105" >CONFIGURAR BALANÇA</option>
                                                    <option value="106" >CONFIGURAR E-MAIL</option>
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
                                            </div>
                                            <div class="col-xs-12 col-sm-6 col-md-4 col-lg-3">
                                                <label>
                                                    ABA FERRAMENTAS:<br/>
                                                    <a style="color:blue;font-size: 8px;" onclick="selectAllCombo(document.getElementById('con_ferramenta'), true);">MARCAR TUDO</a><br/>
                                                    <a style="color:red;font-size: 8px;" onclick="selectAllCombo(document.getElementById('con_ferramenta'), false);">DESMARCAR TUDO</a><br/>
                                                </label>
                                                <select class="form-control text-10px no-padding" name=con_ferramenta id=con_ferramenta multiple onclick="controleCombobox5(this)" size=10 >
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
                                            </div>
                                            <div class="col-xs-12 col-sm-6 col-md-4 col-lg-3">
                                                <label>
                                                    ABA EXPORTAÇÕES:<br/>
                                                    <a style="color:blue;font-size: 8px;" onclick="selectAllCombo(document.getElementById('con_exportacao'), true);">MARCAR TUDO</a><br/>
                                                    <a style="color:red;font-size: 8px;" onclick="selectAllCombo(document.getElementById('con_exportacao'), false);">DESMARCAR TUDO</a><br/></label>
                                                <select class="form-control text-10px no-padding" name="con_exportacao" id="con_exportacao" multiple onclick="controleCombobox6(this)" size="10" >
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
                                            </div>
                                            <div class="col-xs-12 col-sm-6 col-md-4 col-lg-3">
                                                <label>
                                                    ABA EXPEDIÇÃO:<br/>
                                                    <a style="color:blue;font-size: 8px;" onclick="selectAllCombo(document.getElementById('con_exped'), true);">MARCAR TUDO</a><br/>
                                                    <a style="color:red;font-size: 8px;" onclick="selectAllCombo(document.getElementById('con_exped'), false);">DESMARCAR TUDO</a><br/></label>
                                                <select class="form-control text-10px no-padding" name="con_exped" id="con_exped" multiple onclick="controleCombobox7(this)" size="10" >
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
                                            </div>
                                            <div class="col-xs-12 col-sm-6 col-md-4 col-lg-3">
                                                <label>
                                                    ABA PESQUISAS:<br/>
                                                    <a style="color:blue;font-size: 8px;" onclick="selectAllCombo(document.getElementById('con_pesq'), true);">MARCAR TUDO</a><br/>
                                                    <a style="color:red;font-size: 8px;" onclick="selectAllCombo(document.getElementById('con_pesq'), false);">DESMARCAR TUDO</a><br/></label>
                                                <select class="form-control text-10px no-padding" name="con_pesq" id="con_pesq" multiple onclick="controleCombobox8(this)" size="10" >
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
                                            </div>
                                            <div class="col-xs-12 col-sm-6 col-md-4 col-lg-3">
                                                <label>
                                                    ABA RELATÓRIOS:<br/>
                                                    <a style="color:blue;font-size: 8px;" onclick="selectAllCombo(document.getElementById('con_relat'), true);">MARCAR TUDO</a><br/>
                                                    <a style="color:red;font-size: 8px;" onclick="selectAllCombo(document.getElementById('con_relat'), false);">DESMARCAR TUDO</a><br/></label>
                                                <select class="form-control text-10px no-padding" name="con_relat" id="con_relat" multiple onclick="controleCombobox9(this)" size="10" >
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
                                            </div>
                                            <div class="col-xs-12 col-sm-6 col-md-4 col-lg-3">
                                                <label>
                                                    ABA UTILITÁRIOS:<br/>
                                                    <a style="color:blue;font-size: 8px;" onclick="selectAllCombo(document.getElementById('con_util'), true);">MARCAR TUDO</a><br/>
                                                    <a style="color:red;font-size: 8px;" onclick="selectAllCombo(document.getElementById('con_util'), false);">DESMARCAR TUDO</a><br/></label>
                                                <select class="form-control text-10px no-padding" name="con_util" id="con_util" multiple onclick="controleCombobox10(this)" size="10" >
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
                                            </div>--%>
                                            </div>
                                        </li>
                                        <li class="list-group-item">
                                            <dd style="width: 100%;">
                                                <div class="buttons">
                                                    <button type="button" class="btn btn-success" onclick="return preencherCampos();"><i class="fa fa-lg fa-spc fa-save"></i> SALVAR DADOS</button>
                                                </div>
                                            </dd>
                                        </li>
                                    </ul>
                                </form>
                                <div class="panel panel-default">
                                    <div class="panel-heading" >Lista com todos os usuários da agência</div>
                                    <div class="panel-body no-padding">
                                        <div class="dataTable_wrapper no-padding">
                                            <table class="table table-striped table-bordered table-hover table-condensed" id="dataTables-example">
                                                <thead>
                                                    <tr>
                                                        <th width="80">Nº</th>
                                                        <th>Nome</th>
                                                        <th>E-mail</th>
                                                        <th>Nivel</th>
                                                        <th class="no-sort" width="100">Alterar</th>
                                                        <th class="no-sort" width="100">Excluir</th>
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
                                                        <td align="right"><%= idUsuario%></td>
                                                        <td><%= nomeUs%></td>
                                                        <td><%= email%></td>
                                                        <td><%= nomeNivel + " - " + nivelUs%></td>
                                                        <td align="center"><button class="btn btn-sm btn-warning" onclick="ajaxEditarUsuario(<%= idUsuario%>);"><i class="fa fa-lg fa-pencil"></i></button></td>
                                                        <td align="center">
                                                            <form action="../../ServExcluirUsuario" method="post" name="formDel">
                                                                <input type="hidden" name="idUsuario" value="<%= idUsuario%>" />
                                                                <button type="button" class="btn btn-sm btn-danger" onclick="confirmExcluir(this);" ><i class="fa fa-lg fa-trash"></i></button>
                                                            </form>
                                                        </td>
                                                    </tr>
                                                    <%}%>
                                                </tbody>
                                            </table>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>



        <link href="https://gitcdn.github.io/bootstrap-toggle/2.2.0/css/bootstrap-toggle.min.css" rel="stylesheet" />
        <script src="https://gitcdn.github.io/bootstrap-toggle/2.2.0/js/bootstrap-toggle.min.js"></script>
        
        <script src="../../plugins/multiselect/js/bootstrap-select.js"></script>
        <script>
            /*$(function() {
                $('#financeiro').change(function() {
                    console.log($(this).val());
                }).multipleSelect({
                    width: '100%'
                });
            });*/
        </script>
        <script type="text/javascript">
            function ajaxConfereLogin(login, divRetorno) {
                $.ajax({
                    method: "POST",
                    url: "ajax/confereLoginUsuario.jsp",
                    data: {login: login},
                    dataType: 'html'
                }).done(function(data) {
                    document.getElementById(divRetorno).innerHTML = data;
                });
            }

            function populateVar() {
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

            function preencherCampos() {
                var form = document.form1;
                if (form.nome.value === "") {
                    alert('Preencha o nome do usuário!');
                    return false;
                }
                if (form.nivel.value === "") {
                    alert('Preencha o nivel do usuário!');
                    return false;
                }
                if (form.login.value === "") {
                    alert('Insira um login para o usuário!');
                    return false;
                }
                if (document.getElementById('foo').innerHTML.indexOf('w') === -1) {
                    alert('O login digitado é inválido ou já existente!\n Favor escolha outro!');
                    return false;
                }
                if (form.senha.value === "") {
                    alert('Insira uma senha para o usuário!');
                    return false;
                }
                if (form.senha.value !== form.senha2.value) {
                    alert('As senhas digitadas não conferem!');
                    return false;
                }

                form.submit();
            }

            function preencherCamposEdit() {
                var form = document.form5;
                if (form.nome.value === '') {
                    alert('Preencha o nome do usuário!');
                    return false;
                }
                if (form.nivel.value === '') {
                    alert('Preencha o nivel do usuário!');
                    return false;
                }
                if (form.login.value === '') {
                    alert('Insira um login para o usuário!');
                    return false;
                }
                if (form.login.value !== form.loginaux.value) {
                    if (document.getElementById('fooEditar').innerHTML.indexOf('w') === -1) {
                        alert('O login digitado é inválido ou já existente!\n\nFavor escolha outro!');
                        return false;
                    }
                }
                if (form.senha.value !== '') {
                    if (form.senha.value !== form.senha2.value) {
                        alert('As senhas digitadas não conferem!');
                        return false;
                    }
                }
                form.submit();
            }


            /************************************/
                        
            function ajaxEditarUsuario(idUsuario) {
                $.ajax({
                    method: "POST",
                    url: "ajax/usuario_editar_dialog.jsp",
                    data: {idUsuario: idUsuario},
                    dataType: 'html'
                }).done(function(retorno) {
                    editarUsuario(retorno);
                });
            }
            
            function editarUsuario(retorno) {
                bootbox.dialog({
                    title: "Editar Usuário da Agência",
                    message: retorno,
                    animate: true,
                    onEscape: true,
                    className: "modal-lgWidth",
                    buttons: {
                        "Cancelar": {
                            label:"<i class='fa fa-lg fa-times fa-spc'></i> CANCELAR",
                            className: "btn btn-default",
                            callback: function() {
                                bootbox.hideAll();
                            }
                        },
                        success: {
                            label: "<i class='fa fa-lg fa-save fa-spc'></i> SALVAR",
                            className: "btn btn-success",
                            callback: function() {
                                preencherCamposEdit();
                            }
                        }
                    }
                });
                return false;
            }
            
            
            function confirmExcluir(button) {                
                bootbox.confirm({
                    title: 'Excluir Usuário da Agência?',
                    message: 'Deseja realmente excluir este usuário da agência?',
                    buttons: {
                        'cancel': {
                            label: '<i class="fa fa-lg fa-times fa-spc"></i> CANCELAR',
                            className: 'btn btn-default pull-left'
                        },
                        'confirm': {
                            label: '<i class="fa fa-lg fa-trash fa-spc"></i> EXCLUIR',
                            className: 'btn btn-danger pull-right'
                        }
                    },
                    callback: function(result) {
                        if(result){
                            button.form.submit();
                        }
                    }
                });
            }

            function AllTables() {
                StartDataTable('dataTables-example');
                LoadSelect2Script(MakeSelectDataTable('dataTables-example'));
                fechaMsg();
            }

            $(document).ready(function() {
                LoadDataTablesScripts(AllTables);
            });
            
            
            $(function() {
                $('#usaPortalPostal').change(function() {
                    $('#liPortalPostal').slideToggle();
                    if ($(this).prop('checked')) {
                        //$('#radio_1').val('1');
                    } else {
                        //$('#radio_1').val('0');
                    }
                });
            });
            
            $(function() {
                $('#usaConsolidador').change(function() {
                    $('#liConsolidador').slideToggle();
                    if ($(this).prop('checked')) {
                        //$('#radio_1').val('1');
                    } else {
                        //$('#radio_1').val('0');
                    }
                });
            });



        </script>
    </body>
</html>
<%}%>