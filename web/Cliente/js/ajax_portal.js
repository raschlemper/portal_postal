
function getHTTPObject() {
    var xmlhttp;
    if (window.XMLHttpRequest) {
        xmlhttp = new XMLHttpRequest();
    } else if (window.ActiveXObject) {
        xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
    }
    return xmlhttp;
}
var http = getHTTPObject(); // We create the XMLHTTPRequest Object


/****************************************************************/

function waitMsg() {
    bootbox.dialog({
        title: '<h4> Aguarde... Processando Requisi&ccedil;&atilde;o<h4>',
        message: "<div class='progress'> <div class='progress-bar progress-bar-striped active' role='progressbar' aria-valuenow='100' aria-valuemin='0' aria-valuemax='100' style='width:100%;'></div></div>",
        modal: true,
        show: true,
        backdrop: true,
        closeButton: false,
        animate: true,
        className: "my-modal",
        zIndex: 2500
    });
}
function fechaMsg() {
    bootbox.hideAll();
    telaMsg();
}

/*******************************************************************************/

function handleHttpResponseBootStrap() {

    if (http.readyState == 4) {

        if (http.status == 200) {

            var resultado = http.responseText;

            if (resultado.toString() == "sessaoexpirada") {
                window.location = "../../index.jsp?msgLog=3";
            } else {
                //document.getElementById("divInteracao").innerHTML = resultado;
                bootbox.dialog({
                    title: "<span style='color:red; font-weight: bold;'>Etiqueta</span>",
                    message: resultado
                });

            }

        } else {
            alert("Erro: " + http.status + " <br> Informa&ccedil;&atilde;o n&atilde;o dispon&iacute;vel em nosso banco de dados.");
        }
    }
}
function handleHttpResponsePreVenda() {
    if (http.readyState == 4) {
        if (http.status == 200) {
            //alert("handleHTTPResponse");
            var resultado = http.responseText;
            if (resultado.toString() == "sessaoexpirada") {
                window.location = "../../index.jsp?msgLog=3";
            } else {
                document.getElementById("divInteracao").innerHTML = resultado;
                document.getElementById("divProtecao").className = "mostrar";
                document.getElementById("divInteracao").className = "mostrar";
            }

        } else {
            alert("Erro: Objeto sem pr&eacute;-postagem cadastrada!\n\
                    <br><br>Para ter acesso a esta funcionalidade, voc&ecirc; deve fazer a pr&eacute;-postagem\n\
                         <i>(Gerar Etiqueta)</i> do objeto pelo Portal Postal!\n\
                    <br><br>Caso tenha relizado a pr&eacute;-postagem\n\
                         <i>(Gerar Etiqueta)</i> entre em contato com a sua Ag&ecirc;ncia!");
        }
    }
}

function handleHttpResponse() {
    if (http.readyState == 4) {
        if (http.status == 200) {
            //alert("handleHTTPResponse");
            var resultado = http.responseText;
            if (resultado.toString() == "sessaoexpirada") {
                window.location = "../../index.jsp?msgLog=3";
            } else {
                document.getElementById("divInteracao").innerHTML = resultado;
                document.getElementById("divProtecao").className = "mostrar";
                document.getElementById("divInteracao").className = "mostrar";
            }

        } else {
            alert("Erro: " + http.status + " <br><br>Informa&ccedil;&atilde;o n&atilde;o dispon&iacute;vel em nosso banco de dados.<br><br> Verifique com a sua Ag&ecirc;ncia!");
        }
    }
}

// funcao que retorna o forrm parra editarr o cooletador solicitado
function verReversa(idRev) {
    http.open("GET", "../../Cliente/AjaxPages/visualizar_reversa.jsp?idRev=" + idRev, true);
    http.onreadystatechange = handleHttpResponse;
    http.send(null);
}
// funcao que retorna o forrm parra editarr o cooletador solicitado
function verVenda(idVenda) {
    http.open("GET", "../../Cliente/AjaxPages/visualizar_venda.jsp?idVenda=" + idVenda, true);
    http.onreadystatechange = handleHttpResponsePreVenda;
    http.send(null);
}

// funcao que retorna o detalhe da etiqueta
function verVenda2(idVenda, idCliente) {
    http.open("GET", "../../AjaxPages/visualizar_venda.jsp?idCliente=" + idCliente + "&idVenda=" + idVenda, true);
    http.onreadystatechange = handleHttpResponse;
    http.send(null);
}
// funcao que retorna o detalhe da etiqueta FrameWort BootStrap
function verVendaBootStrap(idVenda, idCliente) {

    http.open("GET", "../../AjaxPages/visualizar_venda_bootStrap.jsp?idCliente=" + idCliente + "&idVenda=" + idVenda, true);
    http.onreadystatechange = handleHttpResponseBootStrap;
    http.send(null);
}

// funcao que retorna o forrm parra editarr o cooletador solicitado
function verPesquisarDestinatario(multi) {
    http.open("GET", "../../Cliente/AjaxPages/pesquisar_destinatario.jsp?multi=" + multi, true);
    http.onreadystatechange = handleHttpResponse;
    http.send(null);
}

// funcao que retorna o forrm parra editarr o cooletador solicitado
function verPesquisarChaveNFE() {
    http.open("GET", "../../Cliente/AjaxPages/consulta_chave_nfe.jsp", true);
    http.onreadystatechange = handleHttpResponse;
    http.send(null);
}

function handleHttpResponseConsultaDest() {
    if (http.readyState == 4) {
        if (http.status == 200) {
            //alert("handleHTTPResponse");
            var resultado = http.responseText;
            if (resultado.toString() == "sessaoexpirada") {
                window.location = "../../index.jsp?msgLog=3";
            } else {
                document.getElementById("divLoad").className = "esconder";
                document.getElementById("resultadoPesq").innerHTML = resultado;
                sorterDes.init();
            }

        } else {
            alert(http.status + " - Not able to retrieve name");
        }
    }
}

// funcao que retorna o forrm parra editarr o cooletador solicitado
function pesquisarDestinatario(nomeBD, multi) {

    document.getElementById("divLoad").className = "mostrar";

    var codigo = document.getElementById("codigo_d").value;
    var nome = document.getElementById("nome_d").value;
    var empresa = document.getElementById("empresa_d").value;
    var endereco = document.getElementById("endereco_d").value;
    var cep = document.getElementById("cep_d").value;
    var cidade = document.getElementById("cidade_d").value;
    var tag = document.getElementById("tags_d").value;
    console.log(cidade);

    http.open("GET", "../../Cliente/AjaxPages/consulta_destinatario.jsp?codigo=" + codigo + "&nome=" + nome + "&empresa=" + empresa + "&endereco=" + endereco + "&cep=" + cep + "&tags=" + tag + "&cidade=" + cidade + "&multi=" + multi, true);
    http.onreadystatechange = handleHttpResponseConsultaDest;
    http.send(null);
}

function copiaDadosDest(id, nome, empresa, cpf, cidade, uf, bairro, endereco, numero, complemento, cep, celular, email) {


    document.getElementById('idDestinatario').value = id;
    document.getElementById('nome').value = nome;
    document.getElementById('nomeOrig').value = nome;
    document.getElementById('empresa').value = empresa;
    document.getElementById('cpf_cnpj').value = cpf;
    document.getElementById('cidade').value = cidade;
    document.getElementById('uf').value = uf;
    document.getElementById('uf2').value = uf;
    document.getElementById('bairro').value = bairro;
    document.getElementById('endereco').value = endereco;
    document.getElementById('numero').value = numero;
    document.getElementById('complemento').value = complemento;
    document.getElementById('cep').value = cep;
    document.getElementById('celular').value = celular;
    document.getElementById('email_destinatario').value = email;


    document.getElementById('obs').focus();
    chamaDivProtecao();
}

function copiaDadosDestMulti(cks) {

    var flag = false;
    for (var i = 0; i < cks.length; i++) {
        if (cks[i].checked) {
            flag = true;
            /************************************************/
            var id = cks[i].value;
            var nome = document.getElementById("nome_multi_" + id).value;
            var empr = document.getElementById("empresa_multi_" + id).value;
            var end = document.getElementById("endereco_multi_" + id).value;
            var num = document.getElementById("numero_multi_" + id).value;
            var compl = document.getElementById("compl_multi_" + id).value;
            var bairro = document.getElementById("bairro_multi_" + id).value;
            var cidade = document.getElementById("cidade_multi_" + id).value;
            var uf = document.getElementById("uf_multi_" + id).value;
            var cpf = document.getElementById("cpf_multi_" + id).value;
            var cep = document.getElementById("cep_multi_" + id).value;
            var s = document.getElementById("servico_1");
            var servico = s.options[s.selectedIndex].text;
            var ar = document.getElementById("ar").value;
            var selAR = '';
            if (ar === '1') {
                selAR = 'selected';
            }
            var mp = document.getElementById("mp").value;
            var selMP = '';
            if (mp === '1') {
                selMP = 'selected';
            }
            var vd = document.getElementById("vd").value;

            var nrLinha = document.getElementById('tableMultiDest').rows.length;
            var linha = document.getElementById('tableMultiDest').insertRow(nrLinha);
            linha.insertCell(0).innerHTML = "<input type='text' id='multi_qtd_" + id + "' name='multi_qtd_" + id + "' value='1' size='1' onkeypress='mascara(this, maskNumero)' />";
            linha.insertCell(1).innerHTML = servico +
                    "<input type='hidden' id='multi_id' name='multi_id' value='" + id + "' />" +
                    "<input type='hidden' id='multi_serv_" + id + "' name='multi_serv_" + id + "' value='" + s.value + "' />" +
                    "<input type='hidden' id='multi_nome_" + id + "' name='multi_nome_" + id + "' value='" + nome + "' />" +
                    "<input type='hidden' id='multi_empresa_" + id + "' name='multi_empresa_" + id + "' value='" + empr + "' />" +
                    "<input type='hidden' id='multi_endereco_" + id + "' name='multi_endereco_" + id + "' value='" + end + "' />" +
                    "<input type='hidden' id='multi_numero_" + id + "' name='multi_numero_" + id + "' value='" + num + "' />" +
                    "<input type='hidden' id='multi_compl_" + id + "' name='multi_compl_" + id + "' value='" + compl + "' />" +
                    "<input type='hidden' id='multi_bairro_" + id + "' name='multi_bairro_" + id + "' value='" + bairro + "' />" +
                    "<input type='hidden' id='multi_cidade_" + id + "' name='multi_cidade_" + id + "' value='" + cidade + "' />" +
                    "<input type='hidden' id='multi_uf_" + id + "' name='multi_uf_" + id + "' value='" + uf + "' />" +
                    "<input type='hidden' id='multi_cpf_" + id + "' name='multi_cpf_" + id + "' value='" + cpf + "' />" +
                    "<input type='hidden' id='multi_cep_" + id + "' name='multi_cep_" + id + "' value='" + cep + "' />";
            linha.insertCell(2).appendChild(document.createTextNode(nome));
            linha.insertCell(3).appendChild(document.createTextNode(end + ", " + num + " " + compl));
            linha.insertCell(4).appendChild(document.createTextNode(cidade + " / " + uf));
            linha.insertCell(5).appendChild(document.createTextNode(cep));
            linha.insertCell(6).innerHTML = "<input type='text' id='multi_nf_" + id + "' name='multi_nf_" + id + "' size='4' value='' />";
            linha.insertCell(7).innerHTML = "<select name='multi_ar_" + id + "' id='multi_ar_" + id + "'>" +
                    "<option value='0'>Nao</option>" +
                    "<option " + selAR + " value='1'>Sim</option>" +
                    "</select>";
            linha.insertCell(8).innerHTML = "<select name='multi_mp_" + id + "' id='multi_mp_" + id + "'>" +
                    "<option value='0'>Nao</option>" +
                    "<option " + selMP + " value='1'>Sim</option>" +
                    "</select>";
            linha.insertCell(9).innerHTML = "<input type='text' id='multi_vd_" + id + "' name='multi_vd_" + id + "' value='" + vd + "' size='5' onkeypress='mascara(this, maskReal)' />";
            var cell8 = linha.insertCell(10);
            cell8.innerHTML = "<img src='../../imagensNew/cross-button.png' style='cursor:pointer;' onclick='delRow(this);' />";
            cell8.align = "center";
            linha.className = "faixas";
            /************************************************/
        }
    }

    if (flag) {
        //document.getElementById('flagMulti').value = '1';
        //document.getElementById('singleDest1').className = 'esconder';
        //document.getElementById('singleDest2').className = 'esconder';
        //document.getElementById('multiDest').className = 'mostrar';
        //document.getElementById('obs').focus();
        chamaDivProtecao();
    } else {
        alert('SELECIONE ALGUM DESTINATARIO PARA INSERIR!');
    }
}


function handleHttpResponseCepDest() {
    if (http.readyState == 4) {
        if (http.status == 200) {
            //alert("handleHTTPResponse");
            var resultado = http.responseText;
            if (resultado.toString() == "sessaoexpirada") {
                window.location = "../../index.jsp?msgLog=3";
            } else {
                var aux = resultado.split(";");

                document.getElementById('cidade').value = aux[2];
                document.getElementById('uf').value = aux[1];
                document.getElementById('uf2').value = aux[1];
                document.getElementById('bairro').value = aux[3];
                document.getElementById('endereco').value = aux[4];
                if (aux[4] == '') {
                    document.getElementById('endereco').focus();
                } else {
                    document.getElementById('numero').focus();
                }
            }
        } else {
            alert(http.status + " - Not able to retrieve name");
        }
    }
}

// funcao que retorna o forrm parra editarr o cooletador solicitado
function verPesquisarCepDest(cep) {
    if (cep.length == 9) {
        http.open("GET", "../../AjaxPages/ajax_cep_csv.jsp?cep=" + cep, true);
        http.onreadystatechange = handleHttpResponseCepDest;
        http.send(null);
    } else if (cep != '') {
        alert('Preencha o CEP por completo!');
        document.getElementById('cep').focus();
    }
}

function handleHttpResponseCepDestEdit() {
    if (http.readyState == 4) {
        if (http.status == 200) {
            //alert("handleHTTPResponse");
            var resultado = http.responseText;
            if (resultado.toString() == "sessaoexpirada") {
                window.location = "../../index.jsp?msgLog=3";
            } else {
                var aux = resultado.split(";");

                document.getElementById('cidade2').value = aux[2];
                document.getElementById('uf3').value = aux[1];
                document.getElementById('uf4').value = aux[1];
                document.getElementById('bairro2').value = aux[3];
                document.getElementById('endereco2').value = aux[4];
                if (aux[4] == '') {
                    document.getElementById('endereco2').focus();
                } else {
                    document.getElementById('numero2').focus();
                }
            }
        } else {
            alert(http.status + " - Not able to retrieve name");
        }
    }
}

// funcao que retorna o forrm parra editarr o cooletador solicitado
function verPesquisarCepDestEdit(cep) {
    if (cep.length == 9) {
        http.open("GET", "../../AjaxPages/ajax_cep_csv.jsp?cep=" + cep, true);
        http.onreadystatechange = handleHttpResponseCepDestEdit;
        http.send(null);
    } else if (cep != '') {
        alert('Preencha o CEP por completo!');
        document.getElementById('cep').focus();
    }
}

/*******************************************************************************/


// Pesquisa Objetos por data
function handleHttpResponsePesquisaCustomizadaObjetos() {
    if (http.readyState == 4) {
        if (http.status == 200) {
            var resultado = http.responseText;
            if (resultado.toString() == "sessaoexpirada") {
                window.location = "../../index.jsp?msg=Sua sessao expirou! Para voltar ao Portal faça seu login novamente!";
            } else {
                fecharTelaEspera();
                document.getElementById("tableObjeto").innerHTML = resultado;
                teste();
                sorter2.init();
            }
        } else {
            alert(http.status + " - Not able to retrieve name");
        }
    }
}

function pesquisaSintetica(idCliente, nomeBD) {
    abrirTelaEspera();
    var dataInicio = document.getElementById("dataIni").value;
    var dataFinal = document.getElementById("dataFim").value;
    var situacao = document.getElementById("situacao").value;
    var servico = document.getElementById("servico").value;
    var departamento = document.getElementById("departamento").value;
    var objeto = document.getElementById("objeto").value;
    var notafiscal = document.getElementById("notaFiscal").value;
    var destinatario = document.getElementById("destinatario").value;
    var cep = document.getElementById("cep").value;
    var uf = document.getElementById("uf").value;
    var ar = document.getElementById("ar").checked;
    var vd = document.getElementById("vd").checked;
    var atrasado = document.getElementById("atrasado").checked;
    var tpFat = document.getElementById("tipoFat").value;
    http.open("GET", "../AjaxPages/consulta_sintetico.jsp?idCliente=" + idCliente + "&nomeBD=" + nomeBD + "&dataIni=" + dataInicio + "&dataFim=" + dataFinal + "&situacao=" + situacao + "&servico=" + servico + "&departamento=" + departamento + "&objeto=" + objeto + "&notaFiscal=" + notafiscal + "&destinatario=" + destinatario + "&cep=" + cep + "&ar=" + ar + "&vd=" + vd + "&uf=" + uf + "&tipoFat=" + tpFat + "&atrasado=" + atrasado, true);
    http.onreadystatechange = handleHttpResponsePesquisaCustomizadaObjetos;
    http.send(null);
}

function pesquisaAr(idCliente, nomeBD) {
    abrirTelaEspera();
    var dataInicio = document.getElementById("dataIni").value;
    var dataFinal = document.getElementById("dataFim").value;
    var departamento = document.getElementById("departamento").value;
    var ar = document.getElementById("ar").value;
    http.open("GET", "../AjaxPages/consulta_ar.jsp?idCliente=" + idCliente + "&nomeBD=" + nomeBD + "&dataIni=" + dataInicio + "&dataFim=" + dataFinal + "&departamento=" + departamento + "&ar=" + ar, true);
    http.onreadystatechange = handleHttpResponsePesquisaCustomizadaObjetos;
    http.send(null);
}
function pesqSro(param) {
    $('#objetos').val(param);
    $('#frmSRO').submit();
}

function pesquisaAnalitica(idCliente, nomeBD) {
    abrirTelaEspera();
    var dataInicio = document.getElementById("dataIni").value;
    var dataFinal = document.getElementById("dataFim").value;
    var situacao = document.getElementById("situacao").value;
    var servico = document.getElementById("servico").value;
    var departamento = document.getElementById("departamento").value;
    var objeto = document.getElementById("objeto").value;
    var notafiscal = document.getElementById("notaFiscal").value;
    var destinatario = document.getElementById("destinatario").value;
    var cep = document.getElementById("cep").value;
    var uf = document.getElementById("uf").value;
    var ar = document.getElementById("ar").checked;
    var vd = document.getElementById("vd").checked;
    var lp = document.getElementById("lp").value;
    var tpFat = document.getElementById("tipoFat").value;
    var atrasado = document.getElementById("atrasado").checked;
    http.open("GET", "../AjaxPages/consulta_analitico.jsp?idCliente=" + idCliente + "&nomeBD=" + nomeBD + "&dataIni=" + dataInicio + "&dataFim=" + dataFinal + "&situacao=" + situacao + "&servico=" + servico + "&departamento=" + departamento + "&objeto=" + objeto + "&notaFiscal="
            + notafiscal + "&destinatario=" + destinatario + "&cep=" + cep + "&ar=" + ar + "&vd=" + vd + "&uf=" + uf + "&lp=" + lp + "&tipoFat=" + tpFat + "&atrasado=" + atrasado, true);
    http.onreadystatechange = handleHttpResponsePesquisaCustomizadaObjetos;
    http.send(null);
}

// Pesquisa Objetos por data
function handleHttpResponsePesquisaRelatorios() {
    if (http.readyState == 4) {
        if (http.status == 200) {
            var resultado = http.responseText;
            if (resultado.toString() == "sessaoexpirada") {
                window.location = "../../index.jsp?msg=Sua sessao expirou! Para voltar ao Portal faça seu login novamente!";
            } else {
                fecharTelaEspera();
                document.getElementById("tableObjeto").innerHTML = resultado;
            }
        } else {
            fecharTelaEspera();
            alert(http.status + " - Not able to retrieve name");
        }
    }
}

function pesquisaObjetosRelatorios(idCliente, nomeBD) {
    abrirTelaEspera();
    var dataInicio = document.getElementById("dataIni").value;
    var dataFinal = document.getElementById("dataFim").value;
    var agrupamento = document.getElementById("agrupamento").value;
    var ordenacao = document.getElementById("ordenacao").value;
    http.open("GET", "../AjaxPages/consulta_relatorio.jsp?idCliente=" + idCliente + "&nomeBD=" + nomeBD + "&inicio=" + dataInicio + "&final=" + dataFinal + "&agrupamento=" + agrupamento + "&ordenacao=" + ordenacao, true);
    http.onreadystatechange = handleHttpResponsePesquisaRelatorios;
    http.send(null);
}

function pesquisaPrecoPrazo() {
    abrirTelaEspera();
    var data = document.getElementById("data").value;
    var servico = document.getElementById("servico").value;
    var codEmpresa = document.getElementById("codEmpresa").value;
    var senha = document.getElementById("senha").value;
    var cepOrigem = document.getElementById("cepOrigem").value;
    var cepDestino = document.getElementById("cepDestino").value;
    var peso = document.getElementById("peso").value;
    var formato = document.getElementById("formato").value;
    var comp = document.getElementById("comprimento").value;
    var larg = document.getElementById("largura").value;
    var alt = document.getElementById("altura").value;
    var diam = document.getElementById("diametro").value;
    var mp = document.getElementById("mp").value;
    var ar = document.getElementById("ar").value;
    var vd = document.getElementById("vd").value;

    var agrupado = 0;
    var params = "";
    var str = document.getElementById("servico").options[document.getElementById("servico").selectedIndex].innerHTML;
    var qtd = document.getElementById("quantidade").value;
    if (str.indexOf("AGRUPADO") > 0) {
        agrupado = 1;
        for (i = 0; i < qtd; i++) {
            if (document.getElementById("peso" + i) === null
                    || document.getElementById("altura" + i) === null
                    || document.getElementById("largura" + i) === null
                    || document.getElementById("comprimento" + i) === null) {
                agrupado = -1;
            } else if (document.getElementById("peso" + i).value === ""
                    || document.getElementById("altura" + i).value === ""
                    || document.getElementById("largura" + i).value === ""
                    || document.getElementById("comprimento" + i).value === "") {
                agrupado = -2;
            } else {
                var p = "&p" + i + "=" + document.getElementById("peso" + i).value;
                var a = "&a" + i + "=" + document.getElementById("altura" + i).value;
                var l = "&l" + i + "=" + document.getElementById("largura" + i).value;
                var c = "&c" + i + "=" + document.getElementById("comprimento" + i).value;
                var vd2 = "&vd" + i + "=" + document.getElementById("vd" + i).value;
                params += p + a + l + c + vd2;
            }
        }
    }

    if (agrupado == -1) {
        fecharTelaEspera();
        alert("Clique em 'ADICIONAR PACOTES' para colocar o peso e medidas das caixas.");
    } else if (agrupado == -2) {
        fecharTelaEspera();
        alert("Preencha todos os pesos e medidas das caixas.");
    } else {
        http.open("GET", "../AjaxPages/consulta_preco_prazo.jsp?dataPostagem=" + data + "&nCdEmpresa=" + codEmpresa + "&sDsSenha=" + senha + "&nCdServico=" + servico
                + "&sCepOrigem=" + cepOrigem + "&sCepDestino=" + cepDestino + "&nVlPeso=" + peso + "&nCdFormato=" + formato + "&nVlComprimento=" + comp
                + "&nVlAltura=" + alt + "&nVlLargura=" + larg + "&nVlDiametro=" + diam + "&sCdMaoPropria=" + mp + "&nVlValorDeclarado=" + vd
                + "&sCdAvisoRecebimento=" + ar + "&agrupado=" + agrupado + "&quantidade=" + qtd + params, true);
        http.onreadystatechange = handleHttpResponsePesquisaRelatorios;
        http.send(null);
    }


}




/********************************************************************************/

//Response do montaHoras da coleta
function handleHttpResponseAr() {
    if (http.readyState == 4) {
        if (http.status == 200) {
            //alert("handleHTTPResponse");
            var resultado = http.responseText;
            if (resultado.toString() == "sessaoexpirada") {
                window.location = "../Portal/portal.jsp?msg=Sua sessao expirou! Para voltar ao Portal faça seu login novamente!";
            }
            /*document.getElementById("grafico1").value = resultado;
             updateChart();*/
        } else {
            alert(http.status + " - Not able to retrieve name");
        }
    }
}

// funcao que retorna as interacoes da ocorrencia solicitada
function darBaixaAr(numObjeto) {
    var dataInicio = document.getElementById("dataIni").value;
    var dataFinal = document.getElementById("dataFim").value;
    document.getElementById("img" + numObjeto).src = "../../imagensNew/cross_circle.png";
    document.getElementById("nome" + numObjeto).innerHTML = "";
    document.getElementById("data" + numObjeto).innerHTML = "";
    document.getElementById(numObjeto).value = "1";
    document.getElementById("img" + numObjeto).setAttribute('onclick', "chamaJanelaBaixaAr('" + numObjeto + "');");
    http.open("GET", "../AjaxPages/alterar_ar.jsp?numObjeto=" + numObjeto + "&baixa=0&nome=&data=&inicio=" + dataInicio + "&final=" + dataFinal, true);
    http.onreadystatechange = handleHttpResponseAr;
    http.send(null);
}

// funcao que retorna as interacoes da ocorrencia solicitada
/*function darBaixaAr2() {
 var dataInicio = document.getElementById("dataIni").value;
 var dataFinal = document.getElementById("dataFim").value;
 var nome = retirarAcentos(document.getElementById("nomeRecebedor").value);
 var data = document.getElementById("dataRecebimento").value;
 var result = 0;
 if (nome == "") {
 alert("Preencha o nome de quem recebeu o AR!");
 result = 1;
 }
 if (valida_data(document.getElementById("dataRecebimento")) == false) {
 result = 1;
 }
 if (result == 0) {
 var numObjeto = document.getElementById("numObjetoAr").value;
 document.getElementById("img" + numObjeto).src = "../../imagensNew/tick_circle.png";
 document.getElementById("nome" + numObjeto).innerHTML = nome;
 document.getElementById("data" + numObjeto).innerHTML = data;
 document.getElementById(numObjeto).value = "0";
 document.getElementById("img" + numObjeto).setAttribute('onclick', "javascript:if(confirm('Voce tem certeza que deseja marcar este objeto como nao entregue?')){chamaJanelaBaixaAr('" + numObjeto + "');}else{return false;}");
 chamaDivProtecao();
 http.open("GET", "../AjaxPages/alterar_ar.jsp?numObjeto=" + numObjeto + "&baixa=1&nome=" + nome + "&data=" + data + "&inicio=" + dataInicio + "&final=" + dataFinal, true);
 http.onreadystatechange = handleHttpResponseAr;
 http.send(null);
 }
 }*/



function handleHttpResponseAbrangenciaServ() {
    if (http.readyState === 4) {
        if (http.status === 200) {
            //alert("handleHTTPResponse");
            var resultado = http.responseText;
            if (resultado.toString().trim() === "sessaoexpirada") {
                window.location = "../../index.jsp?msgLog=3";
            } else {
                if (resultado.trim() === 'aceita') {
                    chamaDivProtecao2();
                } else if (resultado.trim() === 'ESEDEX') {
                    alert('Nao existe o ESEDEX para este CEP!');
                } else if (resultado.trim() === 'SEDEX10') {
                    alert('Nao existe o SEDEX 10 para este CEP!');
                } else if (resultado.trim() === 'SEDEX12') {
                    alert('Nao existe o SEDEX 12 para este CEP!');
                } else if (resultado.trim() === 'SEDEXHJ') {
                    alert('Nao existe o SEDEX HOJE para este CEP!');
                } else if (resultado.trim() === 'PAX') {
                    alert('Nao existe o PAX para este CEP!');
                } else if (resultado.trim() === 'MSGRIO') {
                    alert('Os envios para este CEP estao suspensos ate 20/08/2016!<br/>Motivo: Jogos Olimpicos do Rio 2016!');
                }
            }
        } else {
            alert(http.status + " - Not able to retrieve name");
        }
    }
}

// funcao que retorna o forrm parra editarr o cooletador solicitado
function verPesquisarAbrangenciaServ(cep, servico) {
    if (cep.length == 9) {
        http.open("GET", "../AjaxPages/consulta_abrangencia_serv.jsp?cep=" + cep + "&servico=" + servico, true);
        http.onreadystatechange = handleHttpResponseAbrangenciaServ;
        http.send(null);
    } else if (cep != '') {
        alert('Preencha o CEP por completo!');
        document.getElementById('cep').focus();
    }
}

function handleHttpResponseCepsSuspensos() {
    if (http.readyState === 4) {
        if (http.status === 200) {
            //alert("handleHTTPResponse");
            var resultado = http.responseText;
            if (resultado.toString().trim() === "sessaoexpirada") {
                window.location = "../../index.jsp?msgLog=3";
            } else {
                if (resultado.trim() === 'aceita') {
                    chamaDivProtecao2();
                } else if (resultado.trim() === 'MSGRIO') {
                    alert('Os envios para este CEP estao suspensos ate 20/08/2016!<br/>Motivo: Jogos Olimpicos do Rio 2016!');
                }
            }
        } else {
            alert(http.status + " - Not able to retrieve name");
        }
    }
}

// funcao que retorna o forrm parra editarr o cooletador solicitado
function verPesquisarCepsSuspensos(cep, servico) {
    if (cep.length === 9) {
        http.open("GET", "../AjaxPages/consulta_ceps_suspensos.jsp?cep=" + cep + "&servico=" + servico, true);
        http.onreadystatechange = handleHttpResponseCepsSuspensos;
        http.send(null);
    } else if (cep !== '') {
        alert('Preencha o CEP por completo!');
        document.getElementById('cep').focus();
    }
}

/***************************************************************************/

function retirarAcentos(texto) {
    var acento = 'áàãâäéèêëíìîïóòõôöúùûüçÁÀÃÂÄÉÈÊËÍÌÎÏÓÒÕÖÔÚÙÛÜÇ';
    var semacento = 'aaaaaeeeeiiiiooooouuuucAAAAAEEEEIIIIOOOOOUUUUC';
    var nova = '';

    for (i = 0; i < texto.length; i++) {
        if (acento.search(texto.substr(i, 1)) >= 0) {
            nova += semacento.substr(acento.search(texto.substr(i, 1)), 1);
        }
        else {
            nova += texto.substr(i, 1);
        }
    }

    return nova;
}