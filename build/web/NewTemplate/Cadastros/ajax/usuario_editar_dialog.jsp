<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import = "java.util.ArrayList,java.sql.Timestamp, java.util.Date, java.text.SimpleDateFormat"%>
<%
    /*AJAX*/
    response.setContentType("text/xml");
    response.setHeader("Cache-Control", "no-cache");
    String resultado = "sessaoexpirada";

    String nomeBD = (String) session.getAttribute("empresa");
    if (nomeBD != null) {

        int idUsuario = Integer.parseInt(request.getParameter("idUsuario"));
        Entidade.Usuario col = Controle.contrUsuario.consultaUsuarioById(idUsuario, nomeBD);
        String nome = col.getNome();
        String login = col.getLogin();
        String senha = col.getSenha();
        String email = col.getEmail();
        int nivelUs = col.getIdNivel();
        int usaPortal = col.getUsaPortalPostal();
        int usaConsolidador = col.getUsaConsolidador();
        ArrayList<Integer> listaAcPortal = col.getListaAcessosPortalPostal();
        ArrayList<Integer> listaAcConsol = col.getListaAcessosConsolidador();

%>
<form name='form5' action='../../ServEditarUsuario' method='post'>
    <ul class="list-unstyled">
        <li class="list-group-item">
            <div class="row form-horizontal">
                <div class="col-xs-12 col-sm-6 col-md-4 col-lg-4">
                    <label class="small">Nome do Usuário</label>
                    <div class="input-group">
                        <span class="input-group-addon" ><i class="fa fa-user"></i></span>
                        <input type="text" autocomplete="off" name="nome" value='<%= nome%>' class="form-control" placeholder="Nome do Usuário" />
                    </div>
                </div>
                <div class="col-xs-12 col-sm-6 col-md-4 col-lg-4">
                    <label class="small">E-mail do Usuário</label>
                    <div class="input-group">
                        <span class="input-group-addon" ><i class="fa fa-at"></i></span>
                        <input type="text" autocomplete="off" name="email" value='<%= email%>' class="form-control" placeholder="E-mail do Usuário" />
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

                                    String strSelected = "";
                                    if (idNivel == nivelUs) {
                                        strSelected = "selected";
                                    }
                            %>
                            <option value='<%= idNivel%>' <%= strSelected%> ><%= nomeNivel%></option>
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
                        <input type="text" autocomplete="off" name="login" class="form-control" placeholder="Login do Usuário"  value='<%= login%>' onkeyup="ajaxConfereLogin(this.value, 'fooEditar');" />
                    </div>
                    <div id='fooEditar'></div>
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
            <%--<input type="checkbox" name="usaPortalPostal" <%if (usaPortal == 1) {%>checked="true"<%}%> onclick="if (this.checked) {
                        $('#liPortalPostalEdit').show();
                    } else {
                        $('#liPortalPostalEdit').hide();
                    }" value="1" /> --%>
            <b>Usa Portal Postal? </b>
            <input type="checkbox" name="usaPortalPostal" id="usaPortalPostalEdit" value="1" <%if (usaPortal == 1) {%>checked="true"<%}%> data-size="mini" data-toggle="toggle" data-on="SIM" data-off="NÃO" data-onstyle="success" data-offstyle="danger" />
        </li>
        <li class="list-group-item" id="liPortalPostalEdit" <%if (usaPortal == 0) {%> style="display:none;" <%}%>>
            <div class="row form-horizontal">
                <div class="col-xs-12 col-sm-6 col-md-4 col-lg-3">
                    <label>
                        ABA GERENCIAR ETIQUETAS:<br/>
                        <a style="color:blue;font-size: 8px;" onclick="selectAllCombo(document.getElementById('geretq_e'), true);">MARCAR TUDO</a><br/>
                        <a style="color:red;font-size: 8px;" onclick="selectAllCombo(document.getElementById('geretq_e'), false);">DESMARCAR TUDO</a><br/>
                    </label>
                    <select class="form-control text-10px no-padding" name="geretq_e" id="geretq_e" multiple="true" onclick="controleCombobox0(this);" size="10" >
                        <option value="101" <%if (listaAcPortal.contains(101)) {%>selected<%}%> >ETIQUETAS RESTANTES</option>
                        <option value="102" <%if (listaAcPortal.contains(102)) {%>selected<%}%> >SEQUÊNCIAS GERADAS</option>
                        <option value="103" <%if (listaAcPortal.contains(103)) {%>selected<%}%> >PESQUISAR ETIQUETA</option>
                        <option value="104" <%if (listaAcPortal.contains(104)) {%>selected<%}%> >ETIQUETAS PENDENTES</option>
                        <option value="105" <%if (listaAcPortal.contains(105)) {%>selected<%}%> >ETIQUETAS INUTILIZADAS</option>
                    </select>      
                </div>
                <div class="col-xs-12 col-sm-6 col-md-4 col-lg-3">
                    <label>
                        ABA COLETA:<br/>
                        <a style="color:blue;font-size: 8px;" onclick="selectAllCombo(document.getElementById('coleta_e'), true);">MARCAR TUDO</a><br/>
                        <a style="color:red;font-size: 8px;" onclick="selectAllCombo(document.getElementById('coleta_e'), false);">DESMARCAR TUDO</a><br/>
                    </label>
                    <select class="form-control text-10px no-padding" name=coleta_e id=coleta_e multiple onclick="controleCombobox1(this)" size=10 >
                        <option value="201" <%if (listaAcPortal.contains(201)) {%>selected<%}%> >ACOMPANHAMENTO</option>
                        <option value="202" <%if (listaAcPortal.contains(202)) {%>selected<%}%> >NOVA COLETA</option>
                        <option value="203" <%if (listaAcPortal.contains(203)) {%>selected<%}%> >COLETADORES</option>
                        <option value="204" <%if (listaAcPortal.contains(204)) {%>selected<%}%> >TIPO DE COLETA</option>
                        <option value="205" <%if (listaAcPortal.contains(205)) {%>selected<%}%> >HORÁRIOS DA COLETA</option>
                    </select>
                </div>
                <div class="col-xs-12 col-sm-6 col-md-4 col-lg-3">
                    <label>
                        ABA IMPORTAÇÕES:<br/>
                        <a style="color:blue;font-size: 8px;" onclick="selectAllCombo(document.getElementById('importacao_e'), true);">MARCAR TUDO</a><br/>
                        <a style="color:red;font-size: 8px;" onclick="selectAllCombo(document.getElementById('importacao_e'), false);">DESMARCAR TUDO</a><br/></label>
                    <select class="form-control text-10px no-padding" name="importacao_e" id="importacao_e" multiple onclick="controleCombobox2(this)" size="10" >
                        <option value="301" <%if (listaAcPortal.contains(301)) {%>selected<%}%> >ARQUIVO DE MOVIMENTO</option>
                        <option value="302" <%if (listaAcPortal.contains(302)) {%>selected<%}%> >ARQUIVO DE AR's</option>
                        <option value="303" <%if (listaAcPortal.contains(303)) {%>selected<%}%> >ARQUIVO DE CLIENTES</option>
                        <option value="304" <%if (listaAcPortal.contains(304)) {%>selected<%}%> >ARQUIVO DE DEPARTAMENTOS</option>
                    </select>
                </div>
                <div class="col-xs-12 col-sm-6 col-md-4 col-lg-3">
                    <label>
                        ABA CADASTROS:<br/>
                        <a style="color:blue;font-size: 8px;" onclick="selectAllCombo(document.getElementById('cadastro_e'), true);">MARCAR TUDO</a><br/>
                        <a style="color:red;font-size: 8px;" onclick="selectAllCombo(document.getElementById('cadastro_e'), false);">DESMARCAR TUDO</a><br/></label>
                    <select class="form-control text-10px no-padding" name="cadastro_e" id="cadastro_e" multiple onclick="controleCombobox3(this)" size="10" >
                        <option value="401" <%if (listaAcPortal.contains(401)) {%>selected<%}%> >PREFIXO DE ETIQUETAS</option>
                        <option value="402" <%if (listaAcPortal.contains(402)) {%>selected<%}%> >ABRANGÊNCIA DE SERVIÇOS</option>
                        <option value="403" <%if (listaAcPortal.contains(403)) {%>selected<%}%> >AMARRAÇÕES</option>
                        <option value="404" <%if (listaAcPortal.contains(404)) {%>selected<%}%> >USUÁRIOS</option>
                        <option value="405" <%if (listaAcPortal.contains(405)) {%>selected<%}%> >CLIENTES</option>
                        <option value="406" <%if (listaAcPortal.contains(406)) {%>selected<%}%> >VERIFICAÇÃO DE CONTRATOS</option>
                    </select>
                </div>
            </div>
        </li>
        <li class="list-group-item list-group-heading">
            <%--<input type="checkbox" name="usaConsolidador" <%if (usaConsolidador == 1) {%>checked="true"<%}%> onclick="if (this.checked) {
                $('#liConsolidadorEdit').show();
            } else {
                $('#liConsolidadorEdit').hide();
            }" value="1" /> --%>
            <b>Usa Consolidador?</b>
            <input type="checkbox" name="usaConsolidador" id="usaConsolidadorEdit" <%if (usaConsolidador == 1) {%>checked="true"<%}%> value="1" data-size="mini" data-toggle="toggle" data-on="SIM" data-off="NÃO" data-onstyle="success" data-offstyle="danger" />
        </li>
        <li class="list-group-item" id="liConsolidadorEdit" <%if (usaConsolidador == 0) {%> style="display:none;" <%}%>>
            <div class="row form-horizontal">
                <div class="col-xs-12 col-sm-6 col-md-4 col-lg-3">
                    <label>
                        ABA CONFIGURAÇÕES:<br/>
                        <a style="color:blue;font-size: 8px;" onclick="selectAllCombo(document.getElementById('con_conf_e'), true);">MARCAR TUDO</a><br/>
                        <a style="color:red;font-size: 8px;" onclick="selectAllCombo(document.getElementById('con_conf_e'), false);">DESMARCAR TUDO</a><br/>
                    </label>
                    <select class="form-control text-10px no-padding" name=con_conf_e id=con_conf_e multiple onclick="controleCombobox4(this)" size=10 >
                        <option value="101" <%if (listaAcConsol.contains(101)) {%>selected<%}%> >CONFIGURAÇÕES GERAIS</option>
                        <option value="102" <%if (listaAcConsol.contains(102)) {%>selected<%}%> >CONFIGURAR E-MAIL</option>
                        <option value="103" <%if (listaAcConsol.contains(103)) {%>selected<%}%> >CONFIGURAR BALANÇA</option>
                        <option value="104" <%if (listaAcConsol.contains(104)) {%>selected<%}%> >CONFIGURAR IMPRESSORA</option>
                    </select>
                </div>
                <div class="col-xs-12 col-sm-6 col-md-4 col-lg-3">
                    <label>
                        ABA FERRAMENTAS:<br/>
                        <a style="color:blue;font-size: 8px;" onclick="selectAllCombo(document.getElementById('con_ferramenta_e'), true);">MARCAR TUDO</a><br/>
                        <a style="color:red;font-size: 8px;" onclick="selectAllCombo(document.getElementById('con_ferramenta_e'), false);">DESMARCAR TUDO</a><br/>
                    </label>
                    <select class="form-control text-10px no-padding" name=con_ferramenta_e id=con_ferramenta_e multiple onclick="controleCombobox5(this)" size=10 >
                        <option value="201" <%if (listaAcConsol.contains(201)) {%>selected<%}%> >PROCESSAR OBJETOS COM A BOXCUBO</option>
                        <option value="202" <%if (listaAcConsol.contains(202)) {%>selected<%}%> >ALTERAÇÃO DE ETIQUETAS</option>
                        <option value="203" <%if (listaAcConsol.contains(203)) {%>selected<%}%> >REIMPRESSÃO DE ETIQUETAS GERADAS</option>
                        <option value="204" <%if (listaAcConsol.contains(204)) {%>selected<%}%> >ENVIAR E-MAIL DE OBJETOS CONSOLIDADOS</option>
                        <option value="205" <%if (listaAcConsol.contains(205)) {%>selected<%}%> >VENDA LOCAL - SINTÉTICA</option>
                        <option value="206" <%if (listaAcConsol.contains(206)) {%>selected<%}%> >ESTORNAR/ALTERAR VENDAS LOCAIS</option>
                        <option value="207" <%if (listaAcConsol.contains(207)) {%>selected<%}%> >BIPAR OBJETOS DE PLPs DE TERCEIROS</option>
                        <option value="208" <%if (listaAcConsol.contains(208)) {%>selected<%}%> >CONFERIR E ENVIAR PLPs DE TERCEIROS</option>
                        <option value="209" <%if (listaAcConsol.contains(209)) {%>selected<%}%> >COMPARAR VENDAS A FATURAR SECT x SARA</option>
                        <option value="210" <%if (listaAcConsol.contains(210)) {%>selected<%}%> >ENTREGA INTERNA DE ETIQUETAS</option>
                    </select>
                </div>
                <div class="col-xs-12 col-sm-6 col-md-4 col-lg-3">
                    <label>
                        ABA EXPORTAÇÕES:<br/>
                        <a style="color:blue;font-size: 8px;" onclick="selectAllCombo(document.getElementById('con_exportacao_e'), true);">MARCAR TUDO</a><br/>
                        <a style="color:red;font-size: 8px;" onclick="selectAllCombo(document.getElementById('con_exportacao_e'), false);">DESMARCAR TUDO</a><br/></label>
                    <select class="form-control text-10px no-padding" name="con_exportacao_e" id="con_exportacao_e" multiple onclick="controleCombobox6(this)" size="10" >
                        <option value="301" <%if (listaAcConsol.contains(301)) {%>selected<%}%> >EXPORTAR DADOS PARA O SECT</option>
                        <option value="302" <%if (listaAcConsol.contains(302)) {%>selected<%}%> >EXPORTAR DADOS PARA O SARA</option>
                    </select>
                </div>
                <div class="col-xs-12 col-sm-6 col-md-4 col-lg-3">
                    <label>
                        ABA EXPEDIÇÃO:<br/>
                        <a style="color:blue;font-size: 8px;" onclick="selectAllCombo(document.getElementById('con_exped_e'), true);">MARCAR TUDO</a><br/>
                        <a style="color:red;font-size: 8px;" onclick="selectAllCombo(document.getElementById('con_exped_e'), false);">DESMARCAR TUDO</a><br/></label>
                    <select class="form-control text-10px no-padding" name="con_exped_e" id="con_exped_e" multiple onclick="controleCombobox7(this)" size="10" >
                        <option value="401" <%if (listaAcConsol.contains(401)) {%>selected<%}%> >FAZER EXPEDIÇÃO</option>
                        <option value="402" <%if (listaAcConsol.contains(402)) {%>selected<%}%> >RELATÓRIO DE OBJETOS NÃO EXPEDIDOS</option>
                        <option value="403" <%if (listaAcConsol.contains(403)) {%>selected<%}%> >IMPRIMIR/EXPORTAR MALAS</option>
                        <option value="404" <%if (listaAcConsol.contains(404)) {%>selected<%}%> >RELATÓRIO DE OBJETOS PROCESSADOS</option>
                    </select>
                </div>
                <div class="col-xs-12 col-sm-6 col-md-4 col-lg-3">
                    <label>
                        ABA PESQUISAS:<br/>
                        <a style="color:blue;font-size: 8px;" onclick="selectAllCombo(document.getElementById('con_pesq_e'), true);">MARCAR TUDO</a><br/>
                        <a style="color:red;font-size: 8px;" onclick="selectAllCombo(document.getElementById('con_pesq_e'), false);">DESMARCAR TUDO</a><br/></label>
                    <select class="form-control text-10px no-padding" name="con_pesq_e" id="con_pesq_e" multiple onclick="controleCombobox8(this)" size="10" >
                        <option value="501" <%if (listaAcConsol.contains(501)) {%>selected<%}%> >OBJETO POSTAL</option>
                        <option value="502" <%if (listaAcConsol.contains(502)) {%>selected<%}%> >ORDENS DE SERVIÇO</option>
                        <option value="503" <%if (listaAcConsol.contains(503)) {%>selected<%}%> >EXPORTAÇÕES SECT REALIZADAS</option>
                        <option value="503" <%if (listaAcConsol.contains(504)) {%>selected<%}%> >EXPORTAÇÕES SARA REALIZADAS</option>
                    </select>
                </div>
                <div class="col-xs-12 col-sm-6 col-md-4 col-lg-3">
                    <label>
                        ABA RELATÓRIOS:<br/>
                        <a style="color:blue;font-size: 8px;" onclick="selectAllCombo(document.getElementById('con_relat_e'), true);">MARCAR TUDO</a><br/>
                        <a style="color:red;font-size: 8px;" onclick="selectAllCombo(document.getElementById('con_relat_e'), false);">DESMARCAR TUDO</a><br/></label>
                    <select class="form-control text-10px no-padding" name="con_relat_e" id="con_relat_e" multiple onclick="controleCombobox9(this)" size="10" >
                        <option value="601" <%if (listaAcConsol.contains(601)) {%>selected<%}%> >ATENDIMENTOS DO SARA</option>
                        <option value="602" <%if (listaAcConsol.contains(602)) {%>selected<%}%> >COMPARAÇÕES SECT x SARA REALIZADAS</option>
                        <option value="603" <%if (listaAcConsol.contains(603)) {%>selected<%}%> >ETIQUETAS ALTERADAS</option>
                    </select>
                </div>
                <div class="col-xs-12 col-sm-6 col-md-4 col-lg-3">
                    <label>
                        ABA UTILITÁRIOS:<br/>
                        <a style="color:blue;font-size: 8px;" onclick="selectAllCombo(document.getElementById('con_util_e'), true);">MARCAR TUDO</a><br/>
                        <a style="color:red;font-size: 8px;" onclick="selectAllCombo(document.getElementById('con_util_e'), false);">DESMARCAR TUDO</a><br/></label>
                    <select class="form-control text-10px no-padding" name="con_util_e" id="con_util_e" multiple onclick="controleCombobox10(this)" size="10" >
                        <option value="701" <%if (listaAcConsol.contains(701)) {%>selected<%}%> >CALCULAR DIGITO VERIFICADOR</option>
                        <option value="702" <%if (listaAcConsol.contains(702)) {%>selected<%}%> >DOWNLOAD DO ROBÔ DO SARA</option>
                        <option value="703" <%if (listaAcConsol.contains(703)) {%>selected<%}%> >REORGANIZAR BANCO DE DADOS</option>
                        <option value="704" <%if (listaAcConsol.contains(704)) {%>selected<%}%> >MARCAR TODOS OBJETOS COMO EXPEDIDO</option>
                    </select>
                </div>
            </div>
        </li>

    </ul>
    <div class="alert alert-danger no-margin">*Obs.: Caso não queira mudar a senha deixe os campos da senha em branco!</div>
    <input name='idUsuario' type='hidden' value='<%= idUsuario%>' />
    <input type='hidden' name='loginaux' value='<%= login%>' />
</form>

<script>
    $(function() {
        $('#usaPortalPostalEdit').bootstrapToggle({
            width:50
        });
        $('#usaConsolidadorEdit').bootstrapToggle({
            width:50
        });
    });
    $('#usaPortalPostalEdit').change(function() {
        $('#liPortalPostalEdit').slideToggle();
    });
    $('#usaConsolidadorEdit').change(function() {
        $('#liConsolidadorEdit').slideToggle();
    });
</script>
<%}%>