
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

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String vDataAtual = sdf.format(new Date());
        String data = vDataAtual;
        if (request.getParameter("dataIni") != null) {
            data = request.getParameter("dataIni");
        }
        String dataBD = Util.FormatarData.DateToBD(data);
        
        int consolidado = -1;
        int impresso = -1;
        String sit = request.getParameter("situacao");
        if(sit!=null){
            String aux[] = sit.split(";");
            impresso = Integer.parseInt(aux[0]);
            consolidado = Integer.parseInt(aux[1]);
        }
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
            
            $(function() {
                $("#dataIni").datepicker({
                    maxDate:'<%= vDataAtual%>',
                    showOn: "button",
                    buttonImage: "../../imagensNew/calendario.png",
                    buttonImageOnly: true,
                    showAnim: "slideDown"
                });
            });
        </script>

        <title>Portal Postal | Reimpressão de Etiquetas</title>

    </head>
    <body onload="javascript:document.getElementById('nome').focus();">
        <div id="divInteracao" class="esconder" style="top:10%; left:10%; right:10%; bottom:10%;" align="center"><input id="textointeracao" /></div>
        <div id="divProtecao" class="esconder"></div>

        <%@ include file="../../Includes/menu_cliente.jsp" %>
        <%@ include file="../../Includes/telaMsg.jsp" %>

        <div id="divPrincipal" align="center">
            <div id="container">
                <div id="conteudo">

                    <div id="titulo1">Relatório de Etiquetas Geradas/Impressas</div>

                    <form action="rel_etiquetas.jsp" method="post">
                        <ul class="ul_formulario" style="width: 1140px;">
                            <li class="titulo">
                                <dd style="font-size: 12px;">PESQUISAR ETIQUETAS</dd>
                            </li>
                            <li>
                                <dd>
                                    <label>DATA:</label>
                                    <input type="text" style="width:60px;" name="dataIni" id="dataIni" value="<%= data%>" maxlength="10" onkeypress="mascara(this,maskData);" />
                                </dd>
                                <dd>
                                    <label>SITUAÇÃO:</label>
                                    <select name="situacao" style="width: 200px;">
                                        <option value="-1;-1">TODAS AS SITUAÇÕES</option>
                                        <option value="0;0">NÃO IMPRESSO</option>
                                        <option value="1;0">NÃO POSTADO</option>
                                        <option value="1;1">POSTADO</option>
                                    </select>
                                </dd>
                            </li>
                            <li>
                                <dd style="width: 500px;">
                                    <div class="buttons">
                                        <button type="submit" class="regular" ><img src="../../imagensNew/lupa.png"/> PESQUISAR ETIQUETAS</button>
                                    </div>
                                </dd>
                            </li>                            
                        </ul>
                    </form>
                    <img width="100%" src="../../imagensNew/linha.jpg"/>


                    <div id="titulo2">Lista de Etiquetas Geradas no Dia <%= data%></div>
                    <form action="reimpressao_etq.jsp" method="post">
                        <table cellpadding="0" cellspacing="0" border="0" id="table2" class="tinytable">
                            <thead>
                                <tr>
                                    <th><h3>Nº do Objeto</h3></th>
                                    <th><h3>Serviço</h3></th>
                                    <th><h3>Destinatário</h3></th>
                                    <th><h3>Endereço</h3></th>
                                    <th><h3>Cidade / UF</h3></th>
                                    <th><h3>STATUS</h3></th>
                                    <th class="nosort" width="60"><h3>Visualizar</h3></th>
                                </tr>
                            </thead>
                            <tbody>
                                <%
                                    ArrayList<PreVenda> lista = ContrPreVenda.consultaVendasRelatorio(idCli, impresso, consolidado, dataBD, nomeBD);
                                    for (int i = 0; i < lista.size(); i++) {
                                        PreVenda des = lista.get(i);
                                        String numObj = des.getNumObjeto();
                                        if ("avista".equals(numObj)) {
                                            numObj = "- - -";
                                        }
                                        String ar = "NÃO IMPRESSO";
                                        if (des.getDataImpresso() != null && des.getDataConsolidado() == null) {
                                            ar = "NÃO POSTADO";
                                        } else if (des.getDataConsolidado() != null) {
                                            ar = "POSTADO";
                                        }
                                %>
                                <tr style="cursor:default;">
                                    <td align="center"><%= numObj%></td>
                                    <td><%= des.getNomeServico()%></td>
                                    <td><%= des.getNomeDes()%></td>
                                    <td><%= des.getEnderecoDes() + ", " + des.getNumeroDes()%></td>
                                    <td><%= des.getCidadeDes() + " / " + des.getUfDes()%></td>
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
                                sortcolumn:0,
                                sortdir:1,
                                init:true
                            });
                        </script>
                    </form>


                </div>
            </div>
        </div>
    </body>
</html>
<%}%>