
<%@page import="Util.FormataString"%>
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
        response.sendRedirect("../../index.jsp?msg=Sua sessao expirou! Para voltar ao Portal fa�a seu login novamente!");
    } else {

        Clientes cli = (Clientes) session.getAttribute("cliente");
        int idCliente = cli.getCodigo();
        int nivel = (Integer) session.getAttribute("nivelUsuarioEmp");
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date dataAtual = new Date();

        String vDataAtual = request.getParameter("dataFinal");
        if (vDataAtual == null) {
            vDataAtual = sdf.format(dataAtual);
        }
        String dataOntem = request.getParameter("dataInicial");

        if (dataOntem == null) {
            dataOntem = Util.SomaData.SomarDiasDatas(dataAtual, -10);
        }
        String dataInicioCalendario = Util.SomaData.SomarDiasDatas(dataAtual, -180);
        
        String dep = request.getParameter("dep"); 
        String idDeptos = "0"; 
        ArrayList<ClientesDeptos> listaDep = ContrClienteDeptos.consultaDeptos(idCliente, nomeBD);
        
        if(dep ==  null || dep.split(";")[0].trim().equals("0")){
             if (listaDep != null && listaDep.size() > 0) {
            ArrayList<Integer> dpsUser = (ArrayList<Integer>) session.getAttribute("departamentos");
            for (int i = 0; i < listaDep.size(); i++) {
                ClientesDeptos cd = listaDep.get(i);
                if (dpsUser.contains(cd.getIdDepartamento()) || nivel == 1) {
                    String depto = FormataString.removeAccentsToUpper(cd.getNomeDepartamento());
                    if (depto.length() > 20) {
                        depto = depto.substring(0, 20);
                    }
                    idDeptos += "," + cd.getIdDepartamento();
                }
            }
        }
        }else{
            idDeptos = dep.split(";")[0].trim();
        }
       
       
       
        //ArrayList<LogisticaReversa> lista = ContrLogisticaReversa.consultaReversasByCliente(cli.getCodigo(), nomeBD);

        ArrayList<LogisticaReversa> lista = ContrLogisticaReversa.pesqReversas(cli.getCodigo(), nomeBD, Util.FormatarData.DateToBD(dataOntem), Util.FormatarData.DateToBD(vDataAtual), idDeptos);

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


            function validaDataSint() {
                var data1 = $("#dataIni").val();
                var data2 = $("#dataFim").val();
                var dep = $("#departamento").val();
                //console.log(data1);
                //console.log(data2);
                var nova_data1 = parseInt(data1.split("/")[2].toString() + data1.split("/")[1].toString() + data1.split("/")[0].toString());
                var nova_data2 = parseInt(data2.split("/")[2].toString() + data2.split("/")[1].toString() + data2.split("/")[0].toString());
                //console.log(nova_data1);
                //console.log(nova_data2);
                if (nova_data2 < nova_data1) {
                    alert('Data inicial n�o pode ser maior que data final');
                    return false;
                } else {
                    //atualiza pagina com parametros

                    window.location.replace("lista_reversa.jsp?dataInicial=" + data1 + "&dataFinal=" + data2 + "&dep=" + dep);

                    // return true;
                }
            }
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

        <title>Portal Postal | Autoriza��es Geradas</title>

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

                    <div id="titulo1">Autoriza��es Geradas</div>

                    <ul class="ul_formulario">
                        <li class="titulo"><dd><span>MONTE A SUA PESQUISA</span></dd></li>
                        <li>
                            <dd>
                                <label>Periodo de Data de Gera��o</label>
                                <input type="text" style="width:60px;" name="dataIni" id="dataIni" value="<%= dataOntem%>" maxlength="10" onkeypress="mascara(this, maskData);" />
                                at�
                                <input type="text" style="width:60px;" name="dataFim" id="dataFim" value="<%=vDataAtual%>" maxlength="10" onkeypress="mascara(this, maskData);" />
                            </dd>
                            <dd>
                                <label>Departamento</label>
                                <select style="width: 145px;" name="departamento" id="departamento">
                                    <option value="0">-- TODOS --</option>
                                    <%

                                        if (listaDep != null && listaDep.size() > 0) {
                                            ArrayList<Integer> dpsUser = (ArrayList<Integer>) session.getAttribute("departamentos");
                                            for (int i = 0; i < listaDep.size(); i++) {
                                                ClientesDeptos cd = listaDep.get(i);
                                                if (dpsUser.contains(cd.getIdDepartamento()) || nivel == 1) {
                                                    String depto = FormataString.removeAccentsToUpper(cd.getNomeDepartamento());
                                                    if (depto.length() > 20) {
                                                        depto = depto.substring(0, 20);
                                                    }
                                    %>
                                    <option value="<%= cd.getIdDepartamento() + ";" + depto%>"><%= cd.getNomeDepartamento()%></option>
                                    <%}
                                            }
                                        }%>
                                </select>
                            </dd>
                        </li>
                        <li>
                            <dd style="width: 650px;">
                                <div class="buttons">
                                    <button type="button" class="regular" onclick="validaDataSint();
                                            "><img src="../../imagensNew/lupa.png"/> PESQUISAR</button>
                                    <%--<button type="button" class="negative" onclick="teste();"><img src="../../imagensNew/broom.png"/> LIMPAR CAMPOS</button>--%>
                                </div>
                            </dd>
                            <dd class="buttons" style="width: 250px; float: right;">
                                <form name="formAtualiza" action="../../ServReversaAtualizar" method="post">                        
                                    <input type="hidden" name="nomeBD" value="<%= nomeBD%>" />
                                    <input type="hidden" name="usuario" value="<%= cli.getLogin_reversa()%>" />
                                    <input type="hidden" name="senha" value="<%= cli.getSenha_reversa()%>" />
                                    <input type="hidden" name="codAdm" value="<%= cli.getCodAdministrativo()%>" />
                                    <input type="hidden" name="idCli" value="<%= cli.getCodigo()%>" />
                                    <button type="submit" onclick="abrirTelaEspera();" class="regular"><img src="../../imagensNew/refresh.png"/><span style="color: red">ATUALIZAR STATUS DA LISTA</span></button>
                                </form>
                            </dd>
                        </li>
                    </ul>

                    <br/><br/>

                    <div id="titulo2">                        
                        Lista de Autoriza��es Geradas
                    </div>
                    <table cellpadding="0" cellspacing="0" border="0" id="table2" class="tinytable">
                        <thead>
                            <tr>
                                <th><h3>N�</h3></th>
                                <th><h3>Depto</h3></th>
                                <th><h3>Cart�o</h3></th>
                                <th><h3>Qtd.</h3></th>
                                <th><h3>N� do Objeto</h3></th>
                                <th width="50"><h3>AR</h3></th>
                                <th width="50"><h3>VD</h3></th>
                                <th><h3>Nome</h3></th>
                                <th><h3>Cidade / UF</h3></th>
                                <th><h3>CEP</h3></th>
                                <th><h3>Data Gera��o</h3></th>
                                <th><h3>Status</h3></th>
                                <th class="nosort" width="60"><h3>Ver</h3></th>
                                <th class="nosort" width="60"><h3>Cancelar</h3></th>
                            </tr>
                        </thead>
                        <tbody>
                            <%
                                for (LogisticaReversa l : lista) {
                                    String ar = "N�O";
                                    if (l.getAr() == 1) {
                                        ar = "SIM";
                                    }
                                    String obj = "- - -";
                                    if (!l.getNumObjeto().equals("")) {
                                        obj = l.getNumObjeto();
                                    }
                                    String status = "CANCELADO";
                                    if (l.getCancelado() == 0) {
                                        status = l.getDescricaoStatus();
                                    }
                            %>
                            <tr style="cursor:default;">
                                <td><%= l.getCod_ap()%></td>
                                <td><%= l.getNomeDepto()%></td>
                                <td><%= l.getCartao()%></td>
                                <td align="center"><%= l.getQtdObjeto()%></td>
                                <td align="center"><%= obj%></td>
                                <td align="center"><%= ar%></td>
                                <td>R$ <%= l.getVd()%></td>
                                <td><%= l.getNome_rem()%></td>
                                <td><%= l.getCidade_rem() + "/" + l.getUf_rem()%></td>
                                <td><%= l.getCep_rem()%></td>
                                <td><%= l.getDataSolicitacao()%></td>
                                <td><%= status%></td>
                                <td align="center"><a onclick="verReversa(<%= l.getId()%>);" style="cursor:pointer;" ><img src="../../imagensNew/lupa.png" /></a></td>
                                <td align="center"><%if (l.getCancelado() == 0) {%><a onclick="excluirRev(<%= l.getId()%>, <%= l.getCod_ap()%>);" style="cursor:pointer;" ><img src="../../imagensNew/cancel.png" /></a><%}%></td>
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