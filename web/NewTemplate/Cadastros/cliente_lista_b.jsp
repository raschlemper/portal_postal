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


        <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
        <meta name="viewport" content="width=device-width, initial-scale=1"/>
        <meta name="description" content=""/>
        <meta name="author" content=""/>
        <meta http-equiv="Content-Type" content="text/html;charset=ISO-8859-8">

<!--[if IE]><link rel="shortcut icon" href="${pageContext.request.contextPath}/imagensNew/favicon.ico" /><![endif]-->
            <link rel="icon" href="${pageContext.request.contextPath}/imagensNew/favicon.ico" />

            <link href="${pageContext.request.contextPath}/plugins/bootstrap/dist/css/bootstrap.css" rel="stylesheet" type="text/css"/>
            <!--<link rel="stylesheet" href="https://ajax.googleapis.com/ajax/libs/jqueryui/1.11.3/themes/smoothness/jquery-ui.css" />-->
            <link href="${pageContext.request.contextPath}/plugins/jquery-ui/jquery-ui.min.css" rel="stylesheet"/>

            <link href="http://maxcdn.bootstrapcdn.com/font-awesome/4.6.2/css/font-awesome.min.css" rel="stylesheet">
                <%--
            <link href='http://fonts.googleapis.com/css?family=Righteous' rel='stylesheet' type='text/css'>
                
            <link href="${pageContext.request.contextPath}/plugins/fancybox/jquery.fancybox.css" rel="stylesheet">
            <link href="${pageContext.request.contextPath}/plugins/fullcalendar/fullcalendar.css" rel="stylesheet">
            <link href="${pageContext.request.contextPath}/plugins/xcharts/xcharts.min.css" rel="stylesheet">
                
            <link href="${pageContext.request.contextPath}/plugins/select2/select2.css" rel="stylesheet">
                
            <link href="${pageContext.request.contextPath}/plugins/justified-gallery/justifiedGallery.css" rel="stylesheet">
            <!-- <link href="css/style_v1.css" rel="stylesheet"> -->
            <link href="${pageContext.request.contextPath}/plugins/chartist/chartist.min.css" rel="stylesheet">
            <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
            <!--[if lt IE 9]>
                            <script src="http://getbootstrap.com/docs-assets/js/html5shiv.js"></script>
                            <script src="http://getbootstrap.com/docs-assets/js/respond.min.js"></script>
            <![endif]-->
                --%>
                <!--<link rel="stylesheet" href="https://ajax.googleapis.com/ajax/libs/jqueryui/1.11.3/themes/smoothness/jquery-ui.css" /> -->
                <!-- Custom CSS -->
                <link href="${pageContext.request.contextPath}/NewTemplate/dist/css/sb-admin-2.css" rel="stylesheet" type="text/css"/>
                <link href="${pageContext.request.contextPath}/NewTemplate/dist/css/timeline.css" rel="stylesheet" type="text/css"/>
                <link href="${pageContext.request.contextPath}/NewTemplate/dist/css/simple-sidebar.css" rel="stylesheet">



                    <!-- jQuery -->
                    <!--<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script> -->
                    <script src="${pageContext.request.contextPath}/plugins/jquery/jquery.min.js"></script>

                    <script src="${pageContext.request.contextPath}/plugins/jquery-ui/jquery-ui.js"></script>
                    <!-- Include all compiled plugins (below), or include individual files as needed -->


                    <%--
                    <script src="${pageContext.request.contextPath}/plugins/justified-gallery/jquery.justifiedGallery.min.js"></script>
                    <script src="${pageContext.request.contextPath}/plugins/tinymce/tinymce.min.js"></script>
                    <script src="${pageContext.request.contextPath}/plugins/tinymce/jquery.tinymce.min.js"></script>
                    --%>
                    <script type="text/javascript" language="javascript" src="${pageContext.request.contextPath}/NewTemplate/dist/js/devoops.js" ></script>
                    <%--
                    <script type="text/javascript" language="javascript" src="${pageContext.request.contextPath}/plugins/underscore/underscore.js"></script>
                    <script type="text/javascript" language="javascript" src="${pageContext.request.contextPath}/plugins/underscore/underscoreConfig.js"></script>
                    <script type="text/javascript" language="javascript" src="${pageContext.request.contextPath}/plugins/jquery-mask/jquery-mask.js"></script>
                    <script type="text/javascript" language="javascript" src="${pageContext.request.contextPath}/plugins/watch/watch.js"></script>
                    <script type="text/javascript" language="javascript" src="${pageContext.request.contextPath}/plugins/numeral/numeral.js"></script>
                    <script type="text/javascript" language="javascript" src="${pageContext.request.contextPath}/plugins/numeral/pt-br.js"></script>
                    --%>
                    <!-- Custom Theme JavaScript -->
                    <script src="${pageContext.request.contextPath}/plugins/metisMenu/dist/metisMenu.js" type="text/javascript"></script>
                    <script src="${pageContext.request.contextPath}/NewTemplate/dist/js/bootbox.min.js" type="text/javascript"></script>

                    <script src="${pageContext.request.contextPath}/NewTemplate/dist/js/sb-admin-2.js" type="text/javascript"></script>
                    <script src="${pageContext.request.contextPath}/NewTemplate/dist/js/sidebar_menu.js" type="text/javascript"></script>
                    <%--
                    <!-- MASCARAS-->
                    <script type="text/javascript" src="${pageContext.request.contextPath}/javascript/mascara_bootStrap.js" charset="UTF-8"></script>
                   
                    <script type="text/javascript" src="${pageContext.request.contextPath}/Cliente/js/ajax_portal.js"></script>
                    --%>
                    <script>
                        function waitMsg() {
                            bootbox.dialog({
                                title: '<h4> Aguarde... Processando Requisi&ccedil;&atilde;o<h4>',
                                message: "<div class='progress'> <div class='progress-bar progress-bar-striped active' role='progressbar' aria-valuenow='100' aria-valuemin='0' aria-valuemax='100' style='width:100%;'></div></div>",
                                modal: true,
                                show: true,
                                backdrop: true,
                                closeButton: false,
                                animate: true,
                                className: "my-modal",
                                zIndex: 2500
                            });
                        }
                        function fechaMsg() {
                            bootbox.hideAll();
                            telaMsg();
                        }
                    </script>

                    <%-- 
                     <script type="text/javascript" src="${pageContext.request.contextPath}/javascript/ddslick.js"></script>
                    --%>
                    <!-- Bootstrap Core JavaScript -->
                    <script src="${pageContext.request.contextPath}/plugins/bootstrap/dist/js/bootstrap.min.js" type="text/javascript"></script>
                    
                    <script src="${pageContext.request.contextPath}/plugins/datatables/jquery.dataTables.js" type="text/javascript"></script>
                   
                    
                    <script src="${pageContext.request.contextPath}//plugins/datatables/dataTables.tableTools.js" type="text/javascript"></script>
                    <script src="${pageContext.request.contextPath}/plugins/moment/moment.min.js" type="text/javascript"></script>
                    <script src="${pageContext.request.contextPath}/plugins/moment/datetime-moment.js" type="text/javascript"></script>
                    <script src="${pageContext.request.contextPath}/plugins/datatables/dataTables.bootstrap.js" type="text/javascript"></script>
                    <script src="${pageContext.request.contextPath}/plugins/select2/select2.min.js" type="text/javascript"></script>
                    <%--
                    <!-- JQUERY NUMERIC }); -->
                    <script src="${pageContext.request.contextPath}/javascript/jquery/js/jquery.numeric.min.js" type="text/javascript"></script>
                     <!--- X -editable -->
                            <link href="//cdnjs.cloudflare.com/ajax/libs/x-editable/1.5.0/bootstrap3-editable/css/bootstrap-editable.css" rel="stylesheet"/>
                            <script src="//cdnjs.cloudflare.com/ajax/libs/x-editable/1.5.0/bootstrap3-editable/js/bootstrap-editable.min.js"></script>
                            
                            <!--- X -editable fix -->
                            <style>
                                .input-sm{
                                    height: 23px;
                                }
                                .editable-clear-x{
                                    top: 12px;
                                }
                            </style>
                            
                    --%>




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
                                    window.location = "cliente_lista_b.jsp";
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
                                                <%if (agf_empresa.getTipo_sistema().equals("PORTALPOSTAL")) {%>
                                                <button class="btn btn-success" type="submit" onclick="javascript:window.location = 'cliente_cadastro_novo_b.jsp';" name="save" id="sub"> <i class="fa fa-lg fa-spc fa-plus"></i> ADCIONAR NOVO CLIENTE</button>
                                                <%}%>
                                                <button style="margin-left: 10px;" class="btn btn-info" type="submit" onclick="javascript:window.location = 'cliente_login_massa_b.jsp';" name="save" id="sub"> <i class="fa fa-lg fa-spc fa-users"></i> GERAR LOGIN EM MASSA</button>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="col-md-12">   
                                                <div class="panel panel-default">
                                                    <div class="panel-heading">Lista de Todos os Clientes
                                                        <%if (agf_empresa.getTipo_sistema().equals("PORTALPOSTAL")) {%>
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
                                                                        <td><% if (col.getAtivo() == 1) {%><%=statusCp%><%} else {%> ------ <%}%></td>
                                                                        <td><% if (col.getAtivo() == 1) {%><%= sigep%><%} else {%> ------ <%}%></td>
                                                                        <td align="center"><% if (col.getAtivo() == 1) {%><%= dtvg%><%} else {%> INATIVO <%}%></td>
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
                            /*function AllTables() {
                             StartDataTable('dataTables-example');
                             LoadSelect2Script(MakeSelectDataTable('dataTables-example'));
                             fechaMsg();
                             }
                             
                             $(document).ready(function () {
                             LoadDataTablesScripts(AllTables);
                             fechaMsg();
                             });*/

                            $(document).ready(function () {
                                StartDataTable('dataTables-example');
                                MakeSelectDataTable('dataTables-example');
                                fechaMsg();
                            });

                        </script>
                    </body>
                    </html>
                    <%}%>