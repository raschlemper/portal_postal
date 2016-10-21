<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@page import="java.util.ArrayList, java.util.Date, java.text.SimpleDateFormat" %>
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <%
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                    String nomeBD = (String) session.getAttribute("nomeBD");
                    if (nomeBD == null) {
                        response.sendRedirect("../Portal/portal.jsp?msg=Sua sessao expirou! Para voltar ao Portal faça seu login novamente!");
                    } else {

                        //String nomeEmpresa = Controle.contrEmpresa.nomeEmpresaByNomeBD(nomeBD);
                        String numCliente = String.valueOf(session.getAttribute("idCliente"));
                        String dataAtual = sdf.format(new Date());
                        String dataOntem = Util.SomaData.SomarDiasDatas(new Date(), -1);
                        String dataInicioCalendario = Util.SomaData.SomarDiasDatas(new Date(), -180); // diminui 6 meses
        %>
        <meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
        <title>Portal Postal | Relatórios</title>
        <meta name="keywords" content="" />
        <meta name="description" content="" />

        <link href="../../css/estilo.css" rel="stylesheet" type="text/css" />
        <script type="text/javascript" src="../js/ajax_portal.js"></script>
        <script type="text/javascript" src="../../javascript/mascara.js"></script>

        <link type="text/css" href="../../javascript/jquery/css/jquery-ui-1.8.16.custom.css" rel="stylesheet" />
        <script type="text/javascript" src="../../javascript/jquery/js/jquery-1.6.2.min.js"></script>
        <script type="text/javascript" src="../../javascript/jquery/js/jquery-ui-1.8.16.custom.min.js"></script>
        <script type="text/javascript" src="../../javascript/jquery/js/jquery.ui.datepicker-pt-BR.js"></script>

        <link rel="stylesheet" href="../../javascript/plugins/dropdown/css/style.css" type="text/css" media="screen, projection"/>
        <!--[if lte IE 7]><link rel="stylesheet" type="text/css" href="../../javascript/plugins/dropdown/css/ie.css" media="screen" /><![endif]-->
        <script type="text/javascript" language="javascript" src="../../javascript/plugins/dropdown/js/jquery.dropdownPlain.js"></script>

        <script type="text/javascript" charset="utf-8">

            $(function() {
                $("#dataIni").datepicker({
                    minDate:'<%= dataInicioCalendario%>',
                    maxDate:'<%= dataAtual%>',
                    showOn: "button",
                    buttonImage: "../../imagensNew/calendario.png",
                    buttonImageOnly: true,
                    showAnim: "slideDown"
                });
                $("#dataFim").datepicker({
                    minDate:'<%= dataInicioCalendario%>',
                    maxDate:'<%= dataAtual%>',
                    showOn: "button",
                    buttonImage: "../../imagensNew/calendario.png",
                    buttonImageOnly: true,
                    showAnim: "slideDown"
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


                    <ul class="ul_formulario">
                        <li class="titulo"><dd>Monte Seu Relatório!</dd></li>
                        <li>
                            <dd>
                                <label>PERIODO DA PESQUISA</label>
                                <input type="text" style="width:75px;" name="dataIni" id="dataIni" value="<%= dataOntem%>" maxlength="10" onkeypress="mascara(this,maskData);" />
                                até
                                <input type="text" style="width:75px;" name="dataFim" id="dataFim" value="<%= dataAtual%>" maxlength="10" onkeypress="mascara(this,maskData);" />
                            </dd>
                            <dd>
                                <label>Agrupar Por</label>
                                <select name="agrupamento" id="agrupamento" style="width:170px;">
                                    <option value="0">Dia</option>
                                    <option value="MONTH(dataPostagem)">Mês</option>
                                    <option value="descServico">Serviço</option>
                                    <option value="departamento">Departamento</option>
                                    <option value="status">Situação</option>
                                    <option value="tipo">Estado-UF</option>
                                    <option value="deptoServ">Serviço por Departamento</option>
                                </select>
                            </dd>
                            <dd>
                                <label>Ordenar Por</label>
                                <select id="ordenacao" name="ordenacao" style="width:170px;">
                                    <option value="0">Agrupamento</option>
                                    <option value="qtd">Quantidade</option>
                                    <option value="valor">Valor</option>
                                </select>
                            </dd>
                        </li>
                        <li>
                            <dd>
                                <div class="buttons">
                                    <button type="button" class="regular" onclick="pesquisaObjetosRelatorios('<%=numCliente%>', '<%=nomeBD%>');"><img src="../../imagensNew/lupa.png"/> GERAR RELATÓRIO</button>
                                </div>
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