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
                                                        <th width="60">Nº</th>
                                                        <th>Nome Fantasia</th>
                                                        <th width="270">Serviços ECT</th>
                                                        <th width="125">Vigência</th>
                                                        <th width="300">Msg. do SigepWEB</th>
                                                        <th class="no-sort" width="80">Config.</th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <%--
                                                    <% if (log != null) {
                                                            String[] mgFalha = log.getMsgFalha().split(";");
                                                            String[] cf = log.getCodFalha().split(";");
                                                            for (int i = 0; i < mgFalha.length; i++) {
                                                                String falha = mgFalha[i];
                                                                String[] cFalha = cf[i].split("@");
                                                                if (cFalha != null && cf[i].contains("@")) {
                                                                    String nome = "";
                                                                    if (cFalha.length == 2) {
                                                                        nome = cFalha[1];
                                                                    }
                                                    %>   
                                                    <tr>
                                                        <td align="right"><%= cFalha[0]%></td>
                                                        <td><%= nome%></td>
                                                        <td>- - -</td>
                                                        <td>Falha no SigepWEB</td>
                                                        <td style="color:red;"><%= falha%></td>
                                                        <td align="center"><a class="btn btn-sm btn-warning" href="cliente_contrato_b.jsp?idCliente=<%= cFalha[0]%>" ><i class="fa fa-lg fa-pencil-square-o"></i></a></td>
                                                    </tr>
                                                    <%}
                                                            }%>
                                                    <%
                                                        String[] serv = log.getMsgSucesso().split(";");
                                                        String[] cod = log.getCodSucesso().split(";");
                                                        for (int i = 0; i < cod.length; i++) {
                                                            String[] c = cod[i].split("@");

                                                            String s = "";
                                                            String[] saux = serv[i].split("@");
                                                            ArrayList<Integer> listaServ = ContrClienteContrato.consultaContratoCliente(Integer.parseInt(c[0]), nomeBD);
                                                            for (String se : saux) {
                                                                if (se.contains("-")) {
                                                                    String[] aux = se.split(" - ");
                                                                    int codect = Integer.parseInt(aux[0]);
                                                                    if(codect != 10138 && codect != 10065){
                                                                        if (listaServ.contains(codect)) {
                                                                            s += "<span style='color:green;'>" + se + "</span><br/>";
                                                                        } else {
                                                                            s += "<a style='color:red;' class='tip'>" + se + " <span>Serviço não cadastrado no contrato do cliente do Portal Postal.</span></a><br/>";
                                                                        }
                                                                    }
                                                                } else {
                                                                    se += se;
                                                                }
                                                            }

                                                            Clientes cli = contrCliente.consultaClienteById(Integer.parseInt(c[0]), nomeBD);
                                                            if(cli != null){

                                                            String cnpj = cli.getCnpj().trim();
                                                            String cnpjSara = c[2].trim();
                                                            
                                                            String codAdm = cli.getCodAdministrativo();
                                                            try{
                                                                codAdm = Integer.parseInt(cli.getCodAdministrativo().trim()) + "";
                                                            }catch(Exception e){}
                                                            String codAdmSara = c[3];
                                                            try{
                                                                codAdmSara = Integer.parseInt(c[3].trim()) + "";
                                                            }catch(Exception e){}
                                                            String dtVg = cli.getDtVigenciaFimContrato()+"";
                                                            try{
                                                                dtVg = sdf.format(cli.getDtVigenciaFimContrato());
                                                            }catch(Exception e){}
                                                                                                                        
                                                            String dataVg = c[5];
                                                            //String ano = c[6];
                                                            //String uf = c[7];
                                                            String msgm = "";
                                                            String cor = " style='color:green;'";
                                                            if (dtVg != null && !dtVg.equals(dataVg)) { 
                                                                msgm += "<br/>Data de Vigência cadastrada diferente do SARA!<br/>"
                                                                        + "<b>Data Vigência SARA: </b>" + dataVg
                                                                        + "<br/><b>Data Vigência Portal Postal: </b>" + dtVg;
                                                                cor = " style='color:red;'";
                                                            }
                                                            if (codAdm != null && !codAdm.equals(codAdmSara)) { 
                                                                msgm += "<br/>Cód. Adm. cadastrado diferente do SARA!<br/>"
                                                                        + "<b>Cód Adm. SARA: </b>" + codAdmSara
                                                                        + "<br/><b>Cód. Adm. Portal Postal: </b>" + codAdm;
                                                                cor = " style='color:red;'";
                                                            }
                                                            if (!cnpj.equals(cnpjSara)) {
                                                                msgm += "<br/>CNPJ cadastrado diferente do SARA!<br/>"
                                                                        + "<b>CNPJ SARA: </b>" + cnpjSara
                                                                        + "<br/><b>CNPJ Portal Postal: </b>" + cnpj;
                                                                cor = " style='color:red;'";
                                                            }
                                                            if (msgm.equals("")) {
                                                                msgm = "SigepWEB OK!";
                                                            }

                                                            String status = "Suspenso/Cancelado";
                                                            if (c[4].equals("1")) {
                                                                status = "Válido até " + dataVg;
                                                            }
                                                    %>
                                                    <tr>
                                                        <td align="right"><%= c[0]%>
                                                            <input type="hidden" name="idsCli" id="idsCli" value="<%= c[0]%>" />
                                                        </td>
                                                        <td><%= c[1]%></td>
                                                        <td><%= s%></td>
                                                        <td><%= status%></td>
                                                        <td <%=cor%>><%= msgm%></td>
                                                        <td align="center"><a href="cliente_contrato_b.jsp?idCliente=<%= c[0]%>" class="btn btn-sm btn-warning" ><i class="fa fa-lg fa-pencil-square-o"></i></a></td>
                                                    </tr>
                                                    <%}}}%>
                                                    --%>
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

                                                            numContrato = Long.parseLong(numContrato) + "";
                                                            cartaoPostagem = Long.parseLong(cartaoPostagem) + "";
                                                    %>
                                                    <tr>
                                                        <td align="right">
                                                            <%= clie.getCodigo() %>
                                                            <input type="hidden" name="idsCli" id="idsCli" value="<%= clie.getCodigo() %>" />
                                                            <input type="hidden" name="contrato<%= clie.getCodigo() %>" id="contrato<%= clie.getCodigo() %>" value="<%= numContrato %>" />
                                                            <input type="hidden" name="cartao<%= clie.getCodigo() %>" id="cartao<%= clie.getCodigo() %>" value="<%= cartaoPostagem %>" />
                                                            <input type="hidden" name="login<%= clie.getCodigo() %>" id="login<%= clie.getCodigo() %>" value="<%= clie.getLogin_sigep() %>" />
                                                            <input type="hidden" name="senha<%= clie.getCodigo() %>" id="senha<%= clie.getCodigo() %>" value="<%= clie.getSenha_sigep() %>" />
                                                        </td>
                                                        <td>
                                                            <%= clie.getNome() %>
                                                        </td>
                                                        <td>
                                                            <div id="servicos<%= clie.getCodigo() %>">---</div>
                                                        </td>
                                                        <td>
                                                            <div id="vigencia<%= clie.getCodigo() %>">---</div>
                                                        </td>
                                                        <td>
                                                            <div id="mensagem<%= clie.getCodigo() %>">---</div>
                                                        </td>
                                                        <td align="center"><a href="cliente_contrato_b.jsp?idCliente=<%= clie.getCodigo() %>" class="btn btn-sm btn-warning" target="_blank" ><i class="fa fa-lg fa-pencil-square-o"></i></a></td>
                                                    </tr>
                                                    <%}}%>
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

            $(document).ready(function() {
                LoadDataTablesScripts(AllTables);
            });

            function verificarContratos() {
                
                 $.ajax({
                            method: "POST",
                            url: "ajax/atualizaLogContratoSigepWEB.jsp",
                            data: {idUser: '<%=usrSessao.getIdUsuario() %>', nomeUser: '<%=usrSessao.getNome()%>'},
                            dataType: 'html'
                        });
                
                
                $("input[name='idsCli']").each(function() {  // first pass, create name mapping
                    var idCli =  $(this).attr('value');             
                    //if(idCli === '2300' || idCli === '2304' || idCli === '2285'){
                        //console.log('entro ' + idCli);
                        var contrato = document.getElementById('contrato'+idCli).value;
                        var cartao = document.getElementById('cartao'+idCli).value;
                        var loginSigep = document.getElementById('login'+idCli).value;
                        var senhaSigep = document.getElementById('senha'+idCli).value;
                        $.ajax({
                            method: "POST",
                            url: "ajax/pesquisaClienteContratoSigepWEB.jsp",
                            data: {numContrato: contrato, cartaoPostagem: cartao, loginSigep: loginSigep, senhaSigep: senhaSigep},
                            dataType: 'html'
                        }).done(function(retorno) {
                            var aux = retorno.split(';');
                            if (aux[0] !== 'erro') {
                                if (aux[0] === '1') {                                    
                                    var msg_dialog = "SigepWEB OK!";
                                    var validade = "Válido até " + aux[7];
                                    var servicos = aux[9].split('@').join('<br/>') ;
                                    
                                    //console.log(idCli +" - ok: " + validade);
                                    document.getElementById('servicos'+idCli).innerHTML = servicos;
                                    document.getElementById('vigencia'+idCli).innerHTML = validade;
                                    document.getElementById('mensagem'+idCli).innerHTML = msg_dialog;
                                } else {
                                    //console.log(idCli +" - erro: " + aux[1]);
                                    document.getElementById('servicos'+idCli).innerHTML = '';
                                    document.getElementById('vigencia'+idCli).innerHTML = 'Falha no SigepWEB';
                                    document.getElementById('mensagem'+idCli).innerHTML = aux[1];
                                }
                            } else {
                                //console.log(idCli +" - erro: " + aux[1]);
                                document.getElementById('servicos'+idCli).innerHTML = '';
                                document.getElementById('vigencia'+idCli).innerHTML = 'Falha no SigepWEB';
                                document.getElementById('mensagem'+idCli).innerHTML = aux[1];
                            }
                        });
                    
                    //}
                });
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
                    callback: function(result) {
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
