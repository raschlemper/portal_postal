
<%@page import="Util.XTrustProvider"%>
<%@page import="org.dom4j.Node"%>
<%@page import="java.util.List"%>
<%@page import="org.dom4j.Document"%>
<%@page import="java.io.StringReader"%>
<%@page import="org.dom4j.io.SAXReader"%>
<%@page import="Entidade.Clientes"%>
<%@page import="Controle.ContrClienteEtiquetas"%>
<%@page import="Controle.contrCliente"%>
<%@page import="Emporium.Controle.ContrPreVenda"%>
<%@page import="Entidade.PreVenda"%>
<%@page import="Entidade.ClienteLogEtiqueta"%>
<%@ page import = "java.text.DateFormat,java.util.ArrayList,java.sql.Timestamp,java.util.Calendar, java.util.GregorianCalendar, java.util.Date, java.text.SimpleDateFormat"%>

<%
    if (session.getAttribute("usuario") == null) {
        response.sendRedirect("../index.jsp?msgLog=3");
    } else {

        String nomeBD = (String) session.getAttribute("empresa");
        String numObj = "";
        if (request.getParameter("obj") != null) {
            numObj = request.getParameter("obj");
        }


%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />

        <script type="text/javascript" src="../../Cliente/js/ajax_portal.js"></script>
        <script type="text/javascript" src="../../javascript/ajax.js"></script>
        <script type="text/javascript" src="../../javascript/mascara.js"></script>

        <link href="../../css/estilo.css" rel="stylesheet" type="text/css" />


        <link type="text/css" href="../../javascript/jquery/css/jquery-ui-1.8.16.custom.css" rel="stylesheet" />
        <script type="text/javascript" src="../../javascript/jquery/js/jquery-1.6.2.min.js"></script>
        <script type="text/javascript" src="../../javascript/jquery/js/jquery-ui-1.8.16.custom.min.js"></script>
        <script type="text/javascript" src="../../javascript/jquery/js/jquery.ui.datepicker-pt-BR.js"></script>
        <!-- TableSorter -->
        <link rel="stylesheet" href="../../javascript/plugins/TableSorter/styleSorterV3.css" />
        <script type="text/javascript" src="../../javascript/plugins/TableSorter/scriptSorterV3.js"></script>
        <!-- TableSorter -->
        <script type="text/javascript" src="../../javascript/plugins/autocomplete/js/simpleAutoComplete.js"></script>
        <link rel="stylesheet" type="text/css" href="../../javascript/plugins/autocomplete/css/simpleAutoComplete.css" />

        <link rel="stylesheet" href="../../javascript/plugins/dropdown/css/style.css" type="text/css" media="screen, projection"/>
        <!--[if lte IE 7]>
        <link rel="stylesheet" type="text/css" href="../../javascript/plugins/dropdown/css/ie.css" media="screen" />
        <![endif]-->
        <script type="text/javascript" language="javascript" src="../../javascript/plugins/dropdown/js/jquery.dropdownPlain.js"></script>
        <script type="text/javascript" charset="utf-8">
            $(document).ready(function () {
                $('#nome_cliente').simpleAutoComplete('../../AjaxPages/ajax_autocomplete_cliente.jsp', {
                    autoCompleteClassName: 'autocomplete',
                    selectedClassName: 'sel',
                    attrCallBack: 'rel',
                    identifier: 'estado'
                }, estadoCallback);
            });

            $(function () {
                $("#data").datepicker({
                    showOn: "button",
                    buttonImage: "../../imagensNew/calendario.png",
                    buttonImageOnly: true,
                    showAnim: "slideDown"
                });
                $("#data2").datepicker({
                    showOn: "button",
                    buttonImage: "../../imagensNew/calendario.png",
                    buttonImageOnly: true,
                    showAnim: "slideDown"
                });
            });

            function validaForm() {
                var form = document.form1;
                if (form.data.value == "") {
                    alert("Preencha a Data Inicial do Periodo!");
                    return false;
                } else {
                    if (valida_data(form.data) == false) {
                        return false;
                    }
                }

                if (form.data2.value == "") {
                    alert("Preencha a Data Final do Periodo!");
                    return false;
                } else {
                    if (valida_data(form.data2) == false) {
                        return false;
                    }
                }

                //VERIFICA A DIFERENÇA DE DIAS ENTRE AS DATAS!
                var dataIni = form.data.value;
                var dataFim = form.data2.value;
                var aux1 = dataIni.split("/");
                var aux2 = dataFim.split("/");

                var dia1 = aux1[0];
                var mes1 = aux1[1];
                mes1 = Math.floor(mes1) - 1;
                var ano1 = aux1[2];
                var data1 = new Date(ano1, mes1, dia1);

                var dia2 = aux2[0];
                var mes2 = aux2[1];
                mes2 = Math.floor(mes2) - 1;
                var ano2 = aux2[2];
                var data2 = new Date(ano2, mes2, dia2);

                var diferenca = data2.getTime() - data1.getTime();
                diferenca = Math.floor(diferenca / (1000 * 60 * 60 * 24));

                if (data1 <= data2) {
                    if (diferenca > 31) {
                        alert("Periodo máximo de importação por vez é de 31 dias!")
                        return false;
                    }
                } else {
                    alert("A data inicial deve ser menor ou igual a data final!");
                    return false;
                }


                form.submit();
            }


            function estadoCallback(par) {
                //document.getElementById("cli_cod").innerHTML = par[0];
                //document.getElementById("cli_nome").innerHTML = par[1];
                // document.getElementById("cli_endereco").innerHTML = par[2];
                // document.getElementById("cli_cep").innerHTML = par[3];
                // document.getElementById("cli_bairro").innerHTML = par[4] + ", " + par[5];
                //document.getElementById("cli_complemento").value = par[5];
                // document.getElementById("cli_cidade").innerHTML = par[6] + "/" + par[7];
                //document.getElementById("cli_estado").value = par[7];
                //  document.getElementById("cli_email").innerHTML = par[8];
                // document.getElementById("cli_fone").innerHTML = par[9];
                //  document.getElementById("cli_cnpj").innerHTML = par[10];

                document.getElementById("idCliente").value = par[0];
                //montaComboContato(par[0]);
            }

            function pesqSro(param) {
                $('#objetos').val(param);
                $('#frmSRO').submit();
            }
        </script>
        <title>Portal Postal | Importação dos Clientes</title>
    </head>
    <body onload="alertaQtd();">
        <div id="divInteracao" class="esconder" align="center"><input id="textointeracao" /></div>
        <div id="divProtecao" class="esconder"></div>

        <%@ include file="../../Includes/menu_agencia.jsp" %>
        <%@ include file="../../Includes/telaMsg.jsp" %>

        <div id="divPrincipal" align="center">
            <div id="container"><!--style="border:1px solid #008ED6;border-top: none;box-sizing:border-box;-moz-box-sizing:border-box;"-->
                <div id="conteudo">


                    <div id="titulo1">Pesquisa de Etiqueta</div>
                    <form action="painel_etiquetas_pesq.jsp" method="post">
                        <ul class="ul_formulario" >
                            <li class="titulo"><dd><span>PESQUISAR</span></dd></li>
                            <li>
                                <dd>
                                    <label>Número SRO</label>
                                    <input type="text" style="width:100px;" name="obj" id="obj" value="" maxlength="13" onkeypress="mascara(this, maskObjetoSRO);" />
                                </dd>
                            </li>                          
                            <li>
                                <dd>
                                    <div class="buttons">                                        
                                        <button type="submit" onclick="abrirTelaEspera();" class="regular"><img src="../../imagensNew/lupa.png"/> PESQUISAR</button>
                                    </div>
                                </dd>
                            </li>
                        </ul>
                    </form>
                    <form action="painel_etiquetas_pesq.jsp" method="post" name="form2">
                        <ul class="ul_formulario" >                            
                            <li class="titulo">
                                <dd><span>Pesquisar por Cliente e Periodo</span></dd>
                            </li>
                            <li>
                                <dd>
                                    <label>Selecione um Cliente</label>
                                    <input style="width: 450px;" type="text" placeholder="Digite aqui o nome ou código de um cliente..." name="nome_cliente" id="nome_cliente" />
                                    <input type="hidden" name="idCliente" id="idCliente" value="0" />
                                </dd>
                            </li>
                            <li>      
                                <dd>
                                    <label>Periodo da Impressão</label>
                                    <input type="text" id="data" name="data" class="date-pick" value="" size="12" maxlength="10" onKeyPress="mascara(this, maskData)" />
                                    até
                                    <input type="text" id="data2" name="data2" size="12" class="date-pick" value=""  maxlength="10" onKeyPress="mascara(this, maskData)" />
                                </dd>
                            </li> 
                            <li>
                                <dd>
                                    <div class="buttons">
                                        <input type="hidden" style="width:100px;" name="obj" id="obj" />
                                        <button type="button" onclick="abrirTelaEspera();
                                                document.form2.submit();" class="regular"><img src="../../imagensNew/lupa.png"/> PESQUISAR</button>
                                    </div>
                                </dd>
                            </li>
                        </ul>
                    </form>

                    <div style="width: 100%;">
                        <div id="titulo2">Resultado da Pesquisa: <%= numObj%></div>
                        <%
                            if (numObj.equals("") && request.getParameter("idCliente") != null) {
                                int idCliente = Integer.parseInt(request.getParameter("idCliente"));
                                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                                String vDataAtual = sdf.format(new Date());
                                if (request.getParameter("data") != null) {
                                    vDataAtual = request.getParameter("data");
                                }
                                String vData2 = sdf.format(new Date());
                                if (request.getParameter("data2") != null) {
                                    vData2 = request.getParameter("data2");
                                }

                                String dataIni = Util.FormatarData.DateToBD(vDataAtual);
                                String dataFim = Util.FormatarData.DateToBD(vData2);
                                ArrayList<PreVenda> listaPV = ContrPreVenda.consultaPreVendasByClientePeriodo(idCliente, dataIni, dataFim, nomeBD);
                        %>

                        <table cellpadding="0" cellspacing="0" border="0" class="tinytable">
                            <thead>
                                <tr>
                                    <th><h3>Nº do Objeto</h3></th>
                                    <th><h3>Destinatario</h3></th>
                                    <th><h3>Cep Destino</h3></th>
                                    <th><h3>Cidade/UF</h3></th>
                                    <th><h3>Servico</h3></th>
                                    <th><h3>Adicionais</h3></th>
                                </tr>
                            </thead>
                            <%
                                for (PreVenda pv : listaPV) {
                                    String adicionais = "";
                                    if (pv.getAviso_recebimento() == 1) {
                                        adicionais += "AR";
                                    }
                                    if (pv.getMao_propria() == 1) {
                                        adicionais += " / MP";
                                    }
                                    if (pv.getValor_declarado() > 0) {
                                        adicionais += " / VD R$ " + pv.getValor_declarado();
                                    }
                            %>                                
                            <tbody>
                                <tr>
                                    <td><a href="painel_etiquetas_pesq.jsp?obj=<%= pv.getNumObjeto()%>"><%= pv.getNumObjeto()%></a></td>
                                    <td><%= pv.getNomeDes()%></td>
                                    <td><%= pv.getCepDes()%></td>
                                    <td><%= pv.getCidadeDes() + "/" + pv.getUfDes()%></td>
                                    <td><%= pv.getNomeServico()%></td>
                                    <td><%= adicionais%></td>
                                </tr>
                            </tbody>
                            <%}%>
                        </table>


                        <%
                        } else if (!numObj.equals("")) {
                            PreVenda pv = ContrPreVenda.consultaVendaBySRO(numObj, nomeBD);
                            if (pv != null) {

                                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss");
                                Clientes cli = contrCliente.consultaClienteById(pv.getIdCliente(), nomeBD);

                                String dtVenda = "- - -";
                                if (pv.getDataPreVenda() != null) {
                                    dtVenda = sdf.format(pv.getDataPreVenda()) + "<br/>" + pv.getNomePreVenda();
                                }
                                String dtImpr = "- - -";
                                if (pv.getDataImpresso() != null) {
                                    dtImpr = sdf.format(pv.getDataImpresso()) + "<br/>" + pv.getNomeImpresso();
                                }
                                String dtCons = "- - -";
                                if (pv.getDataConsolidado() != null) {
                                    dtCons = sdf.format(pv.getDataConsolidado()) + "<br/>" + pv.getNomeConsolidado();
                                }
                                String dtSect = "- - -";
                                if (pv.getDataVenda() != null) {
                                    dtSect = sdf.format(pv.getDataVenda()) + "<br/>" + pv.getNomeVenda();
                                }

                                String nrObj = "- - -";
                                if (!pv.getNumObjeto().equals("avista")) {
                                    nrObj = pv.getNumObjeto();
                                }

                        %>
                        <ul class="ul_dados">   
                            <li><dd class="titulo">Dados da Venda</dd></li> 
                            <li>
                                <dd style="width: 200px;">
                                    <label>Nº do Objeto</label>
                                     <a href='#' onclick="pesqSro('<%= nrObj%>');"><%= nrObj%></a>
                                </dd>
                                <dd style="width: 200px;">
                                    <label>Serviço</label>
                                    <%= pv.getNomeServico() + " - " + pv.getCodECT()%>
                                </dd>
                            </li>       
                            <li>
                                <dd>
                                    <label>Serviços Adicionais</label>
                                    <b>VD:</b> R$ <%= pv.getValor_declarado()%>
                                    <b style='margin:0 20px 0 20px;'>|</b> <b>AR:</b> <% if (pv.getAviso_recebimento() == 1) { %> <img width="12" src="../../imagensNew/tick_circle.png" /> <%} else {%> <img width="12" src="../../imagensNew/cross_circle.png" /> <%}%>
                                    <b style='margin:0 20px 0 20px;'>|</b> <b>MP:</b> <% if (pv.getMao_propria() == 1) { %> <img width="12" src="../../imagensNew/tick_circle.png" /> <%} else {%> <img width="12" src="../../imagensNew/cross_circle.png" /> <%}%>
                                </dd>
                            </li>   
                            <li>
                                <dd style="width: 200px;">
                                    <label>Data da Pré-Postagem</label>
                                    <%= dtVenda%>
                                </dd>
                                <dd style="width: 200px;">
                                    <label>Data da Impressão</label>
                                    <%= dtImpr%>
                                </dd>
                                <dd style="width: 200px;">
                                    <label>Data da Consolidado</label>
                                    <%= dtCons%>
                                </dd>
                                <dd style="width: 200px;">
                                    <label>Data da Exportado p/ SECT</label>
                                    <%= dtSect%>
                                </dd>
                            </li>     
                            <li><dd class="titulo">Dados do Remetente</dd></li>
                            <li>
                                <dd>
                                    <label>Nome / Razão Social</label>
                                    <%= cli.getNome()%>
                                </dd>
                                <dd style="margin-left: 50px;">
                                    <label>Departamento</label>
                                    <%= pv.getDepartamento()%>
                                </dd>
                                <dd style="margin-left: 50px;">
                                    <label>Contrato</label>
                                    <%= cli.getNumContrato() + "/" + cli.getAnoContrato() + " - DR/" + cli.getUfContrato()%>
                                </dd>
                                <dd style="margin-left: 50px;">
                                    <label>Cartão de Postagem</label>
                                    <%= pv.getCartaoPostagem()%>
                                </dd>
                            </li>
                            <li><dd class="titulo">Dados do Destinatário</dd></li>
                            <li>
                                <dd>
                                    <label>Nome / Razão Social</label>
                                    <%= pv.getNomeDes()%>
                                </dd>
                                <%if (pv.getAos_cuidados() != null && !"".equals(pv.getAos_cuidados())) {%>
                                <dd style="margin-left: 50px;">
                                    <label>Aos Cuidados</label>
                                    <%= pv.getAos_cuidados()%>
                                </dd>
                                <%}%>
                                <dd style="margin-left: 50px;">
                                    <label>Empresa</label>
                                    <%= pv.getEmpresaDes()%>
                                </dd>
                            </li>
                            <li>
                                <dd>
                                    <label>Endereço</label>
                                    <%= pv.getEnderecoDes() + ", " + pv.getNumeroDes() + ", " + pv.getComplementoDes()%>
                                </dd>
                            </li>
                            <li>
                                <dd>
                                    <label>Bairro - Cidade / UF</label>
                                    <%= pv.getBairroDes() + " - " + pv.getCidadeDes() + " / " + pv.getUfDes()%>
                                </dd>
                            </li>
                            <li>
                                <dd>
                                    <label>CEP</label>
                                    <%= pv.getCepDes()%>
                                </dd>
                            </li>
                            <li><dd class="titulo">RASTREAMENTO DO SRO</dd></li>
                                <%
                                    try {
                                        XTrustProvider.install();
                                        br.com.correios.bsb.sigep.master.bean.cliente.AtendeClienteService service = new br.com.correios.bsb.sigep.master.bean.cliente.AtendeClienteService();
                                        br.com.correios.bsb.sigep.master.bean.cliente.AtendeCliente port = service.getAtendeClientePort();
                                        // TODO initialize WS operation arguments here
                                        java.util.List<java.lang.String> listaObjetos = new ArrayList();
                                        listaObjetos.add(pv.getNumObjeto());
                                        java.lang.String tipoConsulta = "L";
                                        java.lang.String tipoResultado = "T";
                                        java.lang.String usuarioSro = "ECT";
                                        java.lang.String senhaSro = "SRO";
                                        // TODO process result here
                                        java.lang.String result = port.consultaSRO(listaObjetos, tipoConsulta, tipoResultado, usuarioSro, senhaSro);
                                        //out.println("Result = "+result);

                                        SAXReader reader = new SAXReader();
                                        StringReader sr = new StringReader(result);
                                        Document doc = reader.read(sr);
                                        List<Node> eventos = (List<Node>) doc.selectNodes("//sroxml/objeto/evento");
                                        // processa eventos
                                        for (Node node : eventos) {
                                            String data = node.valueOf("data");
                                            String hora = node.valueOf("hora");
                                            String descricao = node.valueOf("descricao");
                                            String local = node.valueOf("local");
                                            String cidade = node.valueOf("cidade");
                                            String uf = node.valueOf("uf");
                                %>
                            <li>
                                <dd>
                                    <label style="font-size: 13px;"><%= data + " - " + hora%></label>
                                    <b><%= descricao%></b><br/>
                                    <%= local + " - " + cidade + "/" + uf%>
                                </dd>
                            </li>
                            <div style="border-bottom: 1px solid silver;width: 100%;clear: both;"> </div>
                            <%
                                }

                            } catch (Exception ex) {
                            %>
                            <%= ex.getMessage()%>
                            <%
                                    // TODO handle custom exceptions here
                                }
                            %>
                        </ul>    
                        <div style="width: 100%; clear: both;"></div>
                        <%} else if (!numObj.equals("")) {%>
                        <div style="width: 100%;text-align: center;vertical-align: middle;padding: 50px 0px;background: #ff5555;color: white; font-size: 22px;font-weight: bold;">Etiqueta <%= numObj%> Não Encontrada!</div>
                        <%}
                            }%>
                    </div>

                </div>
            </div>
        </div>
        <form name="frmSRO" id="frmSRO" method="post" action="http://www2.correios.com.br/sistemas/rastreamento/Resultado.cfm" target="_blank">
            <input type="hidden" name="objetos" id="objetos" value="" />
        </form> 
    </body>
</html>
<%}%>