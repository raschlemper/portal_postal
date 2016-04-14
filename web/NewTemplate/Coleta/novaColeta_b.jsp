
<%@page import="Util.XTrustProvider"%>
<%@page import="org.dom4j.Node"%>
<%@page import="java.util.List"%>
<%@page import="org.dom4j.Document"%>
<%@page import="java.io.StringReader"%>
<%@page import="org.dom4j.io.SAXReader"%>
<%@page import="Entidade.Clientes"%>
<%@page import="Controle.ContrClienteEtiquetas"%>
<%@page import="Controle.contrCliente"%>
<%@page import="Emporium.Controle.ContrPreVenda"%>
<%@page import="Entidade.PreVenda"%>
<%@page import="Entidade.ClienteLogEtiqueta"%>
<%@ page import = "java.text.DateFormat,java.util.ArrayList,java.sql.Timestamp,java.util.Calendar, java.util.GregorianCalendar, java.util.Date, java.text.SimpleDateFormat"%>

<%
    response.setHeader("Cache-Control", "no-cache"); //HTTP 1.1
    response.setHeader("Pragma", "no-cache"); //HTTP 1.0
    response.setDateHeader("Expires", 0); //prevent caching at the proxy server

    SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm");
    String nomeBD = (String) session.getAttribute("empresa");
    if (nomeBD == null) {
        response.sendRedirect("../index.jsp?msgLog=3");
    } else {

        int idEmpresa = (Integer) session.getAttribute("idEmpresa");
        int idUsuario = (Integer) session.getAttribute("idUsuario");
        int idCliente = 0;
        if (request.getParameter("idCliente") != null && !request.getParameter("idCliente").equals("")) {
            idCliente = Integer.parseInt(request.getParameter("idCliente"));
        }

%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <title>Portal Postal</title>
        <%@ include file="../includes/Css_js.jsp" %>
    </head>
    <body>
        <script>
            waitMsg();
        </script>
        <jsp:include page="../includes/navBarTop.jsp"></jsp:include>
            <div id="wrapper">
            <%@ include file="../includes/menu_agencia_bootstrap.jsp" %>

            <div id="page-content-wrapper">
                <div class="container-fluid">
                    <div id="page-wrapper">
                        <form name="form1" action="../../ServInserirColeta" method="post">

                        <div class="row">
                            <div class="col-md-12">
                                <h4 class="page-header"><b class="text-primary"><i class="fa fa-truck"></i> Coleta</b> > <small>Nova Coleta</small></h4>
                            </div>
                        </div>
                        <div class="row">
                            
                            <div class="col-xs-12" >
                                <ul class="list-group">
                                    <li class="list-group-item list-group-heading">
                                        <label>Cliente Solicitante</label>
                                    </li>
                                    <li class="list-group-item">
                                        <div class="row form-horizontal">
                                            <div class="col-xs-6">
                                                <select name="idCliente" id="idCliente">
                                                    <option value="sel">-- Selecione um Cliente --</option>
                                                    <%
                                                        ArrayList<Clientes> listaCliente = Controle.contrCliente.getNomeCodigoMetodo(nomeBD, false);
                                                        for (int i = 0; i < listaCliente.size(); i++) {
                                                            if (idCliente == listaCliente.get(i).getCodigo()) {
                                                                out.println("<option  selected value='" + listaCliente.get(i).getCodigo() + "'>[" + listaCliente.get(i).getCodigo() + "] " + listaCliente.get(i).getNome() + "</option>");
                                                            } else {
                                                                out.println("<option  value='" + listaCliente.get(i).getCodigo() + "'>[" + listaCliente.get(i).getCodigo() + "] " + listaCliente.get(i).getNome() + "</option>");
                                                            }
                                                        }
                                                    %>
                                                </select>
                                            </div>                                              
                                        </div>
                                    </li>   
                                    <%
                                        for (int i = 0; i < listaCliente.size(); i++) {
                                    %>
                                    <li class="list-group-item hidden" id="end<%=listaCliente.get(i).getCodigo()%>">
                                        <div class="row form-horizontal">
                                            <div class="col-xs-6">
                                                <div><strong><%= listaCliente.get(i).getNome()%></strong></div>
                                                <div><%=  listaCliente.get(i).getEndereco()%> - <%=  listaCliente.get(i).getBairro()%></div>
                                                <div>Fone:<%=  listaCliente.get(i).getTelefone()%></div>
                                                <div><a href="mailto:#"><%=  listaCliente.get(i).getEmail()%></a></div>
                                            </div>
                                        </div>
                                    </li>
                                    <%}%>
                                </ul>
                            </div>
                        </div>            
                            <div class="row">
                                <div class="col-xs-12" >
                                    <ul class="list-group">
                                        <li class="list-group-item list-group-heading">
                                            <label>Contato Solicitante</label>
                                        </li>
                                        <li class="list-group-item">
                                            <div id="divSelectContato" class="row form-horizontal">
                                                <div id="selectContato" class="col-xs-6">
                                                    <select name="contato" id="contato" disabled >
                                                        <option value="">-- Selecione primeiro um cliente --</option>
                                                    </select>
                                                </div>
                                                <div class="col-xs-2">
                                                    <button type="button" disabled id="addContato" class=" btn btn-warning btn-sm" onclick ="adicionarContato();" > <i class="fa fa-plus fa-lg fa-spc"></i> INSERIR NOVO</button>
                                                </div>
                                            </div>
                                        </li>
                                        <li class="list-group-item">
                                            <div class="row form-horizontal">

                                                <div class="col-sm-6 col-md-3">
                                                    <label class="small">Telefone</label>
                                                    <div class="input-group">
                                                        <span class="input-group-addon" ><i class="fa fa-phone"></i></span>
                                                        <input class="form-control" placeholder="telefone" readonly id="telefone" name="telefone" />
                                                    </div>
                                                </div>
                                                <div class="col-sm-6 col-md-3">
                                                    <label class="small">E-mail</label>
                                                    <div class="input-group">
                                                        <span class="input-group-addon" ><i class="fa fa-at"></i></span>
                                                        <input class="form-control" placeholder="email" readonly type="email" readonly id="email" name="email"/>
                                                    </div>
                                                </div>
                                                <div class="col-sm-6 col-md-3">
                                                    <label class="small">Setor</label>
                                                    <div class="input-group">
                                                        <span class="input-group-addon" ><i class="fa fa-circle-thin"></i></span>
                                                        <input class="form-control" placeholder="setor" readonly  type="text" readonly id="setor" name="setor"/>
                                                    </div>
                                                </div>
                                            </div>

                                        </li>
                                    </ul>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-xs-12" >
                                    <ul class="list-group">
                                        <li class="list-group-item list-group-heading">
                                            <label>Dados da Coleta</label>
                                        </li>
                                        <li class="list-group-item">
                                            <div class="row form-horizontal">
                                                <div class="col-sm-6 col-md-3 ">
                                                    <label class="small">Coletador</label>
                                                    <select name="idColetador" id="idColetador">
                                                        <option value="0">-- SELECIONE --</option>
                                                        <%
                                                            int coletadorEventual = Coleta.Controle.contrColetaFixa.consultaColetadorEventualDoCliente(idCliente, nomeBD);
                                                            ArrayList listaColetador = Coleta.Controle.contrColetador.consultaTodosColetadores(nomeBD);
                                                            for (int i = 0; i < listaColetador.size(); i++) {
                                                                Coleta.Entidade.Coletador col = (Coleta.Entidade.Coletador) listaColetador.get(i);
                                                                String nomeColetador = col.getNome();
                                                                int idColetador = col.getIdColetador();
                                                        %>
                                                        <option value="<%= idColetador%>" <%if (coletadorEventual == idColetador) {%> selected <%}%> ><%= nomeColetador%></option>
                                                        <%}%>
                                                    </select> 
                                                </div>
                                                <div class="col-sm-6 col-md-3">
                                                    <label class="small">Tipo da Coleta</label>
                                                    <select name="idTipo" id="idTipo">
                                                        <option value="0">-- SELECIONE --</option>
                                                        <%
                                                            ArrayList listaTipo = Coleta.Controle.contrTipoColeta.consultaTodosTipoColeta(nomeBD);
                                                            for (int i = 0; i < listaTipo.size(); i++) {
                                                                Coleta.Entidade.TipoColeta tip = (Coleta.Entidade.TipoColeta) listaTipo.get(i);
                                                                String nomeTipo = tip.getTipo();
                                                                int idTipo = tip.getIdTipoColeta();
                                                        %>
                                                        <option value="<%= idTipo%>"><%= nomeTipo%></option>
                                                        <%}%>
                                                    </select> 
                                                </div>
                                                <div class="col-sm-6 col-md-3">
                                                    <label class="small">Data da Coleta</label>
                                                    <div class="input-group">
                                                        <input class="form-control" type="text" name="dataColeta" id="dataColeta"  value="<%= sdf1.format(new Date())%>" maxlength="10" onKeyPress="mascara(this, maskData)" />
                                                        <span class="input-group-addon" ><i class="fa fa-calendar"></i></span>
                                                    </div>
                                                </div>
                                                <div class="col-sm-6 col-md-3">
                                                    <label class="small">Hora da Coleta</label>
                                                    <div class="input-group">
                                                        <input class="form-control" type="text" name="horaColeta" id="horaColeta" value="<%= sdf2.format(new Date())%>" maxlength="5" onKeyPress="mascara(this, maskHora)" />
                                                        <span class="input-group-addon" ><i class="fa fa-clock-o"></i></span>
                                                    </div>
                                                </div>
                                            </div>
                                        </li>
                                        <li class="list-group-item">
                                            <div class="row form-horizontal">
                                                <div class="col-xs-12">
                                                    <label class="small">Digite abaixo as possíveis observações para esta coleta:</label>
                                                    <div class="input-group">
                                                        <span class="input-group-addon" ><i class="fa fa-exclamation-triangle"></i></span>
                                                        <textarea rows="3" maxlength="100" class="form-control" name="obs" id="textointeracao"></textarea>
                                                    </div>
                                                </div>
                                            </div>
                                        </li>
                                        <li class="list-group-item">                                            
                                            <div class="row form-horizontal">
                                                <div class="col-sm-6 col-md-6 col-lg-6">
                                                    <button type="button" class="btn btn-success" onclick="return (preencherCamposColeta());"><i class="fa fa-save fa-lg fa-spc"></i> SOLICITAR COLETA</button>
                                                </div>
                                            </div>
                                        </li>
                                    </ul>
                                </div>
                            </div>
                                                        
                            <input type="hidden" name="idUsuario" id="idUsuario" value="<%= idUsuario%>" />
                        </form>
                    </div>

                    <%--
                    <div class="row">
                        <div class="col-lg-12">
                            <div class="panel panel-default">
                                <div class="panel-heading" >Resultado da Pesquisa</div>
                                <div class="panel-body">

                                    <div class="dataTable_wrapper">

                                        <table class="table table-striped table-bordered table-hover table-condensed" id="dataTables-etqPend">
                                            <thead>
                                                <tr>
                                                    <th>Nº</th>
                                                    <th>Tipo</th>
                                                    <th>Coletador</th>
                                                    <th>Solicitado em</th>
                                                    <th>Coleta para</th>
                                                    <th>Status da Coleta</th>
                                                    <th>Obs</th>
                                                    <th>Cadastrado por</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <%
                                                    ArrayList listaColetas = Coleta.Controle.contrColeta.consultaUltimasColetasDoCliente(idCliente, nomeBD);
                                                    for (int j = 0; j < listaColetas.size(); j++) {
                                                        Coleta.Entidade.Coleta col = (Coleta.Entidade.Coleta) listaColetas.get(j);
                                                        int idColeta = col.getIdColeta();
                                                        int idTipo = col.getIdTipo();
                                                        String tipo = Coleta.Controle.contrTipoColeta.consultaNomeTipoColetaById(idTipo, nomeBD);
                                                        int idColetador = col.getIdColetador();
                                                        String coletador = Coleta.Controle.contrColetador.consultaNomeColetadoresById(idColetador, nomeBD);
                                                        int idUsuarios = col.getIdUsuario();
                                                        String obs = col.getObs();
                                                        String user = Controle.contrUsuario.consultaNomeUsuarioById(idUsuarios, nomeBD);
                                                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                                                        String dataSolicitacao = sdf.format(col.getDataHoraSolicitacao());
                                                        String dataColeta = sdf.format(col.getDataHoraColeta());
                                                        String dataBaixa = "";
                                                        if (col.getDataHoraBaixa() != null) {
                                                            dataBaixa = sdf.format(col.getDataHoraBaixa());
                                                        }
                                                        int status = col.getStatus();
                                                        String nomeStatus = "";
                                                        switch (status) {
                                                            case 1:
                                                                nomeStatus = "Solicitada";
                                                                break;
                                                            case 2:
                                                                nomeStatus = "Aguardando Coleta";
                                                                break;
                                                            case 3:
                                                                nomeStatus = "Coletado em " + dataBaixa;
                                                                break;
                                                        }
                                                %>
                                                <tr>
                                                    <td><b><%= idColeta%></b></td>
                                                    <td><%= tipo%></td>
                                                    <td><%= coletador%></td>
                                                    <td><%= dataSolicitacao%></td>
                                                    <td><%= dataColeta%></td>
                                                    <td><%= nomeStatus%></td>
                                                    <td><%= obs%></td>
                                                    <td><%= user%></td>
                                                </tr>
                                                <%}%>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    --%>                    
                    
                </div>
            </div>
        </div>
        <div class="hidden" id="aux"></div>


        <script type="text/javascript">
            
             function preencherCamposColeta(){
                var form = document.form1;
                if(form.idCliente.value==="0" || form.idCliente.value==="" || form.idCliente.value==="sel"){
                    alert('Escolha um Cliente para a Coleta!');
                    return false;
                }
                if(form.contato.value==="" | form.contato.value==="-1"){
                    alert('Escolha um contato ou cadastre um novo contato para esta coleta!');
                    return false;
                }
                if(form.idColetador.value==="0"){
                    alert('Escolha o Coletador da Coleta!');
                    return false;
                }
                if(form.idTipo.value==="0"){
                    alert('Escolha o Tipo da Coleta!');
                    return false;
                }
                if(form.dataColeta.value === ""){
                    alert('Preencha a Data da Coleta!');
                    return false;
                }
                if(!valida_data(form.dataColeta)){
                    return false;
                }
                if(!valida_hora(form.horaColeta)){
                    return false;
                }

                form.submit();
            }
            
            

            function adicionarContato() {
                //$("#idCliente").prop('disabled', true);
                var htmlCombo = $("#divSelectContato").html();
                $("#aux").html(htmlCombo);
                //salva o contato
                var novoHtml =
                        "<div class='col-sm-6 col-md-6'>" +
                        "  <div class='input-group'>" +
                        "  <span class='input-group-addon' ><i class='fa fa-user'></i></span>" +
                        "    <input class='form-control' placeholder='Nome' id='contato' name='contato' />" +
                        "  </div>" +
                        "</div>" +
                        " <div class='col-xs-2'>" +
                        "  <a id='addContato' class=' btn btn-info btn-sm' onclick ='voltarContato();'><i class='fa fa-backward fa-lg fa-spc'></i> RETORNAR" +
                        "</div>";

                $("#divSelectContato").html(novoHtml);
                $("#telefone").val('');
                $("#email").val('');
                $("#setor").val('');
                $("#telefone").prop('readonly', false);
                $("#email").prop('readonly', false);
                $("#setor").prop('readonly', false);
            }

            /* Volta o conteudo antigo da div do contato , ou seja, o combo para selecionar um contato */
            function voltarContato() {
                $("#idCliente").prop('disabled', false);
                var htmlaux = $("#aux").html();
                $("#divSelectContato").html(htmlaux);
                ajaxPreencherComboContato($('#idCliente').val());
                $("#telefone").val('');
                $("#email").val('');
                $("#setor").val('');
                $("#telefone").prop('readonly', true);
                $("#email").prop('readonly', true);
                $("#setor").prop('readonly', true);
            }


            var id = -1;
            $("#idCliente").change(function () {
                
                if ($('#idCliente').val() === 'sel') {
                    $('#selectContato').html('<select name="contato" id="contato" disabled><option value="">-- Selecione primeiro um cliente --</option></select>');
                    $("#contato").prop('disabled', true);
                    $("#addContato").prop('disabled', true);
                    $('#contato').select2();

                } else if ($('#addContato').is(':disabled')) {
                    $("#contato").prop('disabled', false);
                    $("#addContato").prop('disabled', false);
                }


                $("#end" + id).toggleClass("hidden");
                id = $(this).val();
                $("#end" + id).toggleClass("hidden");
                ajaxPreencherComboContato(id);
                $('#contato').attr('onchange', 'buscarDadosContato(' + id + ');');
                $("#telefone").val('');
                $("#email").val('');
                $("#setor").val('');
            });

            function ajaxPreencherComboContato(idCliente) {
                $.ajax({
                    method: "POST",
                    url: "ajax/montaComboContato.jsp",
                    data: {id: idCliente},
                    dataType: 'html'
                }).done(function (data) {
                    $('#selectContato').html(data);
                    LoadSelect2Script(selectCliente);
                });
            }
            function ajaxDadosDoContato(idCliente) {
                $.ajax({
                    method: "POST",
                    url: "ajax/consultaContato.jsp",
                    data: {idContato: idCliente},
                    dataType: 'html'
                }).done(function (data) {
                    var res = data.split(';');
                    // resultado = email + ";" + fone + ";" + setor;                    
                    $('#email').val(res[0]);
                    $('#telefone').val(res[1]);
                    $('#setor').val(res[2]);
                });
            }


            function selectCliente() {
                $('#idCliente').select2();
                $('#contato').select2();
                $('#idColetador').select2();
                $('#idTipo').select2();
            }
            
            function AllTables() {
                StartDataTable('dataTables-etqPend');
                LoadSelect2Script(MakeSelectDataTable('dataTables-etqPend'));
                fechaMsg();
            }

            function TimePicker() {
                $('#horaColeta').timepicker({setDate: new Date()});
            }
            $(document).ready(function () {
                LoadTimePickerScript(TimePicker);
                LoadSelect2Script(selectCliente);
                $("#dataColeta").datepicker({
                    showAnim: 'slideDown',
                    numberOfMonths: 1
                });
                fechaMsg();
                //LoadDataTablesScripts(AllTables);

            });

        </script>

    </body>
</html>
<%}%>