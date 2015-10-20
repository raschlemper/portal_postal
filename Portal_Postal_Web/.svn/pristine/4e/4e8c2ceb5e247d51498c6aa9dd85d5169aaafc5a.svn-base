/*
    Exemplo:
    - Cria o cookie 'CookieTeste' com o valor 'HellowWorld!' que irá expirar quando o browser for fechado.
    GerarCookie('CookieTeste', 'HellowWorld!', 0);
    - Lê o conteúdo armazenado no cookie.
    LerCookie('CookieTeste');
    - Exclúi o cookie.
    ExcluirCookie('CookieTeste');
    */

// Função para criar o cookie.
// Para que o cookie seja destruído quando o brawser for fechado, basta passar 0 no parametro lngDias.
function GerarCookie(strCookie, strValor, lngDias){
    var dtmData = new Date();
    var strExpires = "";
    if(document.getElementById("checkLembrar").checked){
        if(lngDias){
            dtmData.setTime(dtmData.getTime() + (lngDias * 24 * 60 * 60 * 1000));
            strExpires = "; expires=" + dtmData.toGMTString();
        }
        document.cookie = strCookie + "=" + strValor + strExpires + "; path=/";
    }else{
        document.cookie = strCookie + "=; path=/";
    }
}

// Função para ler o cookie.
function LerCookie(strCookie){

    var strNomeIgual = strCookie + "=";
    var arrCookies = document.cookie.split(';');

    for(var i = 0; i < arrCookies.length; i++){
        var strValorCookie = arrCookies[i];
        while(strValorCookie.charAt(0) == ' '){
            strValorCookie = strValorCookie.substring(1, strValorCookie.length);
        }
        if(strValorCookie.indexOf(strNomeIgual) == 0){
            var login = strValorCookie.substring(strNomeIgual.length, strValorCookie.length);
            document.getElementById("hoitoLogin").value = login;
            if(login != null && login != ''){
                document.getElementById("checkLembrar").checked = true;
            }
        /*return strValorCookie.substring(strNomeIgual.length, strValorCookie.length);*/
        }
    }
    return null;
}


