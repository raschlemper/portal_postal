
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
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

    SimpleDateFormat sdf3 = new SimpleDateFormat("dd/MM/yyyy HH:mm");

    String nomeBD = (String) session.getAttribute("empresa");
    if (nomeBD == null) {
        response.sendRedirect("../index.jsp?msgLog=3");
    } else {

        Integer idUsuario = (Integer) session.getAttribute("idUsuario");

// ***************************************************       
        int situacao = 0;
        // int situacao = 2;
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
        
        ArrayList<StatusEntrega> listaStatus = Controle.ContrStatusEntrega.consultaTodosStatus(nomeBD);
        
        String qtdSolicitadas = contrColeta.consultaQtdColetasSolicitadas(nomeBD);
        
        ArrayList listaColetasWeb = contrColeta.consultaColetasPelaWeb("dataHoraColeta, c.idColetador", nomeBD);
        
        boolean flagRotaFixa = contrColeta.verificaSeJaHouveRotaFixa(dataPesquisaBD, nomeBD);

        String nomeSituacao = "Aguardando Coleta";
        if (situacao == 4) {
            nomeSituacao = "Não Coletadas";
        } else if (situacao == 5) {
            nomeSituacao = "Coletadas";
        } else if (situacao == 0) {
            nomeSituacao = "Todas as Coletas";
        }

        Map<String, Integer> badgMap = new HashMap<String, Integer>();

        int colAberto = 0;

        //******************************************

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

        <script type="text/javascript" src="https://www.google.com/jsapi"></script>
        <style>    
            /* body{ margin-top:50px;}*/
            .nav-tabs .glyphicon:not(.no-margin) { margin-right:10px; }
            .tab-pane .list-group-item:first-child {border-top-right-radius: 0px;border-top-left-radius: 0px;}
            .tab-pane .list-group-item:last-child {border-bottom-right-radius: 0px;border-bottom-left-radius: 0px;}
            .tab-pane .list-group .checkbox { display: inline-block;margin: 0px; }
            .tab-pane .list-group input[type="checkbox"]{ margin-top: 2px;}
            .badge{ margin-top: 2px;}
            .tab-pane .list-group .glyphicon { margin-right:5px; }
            .tab-pane .list-group .glyphicon:hover { color:#FFBC00; }
            a.list-group-item.read { color: #222;background-color: #F3F3F3; }
            hr { margin-top: 5px;margin-bottom: 10px; }
            .nav-pills>li>a {padding: 5px 5px;}
            .ad { padding: 5px;background: #F5F5F5;color: #222;font-size: 80%;border: 1px solid #E5E5E5; }
            .ad a.title {color: #15C;text-decoration: none;font-weight: bold;font-size: 110%;}
            .ad a.url {color: #093;text-decoration: none;}
            .list-group-item {padding: 5px 5px;}


        </style>

    </head>

    <body>
        <script type="text/javascript">
            waitMsg();</script>
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
                                            datasNaoFinalizadas += "<a style='color: blue;' href='pesquisar_b_1.jsp?dataPesquisa=" + dtColeta + "'>" + dtColeta + "</a>";
                                        } else {
                                            datasNaoFinalizadas += ", <a style='color: blue;' href='pesquisar_b_1.jsp?dataPesquisa=" + dtColeta + "'>" + dtColeta + "</a>";
                                        }
                                    }
                            %>
                            <div class="col-lg-12" id="msg">
                                <div class="alert alert-danger">
                                    <a href="#" class="close" data-dismiss="alert">&times;</a>
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

                        <div class="container-fluid">
                            <div class="row">                        
                                <div class="col-sm-12">
                                    <!-- Split button -->
                                    <div class="btn-group">
                                        <button type="button" class="btn btn-default">
                                            <div class="checkbox" style="margin: 0;">
                                                <label>
                                                    <input type="checkbox" id="checkAll">
                                                </label>
                                            </div>
                                        </button>
                                        <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
                                            &nbsp; <span class="caret"></span><span class="sr-only">Toggle Dropdown</span>
                                        </button>
                                        <ul class="dropdown-menu" role="menu">
                                            <li><a href="#" onclick="pesquisaSituacao(0);
                                                    return false;"><%if (situacao == 0) {%><b><%}%>Todas as Situações<%if (situacao == 0) {%></b><%}%></a></li>
                                            <li><a href="#" onclick="pesquisaSituacao(2);
                                                    return false;"><%if (situacao == 2) {%><b><%}%>Aguarda Coleta | Cliente<%if (situacao == 2) {%></b><%}%></a></li>
                                            <li><a href="#" onclick="pesquisaSituacao(3);
                                                    return false;"><%if (situacao == 3) {%><b><%}%>Cancelada | Cliente Ausente<%if (situacao == 3) {%></b><%}%></a></li>  
                                            <li><a href="#" onclick="pesquisaSituacao(5);
                                                    return false;"><%if (situacao == 5) {%><b><%}%>Coletado | Não tinha Objeto<%if (situacao == 5) {%></b><%}%></a></li>                                    
                                        </ul>
                                    </div>

                                    <!-- Single button -->
                                    <div class="btn-group hidden" id="btMais">
                                        <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
                                            Alterar <span class="caret"></span>
                                        </button>
                                        <ul class="dropdown-menu" role="menu">
                                            <li>
                                                <a href="#" onclick="mostraListaColetador();
                                                        return false;">Coletador</a>
                                            </li>
                                            <li>
                                                <a href="#" onclick="mostraListaBaixa();
                                                        return false;" >Status da Coleta</a>
                                            </li>
                                        </ul>
                                    </div> 

                                    <div class="btn-group hidden" id="listaBaixa">

                                        <button id="dropBaixa" type="button" class="btn btn-default dropdown-toggle" style="background-color: LemonChiffon;" data-toggle="dropdown" aria-expanded="true">
                                            Selecione o Status 
                                            <span class="caret"></span>
                                        </button>
                                        <div class="dropdown-backdrop"></div>
                                        <ul class="dropdown-menu" id="ulDropBaixa" aria-labelledby="dropBaixa">                                            
                                            <%
                                                for (int i = 0; i < listaStatus.size(); i++) {
                                                    StatusEntrega se = listaStatus.get(i);

                                                    if (se.getId() > 2 && se.getId() != 6) {
                                            %>
                                            <li >
                                                <a href="#" onclick=" alterarColetas(<%= se.getId()%>,<%= idCol%>);"><%= se.getNome()%></a>                                                   
                                            </li>
                                            <%}
                                                }%>
                                        </ul>
                                    </div>

                                    <div class="btn-group hidden" id="listaColetador">

                                        <button id="dropCol" type="button" class="btn btn-default dropdown-toggle" style="background-color: LemonChiffon;" aria-expanded="false" data-toggle="dropdown">
                                            Selecionar Coletador <span class="caret"></span>
                                        </button>
                                        <ul class="dropdown-menu">
                                            <%
                                                for (int i = 0; i < listaColetadores.size(); i++) {
                                                    Coletador coletador = (Coletador) listaColetadores.get(i);
                                                    String nomeColetador = coletador.getNome();
                                                    int idColetador = coletador.getIdColetador();
                                            %> 
                                            <li>
                                                <a href="#" onclick="alterarColetador(<%= idColetador%>, <%= idCol%>);
                                                        return false;"><%= idColetador%> - <%= nomeColetador%></a>
                                            </li> 
                                            <%}%>
                                        </ul>
                                    </div>

                                    <button id="rotaFixa" type="button" class="btn btn-default" data-toggle="tooltip" title="Refresh" <% if (!dataPesquisa.equals(dataAtual)) {%> disabled <%}%> <%if(flagRotaFixa){%>onclick="alert('Rota fixa já foi carregada nesta data!')"<%}else{%>onclick= "ajaxLoginRestrito('../../ServCarregaColetaFixa', 'Ao RECARREGAR a rota fixa, as rotas carregadas voltam para o status inicial!', <%= idUsuario%>);"<%}%>>
                                           <span class="fa fa-download"></span>  Rota Fixa 
                                    </button>
                                    <a id="printMap" target="_blank" type="button" class="btn btn-default hidden" data-toggle="tooltip" title="Mapa" >
                                           <span class="fa fa-map-marker"></span>  Visualizar Mapa 
                                    </a>   
                                    <a id="printRota" target="_blank" type="button" class="btn btn-default hidden" data-toggle="tooltip" title="Rota">
                                           <span class="fa fa-print"></span> Imprimir Rota 
                                    </a>
                                    <div class="btn-group pull-right">
                                        <div class="form-inline">
                                            <form name="formData" id="formData" action="pesquisar_b_1.jsp"> 
                                                <div class="clearfix"></div>
                                                <div class="input-group">
                                                    <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                                                    <input type="text" id="dataPesquisa" name="dataPesquisa" class="form-control" value="<%= dataPesquisa%>" size="10" maxlength="10" onkeypress="mascara(this, maskData)" />
                                                </div>
                                                <button type="submit" class="btn btn-sm btn-primary form-control"  href="" onclick="waitMsg();
                                                        document.formData.submit();"><i class="fa fa-lg fa-search"></i>
                                                </button>
                                            </form>     
                                        </div>
                                    </div>                           
                                </div>
                            </div>
                            <hr />
                            <div class="row">

                                <div class="col-sm-12">
                                    <!-- Nav tabs -->
                                    <ul class="nav nav-tabs">
                                        <%
                                            for (int i = 0; i < listaColetadores.size(); i++) {
                                                Coletador coletadorx = (Coletador) listaColetadores.get(i);
                                                String nomeColetadorx = coletadorx.getNome();
                                                int idColetadorx = coletadorx.getIdColetador();
                                        %>                                       
                                        <li>
                                            <a id="aba<%= idColetadorx%>" href="#rota<%= idColetadorx%>" data-toggle="tab" onclick="clickRota(<%= idColetadorx%>);
                                                    return false;">
                                                <span class="glyphicon glyphicon-user" id=""></span>
                                                <%=nomeColetadorx%>                                                
                                                <span id="badgeRota<%= idColetadorx%>" class="label label-info"></span>
                                            </a>
                                        </li>  
                                        <%}
                                            if (!qtdSolicitadas.equals("0")) {%>

                                        <li>
                                            <a href="#rotaweb" id="abaWeb" onclick="clickRota(0)" data-toggle="tab">
                                                <span class="glyphicon glyphicon-globe no-margin"></span>  
                                                Web
                                                <span id="badgeRotaWeb" class="label label-danger"><%= qtdSolicitadas%></span>
                                            </a>
                                        </li>
                                        <% }%> 

                                    </ul>
                                    <!-- Tab panes -->
                                    <div class="tab-content" id="listaColetas">

                                        <%
                                            for (int i = 0; i < listaColetadores.size(); i++) {
                                                Coletador coletadorx = (Coletador) listaColetadores.get(i);
                                                int idColetadorx = coletadorx.getIdColetador();
                                                ArrayList listaColetas = contrColeta.consultaColetasPeloStatus(situacao, idColetadorx, dataPesquisaBD, "dataHoraColeta", nomeBD);


                                        %>  

                                        <div class="tab-pane fade in" id="rota<%=idColetadorx%>">                                            
                                            <div class="list-group">
                                                <%int cont = 0;
                                                    colAberto = 0;
                                                    if (listaColetas.size() != 0) {
                                                        for (int s = 0; s < listaColetas.size(); s++) {

                                                            Coleta col = (Coleta) listaColetas.get(s);
                                                            int idColeta = col.getIdColeta();
                                                            int idCli = col.getIdCliente();

                                                            String nomeFantasia = col.getNomeFantasia();
                                                            String nome = col.getNomeCliente();
                                                            if (nomeFantasia == null && nome == null) {
                                                                nomeFantasia = "Cliente Cód. " + idCli + " não encontrado!";
                                                            } else if (nomeFantasia.trim().equals("") && !nome.trim().equals("")) {
                                                                nomeFantasia = nome;
                                                            } else if (nomeFantasia.trim().equals("") && nome.trim().equals("")) {
                                                                nomeFantasia = "Nome do Cliente Cód. " + idCli + " está em branco!";
                                                            }
                                                            String tipo = col.getTipoColeta();
                                                            String obs = col.getObs();
                                                            String hrEstimada = hr1.format(col.getDataHoraColeta());
                                                            String hrColetado = " - - - - ";
                                                            if (col.getDataHoraBaixa() != null) {
                                                                hrColetado = hr1.format(col.getDataHoraBaixa());
                                                            }
                                                            cont++;

                                                            int ts = col.getTipoSolicitacao();
                                                            String img = "glyphicon glyphicon-road"; // download
                                                            if (ts == 1) {
                                                                img = "glyphicon glyphicon-phone-alt"; // telefone
                                                            } else if (ts == 2) {
                                                                img = "glyphicon glyphicon-road";// download
                                                            } else if (ts == 3) {
                                                                img = "glyphicon glyphicon-globe"; // web
                                                            }

                                                            String cor = "blue";
                                                            String hrAguardando = " - - -  ";
                                                            if (col.getStatusEntrega() == 6) {
                                                                if (col.getDataHoraAguardando() != null) {
                                                                    int minutos = (int) Util.SomaData.diferencaEmMinutos(col.getDataHoraAguardando(), new Date());
                                                                    hrAguardando = minutos + " min";//hr1.format(col.getDataHoraAguardando());
                                                                }
                                                                cor = "yellow";
                                                            } else if (col.getStatusEntrega() == 5) {
                                                                cor = "green";
                                                            } else if (col.getStatusEntrega() == 7) {
                                                                cor = "orange";
                                                            } else if (col.getStatusEntrega() == 3) {
                                                                cor = "red";
                                                            }
                                                            if (col.getStatusEntrega() == 6 || col.getStatusEntrega() == 2) {
                                                                colAberto++;
                                                            }

                                                            Clientes cli = Controle.contrCliente.consultaClienteById(idCli, nomeBD);
                                                            String resultado = "CLIENTE CÓD. " + idCli + " NÃO ENCONTRADO";
                                                            String tel = "- - -";
                                                            if (cli != null) {
                                                                String rua = cli.getEndereco();
                                                                String compl = cli.getComplemento();
                                                                String bairro = cli.getBairro();
                                                                String cid = cli.getCidade();
                                                                tel = cli.getTelefone();

                                                                resultado = rua + " " + compl + " " + bairro + " " + cid;
                                                                resultado = resultado.toUpperCase();
                                                            }
                                                %>

                                                <div class="list-group-item sourceDiv" id="line<%= idColeta%>">                                                
                                                    <table  style="width: 100%;">
                                                        <tr>
                                                            <td width="15">
                                                                <input type="checkbox" class="source" name="idColeta<%= idColeta%>" id="check<%= idColeta%>" value="<%= idColeta%>" onclick="verificaCheck(<%= idColeta%>);"/>
                                                            </td>
                                                            <td width="15">   
                                                                <a href="#" data-url="ajax/ajaxLogColeta.jsp?idColeta=<%=idColeta%>" class="popover-ajax"><i class="<%= img%>"></i></a>
                                                            </td> 
                                                            <td width="300">
                                                                <span class="text-muted text-right" style="font-size: 12px; margin-bottom: 5px;">  
                                                                    [<%= cont%>]  &nbsp; &nbsp;
                                                                </span>
                                                                <a data-toggle="popover" title="<%=tel%>" data-content="<%=resultado%>"><%= nomeFantasia%> </a> -  <%=tipo%> 
                                                            </td>
                                                            <td width="150">
                                                                <span class= "xedit" id="<%= idColeta%>;<%= idCol%>">
                                                                    <i class="glyphicon glyphicon-paperclip"></i> <%= obs%>
                                                                </span> 
                                                            </td> 
                                                            <td width="150">
                                                                <span style="color: <%=cor%>;">    
                                                                    <%if (col.getStatusEntrega() == 6) {%> 
                                                                    <a style="color: red;" data-toggle="popover" title="Aguardando à " data-content="<%= hrAguardando%>"><%= col.getNomeStatus()%></a>   
                                                                    <%} else {%><%= col.getNomeStatus()%>
                                                                    <%}%>
                                                                </span>
                                                            </td>
                                                            <td width="60">
                                                                <span class="label label-success pull-left"><%= hrColetado%></span> 
                                                                <span class="label label-default pull-right"><%= hrEstimada%></span> 
                                                            </td>
                                                        </tr>
                                                    </table>
                                                </div>
                                                <%}
                                                    badgMap.put(idColetadorx + "", colAberto);
                                                } else {%>

                                                <div class="list-group-item">
                                                    <span class="text-center">This tab is empty.</span>
                                                </div>


                                                <%}%>
                                            </div>
                                        </div>
                                        <% } %>   

                                        <div class="tab-pane fade in" id="rotaweb">                                            
                                            <div class="list-group">

                                                <%
                                                    int cont2 = 0;
                                                    if (listaColetasWeb.size() > 0) {
                                                        for (int s = 0; s < listaColetasWeb.size(); s++) {
                                                            Coleta col = (Coleta) listaColetasWeb.get(s);
                                                            int idColetaW = col.getIdColeta();
                                                            String nomeFantasia = col.getNomeFantasia();
                                                            String tipo = col.getTipoColeta();
                                                            String obs = col.getObs();
                                                            String dataColeta = sdf3.format(col.getDataHoraColeta());
                                                            String coletador = col.getNomeColetador();
                                                            if (coletador == null) {
                                                                coletador = "<b>Sem Coletador!</b>";
                                                            }
                                                            int ts = col.getTipoSolicitacao();
                                                            String img = "glyphicon glyphicon-road"; // download
                                                            if (ts == 1) {
                                                                img = "glyphicon glyphicon-phone-alt"; // telefone
                                                            } else if (ts == 2) {
                                                                img = "glyphicon glyphicon-road";// download
                                                            } else if (ts == 3) {
                                                                img = "glyphicon glyphicon-globe"; // web
                                                            }
                                                            cont2++;
                                                %>  
                                                <div class="list-group-item sourceDiv" id="line<%= idColetaW%>">                                                
                                                    <table  style="width: 100%;">
                                                        <tr>
                                                            <td width="15">
                                                                <input type="checkbox" class="source" name="idColeta<%= idColetaW%>" id="check<%= idColetaW%>" value="<%= idColetaW%>" onclick="verificaCheck(<%= idColetaW%>);"/>
                                                            </td>
                                                            <td width="15">   
                                                                <a href="#" data-url="ajax/ajaxLogColeta.jsp?idColeta=<%=idColetaW%>" class="popover-ajax"><i class="<%= img%>"></i></a>
                                                            </td> 
                                                            <td width="300">
                                                                <span class="text-muted text-right" style="font-size: 12px; margin-bottom: 5px;">  
                                                                    [<%= idColetaW%>]  &nbsp; &nbsp;
                                                                </span>
                                                                <span>
                                                                    <%= nomeFantasia%> </span>
                                                                - Solicitado às <%= sdf3.format(col.getDataHoraSolicitacao())%> 
                                                            </td>
                                                            <td width="150">
                                                                <span class= "xedit" id="<%= idColetaW%>;<%= idCol%>">
                                                                    <i class="glyphicon glyphicon-paperclip"></i> <%= obs%>
                                                                </span> 
                                                            </td> 
                                                            <td width="150">
                                                                <span >    
                                                                    <%= col.getNomeStatus()%>
                                                                </span>
                                                            </td>
                                                            <td width="60">
                                                                <span class="label label-success pull-left"><%= dataColeta%></span> 
                                                            </td>
                                                        </tr>
                                                    </table>
                                                </div>
                                                <%}
                                                } else {%>

                                                <div class="list-group-item">
                                                    <span class="text-center">This tab is empty.</span>
                                                </div>

                                                <%}%>
                                            </div>
                                        </div>
                                        <div class="tab-pane fade in active" id="">
                                            <div class="list-group">
                                                <div class="list-group-item">
                                                    <span class="text-center">Clique na rota para ver as coletas</span>
                                                </div>
                                            </div>
                                        </div>
                                    </div>  
                                </div>      
                            </div>           
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <script type="text/javascript">
            var isW = false;
            var idColetOld = 0;
            function pesquisaSituacao(situ) {
                var dataP = $('#dataPesquisa').val();
                window.location.replace('pesquisar_b_1.jsp?dataPesquisa=' + dataP + '&situacao=' + situ);
            }

            function alterarColetador(idColtdr) {

                var selected_value = '';
                $(".source:checked").each(function () {
                    selected_value += ',' + ($(this).val());
                });
                selected_value = selected_value.substring(1);
                console.log(selected_value);
                $.post("../../ServAlterarColetadorColeta",
                        {
                            idColetador: idColtdr,
                            coletas: selected_value,
                            isweb: isW
                        },
                function (data, status) {
                    //console.log("Data: " + data + "\nStatus: " + status);
                    window.location.replace('pesquisar_b_1.jsp?dataPesquisa=<%= dataPesquisa%>&idColetador=' + idColetOld);
                });
            }

            function alterarColetas(status) {

                var selected_value = '';
                $(".source:checked").each(function () {
                    selected_value += ',' + ($(this).val());
                });
                // console.log(selected_value);
                $.post("../../ServAlterarStatusColeta",
                        {
                            statusEntrega: status,
                            coletas: selected_value
                        },
                function (data, status) {
                    //  console.log("Data: " + data + "\nStatus: " + status);
                    window.location.replace('pesquisar_b_1.jsp?dataPesquisa=<%= dataPesquisa%>&idColetador=' + idColetOld);
                });
            }

            function verificaCheck(sel) {
                var csel = 'check' + sel;
                var asel = 'line' + sel;
                if (document.getElementById(csel).checked === true) {
                    document.getElementById(asel).style.backgroundColor = 'LightYellow';
                } else {
                    document.getElementById(asel).style.backgroundColor = 'white';
                }
                mostraBotoes();
                if ($('#listaColetas input:checked').length > 0) {

                } else {
                    if (($('#listaBaixa').is(":visible"))) {
                        $('#listaBaixa').toggleClass('hidden');
                    }
                    if (($('#listaColetador').is(":visible"))) {
                        $('#listaColetador').toggleClass('hidden');
                    }
                }


            }

            function mostraBotoes() {

                if ($('#listaColetas input:checked').length > 0) {

                    if (!($('#btMais').is(":visible"))) {
                        $('#btMais').toggleClass('hidden');
                    }
                    if (($('#rotaFixa').is(":visible"))) {
                        $('#rotaFixa').toggleClass('hidden');
                    }
                    if (($('#printMap').is(":visible"))) {
                        $('#printMap').toggleClass('hidden');
                    }
                    if (($('#printRota').is(":visible"))) {
                        $('#printRota').toggleClass('hidden');
                    }


                } else {
                    if (($('#btMais').is(":visible"))) {
                        $('#btMais').toggleClass('hidden');
                    }
                    if (($('#listaColetador').is(":visible"))) {
                        $('#listaColetador').toggleClass('hidden');
                    }

                    if (!($('#rotaFixa').is(":visible"))) {
                        $('#rotaFixa').toggleClass('hidden');
                    }
                    if (!($('#printMap').is(":visible"))) {
                        $('#printMap').toggleClass('hidden');
                    }
                    if (!($('#printRota').is(":visible"))) {
                        $('#printRota').toggleClass('hidden');
                    }




                }
            }

            function mostraListaColetador() {
                if (!($('#listaColetador').is(":visible"))) {
                    $('#listaColetador').toggleClass('hidden');
                }
                if (($('#listaBaixa').is(":visible"))) {
                    $('#listaBaixa').toggleClass('hidden');
                }
            }
            function mostraListaBaixa() {
                //if (!isW) {// não mostrar lista Status para Baixa caso seja WEB
                if (!($('#listaBaixa').is(":visible"))) {
                    $('#listaBaixa').toggleClass('hidden');
                }
                //  }
                if (($('#listaColetador').is(":visible"))) {
                    $('#listaColetador').toggleClass('hidden');
                }

            }

            function clickRota(idC) {
                idColetOld = idC;
                if (idC === 0) {
                    isW = true;
                } else {
                    isW = false;
                }
                $('input:checkbox').prop('checked', false);
                $('.sourceDiv').each(function () {
                    var id = $(this).attr("id");
                    document.getElementById(id).style.backgroundColor = 'white';
                });
                mostraBotoes();
                if (($('#listaBaixa').is(":visible"))) {
                    $('#listaBaixa').toggleClass('hidden');
                }
                if (($('#listaColetador').is(":visible"))) {
                    $('#listaColetador').toggleClass('hidden');
                }

                var rota = 'imprimirRota.jsp?idColetador=' + idC + '&dataPesquisa=<%= dataPesquisa%>';
                var mapa = 'gerarMapaRota_New.jsp?idColetador=' + idC + '&dataPesquisa=<%= dataPesquisa%>';
                $('#printRota').attr('href', rota);
                $('#printMap').attr('href', mapa);
            }

            $(function () {
                $('[data-toggle="popover"]').popover()

            });
            
            function verificaLoginDialog(retorno) {
                bootbox.dialog({
                    title: "Acesso Restrito!",
                    message: retorno,
                    animate: true,
                    //onEscape: true,
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

            function submitForm(servlet) {
                if (confirm('Tem certeza que deseja alterar as coletas selecionadas?')) {
                    var form = document.formColetas;
                    form.action = servlet;
                    form.submit();
                } else {
                    return false;
                }
            }

            $('#checkAll').click(function () {
                $('.active input:checkbox').prop('checked', this.checked);
                mostraBotoes();
                $('.active .sourceDiv').each(function () {
                    var id = $(this).attr("id");
                    if (!($('#checkAll').prop('checked') === true)) {
                        document.getElementById(id).style.backgroundColor = 'white';
                    } else {
                        document.getElementById(id).style.backgroundColor = 'LightYellow';
                    }
                });
                if ($('#listaColetas input:checked').length > 0) {

                } else {
                    if (($('#listaBaixa').is(":visible"))) {
                        $('#listaBaixa').toggleClass('hidden');
                    }
                    if (($('#listaColetador').is(":visible"))) {
                        $('#listaColetador').toggleClass('hidden');
                    }
                }

            });
            function escreveBadge(idDOM, HTML) {
                $('#' + idDOM).html(HTML);
            }


            jQuery(document).ready(function () {

                $("#dataPesquisa").datepicker({
                    showAnim: 'slideDown',
                    maxDate: new Date(),
                    numberOfMonths: 3,
                    onClose: function (selectedDate) {
                        $("#data2").datepicker("option", "minDate", selectedDate);
                    }
                });
                $('[id^=detail-]').hide();
                $('#detail-1').show();
                $('.toggle').click(function () {
                    $input = $(this);
                    $target = $('#' + $input.attr('data-toggle'));
                    $target.slideToggle();
                });
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
                
                $("a.popover-ajax").each(function () {
                    $(this).popover({
                        trigger: "focus",
                        placement: function (context, source) {
                            var obj = $(source);
                            $.ajax({
                                method: "GET",
                                url: obj.data("url"),
                                dataType: "text"
                            })
                                    .done(function (msg) {
                                        $(context).html(msg);
                                    });
                        },
                        html: true,
                        content: "loading"
                    });
                });
                
                $('body').on('click', function (e) {
                    $('[data-toggle="popover"]').each(function () {
                        //the 'is' for buttons that trigger popups
                        //the 'has' for icons within a button that triggers a popup
                        if (!$(this).is(e.target) && $(this).has(e.target).length === 0 && $('.popover').has(e.target).length === 0) {
                            $(this).popover('hide');
                        }
                    });
                });
                
                $("#dataPesquisa").datepicker({
                    showAnim: 'slideDown',
                    numberOfMonths: 1
                });
                fechaMsg();
            <%
                for (Map.Entry<String, Integer> entry : badgMap.entrySet()) {
                    if (entry.getValue() != 0) {%>
                escreveBadge("badgeRota<%=entry.getKey()%>",<%=entry.getValue()%>);
            <%}

                            }%>


            <%if (idCol != 0) {%>
                $("#aba<%=idCol%>").click();
            <%} else {%>
                $("#abaWeb").click();
            <% }%>

            }
            );
        </script>
    </body>
</html>
<%}%>



