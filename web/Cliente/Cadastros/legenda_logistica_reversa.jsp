<%@page import="java.util.List"%>
<%@page import="Entidade.LegendaLogisticaReversa"%>
<%@page import="Emporium.Controle.ContrLogisticaReversa"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
 String nomeBD = (String) session.getAttribute("nomeBD");
 ContrLogisticaReversa dao = new ContrLogisticaReversa();
 List<LegendaLogisticaReversa> legendas = dao.pesquisaLegenda(nomeBD);
 %>

<html>
     <head>
        <meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
        <title>Portal Postal | Stars</title>
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
        <script type="text/javascript" src="../../javascript/jx.js"></script>
        <style type="text/css">
            .imgStars{ width: 32px;height: 32px; vertical-align: middle;}
            .blockStars{margin-bottom: 20px;}
            .inputStars{width:300px;}
             input[type="text"]{
                  height: 22px !important;
            }
        </style>
 </head>
   <body>

        <div id="divInteracao" style="margin:0 auto; width: 90%;height: 650px" class="esconder" align="center"></div>

        <div id="divProtecao" class="esconder"></div>

        <%@ include file="../../Includes/telaMsg.jsp" %>
        <%@ include file="../../Includes/menu_cliente.jsp" %>

        <div id="divPrincipal" align="center">
            <div id="container">
                <div id="conteudo">
                         <ul class="ul_formulario">
                            <li class="titulo">
                             <dd>Legenda Logistica Reversa</dd>
                            </li>
                         </ul>
                    <p class="blockStars">
                        1.<img  class="imgStars" src="../../imagensNew/starwhite.png">
                        <input id="starWhite" class="inputStars" value="<%=legendas.get(0).getNome()%>"  type="text" />
                    </p>
                    <p class="blockStars">
                        2.<img  class="imgStars" src="../../imagensNew/starblue.png">
                        <input id="starBlue" class="inputStars" value="<%=legendas.get(1).getNome()%>"  type="text" />
                    </p>
                    <p class="blockStars">
                        3.<img class="imgStars" src="../../imagensNew/stargrey.png">
                         <input id="starGrey" class="inputStars" value="<%=legendas.get(2).getNome()%>" type="text" />
                    </p>
                    <p class="blockStars">
                        4.<img class="imgStars" src="../../imagensNew/starred.png">
                         <input id="starRed" class="inputStars" value="<%=legendas.get(3).getNome()%>" type="text" />
                    </p>
                    <p class="blockStars">
                        5.<img class="imgStars" src="../../imagensNew/staryellow.png">
                         <input id="starYellow" class="inputStars" value="<%=legendas.get(4).getNome()%>" type="text" />
                    </p>
                    
                    <div class="buttons">
                        <button type="button" class="positive" onclick="cadastroStars();"><img src="../../imagensNew/tick_circle.png">CADASTRAR</button>
                    </div>
                    <br/>
                    <img width="100%" src="../../imagensNew/linha.jpg">
                </div>
            </div>
        </div>
</html>

<script type="text/javascript">
    
    function cadastroStars(){
        JxPost('',JxResult, '../../ServLegendaLogisticaReversa',getParameter(),false);
    }
    
    function getParameter(){
        var starWhite = document.getElementById('starWhite').value;
        var starBlue = document.getElementById('starBlue').value;
        var startGrey = document.getElementById('starGrey').value;
        var startRed = document.getElementById('starRed').value;
        var starYellow = document.getElementById('starYellow').value;
        return "starWhite="+starWhite+
                "&starBlue="+starBlue+
                "&starGrey="+startGrey+
                "&starRed="+startRed+
                "&starYellow="+starYellow;
        
    }
</script>
