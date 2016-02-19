
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

    response.setHeader("Cache-Control","no-cache");
    response.setHeader("Pragma","no-cache");
    response.setDateHeader ("Expires", -1);

    String nomeBD = (String) session.getAttribute("nomeBD");
    if (nomeBD == null) {
        response.sendRedirect("../../index.jsp?msg=Sua sessao expirou! Para voltar ao Portal fa�a seu login novamente!");
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
            jQuery(document).ready(function($) {
                $(window).load(function() {
                    $('nome').val(' ');
                    setTimeout(function() {
                        $('nome').val('');
                    }, 20);
                });
            });
            
            function validaDados(){
                //console.log($("#cep").val());
                $.ajax({
                    method: "POST",
                    url: "../../AjaxPages/ajax_cep_json.jsp",
                    data: {cep: $("#cep").val()},
                    dataType: 'json'
                }).done(function (retorno) {
                    if (retorno.logradouro.toUpperCase() === 'CEP INEXISTENTE') {
                        //console.log(retorno.cep + " <<< " + retorno.logradouro.toUpperCase());
                        alert("Este CEP "+$("#cep").val()+" � inexistente!\n\nConsulte o CEP correto no site dos Correios.");
                    } else {
                        //console.log(retorno.cep + " >>> " + retorno.logradouro.toUpperCase());                        
                        preencherCampos();
                    }
                });
            }

            function preencherCampos() {
                var form = document.form1;                
                
                if (form.servico.value === 'SIMPLES' || form.servico.value === 'CARTA') {
                    alert("ATEN��O!!!<br/><br/>O peso maximo da CARTA � de 500g!<br/>Apos este peso a CARTA vira SEDEX!");
                } else if (form.servico.value === 'SEDEX10') {
                    alert("ATEN��O!!!<br/><br/>O peso maximo do SEDEX 10 � de 10Kg!");
                } else if (form.servico.value === 'SEDEX12') {
                    alert("ATEN��O!!!<br/><br/>O peso maximo do SEDEX 12 � de 10Kg!");
                } else if (form.servico.value === 'ESEDEX') {
                    alert("ATEN��O!!!<br/><br/>O peso maximo do E-SEDEX � de 15Kg!");
                }

                if (form.nome.value === '') {
                    alert('Preencha o NOME do destinat�rio!');
                    return false;
                }

                var auxOutros = document.getElementById("servico_1").value.split(';');
                if(form.servico.value !== 'OUTROS' || auxOutros[2] !== 'INT'){
                    if (form.cep.value === '' || form.cep.value.length !== 9) {
                        alert('Preencha um CEP v�lido do destinat�rio!');
                        return false;
                    }
                    if (form.uf.value === '') {
                        alert('Preencha a UF do destinat�rio!');
                        return false;
                    }
                }

                if (form.endereco.value === '') {
                    alert('Preencha o ENDERE�O do destinat�rio!');
                    return false;
                }
                if (form.numero.value === '') {
                    alert('Preencha o N�MERO do destinat�rio!');
                    return false;
                }
                if (form.cidade.value === '') {
                    alert('Preencha a CIDADE do destinat�rio!');
                    return false;
                }
                    
                if (form.departamento.value === '-1') {
                    alert('Escolha o Departamento/Centro de Custo para a Postagem!\n\nCaso n�o exista o departameto desejado,\npe�a para a ag�ncia incluir no cadastro!');
                    return false;
                }
                if (form.servico.value === 'CARTA' && form.tipoCarta.value === '') {
                    alert('Escolha o Tipo da Carta a ser Postado!');
                    return false;
                }
                if (form.servico.value === 'SEDEXC' || form.servico.value === 'PAC_COB') {
                    if (form.cpf_cnpj.value === '') {
                        alert('Preencha o CPF ou CNPJ do destinat�rio!');
                        return false;
                    } else if (!isCPFCNPJ(form.cpf_cnpj.value)) {
                        return false;
                    } else if (form.valor_cobrar.value === '' || parseFloat(form.valor_cobrar.value) <= 0) {
                        if(!confirm("O valor a cobrar est� vazio!\nSer� cobrado somente o valor da postagem.\n\nDeseja continuar mesmo assim?")){                            
                            return false;
                        }
                    } else if (parseFloat(form.valor_cobrar.value) > 3500) {
                        alert('Valor m�ximo a cobrar permitido � de R$ 3.500,00!');
                        return false;
                    }
                }
                
                if (form.servico.value === 'OUTROS') {                    
                    if(auxOutros[1] === 'PPI' && form.obs.value === ''){
                        alert('Preencha o N� do Processo!');
                        return false;
                    }else if(auxOutros[1] === 'PPI'){
                        form.conteudo.value = form.obs.value;
                    }
                    if(auxOutros[3] === 'INT' && form.pais.value === ''){
                        alert('Selecione um pa�s para a postagem!');
                        return false;
                    }
                }

                if (form.vd.value === '') {
                    form.vd.value = '0.00';
                }
                
                if (form.vd.value > 0 && form.vd.value < 14) {
                    alert('O Valor Declarado minimo � de R$ 14,00!');
                    return false;
                } else if (form.servico.value === 'CARTA' && form.vd.value > 500) {
                    alert('O Valor Declarado Maximo de Cartas � de R$ 500,00!');
                    return false;
                } else if (form.vd.value > 10000) {
                    alert('O Valor Declarado Maximo de Encomendas � de R$ 10.000,00!');
                    return false;
                }

                //PREENCHIMENTO DA CONFIRMA��O                
                document.getElementById("v_nome").innerHTML = form.nome.value;
                document.getElementById("v_cuidados").innerHTML = form.aoscuidados.value;
                document.getElementById("v_empresa").innerHTML = form.empresa.value;
                document.getElementById("v_cep").innerHTML = form.cep.value;
                document.getElementById("v_rua").innerHTML = form.endereco.value + ", " + form.numero.value + ", " + form.complemento.value;
                document.getElementById("v_bairro").innerHTML = form.bairro.value + " - " + form.cidade.value + " / " + form.uf.value;
                document.getElementById("v_remetente").innerHTML = form.nomeCli.value;
                var aux = form.departamento.value.split(';');
                document.getElementById("v_departamento").innerHTML = aux[1];
                
            if (form.servico.value === 'SEDEXC' || form.servico.value === 'PAC_COB') {
                    document.getElementById("v_vc").innerHTML = "<b>Valor a COBRAR:</b> R$ " + form.valor_cobrar.value + " <b style='margin:0 20px 0 20px;'>|</b> ";
                } else {
                    document.getElementById("v_vc").innerHTML = "";
                }

                document.getElementById("v_vd").innerHTML = "R$ " + form.vd.value;
                if (form.ar.value === '1') {
                    document.getElementById("v_ar").innerHTML = "<img width='12' src='../../imagensNew/tick_circle.png' />";
                } else {
                    document.getElementById("v_ar").innerHTML = "<img width='12' src='../../imagensNew/cross_circle.png' />";
                }
                if (form.mp.value === '1') {
                    document.getElementById("v_mp").innerHTML = "<img width='12' src='../../imagensNew/tick_circle.png' />";
                } else {
                    document.getElementById("v_mp").innerHTML = "<img width='12' src='../../imagensNew/cross_circle.png' />";
                }

                document.getElementById("v_servico").innerHTML = "<img src='../../imagensNew/chancelas/" + form.servico.value + ".png' />";
                if (form.tipoCarta.value === '0') {
                    document.getElementById("v_servico").innerHTML = "<img src='../../imagensNew/chancelas/SIMPLES.png' />";
                }


                document.getElementById("v_tipo").innerHTML = "PACOTE";
                if (form.tipo.value === 'ENV') {
                    document.getElementById("v_tipo").innerHTML = "ENVELOPE";
                }

                //cpf
                //obs
                //conteudo
                //nf

                //VERIFICA ABRANGENCIA DE SERVI�OS
                if (form.servico.value === 'ESEDEX') {
                    verPesquisarAbrangenciaServ(form.cep.value, 'ESEDEX');
                } else if (form.servico.value === 'SEDEX10') {
                    verPesquisarAbrangenciaServ(form.cep.value, 'SEDEX10');
                } else if (form.servico.value === 'SEDEX12') {
                    verPesquisarAbrangenciaServ(form.cep.value, 'SEDEX12');
                } else if (form.servico.value === 'PAX') {
                    verPesquisarAbrangenciaServ(form.cep.value, 'PAX');
                } else {
                    chamaDivProtecao2();
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

            function trocaTipoCarta(tp) {
                if (tp === '1') {
                    document.getElementById("vlrDecl").className = 'mostrar';
                    document.getElementById("maoProp").className = 'mostrar';
                    document.getElementById("avisoRec").className = 'mostrar';
                } else {
                    document.getElementById("vlrDecl").className = 'esconder';
                    document.getElementById("maoProp").className = 'esconder';
                    document.getElementById("avisoRec").className = 'esconder';
                    document.getElementById("vd").value = '0.00';
                    document.getElementById("mp").value = '0';
                    document.getElementById("ar").value = '0';
                }
            }

            function alteraServ(serv) {
                if (serv === '') {
                    alert('Este usu�rio n�o pode efetuar postagem para nenhum servi�o!');
                    return false;
                }

                document.getElementById('form_aviso').className = "esconder";
                document.getElementById('form_etiqueta').className = "mostrar";

                document.getElementById('PAC').className = "";
                document.getElementById('PAX').className = "";
                document.getElementById('SEDEX').className = "";
                document.getElementById('SEDEXC').className = "";
                document.getElementById('SEDEX10').className = "";
                document.getElementById('SEDEX12').className = "";
                document.getElementById('ESEDEX').className = "";
                document.getElementById('CARTA').className = "";
                document.getElementById('OUTROS').className = "";
                document.getElementById(serv).className = "ativo";
                document.getElementById("servico").value = serv;
                document.getElementById("tipoCarta").selectedIndex = 0;
                document.getElementById("outros_servicos").className = 'esconder';
                document.getElementById("tipoCt").className = 'esconder';
                document.getElementById("vlrDecl").className = 'mostrar';
                document.getElementById("maoProp").className = 'mostrar';
                document.getElementById("avisoRec").className = 'mostrar';
                document.getElementById('labelObs').innerHTML = "Observa��es<span style='color:red;'>(APARECE SOMENTE NA ETIQUETA)</span>";
                
                document.getElementById("cidade").readOnly = true;
                document.getElementById("paisDest").className = 'esconder';
                document.getElementById("ddUf").className = 'mostrar';
                document.getElementById("tipo").selectedIndex = 2;
                $('#cep').attr('onkeypress','mascara(this, maskCep);handleEnter();');
                $('#cep').attr('onblur','verPesquisarCepDest(this.value);');
                $('#cep').attr('onchange','mascara(this, maskCep);');

                if (serv === 'CARTA') {
                    document.getElementById("tipoPacote").className = 'esconder';
                    document.getElementById("vlrDecl").className = 'esconder';
                    document.getElementById("maoProp").className = 'esconder';
                    document.getElementById("avisoRec").className = 'esconder';
                    document.getElementById("tipoCt").className = 'mostrar';
                    document.getElementById("tipo").selectedIndex = 1;
                } else if (serv === 'PAC' || serv === 'PAX' || serv === 'ESEDEX') {
                    document.getElementById("tipoPacote").className = 'esconder';
                    document.getElementById("tipo").selectedIndex = 2;
                } else if (serv === 'SEDEX10' || serv === 'SEDEX12') {
                    document.getElementById("tipoPacote").className = 'esconder';
                    document.getElementById("tipo").selectedIndex = 2;
                } else if (serv === 'SEDEXC' || serv === 'PAC_COB') {
                    document.getElementById("tipoPacote").className = 'esconder';
                    document.getElementById("tipo").selectedIndex = 2;
                    document.getElementById("vlrCobrar").className = 'mostrar';
                    document.getElementById("cpf_obg").className = 'obg';
                } else if (serv === 'OUTROS') {
                    document.getElementById("tipo").selectedIndex = 2;
                    document.getElementById("tipoPacote").className = 'esconder';
                    document.getElementById("vlrCobrar").className = 'esconder';
                    document.getElementById("outros_servicos").className = 'mostrar';
                    alteraOutroServ(document.getElementById("servico_1").value);
                } else {
                    document.getElementById("vlrCobrar").className = 'esconder';
                    document.getElementById("cpf_obg").className = 'esconder';
                    document.getElementById("tipoPacote").className = 'esconder';
                    document.getElementById("tipo").selectedIndex = 2;
                }

                document.getElementById('nome').focus();
            }

            $(document).ready(function() {
                /* ao pressionar uma tecla em um campo que seja de class="pula" */
                $('#cep').keypress(function(e) {
                    /* 
                     * verifica se o evento � Keycode (para IE e outros browsers)
                     * se n�o for pega o evento Which (Firefox)
                     */
                    var tecla = (e.keyCode ? e.keyCode : e.which);

                    /* verifica se a tecla pressionada foi o ENTER */
                    if (tecla === 13) {
                        verPesquisarCepDest($('#cep').val());
                    }
                    /* impede o sumbit caso esteja dentro de um form */
                    //e.preventDefault(e);
                    //return false;
                });
            });

            function Check200(nomeCheck, checado) {  //JS_PORTAL_POSTAL
                var count = 0;
                $("[name='" + nomeCheck + "']").each(function() {
                    count++;
                    if (count <= 200) {
                        if (checado === true) {
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
                //document.getElementById('formato').value = formato;
                var flag = true;
                var qtdSelecionada = 0;
                $("[name='ids']:checked").each(function() {
                    qtdSelecionada++;
                    flag = false;
                });

                if (qtdSelecionada > 200) {
                    alert("Quantidade m�xima de impress�o de 200 etiquetas de cada vez!");
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

            function excluirVenda(idVenda, numObj) {
                if (confirm('Tem certeza que deseja excluir?')) {
                    document.getElementById('idVendaDel').value = idVenda;
                    document.getElementById('numObjetoDel').value = numObj;
                    document.formDel.submit();
                } else {
                    document.getElementById('idVendaDel').value = '';
                    document.getElementById('numObjetoDel').value = '';
                    return false;
                }
            }

            function semNumero() {
                if (document.getElementById("sn").checked) {
                    document.getElementById("numero").value = "S/N";
                    document.getElementById("numero").disabled = true;
                } else {
                    document.getElementById("numero").value = "";
                    document.getElementById("numero").disabled = false;
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
                    alert('A postagem deve conter pelo menos um destinat�rio!');
                    return false;
                }
            }

            function cancelaMulti() {
                document.getElementById('flagMulti').value = '0';
                document.getElementById('singleDest1').className = 'mostrar';
                document.getElementById('singleDest2').className = 'mostrar';
                document.getElementById('multiDest').className = 'esconder';
                document.getElementById('nome').focus();
            }

            function somarQtd(campo, min, max, escala, mult) {
                var qtd = document.getElementById(campo).value;

                if (qtd > min && mult < 0) {
                    qtd = parseInt(qtd) + (escala * mult);
                } else if (qtd < max && mult > 0) {
                    qtd = parseInt(qtd) + (escala * mult);
                } else if (qtd === min && mult < 0) {
                    alert("Quantidade m�nima � de " + min + " etiquetas!");
                } else if (qtd === max && mult > 0) {
                    alert("Quantidade m�xima � de " + max + " etiquetas!");
                }

                document.getElementById(campo).value = qtd;
                document.getElementById("qtdPost").value = qtd;
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
                            data: {nomePesquisa: request.term, servico: document.form1.servico.value, tipoDestino: document.getElementById("servico_1").value},
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
                        $("#idDestinatario").val(ui.item.value);
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
                        
                        if(ui.item.destino === 'NAC'){                            
                            $("#uf").val(ui.item.uf);                        
                            $("#uf2").find("option").filter(function(){
                                return ( ($(this).val() === ui.item.uf) || ($(this).text() === ui.item.uf) );
                            }).prop('selected', true);
                        }else{
                            $("#estado").val(ui.item.uf);      
                            $("#pais").find("option").filter(function(){
                                return ( ($(this).val() === ui.item.pais) || ($(this).text() === ui.item.pais) );
                            }).prop('selected', true);
                        }
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
                            //"<div style='float: right;'><a onclick='tiraLista("+item.value+");'><img src='../../imagensNew/trash-cancel.gif' /></a></div>" +
                            "<div style='clear:both;'>" +
                            "</div>").appendTo(ul);
                };


            });

            function limparContato() {
                $("#idDestinatario").val('0');
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
            
            function alteraOutroServ(serv) {
                var aux = serv.split(';');
                var codECT = aux[0];
                var grupoServ = aux[1];
                var tipoPostagem = aux[2];
                
                if(grupoServ === 'PPI'){
                    document.getElementById('labelObs').innerHTML = "N� do Processo<span style='color:red;'>(APARECE SOMENTE NA ETIQUETA)</span>";
                }else{
                    document.getElementById('labelObs').innerHTML = "Observa��es<span style='color:red;'>(APARECE SOMENTE NA ETIQUETA)</span>";
                }
                
                if(tipoPostagem === 'INT'){    
                    document.getElementById("ddUf").className = 'esconder';
                    document.getElementById("paisDest").className = 'mostrar';
                    document.getElementById("cidade").readOnly = false;
                    $('#cep').attr('onkeypress','');
                    $('#cep').attr('onblur','');
                    $('#cep').attr('onchange','');
                    document.getElementById("maoProp").className = 'esconder';
                    document.getElementById("avisoRec").className = 'esconder';
                    $.ajax({
                        method: "POST",
                        url: "../AjaxPages/monta_combo_paises.jsp",
                        data: {codECT: ''},
                        dataType: 'html'
                    }).done(function (data) {
                        $('#selectPaises').html(data);
                        //LoadSelect2Script(selectCliente);
                    });
                }else{
                    document.getElementById("maoProp").className = 'mostrar';
                    document.getElementById("avisoRec").className = 'mostrar';
                    document.getElementById("cidade").readOnly = true;
                    document.getElementById("paisDest").className = 'esconder';
                    document.getElementById("ddUf").className = 'mostrar';
                    $('#cep').attr('onkeypress','mascara(this, maskCep);handleEnter();');
                    $('#cep').attr('onblur','verPesquisarCepDest(this.value);');
                    $('#cep').attr('onchange','mascara(this, maskCep);');
                }
            }
        </script>

        <title>Portal Postal | Pr� Postagem</title>

    </head>
    <body onload="fecharTelaEspera();<%if (!ContrClienteContrato.consultaStatusContrato(idCli, nomeBD)) {%> alert('O Contrato ou Cart�o de Postagem est�o Suspensos/Vencidos<br/><br/>Por Favor, entre em contato com a sua ag�ncia.<br/><br/>As Etiquetas ser�o geradas sem contrato at� a regulariza��o da situa��o.');<%}%>">
        <div id="divInteracao" class="esconder" style="top:10%; left:10%; right:10%; bottom:10%;" align="center"><input id="textointeracao" /></div>
        <div id="divInteracao2" class="esconder" style="top:10%; left:12%; right:12%; bottom:10%;" align="center">            
            <div style="width: 100%; margin-top: 15px;">
                <div style="width: 95%; text-align: left;">
                    <div id='titulo1' style="text-align: center;">CONFIRME OS DADOS DA PR�-VENDA</div>
                </div>
                <img width="99%" src="../../imagensNew/linha.jpg"/>

                <ul style="width: 97%; text-align: left;" class="ul_dados">      
                    <li>
                        <dd style="width: 100%; text-align: center;">
                            <span id="v_servico"><img src="../../imagensNew/sedex10.png" border="0" /></span><br/>
                            <span id="v_tipo" style="color: red; font-weight: bold; font-size: 15px;">PACOTE</span>
                        </dd>
                    </li>
                    <li>
                        <dd style="width: 100%; text-align: center;">
                            <span id="v_vc"></span>
                            <b>VD:</b> <span id="v_vd"></span>
                            <b style='margin:0 20px 0 20px;'>|</b> <b>AR:</b> <span id="v_ar"></span>
                            <b style='margin:0 20px 0 20px;'>|</b> <b>MP:</b> <span id="v_mp"><img width="12" src="../../imagensNew/cross_circle.png" /></span>
                        </dd>
                    </li>        
                    <li><dd class="titulo">Dados do Remetente</dd></li>
                    <li>
                        <dd style="width: 40%;">
                            <label>Nome / Raz�o Social</label>
                            <span id="v_remetente"></span>
                        </dd>
                        <dd style="width: 40%;">
                            <label>Departamento</label>
                            <span id="v_departamento"></span>
                        </dd>
                    </li>
                    <li><dd class="titulo">Dados do Destinat�rio</dd></li>
                    <li>
                        <dd style="width: 40%;">
                            <label>Nome / Raz�o Social</label>
                            <span id="v_nome"></span>
                        </dd>
                        <dd style="width: 40%;">
                            <label>Empresa</label>
                            <span id="v_empresa"></span>
                        </dd>
                        <dd style="width: 20%;">
                            <label>Aos Cuidados</label>
                            <span id="v_cuidados"></span>
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
                    <li><dd class="titulo"> </dd></li>
                </ul>                                
                <div class="buttons">
                    <b>Quantidade: </b><input type="text" name="qtdPost" size="2" id="qtdPost" value="1"/>&nbsp;<img style="cursor: pointer;" class="alignImg" height="16" src="../../imagensNew/minus-button.png" onclick="somarQtd('quantidade', 1, 50, 1, -1);" /><img style="cursor: pointer;" class="alignImg" height="16" src="../../imagensNew/plus-button.png" onclick="somarQtd('quantidade', 1, 10, 1, 1);" />
                    <br/><br/>
                    <button type="button" class="positive" onclick="javascript:chamaDivProtecao2();
                            abrirTelaEspera();
                            document.form1.submit();"><img src="../../imagensNew/tick_circle.png" /> CONFIRMAR</button>
                    <button type="button" class="negative" onclick="chamaDivProtecao2();"><img src="../../imagensNew/cross_circle.png" /> CANCELAR</button>
                </div>
            </div>
        </div>
        <div id="divProtecao" class="esconder"></div>

        
        <div class="mostrar" id="protecaoTelaEspera">
            <div id="telaEspera">Por Favor, Aguarde...<br/><br/><img src="../../imagensNew/loader.gif" /></div>
        </div>
        
        
        <%@ include file="../../Includes/menu_cliente.jsp" %>
        <%@ include file="../../Includes/telaMsg.jsp" %>

        <div id="divPrincipal" align="center">
            <div id="container">
                <div id="conteudo">

                    <div id="titulo1">Gerar Etiquetas (Pr�-Postagem)</div>

                    <form name="form1" action="../../ServPreVenda" method="post" autocomplete="off">
                        <ul class="ul_tab" style="width: 1160px;">
                            <li>
                                <%--<dl style='width:105px; border-left: 1px solid #CCC;' id="PAC" <%if (acs.contains(1)) {%> onclick="alteraServ('PAC');" <%} else {%> onclick="alert('Este usu�rio n�o tem permis�o para utilizar este servi�o!');" <%}%>>
                                    <dd><b class='serv'>PAC</b><br/>&nbsp;--<img src="../../imagensNew/pac.png" border="0" />--</dd>                                    --%>
                                    <%
                                        //CONSULTA SE TEM CONTRATO PAC
                                        int codPac = ContrClienteContrato.consultaContratoClienteGrupoServ(idCli, "PAC", nomeBD);
                                        int qtdPac = ContrClienteEtiquetas.contaQtdUtilizadaPorGrupoServ("PAC", 0, idCli, nomeBD);
                                        if (!acs.contains(1)) {
                                            out.println("<dl style='width:90px; border-left: 1px solid #CCC;' id='PAC' onclick=\"alert('Este usu�rio n�o tem permis�o para utilizar este servi�o!');\">");
                                            out.println("<dd><b class='serv'>PAC</b><br/>&nbsp;");//<img src="../../imagensNew/pac.png" border="0" />
                                            out.println("<dd><p><b style='color:gray;'>BLOQUEADO</b></p></dd>");
                                            out.println("<input type='hidden' name='cod_pac' value='" + codPac + "' />");
                                            out.println("</dl>");
                                        } else if (codPac != 0 && qtdPac > 0) {
                                            out.println("<dl style='width:90px; border-left: 1px solid #CCC;' id='PAC' onclick=\"alteraServ('PAC');\" >");
                                            out.println("<dd><b class='serv'>PAC</b><br/>&nbsp;");
                                            out.println("<dd><p>QTD.: <b>" + qtdPac + "</b></p></dd>");
                                            out.println("<input type='hidden' name='cod_pac' value='" + codPac + "' />");
                                            out.println("</dl>");
                                        } else if (codPac != 0) {
                                            out.println("<dl style='width:90px; border-left: 1px solid #CCC;' id='PAC' onclick=\"alteraServ('PAC');\" >");
                                            out.println("<dd><b class='serv'>PAC</b><br/>&nbsp;");
                                            out.println("<dd><p><b>S/ ETIQUETA</b></p></dd>");
                                            out.println("<input type='hidden' name='cod_pac' value='" + codPac + "' />");
                                            out.println("</dl>");
                                        } else {
                                            out.println("<dl style='width:90px; border-left: 1px solid #CCC;' id='PAC' onclick=\"alteraServ('PAC');\" >");
                                            out.println("<dd><b class='serv'>PAC</b><br/>&nbsp;");
                                            out.println("<dd><p><b style='color:green;'>� VISTA</b></p></dd>");
                                            out.println("<input type='hidden' name='cod_pac' value='' />");
                                            out.println("</dl>");
                                        }

                                        //CONSULTA SE TEM CONTRATO PAX
                                        int codPax = ContrClienteContrato.consultaContratoClienteGrupoServ(idCli, "PAX", nomeBD);
                                        int qtdPax = ContrClienteEtiquetas.contaQtdUtilizadaPorGrupoServ("PAX", 0, idCli, nomeBD);
                                        if (!acs.contains(1)) {
                                            out.println("<dl style='width:90px; color:gray;' id='PAX' onclick=\"alert('Este usu�rio n�o tem permis�o para utilizar este servi�o!');\">");
                                            out.println("<dd><b class='serv'>PAC</b><br/>GRANDES FORMATOS</dd>");
                                            out.println("<dd><p><b style='color:gray;'>BLOQUEADO</b></p></dd>");
                                            out.println("<input type='hidden' name='cod_pax' value='" + codPax + "' />");
                                            out.println("</dl>");
                                        } else if (codPax != 0 && qtdPax > 0) {
                                            out.println("<dl style='width:90px;' id='PAX' onclick=\"alteraServ('PAX');\">");
                                            //out.println("<dd><img src='../../imagensNew/pax.png' border='0' /></dd>");
                                            out.println("<dd><b class='serv'>PAC</b><br/>GRANDES FORMATOS</dd>");
                                            out.println("<dd><p>QTD.: <b>" + qtdPax + "</b></p></dd>");
                                            out.println("<input type='hidden' name='cod_pax' value='" + codPax + "' />");
                                            out.println("</dl>");
                                        } else if (codPax != 0 && qtdPax == 0) {
                                            out.println("<dl style='width:90px;' id='PAX' onclick=\"alert('Para utilizar este servi�o � necess�rio cadastrar etiquetas!');\">");
                                            //out.println("<dd><img src='../../imagensNew/pax_c.png' border='0' /></dd>");
                                            out.println("<dd><b class='serv'>PAC</b><br/>GRANDES FORMATOS</dd>");
                                            out.println("<dd><p><b>S/ ETIQUETA</b></p></dd>");
                                            out.println("<input type='hidden' name='cod_pax' value='' />");
                                            out.println("</dl>");
                                        }else {
                                            out.println("<dl style='width:90px; color:gray;' id='PAX' onclick=\"alert('� necess�rio ter Contrato ECT para utilizar este servi�o!');\">");
                                            out.println("<dd><b class='serv'>PAC</b><br/>GRANDES FORMATOS</dd>");
                                            out.println("<dd><p><b style='color:gray;'>BLOQUEADO</b></p></dd>");
                                            out.println("<input type='hidden' name='cod_pax' value='" + codPax + "' />");
                                            out.println("</dl>");
                                        }

                                        //CONSULTA SE TEM CONTRATO SEDEX
                                        int codSedex = ContrClienteContrato.consultaContratoClienteGrupoServ(idCli, "SEDEX", nomeBD);
                                        int qtdSedex = ContrClienteEtiquetas.contaQtdUtilizadaPorGrupoServ("SEDEX", 0, idCli, nomeBD);
                                        if (!acs.contains(2)) {
                                            out.println("<dl style='width:90px; color:gray;' id='SEDEX' onclick=\"alert('Este usu�rio n�o tem permis�o para utilizar este servi�o!');\" >");
                                            out.println("<dd><b class='serv'>SEDEX</b><br/>&nbsp;");//<img src="../../imagensNew/sedex.png" border="0" />
                                            out.println("<dd><p><b style='color:gray;'>BLOQUEADO</b></p></dd>");
                                            out.println("<input type='hidden' name='cod_sedex' value='" + codSedex + "' />");
                                            out.println("</dl>");
                                        } else if (codSedex != 0 && qtdSedex > 0) {
                                            out.println("<dl style='width:90px;' id='SEDEX' onclick=\"alteraServ('SEDEX');\" >");
                                            out.println("<dd><b class='serv'>SEDEX</b><br/>&nbsp;");//<img src="../../imagensNew/sedex.png" border="0" />
                                            out.println("<dd><p>QTD.: <b>" + qtdSedex + "</b></p></dd>");
                                            out.println("<input type='hidden' name='cod_sedex' value='" + codSedex + "' />");
                                            out.println("</dl>");
                                        } else if (codSedex != 0) {
                                            out.println("<dl style='width:90px;' id='SEDEX' onclick=\"alteraServ('SEDEX');\" >");
                                            out.println("<dd><b class='serv'>SEDEX</b><br/>&nbsp;");//<img src="../../imagensNew/sedex.png" border="0" />
                                            out.println("<dd><p><b>S/ ETIQUETA</b></p></dd>");
                                            out.println("<input type='hidden' name='cod_sedex' value='" + codSedex + "' />");
                                            out.println("</dl>");
                                        } else {
                                            out.println("<dl style='width:90px;' id='SEDEX' onclick=\"alteraServ('SEDEX');\" >");
                                            out.println("<dd><b class='serv'>SEDEX</b><br/>&nbsp;");//<img src="../../imagensNew/sedex.png" border="0" />
                                            out.println("<dd><p><b style='color:green;'>� VISTA</b></p></dd>");
                                            out.println("<input type='hidden' name='cod_sedex' value='' />");
                                            out.println("</dl>");
                                        }


                                        //CONSULTA SE TEM CONTRATO SEDEX 10
                                        int codSedex10 = ContrClienteContrato.consultaContratoClienteGrupoServ(idCli, "SEDEX10", nomeBD);
                                        int qtdSedex10 = ContrClienteEtiquetas.contaQtdUtilizadaPorGrupoServ("SEDEX10", 0, idCli, nomeBD);
                                        if (!acs.contains(4)) {
                                            out.println("<dl style='width:105px; color:gray;' id='SEDEX10' onclick=\"alert('Este usu�rio n�o tem permis�o para utilizar este servi�o!');\" >");
                                            out.println("<dd><b class='serv'>SEDEX 10</b><br/>&nbsp;</dd>");//<img src="../../imagensNew/sedex10.png" border="0" />
                                            out.println("<dd><p><b style='color:gray;'>BLOQUEADO</b></p></dd>");
                                            out.println("<input type='hidden' name='cod_sedex10' value='" + codSedex10 + "' />");
                                            out.println("</dl>");
                                        } else if (codSedex10 != 0 && qtdSedex10 > 0) {
                                            out.println("<dl style='width:105px;' id='SEDEX10' onclick=\"alteraServ('SEDEX10');\" >");
                                            out.println("<dd><b class='serv'>SEDEX 10</b><br/>&nbsp;</dd>");
                                            out.println("<dd><p>QTD.: <b>" + qtdSedex10 + "</b></p></dd>");
                                            out.println("<input type='hidden' name='cod_sedex10' value='" + codSedex10 + "' />");
                                            out.println("</dl>");
                                        } else if (codSedex10 != 0) {
                                            out.println("<dl style='width:105px;' id='SEDEX10' onclick=\"alteraServ('SEDEX10');\" >");
                                            out.println("<dd><b class='serv'>SEDEX 10</b><br/>&nbsp;</dd>");
                                            out.println("<dd><p><b>S/ ETIQUETA</b></p></dd>");
                                            out.println("<input type='hidden' name='cod_sedex10' value='" + codSedex10 + "' />");
                                            out.println("</dl>");
                                        } else {
                                            out.println("<dl style='width:105px;' id='SEDEX10' onclick=\"alteraServ('SEDEX10');\" >");
                                            out.println("<dd><b class='serv'>SEDEX 10</b><br/>&nbsp;</dd>");
                                            out.println("<dd><p><b style='color:green;'>� VISTA</b></p></dd>");
                                            out.println("<input type='hidden' name='cod_sedex10' value='' />");
                                            out.println("</dl>");
                                        }

                                        //CONSULTA SE TEM CONTRATO SEDEX 12
                                        int codSedex12 = ContrClienteContrato.consultaContratoClienteGrupoServ(idCli, "SEDEX12", nomeBD);
                                        int qtdSedex12 = ContrClienteEtiquetas.contaQtdUtilizadaPorGrupoServ("SEDEX12", 0, idCli, nomeBD);
                                        if (!acs.contains(7)) {
                                            out.println("<dl style='width:105px; color:gray;' id='SEDEX12' onclick=\"alert('Este usu�rio n�o tem permis�o para utilizar este servi�o!');\" >");
                                            out.println("<dd><b class='serv'>SEDEX 12</b><br/>&nbsp;</dd>");//<img src="../../imagensNew/sedex12.png" border="0" />
                                            out.println("<dd><p><b style='color:gray;'>BLOQUEADO</b></p></dd>");
                                            out.println("<input type='hidden' name='cod_sedex12' value='" + codSedex12 + "' />");
                                            out.println("</dl>");
                                        } else if (codSedex12 != 0 && qtdSedex12 > 0) {
                                            out.println("<dl style='width:105px;' id='SEDEX12' onclick=\"alteraServ('SEDEX12');\" >");
                                            out.println("<dd><b class='serv'>SEDEX 12</b><br/>&nbsp;</dd>");//<img src="../../imagensNew/sedex12.png" border="0" />
                                            out.println("<dd><p>QTD.: <b>" + qtdSedex12 + "</b></p></dd>");
                                            out.println("<input type='hidden' name='cod_sedex12' value='" + codSedex12 + "' />");
                                            out.println("</dl>");
                                        } else if (codSedex12 != 0) {
                                            out.println("<dl style='width:105px;' id='SEDEX12' onclick=\"alteraServ('SEDEX12');\" >");
                                            out.println("<dd><b class='serv'>SEDEX 12</b><br/>&nbsp;</dd>");
                                            out.println("<dd><p><b>S/ ETIQUETA</b></p></dd>");
                                            out.println("<input type='hidden' name='cod_sedex12' value='" + codSedex12 + "' />");
                                            out.println("</dl>");
                                        } else {
                                            out.println("<dl style='width:105px;' id='SEDEX12' onclick=\"alteraServ('SEDEX12');\" >");
                                            out.println("<dd><b class='serv'>SEDEX 12</b><br/>&nbsp;</dd>");
                                            out.println("<dd><p><b style='color:green;'>� VISTA</b></p></dd>");
                                            out.println("<input type='hidden' name='cod_sedex12' value='' />");
                                            out.println("</dl>");
                                        }

                                        //CONSULTA SE TEM CONTRATO ESEDEX
                                        int codEsedex = ContrClienteContrato.consultaContratoClienteGrupoServ(idCli, "ESEDEX", nomeBD);
                                        int qtdEsedex = ContrClienteEtiquetas.contaQtdUtilizadaPorGrupoServ("ESEDEX", 0, idCli, nomeBD);
                                        if (acs.contains(5) && codEsedex != 0 && qtdEsedex > 0) {
                                            out.println("<dl style='width:90px;' id='ESEDEX' onclick=\"alteraServ('ESEDEX');\">");
                                            out.println("<dd><b class='serv'>e-SEDEX</b><br/>&nbsp;</dd>");
                                            out.println("<dd><p>QTD.: <b>" + qtdEsedex + "</b></p></dd>");
                                            out.println("<input type='hidden' name='cod_esedex' value='" + codEsedex + "' />");
                                            out.println("</dl>");
                                        } else if (acs.contains(5) && codEsedex != 0) {
                                            out.println("<dl style='width:90px;' id='ESEDEX' onclick=\"alteraServ('ESEDEX');\">");
                                            out.println("<dd><b class='serv'>e-SEDEX</b><br/>&nbsp;</dd>");
                                            //out.println("<dd><img src='../../imagensNew/esedex.png' border='0' /></dd>");
                                            out.println("<dd><p><b>S/ ETIQUETA</b></p></dd>");
                                            out.println("<input type='hidden' name='cod_esedex' value='" + codEsedex + "' /></dl>");
                                            out.println("</dl>");
                                        } else {
                                            out.println("<dl style='width:90px;color:gray;' id='ESEDEX' onclick=\"alert('Este usu�rio n�o tem permis�o para utilizar este servi�o!');\">");
                                            out.println("<dd><b class='serv'>e-SEDEX</b><br/>&nbsp;</dd>");
                                            out.println("<dd><p><b style='color:gray;'>BLOQUEADO</b></p></dd>");
                                            out.println("<input type='hidden' name='cod_esedex' value='" + codEsedex + "' />");
                                            out.println("</dl>");
                                        }

                                        //CONSULTA SE TEM CONTRATO CARTA
                                        int codCarta = ContrClienteContrato.consultaContratoClienteGrupoServ(idCli, "CARTA", nomeBD);
                                        int qtdCarta = ContrClienteEtiquetas.contaQtdUtilizadaPorGrupoServ("CARTA", 0, idCli, nomeBD);
                                        if (!acs.contains(6)) {
                                            out.println("<dl style='width:90px; color:gray;' id='CARTA' onclick=\"alert('Este usu�rio n�o tem permis�o para utilizar este servi�o!');\" >");
                                            out.println("<dd><b class='serv'>CARTAS</b><br/>&nbsp;</dd>");//<img src="../../imagensNew/carta.png" border="0" />
                                            out.println("<dd><p><b style='color:gray;'>BLOQUEADO</b></p></dd>");
                                            out.println("<input type='hidden' name='cod_carta' value='" + codCarta + "' />");
                                            out.println("</dl>");
                                        } else if (codCarta != 0 && qtdCarta > 0) {
                                            out.println("<dl style='width:90px;' id='CARTA' onclick=\"alteraServ('CARTA');\" >");
                                            out.println("<dd><b class='serv'>CARTAS</b><br/>&nbsp;</dd>");//<img src="../../imagensNew/carta.png" border="0" />
                                            out.println("<dd><p>QTD.: <b>" + qtdCarta + "</b></p></dd>");
                                            out.println("<input type='hidden' name='cod_carta' value='" + codCarta + "' />");
                                            out.println("</dl>");
                                        } else if (codCarta != 0) {
                                            out.println("<dl style='width:90px;' id='CARTA' onclick=\"alteraServ('CARTA');\" >");
                                            out.println("<dd><b class='serv'>CARTAS</b><br/>&nbsp;</dd>");
                                            out.println("<dd><p><b>S/ ETIQUETA</b></p></dd>");
                                            out.println("<input type='hidden' name='cod_carta' value='" + codCarta + "' />");
                                            out.println("</dl>");
                                        } else {
                                            out.println("<dl style='width:90px;' id='CARTA' onclick=\"alteraServ('CARTA');\" >");
                                            out.println("<dd><b class='serv'>CARTAS</b><br/>&nbsp;</dd>");
                                            out.println("<dd><p><b style='color:green;'>� VISTA</b></p></dd>");
                                            out.println("<input type='hidden' name='cod_carta' value='' />");
                                            out.println("</dl>");
                                        }
                                        
                                        //CONSULTA SE TEM CONTRATO SEDEX A COBRAR
                                        int codSedexc = ContrClienteContrato.consultaContratoClienteGrupoServ(idCli, "SEDEXC", nomeBD);
                                        int qtdSedexc = ContrClienteEtiquetas.contaQtdUtilizadaPorGrupoServ("SEDEXC", 0, idCli, nomeBD);
                                        if (!acs.contains(3)) {
                                            out.println("<dl style='width:90px; color:gray;' id='SEDEXC' onclick=\"alert('Este usu�rio n�o tem permis�o para utilizar este servi�o!');\" >");
                                            out.println("<dd><b class='serv'>SEDEX</b><br/>A COBRAR");//<img src="../../imagensNew/sedex_cobrar.png" border="0" />
                                            out.println("<dd><p><b style='color:gray;'>BLOQUEADO</b></p></dd>");
                                            out.println("<input type='hidden' name='cod_sedexc' value='" + codSedexc + "' />");
                                            out.println("</dl>");
                                        } else if (codSedexc != 0 && qtdSedexc > 0) {
                                            out.println("<dl style='width:90px;' id='SEDEXC' onclick=\"alteraServ('SEDEXC');\" >");
                                            out.println("<dd><b class='serv'>SEDEX</b><br/>A COBRAR");//<img src="../../imagensNew/sedex_cobrar.png" border="0" />
                                            out.println("<dd><p>QTD.: <b>" + qtdSedexc + "</b></p></dd>");
                                            out.println("<input type='hidden' name='cod_sedexc' value='" + codSedexc + "' />");
                                            out.println("</dl>");
                                        } else if (codSedexc != 0) {
                                            out.println("<dl style='width:90px;' id='SEDEXC' onclick=\"alteraServ('SEDEXC');\" >");
                                            out.println("<dd><b class='serv'>SEDEX</b><br/>A COBRAR");//<img src="../../imagensNew/sedex_cobrar.png" border="0" />
                                            out.println("<dd><p><b>S/ ETIQUETA</b></p></dd>");
                                            out.println("<input type='hidden' name='cod_sedexc' value='" + codSedexc + "' />");
                                            out.println("</dl>");
                                        } else {
                                            out.println("<dl style='width:90px;' id='SEDEXC' onclick=\"alteraServ('SEDEXC');\" >");
                                            out.println("<dd><b class='serv'>SEDEX</b><br/>A COBRAR");//<img src="../../imagensNew/sedex_cobrar.png" border="0" />
                                            out.println("<dd><p><b style='color:green;'>� VISTA</b></p></dd>");
                                            out.println("<input type='hidden' name='cod_sedexc' value='' />");
                                            out.println("</dl>");
                                        }

                                        //CONSULTA SE TEM CONTRATO PAC A COBRAR
                                        int codPacCob = ContrClienteContrato.consultaContratoClienteGrupoServ(idCli, "PAC_COB", nomeBD);
                                        int qtdPacCob = ContrClienteEtiquetas.contaQtdUtilizadaPorGrupoServ("PAC_COB", 0, idCli, nomeBD);
                                        if (!acs.contains(3)) {
                                            out.println("<dl style='width:90px; color:gray;' id='PAC_COB' onclick=\"alert('Este usu�rio n�o tem permis�o para utilizar este servi�o!');\" >");
                                            out.println("<dd><b class='serv'>PAC</b><br/>A COBRAR");//<img src="../../imagensNew/sedex_cobrar.png" border="0" />
                                            out.println("<dd><p><b style='color:gray;'>BLOQUEADO</b></p></dd>");
                                            out.println("<input type='hidden' name='cod_sedexc' value='" + codPacCob + "' />");
                                            out.println("</dl>");
                                        } else if (codSedexc != 0 && qtdSedexc > 0) {
                                            out.println("<dl style='width:90px;' id='PAC_COB' onclick=\"alteraServ('PAC_COB');\" >");
                                            out.println("<dd><b class='serv'>PAC</b><br/>A COBRAR");//<img src="../../imagensNew/sedex_cobrar.png" border="0" />
                                            out.println("<dd><p>QTD.: <b>" + qtdPacCob + "</b></p></dd>");
                                            out.println("<input type='hidden' name='cod_sedexc' value='" + codPacCob + "' />");
                                            out.println("</dl>");
                                        } else if (codSedexc != 0) {
                                            out.println("<dl style='width:90px;' id='PAC_COB' onclick=\"alteraServ('PAC_COB');\" >");
                                            out.println("<dd><b class='serv'>PAC</b><br/>A COBRAR");//<img src="../../imagensNew/sedex_cobrar.png" border="0" />
                                            out.println("<dd><p><b>S/ ETIQUETA</b></p></dd>");
                                            out.println("<input type='hidden' name='cod_sedexc' value='" + codPacCob + "' />");
                                            out.println("</dl>");
                                        } else {
                                            out.println("<dl style='width:90px;' id='PAC_COB' onclick=\"alteraServ('PAC_COB');\" >");
                                            out.println("<dd><b class='serv'>PAC</b><br/>A COBRAR");//<img src="../../imagensNew/sedex_cobrar.png" border="0" />
                                            out.println("<dd><p><b style='color:green;'>� VISTA</b></p></dd>");
                                            out.println("<input type='hidden' name='cod_sedexc' value='' />");
                                            out.println("</dl>");
                                        }

                                        ArrayList<Integer> listaOutrosServ = ContrClienteContrato.consultaOutrosServicosCliente(idCli, nomeBD);
                                        if(listaOutrosServ.size()>0){                                                        
                                            out.println("<dl style='width:90px;' id='OUTROS' onclick=\"alteraServ('OUTROS');\">");
                                            out.println("<dd><b class='serv'>OUTROS</b><br/>SERVI�OS</dd>");
                                            out.println("<dd><p><b>- - -</b></p></dd>");
                                            out.println("<input type='hidden' name='cod_outros' value='' />");
                                            out.println("</dl>");
                                        }else{                                            
                                            out.println("<input type='hidden' name='OUTROS' id='OUTROS' value='' />");
                                            out.println("<input type='hidden' name='cod_outros' value='' />");
                                        }
                                    %>
                            </li>
                        </ul>
                        <div id="form_aviso" >
                            <ul class="ul_formulario" style="width: 1136px;">
                                <li>
                                    <dd style="font-size: 20px; font-weight: bold; color: red;">
                                        <%if (servIni.equals("")) {%>
                                        ESTE USU�RIO N�O POSSUI PERMISS�O PARA GERAR ETIQUETA DE NENHUM SERVI�O<br/>
                                        PARA PERMITIR ENTRE EM CONTATO COM O USU�RIO ADMINISTRADOR
                                        <%} else {%>
                                        CLIQUE NO SERVI�O DESEJADO PARA GERAR ETIQUETA
                                        <%}%>
                                    </dd>
                                </li>
                            </ul>
                        </div>
                        <div id="form_etiqueta" class="esconder">
                            <ul class="ul_formulario" style="width: 1136px;"  >

                                <div id="outros_servicos" class="esconder">
                                    <li class="titulo">
                                        <dd style="font-size: 11px;">ESCOLHA O SERVI�O DESEJADO</dd>
                                    </li>
                                    <li>
                                        <dd>
                                            <label>Servi�o<b class="obg">*</b></label>
                                            <select style="width: 220px;" name="servico_1" id="servico_1" onchange="alteraOutroServ(this.value);">
                                                <%
                                                    ArrayList<ServicoECT> listaServ = ContrServicoECT.consultaServicos(0, 1, "OSV");
                                                    if (listaServ != null) {
                                                        for (int i = 0; i < listaServ.size(); i++) {
                                                            ServicoECT sv = listaServ.get(i);
                                                            if (listaOutrosServ.contains(sv.getCodECT())) {
                                                                String tipoPost = "NAC";
                                                                if(sv.getTipo_agencia().contains("INT")){
                                                                    tipoPost = "INT";
                                                                }
                                                                out.println("<option value='" + sv.getCodECT() + ";" + sv.getGrupoServico() + ";" + tipoPost + "'>" + sv.getNomeServico() + "</option>");
                                                            }
                                                        }
                                                    }
                                                %>
                                            </select>
                                        </dd>
                                    </li>
                                </div>
                                <li class="titulo">
                                    <dd style="font-size: 11px;">DADOS DO REMETENTE</dd>
                                </li>
                                <li>
                                    <dd>
                                        <label>Nome / Raz�o Social<b class="obg">*</b></label>
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
                                    <dd style="font-size: 11px;">DADOS DO DESTINAT�RIO<%-- - <a style="color: blue" onClick="verPesquisarDestinatario('multi');"><img src="../../imagensNew/users.png" /> INSERIR M�LTIPLOS DESTINAT�RIOS</a>--%></dd>
                                </li>
                                <li id="singleDest1">
                                    <dd>
                                        <label>Nome / Raz�o Social<b class="obg">*</b><a onClick="verPesquisarDestinatario('single');"><img src="../../imagensNew/lupa.png" /> PESQUISAR</a></label>
                                        <input style="display:none;" type="text" name="somefakename" />
                                        <input type="text" name="nome" id="nome" size="50" maxlength="80" value="" autocomplete="off" />
                                    </dd>
                                    <dd>
                                        <label>Empresa</label>
                                        <input type="text" name="empresa" id="empresa" size="20" maxlength="40" value="" autocomplete="off" />
                                    </dd>
                                    </dd>
                                    <dd>
                                        <label>E-mail</label>
                                        <input type="text" name="email_destinatario" id="email_destinatario" size="25" maxlength="100" value="" autocomplete="off" />
                                    </dd>
                                    <dd>
                                        <label>Celular</label>
                                        <input type="text" name="celular" id="celular" size="15" maxlength="20" value="" onkeydown="mascara(this, maskTelefone);" autocomplete="off" />
                                    </dd>
                                    <dd>
                                        <label>CPF / CNPJ<b id="cpf_obg" class="esconder">*</b></label>
                                        <input type="text" name="cpf_cnpj" id="cpf_cnpj" onkeydown="mascara(this, maskCpfCnpj);" value="" size="15" maxlength="18" autocomplete="off" />
                                    </dd>
                                    <dd>
                                        <label>AOS CUIDADOS</label>
                                        <input type="text" name="aoscuidados" id="aoscuidados" maxlength="25" value="" autocomplete="off" />
                                    </dd>
                                </li>
                                <li id="singleDest2">
                                    <dd>
                                        <label>CEP<b class="obg">*</b><a onclick="window.open('http://www.buscacep.correios.com.br', 'CORREIOS');" ><img src="../../imagensNew/question.png" /></a></label>
                                        <input type="text" name="cep" id="cep" size="8" value="" maxlength="9" onchange="mascara(this, maskCep);" onkeypress="mascara(this, maskCep);handleEnter();" onblur="verPesquisarCepDest(this.value);" autocomplete="off" />
                                    </dd>
                                    <dd>
                                        <label>Endere�o<b class="obg">*</b></label>
                                        <input type="text" name="endereco" id="endereco" size="55" maxlength="80" value="" autocomplete="off" />
                                    </dd>
                                    <dd>
                                        <label>N�<b class="obg">*</b></label>
                                        <input type="text" name="numero" id="numero" size="7" value="" maxlength="8" onkeypress="mascara(this, maskNumeroRua)" />
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
                                    <dd id="ddUf">
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
                                <li id="paisDest" class="esconder">
                                    <dd>
                                        <label>Estado<b class="obg">*</b></label>
                                        <input type="text" name="estado" id="estado" maxlength="50" value="" />
                                    </dd>
                                    <dd>
                                        <label>Pa�s<b class="obg">*</b></label>
                                        <div id="selectPaises"></div>
                                    </dd>
                                </li>
                                <li id="multiDest" class="esconder">
                                    <a class="botaoVoltar" href="#" onclick="cancelaMulti();"><img class="link_img" src="../../imagensNew/back.png" /> INSERIR NOVO DESTINAT�RIO</a>
                                    <br/><br/>
                                    <table id="tableMultiDest" width="100%" class="tb1" cellspacing="0">
                                        <tr>
                                            <th>C�D.</th>
                                            <th>NOME / RAZ�O SOCIAL</th>
                                            <th>EMPRESA</th>
                                            <th>CEP</th>
                                            <th>ENDERE�O</th>
                                            <th>BAIRRO</th>
                                            <th>CIDADE / UF</th>
                                            <th>CPF</th>
                                            <th>DEL</th>
                                        </tr>
                                    </table>
                                    <input type="hidden" name="flagMulti" id="flagMulti" value="0" />
                                </li>
                                <li class="titulo">
                                    <dd style="font-size: 11px;">DADOS DO OBJETO</dd>
                                </li>
                                <li>
                                    <dd>
                                        <label id="labelObs">Observa��es<span style="color:red;">(APARECE SOMENTE NA ETIQUETA)</span></label>
                                        <input type="text" name="obs" id="obs" size="55" maxlength="90" value="" />
                                    </dd>
                                    <dd>
                                        <label>Conte�do<span style="color:red;">(APARECE SOMENTE NO A.R.)</span></label>
                                        <input type="text" name="conteudo" id="conteudoObj" size="26" maxlength="200" value="" />
                                    </dd>
                                    <dd>
                                        <label>N� PEDIDO / NOTA FISCAL</label>
                                        <input type="text" name="notaFiscal" id="notaFiscal" maxlength="40" value="" />
                                    </dd>
                                    <%--<dd>
                                        <label>Peso<span style="color:red;">(gr)</span></label>
                                        <input type="text" name="peso" id="peso" size="5" maxlength="6" onkeypress="mascara(this, maskNumero)" />
                                    </dd>--%>
                                    <dd id="tipoPacote" class="mostrar">
                                        <label>Tipo do Objeto<b class="obg">*</b></label>
                                        <select style="width: 115px;" name="tipo" id="tipo">
                                            <option value=""> - - - </option>
                                            <option value="ENV">ENVELOPE</option>
                                            <option value="PAC">PACOTE</option>
                                        </select>
                                    </dd>
                                    <dd id="tipoCt" class="esconder" >
                                        <label>Tipo da Carta<b class="obg">*</b></label>
                                        <select style="width: 115px;" name="tipoCarta" id="tipoCarta" onchange="trocaTipoCarta(this.value);">
                                            <option value=""> - - - </option>
                                            <option value="1">REGISTRADA</option>
                                            <option value="0">SIMPLES</option>
                                        </select>
                                    </dd>
                                    <%--<dd>
                                        <label>Alt.<span style="color:red;">(cm)</span></label>
                                        <input type="text" name="altura" id="altura" size="5" maxlength="3" onkeypress="mascara(this, maskNumero)" />
                                    </dd>
                                    <dd>
                                        <label>Larg.<span style="color:red;">(cm)</span></label>
                                        <input type="text" name="largura" id="largura" size="5" maxlength="3" onkeypress="mascara(this, maskNumero)" />
                                    </dd>
                                    <dd>
                                        <label>Comp.<span style="color:red;">(cm)</span></label>
                                        <input type="text" name="comprimento" id="comprimento" size="5" maxlength="3" onkeypress="mascara(this, maskNumero)" />
                                    </dd>--%>
                                    <dd id="vlrCobrar" class="esconder">
                                        <label>Valor a Cobrar<span style="color:red;">(R$)</span></label>
                                        <input type="text" name="valor_cobrar" id="valor_cobrar" value="0.00"  onkeypress="mascara(this, maskReal)" />
                                    </dd>
                                    <dd id="vlrDecl">
                                        <label>Valor Declarado<span style="color:red;">(R$)</span></label>
                                        <input type="text" name="vd" id="vd" value="0.00" onkeypress="mascara(this, maskReal)" />
                                    </dd>
                                    <dd id="maoProp">
                                        <label>M.P.</label>
                                        <select name="mp" id="mp">
                                            <option value="0">N�o</option>
                                            <option value="1">Sim</option>
                                        </select>
                                    </dd>
                                    <dd id="avisoRec">
                                        <label>A.R.</label>
                                        <select name="ar" id="ar">
                                            <option value="0">N�o</option>
                                            <option value="1">Sim</option>
                                        </select>
                                    </dd>
                                </li>
                                <li>
                                    <dd style="width: 100%;">
                                        <div class="buttons">
                                            <input type="hidden" name="quantidade" id="quantidade" value="1" />
                                            <input type="hidden" name="nomeOrig" id="nomeOrig" value="" />
                                            <input type="hidden" name="servico" id="servico" value="PAC" />
                                            <input type="hidden" name="nomeBD" id="nomeBD" value="<%= nomeBD%>" />
                                            <input type="hidden" name="idDestinatario" id="idDestinatario" value="0" />
                                            <input type="hidden" name="idCliente" value="<%= idCli%>" />
                                            <input type="hidden" name="idUser" value="<%= idUser%>" />
                                            <input type="hidden" name="nomeUser" value="<%= nomeUser%>" />
                                            <%-- return preencherCampos(); --%>
                                            <button type="button" class="positive" onclick="validaDados();"><img src="../../imagensNew/tick_circle.png" /> EFETUAR PR�-POSTAGEM</button>
                                        </div>
                                    </dd>
                                </li>
                            </ul>

                        </div>
                    </form>
                    <img width="100%" src="../../imagensNew/linha.jpg"/>


                    <div id="titulo2">Lista de Etiquetas que est�o Aguardando Impress�o. <i style="font-size: 10px;color: lightcoral;">*Impress�o m�xima de 200 etiquetas por vez</i></div>
                    <form name="formEtq" action="pre_postagem_etq.jsp" method="post">
                        <table cellpadding="0" cellspacing="0" border="0" id="table2" class="tinytable">
                            <thead>
                                <tr>
                                    <th align="center" class="nosort" style="width: 20px; padding-left: 5px;">
                                        <input type=checkbox checked onClick="Check200('ids', this.checked);" title="Marcar/Desmarcar 200 Etiquetas" />
                                        <h3 style="display: none;"></h3>
                                    </th>
                                    <th><h3>N� do Objeto</h3></th>
                                    <th><h3>Servi�o</h3></th>
                                    <th><h3>Destinat�rio</h3></th>
                                    <th><h3>Endere�o</h3></th>
                                    <th><h3>Cidade / UF</h3></th>
                                    <th><h3>CEP</h3></th>
                                    <th><h3>N.F.</h3></th>
                                    <th width="40"><h3>AR</h3></th>
                                    <th class="nosort" width="60"><h3>Visualizar</h3></th>
                                    <th class="nosort" width="60"><h3>Excluir</h3></th>
                                </tr>
                            </thead>
                            <tbody>
                                <%
                                    ArrayList<PreVenda> lista = ContrPreVenda.consultaVendas(idCli, 0, 0, nivel, idUser, nomeBD);
                                    for (int i = 0; i < lista.size(); i++) {
                                        PreVenda des = lista.get(i);
                                        String numObj = des.getNumObjeto();
                                        if ("avista".equals(numObj)) {
                                            numObj = "- - -";
                                        }
                                        String ar = "SIM";
                                        if (des.getAviso_recebimento() == 0) {
                                            ar = "N�O";
                                        }
                                %>
                                <tr style="cursor:default;">
                                    <td align="center"><input type="checkbox" <%if (i < 200) {%>checked<%}%> name="ids" id="ids" value="<%= des.getId()%>" /></td>
                                    <td align="center"><%= numObj%></td>
                                    <td><%= des.getNomeServico()%></td>
                                    <td><%= des.getNomeDes()%></td>
                                    <td><%= des.getEnderecoDes() + ", " + des.getNumeroDes()%></td>
                                    <td><%= des.getCidadeDes() + " / " + des.getUfDes()%></td>
                                    <td><%= des.getCepDes()%></td>
                                    <td><%= des.getNotaFiscal()%></td>
                                    <td><%= ar%></td>
                                    <td align="center"><a onclick="verVenda(<%= des.getId()%>);" style="cursor:pointer;" ><img src="../../imagensNew/lupa.png" /></a></td>
                                    <td align="center">
                                        <img style="cursor: pointer;" src="../../imagensNew/cancel.png" border="0" onClick="excluirVenda(<%= des.getId()%>, '<%= des.getNumObjeto()%>');" />
                                    </td>
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
                        <%if (lista.size() > 0) {%>
                        <ul class="ul_formulario" style="padding: 10px 0; margin: 0;width: 1136px;"  >
                            <li>
                                <dd>
                                    <label>Ordenar impress�o por:</label>
                                    <select style="width: 220px;" name="ordem" id="ordem">
                                        <option value="id">Ordem de Gera��o</option> 
                                        <option value="d.nome">Nome do Destinat�rio</option> 
                                        <option value="notaFiscal">Nota Fiscal</option> 
                                        <option value="nomeServico">Servi�o</option> 
                                        <option value="nObj">N� de Objeto</option> 
                                        <option value="d.cpf_cnpj">CPF/CNPJ</option> 
                                    </select>
                                </dd>
                                <dd>
                                    <label>Tamanho da impress�o:</label>
                                    <select style="width: 220px;" name="formato" id="formato">
                                        <option value="A4">Folha A4</option> 
                                        <option disabled>----------------</option>
                                        <option value="ETQ_16x10">Etiqueta Adesiva - 16cm x 10cm</option> 
                                        <option value="ETQ_10x10">Etiqueta Adesiva - 10cm x 10cm</option> 
                                        <option disabled>----------------</option>
                                        <option value="ENV_DL">Envelope Of�cio / DL (Direita) - 11,4cm x 22,9cm</option> 
                                        <option value="ENV_DL_ESQ">Envelope Of�cio / DL (Esquerda) - 11,4cm x 22,9cm</option> 
                                        <option value="ENV_C5">Envelope C5 (Direita) - 16,2cm x 22,9cm</option> 
                                        <option value="ENV_C5_ESQ">Envelope C5 (Esquerda) - 16,2cm x 22,9cm</option> 
                                        <option value="ENV_B4">Envelope B4 (Centro) - 35,3cm x 25cm</option>
                                    </select>
                                </dd>
                            </li>
                            <li>
                                <dd>
                                    <div class="buttons">
                                        <button type="submit" class="regular" onClick="return verificaSelecao('A4');"><img src="../../imagensNew/printer.png" /> IMPRIMIR ETIQUETAS SELECIONADAS</button>
                                        <%--<input type="hidden" name="formato" id="formato" value="A4" />                                        
                                        <button type="submit" class="regular" onClick="return verificaSelecao('A4');"><img src="../../imagensNew/printer.png" /> IMPRIMIR EM FOLHA A4</button>
                                        <button style="float: right;" type="submit" class="positive" onClick="return verificaSelecao('ETQ_16x10');"><img src="../../imagensNew/printer.png" /> IMPRIMIR EM ETIQUETAS 16 x 10</button>--%>
                                    </div>                                    
                                </dd>
                            </li>
                        </ul>                        
                        <%}%>
                    </form>

                    <form action="../../ServPreVendaExcluir" method="post" name="formDel">
                        <input type="hidden" name="nomeBD" value="<%= nomeBD%>" />
                        <input type="hidden" name="idVenda" id="idVendaDel" value="" />
                        <input type="hidden" name="numObjeto" id="numObjetoDel" value="" />
                    </form>

                </div>
            </div>
        </div>
    </body>
</html>
<%}%>