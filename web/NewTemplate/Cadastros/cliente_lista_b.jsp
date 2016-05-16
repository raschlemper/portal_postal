<%@page import="Entidade.LogAtualizacaoContratos"%>
<%@page import="Controle.ContrLogAtualizacaoContrato"%>
<%@page import="Entidade.Clientes"%>
<%@page import = "java.util.ArrayList,java.sql.Timestamp, java.util.Date, java.text.SimpleDateFormat"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
    if (session.getAttribute("agf_usuario") == null) {
        response.sendRedirect("../../index.jsp?msgLog=3");
    } else {
        
        empresas agf_empresa = (empresas) session.getAttribute("agf_empresa");
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy HH:mm");

        session.setAttribute("msg2", null);
        boolean flag = false;
        String param = "1";
        if (request.getParameter("inativos") != null) {
            if (request.getParameter("inativos").equals("1")) {
                flag = true;
                param = "0";
            }
        }
%>
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <title>Portal Postal</title>
        <%@ include file="../includes/Css_js.jsp" %>
    </head>
    <body>   
        <script type="text/javascript">
            waitMsg();
            
                function ativaCli() {
                var atv = $("#ativar").val();
                var bd = $("#nomeBD").val();                
                $.ajax({
                    url: '../../SerAtivaCliente',
                    data: {nome: atv, nomeBD: bd},
                    type: 'get',
                    cache: false,
                    success: function (data) {
                        alert(data);                             
                        window.location= "cliente_lista_b.jsp";
                    },
                    error: function () {
                        alert('Erro na requisição');
                    }
                }
                );
            }
            
            
            
        </script>
        <jsp:include page="../includes/navBarTop.jsp"></jsp:include>
            <div id="wrapper">
            <%@ include file="../includes/menu_agencia_bootstrap.jsp" %>

            <div id="page-content-wrapper">
                <div class="container-fluid">
                    <div id="page-wrapper">

                        <div class="row">
                            <div class="col-md-12">                       
                                <h4 class="page-header"><b class="text-primary"><i class="fa fa-gears"></i> Cadastros</b> > <small>Clientes</small></h4>
                            </div>
                        </div>

                        <div class="row">
                            <div class="well well-md"> 
                                <%if(agf_empresa.getTipo_sistema().equals("PORTALPOSTAL")){%>
                                    <button class="btn btn-success" type="submit" onclick="javascript:window.location = 'cliente_cadastro_novo_b.jsp';" name="save" id="sub"> <i class="fa fa-lg fa-spc fa-plus"></i> ADCIONAR NOVO CLIENTE</button>
                                <%}%>
                                <button style="margin-left: 10px;" class="btn btn-info" type="submit" onclick="javascript:window.location = 'cliente_login_massa_b.jsp';" name="save" id="sub"> <i class="fa fa-lg fa-spc fa-users"></i> GERAR LOGIN EM MASSA</button>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-12">   
                                <div class="panel panel-default">
                                    <div class="panel-heading">Lista de Todos os Clientes
                                        <%if(agf_empresa.getTipo_sistema().equals("PORTALPOSTAL")){%>
                                        <span class="pull-right">  
                                            <div class="form-inline">
                                                <label><input id="ck_destivar" name="ck_desativar" type="checkbox" value="" <% if (flag) {%>checked="checked"<%}%> onclick="window.location = 'cliente_lista_b.jsp?inativos=<%=param%>';" >&nbsp;MOSTRAR TAMBÉM INATIVOS</label>
                                            </div>
                                        </span>
                                        <%}%>
                                    </div>
                                    <div class="panel-body">
                                        <div class="dataTable_wrapper no-padding">
                                            <table class="table table-striped table-bordered table-hover table-condensed" id="dataTables-example">
                                                <thead>
                                                    <tr>
                                                        <th width="70">Nº</th>
                                                        <th>Razão Social</th>
                                                        <th>Nome Fantasia</th>
                                                        <th width="170">Status Contrato</th>
                                                        <th width="120">SigepWEB</th>
                                                        <th width="120">Vigência</th>
                                                        <th class="no-sort text-center" width="50"><span class="fa fa-pencil-square-o"></span></th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <%
                                                        ArrayList<Clientes> listaCliente = Controle.contrCliente.getNomeCodigoMetodo(agf_empresa.getCnpj(), flag);
                                                        for (int j = 0; j < listaCliente.size(); j++) {
                                                            Clientes col = listaCliente.get(j);
                                                            String nomeCliente = col.getNome();
                                                            int idCliente = col.getCodigo();
                                                            String nomeFantasia = col.getNomeFantasia();
                                                            Date dt = col.getDtVigenciaFimContrato();
                                                            String dtvg = "- - -";
                                                            if (dt != null) {
                                                                dtvg = sdf.format(dt);
                                                            }
                                                            String dtAt = "- - -";
                                                            if (col.getDataHoraAtualizacao() != null) {
                                                                dtAt = sdf2.format(col.getDataHoraAtualizacao());
                                                            }
                                                            if (col.getErro_atualizacao() == 1) {
                                                                dtAt = "<b style='color:red;font-weight:bold;'>FALHA AO ATUALIZAR</b>";
                                                            }

                                                            String statusCp = "<a href='cliente_contrato_b.jsp?idCliente=" + idCliente + "' style='color:silver;font-weight:bold;'>SEM CONTRATO ECT</a>";
                                                            if (col.getTemContrato() == 1) {
                                                                if (col.getStatusCartaoPostagem() == 1) {
                                                                    statusCp = "<a href='cliente_contrato_b.jsp?idCliente=" + idCliente + "' style='color:green;font-weight:bold;'>VÁLIDO</a>";
                                                                } else {
                                                                    statusCp = "<a href='cliente_contrato_b.jsp?idCliente=" + idCliente + "' style='color:red;font-weight:bold;'>INVÁLIDO/SUSPENSO</a>";
                                                                }
                                                            }
                                                            String sigep = "<b>POSSUI</b>";
                                                            if (col.getTemContrato() == 0) {
                                                                sigep = "";
                                                            } else if (col.getLogin_sigep() == null || col.getLogin_sigep().equals("null") || col.getLogin_sigep().equals("")) {
                                                                sigep = "<b style='color:red;'>NÃO POSSUI</b>";
                                                            }
                                                    %>
                                                    <tr>
                                                        <td align="right"<% if (col.getAtivo() == 0) { %> style="color: gray;"  <%}%> ><%= idCliente%></td>
                                                        <td style="font-size: 11px; <% if (col.getAtivo() == 0) { %> color: gray;  <%}%>"   ><%= nomeCliente%></td>
                                                        <td style="font-size: 11px; <% if (col.getAtivo() == 0) { %> color: gray; <%}%>"  ><%= nomeFantasia%></td>
                                                        <%--<td align="center"><%= dtAt%></td>--%>
                                                        <td><% if (col.getAtivo() == 1) { %><%=statusCp%><%}else{%> ------ <%}%></td>
                                                        <td><% if (col.getAtivo() == 1) { %><%= sigep%><%}else{%> ------ <%}%></td>
                                                        <td align="center"><% if (col.getAtivo() == 1) { %><%= dtvg%><%}else{%> INATIVO <%}%></td>
                                                        <% if (col.getAtivo() == 1) {%>

                                                        <td align="center">
                                                            <a href="cliente_usuarios_b.jsp?idCliente=<%= idCliente%>" class="btn btn-sm btn-warning" ><i class="fa fa-lg fa-pencil-square-o"></i></a>
                                                        </td>

                                                        <%} else {%>
                                                        <td align="center">
                                                            <button  onclick="ativaCli();" class="btn btn-sm btn-default" ><i class="fa fa-lg fa-power-off"></i></button>
                                                            <input type="hidden" id="ativar" value="<%= idCliente%>"/>
                                                            <input type="hidden" id="nomeBD" value="<%= agf_empresa.getCnpj()%>"/>
                                                            
                                                        </td>
                                                        <%}%>


                                                    </tr>
                                                    <%}%>
                                                </tbody>
                                            </table>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row spacer-xlg"></div>
                    </div>
                </div>
            </div>
        </div>
        <script type="text/javascript">
            function AllTables() {
                StartDataTable('dataTables-example');
                LoadSelect2Script(MakeSelectDataTable('dataTables-example'));
                fechaMsg();
            }

            $(document).ready(function () {
                LoadDataTablesScripts(AllTables);
            });
        </script>
    </body>
</html>
<%}%>