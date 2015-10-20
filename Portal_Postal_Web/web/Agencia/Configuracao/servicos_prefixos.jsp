
<%@page import="Entidade.ServicoPrefixo"%>
<%@page import="Controle.ContrServicoPrefixo"%>
<%@page import="Entidade.ServicoECT"%>
<%@page import="Controle.ContrServicoECT"%>
<%@page import="Controle.ContrClienteContrato"%>
<%@ page import = "java.util.ArrayList,java.sql.Timestamp, java.util.Date, java.text.SimpleDateFormat"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%
    if (session.getAttribute("usuario") == null) {
        response.sendRedirect("../index.jsp?msgLog=3");
    } else {

        int idNivelDoUsuario = (Integer) session.getAttribute("nivel");
        if (idNivelDoUsuario != 1) {
            response.sendRedirect("../jsp/imp_movimento.jsp?msg=Acesso Negado!");
        }

        String nomeBD = (String) session.getAttribute("empresa");
        int idEmpresa = (Integer) session.getAttribute("idEmpresa");

        String servicosel = request.getParameter("servicosel");

%>
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />

        <script type="text/javascript" src="../../javascript/ajax.js"></script>
        <script type="text/javascript" src="../../javascript/mascara.js"></script>

        <!-- TableSorter -->
        <link rel="stylesheet" href="../../javascript/plugins/TableSorter/styleSorterV3.css" />
        <script type="text/javascript" src="../../javascript/plugins/TableSorter/scriptSorterV3.js"></script>
        <!-- TableSorter -->

        <link href="../../css/estilo.css" rel="stylesheet" type="text/css" />

        <link rel="stylesheet" href="../../javascript/plugins/dropdown/css/style.css" type="text/css" media="screen, projection"/>
        <!--[if lte IE 7]>
        <link rel="stylesheet" type="text/css" href="../../javascript/plugins/dropdown/css/ie.css" media="screen" />
        <![endif]-->
        <script type="text/javascript" language="javascript" src="../../javascript/plugins/dropdown/js/jquery.dropdownPlain.js"></script>

        <script type="text/javascript">

            function addRow() {
                var cont = document.getElementById('contador').value;
                var newCont = parseInt(cont) + 1;

                var prefixo = document.getElementById('prefixo').value.toUpperCase();
                var letters = /^[A-Za-z]+$/;
                if (prefixo == '' || prefixo.length != 2 || !prefixo.match(letters)) {
                    alert('O Prefixo deve ter 2 Digitos!<br/>Deve conter apenas letras!');
                    return false;
                }

                for (var i = 1; i <= cont; i++) {
                    if (document.getElementById('prefixo' + i).value == prefixo) {
                        alert('Este Prefixo já está inserido na tabela!');
                        return false;
                    }
                }

                var fat = '';
                if (document.getElementById('a').checked) {
                    fat = 'checked'
                }

                var vis = '';
                if (document.getElementById('b').checked) {
                    vis = 'checked'
                }

                if (fat == '' && vis == '') {
                    alert('Selecione se o Prefixo é para serviço A VISTA, A FATURAR ou AMBOS!');
                    return false;
                }

                var nrLinha = document.getElementById('table').rows.length;
                var linha = document.getElementById('table').insertRow(nrLinha);

                var cell0 = linha.insertCell(0)
                cell0.innerHTML = "<input style='font-size: 13px;font-weight: bold;text-align: center;' type='input' id='prefixo" + newCont + "' name='prefixo" + newCont + "' size='5' readonly value='" + prefixo + "' />";
                cell0.align = "center";
                var cell1 = linha.insertCell(1)
                cell1.innerHTML = "<input type='checkbox' id='avista" + newCont + "' name='avista" + newCont + "' " + vis + " />";
                cell1.align = "center";
                var cell2 = linha.insertCell(2)
                cell2.innerHTML = "<input type='checkbox' id='faturar" + newCont + "' name='faturar" + newCont + "' " + fat + " />"
                                 +"<input type='hidden' id='suspenso"+newCont+"' name='suspenso"+newCont+"' value='1' />";
                cell2.align = "center";
                var cell3 = linha.insertCell(3)
                cell3.innerHTML = "<img src='../../imagensNew/pause.png' style='cursor:pointer;' onclick='delRow(this);'>";
                cell3.align = "center";
                var cell4 = linha.insertCell(4)
                cell4.innerHTML = "<img src='../../imagensNew/cancel.png' style='cursor:pointer;' onclick='delRow(this);'>";
                cell4.align = "center";

                linha.className = "faixas";

                document.getElementById('contador').value = newCont;
                document.getElementById('prefixo').value = '';
                document.getElementById('prefixo').focus();

            }


            function delRow(linha) {
                var nrLinha = document.getElementById('table').rows.length;
                if (nrLinha > 2) {
                    if (confirm('Tem certeza que deseja excluir?')) {
                        var tabela = document.getElementById('table');
                        linha = linha.parentNode.parentNode;
                        id = linha.rowIndex;
                        tabela.deleteRow(id);
                    } else {
                        return false;
                    }
                } else {
                    alert('A tabela deve conter pelo menos uma faixa de CEP!');
                    return false;
                }
            }


            function validateRow() {
                var contador = document.getElementById('contador').value;
                //PERCORRE TODOS OS CAMPOS DA TABELA DE PESOS E PRECOS
                for (var i = 1; i <= contador; i++) {
                    //VERIFICA SE O CEP INICIAL FOI PREENCHIDO
                    if (!document.getElementById('avista' + i).checked && !document.getElementById('faturar' + i).checked) {
                        alert('Na tabela selecione se o prefixo é para serviço A VISTA, A FATURAR ou AMBOS!');
                        return false;
                    }
                    var pre = document.getElementById('prefixo' + i).value;
                    for (var j = 1; j <= contador; j++) {
                        if (i != j && pre == document.getElementById('prefixo' + j).value) {
                            alert('Este Prefixo já está inserido na tabela!');
                            return false;
                        }
                    }
                }
                return true;
            }
            
            function suspender(id){
                var sus = document.getElementById('suspenso'+id).value;
                    if(sus == 1){
                        document.getElementById('img_sus'+id).src = '../../imagensNew/refresh.png';
                        document.getElementById('suspenso'+id).value = '0';
                    }else{
                        document.getElementById('img_sus'+id).src = '../../imagensNew/pause.png';
                        document.getElementById('suspenso'+id).value = '1';
                    }
            }

        </script>

        <title>Portal Postal | Nova Amarração</title>

    </head>
    <body>
        <div id="divInteracao" class="esconder" style="top:10%; left:20%; right:20%; bottom:10%;" align="center"><input id="textointeracao" /></div>
        <div id="divInteracao3" class="esconder" style="top:10%; left:20%; right:20%; bottom:10%;" align="center"><input id="textointeracao" /></div>
        <div id="divProtecao" class="esconder"></div>

        <%@ include file="../../Includes/menu_agencia.jsp" %>
        <%@ include file="../../Includes/telaMsg.jsp" %>

        <div id="divPrincipal" align="center">
            <div id="container">
                <div id="conteudo">


                    <div id="titulo1">Cadastro de Prefixos</div>
                    <form name="form2" action="servicos_prefixos.jsp" method="post">
                        <ul class="ul_formulario">                            
                            <li class="titulo"><dd>Escolha um Servico para Gerenciar os Prefixos:</dd></li>
                            <li>
                                <dd>
                                    <label>Servicos</label>
                                    <select name="servicosel" id="servicosel" onchange="javascript:document.form2.submit();">
                                        <option value="">-- Escolha um Serviço --</option>
                                        <%                                            ArrayList<ServicoECT> listaServ = ContrServicoECT.consultaServicosPorGrupo();
                                            for (int i = 0; i < listaServ.size(); i++) {
                                                ServicoECT sv = listaServ.get(i);
                                                if (!sv.getGrupoServico().equals("SIMPLES")) {
                                                    if (servicosel != null && servicosel.equals(sv.getGrupoServico())) {
                                                        out.println("<option selected value='" + sv.getGrupoServico() + "'>" + sv.getNomeServico() + "</option>");
                                                    } else {
                                                        out.println("<option value='" + sv.getGrupoServico() + "'>" + sv.getNomeServico() + "</option>");
                                                    }
                                                }
                                            }
                                        %>
                                    </select>
                                </dd>
                            </li>
                        </ul>
                    </form>
                    <form name="form1" action="../../ServServicosPrefixo" method="post">
                        <ul <%if (servicosel == null || servicosel.equals("")) {%>class="esconder" <%} else {%> class="ul_formulario" <%}%>>   
                            <li class="titulo"><dd>Defina os Prefixos do Serviço Selecionado:</dd></li>     
                            <li>
                                <dd>
                                    <label>Prefixo</label>
                                    <input style="text-transform: uppercase;font-weight: bold;font-size: 13px;" type="text" name="prefixo" id="prefixo" size="5" maxlength="2" />
                                </dd>
                                <dd>
                                    <label>&nbsp;</label>
                                    <label style="margin-top: 7px;" for="b"><input type="checkbox" name="avista" value="1" id="b" /> FÍSICA</label>
                                </dd>
                                <dd>
                                    <label>&nbsp;</label>
                                    <label style="margin-top: 7px;" for="a"><input type="checkbox" name="faturar" value="" id="a" /> LÓGICA</label>
                                </dd>
                            </li>
                            <li>
                                <dd>
                                    <div class="buttons" style="margin:20px 0 20px 0;">
                                        <a class="regular" onclick="addRow();"><img src="../../imagensNew/plus_circle.png"> Adicionar Prefixo</a>
                                    </div>
                                </dd>
                            </li>
                            <li>
                                <dd>
                                    <table style="border:1px solid silver;" id="table" name="table">
                                        <tr style="background-color:#e7e7e7;">
                                            <th align="center" width="45"><b>PREFIXOS</b></th>
                                            <th align="center" width="65"><b>FÍSICA</b></th>
                                            <th align="center" width="65"><b>LÓGICA</b></th>
                                            <th align="center" width="75"><b>SUSPENDER</b></th>
                                            <th align="center" width="75"><b>EXCLUIR</b></th>
                                        </tr>
                                        <%
                                            ArrayList<ServicoPrefixo> lista = ContrServicoPrefixo.consultaTodosAmarracao(servicosel, nomeBD);
                                            for (int i = 0 ; i < lista.size() ; i++) {
                                                ServicoPrefixo s = lista.get(i);
                                        %>
                                        <tr class="faixas">
                                            <td align="center"><input style="font-size: 13px;font-weight: bold;text-align: center;" type="input" id="prefixo<%= i+1 %>" name="prefixo<%= i+1 %>" size="5" maxlength="9" readonly value="<%= s.getPrefixo()%>" /></td>
                                            <td align="center"><input type="checkbox" id="avista<%= i+1 %>" name="avista<%= i+1 %>" <%if (s.getAvista() == 1) {%>checked<%}%> /></td>
                                            <td align="center"><input type="checkbox" id="faturar<%= i+1 %>" name="faturar<%= i+1 %>" <%if (s.getAvista() == 0) {%>checked<%}%> /><input type="hidden" id="suspenso<%= i+1 %>" name="suspenso<%= i+1 %>" value="<%= s.getStatus()%>" /></td>
                                            <td align="center"><img id="img_sus<%= i+1 %>" <%if (s.getStatus() == 1) {%>src='../../imagensNew/pause.png'<%} else {%>src='../../imagensNew/refresh.png'<%}%> style='cursor:pointer;' onclick='suspender(<%= i+1 %>);' /></td>
                                            <td align="center"><img src='../../imagensNew/cancel.png' style='cursor:pointer;' onclick='delRow(this);' /></td>
                                        </tr>
                                        <%}%>
                                    </table>
                                </dd>
                            </li>                            
                            <li class="titulo" style="clear: both;"><dd></dd></li>
                            <li>
                                <dd style="width: 100%; text-align: center;">
                                    <div class="buttons">
                                        <input type="hidden" name="contador" id="contador" value="<%= lista.size()%>" />
                                        <input type="hidden" name="servico" id="servico" value="<%= servicosel %>" />
                                        <button type="submit" name="save" id="sub" class="positive" onclick="return validateRow();"><img src="../../imagensNew/tick_circle.png" alt=""/> SALVAR PREFIXOS DO SERVIÇO</button>
                                    </div>
                                </dd>
                            </li>
                        </ul>
                    </form>
                    <div style="width: 100%;clear: both;"></div>                
                </div>
            </div>
        </div>
    </body>
</html>
<%}%>