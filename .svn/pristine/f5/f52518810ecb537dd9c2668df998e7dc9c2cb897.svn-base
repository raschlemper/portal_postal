<%@page import="Entidade.ClientesDeptos"%>
<%@page import="Controle.ContrClienteDeptos"%>
<%@page import="Emporium.Controle.ContrPreVenda"%>
<%@page import="Entidade.PreVenda"%>
<%@page import="Entidade.Endereco"%>
<%@page import="Entidade.TelegramaPostal"%>
<%@page import="Emporium.Controle.ContrTelegramaPostal"%>
<%@page import="Controle.contrCliente"%>
<%@page import="Entidade.Contato"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@page import="java.util.ArrayList, java.util.Date, java.text.SimpleDateFormat, java.sql.*, java.util.Calendar, java.util.Locale" %>

<%
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    String nomeBD = (String) session.getAttribute("nomeBD");
    if (nomeBD == null) {
        response.sendRedirect("../../index.jsp?msg=Sua sessao expirou! Para voltar ao Portal faça seu login novamente!");
    } else {
        String numCliente = String.valueOf(session.getAttribute("idCliente"));
        int idCliente = Integer.parseInt(numCliente);
        String vDataAtual = sdf.format(new Date());
        if (request.getParameter("data") != null) {
            vDataAtual = request.getParameter("data");
        }
        String vData2 = sdf.format(new Date());
        if (request.getParameter("data2") != null) {
            vData2 = request.getParameter("data2");
        }
        String tipo = "0";
        if (request.getParameter("tipo") != null) {
            tipo = request.getParameter("tipo");
        }
        ArrayList<Integer> dps = (ArrayList<Integer>) session.getAttribute("departamentos");

        String dataBD = Util.FormatarData.DateToBD(vDataAtual);
        String dataBD2 = Util.FormatarData.DateToBD(vData2);
%>

<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
        <title>Portal Postal | Solicitar Coleta</title>
        <meta name="keywords" content="" />
        <meta name="description" content="" />

        <link href="../../css/estilo.css" rel="stylesheet" type="text/css" />
        <script type="text/javascript" src="../js/ajax_portal.js"></script>
        <script type="text/javascript" src="../../javascript/mascara.js"></script>
        <script type="text/javascript" src="../../javascript/ajax.js"></script>
        <script type="text/javascript" src="../../javascript/jsContato.js"></script>

        <link type="text/css" href="../../javascript/jquery/css/jquery-ui-1.8.16.custom.css" rel="stylesheet" />
        <script type="text/javascript" src="http://code.jquery.com/jquery-1.10.2.js"></script>
        <script type="text/javascript" src="http://code.jquery.com/ui/1.11.4/jquery-ui.js"></script>
        <script type="text/javascript" src="../../javascript/jquery/js/jquery.ui.datepicker-pt-BR.js"></script>
        <link href="http://code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css" rel="stylesheet" />

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
            $(function () {
                $("#data").datepicker({
                    maxDate: '<%= vDataAtual%>',
                    showOn: "button",
                    buttonImage: "../../imagensNew/calendario.png",
                    buttonImageOnly: true,
                    showAnim: "slideDown"
                });
                $("#data2").datepicker({
                    maxDate: '<%= vDataAtual%>',
                    showOn: "button",
                    buttonImage: "../../imagensNew/calendario.png",
                    buttonImageOnly: true,
                    showAnim: "slideDown"
                });
            });
        </script>
    </head>
    <body>
        <div id="divInteracao" class="esconder"><input id="textointeracao" /></div>
        <div id="divProtecao" class="esconder"></div>

        <%@ include file="../../Includes/telaMsg.jsp" %>
        <%@ include file="../../Includes/menu_cliente.jsp" %>

        <div id="divPrincipal" align="center">
            <div id="container">
                <div id="conteudo">
                    <div id="titulo1">Gerar Arquivo de Ocorrências das Postagens</div>
                    <form action="../AjaxPages/txt_edi_ocorrencia.jsp" method="post" target="_blank">
                        <ul class="ul_formulario">                            
                            <li class="titulo">
                                <dd><span>Pesquisar arquivos</span></dd>
                            </li>
                            <li>
                                <dd>
                                    <label>Periodo de Data:</label>
                                    de
                                    <input type="text" name="data" id="data" style="width:60px;" value="<%= vDataAtual%>" maxlength="10" onKeyPress="mascara(this, maskData)" />
                                    até
                                    <input type="text" name="data2" id="data2" style="width:60px;" value="<%= vData2%>" maxlength="10" onKeyPress="mascara(this, maskData)" />
                                </dd>                                
                                <dd>
                                    <label>Departamento / Centro de Custo</label>
                                    <select name="departamento" style="width: 230px;">
                                        <%
                                            ArrayList<ClientesDeptos> listaDep = ContrClienteDeptos.consultaDeptos(idCliente, nomeBD);
                                            if (listaDep != null && listaDep.size() > 0) {
                                                if (dps.size() != 1) {
                                                    if (listaDep.size() > 1 || dps.size() == 0) {
                                        %>
                                        <option value="0">TODOS OS DEPARTAMENTOS</option>                                    
                                        <%                                                }
                                            }
                                            for (int i = 0; i < listaDep.size(); i++) {
                                                ClientesDeptos cd = listaDep.get(i);
                                                if (dps.contains(cd.getIdDepartamento())) {
                                        %>
                                        <option value="<%=cd.getIdDepartamento()%>"><%= cd.getNomeDepartamento()%></option>
                                        <%}}} else {%>
                                        <option value="0">NENHUM DEPARTAMENTO</option>
                                        <%}%>
                                    </select>
                                </dd>     
                                <dd>
                                    <label>Modelo do Arquivo</label>
                                    <select name="modelo">
                                        <option value='EDI_3.0'>[OCORREN] EDI 3.0</option>
                                        <option value='EDI_3.1'>[OCORREN] EDI 3.1</option>
                                        <option value='EDI_5.0'>[OCORREN] EDI 5.0</option>
                                        <option value='EDI_EMBARC_3.0'>[CONEMB] EDI EMBARQUE 3.0</option>
                                    </select>
                                </dd>
                                <dd>
                                    <label>Mostrar:</label>
                                    <select name="tipoPesquisa">
                                        <option value='1'>Todos os objetos postados no período selecionado.</option>
                                        <option value='2'>Apenas status que mudaram desde o ultimo arquivo gerado.</option>
                                    </select>
                                </dd>
                                <dd style="width: 100%;">
                                    <div class="buttons">
                                        <input type="hidden" name="nomeBD" value="<%= nomeBD %>"/>
                                        <input type="hidden" name="idCliente" value="<%= idCliente%>"/>
                                        <button type="submit" class="regular" ><img src="../../imagensNew/lupa.png" /> GERAR ARQUIVO</button>
                                    </div>
                                </dd>
                            </li>
                        </ul>
                    </form>
                    <img width="100%" src="../../imagensNew/linha.jpg"/>
                    <div style="width: 100%;clear: both;"></div>
                </div>
            </div>
        </div>

    </body>
</html>
<%}%>