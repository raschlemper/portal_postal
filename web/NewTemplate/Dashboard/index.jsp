<%@page import="com.portalpostal.model.dd.TipoManutencao"%>
<%@page import="com.portalpostal.model.VeiculoSeguro"%>
<%@page import="com.portalpostal.service.VeiculoSeguroService"%>
<%@page import="java.util.List"%>
<%@page import="com.portalpostal.model.VeiculoManutencao"%>
<%@page import="com.portalpostal.service.VeiculoManutencaoService"%>
<%@page import="Controle.contrCliente"%>
<%@page import="Entidade.Clientes"%>
<%@page import="Emporium.Controle.ContrTelegramaPostal"%>
<%@page import="Entidade.TelegramaPostal"%>
<%@page import="Entidade.ClienteLogEtiqueta"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="java.util.Map"%>
<%@page import="Entidade.Movimentacao"%>
<%@page import="Controle.ContrDashboard"%>
<%@ page import = "java.text.DateFormat,java.util.ArrayList,java.sql.Timestamp,java.util.Calendar, java.util.GregorianCalendar, java.util.Date, java.text.SimpleDateFormat"%>
<%@page import="java.text.SimpleDateFormat"%>
<%
    if (session.getAttribute("usuario") == null) {
        response.sendRedirect("../index.jsp?msgLog=3");
    } else {

        String nomeBD = (String) session.getAttribute("empresa");
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM");
        DecimalFormat df = new DecimalFormat("0.00");

        empresas empr = (empresas) session.getAttribute("emp");
        
        Date dataAtual = new Date();
        String vDataAtual = sdf.format(dataAtual);
        String dataAnterior = Util.SomaData.SomarDiasDatas(dataAtual, -30);
        if (request.getParameter("dateFim") != null) {
            vDataAtual = request.getParameter("dateFim");
        }
        if (request.getParameter("dateIni") != null) {
            dataAnterior = request.getParameter("dateIni");
        }

        String vDataInicio = Util.FormatarData.DateToBD(dataAnterior);
        String vDataFinal = Util.FormatarData.DateToBD(vDataAtual);
        
        ArrayList<Movimentacao> listaInativos = ContrDashboard.consultaClientesInativos(vDataInicio, nomeBD);
        ArrayList<Movimentacao> listaClientes = ContrDashboard.consultaFaturaPorClientes(vDataInicio, vDataFinal, nomeBD);
        ArrayList<Movimentacao> listaEtiquetas = ContrDashboard.consultaEtiquetasPorClientes(vDataInicio, vDataFinal, nomeBD);
        
        ArrayList<Movimentacao> listaFaturamento = ContrDashboard.consultaFaturaPorData(vDataInicio, vDataFinal, nomeBD);
        String grafico1 = "['Data', 'Faturamento (R$)']";
        if(listaFaturamento.size()==0){
            grafico1+=",['Nenhum Movimento no Periodo Selecionado',0]";
        }else{
            for(Movimentacao m: listaFaturamento){
                grafico1 += ",['"+sdf2.format(m.getDataPostagem())+"', "+m.getValorServico()+"]";            
            }
        }
        
        Map<String, Movimentacao> mapServFat = ContrDashboard.consultaFaturamentoPorServico(vDataInicio, vDataFinal, nomeBD);
        float qtdtotal = 0;
        float valorTotal = 0;
        String tabelaTicket = "";
        String grafico2 = "['Serviços', 'Faturamento (R$)']";                    
        for (String key : mapServFat.keySet()) { 
            //Capturamos o valor a partir da chave 
            Movimentacao m = mapServFat.get(key); 
            //System.out.println(key + " = " + m.getValorServico()+" - "+m.getQuantidade()); 
            if(key.equals("PAC") || key.equals("SEDEX") || key.equals("SEDEX 10") || key.equals("SEDEX 12") || key.equals("E-SEDEX") || key.equals("PPI") || key.equals("INTERNACIONAL")){
                qtdtotal += m.getQuantidade();
                valorTotal += m.getValorServico();
                tabelaTicket+="<tr><td>"+key+"</td><td>R$ "+df.format(m.getValorServico()/m.getQuantidade())+"</td></tr>";
                grafico2+=",['"+key+"', "+m.getValorServico()+"]";          
            }else if(key.equals("CARTA REG.") || key.equals("CARTA SIMPLES") || key.equals("MDPB") || key.equals("TELEGRAMA")){
                tabelaTicket+="<tr><td>"+key+"</td><td>R$ "+df.format(m.getValorServico()/m.getQuantidade())+"</td></tr>";
                grafico2+=",['"+key+"', "+m.getValorServico()+"]";          
                
            }  
        }
        
        VeiculoManutencaoService manutencaoService = new VeiculoManutencaoService(nomeBD);
        VeiculoSeguroService seguroService = new VeiculoSeguroService(nomeBD);
        Calendar dataInicio = Calendar.getInstance();       
        dataInicio.set(Calendar.HOUR_OF_DAY, 00);
        dataInicio.set(Calendar.MINUTE, 00);
        dataInicio.set(Calendar.SECOND, 00);    
        Calendar dataFinal = Calendar.getInstance();
        dataFinal.add(Calendar.DAY_OF_MONTH, 30);      
        dataFinal.set(Calendar.HOUR_OF_DAY, 23);
        dataFinal.set(Calendar.MINUTE, 59);
        dataFinal.set(Calendar.SECOND, 59);
        List<VeiculoManutencao> listaVeiculoManutencao = manutencaoService.findNotDoneByRangeDate(dataInicio.getTime(), dataFinal.getTime());
        List<VeiculoSeguro> listaVeiculoSeguro = seguroService.findNotRenewByRangeDate(dataInicio.getTime(), dataFinal.getTime());

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

    <head>
        <title>Portal Postal</title>
        <%@ include file="../includes/Css_js.jsp" %>
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

                        <!-- Indica o Local Da Página -->
                        <div class="row">
                            <div class="col-md-12">                       
                                <h4 class="page-header"><b class="text-primary"><i class="fa fa-dashboard"></i> Dashboard</b> > <small><%= empr.getEmpresa() %></small></h4>
                            </div>
                        </div>

                        <div class="row">
                            <!-- Mostra Aviso na tela -->
                            <div class="col-lg-12" id="msg" style="display: block">
                                <%                                
                                    ArrayList<ClienteLogEtiqueta> lista = Controle.ContrClienteEtiquetas.consultaQtdEtiquetasRestantes(200, nomeBD);
                                   int listaTele = ContrTelegramaPostal.consultaQtdNaoEnviados(nomeBD);
                                    ArrayList<Clientes> listaContr = contrCliente.getClientesComContratoVencendo(30, nomeBD);
                                    if(lista.size() > 0){
                                %>
                                <div class="alert alert-danger no-margin">
                                    <a href="#" class="close" data-dismiss="alert">&times;</a>
                                    <a class="text-danger" href="../Etiquetas/painel_etiquetas_b.jsp"><strong>ATENÇÃO!</strong> Existem clientes com poucas etiquetas.</a>
                                </div>
                                <%}%>
                                <%if(listaTele > 0){ %>
                                <div class="alert alert-danger no-margin">
                                    <a href="#" class="close" data-dismiss="alert">&times;</a>
                                    <a class="text-danger" href="../Telegrama/telegrama_naoenviados_b.jsp"><strong>ATENÇÃO!</strong> Existem <%= listaTele %> Telegramas para enviar.</a>
                                </div>
                                <%}%>
                                <%if(listaContr.size()>0){%>
                                <div class="alert alert-danger no-margin">
                                    <a href="#" class="close" data-dismiss="alert">&times;</a>
                                    <strong>ATENÇÃO!</strong> Existem clientes com o contrato a vencer em menos de 30 Dias:<br/>
                                    <%
                                        for(int i=0; i<listaContr.size(); i++){
                                            Clientes cli = listaContr.get(i);
                                    %>
                                        <a href="../Cadastros/cliente_contrato_b.jsp?idCliente=<%= cli.getCodigo() %>">- <%= cli.getNome() %> - Contrato vence no dia <%= sdf.format(cli.getDtVigenciaFimContrato()) %></a><br/>
                                    <%}%>                                    
                                </div>
                                <%}%>
                                <%if(!listaVeiculoManutencao.isEmpty()){ %>
                                <div class="alert alert-danger no-margin">
                                    <a href="#" class="close" data-dismiss="alert">&times;</a>
                                    <a class="text-danger" href="${pageContext.request.contextPath}/app/veiculo/manutencao">
                                        <strong>ATENÇÃO!</strong> Existem manutenções agendadas para os próximos 30 dias.
                                    </a>
                                    <%
                                        Integer quantidadeTrocaOleo = 0;
                                        Integer quantidadeProgramada = 0;
                                        Integer quantidadeRotina = 0;
                                        for(int i = 0; i < listaVeiculoManutencao.size(); i++){
                                            VeiculoManutencao veiculoManutencao = listaVeiculoManutencao.get(i);
                                            if(veiculoManutencao.getTipo() == TipoManutencao.TROCA_OLEO) { quantidadeTrocaOleo++; }
                                            if(veiculoManutencao.getTipo() == TipoManutencao.PROGRAMADA) { quantidadeProgramada++; }
                                            if(veiculoManutencao.getTipo() == TipoManutencao.ROTINA) { quantidadeRotina++; }
                                        }
                                    %>
                                    <%if(quantidadeTrocaOleo > 0){ %>
                                    <div class="row">
                                        <div class="col-md-12">
                                            <%= quantidadeTrocaOleo.toString() %>
                                            <%if(quantidadeTrocaOleo > 1) { %> Trocas de óleo agendadas <% } else { %> Troca de óleo agendada <% } %>
                                        </div>
                                    </div>
                                    <%}%>
                                    <%if(quantidadeProgramada > 0){ %>
                                    <div class="row">
                                        <div class="col-md-12">
                                            <%= quantidadeProgramada.toString() %>
                                            <%if(quantidadeProgramada > 1) { %> Manuteções programada agendadas <% } else { %> Manuteção programada agendada <% } %>
                                        </div>
                                    </div>
                                    <%}%>
                                    <%if(quantidadeRotina > 0){ %>
                                    <div class="row">
                                        <div class="col-md-12">
                                            <%= quantidadeRotina.toString() %>
                                            <%if(quantidadeRotina > 1) { %> Manuteções de rotina agendadas <% } else { %> Manuteção de rotina agendada <% } %>
                                        </div>
                                    </div>
                                    <%}%>
                                </div>
                                <%}%>
                                <%if(!listaVeiculoSeguro.isEmpty()){ %>
                                <div class="alert alert-danger no-margin">
                                    <a href="#" class="close" data-dismiss="alert">&times;</a>
                                    <a class="text-danger" href="${pageContext.request.contextPath}/app/veiculo/seguro">
                                        <strong>ATENÇÃO!</strong> Existem seguros com vencimento para os próximos 30 dias.
                                    </a>
                                </div>
                                <%}%>
                            </div>
                        </div>


                        <div class="row">
                            <div class="well well-sm">  
                                <form class="form-inline" action="index.jsp" method="post"> 
                                    <div class="form-group" >                                                
                                        <label for="from">Período de </label>
                                        <div class="input-group">
                                            <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                                            <input class="form-control" size="8" type="text" id="dateIni" name="dateIni" value="<%= dataAnterior%>" onkeypress="mascara(this, maskData);"/>
                                        </div>
                                        <label for="to"> até </label>
                                        <div class="input-group">
                                            <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                                            <input class="form-control" size="8" type="text" id="dateFim" name="dateFim" value="<%=vDataAtual%>" onkeypress="mascara(this, maskData);" />
                                        </div>
                                        <button type="submit" class="btn btn-sm btn-primary form-control" onclick="waitMsg()"><i class="fa fa-lg fa-search"></i></button>
                                    </div>
                                </form>
                            </div>   
                        </div>

                        <!-- /.row -->
                        <div class="row">
                            <div class="col-lg-3 col-md-6">
                                <div class="panel panel-success">
                                    <div class="panel-heading">
                                        <div class="row">
                                            <div class="col-xs-3">
                                                <i class="fa fa-dollar fa-5x"></i>
                                            </div>
                                            <div class="col-xs-9 text-right">
                                                <div class="huge">R$ <%= df.format(valorTotal/qtdtotal) %></div>
                                                <div>Ticket Médio das Encomendas</div>
                                            </div>
                                        </div>
                                    </div>
                                    <div id="detalhes-ticketMedio" class="panel-body" style="display: none; overflow: auto;max-height: 200px;">
                                        <table class="table table-striped table-hover table-condensed no-margin">
                                            <thead>
                                                <tr>
                                                    <th>Serviço</th>
                                                    <th>Ticket Méd.</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <%= tabelaTicket %>
                                            </tbody>
                                        </table>
                                    </div>
                                    <div class="panel-footer text-primary" style="cursor: pointer;" onclick="mostraDetalhes('ticketMedio');">
                                        <span id="botao-txt-ticketMedio" class="pull-left">Mais Detalhes</span>
                                        <span class="pull-right" style="padding-top: 3px;"><i id="botao-img-ticketMedio" class="fa fa-plus-circle"></i></span>
                                        <div class="clearfix"></div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-lg-3 col-md-6">
                                <div class="panel panel-info">
                                    <div class="panel-heading">
                                        <div class="row">
                                            <div class="col-xs-3">
                                                <i class="fa fa-users fa-5x"></i>
                                            </div>
                                            <div class="col-xs-9 text-right">
                                                <div class="huge"><%= listaClientes.size() %></div>
                                                <div>Clientes Ativos</div>
                                            </div>
                                        </div>
                                    </div>
                                    <div id="detalhes-cliAtiv" class="panel-body" style="display: none; overflow: auto;max-height: 200px;">
                                        <table class="table table-striped table-hover table-condensed no-margin">
                                            <thead>
                                                <tr>
                                                    <th>Cliente</th>
                                                    <th>Qtd.</th>
                                                    <th>Ticket Méd.</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <%
                                                    for(Movimentacao m : listaClientes){
                                                %>
                                                <tr>
                                                    <td><%= m.getDescServico() %></td>
                                                    <td align="right"><%= (int) m.getQuantidade() %></td>
                                                    <td align="right">R$<%= df.format(m.getValorServico()/m.getQuantidade()) %></td>
                                                </tr>
                                                <%}%>
                                            </tbody>
                                        </table>
                                    </div>
                                    <div class="panel-footer text-primary" style="cursor: pointer;" onclick="mostraDetalhes('cliAtiv');">
                                        <span id="botao-txt-cliAtiv" class="pull-left">Mais Detalhes</span>
                                        <span class="pull-right" style="padding-top: 3px;"><i id="botao-img-cliAtiv" class="fa fa-plus-circle"></i></span>
                                        <div class="clearfix"></div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-lg-3 col-md-6">
                                <div class="panel panel-danger">
                                    <div class="panel-heading">
                                        <div class="row">
                                            <div class="col-xs-3">
                                                <i class="fa fa-warning fa-5x"></i>
                                            </div>
                                            <div class="col-xs-9 text-right">
                                                <div class="huge"><%= listaInativos.size() %></div>
                                                <div>Clientes Inativos</div>
                                            </div>
                                        </div>
                                    </div>
                                    <div id="detalhes-cliInat" class="panel-body" style="display: none; overflow: auto;max-height: 200px;">
                                        <table class="table table-striped table-hover table-condensed no-margin">
                                            <thead>
                                                <tr>
                                                    <th>Cliente</th>
                                                    <th>Último Acesso</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <%
                                                    for(Movimentacao m : listaInativos){
                                                %>
                                                <tr>
                                                    <td><%= m.getDescServico() %></td>
                                                    <td><b><%= sdf.format(m.getDataPostagem()) %></b></td>
                                                </tr>
                                                <%}%>
                                            </tbody>
                                        </table>
                                    </div>
                                    <div class="panel-footer text-primary" style="cursor: pointer;" onclick="mostraDetalhes('cliInat');">
                                        <span id="botao-txt-cliInat" class="pull-left">Mais Detalhes</span>
                                        <span class="pull-right" style="padding-top: 3px;"><i id="botao-img-cliInat" class="fa fa-plus-circle"></i></span>
                                        <div class="clearfix"></div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-lg-3 col-md-6">
                                <div class="panel panel-warning">
                                    <div class="panel-heading">
                                        <div class="row">
                                            <div class="col-xs-3">
                                                <i class="fa fa-barcode fa-5x"></i>
                                            </div>
                                            <div class="col-xs-9 text-right">
                                                <div class="huge"><%= listaEtiquetas.size() %></div>
                                                <div>Clientes com Pre-Postagem</div>
                                            </div>
                                        </div>
                                    </div>
                                    <div id="detalhes-etqPrePost" class="panel-body" style="display: none; overflow: auto;max-height: 200px;">
                                        <table class="table table-striped table-hover table-condensed no-margin">
                                            <thead>
                                                <tr>
                                                    <th>Cliente</th>
                                                    <th>Etq. Geradas</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <%
                                                    for(Movimentacao m : listaEtiquetas){
                                                %>
                                                <tr>
                                                    <td><%= m.getDescServico() %></td>
                                                    <td align="right"><%= (int) m.getQuantidade() %></td>
                                                </tr>
                                                <%}%>
                                            </tbody>
                                        </table>
                                    </div>
                                    <div class="panel-footer text-primary" style="cursor: pointer;" onclick="mostraDetalhes('etqPrePost');">
                                        <span id="botao-txt-etqPrePost" class="pull-left">Mais Detalhes</span>
                                        <span class="pull-right" style="padding-top: 3px;"><i id="botao-img-etqPrePost" class="fa fa-plus-circle"></i></span>
                                        <div class="clearfix"></div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-xs-12 col-sm-12 col-md-7 col-lg-8">                                
                                        <div id="linechart" style="min-height: 250px;"></div>
                            </div>
                            <div class="col-xs-12 col-sm-12 col-md-5 col-lg-4">
                                        <div id="donutchart" style="min-height: 250px;"></div>
                            </div>
                        </div>

                    </div>
                </div>
            </div>
        </div>
        <!-- /#page-wrapper -->
        <script type="text/javascript" src="https://www.google.com/jsapi"></script>
        <script type="text/javascript">

            function mostraDetalhes(id) {
                $("#detalhes-" + id).slideToggle("slow", function() {
                    if ($("#detalhes-" + id).css('display') === 'none') {
                        $("#botao-txt-" + id).html('Mostrar Detalhes');
                        $("#botao-img-" + id).attr('class', 'fa fa-plus-circle');
                    } else {
                        $("#botao-txt-" + id).html('Minimizar Detalhes');
                        $("#botao-img-" + id).attr('class', 'fa fa-minus-circle');
                    }
                });
            }

            google.load("visualization", "1", {packages: ["corechart"]});
            google.setOnLoadCallback(drawChart);
            function drawChart() {
                var data = google.visualization.arrayToDataTable([<%= grafico2 %>]);
                var options = {
                    allowHtml: true,
                    width: '100%',
                    height: '100%',
                    tooltip: {isHtml: true},
                    //legend: {position: 'top'},
                    pieHole: 0.4
                };
                var chart = new google.visualization.PieChart(document.getElementById('donutchart'));
                var formatter = new google.visualization.NumberFormat({decimalSymbol: ',', groupingSymbol: '.', negativeColor: 'red', negativeParens: false, prefix: 'R$'});
                formatter.format(data, 1);
                chart.draw(data, options);

                /**************************************************************/

                var data2 = google.visualization.arrayToDataTable([<%=grafico1%>]);
                var options2 = {
                    allowHtml: true,
                    width: '100%',
                    height: '100%',
                    tooltip: {isHtml: true},
                    legend: {position: 'top'}
                };
                var chart2 = new google.visualization.LineChart(document.getElementById('linechart'));
                var formatter = new google.visualization.NumberFormat({decimalSymbol: ',', groupingSymbol: '.', negativeColor: 'red', negativeParens: false, prefix: 'R$'});
                formatter.format(data2, 1);

                chart2.draw(data2, options2);
            }
                                        
                                        
            $(document).ready(function () {
                $("#dateIni").datepicker({
                    showAnim: 'slideDown',
                    maxDate: new Date(),
                    numberOfMonths: 3,
                    onClose: function (selectedDate) {
                        $("#dateFim").datepicker("option", "minDate", selectedDate);
                    }
                });
                $("#dateFim").datepicker({
                    showAnim: 'slideDown',
                    maxDate: new Date(),
                    numberOfMonths: 3,
                    onClose: function (selectedDate) {
                        $("#dateIni").datepicker("option", "maxDate", selectedDate);
                    }
                });
                fechaMsg();
            });
        </script>
    </body>

</html>
<%}%>                                

