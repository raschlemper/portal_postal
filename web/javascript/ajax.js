
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


function encode64(input) {
    var keyStr = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=";

    var output = "";
    var chr1, chr2, chr3;
    var enc1, enc2, enc3, enc4;
    var i = 0;

    do {
        chr1 = input.charCodeAt(i++);
        chr2 = input.charCodeAt(i++);
        chr3 = input.charCodeAt(i++);

        enc1 = chr1 >> 2;
        enc2 = ((chr1 & 3) << 4) | (chr2 >> 4);
        enc3 = ((chr2 & 15) << 2) | (chr3 >> 6);
        enc4 = chr3 & 63;

        if (isNaN(chr2)) {
            enc3 = enc4 = 64;
        } else if (isNaN(chr3)) {
            enc4 = 64;
        }

        output = output + keyStr.charAt(enc1) + keyStr.charAt(enc2) +
                keyStr.charAt(enc3) + keyStr.charAt(enc4);
    } while (i < input.length);

    return output;
}


/****************************************************************/


function decode64(input) {
    var keyStr = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=";

    var output = "";
    var chr1, chr2, chr3;
    var enc1, enc2, enc3, enc4;
    var i = 0;

    // remove all characters that are not A-Z, a-z, 0-9, +, /, or =
    input = input.replace(/[^A-Za-z0-9\+\/\=]/g, "");

    do {
        enc1 = keyStr.indexOf(input.charAt(i++));
        enc2 = keyStr.indexOf(input.charAt(i++));
        enc3 = keyStr.indexOf(input.charAt(i++));
        enc4 = keyStr.indexOf(input.charAt(i++));

        chr1 = (enc1 << 2) | (enc2 >> 4);
        chr2 = ((enc2 & 15) << 4) | (enc3 >> 2);
        chr3 = ((enc3 & 3) << 6) | enc4;

        output = output + String.fromCharCode(chr1);

        if (enc3 != 64) {
            output = output + String.fromCharCode(chr2);
        }
        if (enc4 != 64) {
            output = output + String.fromCharCode(chr3);
        }
    } while (i < input.length);

    return output;

}

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

/****************************************************************/

function handleHttpResponse() {
    if (http.readyState == 4) {
        if (http.status == 200) {
            //alert("handleHTTPResponse");
            var resultado = http.responseText;
            if (resultado.toString() == "sessaoexpirada") {
                window.location = "../index.jsp?msgLog=3";
            } else {

                document.getElementById("divInteracao").innerHTML = resultado;
                document.getElementById("divProtecao").className = "mostrar";
                document.getElementById("divInteracao").className = "mostrar";
                chamaTextoInteracao();

            }

        } else {
            alert(http.status + " - Not able to retrieve name");
        }
    }
}

function handleHttpResponseUser() {
    if (http.readyState == 4) {
        if (http.status == 200) {
            //alert("handleHTTPResponse");
            var resultado = http.responseText;
            if(resultado.toString() == "sessaoexpirada"){
                window.location = "../index.jsp?msgLog=3";
            } else {
            
                document.getElementById("divInteracao").innerHTML = resultado;
                document.getElementById("divProtecao").className = "mostrar";
                document.getElementById("divInteracao").className = "mostrar";
                populateVar();

            }

        } else {
            alert ( http.status+" - Not able to retrieve name" );
        }
    }
}

// funcao que retorna o forrm parra editarr o cooletador solicitado
function verEditarColetador(idColetador) {
    http.open("GET", "../../Agencia/Coleta/coletador_editar.jsp?idColetador=" + idColetador, true);
    http.onreadystatechange = handleHttpResponse;
    http.send(null);
}

// funcao que retorna o form para editar  o tipo de coleta solicitado
function verEditarTipoColeta(idTipoColeta) {
    http.open("GET", "../../Agencia/Coleta/tipo_coleta_editar.jsp?idTipoColeta=" + idTipoColeta, true);
    http.onreadystatechange = handleHttpResponse;
    http.send(null);
}

// funcao que retorna o form para editar o usuario solicitado
function verEditarUsuario(idUsuario) {
    http.open("GET", "../../Agencia/Configuracao/usuario_editar.jsp?idUsuario=" + idUsuario, true);
    http.onreadystatechange = handleHttpResponseUser;
    http.send(null);
}

// funcao que retorna o form para editar o usuario solicitado
function verEditarContato(idContato) {
    http.open("GET", "../../Agencia/Configuracao/cliente_contatos_editar.jsp?idContato=" + idContato, true);
    http.onreadystatechange = handleHttpResponse;
    http.send(null);
}

// funcao que retorna o form para editar o usuario solicitado
function verEditarLoginPortal(id, local) {
    http.open("GET", "../../Agencia/Configuracao/cliente_usuarios_editar.jsp?id=" + id + "&local=" + local, true);
    http.onreadystatechange = handleHttpResponse;
    http.send(null);
}

// funcao que retorna o form para editar o usuario solicitado
function verEditarCartaoPostagem(idCliente, idDepartamento, cartao) {
    http.open("GET", "../../Agencia/Configuracao/cliente_cartao_dep_editar.jsp?idCliente=" + idCliente + "&idDepartamento=" + idDepartamento + "&cartao=" + cartao, true);
    http.onreadystatechange = handleHttpResponse;
    http.send(null);
}

// funcao que retorna o form para editar o usuario solicitado
function verLoginRestrito(servlet, msg, idUser) {
    http.open("GET", "../../AjaxPages/verifica_login_restrito.jsp?servlet=" + servlet + "&mensagem=" + msg + "&idUsuario=" + idUser, true);
    http.onreadystatechange = handleHttpResponse;
    http.send(null);
}

// funcao que retorna o form para editar o usuario solicitado
function verEditarObsColeta(idColeta, idColetador) {
    http.open("GET", "../../AjaxPages/coleta_alterar_obs.jsp?idColeta=" + idColeta + "&idColetador=" + idColetador, true);
    http.onreadystatechange = handleHttpResponse;
    http.send(null);
}

// funcao que retorna o form para editar o usuario solicitado
function verEditarDestinatario(idDestinatario, idCliente) {
    http.open("GET", "../../Cliente/Cadastros/destinatario_editar.jsp?idDestinatario=" + idDestinatario + "&idCliente=" + idCliente, true);
    http.onreadystatechange = handleHttpResponse;
    http.send(null);
}

// funcao que retorna o form para editar o usuario solicitado
function verEditarRemetente(idRemetente, idCliente) {
    http.open("GET", "../../Cliente/Cadastros/remetente_editar.jsp?idRemetente=" + idRemetente + "&idCliente=" + idCliente, true);
    http.onreadystatechange = handleHttpResponse;
    http.send(null);
}


/****************************************************************/


//response do buscarDadosContato
function handleHttpResponse2() {
    if (http.readyState == 4) {
        if (http.status == 200) {
            //alert("handleHTTPResponse");
            var resultado = http.responseText;
            if (resultado.toString() == "sessaoexpirada") {
                window.location = "../index.jsp?msgLog=3";
            } else {
                var aux = resultado.split(";");
                var email = aux[0];
                var fone = aux[1];
                var setor = aux[2];

                document.getElementById("email").value = email;
                document.getElementById("telefone").value = fone;
                document.getElementById("setor").value = setor;
            }
        } else {
            alert(http.status + " - Not able to retrieve name");
        }
    }
}

//funcao que consulta os dados do contato solicitado
function buscarDadosContato(idCliente) {
    var contato = document.getElementById('contato').value;
    http.open("GET", "../../AjaxPages/consultaContato.jsp?idContato=" + contato, true);
    http.onreadystatechange = handleHttpResponse2;
    http.send(null);
}

//response do buscarDadosContato
function handleHttpResponseComboContato() {
    if (http.readyState == 4) {
        if (http.status == 200) {
            //alert("handleHTTPResponse");
            var resultado = http.responseText;
            if (resultado.toString() == "sessaoexpirada") {
                window.location = "../index.jsp?msgLog=3";
            } else {
                document.getElementById('divContato').innerHTML = resultado;
            }
        } else {
            alert(http.status + " - Not able to retrieve name");
        }
    }
}

//funcao que consulta os dados do contato solicitado
function montaComboContato(idCliente) {
    http.open("GET", "../../AjaxPages/montaComboContato.jsp?&idCliente=" + idCliente, true);
    http.onreadystatechange = handleHttpResponseComboContato;
    http.send(null);
}


/****************************************************************/


//response do verificarLoginSenha
function handleHttpResponse6() {
    if (http.readyState == 4) {
        if (http.status == 200) {
            var flag = http.responseText;
            if (flag.toString() == "sessaoexpirada") {
                window.location = "../index.jsp?msgLog=3";
            } else {

                var vFlag = parseInt(flag);
                if (vFlag == 1) {
                    document.formLoginRestrito.submit();
                    return true;
                } else {
                    alert('Login ou Senha incorreta!');
                    return false;
                }

            }
        } else {
            alert(http.status + " - Not able to retrieve name");
        }
    }
}

//funcao que verifica se o login e senha solicitados sao válidos
// se forem validos chama o servlet '../ServExcluirColeta'
function verificarLoginSenha(login, senha, nivel) {
    http.open("GET", "../../AjaxPages/verificarLogin.jsp?login=" + login + "&senha=" + senha + "&nivel=" + nivel, true);
    http.onreadystatechange = handleHttpResponse6;
    http.send(null);
}


/****************************************************************/


//Ajax do Login (Confere login)
function handleHttpResponseLogin() {
    if (http.readyState == 4) {
        if (http.status == 200) {
            //alert("handleHTTPResponse");
            var result = http.responseText;
            if (result.toString() == "sessaoexpirada") {
                window.location = "../index.jsp?msgLog=3";
            } else {

                document.getElementById('foo').innerHTML = http.responseText;

            }
        } else {
            alert(http.status + " - Not able to retrieve name");
        }
    }
}

//funcao que verifica se o login digitado já é existente
function confereLoginUsuarioNovo(login) {
    http.open("GET", "../../AjaxPages/confereLoginUsuario.jsp?login=" + login, true);
    http.onreadystatechange = handleHttpResponseLogin;
    http.send(null);
}

//funcao que verifica se o login digitado já é existente
function confereLoginColetadorNovo(login) {
    http.open("GET", "../../AjaxPages/confereLoginColetador.jsp?login=" + login, true);
    http.onreadystatechange = handleHttpResponseLogin;
    http.send(null);
}

//funcao que verifica se o login digitado já é existente
function confereLoginPortal(login) {
    http.open("GET", "../../AjaxPages/confereLoginPortal.jsp?login=" + login, true);
    http.onreadystatechange = handleHttpResponseLogin;
    http.send(null);
}


/****************************************************************/


//Ajax do Login (Confere login)
function handleHttpResponseLoginEditar() {
    if (http.readyState == 4) {
        if (http.status == 200) {
            //alert("handleHTTPResponse");
            var result = http.responseText;
            if (result.toString() == "sessaoexpirada") {
                window.location = "../../index.jsp?msgLog=3";
            } else {
                document.getElementById('fooEditar').innerHTML = http.responseText;
            }
        } else {
            alert(http.status + " - Not able to retrieve name");
        }
    }
}

//funcao que verifica se o login digitado já é existente
function confereLoginUsuarioEditar(login) {
    http.open("GET", "../../AjaxPages/confereLoginUsuario.jsp?login=" + login, true);
    http.onreadystatechange = handleHttpResponseLoginEditar;
    http.send(null);
}

//funcao que verifica se o login digitado já é existente
function confereLoginColetadorEditar(login) {
    http.open("GET", "../../AjaxPages/confereLoginColetador.jsp?login=" + login, true);
    http.onreadystatechange = handleHttpResponseLoginEditar;
    http.send(null);
}

//funcao que verifica se o login digitado já é existente
function confereLoginPortalEditar(login) {
    http.open("GET", "../../AjaxPages/confereLoginPortal.jsp?login=" + login, true);
    http.onreadystatechange = handleHttpResponseLoginEditar;
    http.send(null);
}


/****************************************************************/

function handleHttpResponseLogColeta() {
    if (http.readyState == 4) {
        if (http.status == 200) {
            //alert("handleHTTPResponse");
            var resultado = http.responseText;
            if (resultado.toString() == "sessaoexpirada") {
                window.location = "../index.jsp?msgLog=3";
            } else {
                var aux = resultado.split(';');
                var idColeta = parseInt(aux[1]);

                document.getElementById("tipFoo" + idColeta).innerHTML = aux[0];
                document.getElementById("tooltip" + idColeta).className = "mostraTooltip";
            }
        } else {
            alert(http.status + " - Not able to retrieve name");
        }
    }
}

// funcao que retorna o form para editar o usuario solicitado
function buscaLogColeta(idColeta) {
    http.open("GET", "../../AjaxPages/consultaLogColeta.jsp?idColeta=" + idColeta, true);
    http.onreadystatechange = handleHttpResponseLogColeta;
    http.send(null);
}

function handleHttpResponseLogCli() {
    if (http.readyState == 4) {
        if (http.status == 200) {
            var resultado = http.responseText;
            if (resultado.toString() == "sessaoexpirada") {
                window.location = "../index.jsp?msgLog=3";
            } else {
                var aux = resultado.split(';');
                var idColeta = parseInt(aux[1]);

                document.getElementById("tipFooCli" + idColeta).innerHTML = aux[0];
                document.getElementById("tooltipCli" + idColeta).className = "mostraTooltip";
            }
        } else {
            alert(http.status + " - Not able to retrieve name");
        }
    }
}
function buscaCli(idCli, idColeta) {
    http.open("GET", "../../AjaxPages/ajaxCli.jsp?idCli=" + idCli + "&idColeta=" + idColeta, true);
    http.onreadystatechange = handleHttpResponseLogCli;
    http.send(null);
}

/****************************/


function handleHttpResponseAlteraUsaEtiquetador() {
    if (http.readyState == 4) {
        if (http.status == 200) {
            //alert("handleHTTPResponse");
            var result = http.responseText;
        } else {
            alert(http.status + " - Not able to retrieve name");
        }
    }
}

//funcao que verifica se o login digitado já é existente
function alteraUsaEtiquetador(idCliente, nomeBD) {

    var usaEtiquetador = 0;
    if (document.getElementById("usa_" + idCliente).value == 0) {
        usaEtiquetador = 1;
        document.getElementById("usa_" + idCliente).value = 1;
        document.getElementById("etq_" + idCliente).src = "../../imagensNew/tick_circle.png";

        document.getElementById("contrato_" + idCliente).href = "cliente_contrato.jsp?idCliente=" + idCliente;
        document.getElementById("contrato_" + idCliente).style.cursor = "pointer";
        document.getElementById("contrato_" + idCliente).style.color = "blue";
    } else {
        document.getElementById("usa_" + idCliente).value = 0;
        document.getElementById("etq_" + idCliente).src = "../../imagensNew/cross_circle.png";

        document.getElementById("contrato_" + idCliente).href = null;
        document.getElementById("contrato_" + idCliente).style.cursor = "default";
        document.getElementById("contrato_" + idCliente).style.color = "gray";
    }

    http.open("GET", "../../AjaxPages/altera_usa_etiquetador.jsp?idCliente=" + idCliente + "&usaEtiquetador=" + usaEtiquetador + "&nomeBD=" + nomeBD, true);
    http.onreadystatechange = handleHttpResponseAlteraUsaEtiquetador;
    http.send(null);
}



function handleHttpResponseCliSigep() {
    if (http.readyState == 4) {
        fecharTelaEspera();
        if (http.status == 200) {
            var resultado = http.responseText;
            if (resultado.toString() == "sessaoexpirada") {
                window.location = "../index.jsp?msgLog=3";
            } else {
                var aux = resultado.split(';');
                if (aux[0] !== 'erro') {
                    if (aux[0] === '1') {
                        document.getElementById('statusCartao').value = '1';
                    } else {
                        document.getElementById('statusCartao').value = '2';
                    }
                    document.getElementById('codDir').value = aux[1];
                    document.getElementById('anoContrato').value = aux[2];
                    document.getElementById('ufContrato').value = aux[3];
                    document.getElementById('nomeSara').value = aux[4];
                    document.getElementById('cnpjSara').value = aux[5];
                    document.getElementById('codAdm').value = aux[6];
                    document.getElementById('vigenciaFim').value = aux[7];
                } else {
                    alert(aux[1]);
                }
            }
        } else {
            alert(http.status + " - Not able to retrieve name");
        }
    }
}
function pesquisaDadosCliSigep() {
    abrirTelaEspera();
    var contrato = document.getElementById('numContrato').value;
    var cartao = document.getElementById('cartaoPostagem').value
    var idCli = document.getElementById('idCliente').value
    http.open("GET", "../../AjaxPages/pesquisaDadosCliSigep.jsp?numContrato=" + contrato + "&cartaoPostagem=" + cartao + "&idCliente=" + idCli, true);
    http.onreadystatechange = handleHttpResponseCliSigep;
    http.send(null);
}
