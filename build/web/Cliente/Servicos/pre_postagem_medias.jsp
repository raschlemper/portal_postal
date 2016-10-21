
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
                if ($('#allCheck').is(':checked')) {
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
                                                    if (cd.getCartaoPostagem() != null && !cd.getCartaoPostagem().equals("") && !cd.getCartaoPostagem().equals("null")) {
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
                                        <select style="width: 220px;" name="servico_1" id="servico_1" class="servico_1">
                                            <%
                                                ArrayList<Integer> listaContratoServ = ContrClienteContrato.consultaContratoCliente(idCli, nomeBD);
                                                ArrayList<Integer> listaOutrosServ = ContrClienteContrato.consultaOutrosServicosCliente(idCli, nomeBD);
                                                ArrayList<ServicoECT> listaServ = ContrServicoECT.consultaServicosByContrato(listaContratoServ);
                                                ArrayList<ServicoECT> listaServ1 = ContrServicoECT.consultaServicos(0, 1, "OSV");
                                                if (listaServ != null) {
                                                    for (int i = 0; i < listaServ.size(); i++) {
                                                        ServicoECT sv = listaServ.get(i);
                                                        if (sv.getGrupoServico().equals("SEDEX")
                                                                || sv.getGrupoServico().equals("PAC")
                                                                || sv.getGrupoServico().equals("CARTA")
                                                                || sv.getGrupoServico().equals("SIMPLES")
                                                                || sv.getGrupoServico().equals("IMPRESSO")
                                                                || sv.getGrupoServico().equals("SEDEX10")
                                                                || sv.getGrupoServico().equals("SEDEX12")
                                                                || sv.getGrupoServico().equals("SEDEXHJ")
                                                                || sv.getGrupoServico().equals("ESEDEX")
                                                                || sv.getGrupoServico().equals("PAX")) {

                                                            if (!sv.getGrupoServico().equals("SEDEXC")) {
                                                                out.println("<option value='" + sv.getCodECT() + ";" + sv.getGrupoServico() + ";NAC'>" + sv.getNomeServico() + "</option>");
                                                            }
                                                        }
                                                    }
                                                    for (int i = 0; i < listaServ1.size(); i++) {
                                                        ServicoECT sv = listaServ1.get(i);
                                                        if (sv.getGrupoServico().equals("SEDEX")
                                                                || sv.getGrupoServico().equals("PAC")
                                                                || sv.getGrupoServico().equals("CARTA")
                                                                || sv.getGrupoServico().equals("SIMPLES")
                                                                || sv.getGrupoServico().equals("IMPRESSO")
                                                                || sv.getGrupoServico().equals("SEDEX10")
                                                                || sv.getGrupoServico().equals("SEDEX12")
                                                                || sv.getGrupoServico().equals("SEDEXHJ")
                                                                || sv.getGrupoServico().equals("ESEDEX")
                                                                || sv.getGrupoServico().equals("PAX")) {
                                                            if (listaOutrosServ.contains(sv.getCodECT()) && !sv.getGrupoServico().equals("PPI")) {
                                                                String tipoPost = "NAC";
                                                                if (sv.getTipo_agencia().contains("INT")) {
                                                                    tipoPost = "INT";
                                                                }
                                                                out.println("<option value='" + sv.getCodECT() + ";" + sv.getGrupoServico() + ";" + tipoPost + "'>" + sv.getNomeServico() + "</option>");
                                                            }
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
                                <dd style="width: 100%;text-align: center;">
                                    <div class="buttons">
                                        <button type="button" onclick="consultaCeps();" class="regular"><img src="../../imagensNew/refresh.png" /> VALIDAR CEPs</button>
                                        <button type="button" class="regular"  onclick="window.open('http://www.buscacep.correios.com.br', 'CORREIOS');" ><img src="../../imagensNew/lupa.png" /> CONSULTE AQUI O CEP CORRETO</button>
                                    </div>
                                </dd>
                            </li>
                                <li>
                                    <dd style="width: 100%;text-align: center;">
                                        <div class="buttons">
                                            <input type="hidden" name="nomeBD" id="nomeBD" value="<%= nomeBD%>" />
                                            <input type="hidden" name="idCliente" value="<%= idCli%>" />
                                            <input type="hidden" name="idUser" value="<%= idUser%>" />
                                            <input type="hidden" name="nomeUser" value="<%= nomeUser%>" />
                                            <button type="button" class="positive" onclick="return verificaCepsServicos();"><img src="../../imagensNew/tick_circle.png" /> EFETUAR PRÉ-POSTAGEM</button>
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
        <script type="text/javascript">
function preencherCampos() {
                
            }
          function verificaCepsServicos() {
                abrirTelaEspera();
                var form = document.form1;
                var flagCep = true;
                var flagServ = true;

                $(".multi_id").each(function () {
                    var id = $(this).attr('value');                    
                    if ($("#multi_cep_label_"+id).css("background-color") !== "rgb(153, 255, 102)") {
                        flagCep = false;
                    }
                    if ($("#multi_serv_label_"+id).css("background-color") !== "rgb(153, 255, 102)") {
                        flagServ = false;
                    }
                });

                if(!flagCep){
                    fecharTelaEspera();
                    alert("Existem CEPs inválidos!\nCorrija/Valide todos os CEPs (em vermelho).\nClique em 'Validar Postagens' para validar!");
                    return false;
                }else if(!flagServ){
                    fecharTelaEspera();
                    alert("Existem Serviços Inválidos!\nCorrija/Valide todos os Serviços (em vermelho).\nClique em 'Validar Postagens' para validar!");
                    return false;
                } else if (form.departamento.value === '-1') {
                    fecharTelaEspera();
                    alert('Escolha o Departamento/Centro de Custo para a Postagem!\n\nCaso não exista o departameto desejado,\npeça para a agência incluir no cadastro!');
                    return false;
                } else {
                    form.submit();
                }
            }

            function consultaCeps() {
                $(".multi_id").each(function () {
                    var id = $(this).attr('value');
                    console.log($("#multi_cep_label_" + id).css("background-color"));
                    
                    if ($("#multi_cep_label_"+id).css("background-color") !== "rgb(153, 255, 102)") {
                        $.ajax({
                            method: "POST",
                            url: "../../AjaxPages/ajax_cep_json.jsp",
                            data: {cep: $("#multi_cep_" + id).val()},
                            dataType: 'json'
                        }).done(function (retorno) {
                            if (retorno.logradouro.toUpperCase() === 'CEP INEXISTENTE') {
                                console.log(id + " - " + retorno.cep + " <<< " + retorno.logradouro.toUpperCase());
                                $("#multi_cep_label_"+id).css("background-color", "#ff5050");
                            } else {
                                console.log(id + " - " + retorno.cep + " >>> " + retorno.logradouro.toUpperCase());
                                $("#multi_cep_label_"+id).css("background-color", "#99ff66");
                            }
                        });
                    }
                    
                    if ($("#multi_serv_label_"+id).css("background-color") !== "rgb(153, 255, 102)") {
                        var s = $("#multi_serv_" + id).val().split(';');  
                        $.ajax({
                            method: "POST",
                            url: "../../AjaxPages/ajax_abrangencia_json.jsp",
                            data: {
                                cep: $("#multi_cep_" + id).val(),
                                servico: s[1],
                                troca_servico: 'SIM'
                            },
                            dataType: 'json'
                        }).done(function (retorno) {
                            if (retorno.resultado !== 'TROCA' && retorno.resultado !== 'OK') {
                                console.log(id + " - " + retorno.resultado);
                                $("#multi_serv_label_"+id).css("background-color", "#ff5050");
                                $("#multi_serv_label_"+id).html($("#multi_serv_label_"+id).html()+"<br/>"+retorno.resultado);
                            } else if (retorno.resultado === 'TROCA') {
                                console.log(id + " - " + retorno.resultado);
                                var ns0 = retorno.servico_novo;
                                var ns1 = retorno.servico_novo;
                                $("#servico_1 > option").each(function(){         
                                    if($(this).val().split(';')[1] === retorno.servico_novo){                                        
                                        ns1 = $(this).val();
                                        ns0 = $("#servico_1 option[value='"+$(this).val()+"']").text();
                                    }
                                });
                                $("#multi_serv_label_"+id).css("background-color", "#99ff66");
                                $("#multi_serv_label_"+id).html(ns0+"<br/><span style='background-color:yellow;'>CEP não aceita "+retorno.servico_antigo+"</span>");
                                $("#multi_serv_"+id).val(ns1);
                            } else {
                                console.log(id + " - " + retorno.resultado);
                                $("#multi_serv_label_"+id).css("background-color", "#99ff66");
                            }
                        });
                    }
                });
            }
            
            var sorterDes = new TINY.table.sorter('sorterDes', 'tableDes', {
                headclass: 'head',
                ascclass: 'asc',
                descclass: 'desc',
                evenclass: 'evenrow',
                oddclass: 'oddrow',
                evenselclass: 'evenselected',
                oddselclass: 'oddselected',
                paginate: true,
                size: 20,
                colddid: 'columnsDes',
                currentid: 'currentpageDes',
                totalid: 'totalpagesDes',
                startingrecid: 'startrecordDes',
                endingrecid: 'endrecordDes',
                totalrecid: 'totalrecordsDes',
                hoverid: 'selectedrowDefault',
                pageddid: 'pagedropdownDes',
                navid: 'tablenavDes',
                sortcolumn: 2,
                sortdir: 1,
                init: false
            });

        </script> 
    </body>
</html>
<%}%>