
<%@page import="Coleta.Controle.contrColeta"%>
<%@page import="Entidade.StatusEntrega"%>
<%@page import="Coleta.Controle.contrColetador"%>
<%@ page import = "java.text.DateFormat,java.util.ArrayList,java.sql.Timestamp, java.util.Date, java.text.SimpleDateFormat"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%
    response.setHeader("Cache-Control", "no-cache"); //HTTP 1.1
    response.setHeader("Pragma", "no-cache"); //HTTP 1.0
    response.setDateHeader("Expires", 0); //prevent caching at the proxy server

    DateFormat df1 = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat sdf3 = new SimpleDateFormat("dd/MM/yyyy HH:mm");

    String nomeBD = (String) session.getAttribute("empresa");
    if (nomeBD == null) {
        response.sendRedirect("../index.jsp?msgLog=3");
    } else {

        Integer idUsuario = (Integer) session.getAttribute("idUsuario");

        int situacao = 2;
        int idCol = 0;

        String dataAtual = sdf1.format(new Date());
        String dataPesquisaBD = sdf2.format(new Date());
        String dataPesquisa = dataAtual;
        if (request.getParameter("dataPesquisa") != null && !request.getParameter("dataPesquisa").equals("")) {
            Date dataAux = df1.parse(request.getParameter("dataPesquisa"));
            dataPesquisa = sdf1.format(dataAux);
            dataPesquisaBD = sdf2.format(dataAux);
        }

        ArrayList listaColetadores = contrColetador.consultaTodosColetadoresColeta(dataPesquisaBD, 2, nomeBD);
        ArrayList<String> listaColetasAntigasNaoFinalizadas = contrColeta.consultaColetasAntigasNaoFinalizadas(idCol, nomeBD);
        ArrayList listaColetas = contrColeta.consultaColetasPelaWeb("dataHoraColeta, c.idColetador", nomeBD);
        String qtdSolicitadas = contrColeta.consultaQtdColetasSolicitadas(nomeBD);
%>
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <title>Portal Postal | Acompanhamento das Coletas</title>

        <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
        <meta http-equiv="cache-control" content="no-cache" />
        <meta http-equiv="Expires" content="-1" />

        <script type="text/javascript" src="../../javascript/ajax.js"></script>
        <script type="text/javascript" src="../../javascript/mascara.js" charset="utf-8"></script>

        <link href="../../css/estilo.css" rel="stylesheet" type="text/css" />

        <link rel="stylesheet" href="../../javascript/plugins/dropdown/css/style.css" type="text/css" media="screen, projection"/>
        <!--[if lte IE 7]>
        <link rel="stylesheet" type="text/css" href="../../javascript/plugins/dropdown/css/ie.css" media="screen" />
        <![endif]-->
        <script type="text/javascript" language="javascript" src="../../javascript/plugins/dropdown/js/jquery.dropdownPlain.js"></script>

        <link type="text/css" href="../../javascript/jquery/css/jquery-ui-1.8.16.custom.css" rel="stylesheet" />
        <script type="text/javascript" src="../../javascript/jquery/js/jquery-1.6.2.min.js"></script>
        <script type="text/javascript" src="../../javascript/jquery/js/jquery-ui-1.8.16.custom.min.js"></script>
        <script type="text/javascript" src="../../javascript/jquery/js/jquery.ui.datepicker-pt-BR.js"></script>

        <script type="text/javascript">

            $(function () {
                $("#dataPesquisa").datepicker({
                    showOn: "button",
                    buttonImage: "../../imagensNew/calendario.png",
                    buttonImageOnly: true,
                    showAnim: "slideDown"
                });
            });

            function submitForm2(servlet) {
                if (confirm('Tem certeza que deseja alterar as coletas selecionadas?')) {
                    var form = document.formColetas;
                    form.action = servlet;
                    form.submit();
                } else {
                    return false;
                }
            }

            //FUNCAO QUE SELECIONA TODOS OS CHECKBOX
            function marcarTudo() {
                var campo = document.formColetas;
                for (var i = 0; i < campo.elements.length; i++) {
                    if (campo.elements[i].checked) {
                        campo.elements[i].checked = false;
                    } else {
                        campo.elements[i].checked = true;
                    }
                }
            }

            function mostrarEndereco(idCli, idCol) {
                if (document.getElementById('tooltipCli' + idCol).className == 'esconder') {
                    buscaCli(idCli, idCol);
                } else {
                    document.getElementById('tooltipCli' + idCol).className = 'esconder';
                }
            }

            function coletadorSubmit(idCol) {
                document.getElementById('idColetador').value = idCol;
                document.formBusca.submit();
            }

            function loginRestrito() {
                var login = document.getElementById('login').value;
                var senha = document.getElementById('senha').value;
                if (login != "" && senha != "") {
                    verificarLoginSenha(login, senha, 2);
                } else {
                    alert('Preencha o Login e a Senha!');
                    return false;
                }
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

    </head>
    <body>
        <div id="divInteracao" class="esconder" style="top:25%; left:35%; right:35%; bottom:25%;" align="center"></div>
        <div id="divProtecao" class="esconder"></div>

        <%@ include file="../../Includes/menu_agencia.jsp" %>
        <%@ include file="../../Includes/telaMsg.jsp" %>

        <div id="divPrincipal" align="center">
            <div id="container">
                <div id="conteudo">
                    <%
                        if (listaColetasAntigasNaoFinalizadas.size() > 0) {
                            String datasNaoFinalizadas = "";
                            for (int i = 0; i < listaColetasAntigasNaoFinalizadas.size(); i++) {
                                String dtColeta = (String) listaColetasAntigasNaoFinalizadas.get(i);
                                if (i == 0) {
                                    datasNaoFinalizadas += "<a style='color: blue;' href='acompanhamento.jsp?dataPesquisa=" + dtColeta + "'>" + dtColeta + "</a>";
                                } else {
                                    datasNaoFinalizadas += ", <a style='color: blue;' href='acompanhamento.jsp?dataPesquisa=" + dtColeta + "'>" + dtColeta + "</a>";
                                }
                            }
                    %>
                    <div style="margin: 10px 0px; background:#fef3cd; border:1px solid #ff6666; padding:10px;" align="center">
                        <span id="spanDivServMsg">
                            Existem coletas que não foram realizadas no(os) dia(as) <%= datasNaoFinalizadas%>.
                        </span><br/><br/>
                        <span id="spanDivServMsg">
                            <a style="color: blue;" onclick="verLoginRestrito('../../ServLimpaColetasAtrasadas', 'Digite o Login de Gerente para prosseguir!', <%= idUsuario%>);">
                                Clique aqui para limpar as coletas antigas solicitadas, e enviar para não coletadas as que estão aguardando coleta!
                            </a>
                        </span>
                    </div>
                    <%}%>


                    <%-- FILTRO DE BUSCA--%>
                    <div style="float:left;width:260px;">
                        <ul class="ul_dados" style="width:220px; padding: 0 10px; border-right: 1px solid #dddddd; ">
                            <li>
                                <dd style="text-align: left;">
                                    <div class="buttons">
                                        <button style="width: 205px; text-align: left;" type="button" onclick="verLoginRestrito('../../ServCarregaColetaFixa', 'Ao RECARREGAR a rota fixa, as rotas carregadas voltam para o status inicial!', <%= idUsuario%>);" <% if (!dataPesquisa.equals(dataAtual)) {%> disabled class="disabled" <%} else {%> class="negative" <%}%> ><img src="../../imagensNew/download2.png" /> CARREGAR ROTA FIXA</button>
                                        <button onclick="javascript:window.location = 'novaColeta.jsp'" style="width: 205px; text-align: left;" type="button" class="positive" ><img src="../../imagensNew/truck_plus.png" /> SOLICITAR COLETA</button>
                                        <button onclick="javascript:window.location = 'consultaSolicitadas.jsp'" style="width: 205px; text-align: left;" type="button" class="selected" ><img src="../../imagensNew/web.png" /> SOLICITAÇÕES WEB <b style="color:red;">(<%= qtdSolicitadas%>)</b></button>
                                    </div>
                                </dd>
                            </li>
                        </ul>
                        <form name="formBusca" action="acompanhamento.jsp" method="post" onsubmit="return valida_data(document.formBusca.dataPesquisa);">
                            <ul class="ul_formulario" style="width:220px; padding: 0 10px; border-right: 1px solid #dddddd;">
                                <li class="titulo">
                                    <dd style="margin-left: 0px;"><span>Filtro de Pesquisa:</span></dd>
                                </li>
                                <li>
                                    <dd style="margin-left: 0px;">
                                        <label>Situaçao da Coleta </label>
                                        <select style="width: 200px;" name="situacao" onchange="javascript:document.formBusca.submit();">
                                            <option value="2" <%if (situacao == 2) {%> selected <%}%> >AGUARDANDO COLETA</option>
                                            <option value="3" <%if (situacao == 3) {%> selected <%}%>>NÃO COLETADAS</option>
                                            <option value="5" <%if (situacao == 5) {%> selected <%}%>>COLETADAS</option>
                                            <option value="0" <%if (situacao == 0) {%> selected <%}%>>TODAS AS COLETAS</option>
                                        </select>
                                    </dd>
                                </li>
                                <li>
                                    <dd style="margin-left: 0px;">
                                        <label>Data da Coleta </label>
                                        <input name="dataPesquisa" id="dataPesquisa" maxlength="10" type="text" value="<%= dataPesquisa%>" onKeyPress="mascara(this, maskData)" onclick="javascript:this.value = '';" onchange="javascript:document.formBusca.submit();" style="width: 105px;" />
                                        <input type="hidden" name="idColetador" id="idColetador" value="<%= idCol%>" />
                                    </dd>
                                </li>
                            </ul>
                            <ul class="ul_dados" style="width:220px; padding: 0 10px; border-right: 1px solid #dddddd;">
                                <li>
                                    <dd>
                                        <label>Coletadores:</label>
                                        <ul class="lista1" style="width: 215px;">
                                            <%
                                                for (int i = 0; i < listaColetadores.size(); i++) {
                                                    Coleta.Entidade.Coletador coletador = (Coleta.Entidade.Coletador) listaColetadores.get(i);
                                                    String nomeColetador = coletador.getNome();
                                                    int idColetador = coletador.getIdColetador();
                                                    String lin = "even";
                                                    if (i % 2 == 0) {
                                                        lin = "odd";
                                                    }
                                            %>
                                            <li <%if (idCol == idColetador) {%> class="sel" <%} else {%> class="<%=lin%>" <%}%> ><a onclick="coletadorSubmit(<%= idColetador%>);" ><%= nomeColetador%> <b style="color:red;">(<%= coletador.getRota()%>)</b></a></li>
                                                <%}%>
                                        </ul>
                                    </dd>
                                </li>
                            </ul>
                        </form>
                        <img width="220" src="../../imagensNew/linha.jpg"/>
                    </div>

                    <% //For carregando as coletas solicitadas que não tem coletador designado
                        int cont = 0;
                        if (listaColetas.size() != 0) {
                    %>
                    <div style="width: 900px; margin-left: 262px;">
                        <div id="titulo2" style="font-size: 20px; margin-bottom: 0;" ><b style="color:red;">Coletas Solicitadas pela WEB</b></div>
                        <form name="formColetas" action="#" method="post">

                            <table style="width: 100%;" class="tb1" cellspacing="0">
                                <tr>
                                    <th width="20"><b>Nº</b></th>
                                    <th width="20">TS</th>
                                    <th width="110"><b>Hora Coleta</b></th>
                                    <th><b>Coletador</b></th>
                                    <th><b>Nome Fantasia</b></th>
                                    <th><b>Observações</b></th>
                                    <th width="20"><a onclick="marcarTudo();" style="cursor:pointer;color:blue;">SEL</a></th>
                                </tr>
                                <%
                                    for (int s = 0; s < listaColetas.size(); s++) {
                                        Coleta.Entidade.Coleta col = (Coleta.Entidade.Coleta) listaColetas.get(s);
                                        int idColetador = col.getIdColetador();
                                        int idColeta = col.getIdColeta();
                                        int idCli = col.getIdCliente();
                                        String nomeFantasia = col.getNomeFantasia();
                                        String tipo = col.getTipoColeta();
                                        String obs = col.getObs();
                                        String dataColeta = sdf3.format(col.getDataHoraColeta());
                                        String coletador = col.getNomeColetador();
                                        if (coletador == null) {
                                            coletador = "<b>Sem Coletador!</b>";
                                        }
                                        cont++;

                                        String trClass = "";
                                        if (s % 2 == 0) {
                                            trClass = "even";
                                        } else {
                                            trClass = "odd";
                                        }

                                        int ts = col.getTipoSolicitacao();
                                        String img = "../../imagensNew/download.png";
                                        if (ts == 1) {
                                            img = "../../imagensNew/phone_2.png";
                                        } else if (ts == 2) {
                                            img = "../../imagensNew/download.png";
                                        } else if (ts == 3) {
                                            img = "../../imagensNew/globe_3.png";
                                        }
                                %>
                                <tr class="<%=trClass%>">
                                    <td><b><%= cont%></b></td>
                                    <td align="center">
                                        <img style="cursor: pointer;" onclick="buscaLogColeta(<%= idColeta%>);" onmouseout="javascript:document.getElementById('tooltip<%= idColeta%>').className = 'esconder';" src="<%= img%>" alt="Tipo da Solicitação" />
                                        <span class="esconder" id="tooltip<%= idColeta%>">
                                            <img alt="bullet" src="../../imagensNew/ajax_arrow.png" style="float: left;margin: 5px 0 0 0;" />
                                            <div id="tipFoo<%=idColeta%>" style="padding: 5px;float: right; background: white; border: 1px solid #eaeaea;">
                                                <img alt="loading" style="margin: 8px 15px;" src="../../imagensNew/loader.gif" />
                                            </div>
                                        </span>
                                    </td>
                                    <td><%= dataColeta%></td>
                                    <td><%= coletador%></td>
                                    <td>
                                        <a style="color:blue;" onclick="mostrarEndereco(<%= idCli%>, <%= idColeta%>);"><%= nomeFantasia%></a>
                                        <span class="esconder" id="tooltipCli<%= idColeta%>">
                                            <img alt="bullet" src="../../imagensNew/ajax_arrow.png" style="float: left;margin: 5px 0 0 0;" />
                                            <div id="tipFooCli<%=idColeta%>" style="padding: 5px; width: 400px; float: right; background: white; border: 1px solid #eaeaea;">
                                                <img alt="loading" style="margin: 8px 15px;" src="../../imagensNew/loader.gif" />
                                            </div>
                                        </span>
                                    </td>
                                    <td>
                                        <div class="form-group">
                                            <span class= "xedit" id="<%= idColeta%>;<%= idCol%>">
                                                <i class="fa fa-pencil fa-spc"></i> <%= obs%>
                                            </span>
                                        </div>
                                    </td>
                                    <td align="center">
                                        <input type="checkbox" name="idColeta<%= cont%>" id="check<%= idCol%>" value="<%= idColeta%>" />
                                        <input type="hidden" name="idColetador<%= cont%>" value="<%= idColetador%>" />
                                    </td>
                                </tr>
                                <%}%>
                            </table>

                            <ul style="width: 900px; padding: 10px 0;" class="ul_formulario">
                                <li>
                                    <dd style="width: 49%;">
                                        <label>Alterar de Coletador</label>
                                        <input type="hidden" name="idColetador1" value="<%= idCol%>"/>
                                        <input type="hidden" name="contador" value="<%= cont%>"/>
                                        <input type="hidden" name="pagina" value="consultaSolicitadas.jsp"/>
                                        <select name="idColetador" style="width:200px;">
                                            <option value="0">-- SELECIONE UM COLETADOR --</option>
                                            <%
                                                for (int i = 0; i < listaColetadores.size(); i++) {
                                                    Coleta.Entidade.Coletador coletador = (Coleta.Entidade.Coletador) listaColetadores.get(i);
                                                    String nomeColetador = coletador.getNome();
                                                    int idColetador = coletador.getIdColetador();
                                            %>
                                            <option value="<%= idColetador%>"><%= nomeColetador%></option>
                                            <%}%>
                                        </select>
                                        <div class="buttons">
                                            <button class="positive" type="submit" onclick="submitForm2('../../ServAlterarColetadorColeta');"><img src="../../imagensNew/users.png"/> ALTERAR COLETADOR</button>
                                        </div>
                                    </dd>
                                    <dd style="width: 49%; height: 20px; text-align: right;">
                                        <div class="buttons">
                                            <button style="margin-right: 0;" class="positive" type="button" onClick="submitForm2('../../ServRepassarColeta');"><img src="../../imagensNew/tick_circle.png"/> CONFIRMAR COLETAS</button>
                                        </div>
                                    </dd>
                                </li>
                            </ul>
                            <img width="100%" style="clear: both; margin-bottom: 50px;" src="../../imagensNew/linha.jpg"/>
                        </form>
                    </div>
                    <%}%>
                    <div style="width: 100%; clear: both;"></div>

                </div>
            </div>
        </div>
    </body>
</html>
<%}%>