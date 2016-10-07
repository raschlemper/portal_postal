
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
          String dataFim = vDataAtual;
        if (request.getParameter("dataFim") != null) {
            dataFim = request.getParameter("dataFim");
        }
        if (request.getParameter("dataIni") != null) {
            dataAnterior = request.getParameter("dataIni");
        }

        String vDataInicio = Util.FormatarData.DateToBD(dataAnterior);
        String vDataFinal = Util.FormatarData.DateToBD(dataFim);
           ArrayList<PreVenda> lista = ContrPreVenda.consultaVendasReimpressao(idCli, 1, -1, nivel, idUser, true, vDataInicio, vDataFinal, nomeBD);
                                   
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
              function validaData() {
                if ($("#dataIni").val() > $("#dataFim").val()) {
                    alert('Data inicial não pode ser maior que data final');
                } else {
                    $('#pesq').submit();
                }
            }
        </script>

        <title>Portal Postal | Lista de Postagem</title>

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
                    
                    <form id="pesq" action="lista_postagem_gerar.jsp" method="post">
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
                                        <button type="button" class="regular" onclick="validaData();"><img src="../../imagensNew/lupa.png"/> PESQUISAR</button>
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
                        Etiquetas sem Lista de Postagem - [<span style="color: yellow;"><%=lista.size()%> </span>etiqueta(s)]
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
                                    <th><h3>Nº Lista</h3></th>
                                    <th><h3>Serviço</h3></th>
                                    <th><h3>Destinatário</h3></th>
                                    <th><h3>Endereço</h3></th>
                                    <th><h3>Cidade / UF</h3></th>
                                    <th><h3>CEP</h3></th>
                                    <th><h3>N.F.</h3></th>
                                    <th><h3>Data Impressão</h3></th>
                                    <th width="50"><h3>AR</h3></th>
                                    <th class="nosort" width="40"><h3>Ver</h3></th>
                                    <th class="nosort" width="40"><h3>Del</h3></th>
                                </tr>
                            </thead>
                            <tbody>
                                <%
                                    
                                    ArrayList<Integer> dptosSessaoUsuario = (ArrayList<Integer>) session.getAttribute("departamentos");
                                  for (int i = 0; i < lista.size(); i++) {
                                        PreVenda pv = lista.get(i);
                                        String numObj = pv.getNumObjeto();
                                        if ("avista".equals(numObj)) {
                                            numObj = "- - -";
                                        }
                                        String ar = "SIM";
                                        if(pv.getAviso_recebimento() == 0){
                                         ar = "NÃO";
                                        }
                                        String os = "- - -";
                                        if(pv.getIdOs() > 0){
                                            os = "<a href='lista_postagem_print.jsp?idOs="+pv.getIdOs()+"'>"+pv.getIdOs()+"</a>";
                                        }
                                        if(dptosSessaoUsuario.isEmpty() || pv.getIdDepartamento() == 0 || dptosSessaoUsuario.contains(pv.getIdDepartamento())){                                             
                                        
                                %>
                                <tr style="cursor:default;">
                                    <td align="center">
                                        <%if(pv.getIdOs() == 0){%>
                                            <input type="checkbox" name="ids" value="<%= pv.getId()%>" />
                                            <input type="hidden" name="os_<%= pv.getId()%>" value="<%= pv.getIdOs()%>" />
                                        <%}%>
                                    </td>
                                    <td><%= pv.getId() %></td>
                                    <td align="center"><%= numObj%></td>
                                    <td align="center"><%= os %></td>
                                    <td><%= pv.getNomeServico()%></td>
                                    <td><%= pv.getNomeDes()%></td>
                                    <td><%= pv.getEnderecoDes() + ", " + pv.getNumeroDes()%></td>
                                    <td><%= pv.getCidadeDes() + " / " + pv.getUfDes()%></td>
                                    <td><%= pv.getCepDes() %></td>
                                    <td><%= pv.getNotaFiscal() %></td>
                                    <td><%= pv.getDataImpressoFormatada() %></td>
                                    <td><%= ar%></td>
                                    <td align="center"><a onclick="verVenda(<%= pv.getId()%>);" style="cursor:pointer;" ><img src="../../imagensNew/lupa.png" /></a></td>
                                    <td align="center">
                                        <%if(pv.getIdOs() > 0){%>
                                            <img style="cursor: pointer;" src="../../imagensNew/cancel.png" border="0" onClick="removerObjeto(<%= pv.getId()%>, '<%= pv.getIdOs() %>');" />
                                        <%}%>
                                    </td>
                                </tr>
                                <%}}%>
                            </tbody>
                        </table>
                        <script type="text/javascript">
                            
                            function removerObjeto(idVenda, idOS) {
                                if (confirm('Tem certeza que deseja remover este objeto da lista de postagem?')) {
                                    document.getElementById('idVendaDel').value = idVenda;
                                    document.getElementById('idOsDel').value = idOS;                                    
                                    document.formDelOS.submit();
                                } else {
                                    document.getElementById('idVendaDel').value = '';
                                    document.getElementById('idOsDel').value = '';
                                    return false;
                                }
                            }
            
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
                    <form action="../../ServListaPostagemRemoverObj" method="post" name="formDelOS">
                        <input type="hidden" name="nomeBD" value="<%= nomeBD%>" />
                        <input type="hidden" name="idVenda" id="idVendaDel" value="" />
                        <input type="hidden" name="idOs" id="idOsDel" value="" />
                    </form>

                </div>
            </div>
        </div>
    </body>
</html>
<%}%>