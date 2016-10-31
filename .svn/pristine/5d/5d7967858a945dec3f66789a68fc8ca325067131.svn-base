
<%@page import="Entidade.empresas"%>
<%@page import="Entidade.Endereco"%>
<%@page import="Emporium.Controle.ContrImpressaoPLP"%>
<%@page import="Entidade.DadosPLP"%>
<%@page import="Entidade.DadosPLP"%>
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

<%
    String nomeBD = (String) session.getAttribute("nomeBD");
    if (nomeBD == null) {
        response.sendRedirect("../../index.jsp?msg=Sua sessao expirou! Para voltar ao Portal faça seu login novamente!");
    } else {

        empresas agencia = (empresas) session.getAttribute("agencia");
        int idCli = Integer.parseInt(String.valueOf(session.getAttribute("idCliente")));
        ArrayList<Integer> dps = (ArrayList<Integer>) session.getAttribute("departamentos");
        int idUser = (Integer) session.getAttribute("idUsuarioEmp");
        ArrayList<ClientesDeptos> listaDep = ContrClienteDeptos.consultaDeptos(idCli, nomeBD);
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
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
            
            function validaForm(){
                abrirTelaEspera();
                var form = document.form1;
                if(form.idPLP.value == ""){
                    fecharTelaEspera();
                    alert("Digite o número da PLP!");
                    return false;
                }
                if(form.numObjeto.value == ""){
                    fecharTelaEspera();
                    alert("Digite o numero de um objeto válido!");
                    return false;
                }
                form.submit();
                return true;
            }         
            
            function Check200(nomeCheck, checado) {  //JS_PORTAL_POSTAL
                var count = 0;
                $("[name='" + nomeCheck + "']").each(function() {
                    count++;
                    if (count <= 200) {
                        if (checado == true) {
                            this.checked = checado;
                        } else {
                            this.checked = checado;
                        }
                    } else {
                        this.checked = false;
                    }
                });
            }
            
            function verificaSelecao(formato) {
                document.getElementById('formato').value = formato;
                var flag = true;
                var qtdSelecionada = 0;
                $("[name='ids']:checked").each(function() {
                    qtdSelecionada++;
                    flag = false;
                });

                if (qtdSelecionada > 200) {
                    alert("Quantidade máxima de impressão de 200 etiquetas de cada vez!");
                    return false;
                }

                if (flag) {
                    alert("Selecione alguma etiqueta para imprimir!");
                    return false;
                }

                if (confirm('Tem certeza que deseja imprimir as etiquetas selecionadas?')) {
                    return true;
                } else {
                    return false;
                }
            }
        </script>

        <title>Portal Postal | Importação de PLP</title>

    </head>
    <body onload="javascript:document.getElementById('nome').focus();">
        <div id="divInteracao" class="esconder" style="top:10%; left:10%; right:10%; bottom:10%;" align="center"><input id="textointeracao" /></div>
        <div id="divProtecao" class="esconder"></div>

        <%@ include file="../../Includes/menu_cliente.jsp" %>
        <%@ include file="../../Includes/telaMsg.jsp" %>

        <div id="divPrincipal" align="center">
            <div id="container">
                <div id="conteudo">

                    <div id="titulo1">Importação de PLP</div>

                    <form method="post" action="../../ServImportaImpressaoPLP" id="form1" name="form1" >
                        <ul class="ul_formulario" style="width: 1140px;">
                            <li class="titulo">
                                <dd style="font-size: 12px;">PREENCHA OS DADOS ABAIXO</dd>
                            </li>
                            <li>
                                <dd>
                                    <label>NÚMERO DA PLP</label>
                                    <input style="width:160px;" type="text" name="idPLP" id="idPLP" />
                                </dd>
                            </li>
                            <li>
                                <dd>
                                    <label>NÚMERO DE OBJETO DA PLP <span style="color:gray; font-size: 9px;"> Ex.: PB012321015BR</span></label>
                                    <input style="width:160px;" type="text" name="numObjeto" id="numObjeto" />
                                </dd>
                            </li>
                            <li <%if(listaDep==null || listaDep.size()==0){%>class="esconder"<%}%>>
                                <dd>
                                    <label>Escolha um Departamento</label>                                    
                                    <select  style="width: 300px;" name="departamento">
                                        <option value="0">NENHUM DEPARTAMENTO</option>
                                        <%
                                            for (int i = 0; i < listaDep.size(); i++) {
                                                ClientesDeptos cd = listaDep.get(i);
                                                String cartao = "0";
                                                if (cd.getCartaoPostagem() != null) {
                                                    cartao = cd.getCartaoPostagem();
                                                }
                                                if (dps.contains(cd.getIdDepartamento())) {
                                        %>
                                        <option value="<%=cd.getIdDepartamento()%>"><%= cd.getNomeDepartamento()%></option>
                                        <%}
                                        }%>
                                    </select>
                                </dd>
                            </li>
                            <li>
                                <dd style="width: 100%;">
                                    <div class="buttons">
                                        <input type="hidden" name="login_sigep" id="login_sigep" value="<%= agencia.getLogin_ws_sigep() %>" />
                                        <input type="hidden" name="senha_sigep" id="senha_sigep" value="<%= agencia.getSenha_ws_sigep() %>" />
                                        <input type="hidden" name="nomeBD" id="nomeBD" value="<%= nomeBD%>" />
                                        <input type="hidden" name="idCliente" value="<%= idCli%>" />
                                        <input type="hidden" name="idUser" value="<%= idUser%>" />
                                        <button type="button" class="positive" onclick="return validaForm();"><img src="../../imagensNew/tick_circle.png" /> IMPORTAR PLP</button>
                                    </div>
                                </dd>
                            </li>
                        </ul>
                    </form>
                   
                    <img width="100%" src="../../imagensNew/linha.jpg"/>


                    <div id="titulo2">Lista de Etiquetas que estão Aguardando Impressão. <i style="font-size: 10px;color: lightcoral;">*Impressão máxima de 200 etiquetas por vez</i></div>
                    <form name="formEtq" action="importa_plp_etq.jsp" method="post">
                        <table cellpadding="0" cellspacing="0" border="0" id="table2" class="tinytable">
                            <thead>
                                <tr>
                                    <th align="center" class="nosort" style="width: 20px; padding-left: 5px;">
                                        <input type=checkbox checked onClick="Check200('ids', this.checked);" title="Marcar/Desmarcar 200 Etiquetas" />
                                        <h3 style="display: none;"></h3>
                                    </th>
                                    <th><h3>Nº do Objeto</h3></th>
                                    <th><h3>Serviço</h3></th>
                                    <th><h3>Destinatário</h3></th>
                                    <th><h3>Endereço</h3></th>
                                    <th><h3>Cidade / UF</h3></th>
                                    <th><h3>CEP</h3></th>
                                    <th><h3>N.F.</h3></th>
                                    <th width="40"><h3>AR</h3></th>
                                </tr>
                            </thead>
                            <tbody>
                                <%
                                    ArrayList<DadosPLP> lista = ContrImpressaoPLP.consultaNaoImpressos(idCli, nomeBD);
                                    for (int i = 0; i < lista.size(); i++) {
                                        DadosPLP plp = lista.get(i);
                                        String numObj = plp.getSro();
                                        if ("avista".equals(numObj)) {
                                            numObj = "- - -";
                                        }
                                        String ar = "SIM";
                                        if (plp.getAr() == 0) {
                                            ar = "NÃO";
                                        }
                                        Endereco des = plp.getEndDestinatario();
                                %>
                                <tr style="cursor:default;">
                                    <td align="center"><input type="checkbox" <%if (i < 200) {%>checked<%}%> name="ids" id="ids" value="<%= plp.getSro()%>" /></td>
                                    <td align="center"><%= numObj%></td>
                                    <td><%= plp.getServico()%></td>
                                    <td><%= des.getNome().toUpperCase()%></td>
                                    <td><%= des.getLogradouro().toUpperCase() %></td>
                                    <td><%= des.getCidade().toUpperCase() + " / " + des.getUf()%></td>
                                    <td><%= des.getCep()%></td>
                                    <td><%= plp.getNotaFiscal()%></td>
                                    <td><%= ar%></td>
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
                                sortcolumn: 1,
                                sortdir: 1,
                                init: true
                            });
                        </script>
                        <%if (lista.size() > 0) {%>
                        <div class="buttons">
                            <input type="hidden" name="formato" id="formato" value="A4" />
                            <button type="submit" class="regular" onClick="return verificaSelecao('A4');"><img src="../../imagensNew/printer.png" /> IMPRIMIR EM FOLHA A4</button>
                            <button style="float: right;" type="submit" class="positive" onClick="return verificaSelecao('ETQ_16x10');"><img src="../../imagensNew/printer.png" /> IMPRIMIR EM ETIQUETAS 16 x 10</button>
                        </div>
                        <%}%>
                    </form>
                    
                                        

                </div>
            </div>
        </div>
    </body>
</html>
<%}%>