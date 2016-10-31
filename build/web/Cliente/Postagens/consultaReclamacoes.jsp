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
                String dataInicioCalendario = Util.SomaData.SomarDiasDatas(dataAtual, -90); // diminui 2 meses
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

                /*
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
            
            function chamaDivProtecao2() {
                var classe = document.getElementById("divProtecao").className;
                if (classe === "esconder") {
                    document.getElementById("divProtecao").className = "mostrar";
                    document.getElementById("divInteracao2").className = "mostrar";
                } else {
                    document.getElementById("divProtecao").className = "esconder";
                    document.getElementById("divInteracao2").className = "esconder";
                }
            }
            
            function chamaDivProtecao() {
                var classe = document.getElementById("divProtecao").className;
                if (classe === "esconder") {
                    document.getElementById("divProtecao").className = "mostrar";
                    document.getElementById("divInteracao").className = "mostrar";
                } else {
                    document.getElementById("divProtecao").className = "esconder";
                    document.getElementById("divInteracao").className = "esconder";
                }
            }
            
            function chamaJanelaFechamento(numeroRegistro) {                
                    //document.getElementById("numObjetoAr").value = numeroRegistro;
                    document.getElementById("numObj").innerHTML = numeroRegistro;
                    //document.getElementById("nomeRecebedor").value = "";
                    //document.getElementById("dataRecebimento").value = "<%= sdf.format(new Date())%>";
                    chamaDivProtecao2();                
            }
            function chamaJanelaAbertura(numeroRegistro) {                
                    //document.getElementById("numObjetoAr").value = numeroRegistro;
                    document.getElementById("numAr").innerHTML = numeroRegistro;
                    //document.getElementById("nomeRecebedor").value = "";
                    //document.getElementById("dataRecebimento").value = "<%= sdf.format(new Date())%>";
                    chamaDivProtecao();                
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
                    pesquisaPI('<%=numCliente%>', '<%=nomeBD%>');
                    return true;
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
                sortcolumn: 5,
                sortdir: -1,
                init: false
            });

        </script>
    </head>
    <body>
        <div style="top: 20%;bottom: 35%;left: 30%;right: 30%;" id="divInteracao2" class="esconder" align="center">
            <div style="width: 100%; margin: 15px 0;">
                <div style="width: 95%; text-align: left;">
                    <div style='float:right;'><a onclick='chamaDivProtecao2();' href='#' class='botaoClose'>Fechar</a></div>
                    <div id='titulo1'><span id="numObj"></span> - Finalizar Reclamação</div>
                </div>
                <img style="margin-bottom: 25px;" width="100%" src="../../imagensNew/linha.jpg"/>
                <form  name="formSro" action="../../Serv" method="post">
                    <ul style="width: 95%; text-align: left;" class="ul_formulario">
                        <li>
                            <dd>
                                <label>Indenização Recebida?</label>
                                <select name="status" id="status" >
                                    <option value="3">SIM</option>
                                    <option value="2">NÃO</option>
                                </select>
                            </dd>
                        </li>
                        <li>
                            <dd>
                                <label>Valor Indenizado:</label>
                                <input type="text" onkeypress="mascara(this, maskReal);" />
                            </dd>
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
                        <button type="button" class="negative" onClick='chamaDivProtecao2();'><img src="../../imagensNew/cross_circle.png" /> CANCELAR</button>
                    </div>
                </form>
            </div>
        </div>
        <div style="top: 20%;bottom: 35%;left: 30%;right: 30%;" id="divInteracao" class="esconder" align="center">
            <div style="width: 100%; margin: 15px 0;">
                <div style="width: 95%; text-align: left;">
                    <div style='float:right;'><a onclick='chamaDivProtecao();' href='#' class='botaoClose'>Fechar</a></div>
                    <div id='titulo1'><span id="numAr"></span> - Abrir Reclamação</div>
                </div>
                <img style="margin-bottom: 25px;" width="100%" src="../../imagensNew/linha.jpg"/>
                <form  name="formSro" action="../../Serv" method="post">
                    <ul style="width: 95%; text-align: left;" class="ul_formulario">
                        <li>
                            <dd>
                                <label>Motivo da Reclamação</label>
                                <select name="motivo" id="motivo" >
                                    <option value="">Selecione o motivo</option>
                                    <option value="240">AR Digital - Imagem não disponível</option>
                                    <option value="134">Conteúdo Avariado</option>
                                    <option value="133">Correspondência Violada</option>
                                    <option value="211">Destinatário não recebeu a correspondência</option>
                                    <option value="136">Objeto devolvido indevidamente</option>
                                    <option value="135">Objeto entregue com atraso</option>
                                    <option value="132">Objeto entregue indevidamente</option>
                                    <option value="148">Remetente não recebeu o AR</option>
                                </select>
                            </dd>
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

                    <form action="graf_objetos.jsp" method="post">
                        <div style="width: 100%;">
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
                                        <dd>
                                            <label>Filtrar por situação</label>
                                            <select id="situacao" name="situacao">
                                                <option selected="" value="">-- TODAS --</option>
                                                <option value="AND (data_postagem >= DATE_SUB(DATE(NOW()), INTERVAL 90 DAY)
                                                        AND (prazo_estimado < DATE(prazo_cumprido)
                                                        OR (last_status_code, last_status_type) IN (
                                                        (9, 'BDE'),(9, 'BDR'),(9, 'BDI'),
                                                        (28, 'BDE'),(28, 'BDR'),(28, 'BDI'),
                                                        (37, 'BDE'),(37, 'BDR'),(37, 'BDI'),
                                                        (43, 'BDE'),(43, 'BDR'),(43, 'BDI'),
                                                        (50, 'BDE'),(50, 'BDR'),(50, 'BDI'),
                                                        (51, 'BDE'),(51, 'BDR'),(51, 'BDI'),
                                                        (52, 'BDE'),(52, 'BDR'),(52, 'BDI')))
                                                        AND  mt.pi_status_code <> 2) ">PASSIVEL DE RECLAMAÇAO</option>                                                
                                            </select>
                                        </dd>
                                        <dd>
                                            <label>Filtrar por Reclamação</label>
                                            <select id="status_pi" name="status_pi">
                                                <option value="">Todos</option>
                                                <option value="1">Com Reclamação em Aberto</option>
                                                <option value="3">Finalizado e Indenizado</option>
                                                <option value="2">Finalizado e Não Indenizado</option>
                                                <option value="0">Sem Reclamação</option>
                                                <option value="-1">Falha ao abrir reclamação</option>
                                            </select>
                                        </dd>
                                    </li>
                                    <li>
                                        <dd>
                                            <div class="buttons">
                                                <button style="margin-top: 5px;" type="button" class="regular" onclick="validaDataSint();"><img src="../../imagensNew/lupa.png"/> PESQUISAR</button>
                                            </div>
                                        </dd>
                                        <dd>
                                            *Passível de Reclamação = Situações automaticamente identificadas pelo sistema, que são passíveis de reclamação por atraso ou avaria/extravio.<br/>
                                            Caso 
                                        </dd>
                                    </li>
                                </ul>
                            </div>
                        </div>
                    </form>
                    <img width="100%" src="../../imagensNew/linha.jpg"/>
                    <div style="width: 100%;clear: both;">
                        <div style="width: 100%;clear: both;">
                            <div id="titulo2" >Resultado da Pesquisa</div>
                            <div id="tableObjeto"></div>
                        </div>                        
                    </div>
                </div>                 
            </div>
        </div>
    </body>
</html>
<%}%>