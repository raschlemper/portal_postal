<%@page import="Util.FormataString"%>
<%@page import="Entidade.ClientesDeptos"%>
<%@page import="Controle.ContrClienteDeptos"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.ArrayList"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <%
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            String nomeBD = (String) session.getAttribute("nomeBD");
            if (nomeBD == null) {
                response.sendRedirect("../../index.jsp?msg=Sua sessao expirou! Para voltar ao Portal faça seu login novamente!");
            } else {

                String numCliente = String.valueOf(session.getAttribute("idCliente"));
                int idCliente = Integer.parseInt(numCliente);

                System.out.println(request.getParameter("dataIni"));
                System.out.println(request.getParameter("dataFim"));
                System.out.println(request.getParameter("comAr"));
                System.out.println(request.getParameter("depto"));
                
                Date dataAtual = new Date();
                String vDataAtual = sdf.format(dataAtual);
                String dataFim = sdf.format(dataAtual); 
                String dataOntem = Util.SomaData.SomarDiasDatas(dataAtual, -1);
                String dataInicioCalendario = Util.SomaData.SomarDiasDatas(dataAtual, -150); // diminui 5 meses                       
                if (request.getParameter("dataFim") != null) {
                    dataFim = request.getParameter("dataFim");
                }
                if (request.getParameter("dataIni") != null) {
                    dataOntem = request.getParameter("dataIni");
                }
                String iddepto = ""; 
                if(request.getParameter("depto") != null){
                    iddepto = request.getParameter("depto");
                }
                int comAr = 2; 
                if(request.getParameter("comAr") != null){
                    comAr = Integer.parseInt(request.getParameter("comAr"));
                }
%>
        <meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
        <title>Portal Postal | Controle de AR</title>
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

            $(document).ready(function () {
                pesquisaAr('<%=numCliente%>', '<%=nomeBD%>');
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

            function chamaJanelaBaixaAr(numeroRegistro) {
                var baixa = document.getElementById(numeroRegistro).value;
                if (baixa == 1) {
                    document.getElementById("numObjetoAr").value = numeroRegistro;
                    document.getElementById("numAr").innerHTML = numeroRegistro;
                    document.getElementById("nomeRecebedor").value = "";
                    document.getElementById("dataRecebimento").value = "<%= sdf.format(new Date())%>";
                    chamaDivProtecao();
                } else {
                    darBaixaAr(numeroRegistro);
                }
            }


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
                sortcolumn: 2,
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
                    text: 'RETORNOS DE A.R.'
                },
                tooltip: {
                    formatter: function () {
                        return '<b>' + this.point.name + '</b>: ' + this.y + '';
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

            function chamaDivProtecao() {
                var classe = document.getElementById("divProtecao").className;
                if (classe == "esconder") {
                    document.getElementById("divProtecao").className = "mostrar";
                    document.getElementById("divInteracao").className = "mostrar";
                } else {
                    document.getElementById("divProtecao").className = "esconder";
                    document.getElementById("divInteracao").className = "esconder";
                }
            }

            $(function () {
                $("#dataRecebimento").datepicker({
                    showOn: "button",
                    buttonImage: "../../imagensNew/calendario.png",
                    buttonImageOnly: true,
                    showAnim: "slideDown"
                });
            });

            function darBaixaAr2() {
                var formPesquisa = document.formPesquisa;
                var form = document.formSro;
                
                form.dataI.value = formPesquisa.dataIni.value;
                form.dataF.value = formPesquisa.dataFim.value;
                form.idDepto.value = formPesquisa.departamento.value;
                form.comAr.value = formPesquisa.ar.value;
                
                if (form.dataRecebimento.value === "") {
                    alert("Preencha a Data de recebimento do AR!");
                    return false;
                } else if (form.nomeRecebedor.value === "") {
                    alert("Preencha o nome do recebedor do AR");
                    return false;
                } else if (form.arquivoRec.value !== ""){
                    var indexA = form.arquivoRec.value.lastIndexOf(".");
                    var indexB = form.arquivoRec.value.length;
                    var ext = form.arquivoRec.value.substring(indexA, indexB).toUpperCase();
                    if (ext !== ".JPG" && ext !== ".JPEG" && ext !== ".PNG" && ext !== ".GIF") {
                        alert("O arquivo a ser importado deve ser .JPG, .JPEG, .PNG ou .GIF !");
                        return false;
                    }
                }
                                
                //waitMsg();
                form.submit();
            }

        </script>
    </head>
    <body>
        
        
        <div id="divInteracao" class="esconder" style="top:15%; left:20%; right:20%; bottom:25%;" align="center">
            <div style="width: 100%; margin: 15px 0;">
                <div style="width: 95%; text-align: left;">
                    <div style='float:right;'><a onclick='chamaDivProtecao();' href='#' class='botaoClose'>Fechar</a></div>
                    <div id='titulo1'><span id="numAr"></span> - Insira os Dados do AR</div>
                </div>
                <img style="margin-bottom: 25px;" width="100%" src="../../imagensNew/linha.jpg"/>
                <form  name="formSro" action="../../ServBaixaAr" method="post" enctype="multipart/form-data">
                    <!--   <form name='formObs' action='../../ServAlterarObsColeta' method='post'> -->
                    <ul style="width: 95%; text-align: left;" class="ul_formulario">
                        <li>
                            <dd>
                                <label>Data Recebimento</label>
                                <input type="text" name="dataRec" id="dataRecebimento"  value="" maxlength="10" onKeyPress="mascara(this, maskData)" />
                            </dd>
                        </li>
                        <li>
                            <dd>
                                <label>Nome do Recebedor</label>
                                <input style="width: 220px;" type="text" name="nomeRec" id="nomeRecebedor" />
                            </dd>
                        </li>
                        <li>
                            <label>Escolha a imagem do AR para importar</label><br/>
                            <span class="btn btn-default btn-file"><i class="fa fa-folder-open"></i> Selecionar imagem <input type="file" name="arquivoRec" /></span>
                            <input type="hidden" id="tipoForm" name="tipoFormRec" value="imagem" /></br>
                            <label class="small" style="color: red;">*O arquivo a ser importado deve ser JPG, JPEG, PNG ou GIF !</label><br/>
                            <label class="small" style="color: red;">*O tamanho máximo deve ser de 500 BK !</label>
                        </li>
                    </ul>
                    <div class="buttons">
                        <input type="hidden" name="dataI" id="dataI" value=""/>
                        <input type="hidden" name="dataF" id="dataF" value=""/>
                        <input type="hidden" name="idDepto" id="idDepto" value=""/>
                        <input type="hidden" name="comAr" id="comAr" value=""/>
                        
                        <input type="hidden" name="sroRec" id="numObjetoAr" value=""/>
                        <input type="hidden" name="codCli" id="codCli" value="<%=idCliente%>"/>
                        <button type="button" class="positive" onclick="darBaixaAr2();" ><img src="../../imagensNew/tick_circle.png" /> SALVAR DADOS</button>
                        <button type="button" class="negative" onClick='chamaDivProtecao();'><img src="../../imagensNew/cross_circle.png" /> CANCELAR</button>
                    </div>
                </form>
            </div>
        </div>
        <div id="divProtecao" class="esconder"></div>
        
        
        
        

        <%@ include file="../../Includes/telaMsg.jsp" %>
        <%@ include file="../../Includes/menu_cliente.jsp" %>

        <div id="divPrincipal" align="center">
            <div id="container">
                <div id="conteudo">

                    <ul class="ul_formulario" style="width: 620px; height: 180px; margin-right: 0; border-right: 1px solid silver;">
                        <form action="baixaAr2.jsp" name="formPesquisa" method="post">
                            <li class="titulo">
                                <dd>Pesquisar Objetos Com A. R.</dd>
                            </li>
                            <li>
                                <dd>
                                    <label>PERIODO DA PESQUISA</label>
                                    <input type="text" style="width:65px;" name="dataIni" id="dataIni" value="<%= dataOntem%>" onkeypress="mascara(this, maskData);" />
                                    até
                                    <input type="text" style="width:65px;" name="dataFim" id="dataFim" value="<%= dataFim %>" onkeypress="mascara(this, maskData);" />
                                </dd>
                                <dd>
                                    <label>Somente Objetos com A.R.</label>
                                    <select id="ar" name="ar" style="width:200px;">
                                        <option <%if(comAr==2){%>selected<%}%> value="2">Todos</option>
                                        <option <%if(comAr==0){%>selected<%}%> value="0">Que Não Retornou</option>
                                        <option <%if(comAr==1){%>selected<%}%> value="1">Que Já Retornou</option>
                                    </select>
                                </dd>
                            </li>
                            <li>
                                <dd>
                                    <label>Escolha o Departamento</label>
                                    <select name="departamento" id="departamento" style="width:216px;">
                                        <option value="0">Todos os Departamentos</option>
                                        <%
                                            ArrayList<ClientesDeptos> listaDep = ContrClienteDeptos.consultaDeptos(idCliente, nomeBD);
                                            ArrayList<Integer> dpsUser = (ArrayList<Integer>) session.getAttribute("departamentos");
                                            for (int i = 0; i < listaDep.size(); i++) {
                                                ClientesDeptos cd = listaDep.get(i);
                                                String depto = FormataString.removeAccentsToUpper(cd.getNomeDepartamento());
                                                if (dpsUser.contains(cd.getIdDepartamento())) {
                                        %>
                                        <option value="<%=depto%>" <%if(iddepto.equals("depto")){%>selected<%}%>><%= cd.getNomeDepartamento()%></option>
                                        <%}
                                            }%>
                                    </select>
                                </dd>
                            </li>
                            <li>
                                <dd style="color:red;">
                                    *Obs.: Clique nas figuras da primeira coluna para marcar os AR que já retornaram ou não.<br/>
                                    <div class="buttons">
                                        <button type="button" class="regular" onclick="pesquisaAr('<%=numCliente%>', '<%=nomeBD%>');"><img src="../../imagensNew/lupa.png"/> CONSULTAR AR's</button>
                                    </div>
                                </dd>
                            </li>
                        </form>
                    </ul>
                    <ul class="ul_formulario" style="width: 338px; height: 180px;">
                        <li>
                            <dd>
                                <div id="divGrafico1" style=" width: 315px; height: 180px; margin: 0 auto;"></div>
                            </dd>
                        </li>
                    </ul>
                    <ul class="ul_formulario" style="width: 140px; height: 180px; border-left: 1px solid silver;">
                        <li class="titulo"><dd><span>LEGENDAS</span></dd></li>
                        <li>
                            <dd>
                                <img class="link_img" src="../../imagensNew/cross_circle.png" /><b> NÃO RETORNOU</b><br/><br/>
                                <img class="link_img" src="../../imagensNew/tick_circle.png" /><b> JÁ RETORNOU</b><br/><br/>
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