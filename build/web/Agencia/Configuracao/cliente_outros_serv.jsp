<%@page import="Controle.ContrClienteContrato"%>
<%@page import="Controle.ContrServicoECT"%>
<%@page import="Entidade.ServicoECT"%>
<%@ page import = "java.util.ArrayList,java.sql.Timestamp, java.util.Date, java.text.SimpleDateFormat"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%
    if (session.getAttribute("usuario") == null) {
        response.sendRedirect("../index.jsp?msgLog=3");
    } else {
        int idNivelDoUsuario = (Integer) session.getAttribute("nivel");
        if (idNivelDoUsuario == 3) {
            response.sendRedirect("../Importacao/imp_movimento.jsp?msg=Acesso Negado!");
        }
        String nomeBD = (String) session.getAttribute("empresa");
        int idClienteInc = Integer.parseInt(request.getParameter("idCliente"));    
        Entidade.Clientes cliInc = Controle.contrCliente.consultaClienteById(idClienteInc, nomeBD);    
%>
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />

        <script type="text/javascript" src="../../javascript/ajax.js"></script>
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
            function trocaServ(listRemove, listAdiciona) {
                var selIndex = document.getElementById(listRemove).selectedIndex;
                var idServ = document.getElementById(listRemove).options[selIndex].value;
                var nomeServ = document.getElementById(listRemove).options[selIndex].text;

                document.getElementById(listRemove).remove(selIndex);

                var novaOpcao = new Option(nomeServ, idServ);
                document.getElementById(listAdiciona).options[document.getElementById(listAdiciona).length] = novaOpcao;

                OrdenarLista(listRemove);
                OrdenarLista(listAdiciona);

                document.getElementById(listRemove).focus();
                if (document.getElementById(listRemove).length == selIndex) {
                    document.getElementById(listRemove).selectedIndex = selIndex - 1;
                } else {
                    document.getElementById(listRemove).selectedIndex = selIndex;
                }
            }

            function OrdenarLista(combo) {
                var lb = document.getElementById(combo);
                arrTexts = new Array();

                for (i = 0; i < lb.length; i++) {
                    arrTexts[i] = lb.options[i].text + "@" + lb.options[i].value;
                }

                arrTexts.sort();

                for (i = 0; i < lb.length; i++) {
                    var aux = arrTexts[i].split("@");
                    lb.options[i].text = aux[0];
                    lb.options[i].value = aux[1];
                }
            }

            function preencherCampos() {
                var form = document.form1;

                var lb = document.getElementById('servico_2');
                var servicos = "";
                for (i = 0; i < lb.length; i++) {
                    if (i == 0) {
                        servicos += lb.options[i].value;
                    } else {
                        servicos += "@" + lb.options[i].value;
                    }
                }
                document.getElementById('servicos').value = servicos;

                if (servicos == "") {
                    alert('Selecione algum serviço para o Contrato do Cliente!');
                    return false;
                }                

                form.submit();
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

        </script>

        <title>Portal Postal | Sequências Lógicas</title>

    </head>
    <body>
        <div id="divInteracao" class="esconder" style="top:10%; left:20%; right:20%; bottom:10%;" align="center"><input id="textointeracao" /></div>
        <div id="divProtecao" class="esconder"></div>

        <%@ include file="../../Includes/menu_agencia.jsp" %>
        <%@ include file="../../Includes/telaMsg.jsp" %>

        <div id="divPrincipal" align="center">
            <div id="container">
                <div id="conteudo">

                    <div id="titulo1">Outros Serviços do Cliente <span><%= cliInc.getNomeFantasia()%></span></div>

                    <jsp:include page="cliente_tab_menu.jsp" >
                        <jsp:param name="nomeBDTab" value="<%= nomeBD %>" />
                        <jsp:param name="activeTab" value="3" />
                        <jsp:param name="idClienteTab" value="<%= idClienteInc %>" />
                        <jsp:param name="temContratoTab" value="<%= cliInc.getTemContrato() %>" />
                    </jsp:include>
                    
                    <form name="form1" action="../../ServClienteOutrosServ" method="post">
                        <ul class="ul_formulario">
                            <li class="titulo">
                                <dd><span>ESCOLHA OS OUTROS SERVIÇOS QUE SERVEM PARA ESTE CLIENTE</span></dd>
                            </li>
                            <li id="divServ" style="clear: both;">
                                <dd>
                                    <label>SERVIÇOS DISPONÍVEIS</label>
                                    <select style="width: 220px;" name="servico_1" id="servico_1" size="10">
                                        <%
                                            ArrayList<Integer> listaOutros = ContrClienteContrato.consultaOutrosServicosCliente(idClienteInc, nomeBD);
                                            ArrayList<ServicoECT> listaServ = ContrServicoECT.consultaServicos(0, 1, "OSV");
                                            if(listaServ!=null){
                                                for (int i = 0; i < listaServ.size(); i++) {
                                                    ServicoECT sv = listaServ.get(i);
                                                    if (!listaOutros.contains(sv.getCodECT())) {
                                                        out.println("<option value='" + sv.getCodECT() + ";" + sv.getGrupoServico() + "'>" + sv.getNomeServico() + "</option>");
                                                    }
                                                }
                                            }
                                        %>
                                    </select>
                                    <div id="foo"></div>
                                </dd>
                                <dd>
                                    <div class="buttons2" style="margin: 15px 10px;">
                                        <button onclick="trocaServ('servico_1', 'servico_2');" type="button" class="positive" style="width: 130px;" ><img class="link_img" src="../../imagensNew/plus_circle.png" /> ADICIONAR</button>
                                        <br/><br/>
                                        <button onclick="trocaServ('servico_2', 'servico_1');" type="button" class="negative" style="width: 130px;" ><img class="link_img" src="../../imagensNew/minus_circle.png" /> REMOVER</button>
                                    </div>
                                </dd>
                                <dd>
                                    <label>SERVIÇOS LIBERADOS PARA O CLIENTE</label>
                                    <select style="width: 300px;font-size: 12px;" name="servico_2" id="servico_2" size="10"> 
                                        <%
                                            if (listaServ != null) {
                                                for (int i = 0; i < listaServ.size(); i++) {
                                                    ServicoECT sv = listaServ.get(i);
                                                    if (listaOutros.contains(sv.getCodECT())) {
                                                        out.print("<option value='" + sv.getCodECT() + ";" + sv.getGrupoServico() + "'>" + sv.getNomeServico() + "</option>");
                                                    }
                                                }
                                            }
                                        %>                                
                                    </select>
                                </dd>
                                <dd style="clear:both;margin-top: 15px;"><b style="color: red;"></b></dd>
                            </li>
                            <li>
                                <dd style="width: 100%;">
                                    <div class="buttons">
                                        <input type="hidden" name="idCliente" id="idCliente" value="<%= idClienteInc%>" />
                                        <input type="hidden" name="servicos" id="servicos" value="" />
                                        <button type="button" class="positive" onclick="return preencherCampos();"><img src="../../imagensNew/tick_circle.png" /> SALVAR DADOS</button>
                                    </div>
                                </dd>
                            </li>
                        </ul>
                    </form>
                    <img width="100%" src="../../imagensNew/linha.jpg"/>

                </div>
            </div>
        </div>
    </body>
</html>
<%}%>