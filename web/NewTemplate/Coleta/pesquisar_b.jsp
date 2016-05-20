
<%@page import="Coleta.Entidade.Coletador"%>
<%@page import="Coleta.Entidade.Coleta"%>
<%@page import="Entidade.Clientes"%>
<%@page import="Entidade.StatusEntrega"%>
<%@page import="Coleta.Controle.contrColeta"%>
<%@page import="Coleta.Controle.contrColetador"%>
<%@page import="Controle.ContrServicoECT"%>
<%@page import="Entidade.ServicoECT"%>
<%@page import="Controle.ContrAmarracaoServico"%>
<%@page import="Controle.ContrClienteEtiquetas"%>
<%@page import="Controle.contrCliente"%>
<%@page import="Emporium.Controle.ContrPreVenda"%>
<%@page import="Entidade.PreVenda"%>
<%@page import="Entidade.ClienteLogEtiqueta"%>
<%@ page import = "java.text.DateFormat,java.util.ArrayList,java.sql.Timestamp,java.util.Calendar, java.util.GregorianCalendar, java.util.Date, java.text.SimpleDateFormat"%>

<% response.setHeader("Cache-Control", "no-cache"); //HTTP 1.1
    response.setHeader("Pragma", "no-cache"); //HTTP 1.0
    response.setDateHeader("Expires", 0); //prevent caching at the proxy server

    DateFormat df1 = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat hr1 = new SimpleDateFormat("HH:mm");
    SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");

    String nomeBD = (String) session.getAttribute("empresa");
    if (nomeBD == null) {
        response.sendRedirect("../index.jsp?msgLog=3");
    } else {

        Integer idUsuario = (Integer) session.getAttribute("idUsuario");

        int situacao = 2;
        if (request.getParameter("situacao") != null) {
            situacao = Integer.parseInt(request.getParameter("situacao"));
        }

        String dataAtual = sdf1.format(new Date());
        String dataPesquisaBD = sdf2.format(new Date());
        String dataPesquisa = dataAtual;
        if (request.getParameter("dataPesquisa") != null && !request.getParameter("dataPesquisa").equals("")) {
            Date dataAux = df1.parse(request.getParameter("dataPesquisa"));
            dataPesquisa = sdf1.format(dataAux);
            dataPesquisaBD = sdf2.format(dataAux);
        }

        int idCol = 0;
        if (request.getParameter("idColetador") != null) {
            idCol = Integer.parseInt(request.getParameter("idColetador"));
        }

        ArrayList listaColetadores = contrColetador.consultaTodosColetadoresColeta(dataPesquisaBD, situacao, nomeBD);
        ArrayList<String> listaColetasAntigasNaoFinalizadas = contrColeta.consultaColetasAntigasNaoFinalizadas(idCol, nomeBD);
        ArrayList listaColetas = contrColeta.consultaColetasPeloStatus(situacao, idCol, dataPesquisaBD, "dataHoraColeta", nomeBD);
        String nomeColPesquisa = contrColetador.consultaNomeColetadoresById(idCol, nomeBD);
        ArrayList<StatusEntrega> listaStatus = Controle.ContrStatusEntrega.consultaTodosStatus(nomeBD);
        String qtdSolicitadas = contrColeta.consultaQtdColetasSolicitadas(nomeBD);
        

        String nomeSituacao = "Aguardando Coleta";
        if (situacao == 4) {
            nomeSituacao = "Não Coletadas";
        } else if (situacao == 5) {
            nomeSituacao = "Coletadas";
        } else if (situacao == 0) {
            nomeSituacao = "Todas as Coletas";
        }

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

    <head>
        <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
        <meta name="viewport" content="width=device-width, initial-scale=1"/>
        <meta name="description" content=""/>
        <meta name="author" content=""/>
        <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />

        <title>Acompanhamento Coleta | Portal Postal</title>

        <!-- TimePicker -->
        <script type="text/javascript" src="../../javascript/jquery/js/jquery-1.6.2.min.js"></script>
        <script type="text/javascript" src="../../javascript/plugins/timepicker/jquery.clockpick.1.2.7.js"></script>
        <link href="../../javascript/plugins/timepicker/jquery.clockpick.1.2.7.css" rel="stylesheet" type="text/css" />
        <!-- Fim TimePicker -->

        <%@ include file="../includes/Css_js.jsp" %>


        <style>
            /* Use a wide full screen for small screens like tablets. */
            @media (min-width: 768px) and (max-width:992px) {
                .container {
                    width: initial;
                    padding-left: 2em;
                    padding-right: 2em;
                }
            }

            /* --- Plans ---------------------------- */

            .my_planHeader {
                text-align: center;
                color: white;
                padding-top:0.2em;
                padding-bottom:0.2em;
            }
            .my_planTitle {
                font-size:2em;
                font-weight: bold;
            }
            .my_planPrice {
                font-size:1.2em;
                font-weight: bold;
            }
            .my_planDuration {
                margin-top: -0.6em;
            }

            @media (max-width: 768px) {
                .my_planTitle {
                    font-size:small;
                }
            }

            /* --- Features ------------------------- */

            .my_feature {
                line-height:2.8em;
            }

            @media (max-width: 768px) {
                .my_feature {
                    text-align: center
                }
            }

            .my_featureRow {
                margin-top: 0.2em;
                margin-bottom: 0.2em;
                border: 0.1em solid rgb(163, 163, 163);
            }

            /* --- Plan 1 --------------------------- */
            .my_plan1 {
                background: #337AB7;
            }
            .my_planHeader.my_plan1 a {
                background: rgb(72, 109, 139);
                color:white;
            }
            .my_planHeader.my_plan1 {
                background:  #337AB7;
                border-bottom: thick solid rgb(72, 109, 139);
            }
            /* --- Plan 2 --------------------------- */
            .my_plan2 {
                background: #5CB85C;
            }
            .my_planHeader.my_plan2 a {
                background: #449D44;
                color:white;
            }
            .my_planHeader.my_plan2 {
                background: #5CB85C;
                border-bottom: thick solid rgb(108, 131, 62);
            }
            /* --- Plan 3 --------------------------- */
            .my_plan3 {
                background: #F0AD4E;
            }
            .my_planHeader.my_plan3 a {
                background: rgb(199, 127, 40);
                color:white;
            }
            .my_planHeader.my_plan3 {
                background: #F0AD4E;
                border-bottom: thick solid rgb(199, 127, 40);
            }
            .my_planFeature {
                text-align: center;
                font-size: 2em;
            }
            .my_planFeature i.my_check {
                color: green;
            }

        </style>


        <script type="text/javascript" src="https://www.google.com/jsapi"></script>
       

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
                                <h4 class="page-header"><b class="text-primary"><i class="fa fa-truck"></i> Coleta</b> > <small>Acompanhamento Coleta</small></h4>
                            </div>
                        </div>

                        <div class="row">
                            <%
                                if (listaColetasAntigasNaoFinalizadas.size() > 0) {
                                    String datasNaoFinalizadas = "";
                                    for (int i = 0; i < listaColetasAntigasNaoFinalizadas.size(); i++) {
                                        String dtColeta = (String) listaColetasAntigasNaoFinalizadas.get(i);
                                        if (i == 0) {
                                            datasNaoFinalizadas += "<a style='color: blue;' href='pesquisar_b.jsp?dataPesquisa=" + dtColeta + "'>" + dtColeta + "</a>";
                                        } else {
                                            datasNaoFinalizadas += ", <a style='color: blue;' href='pesquisar_b.jsp?dataPesquisa=" + dtColeta + "'>" + dtColeta + "</a>";
                                        }
                                    }
                            %>
                            <div class="col-lg-12" id="msg">
                                <div class="alert alert-danger">
                                    <a href="#" class="close" data-dismiss="alert"> &times;</a>
                                    <span id="spanDivServMsg">
                                        Existem coletas que não foram realizadas no(os) dia(as) <%= datasNaoFinalizadas%>.
                                    </span><br/><br/>
                                    <span id="spanDivServMsg">
                                        <a  style="cursor: pointer; color: blue;" onclick="ajaxLoginRestrito('../../ServLimpaColetasAtrasadas', 'Digite um Login com perfil Administrador para prosseguir!', <%= idUsuario%>);">
                                            Clique aqui para limpar as coletas antigas solicitadas, e enviar para não coletadas as que estão aguardando coleta!
                                        </a>
                                    </span>
                                </div>
                            </div>
                            <%}%>
                        </div> 
                        <div class="row">
                            <div class="col-lg-12">
                                <div class="col-xs-12">
                                    <div class="row">
                                        <div class="col-xs-4 my_planHeader my_plan1">

                                            <div class="my_planPrice"><i class="fa fa-road fa-lg fa-spc"></i> ROTA FIXA</div>
                                            <div class="my_planDuration"> </div>
                                            <a type="button" class="btn btn-default" <% if (!dataPesquisa.equals(dataAtual)) {%> disabled <%}%> onclick= "ajaxLoginRestrito('../../ServCarregaColetaFixa', 'Ao RECARREGAR a rota fixa, as rotas carregadas voltam para o status inicial!', <%= idUsuario%>);" ><i class="fa fa-download fa-spc"></i> CARREGAR</a>
                                        </div>
                                        <div class="col-xs-4 my_planHeader my_plan3">

                                            <div class="my_planPrice"><i class="fa fa-truck fa-lg fa-spc"></i> COLETA</div>
                                            <div class="my_planDuration"> </div>
                                            <a type="button" onclick="javascript:window.location = 'novaColeta_b.jsp'" class="btn btn-default"><i class="fa fa-plus fa-spc"></i> SOLICITAR</a>
                                        </div>
                                        
                                        
                                        <div class="col-xs-4 my_planHeader my_plan2">
                                            <div class="my_planPrice"><i class="fa fa-globe fa-lg fa-spc"></i> WEB <b style="color:red;">(<%= qtdSolicitadas%>)</b></div>
                                            <div class="my_planDuration"> </div>
                                            <a type="button" class="btn btn-default"  onclick="carregaWeb();"><i class="fa  fa-binoculars fa-spc"></i> VER</a>
                                        </div>
                                            
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div class="row">
                            <div class="col-xs-12" >
                                <form name="formBusca" action="pesquisar_b.jsp" method="post" onsubmit="return valida_data(document.formBusca.dataPesquisa);">
                                    <ul class="list-group">
                                        <li class="list-group-item list-group-heading">
                                            <label>Pesquisar Coletas</label>
                                        </li>
                                        <li class="list-group-item" id="liPesquisa">
                                            <div class="row form-horizontal">
                                                <div class="col-xs-4 col-lg-3">

                                                    <select class="form-control" data-style="btn-info" name="idColetador" id="idColetador" onchange="coletadorSubmit(this);">
                                                        <option value="0">-- Selecione um Coletador --</option>
                                                        <%
                                                            for (int i = 0; i < listaColetadores.size(); i++) {
                                                                Coletador coletadorx = (Coletador) listaColetadores.get(i);
                                                                String nomeColetadorx = coletadorx.getNome();
                                                                int idColetadorx = coletadorx.getIdColetador();
                                                        %>
                                                        <option value="<%= idColetadorx%>" <%if (idCol == idColetadorx) {%> selected <%}%> ><%=idColetadorx%> - <%= nomeColetadorx%> (<%= coletadorx.getRota()%>)</option>
                                                        <%}%>
                                                    </select>
                                                </div>
                                                <div class="col-xs-4 col-lg-3">
                                                    <select class="form-control" name="situacao" onchange="javascript:document.formBusca.submit();">
                                                        <option value="2" <%if (situacao == 2) {%> selected <%}%>>AGUARDANDO COLETA</option>
                                                        <option value="3" <%if (situacao == 3) {%> selected <%}%>>NÃO COLETADAS</option>
                                                        <option value="5" <%if (situacao == 5) {%> selected <%}%>>COLETADAS</option>
                                                        <option value="0" <%if (situacao == 0) {%> selected <%}%>>TODAS AS COLETAS</option>
                                                    </select>
                                                </div>
                                                <div class="col-xs-4 col-lg-2">
                                                    <div class="input-group">
                                                        <input class="form-control" type="text" name="dataPesquisa" id="dataPesquisa"  value="<%= dataPesquisa%>" maxlength="10" size="8" type="text" onKeyPress="mascara(this, maskData)" />
                                                        <span class="input-group-addon" ><i class="fa fa-calendar"></i></span>
                                                    </div>
                                                </div>
                                            </div>
                                        </li>
                                        <li class="list-group-item hidden" id="liVoltarPesq">
                                            <button type="button" class="btn btn-default" onclick="location.reload();"><i class="fa fa-lg fa-spc fa-reply"></i> VOLTAR</button>
                                        </li>
                                    </ul>
                                </form>
                            </div>
                        </div>
                        <div class="row" id="ajxWeb">
                            <form name="formColetas" action="#" method="post">
                                <% //For carregando as coletas solicitadas que não tem coletador designado
                                    int cont = 0;
                                    if (listaColetas.size() != 0) {
                                %>
                                <div class="col-md-12">

                                    <div class="panel panel-default">
                                        <div class="panel-heading" >
                                            <%
                                                if (idCol != 0) {
                                            %>
                                            <b><%= nomeColPesquisa%></b> - <%= nomeSituacao%> do dia <b><%= dataPesquisa%></b>
                                            <%}%>
                                            <div class="pull-right">
                                                <a target="_blank" href="imprimirRota.jsp?idColetador=<%= idCol%>&dataPesquisa=<%= dataPesquisa%>"><i class="fa fa-print"></i> IMPRIMIR ROTA</a> &nbsp;&nbsp;&nbsp;
                                                <a target="_blank" href="gerarMapaRota.jsp?idColetador=<%= idCol%>&dataPesquisa=<%= dataPesquisa%>"><i class="fa fa-map-marker"></i> VER NO MAPA</a>
                                            </div>
                                        </div>
                                        <div class="panel-body">
                                            <div class="dataTable_wrapper no-padding">

                                                <table class="table table-striped table-bordered table-hover table-condensed">
                                                    <thead>
                                                        <tr>
                                                            <th><b>Nº</b></th>
                                                            <th>TS</th>
                                                            <th><b>HR Prev.</b></th>
                                                            <%if (situacao == 0) {%><th><b>HR Baixa</b></th><%}%>
                                                            <th><b>Nome Fantasia</b></th>
                                                            <%if (situacao == 0 || situacao == 5) {%><th><b>Status</b></th><%}%>
                                                            <th><b>Tipo</b></th>
                                                            <th><b>Observações</b></th>
                                                            <th  class="text-center"><a onclick="marcarTudo();" style="cursor:pointer;color:blue;">SEL</a></th>
                                                        </tr>
                                                    </thead>
                                                    <tbody>
                                                        <%
                                                            for (int s = 0; s < listaColetas.size(); s++) {
                                                                Coleta col = (Coleta) listaColetas.get(s);
                                                                int idColeta = col.getIdColeta();
                                                                int idCli = col.getIdCliente();
                                                                String nomeFantasia = col.getNomeFantasia();
                                                                String nome = col.getNomeCliente();
                                                                if(nomeFantasia == null && nome == null){
                                                                    nomeFantasia = "Cliente Cód. " +idCli+" não encontrado!";
                                                                }else if(nomeFantasia.trim().equals("") && !nome.trim().equals("")){
                                                                    nomeFantasia = nome;
                                                                }else if(nomeFantasia.trim().equals("") && nome.trim().equals("")){
                                                                    nomeFantasia = "Nome do Cliente Cód. " + idCli +" está em branco!";
                                                                }
                                                                String tipo = col.getTipoColeta();
                                                                String obs = col.getObs();
                                                                String dataColeta = hr1.format(col.getDataHoraColeta());
                                                                String hrColetado = " - - - ";
                                                                if (col.getDataHoraBaixa() != null) {
                                                                    hrColetado = hr1.format(col.getDataHoraBaixa());
                                                                }
                                                                cont++;

                                                                int ts = col.getTipoSolicitacao();
                                                                String img = "../../imagensNew/download.png";
                                                                if (ts == 1) {
                                                                    img = "../../imagensNew/phone_2.png";
                                                                } else if (ts == 2) {
                                                                    img = "../../imagensNew/download.png";
                                                                } else if (ts == 3) {
                                                                    img = "../../imagensNew/globe_3.png";
                                                                }

                                                                String cor = "blue";
                                                                String hrAguardando = " - - - ";
                                                                if (col.getStatusEntrega() == 6) {
                                                                    if (col.getDataHoraAguardando() != null) {
                                                                        int minutos = (int) Util.SomaData.diferencaEmMinutos(col.getDataHoraAguardando(), new Date());
                                                                        hrAguardando = minutos + " min";//hr1.format(col.getDataHoraAguardando());
                                                                    }
                                                                    cor = "red";
                                                                } else if (col.getStatusEntrega() == 5) {
                                                                    cor = "green";
                                                                } else if (col.getStatusEntrega() == 7) {
                                                                    cor = "orange";
                                                                }
                                                        %>
                                                        <tr>
                                                            <td><b><%= cont%></b></td>
                                                            <td align="center">
                                                                <img style="cursor: pointer;" onclick="buscaLogColeta(<%= idColeta%>);" onmouseout="javascript:document.getElementById('tooltip<%= idColeta%>').className = 'esconder';" src="<%= img%>" alt="Tipo da Solicitação" />

                                                            </td>
                                                            <td align="center"><%= dataColeta%></td>
                                                            <%if (situacao == 0) {%><td align="center"><%= hrColetado%></td><%}%>
                                                            <td>
                                                                <%
                                                                    Clientes cli = Controle.contrCliente.consultaClienteById(idCli, nomeBD);
                                                                    String resultado = "CLIENTE CÓD. " + idCli + " NÃO ENCONTRADO";
                                                                    String tel = "- - -";
                                                                    if(cli != null){                                                                     
                                                                        String rua = cli.getEndereco();
                                                                        String compl = cli.getComplemento();
                                                                        String bairro = cli.getBairro();
                                                                        String cid = cli.getCidade();
                                                                        tel = cli.getTelefone();

                                                                        resultado = rua + "<br/>" + compl + "<br/>" + bairro + "<br/>" + cid;
                                                                        resultado = resultado.toUpperCase();   
                                                                    }

                                                                %>
                                                                <span class="popover-options">
                                                                    <a title="TELEFONE: <h4><%=tel%></h4>"  data-container="body" data-toggle="popover" data-content="<%= resultado%>" style="cursor: pointer; color:<%= cor%>;">
                                                                        <%if (col.getStatusEntrega() == 6) {%><img class="link_img" src="../../imagensNew/clock_alert.png" /> <%= hrAguardando%> - <%}%>
                                                                        <%= nomeFantasia%>
                                                                    </a>
                                                                </span>
                                                            </td>
                                                            <%if (situacao == 0 || situacao == 5) {%><td><%= col.getNomeStatus()%></td><%}%>
                                                            <td><%= tipo%></td>
                                                            <td>
                                                                <span class= "xedit" id="<%= idColeta%>;<%= idCol%>">
                                                                    <i class="fa fa-pencil fa-spc"></i> <%= obs%>
                                                                </span>
                                                            </td>
                                                            <td align="center"><input type="checkbox" name="idColeta<%= cont%>" id="check<%= idCol%>" value="<%= idColeta%>" /></td><!-- ondblclick="CheckAll(this);"-->
                                                        </tr>
                                                        <%}%>
                                                    </tbody>
                                                </table>
                                            </div>
                                        </div>
                                    </div>

                                </div>
                                <div class="col-xs-12 col-sm-12 col-md-5 col-lg-4 form-group pull-left">
                                    <div class="well no-margin">
                                        <%if (situacao <= 2) {%>
                                        <label>Transferir coleta(s) selecionada(s) para: </label>
                                        <input type="hidden" name="idColetador1" value="<%= idCol%>"/>
                                        <input type="hidden" name="contador" value="<%= cont%>"/>
                                        <input type="hidden" name="pagina" value="acompanhamento.jsp"/>
                                        <select class="form-control" name="idColetador">
                                            <option value="0">-- SELECIONE UM COLETADOR --</option>
                                            <%
                                                for (int i = 0; i < listaColetadores.size(); i++) {
                                                    Coletador coletador = (Coletador) listaColetadores.get(i);
                                                    String nomeColetador = coletador.getNome();
                                                    int idColetador = coletador.getIdColetador();
                                            %>
                                            <option value="<%= idColetador%>"><%= nomeColetador%></option>
                                            <%}%>
                                        </select>
                                        <button class="btn btn-info" type="submit" onclick="submitForm('../../ServAlterarColetadorColeta');">
                                            <i class="fa fa-lg fa-spc fa-exchange"></i>
                                            TRANSFERIR COLETA
                                        </button>
                                        <%}%>
                                    </div>
                                </div>

                                <div class="col-xs-12 col-sm-12 col-md-5 col-lg-4 form-group pull-right">
                                    <div class="well no-margin">
                                        <label>Alterar satus da coleta(s) selecionada(s): </label>
                                        <select class="form-control" name="statusEntrega" id="statusEntrega">
                                            <option value="0">-- SELECIONE UM STATUS --</option>
                                            <%
                                                for (int i = 0; i < listaStatus.size(); i++) {
                                                    StatusEntrega se = listaStatus.get(i);
                                                    String cor = "black";
                                                    if (se.getIsCancelar() == 1) {
                                                        cor = "red";
                                                    } else if (se.getIsCancelar() == 2) {
                                                        cor = "green";
                                                    }
                                                    if (se.getId() > 2) {
                                            %>
                                            <option style="color:<%=cor%>;" value="<%= se.getId()%>"><%= se.getNome()%></option>
                                            <%}
                                                }%>
                                        </select>
                                        <button class="btn btn-warning" type="button" onClick="submitForm('../../ServAlterarStatusColeta');"><i class="fa fa-pencil fa-lg fa-spc"></i> ALTERAR STATUS</button>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
                    <%}%>

                </div>
            </div>
        </div>
        </div>

        <!-- /#page-wrapper -->

        <!-- /#wrapper -->

        <script type="text/javascript">

            function verificaLoginDialog(retorno) {
                bootbox.dialog({
                    title: "Acesso Restrito!",
                    message: retorno,
                    animate: true,
                    onEscape: true,
                    buttons: {
                        "Cancelar": {
                            label: "<i class='fa fa-lg fa-times fa-spc'></i> CANCELAR",
                            className: "btn btn-default",
                            callback: function () {
                                bootbox.hideAll();
                            }
                        },
                        success: {
                            label: "<i class='fa fa-lg fa-arrow-circle-right fa-spc'></i> PROSSEGUIR",
                            className: "btn btn-warning",
                            callback: function () {
                                loginRestrito();
                            }
                        }
                    }
                });
                return false;
            }

            function loginRestrito() {
                var login = document.getElementById('login').value;
                var senha = document.getElementById('senha').value;
                if (login !== "" && senha !== "") {     
                    $.ajax({
                        method: "POST",
                        url: "ajax/verificarLogin.jsp",
                        dataType: 'html',
                        data: {login: login, senha: senha, nivel: 2}
                    }).done(function (retorno) {
                        var vFlag = parseInt(retorno);
                        if (vFlag === 1) {              
                            waitMsg();
                            document.formLoginRestrito.submit();
                        } else {
                            bootbox.alert('Login Inválido!');
                        }
                    });
                } else {
                    bootbox.alert('Preencha o Login e a Senha!');
                }
            }

            function ajaxLoginRestrito(servlet, msg, idUser) {
                $.ajax({
                    method: "POST",
                    url: "ajax/verifica_login_restrito.jsp",
                    dataType: 'html',
                    data: {servlet: servlet, mensagem: msg, idUsuario: idUser}
                }).done(function (retorno) {
                    verificaLoginDialog(retorno);
                });

            }

            function carregaWeb() {
                $('#liPesquisa').toggleClass('hidden');
                $('#liVoltarPesq').toggleClass('hidden');
                $.ajax({
                    method: "POST",
                    url: "ajax/consultaSolicitadas.jsp",
                    dataType: 'html'
                }).done(function (data) {
                    $('#ajxWeb').html(data);
                    $(".popover-options a").popover({html: true});
                    $.fn.editable.defaults.mode = 'popup';
                    $('.xedit').editable();
                });
            }

            /* function AllTables() {
             StartDataTable('dataTables-coletas');
             LoadSelect2Script(MakeSelectDataTable('dataTables-coletas'));
             }*/

            $(function () {
                $(".popover-options a").popover({html: true});
            });


            function submitForm(servlet) {
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

            function coletadorSubmit(idCol) {
                if (idCol !== 0) {
                    document.formBusca.submit();
                } else {
                    alert("SELECIONE UM COLETADOR");
                }

            }

            function submitForm2(servlet) {
                if (confirm('Tem certeza que deseja alterar as coletas selecionadas?')) {
                    var form = document.formColetas;
                    form.action = servlet;
                    form.submit();
                } else {
                    return false;
                }
            }


            jQuery(document).ready(function () {
                $.fn.editable.defaults.mode = 'popup';
                $('.xedit').editable();
                $(document).on('click', '.editable-submit', function () {
                    var x = $(this).closest('td').children('span').attr('id');
                    var y = $('.input-sm').val();

                    var aux = x.split(';');

                    $.ajax({
                        url: "ajax/coleta_alterar_obs.jsp",
                        data: {obs: y, idColeta: aux[0], idColetador: aux[1]},
                        type: 'POST'
                    });
                });

                //LoadDataTablesScripts(AllTables);

                $("#dataPesquisa").datepicker({
                    showAnim: 'slideDown',
                    numberOfMonths: 1
                });
                fechaMsg();

            });
        </script>
    </body>
</html>
<%}%>
