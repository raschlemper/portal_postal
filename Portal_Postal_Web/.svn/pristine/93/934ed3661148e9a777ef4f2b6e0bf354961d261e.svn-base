
<%@page import="Controle.ContrClienteEtiquetas"%>
<%@page import="Controle.contrCliente"%>
<%@page import="Emporium.Controle.ContrPreVenda"%>
<%@page import="Entidade.PreVenda"%>
<%@page import="Entidade.ClienteLogEtiqueta"%>
<%@ page import = "java.text.DateFormat,java.util.ArrayList,java.sql.Timestamp,java.util.Calendar, java.util.GregorianCalendar, java.util.Date, java.text.SimpleDateFormat"%>

<%
    if (session.getAttribute("usuario") == null) {
        response.sendRedirect("../index.jsp?msgLog=3");
    } else {

        String nomeBD = (String) session.getAttribute("empresa");
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        Date dataAtual = new Date();
        String vDataAtual = sdf.format(dataAtual);
        String dataAnterior = Util.SomaData.SomarDiasDatas(dataAtual, -30);
        if (request.getParameter("dataFim") != null) {
            vDataAtual = request.getParameter("dataFim");
        }
        if (request.getParameter("dataIni") != null) {
            dataAnterior = request.getParameter("dataIni");
        }

        String vDataInicio = Util.FormatarData.DateToBD(dataAnterior);
        String vDataFinal = Util.FormatarData.DateToBD(vDataAtual);
        ArrayList<ClienteLogEtiqueta> listaLog = Controle.ContrClienteEtiquetas.consultaLogFaixas(vDataInicio, vDataFinal, nomeBD);


%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

    <head>
        <title>Portal Postal</title>
        <%@ include file="../includes/Css_js.jsp" %>
    </head>

    <body onload="fechaMsg();">   
        <script>
            waitMsg();
        </script>
        <jsp:include page="../includes/navBarTop.jsp"></jsp:include>
            <div id="wrapper">
            <%@ include file="../includes/menu_agencia_bootstrap.jsp" %>

            <div id="page-content-wrapper">
                <div class="container-fluid">
                    <div id="page-wrapper">

                        <!<!-- Indica o local da pagina -->
                        <div class="row">
                            <div class="col-md-12">                       
                                <h4 class="page-header"><b class="text-primary"><i class="fa fa-barcode"></i> Gerenciar Etiquetas</b> > <small>Sequencias Geradas</small></h4>
                            </div>
                        </div>

                        <div class="row">
                            <div class="well well-sm">  
                                <div>
                                    <h4 class="page">Escolha um periodo</h4>
                                </div> 
                                <form class="form-inline" action="painel_etiquetas_geradas_b.jsp" method="post"> 
                                    <div class="form-group" >                                                
                                        <label for="from">De</label>
                                        <div class="input-group">
                                            <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                                            <input class="form-control" size="8" type="text" id="dateIni" name="dateIni" value="<%= dataAnterior%>" onkeypress="mascara(this, maskData);"/>
                                        </div>
                                        <label for="to">à</label>
                                        <div class="input-group">
                                            <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                                            <input class="form-control" size="8" type="text" id="dateFin" name="dateFin" value="<%=vDataAtual%>" onkeypress="mascara(this, maskData);" />
                                        </div>
                                        <button type="submit" class="btn btn-sm btn-primary form-control" onclick="waitMsg()"><i class="fa fa-lg fa-search"></i></button>
                                    </div>
                                </form>
                            </div>   
                        </div>



                        <!-- /.row -->
                        <div class="row">
                            <div class="col-lg-12">
                                <div class="panel panel-default">
                                    <div class="panel-heading" >Resultado da Pesquisa</div>
                                    <div class="panel-body">
                                        <div class="dataTable_wrapper no-padding">
                                            <table class="table table-striped table-bordered table-hover table-condensed" id="dataTables-example">
                                                <thead>
                                                    <tr>
                                                        <th>Cód.</th>
                                                        <th>Cliente</th>
                                                        <th>Serviço</th>
                                                        <th>Inicial</th>
                                                        <th>Final</th>
                                                        <th>Utilizadas</th>
                                                        <th>Restam</th>
                                                        <th>Usuário</th>
                                                        <th>Inserção</th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <%
                                                        SimpleDateFormat sdf3 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                                                        for (int i = 0; i < listaLog.size(); i++) {
                                                            ClienteLogEtiqueta log = listaLog.get(i);
                                                            int qtdUt = ContrClienteEtiquetas.contaQtdUtilizadaPorIdLog(log.getIdLog(), 1, nomeBD);
                                                            String nomeServ = log.getServico() + "";
                                                            String cli = log.getNomeServico();
                                                    %>
                                                    <tr>
                                                        <td style="min-width: 80px;"><%= log.getIdLog()%></td>
                                                        <td><%= cli%></td>
                                                        <td style="min-width: 90px;"><%= nomeServ%></td>
                                                        <td><%= log.getFaixaIni()%></td>
                                                        <td><%= log.getFaixaFim()%></td>
                                                        <td style="min-width: 120px;"><%= qtdUt%> / <%= log.getQtd()%></td>

                                                        <td style="min-width: 90px;"><%= log.getQtd() - qtdUt%></td>
                                                        <td style="min-width: 90px;"><%= log.getNomeUsuario()%></td>
                                                        <td style="min-width: 95px;"><%= sdf3.format(log.getDataHora())%></td>
                                                    </tr>
                                                    <%}%>
                                                </tbody>
                                            </table>
                                        </div>
                                    </div>                                                                                 
                                </div>
                            </div>

                        </div>

                        <!-- /.row -->
                    </div>
                </div>
            </div>
        </div>
        <!-- /#page-wrapper -->

        <!-- /#wrapper -->

        <script type="text/javascript">
            function AllTables() {
                StartDataTable('dataTables-example');
                LoadSelect2Script(MakeSelectDataTable('dataTables-example'));
            }
            $(document).ready(function () {
                LoadDataTablesScripts(AllTables);
                $("#dateIni").datepicker({
                    showAnim: 'slideDown',
                    maxDate: new Date(),
                    numberOfMonths: 3,
                    onClose: function (selectedDate) {
                        $("#dateFin").datepicker("option", "minDate", selectedDate);
                    }
                });
                $("#dateFin").datepicker({
                    showAnim: 'slideDown',
                    maxDate: new Date(),
                    numberOfMonths: 3,
                    onClose: function (selectedDate) {
                        $("#dateIni").datepicker("option", "maxDate", selectedDate);
                    }
                });
            });
        </script>
    </body>
</html>
<%}%>
