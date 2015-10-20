
<%@page import="Entidade.ServicoECT"%>
<%@page import="Controle.ContrServicoECT"%>
<%@page import="Util.FormataString"%>
<%@page import="Entidade.ClientesDeptos"%>
<%@page import="Controle.ContrClienteDeptos"%>
<%@page import="java.util.ArrayList, java.util.Date, java.text.SimpleDateFormat" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <%
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat sdfBD = new SimpleDateFormat("yyyy/MM/dd");
            String nomeBD = (String) session.getAttribute("nomeBD");
            if (nomeBD == null) {
                response.sendRedirect("../../index.jsp?msg=Sua sessao expirou! Para voltar ao Portal faça seu login novamente!");
            } else {

                String numCliente = String.valueOf(session.getAttribute("idCliente"));
                        int idCliente = (Integer) session.getAttribute("idCliente");
                
                String servico = "0";
                if(request.getParameter("servico") != null){
                    servico = request.getParameter("servico");
                }
                String departamento = "0";
                if(request.getParameter("departamento") != null){
                    departamento = request.getParameter("departamento");
                }
                
                String graf2 = Emporium.Controle.ContrRelatorios.pesquisaDespesasDoClientePorServico(nomeBD, numCliente, departamento);
                
                ArrayList lista2 = Emporium.Controle.ContrRelatorios.pesquisaDespesasPorPeriodo(nomeBD, numCliente, servico, departamento);
                String lista_dt = "", lista_qtdp = "", lista_qtde = "";
                for (int i = 0; i < lista2.size(); i++) {

                    String[] obj = (String[]) lista2.get(i);
                    String qtdPost = obj[0];
                    String qtdEnt = obj[1];
                    String data = obj[2];

                    //Generate <set name='..' value='..'/>
                    lista_dt += "'" + data + "', ";
                    lista_qtdp += "" + qtdPost + ", ";
                    lista_qtde += "" + qtdEnt + ", ";

                }
        %>
        <meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
        <title>Portal Postal | Pesquisa de Objetos</title>
        <meta name="keywords" content="" />
        <meta name="description" content="" />

        <link href="../../css/estilo.css" rel="stylesheet" type="text/css" />
        <script type="text/javascript" src="../js/ajax_portal.js"></script>
        <script type="text/javascript" src="../../javascript/mascara.js"></script>

        <link type="text/css" href="../../javascript/jquery/css/jquery-ui-1.8.16.custom.css" rel="stylesheet" />
        <script type="text/javascript" src="../../javascript/jquery/js/jquery-1.6.2.min.js"></script>
        <script type="text/javascript" src="../../javascript/jquery/js/jquery-ui-1.8.16.custom.min.js"></script>
        <script type="text/javascript" src="../../javascript/jquery/js/jquery.ui.datepicker-pt-BR.js"></script>

        <!-- FUSION CHARTS -->
        <script type="text/javascript" src="../../javascript/plugins/HighCharts/js/highcharts.js"></script>
        <script type="text/javascript" src="../../javascript/plugins/HighCharts/js/themes/grid.js"></script>
        <!-- FIM FUSION CHARTS -->

        <!-- TableSorter -->
        <link rel="stylesheet" href="../../javascript/plugins/TableSorter/styleSorterV3.css" />
        <script type="text/javascript" src="../../javascript/plugins/TableSorter/scriptSorterV3.js"></script>
        <!-- TableSorter -->

        <link rel="stylesheet" href="../../javascript/plugins/dropdown/css/style.css" type="text/css" media="screen, projection"/>
        <!--[if lte IE 7]><link rel="stylesheet" type="text/css" href="../../javascript/plugins/dropdown/css/ie.css" media="screen" /><![endif]-->
        <script type="text/javascript" language="javascript" src="../../javascript/plugins/dropdown/js/jquery.dropdownPlain.js"></script>

        <script type="text/javascript" charset="utf-8">
         
            jQuery(function() {
            
                var chart2 = new Highcharts.Chart({
                    chart: {
                        renderTo: 'chart2',
                        plotBackgroundColor: null,
                        plotBorderWidth: null,
                        plotShadow: true
                    },
                    title: {
                        text: 'Postagens do Mês Atual'
                    },
                    tooltip: {
                        formatter: function() {
                            return this.point.name +' | R$ '+ this.y;
                        }
                    },
                    plotOptions: {
                        pie: {
                            allowPointSelect: true,
                            cursor: 'pointer',
                            dataLabels: {
                                enabled: false
                            },
                            showInLegend: true,
                            size:220
                        }
                    },
                    legend: {
                        width: '100%',
                        layout: 'vertical',
                        align: 'center',
                        verticalAlign: 'bottom',
                        borderWidth: 1,
                        labelFormatter: function() {
                            return this.name + ' | R$ ' + this.y;
                        }
                    },
                    series: [{
                            type: 'pie',
                            name: 'Browser share',
                            data: [<%= graf2%>]
                        }]
                });
                
                var chart3 = new Highcharts.Chart({
                    chart: {
                        renderTo: 'chart3',
                        marginBottom:110
                    },
                    title: {
                        text: 'Evolução das Despesas dos Últimos Meses'
                    },
                    legend: {
                        enabled: true
                    },
                    xAxis: [{
                            categories: [<%= lista_dt %>],
                            labels:{
                                y: 30,
                                rotation:-90
                            }
                        }],
                    yAxis: [{
                            title: {
                                text: ''
                            }, 
                            showFirstLabel: false,
                            opposite: true
                        }, { // Secondary yAxis
                            title: {
                                text: 'Quantidade / Valor (R$)'
                            },
                            showFirstLabel: false
                        }],
                    tooltip: {
                        formatter: function() {
                            if(this.series.name == 'Quantidade de Objetos Postados'){
                                return ''+ this.x +': '+ this.y + ' Objetos';
                            }else{
                                return ''+ this.x +': R$ '+ this.y;
                            }                            
                        }
                    },
                    series: [ {
                            name: 'Quantidade de Objetos Postados',
                            color: '#058dc7',
                            type: 'column',
                            yAxis: 1,
                            data: [<%= lista_qtdp %>]
                        },{
                            name: 'Valor Total das Postagens',
                            color: '#50b432',
                            type: 'column',
                            yAxis: 1,
                            data: [<%= lista_qtde %>]
      
                        }]
                });

            });
        </script>
    </head>
    <body>
        <div id="divInteracao" class="esconder" align="center"><input id="textointeracao" /></div>
        <div id="divProtecao" class="esconder"></div>

        <%@ include file="../../Includes/telaMsg.jsp" %>
        <%@ include file="../../Includes/menu_cliente.jsp" %>

        <div id="divPrincipal" align="center">
            <div id="container">
                <div id="conteudo">
                    
                    <form action="graf_despesas.jsp" method="post">
                    <ul class="ul_formulario">
                        <li>
                            <dd>
                                <label>Departamento</label>
                                <select style="width: 250px;" name="departamento" id="departamento">
                                    <option value="0">-- TODOS --</option>
                                    <%
                                        ArrayList<ClientesDeptos> listaDep = ContrClienteDeptos.consultaDeptos(idCliente, nomeBD);
                                        for (int i = 0; i < listaDep.size(); i++) {
                                            ClientesDeptos cd = listaDep.get(i);
                                            String depto = FormataString.removeAccentsToUpper(cd.getNomeDepartamento());
                                    %>
                                    <option value="<%=depto%>"><%= cd.getNomeDepartamento()%></option>
                                    <%}%>
                                </select>
                            </dd>
                            <dd>
                                <label>Serviço</label>
                                <select style="width: 250px;" id="servico" name="servico">
                                    <option value="0">-- TODOS --</option>
                                    <%
                                    ArrayList<ServicoECT> listaTipoPostagem = ContrServicoECT.consultaServicosPorGrupo();
                                    for (int i = 0; i < listaTipoPostagem.size(); i++) {
                                        ServicoECT sv = listaTipoPostagem.get(i);
                                    %>
                                    <option value="<%= sv.getGrupoServico() %>"><%= sv.getNomeSimples() %></option>
                                    <%}%>
                                    <option value="OUTROS">OUTROS</option>   
                                </select>
                            </dd>
                        </li>
                        <li>
                            <dd style="width: 500px;">
                                <div class="buttons">
                                    <button type="submit" class="regular" onclick=""><img src="../../imagensNew/lupa.png"/> ATUALIZAR GRÁFICOS</button>
                                </div>
                            </dd>
                        </li>
                    </ul>
                    </form>
                    <img width="100%" src="../../imagensNew/linha.jpg"/>
                    
                    <div style="width: 100%; height: 610px;">
                        <div id="chart2" style="float: left; width: 350px; height: 600px"></div>
                        <div id="chart3" style="float:right;width: 800px; height: 600px"></div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
<%}%>