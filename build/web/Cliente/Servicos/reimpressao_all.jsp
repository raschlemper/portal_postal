
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
%>
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />

        <script type="text/javascript" src="../../javascript/ajax.js"></script>
        <script type="text/javascript" src="../js/ajax_portal.js"></script>
        <script type="text/javascript" src="../../javascript/mascara.js"></script>

        <link href="../../css/estilo.css" rel="stylesheet" type="text/css" />
        <script type="text/javascript" src="../../javascript/jquery/js/jquery-1.6.2.min.js"></script>

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
                    <img width="100%" src="../../imagensNew/linha.jpg"/>


                    <div id="titulo2">Lista de Etiquetas Geradas/Impressas Hoje</div>
                    
                    <div style="padding:8px 5px; background: white;">
                        <a href="#" onclick="document.formEXP.action='../AjaxPages/xls_etq_geradas.jsp'; document.formEXP.submit();"><img class="link_img" src="../../imagensNew/excel.png" /> EXPORTAR .XLS</a>
                        <form name="formEXP" action="#">
                            <input type="hidden" name="idCli" value="<%= idCli%>" />
                            <input type="hidden" name="nomeBD" value="<%= nomeBD%>" />
                        </form>
                    </div>
                    <form action="reimpressao_etq.jsp" method="post">
                        <table cellpadding="0" cellspacing="0" border="0" id="table2" class="tinytable">
                            <thead>
                                <tr>
                                    <th class="nosort" width="40"><h3>Marcar</h3></th>
                                    <th><h3>Nº do Objeto</h3></th>
                                    <th><h3>Serviço</h3></th>
                                    <th><h3>Destinatário</h3></th>
                                    <th><h3>Endereço</h3></th>
                                    <th><h3>Cidade / UF</h3></th>
                                    <th><h3>CEP</h3></th>
                                    <th><h3>N.F.</h3></th>
                                    <th><h3>Data Impressão</h3></th>
                                    <th width="50"><h3>AR</h3></th>
                                    <th class="nosort" width="60"><h3>Visualizar</h3></th>
                                </tr>
                            </thead>
                            <tbody>
                                <%
                                    ArrayList<PreVenda> lista = ContrPreVenda.consultaVendasReimpressao(idCli, 1, 0, nivel, idUser, true, "NULL", "NULL", nomeBD);
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
                                %>
                                <tr style="cursor:default;">
                                    <td align="center"><input type="checkbox" name="ids" value="<%= des.getId()%>" /></td>
                                    <td align="center"><%= numObj%></td>
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
                                <%
                                    ArrayList<PreVenda> lista2 = ContrPreVenda.consultaVendasReimpressao(idCli, 1, 0, nivel, idUser, false, "NULL" , "NULL", nomeBD);
                                    for (int i = 0; i < lista2.size(); i++) {
                                        PreVenda des = lista2.get(i);
                                        String numObj = des.getNumObjeto();
                                        if ("avista".equals(numObj)) {
                                            numObj = "- - -";
                                        }
                                        String ar = "SIM";
                                        if(des.getAviso_recebimento() == 0){
                                         ar = "NÃO";
                                        }
                                %>
                                <tr style="cursor:default;">
                                    <td align="center"><input type="checkbox" name="ids" value="<%= des.getId()%>" /></td>
                                    <td align="center"><%= numObj%></td>
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
                                sortcolumn:8,
                                sortdir:1,
                                init:true
                            });
                        </script>
                        <%if (lista.size() > 0 || lista2.size() > 0) {%>
                        <ul class="ul_formulario" style="padding: 10px 0; margin: 0;width: 1136px;"  >
                            <li>
                                <dd>
                                    <label>Order impressão por:</label>
                                    <select style="width: 220px;" name="ordem" id="ordem">
                                        <option value="d.nome">Nome do Destinatário</option> 
                                        <option value="notaFiscal">Nota Fiscal</option> 
                                        <option value="nomeServico">Serviço</option> 
                                        <option value="nObj">N° de Objeto</option> 
                                    </select>
                                </dd>
                                <dd>
                                    <label>Tamanho da impressão:</label>
                                    <select style="width: 220px;" name="formato" id="formato">
                                        <option value="A4">Folha A4</option> 
                                        <option value="ETQ_16x10">Etiqueta 16cm x 10cm</option> 
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
                        <%}%>
                    </form>
                    

                </div>
            </div>
        </div>
    </body>
</html>
<%}%>