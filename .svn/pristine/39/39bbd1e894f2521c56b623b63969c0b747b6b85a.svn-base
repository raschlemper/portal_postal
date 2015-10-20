
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
        ArrayList<Integer> dps = (ArrayList<Integer>) session.getAttribute("departamentos");
        String nomeCli = contrCliente.consultaNomeById(idCli, nomeBD);
        int nivel = (Integer) session.getAttribute("nivelUsuarioEmp");
        int idUser = (Integer) session.getAttribute("idUsuarioEmp");
        String nomeUser = (String) session.getAttribute("nomeUser");
        
        ArrayList<PreVenda> lista = ContrPreVenda.consultaVendasImportadas(nomeBD, idCli);
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
        </script>
        <style>
            .tb1 th{white-space: nowrap;}
            .tb1 td{padding: 1px;text-align: center;}
            .tb1 input{font-size: 11px; height: 14px;}
        </style>
        <title>Portal Postal | Importação de Postagens</title>

    </head>
    <body onload="javascript:document.getElementById('nome').focus();">
        <div id="divInteracao" class="esconder" style="top:10%; left:10%; right:10%; bottom:10%;" align="center"><input id="textointeracao" /></div>
        <div id="divProtecao" class="esconder"></div>

        <%@ include file="../../Includes/menu_cliente.jsp" %>
        <%@ include file="../../Includes/telaMsg.jsp" %>

        <div id="divPrincipal" align="center">
            <div id="container">
                <div id="conteudo">

                    <div id="titulo1">CONFIRME OS DADOS DA IMPORTAÇÃO!</div><br/>
                    <div style="font-size: 16px;font-weight: bold;">Total de postagens importadas: <%= lista.size() %></div>
                    <form method="post" action="../../ServPreVendaConfirmaImp" id="form1" name="form1">
                        <%--<div style="background: url('../../imagensNew/ajax_arrow.png') right repeat-y; right: 20px; height: 100%; width: 10px; z-index: 50; position: absolute;"></div>--%>
                        <div style="overflow: auto; position: relative; z-index: 1;">
                            <table cellpadding="0" cellspacing="0" class="tb1">
                                <tr>
                                    <th>N.</th>
                                    <th></th>
                                    <th>NOME</th>
                                    <th>EMPRESA</th>
                                    <th>CPF</th>
                                    <th>CEP</th>
                                    <th>ENDEREÇO</th>
                                    <th>Nº</th>
                                    <th>COMPL</th>
                                    <th>BAIRRO</th>
                                    <th>CIDADE</th>
                                    <th>UF</th>
                                    <th>AOS CUIDADOS</th>
                                    <th>N.F.</th>
                                    <th>DEPARTAMENTO</th>
                                    <th>SERVIÇO</th>
                                    <th>AR</th>
                                    <th>MP</th>
                                    <th>VD</th>
                                    <th>OBS</th>
                                    <th>CONTEÚDO</th>
                                </tr>
                                <%
                                    for (int i = 0; i < lista.size(); i++) {
                                        PreVenda pv = lista.get(i);
                                        String cor = "even";
                                        if (i % 2 == 0) {
                                            cor = "odd";
                                        }
                                %>
                                <tr class="<%=cor%>">
                                    <td><input size="1" type="text" name="n" value="<%= i+1 %>" /></td>
                                    <td><%if(!pv.getNumObjeto().equals("avista")){%><input size="12" type="text" name="nObj<%= pv.getId()%>" value="<%= pv.getNumObjeto()%>" /><%}%></td>
                                    <td><input size="50" type="text" name="nome<%= pv.getId()%>" value="<%= pv.getNomeDes()%>" /></td>
                                    <td><input type="text" name="empresa<%= pv.getId()%>" value="<%= pv.getEmpresaDes()%>" /></td>
                                    <td><input type="text" name="cpf<%= pv.getId()%>" size="16" value="<%= pv.getCpfDes()%>" maxlength="18" onkeypress="mascara(this, maskCpfCnpj);" /></td>
                                    <td><input type="text" name="cep<%= pv.getId()%>" size="8" value="<%= pv.getCepDes()%>" maxlength="9" onkeypress="mascara(this, maskCep);" /></td>
                                    <td><input size="50" type="text" name="endereco<%= pv.getId()%>" value="<%= pv.getEnderecoDes()%>" /></td>
                                    <td><input type="text" name="numero<%= pv.getId()%>" size="3" value="<%= pv.getNumeroDes()%>" maxlength="8" onkeypress="mascara(this, maskNumero);" /></td>
                                    <td><input type="text" name="complemento<%= pv.getId()%>" size="6" value="<%= pv.getComplementoDes()%>" maxlength="20" /></td>
                                    <td><input size="30" type="text" name="bairro<%= pv.getId()%>" value="<%= pv.getBairroDes()%>" /></td>
                                    <td><input size="30" type="text" name="cidade<%= pv.getId()%>" value="<%= pv.getCidadeDes()%>" /></td>
                                    <td><input type="text" name="uf<%= pv.getId()%>" size="2" maxlength="2" value="<%= pv.getUfDes()%>" /></td>
                                    <td><input size="40" type="text" name="aoscuidados<%= pv.getId()%>" value="<%= pv.getAos_cuidados()%>" /></td>
                                    <td>
                                        <input type="text" name="nota<%= pv.getId()%>" size="8" value="<%= pv.getNotaFiscal()%>" />
                                        <input type="hidden" name="idDestinatario<%= pv.getId()%>" value="<%= pv.getIdDestinatario()%>" />
                                        <input type="hidden" name="id" value="<%= pv.getId()%>" />
                                    </td>
                                    <td>
                                        <select name="departamento<%= pv.getId()%>">
                                            <option value="">NENHUM DEPARTAMENTO</option>
                                            <%
                                                ArrayList<ClientesDeptos> listaDep = ContrClienteDeptos.consultaDeptos(idCli, nomeBD);
                                                for (int j = 0; j < listaDep.size(); j++) {
                                                    ClientesDeptos cd = listaDep.get(j);
                                                    String cartao = "0";
                                                    if (cd.getCartaoPostagem() != null) {
                                                        cartao = cd.getCartaoPostagem();
                                                    }
                                                    if (dps.contains(cd.getIdDepartamento())) {
                                            %>
                                            <option <%if (pv.getDepartamento().equals(cd.getNomeDepartamento())) {%> selected <%}%> value="<%=cd.getIdDepartamento() + ";" + cd.getNomeDepartamento() + ";" + cartao%>"><%= cd.getNomeDepartamento()%></option>
                                            <%}
                                                }%>
                                        </select>
                                    </td>
                                    <td>
                                        <select name="servico<%= pv.getId()%>">
                                            <option value="">SELECIONE UM SERVIÇO</option>
                                            <option value="PAC" <%if (pv.getNomeServico().equals("PAC")) {%> selected <%}%>>PAC</option>
                                            <option value="SEDEX" <%if (pv.getNomeServico().equals("SEDEX")) {%> selected <%}%>>SEDEX</option>
                                            <option value="ESEDEX" <%if (pv.getNomeServico().equals("ESEDEX")) {%> selected <%}%>>E-SEDEX</option>
                                            <option value="CARTA" <%if (pv.getNomeServico().equals("CARTA")) {%> selected <%}%>>CARTA REGISTRADA</option>
                                            <option value="SIMPLES" <%if (pv.getNomeServico().equals("SIMPLES")) {%> selected <%}%>>CARTA SIMPLES</option>
                                        </select>
                                    </td>
                                    <td align="center">
                                        <select name="ar<%= pv.getId()%>">
                                            <option value="1" <%if (pv.getAviso_recebimento() == 1) {%> selected <%}%>>SIM</option>
                                            <option value="0" <%if (pv.getAviso_recebimento() == 0) {%> selected <%}%>>NÃO</option>
                                        </select>
                                    </td>
                                    <td align="center">
                                        <select name="mp<%= pv.getId()%>">
                                            <option value="1" <%if (pv.getMao_propria() == 1) {%> selected <%}%>>SIM</option>
                                            <option value="0" <%if (pv.getMao_propria() == 0) {%> selected <%}%>>NÃO</option>
                                        </select>
                                    </td>
                                    <td><input type="text" name="vd<%= pv.getId()%>" size="5" value="<%= pv.getValor_declarado()%>" maxlength="8" onkeypress="mascara(this, maskReal);" /></td>
                                    <td><input type="text" name="obs<%= pv.getId()%>" value="<%= pv.getObservacoes()%>" /></td>
                                    <td><input type="text" name="conteudo<%= pv.getId()%>" value="<%= pv.getConteudo()%>" /></td>
                                </tr>
                                <%}%>
                            </table>
                        </div>
                        <ul class="ul_formulario" style="width: 1140px;">
                            <li>
                                <dd style="width: 100%;text-align: center;">
                                    <div class="buttons">
                                        <input type="hidden" name="nomeBD" id="nomeBD" value="<%= nomeBD%>" />
                                        <input type="hidden" name="idCliente" value="<%= idCli%>" />
                                        <input type="hidden" name="idUser" value="<%= idUser%>" />
                                        <input type="hidden" name="nomeUser" value="<%= nomeUser %>" />
                                        <button type="button" class="positive" onclick="abrirTelaEspera();document.form1.submit();"><img src="../../imagensNew/tick_circle.png" /> CONFIRMAR IMPORTAÇÃO</button>
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