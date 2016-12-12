<%-- 
    Document   : cliente_log_contrato
    Created on : Apr 16, 2015, 10:14:31 AM
    Author     : Fernando
--%>
<%@page import="Util.FormataString"%>
<%@page import="java.util.ArrayList"%>
<%@page import="Controle.ContrClienteContrato"%>
<%@page import="Controle.contrCliente"%>
<%@page import="Entidade.Clientes"%>
<%@page import="Entidade.LogAtualizacaoContratos"%>
<%@page import="Controle.ContrLogAtualizacaoContrato"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%

    Usuario usrSessao = (Usuario) session.getAttribute("agf_usuario");
    if (usrSessao == null) {
        response.sendRedirect("../../index.jsp?msgLog=3");
    } else if (usrSessao.getListaAcessosPortalPostal().contains("405")) {
        response.sendRedirect("../../NewTemplate/Dashboard/index.jsp?msg=Usuario sem permissao!");
    } else {

        int idNivelDoUsuario = (Integer) session.getAttribute("nivel");
        String nomeBD = (String) session.getAttribute("empresa");
        int idEmpresa = (Integer) session.getAttribute("idEmpresa");
        empresas emp = (empresas) session.getAttribute("emp");
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy HH:mm");

        String msga = (String) session.getAttribute("msg2");
        session.setAttribute("msg2", null);
        LogAtualizacaoContratos log = ContrLogAtualizacaoContrato.consultaUltimoLog(nomeBD);
%>

<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <title>Portal Postal</title>
        <%@ include file="../includes/Css_js.jsp" %>
        <style>
            a.tip {
                border-bottom: 1px dashed;
                text-decoration: none
            }
            a.tip:hover {
                cursor: help;
                position: relative
            }
            a.tip span {
                display: none
            }
            a.tip:hover span {
                border: #c0c0c0 1px dotted;
                padding: 10px;
                display: block;
                z-index: 100;
                background: url(../images/status-info.png) #f0f0f0 no-repeat 100% 5%;
                left: 0px;
                margin: 10px;
                position: absolute;
                top: 15px;
                white-space: nowrap;
                text-decoration: none;
                font-weight:bold;
            }
        </style>
    </head>
    <body onload="fechaMsg();">   
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
                                <h4 class="page-header"><b class="text-primary"><i class="fa fa-gears"></i> Cadastros</b> > <small>Verificação de Contratos</small></h4>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-12">                                                                
                                <% if (log != null) {%>
                                <div class="alert alert-info">
                                    <i class="fa fa-info-circle fa-lg fa-spc"></i>&nbsp;&nbsp;&nbsp;<b>Última Verificação:</b> <%= sdf2.format(log.getDataHora())%>
                                </div>
                                <%}%>                                
                                <ul class="list-unstyled" >
                                    <li class="list-group-item list-group-heading">
                                        <label>VERIFICAR DADOS DOS CONTRATOS ECT</label>
                                    </li>
                                    <li class="list-group-item">
                                        <%--<button type="button" onclick="verificaComErro();" class="btn btn-primary"><i class="fa fa-refresh fa-lg fa-spc"></i> VERIFICAR CONTRATOS ECT</button>--%>
                                        <button type="button" onclick="verificarContratos();" class="btn btn-primary"><i class="fa fa-refresh fa-lg fa-spc"></i> VERIFICAR CONTRATOS ECT</button>
                                        <div style="margin-right: 50%; float: right"><b id="verificando" class="hidden">VERIFICANDO ..... </b><span id="faltam" style="font-weight: bold"></span></div>
                                        <input type="hidden" name="comErro" id="comErro" value="0"/>                                                 
                                    </li>
                                </ul>                             
                                <div class="panel panel-default">
                                    <div class="panel-heading">Log da Última Verificação dos Contratos ECT</div>
                                    <div class="panel-body">
                                        <div class="dataTable_wrapper no-padding">
                                            <table class="table table-striped table-bordered table-hover table-condensed" id="dataTables-example">
                                                <thead>
                                                    <tr>
                                                        <th width="40">Nº</th>
                                                        <th>Nome Fantasia</th>
                                                        <th width="270">Serviços ECT</th>
                                                        <th width="300">Cadastro AGF | SigepWEB</th>
                                                        <th width="200">Msg. do SigepWEB</th>
                                                        <th class="no-sort" width="80">Config.</th>
                                                    </tr>
                                                </thead>
                                                <tbody>

                                                    <%
                                                        ArrayList<Clientes> listaCliente = Controle.contrCliente.getNomeCodigoMetodo(nomeBD, false);
                                                        for (Clientes clie : listaCliente) {
                                                            int temContrato = clie.getTemContrato();
                                                            String numContrato = clie.getNumContrato();
                                                            String cartaoPostagem = clie.getCartaoPostagem();

                                                            if (temContrato == 1
                                                                    && clie.getLogin_sigep() != null
                                                                    && !clie.getLogin_sigep().trim().equals("")
                                                                    && clie.getSenha_sigep() != null
                                                                    && !clie.getSenha_sigep().trim().equals("")) {

                                                                ///  numContrato = Long.parseLong(numContrato) + "";
                                                                ///  cartaoPostagem = Long.parseLong(cartaoPostagem) + "";
                                                                if (cartaoPostagem == null || cartaoPostagem.trim().equals("")) {
                                                                    cartaoPostagem = "0";
                                                                }
                                                                if (numContrato == null || numContrato.trim().equals("")) {
                                                                    numContrato = "0";
                                                                }
                                                                String dtVigencia = "---";
                                                                if (clie.getDtVigenciaFimContrato() != null) {
                                                                    dtVigencia = sdf.format(clie.getDtVigenciaFimContrato());
                                                                }
                                                    %>
                                                    <tr>
                                                        <td align="right">
                                                            <%= clie.getCodigo()%>
                                                            <input type="hidden" name="idsCli" id="idsCli" value="<%= clie.getCodigo()%>" />
                                                            <input type="hidden" name="contrato<%= clie.getCodigo()%>" id="contrato<%= clie.getCodigo()%>" value="<%= numContrato%>" />
                                                            <input type="hidden" name="cartao<%= clie.getCodigo()%>" id="cartao<%= clie.getCodigo()%>" value="<%= cartaoPostagem%>" />
                                                            <input type="hidden" name="login<%= clie.getCodigo()%>" id="login<%= clie.getCodigo()%>" value="<%= clie.getLogin_sigep()%>" />
                                                            <input type="hidden" name="senha<%= clie.getCodigo()%>" id="senha<%= clie.getCodigo()%>" value="<%= clie.getSenha_sigep()%>" />
                                                        </td>
                                                        <td>
                                                            <%= clie.getNome()%>
                                                        </td>
                                                        <td>
                                                            <div id="servicos<%= clie.getCodigo()%>">---</div>
                                                        </td>
                                                        <td>
                                                            <div id="vigencia<%= clie.getCodigo()%>">
                                                                <b>cnpj:</b> <span style="color: green" id="cnpj_atual<%= clie.getCodigo()%>"><%= clie.getCnpj()%></span> | <span style="color: blueviolet;" id="cnpj<%= clie.getCodigo()%>"></span><br/>
                                                                <b>cod DR:</b> <span style="color: green" id="codDR_atual<%= clie.getCodigo()%>"><%= clie.getCodDiretoria()%></span> | <span style="color: blueviolet;" id="codDR<%= clie.getCodigo()%>"></span><br/>
                                                                <b>vigência:</b> <span style="color: green" id="vige_atual<%= clie.getCodigo()%>"><%= dtVigencia%></span> | <span style="color: blueviolet;" id="vige<%= clie.getCodigo()%>"></span><br/>
                                                                <b>cod. adm</b> <span style="color: green" id="codADM_atual<%= clie.getCodigo()%>" ><%= clie.getCodAdministrativo()%></span> | <span style="color: blueviolet;" id="codADM<%= clie.getCodigo()%>"></span><br/>

                                                            </div>
                                                            <div style="margin-top: 15px;" class="hidden" id="updateContrato<%= clie.getCodigo()%>" ><input class="btn btn-danger" type="button" value="ATUALIZAR CONTRATO" onclick="atualizaDados(<%= clie.getCodigo()%>);"/> </div>
                                                            <div style="margin-top: 15px; font-weight: bold; color: red;" id="msgUpdate<%= clie.getCodigo()%>"> </div>
                                                        </td>

                                                        <td>
                                                            <div id="mensagem<%= clie.getCodigo()%>">---</div>
                                                        </td>
                                                        <td align="center"><a href="cliente_contrato_b.jsp?idCliente=<%= clie.getCodigo()%>" class="btn btn-sm btn-warning" target="_blank" ><i class="fa fa-lg fa-pencil-square-o"></i></a></td>
                                                    </tr>
                                                    <%}
                                                        }%>
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
        <script>

            function AllTables() {
                //StartDataTable('dataTables-example');
                LoadSelect2Script(MakeSelectDataTable('dataTables-example'));
                fechaMsg();
            }

            $(document).ready(function () {
                LoadDataTablesScripts(AllTables);
            });
            var tid = setInterval(mycode, 1000);
            function mycode() {
                // do some stuff...
                // no need to recall the function (it's an interval, it'll loop forever)
                console.log('1');

                console.log($.active);
                if ($.active > 0) {
                    $('#verificando').toggleClass('hidden');
                    $('#faltam').text('[ faltam: ' + $.active + ' ]');
                }
                if ($.active == 0) {
                    $('#faltam').text('');
                }


            }
            function abortTimer() { // to be called when you want to stop the timer
                clearInterval(tid);
            }
            function atualizaDados(codCliente) {

                var cnpj_atual = $('#cnpj_atual' + codCliente).text();
                var cnpj = $('#cnpj' + codCliente).text();
                var codDR_atual = $('#codDR_atual' + codCliente).text();
                var codDR = $('#codDR' + codCliente).text();
                var vige_atual = $('#vige_atual' + codCliente).text();
                var vige = $('#vige' + codCliente).text();
                var codADM_atual = $('#codADM_atual' + codCliente).text();
                var codADM = $('#codADM' + codCliente).text();

                /* console.log(cnpj_atual);
                 console.log(cnpj);
                 console.log('-------------');
                 console.log(codDR_atual);
                 console.log(codDR);
                 console.log('-------------');
                 console.log(vige_atual);
                 console.log(vige);
                 console.log('-------------');
                 console.log(codADM_atual);
                 console.log(codADM);
                 console.log('-------------');*/
                if ($.active > 0) {
                    console.log('aguarde');
                    alert('Aguarde a verificação terminar');
                } else {
                    $.post("ajax/atualizaContratoSigepWEB.jsp", {cnpj: cnpj, codDR: codDR, vige: vige, codADM: codADM, codCliente: codCliente}, function (result) {
                        console.log(result);
                        if (result.trim() == 'success') {
                            $('#cnpj_atual' + codCliente).text(cnpj);
                            $('#codDR_atual' + codCliente).text(codDR);
                            $('#vige_atual' + codCliente).text(vige);
                            $('#codADM_atual' + codCliente).text(codADM);
                            $('#updateContrato' + codCliente).toggleClass('hidden');
                            $('#msgUpdate' + codCliente).text('Contrato Atualizado');
                        }
                    });
                }
                /* $.ajax({
                 method: "POST",
                 url: "ajax/atualizaContratoSigepWEB.jsp",
                 data: {cnpj: cnpj, codDR: codDR, vige: vige, codADM: codADM, codCliente: codCliente},
                 dataType: 'text'
                 }).done(function (retorno) {
                 console.log(retorno);
                 
                 });*/
            }

            function verificarContratos() {

                $.ajax({
                    method: "POST",
                    url: "ajax/atualizaLogContratoSigepWEB.jsp",
                    data: {idUser: '<%=usrSessao.getIdUsuario()%>', nomeUser: '<%=usrSessao.getNome()%>'},
                    dataType: 'html'
                });



                $("input[name='idsCli']").each(function () {  // first pass, create name mapping

                    var idCli = $(this).attr('value');
                    var contrato = document.getElementById('contrato' + idCli).value;
                    var cartao = document.getElementById('cartao' + idCli).value;
                    var loginSigep = document.getElementById('login' + idCli).value;
                    var senhaSigep = document.getElementById('senha' + idCli).value;
                    $.ajax({
                        method: "POST",
                        url: "ajax/pesquisaClienteContratoSigepWEB.jsp",
                        data: {numContrato: contrato, cartaoPostagem: cartao, loginSigep: loginSigep, senhaSigep: senhaSigep},
                        dataType: 'html'
                    }).done(function (retorno) {

                        var aux = retorno.split(';');
                        if (aux[0] !== 'erro') {
                            if (aux[0] === '1') {
                                var msg_dialog = "SigepWEB OK!";
                                var validade = aux[7];
                                var cnpj = aux[8];
                                var codDR = aux[1];
                                var codADM = aux[6];
                                var servicos = aux[9].split('@').join('<br/>');

                                //console.log(idCli +" - ok: " + validade);
                                document.getElementById('servicos' + idCli).innerHTML = servicos;

                                // document.getElementById('vigencia'+idCli).innerHTML = retorno;
                                document.getElementById('cnpj' + idCli).innerHTML = cnpj;
                                highlightDiferenca('cnpj', idCli, $('#cnpj_atual' + idCli).text(), cnpj);

                                document.getElementById('codDR' + idCli).innerHTML = codDR;
                                highlightDiferenca('codDR', idCli, $('#codDR_atual' + idCli).text(), codDR);

                                document.getElementById('vige' + idCli).innerHTML = validade;
                                highlightDiferenca('vige', idCli, $('#vige_atual' + idCli).text(), validade);

                                document.getElementById('codADM' + idCli).innerHTML = codADM;
                                highlightDiferenca('codADM', idCli, $('#codADM_atual' + idCli).text(), codADM);

                                document.getElementById('mensagem' + idCli).innerHTML = msg_dialog;

                            } else {
                                //console.log(idCli +" - erro: " + aux[1]);
                                document.getElementById('servicos' + idCli).innerHTML = '';
                                document.getElementById('vigencia' + idCli).innerHTML = 'Falha no SigepWEB';
                                document.getElementById('mensagem' + idCli).innerHTML = aux[1];
                            }
                        } else {
                            //console.log(idCli +" - erro: " + aux[1]);
                            document.getElementById('servicos' + idCli).innerHTML = '';
                            document.getElementById('vigencia' + idCli).innerHTML = 'Falha no SigepWEB';
                            document.getElementById('mensagem' + idCli).innerHTML = aux[1];
                        }
                    });

                    //}
                });
            }
            function highlightDiferenca(prefix, idCli, valorAGF, valorSigep) {
                console.log(valorAGF);
                console.log(valorSigep);
                if (valorAGF.trim() != valorSigep.trim()) {
                    console.log('dif');
                    $('#' + prefix + idCli).css('color', 'red');
                    $('#' + prefix + idCli).css('font-weight', 'bold');
                    $('#' + prefix + idCli).css('background-color', '#FFFF00');
                    $('#updateContrato' + idCli).removeClass('hidden');
                }
            }

            function verificaComErro() {
                bootbox.confirm({
                    title: 'Verificação de Contratos',
                    message: 'Este processo pode demorar alguns minutos.<br/><br/>Deseja verificar os contratos dos clientes?',
                    buttons: {
                        'cancel': {
                            label: '<i class="fa fa-lg fa-times fa-spc"></i> CANCELAR',
                            className: 'btn btn-default pull-left'
                        },
                        'confirm': {
                            label: '<i class="fa fa-lg fa-refresh fa-spc"></i> VERIFICAR CONTRATOS',
                            className: 'btn btn-primary pull-right'
                        }
                    },
                    callback: function (result) {
                        if (result) {
                            bootbox.hideAll();
                            waitMsg();
                            document.getElementById("comErro").value = 0;
                            document.formAtualiza.submit();
                        }
                    }
                });
            }
        </script>
    </body>
</html>
<%}%>
