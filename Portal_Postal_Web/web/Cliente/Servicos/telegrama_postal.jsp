<%@page import="Entidade.ClientesDeptos"%>
<%@page import="Controle.ContrClienteDeptos"%>
<%@page import="Controle.contrCliente"%>
<%@page import="Entidade.Contato"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@page import="java.util.ArrayList, java.util.Date, java.text.SimpleDateFormat, java.sql.*, java.util.Calendar, java.util.Locale" %>

<%
    SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat hrf1 = new SimpleDateFormat("HH:mm");

    String nomeBD = (String) session.getAttribute("nomeBD");
    if (nomeBD == null) {
        response.sendRedirect("../../index.jsp?msg=Sua sessao expirou! Para voltar ao Portal faça seu login novamente!");
    } else {
        ArrayList<Integer> dps = (ArrayList<Integer>) session.getAttribute("departamentos");
        String numCliente = String.valueOf(session.getAttribute("idCliente"));
        String nomeUser = String.valueOf(session.getAttribute("nomeUser"));
        int idCliente = Integer.parseInt(numCliente);
        Clientes cli = contrCliente.consultaClienteById(idCliente, nomeBD);
%>

<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
        <title>Portal Postal | Solicitar Coleta</title>
        <meta name="keywords" content="" />
        <meta name="description" content="" />

        <link href="../../css/estilo.css" rel="stylesheet" type="text/css" />
        <script type="text/javascript" src="../js/ajax_portal.js"></script>
        <script type="text/javascript" src="../../javascript/mascara.js"></script>
        <script type="text/javascript" src="../../javascript/ajax.js"></script>
        <script type="text/javascript" src="../../javascript/jsContato.js"></script>

        <link type="text/css" href="../../javascript/jquery/css/jquery-ui-1.8.16.custom.css" rel="stylesheet" />
        <script type="text/javascript" src="http://code.jquery.com/jquery-1.10.2.js"></script>
        <script type="text/javascript" src="http://code.jquery.com/ui/1.11.4/jquery-ui.js"></script>
        <script type="text/javascript" src="../../javascript/jquery/js/jquery.ui.datepicker-pt-BR.js"></script>
        <script type="text/javascript" src="../../javascript/plugins/timepicker/jquery.clockpick.1.2.7.js"></script>
        <link href="../../javascript/plugins/timepicker/jquery.clockpick.1.2.7.css" rel="stylesheet" type="text/css" />
        <link href="http://code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css" rel="stylesheet" />

        <!-- FUSION CHARTS -->
        <script type="text/javascript" src="../../javascript/plugins/HighCharts/js/highcharts.js"></script>
        <script type="text/javascript" src="../../javascript/plugins/HighCharts/js/themes/grid.js"></script>
        <!-- FIM FUSION CHARTS -->

        <!-- TableSorter -->
        <link rel="stylesheet" href="../../javascript/plugins/TableSorter/styleSorterV3.css" />
        <script type="text/javascript" src="../../javascript/plugins/TableSorter/scriptSorterV3.js"></script>
        <!-- TableSorter -->

        <link rel="stylesheet" href="../../javascript/plugins/dropdown/css/style.css" type="text/css" media="screen, projection"/>
        <!--[if lte IE 7]><link rel="stylesheet" type="text/css" href="../../javascript/plugins/dropdown/css/ie.css" media="screen" /><![endif]-->
        <script type="text/javascript" src="../../javascript/plugins/dropdown/js/jquery.dropdownPlain.js"></script>

        <script type="text/javascript" charset="utf-8">
            $(function () {
                $("#dataColeta").datepicker({
                    showOn: "button",
                    buttonImage: "../../imagensNew/calendario.png",
                    buttonImageOnly: true,
                    showAnim: "slideDown"
                });

                $("#clockpick1").clockpick({
                    valuefield: 'horaColeta',
                    starthour: 0,
                    endhour: 23,
                    showminutes: true,
                    military: true,
                    minutedivisions: 6
                });
            });

            function preencherCampos() {
                var form = document.form1;
                if (form.departamento.value === "-1") {
                    alert('Escolha o Departamento/Centro de Custo para a Postagem!\n\nCaso não exista o departameto desejado,\npeça para a agência incluir no cadastro!');
                    return false;
                }
                if(form.ckEnvio === 1){
                    if (form.dataColeta.value === "") {
                        alert('Preencha a Data da Coleta!');
                        return false;
                    }
                    if (!valida_data(form.dataColeta)) {
                        return false;
                    }
                    if (!valida_hora(form.horaColeta)) {
                        return false;
                    }
                }

                form.submit();
            }       
            
            $(function() {
                $("#nome").autocomplete({
                    autoFocus: true,
                    focus: function() {
                        return false;
                    },
                    open: function() {
                        limparContato();
                    },
                    source: function(request, response) {
                        $.ajax({
                            url: "autocomplete_dest.jsp",
                            type: "POST",
                            dataType: "json",
                            data: {nomePesquisa: request.term},
                            success: function(data) {
                                response(data);
                            },
                            error: function(error) {
                                //alert('error: ' + error);
                            }
                        });
                    },
                    minLength: 3,
                    select: function(event, ui) {
                        if (event.keyCode === 9)
                            return false;
                        $("#nome").val(ui.item.label);
                        $("#endereco").val(ui.item.endereco);
                        $("#empresa").val(ui.item.empresa);
                        $("#cpf_cnpj").val(ui.item.cpf_cnpj);
                        $("#email_destinatario").val(ui.item.email_destinatario);
                        $("#celular").val(ui.item.celular);
                        $("#aoscuidados").val(ui.item.aoscuidados);
                        $("#cep").val(ui.item.cep);
                        $("#numero").val(ui.item.numero);
                        $("#complemento").val(ui.item.complemento);
                        $("#bairro").val(ui.item.bairro);
                        $("#cidade").val(ui.item.cidade);
                        $("#uf").val(ui.item.uf);
                        $("#uf2").find("option").filter(function(){
                            return ( ($(this).val() == ui.item.uf) || ($(this).text() == ui.item.uf) )
                        }).prop('selected', true);
                        $("#obs").focus();
                        return false;
                    }
                }).autocomplete("instance")._renderItem = function(ul, item) {
                    return $("<li>").append("<div>" +
                            "<div style='float: left;'><img width='24' src='../../imagensNew/contato_cracha.png' /></div>" +
                            "<div style='margin:3px 0px 3px 35px'>" +
                            "<div style='font-weight: bold; color: #333; font-size: 10px;'>" + item.label + "</div>" +
                            "<div style='color: #999; font-size: 9px'>" + item.endereco + " - " + item.cidade + " / " + item.uf + "</div>" +
                            "</div>" +
                            "<div style='clear:both;'>" +
                            "</div>").appendTo(ul);
                };
            });

            function limparContato() {
                $("#endereco").val('');
                $("#empresa").val('');
                $("#cpf_cnpj").val('');
                $("#email_destinatario").val('');
                $("#celular").val('');
                $("#aoscuidados").val('');
                $("#cep").val('');
                $("#numero").val('');
                $("#complemento").val('');
                $("#bairro").val('');
                $("#cidade").val('');
                $("#uf").val('');
                //$("#uf2").val('');
            }
            
            
        </script>
    </head>
    <body>
        <div id="divInteracao" class="esconder"><input id="textointeracao" /></div>
        <div id="divProtecao" class="esconder"></div>

        <%@ include file="../../Includes/telaMsg.jsp" %>
        <%@ include file="../../Includes/menu_cliente.jsp" %>

        <div id="divPrincipal" align="center">
            <div id="container">
                <div id="conteudo">
                    <ul class="ul_tab" style="width: 1160px;">
                        <li>
                            <dl class="ativo" style='width:170px; border-left: 1px solid #CCC;'>
                                <dd><b class='serv'>ENVIAR TELEGRAMA</b></dd>
                            </dl>
                            <dl style='width:170px;' onclick="location.href='telegrama_pesquisa.jsp'">
                                <dd><b class='serv'>PESQUISAR TELEGRAMAS</b></dd>
                            </dl>
                            <dl style="width: 750px; background: white;border-top: 1px solid white;border-right: 1px solid white; cursor: default;" ></dl>
                        </li>
                    </ul>

                    <form action="../../ServTelegramaNovo" method="post" name='form1'>
                        <ul class="ul_formulario">
                            <li class="titulo">
                                <dd><span>DADOS DO REMETENTE</span></dd>
                            </li>
                                <li>
                                    <dd>
                                        <label>Nome / Razão Social<b class="obg">*</b></label>
                                        <input type="text" name="nomeCli" size="55" value="<%= cli.getNome() %>" />
                                    </dd>
                                    <dd>
                                        <label>Departamento / Centro de Custo</label>
                                        <select name="departamento">
                                            <%
                                                ArrayList<ClientesDeptos> listaDep = ContrClienteDeptos.consultaDeptos(idCliente, nomeBD);
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
                                <li id="singleDest2">
                                    <dd>
                                        <label>CEP<b class="obg">*</b><a onclick="window.open('http://www.buscacep.correios.com.br', 'CORREIOS');" ><img src="../../imagensNew/question.png" /></a></label>
                                        <input type="text" name="cepCli" id="cepCli" size="8" maxlength="9" value="<%= cli.getCep() %>" onkeypress="mascara(this, maskCep);" />
                                    </dd>
                                    <dd>
                                        <label>Endereço<b class="obg">*</b></label>
                                        <input type="text" name="enderecoCli" id="enderecoCli" size="55" maxlength="80" value="<%= cli.getEndereco() %>" />
                                    </dd>
                                    <dd>
                                        <label>Nº<b class="obg">*</b></label>
                                        <input type="text" name="numeroCli" id="numeroCli" size="7" value="" maxlength="8" onkeypress="mascara(this, maskNumeroRua)" />
                                    </dd>
                                    <dd>
                                        <label>Complemento</label>
                                        <input type="text" size="20" name="complementoCli" id="complementoCli" maxlength="60" value="<%= cli.getComplemento() %>" />
                                    </dd>
                                    <dd>
                                        <label>Bairro</label>
                                        <input type="text" name="bairroCli" id="bairroCli" maxlength="50" value="<%= cli.getBairro() %>" />
                                    </dd>
                                    <dd>
                                        <label>Cidade<b class="obg">*</b></label>
                                        <input type="text" name="cidadeCli" id="cidadeCli" maxlength="50" value="<%= cli.getCidade() %>" />
                                    </dd>
                                    <dd>
                                        <label>UF<b class="obg">*</b></label>
                                        <input type="text" size="2" name="ufCli" id="ufCli" maxlength="2" value="<%= cli.getUf() %>" />
                                    </dd>
                                </li>
                                <li class="titulo">
                                    <dd style="font-size: 11px;">DADOS DO DESTINATÁRIO</dd>
                                </li>
                                <li id="singleDest1">
                                    <dd>
                                        <label>Nome / Razão Social<b class="obg">*</b></label>
                                        <input type="text" name="nomeChrome" id="nomeChrome" style="display:none" size="50" maxlength="80" value="" />
                                        <input type="text" name="nome" id="nome" size="50" maxlength="80" value="" autocomplete="off" />
                                    </dd>
                                </li>
                                <li id="singleDest2">
                                    <dd>
                                        <label>CEP<b class="obg">*</b><a onclick="window.open('http://www.buscacep.correios.com.br', 'CORREIOS');" ><img src="../../imagensNew/question.png" /></a></label>
                                        <input type="text" name="cep" id="cep" size="8" value="" maxlength="9" onkeypress="mascara(this, maskCep);
                                                handleEnter();" onblur="verPesquisarCepDest(this.value);" />
                                    </dd>
                                    <dd>
                                        <label>Endereço<b class="obg">*</b></label>
                                        <input type="text" name="endereco" id="endereco" size="55" maxlength="80" value="" />
                                    </dd>
                                    <dd>
                                        <label>Nº<b class="obg">*</b></label>
                                        <input type="text" name="numero" id="numero" size="7" value="" maxlength="8" />
                                    </dd>
                                    <dd>
                                        <label>Complemento</label>
                                        <input type="text" size="20" name="complemento" id="complemento" maxlength="60" value="" />
                                    </dd>
                                    <dd>
                                        <label>Bairro</label>
                                        <input type="text" name="bairro" id="bairro" maxlength="50" value="" />
                                    </dd>
                                    <dd>
                                        <label>Cidade<b class="obg">*</b></label>
                                        <input type="text" name="cidade" id="cidade" maxlength="50" readonly value="" />
                                    </dd>
                                    <dd>
                                        <label>UF<b class="obg">*</b></label>
                                        <input type="hidden" name="uf" id="uf" />
                                        <select name="uf2" id="uf2" disabled >
                                            <option value="AC">AC</option>
                                            <option value="AL">AL</option>
                                            <option value="AP">AP</option>
                                            <option value="AM">AM</option>
                                            <option value="BA">BA</option>
                                            <option value="CE">CE</option>
                                            <option value="DF">DF</option>
                                            <option value="ES">ES</option>
                                            <option value="GO">GO</option>
                                            <option value="MA">MA</option>
                                            <option value="MT">MT</option>
                                            <option value="MS">MS</option>
                                            <option value="MG">MG</option>
                                            <option value="PA">PA</option>
                                            <option value="PB">PB</option>
                                            <option value="PR">PR</option>
                                            <option value="PE">PE</option>
                                            <option value="PI">PI</option>
                                            <option value="RJ">RJ</option>
                                            <option value="RN">RN</option>
                                            <option value="RS">RS</option>
                                            <option value="RO">RO</option>
                                            <option value="RR">RR</option>
                                            <option value="SC">SC</option>
                                            <option value="SP">SP</option>
                                            <option value="SE">SE</option>
                                            <option value="TO">TO</option>
                                        </select>
                                    </dd>
                                </li>
                            <li class="titulo">
                                <dd><span>Dados do telegrama</span></dd>
                            </li>
                            <li>
                                <dd>
                                    <label><input type="radio" name="ckEnvio" value="0" checked /> ENVIO IMEDIATO</label>
                                </dd>
                                <dd>
                                    <label><input type="radio" name="ckEnvio" value="1" /> AGENDAR PARA:</label>
                                    <input type="text" name="dataColeta" id="dataColeta" style="width:60px;" value="<%= sdf2.format(new Date())%>" maxlength="10" onKeyPress="mascara(this, maskData)" />
                                    <input type="text" name="horaColeta" id="horaColeta" style="width:30px;" value="<%= hrf1.format(new Date())%>" maxlength="5" onKeyPress="mascara(this, maskHora)" />
                                    <img src="../../javascript/plugins/timepicker/clock.png" id="clockpick1" class="link_img" />
                                </dd>
                            </li>
                            <li>
                                <dd>
                                    <label>SERVIÇOS ADICIONAIS:</label>
                                    <input type="checkbox" name="ar" id="ar" value="AR" /> Pedido de Confirmação (AR)<br/>
                                    <input type="checkbox" name="copiaMsg" id="copiaMsg" value="CP" onclick="if(this.checked){document.getElementById('ddFormaCopia').className='mostrar';}else{document.getElementById('ddFormaCopia').className='esconder';}" /> Cópia de Telegrama
                                </dd>
                                <dd id='ddFormaCopia' class="esconder">
                                    <label>FORMA DE RECEBIMENTO DA CÓPIA:</label>
                                    <input type="radio" name="tipoRet" id="tipoRet" value="POSTAL" checked /> VIA POSTAL<br/>
                                    <input type="radio" name="tipoRet" id="tipoRet" value="E-MAIL" /> VIA E-MAIL <input type="text" name="email" id="email" value="" />
                                </dd>
                            </li>
                            <li>
                                <dd>
                                    <label>Digite abaixo a mesagem do telegrama:</label>
                                    <textarea style="width:800px;height:150px;" placeholder="Escreva aqui a mensagem do telegrama..." name="obs" id="obs"></textarea>
                                </dd>
                            </li>
                            <li>
                                <dd style="width: 100%;">
                                    <div class="buttons">
                                        <input type="hidden" name="nomeBD" value="<%= nomeBD %>"/>
                                        <input type="hidden" name="idCliente" value="<%= idCliente%>"/>
                                        <input type="hidden" name="nomeUser" value="<%= nomeUser%>"/>
                                        <button type="button" class="positive" onclick="return preencherCampos();"><img src="../../imagensNew/tick_circle.png" /> SOLICITAR TELEGRAMA</button>
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