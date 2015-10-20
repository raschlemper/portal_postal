
<%@page import="Controle.contrCliente"%>
<%@page import="Emporium.Controle.ContrPreVenda"%>
<%@page import="Entidade.PreVenda"%>
<%@page import="Controle.ContrClienteDeptos"%>
<%@page import="Entidade.ClientesDeptos"%>
<%@page import="Controle.ContrClienteEtiquetas"%>
<%@page import="Controle.ContrClienteContrato"%>
<%@page import="Controle.contrDestinatario"%>
<%@page import="Entidade.Destinatario"%>
<%@page import="Entidade.SenhaCliente"%>
<%@page import = "java.util.ArrayList,java.sql.Timestamp, java.util.Date, java.text.SimpleDateFormat"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%
    String nomeBD = (String) session.getAttribute("nomeBD");
    if (nomeBD == null) {
        response.sendRedirect("../../index.jsp?msg=Sua sessao expirou! Para voltar ao Portal faça seu login novamente!");
    } else {

        int idCli = Integer.parseInt(String.valueOf(session.getAttribute("idCliente")));
        String nomeCli = contrCliente.consultaNomeById(idCli, nomeBD);
        int nivel = (Integer) session.getAttribute("nivelUsuarioEmp");
        int idUser = (Integer) session.getAttribute("idUsuarioEmp");
                
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String vDataAtual = sdf.format(new Date());
        String dataAnterior = vDataAtual;
        if (request.getParameter("dataFim") != null) {
            vDataAtual = request.getParameter("dataFim");
        }
        if (request.getParameter("dataIni") != null) {
            dataAnterior = request.getParameter("dataIni");
        }

        String vDataInicio = Util.FormatarData.DateToBD(dataAnterior);
        String vDataFinal = Util.FormatarData.DateToBD(vDataAtual);
%>
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />

        <script type="text/javascript" src="../../javascript/ajax.js"></script>
        <script type="text/javascript" src="../js/ajax_portal.js"></script>
        <script type="text/javascript" src="../../javascript/mascara.js"></script>

        <link href="../../css/estilo.css" rel="stylesheet" type="text/css" />        

        <link type="text/css" href="../../javascript/jquery/css/jquery-ui-1.8.16.custom.css" rel="stylesheet" />
        <script type="text/javascript" src="../../javascript/jquery/js/jquery-1.6.2.min.js"></script>
        <script type="text/javascript" src="../../javascript/jquery/js/jquery-ui-1.8.16.custom.min.js"></script>
        <script type="text/javascript" src="../../javascript/jquery/js/jquery.ui.datepicker-pt-BR.js"></script>

        <!-- Menu -->
        <link rel="stylesheet" href="../../javascript/plugins/dropdown/css/style.css" type="text/css" media="screen, projection"/>
        <script type="text/javascript" language="javascript" src="../../javascript/plugins/dropdown/js/jquery.dropdownPlain.js"></script>
        <!--[if lte IE 7]>
        <link rel="stylesheet" type="text/css" href="../../javascript/plugins/dropdown/css/ie.css" media="screen" />
        <![endif]-->
        <!-- Menu -->

        <!-- TableSorter -->
        <link rel="stylesheet" href="../../javascript/plugins/TableSorter/styleSorterV3.css" />
        <script type="text/javascript" src="../../javascript/plugins/TableSorter/scriptSorterV3.js"></script>
        <!-- TableSorter -->

        <script type="text/javascript">
            function chamaDivProtecao(){
                var classe = document.getElementById("divProtecao").className;
                if(classe == "esconder"){
                    document.getElementById("divProtecao").className = "mostrar";
                    document.getElementById("divInteracao").className = "mostrar";
                }else{
                    document.getElementById("divProtecao").className = "esconder";
                    document.getElementById("divInteracao").className = "esconder";
                }
            }
            
            function verificaSelecao(formato){
                if(formato == 'OS'){
                    document.getElementById('formi').action = 'ordem_servico.jsp';
                    var flag = true;
                    $("[name='ids']:checked").each(function(){
                        flag = false;
                    });
                    var flag2 = true;
                    $("[name='ids2']:checked").each(function(){
                        flag2 = false;
                    });
                    if(flag && flag2){
                        alert("Selecione alguma etiqueta para gerar a Ordem de Serviço!");
                        return false;
                    }            
                    if (confirm('Tem certeza que deseja gerar a OS com as etiquetas selecionadas?')){
                        return true;
                    }else{
                        return false;
                    }
                }else{
                    document.getElementById('formi').action = 'reimpressao_etq.jsp';
                    //document.getElementById('formato').value = formato;
                    var flag = true;
                    $("[name='ids']:checked").each(function(){
                        flag = false;
                    });                
                    if(flag){
                        alert("Selecione alguma etiqueta para reimprimir!");
                        return false;
                    }                
                    if (confirm('Tem certeza que deseja reimprimir as etiquetas selecionadas?')){
                        return true;
                    }else{
                        return false;
                    }
                }
            }
            
            
            $(function() {
                $("#dataIni").datepicker({
                    maxDate: '<%= vDataAtual%>',
                    showOn: "button",
                    buttonImage: "../../imagensNew/calendario.png",
                    buttonImageOnly: true,
                    showAnim: "slideDown"
                });
                $("#dataFim").datepicker({
                    maxDate: '<%= vDataAtual%>',
                    showOn: "button",
                    buttonImage: "../../imagensNew/calendario.png",
                    buttonImageOnly: true,
                    showAnim: "slideDown"
                });
            });
        </script>

        <title>Portal Postal | Etiquetas Impressas</title>

    </head>
    <body onload="javascript:document.getElementById('nome').focus();">
        <div id="divInteracao" class="esconder" style="top:10%; left:10%; right:10%; bottom:10%;" align="center"><input id="textointeracao" /></div>
        <div id="divProtecao" class="esconder"></div>

        <%@ include file="../../Includes/menu_cliente.jsp" %>
        <%@ include file="../../Includes/telaMsg.jsp" %>

        <div id="divPrincipal" align="center">
            <div id="container">
                <div id="conteudo">

                    <div id="titulo1">Etiquetas Impressas</div>

                    <ul class="ul_formulario" style="width: 1140px;">
                        <li class="titulo">
                            <dd style="font-size: 12px;">OBSERVAÇÕES</dd>
                        </li>
                        <li>
                            <dd><br/>
                                <b>1) A lista abaixo mostra apenas as etiquetas que foram geradas e impressas hoje.</b><br/><br/>
                                <b>2) Para reimprimir as etiquetas selecione quais você de seja reimprimir.</b><br/><br/>
                                <b style="color: red;">3) ATENÇÃO! Cada etiqueta é unica, tome cuidado para não utilizar a mesma etiqueta em dois objetos distintos.</b>
                            </dd>
                        </li>
                    </ul>
                    
                    <form action="reimpressao.jsp" method="post">
                        <ul class="ul_formulario" >
                            <li class="titulo"><dd><span>SELECIONE O PERIODO</span></dd></li>
                            <li>
                                <dd>
                                    <label>Periodo de Data</label>
                                    <input type="text" style="width:60px;" name="dataIni" id="dataIni" value="<%= dataAnterior%>" maxlength="10" onkeypress="mascara(this, maskData);" />
                                    até
                                    <input type="text" style="width:60px;" name="dataFim" id="dataFim" value="<%=vDataAtual%>" maxlength="10" onkeypress="mascara(this, maskData);" />
                                </dd>
                            </li>
                            <li>
                                <dd>
                                    <div class="buttons">
                                        <button type="submit" class="regular"><img src="../../imagensNew/lupa.png"/> PESQUISAR</button>
                                    </div>
                                </dd>
                            </li>
                        </ul>
                    </form>
                    <img width="100%" src="../../imagensNew/linha.jpg"/>

                    <style>
                        .barraArqTable{float: right; margin-top: 2px;}
                        .barraArqTable a{background: #1571d7; font-weight: normal; border: 1px solid #297edc; border-bottom: none; font-size: 12px; color: whitesmoke; padding: 3px 5px 6px 5px; margin-left: 5px;}
                        .barraArqTable a:hover{background: #2d89ef; border: 1px solid white; border-bottom: none; color: white;}
                    </style>
                    <div id="titulo2">                        
                        Lista de Etiquetas Geradas/Impressas Hoje                    
                        <div class="barraArqTable">
                            <a href="#" onclick="document.formEXP.action='../AjaxPages/xls_etq_geradas.jsp'; document.formEXP.submit();">
                                <img class="link_img" src="../../imagensNew/excel.png" />EXPORTAR COMO .XLS
                            </a>
                            <a href="#" onclick="document.formEXP.action='../AjaxPages/csv_etq_geradas.jsp'; document.formEXP.submit();">
                                <img class="link_img" src="../../imagensNew/csv.png" />EXPORTAR COMO .CSV
                            </a>
                            <form name="formEXP" action="#">
                                <input type="hidden" name="idCli" value="<%= idCli%>" />
                                <input type="hidden" name="nomeBD" value="<%= nomeBD%>" />
                                <input type="hidden" name="dataIni" value="<%= vDataInicio %>" />
                                <input type="hidden" name="dataFim" value="<%= vDataFinal %>" />
                            </form>
                        </div>
                    </div>
                    <form name="formi" id="formi" action="reimpressao_etq.jsp" method="post">
                        <table cellpadding="0" cellspacing="0" border="0" id="table2" class="tinytable">
                            <thead>
                                <tr>
                                    <th align="center" class="nosort" style="width: 20px; padding-left: 5px;">
                                        <input type=checkbox onClick="CheckAll('ids', this.checked);" title="Marcar/Desmarcar Todos" />
                                        <h3 style="display: none;"></h3>
                                    </th>
                                    <th><h3>Nº PP</h3></th>
                                    <th><h3>Nº do Objeto</h3></th>
                                    <th><h3>Nº OS</h3></th>
                                    <th><h3>Serviço</h3></th>
                                    <th><h3>Destinatário</h3></th>
                                    <th><h3>Endereço</h3></th>
                                    <th><h3>Cidade / UF</h3></th>
                                    <th><h3>CEP</h3></th>
                                    <th><h3>N.F.</h3></th>
                                    <th><h3>Data Impressão</h3></th>
                                    <th width="50"><h3>AR</h3></th>
                                    <th class="nosort" width="60"><h3>Ver</h3></th>
                                </tr>
                            </thead>
                            <tbody>
                                <%
                                    ArrayList<PreVenda> lista = ContrPreVenda.consultaVendasReimpressao(idCli, 1, -1, nivel, idUser, true, vDataInicio, vDataFinal, nomeBD);
                                    for (int i = 0; i < lista.size(); i++) {
                                        PreVenda des = lista.get(i);
                                        String numObj = des.getNumObjeto();
                                        if ("avista".equals(numObj)) {
                                            numObj = "- - -";
                                        }
                                        String ar = "SIM";
                                        if(des.getAviso_recebimento() == 0){
                                         ar = "NÃO";
                                        }
                                        String os = "- - -";
                                        if(des.getIdOs() > 0){
                                            os = "<a href='ordem_servico.jsp?idOs="+des.getIdOs()+"'>"+des.getIdOs()+"</a>";
                                        }
                                %>
                                <tr style="cursor:default;">
                                    <td align="center">
                                        <%if(des.getUserConsolidado() == 0 && vDataAtual.equals(sdf.format(des.getDataImpresso()))){%>
                                            <input type="checkbox" name="ids" value="<%= des.getId()%>" />
                                            <input type="hidden" name="os_<%= des.getId()%>" value="<%= des.getIdOs()%>" />
                                        <%}%>
                                    </td>
                                    <td><%= des.getId() %></td>
                                    <td align="center"><%= numObj%></td>
                                    <td align="center"><%= os %></td>
                                    <td><%= des.getNomeServico()%></td>
                                    <td><%= des.getNomeDes()%></td>
                                    <td><%= des.getEnderecoDes() + ", " + des.getNumeroDes()%></td>
                                    <td><%= des.getCidadeDes() + " / " + des.getUfDes()%></td>
                                    <td><%= des.getCepDes() %></td>
                                    <td><%= des.getNotaFiscal() %></td>
                                    <td><%= des.getDataImpressoFormatada() %></td>
                                    <td><%= ar%></td>
                                    <td align="center"><a onclick="verVenda(<%= des.getId()%>);" style="cursor:pointer;" ><img src="../../imagensNew/lupa.png" /></a></td>
                                </tr>
                                <%}%>
                            </tbody>
                        </table>
                        <script type="text/javascript">
                            var sorter2 = new TINY.table.sorter('sorter2','table2',{
                                headclass:'head',
                                ascclass:'asc',
                                descclass:'desc',
                                evenclass:'evenrow',
                                oddclass:'oddrow',
                                evenselclass:'evenselected',
                                oddselclass:'oddselected',
                                paginate:false,
                                hoverid:'selectedrowDefault',
                                sortcolumn:10,
                                sortdir:1,
                                init:true
                            });
                        </script>
                        <%if (lista.size() > 0) {%>
                        <ul class="ul_formulario" style="padding: 10px 0; margin: 0;width: 1136px;"  >
                            <li>
                                <dd>
                                    <label>Order impressão por:</label>
                                    <select style="width: 220px;" name="ordem" id="ordem">
                                        <option value="id">Ordem de Geração</option> 
                                        <option value="d.nome">Nome do Destinatário</option> 
                                        <option value="notaFiscal">Nota Fiscal</option> 
                                        <option value="nomeServico">Serviço</option> 
                                        <option value="nObj">N° de Objeto</option> 
                                        <option value="d.cpf_cnpj">CPF/CNPJ</option> 
                                    </select>
                                </dd>
                                <dd>
                                    <label>Tamanho da impressão:</label>
                                    <select style="width: 220px;" name="formato" id="formato">
                                        <option value="A4">Folha A4</option> 
                                        <option value="ETQ_16x10">Etiqueta 16cm x 10cm</option> 
                                        <%--<option value="PIMACO_6288_1">PIMACO 6288 - Inicio 1/4</option> 
                                        <option value="PIMACO_6288_2">PIMACO 6288 - Inicio 2/4</option> 
                                        <option value="PIMACO_6288_3">PIMACO 6288 - Inicio 3/4</option> 
                                        <option value="PIMACO_6288_4">PIMACO 6288 - Inicio 4/4</option> --%>
                                    </select>
                                </dd>
                            </li>
                            <li>
                                <dd>
                                    <div class="buttons">
                                        <button type="submit" class="regular" onClick="return verificaSelecao('A4');"><img src="../../imagensNew/printer.png" /> REIMPRIMIR ETIQUETAS SELECIONADAS</button>
                                    </div>                                    
                                </dd>
                            </li>
                        </ul>
                        <%--
                        <div class="buttons">
                            <input type="hidden" name="formato" id="formato" value="A4" />
                            <button type="submit" class="regular" onClick="return verificaSelecao('A4');"><img src="../../imagensNew/printer.png" /> REIMPRIMIR EM FOLHA A4</button>                            
                            <b style="margin-right: 6px;"> | </b>                            
                            <button type="submit" class="positive" onClick="return verificaSelecao('ETQ_16x10');"><img src="../../imagensNew/printer.png" /> IMPRIMIR EM ETIQUETAS 16 x 10</button>                              
                            <b style="margin-right: 6px;"> | </b>               
                            <input type="hidden" name="idOs" id="idOs" value="0" />
                            <button type="submit" class="negative" onClick="return verificaSelecao('OS');"><img src="../../imagensNew/printer.png" /> GERAR ORDEM DE SERVIÇO</button>
                        </div>--%>
                        <%}%>
                            
                    </form>

                </div>
            </div>
        </div>
    </body>
</html>
<%}%>