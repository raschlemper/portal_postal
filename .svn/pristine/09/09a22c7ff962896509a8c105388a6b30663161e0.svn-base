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
            <b>Usa Portal Postal? </b>
            <input type="checkbox" name="usaPortalPostal" id="usaPortalPostalEdit" value="1" <%if (usaPortal == 1) {%>checked="true"<%}%> data-size="mini" data-toggle="toggle" data-on="SIM" data-off="NÃO" data-onstyle="success" data-offstyle="danger" />
        </li>
        <li class="list-group-item" id="liPortalPostalEdit" <%if (usaPortal == 0) {%> style="display:none;" <%}%>>
            <div class="row form-horizontal">                
                <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6">
                    <select style="margin-left: 15px;" name="acessos_pp_edit"  id="acessos_pp_edit" data-width="auto" class="selectpicker show-tick" multiple title="Selecione os acessos permitidos..." multiple data-actions-box="true" data-selected-text-format="static" ><%--onclick="controleComboboxPP5(this)"--%>
                        <optgroup label="DASHBOARD">
                            <option value="702" <%if (listaAcPortal.contains(702)) {%>selected<%}%> >OVERVIEW COLETAS</option>
                            <option value="701" <%if (listaAcPortal.contains(701)) {%>selected<%}%> >RELATORIOS</option>                                                            
                        </optgroup>
                        <optgroup label="TELEGRAMAS">
                            <option value="106" <%if (listaAcPortal.contains(106)) {%>selected<%}%> >TELEGRAMAS</option>                                                            
                        </optgroup>
                        <optgroup label="GERENCIAR ETIQUETAS">
                            <option value="101" <%if (listaAcPortal.contains(101)) {%>selected<%}%> >ETIQUETAS RESTANTES</option>
                            <option value="102" <%if (listaAcPortal.contains(102)) {%>selected<%}%> >SEQUÊNCIAS GERADAS</option>
                            <option value="103" <%if (listaAcPortal.contains(103)) {%>selected<%}%> >PESQUISAR ETIQUETA</option>
                            <option value="104" <%if (listaAcPortal.contains(104)) {%>selected<%}%> >ETIQUETAS PENDENTES</option>
                            <option value="105" <%if (listaAcPortal.contains(105)) {%>selected<%}%> >ETIQUETAS INUTILIZADAS</option>
                        </optgroup>
                        <optgroup label="COLETA">
                            <option value="201" <%if (listaAcPortal.contains(201)) {%>selected<%}%> >ACOMPANHAMENTO</option>
                            <option value="206" <%if (listaAcPortal.contains(206)) {%>selected<%}%> >GERENCIAR ROTAS</option>
                            <option value="202" <%if (listaAcPortal.contains(202)) {%>selected<%}%> >NOVA COLETA</option>
                            <option value="203" <%if (listaAcPortal.contains(203)) {%>selected<%}%> >COLETADORES</option>
                            <option value="204" <%if (listaAcPortal.contains(204)) {%>selected<%}%> >TIPO DE COLETA</option>
                            <option value="205" <%if (listaAcPortal.contains(205)) {%>selected<%}%> >HORÁRIOS DA COLETA</option>
                        </optgroup>
                        <optgroup label="IMPORTAÇOES">
                            <option value="302" <%if (listaAcPortal.contains(302)) {%>selected<%}%> >ARQUIVO DE RETORNO DE AR</option>
                            <option value="301" <%if (listaAcPortal.contains(301)) {%>selected<%}%> >ARQUIVO DE MOVIMENTAÇAO</option>
                            <option value="303" <%if (listaAcPortal.contains(303)) {%>selected<%}%> >ARQUIVO DE CLIENTES</option>
                            <option value="304" <%if (listaAcPortal.contains(304)) {%>selected<%}%> >ARQUIVO DE DEPARTAMENTOS</option>
                        </optgroup>
                        <optgroup label="CADASTROS">
                            <option value="401" <%if (listaAcPortal.contains(401)) {%>selected<%}%> >PREFIXO DE ETIQUETAS</option>
                            <option value="402" <%if (listaAcPortal.contains(402)) {%>selected<%}%> >ABRANGÊNCIA DE SERVIÇOS</option>
                            <option value="403" <%if (listaAcPortal.contains(403)) {%>selected<%}%> >AMARRAÇÕES</option>
                            <option value="404" <%if (listaAcPortal.contains(404)) {%>selected<%}%> >USUÁRIOS DA AGÊNCIA</option>
                            <option value="405" <%if (listaAcPortal.contains(405)) {%>selected<%}%> >CLIENTES</option>
                            <option value="407" <%if (listaAcPortal.contains(407)) {%>selected<%}%> >GRUPO DE FATURAMENTO</option>
                            <option value="406" <%if (listaAcPortal.contains(406)) {%>selected<%}%> >VERIFICAÇÃO DE CONTRATOS</option>
                            <option value="408" <%if (listaAcPortal.contains(408)) {%>selected<%}%> >VENDEDORES</option>
                        </optgroup>
                        <optgroup label="VEICULOS">
                            <option value="501" <%if (listaAcPortal.contains(501)) {%>selected<%}%> >CADASTRO DE VEICULO</option>
                            <option value="502" <%if (listaAcPortal.contains(502)) {%>selected<%}%> >MANUTENÇAO</option>
                            <option value="503" <%if (listaAcPortal.contains(503)) {%>selected<%}%> >COMBUSTIVEL</option>
                            <option value="504" <%if (listaAcPortal.contains(504)) {%>selected<%}%> >MULTA</option>
                            <option value="505" <%if (listaAcPortal.contains(505)) {%>selected<%}%> >SEGURO</option>
                            <option value="506" <%if (listaAcPortal.contains(506)) {%>selected<%}%> >SINISTRO</option>
                        </optgroup>
                        <optgroup label="FINANCEIRO">
                            <option value="601" <%if (listaAcPortal.contains(601)) {%>selected<%}%> >INICIO</option>
                            <option value="602" <%if (listaAcPortal.contains(602)) {%>selected<%}%> >LANÇAMENTOS</option>
                            <option value="603" <%if (listaAcPortal.contains(603)) {%>selected<%}%> >PROGRAMAÇAO</option>
                            <option value="604" <%if (listaAcPortal.contains(604)) {%>selected<%}%> >DEMONSTRATIVO</option>
                            <option value="605" <%if (listaAcPortal.contains(605)) {%>selected<%}%> >CADASTROS</option>
                        </optgroup>
                    </select>
                </div>
            </div>
        </li>
        <li class="list-group-item list-group-heading">
            <b>Usa Consolidador?</b>
            <input type="checkbox" name="usaConsolidador" id="usaConsolidadorEdit" <%if (usaConsolidador == 1) {%>checked="true"<%}%> value="1" data-size="mini" data-toggle="toggle" data-on="SIM" data-off="NÃO" data-onstyle="success" data-offstyle="danger" />
        </li>
        <li class="list-group-item" id="liConsolidadorEdit" <%if (usaConsolidador == 0) {%> style="display:none;" <%}%>>
            <div class="row form-horizontal">
                
                <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6">
                    <select style="margin-left: 15px;" name="acessos_cons_edit"  id="acessos_cons_edit" data-width="auto" class="selectpicker show-tick" multiple title="Selecione os acessos permitidos..." multiple data-actions-box="true" data-selected-text-format="static" ><%--onclick="controleComboboxPP5(this)"--%>
                        <optgroup label="CONFIGURAÇOES">
                            <option value="101" <%if (listaAcConsol.contains(101)) {%>selected<%}%> >CADASTRO DE SEQUENCIAS LOGICAS</option>
                            <option value="102" <%if (listaAcConsol.contains(102)) {%>selected<%}%> >CADASTRO DE SUB-CAIXAS DO SARA</option>
                            <option value="103" <%if (listaAcConsol.contains(103)) {%>selected<%}%> >CONFIGURAÇÕES GERAIS</option>
                            <option value="104" <%if (listaAcConsol.contains(104)) {%>selected<%}%> >CONFIGURAR IMPRESSORA</option>
                            <option value="105" <%if (listaAcConsol.contains(105)) {%>selected<%}%> >CONFIGURAR BALANÇA</option>
                            <option value="106" <%if (listaAcConsol.contains(106)) {%>selected<%}%> >CONFIGURAR E-MAIL</option>
                        </optgroup>
                        <optgroup label="IMPORTAÇOES">
                            <option value="1001" <%if (listaAcConsol.contains(1001)) {%>selected<%}%> >IMPORTAR ARQUIVO DE POSTAGEM</option>
                            <option value="1002" <%if (listaAcConsol.contains(1002)) {%>selected<%}%> >CADASTROS DE LAYOUT DE IMPORTAÇAO</option>
                        </optgroup>
                        <optgroup label="FERRAMENTAS">
                            <option value="201" <%if (listaAcConsol.contains(201)) {%>selected<%}%> >PROCESSAR OBJETOS COM A BOXCUBO</option>
                            <option value="202" <%if (listaAcConsol.contains(202)) {%>selected<%}%> >BIPAR OBJETO DE PRE-POSTAGEM</option>
                            <option value="203" <%if (listaAcConsol.contains(203)) {%>selected<%}%> >VENDA LOCAL - SINTETICA</option>
                            <option value="204" <%if (listaAcConsol.contains(204)) {%>selected<%}%> >VENDA LOCAL - COMPLETA</option>
                            <option value="205" <%if (listaAcConsol.contains(205)) {%>selected<%}%> >VENDA LOCAL - POR CHAVE DE CLIENTE</option>
                            <option value="206" <%if (listaAcConsol.contains(206)) {%>selected<%}%> >VENDA LOCAL - OBJETOS SEM REGISTRO</option>
                            <option value="207" <%if (listaAcConsol.contains(207)) {%>selected<%}%> >IMPORTAÇAO DE TICKET DO SARA</option>
                            <option value="208" <%if (listaAcConsol.contains(208)) {%>selected<%}%> >BIPAR OBJETOS DE PLP DE TERCEIRO</option>
                            <option value="209" <%if (listaAcConsol.contains(209)) {%>selected<%}%> >CONFERIR PLP DE TERCEIRO</option>
                            <option value="210" <%if (listaAcConsol.contains(210)) {%>selected<%}%> >ENTREGA INTERNA DE OBJETOS</option>
                            <option value="211" <%if (listaAcConsol.contains(211)) {%>selected<%}%> >ATUALIZAR SISTEMA MANUALMENTE</option>
                        </optgroup>
                        <optgroup label="EXPORTAÇOES">
                            <option value="301" <%if (listaAcConsol.contains(301)) {%>selected<%}%> >EXPORTAR VENDAS PARA O SARA</option>
                            <option value="302" <%if (listaAcConsol.contains(302)) {%>selected<%}%> >EXPORTAR LOTES PARA VENDA EM M.F.</option>
                            <option value="303" <%if (listaAcConsol.contains(303)) {%>selected<%}%> >EXPORTAR MOVIMENTO PARA PORTAL POSTAL WEB</option>
                            <option value="304" <%if (listaAcConsol.contains(304)) {%>selected<%}%> >EXPORTAÇOES DE M.F. REALIZADAS</option>
                            <option value="305" <%if (listaAcConsol.contains(305)) {%>selected<%}%> >EXPORTAÇOES SARA REALIZADAS</option>
                        </optgroup>
                        <optgroup label="EXPEDIÇAO">
                            <option value="401" <%if (listaAcConsol.contains(401)) {%>selected<%}%> >FAZER EXPEDIÇAO</option>
                            <option value="402" <%if (listaAcConsol.contains(402)) {%>selected<%}%> >OBJETOS NAO EXPEDIDOS</option>
                            <option value="403" <%if (listaAcConsol.contains(403)) {%>selected<%}%> >IMPRIMIR/EXPORTAR MALAS</option>
                            <option value="404" <%if (listaAcConsol.contains(404)) {%>selected<%}%> >SEPARAÇAO AUTOMATICA DE MALAS</option>
                        </optgroup>
                        <optgroup label="PESQUISAS">
                            <option value="501" <%if (listaAcConsol.contains(501)) {%>selected<%}%> >PESQUISAR OBJETO POR SRO</option>
                            <option value="502" <%if (listaAcConsol.contains(502)) {%>selected<%}%> >PESQUISAR OBJETOS COMPLETA</option>
                            <option value="503" <%if (listaAcConsol.contains(503)) {%>selected<%}%> >PESQUISAR O.S./LISTA DE POSTAGEM</option>
                            <option value="504" <%if (listaAcConsol.contains(504)) {%>selected<%}%> >PESQUISAR VENDAS LOCAIS</option>
                            <option value="505" <%if (listaAcConsol.contains(505)) {%>selected<%}%> >PESQUISAR CEP</option>
                            <option value="506" <%if (listaAcConsol.contains(506)) {%>selected<%}%> >PESQUISAR CLIENTES</option>
                        </optgroup>
                        <optgroup label="RELATORIOS">
                            <option value="601" <%if (listaAcConsol.contains(601)) {%>selected<%}%> >ATENDIMENTOS DO SARA</option>
                            <option value="602" <%if (listaAcConsol.contains(602)) {%>selected<%}%> >ETIQUETAS ALTERADAS</option>
                            <option value="603" <%if (listaAcConsol.contains(603)) {%>selected<%}%> >OBJETOS SEM PRE-POSTAGEM</option>
                            <option value="604" <%if (listaAcConsol.contains(604)) {%>selected<%}%> >CURVA ABC (RANKING)</option>
                            <option value="605" <%if (listaAcConsol.contains(605)) {%>selected<%}%> >RELATORIO DE OBJETOS PROCESSADOS</option>
                            <option value="606" <%if (listaAcConsol.contains(606)) {%>selected<%}%> >RELATORIO DE MOVIMENTO</option>
                            <option value="607" <%if (listaAcConsol.contains(607)) {%>selected<%}%> >AUDITORIA DE CAIXAS</option>
                        </optgroup>
                        <optgroup label="FATURAS">
                            <option value="701" <%if (listaAcConsol.contains(701)) {%>selected<%}%> >GERAR FATURAS</option>
                            <option value="702" <%if (listaAcConsol.contains(702)) {%>selected<%}%> >CONSULTAR FATURAS</option>
                            <option value="703" <%if (listaAcConsol.contains(703)) {%>selected<%}%> >RECEBER PAGAMENTO DE FATURA</option>
                            <option value="704" <%if (listaAcConsol.contains(704)) {%>selected<%}%> >OUTROS LANÇAMENTOS</option>
                            <option value="705" <%if (listaAcConsol.contains(705)) {%>selected<%}%> >POSIÇAO CONSOLIDADA DAS FATURAS</option>
                        </optgroup>
                        <optgroup label="BDF">
                            <option value="801" <%if (listaAcConsol.contains(801)) {%>selected<%}%> >IMPORTAR DADOS DO BDF</option>
                            <option value="802" <%if (listaAcConsol.contains(802)) {%>selected<%}%> >PRE-ALERTA BDF</option>
                            <option value="803" <%if (listaAcConsol.contains(803)) {%>selected<%}%> >DEFINIR CLIENTE PARA ATENDIMENTO SARA</option>
                        </optgroup>
                        <optgroup label="UTILITARIOS">
                            <option value="901" <%if (listaAcConsol.contains(901)) {%>selected<%}%> >CALCULAR DIGITO VERIFICADOR</option>
                            <option value="902" <%if (listaAcConsol.contains(902)) {%>selected<%}%> >MARCAR TODOS OBJETOS COMO EXPEDIDOS</option>
                            <option value="903" <%if (listaAcConsol.contains(903)) {%>selected<%}%> >IMPRIMIR CHANCELAS</option>
                            <option value="904" <%if (listaAcConsol.contains(904)) {%>selected<%}%> >IMPRIMIR SEQUÊNCIAS LOGICAS DE CLIENTE</option>
                            <option value="905" <%if (listaAcConsol.contains(905)) {%>selected<%}%> >ATUALIZAR IP DA BOXCUBO</option>
                            <option value="906" <%if (listaAcConsol.contains(906)) {%>selected<%}%> >ATUALIZAR BANCO DE CEPS E TARIFAS</option>
                            <option value="907" <%if (listaAcConsol.contains(907)) {%>selected<%}%> >FAZER BACKUP E ORGANIZAR BANCO DE DADOS</option>
                        </optgroup>
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
    $('.selectpicker').selectpicker('render');

    $(function () {
        $('#usaPortalPostalEdit').bootstrapToggle({
            width: 50
        });
        $('#usaConsolidadorEdit').bootstrapToggle({
            width: 50
        });
    });
    $('#usaPortalPostalEdit').change(function () {
        $('#liPortalPostalEdit').slideToggle();
    });
    $('#usaConsolidadorEdit').change(function () {
        $('#liConsolidadorEdit').slideToggle();
    });
</script>
<%}%>