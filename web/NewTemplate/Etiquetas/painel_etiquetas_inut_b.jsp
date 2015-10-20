
<%@page import="java.util.Date"%>
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

        int idNivelDoUsuario = (Integer) session.getAttribute("nivel");
        if (idNivelDoUsuario > 2) {
            response.sendRedirect("../jsp/imp_movimento.jsp?msg=Acesso Negado!");
        }

        SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        int idUsuario = (Integer) session.getAttribute("idUsuario");
        String nomeBD = (String) session.getAttribute("empresa");
        int num = Controle.contrCliente.numeroClientes(nomeBD);

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        Date dataAtual = new Date();
        String vDataAtual = Util.SomaData.SomarDiasDatas(dataAtual, -1);
        String dataAnterior = Util.SomaData.SomarDiasDatas(dataAtual, -6);
        if (request.getParameter("dataFim") != null) {
            vDataAtual = request.getParameter("dataFim");
        }
        if (request.getParameter("dataIni") != null) {
            dataAnterior = request.getParameter("dataIni");
        }

        String vDataInicio = Util.FormatarData.DateToBD(dataAnterior);
        String vDataFinal = Util.FormatarData.DateToBD(vDataAtual);

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


                        <div class="row">
                            <div class="col-md-12">                       
                                <h4 class="page-header"><b class="text-primary"><i class="fa fa-barcode"></i> Gerenciar Etiquetas</b> > <small>Etiquetas Inutilizadas</small></h4>
                            </div>
                        </div>

                        <div class="row">
                            <div class="well well-md">  
                                <div>
                                    <h4 class="page">Escolha um periodo</h4>
                                </div>                                                
                                <form class="form-inline" action="painel_etiquetas_inut_b.jsp" method="post"> 
                                    <div class="form-group" >                                                
                                        <label for="from">De</label>
                                        <div class="input-group">
                                            <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                                            <input class="form-control" size="8" type="text" id="dataIni" name="dataIni" value="<%= dataAnterior%>" onkeypress="mascara(this, maskData);"/>
                                        </div>
                                        <label for="to">à</label>
                                        <div class="input-group">
                                            <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                                            <input class="form-control" size="8" type="text" id="dataFim" name="dataFim" value="<%=vDataAtual%>" onkeypress="mascara(this, maskData);" />
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

                                            <table table class="table table-striped table-bordered table-hover" id="dataTables-etqInut">
                                                <thead>
                                                    <tr>
                                                        <th>Nº do Objeto</th>
                                                        <th style="min-width: 90px;">Serviço</th>
                                                        <th>Cliente</th>
                                                        <th>Destinatário</th>
                                                        <th style="min-width: 90px;">CEP</th>
                                                        <th style="min-width: 90px;">Gerada </th>
                                                        <th style="min-width: 110px;">Impressa</th>
                                                        <th style="min-width: 60px;">AR </th>
                                                        <th class="no-sort" style="min-width: 70px;">VER</th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <%
                                                        ArrayList<PreVenda> lista2 = ContrPreVenda.consultaVendasNaoConsolidadas(nomeBD, vDataInicio, vDataFinal, 1);
                                                        for (int i = 0; i < lista2.size(); i++) {
                                                            PreVenda des = lista2.get(i);
                                                            String numObj = des.getNumObjeto();
                                                            if ("avista".equals(numObj)) {
                                                                numObj = "- - -";
                                                            }
                                                            String ar = "SIM";
                                                            if (des.getAviso_recebimento() == 0) {
                                                                ar = "NÃO";
                                                            }
                                                            String dtVenda = " - - - ";
                                                            if (des.getDataPreVenda() != null) {
                                                                dtVenda = sdf2.format(des.getDataPreVenda());
                                                            }
                                                            String dtImpresso = " - - - ";
                                                            if (des.getDataImpresso() != null) {
                                                                dtImpresso = sdf2.format(des.getDataImpresso());
                                                            }
                                                            String nomeCli = contrCliente.consultaNomeById(des.getIdCliente(), nomeBD);
                                                    %>
                                                    <tr style="cursor:default;">
                                                        <td align="center"><a href='http://websro.correios.com.br/sro_bin/txect01$.QueryList?P_LINGUA=001&P_TIPO=001&P_COD_UNI=<%= numObj%>' target=_blank><%= numObj%></a></td>
                                                        <td><%= des.getNomeServico()%></td>
                                                        <td><%= nomeCli%></td>
                                                        <td><%= des.getNomeDes()%></td>
                                                        <td><%= des.getCepDes()%></td>
                                                        <td><%= dtVenda%></td>
                                                        <td><%= dtImpresso%></td>
                                                        <td><%= ar%></td>
                                                        <td align="center"><a onclick="verVendaBootStrap(<%= des.getId()%>, <%= des.getIdCliente()%>);" style="cursor:pointer;" ><i class="fa fa-search"></i></a></td>
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
                StartDataTable('dataTables-etqInut');
                LoadSelect2Script(MakeSelectDataTable('dataTables-etqInut'));
            }
            $(document).ready(function () {
                LoadDataTablesScripts(AllTables);
                $("#dataIni").datepicker({
                    showAnim: 'slideDown',
                    maxDate: new Date(),
                    numberOfMonths: 3,
                    onClose: function (selectedDate) {
                        $("#dataFim").datepicker("option", "minDate", selectedDate);
                    }
                });
                $("#dataFim").datepicker({
                    showAnim: 'slideDown',
                    maxDate: new Date(),
                    numberOfMonths: 3,
                    onClose: function (selectedDate) {
                        $("#dataIni").datepicker("option", "maxDate", selectedDate);
                    }
                });
            });
        </script>

    </body>

</html>
<%}%>
