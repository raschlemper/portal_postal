<%@page import="caixapostal.componentes.SituacaoObjetoInterno"%>
<%@page import="Util.FormatarData"%>
<%@page import="caixapostal.entity.ObjetoInterno"%>
<%@page import="java.util.List"%>
<%@page import="caixapostal.dao.CaixaPostalDao"%>
<%@page import="caixapostal.filter.FilterObjetos"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    String nomeBD = (String) session.getAttribute("nomeBD");
    String dataIni = (String) request.getParameter("dataIni");
    String dataFim = (String) request.getParameter("dataFim");
    String nome = (String) request.getParameter("nome");
    String objeto = (String) request.getParameter("objeto");
    String situacao = (String) request.getParameter("situacao");
    Integer idCliente = Integer.parseInt(String.valueOf(session.getAttribute("idCliente")));


    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    Date dataAtual = new Date();
    String dataInicioCalendario = Util.SomaData.SomarDiasDatas(dataAtual, -180);
    dataFim = dataFim != null ? dataFim : sdf.format(dataAtual);
    dataIni = dataIni != null ? dataIni : Util.SomaData.SomarDiasDatas(dataAtual, -5);

    nome = nome != null ? nome : "";
    objeto = objeto != null ? objeto : "";
    situacao = situacao != null ? situacao : "";

    FilterObjetos filter = new FilterObjetos();
    filter.setNomeDB(nomeBD);
    filter.setDataIni(dataIni);
    filter.setDataFim(dataFim);
    filter.setDestinatario(nome);
    filter.setNumeroObjeto(objeto);
    filter.setSituacao(situacao);
    filter.setIdCliente(idCliente);

    CaixaPostalDao caixaPostalDao = new CaixaPostalDao(filter.getNomeDB());
    List<ObjetoInterno> objetosInternos = caixaPostalDao.procuraObjetos(filter);


%>
<html>
    <head>
        <meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
        <title>Portal Postal | Objetos Caixa Postal</title>
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
        <script type="text/javascript" src="../../javascript/ajax.js"></script>
 </head>
 <style type="text/css">
     input[type="text"]{
        height: 22px !important;
     }
     
 </style>
    <body>

        <div id="divInteracao" style="margin:0 auto; width: 90%;height: 650px" class="esconder" align="center"></div>

        <div id="divProtecao" class="esconder"></div>

        <%@ include file="../../Includes/telaMsg.jsp" %>
        <%@ include file="../../Includes/menu_cliente.jsp" %>

        <div id="divPrincipal" align="center">
            <div id="container">
                <div id="conteudo">
                 <form name="form"  method="post"> 
                        <ul class="ul_formulario">
                            <li class="titulo">
                            <dd>PESQUISAR OBJETOS</dd>
                            </li>
                            <li>
                            <dd>
                                <label>Periodo de Data<b class="obg">*</b></label>
                                <input type="text" style="width:60px;" name="dataIni" id="dataIni" value="<%=dataIni%>" maxlength="10" onkeypress="mascara(this, maskData);" />
                                até
                                <input type="text" style="width:60px;" name="dataFim" id="dataFim" value="<%=dataFim%>" maxlength="10" onkeypress="mascara(this, maskData);" />
                            </dd>
                            <dd>
                                <label>Destinatário</label>
                                <input type="text" name="nome" size="50"  maxlength="70" value="<%=nome%>" />
                            </dd>
                            <dd>
                                <label>Objeto:</label>
                                <input  type="text" style="width:200px;" name="objeto" value="<%=objeto%>" size="8" value="" maxlength="25"  />
                            </dd>
                            <dd>
                                <label>Situação:</label>
                                <select id="filtroSituacao" name="situacao" >
                                    <option  value="TODOS">TODOS</option>
                                    <option <%=situacao.toUpperCase().equals("PENDENTE") ? "selected" : ""%>  value="PENDENTE">PENDENTE</option>
                                    <option <%=situacao.toUpperCase().equals("DEVOLVER") ? "selected" : ""%>  value="DEVOLVER">DEVOLVER</option>
                                    <option <%=situacao.toUpperCase().equals("REENVIAR") ? "selected" : ""%> value="REENVIAR">REENVIAR</option>
                                    <option <%=situacao.toUpperCase().equals("FINALIZADO") ? "selected" : ""%> value="FINALIZADO">FINALIZADO</option>
                                </select>
                            </dd>
                            <dd>
                                <div class="buttons">
                                     <button  type="button" class="regular" onclick="submit();"><img src="../../imagensNew/lupa.png">PESQUISA</button>
                                </div>
                            </dd>

                            </li>

                        </ul>
                    </form> 
                    <form id="formReenviarDevolver" name="formReenviarDevolver" method="post" action="../../ServCaixaPostal" >
                        <input id="idObjeto" name="idObjeto" type="hidden" />
                        <input id="situacao" name="situacao" type="hidden" />

                    </form>
                    <table cellpadding="0" cellspacing="0" border="0" id="table2" class="tinytable">
                        <thead>
                            <tr>
                                <th width="80"><h3>Data</h3></th>
                        <th width="100"><h3>OBJETO</h3></th>
                        <th width="100"><h3>NOVO OBJETO</h3></th>
                        <th><h3>NF/PEDIDO</h3></th>
                        <th><h3>DESTINATÁRIO</h3></th>
                        <th><h3>MOTIVO</h3></th>
                        <th width="80"><h3>SITUAÇÃO</h3></th>
                        <th width="48"><h3>REENVIAR</h3></th>
                        <th width="48"><h3>DEVOLVER</h3></th>
                        </tr>
                        </thead>
                        <tbody>
                            <%
                                for (ObjetoInterno objetoInterno : objetosInternos) {
                                    String situacaoHtml = "";
                                    if (objetoInterno.getSituacao().equals(SituacaoObjetoInterno.PENDENTE.toString())) {
                                        situacaoHtml = "<font color=\"red\">" + objetoInterno.getSituacao() + "</font>";
                                    } else if (objetoInterno.getSituacao().equals(SituacaoObjetoInterno.DEVOLVER.toString())
                                            || objetoInterno.getSituacao().equals(SituacaoObjetoInterno.ATUALIZAR_ENDERECO.getValue())
                                            || objetoInterno.getSituacao().equals(SituacaoObjetoInterno.REENVIAR.toString())) {
                                        situacaoHtml = "<font color=\"green\">" + objetoInterno.getSituacao() + "</font>";
                                    } else if (objetoInterno.getSituacao().equals(SituacaoObjetoInterno.FINALIZADO.toString())) {
                                        if(SituacaoObjetoInterno.REENVIAR.toString().equals(objetoInterno.getOrigem())){
                                            situacaoHtml = "<font color=\"blue\">REENVIO FINALIZADO</font>";
                                        }else if(SituacaoObjetoInterno.DEVOLVER.toString().equals(objetoInterno.getOrigem())){
                                            situacaoHtml = "<font color=\"blue\">DEVOLUÇÃO FINALIZADA</font>";
                                        }else{
                                            situacaoHtml = "<font color=\"blue\">FINALIZADA</font>";
                                        }
                                    }
                            %>
                            <tr>
                                <td><%=FormatarData.dateTimeFormat(objetoInterno.getDataLancamento())%></td>
                                <td>
                                    <form name="frm<%= objetoInterno.getNumeroObjeto()%>" id="frm<%= objetoInterno.getNumeroObjeto()%>" method="post" action="http://www2.correios.com.br/sistemas/rastreamento/Resultado.cfm" target="_blank">
                                        <input type="hidden" name="objetos" id="objetos" value="<%= objetoInterno.getNumeroObjeto()%>" />
                                    </form>                    
                                    <a href='#' onclick="document.getElementById('frm<%= objetoInterno.getNumeroObjeto()%>').submit();"><%= objetoInterno.getNumeroObjeto()%></a>
                                </td>
                                <td>
                                    <form name="frm<%=objetoInterno.getNumeroNovoObjeto()==null?"":objetoInterno.getNumeroNovoObjeto()%>" id="frm<%=objetoInterno.getNumeroNovoObjeto()==null?"":objetoInterno.getNumeroNovoObjeto()%>" method="post" action="http://www2.correios.com.br/sistemas/rastreamento/Resultado.cfm" target="_blank">
                                        <input type="hidden" name="objetos" id="objetos" value="<%=objetoInterno.getNumeroNovoObjeto()==null?"":objetoInterno.getNumeroNovoObjeto()%>" />
                                    </form>                    
                                    <a href='#' onclick="document.getElementById('frm<%= objetoInterno.getNumeroNovoObjeto()==null?"":objetoInterno.getNumeroNovoObjeto()%>').submit();"><%=objetoInterno.getNumeroNovoObjeto()==null?"":objetoInterno.getNumeroNovoObjeto()%></a>
                                </td>
                                <td><%=objetoInterno.getNumeroPedido()%></td>
                                <td><%=objetoInterno.getDestinatario().getNome()%></td>
                                <td><%=objetoInterno.getMotivo()%></td>
                                <td><%=situacaoHtml%>

                                </td>

                                <% if (objetoInterno.getSituacao().equals(SituacaoObjetoInterno.PENDENTE.toString())) {%>
                               
                                <td align="center">
                                    <img title="Reenviar para o mesmo endereço"  style="cursor:pointer;" onclick="reenviarObjetos(<%=objetoInterno.getId()%>)" src="../../imagensNew/mail_back.png">
                                </td>
                                <td align="center">
                                    <img title="Devolver o objeto"  style="cursor:pointer;" onclick="devolverObjeto(<%=objetoInterno.getId()%>)" src="../../imagensNew/truck_plus.png">
                                </td>
                                <% } else { %>
                                
                                <td align="center">
                                    <img title="Reenviar para o mesmo endereço"  style="cursor:pointer;" onclick="objetoEncaminhado()" src="../../imagensNew/mail_backgray.png">
                                </td>
                                <td align="center">
                                    <img title="Devolver o objeto"  style="cursor:pointer;" onclick="objetoEncaminhado()" src="../../imagensNew/truck_plusgray.png">
                                </td>
                                <% } %>


                            </tr>
                            <% }%>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
</html>

<script type="text/javascript">
   
    function alterarEndereco() {
        var formulario = document.getElementById('alteracaoEndereco');
        formulario.submit();


    }
    
     function devolverObjeto(id) {
        if (confirm("Deseja realmente que este objeto seja devolvido")) {
            var form = document.getElementById('formReenviarDevolver');
            var idObjeto = document.getElementById('idObjeto');
            var situacao = document.getElementById('situacao');
            idObjeto.value = id;
            situacao.value = "DEVOLVER";
            form.submit();
        }
    }

    function objetoEncaminhado() {
        alert('Este objeto já foi encaminhado');
    }

    function chamaDivProtecao(reload) {
        var classe = document.getElementById("divProtecao").className;
        if (classe == "esconder") {
            document.getElementById("divProtecao").className = "mostrar";
            document.getElementById("divInteracao").className = "mostrar";
        } else {
            document.getElementById("divProtecao").className = "esconder";
            document.getElementById("divInteracao").className = "esconder";
        }
        if(reload==true){
            location.reload();
        }
    }

    $(function () {
        $("#dataIni").datepicker({
            minDate: '<%=dataInicioCalendario%>',
            maxDate: '<%= dataAtual%>',
            showOn: "button",
            buttonImage: "../../imagensNew/calendario.png",
            buttonImageOnly: true,
            showAnim: "slideDown"
        });
        $("#dataFim").datepicker({
            minDate: '<%=dataInicioCalendario%>',
            maxDate: '<%=dataAtual%>',
            showOn: "button",
            buttonImage: "../../imagensNew/calendario.png",
            buttonImageOnly: true,
            showAnim: "slideDown"
        });
    });
    
    
    


</script>
