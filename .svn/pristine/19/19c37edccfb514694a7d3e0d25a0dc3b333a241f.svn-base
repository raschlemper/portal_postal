
<%@page import="Entidade.ServicoECT"%>
<%@page import="Controle.ContrServicoECT"%>
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

    response.setHeader("Cache-Control", "no-cache");
    response.setHeader("Pragma", "no-cache");
    response.setDateHeader("Expires", -1);

    String nomeBD = (String) session.getAttribute("nomeBD");
    if (nomeBD == null) {
        response.sendRedirect("../../index.jsp?msg=Sua sessao expirou! Para voltar ao Portal faça seu login novamente!");
    } else {
        int idCli = Integer.parseInt(String.valueOf(session.getAttribute("idCliente")));
        String nomeCli = contrCliente.consultaNomeById(idCli, nomeBD);
        ArrayList<Integer> acs = (ArrayList<Integer>) session.getAttribute("servicos");
        ArrayList<Integer> dps = (ArrayList<Integer>) session.getAttribute("departamentos");
        int nivel = (Integer) session.getAttribute("nivelUsuarioEmp");
        int idUser = (Integer) session.getAttribute("idUsuarioEmp");
        String nomeUser = (String) session.getAttribute("nomeUser");

        String servIni = "";
        if (acs.size() > 0) {
            switch (acs.get(0)) {
                case 1:
                    servIni = "PAC";
                    break;
                case 2:
                    servIni = "SEDEX";
                    break;
                case 3:
                    servIni = "SEDEXC";
                    break;
                case 4:
                    servIni = "SEDEX10";
                    break;
                case 7:
                    servIni = "SEDEX12";
                    break;
                case 5:
                    servIni = "ESEDEX";
                    break;
                case 6:
                    servIni = "CARTA";
                    break;
            }
        }
%>
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="cache-control" content="max-age=0" />
        <meta http-equiv="cache-control" content="no-cache" />
        <meta http-equiv="expires" content="0" />
        <meta http-equiv="expires" content="Tue, 01 Jan 1980 1:00:00 GMT" />
        <meta http-equiv="pragma" content="no-cache" />
        <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />

        <script type="text/javascript" src="../../javascript/ajax.js"></script>
        <script type="text/javascript" src="../js/ajax_portal.js"></script>
        <script type="text/javascript" src="../../javascript/mascara.js"></script>

        <link href="../../css/estilo.css" rel="stylesheet" type="text/css" />
        <link href="http://code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css" rel="stylesheet" />
        <script src="http://code.jquery.com/jquery-1.10.2.js" type="text/javascript"></script>
        <script src="http://code.jquery.com/ui/1.11.4/jquery-ui.js" type="text/javascript"></script>

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

            function preencherCampos() {
                var form = document.form1;
                if (form.departamento.value === '-1') {
                    alert('Escolha o Departamento/Centro de Custo para a Postagem!\n\nCaso não exista o departameto desejado,\npeça para a agência incluir no cadastro!');
                    return false;
                }
                abrirTelaEspera();
                form.submit();
            }

            function verificacaoAbr() {
                //VERIFICA ABRANGENCIA DE SERVIÇOS
                var form = document.form1;
                if (form.servico.value === 'ESEDEX') {
                    verPesquisarAbrangenciaServ(form.cep.value, 'ESEDEX');
                } else if (form.servico.value === 'SEDEX10') {
                    verPesquisarAbrangenciaServ(form.cep.value, 'SEDEX10');
                } else if (form.servico.value === 'SEDEX12') {
                    verPesquisarAbrangenciaServ(form.cep.value, 'SEDEX12');
                } else if (form.servico.value === 'PAX') {
                    verPesquisarAbrangenciaServ(form.cep.value, 'PAX');
                }
            }

            function chamaDivProtecao() {
                var classe = document.getElementById("divProtecao").className;
                if (classe === 'esconder') {
                    document.getElementById("divProtecao").className = "mostrar";
                    document.getElementById("divInteracao").className = "mostrar";
                } else {
                    document.getElementById("divProtecao").className = "esconder";
                    document.getElementById("divInteracao").className = "esconder";
                }
            }

            function chamaDivProtecao2() {
                var classe = document.getElementById("divProtecao").className;
                if (classe === 'esconder') {
                    document.getElementById("divProtecao").className = "mostrar";
                    document.getElementById("divInteracao2").className = "mostrar";
                } else {
                    document.getElementById("divProtecao").className = "esconder";
                    document.getElementById("divInteracao2").className = "esconder";
                }
            }

            function verificaSelecao() {
                var flag = true;
                var qtdSelecionada = 0;
                $("[name='ids']:checked").each(function () {
                    qtdSelecionada++;
                    flag = false;
                });

                if (confirm('Tem certeza que deseja imprimir as etiquetas selecionadas?')) {
                    return true;
                } else {
                    return false;
                }
            }

            function delRow(linha) {
                var nrLinha = document.getElementById('tableMultiDest').rows.length;
                if (nrLinha > 2) {
                    var tabela = document.getElementById('tableMultiDest');
                    linha = linha.parentNode.parentNode;
                    id = linha.rowIndex;
                    tabela.deleteRow(id);
                } else {
                    alert('A postagem deve conter pelo menos um destinatário!');
                    return false;
                }
            }

            function myFunction() {
               var flag = false;
              if($('#allCheck').is(':checked')){
                  flag = true;
              }
                var x = document.getElementsByName("cks_dest");
                var i;
                for (i = 0; i < x.length; i++) {
                    if (x[i].type === "checkbox") {
                        x[i].checked = flag;
                    }
                }
            }

        </script>

        <title>Portal Postal | Pré Postagem</title>

    </head>
    <body onload="fecharTelaEspera();<%if (!ContrClienteContrato.consultaStatusContrato(idCli, nomeBD)) {%> alert('O Contrato ou Cartão de Postagem estão Suspensos/Vencidos<br/><br/>Por Favor, entre em contato com a sua agência.<br/><br/>As Etiquetas serão geradas sem contrato até a regularização da situação.');<%}%>">
        <div id="divInteracao" class="esconder" style="top:10%; left:10%; right:10%; bottom:10%;" align="center"><input id="textointeracao" /></div>
        <div id="divInteracao2" class="esconder" style="top:10%; left:12%; right:12%; bottom:10%;" align="center"></div>
        <div id="divProtecao" class="esconder"></div>

        <div class="mostrar" id="protecaoTelaEspera">
            <div id="telaEspera">Por Favor, Aguarde...<br/><br/><img src="../../imagensNew/loader.gif" /></div>
        </div>

        <%@ include file="../../Includes/menu_cliente.jsp" %>
        <%@ include file="../../Includes/telaMsg.jsp" %>

        <div id="divPrincipal" align="center">
            <div id="container">
                <div id="conteudo">

                    <div id="titulo1">Gerar Multiplas Etiquetas (Pré-Postagem)</div>

                    <form name="form1" action="../../ServPreVendaMulti" method="post" autocomplete="off">
                        <div id="form_etiqueta">
                            <ul class="ul_formulario" style="width: 1136px;"  >
                                <li class="titulo">
                                    <dd style="font-size: 11px;">DADOS DO REMETENTE</dd>
                                </li>
                                <li>
                                    <dd>
                                        <label>Nome / Razão Social<b class="obg">*</b></label>
                                        <input readonly type="text" name="nomeCli" size="55" value="<%= nomeCli%>" />
                                    </dd>
                                    <dd>
                                        <label>Departamento / Centro de Custo</label>
                                        <select name="departamento">
                                            <%
                                                ArrayList<ClientesDeptos> listaDep = ContrClienteDeptos.consultaDeptos(idCli, nomeBD);
                                                if (listaDep != null && listaDep.size() > 0) {
                                            %>
                                            <option value="-1">-- SELECIONE UM DEPARTAMENTO --</option>                                    
                                            <%
                                                for (int i = 0; i < listaDep.size(); i++) {
                                                    ClientesDeptos cd = listaDep.get(i);
                                                    String cartao = "0";
                                                    if (cd.getCartaoPostagem() != null) {
                                                        cartao = cd.getCartaoPostagem();
                                                    }
                                                    if (dps.contains(cd.getIdDepartamento())) {
                                            %>
                                            <option value="<%=cd.getIdDepartamento() + ";" + cd.getNomeDepartamento() + ";" + cartao%>"><%= cd.getNomeDepartamento()%></option>
                                            <%}
                                                }
                                            } else {%>
                                            <option value="">NENHUM DEPARTAMENTO</option>
                                            <%}%>
                                        </select>
                                    </dd>
                                </li>
                                <li class="titulo">
                                    <dd style="font-size: 11px;">ESCOLHA O SERVIÇO DESEJADO</dd>
                                </li>
                                <li>
                                    <dd>
                                        <label>Serviço<b class="obg">*</b></label>
                                        <select style="width: 220px;" name="servico_1" id="servico_1">
                                            <%
                                                ArrayList<Integer> listaContratoServ = ContrClienteContrato.consultaContratoCliente(idCli, nomeBD);
                                                ArrayList<Integer> listaOutrosServ = ContrClienteContrato.consultaOutrosServicosCliente(idCli, nomeBD);
                                                ArrayList<ServicoECT> listaServ = ContrServicoECT.consultaServicosByContrato(listaContratoServ);
                                                ArrayList<ServicoECT> listaServ1 = ContrServicoECT.consultaServicos(0, 1, "OSV");
                                                if (listaServ != null) {
                                                    for (int i = 0; i < listaServ.size(); i++) {
                                                        ServicoECT sv = listaServ.get(i);
                                                        if (!sv.getGrupoServico().equals("SEDEXC")) {
                                                            out.println("<option value='" + sv.getCodECT() + ";" + sv.getGrupoServico() + ";NAC'>" + sv.getNomeServico() + "</option>");
                                                        }
                                                    }
                                                    for (int i = 0; i < listaServ1.size(); i++) {
                                                        ServicoECT sv = listaServ1.get(i);
                                                        if (listaOutrosServ.contains(sv.getCodECT()) && !sv.getGrupoServico().equals("PPI")) {
                                                            String tipoPost = "NAC";
                                                            if (sv.getTipo_agencia().contains("INT")) {
                                                                tipoPost = "INT";
                                                            }
                                                            out.println("<option value='" + sv.getCodECT() + ";" + sv.getGrupoServico() + ";" + tipoPost + "'>" + sv.getNomeServico() + "</option>");
                                                        }
                                                    }
                                                }
                                            %>
                                        </select>
                                    </dd>
                                    <dd>
                                        <label>M.P.</label>
                                        <select name="mp" id="mp">
                                            <option value="0">Não</option>
                                            <option value="1">Sim</option>
                                        </select>
                                    </dd>
                                    <dd>
                                        <label>A.R.</label>
                                        <select name="ar" id="ar">
                                            <option value="0">Não</option>
                                            <option value="1">Sim</option>
                                        </select>
                                    </dd>
                                    <dd>
                                        <label>Valor Declarado<span style="color:red;">(R$)</span></label>
                                        <input type="text" name="vd" id="vd" value="0.00" onkeypress="mascara(this, maskReal)" />
                                    </dd>
                                </li>
                                <li>
                                    <dd>
                                        <div class="buttons">                                            
                                            <button type="button" class="regular" onclick="verPesquisarDestinatario('multi');"><img src="../../imagensNew/32_user.png" /> ADICIONAR DESTINATÁRIOS</button>
                                        </div>
                                    </dd>
                                </li>
                                <li>
                                    <div id="titulo2">DESTINATÁRIOS PARA GERAÇÃO DE ETIQUETAS</div>                    
                                    <table cellpadding="0" cellspacing="0" border="0" id="tableMultiDest" class="tinytable">
                                        <thead>
                                            <tr>
                                                <th><h3>QTD.</h3></th>
                                                <th><h3>Serviço</h3></th>
                                                <th><h3>Destinatário</h3></th>
                                                <th><h3>Endereço</h3></th>
                                                <th><h3>Cidade / UF</h3></th>
                                                <th><h3>CEP</h3></th>
                                                <th><h3>N.F.</h3></th>
                                                <th><h3>AR</h3></th>
                                                <th><h3>MP</h3></th>
                                                <th><h3>VD</h3></th>
                                                <th><h3>DEL</h3></th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                        </tbody>
                                    </table>
                                </li>
                                <li>
                                    <dd style="width: 100%;">
                                        <div class="buttons">
                                            <input type="hidden" name="nomeBD" id="nomeBD" value="<%= nomeBD%>" />
                                            <input type="hidden" name="idCliente" value="<%= idCli%>" />
                                            <input type="hidden" name="idUser" value="<%= idUser%>" />
                                            <input type="hidden" name="nomeUser" value="<%= nomeUser%>" />
                                            <button type="button" class="positive" onclick="return preencherCampos();"><img src="../../imagensNew/tick_circle.png" /> EFETUAR PRÉ-POSTAGEM</button>
                                        </div>
                                    </dd>
                                </li>
                            </ul>

                        </div>
                    </form>
                    <img width="100%" src="../../imagensNew/linha.jpg"/>

                </div>
            </div>
        </div>
    </body>
</html>
<%}%>