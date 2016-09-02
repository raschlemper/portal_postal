
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
                ArrayList<Integer> acessosUs = (ArrayList<Integer>) session.getAttribute("acessos");
                String numCliente = String.valueOf(session.getAttribute("idCliente"));
                int idCliente = (Integer) session.getAttribute("idCliente");
                int nivel = (Integer) session.getAttribute("nivelUsuarioEmp");

                Date dataAtual = new Date();
                String vDataAtual = sdf.format(dataAtual);
                String dataOntem = Util.SomaData.SomarDiasDatas(dataAtual, -1);
                String dataInicioCalendario = Util.SomaData.SomarDiasDatas(dataAtual, -180); // diminui 2 meses
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
        <script type="text/javascript" src="../../javascript/plugins/dropdown/js/jquery.dropdownPlain.js"></script>


        <script type="text/javascript" charset="utf-8">

            function chamaDivProtecao() {
                var classe = document.getElementById("divProtecao").className;
                if (classe === 'esconder') {
                    document.getElementById("divProtecao").className = "mostrar";
                    document.getElementById("divInteracao").className = "mostrar";
                } else {
                    document.getElementById("divProtecao").className = "esconder";
                    document.getElementById("divInteracao").className = "esconder";
                }
            }

            $(document).ready(function () {
                pesquisaSintetica('<%=numCliente%>', '<%=nomeBD%>');
            });

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

            var sorter2 = new TINY.table.sorter('sorter2', 'table2', {
                headclass: 'head',
                ascclass: 'asc',
                descclass: 'desc',
                evenclass: 'evenrow',
                oddclass: 'oddrow',
                evenselclass: 'evenselected',
                oddselclass: 'oddselected',
                paginate: true,
                size: 20,
                colddid: 'columns2',
                currentid: 'currentpage2',
                totalid: 'totalpages2',
                startingrecid: 'startrecord2',
                endingrecid: 'endrecord2',
                totalrecid: 'totalrecords2',
                hoverid: 'selectedrowDefault',
                pageddid: 'pagedropdown2',
                navid: 'tablenav2',
                sortcolumn: 5,
                sortdir: -1,
                init: false
            });


            var chart;
            var options = {
                chart: {
                    renderTo: 'divGrafico1',
                    plotBackgroundColor: null,
                    plotBorderWidth: null,
                    plotShadow: true
                },
                title: {
                    text: 'SITUAÇÕES'
                },
                tooltip: {
                    formatter: function () {
                        return "<b>" + this.point.name + "</b>: " + this.y + "";
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
                        size: 125
                    }
                },
                legend: {
                    layout: 'vertical',
                    align: 'right',
                    verticalAlign: 'top',
                    x: -10,
                    y: 40,
                    borderWidth: 0,
                    labelFormatter: function () {
                        return this.name + ': ' + this.y;
                    }
                },
                series: [{
                        type: 'pie',
                        name: 'Browser share',
                        data: []
                    }]
            };

            function teste() {
                var dados = [];
                var aux = document.getElementById("dadosGrafico").value.split(";");
                for (var i = 0; i < aux.length; i++) {
                    var aux2 = aux[i].split(",");
                    dados.push([aux2[0], parseInt(aux2[1])]);
                }

                options.series[0].data = dados;
                chart = new Highcharts.Chart(options);
            }

            function validaDataSint() {
                var data1 = $("#dataIni").val();
                var data2 = $("#dataFim").val();
                //console.log(data1);
                //console.log(data2);
                var nova_data1 = parseInt(data1.split("/")[2].toString() + data1.split("/")[1].toString() + data1.split("/")[0].toString());
                var nova_data2 = parseInt(data2.split("/")[2].toString() + data2.split("/")[1].toString() + data2.split("/")[0].toString());
                //console.log(nova_data1);
                //console.log(nova_data2);
                if (nova_data2 < nova_data1) {
                    alert('Data inicial não pode ser maior que data final');
                    return false;
                } else {
                    pesquisaSintetica('<%=numCliente%>', '<%=nomeBD%>');
                    return true;
                }
            }
            function validaDataAna() {
                var data1 = $("#dataIni").val();
                var data2 = $("#dataFim").val();
                //console.log(data1);
                //console.log(data2);
                var nova_data1 = parseInt(data1.split("/")[2].toString() + data1.split("/")[1].toString() + data1.split("/")[0].toString());
                var nova_data2 = parseInt(data2.split("/")[2].toString() + data2.split("/")[1].toString() + data2.split("/")[0].toString());
                //console.log(nova_data1);
                //console.log(nova_data2);
                if (nova_data2 < nova_data1) {
                    alert('Data inicial não pode ser maior que data final');
                    return false;
                } else {
                    pesquisaAnalitica('<%=numCliente%>', '<%=nomeBD%>');
                    return true;
                }
            }

        </script>

    </head>
    <body>
        <div id="divInteracao" class="esconder" style="top:10%; left:10%; right:10%; bottom:10%;" align="center"><input id="textointeracao" /></div>
        <div id="divProtecao" class="esconder"></div>

        <%@ include file="../../Includes/telaMsg.jsp" %>
        <%@ include file="../../Includes/menu_cliente.jsp" %>

        <div id="divPrincipal" align="center">
            <div id="container">
                <div id="conteudo">


                    <ul class="ul_formulario" style="width: 636px; height: 220px; margin-right: 0; border-right: 1px solid silver;">
                        <li class="titulo"><dd><span>MONTE A SUA PESQUISA</span></dd></li>
                        <li>
                            <dd>
                                <label>Periodo de Data</label>
                                <input type="text" style="width:60px;" name="dataIni" id="dataIni" value="<%= dataOntem%>" maxlength="10" onkeypress="mascara(this, maskData);" />
                                até
                                <input type="text" style="width:60px;" name="dataFim" id="dataFim" value="<%=vDataAtual%>" maxlength="10" onkeypress="mascara(this, maskData);" />
                            </dd>
                            <dd>
                                <label>Departamento</label>
                                <select style="width: 145px;" name="departamento" id="departamento">
                                    <option value="0">-- TODOS --</option>
                                    <%
                                        ArrayList<ClientesDeptos> listaDep = ContrClienteDeptos.consultaDeptos(idCliente, nomeBD);
                                        if (listaDep != null && listaDep.size() > 0) {
                                            ArrayList<Integer> dpsUser = (ArrayList<Integer>) session.getAttribute("departamentos");
                                            for (int i = 0; i < listaDep.size(); i++) {
                                                ClientesDeptos cd = listaDep.get(i);
                                                if (dpsUser.contains(cd.getIdDepartamento()) || nivel == 1) {
                                                    String depto = FormataString.removeAccentsToUpper(cd.getNomeDepartamento());
                                                    if (depto.length() > 20) {
                                                        depto = depto.substring(0, 20);
                                                    }
                                    %>
                                    <option value="<%= cd.getIdDepartamento() + ";" + depto%>"><%= cd.getNomeDepartamento()%></option>
                                    <%}
                                            }
                                        }%>
                                </select>
                            </dd>
                            <dd>
                                <label>Serviço</label>
                                <select style="width: 130px;" id="servico" name="servico">
                                    <option value="0">-- TODOS --</option>
                                    <%
                                        ArrayList<ServicoECT> listaTipoPostagem = ContrServicoECT.consultaServicosTelaPesquisa();
                                        for (int i = 0; i < listaTipoPostagem.size(); i++) {                                            
                                            ServicoECT sv = listaTipoPostagem.get(i);
                                            
                                    %>
                                    <option value="<%= sv.getGrupoServico()%>"><%= sv.getNomeSimples()%></option>
                                    <%}%>
                                    <option value="OUTROS">OUTROS</option>                                    
                                    <%--
                                    ArrayList<String> listaTipoPostagem = Emporium.Controle.ContrRelatorios.pesquisaTodosTipoPostagemDoCliente(nomeBD, numCliente);
                                    for (int i = 0; i < listaTipoPostagem.size(); i++) {
                                    %>
                                    <option value="<%= listaTipoPostagem.get(i)%>"><%= listaTipoPostagem.get(i)%></option>
                                    <%}--%>
                                </select>
                            </dd>
                            <dd>
                                <label>Situação</label>
                                <select style="width: 110px;" id="situacao" name="situacao">
                                    <option selected value="">-- TODAS --</option>
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

                                    <%--<option value=" AND codStatus <> 1 AND LOCATE('entregue',status) = 0 AND LOCATE('retirada',status) = 0 ">NÃO ENTREGUE</option>
                                    <option value=" AND (codStatus = 1 OR (codStatus = 99 AND (LOCATE('entregue',status) > 0 OR LOCATE('retirada',status) > 0))) ">ENTREGUE</option>
                                    <option value=" AND codStatus = 0 ">POSTADO</option>
                                    <option value=" AND codStatus NOT IN (0, 4, 5, 6, 7, 8, 10, 18, 19, 20, 21, 22, 23, 25, 26, 27, 34, 42, 9, 12, 28, 31, 43, 44, 50, 51, 52, 69, 1) AND LOCATE('entregue',status) = 0 AND LOCATE('retirada',status) = 0 ">ENCAMINHADO</option>
                                    <option value=" AND codStatus IN (4, 5, 6, 7, 8, 10, 18, 19, 20, 21, 22, 23, 25, 26, 27, 34, 42) ">DEVOLVIDO</option>
                                    <option value=" AND codStatus IN (9, 12, 28, 31, 43, 44, 50, 51, 52, 69) ">EXTRAVIADO</option>--%>

                                </select>
                            </dd>
                        </li>
                        <li>
                            <dd>
                                <label>Destinatário</label>
                                <input style="width: 200px;" type="text" name="destinatario" id="destinatario" maxlength="40" value="" />
                            </dd>
                            <dd>
                                <label>Objeto SRO</label>
                                <input style="width: 135px;" type="text" name="objeto" id="objeto" value="" maxlength="13" onkeypress="mascara(this, maskObjetoSRO)" />
                            </dd>
                            <dd>
                                <label>Nota Fiscal</label>
                                <input style="width: 125px;" type="text" name="notaFiscal" id="notaFiscal" maxlength="20" value="" />
                            </dd>
                            <dd>
                                <label>CEP</label>
                                <input style="width: 100px;" type="text" name="cep" id="cep" value="" maxlength="9" onkeypress="mascara(this, maskCep)" />
                            </dd>
                        </li>
                        <li>
                            <dd>
                                <label>UF</label>
                                <select name="uf" id="uf">
                                    <option value="">-- TODOS --</option>
                                    <option value="cep >= 69900000 AND cep <= 69999999">AC</option>
                                    <option value="cep >= 57000000 AND cep <= 57999999">AL</option>
                                    <option value="(cep >= 69000000 AND cep <= 69299999 OR cep >= 69400000 AND cep <= 69899999)">AM</option>
                                    <option value="cep >= 68900000 AND cep <= 68999999">AP</option>
                                    <option value="cep >= 40000000 AND cep <= 48999999">BA</option>
                                    <option value="cep >= 60000000 AND cep <= 63999999">CE</option>
                                    <option value="(cep >= 70000000 AND cep <= 72799999 OR cep >= 73000000 AND cep <= 73699999)">DF</option>
                                    <option value="cep >= 29000000 AND cep <= 29999999">ES</option>
                                    <option value="(cep >= 72800000 AND cep <= 72999999 OR cep >= 73700000 AND cep <= 76799999)">GO</option>
                                    <option value="cep >= 65000000 AND cep <= 65999999">MA</option>
                                    <option value="cep >= 30000000 AND cep <= 39999999">MG</option>
                                    <option value="cep >= 79000000 AND cep <= 79999999">MS</option>
                                    <option value="cep >= 78000000 AND cep <= 78899999">MT</option>
                                    <option value="cep >= 66000000 AND cep <= 68899999">PA</option>
                                    <option value="cep >= 58000000 AND cep <= 58999999">PB</option>
                                    <option value="cep >= 50000000 AND cep <= 56999999">PE</option>
                                    <option value="cep >= 64000000 AND cep <= 64999999">PI</option>
                                    <option value="cep >= 80000000 AND cep <= 87999999">PR</option>
                                    <option value="cep >= 20000000 AND cep <= 28999999">RJ</option>
                                    <option value="cep >= 59000000 AND cep <= 59999999">RN</option>
                                    <option value="cep >= 90000000 AND cep <= 99999999">RS</option>
                                    <option value="cep >= 76800000 AND cep <= 76999999">RO</option>
                                    <option value="cep >= 69300000 AND cep <= 69399999">RR</option>
                                    <option value="cep >= 88000000 AND cep <= 89999999">SC</option>
                                    <option value="cep >= 49000000 AND cep <= 49999999">SE</option>
                                    <option value="cep >= 1000000 AND cep <= 19999999">SP</option>
                                    <option value="cep >= 77000000 AND cep <= 77999999">TO</option>
                                </select>
                            </dd>
                            <dd>
                                <label>L. de Postagem</label>
                                <input style="width: 100px;" type="text" name="lp" id="lp" value="" maxlength="13" onkeypress="mascara(this, maskNumero)" />
                            </dd>
                            <dd>
                                <label>Faturamento</label>
                                <select style="width: 120px;" id="tipoFat" name="tipoFat">
                                    <option selected value="">-- TODOS --</option>
                                    <option value=" AND contratoEct <> '' AND contratoEct <> '0'">CONTRATO ECT</option>
                                    <option value=" AND (contratoEct = '' OR contratoEct = '0')">FATURADO AGF</option> 
                                </select>
                            </dd>
                            <%if (acessosUs.contains(8)) {%>
                            <dd>
                                <br/>
                                <input style="position : relative ; top:3px;" name="atrasado" id="atrasado" value="1" type="checkbox" /><b> SOMENTE COM ATRASO

                            </dd>
                            <%}else{%>
                            <input style="display: none;" name="atrasado" id="atrasado" value="0" type="checkbox" />

                            <%}%>
                        </li>
                        <li>
                            <dd style="width: 500px;">
                                <div class="buttons">
                                    <button type="button" class="regular" onclick="validaDataSint(); "><img src="../../imagensNew/lupa.png"/> PESQUISA SINTÉTICA</button>
                                    <button type="button" class="positive" onclick="validaDataAna();"><img src="../../imagensNew/lupa_mais.png"/> PESQUISA ANALÍTICA</button>
                                    <%--<button type="button" class="negative" onclick="teste();"><img src="../../imagensNew/broom.png"/> LIMPAR CAMPOS</button>--%>
                                </div>
                            </dd>
                            <dd>
                                <input style="position : relative ; top:3px;" name="ar" id="ar" value="1" type="checkbox" /><b> COM A.R.</b><br/><br/>
                                <input style="position : relative ; top:3px;" name="vd" id="vd" value="1" type="checkbox" /><b> COM V.D.</b>
                            </dd>
                        </li>
                    </ul>
                    <ul class="ul_formulario" style="width: 320px; height: 220px;">
                        <li>
                            <dd>
                                <div id="divGrafico1" style=" width: 300px; height: 180px; margin: 0 auto;"></div>
                            </dd>
                        </li>
                    </ul>
                    <ul class="ul_formulario" style="width: 140px; height: 220px; border-left: 1px solid silver;">
                        <li class="titulo"><dd><span>LEGENDAS</span></dd></li>
                        <li>
                            <dd>
                                <img class="link_img" src="../../imagensNew/mail.png" /><b> POSTADO</b><br/><br/>
                                <img class="link_img" src="../../imagensNew/mail_send.png" /><b> ENCAMINHADO</b><br/><br/>
                                <img class="link_img" src="../../imagensNew/mail_open.png" /><b> ENTREGUE</b><br/><br/>
                                <img class="link_img" src="../../imagensNew/mail_back.png" /><b> DEVOLVIDO</b><br/><br/>
                                <img class="link_img" src="../../imagensNew/mail_alert.png" /><b> EXTRAVIADO</b><br/><br/>
                            </dd>
                        </li>
                    </ul>
                    <img width="100%" src="../../imagensNew/linha.jpg"/>
                    <div style="width: 100%;clear: both;">
                        <div id="titulo2" >Resultado da Pesquisa</div>
                        <div id="tableObjeto"></div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
<%}%>