
<%@page import="com.sun.org.apache.xerces.internal.impl.dv.DVFactoryException"%>
<%@page import="Entidade.ServicoECT"%>
<%@page import="Controle.ContrServicoECT"%>
<%@page import="Util.FormataString"%>
<%@page import="Entidade.Clientes"%>
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

        int idCli = Integer.parseInt(String.valueOf(session.getAttribute("idCliente")));

        Clientes cli = (Clientes) session.getAttribute("cliente");
        int nivel = (Integer) session.getAttribute("nivelUsuarioEmp");
        int idUser = (Integer) session.getAttribute("idUsuarioEmp");
        String nomeUser = (String) session.getAttribute("nomeUser");
        String numero = cli.getNumero();
        if (cli.getNumero() == null || cli.getNumero().equals("null")) {
            numero = "";
        }
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

            function preencherCampos() {
                var form = document.form1;

                if (form.numeroCli.value == "") {
                    alert('Preencha o N�MERO do destinat�rio!');
                    return false;
                }

                if (form.nome.value == "") {
                    alert('Preencha o NOME do remetente!');
                    return false;
                }
                if (form.cep.value == "") {
                    alert('Preencha o CEP do remetente!');
                    return false;
                }
                if (form.endereco.value == "") {
                    alert('Preencha o ENDERE�O do remetente!');
                    return false;
                }
                if (form.numero.value == "") {
                    alert('Preencha o N�MERO do remetente!');
                    return false;
                }
                if (form.cidade.value == "") {
                    alert('Preencha a CIDADE do remetente!');
                    return false;
                }
                if (form.uf.value == "") {
                    alert('Preencha a UF do remetente!');
                    return false;
                }
                if (form.qtdObjetos.value == "") {
                    alert('Preencha a Quantidade de Objetos!');
                    return false;
                }

                if (form.vd.value == '') {
                    form.vd.value = '0.00';
                }
                if (form.vd.value > 0 && form.vd.value < 12) {
                    alert('O Valor Declarado minimo � de R$ 12.00!');
                    return false;
                } else if (form.vd.value > 10000) {
                    alert('O Valor Declarado de Encomendas deve ser at� R$ 10.000,00!');
                    return false;
                }

                //PREENCHIMENTO DA CONFIRMA��O                
                document.getElementById("v_nome").innerHTML = form.nome.value;
                document.getElementById("v_email").innerHTML = form.email_destinatario.value;
                document.getElementById("v_celular").innerHTML = form.celular.value;
                document.getElementById("v_cep").innerHTML = form.cep.value;
                document.getElementById("v_rua").innerHTML = form.endereco.value + ", " + form.numero.value + ", " + form.complemento.value;
                document.getElementById("v_bairro").innerHTML = form.bairro.value + " - " + form.cidade.value + " / " + form.uf.value;
                document.getElementById("v_dest").innerHTML = form.nomeCli.value;
                document.getElementById("v_cep_dest").innerHTML = form.cepCli.value;
                document.getElementById("v_rua_dest").innerHTML = form.enderecoCli.value + ", " + form.numeroCli.value + ", " + form.complementoCli.value;
                document.getElementById("v_bairro_dest").innerHTML = form.bairroCli.value + " - " + form.cidadeCli.value + " / " + form.ufCli.value;


                document.getElementById("v_vd").innerHTML = "R$ " + form.vd.value;
                if (form.ar.value == 1) {
                    document.getElementById("v_ar").innerHTML = "<img width='12' src='../../imagensNew/tick_circle.png' />";
                } else {
                    document.getElementById("v_ar").innerHTML = "<img width='12' src='../../imagensNew/cross_circle.png' />";
                }

                chamaDivProtecao2();

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

            function chamaDivProtecao2() {
                var classe = document.getElementById("divProtecao").className;
                if (classe == "esconder") {
                    document.getElementById("divProtecao").className = "mostrar";
                    document.getElementById("divInteracao2").className = "mostrar";
                } else {
                    document.getElementById("divProtecao").className = "esconder";
                    document.getElementById("divInteracao2").className = "esconder";
                }
            }

            $(document).ready(function () {
                /* ao pressionar uma tecla em um campo que seja de class="pula" */
                $('#cep').keypress(function (e) {
                    /* 
                     * verifica se o evento � Keycode (para IE e outros browsers)
                     * se n�o for pega o evento Which (Firefox)
                     */
                    var tecla = (e.keyCode ? e.keyCode : e.which);

                    /* verifica se a tecla pressionada foi o ENTER */
                    if (tecla == 13) {
                        verPesquisarCepDest($('#cep').val());
                    }
                    /* impede o sumbit caso esteja dentro de um form */
                    //e.preventDefault(e);
                    //return false;
                })
            });

            function semNumero() {
                if (document.getElementById("sn").checked) {
                    document.getElementById("numero").value = "S/N";
                    document.getElementById("numero").disabled = true;
                } else {
                    document.getElementById("numero").value = "";
                    document.getElementById("numero").disabled = false;
                }
            }

            function semNumero2() {
                if (document.getElementById("sn2").checked) {
                    document.getElementById("numeroCli").value = "S/N";
                    document.getElementById("numeroCli").disabled = true;
                } else {
                    document.getElementById("numeroCli").value = "";
                    document.getElementById("numeroCli").disabled = false;
                }
            }
        </script>

        <title>Portal Postal | Pr� Postagem</title>

    </head>
    <body onload="<%if (!ContrClienteContrato.consultaStatusContrato(idCli, nomeBD)) {%> alert('O Contrato ou Cart�o de Postagem est�o Suspensos/Vencidos<br/><br/>Por Favor, entre em contato com a sua ag�ncia.<br/><br/>As Etiquetas ser�o geradas sem contrato at� a regulariza��o da situa��o.');
          <%}%>">
        <div id="divInteracao" class="esconder" style="top:10%; left:10%; right:10%; bottom:10%;" align="center"><input id="textointeracao" /></div>
        <div id="divInteracao2" class="esconder" style="top:10%; left:20%; right:20%; bottom:10%;" align="center">            
            <div style="width: 100%; margin-top: 15px;">
                <div style="width: 95%; text-align: left;">
                    <div id='titulo1' style="text-align: center;">CONFIRME OS DADOS DA SOLICITA��O</div>
                </div>
                <img width="99%" src="../../imagensNew/linha.jpg"/>
                <ul style="width: 97%; text-align: left;" class="ul_dados">      
                    <li>
                        <dd style="width: 100%; text-align: center;">
                            <span id="v_vc"></span>
                            <b>Valor Declarado:</b> <span id="v_vd"></span>
                            <b style='margin:0 20px 0 20px;'>|</b> <b>Aviso de Recebimento:</b> <span id="v_ar"></span>
                        </dd>
                    </li>        
                    <li><dd class="titulo">Dados do Remetente</dd></li>
                    <li>
                        <dd style="width: 40%;">
                            <label>Nome / Raz�o Social</label>
                            <span id="v_nome"></span>
                        </dd>
                        <dd style="width: 40%;">
                            <label>E-mail</label>
                            <span id="v_email"></span>
                        </dd>
                        <dd style="width: 20%;">
                            <label>Celular</label>
                            <span id="v_celular"></span>
                        </dd>
                    </li>
                    <li>
                        <dd style="width: 40%;">
                            <label>Endere�o</label>
                            <span id="v_rua"></span>
                        </dd>
                        <dd style="width: 40%;">
                            <label>Bairro - Cidade / UF</label>
                            <span id="v_bairro"></span>
                        </dd>
                        <dd style="width: 20%;">
                            <label>CEP</label>
                            <span id="v_cep"></span>
                        </dd>
                    </li>
                    <li><dd class="titulo">Dados do Destinat�rio</dd></li>
                    <li>
                        <dd style="width: 40%;">
                            <label>Nome / Raz�o Social</label>
                            <span id="v_dest"></span>
                        </dd>
                    </li>
                    <li>
                        <dd style="width: 40%;">
                            <label>Endere�o</label>
                            <span id="v_rua_dest"></span>
                        </dd>
                        <dd style="width: 40%;">
                            <label>Bairro - Cidade / UF</label>
                            <span id="v_bairro_dest"></span>
                        </dd>
                        <dd style="width: 20%;">
                            <label>CEP</label>
                            <span id="v_cep_dest"></span>
                        </dd>
                    </li>
                </ul>                                
                <div class="buttons">
                    <button type="button" class="positive" onclick="javascript:chamaDivProtecao2();
                abrirTelaEspera();
                document.form1.submit();"><img src="../../imagensNew/tick_circle.png" /> CONFIRMAR</button>
                    <button type="button" class="negative" onclick="chamaDivProtecao2();"><img src="../../imagensNew/cross_circle.png" /> CANCELAR</button>
                </div>
            </div>
        </div>
        <div id="divProtecao" class="esconder"></div>

        <%@ include file="../../Includes/menu_cliente.jsp" %>
        <%@ include file="../../Includes/telaMsg.jsp" %>

        <div id="divPrincipal" align="center">
            <div id="container">
                <div id="conteudo">

                    <div id="titulo1">Gerar Autoriza��o de Postagem (Reversa)</div>

                    <div id="form_etiqueta">
                        <ul <%if (!cli.getLogin_reversa().equals("")) {%> class="esconder" <%} else {%> class="ul_formulario" <%}%> id="ul_senha" style="width: 1136px;"  >
                            <li>                            
                                <dd style="margin-left: 50px;">
                                    <b>ENTRE EM CONTATO COM A SUA AG�NCIA PARA UTILIZAR A LOG�STICA REVERSA.<br/><br/></b>
                                    Envie os dados abaixo para criar um login do web service da logistica reversa:<br/>
                                    - N�mero do Contrato ECT: <%= cli.getNumContrato()%><br/>
                                    - Cart�o de Postagem da Log�stica Reversa: <%= cli.getCartaoPostagem()%> <br/>
                                    - C�digo Administrativo: <%= cli.getCodAdministrativo()%><br/>
                                    - Raz�o Social: <%= cli.getNome()%><br/>
                                    - E-mail: <%= cli.getEmail()%><br/><br/>
                                </dd>
                            </li>
                        </ul>

                        <form name="form1" action="../../ServReversaGerar" method="post">
                            <ul <%if (cli.getLogin_reversa().equals("")) {%> class="esconder" <%} else {%> class="ul_formulario" <%}%> style="width: 1136px;"  >
                                <a href="#" style="color: blue;">&nbsp;&bull; Para redefinir a senha entre em contato com a sua ag�ncia.<br/><br/></a>
                                <li class="titulo">
                                    <dd style="font-size: 11px;">DADOS DO DESTINAT�RIO</dd>
                                </li>
                                <li>
                                    <dd>
                                        <label>Nome / Raz�o Social<b class="obg">*</b></label>
                                        <input readonly type="text" name="nomeCli" size="55" value="<%if (cli.getNome_etq() == 1) {%><%= cli.getNomeFantasia()%><%} else {%><%= cli.getNome()%><%}%>" />
                                    </dd>
                                </li>
                                <li>
                                    <dd>
                                        <label>CEP</label>
                                        <input readonly type="text" name="cepCli" value="<%= FormataString.formataCep(cli.getCep() + "")%>" maxlength="9" size="8" onkeypress="mascara(this, maskCep)"/>
                                    </dd>
                                    <dd>
                                        <label>Endere�o</label>
                                        <input readonly type="text" name="enderecoCli" size="55" value="<%= cli.getEndereco()%>" />
                                    </dd>
                                    <dd>
                                        <label>N�<b class="obg">*</b></label>
                                        <input type="text" name="numeroCli" id="numeroCli" size="7" maxlength="6" value="<%= numero%>" onkeypress="mascara(this, maskNumeroRua)" />
                                        <input type="checkbox" name="sn2" id="sn2" value="S/N" onclick="semNumero2();" /> <span style="font-size: 14px;font-weight: bold">S/N</span>
                                    </dd>
                                    <dd>
                                        <label>Complemento</label>
                                        <input readonly type="text" name="complementoCli" size="20" value="<%= cli.getComplemento()%>" />
                                    </dd>
                                    <dd>
                                        <label>Bairro</label>
                                        <input readonly type="text" name="bairroCli" value="<%= cli.getBairro()%>" />
                                    </dd>
                                    <dd>
                                        <label>Cidade</label>
                                        <input readonly type="text" name="cidadeCli" value="<%= cli.getCidade()%>" />
                                    </dd>
                                    <dd>
                                        <label>UF</label>
                                        <input readonly type="text" name="ufCli" maxlength="2" size="2" value="<%= cli.getUf()%>" />
                                    </dd>
                                </li>


                                <li class="titulo">
                                    <dd style="font-size: 11px;">DADOS DO REMETENTE</dd>
                                </li>
                                <li id="singleDest1">
                                    <dd>
                                        <label>Nome / Raz�o Social<b class="obg">*</b><a onClick="verPesquisarDestinatario('single');"><img src="../../imagensNew/lupa.png" /> PESQUISAR CADASTRADOS</a></label>
                                        <input type="text" name="nome" id="nome" size="71" maxlength="80" value="" />
                                        <input type="hidden" name="empresa" id="empresa" size="33" maxlength="40" value="" />
                                        <input type="hidden" name="cpf_cnpj" id="cpf_cnpj" value="" maxlength="16" />
                                        <input type="hidden" name="aoscuidados" id="aoscuidados" maxlength="25" value="" />
                                    </dd>
                                    <dd>
                                        <label>E-mail</label>
                                        <input type="text" name="email_destinatario" id="email_destinatario" size="32" maxlength="100" value="" />
                                    </dd>
                                    <dd>
                                        <label>Celular</label>
                                        <input type="text" name="celular" id="celular" size="15" maxlength="20" value="" onkeydown="mascara(this, maskTelefone);" />
                                    </dd>
                                </li>
                                <li id="singleDest2">
                                    <dd>
                                        <label>CEP<b class="obg">*</b><a onclick="window.open('http://www.buscacep.correios.com.br', 'CORREIOS');" ><img src="../../imagensNew/question.png" /></a></label>
                                        <input type="text" name="cep" id="cep" size="8" value="" maxlength="9" onkeypress="mascara(this, maskCep);
                                                handleEnter();" onblur="verPesquisarCepDest(this.value);" />
                                    </dd>
                                    <dd>
                                        <label>Endere�o<b class="obg">*</b></label>
                                        <input type="text" name="endereco" id="endereco" size="55" maxlength="80" value="" />
                                    </dd>
                                    <dd>
                                        <label>N�<b class="obg">*</b></label>
                                        <input type="text" name="numero" id="numero" size="7" value="" maxlength="5" onkeypress="mascara(this, maskNumeroRua)" />
                                        <input type="checkbox" name="sn" id="sn" value="S/N" onclick="semNumero();" /> <span style="font-size: 14px;font-weight: bold">S/N</span>
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
                                    <dd style="font-size: 11px;">SERVI�O E ADICIONAIS</dd>
                                </li>
                                <li>
                                    <dd id="vlrDecl">
                                        <label>Servi�o da Postagem</label>
                                        <select class="form-control" style="width: 200px;" name="servico_1" id="servico_1">
                                            <%
                                                ArrayList<Integer> listaContrato = ContrClienteContrato.consultaContratoCliente(idCli, nomeBD);
                                                ArrayList<ServicoECT> listaServ = ContrServicoECT.consultaServicosReversa();
                                                if (listaServ != null) {
                                                    for (int i = 0; i < listaServ.size(); i++) {
                                                        ServicoECT sv = listaServ.get(i);
                                                        if (listaContrato.contains(sv.getCodECT()) && sv.getCodECT_reversa() > 0) {
                                                            out.println("<option value='" + sv.getCodECT_reversa() + "'>" + sv.getNomeServico() + "</option>");
                                                        }
                                                    }
                                                }
                                            %>
                                        </select>
                                    </dd>                                
                                    <dd>
                                        <label>QTD. DE OBJETOS</label>
                                        <input type="text" name="qtdObjetos" id="qtdObjetos" value="1" size="15" onkeypress="mascara(this, maskNumero)" />
                                    </dd>
                                    <dd id="vlrDecl">
                                        <label>Valor Declarado<span style="color:red;">(R$)</span></label>
                                        <input type="text" name="vd" id="obs" value="0.00" size="22" onkeypress="mascara(this, maskReal)" />
                                    </dd>
                                    <dd id="avisoRec">
                                        <label>A.R.</label>
                                        <select name="ar" id="ar">
                                            <option value="0">N�o</option>
                                            <option value="1">Sim</option>
                                        </select>
                                    </dd>
                                    <dd>
                                        <label>Autorizar aquisi��o de embalagem?</label>
                                        <select name="caixa" id="caixa">
                                            <option value="0">N�o</option>
                                            <option value="116600403;0">Caixa de Encomenda "B" (16x11x6 cm)</option>
                                            <option value="116600055;0">Caixa Encomenda 01 (18x13,5x9 cm)</option>
                                            <option value="116600063;0">Caixa Encomenda 02 (27x18x9 cm)</option>
                                            <option value="116600071;2">Caixa Encomenda 03 (27x22,5x13,5 cm)</option>
                                            <option value="116600080;0">Caixa Encomenda 04 (36x27x18 cm)</option>
                                            <option value="116600160;2">Caixa Encomenda 05 (54x36x27 cm)</option>
                                            <option value="116600179;0">Caixa Encomenda 06 (36x27x27 cm)</option>
                                            <option value="116600187;0">Caixa Encomenda 07 (36x28x4 cm)</option>
                                            <option value="765000660;0">Envelope Bolha Grande (20x28 cm)</option>
                                            <option value="765000652;2">Envelope Bolha M�dio (21x18 cm)</option>
                                            <option value="765000644;2">Envelope SEDEX Pl�stico Grande (40x28 cm)</option>
                                            <option value="765000636;0">Envelope SEDEX Pl�stico M�dio (35,3x25 cm)</option>
                                        </select>
                                    </dd>
                                    <dd>
                                        <label>Validade Autoriza��o</label>
                                        <select style="width: 150px;" name="dataAgendamento">						
                                            <option value="5">5 Dias</option>
                                            <option value="6">6 Dias</option>
                                            <option value="7">7 Dias</option>
                                            <option value="8">8 Dias</option>
                                            <option value="9">9 Dias</option>
                                            <option value="10">10 Dias</option>
                                            <option value="11">11 Dias</option>
                                            <option value="12">12 Dias</option>
                                            <option value="13">13 Dias</option>
                                            <option value="14">14 Dias</option>
                                            <option value="15">15 Dias</option>
                                            <option value="16">16 Dias</option>
                                            <option value="17">17 Dias</option>
                                            <option value="18">18 Dias</option>
                                            <option value="19">19 Dias</option>
                                            <option value="20">20 Dias</option>
                                            <option value="21">21 Dias</option>
                                            <option value="22">22 Dias</option>
                                            <option value="23">23 Dias</option>
                                            <option value="24">24 Dias</option>
                                            <option value="25">25 Dias</option>
                                            <option value="26">26 Dias</option>
                                            <option value="27">27 Dias</option>
                                            <option value="28">28 Dias</option>
                                            <option value="29">29 Dias</option>
                                            <option value="30" selected>30 Dias</option>
                                        </select>
                                    </dd>     
                                </li>
                                <li>
                                    <dd style="width: 100%;">
                                        <div class="buttons">
                                            <input type="hidden" name="login" value="<%= cli.getLogin_reversa()%>"  />
                                            <input type="hidden" name="senha" value="<%= cli.getSenha_reversa()%>" />
                                            <input type="hidden" name="cartaoPostagem" value="<%= cli.getCartao_reversa()%>" />
                                            <input type="hidden" name="idDestinatario" id="idDestinatario" value="0" />
                                            <input type="hidden" name="nomeOrig" id="nomeOrig" value="" />
                                            <input type="hidden" name="codAdm" value="<%= cli.getCodAdministrativo()%>" />
                                            <input type="hidden" name="contratoEct" value="<%= cli.getNumContrato()%>" />
                                            <input type="hidden" name="idCliente" value="<%= idCli%>" />
                                            <input type="hidden" name="nomeBD" id="nomeBD" value="<%= nomeBD%>" />
                                            <input type="hidden" name="idUser" value="<%= idUser%>" />
                                            <input type="hidden" name="nomeUser" value="<%= nomeUser%>" />
                                            <button type="button" class="positive" onclick="return preencherCampos();"><img src="../../imagensNew/tick_circle.png" /> GERAR AUTORIZA��O DE POSTAGEM</button>
                                        </div>
                                    </dd>
                                </li>
                            </ul>

                        </form>
                    </div>
                    <img width="100%" src="../../imagensNew/linha.jpg"/>
                    <script type="text/javascript">
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

                </div>
            </div>
        </div>
    </body>
</html>
<%}%>