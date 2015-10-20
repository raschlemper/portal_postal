
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
                document.getElementById('formi').action = 'lista_postagem_print.jsp';
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
    <body>
        <div id="divInteracao" class="esconder" style="top:10%; left:10%; right:10%; bottom:10%;" align="center"><input id="textointeracao" /></div>
        <div id="divProtecao" class="esconder"></div>

        <%@ include file="../../Includes/menu_cliente.jsp" %>
        <%@ include file="../../Includes/telaMsg.jsp" %>

        <div id="divPrincipal" align="center">
            <div id="container">
                <div id="conteudo">

                    <div id="titulo1">Gerar Lista de Postagem</div>
                    
                    <form action="lista_postagem_gerar.jsp" method="post">
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
                        Etiquetas sem Lista de Postagem
                    </div>
                    <form name="formi" id="formi" action="ordem_servico.jsp" method="post">
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
                                            os = "<a href='lista_postagem_print.jsp?idOs="+des.getIdOs()+"'>"+des.getIdOs()+"</a>";
                                        }
                                %>
                                <tr style="cursor:default;">
                                    <td align="center">
                                        <%if(des.getIdOs() == 0){%>
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
                        <div class="buttons">            
                            <input type="hidden" name="idOs" id="idOs" value="0" />
                            <button type="submit" class="negative" onClick="return verificaSelecao('OS');"><img src="../../imagensNew/printer.png" /> GERAR LISTA DE POSTAGEM</button>
                        </div>
                        <%}%>
                            
                    </form>

                </div>
            </div>
        </div>
    </body>
</html>
<%}%>