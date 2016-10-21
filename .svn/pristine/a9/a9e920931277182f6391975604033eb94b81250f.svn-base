
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
                if (request.getParameter("dataFim") != null) {
                    dataFim = request.getParameter("dataFim");
                }
                String dataIni = Util.SomaData.SomarDiasDatas(dataAtual, -15);
                if (request.getParameter("dataIni") != null) {
                    dataIni = request.getParameter("dataIni");
                }
                String servico = "0";
                if (request.getParameter("servico") != null) {
                    servico = request.getParameter("servico");
                }

                ArrayList<Integer> dptosSessaoUsuario = (ArrayList<Integer>) session.getAttribute("departamentos");
                String departamento = "0";
                String sqlDepto = "";
                if (request.getParameter("departamento") != null) {
                    departamento = request.getParameter("departamento");
                }
                if (departamento.equals("0")) {
                    if (dptosSessaoUsuario != null && dptosSessaoUsuario.size() > 0) {
                        String idsDepto = "";
                        for (Integer idDep : dptosSessaoUsuario) {
                            idsDepto += idDep + ",";
                        }
                        if (!idsDepto.equals("")) {
                            idsDepto = idsDepto.substring(0, idsDepto.lastIndexOf(","));
                            sqlDepto += " AND movimentacao.idDepartamento IN (" + idsDepto + ") ";
                        }
                    }
                } else {
                    sqlDepto = " AND movimentacao.idDepartamento = " + departamento;
                }

                String graf2 = Emporium.Controle.ContrRelatorios.pesquisaSituacaoDoClientePorServico(nomeBD, numCliente, servico, dataIni, dataFim, sqlDepto);
                //  ArrayList lista2 = Emporium.Controle.ContrRelatorios.pesquisaEntregaPorPeriodo(nomeBD, numCliente, servico, dataIni, dataFim, sqlDepto);
                float prz_medio = Emporium.Controle.ContrRelatorios.pesquisaPrazoEntregaPorServico(nomeBD, numCliente, servico, dataIni, dataFim, sqlDepto);
                ArrayList lista = Emporium.Controle.ContrRelatorios.pesquisaPrazoEntregaPorEstado(nomeBD, numCliente, servico, dataIni, dataFim, sqlDepto);
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
                /*    for (int i = 0; i < lista2.size(); i++) {

                 String[] obj = (String[]) lista2.get(i);
                 String qtdPost = obj[0];
                 String qtdEnt = obj[1];
                 String data = obj[2];

                 //Generate <set name='..' value='..'/>
                 lista_dt += "'" + data + "', ";
                 lista_qtdp += "" + qtdPost + ", ";
                 lista_qtde += "" + qtdEnt + ", ";

                 }*/
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

            $(function () {
                $("#dataIni").datepicker({
                    minDate: '<%=dataInicioCalendario%>',
                    maxDate: '<%= vDataAtual%>',
                    showOn: "button",
                    buttonImage: "../../imagensNew/calendario.png",
                    buttonImageOnly: true,
                    showAnim: "slideDown"
                });
                $("#dataFim").datepicker({
                    minDate: '<%=dataInicioCalendario%>',
                    maxDate: '<%= vDataAtual%>',
                    showOn: "button",
                    buttonImage: "../../imagensNew/calendario.png",
                    buttonImageOnly: true,
                    showAnim: "slideDown"
                });
            });




            jQuery(function () {

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
                    yAxis: [{// Primary yAxis
                            labels: {
                                align: 'left',
                                x: 0,
                                y: -2
                            },
                            showFirstLabel: false,
                            title: {
                                text: ''
                            }
                        }, {// Secondary yAxis
                            labels: {
                                style: {
                                    color: '#ed561b'
                                }
                            }, title: {
                                text: 'QUANTIDADE',
                                style: {
                                    color: '#ed561b'
                                }
                            },
                            showFirstLabel: false,
                            opposite: true
                        }, {// Secondary yAxis
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
                        formatter: function () {
                            return '' + this.x + ': ' + this.y + (this.series.name === 'Quantidade de Obj. Entregues' ? ' Objetos' : ' Dias');
                        }
                    },
                    series: [{
                            name: 'Quantidade de Obj. Entregues',
                            color: '#ed561b',
                            type: 'column',
                            yAxis: 1,
                            data: [<%= lista_qtd%>]
                        }, {
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
                        text: 'Situação dos Objetos'
                    },
                    tooltip: {
                        formatter: function () {
                            return '<b>' + this.point.name + '</b>: ' + this.y + ' Objetos';
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
                            size: 220
                        }
                    },
                    legend: {
                        layout: 'vertical',
                        align: 'center',
                        verticalAlign: 'bottom',
                        borderWidth: 1,
                        labelFormatter: function () {
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
                        marginBottom: 120
                    },
                    title: {
                        text: 'Quantidade de Objetos Entregues por Periodo'
                    },
                    legend: {
                        enabled: true
                    },
                    xAxis: [{
                            categories: [<%= lista_dt%>],
                            labels: {
                                y: 35,
                                rotation: -90
                            }
                        }],
                    yAxis: [{
                            title: {
                                text: ''
                            },
                            showFirstLabel: false,
                            opposite: true
                        }, {// Secondary yAxis
                            title: {
                                text: 'Quantidade'
                            },
                            showFirstLabel: false
                        }],
                    tooltip: {
                        formatter: function () {
                            return '' + this.x + ': ' + this.y + (this.series.name === 'Quantidade de Obj. Postados' ? ' Obj. Postados' : ' Obj. Entregues');
                        }
                    },
                    series: [{
                            name: 'Quantidade de Obj. Postados',
                            color: '#058dc7',
                            type: 'column',
                            yAxis: 1,
                            data: [<%= lista_qtdp%>]
                        }, {
                            name: 'Quantidade de Obj. Entregues',
                            color: '#50b432',
                            type: 'column',
                            yAxis: 1,
                            data: [<%= lista_qtde%>]

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
                        <div style="width: 100%; height: 400px;">
                            <div style=" float: left; width: 600px;">
                                <ul class="ul_formulario">
                                    <li>
                                        <dd>
                                            <label>Periodo de Data de Postagem</label>
                                            <input type="text" style="width:60px;" name="dataIni" id="dataIni" value="<%= dataIni%>" maxlength="10" onkeypress="mascara(this, maskData);" />
                                            até
                                            <input type="text" style="width:60px;" name="dataFim" id="dataFim" value="<%= dataFim%>" maxlength="10" onkeypress="mascara(this, maskData);" />
                                        </dd>
                                        <dd>
                                            <label>Departamento</label>
                                            <select style="width: 250px;" name="departamento" id="departamento">
                                                <option value="0">-- TODOS --</option>
                                                <%
                                                    ArrayList<ClientesDeptos> listaDep = ContrClienteDeptos.consultaDeptos(idCliente, nomeBD);
                                                    for (int i = 0; i < listaDep.size(); i++) {
                                                        ClientesDeptos cd = listaDep.get(i);
                                                        if (dptosSessaoUsuario.contains(cd.getIdDepartamento())) {
                                                %>
                                                <option <%if (departamento.equals(cd.getIdDepartamento() + "")) {%>selected<%}%> value="<%= cd.getIdDepartamento()%>"><%= cd.getNomeDepartamento()%></option>
                                                <%}
                                                    }%>
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
                                                <option value="<%= sv.getGrupoServico()%>"><%= sv.getNomeSimples()%></option>
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
                            </div>
                            <div id="chart2" style="float: right; width: 300px; height: 400px"></div>
                            <div style="width: 830px; float: left; height: 300px;">
                                <div id="chart1" style="float:left; width: 100%; height: 260px"></div> 
                                <!-- <div id="chart3" style="float:right; width: 35%; height: 350px"></div> -->                   
                            </div>
                        </div>
                    </form>
                    <img width="100%" src="../../imagensNew/linha.jpg"/>
                </div>
                <div style="width: 100%;clear: both;">
                    <div id="titulo2">Resultado da Pesquisa</div>
                    <div id="tableObjeto">
                        <div class="" id="" style="float: right; margin: 12px;">   

                            <label>Filtrar por situação</label>
                            <select style="width: 110px;" id="situacao" name="situacao">
                                <option selected="" value="">-- TODAS --</option>
                                <option value=" AND (last_status_code, last_status_type) NOT IN ((0,'BDE'),(1,'BDE'),(0,'BDI'),(1,'BDI'),(0,'BDR'),(1,'BDR')) ">NÃO ENTREGUE</option>
                                <option value=" AND (last_status_code, last_status_type) IN ((0,'BDE'),(1,'BDE'),(0,'BDI'),(1,'BDI'),(0,'BDR'),(1,'BDR')) ">ENTREGUE</option>
                                <option value=" AND last_status_date IS NULL ">POSTADO</option>
                                <option value=" AND (last_status_code, last_status_type) NOT IN 
                                        ((0,'BDE'),(1,'BDE'),(0,'BDI'),(1,'BDI'),(0,'BDR'),(1,'BDR'),
                                        (9,'BDE'),(12,'BDE'),(28,'BDE'),(37,'BDE'),(43,'BDE'),(50,'BDE'),(51,'BDE'),(52,'BDE'),(69,'BDE'),
                                        (9,'BDI'),(12,'BDI'),(28,'BDI'),(37,'BDI'),(43,'BDI'),(50,'BDI'),(51,'BDI'),(52,'BDI'),(69,'BDI'),
                                        (9,'BDR'),(12,'BDR'),(28,'BDR'),(37,'BDR'),(43,'BDR'),(50,'BDR'),(51,'BDR'),(52,'BDR'),(69,'BDR'),
                                        (4,'BDE'),(5,'BDE'),(6,'BDE'),(7,'BDE'),(8,'BDE'),(10,'BDE'),(18,'BDE'),(19,'BDE'),(20,'BDE'),(21,'BDE'),(22,'BDE'),(23,'BDE'),(25,'BDE'),(26,'BDE'),(27,'BDE'),(33,'BDE'),(34,'BDE'),(36,'BDE'),(40,'BDE'),(42,'BDE'),(48,'BDE'),(49,'BDE'),(56,'BDE'),
                                        (4,'BDI'),(5,'BDI'),(6,'BDI'),(7,'BDI'),(8,'BDI'),(10,'BDI'),(18,'BDI'),(19,'BDI'),(20,'BDI'),(21,'BDI'),(22,'BDI'),(23,'BDI'),(25,'BDI'),(26,'BDI'),(27,'BDI'),(33,'BDI'),(34,'BDI'),(36,'BDI'),(40,'BDI'),(42,'BDI'),(48,'BDI'),(49,'BDI'),(56,'BDI'),
                                        (4,'BDR'),(5,'BDR'),(6,'BDR'),(7,'BDR'),(8,'BDR'),(10,'BDR'),(18,'BDR'),(19,'BDR'),(20,'BDR'),(21,'BDR'),(22,'BDR'),(23,'BDR'),(25,'BDR'),(26,'BDR'),(27,'BDR'),(33,'BDR'),(34,'BDR'),(36,'BDR'),(40,'BDR'),(42,'BDR'),(48,'BDR'),(49,'BDR'),(56,'BDR')) ">ENCAMINHADO</option>
                                <option value=" AND (last_status_code, last_status_type) IN 
                                        ((4,'BDE'),(5,'BDE'),(6,'BDE'),(7,'BDE'),(8,'BDE'),(10,'BDE'),(18,'BDE'),(19,'BDE'),(20,'BDE'),(21,'BDE'),(22,'BDE'),(23,'BDE'),(25,'BDE'),(26,'BDE'),(27,'BDE'),(33,'BDE'),(34,'BDE'),(36,'BDE'),(40,'BDE'),(42,'BDE'),(48,'BDE'),(49,'BDE'),(56,'BDE'),
                                        (4,'BDI'),(5,'BDI'),(6,'BDI'),(7,'BDI'),(8,'BDI'),(10,'BDI'),(18,'BDI'),(19,'BDI'),(20,'BDI'),(21,'BDI'),(22,'BDI'),(23,'BDI'),(25,'BDI'),(26,'BDI'),(27,'BDI'),(33,'BDI'),(34,'BDI'),(36,'BDI'),(40,'BDI'),(42,'BDI'),(48,'BDI'),(49,'BDI'),(56,'BDI'),
                                        (4,'BDR'),(5,'BDR'),(6,'BDR'),(7,'BDR'),(8,'BDR'),(10,'BDR'),(18,'BDR'),(19,'BDR'),(20,'BDR'),(21,'BDR'),(22,'BDR'),(23,'BDR'),(25,'BDR'),(26,'BDR'),(27,'BDR'),(33,'BDR'),(34,'BDR'),(36,'BDR'),(40,'BDR'),(42,'BDR'),(48,'BDR'),(49,'BDR'),(56,'BDR')) ">DEVOLVIDO</option>
                                <option value=" AND (last_status_code, last_status_type) IN 
                                        ((9,'BDE'),(12,'BDE'),(28,'BDE'),(37,'BDE'),(43,'BDE'),(50,'BDE'),(51,'BDE'),(52,'BDE'),(69,'BDE'),
                                        (9,'BDI'),(12,'BDI'),(28,'BDI'),(37,'BDI'),(43,'BDI'),(50,'BDI'),(51,'BDI'),(52,'BDI'),(69,'BDI'),
                                        (9,'BDR'),(12,'BDR'),(28,'BDR'),(37,'BDR'),(43,'BDR'),(50,'BDR'),(51,'BDR'),(52,'BDR'),(69,'BDR')) ">EXTRAVIADO</option>
                                <option value=" AND (last_status_code, last_status_type) IN 
                                        ((26,'BDE'),(24,'BDE'),(26,'BDI'),(24,'BDI'),(26,'BDR'),(24,'BDR'),(0,'LDI'),(1,'LDI'),(2,'LDI'),(3,'LDI'),(4,'LDI'),(14,'LDI')) ">AGUARDA RETIRADA</option>



                            </select>
                            
                            <label>Filtrar por prazos</label>
                            <select>
                                <option>Todos os objetos</option>
                                <option>Somente c/ atrazo</option>
                                <option>Somente no prazo</option>
                                <option>Soemnte antecipados</option>
                            </select>
                        </div>
                        <!--  <div style="padding:8px 5px; background: white;">
  
                              <a href="#" onclick="document.formEXP.action = '../AjaxPages/xls_sintetico.jsp';
                                      document.formEXP.submit();"><img class="link_img" src="../../imagensNew/excel.png"> EXPORTAR .XLS</a>
                              <b style="margin:0 12px 0 10px;">|</b>
                              <a href="#" onclick="document.formEXP.action = '../AjaxPages/csv_sintetico.jsp';
                                      document.formEXP.submit();"><img class="link_img" src="../../imagensNew/csv.png"> EXPORTAR .CSV</a>
  
                          </div> -->
                        <table id="barraAtendimento" border="0">
                            <tbody><tr>
                                    <td align="left" style="font-weight:bold;font-size:12px;">
                                        Pesquisa Rápida:
                                        <select style="min-width:150px;" id="columns2" onchange="sorter2.search('query2')"><option value="-1">Todas as Colunas</option><option value="0"></option><option value="1">OBJETO</option><option value="2">SERVIÇO</option><option value="3">PESO</option><option value="4">QTD</option><option value="5">POSTAGEM</option><option value="6">VALOR</option><option value="7">DESTINATÁRIO</option><option value="8">CEP</option><option value="9">SITUAÇÃO</option><option value="10">NF</option><option value="11">DEPARTAMENTO</option><option value="12">PRAZO EST.</option><option value="13">PRAZO REAL</option></select>
                                        <input type="text" id="query2" onkeyup="sorter2.search('query2')" placeholder="Digite aqui a sua pesquisa...">
                                            <a style="text-decoration:none;font-weight:bold;font-size:11px;" href="javascript:document.getElementById('query2').value='';sorter2.reset()">RESTAURAR PADRÕES</a>
                                    </td>
                                    <td align="right">
                                        <div class="details" style="clear:both;">
                                            <div>Resultado <span id="startrecord2">1</span>-<span id="endrecord2">20</span> de <span id="totalrecords2">44</span></div>
                                        </div>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                        <div style="max-width:100%;overflow:auto;">
                            <table cellpadding="0" cellspacing="0" border="0" id="table2" class="tinytable">
                                <thead>
                                    <tr>
                                        <th width="10" class="nosort"><h3></h3></th>
                                        <th width="100" class="head"><h3>OBJETO</h3></th>
                                        <th class="head"><h3>SERVIÇO</h3></th>
                                        <th width="50" class="asc"><h3>POSTAGEM</h3></th>
                                        <th width="50" class="head"><h3>PRAZO EST.</h3></th>
                                        <th width="50" class="head"><h3>PRAZO REAL</h3></th>
                                        <th width="50" class="head"><h3>VALOR</h3></th>
                                        <th class="head"><h3>DESTINATÁRIO</h3></th>
                                        <th width="80" class="head"><h3>CEP</h3></th>
                                        <th width="100" class="head"><h3>SITUAÇÃO</h3></th>
                                        <th width="100" class="head"><h3>DEPARTAMENTO</h3></th>
                                        <th width="50" class="head"><h3>PROTOCOLO</h3></th>



                                    </tr>
                                </thead>
                                <tbody>
                                    <tr align="center" style="font-size: 10px;  " class="evenrow" onmouseover="sorter2.hover(0, 1)" onmouseout="sorter2.hover(0, 0)">
                                        <td class="">
                                            <img class="link_img" src="../../imagensNew/mail_send.png"/></td>
                                        <td class="">

                                            <form name="frmDU599883489BR" id="frmDU599883489BR" method="post" action="http://www2.correios.com.br/sistemas/rastreamento/Resultado.cfm" target="_blank">
                                                <input type="hidden" name="objetos" id="objetos" value="DU599883489BR">
                                            </form>                    
                                            <a href="#" onclick="document.getElementById('frmDU599883489BR').submit();">DU599883489BR</a>
                                        </td>
                                        <td align="left" class=""><a href="visulizaTicket.jsp?idmov=416718" target="_blank">ESEDEX</a></td>
                                        <td class="evenselected">17/08/2016</td>
                                        <td class="">19/08/2016</td>
                                        <td class="">---</td>
                                        <td nowrap="" align="left" class="">R$ 58,07</td>
                                        <td style="font-size: 10px;" class=""><a onclick="verVenda(1009401);" style="cursor:pointer;">AQUECEDORES IVAIR LTDA - ME</a></td>
                                        <td class="">85807-060</td>
                                        <td class="">Objeto encaminhado </td>
                                        <td class="">PECAS</td>
                                        <td class="">40221234</td>
                                    </tr>

                                </tbody>
                                <tfoot>
                                    <tr style="background: #f0f0f0; color:red; font-size: 12px;">
                                        <td colspan="4"></td>
                                        <td nowrap="true" align="center"></td>
                                        <td></td>

                                        <td nowrap="true"></td>

                                        <td colspan="7"></td>
                                    </tr>
                                </tfoot>
                            </table>
                        </div>
                        <div id="tablefooter" align="center">
                            <div align="left" style="float:left; width:20%;">
                                <select onchange="sorter2.size(this.value)">
                                    <option value="5">5</option>
                                    <option value="10">10</option>
                                    <option value="20" selected="selected">20</option>
                                    <option value="50">50</option>
                                    <option value="100">100</option>
                                </select>
                                <span>Linhas por Página</span>
                            </div>
                            <div id="tablenav2" class="tablenav" style="display: block;">
                                <div>
                                    <img src="../../javascript/plugins/TableSorter/images/left_end.png" width="20" height="20" alt="First Page" onclick="sorter2.move(-1, true)">
                                        <img src="../../javascript/plugins/TableSorter/images/left.png" width="20" height="20" alt="First Page" onclick="sorter2.move(-1)">
                                            <img src="../../javascript/plugins/TableSorter/images/right.png" width="20" height="20" alt="First Page" onclick="sorter2.move(1)">
                                                <img src="../../javascript/plugins/TableSorter/images/right_end.png" width="20" height="20" alt="Last Page" onclick="sorter2.move(1, true)">
                                                    <select style="margin-left:5px;" id="pagedropdown2" onchange="sorter2.goto(this.value)"><option value="1">1</option><option value="2">2</option><option value="3">3</option></select>
                                                    <a style="margin-left:10px;" href="javascript:sorter2.showall()">Ver Tudo</a>
                                                    </div>
                                                    </div>
                                                    <div id="tablelocation">
                                                        <div class="page">Página <span id="currentpage2">1</span> de <span id="totalpages2">3</span></div>
                                                    </div>
                                                    </div>
                                                    </div>
                                                    </div>                       
                                                    </div>
                                                    </div>
                                                    </body>
                                                    </html>
                                                    <%}%>