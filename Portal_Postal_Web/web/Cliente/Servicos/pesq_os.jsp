
<%@page import="Emporium.Controle.ContrOrdemServico"%>
<%@page import="Entidade.OrdemServico"%>
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

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        int idCli = Integer.parseInt(String.valueOf(session.getAttribute("idCliente")));
        String nomeCli = contrCliente.consultaNomeById(idCli, nomeBD);
        int nivel = (Integer) session.getAttribute("nivelUsuarioEmp");
        int idUser = (Integer) session.getAttribute("idUsuarioEmp");
        
        String idOs = "";
        if(request.getParameter("os") != null){
            idOs = request.getParameter("os");
        }
                
        Date dataAtual = new Date();
        String vDataAtual = sdf.format(dataAtual);
        String data = vDataAtual;
        if(request.getParameter("data") != null){
            data = request.getParameter("data");
        }
        ArrayList<OrdemServico> lista = ContrOrdemServico.pesquisaOS(idCli, idOs, data, nomeBD);
%>
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />


        <link href="../../css/estilo.css" rel="stylesheet" type="text/css" />
        <script type="text/javascript" src="../js/ajax_portal.js"></script>
        <script type="text/javascript" src="../../javascript/mascara.js"></script>

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
            
            $(function() {
                $("#data").datepicker({
                    maxDate:'<%= vDataAtual %>',
                    showOn: "button",
                    buttonImage: "../../imagensNew/calendario.png",
                    buttonImageOnly: true,
                    showAnim: "slideDown"
                });
            });
            
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
                    document.getElementById('formato').value = formato;
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
        </script>

        <title>Portal Postal | OS</title>

    </head>
    <body onload="javascript:document.getElementById('nome').focus();">
        <div id="divInteracao" class="esconder" style="top:10%; left:10%; right:10%; bottom:10%;" align="center"><input id="textointeracao" /></div>
        <div id="divProtecao" class="esconder"></div>

        <%@ include file="../../Includes/menu_cliente.jsp" %>
        <%@ include file="../../Includes/telaMsg.jsp" %>

        <div id="divPrincipal" align="center">
            <div id="container">
                <div id="conteudo">

                    <div id="titulo1">Pesquisa de Lista de Postagem</div>

                    <form name="form1" action="pesq_os.jsp" method="post">
                        <ul class="ul_formulario" style="width: 1140px;">
                            <li class="titulo">
                                <dd style="font-size: 12px;">PESQUISAR LISTA DE POSTAGEM</dd>
                            </li>
                            <li>
                                <dd>
                                    <label>Data da Lista</label>
                                    <input type="text" style="width:70px;" name="data" id="data" value="<%= data %>" maxlength="10" onkeypress="mascara(this,maskData);" />
                                </dd>
                                <dd>
                                    <label>Nº da Lista</label>
                                    <input type="text" name="os" id="os" value="<%= idOs %>" maxlength="18" onkeypress="mascara(this, maskNumero);" />
                                </dd>
                            </li>
                            <li>
                                <dd style="width: 500px;">
                                    <div class="buttons">
                                        <button type="button" class="regular" onclick="javascript:document.form1.submit();"><img src="../../imagensNew/lupa.png"/> PESQUISAR OS</button>
                                    </div>
                                </dd>
                            </li>
                        </ul>
                    </form>
                    <img width="100%" src="../../imagensNew/linha.jpg"/>


                    <div id="titulo2">Resultado da Pesquisa</div>     
                    <%if(lista.size()>0){%>
                        <table cellpadding="0" cellspacing="0" border="0" id="table2" class="tinytable">
                            <thead>
                                <tr>
                                    <th><h3>Nº</h3></th>
                                    <th><h3>Data</h3></th>
                                    <th><h3>QTD. de Objetos</h3></th>
                                    <th><h3>Responsável</h3></th>
                                    <th class="nosort" width="120"><h3>Imprimir</h3></th>
                                </tr>
                            </thead>
                            <tbody>
                                <%                                    
                                    for (int i = 0; i < lista.size(); i++) {
                                        OrdemServico os = lista.get(i);
                                %>
                                <tr style="cursor:default;">
                                    <td align="center"><b>OS <%= os.getIdOs() %></b></td>
                                    <td><%= sdf2.format(os.getDataOs()) %></td>
                                    <td><%= os.getQtdObjetos() %> Objetos</td>
                                    <td><%= os.getNomeUsuario() %></td>
                                    <td align="center"><a href="lista_postagem_print.jsp?idOs=<%=os.getIdOs()%>" target="_blank" style="cursor:pointer;" ><img class="link_img" src="../../imagensNew/printer.png" /> Imprimir</a></td>
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
                                sortcolumn:1,
                                sortdir:1,
                                init:true
                            });
                        </script>
                            <%}else{%>
                            <div style="width: 100%;text-align: center;padding: 25px 0; font-weight: bold; font-size: 20px;color: red; background: cornsilk;">Nenhuma OS encontrada pela pesquisa!</div>                            
                            <%}%>

                </div>
            </div>
        </div>
    </body>
</html>
<%}%>