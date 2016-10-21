
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
        int idCliente = 0;
        if (request.getParameter("idCliente") != null) {
            idCliente = Integer.parseInt(request.getParameter("idCliente"));
        }
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss");
        String vDataAtual = sdf.format(new Date());
        if (request.getParameter("data") != null) {
            vDataAtual = request.getParameter("data");
        }
        String vData2 = sdf.format(new Date());
        if (request.getParameter("data2") != null) {
            vData2 = request.getParameter("data2");
        }


%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <title>Portal Postal</title>
        <%@ include file="../includes/Css_js.jsp" %>
    </head>  
    <body>   
        <script type="text/javascript">
            waitMsg();
        </script> 
        <jsp:include page="../includes/navBarTop.jsp"></jsp:include>
            <div id="wrapper">
            <%@ include file="../includes/menu_agencia_bootstrap.jsp" %>

            <div id="page-content-wrapper">
                <div class="container-fluid">
                    <div id="page-wrapper">

                        <div class="row">
                            <div class="col-md-12">                       
                                <h4 class="page-header"><b class="text-primary"><i class="fa fa-barcode"></i> Gerenciar Etiquetas</b> > <small>Pesquisar Etiqueta</small></h4>
                            </div>
                        </div>
                        <div class="row">

                            <div class="well well-md"> 
                                <div>
                                    <h4 class="subtitle">Pesquisar por Etiqueta</h4>
                                </div>                        
                                <form class="form-inline" action="painel_etiquetas_pesq_b.jsp" method="post">    
                                    <div class="form-group" >           
                                        <input id="obj" class="form-control" type="text" name="obj"  maxlength="13" onkeypress="mascara(this, maskObjetoSRO);" placeholder="Digite a etiqueta aqui..." />
                                        <button type="submit" class="btn btn-sm btn-primary form-control" onclick="waitMsg()"><i class="fa fa-lg fa-search"></i></button>
                                    </div>
                                </form>
                                <div>
                                    <h4 class="subtitle">Pesquisar por Cliente e Periodo</h4>
                                </div> 
                                <form action="painel_etiquetas_pesq_b.jsp" method="post" name="form2">
                                    <div class="row">
                                        <div class="form-group col-md-5" >
                                            <label class="small">Selecione um Cliente</label>
                                            <select class="populate placeholder" name="idCliente" id="idCliente">
                                                <option value="">-- Selecione um Cliente --</option>
                                                <%
                                                    ArrayList<Clientes> listaCliente = Controle.contrCliente.getNomeCodigoMetodo(nomeBD, false);
                                                    for (Clientes c : listaCliente) {
                                                        if (idCliente == c.getCodigo()) {
                                                            out.println("<option selected value='" + c.getCodigo() + "'>[" + c.getCodigo() + "] " + c.getNome() + "</option>");
                                                        } else {
                                                            out.println("<option value='" + c.getCodigo() + "'>[" + c.getCodigo() + "] " + c.getNome() + "</option>");
                                                        }
                                                    }
                                                %>
                                            </select>
                                        </div>  
                                    </div>
                                    <div class="row">
                                        <div class="form-inline col-md-5">
                                            <label class="small">Selecione o Periodo da Impressão</label>
                                            <div class="clearfix"></div>
                                            <label for="data">De</label>
                                            <div class="input-group">
                                                <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                                                <input type="text" id="data" name="data" class="form-control" value="<%= vDataAtual%>" size="10" maxlength="10" onkeypress="mascara(this, maskData)" />
                                            </div>
                                            <label for="data2">à</label>
                                            <div class="input-group">
                                                <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                                                <input type="text" id="data2" name="data2" size="10" class="form-control" value="<%= vData2%>"  maxlength="10" onkeypress="mascara(this, maskData)" />
                                            </div>
                                            <input type="hidden" style="width:100px;" name="obj" />
                                            <button type="submit" class="btn btn-sm btn-primary form-control" onclick="waitMsg();
                                                    document.form2.submit();"><i class="fa fa-lg fa-search"></i></button>
                                        </div>
                                    </div>
                                </form>
                            </div>   
                        </div>  

                        <div class="row">
                            <form action="../../ServPreVendaInutilizar" method="post" name="formDel">
                                <div class="col-lg-12">
                                    <div class="panel panel-default">  
                                        <div class="panel-heading" >Resultado da Pesquisa</div>
                                        <div class="panel-body">

                                            <div class="dataTable_wrapper no-padding">


                                                <%
                                                    if (numObj.equals("") && request.getParameter("idCliente") != null) {
                                                        String dataIni = Util.FormatarData.DateToBD(vDataAtual);
                                                        String dataFim = Util.FormatarData.DateToBD(vData2);
                                                        ArrayList<PreVenda> listaPV = ContrPreVenda.consultaPreVendasByClientePeriodo(idCliente, dataIni, dataFim, nomeBD);
                                                %>
                                                <table class="table table-striped table-bordered table-hover table-condensed" id="dataTables-etqPend">
                                                    <thead>
                                                        <tr>
                                                            <th>Nº do Objeto</th>
                                                            <th>Destinatario</th>
                                                            <th>Cep Destino</th>
                                                            <th>Cidade/UF</th>
                                                            <th>Servico</th>
                                                            <th>Adicionais</th>
                                                        </tr>
                                                    </thead>                          
                                                    <tbody>
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
                                                        <tr>
                                                            <td><a href="painel_etiquetas_pesq_b.jsp?obj=<%= pv.getNumObjeto()%>"><i class="fa fa-search fa-fw text-info"></i><strong> <%= pv.getNumObjeto()%></strong></a></td>
                                                            <td><%= pv.getNomeDes()%></td>
                                                            <td><%= pv.getCepDes()%></td>
                                                            <td><%= pv.getCidadeDes() + "/" + pv.getUfDes()%></td>
                                                            <td><%= pv.getNomeServico()%></td>
                                                            <td><%= adicionais%></td>
                                                        </tr>
                                                        <%}%>
                                                    </tbody>
                                                </table>


                                                <%
                                                } else if (!numObj.equals("")) {
                                                    PreVenda pv = ContrPreVenda.consultaVendaBySRO(numObj, nomeBD);
                                                    if (pv != null) {

                                                        Clientes cli = contrCliente.consultaClienteById(pv.getIdCliente(), nomeBD);

                                                        String dtVenda = "- - -";
                                                        if (pv.getDataPreVenda() != null) {
                                                            dtVenda = sdf2.format(pv.getDataPreVenda()) + " - " + pv.getNomePreVenda();
                                                        }
                                                        String dtImpr = "- - -";
                                                        if (pv.getDataImpresso() != null) {
                                                            dtImpr = sdf2.format(pv.getDataImpresso()) + " - " + pv.getNomeImpresso();
                                                        }
                                                        String dtCons = "- - -";
                                                        if (pv.getDataConsolidado() != null) {
                                                            dtCons = sdf2.format(pv.getDataConsolidado()) + " - " + pv.getNomeConsolidado();
                                                        }
                                                        String dtSect = "- - -";
                                                        if (pv.getDataVenda() != null) {
                                                            dtSect = sdf2.format(pv.getDataVenda()) + " - " + pv.getNomeVenda();
                                                        }

                                                        String nrObj = "- - -";
                                                        if (!pv.getNumObjeto().equals("avista")) {
                                                            nrObj = pv.getNumObjeto();
                                                        }

                                                %>
                                                <ul class="list-group">
                                                    <li class="list-group-item">
                                                        <div class="row toggle" id="dropdown-detail-1" data-toggle="detail-1">
                                                            <div class="col-xs-10">
                                                                <b>Dados da Venda</b>
                                                            </div>
                                                            <div class="col-xs-2"><i class="fa fa-chevron-down pull-right"></i></div>
                                                        </div>
                                                        <div id="detail-1">
                                                            <hr></hr>
                                                            <div class="container-fluid">
                                                                <div class="fluid-row">
                                                                    <ul class="list-unstyled">
                                                                        <li>
                                                                            <label>Nº do Objeto:</label>
                                                                            <a href='#' onclick="pesqSro('<%= nrObj%>');"><%= nrObj%></a>
                                                                        </li>
                                                                        <li>
                                                                            <label>Serviço:</label>
                                                                            <%= pv.getNomeServico() + " - " + pv.getCodECT()%>
                                                                        </li>
                                                                        <li>
                                                                            <label>Aviso de Recebimento:</label> 
                                                                            <% if (pv.getAviso_recebimento() == 1) { %> <span class="fa fa-check-circle fa-lg text-success"></span> <%} else {%> <span class="fa fa-times-circle fa-lg text-danger"></span> <%}%>
                                                                        </li>
                                                                        <li>
                                                                            <label>Mão Própria:</label> 
                                                                            <% if (pv.getMao_propria() == 1) { %> <span class="fa fa-check-circle fa-lg text-success"></span> <%} else {%> <span class="fa fa-times-circle fa-lg text-danger"></span> <%}%>
                                                                        </li>
                                                                        <li>
                                                                            <label>Valor Declarado:</label> 
                                                                            R$ <%= pv.getValor_declarado()%>
                                                                        </li>
                                                                        <li class="divider"></li>
                                                                        <li>
                                                                            <label>Data da Pré-Postagem: </label>
                                                                            <%= dtVenda%>
                                                                        </li>
                                                                        <li>
                                                                            <label>Data da Impressão: </label>
                                                                            <%= dtImpr%>
                                                                        </li>
                                                                        <li>
                                                                            <label>Data da Consolidado: </label>
                                                                            <%= dtCons%>
                                                                        </li>
                                                                        <li>
                                                                            <label>Data da Exportação: </label>
                                                                            <%= dtSect%>
                                                                        </li>
                                                                    </ul>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </li>   
                                                    <li class="list-group-item">
                                                        <div class="row toggle" id="dropdown-detail-2" data-toggle="detail-2">
                                                            <div class="col-xs-10">
                                                                <b>Dados do Remetente</b>
                                                            </div>
                                                            <div class="col-xs-2"><i class="fa fa-chevron-down pull-right"></i></div>
                                                        </div>
                                                        <div id="detail-2">
                                                            <hr></hr>
                                                            <div class="container-fluid">
                                                                <div class="fluid-row">
                                                                    <ul class="list-unstyled">
                                                                        <li>
                                                                            <label>Nome / Razão Social:</label>
                                                                            <%= cli.getNome()%>
                                                                        </li>
                                                                        <li>
                                                                            <label>Departamento:</label>
                                                                            <%= pv.getDepartamento()%>
                                                                        </li>
                                                                        <li>
                                                                            <label>Contrato:</label>
                                                                            <%= cli.getNumContrato() + "/" + cli.getAnoContrato() + " - DR/" + cli.getUfContrato()%>
                                                                        </li>
                                                                        <li>
                                                                            <label>Cartão de Postagem:</label>
                                                                            <%= pv.getCartaoPostagem()%>
                                                                        </li>
                                                                    </ul>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </li>    
                                                    <li class="list-group-item">
                                                        <div class="row toggle" id="dropdown-detail-3" data-toggle="detail-3">
                                                            <div class="col-xs-10">
                                                                <b>Dados do Destinatário</b>
                                                            </div>
                                                            <div class="col-xs-2"><i class="fa fa-chevron-down pull-right"></i></div>
                                                        </div>
                                                        <div id="detail-3">
                                                            <hr></hr>
                                                            <div class="container-fluid">
                                                                <div class="fluid-row">
                                                                    <ul class="list-unstyled">
                                                                        <li>
                                                                            <label>Nome / Razão Social:</label>
                                                                            <%= pv.getNomeDes()%>
                                                                        </li>
                                                                        <%if (pv.getAos_cuidados() != null && !"".equals(pv.getAos_cuidados())) {%>
                                                                        <li>
                                                                            <label>Aos Cuidados:</label>
                                                                            <%= pv.getAos_cuidados()%>
                                                                        </li>
                                                                        <%}%>
                                                                        <li>
                                                                            <label>Empresa:</label>
                                                                            <%= pv.getEmpresaDes()%>
                                                                        </li>
                                                                        <li>
                                                                            <label>Endereço:</label>
                                                                            <%= pv.getEnderecoDes() + ", " + pv.getNumeroDes() + ", " + pv.getComplementoDes()%>
                                                                        </li>
                                                                        <li>
                                                                            <label>Bairro - Cidade / UF:</label>
                                                                            <%= pv.getBairroDes() + " - " + pv.getCidadeDes() + " / " + pv.getUfDes()%>
                                                                        </li>
                                                                        <li>
                                                                            <label>CEP:</label>
                                                                            <%= pv.getCepDes()%>
                                                                        </li>
                                                                    </ul>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </li>    
                                                    <li class="list-group-item">
                                                        <div class="row toggle" id="dropdown-detail-4" data-toggle="detail-4">
                                                            <div class="col-xs-10">
                                                                <b>Rastreamento do Objeto</b>
                                                            </div>
                                                            <div class="col-xs-2"><i class="fa fa-chevron-down pull-right"></i></div>
                                                        </div>
                                                        <div id="detail-4">
                                                            <hr></hr>
                                                            <div class="container-fluid">
                                                                <div class="fluid-row">
                                                                    <ul class="list-unstyled">
                                                                        <%
                                                                            try {
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
                                                                                if (!eventos.isEmpty()) {
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
                                                                        <%}
                                                                        } else {%>
                                                                        <div class="alert alert-danger">Nenhum registro encontrado nos Correios.</div>
                                                                        <%}
                                                                        } catch (Exception ex) {%>
                                                                        <div class="alert alert-danger"><%= ex.getMessage()%></div>
                                                                        <%}%>
                                                                    </ul>    

                                                                    <%} else if (!numObj.equals("")) {%>
                                                                    <div class="alert alert-danger">Etiqueta <strong><%= numObj%></strong> Não Encontrada!</div>
                                                                    <%}%>

                                                                </div>
                                                            </div>
                                                        </div>
                                                    </li>    
                                                </ul>                       

                                                <%} else {%>
                                                <div class="alert alert-danger"><strong>Nenhum objeto encontrado!</strong></div>
                                                <%}%>

                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
            <form name="frmSRO" id="frmSRO" method="post" action="http://www2.correios.com.br/sistemas/rastreamento/Resultado.cfm" target="_blank">
                <input type="hidden" name="objetos" id="objetos" value="" />
            </form>                                 

            <script type="text/javascript">


                function selectCliente() {
                    $('#idCliente').select2();
                }
                function AllTables() {
                    StartDataTable('dataTables-etqPend');
                    LoadSelect2Script(MakeSelectDataTable('dataTables-etqPend'));
                }

                $(document).ajaxStop(function () {
                    fechaMsg();
                });

                $(document).ready(function () {
                    LoadSelect2Script(selectCliente);
                    $("#data").datepicker({
                        showAnim: 'slideDown',
                        maxDate: new Date(),
                        numberOfMonths: 3,
                        onClose: function (selectedDate) {
                            $("#data2").datepicker("option", "minDate", selectedDate);
                        }
                    });
                    $("#data2").datepicker({
                        showAnim: 'slideDown',
                        maxDate: new Date(),
                        numberOfMonths: 3,
                        onClose: function (selectedDate) {
                            $("#data").datepicker("option", "maxDate", selectedDate);
                        }
                    });

                    $('[id^=detail-]').hide();
                    $('#detail-1').show();
                    $('.toggle').click(function () {
                        $input = $(this);
                        $target = $('#' + $input.attr('data-toggle'));
                        $target.slideToggle();
                    });

                    LoadDataTablesScripts(AllTables);

                });

                function validaForm() {
                    var form = document.form1;
                    if (form.data.value === "") {
                        alert("Preencha a Data Inicial do Periodo!");
                        return false;
                    } else {
                        if (valida_data(form.data) === false) {
                            return false;
                        }
                    }

                    if (form.data2.value === "") {
                        alert("Preencha a Data Final do Periodo!");
                        return false;
                    } else {
                        if (valida_data(form.data2) === false) {
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
                function pesqSro(param) {
                    $('#objetos').val(param);
                    $('#frmSRO').submit();
                }
            </script>

    </body>
</html>
<%}%>