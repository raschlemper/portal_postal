
<%@page import="Entidade.ServicoECT"%>
<%@page import="Controle.ContrServicoECT"%>
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

                Date dataAtual = new Date();
                String dataInicioCalendario = Util.SomaData.SomarDiasDatas(dataAtual, -60); // diminui 2 meses
                String vDataAtual = sdf.format(dataAtual);
                
                String dataFim = vDataAtual;
                if(request.getParameter("dataFim") != null){
                    dataFim = request.getParameter("dataFim");
                }
                String dataIni = Util.SomaData.SomarDiasDatas(dataAtual, -15);
                if(request.getParameter("dataIni") != null){
                    dataIni = request.getParameter("dataIni");
                }
                String servico = "0";
                if(request.getParameter("servico") != null){
                    servico = request.getParameter("servico");
                }
                String departamento = "0";
                if(request.getParameter("departamento") != null){
                    departamento = request.getParameter("departamento");
                }
                
                String graf2 = Emporium.Controle.ContrRelatorios.pesquisaSituacaoDoClientePorServico(nomeBD, numCliente, servico, dataIni, dataFim, departamento);
                float prz_medio = Emporium.Controle.ContrRelatorios.pesquisaPrazoEntregaPorServico(nomeBD, numCliente, servico, dataIni, dataFim, departamento);
                ArrayList lista = Emporium.Controle.ContrRelatorios.pesquisaPrazoEntregaPorEstado(nomeBD, numCliente, servico, dataIni, dataFim, departamento);
                ArrayList lista2 = Emporium.Controle.ContrRelatorios.pesquisaEntregaPorPeriodo(nomeBD, numCliente, servico, dataIni, dataFim, departamento);
                String lista_uf = "", lista_prz = "", lista_med = "", lista_qtd = "";
                for (int i = 0; i < lista.size(); i++) {

                    String[] obj = (String[]) lista.get(i);
                    String conta = obj[0];
                    String prazo = obj[1];
                    String estado = obj[2];

                    //Generate <set name='..' value='..'/>
                    lista_uf += "'" + estado + "', ";
                    lista_prz += "" + prazo + ", ";
                    lista_med += "" + prz_medio + ", ";
                    lista_qtd += "" + conta + ", ";

                }
                
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

            $(function() {
                $("#dataIni").datepicker({
                    minDate:'<%=dataInicioCalendario%>',
                    maxDate:'<%= vDataAtual%>',
                    showOn: "button",
                    buttonImage: "../../imagensNew/calendario.png",
                    buttonImageOnly: true,
                    showAnim: "slideDown"
                });
                $("#dataFim").datepicker({
                    minDate:'<%=dataInicioCalendario%>',
                    maxDate:'<%= vDataAtual%>',
                    showOn: "button",
                    buttonImage: "../../imagensNew/calendario.png",
                    buttonImageOnly: true,
                    showAnim: "slideDown"
                });
            });


            
            
            jQuery(function() {
            
                var chart1 = new Highcharts.Chart({
                    chart: {
                        renderTo: 'chart1'
                    },
                    title: {
                        text: 'Média em Dias de Entrega dos Objetos por Estado'
                    },
                    legend: {
                        enabled: true
                    },
                    xAxis: [{
                            categories: [<%= lista_uf%>]
                        }],
                    yAxis: [{ // Primary yAxis
                            labels: {
                                align: 'left',
                                x: 0,
                                y: -2
                            },
                            showFirstLabel: false,
                            title: {
                                text: ''
                            }
                        }, { // Secondary yAxis
                            labels: {
                                style: {
                                    color: '#ed561b'
                                }
                            },title: {
                                text: 'QUANTIDADE',
                                style: {
                                    color: '#ed561b'
                                }
                            }, 
                            showFirstLabel: false,
                            opposite: true
                        }, { // Secondary yAxis
                            labels: {
                                style: {
                                    color: '#058dc7'
                                }
                            }, title: {
                                text: 'DIAS',
                                style: {
                                    color: '#058dc7'
                                }
                            },
                            showFirstLabel: false
                        }],
                    tooltip: {
                        formatter: function() {
                            return ''+ this.x +': '+ this.y + (this.series.name === 'Quantidade de Obj. Entregues' ? ' Objetos' : ' Dias');
                        }
                    },
                    series: [ {
                            name: 'Quantidade de Obj. Entregues',
                            color: '#ed561b',
                            type: 'column',
                            yAxis: 1,
                            data: [<%= lista_qtd%>]
                        },{
                            name: 'Média em Dias da Entrega',
                            color: '#058dc7',
                            type: 'column',
                            yAxis: 2,
                            data: [<%= lista_prz%>]      
      
                        }, {
                            name: 'Média Geral em Dias',
                            color: '#50b432',
                            type: 'spline',
                            yAxis: 2,
                            data: [<%= lista_med%>]
                        }]
                }); 
            
                var chart2 = new Highcharts.Chart({
                    chart: {
                        renderTo: 'chart2',
                        plotBackgroundColor: null,
                        plotBorderWidth: null,
                        plotShadow: true
                    },
                    title: {
                        text: 'Situações dos Objetos'
                    },
                    tooltip: {
                        formatter: function() {
                            return '<b>'+ this.point.name +'</b>: '+ this.y +' Objetos';
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
                        layout: 'vertical',
                        align: 'center',
                        verticalAlign: 'bottom',
                        borderWidth: 1,
                        labelFormatter: function() {
                            return this.name + ': ' + this.y + ' Objetos';
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
                        marginBottom:120
                    },
                    title: {
                        text: 'Quantidade de Objetos Entregues por Periodo'
                    },
                    legend: {
                        enabled: true
                    },
                    xAxis: [{
                            categories: [<%= lista_dt %>],
                            labels:{
                                y: 35,
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
                                text: 'Quantidade'
                            },
                            showFirstLabel: false
                        }],
                    tooltip: {
                        formatter: function() {
                            return ''+ this.x +': '+ this.y + (this.series.name === 'Quantidade de Obj. Postados' ? ' Obj. Postados' : ' Obj. Entregues');
                        }
                    },
                    series: [ {
                            name: 'Quantidade de Obj. Postados',
                            color: '#058dc7',
                            type: 'column',
                            yAxis: 1,
                            data: [<%= lista_qtdp %>]
                        },{
                            name: 'Quantidade de Obj. Entregues',
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
                    
                    <form action="graf_objetos.jsp" method="post">
                    <ul class="ul_formulario">
                        <li>
                            <dd>
                                <label>Periodo de Data de Postagem</label>
                                <input type="text" style="width:60px;" name="dataIni" id="dataIni" value="<%= dataIni %>" maxlength="10" onkeypress="mascara(this,maskData);" />
                                até
                                <input type="text" style="width:60px;" name="dataFim" id="dataFim" value="<%= dataFim %>" maxlength="10" onkeypress="mascara(this,maskData);" />
                            </dd>
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
                            <dd style="width: 800px;">
                                <div class="buttons">
                                    <button type="submit" class="regular" onclick=""><img src="../../imagensNew/lupa.png"/> ATUALIZAR GRÁFICOS</button>
                                    <b style="color:red;">* Aparecerá nos gráficos somente os objetos com código de rastreamento!</b>
                                </div>
                            </dd>
                        </li>
                    </ul>
                    </form>
                    <img width="100%" src="../../imagensNew/linha.jpg"/>
                    
                    <div style="width: 100%; height: 410px;">
                        <div id="chart2" style="float: left; width: 350px; height: 400px"></div>
                        <div id="chart3" style="float:right;width: 800px; height: 400px"></div>
                    </div>
                    <div style="width: 100%; height: 400px;">
                        <div id="chart1" style="width: 100%; height: 400px"></div>                    
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
<%}%>