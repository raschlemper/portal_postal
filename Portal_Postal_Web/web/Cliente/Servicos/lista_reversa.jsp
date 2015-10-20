
<%@page import="Emporium.Controle.ContrLogisticaReversa"%>
<%@page import="Entidade.LogisticaReversa"%>
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

        Clientes cli = (Clientes) session.getAttribute("cliente");

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
            function chamaDivProtecao() {
                var classe = document.getElementById("divProtecao").className;
                if (classe == "esconder") {
                    document.getElementById("divProtecao").className = "mostrar";
                    document.getElementById("divInteracao").className = "mostrar";
                } else {
                    document.getElementById("divProtecao").className = "esconder";
                    document.getElementById("divInteracao").className = "esconder";
                }
            }

            function excluirRev(id, codap) {
                if (confirm('Tem certeza que deseja excluir?')) {
                    document.getElementById('idRev').value = id;
                    document.getElementById('codAP').value = codap;
                    document.formDel.submit();
                } else {
                    document.getElementById('idRev').value = '';
                    document.getElementById('codAP').value = '';
                    return false;
                }
            }
        </script>

        <title>Portal Postal | Autorizações Geradas</title>

                    <style>
                        .barraArqTable{float: right; margin-top: 2px;}
                        .barraArqTable a{background: #1571d7; font-weight: normal; border: 1px solid #297edc; border-bottom: none; font-size: 12px; color: whitesmoke; padding: 3px 5px 6px 5px; margin-left: 5px;}
                        .barraArqTable a:hover{background: #2d89ef; border: 1px solid white; border-bottom: none; color: white;}
                    </style>
    </head>
    <body onload="javascript:document.getElementById('nome').focus();">
        <div id="divInteracao" class="esconder" style="top:10%; left:25%; right:25%; bottom:10%;" align="center"><input id="textointeracao" /></div>
        <div id="divProtecao" class="esconder"></div>

        <%@ include file="../../Includes/menu_cliente.jsp" %>
        <%@ include file="../../Includes/telaMsg.jsp" %>

        <div id="divPrincipal" align="center">
            <div id="container">
                <div id="conteudo">

                    <div id="titulo1">Autorizações Geradas</div>

                    <div class="buttons">
                        <form name="formAtualiza" action="../../ServReversaAtualizar" method="post">                        
                            <input type="hidden" name="nomeBD" value="<%= nomeBD%>" />
                            <input type="hidden" name="usuario" value="<%= cli.getLogin_reversa() %>" />
                            <input type="hidden" name="senha" value="<%= cli.getSenha_reversa() %>" />
                            <input type="hidden" name="codAdm" value="<%= cli.getCodAdministrativo() %>" />
                            <input type="hidden" name="idCli" value="<%= cli.getCodigo() %>" />
                            <button type="submit" onclick="abrirTelaEspera();" class="regular"><img src="../../imagensNew/refresh.png"/> ATUALIZAR STATUS DA LISTA</button>
                        </form>
                    </div>
                            <br/><br/>

                    <div id="titulo2">                        
                        Lista de Autorizações Geradas
                    </div>
                    <table cellpadding="0" cellspacing="0" border="0" id="table2" class="tinytable">
                        <thead>
                            <tr>
                                <th><h3>Nº Autorização</h3></th>
                                <th><h3>Qtd. de Objeto</h3></th>
                                <th><h3>Nº do Objeto</h3></th>
                                <th width="50"><h3>AR</h3></th>
                                <th width="50"><h3>VD</h3></th>
                                <th><h3>Nome</h3></th>
                                <th><h3>Cidade / UF</h3></th>
                                <th><h3>CEP</h3></th>
                                <th><h3>Data Solicitação</h3></th>
                                <th><h3>Status</h3></th>
                                <th class="nosort" width="60"><h3>Ver</h3></th>
                                <th class="nosort" width="60"><h3>Cancelar</h3></th>
                            </tr>
                        </thead>
                        <tbody>
                            <%                                    
                            ArrayList<LogisticaReversa> lista = ContrLogisticaReversa.consultaReversasByCliente(cli.getCodigo(), nomeBD);
                                for (LogisticaReversa l : lista) {
                                    String ar = "NÃO";
                                    if (l.getAr() == 1) {
                                        ar = "SIM";
                                    }
                                    String obj = "- - -";
                                    if (!l.getNumObjeto().equals("")) {
                                        obj = l.getNumObjeto();
                                    }
                                    String status = "CANCELADO";
                                    if(l.getCancelado() == 0){
                                        status = l.getDescricaoStatus();
                                    }
                            %>
                            <tr style="cursor:default;">
                                <td><%= l.getCod_ap()%></td>
                                <td align="center"><%= l.getQtdObjeto() %></td>
                                <td align="center"><%= obj%></td>
                                <td align="center"><%= ar%></td>
                                <td>R$ <%= l.getVd()%></td>
                                <td><%= l.getNome_rem()%></td>
                                <td><%= l.getCidade_rem() + "/" + l.getUf_rem()%></td>
                                <td><%= l.getCep_rem()%></td>
                                <td><%= l.getDataSolicitacao()%></td>
                                <td><%= status %></td>
                                <td align="center"><a onclick="verReversa(<%= l.getId()%>);" style="cursor:pointer;" ><img src="../../imagensNew/lupa.png" /></a></td>
                                <td align="center"><%if(l.getCancelado()==0){%><a onclick="excluirRev(<%= l.getId()%>, <%= l.getCod_ap()%>);" style="cursor:pointer;" ><img src="../../imagensNew/cancel.png" /></a><%}%></td>
                            </tr>
                            <%}%>
                        </tbody>
                    </table>
                    <script type="text/javascript">
                        var sorter2 = new TINY.table.sorter('sorter2', 'table2', {
                            headclass: 'head',
                            ascclass: 'asc',
                            descclass: 'desc',
                            evenclass: 'evenrow',
                            oddclass: 'oddrow',
                            evenselclass: 'evenselected',
                            oddselclass: 'oddselected',
                            paginate: false,
                            hoverid: 'selectedrowDefault',
                            sortcolumn: 0,
                            sortdir: 1,
                            init: true
                        });
                    </script>
                    <form action="../../ServReversaExcluir" method="post" name="formDel">
                        <input type="hidden" name="nomeBD" value="<%= nomeBD%>" />
                        <input type="hidden" name="codAdm" value="<%= cli.getCodAdministrativo()%>" />
                        <input type="hidden" name="loginRev" value="<%= cli.getLogin_reversa()%>" />
                        <input type="hidden" name="senhaRev" value="<%= cli.getSenha_reversa()%>" />
                        <input type="hidden" name="codAP" id="codAP" value="" />
                        <input type="hidden" name="idRev" id="idRev" value="" />
                    </form>

                </div>
            </div>
        </div>
    </body>
</html>
<%}%>